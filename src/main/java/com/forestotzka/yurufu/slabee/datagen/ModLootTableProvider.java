package com.forestotzka.yurufu.slabee.datagen;

import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        /*addDrop(ModBlocks.OAK_LOG_SLAB);
        addDrop(ModBlocks.SPRUCE_LOG_SLAB);
        addDrop(ModBlocks.BIRCH_LOG_SLAB);
        addDrop(ModBlocks.JUNGLE_LOG_SLAB);
        addDrop(ModBlocks.ACACIA_LOG_SLAB);
        addDrop(ModBlocks.DARK_OAK_LOG_SLAB);
        addDrop(ModBlocks.MANGROVE_LOG_SLAB);
        addDrop(ModBlocks.CHERRY_LOG_SLAB);
        addDrop(ModBlocks.BAMBOO_BLOCK_SLAB);
        addDrop(ModBlocks.CRIMSON_STEM_SLAB);
        addDrop(ModBlocks.WARPED_STEM_SLAB);
        addDrop(ModBlocks.OAK_WOOD_SLAB);
        addDrop(ModBlocks.SPRUCE_WOOD_SLAB);
        addDrop(ModBlocks.BIRCH_WOOD_SLAB);
        addDrop(ModBlocks.JUNGLE_WOOD_SLAB);
        addDrop(ModBlocks.ACACIA_WOOD_SLAB);
        addDrop(ModBlocks.DARK_OAK_WOOD_SLAB);
        addDrop(ModBlocks.MANGROVE_WOOD_SLAB);
        addDrop(ModBlocks.CHERRY_WOOD_SLAB);
        addDrop(ModBlocks.CRIMSON_HYPHAE_SLAB);
        addDrop(ModBlocks.WARPED_HYPHAE_SLAB);
        addDrop(ModBlocks.STRIPPED_OAK_LOG_SLAB);
        addDrop(ModBlocks.STRIPPED_SPRUCE_LOG_SLAB);
        addDrop(ModBlocks.STRIPPED_BIRCH_LOG_SLAB);
        addDrop(ModBlocks.STRIPPED_JUNGLE_LOG_SLAB);
        addDrop(ModBlocks.STRIPPED_ACACIA_LOG_SLAB);
        addDrop(ModBlocks.STRIPPED_DARK_OAK_LOG_SLAB);
        addDrop(ModBlocks.STRIPPED_MANGROVE_LOG_SLAB);
        addDrop(ModBlocks.STRIPPED_CHERRY_LOG_SLAB);
        addDrop(ModBlocks.STRIPPED_BAMBOO_SLAB);
        addDrop(ModBlocks.STRIPPED_CRIMSON_STEM_SLAB);
        addDrop(ModBlocks.STRIPPED_WARPED_STEM_SLAB);
        addDrop(ModBlocks.STRIPPED_OAK_WOOD_SLAB);
        addDrop(ModBlocks.STRIPPED_SPRUCE_WOOD_SLAB);
        addDrop(ModBlocks.STRIPPED_BIRCH_WOOD_SLAB);
        addDrop(ModBlocks.STRIPPED_JUNGLE_WOOD_SLAB);
        addDrop(ModBlocks.STRIPPED_ACACIA_WOOD_SLAB);
        addDrop(ModBlocks.STRIPPED_DARK_OAK_WOOD_SLAB);
        addDrop(ModBlocks.STRIPPED_MANGROVE_WOOD_SLAB);
        addDrop(ModBlocks.STRIPPED_CHERRY_WOOD_SLAB);
        addDrop(ModBlocks.STRIPPED_CRIMSON_HYPHAE_SLAB);
        addDrop(ModBlocks.STRIPPED_WARPED_HYPHAE_SLAB);
        addDrop(ModBlocks.CRACKED_STONE_BRICK_SLAB);
        addDrop(ModBlocks.DEEPSLATE_SLAB);
        addDrop(ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB);
        addDrop(ModBlocks.REINFORCED_DEEPSLATE_SLAB);
        addDrop(ModBlocks.PACKED_MUD_SLAB);
        addDrop(ModBlocks.NETHERRACK_SLAB);
        addDrop(ModBlocks.CRACKED_NETHER_BRICK_SLAB);
        addDrop(ModBlocks.BASALT_SLAB);
        addDrop(ModBlocks.SMOOTH_BASALT_SLAB);
        addDrop(ModBlocks.POLISHED_BASALT_SLAB);
        addDrop(ModBlocks.GILDED_BLACKSTONE_SLAB);
        addDrop(ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB);
        addDrop(ModBlocks.END_STONE_SLAB);
        addDrop(ModBlocks.PURPUR_PILLAR_SLAB);
        addDrop(ModBlocks.QUARTZ_BRICK_SLAB);
        addDrop(ModBlocks.QUARTZ_PILLAR_SLAB);
        addDrop(ModBlocks.AMETHYST_SLAB);
        addDrop(ModBlocks.WHITE_WOOL_SLAB);
        addDrop(ModBlocks.LIGHT_GRAY_WOOL_SLAB);
        addDrop(ModBlocks.GRAY_WOOL_SLAB);
        addDrop(ModBlocks.BLACK_WOOL_SLAB);
        addDrop(ModBlocks.BROWN_WOOL_SLAB);
        addDrop(ModBlocks.RED_WOOL_SLAB);
        addDrop(ModBlocks.ORANGE_WOOL_SLAB);
        addDrop(ModBlocks.YELLOW_WOOL_SLAB);
        addDrop(ModBlocks.LIME_WOOL_SLAB);
        addDrop(ModBlocks.GREEN_WOOL_SLAB);
        addDrop(ModBlocks.CYAN_WOOL_SLAB);
        addDrop(ModBlocks.LIGHT_BLUE_WOOL_SLAB);
        addDrop(ModBlocks.BLUE_WOOL_SLAB);
        addDrop(ModBlocks.PURPLE_WOOL_SLAB);
        addDrop(ModBlocks.MAGENTA_WOOL_SLAB);
        addDrop(ModBlocks.PINK_WOOL_SLAB);
        addDrop(ModBlocks.TERRACOTTA_SLAB);
        addDrop(ModBlocks.WHITE_TERRACOTTA_SLAB);
        addDrop(ModBlocks.LIGHT_GRAY_TERRACOTTA_SLAB);
        addDrop(ModBlocks.GRAY_TERRACOTTA_SLAB);
        addDrop(ModBlocks.BLACK_TERRACOTTA_SLAB);
        addDrop(ModBlocks.BROWN_TERRACOTTA_SLAB);
        addDrop(ModBlocks.RED_TERRACOTTA_SLAB);
        addDrop(ModBlocks.ORANGE_TERRACOTTA_SLAB);
        addDrop(ModBlocks.YELLOW_TERRACOTTA_SLAB);
        addDrop(ModBlocks.LIME_TERRACOTTA_SLAB);
        addDrop(ModBlocks.GREEN_TERRACOTTA_SLAB);
        addDrop(ModBlocks.CYAN_TERRACOTTA_SLAB);
        addDrop(ModBlocks.LIGHT_BLUE_TERRACOTTA_SLAB);
        addDrop(ModBlocks.BLUE_TERRACOTTA_SLAB);
        addDrop(ModBlocks.PURPLE_TERRACOTTA_SLAB);
        addDrop(ModBlocks.MAGENTA_TERRACOTTA_SLAB);
        addDrop(ModBlocks.PINK_TERRACOTTA_SLAB);
        addDrop(ModBlocks.WHITE_CONCRETE_SLAB);
        addDrop(ModBlocks.LIGHT_GRAY_CONCRETE_SLAB);
        addDrop(ModBlocks.GRAY_CONCRETE_SLAB);
        addDrop(ModBlocks.BLACK_CONCRETE_SLAB);
        addDrop(ModBlocks.BROWN_CONCRETE_SLAB);
        addDrop(ModBlocks.RED_CONCRETE_SLAB);
        addDrop(ModBlocks.ORANGE_CONCRETE_SLAB);
        addDrop(ModBlocks.YELLOW_CONCRETE_SLAB);
        addDrop(ModBlocks.LIME_CONCRETE_SLAB);
        addDrop(ModBlocks.GREEN_CONCRETE_SLAB);
        addDrop(ModBlocks.CYAN_CONCRETE_SLAB);
        addDrop(ModBlocks.LIGHT_BLUE_CONCRETE_SLAB);
        addDrop(ModBlocks.BLUE_CONCRETE_SLAB);
        addDrop(ModBlocks.PURPLE_CONCRETE_SLAB);
        addDrop(ModBlocks.MAGENTA_CONCRETE_SLAB);
        addDrop(ModBlocks.PINK_CONCRETE_SLAB);
        addDrop(ModBlocks.WHITE_CONCRETE_POWDER_SLAB);
        addDrop(ModBlocks.LIGHT_GRAY_CONCRETE_POWDER_SLAB);
        addDrop(ModBlocks.GRAY_CONCRETE_POWDER_SLAB);
        addDrop(ModBlocks.BLACK_CONCRETE_POWDER_SLAB);
        addDrop(ModBlocks.BROWN_CONCRETE_POWDER_SLAB);
        addDrop(ModBlocks.RED_CONCRETE_POWDER_SLAB);
        addDrop(ModBlocks.ORANGE_CONCRETE_POWDER_SLAB);
        addDrop(ModBlocks.YELLOW_CONCRETE_POWDER_SLAB);
        addDrop(ModBlocks.LIME_CONCRETE_POWDER_SLAB);
        addDrop(ModBlocks.GREEN_CONCRETE_POWDER_SLAB);
        addDrop(ModBlocks.CYAN_CONCRETE_POWDER_SLAB);
        addDrop(ModBlocks.LIGHT_BLUE_CONCRETE_POWDER_SLAB);
        addDrop(ModBlocks.BLUE_CONCRETE_POWDER_SLAB);
        addDrop(ModBlocks.PURPLE_CONCRETE_POWDER_SLAB);
        addDrop(ModBlocks.MAGENTA_CONCRETE_POWDER_SLAB);
        addDrop(ModBlocks.PINK_CONCRETE_POWDER_SLAB);*/
        addDrop(ModBlocks.GLASS_SLAB);
        addDrop(ModBlocks.TINTED_GLASS_SLAB);
        addDrop(ModBlocks.WHITE_STAINED_GLASS_SLAB);
        addDrop(ModBlocks.LIGHT_GRAY_STAINED_GLASS_SLAB);
        addDrop(ModBlocks.GRAY_STAINED_GLASS_SLAB);
        addDrop(ModBlocks.BLACK_STAINED_GLASS_SLAB);
        addDrop(ModBlocks.BROWN_STAINED_GLASS_SLAB);
        addDrop(ModBlocks.RED_STAINED_GLASS_SLAB);
        addDrop(ModBlocks.ORANGE_STAINED_GLASS_SLAB);
        addDrop(ModBlocks.YELLOW_STAINED_GLASS_SLAB);
        addDrop(ModBlocks.LIME_STAINED_GLASS_SLAB);
        addDrop(ModBlocks.GREEN_STAINED_GLASS_SLAB);
        addDrop(ModBlocks.CYAN_STAINED_GLASS_SLAB);
        addDrop(ModBlocks.LIGHT_BLUE_STAINED_GLASS_SLAB);
        addDrop(ModBlocks.BLUE_STAINED_GLASS_SLAB);
        addDrop(ModBlocks.PURPLE_STAINED_GLASS_SLAB);
        addDrop(ModBlocks.MAGENTA_STAINED_GLASS_SLAB);
        addDrop(ModBlocks.PINK_STAINED_GLASS_SLAB);
        /*addDrop(ModBlocks.GRASS_SLAB);
        addDrop(ModBlocks.PODZOL_SLAB);
        addDrop(ModBlocks.MYCELIUM_SLAB);
        addDrop(ModBlocks.DIRT_SLAB);
        addDrop(ModBlocks.DIRT_PATH_SLAB);
        addDrop(ModBlocks.COARSE_DIRT_SLAB);
        addDrop(ModBlocks.ROOTED_DIRT_SLAB);
        addDrop(ModBlocks.MUD_SLAB);
        addDrop(ModBlocks.CLAY_SLAB);
        addDrop(ModBlocks.GRAVEL_SLAB);
        addDrop(ModBlocks.SAND_SLAB);
        addDrop(ModBlocks.RED_SAND_SLAB);
        addDrop(ModBlocks.ICE_SLAB);
        addDrop(ModBlocks.PACKED_ICE_SLAB);
        addDrop(ModBlocks.BLUE_ICE_SLAB);
        addDrop(ModBlocks.MOSS_SLAB);
        addDrop(ModBlocks.CALCITE_SLAB);
        addDrop(ModBlocks.DRIPSTONE_SLAB);
        addDrop(ModBlocks.MAGMA_BLOCK_SLAB);
        addDrop(ModBlocks.OBSIDIAN_SLAB);
        addDrop(ModBlocks.CRYING_OBSIDIAN_SLAB);
        addDrop(ModBlocks.CRIMSON_NYLIUM_SLAB);
        addDrop(ModBlocks.WARPED_NYLIUM_SLAB);
        addDrop(ModBlocks.SOUL_SAND_SLAB);
        addDrop(ModBlocks.SOUL_SOIL_SLAB);
        addDrop(ModBlocks.BONE_SLAB);*/
        addDrop(ModBlocks.GLOWSTONE_SLAB);
        /*addDrop(ModBlocks.MANGROVE_ROOT_SLAB);
        addDrop(ModBlocks.MUDDY_MANGROVE_ROOT_SLAB);
        addDrop(ModBlocks.MUSHROOM_STEM_SLAB);
        addDrop(ModBlocks.OAK_LEAF_SLAB);
        addDrop(ModBlocks.SPRUCE_LEAF_SLAB);
        addDrop(ModBlocks.BIRCH_LEAF_SLAB);
        addDrop(ModBlocks.JUNGLE_LEAF_SLAB);
        addDrop(ModBlocks.ACACIA_LEAF_SLAB);
        addDrop(ModBlocks.DARK_OAK_LEAF_SLAB);
        addDrop(ModBlocks.MANGROVE_LEAF_SLAB);
        addDrop(ModBlocks.CHERRY_LEAF_SLAB);
        addDrop(ModBlocks.AZALEA_LEAF_SLAB);
        addDrop(ModBlocks.FLOWERING_AZALEA_LEAF_SLAB);
        addDrop(ModBlocks.BROWN_MUSHROOM_SLAB);
        addDrop(ModBlocks.RED_MUSHROOM_SLAB);
        addDrop(ModBlocks.NETHER_WART_SLAB);
        addDrop(ModBlocks.WARPED_WART_SLAB);
        addDrop(ModBlocks.DRIED_KELP_SLAB);
        addDrop(ModBlocks.TUBE_CORAL_SLAB);
        addDrop(ModBlocks.BRAIN_CORAL_SLAB);
        addDrop(ModBlocks.BUBBLE_CORAL_SLAB);
        addDrop(ModBlocks.FIRE_CORAL_SLAB);
        addDrop(ModBlocks.HORN_CORAL_SLAB);
        addDrop(ModBlocks.DEAD_TUBE_CORAL_SLAB);
        addDrop(ModBlocks.DEAD_BRAIN_CORAL_SLAB);
        addDrop(ModBlocks.DEAD_BUBBLE_CORAL_SLAB);
        addDrop(ModBlocks.DEAD_FIRE_CORAL_SLAB);
        addDrop(ModBlocks.DEAD_HORN_CORAL_SLAB);
        addDrop(ModBlocks.SPONGE_SLAB);
        addDrop(ModBlocks.WET_SPONGE_SLAB);
        addDrop(ModBlocks.MELON_SLAB);
        addDrop(ModBlocks.PUMPKIN_SLAB);
        addDrop(ModBlocks.HAY_SLAB);
        addDrop(ModBlocks.HONEYCOMB_SLAB);
        addDrop(ModBlocks.SCULK_SLAB);*/

        /*addDrop(ModBlocks.OAK_VERTICAL_SLAB);
        addDrop(ModBlocks.SPRUCE_VERTICAL_SLAB);
        addDrop(ModBlocks.BIRCH_VERTICAL_SLAB);
        addDrop(ModBlocks.JUNGLE_VERTICAL_SLAB);
        addDrop(ModBlocks.ACACIA_VERTICAL_SLAB);
        addDrop(ModBlocks.DARK_OAK_VERTICAL_SLAB);
        addDrop(ModBlocks.MANGROVE_VERTICAL_SLAB);
        addDrop(ModBlocks.CHERRY_VERTICAL_SLAB);
        addDrop(ModBlocks.BAMBOO_VERTICAL_SLAB);
        addDrop(ModBlocks.BAMBOO_MOSAIC_VERTICAL_SLAB);
        addDrop(ModBlocks.CRIMSON_VERTICAL_SLAB);
        addDrop(ModBlocks.WARPED_VERTICAL_SLAB);
        addDrop(ModBlocks.OAK_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.SPRUCE_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.BIRCH_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.JUNGLE_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.ACACIA_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.DARK_OAK_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.MANGROVE_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.CHERRY_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.BAMBOO_BLOCK_VERTICAL_SLAB);
        addDrop(ModBlocks.CRIMSON_STEM_VERTICAL_SLAB);
        addDrop(ModBlocks.WARPED_STEM_VERTICAL_SLAB);
        addDrop(ModBlocks.OAK_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.SPRUCE_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.BIRCH_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.JUNGLE_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.ACACIA_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.DARK_OAK_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.MANGROVE_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.CHERRY_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.CRIMSON_HYPHAE_VERTICAL_SLAB);
        addDrop(ModBlocks.WARPED_HYPHAE_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_OAK_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_SPRUCE_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_BIRCH_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_JUNGLE_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_ACACIA_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_DARK_OAK_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_MANGROVE_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_CHERRY_LOG_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_BAMBOO_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_CRIMSON_STEM_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_WARPED_STEM_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_OAK_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_SPRUCE_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_BIRCH_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_JUNGLE_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_ACACIA_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_DARK_OAK_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_MANGROVE_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_CHERRY_WOOD_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_CRIMSON_HYPHAE_VERTICAL_SLAB);
        addDrop(ModBlocks.STRIPPED_WARPED_HYPHAE_VERTICAL_SLAB);
        addDrop(ModBlocks.STONE_VERTICAL_SLAB);
        addDrop(ModBlocks.COBBLESTONE_VERTICAL_SLAB);
        addDrop(ModBlocks.MOSSY_COBBLESTONE_VERTICAL_SLAB);
        addDrop(ModBlocks.SMOOTH_STONE_VERTICAL_SLAB);
        addDrop(ModBlocks.STONE_BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.CRACKED_STONE_BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.MOSSY_STONE_BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.GRANITE_VERTICAL_SLAB);
        addDrop(ModBlocks.POLISHED_GRANITE_VERTICAL_SLAB);
        addDrop(ModBlocks.DIORITE_VERTICAL_SLAB);
        addDrop(ModBlocks.POLISHED_DIORITE_VERTICAL_SLAB);
        addDrop(ModBlocks.ANDESITE_VERTICAL_SLAB);
        addDrop(ModBlocks.POLISHED_ANDESITE_VERTICAL_SLAB);
        addDrop(ModBlocks.DEEPSLATE_VERTICAL_SLAB);
        addDrop(ModBlocks.COBBLED_DEEPSLATE_VERTICAL_SLAB);
        addDrop(ModBlocks.POLISHED_DEEPSLATE_VERTICAL_SLAB);
        addDrop(ModBlocks.DEEPSLATE_BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.CRACKED_DEEPSLATE_BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.DEEPSLATE_TILE_VERTICAL_SLAB);
        addDrop(ModBlocks.REINFORCED_DEEPSLATE_VERTICAL_SLAB);
        addDrop(ModBlocks.TUFF_VERTICAL_SLAB);
        addDrop(ModBlocks.POLISHED_TUFF_VERTICAL_SLAB);
        addDrop(ModBlocks.TUFF_BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.PACKED_MUD_VERTICAL_SLAB);
        addDrop(ModBlocks.MUD_BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.SANDSTONE_VERTICAL_SLAB);
        addDrop(ModBlocks.SMOOTH_SANDSTONE_VERTICAL_SLAB);
        addDrop(ModBlocks.CUT_SANDSTONE_VERTICAL_SLAB);
        addDrop(ModBlocks.RED_SANDSTONE_VERTICAL_SLAB);
        addDrop(ModBlocks.SMOOTH_RED_SANDSTONE_VERTICAL_SLAB);
        addDrop(ModBlocks.CUT_RED_SANDSTONE_VERTICAL_SLAB);
        addDrop(ModBlocks.PRISMARINE_VERTICAL_SLAB);
        addDrop(ModBlocks.PRISMARINE_BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.DARK_PRISMARINE_VERTICAL_SLAB);
        addDrop(ModBlocks.NETHERRACK_VERTICAL_SLAB);
        addDrop(ModBlocks.NETHER_BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.CRACKED_NETHER_BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.RED_NETHER_BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.BASALT_VERTICAL_SLAB);
        addDrop(ModBlocks.SMOOTH_BASALT_VERTICAL_SLAB);
        addDrop(ModBlocks.POLISHED_BASALT_VERTICAL_SLAB);
        addDrop(ModBlocks.BLACKSTONE_VERTICAL_SLAB);
        addDrop(ModBlocks.GILDED_BLACKSTONE_VERTICAL_SLAB);
        addDrop(ModBlocks.POLISHED_BLACKSTONE_VERTICAL_SLAB);
        addDrop(ModBlocks.POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.END_STONE_VERTICAL_SLAB);
        addDrop(ModBlocks.END_STONE_BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.PURPUR_VERTICAL_SLAB);
        addDrop(ModBlocks.PURPUR_PILLAR_VERTICAL_SLAB);
        addDrop(ModBlocks.QUARTZ_VERTICAL_SLAB);
        addDrop(ModBlocks.QUARTZ_BRICK_VERTICAL_SLAB);
        addDrop(ModBlocks.QUARTZ_PILLAR_VERTICAL_SLAB);
        addDrop(ModBlocks.SMOOTH_QUARTZ_VERTICAL_SLAB);
        addDrop(ModBlocks.AMETHYST_VERTICAL_SLAB);
        addDrop(ModBlocks.CUT_COPPER_VERTICAL_SLAB);
        addDrop(ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        addDrop(ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        addDrop(ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB);
        addDrop(ModBlocks.WAXED_CUT_COPPER_VERTICAL_SLAB);
        addDrop(ModBlocks.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        addDrop(ModBlocks.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        addDrop(ModBlocks.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB);
        addDrop(ModBlocks.PETRIFIED_OAK_VERTICAL_SLAB);
        addDrop(ModBlocks.WHITE_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.LIGHT_GRAY_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.GRAY_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.BLACK_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.BROWN_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.RED_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.ORANGE_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.YELLOW_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.LIME_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.GREEN_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.CYAN_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.LIGHT_BLUE_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.BLUE_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.PURPLE_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.MAGENTA_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.PINK_WOOL_VERTICAL_SLAB);
        addDrop(ModBlocks.TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.WHITE_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.LIGHT_GRAY_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.GRAY_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.BLACK_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.BROWN_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.RED_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.ORANGE_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.YELLOW_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.LIME_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.GREEN_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.CYAN_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.LIGHT_BLUE_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.BLUE_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.PURPLE_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.MAGENTA_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.PINK_TERRACOTTA_VERTICAL_SLAB);
        addDrop(ModBlocks.WHITE_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.LIGHT_GRAY_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.GRAY_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.BLACK_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.BROWN_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.RED_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.ORANGE_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.YELLOW_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.LIME_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.GREEN_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.CYAN_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.LIGHT_BLUE_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.BLUE_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.PURPLE_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.MAGENTA_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.PINK_CONCRETE_VERTICAL_SLAB);
        addDrop(ModBlocks.WHITE_CONCRETE_POWDER_VERTICAL_SLAB);
        addDrop(ModBlocks.LIGHT_GRAY_CONCRETE_POWDER_VERTICAL_SLAB);
        addDrop(ModBlocks.GRAY_CONCRETE_POWDER_VERTICAL_SLAB);
        addDrop(ModBlocks.BLACK_CONCRETE_POWDER_VERTICAL_SLAB);
        addDrop(ModBlocks.BROWN_CONCRETE_POWDER_VERTICAL_SLAB);
        addDrop(ModBlocks.RED_CONCRETE_POWDER_VERTICAL_SLAB);
        addDrop(ModBlocks.ORANGE_CONCRETE_POWDER_VERTICAL_SLAB);
        addDrop(ModBlocks.YELLOW_CONCRETE_POWDER_VERTICAL_SLAB);
        addDrop(ModBlocks.LIME_CONCRETE_POWDER_VERTICAL_SLAB);
        addDrop(ModBlocks.GREEN_CONCRETE_POWDER_VERTICAL_SLAB);
        addDrop(ModBlocks.CYAN_CONCRETE_POWDER_VERTICAL_SLAB);
        addDrop(ModBlocks.LIGHT_BLUE_CONCRETE_POWDER_VERTICAL_SLAB);
        addDrop(ModBlocks.BLUE_CONCRETE_POWDER_VERTICAL_SLAB);
        addDrop(ModBlocks.PURPLE_CONCRETE_POWDER_VERTICAL_SLAB);
        addDrop(ModBlocks.MAGENTA_CONCRETE_POWDER_VERTICAL_SLAB);
        addDrop(ModBlocks.PINK_CONCRETE_POWDER_VERTICAL_SLAB);*/
        addDrop(ModBlocks.GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.TINTED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.WHITE_STAINED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.GRAY_STAINED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.BLACK_STAINED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.BROWN_STAINED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.RED_STAINED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.ORANGE_STAINED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.YELLOW_STAINED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.LIME_STAINED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.GREEN_STAINED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.CYAN_STAINED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.BLUE_STAINED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.PURPLE_STAINED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.MAGENTA_STAINED_GLASS_VERTICAL_SLAB);
        addDrop(ModBlocks.PINK_STAINED_GLASS_VERTICAL_SLAB);
        /*addDrop(ModBlocks.GRASS_VERTICAL_SLAB);
        addDrop(ModBlocks.PODZOL_VERTICAL_SLAB);
        addDrop(ModBlocks.MYCELIUM_VERTICAL_SLAB);
        addDrop(ModBlocks.DIRT_VERTICAL_SLAB);
        addDrop(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
        addDrop(ModBlocks.COARSE_DIRT_VERTICAL_SLAB);
        addDrop(ModBlocks.ROOTED_DIRT_VERTICAL_SLAB);
        addDrop(ModBlocks.MUD_VERTICAL_SLAB);
        addDrop(ModBlocks.CLAY_VERTICAL_SLAB);
        addDrop(ModBlocks.GRAVEL_VERTICAL_SLAB);
        addDrop(ModBlocks.SAND_VERTICAL_SLAB);
        addDrop(ModBlocks.RED_SAND_VERTICAL_SLAB);
        addDrop(ModBlocks.ICE_VERTICAL_SLAB);
        addDrop(ModBlocks.PACKED_ICE_VERTICAL_SLAB);
        addDrop(ModBlocks.BLUE_ICE_VERTICAL_SLAB);
        addDrop(ModBlocks.MOSS_VERTICAL_SLAB);
        addDrop(ModBlocks.CALCITE_VERTICAL_SLAB);
        addDrop(ModBlocks.DRIPSTONE_VERTICAL_SLAB);
        addDrop(ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB);
        addDrop(ModBlocks.OBSIDIAN_VERTICAL_SLAB);
        addDrop(ModBlocks.CRYING_OBSIDIAN_VERTICAL_SLAB);
        addDrop(ModBlocks.CRIMSON_NYLIUM_VERTICAL_SLAB);
        addDrop(ModBlocks.WARPED_NYLIUM_VERTICAL_SLAB);
        addDrop(ModBlocks.SOUL_SAND_VERTICAL_SLAB);
        addDrop(ModBlocks.SOUL_SOIL_VERTICAL_SLAB);
        addDrop(ModBlocks.BONE_VERTICAL_SLAB);*/
        addDrop(ModBlocks.GLOWSTONE_VERTICAL_SLAB);
        /*addDrop(ModBlocks.MANGROVE_ROOT_VERTICAL_SLAB);
        addDrop(ModBlocks.MUDDY_MANGROVE_ROOT_VERTICAL_SLAB);
        addDrop(ModBlocks.MUSHROOM_STEM_VERTICAL_SLAB);
        addDrop(ModBlocks.OAK_LEAF_VERTICAL_SLAB);
        addDrop(ModBlocks.SPRUCE_LEAF_VERTICAL_SLAB);
        addDrop(ModBlocks.BIRCH_LEAF_VERTICAL_SLAB);
        addDrop(ModBlocks.JUNGLE_LEAF_VERTICAL_SLAB);
        addDrop(ModBlocks.ACACIA_LEAF_VERTICAL_SLAB);
        addDrop(ModBlocks.DARK_OAK_LEAF_VERTICAL_SLAB);
        addDrop(ModBlocks.MANGROVE_LEAF_VERTICAL_SLAB);
        addDrop(ModBlocks.CHERRY_LEAF_VERTICAL_SLAB);
        addDrop(ModBlocks.AZALEA_LEAF_VERTICAL_SLAB);
        addDrop(ModBlocks.FLOWERING_AZALEA_LEAF_VERTICAL_SLAB);
        addDrop(ModBlocks.BROWN_MUSHROOM_VERTICAL_SLAB);
        addDrop(ModBlocks.RED_MUSHROOM_VERTICAL_SLAB);
        addDrop(ModBlocks.NETHER_WART_VERTICAL_SLAB);
        addDrop(ModBlocks.WARPED_WART_VERTICAL_SLAB);
        addDrop(ModBlocks.DRIED_KELP_VERTICAL_SLAB);
        addDrop(ModBlocks.TUBE_CORAL_VERTICAL_SLAB);
        addDrop(ModBlocks.BRAIN_CORAL_VERTICAL_SLAB);
        addDrop(ModBlocks.BUBBLE_CORAL_VERTICAL_SLAB);
        addDrop(ModBlocks.FIRE_CORAL_VERTICAL_SLAB);
        addDrop(ModBlocks.HORN_CORAL_VERTICAL_SLAB);
        addDrop(ModBlocks.DEAD_TUBE_CORAL_VERTICAL_SLAB);
        addDrop(ModBlocks.DEAD_BRAIN_CORAL_VERTICAL_SLAB);
        addDrop(ModBlocks.DEAD_BUBBLE_CORAL_VERTICAL_SLAB);
        addDrop(ModBlocks.DEAD_FIRE_CORAL_VERTICAL_SLAB);
        addDrop(ModBlocks.DEAD_HORN_CORAL_VERTICAL_SLAB);
        addDrop(ModBlocks.SPONGE_VERTICAL_SLAB);
        addDrop(ModBlocks.WET_SPONGE_VERTICAL_SLAB);
        addDrop(ModBlocks.MELON_VERTICAL_SLAB);
        addDrop(ModBlocks.PUMPKIN_VERTICAL_SLAB);
        addDrop(ModBlocks.HAY_VERTICAL_SLAB);
        addDrop(ModBlocks.HONEYCOMB_VERTICAL_SLAB);
        addDrop(ModBlocks.SCULK_VERTICAL_SLAB);*/
    }
}
