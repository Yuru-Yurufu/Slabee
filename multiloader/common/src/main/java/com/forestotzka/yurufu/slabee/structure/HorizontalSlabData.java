package com.forestotzka.yurufu.slabee.structure;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Predicate;

/** Horizontal legacy NBT slice extracted from DoubleSlabBlockEntity at 594cbba2. */
public record HorizontalSlabData(Slot positive, Slot negative) {
    public record Slot(ResourceLocation id, Direction facing) {}

    public static HorizontalSlabData defaults() {
        return new HorizontalSlabData(new Slot(id("purple_concrete_slab"), Direction.SOUTH),
                new Slot(id("black_concrete_slab"), Direction.SOUTH));
    }

    public void write(CompoundTag tag) {
        writeSlot(tag, "positive_slab", positive);
        writeSlot(tag, "negative_slab", negative);
    }

    private static void writeSlot(CompoundTag tag, String key, Slot slot) {
        CompoundTag value = new CompoundTag();
        value.putString("id", slot.id().toString());
        value.putString("facing", slot.facing().getName());
        tag.put(key, value);
    }

    public static HorizontalSlabData read(CompoundTag tag, Predicate<ResourceLocation> validSlab) {
        HorizontalSlabData defaults = defaults();
        Slot positive = readSlot(tag, "positive_slab", defaults.positive(), validSlab);
        Slot negative = readSlot(tag, "negative_slab", defaults.negative(), validSlab);
        // Preserve the original lower-half path-to-dirt rule; this is not the B2 redesign.
        if (negative.id().equals(id("dirt_path_slab"))) {
            negative = new Slot(id("dirt_slab"), negative.facing());
        }
        return new HorizontalSlabData(positive, negative);
    }

    private static Slot readSlot(CompoundTag tag, String key, Slot fallback,
                                 Predicate<ResourceLocation> validSlab) {
        if (!tag.contains(key)) return fallback;
        CompoundTag value = tag.getCompound(key);
        ResourceLocation id = ResourceLocation.parse(value.getString("id"));
        Direction facing = Direction.byName(value.getString("facing"));
        return new Slot(validSlab.test(id) ? id : fallback.id(),
                facing == null ? Direction.SOUTH : facing);
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath("slabee", path);
    }
}
