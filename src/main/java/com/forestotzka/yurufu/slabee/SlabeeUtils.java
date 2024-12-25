package com.forestotzka.yurufu.slabee;

import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.Set;

public class SlabeeUtils {
    private static final Set<Block> CutoutSlabs = Set.of(
            ModBlocks.GLASS_SLAB
    );
    private static final Set<Block> CutoutVerticalSlabs = Set.of(
            ModBlocks.GLASS_VERTICAL_SLAB
    );
    private static final Set<Block> CutoutMippedSlabs = Set.of(
            ModBlocks.GRASS_SLAB,
            ModBlocks.OAK_LEAF_SLAB,
            ModBlocks.SPRUCE_LEAF_SLAB,
            ModBlocks.BIRCH_LEAF_SLAB,
            ModBlocks.JUNGLE_LEAF_SLAB,
            ModBlocks.ACACIA_LEAF_SLAB,
            ModBlocks.DARK_OAK_LEAF_SLAB,
            ModBlocks.MANGROVE_LEAF_SLAB,
            ModBlocks.CHERRY_LEAF_SLAB,
            ModBlocks.AZALEA_LEAF_SLAB,
            ModBlocks.FLOWERING_AZALEA_LEAF_SLAB,
            ModBlocks.MANGROVE_ROOT_SLAB,
            ModBlocks.DIRT_PATH_SLAB
    );
    private static final Set<Block> CutoutMippedVerticalSlabs = Set.of(
            ModBlocks.GRASS_VERTICAL_SLAB,
            ModBlocks.OAK_LEAF_VERTICAL_SLAB,
            ModBlocks.SPRUCE_LEAF_VERTICAL_SLAB,
            ModBlocks.BIRCH_LEAF_VERTICAL_SLAB,
            ModBlocks.JUNGLE_LEAF_VERTICAL_SLAB,
            ModBlocks.ACACIA_LEAF_VERTICAL_SLAB,
            ModBlocks.DARK_OAK_LEAF_VERTICAL_SLAB,
            ModBlocks.MANGROVE_LEAF_VERTICAL_SLAB,
            ModBlocks.CHERRY_LEAF_VERTICAL_SLAB,
            ModBlocks.AZALEA_LEAF_VERTICAL_SLAB,
            ModBlocks.FLOWERING_AZALEA_LEAF_VERTICAL_SLAB,
            ModBlocks.MANGROVE_ROOT_VERTICAL_SLAB,
            ModBlocks.DIRT_PATH_VERTICAL_SLAB
    );
    private static final Set<Block> TranslucentSlabs = Set.of(
            ModBlocks.TINTED_GLASS_SLAB,
            ModBlocks.WHITE_STAINED_GLASS_SLAB,
            ModBlocks.LIGHT_GRAY_STAINED_GLASS_SLAB,
            ModBlocks.GRAY_STAINED_GLASS_SLAB,
            ModBlocks.BLACK_STAINED_GLASS_SLAB,
            ModBlocks.BROWN_STAINED_GLASS_SLAB,
            ModBlocks.RED_STAINED_GLASS_SLAB,
            ModBlocks.ORANGE_STAINED_GLASS_SLAB,
            ModBlocks.YELLOW_STAINED_GLASS_SLAB,
            ModBlocks.LIME_STAINED_GLASS_SLAB,
            ModBlocks.GREEN_STAINED_GLASS_SLAB,
            ModBlocks.CYAN_STAINED_GLASS_SLAB,
            ModBlocks.LIGHT_BLUE_STAINED_GLASS_SLAB,
            ModBlocks.BLUE_STAINED_GLASS_SLAB,
            ModBlocks.PURPLE_STAINED_GLASS_SLAB,
            ModBlocks.MAGENTA_STAINED_GLASS_SLAB,
            ModBlocks.PINK_STAINED_GLASS_SLAB
    );
    private static final Set<Block> TranslucentVerticalSlabs = Set.of(
            ModBlocks.TINTED_GLASS_VERTICAL_SLAB,
            ModBlocks.WHITE_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.GRAY_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.BLACK_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.BROWN_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.RED_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.ORANGE_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.YELLOW_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.LIME_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.GREEN_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.CYAN_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.BLUE_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.PURPLE_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.MAGENTA_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.PINK_STAINED_GLASS_VERTICAL_SLAB
    );
    private static final Set<Block> NonOpaqueSlabs = Set.of(
            ModBlocks.GLASS_SLAB,
            ModBlocks.WHITE_STAINED_GLASS_SLAB,
            ModBlocks.LIGHT_GRAY_STAINED_GLASS_SLAB,
            ModBlocks.GRAY_STAINED_GLASS_SLAB,
            ModBlocks.BLACK_STAINED_GLASS_SLAB,
            ModBlocks.BROWN_STAINED_GLASS_SLAB,
            ModBlocks.RED_STAINED_GLASS_SLAB,
            ModBlocks.ORANGE_STAINED_GLASS_SLAB,
            ModBlocks.YELLOW_STAINED_GLASS_SLAB,
            ModBlocks.LIME_STAINED_GLASS_SLAB,
            ModBlocks.GREEN_STAINED_GLASS_SLAB,
            ModBlocks.CYAN_STAINED_GLASS_SLAB,
            ModBlocks.LIGHT_BLUE_STAINED_GLASS_SLAB,
            ModBlocks.BLUE_STAINED_GLASS_SLAB,
            ModBlocks.PURPLE_STAINED_GLASS_SLAB,
            ModBlocks.MAGENTA_STAINED_GLASS_SLAB,
            ModBlocks.PINK_STAINED_GLASS_SLAB,

            ModBlocks.OAK_LEAF_SLAB,
            ModBlocks.SPRUCE_LEAF_SLAB,
            ModBlocks.BIRCH_LEAF_SLAB,
            ModBlocks.JUNGLE_LEAF_SLAB,
            ModBlocks.ACACIA_LEAF_SLAB,
            ModBlocks.DARK_OAK_LEAF_SLAB,
            ModBlocks.MANGROVE_LEAF_SLAB,
            ModBlocks.CHERRY_LEAF_SLAB,
            ModBlocks.AZALEA_LEAF_SLAB,
            ModBlocks.FLOWERING_AZALEA_LEAF_SLAB,
            ModBlocks.MANGROVE_ROOT_SLAB,
            ModBlocks.DIRT_PATH_SLAB
    );
    private static final Set<Block> NonOpaqueVerticalSlabs = Set.of(
            ModBlocks.GLASS_VERTICAL_SLAB,
            ModBlocks.WHITE_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.GRAY_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.BLACK_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.BROWN_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.RED_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.ORANGE_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.YELLOW_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.LIME_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.GREEN_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.CYAN_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.BLUE_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.PURPLE_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.MAGENTA_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.PINK_STAINED_GLASS_VERTICAL_SLAB,

            ModBlocks.OAK_LEAF_VERTICAL_SLAB,
            ModBlocks.SPRUCE_LEAF_VERTICAL_SLAB,
            ModBlocks.BIRCH_LEAF_VERTICAL_SLAB,
            ModBlocks.JUNGLE_LEAF_VERTICAL_SLAB,
            ModBlocks.ACACIA_LEAF_VERTICAL_SLAB,
            ModBlocks.DARK_OAK_LEAF_VERTICAL_SLAB,
            ModBlocks.MANGROVE_LEAF_VERTICAL_SLAB,
            ModBlocks.CHERRY_LEAF_VERTICAL_SLAB,
            ModBlocks.AZALEA_LEAF_VERTICAL_SLAB,
            ModBlocks.FLOWERING_AZALEA_LEAF_VERTICAL_SLAB,
            ModBlocks.MANGROVE_ROOT_VERTICAL_SLAB,
            ModBlocks.DIRT_PATH_VERTICAL_SLAB
    );
    private static final Set<Block> EmissiveLightingSlabs = Set.of(
            ModBlocks.MAGMA_BLOCK_SLAB
    );
    private static final Set<Block> EmissiveLightingVerticalSlabs = Set.of(
            ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB
    );

    public static boolean isCutoutSlabs(Block block) {
        return CutoutSlabs.contains(block);
    }
    public static boolean isCutoutVerticalSlabs(Block block) {
        return CutoutVerticalSlabs.contains(block);
    }
    public static boolean isCutoutMippedSlabs(Block block) {
        return CutoutMippedSlabs.contains(block);
    }
    public static boolean isCutoutMippedVerticalSlabs(Block block) {
        return CutoutMippedVerticalSlabs.contains(block);
    }
    public static boolean isTranslucentSlabs(Block block) {
        return TranslucentSlabs.contains(block);
    }
    public static boolean isTranslucentVerticalSlabs(Block block) {
        return TranslucentVerticalSlabs.contains(block);
    }
    public static boolean isOpaqueSlabs(Block block) {
        return !NonOpaqueSlabs.contains(block);
    }
    public static boolean isOpaqueVerticalSlabs(Block block) {
        return !NonOpaqueVerticalSlabs.contains(block);
    }
    public static boolean isEmissiveLightingSlabs(Block block) {
        return EmissiveLightingSlabs.contains(block);
    }
    public static boolean isEmissiveLightingVerticalSlabs(Block block) {
        return EmissiveLightingVerticalSlabs.contains(block);
    }
}
