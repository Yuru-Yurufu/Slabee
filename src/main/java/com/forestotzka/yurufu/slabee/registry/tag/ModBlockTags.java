package com.forestotzka.yurufu.slabee.registry.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    public static final TagKey<Block> VERTICAL_SLABS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("slabee", "vertical_slabs"));
    public static final TagKey<Block> SLABS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("slabee", "slabs"));
    public static final TagKey<Block> MINEABLE_SHEARS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("slabee", "mineable/shears"));

    private ModBlockTags() {
    }
}
