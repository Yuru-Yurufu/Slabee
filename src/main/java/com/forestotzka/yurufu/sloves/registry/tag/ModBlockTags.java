package com.forestotzka.yurufu.sloves.registry.tag;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {
    public static final TagKey<Block> VERTICAL_SLABS = TagKey.of(RegistryKeys.BLOCK, Identifier.of("sloves", "vertical_slabs"));
    public static final TagKey<Block> SLABS = TagKey.of(RegistryKeys.BLOCK, Identifier.of("sloves", "slabs"));
    public static final TagKey<Block> CUTOUT_SLABS = TagKey.of(RegistryKeys.BLOCK, Identifier.of("sloves", "cutout_slabs"));
    public static final TagKey<Block> CUTOUT_MIPPED_SLABS = TagKey.of(RegistryKeys.BLOCK, Identifier.of("sloves", "cutout_mipped_slabs"));
    public static final TagKey<Block> TRANSPARENT_SLABS = TagKey.of(RegistryKeys.BLOCK, Identifier.of("sloves", "transparent_slabs"));

    private ModBlockTags() {
    }

    private static TagKey<Block> of(String namespace, String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(namespace,id));
    }
}
