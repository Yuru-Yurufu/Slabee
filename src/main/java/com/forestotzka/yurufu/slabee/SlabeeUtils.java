package com.forestotzka.yurufu.slabee;

import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.Set;

public class SlabeeUtils {
    private static final Set<Block> CutoutSlabs = Set.of(
    );
    private static final Set<Block> CutoutVerticalSlabs = Set.of(
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
    private static final Set<Block> OpaqueSlabs = Set.of(
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
    private static final Set<Block> OpaqueVerticalSlabs = Set.of(
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
    public static boolean isOpaqueSlabs(Block block) {
        return OpaqueSlabs.contains(block);
    }
    public static boolean isOpaqueVerticalSlabs(Block block) {
        return OpaqueVerticalSlabs.contains(block);
    }
    public static boolean isEmissiveLightingSlabs(Block block) {
        return EmissiveLightingSlabs.contains(block);
    }
    public static boolean isEmissiveLightingVerticalSlabs(Block block) {
        return EmissiveLightingVerticalSlabs.contains(block);
    }
}
