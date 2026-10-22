package com.forestotzka.yurufu.slabee.block.persistence;

import com.forestotzka.yurufu.slabee.Slabee;
import com.forestotzka.yurufu.slabee.block.VerticalSlabBlock;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.forestotzka.yurufu.slabee.block.persistence.ComponentStateDiagnosticSink.Code;
import static com.forestotzka.yurufu.slabee.block.persistence.ComponentStateDiagnosticSink.Diagnostic;

/**
 * Stage 1 B2 spike codec. It is intentionally not connected to live Block Entity
 * serialization; Stage 6 owns that integration and world migration.
 */
public final class ComponentStateCodec {
    public static final int CURRENT_SCHEMA_VERSION = 1;
    public static final String PAYLOAD_KEY = "slabee:component_state";

    private static final String SCHEMA_VERSION_KEY = "schema_version";
    private static final String CONTAINER_KIND_KEY = "container_kind";
    private static final String COMPONENT_STATE_KEY = "block_state";
    private static final String AXIS_KEY = "axis";
    private static final String LEGACY_POSITIVE_KEY = "positive_slab";
    private static final String LEGACY_NEGATIVE_KEY = "negative_slab";
    private static final String LEGACY_ID_KEY = "id";
    private static final String BLOCK_NAME_KEY = "Name";
    private static final String PROPERTIES_KEY = "Properties";
    private static final int DEFAULT_DIAGNOSTIC_LIMIT = 256;

    private final ComponentStateAdapterRegistry adapters;
    private final RegistryEntryLookup<Block> blockLookup;
    private final ComponentStateDiagnosticSink diagnostics;

    public ComponentStateCodec(
            ComponentStateAdapterRegistry adapters,
            RegistryEntryLookup<Block> blockLookup,
            ComponentStateDiagnosticSink diagnostics
    ) {
        this.adapters = Objects.requireNonNull(adapters, "adapters");
        this.blockLookup = Objects.requireNonNull(blockLookup, "blockLookup");
        this.diagnostics = Objects.requireNonNull(diagnostics, "diagnostics");
    }

    public static ComponentStateCodec createForSlabeeMappings() {
        return new ComponentStateCodec(
                ComponentStateAdapterRegistry.createForSlabeeMappings(),
                Registries.BLOCK.getReadOnlyWrapper(),
                ComponentStateDiagnosticSink.rateLimitedLogger(Slabee.LOGGER, DEFAULT_DIAGNOSTIC_LIMIT)
        );
    }

    public EncodeResult encode(ContainerComponents components) {
        Objects.requireNonNull(components, "components");

        NbtCompound root = new NbtCompound();
        NbtCompound payload = new NbtCompound();
        payload.putInt(SCHEMA_VERSION_KEY, CURRENT_SCHEMA_VERSION);
        payload.putString(CONTAINER_KIND_KEY, components.containerKind().serializedName());
        if (components.containerKind() == ComponentContainerKind.VERTICAL) {
            payload.putString(AXIS_KEY, components.verticalAxis().orElseThrow().asString());
        }

        EnumSet<ComponentSlot> omittedSlots = EnumSet.noneOf(ComponentSlot.class);
        encodeSlot(payload, ComponentSlot.POSITIVE, components.positive(), components.containerKind(), omittedSlots);
        encodeSlot(payload, ComponentSlot.NEGATIVE, components.negative(), components.containerKind(), omittedSlots);
        root.put(PAYLOAD_KEY, payload);
        return new EncodeResult(root, omittedSlots);
    }

    public DecodeResult decode(
            NbtCompound root,
            ComponentContainerKind expectedKind,
            Optional<VerticalSlabAxis> authoritativeOuterAxis
    ) {
        Objects.requireNonNull(root, "root");
        Objects.requireNonNull(expectedKind, "expectedKind");
        Objects.requireNonNull(authoritativeOuterAxis, "authoritativeOuterAxis");

        if (root.contains(PAYLOAD_KEY)) {
            if (!root.contains(PAYLOAD_KEY, NbtElement.COMPOUND_TYPE)) {
                diagnostics.report(Diagnostic.container(Code.MALFORMED_COMPONENT, "versioned payload is not a compound"));
                return failedContainer(expectedKind, authoritativeOuterAxis, InputFormat.VERSIONED,
                        ComponentFailure.MALFORMED_COMPONENT);
            }
            return decodeVersioned(root.getCompound(PAYLOAD_KEY), expectedKind, authoritativeOuterAxis);
        }
        return decodeLegacy(root, expectedKind, authoritativeOuterAxis);
    }

    private DecodeResult decodeVersioned(
            NbtCompound payload,
            ComponentContainerKind expectedKind,
            Optional<VerticalSlabAxis> authoritativeOuterAxis
    ) {
        if (!payload.contains(SCHEMA_VERSION_KEY, NbtElement.NUMBER_TYPE)) {
            diagnostics.report(Diagnostic.container(Code.UNKNOWN_SCHEMA, "missing numeric schema_version"));
            return failedContainer(expectedKind, authoritativeOuterAxis, InputFormat.VERSIONED,
                    ComponentFailure.UNKNOWN_SCHEMA);
        }

        int schemaVersion = payload.getInt(SCHEMA_VERSION_KEY);
        if (schemaVersion != CURRENT_SCHEMA_VERSION) {
            diagnostics.report(Diagnostic.container(Code.UNKNOWN_SCHEMA, "unsupported schema_version " + schemaVersion));
            return failedContainer(expectedKind, authoritativeOuterAxis, InputFormat.UNSUPPORTED_SCHEMA,
                    ComponentFailure.UNKNOWN_SCHEMA);
        }

        Optional<ComponentContainerKind> serializedKind = payload.contains(CONTAINER_KIND_KEY, NbtElement.STRING_TYPE)
                ? ComponentContainerKind.fromSerializedName(payload.getString(CONTAINER_KIND_KEY))
                : Optional.empty();
        if (serializedKind.isEmpty() || serializedKind.get() != expectedKind) {
            diagnostics.report(Diagnostic.container(
                    Code.CONTAINER_KIND_MISMATCH,
                    "expected " + expectedKind.serializedName() + " but found "
                            + serializedKind.map(ComponentContainerKind::serializedName).orElse("invalid")
            ));
            return failedContainer(expectedKind, authoritativeOuterAxis, InputFormat.VERSIONED,
                    ComponentFailure.CONTAINER_KIND_MISMATCH);
        }

        Optional<VerticalSlabAxis> axis = resolveVersionedAxis(payload, expectedKind, authoritativeOuterAxis);
        DecodedComponent positive = decodeVersionedSlot(payload, ComponentSlot.POSITIVE, expectedKind, axis);
        DecodedComponent negative = decodeVersionedSlot(payload, ComponentSlot.NEGATIVE, expectedKind, axis);
        return new DecodeResult(expectedKind, axis, positive, negative, InputFormat.VERSIONED,
                recoveryFor(positive, negative));
    }

    private DecodeResult decodeLegacy(
            NbtCompound root,
            ComponentContainerKind expectedKind,
            Optional<VerticalSlabAxis> authoritativeOuterAxis
    ) {
        Optional<VerticalSlabAxis> axis = resolveLegacyAxis(root, expectedKind, authoritativeOuterAxis);
        DecodedComponent positive = decodeLegacySlot(root, ComponentSlot.POSITIVE, expectedKind, axis);
        DecodedComponent negative = decodeLegacySlot(root, ComponentSlot.NEGATIVE, expectedKind, axis);
        InputFormat format = root.contains(LEGACY_POSITIVE_KEY) || root.contains(LEGACY_NEGATIVE_KEY)
                ? InputFormat.LEGACY
                : InputFormat.EMPTY;
        return new DecodeResult(expectedKind, axis, positive, negative, format, recoveryFor(positive, negative));
    }

    private void encodeSlot(
            NbtCompound payload,
            ComponentSlot slot,
            Optional<BlockState> state,
            ComponentContainerKind containerKind,
            EnumSet<ComponentSlot> omittedSlots
    ) {
        if (state.isEmpty()) {
            omittedSlots.add(slot);
            return;
        }

        BlockState blockState = state.get();
        Optional<ComponentStateAdapterRegistry.Adapter> adapter = adapters.find(blockState.getBlock(), containerKind);
        if (adapter.isEmpty()) {
            omittedSlots.add(slot);
            diagnostics.report(Diagnostic.component(
                    Code.ENCODE_UNSUPPORTED_BLOCK,
                    slot,
                    Registries.BLOCK.getId(blockState.getBlock()).toString()
            ));
            return;
        }

        NbtCompound serialized = NbtHelper.fromBlockState(blockState);
        NbtCompound filteredState = new NbtCompound();
        filteredState.putString(BLOCK_NAME_KEY, serialized.getString(BLOCK_NAME_KEY));

        if (serialized.contains(PROPERTIES_KEY, NbtElement.COMPOUND_TYPE)) {
            NbtCompound allProperties = serialized.getCompound(PROPERTIES_KEY);
            NbtCompound ownedProperties = new NbtCompound();
            adapter.get().componentOwnedProperties().stream().sorted().forEach(propertyName -> {
                if (allProperties.contains(propertyName, NbtElement.STRING_TYPE)) {
                    ownedProperties.putString(propertyName, allProperties.getString(propertyName));
                }
            });
            if (!ownedProperties.isEmpty()) {
                filteredState.put(PROPERTIES_KEY, ownedProperties);
            }
        }

        NbtCompound component = new NbtCompound();
        component.put(COMPONENT_STATE_KEY, filteredState);
        payload.put(slot.serializedName(), component);
    }

    private DecodedComponent decodeVersionedSlot(
            NbtCompound payload,
            ComponentSlot slot,
            ComponentContainerKind containerKind,
            Optional<VerticalSlabAxis> axis
    ) {
        if (!payload.contains(slot.serializedName())) {
            return DecodedComponent.failed(slot, ComponentFailure.MISSING_COMPONENT);
        }
        if (!payload.contains(slot.serializedName(), NbtElement.COMPOUND_TYPE)) {
            diagnostics.report(Diagnostic.component(Code.MALFORMED_COMPONENT, slot, "component entry is not a compound"));
            return DecodedComponent.failed(slot, ComponentFailure.MALFORMED_COMPONENT);
        }

        NbtCompound component = payload.getCompound(slot.serializedName());
        if (!component.contains(COMPONENT_STATE_KEY, NbtElement.COMPOUND_TYPE)) {
            diagnostics.report(Diagnostic.component(Code.MALFORMED_COMPONENT, slot, "missing block_state compound"));
            return DecodedComponent.failed(slot, ComponentFailure.MALFORMED_COMPONENT);
        }
        return decodeState(component.getCompound(COMPONENT_STATE_KEY), slot, containerKind, axis);
    }

    private DecodedComponent decodeLegacySlot(
            NbtCompound root,
            ComponentSlot slot,
            ComponentContainerKind containerKind,
            Optional<VerticalSlabAxis> axis
    ) {
        String legacyKey = slot == ComponentSlot.POSITIVE ? LEGACY_POSITIVE_KEY : LEGACY_NEGATIVE_KEY;
        if (!root.contains(legacyKey)) {
            return DecodedComponent.failed(slot, ComponentFailure.MISSING_COMPONENT);
        }
        if (!root.contains(legacyKey, NbtElement.COMPOUND_TYPE)) {
            diagnostics.report(Diagnostic.component(Code.MALFORMED_COMPONENT, slot, "legacy component is not a compound"));
            return DecodedComponent.failed(slot, ComponentFailure.MALFORMED_COMPONENT);
        }

        NbtCompound legacyComponent = root.getCompound(legacyKey);
        if (!legacyComponent.contains(LEGACY_ID_KEY, NbtElement.STRING_TYPE)) {
            diagnostics.report(Diagnostic.component(Code.INVALID_IDENTIFIER, slot, "legacy id is missing or not a string"));
            return DecodedComponent.failed(slot, ComponentFailure.INVALID_IDENTIFIER);
        }

        NbtCompound state = new NbtCompound();
        state.putString(BLOCK_NAME_KEY, legacyComponent.getString(LEGACY_ID_KEY));
        return decodeState(state, slot, containerKind, axis);
    }

    private DecodedComponent decodeState(
            NbtCompound serializedState,
            ComponentSlot slot,
            ComponentContainerKind containerKind,
            Optional<VerticalSlabAxis> axis
    ) {
        if (!serializedState.contains(BLOCK_NAME_KEY, NbtElement.STRING_TYPE)) {
            diagnostics.report(Diagnostic.component(Code.INVALID_IDENTIFIER, slot, "BlockState Name is missing or not a string"));
            return DecodedComponent.failed(slot, ComponentFailure.INVALID_IDENTIFIER);
        }

        String rawId = serializedState.getString(BLOCK_NAME_KEY);
        Identifier id = Identifier.tryParse(rawId);
        if (id == null) {
            diagnostics.report(Diagnostic.component(Code.INVALID_IDENTIFIER, slot, rawId));
            return DecodedComponent.failed(slot, ComponentFailure.INVALID_IDENTIFIER);
        }

        Optional<Block> registeredBlock = Registries.BLOCK.getOrEmpty(id);
        if (registeredBlock.isEmpty()) {
            diagnostics.report(Diagnostic.component(Code.UNKNOWN_BLOCK, slot, id.toString()));
            return DecodedComponent.failed(slot, ComponentFailure.UNKNOWN_BLOCK);
        }

        Block block = registeredBlock.get();
        Optional<ComponentStateAdapterRegistry.Adapter> adapter = adapters.find(block, containerKind);
        if (adapter.isEmpty()) {
            diagnostics.report(Diagnostic.component(Code.UNSUPPORTED_BLOCK, slot, id.toString()));
            return DecodedComponent.failed(slot, ComponentFailure.UNSUPPORTED_BLOCK);
        }

        NbtCompound sanitized = new NbtCompound();
        sanitized.putString(BLOCK_NAME_KEY, id.toString());
        NbtCompound sanitizedProperties = new NbtCompound();
        Set<String> defaultedProperties = new java.util.LinkedHashSet<>(adapter.get().componentOwnedProperties());

        if (serializedState.contains(PROPERTIES_KEY)) {
            if (!serializedState.contains(PROPERTIES_KEY, NbtElement.COMPOUND_TYPE)) {
                diagnostics.report(Diagnostic.component(
                        Code.INVALID_PROPERTY_VALUE,
                        slot,
                        id + " Properties is not a compound"
                ));
            } else {
                NbtCompound properties = serializedState.getCompound(PROPERTIES_KEY);
                List<String> propertyNames = new ArrayList<>(properties.getKeys());
                propertyNames.sort(String::compareTo);
                for (String propertyName : propertyNames) {
                    ComponentStateAdapterRegistry.PropertyOwnership ownership = adapter.get().ownershipOf(propertyName);
                    if (ownership != ComponentStateAdapterRegistry.PropertyOwnership.COMPONENT) {
                        if (ownership == ComponentStateAdapterRegistry.PropertyOwnership.UNSUPPORTED) {
                            defaultedProperties.add(propertyName);
                            diagnostics.report(Diagnostic.component(
                                    Code.UNKNOWN_PROPERTY,
                                    slot,
                                    id + " property " + propertyName
                            ));
                        }
                        continue;
                    }

                    Property<?> property = block.getStateManager().getProperty(propertyName);
                    if (!properties.contains(propertyName, NbtElement.STRING_TYPE)
                            || property == null
                            || property.parse(properties.getString(propertyName)).isEmpty()) {
                        diagnostics.report(Diagnostic.component(
                                Code.INVALID_PROPERTY_VALUE,
                                slot,
                                id + " property " + propertyName
                        ));
                        continue;
                    }

                    sanitizedProperties.putString(propertyName, properties.getString(propertyName));
                    defaultedProperties.remove(propertyName);
                }
            }
        }

        if (!sanitizedProperties.isEmpty()) {
            sanitized.put(PROPERTIES_KEY, sanitizedProperties);
        }

        BlockState decoded;
        try {
            decoded = NbtHelper.toBlockState(blockLookup, sanitized);
        } catch (RuntimeException exception) {
            diagnostics.report(Diagnostic.component(Code.MALFORMED_COMPONENT, slot, id + " failed validated decode"));
            return DecodedComponent.failed(slot, ComponentFailure.MALFORMED_COMPONENT);
        }
        if (!decoded.isOf(block)) {
            diagnostics.report(Diagnostic.component(Code.MALFORMED_COMPONENT, slot, id + " decoded as another block"));
            return DecodedComponent.failed(slot, ComponentFailure.MALFORMED_COMPONENT);
        }

        BlockState reconstructed = reconstructSlotState(decoded, slot, containerKind, axis);
        return DecodedComponent.restored(slot, reconstructed, defaultedProperties);
    }

    private Optional<VerticalSlabAxis> resolveVersionedAxis(
            NbtCompound payload,
            ComponentContainerKind kind,
            Optional<VerticalSlabAxis> authoritativeOuterAxis
    ) {
        if (kind == ComponentContainerKind.HORIZONTAL) {
            return Optional.empty();
        }

        Optional<VerticalSlabAxis> serializedAxis = readAxis(payload);
        if (serializedAxis.isEmpty()) {
            diagnostics.report(Diagnostic.container(Code.INVALID_AXIS, "missing or invalid versioned axis"));
        }
        if (authoritativeOuterAxis.isPresent()) {
            if (serializedAxis.isPresent() && serializedAxis.get() != authoritativeOuterAxis.get()) {
                diagnostics.report(Diagnostic.container(Code.INVALID_AXIS,
                        "versioned axis differs from authoritative outer BlockState"));
            }
            return authoritativeOuterAxis;
        }
        return Optional.of(serializedAxis.orElse(VerticalSlabAxis.X));
    }

    private Optional<VerticalSlabAxis> resolveLegacyAxis(
            NbtCompound root,
            ComponentContainerKind kind,
            Optional<VerticalSlabAxis> outerAxisFallback
    ) {
        if (kind == ComponentContainerKind.HORIZONTAL) {
            return Optional.empty();
        }

        Optional<VerticalSlabAxis> legacyAxis = readAxis(root);
        if (root.contains(AXIS_KEY) && legacyAxis.isEmpty()) {
            diagnostics.report(Diagnostic.container(Code.INVALID_AXIS, "invalid legacy axis"));
        }
        return Optional.of(legacyAxis.orElseGet(() -> outerAxisFallback.orElse(VerticalSlabAxis.X)));
    }

    private static Optional<VerticalSlabAxis> readAxis(NbtCompound nbt) {
        if (!nbt.contains(AXIS_KEY, NbtElement.STRING_TYPE)) {
            return Optional.empty();
        }
        return switch (nbt.getString(AXIS_KEY).toLowerCase(java.util.Locale.ROOT)) {
            case "x" -> Optional.of(VerticalSlabAxis.X);
            case "z" -> Optional.of(VerticalSlabAxis.Z);
            default -> Optional.empty();
        };
    }

    private static BlockState reconstructSlotState(
            BlockState state,
            ComponentSlot slot,
            ComponentContainerKind containerKind,
            Optional<VerticalSlabAxis> axis
    ) {
        if (containerKind == ComponentContainerKind.HORIZONTAL) {
            BlockState reconstructed = state.with(
                    SlabBlock.TYPE,
                    slot == ComponentSlot.POSITIVE ? SlabType.TOP : SlabType.BOTTOM
            );
            return reconstructed.withIfExists(Properties.WATERLOGGED, false);
        }

        VerticalSlabAxis verticalAxis = axis.orElse(VerticalSlabAxis.X);
        Direction facing;
        if (verticalAxis == VerticalSlabAxis.X) {
            facing = slot == ComponentSlot.POSITIVE ? Direction.EAST : Direction.WEST;
        } else {
            facing = slot == ComponentSlot.POSITIVE ? Direction.SOUTH : Direction.NORTH;
        }
        return state.with(HorizontalFacingBlock.FACING, facing)
                .withIfExists(VerticalSlabBlock.IS_DOUBLE, false)
                .withIfExists(Properties.WATERLOGGED, false);
    }

    private static RecoveryAction recoveryFor(DecodedComponent positive, DecodedComponent negative) {
        if (positive.state().isPresent() && negative.state().isPresent()) {
            return RecoveryAction.KEEP_DOUBLE;
        }
        if (positive.state().isPresent()) {
            return RecoveryAction.SINGLEIZE_POSITIVE;
        }
        if (negative.state().isPresent()) {
            return RecoveryAction.SINGLEIZE_NEGATIVE;
        }
        return RecoveryAction.REPLACE_WITH_AIR;
    }

    private static DecodeResult failedContainer(
            ComponentContainerKind kind,
            Optional<VerticalSlabAxis> axis,
            InputFormat format,
            ComponentFailure failure
    ) {
        DecodedComponent positive = DecodedComponent.failed(ComponentSlot.POSITIVE, failure);
        DecodedComponent negative = DecodedComponent.failed(ComponentSlot.NEGATIVE, failure);
        Optional<VerticalSlabAxis> normalizedAxis = kind == ComponentContainerKind.VERTICAL
                ? Optional.of(axis.orElse(VerticalSlabAxis.X))
                : Optional.empty();
        return new DecodeResult(kind, normalizedAxis, positive, negative, format, RecoveryAction.REPLACE_WITH_AIR);
    }

    public record ContainerComponents(
            ComponentContainerKind containerKind,
            Optional<VerticalSlabAxis> verticalAxis,
            Optional<BlockState> positive,
            Optional<BlockState> negative
    ) {
        public ContainerComponents {
            Objects.requireNonNull(containerKind, "containerKind");
            verticalAxis = Objects.requireNonNull(verticalAxis, "verticalAxis");
            positive = Objects.requireNonNull(positive, "positive");
            negative = Objects.requireNonNull(negative, "negative");

            if (containerKind == ComponentContainerKind.HORIZONTAL && verticalAxis.isPresent()) {
                throw new IllegalArgumentException("Horizontal containers do not own a vertical axis");
            }
            if (containerKind == ComponentContainerKind.VERTICAL && verticalAxis.isEmpty()) {
                throw new IllegalArgumentException("Vertical containers require a container-owned axis");
            }
        }

        public static ContainerComponents horizontal(BlockState positive, BlockState negative) {
            return new ContainerComponents(
                    ComponentContainerKind.HORIZONTAL,
                    Optional.empty(),
                    Optional.of(positive),
                    Optional.of(negative)
            );
        }

        public static ContainerComponents vertical(
                VerticalSlabAxis axis,
                BlockState positive,
                BlockState negative
        ) {
            return new ContainerComponents(
                    ComponentContainerKind.VERTICAL,
                    Optional.of(axis),
                    Optional.of(positive),
                    Optional.of(negative)
            );
        }
    }

    public record EncodeResult(NbtCompound nbt, Set<ComponentSlot> omittedSlots) {
        public EncodeResult {
            nbt = Objects.requireNonNull(nbt, "nbt");
            omittedSlots = Set.copyOf(omittedSlots);
        }
    }

    public record DecodedComponent(
            ComponentSlot slot,
            Optional<BlockState> state,
            ComponentFailure failure,
            Set<String> defaultedProperties
    ) {
        public DecodedComponent {
            Objects.requireNonNull(slot, "slot");
            state = Objects.requireNonNull(state, "state");
            Objects.requireNonNull(failure, "failure");
            defaultedProperties = Set.copyOf(defaultedProperties);
        }

        public static DecodedComponent restored(
                ComponentSlot slot,
                BlockState state,
                Set<String> defaultedProperties
        ) {
            return new DecodedComponent(slot, Optional.of(state), ComponentFailure.NONE, defaultedProperties);
        }

        public static DecodedComponent failed(ComponentSlot slot, ComponentFailure failure) {
            return new DecodedComponent(slot, Optional.empty(), failure, Set.of());
        }
    }

    public record DecodeResult(
            ComponentContainerKind containerKind,
            Optional<VerticalSlabAxis> verticalAxis,
            DecodedComponent positive,
            DecodedComponent negative,
            InputFormat inputFormat,
            RecoveryAction recoveryAction
    ) {
    }

    public enum InputFormat {
        VERSIONED,
        LEGACY,
        EMPTY,
        UNSUPPORTED_SCHEMA
    }

    public enum ComponentFailure {
        NONE,
        MISSING_COMPONENT,
        INVALID_IDENTIFIER,
        UNKNOWN_BLOCK,
        UNSUPPORTED_BLOCK,
        MALFORMED_COMPONENT,
        UNKNOWN_SCHEMA,
        CONTAINER_KIND_MISMATCH
    }

    public enum RecoveryAction {
        KEEP_DOUBLE,
        SINGLEIZE_POSITIVE,
        SINGLEIZE_NEGATIVE,
        REPLACE_WITH_AIR
    }
}
