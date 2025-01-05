package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.Slabee;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class ModBlocks {

    //////////////
    //   Slab   //
    //////////////

    // Log Slabs
    public static final Block OAK_LOG_SLAB = registerBlock("oak_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block SPRUCE_LOG_SLAB = registerBlock("spruce_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.SPRUCE_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block BIRCH_LOG_SLAB = registerBlock("birch_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_YELLOW)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block JUNGLE_LOG_SLAB = registerBlock("jungle_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DIRT_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block ACACIA_LOG_SLAB = registerBlock("acacia_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.ORANGE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block DARK_OAK_LOG_SLAB = registerBlock("dark_oak_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block MANGROVE_LOG_SLAB = registerBlock("mangrove_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block CHERRY_LOG_SLAB = registerBlock("cherry_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.CHERRY_WOOD)
                    .burnable()));

    public static final Block BAMBOO_BLOCK_SLAB = registerBlock("bamboo_block_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.YELLOW)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.BAMBOO_WOOD)
                    .burnable()));

    public static final Block CRIMSON_STEM_SLAB = registerBlock("crimson_stem_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DULL_PINK)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block WARPED_STEM_SLAB = registerBlock("warped_stem_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_AQUA)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));


    // Wood Slabs
    public static final Block OAK_WOOD_SLAB = registerBlock("oak_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block SPRUCE_WOOD_SLAB = registerBlock("spruce_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.SPRUCE_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block BIRCH_WOOD_SLAB = registerBlock("birch_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_YELLOW)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block JUNGLE_WOOD_SLAB = registerBlock("jungle_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DIRT_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block ACACIA_WOOD_SLAB = registerBlock("acacia_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.GRAY)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block DARK_OAK_WOOD_SLAB = registerBlock("dark_oak_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block MANGROVE_WOOD_SLAB = registerBlock("mangrove_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block CHERRY_WOOD_SLAB = registerBlock("cherry_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.TERRACOTTA_GRAY)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.CHERRY_WOOD)
                    .burnable()));

    public static final Block CRIMSON_HYPHAE_SLAB = registerBlock("crimson_hyphae_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_CRIMSON)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block WARPED_HYPHAE_SLAB = registerBlock("warped_hyphae_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_DULL_PINK)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));


    // Stripped Log Slabs
    public static final Block STRIPPED_OAK_LOG_SLAB = registerBlock("stripped_oak_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_SPRUCE_LOG_SLAB = registerBlock("stripped_spruce_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.SPRUCE_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_BIRCH_LOG_SLAB = registerBlock("stripped_birch_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_YELLOW)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_JUNGLE_LOG_SLAB = registerBlock("stripped_jungle_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DIRT_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_ACACIA_LOG_SLAB = registerBlock("stripped_acacia_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.ORANGE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_DARK_OAK_LOG_SLAB = registerBlock("stripped_dark_oak_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_MANGROVE_LOG_SLAB = registerBlock("stripped_mangrove_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_CHERRY_LOG_SLAB = registerBlock("stripped_cherry_log_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.CHERRY_WOOD)
                    .burnable()));

    public static final Block STRIPPED_BAMBOO_SLAB = registerBlock("stripped_bamboo_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.YELLOW)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.BAMBOO_WOOD)
                    .burnable()));

    public static final Block STRIPPED_CRIMSON_STEM_SLAB = registerBlock("stripped_crimson_stem_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DULL_PINK)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block STRIPPED_WARPED_STEM_SLAB = registerBlock("stripped_warped_stem_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_AQUA)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));


    // Stripped Wood Slabs
    public static final Block STRIPPED_OAK_WOOD_SLAB = registerBlock("stripped_oak_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_SPRUCE_WOOD_SLAB = registerBlock("stripped_spruce_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.SPRUCE_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_BIRCH_WOOD_SLAB = registerBlock("stripped_birch_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_YELLOW)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_JUNGLE_WOOD_SLAB = registerBlock("stripped_jungle_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DIRT_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_ACACIA_WOOD_SLAB = registerBlock("stripped_acacia_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.ORANGE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_DARK_OAK_WOOD_SLAB = registerBlock("stripped_dark_oak_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_MANGROVE_WOOD_SLAB = registerBlock("stripped_mangrove_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_CHERRY_WOOD_SLAB = registerBlock("stripped_cherry_wood_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.TERRACOTTA_PINK)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.CHERRY_WOOD)
                    .burnable()));

    public static final Block STRIPPED_CRIMSON_HYPHAE_SLAB = registerBlock("stripped_crimson_hyphae_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_CRIMSON)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block STRIPPED_WARPED_HYPHAE_SLAB = registerBlock("stripped_warped_hyphae_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_DULL_PINK)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));


    // Stone-related Slabs
    public static final Block CRACKED_STONE_BRICK_SLAB = registerBlock("cracked_stone_brick_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CRACKED_STONE_BRICKS.getDefaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block DEEPSLATE_SLAB = registerBlock("deepslate_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DEEPSLATE.getDefaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)));

    public static final Block CRACKED_DEEPSLATE_BRICK_SLAB = registerBlock("cracked_deepslate_brick_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_BRICKS)));

    public static final Block REINFORCED_DEEPSLATE_SLAB = registerBlock("reinforced_deepslate_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.REINFORCED_DEEPSLATE.getDefaultMapColor())
                    .strength(55.0F, 1200.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)));

    public static final Block PACKED_MUD_SLAB = registerBlock("packed_mud_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PACKED_MUD.getDefaultMapColor())
                    .strength(1.5F)
                    .sounds(BlockSoundGroup.PACKED_MUD)));

    public static final Block NETHERRACK_SLAB = registerBlock("netherrack_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.NETHERRACK.getDefaultMapColor())
                    .strength(0.4F)
                    .sounds(BlockSoundGroup.NETHERRACK)));

    public static final Block CRACKED_NETHER_BRICK_SLAB = registerBlock("cracked_nether_brick_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CRACKED_NETHER_BRICKS.getDefaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.NETHER_BRICKS)));

    public static final Block BASALT_SLAB = registerBlock("basalt_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BASALT.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.BASALT)));

    public static final Block SMOOTH_BASALT_SLAB = registerBlock("smooth_basalt_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SMOOTH_BASALT.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.BASALT)));

    public static final Block POLISHED_BASALT_SLAB = registerBlock("polished_basalt_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.POLISHED_BASALT.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.BASALT)));

    public static final Block GILDED_BLACKSTONE_SLAB = registerBlock("gilded_blackstone_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GILDED_BLACKSTONE.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.GILDED_BLACKSTONE)));

    public static final Block CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB = registerBlock("cracked_polished_blackstone_brick_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block END_STONE_SLAB = registerBlock("end_stone_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.END_STONE.getDefaultMapColor())
                    .strength(3.0F, 9.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PURPUR_PILLAR_SLAB = registerBlock("purpur_pillar_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PURPUR_PILLAR.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block QUARTZ_BRICK_SLAB = registerBlock("quartz_brick_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.QUARTZ_BRICKS.getDefaultMapColor())
                    .strength(0.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block QUARTZ_PILLAR_SLAB = registerBlock("quartz_pillar_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.QUARTZ_PILLAR.getDefaultMapColor())
                    .strength(0.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block AMETHYST_SLAB = registerBlock("amethyst_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.AMETHYST_BLOCK.getDefaultMapColor())
                    .strength(1.5F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK)));


    // Wool Slabs
    public static final Block WHITE_WOOL_SLAB = registerBlock("white_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WHITE_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));

    public static final Block LIGHT_GRAY_WOOL_SLAB = registerBlock("light_gray_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIGHT_GRAY_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));

    public static final Block GRAY_WOOL_SLAB = registerBlock("gray_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GRAY_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));

    public static final Block BLACK_WOOL_SLAB = registerBlock("black_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLACK_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));

    public static final Block BROWN_WOOL_SLAB = registerBlock("brown_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BROWN_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));

    public static final Block RED_WOOL_SLAB = registerBlock("red_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.RED_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));

    public static final Block ORANGE_WOOL_SLAB = registerBlock("orange_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.ORANGE_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));

    public static final Block YELLOW_WOOL_SLAB = registerBlock("yellow_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.YELLOW_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));

    public static final Block LIME_WOOL_SLAB = registerBlock("lime_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIME_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));

    public static final Block GREEN_WOOL_SLAB = registerBlock("green_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GREEN_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));

    public static final Block CYAN_WOOL_SLAB = registerBlock("cyan_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CYAN_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));

    public static final Block LIGHT_BLUE_WOOL_SLAB = registerBlock("light_blue_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIGHT_BLUE_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));

    public static final Block BLUE_WOOL_SLAB = registerBlock("blue_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLUE_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));

    public static final Block PURPLE_WOOL_SLAB = registerBlock("purple_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PURPLE_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));

    public static final Block MAGENTA_WOOL_SLAB = registerBlock("magenta_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MAGENTA_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));

    public static final Block PINK_WOOL_SLAB = registerBlock("pink_wool_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PINK_WOOL.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .burnable()));


    // Terracotta Slabs
    public static final Block TERRACOTTA_SLAB = registerBlock("terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block WHITE_TERRACOTTA_SLAB = registerBlock("white_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WHITE_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LIGHT_GRAY_TERRACOTTA_SLAB = registerBlock("light_gray_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GRAY_TERRACOTTA_SLAB = registerBlock("gray_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GRAY_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BLACK_TERRACOTTA_SLAB = registerBlock("black_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLACK_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BROWN_TERRACOTTA_SLAB = registerBlock("brown_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BROWN_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block RED_TERRACOTTA_SLAB = registerBlock("red_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.RED_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block ORANGE_TERRACOTTA_SLAB = registerBlock("orange_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.ORANGE_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block YELLOW_TERRACOTTA_SLAB = registerBlock("yellow_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.YELLOW_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LIME_TERRACOTTA_SLAB = registerBlock("lime_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIME_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GREEN_TERRACOTTA_SLAB = registerBlock("green_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GREEN_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block CYAN_TERRACOTTA_SLAB = registerBlock("cyan_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CYAN_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LIGHT_BLUE_TERRACOTTA_SLAB = registerBlock("light_blue_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIGHT_BLUE_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BLUE_TERRACOTTA_SLAB = registerBlock("blue_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLUE_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PURPLE_TERRACOTTA_SLAB = registerBlock("purple_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PURPLE_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MAGENTA_TERRACOTTA_SLAB = registerBlock("magenta_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MAGENTA_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PINK_TERRACOTTA_SLAB = registerBlock("pink_terracotta_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PINK_TERRACOTTA.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));


    // Concrete Slabs
    public static final Block WHITE_CONCRETE_SLAB = registerBlock("white_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WHITE_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LIGHT_GRAY_CONCRETE_SLAB = registerBlock("light_gray_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIGHT_GRAY_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GRAY_CONCRETE_SLAB = registerBlock("gray_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GRAY_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BLACK_CONCRETE_SLAB = registerBlock("black_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLACK_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BROWN_CONCRETE_SLAB = registerBlock("brown_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BROWN_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block RED_CONCRETE_SLAB = registerBlock("red_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.RED_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block ORANGE_CONCRETE_SLAB = registerBlock("orange_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.ORANGE_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block YELLOW_CONCRETE_SLAB = registerBlock("yellow_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.YELLOW_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LIME_CONCRETE_SLAB = registerBlock("lime_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIME_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GREEN_CONCRETE_SLAB = registerBlock("green_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GREEN_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block CYAN_CONCRETE_SLAB = registerBlock("cyan_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CYAN_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LIGHT_BLUE_CONCRETE_SLAB = registerBlock("light_blue_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIGHT_BLUE_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BLUE_CONCRETE_SLAB = registerBlock("blue_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLUE_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PURPLE_CONCRETE_SLAB = registerBlock("purple_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PURPLE_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MAGENTA_CONCRETE_SLAB = registerBlock("magenta_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MAGENTA_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PINK_CONCRETE_SLAB = registerBlock("pink_concrete_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PINK_CONCRETE.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));


    // Concrete Powder Slabs
    public static final Block WHITE_CONCRETE_POWDER_SLAB = registerBlock("white_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WHITE_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block LIGHT_GRAY_CONCRETE_POWDER_SLAB = registerBlock("light_gray_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIGHT_GRAY_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block GRAY_CONCRETE_POWDER_SLAB = registerBlock("gray_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GRAY_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block BLACK_CONCRETE_POWDER_SLAB = registerBlock("black_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLACK_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block BROWN_CONCRETE_POWDER_SLAB = registerBlock("brown_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BROWN_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block RED_CONCRETE_POWDER_SLAB = registerBlock("red_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.RED_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block ORANGE_CONCRETE_POWDER_SLAB = registerBlock("orange_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.ORANGE_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block YELLOW_CONCRETE_POWDER_SLAB = registerBlock("yellow_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.YELLOW_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block LIME_CONCRETE_POWDER_SLAB = registerBlock("lime_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIME_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block GREEN_CONCRETE_POWDER_SLAB = registerBlock("green_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GREEN_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block CYAN_CONCRETE_POWDER_SLAB = registerBlock("cyan_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CYAN_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block LIGHT_BLUE_CONCRETE_POWDER_SLAB = registerBlock("light_blue_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIGHT_BLUE_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block BLUE_CONCRETE_POWDER_SLAB = registerBlock("blue_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLUE_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block PURPLE_CONCRETE_POWDER_SLAB = registerBlock("purple_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PURPLE_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block MAGENTA_CONCRETE_POWDER_SLAB = registerBlock("magenta_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MAGENTA_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block PINK_CONCRETE_POWDER_SLAB = registerBlock("pink_concrete_powder_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PINK_CONCRETE_POWDER.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));


    // Glass Slabs
    public static final Block GLASS_SLAB = registerBlock("glass_slab",
            new TransparentSlabBlock(AbstractBlock.Settings.create()
                    .instrument(NoteBlockInstrument.HAT)
                    .strength(0.3F)
                    .sounds(BlockSoundGroup.GLASS)
                    .nonOpaque()
                    .allowsSpawning(Blocks::never)
                    .solidBlock(Blocks::never)
                    .suffocates(Blocks::never)
                    .blockVision(Blocks::never)));

    public static final Block TINTED_GLASS_SLAB = registerBlock("tinted_glass_slab",
            new TintedGlassSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.GRAY)
                    .instrument(NoteBlockInstrument.HAT)
                    .strength(0.3F)
                    .sounds(BlockSoundGroup.GLASS)
                    .nonOpaque()
                    .allowsSpawning(Blocks::never)
                    .solidBlock(Blocks::never)
                    .suffocates(Blocks::never)
                    .blockVision(Blocks::never)));

    public static final Block WHITE_STAINED_GLASS_SLAB = registerBlock("white_stained_glass_slab", createStainedGlassSlab(DyeColor.WHITE));
    public static final Block ORANGE_STAINED_GLASS_SLAB = registerBlock("orange_stained_glass_slab", createStainedGlassSlab(DyeColor.ORANGE));
    public static final Block MAGENTA_STAINED_GLASS_SLAB = registerBlock("magenta_stained_glass_slab", createStainedGlassSlab(DyeColor.MAGENTA));
    public static final Block LIGHT_BLUE_STAINED_GLASS_SLAB = registerBlock("light_blue_stained_glass_slab", createStainedGlassSlab(DyeColor.LIGHT_BLUE));
    public static final Block YELLOW_STAINED_GLASS_SLAB = registerBlock("yellow_stained_glass_slab", createStainedGlassSlab(DyeColor.YELLOW));
    public static final Block LIME_STAINED_GLASS_SLAB = registerBlock("lime_stained_glass_slab", createStainedGlassSlab(DyeColor.LIME));
    public static final Block PINK_STAINED_GLASS_SLAB = registerBlock("pink_stained_glass_slab", createStainedGlassSlab(DyeColor.PINK));
    public static final Block GRAY_STAINED_GLASS_SLAB = registerBlock("gray_stained_glass_slab", createStainedGlassSlab(DyeColor.GRAY));
    public static final Block LIGHT_GRAY_STAINED_GLASS_SLAB = registerBlock("light_gray_stained_glass_slab", createStainedGlassSlab(DyeColor.LIGHT_GRAY));
    public static final Block CYAN_STAINED_GLASS_SLAB = registerBlock("cyan_stained_glass_slab", createStainedGlassSlab(DyeColor.CYAN));
    public static final Block PURPLE_STAINED_GLASS_SLAB = registerBlock("purple_stained_glass_slab", createStainedGlassSlab(DyeColor.PURPLE));
    public static final Block BLUE_STAINED_GLASS_SLAB = registerBlock("blue_stained_glass_slab", createStainedGlassSlab(DyeColor.BLUE));
    public static final Block BROWN_STAINED_GLASS_SLAB = registerBlock("brown_stained_glass_slab", createStainedGlassSlab(DyeColor.BROWN));
    public static final Block GREEN_STAINED_GLASS_SLAB = registerBlock("green_stained_glass_slab", createStainedGlassSlab(DyeColor.GREEN));
    public static final Block RED_STAINED_GLASS_SLAB = registerBlock("red_stained_glass_slab", createStainedGlassSlab(DyeColor.RED));
    public static final Block BLACK_STAINED_GLASS_SLAB = registerBlock("black_stained_glass_slab", createStainedGlassSlab(DyeColor.BLACK));


    // Natural Slabs
    public static final Block GRASS_SLAB = registerBlock("grass_slab",
            new GrassSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GRASS_BLOCK.getDefaultMapColor())
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block PODZOL_SLAB = registerBlock("podzol_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PODZOL.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GRAVEL)));

    public static final Block MYCELIUM_SLAB = registerBlock("mycelium_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MYCELIUM.getDefaultMapColor())
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block DIRT_SLAB = registerBlock("dirt_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DIRT.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GRAVEL)));

    public static final Block DIRT_PATH_SLAB = registerBlock("dirt_path_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DIRT_PATH.getDefaultMapColor())
                    .strength(0.65F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block COARSE_DIRT_SLAB = registerBlock("coarse_dirt_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.COARSE_DIRT.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GRAVEL)));

    public static final Block ROOTED_DIRT_SLAB = registerBlock("rooted_dirt_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.ROOTED_DIRT.getDefaultMapColor())
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.ROOTED_DIRT)));

    public static final Block MUD_SLAB = registerBlock("mud_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MUD.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.MUD)));

    public static final Block CLAY_SLAB = registerBlock("clay_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CLAY.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.FLUTE)
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.GRAVEL)));

    public static final Block GRAVEL_SLAB = registerBlock("gravel_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GRAVEL.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GRAVEL)));

    public static final Block SAND_SLAB = registerBlock("sand_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SAND.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block RED_SAND_SLAB = registerBlock("red_sand_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.RED_SAND.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block ICE_SLAB = registerBlock("ice_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.ICE.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GLASS)));

    public static final Block PACKED_ICE_SLAB = registerBlock("packed_ice_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PACKED_ICE.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GLASS)));

    public static final Block BLUE_ICE_SLAB = registerBlock("blue_ice_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLUE_ICE.getDefaultMapColor())
                    .strength(2.8F)
                    .sounds(BlockSoundGroup.GLASS)));

    public static final Block MOSS_SLAB = registerBlock("moss_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MOSS_BLOCK.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.MOSS_BLOCK)));

    public static final Block CALCITE_SLAB = registerBlock("calcite_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CALCITE.getDefaultMapColor())
                    .strength(0.75F)
                    .sounds(BlockSoundGroup.CALCITE)));

    public static final Block DRIPSTONE_SLAB = registerBlock("dripstone_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DRIPSTONE_BLOCK.getDefaultMapColor())
                    .strength(1.5F)
                    .sounds(BlockSoundGroup.DRIPSTONE_BLOCK)));

    public static final Block MAGMA_BLOCK_SLAB = registerBlock("magma_block_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_RED)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool()
                    .luminance((state) -> 3)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.STONE)
                    .postProcess(Blocks::always)
                    .emissiveLighting(Blocks::always)));

    public static final Block OBSIDIAN_SLAB = registerBlock("obsidian_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.OBSIDIAN.getDefaultMapColor())
                    .strength(50.0F, 1200.0F)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block CRYING_OBSIDIAN_SLAB = registerBlock("crying_obsidian_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CRYING_OBSIDIAN.getDefaultMapColor())
                    .strength(50.0F, 1200.0F)
                    .sounds(BlockSoundGroup.STONE)
                    .luminance((state) -> 10)));

    public static final Block CRIMSON_NYLIUM_SLAB = registerBlock("crimson_nylium_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CRIMSON_NYLIUM.getDefaultMapColor())
                    .strength(0.4F)
                    .sounds(BlockSoundGroup.NYLIUM)));

    public static final Block WARPED_NYLIUM_SLAB = registerBlock("warped_nylium_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WARPED_NYLIUM.getDefaultMapColor())
                    .strength(0.4F)
                    .sounds(BlockSoundGroup.NYLIUM)));

    public static final Block SOUL_SAND_SLAB = registerBlock("soul_sand_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SOUL_SAND.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.COW_BELL)
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SOUL_SAND)));

    public static final Block SOUL_SOIL_SLAB = registerBlock("soul_soil_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SOUL_SOIL.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SOUL_SOIL)));

    public static final Block BONE_SLAB = registerBlock("bone_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BONE_BLOCK.getDefaultMapColor())
                    .instrument(NoteBlockInstrument.XYLOPHONE)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.BONE)));

    public static final Block GLOWSTONE_SLAB = registerBlock("glowstone_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_YELLOW)
                    .instrument(NoteBlockInstrument.PLING)
                    .strength(0.3F)
                    .sounds(BlockSoundGroup.GLASS)
                    .luminance((state) -> 15)
                    .solidBlock(Blocks::never)));

    public static final Block MANGROVE_ROOT_SLAB = registerBlock("mangrove_root_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MANGROVE_ROOTS.getDefaultMapColor())
                    .strength(0.7F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.ROOTS)));

    public static final Block MUDDY_MANGROVE_ROOT_SLAB = registerBlock("muddy_mangrove_root_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MUDDY_MANGROVE_ROOTS.getDefaultMapColor())
                    .strength(0.7F)
                    .sounds(BlockSoundGroup.ROOTS)));

    public static final Block MUSHROOM_STEM_SLAB = registerBlock("mushroom_stem_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MUSHROOM_STEM.getDefaultMapColor())
                    .strength(0.2F)
                    .sounds(BlockSoundGroup.WOOD)));

    public static final Block OAK_LEAF_SLAB = registerBlock("oak_leaf_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block SPRUCE_LEAF_SLAB = registerBlock("spruce_leaf_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block BIRCH_LEAF_SLAB = registerBlock("birch_leaf_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block JUNGLE_LEAF_SLAB = registerBlock("jungle_leaf_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block ACACIA_LEAF_SLAB = registerBlock("acacia_leaf_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block DARK_OAK_LEAF_SLAB = registerBlock("dark_oak_leaf_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block MANGROVE_LEAF_SLAB = registerBlock("mangrove_leaf_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block CHERRY_LEAF_SLAB = registerBlock("cherry_leaf_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.PINK)
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.CHERRY_LEAVES)
                    .instrument(NoteBlockInstrument.FLUTE)));

    public static final Block AZALEA_LEAF_SLAB = registerBlock("azalea_leaf_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.AZALEA_LEAVES)
                    .instrument(NoteBlockInstrument.FLUTE)));

    public static final Block FLOWERING_AZALEA_LEAF_SLAB = registerBlock("flowering_azalea_leaf_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.AZALEA_LEAVES)
                    .instrument(NoteBlockInstrument.FLUTE)));

    public static final Block BROWN_MUSHROOM_SLAB = registerBlock("brown_mushroom_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BROWN_MUSHROOM_BLOCK.getDefaultMapColor())
                    .strength(0.2F)
                    .sounds(BlockSoundGroup.WOOD)));

    public static final Block RED_MUSHROOM_SLAB = registerBlock("red_mushroom_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.RED_MUSHROOM_BLOCK.getDefaultMapColor())
                    .strength(0.2F)
                    .sounds(BlockSoundGroup.WOOD)));

    public static final Block NETHER_WART_SLAB = registerBlock("nether_wart_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.NETHER_WART_BLOCK.getDefaultMapColor())
                    .strength(1.0F)
                    .sounds(BlockSoundGroup.WART_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block WARPED_WART_SLAB = registerBlock("warped_wart_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WARPED_WART_BLOCK.getDefaultMapColor())
                    .strength(1.0F)
                    .sounds(BlockSoundGroup.WART_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block DRIED_KELP_SLAB = registerBlock("dried_kelp_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DRIED_KELP_BLOCK.getDefaultMapColor())
                    .strength(0.5F, 2.5F)
                    .sounds(BlockSoundGroup.GRASS)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block TUBE_CORAL_SLAB = registerBlock("tube_coral_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.TUBE_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block BRAIN_CORAL_SLAB = registerBlock("brain_coral_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BRAIN_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block BUBBLE_CORAL_SLAB = registerBlock("bubble_coral_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BUBBLE_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block FIRE_CORAL_SLAB = registerBlock("fire_coral_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.FIRE_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block HORN_CORAL_SLAB = registerBlock("horn_coral_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.HORN_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block DEAD_TUBE_CORAL_SLAB = registerBlock("dead_tube_coral_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DEAD_TUBE_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)));

    public static final Block DEAD_BRAIN_CORAL_SLAB = registerBlock("dead_brain_coral_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DEAD_BRAIN_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)));

    public static final Block DEAD_BUBBLE_CORAL_SLAB = registerBlock("dead_bubble_coral_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DEAD_BUBBLE_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)));

    public static final Block DEAD_FIRE_CORAL_SLAB = registerBlock("dead_fire_coral_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DEAD_FIRE_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)));

    public static final Block DEAD_HORN_CORAL_SLAB = registerBlock("dead_horn_coral_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)));

    public static final Block SPONGE_SLAB = registerBlock("sponge_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SPONGE.getDefaultMapColor())
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.SPONGE)));

    public static final Block WET_SPONGE_SLAB = registerBlock("wet_sponge_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WET_SPONGE.getDefaultMapColor())
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.WET_SPONGE)));

    public static final Block MELON_SLAB = registerBlock("melon_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MELON.getDefaultMapColor())
                    .strength(1.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block PUMPKIN_SLAB = registerBlock("pumpkin_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PUMPKIN.getDefaultMapColor())
                    .strength(1.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .instrument(NoteBlockInstrument.BASS)));

    /*public static final Block CARVED_PUMPKIN_SLAB = registerBlock("carved_pumpkin_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CARVED_PUMPKIN.getDefaultMapColor())
                    .strength(1.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .instrument(NoteBlockInstrument.BASS)));*/

    public static final Block HAY_SLAB = registerBlock("hay_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.HAY_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GRASS)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block HONEYCOMB_SLAB = registerBlock("honeycomb_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.HONEYCOMB_BLOCK.getDefaultMapColor())
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.CORAL)));

    public static final Block SCULK_SLAB = registerBlock("sculk_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SCULK.getDefaultMapColor())
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.SCULK)
                    .instrument(NoteBlockInstrument.BASS)));


    ///////////////////////
    //   Vertical Slab   //
    ///////////////////////

    // Planks Vertical Slabs
    public static final Block OAK_VERTICAL_SLAB = registerBlock("oak_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.OAK_PLANKS.getDefaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.WOOD)));

    public static final Block SPRUCE_VERTICAL_SLAB = registerBlock("spruce_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SPRUCE_PLANKS.getDefaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.WOOD)));

    public static final Block BIRCH_VERTICAL_SLAB = registerBlock("birch_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BIRCH_PLANKS.getDefaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.WOOD)));

    public static final Block JUNGLE_VERTICAL_SLAB = registerBlock("jungle_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.JUNGLE_PLANKS.getDefaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.WOOD)));

    public static final Block ACACIA_VERTICAL_SLAB = registerBlock("acacia_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.ACACIA_PLANKS.getDefaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.WOOD)));

    public static final Block DARK_OAK_VERTICAL_SLAB = registerBlock("dark_oak_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DARK_OAK_PLANKS.getDefaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.WOOD)));

    public static final Block MANGROVE_VERTICAL_SLAB = registerBlock("mangrove_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MANGROVE_PLANKS.getDefaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.WOOD)));

    public static final Block CHERRY_VERTICAL_SLAB = registerBlock("cherry_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CHERRY_PLANKS.getDefaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.CHERRY_WOOD)));

    public static final Block BAMBOO_VERTICAL_SLAB = registerBlock("bamboo_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BAMBOO_PLANKS.getDefaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.BAMBOO_WOOD)));

    public static final Block BAMBOO_MOSAIC_VERTICAL_SLAB = registerBlock("bamboo_mosaic_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BAMBOO_MOSAIC.getDefaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.BAMBOO_WOOD)));

    public static final Block CRIMSON_VERTICAL_SLAB = registerBlock("crimson_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CRIMSON_PLANKS.getDefaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.NETHER_WOOD)));

    public static final Block WARPED_VERTICAL_SLAB = registerBlock("warped_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WARPED_PLANKS.getDefaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.NETHER_WOOD)));


    // Log Slabs
    public static final Block OAK_LOG_VERTICAL_SLAB = registerBlock("oak_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block SPRUCE_LOG_VERTICAL_SLAB = registerBlock("spruce_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.SPRUCE_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block BIRCH_LOG_VERTICAL_SLAB = registerBlock("birch_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_YELLOW)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block JUNGLE_LOG_VERTICAL_SLAB = registerBlock("jungle_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DIRT_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block ACACIA_LOG_VERTICAL_SLAB = registerBlock("acacia_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.ORANGE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block DARK_OAK_LOG_VERTICAL_SLAB = registerBlock("dark_oak_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block MANGROVE_LOG_VERTICAL_SLAB = registerBlock("mangrove_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block CHERRY_LOG_VERTICAL_SLAB = registerBlock("cherry_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.CHERRY_WOOD)
                    .burnable()));

    public static final Block BAMBOO_BLOCK_VERTICAL_SLAB = registerBlock("bamboo_block_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.YELLOW)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.BAMBOO_WOOD)
                    .burnable()));

    public static final Block CRIMSON_STEM_VERTICAL_SLAB = registerBlock("crimson_stem_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DULL_PINK)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block WARPED_STEM_VERTICAL_SLAB = registerBlock("warped_stem_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_AQUA)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));


    // Wood Slabs
    public static final Block OAK_WOOD_VERTICAL_SLAB = registerBlock("oak_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block SPRUCE_WOOD_VERTICAL_SLAB = registerBlock("spruce_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.SPRUCE_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block BIRCH_WOOD_VERTICAL_SLAB = registerBlock("birch_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_YELLOW)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block JUNGLE_WOOD_VERTICAL_SLAB = registerBlock("jungle_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DIRT_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block ACACIA_WOOD_VERTICAL_SLAB = registerBlock("acacia_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.GRAY)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block DARK_OAK_WOOD_VERTICAL_SLAB = registerBlock("dark_oak_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block MANGROVE_WOOD_VERTICAL_SLAB = registerBlock("mangrove_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block CHERRY_WOOD_VERTICAL_SLAB = registerBlock("cherry_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.TERRACOTTA_GRAY)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.CHERRY_WOOD)
                    .burnable()));

    public static final Block CRIMSON_HYPHAE_VERTICAL_SLAB = registerBlock("crimson_hyphae_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_CRIMSON)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block WARPED_HYPHAE_VERTICAL_SLAB = registerBlock("warped_hyphae_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_DULL_PINK)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));


    // Stripped Log Slabs
    public static final Block STRIPPED_OAK_LOG_VERTICAL_SLAB = registerBlock("stripped_oak_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_SPRUCE_LOG_VERTICAL_SLAB = registerBlock("stripped_spruce_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.SPRUCE_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_BIRCH_LOG_VERTICAL_SLAB = registerBlock("stripped_birch_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_YELLOW)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_JUNGLE_LOG_VERTICAL_SLAB = registerBlock("stripped_jungle_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DIRT_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_ACACIA_LOG_VERTICAL_SLAB = registerBlock("stripped_acacia_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.ORANGE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_DARK_OAK_LOG_VERTICAL_SLAB = registerBlock("stripped_dark_oak_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_MANGROVE_LOG_VERTICAL_SLAB = registerBlock("stripped_mangrove_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_CHERRY_LOG_VERTICAL_SLAB = registerBlock("stripped_cherry_log_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.CHERRY_WOOD)
                    .burnable()));

    public static final Block STRIPPED_BAMBOO_VERTICAL_SLAB = registerBlock("stripped_bamboo_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.YELLOW)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.BAMBOO_WOOD)
                    .burnable()));

    public static final Block STRIPPED_CRIMSON_STEM_VERTICAL_SLAB = registerBlock("stripped_crimson_stem_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DULL_PINK)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block STRIPPED_WARPED_STEM_VERTICAL_SLAB = registerBlock("stripped_warped_stem_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_AQUA)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));


    // Stripped Wood Slabs
    public static final Block STRIPPED_OAK_WOOD_VERTICAL_SLAB = registerBlock("stripped_oak_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_SPRUCE_WOOD_VERTICAL_SLAB = registerBlock("stripped_spruce_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.SPRUCE_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_BIRCH_WOOD_VERTICAL_SLAB = registerBlock("stripped_birch_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_YELLOW)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_JUNGLE_WOOD_VERTICAL_SLAB = registerBlock("stripped_jungle_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DIRT_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_ACACIA_WOOD_VERTICAL_SLAB = registerBlock("stripped_acacia_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.ORANGE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_DARK_OAK_WOOD_VERTICAL_SLAB = registerBlock("stripped_dark_oak_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_MANGROVE_WOOD_VERTICAL_SLAB = registerBlock("stripped_mangrove_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));

    public static final Block STRIPPED_CHERRY_WOOD_VERTICAL_SLAB = registerBlock("stripped_cherry_wood_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.TERRACOTTA_PINK)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.CHERRY_WOOD)
                    .burnable()));

    public static final Block STRIPPED_CRIMSON_HYPHAE_VERTICAL_SLAB = registerBlock("stripped_crimson_hyphae_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_CRIMSON)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));

    public static final Block STRIPPED_WARPED_HYPHAE_VERTICAL_SLAB = registerBlock("stripped_warped_hyphae_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_DULL_PINK)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.NETHER_STEM)));


    // Stone-related Vertical Slabs
    public static final Block STONE_VERTICAL_SLAB = registerBlock("stone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.STONE.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block COBBLESTONE_VERTICAL_SLAB = registerBlock("cobblestone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.COBBLESTONE.getDefaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MOSSY_COBBLESTONE_VERTICAL_SLAB = registerBlock("mossy_cobblestone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MOSSY_COBBLESTONE.getDefaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block SMOOTH_STONE_VERTICAL_SLAB = registerBlock("smooth_stone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SMOOTH_STONE.getDefaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block STONE_BRICK_VERTICAL_SLAB = registerBlock("stone_brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.STONE_BRICKS.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block CRACKED_STONE_BRICK_VERTICAL_SLAB = registerBlock("cracked_stone_brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CRACKED_STONE_BRICKS.getDefaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MOSSY_STONE_BRICK_VERTICAL_SLAB = registerBlock("mossy_stone_brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MOSSY_STONE_BRICKS.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GRANITE_VERTICAL_SLAB = registerBlock("granite_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GRANITE.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block POLISHED_GRANITE_VERTICAL_SLAB = registerBlock("polished_granite_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.POLISHED_GRANITE.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block DIORITE_VERTICAL_SLAB = registerBlock("diorite_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DIORITE.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block POLISHED_DIORITE_VERTICAL_SLAB = registerBlock("polished_diorite_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.POLISHED_DIORITE.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block ANDESITE_VERTICAL_SLAB = registerBlock("andesite_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.ANDESITE.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block POLISHED_ANDESITE_VERTICAL_SLAB = registerBlock("polished_andesite_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.POLISHED_ANDESITE.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block DEEPSLATE_VERTICAL_SLAB = registerBlock("deepslate_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DEEPSLATE.getDefaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)));

    public static final Block COBBLED_DEEPSLATE_VERTICAL_SLAB = registerBlock("cobbled_deepslate_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.COBBLED_DEEPSLATE.getDefaultMapColor())
                    .strength(3.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)));

    public static final Block POLISHED_DEEPSLATE_VERTICAL_SLAB = registerBlock("polished_deepslate_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.POLISHED_DEEPSLATE.getDefaultMapColor())
                    .strength(3.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)));

    public static final Block DEEPSLATE_BRICK_VERTICAL_SLAB = registerBlock("deepslate_brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DEEPSLATE_BRICKS.getDefaultMapColor())
                    .strength(3.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)));

    public static final Block CRACKED_DEEPSLATE_BRICK_VERTICAL_SLAB = registerBlock("cracked_deepslate_brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE_BRICKS)));

    public static final Block DEEPSLATE_TILE_VERTICAL_SLAB = registerBlock("deepslate_tile_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DEEPSLATE_TILES.getDefaultMapColor())
                    .strength(3.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)));

    public static final Block REINFORCED_DEEPSLATE_VERTICAL_SLAB = registerBlock("reinforced_deepslate_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.REINFORCED_DEEPSLATE.getDefaultMapColor())
                    .strength(55.0F, 1200.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)));

    public static final Block TUFF_VERTICAL_SLAB = registerBlock("tuff_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.TUFF.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.TUFF)
                    .instrument(NoteBlockInstrument.BASEDRUM)));

    public static final Block POLISHED_TUFF_VERTICAL_SLAB = registerBlock("polished_tuff_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.POLISHED_TUFF.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.POLISHED_TUFF)
                    .instrument(NoteBlockInstrument.BASEDRUM)));

    public static final Block TUFF_BRICK_VERTICAL_SLAB = registerBlock("tuff_brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.TUFF_BRICKS.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.TUFF_BRICKS)
                    .instrument(NoteBlockInstrument.BASEDRUM)));

    public static final Block BRICK_VERTICAL_SLAB = registerBlock("brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BRICKS.getDefaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PACKED_MUD_VERTICAL_SLAB = registerBlock("packed_mud_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PACKED_MUD.getDefaultMapColor())
                    .strength(1.5F)
                    .sounds(BlockSoundGroup.PACKED_MUD)));

    public static final Block MUD_BRICK_VERTICAL_SLAB = registerBlock("mud_brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MUD_BRICKS.getDefaultMapColor())
                    .strength(1.5F, 3.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.MUD_BRICKS)));

    public static final Block SANDSTONE_VERTICAL_SLAB = registerBlock("sandstone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SANDSTONE.getDefaultMapColor())
                    .strength(0.8F, 0.8F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block SMOOTH_SANDSTONE_VERTICAL_SLAB = registerBlock("smooth_sandstone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SMOOTH_SANDSTONE.getDefaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block CUT_SANDSTONE_VERTICAL_SLAB = registerBlock("cut_sandstone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CUT_SANDSTONE.getDefaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block RED_SANDSTONE_VERTICAL_SLAB = registerBlock("red_sandstone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.RED_SANDSTONE.getDefaultMapColor())
                    .strength(0.8F, 0.8F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block SMOOTH_RED_SANDSTONE_VERTICAL_SLAB = registerBlock("smooth_red_sandstone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SMOOTH_RED_SANDSTONE.getDefaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block CUT_RED_SANDSTONE_VERTICAL_SLAB = registerBlock("cut_red_sandstone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CUT_RED_SANDSTONE.getDefaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PRISMARINE_VERTICAL_SLAB = registerBlock("prismarine_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PRISMARINE.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PRISMARINE_BRICK_VERTICAL_SLAB = registerBlock("prismarine_brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PRISMARINE_BRICKS.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block DARK_PRISMARINE_VERTICAL_SLAB = registerBlock("dark_prismarine_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DARK_PRISMARINE.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block NETHERRACK_VERTICAL_SLAB = registerBlock("netherrack_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.NETHERRACK.getDefaultMapColor())
                    .strength(0.4F)
                    .sounds(BlockSoundGroup.NETHERRACK)));

    public static final Block NETHER_BRICK_VERTICAL_SLAB = registerBlock("nether_brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.NETHER_BRICKS.getDefaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.NETHER_BRICKS)));

    public static final Block CRACKED_NETHER_BRICK_VERTICAL_SLAB = registerBlock("cracked_nether_brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CRACKED_NETHER_BRICKS.getDefaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.NETHER_BRICKS)));

    public static final Block RED_NETHER_BRICK_VERTICAL_SLAB = registerBlock("red_nether_brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.RED_NETHER_BRICKS.getDefaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.NETHER_BRICKS)));

    public static final Block BASALT_VERTICAL_SLAB = registerBlock("basalt_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BASALT.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.BASALT)));

    public static final Block SMOOTH_BASALT_VERTICAL_SLAB = registerBlock("smooth_basalt_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SMOOTH_BASALT.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.BASALT)));

    public static final Block POLISHED_BASALT_VERTICAL_SLAB = registerBlock("polished_basalt_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.POLISHED_BASALT.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.BASALT)));

    public static final Block BLACKSTONE_VERTICAL_SLAB = registerBlock("blackstone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLACKSTONE.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GILDED_BLACKSTONE_VERTICAL_SLAB = registerBlock("gilded_blackstone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GILDED_BLACKSTONE.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.GILDED_BLACKSTONE)));

    public static final Block POLISHED_BLACKSTONE_VERTICAL_SLAB = registerBlock("polished_blackstone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.POLISHED_BLACKSTONE.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB = registerBlock("polished_blackstone_brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.POLISHED_BLACKSTONE_BRICKS.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block CRACKED_POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB = registerBlock("cracked_polished_blackstone_brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block END_STONE_VERTICAL_SLAB = registerBlock("end_stone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.END_STONE.getDefaultMapColor())
                    .strength(3.0F, 9.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block END_STONE_BRICK_VERTICAL_SLAB = registerBlock("end_stone_brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.END_STONE_BRICKS.getDefaultMapColor())
                    .strength(3.0F, 9.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PURPUR_VERTICAL_SLAB = registerBlock("purpur_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PURPUR_BLOCK.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PURPUR_PILLAR_VERTICAL_SLAB = registerBlock("purpur_pillar_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PURPUR_PILLAR.getDefaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block QUARTZ_VERTICAL_SLAB = registerBlock("quartz_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.QUARTZ_BLOCK.getDefaultMapColor())
                    .strength(0.8F, 0.8F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block QUARTZ_BRICK_VERTICAL_SLAB = registerBlock("quartz_brick_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.QUARTZ_BRICKS.getDefaultMapColor())
                    .strength(0.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block QUARTZ_PILLAR_VERTICAL_SLAB = registerBlock("quartz_pillar_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.QUARTZ_PILLAR.getDefaultMapColor())
                    .strength(0.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block SMOOTH_QUARTZ_VERTICAL_SLAB = registerBlock("smooth_quartz_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SMOOTH_QUARTZ.getDefaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block AMETHYST_VERTICAL_SLAB = registerBlock("amethyst_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.AMETHYST_BLOCK.getDefaultMapColor())
                    .strength(1.5F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK)));

    public static final Block CUT_COPPER_VERTICAL_SLAB = registerBlock("cut_copper_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CUT_COPPER.getDefaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.COPPER)));

    public static final Block EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerBlock("exposed_cut_copper_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.EXPOSED_CUT_COPPER.getDefaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.COPPER)));

    public static final Block WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerBlock("weathered_cut_copper_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WEATHERED_CUT_COPPER.getDefaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.COPPER)));

    public static final Block OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerBlock("oxidized_cut_copper_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.OXIDIZED_CUT_COPPER.getDefaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.COPPER)));

    public static final Block WAXED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_cut_copper_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WAXED_CUT_COPPER.getDefaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.COPPER)));

    public static final Block WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_exposed_cut_copper_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WAXED_EXPOSED_CUT_COPPER.getDefaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.COPPER)));

    public static final Block WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_weathered_cut_copper_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WAXED_WEATHERED_CUT_COPPER.getDefaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.COPPER)));

    public static final Block WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_oxidized_cut_copper_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WAXED_OXIDIZED_CUT_COPPER.getDefaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.COPPER)));

    public static final Block PETRIFIED_OAK_VERTICAL_SLAB = registerBlock("petrified_oak_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PETRIFIED_OAK_SLAB.getDefaultMapColor())
                    .strength(2.0F, 3.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.WOOD)));


    // Wool Vertical Slabs
    public static final Block WHITE_WOOL_VERTICAL_SLAB = registerBlock("white_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.WHITE)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));

    public static final Block LIGHT_GRAY_WOOL_VERTICAL_SLAB = registerBlock("light_gray_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.LIGHT_GRAY)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));

    public static final Block GRAY_WOOL_VERTICAL_SLAB = registerBlock("gray_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.GRAY)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));

    public static final Block BLACK_WOOL_VERTICAL_SLAB = registerBlock("black_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.BLACK)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));

    public static final Block BROWN_WOOL_VERTICAL_SLAB = registerBlock("brown_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.BROWN)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));

    public static final Block RED_WOOL_VERTICAL_SLAB = registerBlock("red_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.RED)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));

    public static final Block ORANGE_WOOL_VERTICAL_SLAB = registerBlock("orange_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.ORANGE)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));

    public static final Block YELLOW_WOOL_VERTICAL_SLAB = registerBlock("yellow_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.YELLOW)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));

    public static final Block LIME_WOOL_VERTICAL_SLAB = registerBlock("lime_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.LIME)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));

    public static final Block GREEN_WOOL_VERTICAL_SLAB = registerBlock("green_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.GREEN)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));

    public static final Block CYAN_WOOL_VERTICAL_SLAB = registerBlock("cyan_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.CYAN)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));

    public static final Block LIGHT_BLUE_WOOL_VERTICAL_SLAB = registerBlock("light_blue_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.LIGHT_BLUE)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));

    public static final Block BLUE_WOOL_VERTICAL_SLAB = registerBlock("blue_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.BLUE)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));

    public static final Block PURPLE_WOOL_VERTICAL_SLAB = registerBlock("purple_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.PURPLE)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));

    public static final Block MAGENTA_WOOL_VERTICAL_SLAB = registerBlock("magenta_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.MAGENTA)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));

    public static final Block PINK_WOOL_VERTICAL_SLAB = registerBlock("pink_wool_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.PINK)
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .burnable()));


    // Terracotta Vertical Slabs
    public static final Block TERRACOTTA_VERTICAL_SLAB = registerBlock("terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block WHITE_TERRACOTTA_VERTICAL_SLAB = registerBlock("white_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WHITE_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LIGHT_GRAY_TERRACOTTA_VERTICAL_SLAB = registerBlock("light_gray_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GRAY_TERRACOTTA_VERTICAL_SLAB = registerBlock("gray_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GRAY_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BLACK_TERRACOTTA_VERTICAL_SLAB = registerBlock("black_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLACK_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BROWN_TERRACOTTA_VERTICAL_SLAB = registerBlock("brown_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BROWN_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block RED_TERRACOTTA_VERTICAL_SLAB = registerBlock("red_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.RED_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block ORANGE_TERRACOTTA_VERTICAL_SLAB = registerBlock("orange_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.ORANGE_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block YELLOW_TERRACOTTA_VERTICAL_SLAB = registerBlock("yellow_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.YELLOW_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LIME_TERRACOTTA_VERTICAL_SLAB = registerBlock("lime_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIME_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GREEN_TERRACOTTA_VERTICAL_SLAB = registerBlock("green_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GREEN_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block CYAN_TERRACOTTA_VERTICAL_SLAB = registerBlock("cyan_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CYAN_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LIGHT_BLUE_TERRACOTTA_VERTICAL_SLAB = registerBlock("light_blue_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIGHT_BLUE_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BLUE_TERRACOTTA_VERTICAL_SLAB = registerBlock("blue_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLUE_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PURPLE_TERRACOTTA_VERTICAL_SLAB = registerBlock("purple_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PURPLE_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MAGENTA_TERRACOTTA_VERTICAL_SLAB = registerBlock("magenta_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MAGENTA_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PINK_TERRACOTTA_VERTICAL_SLAB = registerBlock("pink_terracotta_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PINK_TERRACOTTA.getDefaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresTool()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sounds(BlockSoundGroup.STONE)));


    // Concrete Vertical Slabs
    public static final Block WHITE_CONCRETE_VERTICAL_SLAB = registerBlock("white_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WHITE_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LIGHT_GRAY_CONCRETE_VERTICAL_SLAB = registerBlock("light_gray_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIGHT_GRAY_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GRAY_CONCRETE_VERTICAL_SLAB = registerBlock("gray_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GRAY_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BLACK_CONCRETE_VERTICAL_SLAB = registerBlock("black_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLACK_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BROWN_CONCRETE_VERTICAL_SLAB = registerBlock("brown_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BROWN_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block RED_CONCRETE_VERTICAL_SLAB = registerBlock("red_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.RED_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block ORANGE_CONCRETE_VERTICAL_SLAB = registerBlock("orange_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.ORANGE_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block YELLOW_CONCRETE_VERTICAL_SLAB = registerBlock("yellow_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.YELLOW_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LIME_CONCRETE_VERTICAL_SLAB = registerBlock("lime_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIME_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GREEN_CONCRETE_VERTICAL_SLAB = registerBlock("green_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GREEN_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block CYAN_CONCRETE_VERTICAL_SLAB = registerBlock("cyan_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CYAN_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LIGHT_BLUE_CONCRETE_VERTICAL_SLAB = registerBlock("light_blue_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIGHT_BLUE_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BLUE_CONCRETE_VERTICAL_SLAB = registerBlock("blue_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLUE_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PURPLE_CONCRETE_VERTICAL_SLAB = registerBlock("purple_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PURPLE_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MAGENTA_CONCRETE_VERTICAL_SLAB = registerBlock("magenta_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MAGENTA_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PINK_CONCRETE_VERTICAL_SLAB = registerBlock("pink_concrete_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PINK_CONCRETE.getDefaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));


    // Concrete Powder Vertical Slabs
    public static final Block WHITE_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("white_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WHITE_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block LIGHT_GRAY_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("light_gray_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIGHT_GRAY_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block GRAY_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("gray_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GRAY_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block BLACK_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("black_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLACK_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block BROWN_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("brown_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BROWN_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block RED_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("red_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.RED_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block ORANGE_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("orange_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.ORANGE_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block YELLOW_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("yellow_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.YELLOW_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block LIME_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("lime_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIME_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block GREEN_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("green_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GREEN_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block CYAN_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("cyan_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CYAN_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block LIGHT_BLUE_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("light_blue_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.LIGHT_BLUE_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block BLUE_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("blue_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLUE_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block PURPLE_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("purple_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PURPLE_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block MAGENTA_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("magenta_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MAGENTA_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));

    public static final Block PINK_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("pink_concrete_powder_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PINK_CONCRETE_POWDER.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)));


    // Glass Vertical Slabs
    public static final Block GLASS_VERTICAL_SLAB = registerBlock("glass_vertical_slab",
            new TransparentVerticalSlabBlock(AbstractBlock.Settings.create()
                    .instrument(NoteBlockInstrument.HAT)
                    .strength(0.3F)
                    .sounds(BlockSoundGroup.GLASS)
                    .nonOpaque()
                    .allowsSpawning(Blocks::never)
                    .solidBlock(Blocks::never)
                    .suffocates(Blocks::never)
                    .blockVision(Blocks::never)));

    public static final Block TINTED_GLASS_VERTICAL_SLAB = registerBlock("tinted_glass_vertical_slab",
            new TintedGlassVerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.GRAY)
                    .instrument(NoteBlockInstrument.HAT)
                    .strength(0.3F)
                    .sounds(BlockSoundGroup.GLASS)
                    .nonOpaque()
                    .allowsSpawning(Blocks::never)
                    .solidBlock(Blocks::never)
                    .suffocates(Blocks::never)
                    .blockVision(Blocks::never)));

    public static final Block WHITE_STAINED_GLASS_VERTICAL_SLAB = registerBlock("white_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.WHITE));
    public static final Block ORANGE_STAINED_GLASS_VERTICAL_SLAB = registerBlock("orange_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.ORANGE));
    public static final Block MAGENTA_STAINED_GLASS_VERTICAL_SLAB = registerBlock("magenta_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.MAGENTA));
    public static final Block LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB = registerBlock("light_blue_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.LIGHT_BLUE));
    public static final Block YELLOW_STAINED_GLASS_VERTICAL_SLAB = registerBlock("yellow_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.YELLOW));
    public static final Block LIME_STAINED_GLASS_VERTICAL_SLAB = registerBlock("lime_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.LIME));
    public static final Block PINK_STAINED_GLASS_VERTICAL_SLAB = registerBlock("pink_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.PINK));
    public static final Block GRAY_STAINED_GLASS_VERTICAL_SLAB = registerBlock("gray_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.GRAY));
    public static final Block LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB = registerBlock("light_gray_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.LIGHT_GRAY));
    public static final Block CYAN_STAINED_GLASS_VERTICAL_SLAB = registerBlock("cyan_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.CYAN));
    public static final Block PURPLE_STAINED_GLASS_VERTICAL_SLAB = registerBlock("purple_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.PURPLE));
    public static final Block BLUE_STAINED_GLASS_VERTICAL_SLAB = registerBlock("blue_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.BLUE));
    public static final Block BROWN_STAINED_GLASS_VERTICAL_SLAB = registerBlock("brown_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.BROWN));
    public static final Block GREEN_STAINED_GLASS_VERTICAL_SLAB = registerBlock("green_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.GREEN));
    public static final Block RED_STAINED_GLASS_VERTICAL_SLAB = registerBlock("red_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.RED));
    public static final Block BLACK_STAINED_GLASS_VERTICAL_SLAB = registerBlock("black_stained_glass_vertical_slab", createStainedGlassVerticalSlab(DyeColor.BLACK));


    // Natural Vertical Slabs
    public static final Block GRASS_VERTICAL_SLAB = registerBlock("grass_vertical_slab",
            new GrassVerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GRASS_BLOCK.getDefaultMapColor())
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block PODZOL_VERTICAL_SLAB = registerBlock("podzol_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PODZOL.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GRAVEL)));

    public static final Block MYCELIUM_VERTICAL_SLAB = registerBlock("mycelium_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MYCELIUM.getDefaultMapColor())
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block DIRT_VERTICAL_SLAB = registerBlock("dirt_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DIRT.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GRAVEL)));

    public static final Block DIRT_PATH_VERTICAL_SLAB = registerBlock("dirt_path_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DIRT_PATH.getDefaultMapColor())
                    .strength(0.65F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block COARSE_DIRT_VERTICAL_SLAB = registerBlock("coarse_dirt_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.COARSE_DIRT.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GRAVEL)));

    public static final Block ROOTED_DIRT_VERTICAL_SLAB = registerBlock("rooted_dirt_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.ROOTED_DIRT.getDefaultMapColor())
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.ROOTED_DIRT)));

    public static final Block MUD_VERTICAL_SLAB = registerBlock("mud_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MUD.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.MUD)));

    public static final Block CLAY_VERTICAL_SLAB = registerBlock("clay_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CLAY.getDefaultMapColor())
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.GRAVEL)
                    .instrument(NoteBlockInstrument.FLUTE)));

    public static final Block GRAVEL_VERTICAL_SLAB = registerBlock("gravel_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.GRAVEL.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GRAVEL)));

    public static final Block SAND_VERTICAL_SLAB = registerBlock("sand_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SAND.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)
                    .instrument(NoteBlockInstrument.SNARE)));

    public static final Block RED_SAND_VERTICAL_SLAB = registerBlock("red_sand_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.RED_SAND.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SAND)
                    .instrument(NoteBlockInstrument.SNARE)));

    public static final Block ICE_VERTICAL_SLAB = registerBlock("ice_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.ICE.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GLASS)));

    public static final Block PACKED_ICE_VERTICAL_SLAB = registerBlock("packed_ice_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PACKED_ICE.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GLASS)));

    public static final Block BLUE_ICE_VERTICAL_SLAB = registerBlock("blue_ice_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BLUE_ICE.getDefaultMapColor())
                    .strength(2.8F)
                    .sounds(BlockSoundGroup.GLASS)));

    public static final Block MOSS_VERTICAL_SLAB = registerBlock("moss_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MOSS_BLOCK.getDefaultMapColor())
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.MOSS_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block CALCITE_VERTICAL_SLAB = registerBlock("calcite_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CALCITE.getDefaultMapColor())
                    .strength(0.75F)
                    .sounds(BlockSoundGroup.CALCITE)));

    public static final Block DRIPSTONE_VERTICAL_SLAB = registerBlock("dripstone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DRIPSTONE_BLOCK.getDefaultMapColor())
                    .strength(1.5F)
                    .sounds(BlockSoundGroup.DRIPSTONE_BLOCK)));

    public static final Block MAGMA_BLOCK_VERTICAL_SLAB = registerBlock("magma_block_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_RED)
                    .strength(0.5F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)
                    .luminance((state) -> 3)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .allowsSpawning((state, world, pos, entityType) -> entityType.isFireImmune())
                    .postProcess(Blocks::always)
                    .emissiveLighting(Blocks::always)));

    public static final Block OBSIDIAN_VERTICAL_SLAB = registerBlock("obsidian_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.OBSIDIAN.getDefaultMapColor())
                    .strength(50.0F, 1200.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block CRYING_OBSIDIAN_VERTICAL_SLAB = registerBlock("crying_obsidian_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CRYING_OBSIDIAN.getDefaultMapColor())
                    .strength(50.0F, 1200.0F)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)
                    .luminance((state) -> 10)));

    public static final Block CRIMSON_NYLIUM_VERTICAL_SLAB = registerBlock("crimson_nylium_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CRIMSON_NYLIUM.getDefaultMapColor())
                    .strength(0.4F)
                    .sounds(BlockSoundGroup.NYLIUM)));

    public static final Block WARPED_NYLIUM_VERTICAL_SLAB = registerBlock("warped_nylium_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WARPED_NYLIUM.getDefaultMapColor())
                    .strength(0.4F)
                    .sounds(BlockSoundGroup.NYLIUM)));

    public static final Block SOUL_SAND_VERTICAL_SLAB = registerBlock("soul_sand_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SOUL_SAND.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SOUL_SAND)
                    .instrument(NoteBlockInstrument.COW_BELL)));

    public static final Block SOUL_SOIL_VERTICAL_SLAB = registerBlock("soul_soil_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SOUL_SOIL.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.SOUL_SOIL)));

    public static final Block BONE_VERTICAL_SLAB = registerBlock("bone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BONE_BLOCK.getDefaultMapColor())
                    .strength(2.0F)
                    .sounds(BlockSoundGroup.BONE)
                    .instrument(NoteBlockInstrument.XYLOPHONE)));

    public static final Block GLOWSTONE_VERTICAL_SLAB = registerBlock("glowstone_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_YELLOW)
                    .instrument(NoteBlockInstrument.PLING)
                    .strength(0.3F)
                    .sounds(BlockSoundGroup.GLASS)
                    .luminance((state) -> 15)
                    .solidBlock(Blocks::never)));

    public static final Block MANGROVE_ROOT_VERTICAL_SLAB = registerBlock("mangrove_root_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MANGROVE_ROOTS.getDefaultMapColor())
                    .strength(0.7F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.ROOTS)));

    public static final Block MUDDY_MANGROVE_ROOT_VERTICAL_SLAB = registerBlock("muddy_mangrove_root_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MUDDY_MANGROVE_ROOTS.getDefaultMapColor())
                    .strength(0.7F)
                    .sounds(BlockSoundGroup.ROOTS)));

    public static final Block MUSHROOM_STEM_VERTICAL_SLAB = registerBlock("mushroom_stem_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MUSHROOM_STEM.getDefaultMapColor())
                    .strength(0.2F)
                    .sounds(BlockSoundGroup.WOOD)));

    public static final Block OAK_LEAF_VERTICAL_SLAB = registerBlock("oak_leaf_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.OAK_LEAVES.getDefaultMapColor())
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block SPRUCE_LEAF_VERTICAL_SLAB = registerBlock("spruce_leaf_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SPRUCE_LEAVES.getDefaultMapColor())
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block BIRCH_LEAF_VERTICAL_SLAB = registerBlock("birch_leaf_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BIRCH_LEAVES.getDefaultMapColor())
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block JUNGLE_LEAF_VERTICAL_SLAB = registerBlock("jungle_leaf_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.JUNGLE_LEAVES.getDefaultMapColor())
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block ACACIA_LEAF_VERTICAL_SLAB = registerBlock("acacia_leaf_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.ACACIA_LEAVES.getDefaultMapColor())
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block DARK_OAK_LEAF_VERTICAL_SLAB = registerBlock("dark_oak_leaf_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DARK_OAK_LEAVES.getDefaultMapColor())
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block MANGROVE_LEAF_VERTICAL_SLAB = registerBlock("mangrove_leaf_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MANGROVE_LEAVES.getDefaultMapColor())
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block CHERRY_LEAF_VERTICAL_SLAB = registerBlock("cherry_leaf_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CHERRY_LEAVES.getDefaultMapColor())
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block AZALEA_LEAF_VERTICAL_SLAB = registerBlock("azalea_leaf_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.AZALEA_LEAVES.getDefaultMapColor())
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block FLOWERING_AZALEA_LEAF_VERTICAL_SLAB = registerBlock("flowering_azalea_leaf_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.FLOWERING_AZALEA_LEAVES.getDefaultMapColor())
                    .strength(0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.GRASS)));

    public static final Block BROWN_MUSHROOM_VERTICAL_SLAB = registerBlock("brown_mushroom_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BROWN_MUSHROOM_BLOCK.getDefaultMapColor())
                    .strength(0.2F)
                    .sounds(BlockSoundGroup.WOOD)));

    public static final Block RED_MUSHROOM_VERTICAL_SLAB = registerBlock("red_mushroom_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.RED_MUSHROOM_BLOCK.getDefaultMapColor())
                    .strength(0.2F)
                    .sounds(BlockSoundGroup.WOOD)));

    public static final Block NETHER_WART_VERTICAL_SLAB = registerBlock("nether_wart_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.NETHER_WART_BLOCK.getDefaultMapColor())
                    .strength(1.0F)
                    .sounds(BlockSoundGroup.WART_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block WARPED_WART_VERTICAL_SLAB = registerBlock("warped_wart_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WARPED_WART_BLOCK.getDefaultMapColor())
                    .strength(1.0F)
                    .sounds(BlockSoundGroup.WART_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block DRIED_KELP_VERTICAL_SLAB = registerBlock("dried_kelp_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DRIED_KELP_BLOCK.getDefaultMapColor())
                    .strength(0.5F, 2.5F)
                    .sounds(BlockSoundGroup.GRASS)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block TUBE_CORAL_VERTICAL_SLAB = registerBlock("tube_coral_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.TUBE_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block BRAIN_CORAL_VERTICAL_SLAB = registerBlock("brain_coral_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BRAIN_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block BUBBLE_CORAL_VERTICAL_SLAB = registerBlock("bubble_coral_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.BUBBLE_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block FIRE_CORAL_VERTICAL_SLAB = registerBlock("fire_coral_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.FIRE_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block HORN_CORAL_VERTICAL_SLAB = registerBlock("horn_coral_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.HORN_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block DEAD_TUBE_CORAL_VERTICAL_SLAB = registerBlock("dead_tube_coral_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DEAD_TUBE_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)));

    public static final Block DEAD_BRAIN_CORAL_VERTICAL_SLAB = registerBlock("dead_brain_coral_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DEAD_BRAIN_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)));

    public static final Block DEAD_BUBBLE_CORAL_VERTICAL_SLAB = registerBlock("dead_bubble_coral_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DEAD_BUBBLE_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)));

    public static final Block DEAD_FIRE_CORAL_VERTICAL_SLAB = registerBlock("dead_fire_coral_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DEAD_FIRE_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)));

    public static final Block DEAD_HORN_CORAL_VERTICAL_SLAB = registerBlock("dead_horn_coral_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.CORAL)));

    public static final Block SPONGE_VERTICAL_SLAB = registerBlock("sponge_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SPONGE.getDefaultMapColor())
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.SPONGE)));

    public static final Block WET_SPONGE_VERTICAL_SLAB = registerBlock("wet_sponge_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.WET_SPONGE.getDefaultMapColor())
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.WET_SPONGE)));

    public static final Block MELON_VERTICAL_SLAB = registerBlock("melon_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.MELON.getDefaultMapColor())
                    .strength(1.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block PUMPKIN_VERTICAL_SLAB = registerBlock("pumpkin_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.PUMPKIN.getDefaultMapColor())
                    .strength(1.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .instrument(NoteBlockInstrument.BASS)));

    /*public static final Block CARVED_PUMPKIN_VERTICAL_SLAB = registerBlock("carved_pumpkin_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CARVED_PUMPKIN.getDefaultMapColor())
                    .strength(1.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .instrument(NoteBlockInstrument.BASS)));*/

    public static final Block HAY_VERTICAL_SLAB = registerBlock("hay_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.HAY_BLOCK.getDefaultMapColor())
                    .strength(0.5F)
                    .sounds(BlockSoundGroup.GRASS)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block HONEYCOMB_VERTICAL_SLAB = registerBlock("honeycomb_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.HONEYCOMB_BLOCK.getDefaultMapColor())
                    .strength(0.6F)
                    .sounds(BlockSoundGroup.CORAL)));

    public static final Block SCULK_VERTICAL_SLAB = registerBlock("sculk_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.SCULK.getDefaultMapColor())
                    .strength(0.8F)
                    .sounds(BlockSoundGroup.SCULK)
                    .instrument(NoteBlockInstrument.BASS)));



    ///////////////////////
    //   System Blocks   //
    ///////////////////////

    public static final Block DOUBLE_SLAB_BLOCK = registerBlock("double_slab_block",
            new DoubleSlabBlock(AbstractBlock.Settings.create()
                    .luminance(DoubleSlabBlockEntity.LUMINANCE)
                    .emissiveLighting((state, world, pos) -> state.get(DoubleSlabBlock.IS_EMISSIVE_LIGHTING))));

    public static final Block DOUBLE_VERTICAL_SLAB_BLOCK = registerBlock("double_vertical_slab_block",
            new DoubleVerticalSlabBlock(AbstractBlock.Settings.create()
                    .luminance(DoubleVerticalSlabBlockEntity.LUMINANCE)
                    .emissiveLighting((state, world, pos) -> state.get(DoubleVerticalSlabBlock.IS_EMISSIVE_LIGHTING))
                    .nonOpaque()));



    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Slabee.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Slabee.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    private static Block createStainedGlassSlab(DyeColor color) {
        return new TransparentSlabBlock(AbstractBlock.Settings.create()
                .mapColor(color)
                .instrument(NoteBlockInstrument.HAT)
                .strength(0.3F)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque()
                .allowsSpawning(Blocks::never)
                .solidBlock(Blocks::never)
                .suffocates(Blocks::never)
                .blockVision(Blocks::never));
    }

    private static Block createStainedGlassVerticalSlab(DyeColor color) {
        return new TransparentVerticalSlabBlock(AbstractBlock.Settings.create()
                .mapColor(color)
                .instrument(NoteBlockInstrument.HAT)
                .strength(0.3F)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque()
                .allowsSpawning(Blocks::never)
                .solidBlock(Blocks::never)
                .suffocates(Blocks::never)
                .blockVision(Blocks::never));
    }

    public static void registerModBlocks() {
        Slabee.LOGGER.info("Registering Mod Blocks for " + Slabee.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.addAfter(Blocks.OAK_LOG, ModBlocks.OAK_LOG_SLAB);
            entries.addAfter(Blocks.SPRUCE_LOG, ModBlocks.SPRUCE_LOG_SLAB);
            entries.addAfter(Blocks.BIRCH_LOG, ModBlocks.BIRCH_LOG_SLAB);
            entries.addAfter(Blocks.JUNGLE_LOG, ModBlocks.JUNGLE_LOG_SLAB);
            entries.addAfter(Blocks.ACACIA_LOG, ModBlocks.ACACIA_LOG_SLAB);
            entries.addAfter(Blocks.DARK_OAK_LOG, ModBlocks.DARK_OAK_LOG_SLAB);
            entries.addAfter(Blocks.MANGROVE_LOG, ModBlocks.MANGROVE_LOG_SLAB);
            entries.addAfter(Blocks.CHERRY_LOG, ModBlocks.CHERRY_LOG_SLAB);
            entries.addAfter(Blocks.BAMBOO_BLOCK, ModBlocks.BAMBOO_BLOCK_SLAB);
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
            entries.addAfter(Blocks.STONE_BRICK_SLAB, ModBlocks.CRACKED_STONE_BRICK_SLAB);
            entries.addAfter(Blocks.DEEPSLATE, ModBlocks.DEEPSLATE_SLAB);
            entries.addAfter(Blocks.DEEPSLATE_BRICK_SLAB, ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB);
            entries.addAfter(Blocks.REINFORCED_DEEPSLATE, ModBlocks.REINFORCED_DEEPSLATE_SLAB);
            entries.addAfter(Blocks.PACKED_MUD, ModBlocks.PACKED_MUD_SLAB);
            entries.addAfter(Blocks.NETHERRACK, ModBlocks.NETHERRACK_SLAB);
            entries.addAfter(Blocks.NETHER_BRICK_SLAB, ModBlocks.CRACKED_NETHER_BRICK_SLAB);
            entries.addAfter(Blocks.BASALT, ModBlocks.BASALT_SLAB);
            entries.addAfter(Blocks.SMOOTH_BASALT, ModBlocks.SMOOTH_BASALT_SLAB);
            entries.addAfter(Blocks.POLISHED_BASALT, ModBlocks.POLISHED_BASALT_SLAB);
            entries.addAfter(Blocks.GILDED_BLACKSTONE, ModBlocks.GILDED_BLACKSTONE_SLAB);
            entries.addAfter(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB);
            entries.addAfter(Blocks.END_STONE, ModBlocks.END_STONE_SLAB);
            entries.addAfter(Blocks.PURPUR_PILLAR, ModBlocks.PURPUR_PILLAR_SLAB);
            entries.addAfter(Blocks.QUARTZ_BRICKS, ModBlocks.QUARTZ_BRICK_SLAB);
            entries.addAfter(Blocks.QUARTZ_PILLAR, ModBlocks.QUARTZ_PILLAR_SLAB);
            entries.addAfter(Blocks.AMETHYST_BLOCK, ModBlocks.AMETHYST_SLAB);

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
            entries.addAfter(ModBlocks.OAK_LOG_SLAB, ModBlocks.OAK_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.SPRUCE_LOG_SLAB, ModBlocks.SPRUCE_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BIRCH_LOG_SLAB, ModBlocks.BIRCH_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.JUNGLE_LOG_SLAB, ModBlocks.JUNGLE_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.ACACIA_LOG_SLAB, ModBlocks.ACACIA_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.DARK_OAK_LOG_SLAB, ModBlocks.DARK_OAK_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.MANGROVE_LOG_SLAB, ModBlocks.MANGROVE_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.CHERRY_LOG_SLAB, ModBlocks.CHERRY_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BAMBOO_BLOCK_SLAB, ModBlocks.BAMBOO_BLOCK_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.CRIMSON_STEM_SLAB, ModBlocks.CRIMSON_STEM_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.OAK_WOOD_SLAB, ModBlocks.OAK_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.SPRUCE_WOOD_SLAB, ModBlocks.SPRUCE_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BIRCH_WOOD_SLAB, ModBlocks.BIRCH_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.JUNGLE_WOOD_SLAB, ModBlocks.JUNGLE_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.ACACIA_WOOD_SLAB, ModBlocks.ACACIA_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.DARK_OAK_WOOD_SLAB, ModBlocks.DARK_OAK_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.MANGROVE_WOOD_SLAB, ModBlocks.MANGROVE_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.CHERRY_WOOD_SLAB, ModBlocks.CHERRY_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.CRIMSON_HYPHAE_SLAB, ModBlocks.CRIMSON_HYPHAE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.WARPED_HYPHAE_SLAB, ModBlocks.WARPED_HYPHAE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.WARPED_STEM_SLAB, ModBlocks.WARPED_STEM_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_OAK_LOG_SLAB, ModBlocks.STRIPPED_OAK_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_SPRUCE_LOG_SLAB, ModBlocks.STRIPPED_SPRUCE_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_BIRCH_LOG_SLAB, ModBlocks.STRIPPED_BIRCH_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_JUNGLE_LOG_SLAB, ModBlocks.STRIPPED_JUNGLE_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_ACACIA_LOG_SLAB, ModBlocks.STRIPPED_ACACIA_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_DARK_OAK_LOG_SLAB, ModBlocks.STRIPPED_DARK_OAK_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_MANGROVE_LOG_SLAB, ModBlocks.STRIPPED_MANGROVE_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_CHERRY_LOG_SLAB, ModBlocks.STRIPPED_CHERRY_LOG_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_BAMBOO_SLAB, ModBlocks.STRIPPED_BAMBOO_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_CRIMSON_STEM_SLAB, ModBlocks.STRIPPED_CRIMSON_STEM_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_WARPED_STEM_SLAB, ModBlocks.STRIPPED_WARPED_STEM_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_OAK_WOOD_SLAB, ModBlocks.STRIPPED_OAK_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_SPRUCE_WOOD_SLAB, ModBlocks.STRIPPED_SPRUCE_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_BIRCH_WOOD_SLAB, ModBlocks.STRIPPED_BIRCH_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_JUNGLE_WOOD_SLAB, ModBlocks.STRIPPED_JUNGLE_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_ACACIA_WOOD_SLAB, ModBlocks.STRIPPED_ACACIA_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_DARK_OAK_WOOD_SLAB, ModBlocks.STRIPPED_DARK_OAK_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_MANGROVE_WOOD_SLAB, ModBlocks.STRIPPED_MANGROVE_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_CHERRY_WOOD_SLAB, ModBlocks.STRIPPED_CHERRY_WOOD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_CRIMSON_HYPHAE_SLAB, ModBlocks.STRIPPED_CRIMSON_HYPHAE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.STRIPPED_WARPED_HYPHAE_SLAB, ModBlocks.STRIPPED_WARPED_HYPHAE_VERTICAL_SLAB);
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

            entries.addAfter(ModBlocks.CRACKED_STONE_BRICK_SLAB, ModBlocks.CRACKED_STONE_BRICK_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.DEEPSLATE_SLAB, ModBlocks.DEEPSLATE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB, ModBlocks.CRACKED_DEEPSLATE_BRICK_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.REINFORCED_DEEPSLATE_SLAB, ModBlocks.REINFORCED_DEEPSLATE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.PACKED_MUD_SLAB, ModBlocks.PACKED_MUD_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.NETHERRACK_SLAB, ModBlocks.NETHERRACK_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.CRACKED_NETHER_BRICK_SLAB, ModBlocks.CRACKED_NETHER_BRICK_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BASALT_SLAB, ModBlocks.BASALT_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.SMOOTH_BASALT_SLAB, ModBlocks.SMOOTH_BASALT_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.POLISHED_BASALT_SLAB, ModBlocks.POLISHED_BASALT_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.GILDED_BLACKSTONE_SLAB, ModBlocks.GILDED_BLACKSTONE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.END_STONE_SLAB, ModBlocks.END_STONE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.PURPUR_PILLAR_SLAB, ModBlocks.PURPUR_PILLAR_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.QUARTZ_BRICK_SLAB, ModBlocks.QUARTZ_BRICK_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.QUARTZ_PILLAR_SLAB, ModBlocks.QUARTZ_PILLAR_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.AMETHYST_SLAB, ModBlocks.AMETHYST_VERTICAL_SLAB);

        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> {

            // Wool Slabs
            entries.addAfter(Blocks.PINK_WOOL, ModBlocks.WHITE_WOOL_SLAB);
            entries.addAfter(ModBlocks.WHITE_WOOL_SLAB, ModBlocks.LIGHT_GRAY_WOOL_SLAB);
            entries.addAfter(ModBlocks.LIGHT_GRAY_WOOL_SLAB, ModBlocks.GRAY_WOOL_SLAB);
            entries.addAfter(ModBlocks.GRAY_WOOL_SLAB, ModBlocks.BLACK_WOOL_SLAB);
            entries.addAfter(ModBlocks.BLACK_WOOL_SLAB, ModBlocks.BROWN_WOOL_SLAB);
            entries.addAfter(ModBlocks.BROWN_WOOL_SLAB, ModBlocks.RED_WOOL_SLAB);
            entries.addAfter(ModBlocks.RED_WOOL_SLAB, ModBlocks.ORANGE_WOOL_SLAB);
            entries.addAfter(ModBlocks.ORANGE_WOOL_SLAB, ModBlocks.YELLOW_WOOL_SLAB);
            entries.addAfter(ModBlocks.YELLOW_WOOL_SLAB, ModBlocks.LIME_WOOL_SLAB);
            entries.addAfter(ModBlocks.LIME_WOOL_SLAB, ModBlocks.GREEN_WOOL_SLAB);
            entries.addAfter(ModBlocks.GREEN_WOOL_SLAB, ModBlocks.CYAN_WOOL_SLAB);
            entries.addAfter(ModBlocks.CYAN_WOOL_SLAB, ModBlocks.LIGHT_BLUE_WOOL_SLAB);
            entries.addAfter(ModBlocks.LIGHT_BLUE_WOOL_SLAB, ModBlocks.BLUE_WOOL_SLAB);
            entries.addAfter(ModBlocks.BLUE_WOOL_SLAB, ModBlocks.PURPLE_WOOL_SLAB);
            entries.addAfter(ModBlocks.PURPLE_WOOL_SLAB, ModBlocks.MAGENTA_WOOL_SLAB);
            entries.addAfter(ModBlocks.MAGENTA_WOOL_SLAB, ModBlocks.PINK_WOOL_SLAB);

            // Terracotta Slabs
            entries.addAfter(Blocks.PINK_TERRACOTTA, ModBlocks.TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.TERRACOTTA_SLAB, ModBlocks.WHITE_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.WHITE_TERRACOTTA_SLAB, ModBlocks.LIGHT_GRAY_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.LIGHT_GRAY_TERRACOTTA_SLAB, ModBlocks.GRAY_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.GRAY_TERRACOTTA_SLAB, ModBlocks.BLACK_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.BLACK_TERRACOTTA_SLAB, ModBlocks.BROWN_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.BROWN_TERRACOTTA_SLAB, ModBlocks.RED_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.RED_TERRACOTTA_SLAB, ModBlocks.ORANGE_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.ORANGE_TERRACOTTA_SLAB, ModBlocks.YELLOW_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.YELLOW_TERRACOTTA_SLAB, ModBlocks.LIME_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.LIME_TERRACOTTA_SLAB, ModBlocks.GREEN_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.GREEN_TERRACOTTA_SLAB, ModBlocks.CYAN_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.CYAN_TERRACOTTA_SLAB, ModBlocks.LIGHT_BLUE_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.LIGHT_BLUE_TERRACOTTA_SLAB, ModBlocks.BLUE_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.BLUE_TERRACOTTA_SLAB, ModBlocks.PURPLE_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.PURPLE_TERRACOTTA_SLAB, ModBlocks.MAGENTA_TERRACOTTA_SLAB);
            entries.addAfter(ModBlocks.MAGENTA_TERRACOTTA_SLAB, ModBlocks.PINK_TERRACOTTA_SLAB);

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
            entries.addAfter(Blocks.PINK_CONCRETE_POWDER, ModBlocks.WHITE_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.WHITE_CONCRETE_POWDER_SLAB, ModBlocks.LIGHT_GRAY_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.LIGHT_GRAY_CONCRETE_POWDER_SLAB, ModBlocks.GRAY_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.GRAY_CONCRETE_POWDER_SLAB, ModBlocks.BLACK_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.BLACK_CONCRETE_POWDER_SLAB, ModBlocks.BROWN_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.BROWN_CONCRETE_POWDER_SLAB, ModBlocks.RED_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.RED_CONCRETE_POWDER_SLAB, ModBlocks.ORANGE_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.ORANGE_CONCRETE_POWDER_SLAB, ModBlocks.YELLOW_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.YELLOW_CONCRETE_POWDER_SLAB, ModBlocks.LIME_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.LIME_CONCRETE_POWDER_SLAB, ModBlocks.GREEN_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.GREEN_CONCRETE_POWDER_SLAB, ModBlocks.CYAN_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.CYAN_CONCRETE_POWDER_SLAB, ModBlocks.LIGHT_BLUE_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.LIGHT_BLUE_CONCRETE_POWDER_SLAB, ModBlocks.BLUE_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.BLUE_CONCRETE_POWDER_SLAB, ModBlocks.PURPLE_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.PURPLE_CONCRETE_POWDER_SLAB, ModBlocks.MAGENTA_CONCRETE_POWDER_SLAB);
            entries.addAfter(ModBlocks.MAGENTA_CONCRETE_POWDER_SLAB, ModBlocks.PINK_CONCRETE_POWDER_SLAB);

            // Glass Slabs
            entries.addAfter(Blocks.PINK_STAINED_GLASS, ModBlocks.GLASS_SLAB);
            entries.addAfter(ModBlocks.GLASS_SLAB, ModBlocks.TINTED_GLASS_SLAB);
            entries.addAfter(ModBlocks.TINTED_GLASS_SLAB, ModBlocks.WHITE_STAINED_GLASS_SLAB);
            entries.addAfter(ModBlocks.WHITE_STAINED_GLASS_SLAB, ModBlocks.LIGHT_GRAY_STAINED_GLASS_SLAB);
            entries.addAfter(ModBlocks.LIGHT_GRAY_STAINED_GLASS_SLAB, ModBlocks.GRAY_STAINED_GLASS_SLAB);
            entries.addAfter(ModBlocks.GRAY_STAINED_GLASS_SLAB, ModBlocks.BLACK_STAINED_GLASS_SLAB);
            entries.addAfter(ModBlocks.BLACK_STAINED_GLASS_SLAB, ModBlocks.BROWN_STAINED_GLASS_SLAB);
            entries.addAfter(ModBlocks.BROWN_STAINED_GLASS_SLAB, ModBlocks.RED_STAINED_GLASS_SLAB);
            entries.addAfter(ModBlocks.RED_STAINED_GLASS_SLAB, ModBlocks.ORANGE_STAINED_GLASS_SLAB);
            entries.addAfter(ModBlocks.ORANGE_STAINED_GLASS_SLAB, ModBlocks.YELLOW_STAINED_GLASS_SLAB);
            entries.addAfter(ModBlocks.YELLOW_STAINED_GLASS_SLAB, ModBlocks.LIME_STAINED_GLASS_SLAB);
            entries.addAfter(ModBlocks.LIME_STAINED_GLASS_SLAB, ModBlocks.GREEN_STAINED_GLASS_SLAB);
            entries.addAfter(ModBlocks.GREEN_STAINED_GLASS_SLAB, ModBlocks.CYAN_STAINED_GLASS_SLAB);
            entries.addAfter(ModBlocks.CYAN_STAINED_GLASS_SLAB, ModBlocks.LIGHT_BLUE_STAINED_GLASS_SLAB);
            entries.addAfter(ModBlocks.LIGHT_BLUE_STAINED_GLASS_SLAB, ModBlocks.BLUE_STAINED_GLASS_SLAB);
            entries.addAfter(ModBlocks.BLUE_STAINED_GLASS_SLAB, ModBlocks.PURPLE_STAINED_GLASS_SLAB);
            entries.addAfter(ModBlocks.PURPLE_STAINED_GLASS_SLAB, ModBlocks.MAGENTA_STAINED_GLASS_SLAB);
            entries.addAfter(ModBlocks.MAGENTA_STAINED_GLASS_SLAB, ModBlocks.PINK_STAINED_GLASS_SLAB);

            // Wool Vertical Slabs
            entries.addAfter(ModBlocks.PINK_WOOL_SLAB, ModBlocks.WHITE_WOOL_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.WHITE_WOOL_VERTICAL_SLAB, ModBlocks.LIGHT_GRAY_WOOL_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.LIGHT_GRAY_WOOL_VERTICAL_SLAB, ModBlocks.GRAY_WOOL_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.GRAY_WOOL_VERTICAL_SLAB, ModBlocks.BLACK_WOOL_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BLACK_WOOL_VERTICAL_SLAB, ModBlocks.BROWN_WOOL_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BROWN_WOOL_VERTICAL_SLAB, ModBlocks.RED_WOOL_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.RED_WOOL_VERTICAL_SLAB, ModBlocks.ORANGE_WOOL_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.ORANGE_WOOL_VERTICAL_SLAB, ModBlocks.YELLOW_WOOL_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.YELLOW_WOOL_VERTICAL_SLAB, ModBlocks.LIME_WOOL_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.LIME_WOOL_VERTICAL_SLAB, ModBlocks.GREEN_WOOL_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.GREEN_WOOL_VERTICAL_SLAB, ModBlocks.CYAN_WOOL_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.CYAN_WOOL_VERTICAL_SLAB, ModBlocks.LIGHT_BLUE_WOOL_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.LIGHT_BLUE_WOOL_VERTICAL_SLAB, ModBlocks.BLUE_WOOL_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BLUE_WOOL_VERTICAL_SLAB, ModBlocks.PURPLE_WOOL_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.PURPLE_WOOL_VERTICAL_SLAB, ModBlocks.MAGENTA_WOOL_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.MAGENTA_WOOL_VERTICAL_SLAB, ModBlocks.PINK_WOOL_VERTICAL_SLAB);

            // Terracotta Vertical Slabs
            entries.addAfter(ModBlocks.PINK_TERRACOTTA_SLAB, ModBlocks.TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.TERRACOTTA_VERTICAL_SLAB, ModBlocks.WHITE_TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.WHITE_TERRACOTTA_VERTICAL_SLAB, ModBlocks.LIGHT_GRAY_TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.LIGHT_GRAY_TERRACOTTA_VERTICAL_SLAB, ModBlocks.GRAY_TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.GRAY_TERRACOTTA_VERTICAL_SLAB, ModBlocks.BLACK_TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BLACK_TERRACOTTA_VERTICAL_SLAB, ModBlocks.BROWN_TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BROWN_TERRACOTTA_VERTICAL_SLAB, ModBlocks.RED_TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.RED_TERRACOTTA_VERTICAL_SLAB, ModBlocks.ORANGE_TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.ORANGE_TERRACOTTA_VERTICAL_SLAB, ModBlocks.YELLOW_TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.YELLOW_TERRACOTTA_VERTICAL_SLAB, ModBlocks.LIME_TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.LIME_TERRACOTTA_VERTICAL_SLAB, ModBlocks.GREEN_TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.GREEN_TERRACOTTA_VERTICAL_SLAB, ModBlocks.CYAN_TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.CYAN_TERRACOTTA_VERTICAL_SLAB, ModBlocks.LIGHT_BLUE_TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.LIGHT_BLUE_TERRACOTTA_VERTICAL_SLAB, ModBlocks.BLUE_TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BLUE_TERRACOTTA_VERTICAL_SLAB, ModBlocks.PURPLE_TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.PURPLE_TERRACOTTA_VERTICAL_SLAB, ModBlocks.MAGENTA_TERRACOTTA_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.MAGENTA_TERRACOTTA_VERTICAL_SLAB, ModBlocks.PINK_TERRACOTTA_VERTICAL_SLAB);

            // Concrete Vertical Slabs
            entries.addAfter(ModBlocks.PINK_CONCRETE_SLAB, ModBlocks.WHITE_CONCRETE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.WHITE_CONCRETE_VERTICAL_SLAB, ModBlocks.LIGHT_GRAY_CONCRETE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.LIGHT_GRAY_CONCRETE_VERTICAL_SLAB, ModBlocks.GRAY_CONCRETE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.GRAY_CONCRETE_VERTICAL_SLAB, ModBlocks.BLACK_CONCRETE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BLACK_CONCRETE_VERTICAL_SLAB, ModBlocks.BROWN_CONCRETE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BROWN_CONCRETE_VERTICAL_SLAB, ModBlocks.RED_CONCRETE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.RED_CONCRETE_VERTICAL_SLAB, ModBlocks.ORANGE_CONCRETE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.ORANGE_CONCRETE_VERTICAL_SLAB, ModBlocks.YELLOW_CONCRETE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.YELLOW_CONCRETE_VERTICAL_SLAB, ModBlocks.LIME_CONCRETE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.LIME_CONCRETE_VERTICAL_SLAB, ModBlocks.GREEN_CONCRETE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.GREEN_CONCRETE_VERTICAL_SLAB, ModBlocks.CYAN_CONCRETE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.CYAN_CONCRETE_VERTICAL_SLAB, ModBlocks.LIGHT_BLUE_CONCRETE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.LIGHT_BLUE_CONCRETE_VERTICAL_SLAB, ModBlocks.BLUE_CONCRETE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BLUE_CONCRETE_VERTICAL_SLAB, ModBlocks.PURPLE_CONCRETE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.PURPLE_CONCRETE_VERTICAL_SLAB, ModBlocks.MAGENTA_CONCRETE_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.MAGENTA_CONCRETE_VERTICAL_SLAB, ModBlocks.PINK_CONCRETE_VERTICAL_SLAB);

            // Concrete Powder Vertical Slabs
            entries.addAfter(ModBlocks.PINK_CONCRETE_POWDER_SLAB, ModBlocks.WHITE_CONCRETE_POWDER_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.WHITE_CONCRETE_POWDER_VERTICAL_SLAB, ModBlocks.LIGHT_GRAY_CONCRETE_POWDER_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.LIGHT_GRAY_CONCRETE_POWDER_VERTICAL_SLAB, ModBlocks.GRAY_CONCRETE_POWDER_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.GRAY_CONCRETE_POWDER_VERTICAL_SLAB, ModBlocks.BLACK_CONCRETE_POWDER_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BLACK_CONCRETE_POWDER_VERTICAL_SLAB, ModBlocks.BROWN_CONCRETE_POWDER_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BROWN_CONCRETE_POWDER_VERTICAL_SLAB, ModBlocks.RED_CONCRETE_POWDER_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.RED_CONCRETE_POWDER_VERTICAL_SLAB, ModBlocks.ORANGE_CONCRETE_POWDER_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.ORANGE_CONCRETE_POWDER_VERTICAL_SLAB, ModBlocks.YELLOW_CONCRETE_POWDER_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.YELLOW_CONCRETE_POWDER_VERTICAL_SLAB, ModBlocks.LIME_CONCRETE_POWDER_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.LIME_CONCRETE_POWDER_VERTICAL_SLAB, ModBlocks.GREEN_CONCRETE_POWDER_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.GREEN_CONCRETE_POWDER_VERTICAL_SLAB, ModBlocks.CYAN_CONCRETE_POWDER_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.CYAN_CONCRETE_POWDER_VERTICAL_SLAB, ModBlocks.LIGHT_BLUE_CONCRETE_POWDER_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.LIGHT_BLUE_CONCRETE_POWDER_VERTICAL_SLAB, ModBlocks.BLUE_CONCRETE_POWDER_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BLUE_CONCRETE_POWDER_VERTICAL_SLAB, ModBlocks.PURPLE_CONCRETE_POWDER_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.PURPLE_CONCRETE_POWDER_VERTICAL_SLAB, ModBlocks.MAGENTA_CONCRETE_POWDER_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.MAGENTA_CONCRETE_POWDER_VERTICAL_SLAB, ModBlocks.PINK_CONCRETE_POWDER_VERTICAL_SLAB);

            // Glass Vertical Slabs
            entries.addAfter(ModBlocks.PINK_STAINED_GLASS_SLAB, ModBlocks.GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.GLASS_VERTICAL_SLAB, ModBlocks.TINTED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.TINTED_GLASS_VERTICAL_SLAB, ModBlocks.WHITE_STAINED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.WHITE_STAINED_GLASS_VERTICAL_SLAB, ModBlocks.LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB, ModBlocks.GRAY_STAINED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.GRAY_STAINED_GLASS_VERTICAL_SLAB, ModBlocks.BLACK_STAINED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BLACK_STAINED_GLASS_VERTICAL_SLAB, ModBlocks.BROWN_STAINED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BROWN_STAINED_GLASS_VERTICAL_SLAB, ModBlocks.RED_STAINED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.RED_STAINED_GLASS_VERTICAL_SLAB, ModBlocks.ORANGE_STAINED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.ORANGE_STAINED_GLASS_VERTICAL_SLAB, ModBlocks.YELLOW_STAINED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.YELLOW_STAINED_GLASS_VERTICAL_SLAB, ModBlocks.LIME_STAINED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.LIME_STAINED_GLASS_VERTICAL_SLAB, ModBlocks.GREEN_STAINED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.GREEN_STAINED_GLASS_VERTICAL_SLAB, ModBlocks.CYAN_STAINED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.CYAN_STAINED_GLASS_VERTICAL_SLAB, ModBlocks.LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB, ModBlocks.BLUE_STAINED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.BLUE_STAINED_GLASS_VERTICAL_SLAB, ModBlocks.PURPLE_STAINED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.PURPLE_STAINED_GLASS_VERTICAL_SLAB, ModBlocks.MAGENTA_STAINED_GLASS_VERTICAL_SLAB);
            entries.addAfter(ModBlocks.MAGENTA_STAINED_GLASS_VERTICAL_SLAB, ModBlocks.PINK_STAINED_GLASS_VERTICAL_SLAB);
        });
    }
}
