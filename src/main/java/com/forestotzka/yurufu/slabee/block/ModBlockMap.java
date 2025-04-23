package com.forestotzka.yurufu.slabee.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;

import java.util.*;

public class ModBlockMap {
    private record BlockTriple(Block original, Block slab, Block verticalSlab) {
    }

    private static final List<BlockTriple> ENTRIES = Arrays.asList(
            new BlockTriple(Blocks.OAK_PLANKS, Blocks.OAK_SLAB, ModBlocks.OAK_VERTICAL_SLAB),
            new BlockTriple(Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_SLAB, ModBlocks.SPRUCE_VERTICAL_SLAB),
            new BlockTriple(Blocks.BIRCH_PLANKS, Blocks.BIRCH_SLAB, ModBlocks.BIRCH_VERTICAL_SLAB),
            new BlockTriple(Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_SLAB, ModBlocks.JUNGLE_VERTICAL_SLAB),
            new BlockTriple(Blocks.ACACIA_PLANKS, Blocks.ACACIA_SLAB, ModBlocks.ACACIA_VERTICAL_SLAB),
            new BlockTriple(Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_SLAB, ModBlocks.DARK_OAK_VERTICAL_SLAB),
            new BlockTriple(Blocks.MANGROVE_PLANKS, Blocks.MANGROVE_SLAB, ModBlocks.MANGROVE_VERTICAL_SLAB),
            new BlockTriple(Blocks.CHERRY_PLANKS, Blocks.CHERRY_SLAB, ModBlocks.CHERRY_VERTICAL_SLAB),
            new BlockTriple(Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_SLAB, ModBlocks.BAMBOO_VERTICAL_SLAB),
            new BlockTriple(Blocks.BAMBOO_MOSAIC, Blocks.BAMBOO_MOSAIC_SLAB, ModBlocks.BAMBOO_MOSAIC_VERTICAL_SLAB),
            new BlockTriple(Blocks.CRIMSON_PLANKS, Blocks.CRIMSON_SLAB, ModBlocks.CRIMSON_VERTICAL_SLAB),
            new BlockTriple(Blocks.WARPED_PLANKS, Blocks.WARPED_SLAB, ModBlocks.WARPED_VERTICAL_SLAB),

            new BlockTriple(Blocks.OAK_LOG, ModBlocks.OAK_LOG_SLAB, ModBlocks.OAK_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.SPRUCE_LOG, ModBlocks.SPRUCE_LOG_SLAB, ModBlocks.SPRUCE_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.BIRCH_LOG, ModBlocks.BIRCH_LOG_SLAB, ModBlocks.BIRCH_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.JUNGLE_LOG, ModBlocks.JUNGLE_LOG_SLAB, ModBlocks.JUNGLE_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.ACACIA_LOG, ModBlocks.ACACIA_LOG_SLAB, ModBlocks.ACACIA_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.DARK_OAK_LOG, ModBlocks.DARK_OAK_LOG_SLAB, ModBlocks.DARK_OAK_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.MANGROVE_LOG, ModBlocks.MANGROVE_LOG_SLAB, ModBlocks.MANGROVE_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.CHERRY_LOG, ModBlocks.CHERRY_LOG_SLAB, ModBlocks.CHERRY_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.BAMBOO_BLOCK, ModBlocks.BAMBOO_BLOCK_SLAB, ModBlocks.BAMBOO_BLOCK_VERTICAL_SLAB),
            new BlockTriple(Blocks.CRIMSON_STEM, ModBlocks.CRIMSON_STEM_SLAB, ModBlocks.CRIMSON_STEM_VERTICAL_SLAB),
            new BlockTriple(Blocks.WARPED_STEM, ModBlocks.WARPED_STEM_SLAB, ModBlocks.WARPED_STEM_VERTICAL_SLAB),

            new BlockTriple(Blocks.OAK_WOOD, ModBlocks.OAK_WOOD_SLAB, ModBlocks.OAK_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.SPRUCE_WOOD, ModBlocks.SPRUCE_WOOD_SLAB, ModBlocks.SPRUCE_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.BIRCH_WOOD, ModBlocks.BIRCH_WOOD_SLAB, ModBlocks.BIRCH_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.JUNGLE_WOOD, ModBlocks.JUNGLE_WOOD_SLAB, ModBlocks.JUNGLE_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.ACACIA_WOOD, ModBlocks.ACACIA_WOOD_SLAB, ModBlocks.ACACIA_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.DARK_OAK_WOOD, ModBlocks.DARK_OAK_WOOD_SLAB, ModBlocks.DARK_OAK_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.MANGROVE_WOOD, ModBlocks.MANGROVE_WOOD_SLAB, ModBlocks.MANGROVE_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.CHERRY_WOOD, ModBlocks.CHERRY_WOOD_SLAB, ModBlocks.CHERRY_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.CRIMSON_HYPHAE, ModBlocks.CRIMSON_HYPHAE_SLAB, ModBlocks.CRIMSON_HYPHAE_VERTICAL_SLAB),
            new BlockTriple(Blocks.WARPED_HYPHAE, ModBlocks.WARPED_HYPHAE_SLAB, ModBlocks.WARPED_HYPHAE_VERTICAL_SLAB),

            new BlockTriple(Blocks.STRIPPED_OAK_LOG, ModBlocks.STRIPPED_OAK_LOG_SLAB, ModBlocks.STRIPPED_OAK_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_SPRUCE_LOG, ModBlocks.STRIPPED_SPRUCE_LOG_SLAB, ModBlocks.STRIPPED_SPRUCE_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_BIRCH_LOG, ModBlocks.STRIPPED_BIRCH_LOG_SLAB, ModBlocks.STRIPPED_BIRCH_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_JUNGLE_LOG, ModBlocks.STRIPPED_JUNGLE_LOG_SLAB, ModBlocks.STRIPPED_JUNGLE_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_ACACIA_LOG, ModBlocks.STRIPPED_ACACIA_LOG_SLAB, ModBlocks.STRIPPED_ACACIA_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_DARK_OAK_LOG, ModBlocks.STRIPPED_DARK_OAK_LOG_SLAB, ModBlocks.STRIPPED_DARK_OAK_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_MANGROVE_LOG, ModBlocks.STRIPPED_MANGROVE_LOG_SLAB, ModBlocks.STRIPPED_MANGROVE_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_CHERRY_LOG, ModBlocks.STRIPPED_CHERRY_LOG_SLAB, ModBlocks.STRIPPED_CHERRY_LOG_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_BAMBOO_BLOCK, ModBlocks.STRIPPED_BAMBOO_SLAB, ModBlocks.STRIPPED_BAMBOO_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_CRIMSON_STEM, ModBlocks.STRIPPED_CRIMSON_STEM_SLAB, ModBlocks.STRIPPED_CRIMSON_STEM_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_WARPED_STEM, ModBlocks.STRIPPED_WARPED_STEM_SLAB, ModBlocks.STRIPPED_WARPED_STEM_VERTICAL_SLAB),

            new BlockTriple(Blocks.STRIPPED_OAK_WOOD, ModBlocks.STRIPPED_OAK_WOOD_SLAB, ModBlocks.STRIPPED_OAK_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_SPRUCE_WOOD, ModBlocks.STRIPPED_SPRUCE_WOOD_SLAB, ModBlocks.STRIPPED_SPRUCE_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_BIRCH_WOOD, ModBlocks.STRIPPED_BIRCH_WOOD_SLAB, ModBlocks.STRIPPED_BIRCH_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_JUNGLE_WOOD, ModBlocks.STRIPPED_JUNGLE_WOOD_SLAB, ModBlocks.STRIPPED_JUNGLE_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_ACACIA_WOOD, ModBlocks.STRIPPED_ACACIA_WOOD_SLAB, ModBlocks.STRIPPED_ACACIA_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_DARK_OAK_WOOD, ModBlocks.STRIPPED_DARK_OAK_WOOD_SLAB, ModBlocks.STRIPPED_DARK_OAK_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_MANGROVE_WOOD, ModBlocks.STRIPPED_MANGROVE_WOOD_SLAB, ModBlocks.STRIPPED_MANGROVE_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_CHERRY_WOOD, ModBlocks.STRIPPED_CHERRY_WOOD_SLAB, ModBlocks.STRIPPED_CHERRY_WOOD_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_CRIMSON_HYPHAE, ModBlocks.STRIPPED_CRIMSON_HYPHAE_SLAB, ModBlocks.STRIPPED_CRIMSON_HYPHAE_VERTICAL_SLAB),
            new BlockTriple(Blocks.STRIPPED_WARPED_HYPHAE, ModBlocks.STRIPPED_WARPED_HYPHAE_SLAB, ModBlocks.STRIPPED_WARPED_HYPHAE_VERTICAL_SLAB),

            new BlockTriple(Blocks.STONE, Blocks.STONE_SLAB, ModBlocks.STONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.COBBLESTONE, Blocks.COBBLESTONE_SLAB, ModBlocks.COBBLESTONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.MOSSY_COBBLESTONE, Blocks.MOSSY_COBBLESTONE_SLAB, ModBlocks.MOSSY_COBBLESTONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.SMOOTH_STONE, Blocks.SMOOTH_STONE_SLAB, ModBlocks.SMOOTH_STONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.STONE_BRICKS, Blocks.STONE_BRICK_SLAB, ModBlocks.STONE_BRICK_VERTICAL_SLAB),
            new BlockTriple(Blocks.CRACKED_STONE_BRICKS, ModBlocks.CRACKED_STONE_BRICK_SLAB, ModBlocks.CRACKED_STONE_BRICK_VERTICAL_SLAB),
            new BlockTriple(Blocks.MOSSY_STONE_BRICKS, Blocks.MOSSY_STONE_BRICK_SLAB, ModBlocks.MOSSY_STONE_BRICK_VERTICAL_SLAB),
            new BlockTriple(Blocks.GRANITE, Blocks.GRANITE_SLAB, ModBlocks.GRANITE_VERTICAL_SLAB),
            new BlockTriple(Blocks.POLISHED_GRANITE, Blocks.POLISHED_GRANITE_SLAB, ModBlocks.POLISHED_GRANITE_VERTICAL_SLAB),
            new BlockTriple(Blocks.DIORITE, Blocks.DIORITE_SLAB, ModBlocks.DIORITE_VERTICAL_SLAB),
            new BlockTriple(Blocks.POLISHED_DIORITE, Blocks.POLISHED_DIORITE_SLAB, ModBlocks.POLISHED_DIORITE_VERTICAL_SLAB),
            new BlockTriple(Blocks.ANDESITE, Blocks.ANDESITE_SLAB, ModBlocks.ANDESITE_VERTICAL_SLAB),
            new BlockTriple(Blocks.POLISHED_ANDESITE, Blocks.POLISHED_ANDESITE_SLAB, ModBlocks.POLISHED_ANDESITE_VERTICAL_SLAB),
            new BlockTriple(Blocks.DEEPSLATE, ModBlocks.DEEPSLATE_SLAB, ModBlocks.DEEPSLATE_VERTICAL_SLAB),
            new BlockTriple(Blocks.COBBLED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE_SLAB, ModBlocks.COBBLED_DEEPSLATE_VERTICAL_SLAB),
            new BlockTriple(Blocks.POLISHED_DEEPSLATE, Blocks.POLISHED_DEEPSLATE_SLAB, ModBlocks.POLISHED_DEEPSLATE_VERTICAL_SLAB),
            new BlockTriple(Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_BRICK_SLAB, ModBlocks.DEEPSLATE_BRICK_VERTICAL_SLAB),
            new BlockTriple(Blocks.CRACKED_DEEPSLATE_BRICKS, ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB, ModBlocks.CRACKED_DEEPSLATE_BRICK_VERTICAL_SLAB),
            new BlockTriple(Blocks.DEEPSLATE_TILES, Blocks.DEEPSLATE_TILE_SLAB, ModBlocks.DEEPSLATE_TILE_VERTICAL_SLAB),
            new BlockTriple(Blocks.REINFORCED_DEEPSLATE, ModBlocks.REINFORCED_DEEPSLATE_SLAB, ModBlocks.REINFORCED_DEEPSLATE_VERTICAL_SLAB),

            new BlockTriple(Blocks.TUFF, Blocks.TUFF_SLAB, ModBlocks.TUFF_VERTICAL_SLAB),
            new BlockTriple(Blocks.POLISHED_TUFF, Blocks.POLISHED_TUFF_SLAB, ModBlocks.POLISHED_TUFF_VERTICAL_SLAB),
            new BlockTriple(Blocks.TUFF_BRICKS, Blocks.TUFF_BRICK_SLAB, ModBlocks.TUFF_BRICK_VERTICAL_SLAB),
            new BlockTriple(Blocks.BRICKS, Blocks.BRICK_SLAB, ModBlocks.BRICK_VERTICAL_SLAB),
            new BlockTriple(Blocks.PACKED_MUD, ModBlocks.PACKED_MUD_SLAB, ModBlocks.PACKED_MUD_VERTICAL_SLAB),
            new BlockTriple(Blocks.MUD_BRICKS, Blocks.MUD_BRICK_SLAB, ModBlocks.MUD_BRICK_VERTICAL_SLAB),
            new BlockTriple(Blocks.SANDSTONE, Blocks.SANDSTONE_SLAB, ModBlocks.SANDSTONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_SANDSTONE_SLAB, ModBlocks.SMOOTH_SANDSTONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.CUT_SANDSTONE, Blocks.CUT_SANDSTONE_SLAB, ModBlocks.CUT_SANDSTONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE_SLAB, ModBlocks.RED_SANDSTONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE_SLAB, ModBlocks.SMOOTH_RED_SANDSTONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.CUT_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE_SLAB, ModBlocks.CUT_RED_SANDSTONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.PRISMARINE, Blocks.PRISMARINE_SLAB, ModBlocks.PRISMARINE_VERTICAL_SLAB),
            new BlockTriple(Blocks.PRISMARINE_BRICKS, Blocks.PRISMARINE_BRICK_SLAB, ModBlocks.PRISMARINE_BRICK_VERTICAL_SLAB),
            new BlockTriple(Blocks.DARK_PRISMARINE, Blocks.DARK_PRISMARINE_SLAB, ModBlocks.DARK_PRISMARINE_VERTICAL_SLAB),
            new BlockTriple(Blocks.NETHERRACK, ModBlocks.NETHERRACK_SLAB, ModBlocks.NETHERRACK_VERTICAL_SLAB),
            new BlockTriple(Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_SLAB, ModBlocks.NETHER_BRICK_VERTICAL_SLAB),
            new BlockTriple(Blocks.CRACKED_NETHER_BRICKS, ModBlocks.CRACKED_NETHER_BRICK_SLAB, ModBlocks.CRACKED_NETHER_BRICK_VERTICAL_SLAB),
            new BlockTriple(Blocks.RED_NETHER_BRICKS, Blocks.RED_NETHER_BRICK_SLAB, ModBlocks.RED_NETHER_BRICK_VERTICAL_SLAB),
            new BlockTriple(Blocks.BASALT, ModBlocks.BASALT_SLAB, ModBlocks.BASALT_VERTICAL_SLAB),
            new BlockTriple(Blocks.SMOOTH_BASALT, ModBlocks.SMOOTH_BASALT_SLAB, ModBlocks.SMOOTH_BASALT_VERTICAL_SLAB),
            new BlockTriple(Blocks.POLISHED_BASALT, ModBlocks.POLISHED_BASALT_SLAB, ModBlocks.POLISHED_BASALT_VERTICAL_SLAB),
            new BlockTriple(Blocks.BLACKSTONE, Blocks.BLACKSTONE_SLAB, ModBlocks.BLACKSTONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.GILDED_BLACKSTONE, ModBlocks.GILDED_BLACKSTONE_SLAB, ModBlocks.GILDED_BLACKSTONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_SLAB, ModBlocks.POLISHED_BLACKSTONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB),
            new BlockTriple(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB),

            new BlockTriple(Blocks.END_STONE, ModBlocks.END_STONE_SLAB, ModBlocks.END_STONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.END_STONE_BRICKS, Blocks.END_STONE_BRICK_SLAB, ModBlocks.END_STONE_BRICK_VERTICAL_SLAB),
            new BlockTriple(Blocks.PURPUR_BLOCK, Blocks.PURPUR_SLAB, ModBlocks.PURPUR_VERTICAL_SLAB),
            new BlockTriple(Blocks.PURPUR_PILLAR, ModBlocks.PURPUR_PILLAR_SLAB, ModBlocks.PURPUR_PILLAR_VERTICAL_SLAB),
            new BlockTriple(Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_SLAB, ModBlocks.QUARTZ_VERTICAL_SLAB),
            new BlockTriple(Blocks.QUARTZ_BRICKS, ModBlocks.QUARTZ_BRICK_SLAB, ModBlocks.QUARTZ_BRICK_VERTICAL_SLAB),
            new BlockTriple(Blocks.QUARTZ_PILLAR, ModBlocks.QUARTZ_PILLAR_SLAB, ModBlocks.QUARTZ_PILLAR_VERTICAL_SLAB),
            new BlockTriple(Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_QUARTZ_SLAB, ModBlocks.SMOOTH_QUARTZ_VERTICAL_SLAB),
            new BlockTriple(Blocks.AMETHYST_BLOCK, ModBlocks.AMETHYST_SLAB, ModBlocks.AMETHYST_VERTICAL_SLAB),
            new BlockTriple(Blocks.CUT_COPPER, Blocks.CUT_COPPER_SLAB, ModBlocks.CUT_COPPER_VERTICAL_SLAB),
            new BlockTriple(Blocks.EXPOSED_CUT_COPPER, Blocks.EXPOSED_CUT_COPPER_SLAB, ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB),
            new BlockTriple(Blocks.WEATHERED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER_SLAB, ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB),
            new BlockTriple(Blocks.OXIDIZED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER_SLAB, ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB),
            new BlockTriple(Blocks.WAXED_CUT_COPPER, Blocks.WAXED_CUT_COPPER_SLAB, ModBlocks.WAXED_CUT_COPPER_VERTICAL_SLAB),
            new BlockTriple(Blocks.WAXED_EXPOSED_CUT_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB, ModBlocks.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB),
            new BlockTriple(Blocks.WAXED_WEATHERED_CUT_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB, ModBlocks.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB),
            new BlockTriple(Blocks.WAXED_OXIDIZED_CUT_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB, ModBlocks.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB),
            new BlockTriple(Blocks.PETRIFIED_OAK_SLAB, Blocks.PETRIFIED_OAK_SLAB, ModBlocks.PETRIFIED_OAK_VERTICAL_SLAB),

            new BlockTriple(Blocks.WHITE_WOOL, ModBlocks.WHITE_WOOL_SLAB, ModBlocks.WHITE_WOOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.LIGHT_GRAY_WOOL, ModBlocks.LIGHT_GRAY_WOOL_SLAB, ModBlocks.LIGHT_GRAY_WOOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.GRAY_WOOL, ModBlocks.GRAY_WOOL_SLAB, ModBlocks.GRAY_WOOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.BLACK_WOOL, ModBlocks.BLACK_WOOL_SLAB, ModBlocks.BLACK_WOOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.BROWN_WOOL, ModBlocks.BROWN_WOOL_SLAB, ModBlocks.BROWN_WOOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.RED_WOOL, ModBlocks.RED_WOOL_SLAB, ModBlocks.RED_WOOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.ORANGE_WOOL, ModBlocks.ORANGE_WOOL_SLAB, ModBlocks.ORANGE_WOOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.YELLOW_WOOL, ModBlocks.YELLOW_WOOL_SLAB, ModBlocks.YELLOW_WOOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.LIME_WOOL, ModBlocks.LIME_WOOL_SLAB, ModBlocks.LIME_WOOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.GREEN_WOOL, ModBlocks.GREEN_WOOL_SLAB, ModBlocks.GREEN_WOOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.CYAN_WOOL, ModBlocks.CYAN_WOOL_SLAB, ModBlocks.CYAN_WOOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.LIGHT_BLUE_WOOL, ModBlocks.LIGHT_BLUE_WOOL_SLAB, ModBlocks.LIGHT_BLUE_WOOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.BLUE_WOOL, ModBlocks.BLUE_WOOL_SLAB, ModBlocks.BLUE_WOOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.PURPLE_WOOL, ModBlocks.PURPLE_WOOL_SLAB, ModBlocks.PURPLE_WOOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.MAGENTA_WOOL, ModBlocks.MAGENTA_WOOL_SLAB, ModBlocks.MAGENTA_WOOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.PINK_WOOL, ModBlocks.PINK_WOOL_SLAB, ModBlocks.PINK_WOOL_VERTICAL_SLAB),

            new BlockTriple(Blocks.TERRACOTTA, ModBlocks.TERRACOTTA_SLAB, ModBlocks.TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.WHITE_TERRACOTTA, ModBlocks.WHITE_TERRACOTTA_SLAB, ModBlocks.WHITE_TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.LIGHT_GRAY_TERRACOTTA, ModBlocks.LIGHT_GRAY_TERRACOTTA_SLAB, ModBlocks.LIGHT_GRAY_TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.GRAY_TERRACOTTA, ModBlocks.GRAY_TERRACOTTA_SLAB, ModBlocks.GRAY_TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.BLACK_TERRACOTTA, ModBlocks.BLACK_TERRACOTTA_SLAB, ModBlocks.BLACK_TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.BROWN_TERRACOTTA, ModBlocks.BROWN_TERRACOTTA_SLAB, ModBlocks.BROWN_TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.RED_TERRACOTTA, ModBlocks.RED_TERRACOTTA_SLAB, ModBlocks.RED_TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.ORANGE_TERRACOTTA, ModBlocks.ORANGE_TERRACOTTA_SLAB, ModBlocks.ORANGE_TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.YELLOW_TERRACOTTA, ModBlocks.YELLOW_TERRACOTTA_SLAB, ModBlocks.YELLOW_TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.LIME_TERRACOTTA, ModBlocks.LIME_TERRACOTTA_SLAB, ModBlocks.LIME_TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.GREEN_TERRACOTTA, ModBlocks.GREEN_TERRACOTTA_SLAB, ModBlocks.GREEN_TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.CYAN_TERRACOTTA, ModBlocks.CYAN_TERRACOTTA_SLAB, ModBlocks.CYAN_TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.LIGHT_BLUE_TERRACOTTA, ModBlocks.LIGHT_BLUE_TERRACOTTA_SLAB, ModBlocks.LIGHT_BLUE_TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.BLUE_TERRACOTTA, ModBlocks.BLUE_TERRACOTTA_SLAB, ModBlocks.BLUE_TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.PURPLE_TERRACOTTA, ModBlocks.PURPLE_TERRACOTTA_SLAB, ModBlocks.PURPLE_TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.MAGENTA_TERRACOTTA, ModBlocks.MAGENTA_TERRACOTTA_SLAB, ModBlocks.MAGENTA_TERRACOTTA_VERTICAL_SLAB),
            new BlockTriple(Blocks.PINK_TERRACOTTA, ModBlocks.PINK_TERRACOTTA_SLAB, ModBlocks.PINK_TERRACOTTA_VERTICAL_SLAB),

            new BlockTriple(Blocks.WHITE_CONCRETE, ModBlocks.WHITE_CONCRETE_SLAB, ModBlocks.WHITE_CONCRETE_VERTICAL_SLAB),
            new BlockTriple(Blocks.LIGHT_GRAY_CONCRETE, ModBlocks.LIGHT_GRAY_CONCRETE_SLAB, ModBlocks.LIGHT_GRAY_CONCRETE_VERTICAL_SLAB),
            new BlockTriple(Blocks.GRAY_CONCRETE, ModBlocks.GRAY_CONCRETE_SLAB, ModBlocks.GRAY_CONCRETE_VERTICAL_SLAB),
            new BlockTriple(Blocks.BLACK_CONCRETE, ModBlocks.BLACK_CONCRETE_SLAB, ModBlocks.BLACK_CONCRETE_VERTICAL_SLAB),
            new BlockTriple(Blocks.BROWN_CONCRETE, ModBlocks.BROWN_CONCRETE_SLAB, ModBlocks.BROWN_CONCRETE_VERTICAL_SLAB),
            new BlockTriple(Blocks.RED_CONCRETE, ModBlocks.RED_CONCRETE_SLAB, ModBlocks.RED_CONCRETE_VERTICAL_SLAB),
            new BlockTriple(Blocks.ORANGE_CONCRETE, ModBlocks.ORANGE_CONCRETE_SLAB, ModBlocks.ORANGE_CONCRETE_VERTICAL_SLAB),
            new BlockTriple(Blocks.YELLOW_CONCRETE, ModBlocks.YELLOW_CONCRETE_SLAB, ModBlocks.YELLOW_CONCRETE_VERTICAL_SLAB),
            new BlockTriple(Blocks.LIME_CONCRETE, ModBlocks.LIME_CONCRETE_SLAB, ModBlocks.LIME_CONCRETE_VERTICAL_SLAB),
            new BlockTriple(Blocks.GREEN_CONCRETE, ModBlocks.GREEN_CONCRETE_SLAB, ModBlocks.GREEN_CONCRETE_VERTICAL_SLAB),
            new BlockTriple(Blocks.CYAN_CONCRETE, ModBlocks.CYAN_CONCRETE_SLAB, ModBlocks.CYAN_CONCRETE_VERTICAL_SLAB),
            new BlockTriple(Blocks.LIGHT_BLUE_CONCRETE, ModBlocks.LIGHT_BLUE_CONCRETE_SLAB, ModBlocks.LIGHT_BLUE_CONCRETE_VERTICAL_SLAB),
            new BlockTriple(Blocks.BLUE_CONCRETE, ModBlocks.BLUE_CONCRETE_SLAB, ModBlocks.BLUE_CONCRETE_VERTICAL_SLAB),
            new BlockTriple(Blocks.PURPLE_CONCRETE, ModBlocks.PURPLE_CONCRETE_SLAB, ModBlocks.PURPLE_CONCRETE_VERTICAL_SLAB),
            new BlockTriple(Blocks.MAGENTA_CONCRETE, ModBlocks.MAGENTA_CONCRETE_SLAB, ModBlocks.MAGENTA_CONCRETE_VERTICAL_SLAB),
            new BlockTriple(Blocks.PINK_CONCRETE, ModBlocks.PINK_CONCRETE_SLAB, ModBlocks.PINK_CONCRETE_VERTICAL_SLAB),

            new BlockTriple(Blocks.WHITE_CONCRETE_POWDER, ModBlocks.WHITE_CONCRETE_POWDER_SLAB, ModBlocks.WHITE_CONCRETE_POWDER_VERTICAL_SLAB),
            new BlockTriple(Blocks.LIGHT_GRAY_CONCRETE_POWDER, ModBlocks.LIGHT_GRAY_CONCRETE_POWDER_SLAB, ModBlocks.LIGHT_GRAY_CONCRETE_POWDER_VERTICAL_SLAB),
            new BlockTriple(Blocks.GRAY_CONCRETE_POWDER, ModBlocks.GRAY_CONCRETE_POWDER_SLAB, ModBlocks.GRAY_CONCRETE_POWDER_VERTICAL_SLAB),
            new BlockTriple(Blocks.BLACK_CONCRETE_POWDER, ModBlocks.BLACK_CONCRETE_POWDER_SLAB, ModBlocks.BLACK_CONCRETE_POWDER_VERTICAL_SLAB),
            new BlockTriple(Blocks.BROWN_CONCRETE_POWDER, ModBlocks.BROWN_CONCRETE_POWDER_SLAB, ModBlocks.BROWN_CONCRETE_POWDER_VERTICAL_SLAB),
            new BlockTriple(Blocks.RED_CONCRETE_POWDER, ModBlocks.RED_CONCRETE_POWDER_SLAB, ModBlocks.RED_CONCRETE_POWDER_VERTICAL_SLAB),
            new BlockTriple(Blocks.ORANGE_CONCRETE_POWDER, ModBlocks.ORANGE_CONCRETE_POWDER_SLAB, ModBlocks.ORANGE_CONCRETE_POWDER_VERTICAL_SLAB),
            new BlockTriple(Blocks.YELLOW_CONCRETE_POWDER, ModBlocks.YELLOW_CONCRETE_POWDER_SLAB, ModBlocks.YELLOW_CONCRETE_POWDER_VERTICAL_SLAB),
            new BlockTriple(Blocks.LIME_CONCRETE_POWDER, ModBlocks.LIME_CONCRETE_POWDER_SLAB, ModBlocks.LIME_CONCRETE_POWDER_VERTICAL_SLAB),
            new BlockTriple(Blocks.GREEN_CONCRETE_POWDER, ModBlocks.GREEN_CONCRETE_POWDER_SLAB, ModBlocks.GREEN_CONCRETE_POWDER_VERTICAL_SLAB),
            new BlockTriple(Blocks.CYAN_CONCRETE_POWDER, ModBlocks.CYAN_CONCRETE_POWDER_SLAB, ModBlocks.CYAN_CONCRETE_POWDER_VERTICAL_SLAB),
            new BlockTriple(Blocks.LIGHT_BLUE_CONCRETE_POWDER, ModBlocks.LIGHT_BLUE_CONCRETE_POWDER_SLAB, ModBlocks.LIGHT_BLUE_CONCRETE_POWDER_VERTICAL_SLAB),
            new BlockTriple(Blocks.BLUE_CONCRETE_POWDER, ModBlocks.BLUE_CONCRETE_POWDER_SLAB, ModBlocks.BLUE_CONCRETE_POWDER_VERTICAL_SLAB),
            new BlockTriple(Blocks.PURPLE_CONCRETE_POWDER, ModBlocks.PURPLE_CONCRETE_POWDER_SLAB, ModBlocks.PURPLE_CONCRETE_POWDER_VERTICAL_SLAB),
            new BlockTriple(Blocks.MAGENTA_CONCRETE_POWDER, ModBlocks.MAGENTA_CONCRETE_POWDER_SLAB, ModBlocks.MAGENTA_CONCRETE_POWDER_VERTICAL_SLAB),
            new BlockTriple(Blocks.PINK_CONCRETE_POWDER, ModBlocks.PINK_CONCRETE_POWDER_SLAB, ModBlocks.PINK_CONCRETE_POWDER_VERTICAL_SLAB),

            new BlockTriple(Blocks.GLASS, ModBlocks.GLASS_SLAB, ModBlocks.GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.TINTED_GLASS, ModBlocks.TINTED_GLASS_SLAB, ModBlocks.TINTED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.WHITE_STAINED_GLASS, ModBlocks.WHITE_STAINED_GLASS_SLAB, ModBlocks.WHITE_STAINED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.ORANGE_STAINED_GLASS, ModBlocks.ORANGE_STAINED_GLASS_SLAB, ModBlocks.ORANGE_STAINED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.MAGENTA_STAINED_GLASS, ModBlocks.MAGENTA_STAINED_GLASS_SLAB, ModBlocks.MAGENTA_STAINED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.LIGHT_BLUE_STAINED_GLASS, ModBlocks.LIGHT_BLUE_STAINED_GLASS_SLAB, ModBlocks.LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.YELLOW_STAINED_GLASS, ModBlocks.YELLOW_STAINED_GLASS_SLAB, ModBlocks.YELLOW_STAINED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.LIME_STAINED_GLASS, ModBlocks.LIME_STAINED_GLASS_SLAB, ModBlocks.LIME_STAINED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.PINK_STAINED_GLASS, ModBlocks.PINK_STAINED_GLASS_SLAB, ModBlocks.PINK_STAINED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.GRAY_STAINED_GLASS, ModBlocks.GRAY_STAINED_GLASS_SLAB, ModBlocks.GRAY_STAINED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.LIGHT_GRAY_STAINED_GLASS, ModBlocks.LIGHT_GRAY_STAINED_GLASS_SLAB, ModBlocks.LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.CYAN_STAINED_GLASS, ModBlocks.CYAN_STAINED_GLASS_SLAB, ModBlocks.CYAN_STAINED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.PURPLE_STAINED_GLASS, ModBlocks.PURPLE_STAINED_GLASS_SLAB, ModBlocks.PURPLE_STAINED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.BLUE_STAINED_GLASS, ModBlocks.BLUE_STAINED_GLASS_SLAB, ModBlocks.BLUE_STAINED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.BROWN_STAINED_GLASS, ModBlocks.BROWN_STAINED_GLASS_SLAB, ModBlocks.BROWN_STAINED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.GREEN_STAINED_GLASS, ModBlocks.GREEN_STAINED_GLASS_SLAB, ModBlocks.GREEN_STAINED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.RED_STAINED_GLASS, ModBlocks.RED_STAINED_GLASS_SLAB, ModBlocks.RED_STAINED_GLASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.BLACK_STAINED_GLASS, ModBlocks.BLACK_STAINED_GLASS_SLAB, ModBlocks.BLACK_STAINED_GLASS_VERTICAL_SLAB),

            new BlockTriple(Blocks.GRASS_BLOCK, ModBlocks.GRASS_SLAB, ModBlocks.GRASS_VERTICAL_SLAB),
            new BlockTriple(Blocks.PODZOL, ModBlocks.PODZOL_SLAB, ModBlocks.PODZOL_VERTICAL_SLAB),
            new BlockTriple(Blocks.MYCELIUM, ModBlocks.MYCELIUM_SLAB, ModBlocks.MYCELIUM_VERTICAL_SLAB),
            new BlockTriple(Blocks.DIRT, ModBlocks.DIRT_SLAB, ModBlocks.DIRT_VERTICAL_SLAB),
            new BlockTriple(Blocks.DIRT_PATH, ModBlocks.DIRT_PATH_SLAB, ModBlocks.DIRT_PATH_VERTICAL_SLAB),
            new BlockTriple(Blocks.COARSE_DIRT, ModBlocks.COARSE_DIRT_SLAB, ModBlocks.COARSE_DIRT_VERTICAL_SLAB),
            new BlockTriple(Blocks.ROOTED_DIRT, ModBlocks.ROOTED_DIRT_SLAB, ModBlocks.ROOTED_DIRT_VERTICAL_SLAB),
            new BlockTriple(Blocks.MUD, ModBlocks.MUD_SLAB, ModBlocks.MUD_VERTICAL_SLAB),
            new BlockTriple(Blocks.CLAY, ModBlocks.CLAY_SLAB, ModBlocks.CLAY_VERTICAL_SLAB),
            new BlockTriple(Blocks.GRAVEL, ModBlocks.GRAVEL_SLAB, ModBlocks.GRAVEL_VERTICAL_SLAB),
            new BlockTriple(Blocks.SAND, ModBlocks.SAND_SLAB, ModBlocks.SAND_VERTICAL_SLAB),
            new BlockTriple(Blocks.RED_SAND, ModBlocks.RED_SAND_SLAB, ModBlocks.RED_SAND_VERTICAL_SLAB),
            new BlockTriple(Blocks.ICE, ModBlocks.ICE_SLAB, ModBlocks.ICE_VERTICAL_SLAB),
            new BlockTriple(Blocks.PACKED_ICE, ModBlocks.PACKED_ICE_SLAB, ModBlocks.PACKED_ICE_VERTICAL_SLAB),
            new BlockTriple(Blocks.BLUE_ICE, ModBlocks.BLUE_ICE_SLAB, ModBlocks.BLUE_ICE_VERTICAL_SLAB),
            new BlockTriple(Blocks.MOSS_BLOCK, ModBlocks.MOSS_SLAB, ModBlocks.MOSS_VERTICAL_SLAB),
            new BlockTriple(Blocks.CALCITE, ModBlocks.CALCITE_SLAB, ModBlocks.CALCITE_VERTICAL_SLAB),
            new BlockTriple(Blocks.DRIPSTONE_BLOCK, ModBlocks.DRIPSTONE_SLAB, ModBlocks.DRIPSTONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.MAGMA_BLOCK, ModBlocks.MAGMA_BLOCK_SLAB, ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB),
            new BlockTriple(Blocks.OBSIDIAN, ModBlocks.OBSIDIAN_SLAB, ModBlocks.OBSIDIAN_VERTICAL_SLAB),
            new BlockTriple(Blocks.CRYING_OBSIDIAN, ModBlocks.CRYING_OBSIDIAN_SLAB, ModBlocks.CRYING_OBSIDIAN_VERTICAL_SLAB),
            new BlockTriple(Blocks.CRIMSON_NYLIUM, ModBlocks.CRIMSON_NYLIUM_SLAB, ModBlocks.CRIMSON_NYLIUM_VERTICAL_SLAB),
            new BlockTriple(Blocks.WARPED_NYLIUM, ModBlocks.WARPED_NYLIUM_SLAB, ModBlocks.WARPED_NYLIUM_VERTICAL_SLAB),
            new BlockTriple(Blocks.SOUL_SAND, ModBlocks.SOUL_SAND_SLAB, ModBlocks.SOUL_SAND_VERTICAL_SLAB),
            new BlockTriple(Blocks.SOUL_SOIL, ModBlocks.SOUL_SOIL_SLAB, ModBlocks.SOUL_SOIL_VERTICAL_SLAB),
            new BlockTriple(Blocks.BONE_BLOCK, ModBlocks.BONE_SLAB, ModBlocks.BONE_VERTICAL_SLAB),
            new BlockTriple(Blocks.GLOWSTONE, ModBlocks.GLOWSTONE_SLAB, ModBlocks.GLOWSTONE_VERTICAL_SLAB),

            new BlockTriple(Blocks.MANGROVE_ROOTS, ModBlocks.MANGROVE_ROOT_SLAB, ModBlocks.MANGROVE_ROOT_VERTICAL_SLAB),
            new BlockTriple(Blocks.MUDDY_MANGROVE_ROOTS, ModBlocks.MUDDY_MANGROVE_ROOT_SLAB, ModBlocks.MUDDY_MANGROVE_ROOT_VERTICAL_SLAB),
            new BlockTriple(Blocks.MUSHROOM_STEM, ModBlocks.MUSHROOM_STEM_SLAB, ModBlocks.MUSHROOM_STEM_VERTICAL_SLAB),
            new BlockTriple(Blocks.OAK_LEAVES, ModBlocks.OAK_LEAF_SLAB, ModBlocks.OAK_LEAF_VERTICAL_SLAB),
            new BlockTriple(Blocks.SPRUCE_LEAVES, ModBlocks.SPRUCE_LEAF_SLAB, ModBlocks.SPRUCE_LEAF_VERTICAL_SLAB),
            new BlockTriple(Blocks.BIRCH_LEAVES, ModBlocks.BIRCH_LEAF_SLAB, ModBlocks.BIRCH_LEAF_VERTICAL_SLAB),
            new BlockTriple(Blocks.JUNGLE_LEAVES, ModBlocks.JUNGLE_LEAF_SLAB, ModBlocks.JUNGLE_LEAF_VERTICAL_SLAB),
            new BlockTriple(Blocks.ACACIA_LEAVES, ModBlocks.ACACIA_LEAF_SLAB, ModBlocks.ACACIA_LEAF_VERTICAL_SLAB),
            new BlockTriple(Blocks.DARK_OAK_LEAVES, ModBlocks.DARK_OAK_LEAF_SLAB, ModBlocks.DARK_OAK_LEAF_VERTICAL_SLAB),
            new BlockTriple(Blocks.MANGROVE_LEAVES, ModBlocks.MANGROVE_LEAF_SLAB, ModBlocks.MANGROVE_LEAF_VERTICAL_SLAB),
            new BlockTriple(Blocks.CHERRY_LEAVES, ModBlocks.CHERRY_LEAF_SLAB, ModBlocks.CHERRY_LEAF_VERTICAL_SLAB),
            new BlockTriple(Blocks.AZALEA_LEAVES, ModBlocks.AZALEA_LEAF_SLAB, ModBlocks.AZALEA_LEAF_VERTICAL_SLAB),
            new BlockTriple(Blocks.FLOWERING_AZALEA_LEAVES, ModBlocks.FLOWERING_AZALEA_LEAF_SLAB, ModBlocks.FLOWERING_AZALEA_LEAF_VERTICAL_SLAB),
            new BlockTriple(Blocks.BROWN_MUSHROOM_BLOCK, ModBlocks.BROWN_MUSHROOM_SLAB, ModBlocks.BROWN_MUSHROOM_VERTICAL_SLAB),
            new BlockTriple(Blocks.RED_MUSHROOM_BLOCK, ModBlocks.RED_MUSHROOM_SLAB, ModBlocks.RED_MUSHROOM_VERTICAL_SLAB),
            new BlockTriple(Blocks.NETHER_WART_BLOCK, ModBlocks.NETHER_WART_SLAB, ModBlocks.NETHER_WART_VERTICAL_SLAB),
            new BlockTriple(Blocks.WARPED_WART_BLOCK, ModBlocks.WARPED_WART_SLAB, ModBlocks.WARPED_WART_VERTICAL_SLAB),
            new BlockTriple(Blocks.DRIED_KELP_BLOCK, ModBlocks.DRIED_KELP_SLAB, ModBlocks.DRIED_KELP_VERTICAL_SLAB),
            new BlockTriple(Blocks.TUBE_CORAL_BLOCK, ModBlocks.TUBE_CORAL_SLAB, ModBlocks.TUBE_CORAL_VERTICAL_SLAB),
            new BlockTriple(Blocks.BRAIN_CORAL_BLOCK, ModBlocks.BRAIN_CORAL_SLAB, ModBlocks.BRAIN_CORAL_VERTICAL_SLAB),
            new BlockTriple(Blocks.BUBBLE_CORAL_BLOCK, ModBlocks.BUBBLE_CORAL_SLAB, ModBlocks.BUBBLE_CORAL_VERTICAL_SLAB),
            new BlockTriple(Blocks.FIRE_CORAL_BLOCK, ModBlocks.FIRE_CORAL_SLAB, ModBlocks.FIRE_CORAL_VERTICAL_SLAB),
            new BlockTriple(Blocks.HORN_CORAL_BLOCK, ModBlocks.HORN_CORAL_SLAB, ModBlocks.HORN_CORAL_VERTICAL_SLAB),
            new BlockTriple(Blocks.DEAD_TUBE_CORAL_BLOCK, ModBlocks.DEAD_TUBE_CORAL_SLAB, ModBlocks.DEAD_TUBE_CORAL_VERTICAL_SLAB),
            new BlockTriple(Blocks.DEAD_BRAIN_CORAL_BLOCK, ModBlocks.DEAD_BRAIN_CORAL_SLAB, ModBlocks.DEAD_BRAIN_CORAL_VERTICAL_SLAB),
            new BlockTriple(Blocks.DEAD_BUBBLE_CORAL_BLOCK, ModBlocks.DEAD_BUBBLE_CORAL_SLAB, ModBlocks.DEAD_BUBBLE_CORAL_VERTICAL_SLAB),
            new BlockTriple(Blocks.DEAD_FIRE_CORAL_BLOCK, ModBlocks.DEAD_FIRE_CORAL_SLAB, ModBlocks.DEAD_FIRE_CORAL_VERTICAL_SLAB),
            new BlockTriple(Blocks.DEAD_HORN_CORAL_BLOCK, ModBlocks.DEAD_HORN_CORAL_SLAB, ModBlocks.DEAD_HORN_CORAL_VERTICAL_SLAB),
            new BlockTriple(Blocks.SPONGE, ModBlocks.SPONGE_SLAB, ModBlocks.SPONGE_VERTICAL_SLAB),
            new BlockTriple(Blocks.WET_SPONGE, ModBlocks.WET_SPONGE_SLAB, ModBlocks.WET_SPONGE_VERTICAL_SLAB),
            new BlockTriple(Blocks.MELON, ModBlocks.MELON_SLAB, ModBlocks.MELON_VERTICAL_SLAB),
            new BlockTriple(Blocks.PUMPKIN, ModBlocks.PUMPKIN_SLAB, ModBlocks.PUMPKIN_VERTICAL_SLAB),
            new BlockTriple(Blocks.HAY_BLOCK, ModBlocks.HAY_SLAB, ModBlocks.HAY_VERTICAL_SLAB),
            new BlockTriple(Blocks.HONEYCOMB_BLOCK, ModBlocks.HONEYCOMB_SLAB, ModBlocks.HONEYCOMB_VERTICAL_SLAB),
            new BlockTriple(Blocks.SCULK, ModBlocks.SCULK_SLAB, ModBlocks.SCULK_VERTICAL_SLAB),
            new BlockTriple(Blocks.BEDROCK, ModBlocks.BEDROCK_SLAB, null)
    );

    private static final Map<Block, BlockTriple> originalMap = new HashMap<>();
    private static final Map<Block, BlockTriple> slabMap = new HashMap<>();
    private static final Map<Block, BlockTriple> verticalSlabMap = new HashMap<>();

    static {
        for (BlockTriple triple : ENTRIES) {
            originalMap.put(triple.original(), triple);
            slabMap.put(triple.slab(), triple);
            verticalSlabMap.put(triple.verticalSlab(), triple);
        }
    }

    public static Block toOriginal(Block block) {
        if (block == null) {
            return null;
        }

        BlockTriple triple;
        if (block instanceof SlabBlock) {
            triple = slabMap.get(block);
            if (triple != null) {
                return triple.original();
            }
        } else if (block instanceof VerticalSlabBlock) {
            triple = verticalSlabMap.get(block);
            if (triple != null) {
                return triple.original();
            }
        } else {
            triple = originalMap.get(block);
            if (triple != null) {
                return block;
            }
        }

        return null;
    }

    public static Block toSlab(Block block) {
        if (block == null) {
            return null;
        }

        if (block instanceof SlabBlock) {
            return block;
        }

        BlockTriple triple;
        if (block instanceof VerticalSlabBlock) {
            triple = verticalSlabMap.get(block);
        } else {
            triple = originalMap.get(block);
        }

        return triple != null ? triple.slab() : null;
    }

    public static Block toVerticalSlab(Block block) {
        if (block == null) {
            return null;
        }

        if (block instanceof VerticalSlabBlock) {
            return block;
        }

        BlockTriple triple;
        if (block instanceof SlabBlock) {
            triple = slabMap.get(block);
        } else {
            triple = originalMap.get(block);
        }

        return triple != null ? triple.verticalSlab() : null;
    }

    public static Block originalToSlab(Block block) {
        if (block == null) {
            return null;
        }
        BlockTriple triple = originalMap.get(block);
        return triple != null ? triple.slab() : null;
    }

    public static Block originalToVerticalSlab(Block block) {
        if (block == null) {
            return null;
        }
        BlockTriple triple = originalMap.get(block);
        return triple != null ? triple.verticalSlab() : null;
    }

    public static Block slabToOriginal(Block block) {
        if (!(block instanceof SlabBlock)) {
            return null;
        }
        BlockTriple triple = slabMap.get(block);
        return triple != null ? triple.original() : null;
    }

    public static Block slabToVerticalSlab(Block block) {
        if (!(block instanceof SlabBlock)) {
            return null;
        }
        BlockTriple triple = slabMap.get(block);
        return triple != null ? triple.verticalSlab() : null;
    }

    public static Block verticalSlabToOriginal(Block block) {
        if (!(block instanceof VerticalSlabBlock)) {
            return null;
        }
        BlockTriple triple = verticalSlabMap.get(block);
        return triple != null ? triple.original() : null;
    }

    public static Block verticalSlabToSlab(Block block) {
        if (!(block instanceof VerticalSlabBlock)) {
            return null;
        }
        BlockTriple triple = verticalSlabMap.get(block);
        return triple != null ? triple.slab() : null;
    }
}
