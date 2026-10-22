package com.forestotzka.yurufu.slabee.block.persistence;

import com.forestotzka.yurufu.slabee.block.ModBlockMap;
import com.forestotzka.yurufu.slabee.block.VerticalSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Property;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Explicit allow-list for component BlockState persistence.
 *
 * <p>The built-in factory registers only blocks present in Slabee's existing
 * {@link ModBlockMap}. A block supplied by another mod is rejected until its
 * adapter is explicitly registered.</p>
 */
public final class ComponentStateAdapterRegistry {
    private static final Set<String> SLOT_DERIVED_PROPERTIES = Set.of("type", "facing", "is_double");
    private static final Set<String> CONTAINER_OWNED_PROPERTIES = Set.of("axis");
    private static final Set<String> STANDARD_ENVIRONMENT_DERIVED_PROPERTIES = Set.of(
            "snowy",
            "distance",
            "waterlogged"
    );

    private final Map<Block, Adapter> adapters = new IdentityHashMap<>();

    public static ComponentStateAdapterRegistry createForSlabeeMappings() {
        ComponentStateAdapterRegistry registry = new ComponentStateAdapterRegistry();

        // Initialize the explicit BlockTriple table before iterating the block registry.
        ModBlockMap.toOriginal(Blocks.AIR);
        for (Block block : Registries.BLOCK) {
            if (block instanceof SlabBlock && ModBlockMap.slabToOriginal(block) != null) {
                registry.register(Adapter.horizontal(block, Set.of(), Set.of()));
            } else if (block instanceof VerticalSlabBlock && ModBlockMap.verticalSlabToOriginal(block) != null) {
                registry.register(Adapter.vertical(block, Set.of(), Set.of()));
            }
        }

        return registry;
    }

    public ComponentStateAdapterRegistry register(Adapter adapter) {
        Objects.requireNonNull(adapter, "adapter");
        Adapter previous = adapters.putIfAbsent(adapter.block(), adapter);
        if (previous != null && !previous.equals(adapter)) {
            throw new IllegalArgumentException("A component-state adapter is already registered for "
                    + Registries.BLOCK.getId(adapter.block()));
        }
        return this;
    }

    public ComponentStateAdapterRegistry replace(Adapter adapter) {
        Objects.requireNonNull(adapter, "adapter");
        adapters.put(adapter.block(), adapter);
        return this;
    }

    public Optional<Adapter> find(Block block, ComponentContainerKind expectedKind) {
        Adapter adapter = adapters.get(block);
        if (adapter == null || adapter.containerKind() != expectedKind) {
            return Optional.empty();
        }
        return Optional.of(adapter);
    }

    public int size() {
        return adapters.size();
    }

    public enum PropertyOwnership {
        COMPONENT,
        SLOT,
        CONTAINER,
        ENVIRONMENT,
        UNSUPPORTED
    }

    public record Adapter(
            Block block,
            ComponentContainerKind containerKind,
            Set<String> componentOwnedProperties,
            Set<String> environmentDerivedProperties
    ) {
        public Adapter {
            Objects.requireNonNull(block, "block");
            Objects.requireNonNull(containerKind, "containerKind");
            componentOwnedProperties = Set.copyOf(componentOwnedProperties);
            environmentDerivedProperties = Set.copyOf(environmentDerivedProperties);

            if (containerKind == ComponentContainerKind.HORIZONTAL && !(block instanceof SlabBlock)) {
                throw new IllegalArgumentException("Horizontal adapters require a SlabBlock");
            }
            if (containerKind == ComponentContainerKind.VERTICAL && !(block instanceof VerticalSlabBlock)) {
                throw new IllegalArgumentException("Vertical adapters require a VerticalSlabBlock");
            }
            if (!java.util.Collections.disjoint(componentOwnedProperties, environmentDerivedProperties)) {
                throw new IllegalArgumentException("A property cannot be both component-owned and environment-derived");
            }

            for (String propertyName : componentOwnedProperties) {
                if (SLOT_DERIVED_PROPERTIES.contains(propertyName)
                        || CONTAINER_OWNED_PROPERTIES.contains(propertyName)
                        || STANDARD_ENVIRONMENT_DERIVED_PROPERTIES.contains(propertyName)) {
                    throw new IllegalArgumentException("Derived property cannot be registered as component-owned: "
                            + propertyName);
                }
                requireProperty(block, propertyName, "component-owned");
            }
            for (String propertyName : environmentDerivedProperties) {
                if (SLOT_DERIVED_PROPERTIES.contains(propertyName)
                        || CONTAINER_OWNED_PROPERTIES.contains(propertyName)) {
                    throw new IllegalArgumentException("Slot/container property cannot be environment-derived: "
                            + propertyName);
                }
                requireProperty(block, propertyName, "environment-derived");
            }
        }

        public static Adapter horizontal(
                Block block,
                Set<String> componentOwnedProperties,
                Set<String> environmentDerivedProperties
        ) {
            return new Adapter(
                    block,
                    ComponentContainerKind.HORIZONTAL,
                    componentOwnedProperties,
                    environmentDerivedProperties
            );
        }

        public static Adapter vertical(
                Block block,
                Set<String> componentOwnedProperties,
                Set<String> environmentDerivedProperties
        ) {
            return new Adapter(
                    block,
                    ComponentContainerKind.VERTICAL,
                    componentOwnedProperties,
                    environmentDerivedProperties
            );
        }

        public PropertyOwnership ownershipOf(String propertyName) {
            if (componentOwnedProperties.contains(propertyName)) {
                return PropertyOwnership.COMPONENT;
            }
            if (SLOT_DERIVED_PROPERTIES.contains(propertyName)) {
                return PropertyOwnership.SLOT;
            }
            if (CONTAINER_OWNED_PROPERTIES.contains(propertyName)) {
                return PropertyOwnership.CONTAINER;
            }
            if (environmentDerivedProperties.contains(propertyName)
                    || STANDARD_ENVIRONMENT_DERIVED_PROPERTIES.contains(propertyName)) {
                return PropertyOwnership.ENVIRONMENT;
            }
            return PropertyOwnership.UNSUPPORTED;
        }

        private static void requireProperty(Block block, String propertyName, String ownership) {
            Property<?> property = block.getStateManager().getProperty(propertyName);
            if (property == null) {
                throw new IllegalArgumentException("Block " + Registries.BLOCK.getId(block)
                        + " has no " + ownership + " property named " + propertyName);
            }
        }
    }
}
