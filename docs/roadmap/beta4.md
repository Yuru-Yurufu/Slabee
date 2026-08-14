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
| `Not started` | No beta.4 implementation covers the behavior. |
| `Partial` | Some shapes or cases exist, but known gaps remain. |
| `Implemented` | The intended implementation exists, but completion evidence is not yet assembled. |
| `Needs verification` | The current code may meet the target; focused automated and manual checks are still required. |
| `Blocked by design decision` | Scope is known, but implementation must wait for an unanswered owner item or an explicitly named technical Decision Record. The row must name its blocker. |
| `Complete` | Target behavior, migration where relevant, automated tests, manual checks, and documentation all pass. |

No current row is marked `Complete` because the repository has no GameTest suite
yet. `Implemented` and `Needs verification` are not release-complete states.
For huge mushrooms, passing the asset/UV feasibility gate alone does not advance
the row to `Implemented` or `Complete`; implementation and the full automated,
manual, persistence, synchronization, and rendering checks are still required.

## Beta.4 scope classification

The initial reviewed inventory grouped behavior into 31 core rows and 10 additional
parity rows. Leaf loot is now separated from leaf lifecycle as required by the
owner decision, so the current checklist has 32 core rows and 10 additional rows.
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
  Decision Record; its beta.4 scope is not undecided.
- Stability-only Sculk verification: placement, breaking, save/reload, and mob death
  nearby must not crash, lose block entities, duplicate experience, or corrupt the
  world. Full catalyst propagation and bloom are not required.

### Planned for beta.4, with an independent design or asset gate

- Simultaneous combustion, component falling, ice-melt fluid results, and split
  bubble behavior. Their product-level choices remain candidates until answered in
  the canonical owner guide.
- Soul Fire, using the owner-approved contact and priority rules.
- Huge mushroom face-state parity for red mushroom blocks, brown mushroom blocks,
  and mushroom stems. An asset/UV feasibility review must finish before code or
  model changes begin.

These items are intended for beta.4, but implementation must not begin by silently
choosing an unresolved candidate or creating placeholder assets.

### Out of beta.4 and future candidates

- Log-family `AXIS` and selectable grain direction. Existing Slabee log Slabs keep
  their intentional fixed texture/model direction. This does not remove axe
  stripping from beta.4.
- Full Sculk Catalyst propagation, charge, bloom, or component conversion.
- Component-only piston movement. Beta.4 retains and verifies cell-level behavior;
  unsafe movement may be explicitly rejected without implementing half movement.
- The platform, shape, fluid, and architecture exclusions listed later in this
  document.

### Owner scope decision still required

- Pumpkin carving.
- The beta.4 disposition of additional parity audit rows not explicitly classified
  above: hoe/water-bottle/mud-to-clay transformations, mangrove-root waterlogging,
  non-log pillar orientation, hay fall reduction, wool vibration occlusion, special
  loot/guarded interactions other than required leaf loot, and static
  light/particle/material parity.

The canonical questions, choices, and answers are maintained only in
[`beta4-owner-guide.ja.md`](beta4-owner-guide.ja.md).

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
| Grass survival and conversion to dirt | Grass block | Partial | Partial | None | None | None | None | Single classes copy vanilla-like light/fluid checks, but random ticks are not enabled and failure replaces the cell with full dirt. | Each exposed grass component evaluates vanilla survival; a failed component becomes matching dirt without changing its partner, shape, axis, or valid state. A buried lower component does not survive as grass. | Light and opacity APIs are cell-oriented; the lower horizontal component is covered by its partner. | Use component top-face exposure plus vanilla light/fluid checks. Treat the covered negative horizontal component as unable to survive. | Survival under air, snow layer, opaque cover, full water, and partial Slabee cover; every shape/slot/axis; partner preservation; save/reload. | Compare timing and visible conversion with vanilla grass; inspect snow and water edge cases. | `Partial` |
| Grass spread to dirt components | Grass block; dirt | Partial | Partial | None | None | None | None | Single code only targets vanilla full dirt and can place the wrong shape. Backup commit `9c8b932` contains ideas but is not production code. | Eligible grass spreads to eligible exposed dirt components. Only the target component becomes grass; slot, orientation, partner, and valid state are retained. | Vanilla random tick samples block positions, not components; two eligible targets may share one cell. | Expand each sampled position into eligible component references, select deterministically with seeded randomness, and keep vanilla attempt count/frequency. | Seeded spread/no-spread tests for all source/target shape pairs, both Double slots and axes, light/water blockers, no duplicate attempts, save/reload. | Observe natural spread density and symmetry in a controlled mixed-slab plot. | `Partial` |
| Grass bone meal | Grass block | Empty `grow` | Empty `grow` | None | None | None | None | `Fertilizable` is implemented on grass singles, but both `grow` methods are empty. | Bone meal applied to an exposed grass component runs vanilla-like vegetation generation without modifying the partner. | Plants occupy whole cells and cannot be limited to a half-cell; one destination cell cannot host results from two components. | Hit position selects the source component. Use vanilla vegetation placement in whole cells above exposed top regions and de-duplicate destination positions. | Success/failure and consumption; top/bottom and four vertical facings; both Double slots; blocked space; deterministic seeded vegetation set. | Compare density, sound/particles, and item use with vanilla grass in open and cramped areas. | `Partial` |
| Snow support, `SNOWY` state, and appearance | Grass block, podzol, mycelium; snow and snow layers | Partial | Partial | Partial | Partial render/state assumptions | Partial | Partial | Singles recalculate `SNOWY` from the block above; Double rendering has special handling, and snow placement has soul-sand/mud/path shape hooks. State is not generally persisted per component. | Recalculate `SNOWY` from snow contact with each exposed top region. Horizontal Double positive may be snowy; its covered negative component may not. Both vertical halves may be snowy when their top regions contact snow. | Snow occupies a cell/layer rather than a component region; vanilla has one snow state above the pair. | Treat snow presence as cell-wide, then project contact onto exposed component top faces. Store no historical `SNOWY` value. | Placement/removal neighbor updates; snow layer counts; grass/podzol/mycelium; path, mud, soul-sand support; all slots/axes; chunk reload. | Inspect tint/model seams and collision with one and multiple snow layers in each orientation. | `Partial` |
| Dirt-path creation, including Rooted Dirt | Grass, dirt, coarse dirt, podzol, mycelium, and rooted dirt where the Minecraft 1.21.1 shovel mapping permits | Implemented; verify | Implemented; verify | Partial | Deliberately rejected while covered | Partial | Partial | Singles preserve `TYPE`/`FACING`, and the current dirt tags include Rooted Dirt. Horizontal Double only changes the exposed positive component; vertical Double can select sides. **Verified vanilla fact:** `ShovelItem.PATH_STATES` maps Rooted Dirt directly to Dirt Path when the clicked side is not down and the entire cell above is air. It plays the shovel-flatten sound, emits `BLOCK_CHANGE`, and damages the tool by one for a player; it drops no Hanging Roots. The separate hoe action changes Rooted Dirt to Dirt and drops one Hanging Roots item. | A shovel changes only an eligible target component to the matching Dirt Path shape and leaves its partner unchanged. Covered horizontal negative dirt is not path-eligible. Rooted Dirt follows the same shovel result and feedback as vanilla, including no Hanging Roots drop. Hoe behavior remains the separate owner-scope-pending soil-transformation item and is not decided by this row. | Vanilla checks one whole above cell and returns one whole replacement state; it cannot express a top region blocked over only one vertical half. | Route item use through `ComponentRef`. A normal full block above blocks all components; a Slabee above blocks only the component whose top projection it overlaps. Horizontal negative components covered by their partner are rejected. Use hit projection for explicit Vertical/Double selection and the documented fallback policy only when no component is selected. Emit flatten sound, `BLOCK_CHANGE`, and one durability use once for a successful transaction, with no shovel-generated Hanging Roots. | Every listed source including Rooted Dirt; successful Rooted Dirt result is Dirt Path with zero Hanging Roots; hoe behavior kept in its separate test/scope; click from down; full-air/full-block/partial-Slabee space above; horizontal top/bottom; all vertical facings; both Double slots and axes; target and partner identities; sound, `BLOCK_CHANGE`, durability, and item result exactly once. | Shovel Rooted Dirt beside vanilla controls with clear, fully blocked, and half-blocked space above; inspect Dirt Path result, absence of Hanging Roots, feedback, tool wear, precise hit positions, and unchanged mixed partners. | `Partial` |
| Dirt-path survival and reversion | Dirt path | Implemented; verify | Implemented; verify | Partial | N/A covered | Partial | Partial | Singles schedule conversion when blocked. Doubles use an incomplete per-entity conversion queue; horizontal only supports positive. | Every exposed path component checks vanilla placement support and becomes matching dirt independently when obstructed. | Scheduled ticks target the container position, not a stored slot. | Schedule a container tick and recompute all path components at execution; commit all due replacements atomically. Do not retain a permanent block-entity ticker. | Obstruction added/removed before tick; both vertical sides due together; partner preservation; unloaded/reloaded scheduled tick; sound/event count. | Place gates, solids, slabs, and vertical slabs above each orientation and inspect height/collision. | `Partial` |

### Sponge and coral

| Feature | Target vanilla blocks | Horizontal Slab | Vertical Slab | DoubleSlab Positive | DoubleSlab Negative | DoubleVertical Positive | DoubleVertical Negative | Current implementation | Target behavior | Minecraft constraint | Planned approximation | Required automated tests | Required manual tests | Status |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Sponge absorption and wet conversion | Sponge; wet sponge | None | None | None | None | None | None | Sponge variants are ordinary Slab/VerticalSlab blocks with copied settings. | A dry sponge component with exposed water contact performs a vanilla-bounded search and becomes wet independently. Both components are evaluated symmetrically from the unchanged pre-transaction snapshot; if both qualify, both become wet. | Fluid removal is cell-wide, and the two reachable-water sets may overlap. Mutating one slot before testing the other would make the result order-dependent. | Compute both qualification results and bounded reachable-water sets from one snapshot. Remove the union so each water position is mutated once, then wet every qualifying component in one transaction. Overlap does not prevent either qualifying sponge from becoming wet. | Vanilla radius/count limits per component; overlapping and disjoint water sets; both/one/neither qualifies; flowing/source/waterlogged targets; slot swap/axes; one transaction notification; per-component wet effects once; partner/save preservation. | Compare absorption reach, sound/particles, ocean edge cases, and nearby waterlogged blocks with vanilla; repeat after swapping slots to prove symmetry. | `Not started` |
| Wet sponge drying | Wet sponge; sponge | None | None | None | None | None | None | No source-equivalent placement/update behavior is present. | In an ultrawarm dimension, only the placed or exposed wet-sponge component dries with vanilla sound/particles; its partner is preserved. Non-ultrawarm drying behavior remains vanilla-equivalent. | Dimension checks and particles are cell-position based. | Select the newly placed/replaced component; emit one cell event per transaction and replace only that component. | All six shape/slot cases in ultrawarm and normal dimensions; wet+wet and wet+other pairs; reload. | Verify Nether feedback and that the other half neither dries nor changes visually. | `Not started` |
| Living coral survival and death | Five living coral blocks and matching dead variants | None | None | None | None | None | None | Living and dead variants are ordinary shape classes; no wet check or scheduled death exists. | Each living coral component remains alive when its own exposed geometry has valid water contact and otherwise schedules conversion to its matching dead component. | A Double cannot be waterlogged per side, and adjacent water is represented for a whole cell. Scheduled ticks do not identify a slot. | Project cell water contact onto exposed faces; on the scheduled tick recompute both components and replace only dry living coral. | Waterlogged singles; adjacent water on every face; partner shielding; water removed/restored before tick; five colors; slots/axes; reload. | Observe delayed death timing and texture transition in mixed live/dead Doubles. | `Not started` |

### Fire

| Feature | Target vanilla blocks | Horizontal Slab | Vertical Slab | DoubleSlab Positive | DoubleSlab Negative | DoubleVertical Positive | DoubleVertical Negative | Current implementation | Target behavior | Minecraft constraint | Planned approximation | Required automated tests | Required manual tests | Status |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Ignition and flammability classification | Planks, logs/wood, leaves, wool, dried kelp, hay, and every other mapped flammable; crimson/warped and petrified exceptions | Vanilla or Settings; verify | Settings; verify | None | None | None | None | Many registrations use burnable settings; there is no general source-block/component flammability lookup. | Each component uses its mapped vanilla source's ignition and burn chance. Nonflammable partners and fireproof wood families remain unaffected. | Vanilla fire APIs query one block at a cell face and do not expose a component hit. | Resolve components through fire-to-cell face projection and delegate rates to a central mapped-source lookup. Evaluate every contacted component independently. | Flammability matrix against source blocks; every face/slot/axis; mixed flammable/nonflammable and crimson/overworld pairs; lava ignition. | Ignite representative wood, leaf, wool, hay, dried kelp, crimson, and petrified pairs from every face. | `Partial` |
| Burning, fire spread, and component consumption | Same as ignition targets | Vanilla or Settings; verify | Settings; verify | None | None | None | None | No component-local burn-out/singleization path exists. | Successful burn-out removes only that component and singleizes the partner. If one fire contacts both, each receives an independent random decision calculated from one pre-transaction snapshot; two successes resolve atomically. | Fire state and update order are cell-wide; fire may replace the same cell that contains the remaining component. | Candidate only: either evaluate vanilla fire survival after both removals, leave air, or leave fire. Final state and random evaluation order must come from the canonical owner decision; no candidate is implementation-ready yet. | One/both/neither side burns; every candidate's deterministic same-tick result; fire spread through mixed grids; per-component loot/effects exactly once; transaction notifications once; chunk unload; no duplicate updates. | Long-running burn pens for representative rates; verify remaining shape, orientation, and visual fire contact after the selected decision. | `Blocked by design decision` |

### Falling blocks and concrete powder

| Feature | Target vanilla blocks | Horizontal Slab | Vertical Slab | DoubleSlab Positive | DoubleSlab Negative | DoubleVertical Positive | DoubleVertical Negative | Current implementation | Target behavior | Minecraft constraint | Planned approximation | Required automated tests | Required manual tests | Status |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Sand, red sand, and gravel falling | Sand, red sand, gravel | None | None | None | None | None | None | These are ordinary shape classes and do not use falling-block behavior. | An unsupported gravity component falls alone. Upper horizontal gravity is supported by its lower partner; lower gravity checks below. Vertical gravity checks support under its own projected half. The partner singleizes in place. | `FallingBlockEntity` normally carries one block state and lands into one cell. Multiple falling halves may cross chunk/unload boundaries. | Candidate only: a minimal namespaced payload on the vanilla falling entity or a dedicated falling-component entity must carry shape, slot geometry, persisted state, and drop policy. The owner guide is authoritative; landing-as-single/recombination requirements apply after the mechanism is chosen. | Support matrix (air, full block, each Slabee projection); all slots/axes; both payload candidates; landing single/combine; two falling halves; obstruction; per-component item/drop once; chunk save/unload and world-height/void cases. | Build the wood/sand examples from the design, observe falling render/orientation, landing combinations, particles, and sounds after the mechanism decision. | `Blocked by design decision` |
| Concrete powder falling | All 16 concrete powders | None | None | None | None | None | None | Powder variants are ordinary shape classes. | Use the selected component falling pipeline as sand while retaining color and persisted geometry. | Falling powder may harden before, during, or at landing through cell-wide water contact. | Candidate only until the falling payload decision: the selected payload performs the concrete water-contact check before placement and converts only its carried component identity. | Reuse gravity matrix for all representative colors and both payload candidates; water crossed in flight; water at landing; mixed partner; payload save. | Drop powder beside/through water in each orientation and inspect color, shape, and partner after the mechanism decision. | `Blocked by design decision` |
| Concrete powder hardening | All 16 powders to matching concrete | None | None | None | None | None | None | No shape-aware hardening exists. **Verified vanilla fact:** Minecraft 1.21.1 hardens powder when its current `FluidState` is water-tagged or qualifying water is found on a non-`DOWN` side. Water only below dry powder is excluded. A water-bearing neighbor is also rejected when its face toward the powder is a solid full square. Landing performs the hardening check through the falling path. | A powder component hardens to matching concrete only under those vanilla conditions, without changing its partner or slot. Below-only water does not harden stationary powder; valid upper/side or current-cell water does. Falling into water is handled by the falling/landing pipeline. | Fluid and neighbor states are cell-wide, while exposure and solid-face overlap can differ per component. A Double cannot contain half-cell water. | Project only non-`DOWN` neighboring contacts onto the component and retain vanilla's neighbor-side `isSideSolidFullSquare` exclusion. Use the current-cell water path for waterlogged singles. Use the selected falling payload's landing check for water entered during descent or at landing. Replace only the qualifying powder component. | For horizontal and vertical singles and every Double slot/axis: water below only (no in-place hardening), each horizontal side, above, source/flowing water, waterlogged current cell, water behind a solid full-square face, exposed versus partner-shielded contact, and falling into/landing in water; color mapping, partner preservation, race handling, and save/reload. | Compare stationary below/side/top arrangements and waterlogged singles with vanilla controls; drop every shape into water, inspect landing result, and verify mixed partners never harden from excluded or shielded contact. | `Not started` |

### Copper, logs, and leaves

| Feature | Target vanilla blocks | Horizontal Slab | Vertical Slab | DoubleSlab Positive | DoubleSlab Negative | DoubleVertical Positive | DoubleVertical Negative | Current implementation | Target behavior | Minecraft constraint | Planned approximation | Required automated tests | Required manual tests | Status |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Copper oxidation | Cut copper, exposed, weathered, oxidized Slabs | Vanilla | None | None | None | None | None | Horizontal singles reuse vanilla copper slabs; vertical variants are ordinary VerticalSlabs, and Doubles do not random-tick components. | Each unwaxed component advances using vanilla oxidation rules and source-stage mapping. Waxed partners never advance. | Vanilla oxidation samples blocks and age relationships by cell, while two copper ages may share one cell. | Run vanilla-frequency random ticks on eligible containers and evaluate each copper component from a snapshot; commit both results atomically. Count nearby copper per cell with a documented component-aware weighting. | Every stage/shape/slot/axis; waxed mix; nearby age influence; seeded simultaneous transitions; chunk reload and no idle BE tick. | Weather controlled copper arrays and compare rate/order with vanilla within an agreed tolerance. | `Not started` |
| Copper waxing | All four unwaxed cut-copper stages; honeycomb item | Vanilla; verify | None | None | None | None | None | Vanilla horizontal item mapping may work for singles; no component-local mapping exists. | Honeycomb waxes only the hit component, retaining oxidation stage, slot, partner, and valid state; sound, particles, advancement, and item consumption occur once. | Item use selects one cell state, and vanilla block maps do not contain Double containers. | Resolve hit component, map its source stage to the matching waxed shape, then use component replacement. | Every stage/shape/slot/axis; off-center and center hits; creative/survival item use; event/advancement once; partner unchanged. | Wax mixed-stage Doubles from each visible face and inspect particles/sound. | `Partial` |
| Copper axe scraping and deoxidation | Waxed and oxidized cut-copper stages; axe | Vanilla; verify | None | None | None | None | None | No vertical/Double component operation exists. | Axe first removes wax or steps oxidation back exactly as vanilla for only the hit component, with correct durability and feedback. | One cell can expose both copper components to the same clicked face. | Select by ray-hit projection; reject ambiguous internal faces; delegate transition mapping and effects to one component transaction. | All wax/stage transitions; every exposed face/slot/axis; durability, events, advancement, and partner preservation. | Scrape mixed wax/oxidation pairs and compare sequence and feedback with vanilla. | `Partial` |
| Log, wood, stem, hypha, and bamboo stripping | All mapped unstripped pillar families | None | None | None | None | None | None | Custom shape blocks copy settings but do not expose a component-local axe strip path. Their current fixed grain direction is intentional. | Strip only the hit component to its matching stripped variant while retaining slot and the existing fixed texture/model direction; invalid/non-strippable partners remain unchanged. Full log `AXIS` support is outside beta.4. | Item callbacks see the container, and corresponding stripped mappings must exist for every supported fixed-orientation shape. | Select by hit projection and replace through the vanilla strip mapping adapted to mapped shapes. Do not add or persist log `AXIS`. | Every wood/stem/hypha/bamboo family; all component slots and Double axes; already stripped/no mapping; durability/effects/loot exactly once; fixed direction before/after save/reload. | Strip mixed species and crimson/warped pairs from each exposed face; verify the intended fixed end/side textures. | `Not started` |
| Leaf distance and persistent state | All ten leaf variants | None | None | None | None | None | None | Leaf shapes are ordinary blocks without `DISTANCE` or `PERSISTENT`; Doubles persist only IDs. | Each leaf component stores and updates vanilla-equivalent distance and player-placed persistence independently. | Neighbor updates target cells, and a log/leaf component may overlap only half the supporting projection. | Persist both properties. Recompute distance through component contact graphs, treating full vanilla logs cell-wide and Slabee logs by projected contact. | Natural/player-placed leaves; distance 1-7; mixed components; X/Z paths; log removal/addition; save migration and reload. | Build asymmetric half-log canopies and inspect debug states and stability. | `Not started` |
| Leaf decay and component removal | All ten leaf variants | None | None | None | None | None | None | No random decay behavior is present. | A nonpersistent component at terminal distance decays independently and singleizes its partner. Whether it emits loot is delegated to the separate required leaf-loot contract; lifecycle eligibility does not define loot probabilities. | Random ticks are cell-based, and two leaf components may be independently eligible in one cell. | Evaluate decay eligibility for both components from one snapshot at vanilla frequency, then commit qualifying removals atomically through their natural-decay `MutationCause`. Do not embed a second loot algorithm in the lifecycle implementation. | One/both components decay; persistent mix; every slot/axis; atomic removal; exact natural-decay cause forwarded to leaf loot; no duplicate invocation; save/unload. | Observe a mixed canopy decay and verify timing, remaining geometry, particles, and exactly one leaf-loot invocation per removed component. | `Not started` |
| Leaf loot parity | All ten leaf variants and their Slabee horizontal/vertical items | Generated tables; incomplete parity | Generated tables; incomplete parity | Partial component break | Partial component break | Partial component break | Partial component break | Slabee has generated leaf-shape loot tables and component break handling, but source-species sapling/stick/apple conditions, Fortune scaling, natural-decay routing, and half-component expected values are not established. | **Scope: Required. Readiness: Blocked by leaf-loot Decision Record.** Shears or Silk Touch drops exactly one matching leaf Slab item for the removed component. Ordinary breaking and natural decay retain the mapped vanilla species' sapling, stick, apple, and other loot conditions plus Fortune influence. One component targets half the vanilla full-leaf expected value; two matching components together target one vanilla block's expected value. Mixed Doubles evaluate only the removed component. Creative and no-loot mutation causes drop nothing. | Vanilla loot tables evaluate one full block at one position. Halving probabilistic expectations while retaining Fortune and species conditions needs an explicit probability transform; two components can be removed in one transaction but must not duplicate the loot context. | Before implementation, approve a leaf-loot Decision Record covering Fortune probability math, rounding, statistical tolerances, and independent-per-component versus combined evaluation. Implement only the recorded algorithm through the mapped vanilla source context and `MutationCause`; do not couple it to distance/decay feasibility. | Exact one matching Slab item with shears and Silk Touch for every species/shape/slot/axis; zero for creative/no-loot causes; normal break and natural decay for saplings/sticks/apples and species-specific conditions; Fortune levels; seeded/statistical half expectation for one component and full expectation for two matching components; mixed partner isolation; simultaneous removal; no duplicate loot. | Break and decay representative oak, non-apple, azalea, and mangrove leaf components with bare hand, shears, Silk Touch, and Fortune; count large samples using the Decision Record tolerances and inspect mixed Doubles. | `Blocked by design decision` — readiness: `Blocked by leaf-loot Decision Record` |

### Ice, magma, soul sand, and bubbles

| Feature | Target vanilla blocks | Horizontal Slab | Vertical Slab | DoubleSlab Positive | DoubleSlab Negative | DoubleVertical Positive | DoubleVertical Negative | Current implementation | Target behavior | Minecraft constraint | Planned approximation | Required automated tests | Required manual tests | Status |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Ice slipperiness | Ice, packed ice, blue ice | Settings; verify | Settings; verify | Partial | Incorrect influence possible | Partial | Partial | Singles copy slipperiness. Horizontal Double uses the maximum of both components, so a buried negative ice component can affect the top surface. Vertical selection uses entity position. | Slipperiness comes only from the component under the entity's support footprint. Horizontal Double normally exposes positive on top; vertical halves select by footprint. | Vanilla movement samples one block below an entity that can overlap both halves. | Sample support projection under the entity; for multi-half overlap choose the maximum only among actually contacted components, matching vanilla multi-block footing. | Entity positions over center/edges; all ice variants and partner swaps; boats and living/items where relevant; horizontal buried ice regression. | Walk, sprint, jump, and boat across striped ice/non-ice Slabee tracks and compare with vanilla. | `Partial` |
| Ice melting | Ice (not packed or blue ice) | None | None | None | None | None | None | No melt behavior exists. | Each exposed normal-ice component uses vanilla light/random-tick melt rules and only that component is removed. The cell-wide melt-water result is unresolved. | Vanilla melting may replace the whole cell with water; a partner can remain in that cell, and Double water history is not restored. | Candidate only: waterlog a compatible remaining single, delay water until both components disappear, or use another deterministic cell-wide rule. The canonical owner answer must be recorded before implementation. | Light threshold; dimensions; every candidate source/flowing result; every slot/axis; partner waterloggable/nonwaterloggable; packed/blue non-melt; reload. | Compare candidate results in controlled mixed-ice setups, then validate the selected rule's flow and partner state. | `Blocked by design decision` |
| Magma hot-floor damage | Magma block | Implemented; verify | Implemented; verify | Partial | N/A covered | Partial | Partial | Singles damage living entities. Horizontal Double checks positive; vertical Double selects a side from entity position. | Damage applies only while the entity contacts the magma component's exposed support surface, with vanilla exemptions. | Entity boxes can span both components and vanilla invokes one block callback. | Resolve contacted top-face regions and apply at most one vanilla-strength damage event per cell callback. | Living/nonliving; crouching, frost walker, immunity/bypass; edge/center positions; all slots/axes; no double damage. | Walk and crouch across mixed magma tracks; verify damage cadence and particles/sounds. | `Needs verification` |
| Magma bubble source | Magma block; water/bubble column | Partial | Partial | Partial | None | Partial whole-column | Partial whole-column | Scheduled creation exists, but bubble state and force are whole-cell; mixed vertical sources are not split. | Horizontal exposed magma produces vanilla downward force. Vertical magma affects only entities in its half-column at vanilla strength. | Bubble-column `DRAG` is one boolean for the whole water cell. | Candidate framework: keep a cell-wide visual column and derive force from the source component under the entity. Center-plane selection and mixed soul/magma visual `DRAG` remain owner decisions. | Source creation/removal; water column propagation; each slot/axis; all center candidates; mixed visual candidates; boats/items/living entities; chunk reload. | Observe bubble visuals and measure descent on both halves and the center after the owner decisions. | `Blocked by design decision` |
| Soul-sand slowdown and collision | Soul sand | Implemented; verify | Implemented; verify | Partial | N/A covered | Partial | Partial | Singles use reduced collision and velocity. Double movement selects positive for horizontal and entity side for vertical. | Slowdown and lowered support surface apply only to the contacted soul-sand component. | Wide entities can contact both halves; collision is combined for one block. | Compose collision from both components and derive velocity from actual support contacts, using the strongest contacted slowdown when spanning both. | Collision heights; entity positions/widths; all slots/axes; mixed path/soul pairs; boats/items/living. | Walk and jump across seams and compare speed/height with vanilla soul sand. | `Needs verification` |
| Soul Speed | Soul sand and soul soil; enchantment | Partial | Partial | Partial | Partial | Broken data candidate | Broken data candidate | Custom data includes Slabee sources, but DoubleVertical conditions repeatedly reference `slabee:double_slab_vertical_block` rather than the registered container ID. | Soul Speed activates only over a contacted soul-sand/soul-soil component and uses vanilla level effects and durability chance. | Enchantment effect conditions operate on cell block/NBT and may not express entity-position component selection reliably. | Prefer a tested component-aware predicate/injection with the smallest data override; fix IDs only as part of the feature PR. | Levels 1-3; soul sand/soil; every slot/axis and center; mixed partner; speed, particles, and boot durability. | Run timed tracks for singles and mixed Doubles and compare with vanilla. | `Partial` |
| Soul-sand bubble source | Soul sand; water/bubble column | Partial | Partial | Partial | None | Partial whole-column | Partial whole-column | Bubble creation recognizes singles and Doubles, but a vertical source affects the full column at full strength; soul wins over magma for a mixed pair. | Horizontal exposed soul sand produces vanilla upward force. Vertical soul sand affects only its half-column at vanilla strength. Mixed soul/magma chooses force by entity position away from the exact center. | Bubble-column state is cell-wide and cannot encode two force directions. | Candidate framework: one visual bubble state plus component-derived force. The exact center tie-breaker and mixed visual `DRAG` are unresolved in the canonical owner guide. | Upward force/caps for entities and boats; every slot/axis; all center and visual candidates; source changes under an existing column; reload. | Measure ascent/descent separately over both halves and the center; inspect each candidate's visual artifacts. | `Blocked by design decision` |

### Mud and other required source behavior

| Feature | Target vanilla blocks | Horizontal Slab | Vertical Slab | DoubleSlab Positive | DoubleSlab Negative | DoubleVertical Positive | DoubleVertical Negative | Current implementation | Target behavior | Minecraft constraint | Planned approximation | Required automated tests | Required manual tests | Status |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Mud height, collision, and entity contact | Mud | Settings only | Settings only | None | None | None | None | Mud uses ordinary Slab/VerticalSlab geometry; snow placement has a mud special case. | Each mud component exposes vanilla-relative lowered collision/support geometry, composes correctly in Doubles, and affects entities only on its footprint. | Vanilla mud is a lowered full-cell surface; half shapes need a consistent scaled/offset interpretation. | Apply the source height reduction to the component's exposed top, union partner geometry, and use projected entity support. | Collision/outline/support for all orientations; path/soul/mud mixed pairs; snow placement; entities of several widths; suffocation/spawn checks. | Walk, jump, place snow, and inspect selection boxes over mixed mud pairs. | `Not started` |
| Mycelium and podzol spread/survival/snow | Mycelium, podzol, dirt | None | None | None | None | None | None | Both are ordinary shape blocks. Only grass subclasses own current spread/snow code. | Reproduce source-specific survival, spread eligibility, and snow appearance per exposed component; do not incorrectly grant grass-only bone meal behavior. | Random ticks and snowy state are cell-level callbacks. | Reuse the grass component scheduler and environment-state calculation with source-specific strategy rules. | Light/fluid/snow; dirt targets; grass/mycelium competition; all slots/axes; deterministic random ticks; reload. | Observe mixed biome/soil plots and confirm textures and spread boundaries. | `Not started` |
| Nylium decay and bone meal | Crimson nylium, warped nylium, netherrack | None | None | None | None | None | None | Nylium variants are ordinary shape blocks. | Each exposed nylium component decays to matching netherrack under vanilla invalid-cover conditions and grows the correct Nether vegetation when fertilized. | Vegetation and features occupy whole cells; dimension/registry feature lookup is cell-based. | Component-local decay; selected-component bone meal with cell-wide feature placement and destination de-duplication. | Both variants; cover/light conditions; all slots/axes; bone meal feature selection; blocked destinations; partner/reload. | Compare crimson/warped results in Nether test plots and mixed Doubles. | `Not started` |
| Moss and rooted-dirt bone meal | Moss block, rooted dirt | None | None | None | None | None | None | Both are ordinary shape blocks. | Selected moss uses vanilla-style spreading/vegetation; selected rooted dirt produces hanging roots only when its required exposed face and destination are valid. | Generated blocks occupy cells, and a destination can be shared by both components. | Use hit-selected source and exposed-face projection; perform vanilla whole-cell destination placement once. | Valid/blocked faces; every slot/axis; moss conversion radius; rooted dirt below target; consumption/events; partner preservation. | Compare feature density and hanging-root placement with vanilla. | `Not started` |

## Additional mapped-block parity checklist

These rows come from the current 265 mappings and complement the core inventory
above. Their beta.4 disposition follows the scope classification and the owner
guide. Small static-setting corrections may
join the closest feature PR; any behavior needing substantial new machinery must
receive its own PR or an explicit scope decision.

For owner-scope-pending Q8 rows, the `Status` column records baseline implementation
state only. It does not authorize beta.4 work before the owner classifies that row.

| Feature | Target vanilla blocks | Horizontal Slab | Vertical Slab | DoubleSlab Positive | DoubleSlab Negative | DoubleVertical Positive | DoubleVertical Negative | Current implementation | Target behavior | Minecraft constraint | Planned approximation | Required automated tests | Required manual tests | Status |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Hoe, water-bottle, and mud-to-clay transformations | Grass/dirt/coarse/rooted dirt; mud; clay | None or vanilla-dependent | None | None | None | None | None | Shovel paths are customized; no general component transform covers other tools/environmental conversions. | Every supported vanilla source transformation changes only the targeted component and retains valid component state. | Some item behaviors and dripstone conversion inspect whole cells and full support chains. | Add transformations to the component replacement registry after confirming exact 1.21.1 rules; use hit or projected support to select a component. | Every source/result mapping; invalid faces; item durability/consumption; dripstone chain; all slots/axes; partner preservation. | Exercise hoes, water bottles, and dripstone over mixed soil/mud pairs; compare feedback with vanilla. | `Not started` |
| Soul-fire base behavior | Soul sand and soul soil families | Partial tags/settings | Partial tags/settings | None | None | None | None | Soul sand has custom mechanics; component-local Soul Fire placement was not found. | Owner-approved: a horizontal single uses its exposed top; a horizontal Double uses only the positive upper component; vertical singles/Doubles use the contacted component. When one fire cell contacts normal and Soul components, Soul Fire wins. The partner is unchanged and vanilla ignition/extinguishing rules are retained where possible. | Fire type is one state for the whole fire cell, even when it contacts two different components. | Project the fire contact onto components and apply the approved Soul-priority rule. Do not infer eligibility from a covered horizontal negative component. | Soul sand and soul soil; every shape/slot/axis/contact face; mixed Soul/normal contact with Soul priority; covered negative regression; portal/lava/player ignition; extinguishing; transaction notifications once; partner unchanged. | Ignite mixed bases from several positions and inspect type, placement, persistence, and extinguishing against vanilla. | `Not started` |
| Pumpkin carving | Pumpkin | None | None | None | None | None | None | Pumpkin slabs are ordinary shape blocks; carved slab registrations are commented out. | Shears carve only the hit pumpkin component, preserve its shape, set a deterministic horizontal facing, damage the tool, and drop seeds once. | The matching carved Slab/VerticalSlab result is not currently registered; facing may need persisted component state. | Decide whether to restore the existing commented result family or document pumpkin carving as out of scope before implementation. | Every face/slot/axis; direction; seed drop; shears durability; mixed partner; save/reload. | Carve each orientation and inspect face texture, particles, sound, and drops. | `Blocked by design decision` |
| Huge mushroom directional faces and textures | Brown mushroom block, red mushroom block, mushroom stem | None | None | None | None | None | None | Mapped shapes use ordinary Slab states and do not preserve vanilla's six directional booleans. Current assets use fixed generated models. | Owner-approved beta.4 scope: use Minecraft 1.21.1 behavior for all six face states and outer, inner, and stem textures during generation, placement, neighbor changes, breaking, component removal/singleization, and save/reload. | A half-block adds cut/exposed regions not directly represented by a full-block model; existing Slabee UV extraction may or may not represent every newly exposed face. | First perform an asset/UV feasibility review. Reuse vanilla textures and current UV extraction if sufficient. If new assets are required, stop and report to the owner; do not create placeholder textures. The gate only permits implementation to start: it does not change status. Then implement BlockState, per-component persistence, models/UVs, and vanilla neighbor behavior before validation. | Vanilla default and generated face combinations; all six directions; horizontal and vertical singles; every DoubleSlab and DoubleVertical slot/axis; partner removal exposing a new face; placement/generation/neighbor update/break; legacy migration; save/reload; client synchronization; model/UV rendering snapshots. | Generate and place all three block types, remove neighbors and partners, inspect outer/inner/stem/cut faces, reload chunks, verify client rendering, and compare with vanilla 1.21.1. The row becomes `Complete` only after implementation plus every automated and manual check succeeds. | `Not started` |
| Mangrove-root waterlogging | Mangrove roots | Partial through base shape | Partial through base shape | No historical state | No historical state | No historical state | No historical state | Base Slab classes expose normal waterlogging, but source-specific root behavior and Double transitions are unverified. | Singles follow normal Slab waterlogging. Doubles hold no per-component water history, and combining expels water according to the virtual-component fluid policy. | Only one cell `FluidState` exists. | Use the common waterlogging contract and GameTest-fixed combine/remove results; do not restore expelled water. | Placement in source/flowing water; combine and singleize; neighbor fluid ticks; every slot/axis; save/reload. | Inspect water flow, sounds, and rendering around root slabs and mixed Doubles. | `Needs verification` |
| Fixed log texture orientation; non-log pillar and hay audit | Logs/wood/stems/hyphae, basalt, purpur/quartz pillars, bone block, hay bale | Fixed/default orientation | Fixed/default orientation | Fixed/default orientation | Fixed/default orientation | Fixed/default orientation | Fixed/default orientation | Slabee pillar-like shapes generally use fixed/default texture orientation; source `AXIS` is not persisted. Hay fall reduction was not found. | Owner-approved only for log families: retain the intentional fixed grain direction, and preserve it during component-local stripping. Full log `AXIS` support is future work. Non-log pillar orientation parity and hay fall reduction remain owner-scope-pending. | Selectable log grain direction needs cut-face assets, a new state, and model variants; other pillars require their own parity review; fall callbacks are cell-based. | Do not add log `AXIS` in beta.4. Record its asset/state/model work for a future proposal. Implement non-log pillar or hay behavior only if the owner approves those separate Q8 items. | Fixed log direction through placement/combine/strip/singleize/save; no accidental log `AXIS` migration; future-work asset note; non-log pillar/hay tests only after scope decisions. | Inspect fixed log end-grain/side textures before and after stripping; evaluate other pillars and hay only if approved. | `Blocked by design decision` |
| Wool vibration occlusion | All 16 wool colors | Settings/tags; verify | Settings/tags; verify | None | None | None | None | Wool shape settings exist, but component-aware vibration paths were not found. | A vibration path intersecting a wool component is occluded according to vanilla, without treating an uncovered half as wool. | Vibration occlusion checks block states and rays through cells, not virtual components. | Intersect the vibration segment with component geometry before delegating the wool result. | Rays through wool half, empty half, boundary, both axes, mixed partner, and full wool; source/listener combinations. | Use calibrated sculk sensors on both sides of mixed wool Doubles and compare detection. | `Not started` |
| Special non-leaf loot, experience, and guarded interaction | Gravel, glass/tinted glass, glowstone, melon, gilded blackstone, sculk, and other non-leaf non-self-dropping sources | Generated loot; verify | Generated loot; verify | Partial component break | Partial component break | Partial component break | Partial component break | Slabee has generated loot tables and component break handling, but source-specific fortune/silk/experience and guarded behavior need a parity audit. Leaf loot is excluded from this owner-scope-pending row and tracked as a required core behavior. | If approved for beta.4, breaking one affected component produces its documented component-scale loot/XP and triggers source interaction hooks once; the partner remains. This row cannot change the already-required leaf-loot contract. | Vanilla loot and piglin anger receive a container state rather than the mapped component source. | After the Q8 scope decision, build the loot context from the mapped source plus component state, scale only where Slabee's crafting economy requires it, and explicitly forward guarded events. | Approved families only: silk/fortune levels and no-tool cases; XP; explosions; creative; each slot/axis; piglin anger; no duplicate drops. Leaf cases remain in the core leaf-loot tests. | For approved families, mine representative blocks with tools/enchants near piglins and count drops/XP against documented expectations. | `Partial` |
| Sculk safety near a catalyst; full catalyst behavior deferred | Sculk (the baseline has no Slabee Sculk Catalyst mapping) | Ordinary block; verify | Ordinary block; verify | Storage; verify | Storage; verify | Storage; verify | Storage; verify | The current mapping includes `SCULK` Slabs, not `SCULK_CATALYST`; there is no Slabee catalyst Block Entity or component spread contract. | Owner-approved beta.4 scope is stability only: placement, breaking, save/reload, and nearby mob deaths must not crash, lose block entities, duplicate experience, or corrupt the world. Full propagation, charge, bloom, and component conversion are future work. | Vanilla catalyst events and experience handling are cell/Block-Entity based even though the mapped Slabee target has no catalyst Block Entity. | Do not implement propagation/bloom parity. Test current mapped Sculk components around a vanilla catalyst and guard only verified crash, corruption, loss, or duplication paths. | Every shape/slot/axis placement and break; save/reload; mob death near vanilla catalyst; no crash, orphan/lost Block Entity, duplicate XP, component loss, or region corruption. | Repeat mob deaths and reloads around mixed Sculk Doubles; inspect XP counts, logs, and world integrity. | `Needs verification` |
| Light, particles, transparency, and static material parity | Glowstone, crying obsidian, glass/tinted glass, amethyst, static mapped families | Partial/settings | Partial/settings | Partial derived | Partial derived | Partial derived | Partial derived | Luminance and advanced glass culling are implemented; crying-obsidian particles and a complete source-settings audit are not. | Derived Double light uses an explicit documented rule; transparency and occlusion remain geometry-aware; source particles appear from the correct component surface; static settings/tags/loot match sources. | One cell has one light level and particle callbacks receive one container state. | Keep cell luminance as the maximum component luminance; select particle source surface geometrically; validate all static families through generated parity tests. | Mixed luminance pairs; skylight/tinted glass; culling seams; particles by face; exhaustive settings/tag snapshot for all 265 mappings. | Inspect mixed glowing/glass builds with and without shaders, and watch crying-obsidian particles at every orientation. | `Partial` |

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
spike and approve a versioned Decision Record covering:

- Minecraft's standard BlockState NBT serialization as the first candidate,
  including verified registry lookup, invalid-data, and round-trip behavior for the
  exact API selected by the spike;
- validation that every decoded block belongs to the known Slabee `BlockTriple`
  mapping and expected shape family;
- a filter that writes only component-owned properties, never treating slot-derived
  `TYPE`/`FACING` or environment-derived `SNOWY` as authoritative;
- explicit container persistence for DoubleVertical `AXIS`;
- legacy block-ID NBT compatibility and safe per-component fallback for unknown
  blocks, disallowed/unknown properties, and invalid values; and
- the new schema, diagnostics, fixtures, and ownership rules that Stage 6 will
  implement completely.

Stages 4 through 13 may prepare unrelated tests or scaffolding, but no
persistence-dependent implementation in those stages may begin until this
Decision Record is accepted. The spike is a gate, not the Stage 6 implementation.

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
- Run the persistence API spike and approve the Decision Record defined by the
  persistence design gate. Prefer Minecraft 1.21.1's standard BlockState NBT path,
  but verify the exact API and failure behavior before committing Stage 6 to it.
- Approve and version the performance procedure: hardware/runtime controls,
  fixtures, warm-up, repetitions, statistics, and explicit pass/fail thresholds.
  Later feature PRs and release gates must cite this procedure and threshold set.
- Exit when tests run locally and in CI, failures preserve useful server logs,
  there is no dependency on a pre-existing world, and the persistence Decision
  Record is accepted and versioned.

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
  state such as `SNOWY`, and persist container `AXIS` explicitly.
- Read the current unversioned `positive_slab`, `negative_slab`, legacy horizontal
  `facing`, and vertical `axis` layout without requiring a prior resave.
- Make outer `BlockState.AXIS` authoritative for loaded beta.4 DoubleVertical
  containers. Use valid legacy block-entity `axis` only to initialize/repair that
  state during unversioned migration; diagnose versioned duplicates that disagree.
- Apply the documented safe per-component fallback for unknown block IDs, blocks
  outside the expected mapping, unknown/disallowed properties, and invalid values;
  never discard a valid partner because the other component is malformed.
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
  hoe-to-Dirt plus Hanging Roots behavior under its existing owner-scope decision.
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

- Resolve the canonical owner choices for ice-melt fluid results, bubble center
  selection, and mixed visual `DRAG` before implementing those paths.
- Correct projected slipperiness and complete ice melting only after its fluid
  result is approved.
- Consolidate magma damage, soul-sand collision/slowdown, and Soul Speed selection.
- Separate bubble visuals from entity force so vertical halves can apply normal
  strength independently and mixed soul/magma can split by entity position.
- Exit when source changes propagate through existing columns, center behavior is
  documented, and all affected checklist rows are `Complete`.

### 10. Combustion

- Resolve the canonical simultaneous-burn final-state and evaluation-order question
  before implementing that path.
- Add mapped-source flammability, projected fire contact, independent random burn
  decisions, component removal, and deterministic simultaneous burn-out.
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
  distance/persistent state, projected support graph updates, random decay, and
  flammability without embedding loot probability rules in the lifecycle code.
- Before leaf-loot implementation, approve a Decision Record for Fortune-aware
  probability scaling, rounding, statistical tolerances, and independent versus
  combined evaluation. This record blocks readiness only; leaf-loot scope is
  already beta.4-required.
- Implement exact shears/Silk Touch one-Slab drops, source-species normal/decay
  loot conditions, Fortune, half expected value per component, one vanilla-block
  expected value for two matching components, mixed-component isolation, and
  no-drop mutation causes through the common loot/mutation path.
- Cover all ten leaf variants and natural versus player-placed components.
- Exit only when the separate lifecycle and leaf-loot checklist rows are both
  `Complete`: asymmetric canopies update and decay across chunk save/load without
  broad per-tick scans, and the Decision Record's exact and statistical loot tests
  pass for every relevant tool, cause, species, shape, slot, and axis.

### 13. Falling blocks

- Resolve and record the canonical falling-payload decision before choosing an
  entity or NBT representation.
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

- Resolve every remaining additional parity row or record an owner-approved scope
  disposition.
- Audit the actual Minecraft 1.21.1 piston behavior selected by every current
  Single mapping and both Double containers, then run extension, sticky retraction,
  push-limit, blocked-destination, chunk save/reload, and client synchronization
  tests. Preserve the verified cell-level result; if a current container path is
  unsafe, explicitly refuse that movement rather than implementing component-only
  movement.
- Run the required Sculk safety checks around a vanilla catalyst without adding
  catalyst propagation/bloom parity.
- Run the full automated matrix, migration fixtures, clean-world and upgraded-world
  manual plans, dedicated server smoke test, and client smoke test.
- Compare performance using the versioned procedure and thresholds approved by the
  baseline PR; investigate only measured regressions or bottlenecks.
- Remove debug output, review warnings/deprecations, verify documentation and
  metadata, and produce release notes that explain intentional approximations.
- Exit only when every beta.4-required and beta.4-planned item is `Complete`, every
  owner-scope-pending item has a recorded disposition, no canonical release-blocking
  question remains, CI is green, and the owner approves publication. Publishing is
  a later, explicit operation and is not part of these implementation PRs.

## Performance baseline before feature code

Record the baseline on a fixed Java 21 runtime, fixed JVM arguments, fixed render
distance/simulation distance, and a committed test-world generator or command set.
Capture warm-up separately from measurements and retain raw logs with the PR or a
linked artifact.

| Scenario | Suggested fixture | Measurements |
| --- | --- | --- |
| Idle containers | Grids of 0, 1,000, and 10,000 heterogeneous Doubles in loaded chunks | Server MSPT distribution, block-entity tick time/count, client FPS/frame-time, allocations if available. |
| Random-tick behavior | Fixed-size grass/copper/leaf grids at a fixed random-tick speed | MSPT, callbacks per second, successful transitions, and work per transition. |
| Neighbor burst | Redstone- or command-driven updates beside a large mixed grid | Total update time, scheduled tick count, duplicate notifications, and maximum queue depth. |
| Bubble columns | Equal-height water columns over singles, horizontal Doubles, and vertical mixed sources | MSPT, entity update cost, force correctness, and client frame-time. |
| Combustion | Controlled flammable/nonflammable mixed grid with a fixed fire-tick configuration | MSPT, fire/component updates, removals per second, and neighbor-event count. |
| Falling blocks | Release a fixed grid of single and paired gravity components | Entity count, MSPT/frame-time, landing time, drops, and lost/duplicated components. |
| Save and reload | Same worlds before and after state persistence, with a forced save and restart | Region/chunk size, save duration, load duration, migration diagnostics, and state mismatches. |

The first baseline PR must not invent a universal percentage target before data
exists. It must use initial samples to approve a versioned procedure and explicit
scenario-level thresholds, including the statistical rule for distinguishing noise
from regression. The release gate cites that approved procedure/version and passes
its thresholds. It also requires no unbounded queue or scan. Any threshold change
or accepted feature cost must be reviewed and recorded beside its evidence rather
than being redefined at release time.

## Out of scope for beta.4

- Other Minecraft versions.
- NeoForge.
- New shapes such as stairs, fences, walls, or panes.
- A true half-cell fluid implementation.
- A large Block Entity or rendering-system redesign.
- Adding large numbers of vanilla blocks that Slabee does not currently map.
- Selectable log-family `AXIS` and new grain-direction model variants; existing
  fixed Slabee log direction remains intentional.
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
- every owner-scope-pending parity item has an answer recorded in the canonical
  owner guide, and every deferred item is accurately described as future work;
- every accepted approximation has an automated regression test and a manual test;
- huge-mushroom asset/UV feasibility has been followed by the required BlockState,
  component persistence, model/UV, neighbor, every-shape, newly-exposed-face,
  save/reload, client-sync, and rendering implementation and tests; the asset gate
  alone is not completion evidence;
- current-main and beta.3-era Double NBT fixtures load without component loss;
- normal and waterlogged single-component removal produces the documented
  `MutationResult`, final air/fluid state, cause-specific effects, and no residual
  Double container or block entity;
- the persistence Decision Record was accepted before dependent implementation,
  and Stage 6 independently passes new/legacy fixtures, malformed-data fallback,
  server/client packets, initial chunk data, dirty/listener checks, Double and
  Single retention, and chunk save/reload;
- combine, replace, remove, singleize, save/reload, and client synchronization pass
  for both container types and axes;
- piston safety is verified for Single Slabs, VerticalSlabs, DoubleSlabs, and both
  DoubleVertical axes with no component/Block Entity loss, duplication, NBT damage,
  or client/server mismatch; any unsafe container movement is explicitly refused,
  and component-only movement is not required;
- leaf loot remains classified as beta.4-required, its Decision Record is approved,
  and exact shears/Silk Touch/no-drop results plus Fortune-aware statistical tests
  pass for single components, two matching components, and mixed Doubles; lifecycle
  completion is assessed separately;
- no unconditional continuous tick was added to all Doubles, and the remaining
  existing block-entity ticker has been removed or justified with measurements;
- the integrated performance comparison uses and passes the versioned procedure
  and explicit thresholds approved by the baseline PR;
- no unanswered canonical question blocks a beta.4-required or beta.4-planned item;
- a clean client and dedicated server complete smoke validation on Minecraft
  1.21.1 Fabric with Java 21; and
- CI is green on the release candidate commit.

## Owner decision registry

The single canonical list of unresolved owner decisions, their candidate behaviors,
technical tradeoffs, recommendations, and answers is
[`beta4-owner-guide.ja.md`](beta4-owner-guide.ja.md). This roadmap does not duplicate
that list. A table row marked `Blocked by design decision` must link conceptually to
an unanswered entry there and must present alternatives as candidates only.
