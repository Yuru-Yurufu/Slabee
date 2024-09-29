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
