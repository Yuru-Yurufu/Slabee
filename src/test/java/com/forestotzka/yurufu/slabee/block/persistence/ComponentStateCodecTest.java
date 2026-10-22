package com.forestotzka.yurufu.slabee.block.persistence;

import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.Bootstrap;
import net.minecraft.SharedConstants;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.forestotzka.yurufu.slabee.block.persistence.ComponentStateCodec.ComponentFailure;
import static com.forestotzka.yurufu.slabee.block.persistence.ComponentStateCodec.ContainerComponents;
import static com.forestotzka.yurufu.slabee.block.persistence.ComponentStateCodec.InputFormat;
import static com.forestotzka.yurufu.slabee.block.persistence.ComponentStateCodec.RecoveryAction;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComponentStateCodecTest {
    static {
        SharedConstants.createGameVersion();
        Bootstrap.initialize();
    }

    private static final Identifier STATEFUL_FIXTURE_ID = Identifier.of("slabee_test", "stateful_slab");
    private static final StatefulFixtureSlabBlock STATEFUL_FIXTURE_BLOCK = new StatefulFixtureSlabBlock();

    private static ComponentStateAdapterRegistry adapters;
    private static ComponentStateCodec codec;
    private static final List<ComponentStateDiagnosticSink.Diagnostic> DIAGNOSTICS = new ArrayList<>();

    @BeforeAll
    static void initializeRegistryAndCodec() {
        if (!Registries.BLOCK.containsId(STATEFUL_FIXTURE_ID)) {
            Registry.register(Registries.BLOCK, STATEFUL_FIXTURE_ID, STATEFUL_FIXTURE_BLOCK);
        }

        adapters = ComponentStateAdapterRegistry.createForSlabeeMappings();
        adapters.register(ComponentStateAdapterRegistry.Adapter.horizontal(
                STATEFUL_FIXTURE_BLOCK,
                Set.of(Properties.PERSISTENT.getName(), Properties.NORTH.getName()),
                Set.of(Properties.DISTANCE_1_7.getName())
        ));
        codec = new ComponentStateCodec(adapters, Registries.BLOCK.getReadOnlyWrapper(), DIAGNOSTICS::add);
    }

    @BeforeEach
    void clearDiagnostics() {
        DIAGNOSTICS.clear();
    }

    @Test
    void horizontalRoundTripReconstructsPositiveAndNegativeSlots() {
        BlockState positive = Blocks.OAK_SLAB.getDefaultState()
                .with(Properties.SLAB_TYPE, SlabType.TOP)
                .with(Properties.WATERLOGGED, false);
        BlockState negative = Blocks.STONE_SLAB.getDefaultState()
                .with(Properties.SLAB_TYPE, SlabType.BOTTOM)
                .with(Properties.WATERLOGGED, false);

        ComponentStateCodec.EncodeResult encoded = codec.encode(ContainerComponents.horizontal(positive, negative));
        ComponentStateCodec.DecodeResult decoded = codec.decode(
                encoded.nbt(),
                ComponentContainerKind.HORIZONTAL,
                Optional.empty()
        );

        assertAll(
                () -> assertTrue(encoded.omittedSlots().isEmpty()),
                () -> assertEquals(InputFormat.VERSIONED, decoded.inputFormat()),
                () -> assertEquals(RecoveryAction.KEEP_DOUBLE, decoded.recoveryAction()),
                () -> assertEquals(positive, decoded.positive().state().orElseThrow()),
                () -> assertEquals(negative, decoded.negative().state().orElseThrow()),
                () -> assertEquals(ComponentSlot.POSITIVE, decoded.positive().slot()),
                () -> assertEquals(ComponentSlot.NEGATIVE, decoded.negative().slot())
        );
    }

    @Test
    void verticalXRoundTripReconstructsFacingFromSlotAndAxis() {
        assertVerticalRoundTrip(
                VerticalSlabAxis.X,
                Direction.EAST,
                Direction.WEST
        );
    }

    @Test
    void verticalZRoundTripReconstructsFacingFromSlotAndAxis() {
        assertVerticalRoundTrip(
                VerticalSlabAxis.Z,
                Direction.SOUTH,
                Direction.NORTH
        );
    }

    @Test
    void componentOwnedPropertiesRoundTripWhileEnvironmentAndSlotPropertiesAreFiltered() {
        BlockState statefulPositive = STATEFUL_FIXTURE_BLOCK.getDefaultState()
                .with(Properties.SLAB_TYPE, SlabType.TOP)
                .with(Properties.PERSISTENT, true)
                .with(Properties.NORTH, true)
                .with(Properties.DISTANCE_1_7, 2)
                .with(Properties.WATERLOGGED, true);
        BlockState negative = Blocks.OAK_SLAB.getDefaultState();

        ComponentStateCodec.EncodeResult encoded = codec.encode(
                ContainerComponents.horizontal(statefulPositive, negative)
        );
        NbtCompound properties = componentProperties(encoded.nbt(), ComponentSlot.POSITIVE);
        ComponentStateCodec.DecodeResult decoded = codec.decode(
                encoded.nbt(),
                ComponentContainerKind.HORIZONTAL,
                Optional.empty()
        );
        BlockState restored = decoded.positive().state().orElseThrow();

        assertAll(
                () -> assertEquals(Set.of("north", "persistent"), properties.getKeys()),
                () -> assertFalse(properties.contains("type")),
                () -> assertFalse(properties.contains("distance")),
                () -> assertFalse(properties.contains("waterlogged")),
                () -> assertTrue(restored.get(Properties.PERSISTENT)),
                () -> assertTrue(restored.get(Properties.NORTH)),
                () -> assertEquals(7, restored.get(Properties.DISTANCE_1_7)),
                () -> assertEquals(SlabType.TOP, restored.get(Properties.SLAB_TYPE)),
                () -> assertFalse(restored.get(Properties.WATERLOGGED))
        );
    }

    @Test
    void snowyEnvironmentPropertyIsNotSerialized() {
        BlockState snowyGrass = ModBlocks.GRASS_SLAB.getDefaultState()
                .with(Properties.SLAB_TYPE, SlabType.TOP)
                .with(Properties.SNOWY, true);

        ComponentStateCodec.EncodeResult encoded = codec.encode(
                ContainerComponents.horizontal(snowyGrass, Blocks.OAK_SLAB.getDefaultState())
        );
        NbtCompound state = componentState(encoded.nbt(), ComponentSlot.POSITIVE);
        ComponentStateCodec.DecodeResult decoded = codec.decode(
                encoded.nbt(),
                ComponentContainerKind.HORIZONTAL,
                Optional.empty()
        );

        assertAll(
                () -> assertFalse(state.contains("Properties")),
                () -> assertFalse(decoded.positive().state().orElseThrow().get(Properties.SNOWY))
        );
    }

    @Test
    void containerAxisIsSerializedExactlyOnceOutsideComponents() {
        ComponentStateCodec.EncodeResult encoded = codec.encode(ContainerComponents.vertical(
                VerticalSlabAxis.Z,
                ModBlocks.STONE_VERTICAL_SLAB.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH),
                ModBlocks.COBBLESTONE_VERTICAL_SLAB.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH)
        ));
        NbtCompound payload = encoded.nbt().getCompound(ComponentStateCodec.PAYLOAD_KEY);

        assertAll(
                () -> assertEquals("z", payload.getString("axis")),
                () -> assertFalse(componentState(encoded.nbt(), ComponentSlot.POSITIVE).contains("axis")),
                () -> assertFalse(componentState(encoded.nbt(), ComponentSlot.NEGATIVE).contains("axis")),
                () -> assertFalse(componentProperties(encoded.nbt(), ComponentSlot.POSITIVE).contains("facing")),
                () -> assertFalse(componentProperties(encoded.nbt(), ComponentSlot.NEGATIVE).contains("facing"))
        );
    }

    @Test
    void legacyHorizontalIdsLoadAndLegacyFacingCannotOverrideSlots() throws Exception {
        ComponentStateCodec.DecodeResult decoded = codec.decode(
                fixture("legacy-horizontal.snbt"),
                ComponentContainerKind.HORIZONTAL,
                Optional.empty()
        );

        assertAll(
                () -> assertEquals(InputFormat.LEGACY, decoded.inputFormat()),
                () -> assertEquals(RecoveryAction.KEEP_DOUBLE, decoded.recoveryAction()),
                () -> assertEquals(Blocks.OAK_SLAB, decoded.positive().state().orElseThrow().getBlock()),
                () -> assertEquals(SlabType.TOP,
                        decoded.positive().state().orElseThrow().get(Properties.SLAB_TYPE)),
                () -> assertEquals(Blocks.STONE_SLAB, decoded.negative().state().orElseThrow().getBlock()),
                () -> assertEquals(SlabType.BOTTOM,
                        decoded.negative().state().orElseThrow().get(Properties.SLAB_TYPE))
        );
    }

    @Test
    void legacyVerticalAxisLoadsAndReconstructsBothSlots() throws Exception {
        ComponentStateCodec.DecodeResult decoded = codec.decode(
                fixture("legacy-vertical-z.snbt"),
                ComponentContainerKind.VERTICAL,
                Optional.of(VerticalSlabAxis.X)
        );

        assertAll(
                () -> assertEquals(InputFormat.LEGACY, decoded.inputFormat()),
                () -> assertEquals(Optional.of(VerticalSlabAxis.Z), decoded.verticalAxis()),
                () -> assertEquals(Direction.SOUTH,
                        decoded.positive().state().orElseThrow().get(Properties.HORIZONTAL_FACING)),
                () -> assertEquals(Direction.NORTH,
                        decoded.negative().state().orElseThrow().get(Properties.HORIZONTAL_FACING))
        );
    }

    @Test
    void invalidIdentifierLosesOnlyTheAffectedComponent() throws Exception {
        ComponentStateCodec.DecodeResult decoded = codec.decode(
                fixture("versioned-invalid-identifier.snbt"),
                ComponentContainerKind.HORIZONTAL,
                Optional.empty()
        );

        assertAll(
                () -> assertEquals(ComponentFailure.INVALID_IDENTIFIER, decoded.positive().failure()),
                () -> assertTrue(decoded.positive().state().isEmpty()),
                () -> assertEquals(ComponentFailure.NONE, decoded.negative().failure()),
                () -> assertEquals(RecoveryAction.SINGLEIZE_NEGATIVE, decoded.recoveryAction()),
                () -> assertTrue(DIAGNOSTICS.stream().anyMatch(diagnostic ->
                        diagnostic.code() == ComponentStateDiagnosticSink.Code.INVALID_IDENTIFIER))
        );
    }

    @Test
    void unknownBlockIdLosesOnlyTheAffectedComponent() throws Exception {
        ComponentStateCodec.DecodeResult decoded = codec.decode(
                fixture("versioned-unknown-block.snbt"),
                ComponentContainerKind.HORIZONTAL,
                Optional.empty()
        );

        assertAll(
                () -> assertEquals(ComponentFailure.UNKNOWN_BLOCK, decoded.positive().failure()),
                () -> assertEquals(ComponentFailure.NONE, decoded.negative().failure()),
                () -> assertEquals(RecoveryAction.SINGLEIZE_NEGATIVE, decoded.recoveryAction())
        );
    }

    @Test
    void registeredButUnsupportedBlockIsRejectedUntilAnAdapterExists() {
        NbtCompound root = versionedHorizontalState("minecraft:dirt", "minecraft:oak_slab");
        ComponentStateCodec.DecodeResult decoded = codec.decode(
                root,
                ComponentContainerKind.HORIZONTAL,
                Optional.empty()
        );

        assertAll(
                () -> assertEquals(ComponentFailure.UNSUPPORTED_BLOCK, decoded.positive().failure()),
                () -> assertEquals(RecoveryAction.SINGLEIZE_NEGATIVE, decoded.recoveryAction()),
                () -> assertTrue(adapters.find(STATEFUL_FIXTURE_BLOCK, ComponentContainerKind.HORIZONTAL).isPresent()),
                () -> assertTrue(codec.decode(
                        versionedHorizontalState(STATEFUL_FIXTURE_ID.toString(), "minecraft:oak_slab"),
                        ComponentContainerKind.HORIZONTAL,
                        Optional.empty()
                ).positive().state().isPresent())
        );
    }

    @Test
    void mappedBlockFromWrongShapeFamilyIsRejected() {
        NbtCompound root = codec.encode(ContainerComponents.vertical(
                VerticalSlabAxis.X,
                ModBlocks.STONE_VERTICAL_SLAB.getDefaultState(),
                ModBlocks.COBBLESTONE_VERTICAL_SLAB.getDefaultState()
        )).nbt();
        componentState(root, ComponentSlot.POSITIVE).putString("Name", "minecraft:oak_slab");

        ComponentStateCodec.DecodeResult decoded = codec.decode(
                root,
                ComponentContainerKind.VERTICAL,
                Optional.of(VerticalSlabAxis.X)
        );

        assertAll(
                () -> assertEquals(ComponentFailure.UNSUPPORTED_BLOCK, decoded.positive().failure()),
                () -> assertEquals(RecoveryAction.SINGLEIZE_NEGATIVE, decoded.recoveryAction())
        );
    }

    @Test
    void adapterCannotReclassifySlotDerivedPropertyAsComponentOwned() {
        assertThrows(IllegalArgumentException.class, () ->
                ComponentStateAdapterRegistry.Adapter.horizontal(
                        Blocks.OAK_SLAB,
                        Set.of(Properties.SLAB_TYPE.getName()),
                        Set.of()
                ));
    }

    @Test
    void unknownPropertyUsesKnownBlockDefaultsWithoutLosingComponent() {
        BlockState stateful = STATEFUL_FIXTURE_BLOCK.getDefaultState()
                .with(Properties.PERSISTENT, true)
                .with(Properties.NORTH, true);
        NbtCompound root = codec.encode(
                ContainerComponents.horizontal(stateful, Blocks.OAK_SLAB.getDefaultState())
        ).nbt();
        componentProperties(root, ComponentSlot.POSITIVE).putString("future_unknown", "true");

        ComponentStateCodec.DecodeResult decoded = codec.decode(
                root,
                ComponentContainerKind.HORIZONTAL,
                Optional.empty()
        );
        BlockState restored = decoded.positive().state().orElseThrow();

        assertAll(
                () -> assertEquals(ComponentFailure.NONE, decoded.positive().failure()),
                () -> assertTrue(restored.get(Properties.PERSISTENT)),
                () -> assertTrue(restored.get(Properties.NORTH)),
                () -> assertTrue(decoded.positive().defaultedProperties().contains("future_unknown")),
                () -> assertEquals(RecoveryAction.KEEP_DOUBLE, decoded.recoveryAction())
        );
    }

    @Test
    void invalidPropertyValueDefaultsOnlyThatProperty() {
        BlockState stateful = STATEFUL_FIXTURE_BLOCK.getDefaultState()
                .with(Properties.PERSISTENT, true)
                .with(Properties.NORTH, true);
        NbtCompound root = codec.encode(
                ContainerComponents.horizontal(stateful, Blocks.OAK_SLAB.getDefaultState())
        ).nbt();
        componentProperties(root, ComponentSlot.POSITIVE).putString("north", "not-a-boolean");

        ComponentStateCodec.DecodeResult decoded = codec.decode(
                root,
                ComponentContainerKind.HORIZONTAL,
                Optional.empty()
        );
        BlockState restored = decoded.positive().state().orElseThrow();

        assertAll(
                () -> assertTrue(restored.get(Properties.PERSISTENT)),
                () -> assertFalse(restored.get(Properties.NORTH)),
                () -> assertEquals(Set.of("north"), decoded.positive().defaultedProperties()),
                () -> assertEquals(ComponentFailure.NONE, decoded.positive().failure()),
                () -> assertTrue(DIAGNOSTICS.stream().anyMatch(diagnostic ->
                        diagnostic.code() == ComponentStateDiagnosticSink.Code.INVALID_PROPERTY_VALUE))
        );
    }

    @Test
    void slotAndEnvironmentValuesInInputAreIgnoredAndReconstructed() {
        NbtCompound root = codec.encode(ContainerComponents.horizontal(
                Blocks.OAK_SLAB.getDefaultState(),
                Blocks.STONE_SLAB.getDefaultState()
        )).nbt();
        NbtCompound positiveProperties = componentProperties(root, ComponentSlot.POSITIVE);
        positiveProperties.putString("type", "bottom");
        positiveProperties.putString("waterlogged", "true");

        ComponentStateCodec.DecodeResult decoded = codec.decode(
                root,
                ComponentContainerKind.HORIZONTAL,
                Optional.empty()
        );
        BlockState positive = decoded.positive().state().orElseThrow();

        assertAll(
                () -> assertEquals(SlabType.TOP, positive.get(Properties.SLAB_TYPE)),
                () -> assertFalse(positive.get(Properties.WATERLOGGED)),
                () -> assertTrue(decoded.positive().defaultedProperties().isEmpty())
        );
    }

    @Test
    void unknownFutureSchemaIsRejectedWithoutInterpretingComponents() throws Exception {
        ComponentStateCodec.DecodeResult decoded = codec.decode(
                fixture("versioned-unknown-schema.snbt"),
                ComponentContainerKind.HORIZONTAL,
                Optional.empty()
        );

        assertAll(
                () -> assertEquals(InputFormat.UNSUPPORTED_SCHEMA, decoded.inputFormat()),
                () -> assertEquals(ComponentFailure.UNKNOWN_SCHEMA, decoded.positive().failure()),
                () -> assertEquals(ComponentFailure.UNKNOWN_SCHEMA, decoded.negative().failure()),
                () -> assertEquals(RecoveryAction.REPLACE_WITH_AIR, decoded.recoveryAction())
        );
    }

    @Test
    void oneAndBothInvalidComponentsExposeSingleOrAirRecovery() throws Exception {
        NbtCompound oneInvalid = fixture("versioned-unknown-block.snbt");
        ComponentStateCodec.DecodeResult oneInvalidResult = codec.decode(
                oneInvalid,
                ComponentContainerKind.HORIZONTAL,
                Optional.empty()
        );

        NbtCompound bothInvalid = oneInvalid.copy();
        componentState(bothInvalid, ComponentSlot.NEGATIVE).putString("Name", "removed:also_missing");
        ComponentStateCodec.DecodeResult bothInvalidResult = codec.decode(
                bothInvalid,
                ComponentContainerKind.HORIZONTAL,
                Optional.empty()
        );

        assertAll(
                () -> assertEquals(RecoveryAction.SINGLEIZE_NEGATIVE, oneInvalidResult.recoveryAction()),
                () -> assertTrue(oneInvalidResult.negative().state().isPresent()),
                () -> assertEquals(RecoveryAction.REPLACE_WITH_AIR, bothInvalidResult.recoveryAction()),
                () -> assertTrue(bothInvalidResult.positive().state().isEmpty()),
                () -> assertTrue(bothInvalidResult.negative().state().isEmpty())
        );
    }

    @Test
    void unknownAdditiveFieldsAreIgnored() throws Exception {
        ComponentStateCodec.DecodeResult decoded = codec.decode(
                fixture("versioned-additive-fields.snbt"),
                ComponentContainerKind.HORIZONTAL,
                Optional.empty()
        );

        assertAll(
                () -> assertEquals(RecoveryAction.KEEP_DOUBLE, decoded.recoveryAction()),
                () -> assertEquals(Blocks.OAK_SLAB, decoded.positive().state().orElseThrow().getBlock()),
                () -> assertEquals(Blocks.STONE_SLAB, decoded.negative().state().orElseThrow().getBlock())
        );
    }

    @Test
    void encodeNbtDecodeEncodeIsDeterministic() {
        BlockState positive = STATEFUL_FIXTURE_BLOCK.getDefaultState()
                .with(Properties.PERSISTENT, true)
                .with(Properties.NORTH, true)
                .with(Properties.SLAB_TYPE, SlabType.TOP);
        BlockState negative = Blocks.OAK_SLAB.getDefaultState()
                .with(Properties.SLAB_TYPE, SlabType.BOTTOM);

        NbtCompound first = codec.encode(ContainerComponents.horizontal(positive, negative)).nbt();
        ComponentStateCodec.DecodeResult decoded = codec.decode(
                first.copy(),
                ComponentContainerKind.HORIZONTAL,
                Optional.empty()
        );
        NbtCompound second = codec.encode(ContainerComponents.horizontal(
                decoded.positive().state().orElseThrow(),
                decoded.negative().state().orElseThrow()
        )).nbt();

        assertEquals(first, second);
    }

    @Test
    void versionedOuterAxisRemainsAuthoritativeOnMismatch() {
        NbtCompound root = codec.encode(ContainerComponents.vertical(
                VerticalSlabAxis.Z,
                ModBlocks.STONE_VERTICAL_SLAB.getDefaultState(),
                ModBlocks.COBBLESTONE_VERTICAL_SLAB.getDefaultState()
        )).nbt();

        ComponentStateCodec.DecodeResult decoded = codec.decode(
                root,
                ComponentContainerKind.VERTICAL,
                Optional.of(VerticalSlabAxis.X)
        );

        assertAll(
                () -> assertEquals(Optional.of(VerticalSlabAxis.X), decoded.verticalAxis()),
                () -> assertEquals(Direction.EAST,
                        decoded.positive().state().orElseThrow().get(Properties.HORIZONTAL_FACING)),
                () -> assertEquals(Direction.WEST,
                        decoded.negative().state().orElseThrow().get(Properties.HORIZONTAL_FACING)),
                () -> assertTrue(DIAGNOSTICS.stream().anyMatch(diagnostic ->
                        diagnostic.code() == ComponentStateDiagnosticSink.Code.INVALID_AXIS))
        );
    }

    private static void assertVerticalRoundTrip(
            VerticalSlabAxis axis,
            Direction expectedPositiveFacing,
            Direction expectedNegativeFacing
    ) {
        BlockState positive = ModBlocks.STONE_VERTICAL_SLAB.getDefaultState()
                .with(Properties.HORIZONTAL_FACING, expectedPositiveFacing);
        BlockState negative = ModBlocks.COBBLESTONE_VERTICAL_SLAB.getDefaultState()
                .with(Properties.HORIZONTAL_FACING, expectedNegativeFacing);

        ComponentStateCodec.EncodeResult encoded = codec.encode(
                ContainerComponents.vertical(axis, positive, negative)
        );
        ComponentStateCodec.DecodeResult decoded = codec.decode(
                encoded.nbt(),
                ComponentContainerKind.VERTICAL,
                Optional.of(axis)
        );

        assertAll(
                () -> assertEquals(Optional.of(axis), decoded.verticalAxis()),
                () -> assertEquals(expectedPositiveFacing,
                        decoded.positive().state().orElseThrow().get(Properties.HORIZONTAL_FACING)),
                () -> assertEquals(expectedNegativeFacing,
                        decoded.negative().state().orElseThrow().get(Properties.HORIZONTAL_FACING)),
                () -> assertEquals(RecoveryAction.KEEP_DOUBLE, decoded.recoveryAction())
        );
    }

    private static NbtCompound fixture(String name) throws IOException, CommandSyntaxException {
        String path = "/fixtures/component-state/" + name;
        try (InputStream stream = ComponentStateCodecTest.class.getResourceAsStream(path)) {
            if (stream == null) {
                throw new IOException("Missing fixture " + path);
            }
            return StringNbtReader.parse(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
        }
    }

    private static NbtCompound versionedHorizontalState(String positiveId, String negativeId) {
        NbtCompound root = new NbtCompound();
        NbtCompound payload = new NbtCompound();
        payload.putInt("schema_version", ComponentStateCodec.CURRENT_SCHEMA_VERSION);
        payload.putString("container_kind", "horizontal");
        payload.put("positive", componentWithId(positiveId));
        payload.put("negative", componentWithId(negativeId));
        root.put(ComponentStateCodec.PAYLOAD_KEY, payload);
        return root;
    }

    private static NbtCompound componentWithId(String id) {
        NbtCompound blockState = new NbtCompound();
        blockState.putString("Name", id);
        NbtCompound component = new NbtCompound();
        component.put("block_state", blockState);
        return component;
    }

    private static NbtCompound componentState(NbtCompound root, ComponentSlot slot) {
        return root.getCompound(ComponentStateCodec.PAYLOAD_KEY)
                .getCompound(slot.serializedName())
                .getCompound("block_state");
    }

    private static NbtCompound componentProperties(NbtCompound root, ComponentSlot slot) {
        NbtCompound state = componentState(root, slot);
        if (!state.contains("Properties", NbtElement.COMPOUND_TYPE)) {
            state.put("Properties", new NbtCompound());
        }
        return state.getCompound("Properties");
    }

    private static final class StatefulFixtureSlabBlock extends SlabBlock {
        private StatefulFixtureSlabBlock() {
            super(AbstractBlock.Settings.create());
            setDefaultState(getDefaultState()
                    .with(Properties.PERSISTENT, false)
                    .with(Properties.NORTH, false)
                    .with(Properties.DISTANCE_1_7, 7));
        }

        @Override
        protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
            super.appendProperties(builder);
            builder.add(Properties.PERSISTENT, Properties.NORTH, Properties.DISTANCE_1_7);
        }
    }
}
