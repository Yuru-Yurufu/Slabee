package com.forestotzka.yurufu.slabee.structure;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HorizontalSlabDataTest {
    private static ResourceLocation id(String path) { return ResourceLocation.parse("slabee:" + path); }
    @Test void roundTripPreservesDifferentMaterialsAndFacing() {
        var input = new HorizontalSlabData(new HorizontalSlabData.Slot(id("black_concrete_slab"), Direction.WEST),
                new HorizontalSlabData.Slot(id("purple_concrete_slab"), Direction.EAST));
        var tag = new CompoundTag(); input.write(tag);
        assertEquals(input, HorizontalSlabData.read(tag, ignored -> true));
        assertEquals("slabee:black_concrete_slab", tag.getCompound("positive_slab").getString("id"));
    }
    @Test void missingSlotsUseLegacyDefaults() {
        assertEquals(HorizontalSlabData.defaults(), HorizontalSlabData.read(new CompoundTag(), ignored -> true));
    }
    @Test void invalidRegistryEntryDoesNotDiscardPartner() {
        var tag = new CompoundTag(); HorizontalSlabData.defaults().write(tag);
        tag.getCompound("positive_slab").putString("id", "unknown:missing");
        assertEquals(HorizontalSlabData.defaults(), HorizontalSlabData.read(tag, id -> !id.getNamespace().equals("unknown")));
    }
    @Test void unknownFacingFallsBackToSouth() {
        var tag = new CompoundTag(); HorizontalSlabData.defaults().write(tag);
        tag.getCompound("positive_slab").putString("facing", "invalid");
        assertEquals(Direction.SOUTH, HorizontalSlabData.read(tag, ignored -> true).positive().facing());
    }
    @Test void onlyLowerPathBecomesDirt() {
        var input = new HorizontalSlabData(new HorizontalSlabData.Slot(id("dirt_path_slab"), Direction.SOUTH),
                new HorizontalSlabData.Slot(id("dirt_path_slab"), Direction.SOUTH));
        var tag = new CompoundTag(); input.write(tag);
        var output = HorizontalSlabData.read(tag, ignored -> true);
        assertEquals(id("dirt_path_slab"), output.positive().id());
        assertEquals(id("dirt_slab"), output.negative().id());
    }
    @Test void writePreservesBlockEntityMetadata() {
        var tag = new CompoundTag(); tag.putString("id", "slabee:double_slab_block_entity");
        tag.putInt("x", 42); HorizontalSlabData.defaults().write(tag);
        assertEquals("slabee:double_slab_block_entity", tag.getString("id"));
        assertEquals(42, tag.getInt("x"));
    }
}
