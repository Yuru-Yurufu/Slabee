package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.enums.DoubleSlabVariant;
import com.forestotzka.yurufu.slabee.registry.tag.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
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

    /**
     * ダブルハーフの内側の面（もう片方のハーフに接している面）を描くかどうか。
     * 「同じ並びを通常ブロックで作ったら、この面は見えるか」に合わせる（MODの基本方針）。
     * - 相手の元のブロックが不透明なら見えない（例：上が石）
     * - 同じガラスどうしのように、元のブロックどうしで面を省く組み合わせなら見えない
     * - それ以外は見える（例：葉の下のグロウストーン、色の違う色付きガラスどうし、葉どうし）
     */
    public static boolean shouldDrawInnerFace(BlockState half, BlockState otherHalf, Direction side) {
        Block thisFull = ModBlockMap.toOriginal(half.getBlock());
        Block otherFull = ModBlockMap.toOriginal(otherHalf.getBlock());
        if (thisFull == null || otherFull == null) {
            return true;
        }
        BlockState other = otherFull.getDefaultState();
        if (other.isOpaque()) {
            return false;
        }
        return !thisFull.getDefaultState().isSideInvisible(other, side);
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

    /**
     * ダブルハーフの中のハーフの明るさ。単体のハーフと同じ明るさにする（あやの決定 2026-10-23）。
     * 光らない側のハーフがある方向には光が抜けないことは、光をさえぎる形（getLightingShape）で表している。
     */
    public static int getLuminance(BlockState state) {
        return state.getLuminance();
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
