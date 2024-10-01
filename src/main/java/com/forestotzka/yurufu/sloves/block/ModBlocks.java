package com.forestotzka.yurufu.sloves.block;

import com.forestotzka.yurufu.sloves.Sloves;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class ModBlocks {
    public static final Block OAK_LOG_SLAB = registerBlock("oak_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block SPRUCE_LOG_SLAB = registerBlock("spruce_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block BIRCH_LOG_SLAB = registerBlock("birch_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block JUNGLE_LOG_SLAB = registerBlock("jungle_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block ACACIA_LOG_SLAB = registerBlock("acacia_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block DARK_OAK_LOG_SLAB = registerBlock("dark_oak_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block MANGROVE_LOG_SLAB = registerBlock("mangrove_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.RED).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block CHERRY_LOG_SLAB = registerBlock("cherry_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.CHERRY_WOOD).burnable()));
    public static final Block BAMBOO_SLAB = registerBlock("bamboo_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).strength(2.0F).sounds(BlockSoundGroup.BAMBOO_WOOD).burnable()));
    public static final Block CRIMSON_STEM_SLAB = registerBlock("crimson_stem_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.DULL_PINK).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM)));
    public static final Block WARPED_STEM_SLAB = registerBlock("warped_stem_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_AQUA).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM)));
    public static final Block OAK_WOOD_SLAB = registerBlock("oak_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block SPRUCE_WOOD_SLAB = registerBlock("spruce_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block BIRCH_WOOD_SLAB = registerBlock("birch_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block JUNGLE_WOOD_SLAB = registerBlock("jungle_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block ACACIA_WOOD_SLAB = registerBlock("acacia_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block DARK_OAK_WOOD_SLAB = registerBlock("dark_oak_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block MANGROVE_WOOD_SLAB = registerBlock("mangrove_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.RED).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block CHERRY_WOOD_SLAB = registerBlock("cherry_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.CHERRY_WOOD).burnable()));
    public static final Block CRIMSON_HYPHAE_SLAB = registerBlock("crimson_hyphae_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_CRIMSON).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM)));
    public static final Block WARPED_HYPHAE_SLAB = registerBlock("warped_hyphae_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_DULL_PINK).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM)));
    public static final Block STRIPPED_OAK_LOG_SLAB = registerBlock("stripped_oak_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_SPRUCE_LOG_SLAB = registerBlock("stripped_spruce_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_BIRCH_LOG_SLAB = registerBlock("stripped_birch_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_JUNGLE_LOG_SLAB = registerBlock("stripped_jungle_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_ACACIA_LOG_SLAB = registerBlock("stripped_acacia_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_DARK_OAK_LOG_SLAB = registerBlock("stripped_dark_oak_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_MANGROVE_LOG_SLAB = registerBlock("stripped_mangrove_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.RED).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_CHERRY_LOG_SLAB = registerBlock("stripped_cherry_log_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.CHERRY_WOOD).burnable()));
    public static final Block STRIPPED_BAMBOO_SLAB = registerBlock("stripped_bamboo_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.BAMBOO_WOOD).burnable()));
    public static final Block STRIPPED_CRIMSON_STEM_SLAB = registerBlock("stripped_crimson_stem_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.DULL_PINK).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM)));
    public static final Block STRIPPED_WARPED_STEM_SLAB = registerBlock("stripped_warped_stem_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_AQUA).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM)));
    public static final Block STRIPPED_OAK_WOOD_SLAB = registerBlock("stripped_oak_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_SPRUCE_WOOD_SLAB = registerBlock("stripped_spruce_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_BIRCH_WOOD_SLAB = registerBlock("stripped_birch_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_JUNGLE_WOOD_SLAB = registerBlock("stripped_jungle_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_ACACIA_WOOD_SLAB = registerBlock("stripped_acacia_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_DARK_OAK_WOOD_SLAB = registerBlock("stripped_dark_oak_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_MANGROVE_WOOD_SLAB = registerBlock("stripped_mangrove_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.RED).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_CHERRY_WOOD_SLAB = registerBlock("stripped_cherry_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_PINK).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.CHERRY_WOOD).burnable()));
    public static final Block STRIPPED_CRIMSON_HYPHAE_SLAB = registerBlock("stripped_crimson_hyphae_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_CRIMSON).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM)));
    public static final Block STRIPPED_WARPED_HYPHAE_SLAB = registerBlock("stripped_warped_hyphae_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_DULL_PINK).instrument(NoteBlockInstrument.BASS).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM)));
    public static final Block DIRT_SLAB = registerBlock("dirt_slab",
            new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).strength(0.5f).sounds(BlockSoundGroup.GRAVEL)));
    public static final Block GRASS_SLAB = registerBlock("grass_slab",
            new GrassSlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_GREEN).strength(0.6f).sounds(BlockSoundGroup.GRASS)));
    public static final Block OAK_VERTICAL_SLAB = registerBlock("oak_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(1.0F, 2.0F).requiresTool().sounds(BlockSoundGroup.WOOD)));
    public static final Block SPRUCE_VERTICAL_SLAB = registerBlock("spruce_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(1.0F, 2.0F).requiresTool().sounds(BlockSoundGroup.WOOD)));
    public static final Block BIRCH_VERTICAL_SLAB = registerBlock("birch_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(1.0F, 2.0F).requiresTool().sounds(BlockSoundGroup.WOOD)));
    public static final Block JUNGLE_VERTICAL_SLAB = registerBlock("jungle_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(1.0F, 2.0F).requiresTool().sounds(BlockSoundGroup.WOOD)));
    public static final Block ACACIA_VERTICAL_SLAB = registerBlock("acacia_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(1.0F, 2.0F).requiresTool().sounds(BlockSoundGroup.WOOD)));
    public static final Block DARK_OAK_VERTICAL_SLAB = registerBlock("dark_oak_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(1.0F, 2.0F).requiresTool().sounds(BlockSoundGroup.WOOD)));
    public static final Block MANGROVE_VERTICAL_SLAB = registerBlock("mangrove_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(1.0F, 2.0F).requiresTool().sounds(BlockSoundGroup.WOOD)));
    public static final Block CHERRY_VERTICAL_SLAB = registerBlock("cherry_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(1.0F, 2.0F).requiresTool().sounds(BlockSoundGroup.WOOD)));
    public static final Block BAMBOO_VERTICAL_SLAB = registerBlock("bamboo_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(1.0F, 2.0F).requiresTool().sounds(BlockSoundGroup.WOOD)));
    public static final Block CRIMSON_VERTICAL_SLAB = registerBlock("crimson_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(1.0F, 2.0F).requiresTool().sounds(BlockSoundGroup.WOOD)));
    public static final Block WARPED_VERTICAL_SLAB = registerBlock("warped_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(1.0F, 2.0F).requiresTool().sounds(BlockSoundGroup.WOOD)));
    public static final Block STONE_VERTICAL_SLAB = registerBlock("stone_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    //ここから詳細未設定
    public static final Block BAMBOO_MOSAIC_VERTICAL_SLAB = registerBlock("bamboo_mosaic_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block SMOOTH_STONE_VERTICAL_SLAB = registerBlock("smooth_stone_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block STONE_BRICK_VERTICAL_SLAB = registerBlock("stone_brick_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block SANDSTONE_VERTICAL_SLAB = registerBlock("sandstone_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block PURPUR_VERTICAL_SLAB = registerBlock("purpur_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block QUARTZ_VERTICAL_SLAB = registerBlock("quartz_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block RED_SANDSTONE_VERTICAL_SLAB = registerBlock("red_sandstone_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block BRICK_VERTICAL_SLAB = registerBlock("brick_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block COBBLESTONE_VERTICAL_SLAB = registerBlock("cobblestone_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block NETHER_BRICK_VERTICAL_SLAB = registerBlock("nether_brick_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block PETRIFIED_OAK_VERTICAL_SLAB = registerBlock("petrified_oak_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block PRISMARINE_VERTICAL_SLAB = registerBlock("prismarine_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block PRISMARINE_BRICK_VERTICAL_SLAB = registerBlock("prismarine_brick_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block DARK_PRISMARINE_VERTICAL_SLAB = registerBlock("dark_prismarine_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block POLISHED_GRANITE_VERTICAL_SLAB = registerBlock("polished_granite_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block SMOOTH_RED_SANDSTONE_VERTICAL_SLAB = registerBlock("smooth_red_sandstone_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_STONE_BRICK_VERTICAL_SLAB = registerBlock("mossy_stone_brick_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block POLISHED_DIORITE_VERTICAL_SLAB = registerBlock("polished_diorite_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block MOSSY_COBBLESTONE_VERTICAL_SLAB = registerBlock("mossy_cobblestone_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block END_STONE_BRICK_VERTICAL_SLAB = registerBlock("end_stone_brick_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block SMOOTH_SANDSTONE_VERTICAL_SLAB = registerBlock("smooth_sandstone_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block SMOOTH_QUARTZ_VERTICAL_SLAB = registerBlock("smooth_quartz_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block GRANITE_VERTICAL_SLAB = registerBlock("granite_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block ANDESITE_VERTICAL_SLAB = registerBlock("andesite_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block RED_NETHER_BRICK_VERTICAL_SLAB = registerBlock("red_nether_brick_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block POLISHED_ANDESITE_VERTICAL_SLAB = registerBlock("polished_andesite_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block DIORITE_VERTICAL_SLAB = registerBlock("diorite_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block CUT_SANDSTONE_VERTICAL_SLAB = registerBlock("cut_sandstone_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block CUT_RED_SANDSTONE_VERTICAL_SLAB = registerBlock("cut_red_sandstone_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block BLACKSTONE_VERTICAL_SLAB = registerBlock("blackstone_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB = registerBlock("polished_blackstone_brick_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block POLISHED_BLACKSTONE_VERTICAL_SLAB = registerBlock("polished_blackstone_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block COBBLED_DEEPSLATE_VERTICAL_SLAB = registerBlock("cobbled_deepslate_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block POLISHED_DEEPSLATE_VERTICAL_SLAB = registerBlock("polished_deepslate_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block DEEPSLATE_TILE_VERTICAL_SLAB = registerBlock("deepslate_tile_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block DEEPSLATE_BRICK_VERTICAL_SLAB = registerBlock("deepslate_brick_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_weathered_cut_copper_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_exposed_cut_copper_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block WAXED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_cut_copper_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerBlock("oxidized_cut_copper_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerBlock("weathered_cut_copper_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerBlock("exposed_cut_copper_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block CUT_COPPER_VERTICAL_SLAB = registerBlock("cut_copper_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_oxidized_cut_copper_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block MUD_BRICK_VERTICAL_SLAB = registerBlock("mud_brick_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block TUFF_VERTICAL_SLAB = registerBlock("tuff_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block POLISHED_TUFF_VERTICAL_SLAB = registerBlock("polished_tuff_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block TUFF_BRICK_VERTICAL_SLAB = registerBlock("tuff_brick_vertical_slab",
            new VerticalSlabBlocks(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_STONE_BRICK_SLAB = registerBlock("cracked_stone_brick_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block DEEPSLATE_SLAB = registerBlock("deepslate_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_DEEPSLATE_BRICK_SLAB = registerBlock("cracked_deepslate_brick_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block REINFORCED_DEEPSLATE_SLAB = registerBlock("reinforced_deepslate_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block PACKED_MUD_SLAB = registerBlock("packed_mud_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block NETHERRACK_SLAB = registerBlock("netherrack_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_NETHER_BRICK_SLAB = registerBlock("cracked_nether_brick_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block BASALT_SLAB = registerBlock("basalt_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block SMOOTH_BASALT_SLAB = registerBlock("smooth_basalt_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block POLISHED_BASALT_SLAB = registerBlock("polished_basalt_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block GILDED_BLACKSTONE_SLAB = registerBlock("gilded_blackstone_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB = registerBlock("cracked_polished_blackstone_brick_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block END_STONE_SLAB = registerBlock("end_stone_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block PURPUR_PILLAR_SLAB = registerBlock("purpur_pillar_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block QUARTZ_BRICK_SLAB = registerBlock("quartz_brick_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block QUARTZ_PILLAR_SLAB = registerBlock("quartz_pillar_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block AMETHYST_SLAB = registerBlock("amethyst_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));

    // Wool Slabs
    public static final Block WHITE_WOOL_SLAB = registerBlock("white_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));
    public static final Block LIGHT_GRAY_WOOL_SLAB = registerBlock("light_gray_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));
    public static final Block GRAY_WOOL_SLAB = registerBlock("gray_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));
    public static final Block BLACK_WOOL_SLAB = registerBlock("black_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));
    public static final Block BROWN_WOOL_SLAB = registerBlock("brown_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));
    public static final Block RED_WOOL_SLAB = registerBlock("red_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));
    public static final Block ORANGE_WOOL_SLAB = registerBlock("orange_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));
    public static final Block YELLOW_WOOL_SLAB = registerBlock("yellow_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));
    public static final Block LIME_WOOL_SLAB = registerBlock("lime_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));
    public static final Block GREEN_WOOL_SLAB = registerBlock("green_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));
    public static final Block CYAN_WOOL_SLAB = registerBlock("cyan_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));
    public static final Block LIGHT_BLUE_WOOL_SLAB = registerBlock("light_blue_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));
    public static final Block BLUE_WOOL_SLAB = registerBlock("blue_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));
    public static final Block PURPLE_WOOL_SLAB = registerBlock("purple_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));
    public static final Block MAGENTA_WOOL_SLAB = registerBlock("magenta_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));
    public static final Block PINK_WOOL_SLAB = registerBlock("pink_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.8F).sounds(BlockSoundGroup.WOOL)));

    // Terracotta Slabs
    public static final Block TERRACOTTA_SLAB = registerBlock("terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block WHITE_TERRACOTTA_SLAB = registerBlock("white_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block LIGHT_GRAY_TERRACOTTA_SLAB = registerBlock("light_gray_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block GRAY_TERRACOTTA_SLAB = registerBlock("gray_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block BLACK_TERRACOTTA_SLAB = registerBlock("black_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block BROWN_TERRACOTTA_SLAB = registerBlock("brown_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block RED_TERRACOTTA_SLAB = registerBlock("red_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block ORANGE_TERRACOTTA_SLAB = registerBlock("orange_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block YELLOW_TERRACOTTA_SLAB = registerBlock("yellow_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block LIME_TERRACOTTA_SLAB = registerBlock("lime_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block GREEN_TERRACOTTA_SLAB = registerBlock("green_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block CYAN_TERRACOTTA_SLAB = registerBlock("cyan_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block LIGHT_BLUE_TERRACOTTA_SLAB = registerBlock("light_blue_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block BLUE_TERRACOTTA_SLAB = registerBlock("blue_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block PURPLE_TERRACOTTA_SLAB = registerBlock("purple_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block MAGENTA_TERRACOTTA_SLAB = registerBlock("magenta_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block PINK_TERRACOTTA_SLAB = registerBlock("pink_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.25F, 4.2F).requiresTool().sounds(BlockSoundGroup.STONE)));

    // Concrete Slabs
    public static final Block WHITE_CONCRETE_SLAB = registerBlock("white_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block LIGHT_GRAY_CONCRETE_SLAB = registerBlock("light_gray_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block GRAY_CONCRETE_SLAB = registerBlock("gray_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block BLACK_CONCRETE_SLAB = registerBlock("black_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block BROWN_CONCRETE_SLAB = registerBlock("brown_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block RED_CONCRETE_SLAB = registerBlock("red_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block ORANGE_CONCRETE_SLAB = registerBlock("orange_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block YELLOW_CONCRETE_SLAB = registerBlock("yellow_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block LIME_CONCRETE_SLAB = registerBlock("lime_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block GREEN_CONCRETE_SLAB = registerBlock("green_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block CYAN_CONCRETE_SLAB = registerBlock("cyan_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block LIGHT_BLUE_CONCRETE_SLAB = registerBlock("light_blue_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block BLUE_CONCRETE_SLAB = registerBlock("blue_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block PURPLE_CONCRETE_SLAB = registerBlock("purple_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block MAGENTA_CONCRETE_SLAB = registerBlock("magenta_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block PINK_CONCRETE_SLAB = registerBlock("pink_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(1.8F, 3.0F).requiresTool().sounds(BlockSoundGroup.STONE)));

    // Concrete Powder Slabs
    public static final Block WHITE_CONCRETE_POWDER_SLAB = registerBlock("white_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block LIGHT_GRAY_CONCRETE_POWDER_SLAB = registerBlock("light_gray_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block GRAY_CONCRETE_POWDER_SLAB = registerBlock("gray_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block BLACK_CONCRETE_POWDER_SLAB = registerBlock("black_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block BROWN_CONCRETE_POWDER_SLAB = registerBlock("brown_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block RED_CONCRETE_POWDER_SLAB = registerBlock("red_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block ORANGE_CONCRETE_POWDER_SLAB = registerBlock("orange_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block YELLOW_CONCRETE_POWDER_SLAB = registerBlock("yellow_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block LIME_CONCRETE_POWDER_SLAB = registerBlock("lime_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block GREEN_CONCRETE_POWDER_SLAB = registerBlock("green_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block CYAN_CONCRETE_POWDER_SLAB = registerBlock("cyan_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block LIGHT_BLUE_CONCRETE_POWDER_SLAB = registerBlock("light_blue_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block BLUE_CONCRETE_POWDER_SLAB = registerBlock("blue_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block PURPLE_CONCRETE_POWDER_SLAB = registerBlock("purple_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block MAGENTA_CONCRETE_POWDER_SLAB = registerBlock("magenta_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));
    public static final Block PINK_CONCRETE_POWDER_SLAB = registerBlock("pink_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(0.5F).sounds(BlockSoundGroup.SAND)));

    public static final Block DOUBLE_SLAB_BLOCK = registerBlock("double_slab_block",
            new DoubleSlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block DOUBLE_VERTICAL_SLAB_BLOCK = registerBlock("double_vertical_slab_block",
            new DoubleVerticalSlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Sloves.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Sloves.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        Sloves.LOGGER.info("Registering Mod Blocks for " + Sloves.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.addAfter(Blocks.OAK_LOG, ModBlocks.OAK_LOG_SLAB);
            entries.addAfter(Blocks.SPRUCE_LOG, ModBlocks.SPRUCE_LOG_SLAB);
            entries.addAfter(Blocks.BIRCH_LOG, ModBlocks.BIRCH_LOG_SLAB);
            entries.addAfter(Blocks.JUNGLE_LOG, ModBlocks.JUNGLE_LOG_SLAB);
            entries.addAfter(Blocks.ACACIA_LOG, ModBlocks.ACACIA_LOG_SLAB);
            entries.addAfter(Blocks.DARK_OAK_LOG, ModBlocks.DARK_OAK_LOG_SLAB);
            entries.addAfter(Blocks.MANGROVE_LOG, ModBlocks.MANGROVE_LOG_SLAB);
            entries.addAfter(Blocks.CHERRY_LOG, ModBlocks.CHERRY_LOG_SLAB);
            entries.addAfter(Blocks.BAMBOO_BLOCK, ModBlocks.BAMBOO_SLAB);
            entries.addAfter(Blocks.CRIMSON_STEM, ModBlocks.CRIMSON_STEM_SLAB);
            entries.addAfter(Blocks.OAK_WOOD, ModBlocks.OAK_WOOD_SLAB);
            entries.addAfter(Blocks.SPRUCE_WOOD, ModBlocks.SPRUCE_WOOD_SLAB);
            entries.addAfter(Blocks.BIRCH_WOOD, ModBlocks.BIRCH_WOOD_SLAB);
            entries.addAfter(Blocks.JUNGLE_WOOD, ModBlocks.JUNGLE_WOOD_SLAB);
            entries.addAfter(Blocks.ACACIA_WOOD, ModBlocks.ACACIA_WOOD_SLAB);
            entries.addAfter(Blocks.DARK_OAK_WOOD, ModBlocks.DARK_OAK_WOOD_SLAB);
            entries.addAfter(Blocks.MANGROVE_WOOD, ModBlocks.MANGROVE_WOOD_SLAB);
            entries.addAfter(Blocks.CHERRY_WOOD, ModBlocks.CHERRY_WOOD_SLAB);
            entries.addAfter(Blocks.CRIMSON_HYPHAE, ModBlocks.CRIMSON_HYPHAE_SLAB);
            entries.addAfter(Blocks.WARPED_HYPHAE, ModBlocks.WARPED_HYPHAE_SLAB);
            entries.addAfter(Blocks.WARPED_STEM, ModBlocks.WARPED_STEM_SLAB);
            entries.addAfter(Blocks.STRIPPED_OAK_LOG, ModBlocks.STRIPPED_OAK_LOG_SLAB);
            entries.addAfter(Blocks.STRIPPED_SPRUCE_LOG, ModBlocks.STRIPPED_SPRUCE_LOG_SLAB);
            entries.addAfter(Blocks.STRIPPED_BIRCH_LOG, ModBlocks.STRIPPED_BIRCH_LOG_SLAB);
            entries.addAfter(Blocks.STRIPPED_JUNGLE_LOG, ModBlocks.STRIPPED_JUNGLE_LOG_SLAB);
            entries.addAfter(Blocks.STRIPPED_ACACIA_LOG, ModBlocks.STRIPPED_ACACIA_LOG_SLAB);
            entries.addAfter(Blocks.STRIPPED_DARK_OAK_LOG, ModBlocks.STRIPPED_DARK_OAK_LOG_SLAB);
            entries.addAfter(Blocks.STRIPPED_MANGROVE_LOG, ModBlocks.STRIPPED_MANGROVE_LOG_SLAB);
            entries.addAfter(Blocks.STRIPPED_CHERRY_LOG, ModBlocks.STRIPPED_CHERRY_LOG_SLAB);
            entries.addAfter(Blocks.STRIPPED_BAMBOO_BLOCK, ModBlocks.STRIPPED_BAMBOO_SLAB);
            entries.addAfter(Blocks.STRIPPED_CRIMSON_STEM, ModBlocks.STRIPPED_CRIMSON_STEM_SLAB);
            entries.addAfter(Blocks.STRIPPED_WARPED_STEM, ModBlocks.STRIPPED_WARPED_STEM_SLAB);
            entries.addAfter(Blocks.STRIPPED_OAK_WOOD, ModBlocks.STRIPPED_OAK_WOOD_SLAB);
            entries.addAfter(Blocks.STRIPPED_SPRUCE_WOOD, ModBlocks.STRIPPED_SPRUCE_WOOD_SLAB);
            entries.addAfter(Blocks.STRIPPED_BIRCH_WOOD, ModBlocks.STRIPPED_BIRCH_WOOD_SLAB);
            entries.addAfter(Blocks.STRIPPED_JUNGLE_WOOD, ModBlocks.STRIPPED_JUNGLE_WOOD_SLAB);
            entries.addAfter(Blocks.STRIPPED_ACACIA_WOOD, ModBlocks.STRIPPED_ACACIA_WOOD_SLAB);
            entries.addAfter(Blocks.STRIPPED_DARK_OAK_WOOD, ModBlocks.STRIPPED_DARK_OAK_WOOD_SLAB);
            entries.addAfter(Blocks.STRIPPED_MANGROVE_WOOD, ModBlocks.STRIPPED_MANGROVE_WOOD_SLAB);
            entries.addAfter(Blocks.STRIPPED_CHERRY_WOOD, ModBlocks.STRIPPED_CHERRY_WOOD_SLAB);
            entries.addAfter(Blocks.STRIPPED_CRIMSON_HYPHAE, ModBlocks.STRIPPED_CRIMSON_HYPHAE_SLAB);
            entries.addAfter(Blocks.STRIPPED_WARPED_HYPHAE, ModBlocks.STRIPPED_WARPED_HYPHAE_SLAB);
            entries.add(ModBlocks.DIRT_SLAB);
            entries.add(ModBlocks.GRASS_SLAB);
            entries.addAfter(ModBlocks.CRACKED_STONE_BRICK_SLAB, ModBlocks.CRACKED_STONE_BRICK_SLAB);
            entries.addAfter(ModBlocks.DEEPSLATE_SLAB, ModBlocks.DEEPSLATE_SLAB);
            entries.addAfter(ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB, ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB);
            entries.addAfter(ModBlocks.REINFORCED_DEEPSLATE_SLAB, ModBlocks.REINFORCED_DEEPSLATE_SLAB);
            entries.addAfter(ModBlocks.PACKED_MUD_SLAB, ModBlocks.PACKED_MUD_SLAB);
            entries.addAfter(ModBlocks.NETHERRACK_SLAB, ModBlocks.NETHERRACK_SLAB);
            entries.addAfter(ModBlocks.CRACKED_NETHER_BRICK_SLAB, ModBlocks.CRACKED_NETHER_BRICK_SLAB);
            entries.addAfter(ModBlocks.BASALT_SLAB, ModBlocks.BASALT_SLAB);
            entries.addAfter(ModBlocks.SMOOTH_BASALT_SLAB, ModBlocks.SMOOTH_BASALT_SLAB);
            entries.addAfter(ModBlocks.POLISHED_BASALT_SLAB, ModBlocks.POLISHED_BASALT_SLAB);
            entries.addAfter(ModBlocks.GILDED_BLACKSTONE_SLAB, ModBlocks.GILDED_BLACKSTONE_SLAB);
            entries.addAfter(ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB);
            entries.addAfter(ModBlocks.END_STONE_SLAB, ModBlocks.END_STONE_SLAB);
            entries.addAfter(ModBlocks.PURPUR_PILLAR_SLAB, ModBlocks.PURPUR_PILLAR_SLAB);
            entries.addAfter(ModBlocks.QUARTZ_BRICK_SLAB, ModBlocks.QUARTZ_BRICK_SLAB);
            entries.addAfter(ModBlocks.QUARTZ_PILLAR_SLAB, ModBlocks.QUARTZ_PILLAR_SLAB);
            entries.addAfter(ModBlocks.AMETHYST_SLAB, ModBlocks.AMETHYST_SLAB);

            // Wool Slabs
            entries.addAfter(ModBlocks.WHITE_WOOL_SLAB, ModBlocks.WHITE_WOOL_SLAB);
            entries.addAfter(ModBlocks.LIGHT_GRAY_WOOL_SLAB, ModBlocks.LIGHT_GRAY_WOOL_SLAB);
            entries.addAfter(ModBlocks.GRAY_WOOL_SLAB, ModBlocks.GRAY_WOOL_SLAB);
            entries.addAfter(ModBlocks.BLACK_WOOL_SLAB, ModBlocks.BLACK_WOOL_SLAB);
            entries.addAfter(ModBlocks.BROWN_WOOL_SLAB, ModBlocks.BROWN_WOOL_SLAB);
            entries.addAfter(ModBlocks.RED_WOOL_SLAB, ModBlocks.RED_WOOL_SLAB);
            entries.addAfter(ModBlocks.ORANGE_WOOL_SLAB, ModBlocks.ORANGE_WOOL_SLAB);
            entries.addAfter(ModBlocks.YELLOW_WOOL_SLAB, ModBlocks.YELLOW_WOOL_SLAB);
            entries.addAfter(ModBlocks.LIME_WOOL_SLAB, ModBlocks.LIME_WOOL_SLAB);
            entries.addAfter(ModBlocks.GREEN_WOOL_SLAB, ModBlocks.GREEN_WOOL_SLAB);
            entries.addAfter(ModBlocks.CYAN_WOOL_SLAB, ModBlocks.CYAN_WOOL_SLAB);
            entries.addAfter(ModBlocks.LIGHT_BLUE_WOOL_SLAB, ModBlocks.LIGHT_BLUE_WOOL_SLAB);
            entries.addAfter(ModBlocks.BLUE_WOOL_SLAB, ModBlocks.BLUE_WOOL_SLAB);
            entries.addAfter(ModBlocks.PURPLE_WOOL_SLAB, ModBlocks.PURPLE_WOOL_SLAB);
            entries.addAfter(ModBlocks.MAGENTA_WOOL_SLAB, ModBlocks.MAGENTA_WOOL_SLAB);
            entries.addAfter(ModBlocks.PINK_WOOL_SLAB, ModBlocks.PINK_WOOL_SLAB);

            // Terracotta Slabs
            entries.addAfter(ModBlocks.TERRACOTTA_SLAB, ModBlocks.TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.WHITE_TERRACOTTA_SLAB, ModBlocks.WHITE_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.LIGHT_GRAY_TERRACOTTA_SLAB, ModBlocks.LIGHT_GRAY_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.GRAY_TERRACOTTA_SLAB, ModBlocks.GRAY_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.BLACK_TERRACOTTA_SLAB, ModBlocks.BLACK_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.BROWN_TERRACOTTA_SLAB, ModBlocks.BROWN_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.RED_TERRACOTTA_SLAB, ModBlocks.RED_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.ORANGE_TERRACOTTA_SLAB, ModBlocks.ORANGE_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.YELLOW_TERRACOTTA_SLAB, ModBlocks.YELLOW_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.LIME_TERRACOTTA_SLAB, ModBlocks.LIME_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.GREEN_TERRACOTTA_SLAB, ModBlocks.GREEN_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.CYAN_TERRACOTTA_SLAB, ModBlocks.CYAN_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.LIGHT_BLUE_TERRACOTTA_SLAB, ModBlocks.LIGHT_BLUE_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.BLUE_TERRACOTTA_SLAB, ModBlocks.BLUE_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.PURPLE_TERRACOTTA_SLAB, ModBlocks.PURPLE_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.MAGENTA_TERRACOTTA_SLAB, ModBlocks.MAGENTA_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.PINK_TERRACOTTA_SLAB, ModBlocks.PINK_TERRACOTTA_SLAB);

            // Concrete Slabs
            entries.addAfter(Blocks.PINK_CONCRETE, ModBlocks.WHITE_CONCRETE_SLAB);
            entries.addAfter(ModBlocks.WHITE_CONCRETE_SLAB, ModBlocks.LIGHT_GRAY_CONCRETE_SLAB);
            entries.addAfter(ModBlocks.LIGHT_GRAY_CONCRETE_SLAB, ModBlocks.GRAY_CONCRETE_SLAB);
            entries.addAfter(ModBlocks.GRAY_CONCRETE_SLAB, ModBlocks.BLACK_CONCRETE_SLAB);
            entries.addAfter(ModBlocks.BLACK_CONCRETE_SLAB, ModBlocks.BROWN_CONCRETE_SLAB);
            entries.addAfter(ModBlocks.BROWN_CONCRETE_SLAB, ModBlocks.RED_CONCRETE_SLAB);
            entries.addAfter(ModBlocks.RED_CONCRETE_SLAB, ModBlocks.ORANGE_CONCRETE_SLAB);
            entries.addAfter(ModBlocks.ORANGE_CONCRETE_SLAB, ModBlocks.YELLOW_CONCRETE_SLAB);
            entries.addAfter(ModBlocks.YELLOW_CONCRETE_SLAB, ModBlocks.LIME_CONCRETE_SLAB);
            entries.addAfter(ModBlocks.LIME_CONCRETE_SLAB, ModBlocks.GREEN_CONCRETE_SLAB);
            entries.addAfter(ModBlocks.GREEN_CONCRETE_SLAB, ModBlocks.CYAN_CONCRETE_SLAB);
            entries.addAfter(ModBlocks.CYAN_CONCRETE_SLAB, ModBlocks.LIGHT_BLUE_CONCRETE_SLAB);
            entries.addAfter(ModBlocks.LIGHT_BLUE_CONCRETE_SLAB, ModBlocks.BLUE_CONCRETE_SLAB);
            entries.addAfter(ModBlocks.BLUE_CONCRETE_SLAB, ModBlocks.PURPLE_CONCRETE_SLAB);
            entries.addAfter(ModBlocks.PURPLE_CONCRETE_SLAB, ModBlocks.MAGENTA_CONCRETE_SLAB);
            entries.addAfter(ModBlocks.MAGENTA_CONCRETE_SLAB, ModBlocks.PINK_CONCRETE_SLAB);

            // Concrete Powder Slabs
            entries.addAfter(ModBlocks.WHITE_CONCRETE_POWDER_SLAB, ModBlocks.WHITE_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.LIGHT_GRAY_CONCRETE_POWDER_SLAB, ModBlocks.LIGHT_GRAY_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.GRAY_CONCRETE_POWDER_SLAB, ModBlocks.GRAY_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.BLACK_CONCRETE_POWDER_SLAB, ModBlocks.BLACK_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.BROWN_CONCRETE_POWDER_SLAB, ModBlocks.BROWN_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.RED_CONCRETE_POWDER_SLAB, ModBlocks.RED_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.ORANGE_CONCRETE_POWDER_SLAB, ModBlocks.ORANGE_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.YELLOW_CONCRETE_POWDER_SLAB, ModBlocks.YELLOW_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.LIME_CONCRETE_POWDER_SLAB, ModBlocks.LIME_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.GREEN_CONCRETE_POWDER_SLAB, ModBlocks.GREEN_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.CYAN_CONCRETE_POWDER_SLAB, ModBlocks.CYAN_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.LIGHT_BLUE_CONCRETE_POWDER_SLAB, ModBlocks.LIGHT_BLUE_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.BLUE_CONCRETE_POWDER_SLAB, ModBlocks.BLUE_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.PURPLE_CONCRETE_POWDER_SLAB, ModBlocks.PURPLE_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.MAGENTA_CONCRETE_POWDER_SLAB, ModBlocks.MAGENTA_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.PINK_CONCRETE_POWDER_SLAB, ModBlocks.PINK_CONCRETE_POWDER_SLAB);

            entries.addAfter(Blocks.OAK_SLAB, ModBlocks.OAK_VERTICAL_SLAB);
            entries.addAfter(Blocks.SPRUCE_SLAB, ModBlocks.SPRUCE_VERTICAL_SLAB);
            entries.addAfter(Blocks.BIRCH_SLAB, ModBlocks.BIRCH_VERTICAL_SLAB);
            entries.addAfter(Blocks.JUNGLE_SLAB, ModBlocks.JUNGLE_VERTICAL_SLAB);
            entries.addAfter(Blocks.ACACIA_SLAB, ModBlocks.ACACIA_VERTICAL_SLAB);
            entries.addAfter(Blocks.DARK_OAK_SLAB, ModBlocks.DARK_OAK_VERTICAL_SLAB);
            entries.addAfter(Blocks.MANGROVE_SLAB, ModBlocks.MANGROVE_VERTICAL_SLAB);
            entries.addAfter(Blocks.CHERRY_SLAB, ModBlocks.CHERRY_VERTICAL_SLAB);
            entries.addAfter(Blocks.BAMBOO_SLAB, ModBlocks.BAMBOO_VERTICAL_SLAB);
            entries.addAfter(Blocks.CRIMSON_SLAB, ModBlocks.CRIMSON_VERTICAL_SLAB);
            entries.addAfter(Blocks.WARPED_SLAB, ModBlocks.WARPED_VERTICAL_SLAB);
            entries.addAfter(Blocks.STONE_SLAB, ModBlocks.STONE_VERTICAL_SLAB);
            entries.addAfter(Blocks.BAMBOO_MOSAIC_SLAB, ModBlocks.BAMBOO_MOSAIC_VERTICAL_SLAB);
            entries.addAfter(Blocks.SMOOTH_STONE_SLAB, ModBlocks.SMOOTH_STONE_VERTICAL_SLAB);
            entries.addAfter(Blocks.STONE_BRICK_SLAB, ModBlocks.STONE_BRICK_VERTICAL_SLAB);
            entries.addAfter(Blocks.SANDSTONE_SLAB, ModBlocks.SANDSTONE_VERTICAL_SLAB);
            entries.addAfter(Blocks.PURPUR_SLAB, ModBlocks.PURPUR_VERTICAL_SLAB);
            entries.addAfter(Blocks.QUARTZ_SLAB, ModBlocks.QUARTZ_VERTICAL_SLAB);
            entries.addAfter(Blocks.RED_SANDSTONE_SLAB, ModBlocks.RED_SANDSTONE_VERTICAL_SLAB);
            entries.addAfter(Blocks.BRICK_SLAB, ModBlocks.BRICK_VERTICAL_SLAB);
            entries.addAfter(Blocks.COBBLESTONE_SLAB, ModBlocks.COBBLESTONE_VERTICAL_SLAB);
            entries.addAfter(Blocks.NETHER_BRICK_SLAB, ModBlocks.NETHER_BRICK_VERTICAL_SLAB);
            entries.addAfter(Blocks.PETRIFIED_OAK_SLAB, ModBlocks.PETRIFIED_OAK_VERTICAL_SLAB);
            entries.addAfter(Blocks.PRISMARINE_SLAB, ModBlocks.PRISMARINE_VERTICAL_SLAB);
            entries.addAfter(Blocks.PRISMARINE_BRICK_SLAB, ModBlocks.PRISMARINE_BRICK_VERTICAL_SLAB);
            entries.addAfter(Blocks.DARK_PRISMARINE_SLAB, ModBlocks.DARK_PRISMARINE_VERTICAL_SLAB);
            entries.addAfter(Blocks.POLISHED_GRANITE_SLAB, ModBlocks.POLISHED_GRANITE_VERTICAL_SLAB);
            entries.addAfter(Blocks.SMOOTH_RED_SANDSTONE_SLAB, ModBlocks.SMOOTH_RED_SANDSTONE_VERTICAL_SLAB);
            entries.addAfter(Blocks.MOSSY_STONE_BRICK_SLAB, ModBlocks.MOSSY_STONE_BRICK_VERTICAL_SLAB);
            entries.addAfter(Blocks.POLISHED_DIORITE_SLAB, ModBlocks.POLISHED_DIORITE_VERTICAL_SLAB);
            entries.addAfter(Blocks.MOSSY_COBBLESTONE_SLAB, ModBlocks.MOSSY_COBBLESTONE_VERTICAL_SLAB);
            entries.addAfter(Blocks.END_STONE_BRICK_SLAB, ModBlocks.END_STONE_BRICK_VERTICAL_SLAB);
            entries.addAfter(Blocks.SMOOTH_SANDSTONE_SLAB, ModBlocks.SMOOTH_SANDSTONE_VERTICAL_SLAB);
            entries.addAfter(Blocks.SMOOTH_QUARTZ_SLAB, ModBlocks.SMOOTH_QUARTZ_VERTICAL_SLAB);
            entries.addAfter(Blocks.GRANITE_SLAB, ModBlocks.GRANITE_VERTICAL_SLAB);
            entries.addAfter(Blocks.ANDESITE_SLAB, ModBlocks.ANDESITE_VERTICAL_SLAB);
            entries.addAfter(Blocks.RED_NETHER_BRICK_SLAB, ModBlocks.RED_NETHER_BRICK_VERTICAL_SLAB);
            entries.addAfter(Blocks.POLISHED_ANDESITE_SLAB, ModBlocks.POLISHED_ANDESITE_VERTICAL_SLAB);
            entries.addAfter(Blocks.DIORITE_SLAB, ModBlocks.DIORITE_VERTICAL_SLAB);
            entries.addAfter(Blocks.CUT_SANDSTONE_SLAB, ModBlocks.CUT_SANDSTONE_VERTICAL_SLAB);
            entries.addAfter(Blocks.CUT_RED_SANDSTONE_SLAB, ModBlocks.CUT_RED_SANDSTONE_VERTICAL_SLAB);
            entries.addAfter(Blocks.BLACKSTONE_SLAB, ModBlocks.BLACKSTONE_VERTICAL_SLAB);
            entries.addAfter(Blocks.POLISHED_BLACKSTONE_SLAB, ModBlocks.POLISHED_BLACKSTONE_VERTICAL_SLAB);
            entries.addAfter(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB);
            entries.addAfter(Blocks.COBBLED_DEEPSLATE_SLAB, ModBlocks.COBBLED_DEEPSLATE_VERTICAL_SLAB);
            entries.addAfter(Blocks.POLISHED_DEEPSLATE_SLAB, ModBlocks.POLISHED_DEEPSLATE_VERTICAL_SLAB);
            entries.addAfter(Blocks.DEEPSLATE_TILE_SLAB, ModBlocks.DEEPSLATE_TILE_VERTICAL_SLAB);
            entries.addAfter(Blocks.DEEPSLATE_BRICK_SLAB, ModBlocks.DEEPSLATE_BRICK_VERTICAL_SLAB);
            entries.addAfter(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB, ModBlocks.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB);
            entries.addAfter(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB, ModBlocks.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB);
            entries.addAfter(Blocks.WAXED_CUT_COPPER_SLAB, ModBlocks.WAXED_CUT_COPPER_VERTICAL_SLAB);
            entries.addAfter(Blocks.OXIDIZED_CUT_COPPER_SLAB, ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB);
            entries.addAfter(Blocks.WEATHERED_CUT_COPPER_SLAB, ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB);
            entries.addAfter(Blocks.EXPOSED_CUT_COPPER_SLAB, ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB);
            entries.addAfter(Blocks.CUT_COPPER_SLAB, ModBlocks.CUT_COPPER_VERTICAL_SLAB);
            entries.addAfter(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB, ModBlocks.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB);
            entries.addAfter(Blocks.MUD_BRICK_SLAB, ModBlocks.MUD_BRICK_VERTICAL_SLAB);
            entries.addAfter(Blocks.TUFF_SLAB, ModBlocks.TUFF_VERTICAL_SLAB);
            entries.addAfter(Blocks.POLISHED_TUFF_SLAB, ModBlocks.POLISHED_TUFF_VERTICAL_SLAB);
            entries.addAfter(Blocks.TUFF_BRICK_SLAB, ModBlocks.TUFF_BRICK_VERTICAL_SLAB);

        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {
            entries.add(ModBlocks.DOUBLE_SLAB_BLOCK);
            entries.add(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK);
        });
    }
}
