package com.forestotzka.yurufu.sloves.registry.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    public static final TagKey<Item> VERTICAL_SLABS = TagKey.of(RegistryKeys.ITEM, Identifier.of("sloves", "vertical_slabs"));
    public static final TagKey<Item> SLABS = TagKey.of(RegistryKeys.ITEM, Identifier.of("sloves", "slabs"));

    private ModItemTags() {
    }
}
