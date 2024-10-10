package com.forestotzka.yurufu.sloves;

import com.forestotzka.yurufu.sloves.block.ModBlocks;
import com.forestotzka.yurufu.sloves.registry.tag.ModItemTags;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModCreativeTabs {
    public static final ItemGroup SLABS = Registry.register(Registries.ITEM_GROUP, Sloves.MOD_ID + "_slabs", FabricItemGroup.builder()
            .displayName(Text.translatable("slabs"))
            .icon(Blocks.OAK_SLAB.asItem()::getDefaultStack)
            .entries((displayContext, entries) -> Registries.ITEM.streamEntries()
                    .filter(entry -> entry.isIn(ModItemTags.SLABS))
                    .forEach(entry -> entries.add(entry.value())))
            .build()
    );

    public static final ItemGroup VERTICAL_SLABS = Registry.register(Registries.ITEM_GROUP, Sloves.MOD_ID + "_vertical_slabs", FabricItemGroup.builder()
            .displayName(Text.translatable("vertical_slabs"))
            .icon(ModBlocks.OAK_VERTICAL_SLAB.asItem()::getDefaultStack)
            .entries((displayContext, entries) -> Registries.ITEM.streamEntries()
                    .filter(entry -> entry.isIn(ModItemTags.VERTICAL_SLABS))
                    .forEach(entry -> entries.add(entry.value())))
            .build()
    );

    public static void registerItemGroups() {
        Sloves.LOGGER.info("Registering Item Groups for" + Sloves.MOD_ID);
    }
}
