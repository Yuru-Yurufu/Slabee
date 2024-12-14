package com.forestotzka.yurufu.slabee.registry.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    public static final TagKey<Item> VERTICAL_SLABS = TagKey.of(RegistryKeys.ITEM, Identifier.of("slabee", "vertical_slabs"));
    public static final TagKey<Item> SLABS = TagKey.of(RegistryKeys.ITEM, Identifier.of("slabee", "slabs"));

    private ModItemTags() {
    }
}
