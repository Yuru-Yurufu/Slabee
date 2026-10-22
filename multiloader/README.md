# Slabee multiloader structure spike

A partial 1.21.1 port using the MultiLoader Template pattern: vanilla Minecraft
in `common`, Fabric Loom in `fabric`, and ModDevGradle in `neoforge`. No
Architectury runtime dependency is used. Both loaders compile the same common
sources using Mojang's official names and package them in their own jar.

Run with Java 21 from this directory:

```sh
./gradlew build --console=plain
```

Artifacts are in `fabric/build/libs/` and `neoforge/build/libs/`. The Fabric
distributable is the remapped jar without the `-dev` or `-sources` suffix.
`common/build/libs/` contains a development artifact, not a separate mod to install.

This spike extracts/adapts three small pieces from baseline `594cbba2`:

- `ModBlocks`: purple/black concrete slab definitions and item registrations.
- `DoubleSlabBlockEntity`: horizontal legacy `positive_slab`/`negative_slab` NBT,
  IDs, facing fields, defaults, and the lower path-to-dirt normalization. The
  predicate for valid IDs is supplied by the Minecraft host. Six common tests cover
  the persisted contract. This is not the B2 schema migration.
- `BubbleColumnBlockInvoker`: the existing accessor ported to Mojang's
  `canExistIn` method, as a representative common Mixin.

The storage-only Double block hosts the shared persistence implementation.
Registration timing belongs to each loader: immediate Fabric registry calls versus
NeoForge `DeferredRegister`. Shared suppliers are resolved when the corresponding
registry value is available. Block-entity type builders also belong to the loader
adapters: the vanilla builder exposes a private factory type, so common passes a
plain Java factory instead of depending on loader access extensions.

The original Yarn project at the repository root remains intact and has its own
Gradle wrapper. It is not included by this nested build. None of its client models,
scene tooling, or existing tests are moved. This is an extraction port, not a
complete migration of the original source tree.

The spike jars deliberately retain the tested `slabee` IDs. They contain only this
slice: no full registration catalog, renderer, placement/half-breaking behavior,
vertical storage, recipes, or production client synchronization. They are not
release builds or replacements for Slabee in an existing world.

The build arrangement follows
[MultiLoader Template, 1.21.1 at 7bd74e0](https://github.com/jaredlll08/MultiLoader-Template/tree/7bd74e027936c3ecefb38a518c5d7ac71476d543)
(CC0). The wrapper comes from that revision; build scripts are reduced for this
slice. Dependency versions are pinned in the scripts/properties. ModDevGradle's
non-recompiling pipeline is enabled for the build experiment; source debugging and
IDE import still need a separate check.
