package com.forestotzka.yurufu.sloves;

import com.forestotzka.yurufu.sloves.block.ModBlocks;
import com.forestotzka.yurufu.sloves.registry.tag.ModItemTags;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ModCreativeTabs {
    public static final ItemGroup SLABS = Registry.register(Registries.ITEM_GROUP, Sloves.MOD_ID + "_slabs", FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.sloves.slabs"))
            .icon(Blocks.OAK_SLAB.asItem()::getDefaultStack)
            .entries((displayContext, entries) -> {
                List<Item> slabs = new ArrayList<>(Registries.ITEM.streamEntries()
                        .filter(entry -> entry.isIn(ModItemTags.SLABS))
                        .map(RegistryEntry::value)
                        .toList());

                slabs.add(0, Items.OAK_SLAB);
                slabs.add(1, Items.SPRUCE_SLAB);
                slabs.add(2, Items.BIRCH_SLAB);
                slabs.add(3, Items.JUNGLE_SLAB);
                slabs.add(4, Items.ACACIA_SLAB);
                slabs.add(5, Items.DARK_OAK_SLAB);
                slabs.add(6, Items.MANGROVE_SLAB);
                slabs.add(7, Items.CHERRY_SLAB);
                slabs.add(8, Items.BAMBOO_SLAB);
                slabs.add(9, Items.BAMBOO_MOSAIC_SLAB);
                slabs.add(10, Items.CRIMSON_SLAB);
                slabs.add(11, Items.WARPED_SLAB);
                slabs.add(54, Items.STONE_SLAB);
                slabs.add(55, Items.COBBLESTONE_SLAB);
                slabs.add(56, Items.MOSSY_COBBLESTONE_SLAB);
                slabs.add(57, Items.SMOOTH_STONE_SLAB);
                slabs.add(59, Items.STONE_BRICK_SLAB);
                slabs.add(60, Items.MOSSY_STONE_BRICK_SLAB);
                slabs.add(61, Items.GRANITE_SLAB);
                slabs.add(62, Items.POLISHED_GRANITE_SLAB);
                slabs.add(63, Items.DIORITE_SLAB);
                slabs.add(64, Items.POLISHED_DIORITE_SLAB);
                slabs.add(65, Items.ANDESITE_SLAB);
                slabs.add(66, Items.POLISHED_ANDESITE_SLAB);
                slabs.add(68, Items.COBBLED_DEEPSLATE_SLAB);
                slabs.add(69, Items.POLISHED_DEEPSLATE_SLAB);
                slabs.add(71, Items.DEEPSLATE_BRICK_SLAB);
                slabs.add(72, Items.DEEPSLATE_TILE_SLAB);
                slabs.add(74, Items.TUFF_SLAB);
                slabs.add(75, Items.POLISHED_TUFF_SLAB);
                slabs.add(76, Items.TUFF_BRICK_SLAB);
                slabs.add(77, Items.BRICK_SLAB);
                slabs.add(79, Items.MUD_BRICK_SLAB);
                slabs.add(80, Items.SANDSTONE_SLAB);
                slabs.add(81, Items.SMOOTH_SANDSTONE_SLAB);
                slabs.add(82, Items.CUT_SANDSTONE_SLAB);
                slabs.add(83, Items.RED_SANDSTONE_SLAB);
                slabs.add(84, Items.SMOOTH_RED_SANDSTONE_SLAB);
                slabs.add(85, Items.CUT_RED_SANDSTONE_SLAB);
                slabs.add(86, Items.PRISMARINE_SLAB);
                slabs.add(87, Items.PRISMARINE_BRICK_SLAB);
                slabs.add(88, Items.DARK_PRISMARINE_SLAB);
                slabs.add(91, Items.NETHER_BRICK_SLAB);
                slabs.add(92, Items.RED_NETHER_BRICK_SLAB);
                slabs.add(97, Items.BLACKSTONE_SLAB);
                slabs.add(98, Items.POLISHED_BLACKSTONE_SLAB);
                slabs.add(99, Items.POLISHED_BLACKSTONE_BRICK_SLAB);
                slabs.add(102, Items.END_STONE_BRICK_SLAB);
                slabs.add(103, Items.PURPUR_SLAB);
                slabs.add(105, Items.QUARTZ_SLAB);
                slabs.add(108, Items.SMOOTH_QUARTZ_SLAB);
                slabs.add(110, Items.CUT_COPPER_SLAB);
                slabs.add(111, Items.EXPOSED_CUT_COPPER_SLAB);
                slabs.add(112, Items.WEATHERED_CUT_COPPER_SLAB);
                slabs.add(113, Items.OXIDIZED_CUT_COPPER_SLAB);
                slabs.add(114, Items.WAXED_CUT_COPPER_SLAB);
                slabs.add(115, Items.WAXED_EXPOSED_CUT_COPPER_SLAB);
                slabs.add(116, Items.WAXED_WEATHERED_CUT_COPPER_SLAB);
                slabs.add(117, Items.WAXED_OXIDIZED_CUT_COPPER_SLAB);
                slabs.add(118, Items.PETRIFIED_OAK_SLAB);

                for (Item slab : slabs) {
                    entries.add(slab);
                }
            })
            .build()
    );

    public static final ItemGroup VERTICAL_SLABS = Registry.register(Registries.ITEM_GROUP, Sloves.MOD_ID + "_vertical_slabs", FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.sloves.verticalSlabs"))
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
