package com.forestotzka.yurufu.slabee;

import com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlock;
import com.forestotzka.yurufu.slabee.block.DoubleSlabUtils;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.VerticalSlabBlock;
import com.forestotzka.yurufu.slabee.block.enums.DoubleSlabVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;

import java.util.HashSet;
import java.util.Set;

import static com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlock.*;

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
        if (block instanceof SlabBlock) {
            return CutoutSlabs.contains(block);
        } else if (block instanceof VerticalSlabBlock) {
            return CutoutVerticalSlabs.contains(block);
        } else {
            return false;
        }
    }

    public static boolean isCutoutMippedSlabs(Block block) {
        if (block instanceof SlabBlock) {
            return CutoutMippedSlabs.contains(block);
        } else if (block instanceof VerticalSlabBlock) {
            return CutoutMippedVerticalSlabs.contains(block);
        } else {
            return false;
        }
    }

    public static boolean isTranslucentSlabs(Block block) {
        if (block instanceof SlabBlock) {
            return TranslucentSlabs.contains(block);
        } else if (block instanceof VerticalSlabBlock) {
            return TranslucentVerticalSlabs.contains(block);
        } else {
            return false;
        }
    }

    public static boolean isEmissiveLightingSlabs(Block p, Block n) {
        if (p instanceof SlabBlock) {
            return EmissiveLightingSlabs.contains(p) || EmissiveLightingSlabs.contains(n);
        } else if (p instanceof VerticalSlabBlock) {
            return EmissiveLightingVerticalSlabs.contains(p) || EmissiveLightingVerticalSlabs.contains(n);
        } else {
            return false;
        }
    }

    public static boolean isOpaque(Block block) {
        if (block instanceof SlabBlock) {
            return !NonOpaqueSlabs.contains(block);
        } else if (block instanceof VerticalSlabBlock) {
            return !NonOpaqueVerticalSlabs.contains(block);
        } else {
            return false;
        }
    }

    public static int getLuminance(Block p, Block n) {
        return Math.max(DoubleSlabUtils.getLuminance(p.getDefaultState()), DoubleSlabUtils.getLuminance(n.getDefaultState()));
    }

    public static BlockState getAbstractState(Block p, Block n) {
        BlockState state;

        if (p instanceof SlabBlock) {
            state = ModBlocks.DOUBLE_SLAB_BLOCK.getDefaultState();
        } else if (p instanceof VerticalSlabBlock) {
            state = ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK.getDefaultState();
        } else {
            return ModBlocks.DOUBLE_SLAB_BLOCK.getDefaultState();
        }

        return getAbstractState(p, n, state);
    }

    public static BlockState getAbstractState(Block p, Block n, BlockState state) {
        if (!SlabeeUtils.isDoubleSlab(state)) {
            return ModBlocks.DOUBLE_SLAB_BLOCK.getDefaultState();
        }

        return state.with(IS_EMISSIVE_LIGHTING, SlabeeUtils.isEmissiveLightingSlabs(p, n))
                .with(LIGHT_LEVEL, SlabeeUtils.getLuminance(p, n))
                .with(POSITIVE_SLAB, DoubleSlabVariant.fromBlock(p))
                .with(NEGATIVE_SLAB, DoubleSlabVariant.fromBlock(n));
    }

    public static boolean isDoubleSlab(BlockState state) {
        return isDoubleSlab(state.getBlock());
    }

    public static boolean isDoubleSlab(Block block) {
        return block instanceof AbstractDoubleSlabBlock;
    }
}
