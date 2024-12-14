package com.forestotzka.yurufu.slabee.registry.tag;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {
    public static final TagKey<Block> VERTICAL_SLABS = TagKey.of(RegistryKeys.BLOCK, Identifier.of("slabee", "vertical_slabs"));
    public static final TagKey<Block> SLABS = TagKey.of(RegistryKeys.BLOCK, Identifier.of("slabee", "slabs"));
    public static final TagKey<Block> CUTOUT_SLABS = TagKey.of(RegistryKeys.BLOCK, Identifier.of("slabee", "cutout_slabs"));
    public static final TagKey<Block> CUTOUT_MIPPED_SLABS = TagKey.of(RegistryKeys.BLOCK, Identifier.of("slabee", "cutout_mipped_slabs"));
    public static final TagKey<Block> TRANSPARENT_SLABS = TagKey.of(RegistryKeys.BLOCK, Identifier.of("slabee", "transparent_slabs"));
    public static final TagKey<Block> MINEABLE_SHEARS = TagKey.of(RegistryKeys.BLOCK, Identifier.of("slabee", "mineable/shears"));

    private ModBlockTags() {
    }
}
