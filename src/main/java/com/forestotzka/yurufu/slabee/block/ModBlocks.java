package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.Slabee;
import com.forestotzka.yurufu.slabee.SlabeeUtils;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {

    //////////////
    //   Slab   //
    //////////////

    // Log Slabs
    public static final Block OAK_LOG_SLAB = registerBlock("oak_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block SPRUCE_LOG_SLAB = registerBlock("spruce_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block BIRCH_LOG_SLAB = registerBlock("birch_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block JUNGLE_LOG_SLAB = registerBlock("jungle_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIRT)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block ACACIA_LOG_SLAB = registerBlock("acacia_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block DARK_OAK_LOG_SLAB = registerBlock("dark_oak_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block MANGROVE_LOG_SLAB = registerBlock("mangrove_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block CHERRY_LOG_SLAB = registerBlock("cherry_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.CHERRY_WOOD)
                    .ignitedByLava()));

    public static final Block BAMBOO_BLOCK_SLAB = registerBlock("bamboo_block_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .strength(2.0F)
                    .sound(SoundType.BAMBOO_WOOD)
                    .ignitedByLava()));

    public static final Block CRIMSON_STEM_SLAB = registerBlock("crimson_stem_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.CRIMSON_STEM)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));

    public static final Block WARPED_STEM_SLAB = registerBlock("warped_stem_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WARPED_STEM)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));


    // Wood Slabs
    public static final Block OAK_WOOD_SLAB = registerBlock("oak_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block SPRUCE_WOOD_SLAB = registerBlock("spruce_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block BIRCH_WOOD_SLAB = registerBlock("birch_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block JUNGLE_WOOD_SLAB = registerBlock("jungle_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIRT)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block ACACIA_WOOD_SLAB = registerBlock("acacia_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block DARK_OAK_WOOD_SLAB = registerBlock("dark_oak_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block MANGROVE_WOOD_SLAB = registerBlock("mangrove_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block CHERRY_WOOD_SLAB = registerBlock("cherry_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_GRAY)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.CHERRY_WOOD)
                    .ignitedByLava()));

    public static final Block CRIMSON_HYPHAE_SLAB = registerBlock("crimson_hyphae_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.CRIMSON_HYPHAE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));

    public static final Block WARPED_HYPHAE_SLAB = registerBlock("warped_hyphae_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WARPED_HYPHAE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));


    // Stripped Log Slabs
    public static final Block STRIPPED_OAK_LOG_SLAB = registerBlock("stripped_oak_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_SPRUCE_LOG_SLAB = registerBlock("stripped_spruce_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_BIRCH_LOG_SLAB = registerBlock("stripped_birch_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_JUNGLE_LOG_SLAB = registerBlock("stripped_jungle_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIRT)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_ACACIA_LOG_SLAB = registerBlock("stripped_acacia_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_DARK_OAK_LOG_SLAB = registerBlock("stripped_dark_oak_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_MANGROVE_LOG_SLAB = registerBlock("stripped_mangrove_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_CHERRY_LOG_SLAB = registerBlock("stripped_cherry_log_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.CHERRY_WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_BAMBOO_SLAB = registerBlock("stripped_bamboo_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.BAMBOO_WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_CRIMSON_STEM_SLAB = registerBlock("stripped_crimson_stem_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.CRIMSON_STEM)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));

    public static final Block STRIPPED_WARPED_STEM_SLAB = registerBlock("stripped_warped_stem_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WARPED_STEM)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));


    // Stripped Wood Slabs
    public static final Block STRIPPED_OAK_WOOD_SLAB = registerBlock("stripped_oak_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_SPRUCE_WOOD_SLAB = registerBlock("stripped_spruce_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_BIRCH_WOOD_SLAB = registerBlock("stripped_birch_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_JUNGLE_WOOD_SLAB = registerBlock("stripped_jungle_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIRT)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_ACACIA_WOOD_SLAB = registerBlock("stripped_acacia_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_DARK_OAK_WOOD_SLAB = registerBlock("stripped_dark_oak_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_MANGROVE_WOOD_SLAB = registerBlock("stripped_mangrove_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_CHERRY_WOOD_SLAB = registerBlock("stripped_cherry_wood_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_PINK)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.CHERRY_WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_CRIMSON_HYPHAE_SLAB = registerBlock("stripped_crimson_hyphae_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.CRIMSON_HYPHAE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));

    public static final Block STRIPPED_WARPED_HYPHAE_SLAB = registerBlock("stripped_warped_hyphae_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WARPED_HYPHAE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));


    // Stone-related Slabs
    public static final Block CRACKED_STONE_BRICK_SLAB = registerBlock("cracked_stone_brick_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CRACKED_STONE_BRICKS.defaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block DEEPSLATE_SLAB = registerBlock("deepslate_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DEEPSLATE.defaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)));

    public static final Block CRACKED_DEEPSLATE_BRICK_SLAB = registerBlock("cracked_deepslate_brick_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CRACKED_DEEPSLATE_BRICKS.defaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE_BRICKS)));

    public static final Block REINFORCED_DEEPSLATE_SLAB = registerBlock("reinforced_deepslate_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.REINFORCED_DEEPSLATE.defaultMapColor())
                    .strength(55.0F, 1200.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)));

    public static final Block PACKED_MUD_SLAB = registerBlock("packed_mud_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PACKED_MUD.defaultMapColor())
                    .strength(1.5F)
                    .sound(SoundType.PACKED_MUD)));

    public static final Block NETHERRACK_SLAB = registerBlock("netherrack_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.NETHERRACK.defaultMapColor())
                    .strength(0.4F)
                    .sound(SoundType.NETHERRACK)));

    public static final Block CRACKED_NETHER_BRICK_SLAB = registerBlock("cracked_nether_brick_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CRACKED_NETHER_BRICKS.defaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.NETHER_BRICKS)));

    public static final Block BASALT_SLAB = registerBlock("basalt_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BASALT.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.BASALT)));

    public static final Block SMOOTH_BASALT_SLAB = registerBlock("smooth_basalt_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SMOOTH_BASALT.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.BASALT)));

    public static final Block POLISHED_BASALT_SLAB = registerBlock("polished_basalt_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.POLISHED_BASALT.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.BASALT)));

    public static final Block GILDED_BLACKSTONE_SLAB = registerBlock("gilded_blackstone_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GILDED_BLACKSTONE.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.GILDED_BLACKSTONE)));

    public static final Block CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB = registerBlock("cracked_polished_blackstone_brick_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block END_STONE_SLAB = registerBlock("end_stone_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.END_STONE.defaultMapColor())
                    .strength(3.0F, 9.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block PURPUR_PILLAR_SLAB = registerBlock("purpur_pillar_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PURPUR_PILLAR.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block QUARTZ_BRICK_SLAB = registerBlock("quartz_brick_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.QUARTZ_BRICKS.defaultMapColor())
                    .strength(0.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block QUARTZ_PILLAR_SLAB = registerBlock("quartz_pillar_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.QUARTZ_PILLAR.defaultMapColor())
                    .strength(0.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block AMETHYST_SLAB = registerBlock("amethyst_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.AMETHYST_BLOCK.defaultMapColor())
                    .strength(1.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)));


    // Wool Slabs
    public static final Block WHITE_WOOL_SLAB = registerBlock("white_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WHITE_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));

    public static final Block LIGHT_GRAY_WOOL_SLAB = registerBlock("light_gray_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIGHT_GRAY_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));

    public static final Block GRAY_WOOL_SLAB = registerBlock("gray_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GRAY_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));

    public static final Block BLACK_WOOL_SLAB = registerBlock("black_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLACK_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));

    public static final Block BROWN_WOOL_SLAB = registerBlock("brown_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BROWN_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));

    public static final Block RED_WOOL_SLAB = registerBlock("red_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.RED_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));

    public static final Block ORANGE_WOOL_SLAB = registerBlock("orange_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.ORANGE_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));

    public static final Block YELLOW_WOOL_SLAB = registerBlock("yellow_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.YELLOW_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));

    public static final Block LIME_WOOL_SLAB = registerBlock("lime_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIME_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));

    public static final Block GREEN_WOOL_SLAB = registerBlock("green_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GREEN_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));

    public static final Block CYAN_WOOL_SLAB = registerBlock("cyan_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CYAN_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));

    public static final Block LIGHT_BLUE_WOOL_SLAB = registerBlock("light_blue_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIGHT_BLUE_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));

    public static final Block BLUE_WOOL_SLAB = registerBlock("blue_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLUE_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));

    public static final Block PURPLE_WOOL_SLAB = registerBlock("purple_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PURPLE_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));

    public static final Block MAGENTA_WOOL_SLAB = registerBlock("magenta_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MAGENTA_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));

    public static final Block PINK_WOOL_SLAB = registerBlock("pink_wool_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PINK_WOOL.defaultMapColor())
                    .instrument(NoteBlockInstrument.GUITAR)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .ignitedByLava()));


    // Terracotta Slabs
    public static final Block TERRACOTTA_SLAB = registerBlock("terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block WHITE_TERRACOTTA_SLAB = registerBlock("white_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WHITE_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block LIGHT_GRAY_TERRACOTTA_SLAB = registerBlock("light_gray_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIGHT_GRAY_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block GRAY_TERRACOTTA_SLAB = registerBlock("gray_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GRAY_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block BLACK_TERRACOTTA_SLAB = registerBlock("black_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLACK_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block BROWN_TERRACOTTA_SLAB = registerBlock("brown_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BROWN_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block RED_TERRACOTTA_SLAB = registerBlock("red_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.RED_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block ORANGE_TERRACOTTA_SLAB = registerBlock("orange_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.ORANGE_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block YELLOW_TERRACOTTA_SLAB = registerBlock("yellow_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.YELLOW_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block LIME_TERRACOTTA_SLAB = registerBlock("lime_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIME_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block GREEN_TERRACOTTA_SLAB = registerBlock("green_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GREEN_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block CYAN_TERRACOTTA_SLAB = registerBlock("cyan_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CYAN_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block LIGHT_BLUE_TERRACOTTA_SLAB = registerBlock("light_blue_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIGHT_BLUE_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block BLUE_TERRACOTTA_SLAB = registerBlock("blue_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLUE_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block PURPLE_TERRACOTTA_SLAB = registerBlock("purple_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PURPLE_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block MAGENTA_TERRACOTTA_SLAB = registerBlock("magenta_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MAGENTA_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block PINK_TERRACOTTA_SLAB = registerBlock("pink_terracotta_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PINK_TERRACOTTA.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));


    // Concrete Slabs
    public static final Block WHITE_CONCRETE_SLAB = registerBlock("white_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WHITE_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block LIGHT_GRAY_CONCRETE_SLAB = registerBlock("light_gray_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIGHT_GRAY_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block GRAY_CONCRETE_SLAB = registerBlock("gray_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GRAY_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block BLACK_CONCRETE_SLAB = registerBlock("black_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLACK_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block BROWN_CONCRETE_SLAB = registerBlock("brown_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BROWN_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block RED_CONCRETE_SLAB = registerBlock("red_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.RED_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block ORANGE_CONCRETE_SLAB = registerBlock("orange_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.ORANGE_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block YELLOW_CONCRETE_SLAB = registerBlock("yellow_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.YELLOW_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block LIME_CONCRETE_SLAB = registerBlock("lime_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIME_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block GREEN_CONCRETE_SLAB = registerBlock("green_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GREEN_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block CYAN_CONCRETE_SLAB = registerBlock("cyan_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CYAN_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block LIGHT_BLUE_CONCRETE_SLAB = registerBlock("light_blue_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIGHT_BLUE_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block BLUE_CONCRETE_SLAB = registerBlock("blue_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLUE_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block PURPLE_CONCRETE_SLAB = registerBlock("purple_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PURPLE_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block MAGENTA_CONCRETE_SLAB = registerBlock("magenta_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MAGENTA_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block PINK_CONCRETE_SLAB = registerBlock("pink_concrete_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PINK_CONCRETE.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));


    // Concrete Powder Slabs
    public static final Block WHITE_CONCRETE_POWDER_SLAB = registerBlock("white_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WHITE_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block LIGHT_GRAY_CONCRETE_POWDER_SLAB = registerBlock("light_gray_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIGHT_GRAY_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block GRAY_CONCRETE_POWDER_SLAB = registerBlock("gray_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GRAY_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block BLACK_CONCRETE_POWDER_SLAB = registerBlock("black_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLACK_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block BROWN_CONCRETE_POWDER_SLAB = registerBlock("brown_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BROWN_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block RED_CONCRETE_POWDER_SLAB = registerBlock("red_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.RED_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block ORANGE_CONCRETE_POWDER_SLAB = registerBlock("orange_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.ORANGE_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block YELLOW_CONCRETE_POWDER_SLAB = registerBlock("yellow_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.YELLOW_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block LIME_CONCRETE_POWDER_SLAB = registerBlock("lime_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIME_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block GREEN_CONCRETE_POWDER_SLAB = registerBlock("green_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GREEN_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block CYAN_CONCRETE_POWDER_SLAB = registerBlock("cyan_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CYAN_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block LIGHT_BLUE_CONCRETE_POWDER_SLAB = registerBlock("light_blue_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIGHT_BLUE_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block BLUE_CONCRETE_POWDER_SLAB = registerBlock("blue_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLUE_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block PURPLE_CONCRETE_POWDER_SLAB = registerBlock("purple_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PURPLE_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block MAGENTA_CONCRETE_POWDER_SLAB = registerBlock("magenta_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MAGENTA_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block PINK_CONCRETE_POWDER_SLAB = registerBlock("pink_concrete_powder_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PINK_CONCRETE_POWDER.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));


    // Glass Slabs
    public static final Block GLASS_SLAB = registerBlock("glass_slab",
            new TransparentSlabBlock(BlockBehaviour.Properties.of()
                    .instrument(NoteBlockInstrument.HAT)
                    .strength(0.3F)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .isValidSpawn(Blocks::never)
                    .isRedstoneConductor(Blocks::never)
                    .isSuffocating(Blocks::never)
                    .isViewBlocking(Blocks::never)));

    public static final Block TINTED_GLASS_SLAB = registerBlock("tinted_glass_slab",
            new TintedGlassSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .instrument(NoteBlockInstrument.HAT)
                    .strength(0.3F)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .isValidSpawn(Blocks::never)
                    .isRedstoneConductor(Blocks::never)
                    .isSuffocating(Blocks::never)
                    .isViewBlocking(Blocks::never)));

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
            new GrassSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GRASS_BLOCK.defaultMapColor())
                    .strength(0.6F)
                    .sound(SoundType.GRASS)));

    public static final Block PODZOL_SLAB = registerBlock("podzol_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PODZOL.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.GRAVEL)));

    public static final Block MYCELIUM_SLAB = registerBlock("mycelium_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MYCELIUM.defaultMapColor())
                    .strength(0.6F)
                    .sound(SoundType.GRASS)));

    public static final Block DIRT_SLAB = registerBlock("dirt_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DIRT.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.GRAVEL)));

    public static final Block DIRT_PATH_SLAB = registerBlock("dirt_path_slab",
            new DirtPathSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DIRT_PATH.defaultMapColor())
                    .strength(0.65F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block COARSE_DIRT_SLAB = registerBlock("coarse_dirt_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.COARSE_DIRT.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.GRAVEL)));

    public static final Block ROOTED_DIRT_SLAB = registerBlock("rooted_dirt_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.ROOTED_DIRT.defaultMapColor())
                    .strength(0.6F)
                    .sound(SoundType.ROOTED_DIRT)));

    public static final Block MUD_SLAB = registerBlock("mud_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MUD.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.MUD)));

    public static final Block CLAY_SLAB = registerBlock("clay_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CLAY.defaultMapColor())
                    .instrument(NoteBlockInstrument.FLUTE)
                    .strength(0.6F)
                    .sound(SoundType.GRAVEL)));

    public static final Block GRAVEL_SLAB = registerBlock("gravel_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GRAVEL.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.GRAVEL)));

    public static final Block SAND_SLAB = registerBlock("sand_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SAND.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block RED_SAND_SLAB = registerBlock("red_sand_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.RED_SAND.defaultMapColor())
                    .instrument(NoteBlockInstrument.SNARE)
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block ICE_SLAB = registerBlock("ice_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.ICE.defaultMapColor())
                    .strength(0.5F)
                    .friction(0.98F)
                    .sound(SoundType.GLASS)));

    public static final Block PACKED_ICE_SLAB = registerBlock("packed_ice_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PACKED_ICE.defaultMapColor())
                    .strength(0.5F)
                    .friction(0.98F)
                    .sound(SoundType.GLASS)));

    public static final Block BLUE_ICE_SLAB = registerBlock("blue_ice_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLUE_ICE.defaultMapColor())
                    .strength(2.8F)
                    .friction(0.989F)
                    .sound(SoundType.GLASS)));

    public static final Block MOSS_SLAB = registerBlock("moss_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MOSS_BLOCK.defaultMapColor())
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(0.6F)
                    .sound(SoundType.MOSS)));

    public static final Block CALCITE_SLAB = registerBlock("calcite_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CALCITE.defaultMapColor())
                    .strength(0.75F)
                    .sound(SoundType.CALCITE)));

    public static final Block DRIPSTONE_SLAB = registerBlock("dripstone_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DRIPSTONE_BLOCK.defaultMapColor())
                    .strength(1.5F)
                    .sound(SoundType.DRIPSTONE_BLOCK)));

    public static final Block MAGMA_BLOCK_SLAB = registerBlock("magma_block_slab",
            new MagmaSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.NETHER)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> 3)
                    .strength(0.5F)
                    .sound(SoundType.STONE)
                    .hasPostProcess(Blocks::always)
                    .emissiveRendering(Blocks::always)));

    public static final Block OBSIDIAN_SLAB = registerBlock("obsidian_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.OBSIDIAN.defaultMapColor())
                    .strength(50.0F, 1200.0F)
                    .sound(SoundType.STONE)));

    public static final Block CRYING_OBSIDIAN_SLAB = registerBlock("crying_obsidian_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CRYING_OBSIDIAN.defaultMapColor())
                    .strength(50.0F, 1200.0F)
                    .sound(SoundType.STONE)
                    .lightLevel((state) -> 10)));

    public static final Block CRIMSON_NYLIUM_SLAB = registerBlock("crimson_nylium_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CRIMSON_NYLIUM.defaultMapColor())
                    .strength(0.4F)
                    .sound(SoundType.NYLIUM)));

    public static final Block WARPED_NYLIUM_SLAB = registerBlock("warped_nylium_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WARPED_NYLIUM.defaultMapColor())
                    .strength(0.4F)
                    .sound(SoundType.NYLIUM)));

    public static final Block SOUL_SAND_SLAB = registerBlock("soul_sand_slab",
            new SoulSandSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SOUL_SAND.defaultMapColor())
                    .instrument(NoteBlockInstrument.COW_BELL)
                    .strength(0.5F)
                    .speedFactor(0.4F)
                    .sound(SoundType.SOUL_SAND)));

    public static final Block SOUL_SOIL_SLAB = registerBlock("soul_soil_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SOUL_SOIL.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SOUL_SOIL)));

    public static final Block BONE_SLAB = registerBlock("bone_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BONE_BLOCK.defaultMapColor())
                    .instrument(NoteBlockInstrument.XYLOPHONE)
                    .strength(2.0F)
                    .sound(SoundType.BONE_BLOCK)));

    public static final Block GLOWSTONE_SLAB = registerBlock("glowstone_slab",
            new GlowingSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.PLING)
                    .strength(0.3F)
                    .sound(SoundType.GLASS)
                    .lightLevel((state) -> 15)
                    .isRedstoneConductor(Blocks::never)));

    public static final Block MANGROVE_ROOT_SLAB = registerBlock("mangrove_root_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MANGROVE_ROOTS.defaultMapColor())
                    .strength(0.7F)
                    .noOcclusion()
                    .sound(SoundType.ROOTS)));

    public static final Block MUDDY_MANGROVE_ROOT_SLAB = registerBlock("muddy_mangrove_root_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MUDDY_MANGROVE_ROOTS.defaultMapColor())
                    .strength(0.7F)
                    .sound(SoundType.ROOTS)));

    public static final Block MUSHROOM_STEM_SLAB = registerBlock("mushroom_stem_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MUSHROOM_STEM.defaultMapColor())
                    .strength(0.2F)
                    .sound(SoundType.WOOD)));

    public static final Block OAK_LEAF_SLAB = registerBlock("oak_leaf_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block SPRUCE_LEAF_SLAB = registerBlock("spruce_leaf_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block BIRCH_LEAF_SLAB = registerBlock("birch_leaf_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block JUNGLE_LEAF_SLAB = registerBlock("jungle_leaf_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block ACACIA_LEAF_SLAB = registerBlock("acacia_leaf_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block DARK_OAK_LEAF_SLAB = registerBlock("dark_oak_leaf_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block MANGROVE_LEAF_SLAB = registerBlock("mangrove_leaf_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block CHERRY_LEAF_SLAB = registerBlock("cherry_leaf_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.CHERRY_LEAVES)
                    .instrument(NoteBlockInstrument.FLUTE)));

    public static final Block AZALEA_LEAF_SLAB = registerBlock("azalea_leaf_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.AZALEA_LEAVES)
                    .instrument(NoteBlockInstrument.FLUTE)));

    public static final Block FLOWERING_AZALEA_LEAF_SLAB = registerBlock("flowering_azalea_leaf_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.AZALEA_LEAVES)
                    .instrument(NoteBlockInstrument.FLUTE)));

    public static final Block BROWN_MUSHROOM_SLAB = registerBlock("brown_mushroom_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BROWN_MUSHROOM_BLOCK.defaultMapColor())
                    .strength(0.2F)
                    .sound(SoundType.WOOD)));

    public static final Block RED_MUSHROOM_SLAB = registerBlock("red_mushroom_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.RED_MUSHROOM_BLOCK.defaultMapColor())
                    .strength(0.2F)
                    .sound(SoundType.WOOD)));

    public static final Block NETHER_WART_SLAB = registerBlock("nether_wart_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.NETHER_WART_BLOCK.defaultMapColor())
                    .strength(1.0F)
                    .sound(SoundType.WART_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block WARPED_WART_SLAB = registerBlock("warped_wart_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WARPED_WART_BLOCK.defaultMapColor())
                    .strength(1.0F)
                    .sound(SoundType.WART_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block DRIED_KELP_SLAB = registerBlock("dried_kelp_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DRIED_KELP_BLOCK.defaultMapColor())
                    .strength(0.5F, 2.5F)
                    .sound(SoundType.GRASS)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block TUBE_CORAL_SLAB = registerBlock("tube_coral_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.TUBE_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block BRAIN_CORAL_SLAB = registerBlock("brain_coral_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BRAIN_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block BUBBLE_CORAL_SLAB = registerBlock("bubble_coral_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BUBBLE_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block FIRE_CORAL_SLAB = registerBlock("fire_coral_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.FIRE_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block HORN_CORAL_SLAB = registerBlock("horn_coral_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.HORN_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block DEAD_TUBE_CORAL_SLAB = registerBlock("dead_tube_coral_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DEAD_TUBE_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)));

    public static final Block DEAD_BRAIN_CORAL_SLAB = registerBlock("dead_brain_coral_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DEAD_BRAIN_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)));

    public static final Block DEAD_BUBBLE_CORAL_SLAB = registerBlock("dead_bubble_coral_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DEAD_BUBBLE_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)));

    public static final Block DEAD_FIRE_CORAL_SLAB = registerBlock("dead_fire_coral_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DEAD_FIRE_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)));

    public static final Block DEAD_HORN_CORAL_SLAB = registerBlock("dead_horn_coral_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DEAD_HORN_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)));

    public static final Block SPONGE_SLAB = registerBlock("sponge_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SPONGE.defaultMapColor())
                    .strength(0.6F)
                    .sound(SoundType.SPONGE)));

    public static final Block WET_SPONGE_SLAB = registerBlock("wet_sponge_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WET_SPONGE.defaultMapColor())
                    .strength(0.6F)
                    .sound(SoundType.WET_SPONGE)));

    public static final Block MELON_SLAB = registerBlock("melon_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MELON.defaultMapColor())
                    .strength(1.0F)
                    .sound(SoundType.WOOD)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block PUMPKIN_SLAB = registerBlock("pumpkin_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PUMPKIN.defaultMapColor())
                    .strength(1.0F)
                    .sound(SoundType.WOOD)
                    .instrument(NoteBlockInstrument.BASS)));

    /*public static final Block CARVED_PUMPKIN_SLAB = registerBlock("carved_pumpkin_slab",
            new SlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CARVED_PUMPKIN.getDefaultMapColor())
                    .strength(1.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .instrument(NoteBlockInstrument.BASS)));*/

    public static final Block HAY_SLAB = registerBlock("hay_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.HAY_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.GRASS)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block HONEYCOMB_SLAB = registerBlock("honeycomb_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.HONEYCOMB_BLOCK.defaultMapColor())
                    .strength(0.6F)
                    .sound(SoundType.CORAL_BLOCK)));

    public static final Block SCULK_SLAB = registerBlock("sculk_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SCULK.defaultMapColor())
                    .strength(0.8F)
                    .sound(SoundType.SCULK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block BEDROCK_SLAB = registerBlock("bedrock_slab",
            new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BEDROCK.defaultMapColor())
                    .strength(-1.0F, 3600000.0F)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .noLootTable()
                    .isValidSpawn(Blocks::never)));


    ///////////////////////
    //   Vertical Slab   //
    ///////////////////////

    // Planks Vertical Slabs
    public static final Block OAK_VERTICAL_SLAB = registerBlock("oak_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.OAK_PLANKS.defaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));

    public static final Block SPRUCE_VERTICAL_SLAB = registerBlock("spruce_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SPRUCE_PLANKS.defaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));

    public static final Block BIRCH_VERTICAL_SLAB = registerBlock("birch_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BIRCH_PLANKS.defaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));

    public static final Block JUNGLE_VERTICAL_SLAB = registerBlock("jungle_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.JUNGLE_PLANKS.defaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));

    public static final Block ACACIA_VERTICAL_SLAB = registerBlock("acacia_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.ACACIA_PLANKS.defaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));

    public static final Block DARK_OAK_VERTICAL_SLAB = registerBlock("dark_oak_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DARK_OAK_PLANKS.defaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));

    public static final Block MANGROVE_VERTICAL_SLAB = registerBlock("mangrove_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MANGROVE_PLANKS.defaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));

    public static final Block CHERRY_VERTICAL_SLAB = registerBlock("cherry_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CHERRY_PLANKS.defaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.CHERRY_WOOD)));

    public static final Block BAMBOO_VERTICAL_SLAB = registerBlock("bamboo_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BAMBOO_PLANKS.defaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.BAMBOO_WOOD)));

    public static final Block BAMBOO_MOSAIC_VERTICAL_SLAB = registerBlock("bamboo_mosaic_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BAMBOO_MOSAIC.defaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.BAMBOO_WOOD)));

    public static final Block CRIMSON_VERTICAL_SLAB = registerBlock("crimson_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CRIMSON_PLANKS.defaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)));

    public static final Block WARPED_VERTICAL_SLAB = registerBlock("warped_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WARPED_PLANKS.defaultMapColor())
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.NETHER_WOOD)));


    // Log Slabs
    public static final Block OAK_LOG_VERTICAL_SLAB = registerBlock("oak_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block SPRUCE_LOG_VERTICAL_SLAB = registerBlock("spruce_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block BIRCH_LOG_VERTICAL_SLAB = registerBlock("birch_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block JUNGLE_LOG_VERTICAL_SLAB = registerBlock("jungle_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIRT)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block ACACIA_LOG_VERTICAL_SLAB = registerBlock("acacia_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block DARK_OAK_LOG_VERTICAL_SLAB = registerBlock("dark_oak_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block MANGROVE_LOG_VERTICAL_SLAB = registerBlock("mangrove_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block CHERRY_LOG_VERTICAL_SLAB = registerBlock("cherry_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.CHERRY_WOOD)
                    .ignitedByLava()));

    public static final Block BAMBOO_BLOCK_VERTICAL_SLAB = registerBlock("bamboo_block_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .strength(2.0F)
                    .sound(SoundType.BAMBOO_WOOD)
                    .ignitedByLava()));

    public static final Block CRIMSON_STEM_VERTICAL_SLAB = registerBlock("crimson_stem_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.CRIMSON_STEM)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));

    public static final Block WARPED_STEM_VERTICAL_SLAB = registerBlock("warped_stem_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WARPED_STEM)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));


    // Wood Slabs
    public static final Block OAK_WOOD_VERTICAL_SLAB = registerBlock("oak_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block SPRUCE_WOOD_VERTICAL_SLAB = registerBlock("spruce_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block BIRCH_WOOD_VERTICAL_SLAB = registerBlock("birch_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block JUNGLE_WOOD_VERTICAL_SLAB = registerBlock("jungle_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIRT)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block ACACIA_WOOD_VERTICAL_SLAB = registerBlock("acacia_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block DARK_OAK_WOOD_VERTICAL_SLAB = registerBlock("dark_oak_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block MANGROVE_WOOD_VERTICAL_SLAB = registerBlock("mangrove_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block CHERRY_WOOD_VERTICAL_SLAB = registerBlock("cherry_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_GRAY)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.CHERRY_WOOD)
                    .ignitedByLava()));

    public static final Block CRIMSON_HYPHAE_VERTICAL_SLAB = registerBlock("crimson_hyphae_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.CRIMSON_HYPHAE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));

    public static final Block WARPED_HYPHAE_VERTICAL_SLAB = registerBlock("warped_hyphae_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WARPED_HYPHAE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));


    // Stripped Log Slabs
    public static final Block STRIPPED_OAK_LOG_VERTICAL_SLAB = registerBlock("stripped_oak_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_SPRUCE_LOG_VERTICAL_SLAB = registerBlock("stripped_spruce_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_BIRCH_LOG_VERTICAL_SLAB = registerBlock("stripped_birch_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_JUNGLE_LOG_VERTICAL_SLAB = registerBlock("stripped_jungle_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIRT)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_ACACIA_LOG_VERTICAL_SLAB = registerBlock("stripped_acacia_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_DARK_OAK_LOG_VERTICAL_SLAB = registerBlock("stripped_dark_oak_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_MANGROVE_LOG_VERTICAL_SLAB = registerBlock("stripped_mangrove_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_CHERRY_LOG_VERTICAL_SLAB = registerBlock("stripped_cherry_log_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.CHERRY_WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_BAMBOO_VERTICAL_SLAB = registerBlock("stripped_bamboo_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.BAMBOO_WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_CRIMSON_STEM_VERTICAL_SLAB = registerBlock("stripped_crimson_stem_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.CRIMSON_STEM)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));

    public static final Block STRIPPED_WARPED_STEM_VERTICAL_SLAB = registerBlock("stripped_warped_stem_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WARPED_STEM)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));


    // Stripped Wood Slabs
    public static final Block STRIPPED_OAK_WOOD_VERTICAL_SLAB = registerBlock("stripped_oak_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_SPRUCE_WOOD_VERTICAL_SLAB = registerBlock("stripped_spruce_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_BIRCH_WOOD_VERTICAL_SLAB = registerBlock("stripped_birch_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_JUNGLE_WOOD_VERTICAL_SLAB = registerBlock("stripped_jungle_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIRT)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_ACACIA_WOOD_VERTICAL_SLAB = registerBlock("stripped_acacia_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_DARK_OAK_WOOD_VERTICAL_SLAB = registerBlock("stripped_dark_oak_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_MANGROVE_WOOD_VERTICAL_SLAB = registerBlock("stripped_mangrove_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_CHERRY_WOOD_VERTICAL_SLAB = registerBlock("stripped_cherry_wood_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_PINK)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.CHERRY_WOOD)
                    .ignitedByLava()));

    public static final Block STRIPPED_CRIMSON_HYPHAE_VERTICAL_SLAB = registerBlock("stripped_crimson_hyphae_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.CRIMSON_HYPHAE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));

    public static final Block STRIPPED_WARPED_HYPHAE_VERTICAL_SLAB = registerBlock("stripped_warped_hyphae_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WARPED_HYPHAE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.STEM)));


    // Stone-related Vertical Slabs
    public static final Block STONE_VERTICAL_SLAB = registerBlock("stone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.STONE.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block COBBLESTONE_VERTICAL_SLAB = registerBlock("cobblestone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.COBBLESTONE.defaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block MOSSY_COBBLESTONE_VERTICAL_SLAB = registerBlock("mossy_cobblestone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MOSSY_COBBLESTONE.defaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block SMOOTH_STONE_VERTICAL_SLAB = registerBlock("smooth_stone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SMOOTH_STONE.defaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block STONE_BRICK_VERTICAL_SLAB = registerBlock("stone_brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.STONE_BRICKS.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block CRACKED_STONE_BRICK_VERTICAL_SLAB = registerBlock("cracked_stone_brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CRACKED_STONE_BRICKS.defaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block MOSSY_STONE_BRICK_VERTICAL_SLAB = registerBlock("mossy_stone_brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MOSSY_STONE_BRICKS.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block GRANITE_VERTICAL_SLAB = registerBlock("granite_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GRANITE.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block POLISHED_GRANITE_VERTICAL_SLAB = registerBlock("polished_granite_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.POLISHED_GRANITE.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block DIORITE_VERTICAL_SLAB = registerBlock("diorite_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DIORITE.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block POLISHED_DIORITE_VERTICAL_SLAB = registerBlock("polished_diorite_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.POLISHED_DIORITE.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block ANDESITE_VERTICAL_SLAB = registerBlock("andesite_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.ANDESITE.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block POLISHED_ANDESITE_VERTICAL_SLAB = registerBlock("polished_andesite_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.POLISHED_ANDESITE.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block DEEPSLATE_VERTICAL_SLAB = registerBlock("deepslate_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DEEPSLATE.defaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)));

    public static final Block COBBLED_DEEPSLATE_VERTICAL_SLAB = registerBlock("cobbled_deepslate_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.COBBLED_DEEPSLATE.defaultMapColor())
                    .strength(3.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)));

    public static final Block POLISHED_DEEPSLATE_VERTICAL_SLAB = registerBlock("polished_deepslate_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.POLISHED_DEEPSLATE.defaultMapColor())
                    .strength(3.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)));

    public static final Block DEEPSLATE_BRICK_VERTICAL_SLAB = registerBlock("deepslate_brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DEEPSLATE_BRICKS.defaultMapColor())
                    .strength(3.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)));

    public static final Block CRACKED_DEEPSLATE_BRICK_VERTICAL_SLAB = registerBlock("cracked_deepslate_brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CRACKED_DEEPSLATE_BRICKS.defaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE_BRICKS)));

    public static final Block DEEPSLATE_TILE_VERTICAL_SLAB = registerBlock("deepslate_tile_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DEEPSLATE_TILES.defaultMapColor())
                    .strength(3.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)));

    public static final Block REINFORCED_DEEPSLATE_VERTICAL_SLAB = registerBlock("reinforced_deepslate_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.REINFORCED_DEEPSLATE.defaultMapColor())
                    .strength(55.0F, 1200.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)));

    public static final Block TUFF_VERTICAL_SLAB = registerBlock("tuff_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.TUFF.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.TUFF)
                    .instrument(NoteBlockInstrument.BASEDRUM)));

    public static final Block POLISHED_TUFF_VERTICAL_SLAB = registerBlock("polished_tuff_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.POLISHED_TUFF.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.POLISHED_TUFF)
                    .instrument(NoteBlockInstrument.BASEDRUM)));

    public static final Block TUFF_BRICK_VERTICAL_SLAB = registerBlock("tuff_brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.TUFF_BRICKS.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.TUFF_BRICKS)
                    .instrument(NoteBlockInstrument.BASEDRUM)));

    public static final Block BRICK_VERTICAL_SLAB = registerBlock("brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BRICKS.defaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block PACKED_MUD_VERTICAL_SLAB = registerBlock("packed_mud_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PACKED_MUD.defaultMapColor())
                    .strength(1.5F)
                    .sound(SoundType.PACKED_MUD)));

    public static final Block MUD_BRICK_VERTICAL_SLAB = registerBlock("mud_brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MUD_BRICKS.defaultMapColor())
                    .strength(1.5F, 3.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.MUD_BRICKS)));

    public static final Block SANDSTONE_VERTICAL_SLAB = registerBlock("sandstone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SANDSTONE.defaultMapColor())
                    .strength(0.8F, 0.8F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.SAND)));

    public static final Block SMOOTH_SANDSTONE_VERTICAL_SLAB = registerBlock("smooth_sandstone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SMOOTH_SANDSTONE.defaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block CUT_SANDSTONE_VERTICAL_SLAB = registerBlock("cut_sandstone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CUT_SANDSTONE.defaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block RED_SANDSTONE_VERTICAL_SLAB = registerBlock("red_sandstone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.RED_SANDSTONE.defaultMapColor())
                    .strength(0.8F, 0.8F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.SAND)));

    public static final Block SMOOTH_RED_SANDSTONE_VERTICAL_SLAB = registerBlock("smooth_red_sandstone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SMOOTH_RED_SANDSTONE.defaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block CUT_RED_SANDSTONE_VERTICAL_SLAB = registerBlock("cut_red_sandstone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CUT_RED_SANDSTONE.defaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block PRISMARINE_VERTICAL_SLAB = registerBlock("prismarine_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PRISMARINE.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block PRISMARINE_BRICK_VERTICAL_SLAB = registerBlock("prismarine_brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PRISMARINE_BRICKS.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block DARK_PRISMARINE_VERTICAL_SLAB = registerBlock("dark_prismarine_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DARK_PRISMARINE.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block NETHERRACK_VERTICAL_SLAB = registerBlock("netherrack_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.NETHERRACK.defaultMapColor())
                    .strength(0.4F)
                    .sound(SoundType.NETHERRACK)));

    public static final Block NETHER_BRICK_VERTICAL_SLAB = registerBlock("nether_brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.NETHER_BRICKS.defaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.NETHER_BRICKS)));

    public static final Block CRACKED_NETHER_BRICK_VERTICAL_SLAB = registerBlock("cracked_nether_brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CRACKED_NETHER_BRICKS.defaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.NETHER_BRICKS)));

    public static final Block RED_NETHER_BRICK_VERTICAL_SLAB = registerBlock("red_nether_brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.RED_NETHER_BRICKS.defaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.NETHER_BRICKS)));

    public static final Block BASALT_VERTICAL_SLAB = registerBlock("basalt_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BASALT.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.BASALT)));

    public static final Block SMOOTH_BASALT_VERTICAL_SLAB = registerBlock("smooth_basalt_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SMOOTH_BASALT.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.BASALT)));

    public static final Block POLISHED_BASALT_VERTICAL_SLAB = registerBlock("polished_basalt_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.POLISHED_BASALT.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.BASALT)));

    public static final Block BLACKSTONE_VERTICAL_SLAB = registerBlock("blackstone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLACKSTONE.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block GILDED_BLACKSTONE_VERTICAL_SLAB = registerBlock("gilded_blackstone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GILDED_BLACKSTONE.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.GILDED_BLACKSTONE)));

    public static final Block POLISHED_BLACKSTONE_VERTICAL_SLAB = registerBlock("polished_blackstone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.POLISHED_BLACKSTONE.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB = registerBlock("polished_blackstone_brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.POLISHED_BLACKSTONE_BRICKS.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block CRACKED_POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB = registerBlock("cracked_polished_blackstone_brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block END_STONE_VERTICAL_SLAB = registerBlock("end_stone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.END_STONE.defaultMapColor())
                    .strength(3.0F, 9.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block END_STONE_BRICK_VERTICAL_SLAB = registerBlock("end_stone_brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.END_STONE_BRICKS.defaultMapColor())
                    .strength(3.0F, 9.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block PURPUR_VERTICAL_SLAB = registerBlock("purpur_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PURPUR_BLOCK.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block PURPUR_PILLAR_VERTICAL_SLAB = registerBlock("purpur_pillar_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PURPUR_PILLAR.defaultMapColor())
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block QUARTZ_VERTICAL_SLAB = registerBlock("quartz_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.QUARTZ_BLOCK.defaultMapColor())
                    .strength(0.8F, 0.8F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block QUARTZ_BRICK_VERTICAL_SLAB = registerBlock("quartz_brick_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.QUARTZ_BRICKS.defaultMapColor())
                    .strength(0.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block QUARTZ_PILLAR_VERTICAL_SLAB = registerBlock("quartz_pillar_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.QUARTZ_PILLAR.defaultMapColor())
                    .strength(0.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block SMOOTH_QUARTZ_VERTICAL_SLAB = registerBlock("smooth_quartz_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SMOOTH_QUARTZ.defaultMapColor())
                    .strength(2.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block AMETHYST_VERTICAL_SLAB = registerBlock("amethyst_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.AMETHYST_BLOCK.defaultMapColor())
                    .strength(1.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)));

    public static final Block CUT_COPPER_VERTICAL_SLAB = registerBlock("cut_copper_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CUT_COPPER.defaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.COPPER)));

    public static final Block EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerBlock("exposed_cut_copper_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.EXPOSED_CUT_COPPER.defaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.COPPER)));

    public static final Block WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerBlock("weathered_cut_copper_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WEATHERED_CUT_COPPER.defaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.COPPER)));

    public static final Block OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerBlock("oxidized_cut_copper_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.OXIDIZED_CUT_COPPER.defaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.COPPER)));

    public static final Block WAXED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_cut_copper_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WAXED_CUT_COPPER.defaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.COPPER)));

    public static final Block WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_exposed_cut_copper_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WAXED_EXPOSED_CUT_COPPER.defaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.COPPER)));

    public static final Block WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_weathered_cut_copper_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WAXED_WEATHERED_CUT_COPPER.defaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.COPPER)));

    public static final Block WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerBlock("waxed_oxidized_cut_copper_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WAXED_OXIDIZED_CUT_COPPER.defaultMapColor())
                    .strength(3.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.COPPER)));

    public static final Block PETRIFIED_OAK_VERTICAL_SLAB = registerBlock("petrified_oak_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PETRIFIED_OAK_SLAB.defaultMapColor())
                    .strength(2.0F, 3.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.WOOD)));


    // Wool Vertical Slabs
    public static final Block WHITE_WOOL_VERTICAL_SLAB = registerBlock("white_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));

    public static final Block LIGHT_GRAY_WOOL_VERTICAL_SLAB = registerBlock("light_gray_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));

    public static final Block GRAY_WOOL_VERTICAL_SLAB = registerBlock("gray_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));

    public static final Block BLACK_WOOL_VERTICAL_SLAB = registerBlock("black_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));

    public static final Block BROWN_WOOL_VERTICAL_SLAB = registerBlock("brown_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));

    public static final Block RED_WOOL_VERTICAL_SLAB = registerBlock("red_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));

    public static final Block ORANGE_WOOL_VERTICAL_SLAB = registerBlock("orange_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));

    public static final Block YELLOW_WOOL_VERTICAL_SLAB = registerBlock("yellow_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));

    public static final Block LIME_WOOL_VERTICAL_SLAB = registerBlock("lime_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GREEN)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));

    public static final Block GREEN_WOOL_VERTICAL_SLAB = registerBlock("green_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));

    public static final Block CYAN_WOOL_VERTICAL_SLAB = registerBlock("cyan_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));

    public static final Block LIGHT_BLUE_WOOL_VERTICAL_SLAB = registerBlock("light_blue_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));

    public static final Block BLUE_WOOL_VERTICAL_SLAB = registerBlock("blue_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLUE)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));

    public static final Block PURPLE_WOOL_VERTICAL_SLAB = registerBlock("purple_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));

    public static final Block MAGENTA_WOOL_VERTICAL_SLAB = registerBlock("magenta_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_MAGENTA)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));

    public static final Block PINK_WOOL_VERTICAL_SLAB = registerBlock("pink_wool_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .strength(0.8F)
                    .sound(SoundType.WOOL)
                    .instrument(NoteBlockInstrument.GUITAR)
                    .ignitedByLava()));


    // Terracotta Vertical Slabs
    public static final Block TERRACOTTA_VERTICAL_SLAB = registerBlock("terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block WHITE_TERRACOTTA_VERTICAL_SLAB = registerBlock("white_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WHITE_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block LIGHT_GRAY_TERRACOTTA_VERTICAL_SLAB = registerBlock("light_gray_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIGHT_GRAY_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block GRAY_TERRACOTTA_VERTICAL_SLAB = registerBlock("gray_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GRAY_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block BLACK_TERRACOTTA_VERTICAL_SLAB = registerBlock("black_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLACK_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block BROWN_TERRACOTTA_VERTICAL_SLAB = registerBlock("brown_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BROWN_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block RED_TERRACOTTA_VERTICAL_SLAB = registerBlock("red_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.RED_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block ORANGE_TERRACOTTA_VERTICAL_SLAB = registerBlock("orange_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.ORANGE_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block YELLOW_TERRACOTTA_VERTICAL_SLAB = registerBlock("yellow_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.YELLOW_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block LIME_TERRACOTTA_VERTICAL_SLAB = registerBlock("lime_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIME_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block GREEN_TERRACOTTA_VERTICAL_SLAB = registerBlock("green_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GREEN_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block CYAN_TERRACOTTA_VERTICAL_SLAB = registerBlock("cyan_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CYAN_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block LIGHT_BLUE_TERRACOTTA_VERTICAL_SLAB = registerBlock("light_blue_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIGHT_BLUE_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block BLUE_TERRACOTTA_VERTICAL_SLAB = registerBlock("blue_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLUE_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block PURPLE_TERRACOTTA_VERTICAL_SLAB = registerBlock("purple_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PURPLE_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block MAGENTA_TERRACOTTA_VERTICAL_SLAB = registerBlock("magenta_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MAGENTA_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));

    public static final Block PINK_TERRACOTTA_VERTICAL_SLAB = registerBlock("pink_terracotta_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PINK_TERRACOTTA.defaultMapColor())
                    .strength(1.25F, 4.2F)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.STONE)));


    // Concrete Vertical Slabs
    public static final Block WHITE_CONCRETE_VERTICAL_SLAB = registerBlock("white_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WHITE_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block LIGHT_GRAY_CONCRETE_VERTICAL_SLAB = registerBlock("light_gray_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIGHT_GRAY_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block GRAY_CONCRETE_VERTICAL_SLAB = registerBlock("gray_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GRAY_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block BLACK_CONCRETE_VERTICAL_SLAB = registerBlock("black_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLACK_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block BROWN_CONCRETE_VERTICAL_SLAB = registerBlock("brown_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BROWN_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block RED_CONCRETE_VERTICAL_SLAB = registerBlock("red_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.RED_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block ORANGE_CONCRETE_VERTICAL_SLAB = registerBlock("orange_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.ORANGE_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block YELLOW_CONCRETE_VERTICAL_SLAB = registerBlock("yellow_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.YELLOW_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block LIME_CONCRETE_VERTICAL_SLAB = registerBlock("lime_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIME_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block GREEN_CONCRETE_VERTICAL_SLAB = registerBlock("green_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GREEN_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block CYAN_CONCRETE_VERTICAL_SLAB = registerBlock("cyan_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CYAN_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block LIGHT_BLUE_CONCRETE_VERTICAL_SLAB = registerBlock("light_blue_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIGHT_BLUE_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block BLUE_CONCRETE_VERTICAL_SLAB = registerBlock("blue_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLUE_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block PURPLE_CONCRETE_VERTICAL_SLAB = registerBlock("purple_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PURPLE_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block MAGENTA_CONCRETE_VERTICAL_SLAB = registerBlock("magenta_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MAGENTA_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block PINK_CONCRETE_VERTICAL_SLAB = registerBlock("pink_concrete_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PINK_CONCRETE.defaultMapColor())
                    .strength(1.8F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));


    // Concrete Powder Vertical Slabs
    public static final Block WHITE_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("white_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WHITE_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block LIGHT_GRAY_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("light_gray_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIGHT_GRAY_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block GRAY_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("gray_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GRAY_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block BLACK_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("black_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLACK_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block BROWN_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("brown_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BROWN_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block RED_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("red_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.RED_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block ORANGE_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("orange_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.ORANGE_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block YELLOW_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("yellow_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.YELLOW_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block LIME_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("lime_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIME_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block GREEN_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("green_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GREEN_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block CYAN_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("cyan_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CYAN_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block LIGHT_BLUE_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("light_blue_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.LIGHT_BLUE_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block BLUE_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("blue_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLUE_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block PURPLE_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("purple_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PURPLE_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block MAGENTA_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("magenta_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MAGENTA_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));

    public static final Block PINK_CONCRETE_POWDER_VERTICAL_SLAB = registerBlock("pink_concrete_powder_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PINK_CONCRETE_POWDER.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)));


    // Glass Vertical Slabs
    public static final Block GLASS_VERTICAL_SLAB = registerBlock("glass_vertical_slab",
            new TransparentVerticalSlabBlock(BlockBehaviour.Properties.of()
                    .instrument(NoteBlockInstrument.HAT)
                    .strength(0.3F)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .isValidSpawn(Blocks::never)
                    .isRedstoneConductor(Blocks::never)
                    .isSuffocating(Blocks::never)
                    .isViewBlocking(Blocks::never)));

    public static final Block TINTED_GLASS_VERTICAL_SLAB = registerBlock("tinted_glass_vertical_slab",
            new TintedGlassVerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .instrument(NoteBlockInstrument.HAT)
                    .strength(0.3F)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .isValidSpawn(Blocks::never)
                    .isRedstoneConductor(Blocks::never)
                    .isSuffocating(Blocks::never)
                    .isViewBlocking(Blocks::never)));

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
            new GrassVerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GRASS_BLOCK.defaultMapColor())
                    .strength(0.6F)
                    .sound(SoundType.GRASS)));

    public static final Block PODZOL_VERTICAL_SLAB = registerBlock("podzol_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PODZOL.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.GRAVEL)));

    public static final Block MYCELIUM_VERTICAL_SLAB = registerBlock("mycelium_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MYCELIUM.defaultMapColor())
                    .strength(0.6F)
                    .sound(SoundType.GRASS)));

    public static final Block DIRT_VERTICAL_SLAB = registerBlock("dirt_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DIRT.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.GRAVEL)));

    public static final Block DIRT_PATH_VERTICAL_SLAB = registerBlock("dirt_path_vertical_slab",
            new DirtPathVerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DIRT_PATH.defaultMapColor())
                    .strength(0.65F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block COARSE_DIRT_VERTICAL_SLAB = registerBlock("coarse_dirt_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.COARSE_DIRT.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.GRAVEL)));

    public static final Block ROOTED_DIRT_VERTICAL_SLAB = registerBlock("rooted_dirt_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.ROOTED_DIRT.defaultMapColor())
                    .strength(0.6F)
                    .sound(SoundType.ROOTED_DIRT)));

    public static final Block MUD_VERTICAL_SLAB = registerBlock("mud_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MUD.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.MUD)));

    public static final Block CLAY_VERTICAL_SLAB = registerBlock("clay_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CLAY.defaultMapColor())
                    .strength(0.6F)
                    .sound(SoundType.GRAVEL)
                    .instrument(NoteBlockInstrument.FLUTE)));

    public static final Block GRAVEL_VERTICAL_SLAB = registerBlock("gravel_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.GRAVEL.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.GRAVEL)));

    public static final Block SAND_VERTICAL_SLAB = registerBlock("sand_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SAND.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)
                    .instrument(NoteBlockInstrument.SNARE)));

    public static final Block RED_SAND_VERTICAL_SLAB = registerBlock("red_sand_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.RED_SAND.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SAND)
                    .instrument(NoteBlockInstrument.SNARE)));

    public static final Block ICE_VERTICAL_SLAB = registerBlock("ice_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.ICE.defaultMapColor())
                    .strength(0.5F)
                    .friction(0.98F)
                    .sound(SoundType.GLASS)));

    public static final Block PACKED_ICE_VERTICAL_SLAB = registerBlock("packed_ice_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PACKED_ICE.defaultMapColor())
                    .strength(0.5F)
                    .friction(0.98F)
                    .sound(SoundType.GLASS)));

    public static final Block BLUE_ICE_VERTICAL_SLAB = registerBlock("blue_ice_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BLUE_ICE.defaultMapColor())
                    .strength(2.8F)
                    .friction(0.989F)
                    .sound(SoundType.GLASS)));

    public static final Block MOSS_VERTICAL_SLAB = registerBlock("moss_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MOSS_BLOCK.defaultMapColor())
                    .strength(0.6F)
                    .sound(SoundType.MOSS)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block CALCITE_VERTICAL_SLAB = registerBlock("calcite_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CALCITE.defaultMapColor())
                    .strength(0.75F)
                    .sound(SoundType.CALCITE)));

    public static final Block DRIPSTONE_VERTICAL_SLAB = registerBlock("dripstone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DRIPSTONE_BLOCK.defaultMapColor())
                    .strength(1.5F)
                    .sound(SoundType.DRIPSTONE_BLOCK)));

    public static final Block MAGMA_BLOCK_VERTICAL_SLAB = registerBlock("magma_block_vertical_slab",
            new MagmaVerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.NETHER)
                    .strength(0.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .lightLevel((state) -> 3)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .isValidSpawn((state, world, pos, entityType) -> entityType.fireImmune())
                    .hasPostProcess(Blocks::always)
                    .emissiveRendering(Blocks::always)));

    public static final Block OBSIDIAN_VERTICAL_SLAB = registerBlock("obsidian_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.OBSIDIAN.defaultMapColor())
                    .strength(50.0F, 1200.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final Block CRYING_OBSIDIAN_VERTICAL_SLAB = registerBlock("crying_obsidian_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CRYING_OBSIDIAN.defaultMapColor())
                    .strength(50.0F, 1200.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .lightLevel((state) -> 10)));

    public static final Block CRIMSON_NYLIUM_VERTICAL_SLAB = registerBlock("crimson_nylium_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CRIMSON_NYLIUM.defaultMapColor())
                    .strength(0.4F)
                    .sound(SoundType.NYLIUM)));

    public static final Block WARPED_NYLIUM_VERTICAL_SLAB = registerBlock("warped_nylium_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WARPED_NYLIUM.defaultMapColor())
                    .strength(0.4F)
                    .sound(SoundType.NYLIUM)));

    public static final Block SOUL_SAND_VERTICAL_SLAB = registerBlock("soul_sand_vertical_slab",
            new SoulSandVerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SOUL_SAND.defaultMapColor())
                    .strength(0.5F)
                    .speedFactor(0.4F)
                    .sound(SoundType.SOUL_SAND)
                    .instrument(NoteBlockInstrument.COW_BELL)));

    public static final Block SOUL_SOIL_VERTICAL_SLAB = registerBlock("soul_soil_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SOUL_SOIL.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.SOUL_SOIL)));

    public static final Block BONE_VERTICAL_SLAB = registerBlock("bone_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BONE_BLOCK.defaultMapColor())
                    .strength(2.0F)
                    .sound(SoundType.BONE_BLOCK)
                    .instrument(NoteBlockInstrument.XYLOPHONE)));

    public static final Block GLOWSTONE_VERTICAL_SLAB = registerBlock("glowstone_vertical_slab",
            new GlowingVerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.PLING)
                    .strength(0.3F)
                    .sound(SoundType.GLASS)
                    .lightLevel((state) -> 15)
                    .isRedstoneConductor(Blocks::never)));

    public static final Block MANGROVE_ROOT_VERTICAL_SLAB = registerBlock("mangrove_root_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MANGROVE_ROOTS.defaultMapColor())
                    .strength(0.7F)
                    .noOcclusion()
                    .sound(SoundType.ROOTS)));

    public static final Block MUDDY_MANGROVE_ROOT_VERTICAL_SLAB = registerBlock("muddy_mangrove_root_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MUDDY_MANGROVE_ROOTS.defaultMapColor())
                    .strength(0.7F)
                    .sound(SoundType.ROOTS)));

    public static final Block MUSHROOM_STEM_VERTICAL_SLAB = registerBlock("mushroom_stem_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MUSHROOM_STEM.defaultMapColor())
                    .strength(0.2F)
                    .sound(SoundType.WOOD)));

    public static final Block OAK_LEAF_VERTICAL_SLAB = registerBlock("oak_leaf_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.OAK_LEAVES.defaultMapColor())
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block SPRUCE_LEAF_VERTICAL_SLAB = registerBlock("spruce_leaf_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SPRUCE_LEAVES.defaultMapColor())
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block BIRCH_LEAF_VERTICAL_SLAB = registerBlock("birch_leaf_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BIRCH_LEAVES.defaultMapColor())
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block JUNGLE_LEAF_VERTICAL_SLAB = registerBlock("jungle_leaf_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.JUNGLE_LEAVES.defaultMapColor())
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block ACACIA_LEAF_VERTICAL_SLAB = registerBlock("acacia_leaf_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.ACACIA_LEAVES.defaultMapColor())
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block DARK_OAK_LEAF_VERTICAL_SLAB = registerBlock("dark_oak_leaf_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DARK_OAK_LEAVES.defaultMapColor())
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block MANGROVE_LEAF_VERTICAL_SLAB = registerBlock("mangrove_leaf_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MANGROVE_LEAVES.defaultMapColor())
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block CHERRY_LEAF_VERTICAL_SLAB = registerBlock("cherry_leaf_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CHERRY_LEAVES.defaultMapColor())
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block AZALEA_LEAF_VERTICAL_SLAB = registerBlock("azalea_leaf_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.AZALEA_LEAVES.defaultMapColor())
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block FLOWERING_AZALEA_LEAF_VERTICAL_SLAB = registerBlock("flowering_azalea_leaf_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.FLOWERING_AZALEA_LEAVES.defaultMapColor())
                    .strength(0.2F)
                    .noOcclusion()
                    .sound(SoundType.GRASS)));

    public static final Block BROWN_MUSHROOM_VERTICAL_SLAB = registerBlock("brown_mushroom_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BROWN_MUSHROOM_BLOCK.defaultMapColor())
                    .strength(0.2F)
                    .sound(SoundType.WOOD)));

    public static final Block RED_MUSHROOM_VERTICAL_SLAB = registerBlock("red_mushroom_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.RED_MUSHROOM_BLOCK.defaultMapColor())
                    .strength(0.2F)
                    .sound(SoundType.WOOD)));

    public static final Block NETHER_WART_VERTICAL_SLAB = registerBlock("nether_wart_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.NETHER_WART_BLOCK.defaultMapColor())
                    .strength(1.0F)
                    .sound(SoundType.WART_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block WARPED_WART_VERTICAL_SLAB = registerBlock("warped_wart_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WARPED_WART_BLOCK.defaultMapColor())
                    .strength(1.0F)
                    .sound(SoundType.WART_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block DRIED_KELP_VERTICAL_SLAB = registerBlock("dried_kelp_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DRIED_KELP_BLOCK.defaultMapColor())
                    .strength(0.5F, 2.5F)
                    .sound(SoundType.GRASS)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block TUBE_CORAL_VERTICAL_SLAB = registerBlock("tube_coral_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.TUBE_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block BRAIN_CORAL_VERTICAL_SLAB = registerBlock("brain_coral_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BRAIN_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block BUBBLE_CORAL_VERTICAL_SLAB = registerBlock("bubble_coral_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BUBBLE_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block FIRE_CORAL_VERTICAL_SLAB = registerBlock("fire_coral_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.FIRE_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block HORN_CORAL_VERTICAL_SLAB = registerBlock("horn_coral_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.HORN_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block DEAD_TUBE_CORAL_VERTICAL_SLAB = registerBlock("dead_tube_coral_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DEAD_TUBE_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)));

    public static final Block DEAD_BRAIN_CORAL_VERTICAL_SLAB = registerBlock("dead_brain_coral_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DEAD_BRAIN_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)));

    public static final Block DEAD_BUBBLE_CORAL_VERTICAL_SLAB = registerBlock("dead_bubble_coral_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DEAD_BUBBLE_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)));

    public static final Block DEAD_FIRE_CORAL_VERTICAL_SLAB = registerBlock("dead_fire_coral_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DEAD_FIRE_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)));

    public static final Block DEAD_HORN_CORAL_VERTICAL_SLAB = registerBlock("dead_horn_coral_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.DEAD_HORN_CORAL_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.CORAL_BLOCK)));

    public static final Block SPONGE_VERTICAL_SLAB = registerBlock("sponge_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SPONGE.defaultMapColor())
                    .strength(0.6F)
                    .sound(SoundType.SPONGE)));

    public static final Block WET_SPONGE_VERTICAL_SLAB = registerBlock("wet_sponge_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.WET_SPONGE.defaultMapColor())
                    .strength(0.6F)
                    .sound(SoundType.WET_SPONGE)));

    public static final Block MELON_VERTICAL_SLAB = registerBlock("melon_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.MELON.defaultMapColor())
                    .strength(1.0F)
                    .sound(SoundType.WOOD)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block PUMPKIN_VERTICAL_SLAB = registerBlock("pumpkin_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PUMPKIN.defaultMapColor())
                    .strength(1.0F)
                    .sound(SoundType.WOOD)
                    .instrument(NoteBlockInstrument.BASS)));

    /*public static final Block CARVED_PUMPKIN_VERTICAL_SLAB = registerBlock("carved_pumpkin_vertical_slab",
            new VerticalSlabBlock(AbstractBlock.Settings.create()
                    .mapColor(Blocks.CARVED_PUMPKIN.getDefaultMapColor())
                    .strength(1.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .instrument(NoteBlockInstrument.BASS)));*/

    public static final Block HAY_VERTICAL_SLAB = registerBlock("hay_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.HAY_BLOCK.defaultMapColor())
                    .strength(0.5F)
                    .sound(SoundType.GRASS)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block HONEYCOMB_VERTICAL_SLAB = registerBlock("honeycomb_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.HONEYCOMB_BLOCK.defaultMapColor())
                    .strength(0.6F)
                    .sound(SoundType.CORAL_BLOCK)));

    public static final Block SCULK_VERTICAL_SLAB = registerBlock("sculk_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.SCULK.defaultMapColor())
                    .strength(0.8F)
                    .sound(SoundType.SCULK)
                    .instrument(NoteBlockInstrument.BASS)));

    public static final Block BEDROCK_VERTICAL_SLAB = registerBlock("bedrock_vertical_slab",
            new VerticalSlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(Blocks.BEDROCK.defaultMapColor())
                    .strength(-1.0F, 3600000.0F)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .noLootTable()
                    .isValidSpawn(Blocks::never)));



    ///////////////////////
    //   System Blocks   //
    ///////////////////////

    public static final Block DOUBLE_SLAB_BLOCK = registerBlock("double_slab_block",
            new DoubleSlabBlock(BlockBehaviour.Properties.of()
                    .lightLevel(DoubleSlabBlockEntity.LUMINANCE)
                    .emissiveRendering((state, world, pos) -> {
                        if (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity entity) {
                            return SlabeeUtils.isEmissiveLightingSlabs(entity.getPositiveSlabState().getBlock(), entity.getNegativeSlabState().getBlock());
                        }

                        return false;
                    })));

    public static final Block DOUBLE_VERTICAL_SLAB_BLOCK = registerBlock("double_vertical_slab_block",
            new DoubleVerticalSlabBlock(BlockBehaviour.Properties.of()
                    .lightLevel(DoubleVerticalSlabBlockEntity.LUMINANCE)
                    .emissiveRendering((state, world, pos) -> {
                        if (world.getBlockEntity(pos) instanceof DoubleVerticalSlabBlockEntity entity) {
                            return SlabeeUtils.isEmissiveLightingSlabs(entity.getPositiveSlabState().getBlock(), entity.getNegativeSlabState().getBlock());
                        }

                        return false;
                    })));



    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(Slabee.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Slabee.MOD_ID, name),
                new BlockItem(block, new Item.Properties()));
    }

    private static Block createStainedGlassSlab(DyeColor color) {
        return new StainedGlassSlabBlock(BlockBehaviour.Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.HAT)
                .strength(0.3F)
                .sound(SoundType.GLASS)
                .noOcclusion()
                .isValidSpawn(Blocks::never)
                .isRedstoneConductor(Blocks::never)
                .isSuffocating(Blocks::never)
                .isViewBlocking(Blocks::never));
    }

    private static Block createStainedGlassVerticalSlab(DyeColor color) {
        return new StainedGlassVerticalSlabBlock(BlockBehaviour.Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.HAT)
                .strength(0.3F)
                .sound(SoundType.GLASS)
                .noOcclusion()
                .isValidSpawn(Blocks::never)
                .isRedstoneConductor(Blocks::never)
                .isSuffocating(Blocks::never)
                .isViewBlocking(Blocks::never));
    }

    public static void registerModBlocks() {
        Slabee.LOGGER.info("Registering Mod Blocks for " + Slabee.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
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
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COLORED_BLOCKS).register(entries -> {

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
