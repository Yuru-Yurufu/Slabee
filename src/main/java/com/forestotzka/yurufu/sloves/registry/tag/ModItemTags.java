package com.forestotzka.yurufu.sloves.registry.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    public static final TagKey<Item> VERTICAL_SLABS = TagKey.of(RegistryKeys.ITEM, Identifier.of("sloves", "vertical_slabs"));
    public static final TagKey<Item> SLABS = TagKey.of(RegistryKeys.ITEM, Identifier.of("sloves", "slabs"));
    //public static final TagKey<Item> CUTOUT_SLABS = TagKey.of(RegistryKeys.ITEM, Identifier.of("sloves", "cutout_slabs"));
    //public static final TagKey<Item> CUTOUT_MIPPED_SLABS = TagKey.of(RegistryKeys.ITEM, Identifier.of("sloves", "cutout_mipped_slabs"));
    public static final TagKey<Item> TRANSPARENT_SLABS = TagKey.of(RegistryKeys.ITEM, Identifier.of("sloves", "transparent_slabs"));

    private ModItemTags() {
    }

    private static TagKey<Item> of(String namespace, String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(namespace,id));
    }
}
