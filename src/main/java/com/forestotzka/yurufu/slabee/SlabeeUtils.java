package com.forestotzka.yurufu.slabee;

import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.VerticalSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;

import java.util.HashSet;
import java.util.Set;

public class SlabeeUtils {
    private static final Set<Block> StainedGlassSlabs;
    private static final Set<Block> StainedGlassVerticalSlabs;
    private static final Set<Block> LeafSlabs;
    private static final Set<Block> LeafVerticalSlabs;
    private static final Set<Block> GlowingSlabs;
    private static final Set<Block> GlowingVerticalSlabs;

    private static final Set<Block> CutoutSlabs;
    private static final Set<Block> CutoutVerticalSlabs;
    private static final Set<Block> CutoutMippedSlabs;
    private static final Set<Block> CutoutMippedVerticalSlabs;
    private static final Set<Block> TranslucentSlabs;
    private static final Set<Block> TranslucentVerticalSlabs;
    private static final Set<Block> NonOpaqueSlabs;
    private static final Set<Block> NonOpaqueVerticalSlabs;
    private static final Set<Block> EmissiveLightingSlabs;
    private static final Set<Block> EmissiveLightingVerticalSlabs;
    private static final Set<Block> SeeThroughSlabs;
    private static final Set<Block> SeeThroughVerticalSlabs;

    static {
        StainedGlassSlabs = Set.of(
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
        StainedGlassVerticalSlabs = Set.of(
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
        LeafSlabs = Set.of(
                ModBlocks.OAK_LEAF_SLAB,
                ModBlocks.SPRUCE_LEAF_SLAB,
                ModBlocks.BIRCH_LEAF_SLAB,
                ModBlocks.JUNGLE_LEAF_SLAB,
                ModBlocks.ACACIA_LEAF_SLAB,
                ModBlocks.DARK_OAK_LEAF_SLAB,
                ModBlocks.MANGROVE_LEAF_SLAB,
                ModBlocks.CHERRY_LEAF_SLAB,
                ModBlocks.AZALEA_LEAF_SLAB,
                ModBlocks.FLOWERING_AZALEA_LEAF_SLAB
        );
        LeafVerticalSlabs = Set.of(
                ModBlocks.OAK_LEAF_VERTICAL_SLAB,
                ModBlocks.SPRUCE_LEAF_VERTICAL_SLAB,
                ModBlocks.BIRCH_LEAF_VERTICAL_SLAB,
                ModBlocks.JUNGLE_LEAF_VERTICAL_SLAB,
                ModBlocks.ACACIA_LEAF_VERTICAL_SLAB,
                ModBlocks.DARK_OAK_LEAF_VERTICAL_SLAB,
                ModBlocks.MANGROVE_LEAF_VERTICAL_SLAB,
                ModBlocks.CHERRY_LEAF_VERTICAL_SLAB,
                ModBlocks.AZALEA_LEAF_VERTICAL_SLAB,
                ModBlocks.FLOWERING_AZALEA_LEAF_VERTICAL_SLAB
        );
        GlowingSlabs = Set.of(
                ModBlocks.GLOWSTONE_SLAB,
                ModBlocks.MAGMA_BLOCK_SLAB,
                ModBlocks.CRYING_OBSIDIAN_SLAB
        );
        GlowingVerticalSlabs = Set.of(
                ModBlocks.GLOWSTONE_VERTICAL_SLAB,
                ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB,
                ModBlocks.CRYING_OBSIDIAN_VERTICAL_SLAB
        );

        CutoutSlabs = Set.of(
                ModBlocks.GLASS_SLAB
        );
        CutoutVerticalSlabs = Set.of(
                ModBlocks.GLASS_VERTICAL_SLAB
        );

        CutoutMippedSlabs = new HashSet<>(Set.of(
                ModBlocks.GRASS_SLAB,
                ModBlocks.MANGROVE_ROOT_SLAB,
                ModBlocks.DIRT_PATH_SLAB
        ));
        CutoutMippedSlabs.addAll(LeafSlabs);
        CutoutMippedVerticalSlabs = new HashSet<>(Set.of(
                ModBlocks.GRASS_VERTICAL_SLAB,
                ModBlocks.MANGROVE_ROOT_VERTICAL_SLAB,
                ModBlocks.DIRT_PATH_VERTICAL_SLAB
        ));
        CutoutMippedVerticalSlabs.addAll(LeafVerticalSlabs);

        TranslucentSlabs = new HashSet<>(Set.of(
                ModBlocks.TINTED_GLASS_SLAB
        ));
        TranslucentSlabs.addAll(StainedGlassSlabs);
        TranslucentVerticalSlabs = new HashSet<>(Set.of(
                ModBlocks.TINTED_GLASS_VERTICAL_SLAB
        ));
        TranslucentVerticalSlabs.addAll(StainedGlassVerticalSlabs);

        NonOpaqueSlabs = new HashSet<>(Set.of(
                ModBlocks.GLASS_SLAB,
                ModBlocks.MANGROVE_ROOT_SLAB,
                ModBlocks.DIRT_PATH_SLAB
        ));
        NonOpaqueSlabs.addAll(StainedGlassSlabs);
        NonOpaqueSlabs.addAll(LeafSlabs);
        NonOpaqueSlabs.addAll(GlowingSlabs);
        NonOpaqueVerticalSlabs = new HashSet<>(Set.of(
                ModBlocks.GLASS_VERTICAL_SLAB,
                ModBlocks.MANGROVE_ROOT_VERTICAL_SLAB,
                ModBlocks.DIRT_PATH_VERTICAL_SLAB
        ));
        NonOpaqueVerticalSlabs.addAll(StainedGlassVerticalSlabs);
        NonOpaqueVerticalSlabs.addAll(LeafVerticalSlabs);
        NonOpaqueVerticalSlabs.addAll(GlowingVerticalSlabs);

        EmissiveLightingSlabs = Set.of(
                ModBlocks.MAGMA_BLOCK_SLAB
        );
        EmissiveLightingVerticalSlabs = Set.of(
                ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB
        );

        SeeThroughSlabs = new HashSet<>(Set.of(
                ModBlocks.GLASS_SLAB,
                ModBlocks.TINTED_GLASS_SLAB,
                ModBlocks.MANGROVE_ROOT_SLAB,
                ModBlocks.DIRT_PATH_SLAB
        ));
        SeeThroughSlabs.addAll(StainedGlassSlabs);
        SeeThroughSlabs.addAll(LeafSlabs);
        SeeThroughVerticalSlabs = new HashSet<>(Set.of(
                ModBlocks.GLASS_VERTICAL_SLAB,
                ModBlocks.TINTED_GLASS_VERTICAL_SLAB,
                ModBlocks.MANGROVE_ROOT_VERTICAL_SLAB,
                ModBlocks.DIRT_PATH_VERTICAL_SLAB
        ));
        SeeThroughVerticalSlabs.addAll(StainedGlassVerticalSlabs);
        SeeThroughVerticalSlabs.addAll(LeafVerticalSlabs);
    }

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

    public static boolean isEmissiveLightingSlabs(Block block) {
        return EmissiveLightingSlabs.contains(block);
    }
    public static boolean isEmissiveLightingVerticalSlabs(Block block) {
        return EmissiveLightingVerticalSlabs.contains(block);
    }

    public static int getOpaque(Block p, Block n) {
        if (p instanceof SlabBlock) {
            return booleanToInt(!NonOpaqueSlabs.contains(p), !NonOpaqueSlabs.contains(n));
        } else if (p instanceof VerticalSlabBlock) {
            return booleanToInt(!NonOpaqueVerticalSlabs.contains(p), !NonOpaqueVerticalSlabs.contains(n));
        } else {
            return 0;
        }
    }
    public static int getSeeThrough(Block p, Block n) {
        if (p instanceof SlabBlock) {
            return booleanToInt(SeeThroughSlabs.contains(p), SeeThroughSlabs.contains(n));
        } else if (p instanceof VerticalSlabBlock) {
            return booleanToInt(SeeThroughVerticalSlabs.contains(p), SeeThroughVerticalSlabs.contains(n));
        } else {
            return 0;
        }
    }

    private static int booleanToInt(boolean bl1, boolean bl2) {
        return (bl1 ? 2 : 0) + (bl2 ? 1 : 0);
    }
}
