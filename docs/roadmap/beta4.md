# Slabee 1.0.0-beta.4 Roadmap

Status: scope definition and implementation checklist

Target platform: Minecraft 1.21.1 with Fabric

Code baseline reviewed: `0db615b292e3950158db8cd3aec864c386d81ddc`

## Release policy

Beta.4 is a large core-behavior update after a long release interval. It is not a
small patch. The release will be published only after the required behavior groups
in this document are complete, integrated, and validated together.

Implementation must still be incremental. The numbered list defines 14 roadmap
stages, not exactly 14 pull requests. Each stage uses one or more small branches
and pull requests as needed to remain reviewable. Every feature PR must include
focused automated tests and a short manual test plan. No feature branch should
become a second long-lived integration branch.

The normative technical component model is defined in
[`virtual-components.md`](../design/virtual-components.md). Issue #6 is a useful
historical memo, not the beta.4 specification. The Windows backup branch is design
material only: commit `9c8b932` may inform a clean implementation, while the WIP
commit `705cbdf` must not be cherry-picked.

Owner-approved product decisions and the canonical unresolved-decision registry
are maintained in [`beta4-owner-guide.ja.md`](beta4-owner-guide.ja.md). An unresolved
item is not decided by more specific wording elsewhere in this roadmap.

## Scope rules

- Preserve the illusion that a Double contains two independent half-blocks.
- Apply behavior to the selected or contacted component whenever Minecraft exposes
  enough information to identify it.
- Record Minecraft constraints and adopted approximations in this checklist before
  merging the affected feature.
- Prefer direct events, neighbor updates, scheduled ticks, random ticks at vanilla
  frequency, and existing entity/item callbacks.
- Do not add unconditional continuous ticking to all Double block entities.
- Preserve old worlds and the existing unversioned Double NBT layout through an
  explicit migration path.
- Use the scope classification below, not table membership or row count, to decide
  whether a behavior is release-blocking.
- Audit additional mapped-block obligations before release. A beta.4-required or
  beta.4-planned item must become `Complete`; a deferred item receives a documented
  future-work note; an unresolved item requires an owner answer in the owner guide.

## Status definitions

| Status | Meaning |
| --- | --- |
| `Blocked by design decision` | An Owner Decision or Technical Decision is unanswered. The row must name the unanswered Decision. |
| `Pending feasibility spike` | The Decision is approved, but an independent spike must succeed before production implementation begins. The row must name the spike. |
| `Pending dependency` | The Decision is approved, but a preceding PR, common foundation, or another Stage must complete first. The row or section must name the dependency. |
| `Not started` | The Decisions and every mandatory dependency are complete, so work can begin now, but implementation has not started. |
| `In progress` | Implementation or its mandatory validation is actively being worked on. |
| `Complete` | Implementation and every mandatory validation are complete. |
| `Deferred` | The feature is outside beta.4 scope. |

No current row is marked `Complete` because the repository has no GameTest suite
yet. Existing partial code and verification gaps remain documented in `Current
implementation`; they do not define readiness status.
At this Stage 1 documentation baseline, no checklist row is `Blocked by design
decision`, `Not started`, `In progress`, or `Complete`: named independent gates use
`Pending feasibility spike`, out-of-scope rows use `Deferred`, and the remaining
rows use `Pending dependency` until their common foundation and ordered prerequisite
Stages complete.
For huge mushrooms, passing the asset/UV feasibility gate alone does not advance
the row to `Complete`; implementation and the full automated,
manual, persistence, synchronization, and rendering checks are still required.

## Beta.4 scope classification

The initial reviewed inventory grouped behavior into 31 core rows and 10 additional
parity rows. Leaf loot is now separated from leaf lifecycle, and the additional
pillar/hay and static/particle rows are split where their approved scopes differ,
so the current checklist has 32 core rows and 12 additional rows.
Both counts are snapshots, not permanent targets, measures of completeness, or
rules that every row is release-blocking.

### Beta.4 required

- Component foundation, removal/singleization, replacement, state persistence,
  legacy migration, GameTests, and performance baseline.
- Grass survival/dirt conversion/spread/bone meal, snow, and dirt paths.
- Sponge absorption/wet conversion/drying and coral death.
- Ignition/flammability, copper transitions, fixed-orientation log stripping,
  leaf lifecycle **and leaf loot parity**, concrete hardening, ice slipperiness,
  magma damage, soul-sand contact, Soul Speed, mud, mycelium/podzol, nylium, and
  moss/rooted-dirt behavior. Leaf loot readiness is blocked only by its technical
  implementation PR and statistical evidence; its beta.4 scope and thinning
  algorithm are not undecided.
- Mangrove-root waterlogging and regression verification for static source settings,
  including luminance, transparency/occlusion, slipperiness, strength, sounds,
  tool requirements, and relevant tags.
- Stability-only Sculk verification: placement, breaking, save/reload, and mob death
  nearby must not crash, lose block entities, duplicate experience, or corrupt the
  world. Full catalyst propagation and bloom are not required.

### Planned for beta.4

- Simultaneous combustion, component falling, ice-melt fluid results, and split
  bubble behavior. Their product-level behavior is owner-approved. Falling remains
  gated by the B1 payload spike, and bubble work remains gated by its rendering and
  force feasibility spike. The per-entity AABB primary mode is tested first;
  fallback is not adopted unless constraint evidence is reported in Japanese and
  the owner explicitly approves separate validation of the entity-independent
  fixed-exposed-area mode.
- Soul Fire, using the owner-approved contact and priority rules.
- Huge mushroom face-state parity for red mushroom blocks, brown mushroom blocks,
  and mushroom stems. An asset/UV feasibility review must finish before code or
  model changes begin.
- Hoe/water-bottle/mud-to-clay transformations, hay fall-damage reduction, special
  non-leaf loot/experience/guarded interactions, and decorative source particles.
  These rows have no independent feasibility gate; their existing work unit is the
  first bullet of Stage 14 after its ordered common dependencies complete.

These items are intended for beta.4. Only the named gates in the traceability table
below apply; the list itself does not create another design or asset gate. Work must
not bypass a retained spike/review, silently change an approved fallback, or create
placeholder assets.

### Out of beta.4 and future candidates

- Log-family `AXIS` and selectable grain direction. Existing Slabee log Slabs keep
  their intentional fixed texture/model direction. This does not remove axe
  stripping from beta.4.
- Full Sculk Catalyst propagation, charge, bloom, or component conversion.
- Component-only piston movement. Beta.4 retains and verifies cell-level behavior;
  unsafe movement may be explicitly rejected without implementing half movement.
- Non-log pillar `AXIS` parity and wool vibration occlusion.
- Pumpkin carving and the Carved Pumpkin Slab families. Ordinary Pumpkin Slabs
  remain. Carving is only a post-gate stretch candidate after every Required and
  normal Planned item is complete, and requires a new owner approval before work.
  Here `post-gate` refers to the existing Stage 14 release completion; it is not a
  separate beta.4 gate or work unit.
- The platform, shape, fluid, and architecture exclusions listed later in this
  document.

The canonical owner decisions and approved technical choices are maintained in
[`beta4-owner-guide.ja.md`](beta4-owner-guide.ja.md).

### Named gate traceability

No gate below creates a new PR or feature scope. Each is completed by an existing
roadmap work unit and leaves the listed reviewable evidence. A Stage exit condition
is evidence produced by that Stage, not a second gate under another name.

| Retained gate | Existing work unit | Deliverable / passing evidence |
| --- | --- | --- |
| B2 persistence feasibility | Stage 1 B2 persistence spike PR | Reviewed spike commit/PR plus versioned-schema, validated `NbtHelper`, registry/property-filter, legacy/malformed fixture, fallback, and round-trip results. |
| B4 baseline pilot | Stage 1 B4 baseline PR and pilot | Versioned measurement procedure, fixed-environment fixture/configuration, raw/JFR output, comparison report, and pilot-versioned thresholds. |
| A1 bubble feasibility | Stage 9 bubble feasibility spike task | Spike report containing tested approaches, failed conditions, performance values, remaining constraints, and primary acceptance evidence; a fallback branch also requires the existing Japanese owner report and explicit approval. |
| Huge-mushroom asset/UV feasibility | Stage 11 steps 1–2 | Asset/UV review record showing that existing vanilla textures/current extraction suffice, or an exact owner-facing gap report. This is a Stage 11 task, not a separate PR. |
| B3 formal leaf-loot evidence | Stage 12 formal statistical task at Stage 12 exit, rerun unchanged by Stage 14 before release | Exact-assert report plus paired-difference statistical report for every fixed seed, probabilistic condition, and target item. The second run reuses the same task and constants; it is not another gate implementation. |
| B1 falling-payload feasibility | Stage 13 opening spike | Reviewed spike artifact covering in-flight save/reload, partner retention, landing/recombination, no loss/duplication, Concrete Powder hardening, spawn rollback, and chunk boundaries. |

Soul Fire and combustion use Stage 10, ice-melt behavior uses Stage 9, and the
additional Planned rows named above use Stage 14. They retain their existing scope
and order but have no separate feasibility gate.

## Current implementation baseline

The following observations describe the reviewed baseline, not the target design:

- `ModBlockMap` contains 265 vanilla-to-horizontal-to-vertical mappings.
- Doubles currently persist component block IDs and a block-entity copy of the
  vertical axis, while DoubleVertical also has an outer block-state `AXIS`. Most
  component states are rebuilt from block defaults, and arbitrary component
  `BlockState` properties are not preserved. Beta.4 makes the outer `AXIS`
  authoritative after legacy migration.
- Player-driven removal can singleize a Double, and dirt-path replacement can
  update selected components in some shapes. There is no general component API.
- Grass and snowy single classes exist. Grass random-tick logic is incomplete, the
  registrations do not currently opt into random ticks, failure replaces the
  entire position with full dirt, spread only targets vanilla full dirt, and bone
  meal `grow` methods are empty.
- Dirt-path creation and reversion exist for single slabs. The current Slabee dirt
  tags include Rooted Dirt, so the generic shovel mixin also maps its single shapes
  to Dirt Path. Horizontal Doubles only expose/convert the positive upper
  component; vertical Doubles have per-side code.
- Ice slipperiness, magma damage, soul-sand movement, soul speed data, and bubble
  creation have partial special handling. Bubble columns and their `DRAG` state
  are still cell-wide, mixed vertical soul-sand/magma chooses one whole-column
  result, and the soul-speed data references the wrong DoubleVertical block ID in
  several conditions.
- Sponge, falling blocks, concrete hardening, vertical copper transitions, log
  stripping, leaf lifecycle, and coral death use ordinary Slab/VerticalSlab classes
  or settings and do not yet reproduce their source behavior.
- Current log Slabs intentionally use fixed grain direction; generic log `AXIS`
  parity is not a beta.4 target.
- The mapping contains ordinary `SCULK` Slabs, but no `SCULK_CATALYST` mapping or
  Slabee catalyst Block Entity. Beta.4 therefore verifies Sculk safety around a
  vanilla catalyst instead of implementing catalyst propagation/bloom parity.
- Many material settings copy sound, hardness, map color, slipperiness, velocity,
  luminance, or burnability. Copied settings alone do not establish component-level
  vanilla parity.
- The Double block entity currently has a continuous server ticker used for
  initialization and a small conversion queue. Beta.4 should replace new delayed
  behavior with scheduled/random/event-driven work and remove continuous ticking
  when no longer necessary.
- Both outer Double registrations use default `NORMAL` piston behavior and create
  Block Entities; Minecraft 1.21.1's normal piston movement path rejects states
  with Block Entities. No Slabee-wide piston override was found. This is code
  evidence, not a substitute for auditing every Single mapping and exercising
  Single, DoubleSlab, and DoubleVerticalSlab behavior in GameTests.

## Current mapped-block behavior inventory

This inventory groups all current `ModBlockMap` entries by parity obligation.
Color and wood-species variants are grouped because their gameplay rule is shared.
The checklist below expands every behavior that requires beta.4 work.

| Mapped vanilla family | Included targets | Source behavior that must be preserved or explicitly dispositioned |
| --- | --- | --- |
| Planks and bamboo mosaic | All overworld plank species, bamboo, crimson, warped | Fire ignition/spread for flammable woods; crimson and warped immunity; instrument, sound, tool, and drops. |
| Logs, wood, stems, hyphae, bamboo blocks, and stripped forms | All mapped species and both barked/stripped families | Intentional fixed grain direction; component-local axe stripping where applicable; species-specific flammability, tool, and drops. Full source `AXIS` parity is future work. |
| Vanilla slab-backed stone families | Stone, cobblestone, masonry, deepslate, tuff, brick, mud brick, sandstone, prismarine, nether brick, blackstone, end stone, purpur, quartz | Existing vanilla horizontal slab behavior where reused; vertical and heterogeneous-Double parity for settings, drops, tool rules, sounds, and relevant axes. |
| Custom static stone-like blocks | Cracked blocks, reinforced deepslate, netherrack, basalt variants, end stone, quartz brick, amethyst, calcite, dripstone, obsidian | Hardness/blast resistance, required tools, drops, note-block instrument, piston reaction, spawning/portal/support implications, and source-specific sounds or resonance. The current reinforced-deepslate Slabs are extremely resistant but not configured as unbreakable. |
| Gilded blackstone | Gilded blackstone | Special drops/fortune behavior and piglin-guarded interaction in addition to stone settings. |
| Copper | Four cut-copper oxidation stages and four waxed stages | Random oxidation, waxing, axe scraping/deoxidation, state-preserving component replacement, and correct drops. |
| Wool | All 16 colors | Flammability, vibration occlusion/dampening, note-block instrument, shears/tool behavior, and drops. |
| Terracotta and concrete | Base terracotta plus 16 colors; 16 concrete colors | Primarily settings/tool/drop parity; concrete is the hardened result for concrete powder. |
| Concrete powder | All 16 colors | Gravity, falling-entity state, water contact, and hardening to matching concrete. |
| Glass | Clear, tinted, and all 16 stained colors | Transparency/culling, tint, skylight/light blocking, no-drop or silk-touch behavior, beacon interaction where applicable, and connected rendering already implemented by Slabee. |
| Spreadable soils | Grass block, podzol, mycelium | Survival/decay, spread, snow state, color/rendering, and grass bone meal behavior. |
| Dirt family | Dirt, coarse dirt, rooted dirt, dirt path | Shovel path creation, path support/reversion, hoe and bone-meal transformations where applicable, and component-local replacement. |
| Mud family | Mud, packed mud, mud bricks, muddy mangrove roots, clay | Reduced top/collision height where applicable, sinking/contact behavior, mud-to-clay conversion conditions, and normal static behavior for packed forms. |
| Gravity blocks | Sand, red sand, gravel | Falling, support, falling-entity landing, item/drop behavior, and per-component removal/singleization. |
| Ice | Ice, packed ice, blue ice | Variant slipperiness; normal ice light-based melting and water result; tool/drop behavior. |
| Moss and rooted dirt | Moss block and rooted dirt | Bone-meal growth/conversion behavior, exposed-face requirements, and component-local changes. |
| Magma | Magma block | Hot-floor damage, crouch/frost-walker immunity, bubble-column drag, light/tool behavior. |
| Nether ground | Crimson and warped nylium, soul sand, soul soil, netherrack | Nylium decay and bone meal; soul-sand slowdown/collision and bubbles; soul speed; valid soul-fire base behavior. |
| Luminous and particle blocks | Glowstone and crying obsidian | Luminance/drop parity and crying-obsidian ambient particles. |
| Roots and mushroom blocks | Mangrove roots, muddy roots, mushroom stem, red/brown mushroom blocks | Waterlogging audit for mangrove roots; vanilla 1.21.1 six-direction mushroom face states, outer/inner/stem textures, and state persistence. |
| Leaves | Ten mapped leaf variants | `DISTANCE`, `PERSISTENT`, waterlogging where supported, scheduled distance updates, random decay, drops, flammability, particles, and species differences. |
| Wart and dried kelp blocks | Nether wart block, warped wart block, dried kelp block | Flammability distinctions, sounds, tools, and drops. |
| Coral | Five living and five dead coral blocks | Water survival checks, scheduled death, matching dead variant, and cell-fluid constraints. |
| Sponge | Sponge and wet sponge | Bounded water absorption, wet conversion, Nether drying, sounds/particles, and component-local replacement. |
| Crops stored as blocks | Melon, pumpkin, hay bale | Special drops, pumpkin carving, hay fall-damage reduction/axis, flammability, tools, and sounds. |
| Honeycomb | Honeycomb block | Settings, sound, tool, and flammability audit; it is not itself a copper-wax operation target. |
| Sculk | Sculk | Beta.4 stability around a vanilla catalyst, plus experience drops, tool requirements, sounds, and corruption/duplication checks. Full catalyst spread/charge/bloom is future work. |
| Bedrock | Bedrock | Unbreakability/blast/piston/spawn behavior and no-drop rules; do not broaden survival availability. Reinforced deepslate remains in the resistant stone-like audit, not this category. |
| Petrified oak slab | Petrified oak slab | Stone-like nonflammable behavior despite its wood appearance. |

Static appearance alone is not a completion criterion. For every family, the
regression pass must compare collision/outline shape, light and opacity, hardness,
blast resistance, required tool, loot, sound, map color, note-block instrument,
flammability, piston reaction, entity spawning, and relevant block tags with the
mapped vanilla source.

## Checklist notation

The six shape columns report the **current baseline**, not the desired end state.
`Vanilla` means the horizontal mapping directly reuses a vanilla slab implementation;
it still requires regression coverage. `None` means no source-equivalent behavior
was found. `Partial` records known incomplete custom handling. `Settings` means
only copied block settings/tags appear relevant. `N/A covered` means that component
has no exposed surface required by the behavior while the Double is intact.

For DoubleVertical, every row must be tested on both X and Z axes. For every
Double row, tests must run with the target material in the stated slot and with
the partner identities swapped.

## Core behavior tracking checklist

Rows in this table are tracking units. Their release role is defined by
**Beta.4 scope classification**, not by appearing in this section.

### Grass, snow, and paths

| Feature | Target vanilla blocks | Horizontal Slab | Vertical Slab | DoubleSlab Positive | DoubleSlab Negative | DoubleVertical Positive | DoubleVertical Negative | Current implementation | Target behavior | Minecraft constraint | Planned approximation | Required automated tests | Required manual tests | Status |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Grass survival and conversion to dirt | Grass block | Partial | Partial | None | None | None | None | Single classes copy vanilla-like light/fluid checks, but random ticks are not enabled and failure replaces the cell with full dirt. | Each exposed grass component evaluates vanilla survival; a failed component becomes matching dirt without changing its partner, shape, axis, or valid state. A buried lower component does not survive as grass. | Light and opacity APIs are cell-oriented; the lower horizontal component is covered by its partner. | Use component top-face exposure plus vanilla light/fluid checks. Treat the covered negative horizontal component as unable to survive. | Survival under air, snow layer, opaque cover, full water, and partial Slabee cover; every shape/slot/axis; partner preservation; save/reload. | Compare timing and visible conversion with vanilla grass; inspect snow and water edge cases. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Grass spread to dirt components | Grass block; dirt | Partial | Partial | None | None | None | None | Single code only targets vanilla full dirt and can place the wrong shape. Backup commit `9c8b932` contains ideas but is not production code. | Eligible grass spreads to eligible exposed dirt components. Only the target component becomes grass; slot, orientation, partner, and valid state are retained. | Vanilla random tick samples block positions, not components; two eligible targets may share one cell. | Expand each sampled position into eligible component references, select deterministically with seeded randomness, and keep vanilla attempt count/frequency. | Seeded spread/no-spread tests for all source/target shape pairs, both Double slots and axes, light/water blockers, no duplicate attempts, save/reload. | Observe natural spread density and symmetry in a controlled mixed-slab plot. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Grass bone meal | Grass block | Empty `grow` | Empty `grow` | None | None | None | None | `Fertilizable` is implemented on grass singles, but both `grow` methods are empty. | Bone meal applied to an exposed grass component runs vanilla-like vegetation generation without modifying the partner. | Plants occupy whole cells and cannot be limited to a half-cell; one destination cell cannot host results from two components. | Hit position selects the source component. Use vanilla vegetation placement in whole cells above exposed top regions and de-duplicate destination positions. | Success/failure and consumption; top/bottom and four vertical facings; both Double slots; blocked space; deterministic seeded vegetation set. | Compare density, sound/particles, and item use with vanilla grass in open and cramped areas. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Snow support, `SNOWY` state, and appearance | Grass block, podzol, mycelium; snow and snow layers | Partial | Partial | Partial | Partial render/state assumptions | Partial | Partial | Singles recalculate `SNOWY` from the block above; Double rendering has special handling, and snow placement has soul-sand/mud/path shape hooks. State is not generally persisted per component. | Recalculate `SNOWY` from snow contact with each exposed top region. Horizontal Double positive may be snowy; its covered negative component may not. Both vertical halves may be snowy when their top regions contact snow. | Snow occupies a cell/layer rather than a component region; vanilla has one snow state above the pair. | Treat snow presence as cell-wide, then project contact onto exposed component top faces. Store no historical `SNOWY` value. | Placement/removal neighbor updates; snow layer counts; grass/podzol/mycelium; path, mud, soul-sand support; all slots/axes; chunk reload. | Inspect tint/model seams and collision with one and multiple snow layers in each orientation. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Dirt-path creation, including Rooted Dirt | Grass, dirt, coarse dirt, podzol, mycelium, and rooted dirt where the Minecraft 1.21.1 shovel mapping permits | Implemented; verify | Implemented; verify | Partial | Deliberately rejected while covered | Partial | Partial | Singles preserve `TYPE`/`FACING`, and the current dirt tags include Rooted Dirt. Horizontal Double only changes the exposed positive component; vertical Double can select sides. **Verified vanilla fact:** `ShovelItem.PATH_STATES` maps Rooted Dirt directly to Dirt Path when the clicked side is not down and the entire cell above is air. It plays the shovel-flatten sound, emits `BLOCK_CHANGE`, and damages the tool by one for a player; it drops no Hanging Roots. The separate hoe action changes Rooted Dirt to Dirt and drops one Hanging Roots item. | A shovel changes only an eligible target component to the matching Dirt Path shape and leaves its partner unchanged. Covered horizontal negative dirt is not path-eligible. Rooted Dirt follows the same shovel result and feedback as vanilla, including no Hanging Roots drop. Hoe behavior remains the separate Planned soil-transformation item and is not decided by this row. | Vanilla checks one whole above cell and returns one whole replacement state; it cannot express a top region blocked over only one vertical half. | Route item use through `ComponentRef`. A normal full block above blocks all components; a Slabee above blocks only the component whose top projection it overlaps. Horizontal negative components covered by their partner are rejected. Use hit projection for explicit Vertical/Double selection and the documented fallback policy only when no component is selected. Emit flatten sound, `BLOCK_CHANGE`, and one durability use once for a successful transaction, with no shovel-generated Hanging Roots. | Every listed source including Rooted Dirt; successful Rooted Dirt result is Dirt Path with zero Hanging Roots; hoe behavior kept in its separate test/scope; click from down; full-air/full-block/partial-Slabee space above; horizontal top/bottom; all vertical facings; both Double slots and axes; target and partner identities; sound, `BLOCK_CHANGE`, durability, and item result exactly once. | Shovel Rooted Dirt beside vanilla controls with clear, fully blocked, and half-blocked space above; inspect Dirt Path result, absence of Hanging Roots, feedback, tool wear, precise hit positions, and unchanged mixed partners. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Dirt-path survival and reversion | Dirt path | Implemented; verify | Implemented; verify | Partial | N/A covered | Partial | Partial | Singles schedule conversion when blocked. Doubles use an incomplete per-entity conversion queue; horizontal only supports positive. | Every exposed path component checks vanilla placement support and becomes matching dirt independently when obstructed. | Scheduled ticks target the container position, not a stored slot. | Schedule a container tick and recompute all path components at execution; commit all due replacements atomically. Do not retain a permanent block-entity ticker. | Obstruction added/removed before tick; both vertical sides due together; partner preservation; unloaded/reloaded scheduled tick; sound/event count. | Place gates, solids, slabs, and vertical slabs above each orientation and inspect height/collision. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |

### Sponge and coral

| Feature | Target vanilla blocks | Horizontal Slab | Vertical Slab | DoubleSlab Positive | DoubleSlab Negative | DoubleVertical Positive | DoubleVertical Negative | Current implementation | Target behavior | Minecraft constraint | Planned approximation | Required automated tests | Required manual tests | Status |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Sponge absorption and wet conversion | Sponge; wet sponge | None | None | None | None | None | None | Sponge variants are ordinary Slab/VerticalSlab blocks with copied settings. | A dry sponge component with exposed water contact performs a vanilla-bounded search and becomes wet independently. Both components are evaluated symmetrically from the unchanged pre-transaction snapshot; if both qualify, both become wet. | Fluid removal is cell-wide, and the two reachable-water sets may overlap. Mutating one slot before testing the other would make the result order-dependent. | Compute both qualification results and bounded reachable-water sets from one snapshot. Remove the union so each water position is mutated once, then wet every qualifying component in one transaction. Overlap does not prevent either qualifying sponge from becoming wet. | Vanilla radius/count limits per component; overlapping and disjoint water sets; both/one/neither qualifies; flowing/source/waterlogged targets; slot swap/axes; one transaction notification; per-component wet effects once; partner/save preservation. | Compare absorption reach, sound/particles, ocean edge cases, and nearby waterlogged blocks with vanilla; repeat after swapping slots to prove symmetry. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Wet sponge drying | Wet sponge; sponge | None | None | None | None | None | None | No source-equivalent placement/update behavior is present. | In an ultrawarm dimension, only the placed or exposed wet-sponge component dries with vanilla sound/particles; its partner is preserved. Non-ultrawarm drying behavior remains vanilla-equivalent. | Dimension checks and particles are cell-position based. | Select the newly placed/replaced component; emit one cell event per transaction and replace only that component. | All six shape/slot cases in ultrawarm and normal dimensions; wet+wet and wet+other pairs; reload. | Verify Nether feedback and that the other half neither dries nor changes visually. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Living coral survival and death | Five living coral blocks and matching dead variants | None | None | None | None | None | None | Living and dead variants are ordinary shape classes; no wet check or scheduled death exists. | Each living coral component remains alive when its own exposed geometry has valid water contact and otherwise schedules conversion to its matching dead component. | A Double cannot be waterlogged per side, and adjacent water is represented for a whole cell. Scheduled ticks do not identify a slot. | Project cell water contact onto exposed faces; on the scheduled tick recompute both components and replace only dry living coral. | Waterlogged singles; adjacent water on every face; partner shielding; water removed/restored before tick; five colors; slots/axes; reload. | Observe delayed death timing and texture transition in mixed live/dead Doubles. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |

### Fire

| Feature | Target vanilla blocks | Horizontal Slab | Vertical Slab | DoubleSlab Positive | DoubleSlab Negative | DoubleVertical Positive | DoubleVertical Negative | Current implementation | Target behavior | Minecraft constraint | Planned approximation | Required automated tests | Required manual tests | Status |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Ignition and flammability classification | Planks, logs/wood, leaves, wool, dried kelp, hay, and every other mapped flammable; crimson/warped and petrified exceptions | Vanilla or Settings; verify | Settings; verify | None | None | None | None | Many registrations use burnable settings; there is no general source-block/component flammability lookup. | Each component uses its mapped vanilla source's ignition and burn chance. Nonflammable partners and fireproof wood families remain unaffected. | Vanilla fire APIs query one block at a cell face and do not expose a component hit. | Resolve components through fire-to-cell face projection and delegate rates to a central mapped-source lookup. Evaluate every contacted component independently. | Flammability matrix against source blocks; every face/slot/axis; mixed flammable/nonflammable and crimson/overworld pairs; lava ignition. | Ignite representative wood, leaf, wool, hay, dried kelp, crimson, and petrified pairs from every face. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Burning, fire spread, and component consumption | Same as ignition targets | Vanilla or Settings; verify | Settings; verify | None | None | None | None | No component-local burn-out/singleization path exists. | Successful burn-out removes only that component and singleizes the partner. If one fire contacts both, each receives an independent random decision calculated from one pre-transaction snapshot; two successes resolve atomically. One success leaves the valid Single and creates no fire in the occupied cell. Only two successes may place normal Fire or Soul Fire when vanilla placement, survival, rain, and fluid conditions allow it; otherwise the cell becomes air. | Fire state and update order are cell-wide; no half-cell fire exists. | Derive order-independent slot results from one snapshot, commit once, and run the owner-approved Soul-priority selection only for a fully cleared cell. Never expose an intermediate Single to neighbor updates. | One/both/neither side burns; deterministic slot-order independence; normal/Soul priority; placement/rain/fluid rejection; fire spread through mixed grids; per-component loot/effects exactly once; transaction notifications once; chunk unload; no duplicate updates. | Long-running burn pens for representative rates; verify remaining shape, orientation, final fire type, and extinguishing against vanilla. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |

### Falling blocks and concrete powder

| Feature | Target vanilla blocks | Horizontal Slab | Vertical Slab | DoubleSlab Positive | DoubleSlab Negative | DoubleVertical Positive | DoubleVertical Negative | Current implementation | Target behavior | Minecraft constraint | Planned approximation | Required automated tests | Required manual tests | Status |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Sand, red sand, and gravel falling | Sand, red sand, gravel | None | None | None | None | None | None | These are ordinary shape classes and do not use falling-block behavior. | An unsupported gravity component falls alone. Upper horizontal gravity is supported by its lower partner; lower gravity checks below. Vertical gravity checks support under its own projected half. The partner singleizes in place. | `FallingBlockEntity` normally carries one block state and lands into one cell. Multiple falling halves may cross chunk/unload boundaries. | **B1 accepted:** extend vanilla `FallingBlockEntity` with a Slabee namespaced payload containing schema version, component BlockState, container kind, slot, and vertical axis. Do not introduce a custom entity or Fabric Data Attachment. Permit modded blocks only through explicit adapters. A standalone spike must pass before production falling work. | Spike: entity save/reload in flight; source-partner retention; landing as Single/recombination; spawn rollback; chunk boundaries; obstruction and void; exact no-loss/no-duplication. Later implementation: full support matrix, slots/axes, drops, rendering, sounds. | In the spike, observe render/orientation and reload/landing conservation. Repeat the complete wood/sand examples only in the later implementation PR. | `Pending feasibility spike` — Stage 13 opening B1 falling-payload spike |
| Concrete powder falling | All 16 concrete powders | None | None | None | None | None | None | Powder variants are ordinary shape classes. | Use the accepted component falling payload while retaining color, persisted geometry, and hardening identity. | Falling powder may harden before, during, or at landing through cell-wide water contact. | The B1 spike must prove crossed-water and landing hardening through the vanilla entity payload before production falling work begins. Only the carried component may convert. | Spike representative colors: water crossed in flight, water at landing, mixed partner, payload save/reload, spawn rollback, chunk boundary, no loss/duplication. Full colors remain implementation tests. | Drop representative shapes beside/through water during the spike; defer the all-color matrix to the implementation PR. | `Pending feasibility spike` — Stage 13 opening B1 falling-payload spike |
| Concrete powder hardening | All 16 powders to matching concrete | None | None | None | None | None | None | No shape-aware hardening exists. **Verified vanilla fact:** Minecraft 1.21.1 hardens powder when its current `FluidState` is water-tagged or qualifying water is found on a non-`DOWN` side. Water only below dry powder is excluded. A water-bearing neighbor is also rejected when its face toward the powder is a solid full square. Landing performs the hardening check through the falling path. | A powder component hardens to matching concrete only under those vanilla conditions, without changing its partner or slot. Below-only water does not harden stationary powder; valid upper/side or current-cell water does. Falling into water is handled by the falling/landing pipeline. | Fluid and neighbor states are cell-wide, while exposure and solid-face overlap can differ per component. A Double cannot contain half-cell water. | Project only non-`DOWN` neighboring contacts onto the component and retain vanilla's neighbor-side `isSideSolidFullSquare` exclusion. Use the current-cell water path for waterlogged singles. Use the selected falling payload's landing check for water entered during descent or at landing. Replace only the qualifying powder component. | For horizontal and vertical singles and every Double slot/axis: water below only (no in-place hardening), each horizontal side, above, source/flowing water, waterlogged current cell, water behind a solid full-square face, exposed versus partner-shielded contact, and falling into/landing in water; color mapping, partner preservation, race handling, and save/reload. | Compare stationary below/side/top arrangements and waterlogged singles with vanilla controls; drop every shape into water, inspect landing result, and verify mixed partners never harden from excluded or shielded contact. | `Pending feasibility spike` — Stage 13 opening B1 falling-payload spike |

### Copper, logs, and leaves

| Feature | Target vanilla blocks | Horizontal Slab | Vertical Slab | DoubleSlab Positive | DoubleSlab Negative | DoubleVertical Positive | DoubleVertical Negative | Current implementation | Target behavior | Minecraft constraint | Planned approximation | Required automated tests | Required manual tests | Status |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Copper oxidation | Cut copper, exposed, weathered, oxidized Slabs | Vanilla | None | None | None | None | None | Horizontal singles reuse vanilla copper slabs; vertical variants are ordinary VerticalSlabs, and Doubles do not random-tick components. | Each unwaxed component advances using vanilla oxidation rules and source-stage mapping. Waxed partners never advance. | Vanilla oxidation samples blocks and age relationships by cell, while two copper ages may share one cell. | Run vanilla-frequency random ticks on eligible containers and evaluate each copper component from a snapshot; commit both results atomically. Count nearby copper per cell with a documented component-aware weighting. | Every stage/shape/slot/axis; waxed mix; nearby age influence; seeded simultaneous transitions; chunk reload and no idle BE tick. | Weather controlled copper arrays and compare rate/order with vanilla within an agreed tolerance. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Copper waxing | All four unwaxed cut-copper stages; honeycomb item | Vanilla; verify | None | None | None | None | None | Vanilla horizontal item mapping may work for singles; no component-local mapping exists. | Honeycomb waxes only the hit component, retaining oxidation stage, slot, partner, and valid state; sound, particles, advancement, and item consumption occur once. | Item use selects one cell state, and vanilla block maps do not contain Double containers. | Resolve hit component, map its source stage to the matching waxed shape, then use component replacement. | Every stage/shape/slot/axis; off-center and center hits; creative/survival item use; event/advancement once; partner unchanged. | Wax mixed-stage Doubles from each visible face and inspect particles/sound. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Copper axe scraping and deoxidation | Waxed and oxidized cut-copper stages; axe | Vanilla; verify | None | None | None | None | None | No vertical/Double component operation exists. | Axe first removes wax or steps oxidation back exactly as vanilla for only the hit component, with correct durability and feedback. | One cell can expose both copper components to the same clicked face. | Select by ray-hit projection; reject ambiguous internal faces; delegate transition mapping and effects to one component transaction. | All wax/stage transitions; every exposed face/slot/axis; durability, events, advancement, and partner preservation. | Scrape mixed wax/oxidation pairs and compare sequence and feedback with vanilla. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Log, wood, stem, hypha, and bamboo stripping | All mapped unstripped pillar families | None | None | None | None | None | None | Custom shape blocks copy settings but do not expose a component-local axe strip path. Their current fixed grain direction is intentional. | Strip only the hit component to its matching stripped variant while retaining slot and the existing fixed texture/model direction; invalid/non-strippable partners remain unchanged. Full log `AXIS` support is outside beta.4. | Item callbacks see the container, and corresponding stripped mappings must exist for every supported fixed-orientation shape. | Select by hit projection and replace through the vanilla strip mapping adapted to mapped shapes. Do not add or persist log `AXIS`. | Every wood/stem/hypha/bamboo family; all component slots and Double axes; already stripped/no mapping; durability/effects/loot exactly once; fixed direction before/after save/reload. | Strip mixed species and crimson/warped pairs from each exposed face; verify the intended fixed end/side textures. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Leaf distance and persistent state | All ten leaf variants | None | None | None | None | None | None | Leaf shapes are ordinary blocks without `DISTANCE` or `PERSISTENT`; Doubles persist only IDs. | Each leaf component updates vanilla-equivalent environment-derived distance and retains player-placed persistence independently. | Neighbor updates target cells, and a log/leaf component may overlap only half the supporting projection. | Persist `PERSISTENT`; reconstruct `DISTANCE` after placement/load through component contact graphs, treating full vanilla logs cell-wide and Slabee logs by projected contact. | Natural/player-placed leaves; distance 1-7; mixed components; X/Z paths; log removal/addition; save migration and reload. | Build asymmetric half-log canopies and inspect debug states and stability. | `Pending dependency` — B2 persistence and common component foundation |
| Leaf decay and component removal | All ten leaf variants | None | None | None | None | None | None | No random decay behavior is present. | A nonpersistent component at terminal distance decays independently and singleizes its partner. Whether it emits loot is delegated to the separate required leaf-loot contract; lifecycle eligibility does not define loot probabilities. | Random ticks are cell-based, and two leaf components may be independently eligible in one cell. | Evaluate decay eligibility for both components from one snapshot at vanilla frequency, then commit qualifying removals atomically through their natural-decay `MutationCause`. Do not embed a second loot algorithm in the lifecycle implementation. | One/both components decay; persistent mix; every slot/axis; atomic removal; exact natural-decay cause forwarded to leaf loot; no duplicate invocation; save/unload. | Observe a mixed canopy decay and verify timing, remaining geometry, particles, and exactly one leaf-loot invocation per removed component. | `Pending dependency` — B2 persistence and common component foundation |
| Leaf loot parity | All ten leaf variants and their Slabee horizontal/vertical items | Generated tables; incomplete parity | Generated tables; incomplete parity | Partial component break | Partial component break | Partial component break | Partial component break | Slabee has generated leaf-shape loot tables and component break handling, but source-species sapling/stick/apple conditions, Fortune scaling, natural-decay routing, and half-component expected values are not established. | **Scope: Required. B3 accepted.** Shears or Silk Touch drops exactly one matching leaf Slab item for the removed component. Ordinary breaking and natural decay evaluate the mapped vanilla species loot with its Fortune context once, then give every generated `ItemStack` an independent 50% keep/drop roll. Do not roll once for the whole result or change a retained stack's `count`. One component targets half the vanilla expected value and two matching components target one vanilla block in expectation; exact distribution equality is not required. Mixed Doubles evaluate only the removed component. Creative, Fire, and other no-loot causes drop nothing. | Vanilla loot tables evaluate one full block at one position. Per-stack thinning preserves expected values but intentionally changes the full distribution and must not duplicate the loot context. | Evaluate the mapped vanilla loot once per removed component, bypass thinning for exact Shears/Silk drops, and independently thin only ordinary `ItemStack` results with a fixed-seed-reproducible roll stream. Keep lifecycle eligibility separate. | Exact tool/no-loot tests plus short fixed-seed CI tests for per-stack independence, unchanged `count`, no whole-result roll, species, Fortune, natural break/decay, mixed isolation, simultaneous removal, and no duplicate evaluation. Run the paired-difference formal suite with the design document's three fixed seeds and `n = 1,000,000` at Stage 12 completion and rerun the same task in Stage 14 before release, not on every CI run. | Break and decay representative oak, non-apple, azalea, and mangrove leaf components with bare hand, shears, Silk Touch, and Fortune; review fixed-seed reproduction, the formal statistical report, and the distribution caveat. | `Pending dependency` — common component/loot foundation and ordered Stage 12 work |

### Ice, magma, soul sand, and bubbles

Bubble weighting has two distinct approved modes. The primary target computes each
entity's weights from the overlap of its AABB horizontal projection with each
component half and supports left/right visuals. The fallback never uses entity
geometry: it computes one cell force as
`sum(component exposed-top-area fraction × corresponding vanilla bubble force)`
and applies it to every entity in that cell. Fallback fractions are 1.0 for a
horizontal Slab, 0.5 for a VerticalSlab, 0.5 for each DoubleVertical component, and
1.0 only for the water-contacting upper component of a horizontal Double (covered
lower component 0). Non-sources contribute 0. Two same-type vertical components
sum to a full vanilla-block force; Soul/Magma composes 0.5 upward with 0.5 downward.
Entity positions and counts never change the fallback. Its force and visual
direction must agree; the spike fixes the zero/near-zero threshold, particles, and
velocity/cap application order.

| Feature | Target vanilla blocks | Horizontal Slab | Vertical Slab | DoubleSlab Positive | DoubleSlab Negative | DoubleVertical Positive | DoubleVertical Negative | Current implementation | Target behavior | Minecraft constraint | Planned approximation | Required automated tests | Required manual tests | Status |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Ice slipperiness | Ice, packed ice, blue ice | Settings; verify | Settings; verify | Partial | Incorrect influence possible | Partial | Partial | Singles copy slipperiness. Horizontal Double uses the maximum of both components, so a buried negative ice component can affect the top surface. Vertical selection uses entity position. | Slipperiness comes only from the component under the entity's support footprint. Horizontal Double normally exposes positive on top; vertical halves select by footprint. | Vanilla movement samples one block below an entity that can overlap both halves. | Sample support projection under the entity; for multi-half overlap choose the maximum only among actually contacted components, matching vanilla multi-block footing. | Entity positions over center/edges; all ice variants and partner swaps; boats and living/items where relevant; horizontal buried ice regression. | Walk, sprint, jump, and boat across striped ice/non-ice Slabee tracks and compare with vanilla. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Ice melting | Ice (not packed or blue ice) | None | None | None | None | None | None | No melt behavior exists. | Each exposed normal-ice component uses vanilla light/random-tick melt rules. If one melts, a waterloggable partner singleizes with `WATERLOGGED=true`; a non-waterloggable partner remains dry and no water is created. If the entire cell's ice melts, normal dimensions create source water and ultrawarm dimensions create air. | Vanilla melting may replace the whole cell with water; a partner can remain in that cell, and Double water history is not restored. | Apply the approved deterministic cell rule. Never push melt water into an adjacent cell. Packed and blue ice never enter this path. | Light threshold; normal/ultrawarm dimensions; source and neighbor fluid ticks; every slot/axis; partner waterloggable/nonwaterloggable; both components melting; packed/blue non-melt; reload. | Validate flow and partner state in controlled mixed-ice setups against the approved approximation. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Magma hot-floor damage | Magma block | Implemented; verify | Implemented; verify | Partial | N/A covered | Partial | Partial | Singles damage living entities. Horizontal Double checks positive; vertical Double selects a side from entity position. | Damage applies only while the entity contacts the magma component's exposed support surface, with vanilla exemptions. | Entity boxes can span both components and vanilla invokes one block callback. | Resolve contacted top-face regions and apply at most one vanilla-strength damage event per cell callback. | Living/nonliving; crouching, frost walker, immunity/bypass; edge/center positions; all slots/axes; no double damage. | Walk and crouch across mixed magma tracks; verify damage cadence and particles/sounds. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Magma bubble source | Magma block; water/bubble column | Partial | Partial | Partial | None | Partial whole-column | Partial whole-column | Scheduled creation exists, but bubble state and force are whole-cell; mixed vertical sources are not split. | Horizontal exposed magma produces vanilla downward force. A vertical magma half produces downward force and matching downward bubbles over its footprint. In the primary mode, a spanning entity uses its own AABB-overlap weights. | Bubble-column `DRAG` is one boolean for the whole water cell, and vanilla upward/downward velocity updates and caps are asymmetric. | First spike the primary per-entity AABB mode. If infeasible, use only the fixed exposed-top-area fallback defined above; never use an entity AABB or entity population to choose that fallback force. | Primary AABB weights; fallback weights for horizontal/vertical/Double variants; same-type sum; Soul/Magma 0.5 composition; non-source zero; narrow/wide and multiple entities; near-zero result; particles; velocity caps; client/server agreement; reload. | Observe primary and fallback separately. Verify every entity in one fallback cell receives the same motion and that visible direction matches it. | `Pending feasibility spike` — Stage 9 A1 bubble feasibility spike |
| Soul-sand slowdown and collision | Soul sand | Implemented; verify | Implemented; verify | Partial | N/A covered | Partial | Partial | Singles use reduced collision and velocity. Double movement selects positive for horizontal and entity side for vertical. | Slowdown and lowered support surface apply only to the contacted soul-sand component. | Wide entities can contact both halves; collision is combined for one block. | Compose collision from both components and derive velocity from actual support contacts, using the strongest contacted slowdown when spanning both. | Collision heights; entity positions/widths; all slots/axes; mixed path/soul pairs; boats/items/living. | Walk and jump across seams and compare speed/height with vanilla soul sand. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Soul Speed | Soul sand and soul soil; enchantment | Partial | Partial | Partial | Partial | Broken data candidate | Broken data candidate | Custom data includes Slabee sources, but DoubleVertical conditions repeatedly reference `slabee:double_slab_vertical_block` rather than the registered container ID. | Soul Speed activates only over a contacted soul-sand/soul-soil component and uses vanilla level effects and durability chance. | Enchantment effect conditions operate on cell block/NBT and may not express entity-position component selection reliably. | Prefer a tested component-aware predicate/injection with the smallest data override; fix IDs only as part of the feature PR. | Levels 1-3; soul sand/soil; every slot/axis and center; mixed partner; speed, particles, and boot durability. | Run timed tracks for singles and mixed Doubles and compare with vanilla. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Soul-sand bubble source | Soul sand; water/bubble column | Partial | Partial | Partial | None | Partial whole-column | Partial whole-column | Bubble creation recognizes singles and Doubles, but a vertical source affects the full column at full strength; soul wins over magma for a mixed pair. | Horizontal exposed soul sand produces vanilla upward force. A vertical soul half produces upward force and matching upward bubbles over its footprint. In the primary mode, mixed Soul/Magma uses each entity's AABB-overlap weights rather than a center tie-break. | Bubble-column state is cell-wide and cannot encode two force directions; multiple entities may require different primary results in the same cell. | Share the primary and fixed exposed-top-area fallback definitions above. The fallback applies one entity-independent result and matching visual direction to the cell. | Upward/downward caps; every slot/axis; primary AABB weights; fallback fixed weights and entity-count independence; source changes; near-zero composition; simultaneous entities; client/server agreement; reload. | Measure primary straddling motion and fallback cell-wide motion separately; reject force/visual disagreement. | `Pending feasibility spike` — Stage 9 A1 bubble feasibility spike |

### Mud and other required source behavior

| Feature | Target vanilla blocks | Horizontal Slab | Vertical Slab | DoubleSlab Positive | DoubleSlab Negative | DoubleVertical Positive | DoubleVertical Negative | Current implementation | Target behavior | Minecraft constraint | Planned approximation | Required automated tests | Required manual tests | Status |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Mud height, collision, and entity contact | Mud | Settings only | Settings only | None | None | None | None | Mud uses ordinary Slab/VerticalSlab geometry; snow placement has a mud special case. | Each mud component exposes vanilla-relative lowered collision/support geometry, composes correctly in Doubles, and affects entities only on its footprint. | Vanilla mud is a lowered full-cell surface; half shapes need a consistent scaled/offset interpretation. | Apply the source height reduction to the component's exposed top, union partner geometry, and use projected entity support. | Collision/outline/support for all orientations; path/soul/mud mixed pairs; snow placement; entities of several widths; suffocation/spawn checks. | Walk, jump, place snow, and inspect selection boxes over mixed mud pairs. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Mycelium and podzol spread/survival/snow | Mycelium, podzol, dirt | None | None | None | None | None | None | Both are ordinary shape blocks. Only grass subclasses own current spread/snow code. | Reproduce source-specific survival, spread eligibility, and snow appearance per exposed component; do not incorrectly grant grass-only bone meal behavior. | Random ticks and snowy state are cell-level callbacks. | Reuse the grass component scheduler and environment-state calculation with source-specific strategy rules. | Light/fluid/snow; dirt targets; grass/mycelium competition; all slots/axes; deterministic random ticks; reload. | Observe mixed biome/soil plots and confirm textures and spread boundaries. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Nylium decay and bone meal | Crimson nylium, warped nylium, netherrack | None | None | None | None | None | None | Nylium variants are ordinary shape blocks. | Each exposed nylium component decays to matching netherrack under vanilla invalid-cover conditions and grows the correct Nether vegetation when fertilized. | Vegetation and features occupy whole cells; dimension/registry feature lookup is cell-based. | Component-local decay; selected-component bone meal with cell-wide feature placement and destination de-duplication. | Both variants; cover/light conditions; all slots/axes; bone meal feature selection; blocked destinations; partner/reload. | Compare crimson/warped results in Nether test plots and mixed Doubles. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Moss and rooted-dirt bone meal | Moss block, rooted dirt | None | None | None | None | None | None | Both are ordinary shape blocks. | Selected moss uses vanilla-style spreading/vegetation; selected rooted dirt produces hanging roots only when its required exposed face and destination are valid. | Generated blocks occupy cells, and a destination can be shared by both components. | Use hit-selected source and exposed-face projection; perform vanilla whole-cell destination placement once. | Valid/blocked faces; every slot/axis; moss conversion radius; rooted dirt below target; consumption/events; partner preservation. | Compare feature density and hanging-root placement with vanilla. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |

## Additional mapped-block parity checklist

These rows come from the current 265 mappings and complement the core inventory
above. Their beta.4 disposition follows the scope classification and the owner
guide. Small static-setting corrections may
join the closest feature PR; any behavior needing substantial new machinery must
receive its own PR or an explicit scope decision.

The owner has classified every additional row. `Status` records current readiness;
existing implementation evidence remains in `Current implementation`. Required and
Planned rows must become `Complete`, while Deferred rows remain `Deferred` and
receive future-work documentation only.

| Feature | Target vanilla blocks | Horizontal Slab | Vertical Slab | DoubleSlab Positive | DoubleSlab Negative | DoubleVertical Positive | DoubleVertical Negative | Current implementation | Target behavior | Minecraft constraint | Planned approximation | Required automated tests | Required manual tests | Status |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Hoe, water-bottle, and mud-to-clay transformations — **Planned** | Grass/dirt/coarse/rooted dirt; mud; clay | None or vanilla-dependent | None | None | None | None | None | Shovel paths are customized; no general component transform covers other tools/environmental conversions. | Every supported vanilla source transformation changes only the targeted component and retains valid component state. | Some item behaviors and dripstone conversion inspect whole cells and full support chains. | Add transformations to the component replacement registry after confirming exact 1.21.1 rules; use hit or projected support to select a component. | Every source/result mapping; invalid faces; item durability/consumption; dripstone chain; all slots/axes; partner preservation. | Exercise hoes, water bottles, and dripstone over mixed soil/mud pairs; compare feedback with vanilla. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Soul-fire base behavior | Soul sand and soul soil families | Partial tags/settings | Partial tags/settings | None | None | None | None | Soul sand has custom mechanics; component-local Soul Fire placement was not found. | Owner-approved: a horizontal single uses its exposed top; a horizontal Double uses only the positive upper component; vertical singles/Doubles use the contacted component. When one fire cell contacts normal and Soul components, Soul Fire wins. The partner is unchanged and vanilla ignition/extinguishing rules are retained where possible. | Fire type is one state for the whole fire cell, even when it contacts two different components. | Project the fire contact onto components and apply the approved Soul-priority rule. Do not infer eligibility from a covered horizontal negative component. | Soul sand and soul soil; every shape/slot/axis/contact face; mixed Soul/normal contact with Soul priority; covered negative regression; portal/lava/player ignition; extinguishing; transaction notifications once; partner unchanged. | Ignite mixed bases from several positions and inspect type, placement, persistence, and extinguishing against vanilla. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Pumpkin carving — **Deferred / post-gate stretch candidate** | Pumpkin | None | None | None | None | None | None | Ordinary Pumpkin Slabs remain; carved slab registrations are commented out. | No beta.4 Required or normal Planned implementation. Reconsider only after all release-gate work is complete and a new owner approval is recorded. | The matching carved Slab/VerticalSlab result is not registered and facing needs persisted component state. | Do not make carving a dependency of persistence or Pumpkin Slab maintenance. Document the known omission in release notes. | None for the normal beta.4 gate; retain future test notes for every face/slot/axis, facing, drops, durability, mixed partner, and reload. | Verify ordinary Pumpkin Slabs still work and carving remains intentionally unsupported. | `Deferred` |
| Huge mushroom directional faces and textures | Brown mushroom block, red mushroom block, mushroom stem | None | None | None | None | None | None | Mapped shapes use ordinary Slab states and do not preserve vanilla's six directional booleans. Current assets use fixed generated models. | Owner-approved beta.4 scope: use Minecraft 1.21.1 behavior for all six face states and outer, inner, and stem textures during generation, placement, neighbor changes, breaking, component removal/singleization, and save/reload. | A half-block adds cut/exposed regions not directly represented by a full-block model; existing Slabee UV extraction may or may not represent every newly exposed face. | First perform an asset/UV feasibility review. Reuse vanilla textures and current UV extraction if sufficient. If new assets are required, stop and report to the owner; do not create placeholder textures. The gate only permits implementation to start: it does not change status. Then implement BlockState, per-component persistence, models/UVs, and vanilla neighbor behavior before validation. | Vanilla default and generated face combinations; all six directions; horizontal and vertical singles; every DoubleSlab and DoubleVertical slot/axis; partner removal exposing a new face; placement/generation/neighbor update/break; legacy migration; save/reload; client synchronization; model/UV rendering snapshots. | Generate and place all three block types, remove neighbors and partners, inspect outer/inner/stem/cut faces, reload chunks, verify client rendering, and compare with vanilla 1.21.1. The row becomes `Complete` only after implementation plus every automated and manual check succeeds. | `Pending feasibility spike` — Stage 11 steps 1–2 asset/UV task |
| Mangrove-root waterlogging — **Required** | Mangrove roots | Partial through base shape | Partial through base shape | No historical state | No historical state | No historical state | No historical state | Base Slab classes expose normal waterlogging, but source-specific root behavior and Double transitions are unverified. | Singles follow normal Slab waterlogging. Doubles hold no per-component water history, and combining expels water according to the virtual-component fluid policy. | Only one cell `FluidState` exists. | Use the common waterlogging contract and GameTest-fixed combine/remove results; do not restore expelled water. | Placement in source/flowing water; combine and singleize; neighbor fluid ticks; every slot/axis; save/reload. | Inspect water flow, sounds, and rendering around root slabs and mixed Doubles. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Non-log pillar orientation — **Deferred** | Basalt, purpur/quartz pillars, bone block, other non-log pillars | Fixed/default orientation | Fixed/default orientation | Fixed/default orientation | Fixed/default orientation | Fixed/default orientation | Fixed/default orientation | Source `AXIS` is not persisted. The fixed log contract is tracked separately in the required core row. | No beta.4 axis parity implementation. Keep current fixed/default orientation and document future BlockState/model work. | Selectable orientation needs component-owned state, model variants, persistence, and migration. | Do not add non-log pillar `AXIS` in beta.4. | No release-blocking tests beyond preventing accidental partial migration; retain a future all-axis matrix. | Verify beta.4 does not silently add inconsistent axis behavior. | `Deferred` |
| Hay fall-damage reduction — **Planned** | Hay bale | Settings only | Settings only | None | None | None | None | Hay fall reduction was not found. | Landing on an exposed hay component applies the vanilla fall-damage reduction once; landing only on a non-hay partner does not. | Fall callbacks are cell-based and an entity footprint can span both halves. | Project the landing footprint to contacted components and apply at most one vanilla-strength hay reduction. | Every shape/slot/axis; hay/non-hay pairs; boundary and wide entities; no double application. | Compare calibrated falls onto vanilla hay, hay Singles, and mixed Doubles. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Wool vibration occlusion — **Deferred** | All 16 wool colors | Settings/tags; verify | Settings/tags; verify | None | None | None | None | Wool shape settings exist, but component-aware vibration paths were not found. | No beta.4 component-aware vibration implementation. Existing behavior must not be represented as parity. | Vibration occlusion checks block states and rays through cells, not virtual components. | Document the gap for future geometry-aware vibration work. | No release-blocking implementation tests; retain future ray cases through wool/empty halves and both axes. | Verify release notes describe the limitation if observable. | `Deferred` |
| Special non-leaf loot, experience, and guarded interaction — **Planned** | Gravel, glass/tinted glass, glowstone, melon, gilded blackstone, sculk, and other non-leaf non-self-dropping sources | Generated loot; verify | Generated loot; verify | Partial component break | Partial component break | Partial component break | Partial component break | Slabee has generated loot tables and component break handling, but source-specific fortune/silk/experience and guarded behavior need a parity audit. Leaf loot is excluded and tracked as required core behavior. | Breaking one affected component produces its documented component-scale loot/XP and triggers source interaction hooks once; the partner remains. This row cannot change the required leaf-loot contract. | Vanilla loot and piglin anger receive a container state rather than the mapped component source. | Build the loot context from the mapped source plus component state, scale only where Slabee's crafting economy requires it, and explicitly forward guarded events. | Approved families: silk/fortune levels and no-tool cases; XP; explosions; creative; each slot/axis; piglin anger; no duplicate drops. Leaf cases remain in core tests. | Mine representative blocks with tools/enchants near piglins and count drops/XP against documented expectations. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Sculk safety near a catalyst; full catalyst behavior deferred | Sculk (the baseline has no Slabee Sculk Catalyst mapping) | Ordinary block; verify | Ordinary block; verify | Storage; verify | Storage; verify | Storage; verify | Storage; verify | The current mapping includes `SCULK` Slabs, not `SCULK_CATALYST`; there is no Slabee catalyst Block Entity or component spread contract. | Owner-approved beta.4 scope is stability only: placement, breaking, save/reload, and nearby mob deaths must not crash, lose block entities, duplicate experience, or corrupt the world. Full propagation, charge, bloom, and component conversion are future work. | Vanilla catalyst events and experience handling are cell/Block-Entity based even though the mapped Slabee target has no catalyst Block Entity. | Do not implement propagation/bloom parity. Test current mapped Sculk components around a vanilla catalyst and guard only verified crash, corruption, loss, or duplication paths. | Every shape/slot/axis placement and break; save/reload; mob death near vanilla catalyst; no crash, orphan/lost Block Entity, duplicate XP, component loss, or region corruption. | Repeat mob deaths and reloads around mixed Sculk Doubles; inspect XP counts, logs, and world integrity. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Static light, transparency, friction, and material settings — **Required** | All 265 mapped families, including glowstone, glass/tinted glass, ice, soul sand, and static source variants | Partial/settings | Partial/settings | Partial derived | Partial derived | Partial derived | Partial derived | Luminance and advanced glass culling are implemented, but a complete source-settings audit is absent. | Derived Double light uses the documented cell rule; transparency and occlusion remain geometry-aware; luminance, slipperiness, strength, sounds, tool requirements, burnability, and relevant tags match the mapped source unless an approved component rule overrides them. | Some settings are cell-wide or summarized for a Double. | Generate a source-to-Slab/Vertical snapshot and add focused geometry tests for approved derived values. Do not include decorative particles in this Required row. | Exhaustive settings/tag snapshot; mixed luminance and slipperiness pairs; skylight/tinted glass; culling seams; tool/strength/sound representatives; no accidental mapping omissions. | Inspect representative mixed glowing, glass, ice, and soul builds with fixed graphics settings. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |
| Decorative source particles — **Planned** | Crying obsidian and other mapped families with distinctive ambient particles | None or cell-wide | None or cell-wide | None | None | None | None | Crying-obsidian and other source-specific particle parity is incomplete. | Emit source particles from the exposed surface of the correct component without attributing them to a hidden partner. | Particle callbacks receive one container state and may be client-random. | Select the emitting component and exposed face geometrically; keep this separate from the Required static-settings audit. | Every particle family; all faces/slots/axes; hidden-partner rejection; deterministic seeded callback tests where possible. | Observe particle origin and rate for representative Singles and mixed Doubles. | `Pending dependency` — Stage 1 common foundation and ordered prerequisite Stages |

## Cross-cutting acceptance matrix

Every behavior PR must cover the dimensions below. Tests may use parameterized
fixtures rather than duplicate structures.

| Dimension | Required cases |
| --- | --- |
| Shape | Horizontal bottom and top Slab; Vertical east, west, south, and north; horizontal Double; vertical Double X and Z. |
| Slot | Positive and negative, with material order swapped. |
| Partner | Same material, inert solid, behaviorally opposite material, transparent/non-opaque material, and a stateful material where relevant. |
| Surface | Fully exposed, fully covered by partner, partially supported by Slabee, supported by a normal full block, and boundary contact. |
| Fluid | Dry, source water, flowing water, waterlogged single, combine while waterlogged, and remove after water was expelled. |
| Timing | Immediate callback, neighbor update, scheduled tick, random tick, chunk unload before execution, and save/reload after execution. |
| Player/entity | Survival and creative item use; centered and boundary hit; narrow and wide entities; applicable enchantments and exemptions. |
| Persistence | Current unversioned NBT, beta.4 versioned NBT, invalid/missing per-component data, client synchronization, and singleization after reload. |
| Removal result | Double removal returns a remaining single; normal single removal returns air; waterlogged single removal restores its held fluid; no path leaves a Double container or block entity. |
| Mutation cause | Survival and creative player breaks, natural disappearance, combustion, and falling transfer select the correct loot/effect policy without duplicate drops, sounds, particles, or game events. |
| Piston safety | Audit Minecraft 1.21.1 `PistonBehavior` and Block Entity handling for horizontal/vertical Singles, DoubleSlab, and both DoubleVertical axes; verify cell-level push/retract/refusal without component loss, duplication, Block Entity loss, NBT corruption, or client/server divergence. Component-only movement is not a beta.4 case. |
| Transaction effects | Dirty/listener/neighbor work exactly once per transaction; loot, game effects, and component-specific outcomes exactly once per affected component. |

## Persistence design gate

Component-state persistence is an architectural prerequisite, even though its full
implementation belongs to Stage 6. Stage 1 must run a small Minecraft 1.21.1 API
spike that proves the accepted B2 Decision. Its readiness is `Pending feasibility
spike`, not `Blocked by design decision`:

- an additive versioned schema using validated `NbtHelper.fromBlockState` and
  `NbtHelper.toBlockState`, including registry lookup, malformed-data, and round-trip
  behavior;
- validation that every decoded block belongs to the known Slabee `BlockTriple`
  mapping and expected shape family;
- a filter that writes only component-owned properties, never treating slot-derived
  `TYPE`/`FACING` or environment-derived `SNOWY`/leaf `DISTANCE` as authoritative;
- explicit container persistence for DoubleVertical `AXIS`;
- legacy block-ID NBT compatibility; known-block property errors use that block's
  default, while invalid/removed/unsupported IDs lose only that component, yielding
  a valid Single partner or air when neither restores;
- rate-limited diagnostics; and
- the new schema, diagnostics, fixtures, and ownership rules that Stage 6 will
  implement completely.

Stages 4 through 13 may prepare unrelated tests or scaffolding, but no
persistence-dependent implementation in those stages may begin until this
B2 spike review passes. This is an implementation-order dependency on an approved
gate, not an unanswered design decision. The spike is not the Stage 6 implementation.

## Pull request roadmap

Each item below is an independently reviewable roadmap stage, not necessarily one
pull request. Split a stage into multiple small branches and PRs whenever that
keeps design, implementation, and tests reviewable. No PR may start by copying the
two Windows backup commits. Dependencies are intentionally ordered around reuse
of the virtual-component foundation, not around apparent feature difficulty.

### 1. GameTest foundation

- Add a Fabric-compatible GameTest source set, run configuration, fixtures, and a
  CI task without changing gameplay.
- Add shape/slot/axis fixture helpers and a test naming convention.
- Capture current placement, combination, removal, fluid, NBT, and chunk-reload
  behavior as baseline tests, marking known defects explicitly rather than encoding
  them as desired behavior.
- Run the B2 persistence spike in its own first PR. Prove the accepted versioned,
  filtered `NbtHelper.fromBlockState`/`toBlockState` path, registry validation,
  property ownership, legacy ID-only input, malformed-data fallback, and round trip.
  This PR adds tests and fixtures but no new gameplay behavior. Stage 6 may not begin
  until its review succeeds.
- Add and pilot the B4 performance procedure in a separate baseline PR: fixed
  hardware/runtime controls, fixtures, warm-up, repetitions, JFR/raw output, and
  comparison reports. Start with the provisional unit-consistent thresholds defined
  in [Performance baseline before feature code](#performance-baseline-before-feature-code),
  including the 5% idle and 10% burst terms after conversion to the measured
  metric's unit. Pilot evidence must version the final thresholds; GitHub Actions
  shared runners never decide performance pass/fail.
- Exit when tests run locally and in CI, failures preserve useful server logs,
  there is no dependency on a pre-existing world, the B2 spike is reviewed, and
  the B4 procedure plus pilot thresholds are versioned.

### 2. Development version

- Change to an owner-approved beta.4 development version in a dedicated PR.
- Verify generated metadata and artifact names only; do not publish artifacts.
- Exit when development builds cannot be confused with public beta.3 and no
  dependency or Minecraft version changes are included.

### 3. Read-only `ComponentRef`

- Introduce immutable component lookup for singles and both Double containers.
- Give singles the explicit location `SINGLE`; reserve `POSITIVE` and `NEGATIVE`
  for Double slots. Normalize location, container kind, shape, container-owned axis,
  mapped source, state ownership, and face geometry without mutating the world or
  block entity.
- Add exhaustive mapping and hit/entity-position selection tests.
- Exit when all later features can identify a component without reading raw NBT or
  duplicating positive/negative branches.

### 4. Component removal and singleization

- Implement one transactional operation that removes a selected component and
  returns a structured `MutationResult`. It must distinguish `SINGLEIZED`,
  `CELL_CLEARED`, `FLUID_RESTORED`, and `NO_CHANGE`-equivalent outcomes.
- Convert a Double partner to the correct single shape. For a `SINGLE` reference,
  clear an ordinary single to air or restore the held `FluidState` from a
  waterlogged single according to Minecraft 1.21.1 behavior. Leave no Double
  container, block entity, or orphaned component data.
- Represent survival break, creative break, natural disappearance, combustion,
  and falling transfer as distinct mutation causes so each receives the correct
  loot, sound, particle, game-event, and follow-up behavior.
- Route the existing player component-break path through it without changing drop
  policy beyond fixing duplicate/lost events.
- Test all slots, axes, state preservation, client updates, neighbor notifications,
  game events, and save/reload. Include horizontal and vertical singles, dry and
  waterlogged removal, every mutation cause, all result variants, exact effect/drop
  counts, restored fluid, and absence of residual block entities.
- Emit dirty/listener/neighbor work once per transaction, while loot and affected-
  component effects occur exactly once for each removed component.
- Exit when no removal leaves an orphaned block entity or changes the partner.

### 5. Component replacement

- Implement transactional identity/state replacement with slot normalization,
  environment-state recalculation, one dirty/update cycle, and atomic two-component
  batches.
- Replace the incomplete conversion enum/queue with scheduled or direct operations
  where appropriate.
- Add rollback/failure, simultaneous replacement, and event-count tests.
- Exit when grass, sponge, copper, coral, hardening, and stripping can share one
  primitive.

### 6. BlockState persistence and legacy NBT migration

- Implement the accepted Decision Record completely: write and restore the new
  versioned, filtered component BlockState NBT for both Double containers.
- Validate decoded blocks through the known `BlockTriple`/shape mapping; restore
  only component-owned properties. Derive `TYPE`/`FACING`, recalculate environment
  state such as `SNOWY` and leaf `DISTANCE`, and persist container `AXIS` once.
- Read the current unversioned `positive_slab`, `negative_slab`, legacy horizontal
  `facing`, and vertical `axis` layout without requiring a prior resave.
- Make outer `BlockState.AXIS` authoritative for loaded beta.4 DoubleVertical
  containers. Use valid legacy block-entity `axis` only to initialize/repair that
  state during unversioned migration; diagnose versioned duplicates that disagree.
- If a known supported block has only an invalid property, use that block's default
  value for the property. Treat an invalid, removed, unknown, or unsupported block
  ID as a lost component: singleize a valid partner, or replace the cell with air
  if neither component restores. Rate-limit diagnostics and never discard a valid
  partner because the other component is malformed.
- Implement server/client synchronization through update packets and initial chunk
  data. Every committed state mutation must call `markDirty` and notify listeners
  once per transaction with the authoritative post-mutation state.
- Preserve the new state both while the Double remains intact and when one side is
  converted to a Single. Cover forced chunk save/unload/reload and client join or
  chunk re-entry after mutation.
- Check in both legacy and new-format fixtures covering both containers, both axes,
  both slots, valid round trips, missing fields, invalid IDs/properties/values,
  Double-preserving mutation, and singleization. The new-format suite must include
  a Decision-Record-approved representative component-owned property, a filtered
  derived/environment property, and malformed property data so the property path
  is tested before feature-specific extensions exist.
- Exit Stage 6 only when new-format save/restore, legacy reads, fallback,
  server/client update packets, initial chunk data, dirty/listener behavior,
  Double and Single state retention, chunk reload, and every fixture pass. Log
  `AXIS` remains explicitly excluded.
- Later feature stages add and validate their own approved property sets, such as
  leaves or huge-mushroom faces, using this completed mechanism. Those extensions
  are feature-stage requirements and do not defer any Stage 6 exit criterion.

### 7. Grass, snow, and paths

- Complete grass survival, dirt conversion, component-to-component spread, and bone
  meal; add mycelium/podzol parity where the shared strategy applies.
- Make `SNOWY` environment-derived and complete projected snow/path support.
- Cover the verified Minecraft 1.21.1 Rooted Dirt shovel behavior: Dirt Path result,
  no Hanging Roots drop, flatten sound, `BLOCK_CHANGE`, one tool-durability use,
  obstruction above, and component-local partner preservation. Keep the separate
  hoe-to-Dirt plus Hanging Roots behavior under the separate Planned soil-transform
  row.
- Move path creation/reversion to the common component operations and scheduled
  ticks; remove dependency on continuous block-entity ticking for conversions.
- Exit when every row in the grass/snow/path table is `Complete`.

### 8. Sponge and coral

- Implement exposed-face water contact, bounded cell-wide sponge absorption,
  component-local wet/dry transitions, and scheduled coral death.
- Evaluate both sponge components and their reachable-water sets symmetrically from
  one pre-transaction snapshot. Wet every qualifying component, remove the union of
  water positions once, and avoid fixed positive/negative priority.
- Share one fluid-contact abstraction while keeping fluid removal cell-wide.
- Exit when absorption limits, mixed partners, ultrawarm drying, all coral colors,
  save/reload, and waterlogged-single cases pass.

### 9. Ice, magma, soul sand, and bubbles

- Implement the approved ice-melt rule: waterlog a compatible singleized partner,
  suppress water for an incompatible partner, create source water only when the
  whole ice cell melts outside ultrawarm dimensions, and never push water sideways.
- Before production bubble code, run an independent feasibility spike for the
  approved primary target: component-local upward/downward force, AABB-overlap area
  weighting for spanning entities, and left/right visuals that match the force.
  Cover asymmetric vanilla velocity updates/caps, zero-near compositions, multiple
  entities, Player/Mob/Item/Boat, both axes, client prediction, and rendering cost.
- Primary acceptance is separate from fallback acceptance. The primary passes only
  when its per-entity AABB composition is correct for the complete matrix, physical
  direction, bubble direction, and bubble presence agree, client/server behavior is
  stable, and compatibility plus B4 performance criteria pass.
- An unfinished spike or isolated failed test does not permit fallback. Treat the
  fallback as a candidate only after evidence shows that Minecraft 1.21.1
  expressiveness, compatibility, stability, or B4 performance criteria prevent a
  safe primary implementation. Record failed conditions, attempted approaches,
  measured performance, and remaining constraints; report them in Japanese and
  obtain explicit owner approval before adopting fallback. Codex must not adopt it
  automatically.
- Only after that approval, validate the fallback
  using fixed exposed-top-area fractions, never entity AABBs: horizontal Slab 1.0,
  VerticalSlab 0.5, each DoubleVertical component 0.5, horizontal Double upper 1.0
  and lower 0, and non-source 0. Apply the one composed result to every entity,
  independent of positions/counts. Fallback passes only when its fixed formula and
  finalized zero/near-zero and velocity/cap order produce the same physical
  direction, bubble direction, and bubble presence for every entity and pass its
  stability, compatibility, and B4 performance tests. If fallback fails, stop and
  report; do not proceed automatically to a different approximation.
- Correct projected slipperiness alongside the approved ice rules.
- Consolidate magma damage, soul-sand collision/slowdown, and Soul Speed selection.
- Keep source propagation bounded and avoid an unconditional Double BE tick.
- Exit when source changes propagate through existing columns, center behavior is
  documented, and all affected checklist rows are `Complete`.

### 10. Combustion

- Add mapped-source flammability, projected fire contact, independent random burn
  decisions from one snapshot, component removal, and deterministic simultaneous
  burn-out whose result is independent of slot evaluation order.
- When one component burns, singleize its partner and create no fire in the occupied
  cell. Only when both burn may vanilla placement/survival/rain/fluid rules produce
  normal Fire or Soul Fire; otherwise leave air. Commit once with no observable
  intermediate Single state.
- Implement the owner-approved Soul Fire rules, including upper-only horizontal
  Double selection, contacted vertical components, and Soul priority on mixed
  contact.
- Exit when the flammability matrix, mixed pairs, fireproof exceptions, lava, save
  boundaries, and long manual burn runs pass without continuous ticking.

### 11. Copper, logs, and huge mushrooms

- Add component copper oxidation, wax, axe scraping/deoxidation, and log/wood/stem/
  hypha/bamboo stripping.
- Keep Slabee's intentional fixed log grain direction; stripping changes only the
  selected component to its mapped stripped form. Do not add log `AXIS`.
- Complete huge-mushroom work in this order:
  1. Verify Minecraft 1.21.1 placement, generation, neighbor, break, six-direction
     state, and outer/inner/stem texture behavior, then compare the existing assets
     with Slabee's current UV extraction.
  2. If existing assets are insufficient, stop before implementation and report
     the exact gap to the owner; do not create placeholder textures.
  3. After the asset decision, implement the required single BlockStates,
     per-component Double persistence, models, UVs, and vanilla-equivalent neighbor
     behavior.
  4. Test horizontal Slabs, VerticalSlabs, DoubleSlabs, and DoubleVerticalSlabs on
     every slot and X/Z axis.
  5. Remove each partner and verify the newly exposed face against the corresponding
     Minecraft 1.21.1 behavior and intended inner/outer/stem texture.
  6. Verify save/reload, client synchronization, singleization, and final rendering
     for every required state combination.
  7. Mark the checklist row `Complete` only after implementation and every step
     above passes. Passing the asset gate alone is not a completed feature.
- Exit the stage only when copper stages/wax, fixed-direction stripping, all seven
  huge-mushroom steps, item feedback, and mixed-partner tests pass.

### 12. Leaves

- Treat lifecycle and loot as separate required workstreams. Add persisted
  `PERSISTENT`, environment-recalculated `DISTANCE`, projected support graph updates, random decay, and
  flammability without embedding loot probability rules in the lifecycle code.
- Implement the accepted B3 algorithm: evaluate the mapped vanilla leaf loot once
  per removed component with its Fortune context, then give every generated
  ordinary `ItemStack` its own independent 50% keep/drop roll. Never roll once for
  the entire result and never change the `count` in a retained stack. Distribution
  equality is not required; expected-value equality is. Make fixed-seed runs
  reproducible. Bypass thinning for exact Shears/Silk Touch one-Slab drops and emit
  nothing for creative, Fire, and other no-loot causes.
- Keep short fixed-seed coverage in normal CI. Provide the formal high-sample
  statistical suite as a separate task/artifact. It uses three fixed seeds and
  `n = 1,000,000` paired trials per seed and probabilistic condition. For a target
  item in trial `i`, let `V_i` be the count from one vanilla leaf, `S_i` the total
  from two same-species Slabee leaf components, and `D_i = S_i - V_i`. Compute
  `mean_D`, the sample standard deviation `s_D` of the per-trial differences, and
  `SE_D = s_D / sqrt(n)`; pass only when `abs(mean_D) <= 5 * SE_D`. Do not add the
  vanilla and Slabee sample variances as independent samples. If `s_D = 0`, pass
  only when `mean_D = 0`. Every seed, condition, and target item must pass. This
  tests expected values, not complete distribution equality. Use exact equality
  assertions instead for Shears, Silk Touch, Creative, Fire, and other deterministic
  outcomes. Run the formal task at Stage 12 completion and rerun the same task in
  Stage 14 before release, not on every CI run; normal CI contains only short
  deterministic unit tests and fixtures. The seed literals, constant names, exact
  trial-count representation, and paired-difference formula have one source of truth
  in the design document's [Leaf loot](../design/virtual-components.md#leaf-loot)
  section.
- Cover all ten leaf variants and natural versus player-placed components.
- Exit only when the separate lifecycle and leaf-loot checklist rows are both
  `Complete`: asymmetric canopies update and decay across chunk save/load without
  broad per-tick scans, and the Decision Record's exact and statistical loot tests
  pass for every relevant tool, cause, species, shape, slot, and axis.

### 13. Falling blocks

- Before production work, complete an independent B1 spike that adds a Slabee
  namespaced payload to vanilla `FallingBlockEntity`. Store schema version,
  component BlockState, container kind, slot, and vertical axis. Do not register a
  custom entity or use Fabric Data Attachment. Accept non-Slabee blocks only through
  explicit adapters.
- The spike must prove entity save/reload while falling, source-partner retention,
  landing as a Single and recombination, exact no-loss/no-duplication, representative
  Concrete Powder hardening in crossed/landing water, spawn-failure rollback, and
  chunk-boundary behavior. Do not begin the remaining bullets until this review passes.
- Implement component support projection, falling payload, removal/singleization,
  render/state preservation, landing as a single or recombination, water hardening,
  drops, and chunk/world-boundary handling.
- Preserve the verified concrete-powder split: water below stationary dry powder
  does not harden it, while entering or landing in water uses the falling hardening
  path and the vanilla side-solid/contact rules.
- Cover sand, red sand, gravel, and every concrete-powder color through parameterized
  tests.
- Exit when the complete gravity and hardening tables are `Complete` and no falling
  case duplicates or deletes the partner.

### 14. Integrated regression, performance, and release preparation

- Complete every Required and Planned additional parity row under its approved
  scope. Keep non-log pillar `AXIS`, wool vibration occlusion, and Pumpkin carving
  out of normal implementation PRs and document them as Deferred. Pumpkin may be
  reconsidered only after all normal release-gate work and a new owner approval.
- Audit the actual Minecraft 1.21.1 piston behavior selected by every current
  Single mapping and both Double containers, then run extension, sticky retraction,
  push-limit, blocked-destination, chunk save/reload, and client synchronization
  tests. Preserve the verified cell-level result; if a current container path is
  unsafe, explicitly refuse that movement rather than implementing component-only
  movement.
- Run the required Sculk safety checks around a vanilla catalyst without adding
  catalyst propagation/bloom parity.
- Run the full automated matrix, migration fixtures, clean-world and upgraded-world
  manual plans, dedicated server smoke test, and client smoke test. Rerun the
  existing Stage 12 B3 formal statistical task unchanged and retain its report as
  the pre-release leaf-loot evidence; do not create another statistical gate or task.
- Run the complete fixed-environment B4 measurement and compare it with current
  main, matching vanilla controls, Single Slabs, and work per 1,000 events. Apply
  the pilot-versioned thresholds; do not use GitHub Actions shared-runner timings
  for pass/fail. Feature PRs use only the relevant shortened scenario set.
- Remove debug output, review warnings/deprecations, verify documentation and
  metadata, and produce release notes that explain intentional approximations.
- Exit only when every beta.4-required and beta.4-planned item is `Complete`, every
  Deferred item is documented, no spike or release-blocking gate remains, CI is
  green, and the owner approves publication. Publishing is
  a later, explicit operation and is not part of these implementation PRs.

## Performance baseline before feature code

Record the baseline on a fixed Java 21 runtime, fixed JVM arguments, fixed render
distance/simulation distance, and a committed test-world generator or command set.
Capture warm-up separately from measurements and retain raw logs with the PR or a
linked artifact. Record CPU, memory, OS, power mode, Minecraft/Fabric/Slabee commits,
world seed, gamerules, graphics settings, loaded chunks, and entity counts. The
initial procedure uses a 10-minute warm-up, 15-minute measurement, and five fresh-JVM
trials per full scenario unless the pilot justifies and versions a change.

| Scenario | Suggested fixture | Measurements |
| --- | --- | --- |
| Idle containers | Grids of 0, 1,000, and 10,000 heterogeneous Doubles in loaded chunks | Server MSPT distribution, block-entity tick time/count, client frame-time, informational FPS, and allocations if available. |
| Random-tick behavior | Fixed-size grass/copper/leaf grids at a fixed random-tick speed | MSPT, callbacks per second, successful transitions, and work per transition. |
| Scheduled/neighbor burst | Fixed-count fluid, coral, ice, and command-driven updates beside a large mixed grid | Total update time, scheduled tick count, duplicate notifications, maximum queue depth, and time per 1,000 events. |
| Sponge | Recreated equal-volume water tanks with single and paired sponges | MSPT, cells visited/removed, duplicate reachability, and time per 1,000 qualifying component operations. |
| Bubble columns | Equal-height water columns over singles, horizontal Doubles, and vertical mixed sources | MSPT, entity update cost, force correctness, and client frame-time. |
| Combustion | Controlled flammable/nonflammable mixed grid with a fixed fire-tick configuration | MSPT, fire/component updates, removals per second, and neighbor-event count. |
| Falling blocks | Release a fixed grid of single and paired gravity components | Entity count, MSPT/frame-time, landing time, drops, and lost/duplicated components. |
| Client rendering | Fixed camera path across 0, 1,000, and 10,000 visible/loaded Single and Double fixtures | Frame-time p50/p95/p99, draw/render work where available, allocations, and visible correctness. |
| Save and reload | Same worlds before and after state persistence, with a forced save and restart | Region/chunk size, save duration, load duration, migration diagnostics, and state mismatches. |

Every report includes server MSPT and client frame-time p50/p95/p99 where applicable,
JFR CPU/allocation/GC evidence, the matching vanilla control, Single Slab control,
and time per 1,000 events. An unimplemented-main run is an absence-of-work baseline,
not sufficient evidence by percentage alone for a completed feature.

For every declared comparison, let `baseline_ms` and `candidate_ms` be the baseline
and candidate values of the same time metric, and let
`delta_ms = candidate_ms - baseline_ms`. Convert the percentage term to milliseconds
before taking a maximum:

- `idle_threshold_ms = max(0.05 * baseline_ms, 3 * MAD_ms, 0.5 ms)`;
- `burst_threshold_ms = max(0.10 * baseline_ms, 3 * MAD_ms, 2 ms)`; and
- `regression = (delta_ms > corresponding_threshold_ms)`.

Calculate server MSPT and client frame time separately, each with its own baseline,
MAD, delta, and threshold. Client pass/fail uses frame time in milliseconds, never
FPS; FPS may be reported only as informational output. For a non-time metric such
as allocations, first express the percentage term and MAD in that metric's own
unit and use `max(rate * baseline_metric, 3 * MAD_metric)` without the `0.5 ms` or
`2 ms` absolute floor. Never compare a dimensionless percentage directly with
milliseconds or another metric unit. Mandatory investigation whenever server p99
remains above 50 ms is unchanged.

The first baseline PR pilots and versions scenario-level thresholds from these
starting values; they are not final release limits before pilot evidence. A normal
feature PR runs only its shortened affected scenarios. Each Stage completion and
the beta.4 release candidate run the full fixed-environment suite. GitHub Actions
shared runners may execute correctness tests but never decide performance pass/fail.
The release gate cites the approved procedure/version and also requires no unbounded
queue or scan. Any threshold change or accepted feature cost must be reviewed and
recorded beside its evidence rather than being redefined at release time.

## Out of scope for beta.4

- Other Minecraft versions.
- NeoForge.
- New shapes such as stairs, fences, walls, or panes.
- A true half-cell fluid implementation.
- A large Block Entity or rendering-system redesign.
- Adding large numbers of vanilla blocks that Slabee does not currently map.
- Selectable log-family `AXIS` and new grain-direction model variants; existing
  fixed Slabee log direction remains intentional.
- Non-log pillar `AXIS` and component-aware wool vibration occlusion.
- Pumpkin carving and Carved Pumpkin Slab families, unless a separate post-gate
  owner approval promotes the stretch candidate after normal beta.4 work is done.
- Full Sculk Catalyst propagation, charge, bloom, and component conversion. The
  beta.4 stability checks around a vanilla catalyst remain in scope.
- Component-only piston movement, moving-component payloads, and component-level
  push-chain/recombination logic. Beta.4 verifies cell-level safety only.

These exclusions do not excuse regressions in current mappings. They prevent the
core-behavior project from expanding into ports, new geometry families, a fluid
engine, or a wholesale architecture rewrite.

## Release gates

Beta.4 may be published only when all of the following are true:

- every beta.4-required and beta.4-planned item in the scope classification is
  `Complete`;
- every deferred item is accurately described as future work and excluded from
  normal implementation dependencies;
- every accepted approximation has an automated regression test and a manual test;
- huge-mushroom asset/UV feasibility has been followed by the required BlockState,
  component persistence, model/UV, neighbor, every-shape, newly-exposed-face,
  save/reload, client-sync, and rendering implementation and tests; the asset gate
  alone is not completion evidence;
- current-main and beta.3-era Double NBT fixtures load without component loss;
- normal and waterlogged single-component removal produces the documented
  `MutationResult`, final air/fluid state, cause-specific effects, and no residual
  Double container or block entity;
- the B2 persistence spike was reviewed before dependent implementation, and Stage
  6 independently passes new/legacy fixtures, property-default fallback,
  invalid/removed/unsupported-ID component loss with correct Single/air results,
  rate-limited diagnostics, server/client packets, initial chunk data,
  dirty/listener checks, Double and Single retention, and chunk save/reload;
- combine, replace, remove, singleize, save/reload, and client synchronization pass
  for both container types and axes;
- piston safety is verified for Single Slabs, VerticalSlabs, DoubleSlabs, and both
  DoubleVertical axes with no component/Block Entity loss, duplication, NBT damage,
  or client/server mismatch; any unsafe container movement is explicitly refused,
  and component-only movement is not required;
- leaf loot remains classified as beta.4-required, uses the accepted per-`ItemStack`
  independent 50% thinning Decision without whole-result rolls or `count` changes,
  and exact Shears/Silk Touch/Creative/Fire results plus the design document's
  three-seed, `n = 1,000,000` paired-difference formal expected-value test, including
  its `s_D = 0` rule, passes for every seed, condition, and target item at both the
  Stage 12 and pre-release executions of the same task;
  lifecycle completion is assessed separately;
- the B1 falling-payload spike passed save/reload, partner preservation, landing
  recombination, no-loss/no-duplication, Concrete Powder hardening, spawn rollback,
  and chunk-boundary checks before production falling implementation;
- the bubble feasibility spike proves the primary component-force/left-right visual
  target with per-entity AABB weights; or, only after documented primary infeasibility,
  a Japanese owner report, and explicit owner approval, proves the entity-independent
  fixed-exposed-area fallback. Primary and fallback use separate acceptance criteria,
  and neither permits a physical-direction, bubble-direction, or bubble-presence
  mismatch. A failed fallback returns to owner review rather than another automatic
  approximation;
- no unconditional continuous tick was added to all Doubles, and the remaining
  existing block-entity ticker has been removed or justified with measurements;
- the integrated performance comparison uses and passes the fixed-environment B4
  procedure and pilot-versioned thresholds, includes vanilla/Single/per-1,000-event
  controls, and does not use GitHub Actions shared-runner timing as pass/fail;
- no failed or unreviewed spike blocks a beta.4-required or beta.4-planned item;
- a clean client and dedicated server complete smoke validation on Minecraft
  1.21.1 Fabric with Java 21; and
- CI is green on the release candidate commit.

## Decision registry

The canonical owner decisions, approved technical Decisions, and remaining spike
gates are maintained in [`beta4-owner-guide.ja.md`](beta4-owner-guide.ja.md). This
roadmap tracks their implementation readiness without reopening them. `Blocked by
design decision` is used only for an unanswered Owner or Technical Decision.
`Pending feasibility spike` names an approved Decision's independent spike gate.
`Pending dependency` identifies an approved item waiting for a preceding PR, common
foundation, or another Stage. `Not started` means all Decisions and mandatory
dependencies are complete and work can begin immediately. `In progress` means work
is active, `Complete` requires implementation plus mandatory validation, and
`Deferred` is outside beta.4. Ordinary Stage/PR ordering is never a design-decision
blocker.
