package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.enums.DoubleSlabVariant;
import com.forestotzka.yurufu.slabee.registry.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class DoubleSlabUtils {
    private DoubleSlabUtils() {}

    public static boolean canPlace(ItemPlacementContext context, VoxelShape voxelShape) {
        BlockPos pos = context.getBlockPos();
        return voxelShape.isEmpty() || context.getWorld().doesNotIntersectEntities(null, voxelShape.offset(pos.getX(), pos.getY(), pos.getZ()));
    }

    public static boolean isPositiveSeeThrough(BlockState state) {
        if (!SlabeeUtils.isDoubleSlab(state)) {
            return false;
        }
        return isSeeThrough(state.get(AbstractDoubleSlabBlock.POSITIVE_SLAB));
    }

    public static boolean isNegativeSeeThrough(BlockState state) {
        if (!SlabeeUtils.isDoubleSlab(state)) {
            return false;
        }
        return isSeeThrough(state.get(AbstractDoubleSlabBlock.NEGATIVE_SLAB));
    }

    private static boolean isSeeThrough(DoubleSlabVariant variant) {
        return variant != DoubleSlabVariant.NORMAL;
    }

    public static boolean isPositiveOpaque(BlockState state) {
        if (!SlabeeUtils.isDoubleSlab(state)) {
            return false;
        }
        return isOpaque(state.get(AbstractDoubleSlabBlock.POSITIVE_SLAB));
    }

    public static boolean isNegativeOpaque(BlockState state) {
        if (!SlabeeUtils.isDoubleSlab(state)) {
            return false;
        }
        return isOpaque(state.get(AbstractDoubleSlabBlock.NEGATIVE_SLAB));
    }

    private static boolean isOpaque(DoubleSlabVariant variant) {
        return variant == DoubleSlabVariant.NORMAL || variant == DoubleSlabVariant.TINTED_GLASS;
    }

    public static boolean isTrueSlabId(Identifier id) {
        return id != null && Registries.BLOCK.containsId(id);
    }

    public static int getLuminance(BlockState state) {
        if (state.isOf(ModBlocks.GLOWSTONE_SLAB) || state.isOf(ModBlocks.GLOWSTONE_VERTICAL_SLAB)) {
            return 15;
        } else if (state.isOf(ModBlocks.MAGMA_BLOCK_SLAB) || state.isOf(ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB)) {
            return 3;
        } else if (state.isOf(ModBlocks.CRYING_OBSIDIAN_SLAB) || state.isOf(ModBlocks.CRYING_OBSIDIAN_VERTICAL_SLAB)) {
            return 1;
        } else {
            return 0;
        }
    }

    public static float getMiningSpeed(BlockState positiveSlab, BlockState negativeSlab, PlayerEntity player, BlockView world, BlockPos pos) {
        float hardness = getHardness(positiveSlab, negativeSlab, world, pos);
        return hardness == -1.0F ? 0.0F : getBlockBreakingSpeed(positiveSlab, negativeSlab, player) / hardness / getHarvest(positiveSlab, negativeSlab, player);
    }

    private static float getHardness(BlockState positiveSlab, BlockState negativeSlab, BlockView world, BlockPos pos) {
        float positiveHardness = positiveSlab.getHardness(world, pos);
        float negativeHardness = negativeSlab.getHardness(world, pos);
        if (positiveHardness == -1.0F || negativeHardness == -1.0F) {
            return -1.0F;
        }
        return (positiveHardness + negativeHardness) / 2.0F;
    }

    private static float getBlockBreakingSpeed(BlockState positiveSlab, BlockState negativeSlab, PlayerEntity player) {
        return (player.getBlockBreakingSpeed(positiveSlab) + player.getBlockBreakingSpeed(negativeSlab)) / 2.0F;
    }

    private static float getHarvest(BlockState positiveSlab, BlockState negativeSlab, PlayerEntity player) {
        float harvest = 30;
        boolean positiveCanHarvest = canHarvest(positiveSlab, player);
        boolean negativeCanHarvest = canHarvest(negativeSlab, player);
        if (!positiveCanHarvest && !negativeCanHarvest) {
            harvest = 100;
        } else if (!positiveCanHarvest || !negativeCanHarvest) {
            harvest = 50;
        }
        return harvest;
    }

    private static boolean canHarvest(BlockState state, PlayerEntity player) {
        ItemStack mainhandItem = player.getInventory().getMainHandStack();
        if (state.isIn(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return (mainhandItem.isOf(Items.DIAMOND_PICKAXE) || mainhandItem.isOf(Items.NETHERITE_PICKAXE));
        }
        return (player.canHarvest(state)) || (mainhandItem.isOf(Items.SHEARS) && state.isIn(ModBlockTags.MINEABLE_SHEARS));
    }
}
