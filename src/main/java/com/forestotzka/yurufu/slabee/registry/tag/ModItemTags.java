package com.forestotzka.yurufu.slabee.registry.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> VERTICAL_SLABS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("slabee", "vertical_slabs"));
    public static final TagKey<Item> SLABS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("slabee", "slabs"));

    private ModItemTags() {
    }
}
