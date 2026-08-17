# Virtual Component Semantics

Status: proposed normative design for Slabee 1.0.0-beta.4

Target platform: Minecraft 1.21.1 with Fabric

## Purpose

`DoubleSlab` and `DoubleVerticalSlab` occupy one Minecraft block cell and use one
block entity, but they must behave as two independent half-blocks from a player's
point of view. This document calls those halves **virtual components**.

The implementation detail of sharing a cell must not make one component inherit
the material behavior of its partner. Whenever Minecraft exposes a suitable
event, a state change, removal, movement, or contact effect must be applied only
to the component that caused or received it.

This document is the technical design authority for beta.4. Product decisions
approved by the owner are authoritative in
[`beta4-owner-guide.ja.md`](../roadmap/beta4-owner-guide.ja.md); this document must
be updated to match them. Issue #6 is historical planning material, not a
specification. The commits on
`backup/windows-grass-propagation-2026-08-11` may be used as design evidence, but
must not be cherry-picked. Any useful idea from that branch must be reimplemented
against this model and covered by tests.

## Normative language

The words **must**, **should**, and **may** describe requirements, preferred
behavior, and permitted behavior respectively. When Minecraft's one-cell model
makes a requirement impossible, the implementation must document the constraint,
choose an explicit approximation, and add a regression test for that approximation.
It must not silently substitute a different behavior.

## Component model

A virtual component consists of:

- a container position;
- a slot (`positive` or `negative`);
- a mapped single Slab or VerticalSlab block;
- geometry derived from the container kind, slot, and axis; and
- component-specific state that cannot be derived safely.

The pair is an atomic storage container, not an atomic gameplay material. Reading
or changing one component must not change the partner's identity, orientation,
axis, or stored state unless a documented Minecraft constraint requires a
cell-wide result.

### Canonical slot mapping

The mapping below matches the current implementation and is fixed for save
compatibility.

| Container | Axis | Positive component | Negative component | Derived single-block state |
| --- | --- | --- | --- | --- |
| `DoubleSlab` | Y | Upper half | Lower half | positive `TYPE=TOP`; negative `TYPE=BOTTOM` |
| `DoubleVerticalSlab` | X | East half | West half | positive `FACING=EAST`; negative `FACING=WEST` |
| `DoubleVerticalSlab` | Z | South half | North half | positive `FACING=SOUTH`; negative `FACING=NORTH` |

`positive` and `negative` are storage slot names. They must not be reinterpreted
from player facing, placement order, camera direction, or block identity.

### State ownership

Every property must have exactly one ownership category. Container state and
component state are deliberately separate.

#### 1. State derived from the slot

These values describe container geometry and must be reconstructed rather than
stored independently for each component:

- horizontal Slab `TYPE`;
- VerticalSlab `FACING`.

A component replacement must inherit the slot geometry. Replacing the east
component, for example, must not copy a source state's west-facing value into the
east slot.

#### 2. State owned and persisted by the container

The `DoubleVerticalSlab` X/Z axis belongs to the container, not to either slot.
It determines whether positive/negative mean east/west or south/north and must be
persisted once for the container. Component replacement must not change it.

The outer Double block state's `AXIS` is the runtime source of truth for a loaded
beta.4 container. Any duplicate axis value retained in block-entity NBT is
validation or legacy migration data, not an independent component property.

#### 3. State recalculated from the environment

Properties such as `SNOWY` and leaf `DISTANCE` describe the current surroundings.
They should be
recalculated after placement, component replacement, component removal,
neighbor updates, and chunk loading. They should not be treated as durable
component history.

Recalculation must use the virtual component's exposed geometry. A partner may
cover a face that would be exposed on a single Slab, and a neighboring Slabee
component may cover only part of a face.

#### 4. State persisted per component

Properties whose values cannot be reconstructed without changing gameplay must
be stored with the component. Examples include:

- leaf `PERSISTENT` and other player-placed/natural distinctions;
- huge-mushroom directional face states;
- material-specific age or stage values when beta.4 adds a mapped behavior; and
- any future property explicitly classified as component-owned.

The persistence layer should store only supported properties for the mapped
component. Unknown or invalid property values must fall back safely and produce
a diagnostic suitable for debugging a migration; they must not invalidate the
partner.

## Component operations and invariants

The beta.4 implementation should expose a small internal API rather than duplicate
slot logic in each feature. Names are illustrative, not a required Java API.

| Operation | Required result |
| --- | --- |
| `getComponent(slot)` | Return an immutable reference containing position, container kind, location, mapped state, and geometry. A single block uses an explicit `SINGLE` location; only Double containers use `POSITIVE` or `NEGATIVE`. The operation must not mutate or mark the block entity dirty. |
| `replaceComponent(ref, state)` | Replace only the referenced component, normalize slot-derived state, preserve the partner, recalculate environment-derived state, mark dirty, and notify clients/neighbors once. |
| `removeComponent(ref, cause) -> MutationResult` | Remove only the referenced component. A Double is converted to the correct remaining single Slab or VerticalSlab; a single component clears the cell or restores its held fluid as defined below. |
| `getExposedFaces(ref)` | Return component face regions not covered by the partner or neighboring collision/support geometry. |
| `getContact(ref, direction)` | Return the projected overlap between a component face and the adjacent cell's relevant component or block. |
| `isSupported(ref, direction)` | Apply the support rules below to the requested face and component. |

All mutating operations must satisfy these invariants:

1. The non-target partner retains its block identity and all component-owned state.
2. The container-owned axis and slot-to-world mapping remain unchanged.
3. When a partner remains, it becomes its mapped single Slab or VerticalSlab, with
   the correct `TYPE` or `FACING` for its former slot.
4. A component must never survive only as an orphaned block-entity entry. A
   removal that leaves no component must not leave a Double container or block
   entity behind.
5. World-state writes, block-entity dirty marking, listener updates, and neighbor
   notifications are emitted once per transaction, not once per changed component.
6. If both components change in the same game event, the result is deterministic
   and is committed as one container transaction.
7. A failed operation leaves both components unchanged.
8. Loot and component-specific effects are emitted exactly once for each affected
   component, even when two components are changed by one transaction. Transaction-
   level notifications must not suppress or duplicate per-component outcomes.

### Mutation causes and results

`MutationCause` must carry semantic cause information rather than only a
`dropItems` boolean. At minimum, player survival break, creative break, natural
disappearance, combustion, and transfer into falling movement must be
distinguishable. The cause determines whether loot is generated and which sound,
particle, game event, tool, entity, or source-specific callback applies. A cause
such as creative removal, natural disappearance, combustion, or falling transfer
must not accidentally produce ordinary survival-break drops. A natural cause may
still produce its source-specific loot, such as vanilla leaf-decay loot, when that
behavior explicitly requires it.

`MutationResult` must expose the committed structural outcome without requiring a
caller to infer it by rereading the world:

| Outcome | Meaning |
| --- | --- |
| `CELL_CLEARED` | No component or fluid remains; the final cell is air. |
| `FLUID_RESTORED` | No component remains, and the single component's held `FluidState` became the cell fluid. |
| `SINGLEIZED` | One component remains and was converted from a Double container to its correct single shape. |
| `NO_CHANGE` | Validation failed or the target no longer matched; the transaction emitted no mutation effects. |

The names are illustrative, but the result must distinguish these outcomes and
record the removed component, final block/fluid state, and cause needed to audit
follow-up behavior. World notifications remain once per transaction, while loot,
sound, particles, and game events are produced exactly as required by the cause
and affected component, never by the number of storage writes.

### Removal and singleization

Removing the positive component of a horizontal pair leaves a bottom Slab.
Removing the negative component leaves a top Slab. For a vertical pair, the
remaining component keeps its world-facing side: east, west, south, or north.

Singleization must preserve every state owned by the remaining component. State
that belongs to the Double container, including render variants derived from the
two materials, must be discarded and recalculated for the single block.

A single block has one `SINGLE` component and no partner. Removing a normal single
therefore replaces the cell with air and returns `CELL_CLEARED`. Removing a
waterlogged single restores its held `FluidState` to the cell according to
Minecraft 1.21.1 vanilla removal behavior and returns `FLUID_RESTORED`. Neither
path may leave a Double block, block entity, or orphaned component data. A Double
does not retain historical per-component waterlogging, so singleization after
removing one Double component does not restore water expelled when that Double was
formed.

### Replacement

Replacement is an identity or state transition in place. Grass becoming dirt,
sponge becoming wet sponge, copper oxidizing, coral dying, or one side being
stripped are all the same primitive operation. The replacement must:

- map the result to the same shape family;
- retain the target slot;
- retain component-owned properties that are valid for the result;
- initialize newly introduced properties according to vanilla behavior; and
- preserve the untouched component exactly.

### Movement

When a behavior can represent a half independently, only the target component
should move. Falling sand is the beta.4 reference implementation: it is removed
from the pair, the partner is singleized, and the falling operation must preserve
enough data to restore the component's Slab/VerticalSlab geometry and persisted
state.

The accepted B1 mechanism extends vanilla `FallingBlockEntity` with a Slabee
namespaced payload. It stores a schema version, component BlockState, container
kind, slot, and vertical axis. Beta.4 does not register a custom falling entity or
use Fabric Data Attachment for this payload. A non-Slabee block is accepted only
through an explicit adapter registration.

This approved mechanism has readiness `Pending feasibility spike`. Before
production falling work, the independent spike must prove save/reload while
airborne, exact source-partner
retention, landing as a Single and recombination, no duplication or loss,
representative Concrete Powder hardening, spawn-failure rollback, and chunk-boundary
behavior. Failure of any conservation or rollback case stops the feature PR rather
than silently selecting another transport.

Minecraft piston movement is cell-based. Beta.4 does not implement movement of
only one component; that is owner-approved future research and is not implied by
the falling-block design. Beta.4 retains the current whole-cell result for each
single or Double shape after verifying it against Minecraft 1.21.1 code and tests.

At the reviewed baseline, both Double containers use the default `NORMAL`
`PistonBehavior`, extend `BlockWithEntity`, and create a block entity. Minecraft
1.21.1 `PistonBlock.isMovable` rejects the normal movement path when a state has a
block entity. No Slabee-wide piston override was found. This code evidence must not
be generalized to every single mapping or treated as runtime proof: the source
`PistonBehavior` copied or selected by each Single Slab/VerticalSlab registration,
and the actual Double result, must be audited and fixed by GameTests.

Those tests must prove that piston extension, sticky retraction, push limits, and
chunk save/reload do not lose or duplicate a component, lose a block entity,
corrupt component NBT, or desynchronize client and server. If the current behavior
is unsafe, beta.4 may explicitly refuse movement for the affected container. It
must not add component-only movement as the fix. A future investigation may study
component payloads, push-chain conflicts, destination recombination, and moving-
block-entity synchronization independently of the beta.4 release gate.

## Surface and support rules

The implementation must reason about rectangular face regions, not only about a
six-direction neighbor relation.

### Contacting face

A component contacts an adjacent block when the projection of its face overlaps
the supporting, colliding, fluid, fire, or effect-bearing region on the adjacent
cell's opposite face. The check must use the behavior's relevant geometry; visual
occlusion alone is not a gameplay support rule.

The internal plane between two components is contact between partners, but it is
not exposed to the world. A behavior may use this internal contact only when the
vanilla rule accepts that material as support. For example, an upper falling
component can be supported by its lower partner.

### Exposed face

A face region is exposed when it is not occupied by the partner and is not fully
covered by relevant adjacent geometry. Effects that arrive from a direction,
such as fire or water contact, target only components whose face projection is
reached from that direction.

A cell-wide medium can still contact more than one exposed component. If the same
fire cell touches both components, each component receives an independent burn
check. One successful burn must not force the other check to succeed.

### Supporting face

Support is evaluated in the direction used by the vanilla behavior.

- A normal full block supplies cell-wide support on its solid face.
- Slabee-to-Slabee support is decided per component by projected contact.
- An upper horizontal component can be supported by its lower partner.
- A gravity-affected lower horizontal component still needs support below the
  cell; a non-falling upper partner does not prevent the lower component from
  falling.
- Each half of a vertical pair tests the matching half of the cell below. A full
  supporting block below supports both halves.

These rules describe structural support only. They do not make ordinary
non-falling blocks fall when their support disappears.

## Fluids and waterlogging

Minecraft stores one `FluidState` per block cell. Slabee does not introduce a
half-cell fluid model in beta.4.

- A single horizontal or vertical Slab uses `WATERLOGGED` normally.
- Removing a waterlogged single restores its held `FluidState` to the cell; removing
  an ordinary single leaves air. Cause-specific effects are handled separately
  from this structural result.
- A Double container fills the cell, so it does not preserve a historical
  `WATERLOGGED` value for either component.
- Water expelled when a single becomes a Double is not restored automatically
  when one component is later removed.
- Contact with water is tested against the target component's exposed faces.
- Removing or creating water remains a cell-wide operation. Sponge absorption
  and concrete hardening must document this distinction.
- Exact placement, combination, removal, update, and `FluidState` results must be
  fixed by GameTests before feature work depends on them.

## Target interaction semantics

The following decisions are part of the current beta.4 target.

### Bubble sources

- A horizontal soul-sand or magma component exposes a full-width top face and
  should produce vanilla-strength bubbles when that top face contacts a valid
  water column.
- A vertical soul-sand or magma component should affect only the half-column above
  its component footprint, at vanilla strength for entities in that half.
- If soul sand and magma share a `DoubleVerticalSlab`, the soul-sand half pushes up
  and the magma half pulls down. In the primary target, each entity uses the
  proportion of its AABB's horizontal projection intersecting each component half
  as the weights for composing the contacted forces; a center-coordinate tie-break
  is not used.
- The primary visual target shows the two bubble directions over their respective
  component halves. Physical direction, bubble direction, and bubble presence must
  agree.
- The approved bubble Decision has readiness `Pending feasibility spike`. Because
  vanilla `BubbleColumnBlock.DRAG` is cell-wide, an independent spike must
  verify the area-weighted force, left/right rendering or particles, asymmetric
  vanilla velocity changes and caps, near-zero results, multiple entities, both
  axes, client prediction, and Player/Mob/Item/Boat behavior before production code.
- An incomplete spike or an isolated failing test does not authorize fallback.
  Fallback becomes a candidate only when recorded evidence shows that Minecraft
  1.21.1 expressiveness, compatibility, stability, or the B4 performance criteria
  prevent a safe primary implementation. The spike record must list failed
  conditions, attempted approaches, measured performance, and remaining constraints.
  Before fallback is adopted, those results must be reported to the owner in Japanese
  and receive explicit approval. Codex must not select fallback automatically.
- After that explicit approval, only the cell-wide fixed-area fallback described
  here may be implemented. This fallback never uses an entity AABB. It computes
  one force for the cell as
  `sum(exposed-top-area fraction of component × corresponding vanilla bubble force)`
  and applies that same result to every entity in the cell.
- The fixed exposed-top-area fractions are: 1.0 for a horizontal Slab; 0.5 for a
  VerticalSlab; 0.5 for each `DoubleVerticalSlab` component; and 1.0 for only the
  water-contacting upper component of a horizontal Double, with 0 for its covered
  lower component. A non-bubble-source component contributes 0.
- Two same-type vertical components therefore sum to one vanilla full-block force.
  A Soul/Magma vertical pair composes 0.5 of the vanilla upward force with 0.5 of
  the vanilla downward force. Entity position and entity count never change this
  fallback force or direction.
- The feasibility spike fixes the fallback's zero/near-zero threshold, whether
  particles appear at that threshold, and the exact order for applying the composed
  force to asymmetric vanilla velocity changes and caps.

Primary acceptance requires the calculated per-entity AABB-overlap force to match
observed force for Player, Mob, Item, and Boat across both axes, slots, center
boundaries, multiple-entity cases, and client/server behavior. Its left/right
bubbles must match physical direction and presence, and compatibility, stability,
and B4 performance checks must pass.

Fallback acceptance is evaluated separately and only after owner approval. The
fixed-area formula must produce one entity-position-independent and entity-count-
independent vector, apply it identically to every entity in the cell, keep physical
direction, bubble direction, and bubble presence consistent under the finalized
threshold and velocity-cap order, and pass fallback stability, compatibility, and
B4 performance checks. If fallback fails, implementation stops for owner review;
it must not proceed automatically to another approximation.

### Fire

Fire contact is mapped from the contacting face region to a component. A single
fire can contact both halves, in which case ignition and burn-out are evaluated
independently using each source block's vanilla flammability rules. After one
component is consumed, the other is singleized. If both are consumed in the same
update, both eligibility and random outcomes are calculated from the unchanged
pre-transaction snapshot and committed together. Evaluation order must not alter
either slot's result.

There is no half-cell fire. If one component burns, the surviving component is
singleized and the occupied cell contains no fire. Only when both components burn
and the cell is fully cleared may the implementation evaluate vanilla placement,
survival, rain, and fluid conditions once. It places normal Fire or Soul Fire when
those conditions and the approved type-selection rule allow it; otherwise it
leaves air. The transaction exposes no intermediate Single state to neighbor
updates.

Soul Fire selection is already an owner-approved beta.4 requirement:

- a single horizontal Slab produces Soul Fire above it when its exposed top
  component is mapped to soul sand or soul soil;
- a horizontal Double uses only its positive upper component for the external top
  surface;
- a VerticalSlab or DoubleVerticalSlab uses the component contacted by the fire;
- when the same fire cell contacts both a normal component and a soul-fire-base
  component, Soul Fire has priority; and
- selecting the fire type must not modify the partner component and should retain
  vanilla ignition and extinguishing conditions wherever the one-cell model allows.

### Water contact

Water reaches a component through its exposed faces, but water removal and
creation remain cell-wide. A component may change independently because of that
contact even though the source water cannot be represented as half a cell.

### Ice melting

Only normal Ice uses the melt path; Packed Ice and Blue Ice do not. When one Ice
component melts and the partner can be waterlogged, the partner is singleized with
`WATERLOGGED=true`. When the partner cannot be waterlogged, it remains dry and the
melt creates no water. When all Ice in the cell melts, normal dimensions create
source water and ultrawarm dimensions create air. Slabee never pushes melt water
into an adjacent cell to preserve volume.

### Concrete powder hardening

Minecraft 1.21.1 `ConcretePowderBlock` hardens when its current position already
contains a water-tagged `FluidState`, or when `hardensOnAnySide` finds qualifying
water on a non-`DOWN` side. Water merely below dry powder does not harden it in
place. For a neighboring water-bearing block, vanilla also rejects contact when
that neighbor's face toward the powder is a solid full square. Falling powder
performs the same hardening check through its placement/landing path.

Slabee must retain those distinctions per component:

- water only below an otherwise dry component does not harden it in place;
- qualifying contact from above or a horizontal side hardens only the contacted
  powder component;
- a waterlogged single, whose current cell contains water, takes the in-water
  hardening path;
- a hard full-square face between the component and water blocks the contact under
  the vanilla side-solid rule; and
- a component falling into water uses the falling/landing hardening path rather
  than the stationary neighbor path.

Projection selects the contacted component for Vertical and Double shapes, but it
does not change the vanilla excluded `DOWN` direction or side-solid requirement.
The partner and slot are preserved through the common replacement operation.

### Sponge transactions

Sponge activation must not prefer a fixed slot. Every dry sponge component and
its reachable water set are evaluated from the same unchanged pre-transaction
snapshot. The transaction then applies the symmetric result:

- each component that met the activation rule in that snapshot becomes wet;
- if both components met it, both become wet, including when their reachable water
  sets overlap;
- the union of all selected water positions is removed, so each water block is
  removed at most once; and
- each component keeps its own vanilla-bounded search limit, while overlapping
  removals do not count twice in the world mutation.

World writes and notifications are transaction-level. Wet-state transitions and
their component-specific effects are produced once per activated sponge component.

### Logs and huge mushrooms

Log grain-axis parity is intentionally outside beta.4. Existing Slabee log Slabs
retain their current fixed grain direction because full axis support would require
new cut-face assets, new block state, and model variants. Axe stripping remains in
scope: it replaces only the selected component with the corresponding stripped
Slab while retaining the fixed grain direction and the partner unchanged.

Huge mushroom face behavior is in beta.4. Red mushroom blocks, brown mushroom
blocks, and mushroom stems must preserve the vanilla 1.21.1 six directional face
states per component. Placement, generation, neighbor changes, removal that exposes
a formerly internal face, save/reload, and singleization must retain the correct
outer, inner, and stem appearance. Before implementation, the asset/UV investigation
must determine whether vanilla textures and the existing Slabee UV extraction are
sufficient. Existing textures are reused when sufficient; any need for a new asset
is reported to the owner instead of being filled with a placeholder. This asset
gate is only an implementation prerequisite, not completion evidence. The feature
is complete only after BlockState and per-component persistence, models and UVs,
vanilla neighbor behavior, every single/Double shape, newly exposed partner faces,
save/reload, client synchronization, and rendering all pass their required tests.

### Leaf loot

Leaf loot parity is an owner-approved beta.4 requirement independent of whether
distance propagation and natural-decay mechanics are ready. Loot is evaluated only
for the component actually broken or removed:

- shears or Silk Touch drop exactly one matching leaf Slab item for that component;
- ordinary player breaking and natural decay use the mapped vanilla leaf species'
  loot families and conditions, including saplings, sticks, apples where applicable,
  and Fortune influence;
- one half component targets one half of the vanilla full-leaf block's expected
  value, while two matching leaf components together target one vanilla block's
  total expected value;
- a mixed Double evaluates only the removed leaf component and never the partner;
  and
- creative removal and any other `MutationCause` whose policy produces no loot
  remain drop-free.

The accepted B3 algorithm evaluates the mapped vanilla leaf loot once for each
removed component, passing the original tool and Fortune context. Each ordinary
`ItemStack` produced by that evaluation receives its own independent 50% keep/drop
roll. The algorithm does not apply one 50% roll to the loot result as a whole and
does not change the `count` inside a retained stack. The roll stream must be
reproducible from a fixed seed. Shears and Silk Touch bypass thinning and produce
their exact one matching Leaf Slab result. Creative, Fire, and other no-loot
mutation causes produce nothing. A mixed Double evaluates only the removed
component's leaf species.

This transform is required to match expected values, not the exact full-block loot
distribution. This section is the single source of truth for the formal suite's
seed literals and trial-count representation:

- `LEAF_LOOT_FORMAL_SEEDS = [0x534C414245453031L, 0x534C414245453032L, 0x534C414245453033L]`;
- `LEAF_LOOT_FORMAL_TRIALS_PER_CONDITION = 1_000_000`.

For each of the three fixed seeds and every probabilistic loot condition, execute
`n = LEAF_LOOT_FORMAL_TRIALS_PER_CONDITION` paired trials. For each target item in
trial `i`, define:

- `V_i`: item count produced by one mapped vanilla leaf block;
- `S_i`: total item count produced by two same-species Slabee leaf components; and
- `D_i = S_i - V_i`.

Let `mean_D` be the sample mean of the `D_i` values and let `s_D` be their sample
standard deviation, calculated with denominator `n - 1`. Calculate
`SE_D = s_D / sqrt(n)`. A seed/condition/item passes only when
`abs(mean_D) <= 5 * SE_D`. Treat the trials as paired observations and calculate
the standard error directly from `D_i`; do not add the vanilla-side and Slabee-side
sample variances as if the samples were independent. If `s_D = 0`, pass only when
`mean_D = 0`; otherwise fail. Every fixed seed, every probabilistic condition, and
every target item must pass. This is an expected-value test and does not require
identical distributions.

Shears, Silk Touch, Creative, Fire, and other deterministic outcomes use exact
equality assertions instead of the statistical criterion. Normal CI contains only
short deterministic unit tests and fixtures, including fixed-seed reproducibility,
duplicate vanilla-loot evaluation, partner leakage, whole-result thinning, and
stack-count modification checks. The separate formal statistical task runs at
Stage 12 completion and is rerun unchanged by Stage 14 before beta.4 release, not
on every CI run.

The B3 Decision has no remaining feasibility gate, but it waits for the common
component/loot foundation and ordered Leaves Stage work, so its readiness is
`Pending dependency`, not an unanswered design decision.

## Event and performance policy

Virtual behavior must be driven by the least continuous mechanism that matches
vanilla:

1. direct player/item interactions;
2. entity contact callbacks;
3. neighbor updates;
4. scheduled ticks;
5. vanilla-frequency random ticks; and
6. existing world or block events.

Beta.4 must not add an unconditional continuous block-entity tick to every Double
container. Existing continuous ticking should be reduced or removed when the
component operations make it unnecessary. Delayed work should be represented by
scheduled block ticks or a bounded event queue whose pending state survives a
save when required.

Before behavior changes, the project must record a repeatable baseline for idle
Double containers, random-tick-heavy scenes, neighbor-update bursts, bubble
columns, falling, fire, sponge, client rendering, and chunk save/load. Measure 0,
1,000, and 10,000 Double fixtures where applicable. Reports include server MSPT and
client frame-time p50/p95/p99, JFR evidence, matching vanilla and Single Slab
controls, and time per 1,000 events. An unimplemented-main result is an
absence-of-work baseline and cannot be the only percentage comparison for a new
feature.

The initial full procedure uses a fixed Java 21/hardware/runtime configuration,
10-minute warm-up, 15-minute measurement, and five fresh-JVM trials unless the pilot
versions a justified change. For each declared comparison of a time metric, define
`delta_ms = candidate_ms - baseline_ms` using candidate and baseline values of that
same metric. The provisional thresholds are
`idle_threshold_ms = max(0.05 * baseline_ms, 3 * MAD_ms, 0.5 ms)` and
`burst_threshold_ms = max(0.10 * baseline_ms, 3 * MAD_ms, 2 ms)`, with
`regression = (delta_ms > corresponding_threshold_ms)`. The percentage term is
therefore converted to milliseconds before `max`; no dimensionless percentage is
compared directly with milliseconds.

Server MSPT and client frame time are calculated separately with their own baseline,
MAD, delta, and threshold. Client pass/fail uses frame time in milliseconds rather
than FPS; FPS may remain informational. For a non-time metric such as allocations,
convert the percentage and MAD terms to that metric's own unit and use
`max(rate * baseline_metric, 3 * MAD_metric)` without either millisecond absolute
floor. Mandatory investigation whenever server p99 remains above 50 ms is unchanged.
These provisional values are not final release limits until the B4 pilot records
its evidence and versions the result.

Normal feature PRs run shortened affected scenarios. Stage completion and the
beta.4 release candidate run the full fixed-environment suite. GitHub Actions shared
runners may run correctness tests but never determine performance pass/fail. Later
PRs and the release gate refer to the versioned procedure rather than inventing new
criteria. A performance optimization is accepted only when it fixes a measured
regression or bottleneck and preserves component semantics.

## Persistence and legacy NBT compatibility

Worlds created by beta.3 and by the pre-beta.4 `main` branch must remain loadable.
The current keys and meanings are compatibility inputs:

- `positive_slab.id` and `negative_slab.id`;
- the legacy horizontal per-slot `facing` values, even where geometry is derived;
  and
- the DoubleVertical `axis` value.

### Persistence format design gate

Persistence is a prerequisite for stateful component features. During the early
GameTest/foundation stage, a small technical spike must verify the exact Minecraft
1.21.1 behavior before any persistence-dependent implementation stage begins. B2
accepts an additive versioned schema using validated
`NbtHelper.fromBlockState` and `NbtHelper.toBlockState`. The first PR is limited to
the codec/registry spike, fixtures, malformed input, and round-trip tests and must
not change gameplay behavior. The B2 Decision is answered; its readiness is
`Pending feasibility spike`, not `Blocked by design decision`.

The Decision Record must fix these rules:

- deserialize only blocks validated as members of Slabee's known `BlockTriple`
  mapping and the expected horizontal/vertical shape family;
- persist only properties classified as component-owned for that mapped block;
- never treat serialized slot-derived `TYPE` or `FACING` as authoritative;
- never treat environment-derived values such as `SNOWY` or leaf `DISTANCE` as
  authoritative;
- persist the container-owned DoubleVertical `AXIS` explicitly under the
  container rule, not as a component property;
- continue reading the legacy block-ID NBT layout;
- use the known block's default value when only a property is invalid;
- treat an invalid, removed, unknown, or unsupported block ID as a lost component,
  singleize a valid partner, and use air when neither component restores;
- rate-limit diagnostics without hiding the first actionable failure; and
- record the versioned schema, property filter, exact API, diagnostics, fallback,
  and round-trip fixtures before the full Stage 6 implementation.

The spike and Decision Record select and prove the format; they do not substitute
for Stage 6's complete persistence, synchronization, migration, and reload work.

The state-persistence PR must use an additive, versioned schema. Its reader must:

1. recognize the current unversioned layout;
2. map legacy IDs through the existing Slab/VerticalSlab registry mapping;
3. preserve slot identity and the X/Z axis;
4. synthesize missing or invalid component-owned properties from that block's
   default state;
5. ignore unknown additive fields without discarding known data;
6. treat an invalid/removed/unsupported block as a lost component, retaining a valid
   partner as a Single or using air when both are lost;
7. write the new format only after a successful read and world update.

### Runtime and migration sources of truth

The source of truth depends on the kind and age of the data:

- In a loaded beta.4 world, container-owned properties represented by the outer
  block state, currently DoubleVertical `AXIS`, are authoritative in `BlockState`.
- Component IDs and persisted component-owned properties are authoritative in the
  versioned block-entity NBT because the outer Double state cannot represent them.
- Slot-derived `TYPE` and `FACING`, render variants, light summaries, collision
  summaries, and environment-derived values are caches or derived values. They are
  rebuilt from the authoritative container and component data.
- For unversioned legacy NBT, a valid legacy block-entity `axis` is migration input
  and repairs/initializes the outer `AXIS` before component references are exposed.
  If it is missing or invalid, the existing outer `AXIS` is used; if that is also
  unavailable, X is the documented safe fallback.
- For versioned beta.4 data, a duplicated block-entity axis never overrides the
  outer block state. A mismatch is diagnosed, and the duplicate is rewritten from
  the block state on the next successful save.
- A legacy per-slot horizontal `facing` value is read for compatibility but cannot
  override the canonical top/bottom slot mapping. Valid future component-owned
  orientation properties are migrated separately under the accepted filtered
  BlockState serialization schema.

Migration fixtures must cover both container types, both axes, every slot,
missing keys, invalid IDs, properties added in beta.4, save/reload after
singleization, and client synchronization. No migration may depend on loading or
cherry-picking the Windows backup branch.

## One-cell constraints and approximation policy

Minecraft APIs frequently select one `BlockState`, collision shape, fluid state,
loot context, scheduled tick target, or falling entity per position. When a
vanilla callback cannot distinguish components, the implementation should first
recover a target from hit position, entity position, contacting-face projection,
or the event source. If no stable target exists, it must use a documented,
deterministic approximation.

An approximation is acceptable only when it:

- preserves the untouched component whenever possible;
- is symmetric under swapping positive and negative components;
- behaves consistently across X and Z axes;
- does not require continuous ticking;
- is covered by automated tests and a player-visible manual test; and
- is recorded in the beta.4 checklist.

## Gate traceability

The roadmap's [Named gate traceability](../roadmap/beta4.md#named-gate-traceability)
table is authoritative for gate-to-work ownership. No gate creates a new PR or
feature scope:

| Retained gate | Existing work unit | Required evidence |
| --- | --- | --- |
| B2 persistence feasibility | Stage 1 B2 persistence spike PR | Reviewed schema/API/property-filter and legacy/malformed/round-trip fixture results. |
| B4 baseline pilot | Stage 1 B4 baseline PR and pilot | Versioned procedure, fixed fixture/configuration, raw/JFR data, comparison report, and pilot-versioned thresholds. |
| A1 bubble feasibility | Stage 9 bubble feasibility spike task | Approach/failure/performance/constraint report and primary acceptance evidence; the conditional fallback uses this same report plus Japanese owner reporting and explicit approval. |
| Huge-mushroom asset/UV feasibility | Stage 11 steps 1–2 | Existing-asset/UV sufficiency record or exact owner-facing gap report; no separate PR. |
| B3 formal leaf-loot evidence | Stage 12 formal task, rerun unchanged by Stage 14 before release | Deterministic exact assertions and paired-difference report for every fixed seed, condition, and target item. |
| B1 falling-payload feasibility | Stage 13 opening spike | Reviewed conservation, persistence, landing/recombination, hardening, rollback, and chunk-boundary artifact. |

Soul Fire and combustion are Stage 10 work, ice melt is Stage 9 work, and the
additional Planned parity rows are Stage 14 work. They have no independent
feasibility gate. A Stage exit condition is evidence from that Stage, not another
gate under a different name.

## Decision registry

The canonical and non-duplicated list of owner decisions, approved technical
Decisions, and remaining spike gates is
[`beta4-owner-guide.ja.md`](../roadmap/beta4-owner-guide.ja.md). This English design
document deliberately does not maintain a second Open Questions list. Stage 1 A1
through A5 and B1 through B4 have recorded answers. B1/B2 implementation readiness,
A1 bubble feasibility, the Stage 11 huge-mushroom asset/UV task, and B4 final
thresholds remain gated by the work units in the traceability table; a failed gate
must return to owner review rather than silently changing the approved behavior or
transport. B3 formal evidence is produced by the same task at Stage 12 completion
and Stage 14 pre-release, not by a second feasibility gate.

Roadmap readiness terms have distinct meanings: `Blocked by design decision` is
reserved for an unanswered Owner Decision or Technical Decision. `Pending
feasibility spike` means the Decision is approved but a named independent spike
gates production work. `Pending dependency` means the Decision is approved but a
preceding PR, common foundation, or another Stage is incomplete. `Not started`
means all Decisions and mandatory dependencies are complete and work can begin
immediately. `In progress` means work is active. `Complete` requires implementation
and all mandatory validation. `Deferred` means outside beta.4. Ordinary Stage/PR
ordering is a dependency, never a design-decision blocker.
