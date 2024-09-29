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
    public static final Block DOUBLE_SLAB_BLOCK = registerBlock("double_slab_block",
            new DoubleSlabBlock(AbstractBlock.Settings.create().strength(2.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.STONE)));

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
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(entries -> {
            entries.add(ModBlocks.DOUBLE_SLAB_BLOCK);
        });
    }
}
