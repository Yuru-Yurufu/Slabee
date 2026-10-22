package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.enums.DoubleSlabVariant;
import com.forestotzka.yurufu.slabee.registry.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DoubleSlabUtils {
    private DoubleSlabUtils() {}

    public static boolean canPlace(BlockPlaceContext context, VoxelShape voxelShape) {
        BlockPos pos = context.getClickedPos();
        return voxelShape.isEmpty() || context.getLevel().isUnobstructed(null, voxelShape.move(pos.getX(), pos.getY(), pos.getZ()));
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
        BlockState other = otherFull.defaultBlockState();
        if (other.canOcclude()) {
            return false;
        }
        return !thisFull.defaultBlockState().skipRendering(other, side);
    }

    public static boolean isPositiveSeeThrough(BlockState state) {
        if (!SlabeeUtils.isDoubleSlab(state)) {
            return false;
        }
        return isSeeThrough(state.getValue(AbstractDoubleSlabBlock.POSITIVE_SLAB));
    }

    public static boolean isNegativeSeeThrough(BlockState state) {
        if (!SlabeeUtils.isDoubleSlab(state)) {
            return false;
        }
        return isSeeThrough(state.getValue(AbstractDoubleSlabBlock.NEGATIVE_SLAB));
    }

    private static boolean isSeeThrough(DoubleSlabVariant variant) {
        return variant != DoubleSlabVariant.NORMAL;
    }

    public static boolean isPositiveOpaque(BlockState state) {
        if (!SlabeeUtils.isDoubleSlab(state)) {
            return false;
        }
        return isOpaque(state.getValue(AbstractDoubleSlabBlock.POSITIVE_SLAB));
    }

    public static boolean isNegativeOpaque(BlockState state) {
        if (!SlabeeUtils.isDoubleSlab(state)) {
            return false;
        }
        return isOpaque(state.getValue(AbstractDoubleSlabBlock.NEGATIVE_SLAB));
    }

    private static boolean isOpaque(DoubleSlabVariant variant) {
        return variant == DoubleSlabVariant.NORMAL || variant == DoubleSlabVariant.TINTED_GLASS;
    }

    public static boolean isTrueSlabId(ResourceLocation id) {
        return id != null && BuiltInRegistries.BLOCK.containsKey(id);
    }

    /**
     * ダブルハーフの中のハーフの明るさ。単体のハーフと同じ明るさにする（あやの決定 2026-10-23）。
     * 光らない側のハーフがある方向には光が抜けないことは、光をさえぎる形（getLightingShape）で表している。
     */
    public static int getLuminance(BlockState state) {
        return state.getLightEmission();
    }

    public static float getMiningSpeed(BlockState positiveSlab, BlockState negativeSlab, Player player, BlockGetter world, BlockPos pos) {
        float hardness = getHardness(positiveSlab, negativeSlab, world, pos);
        return hardness == -1.0F ? 0.0F : getBlockBreakingSpeed(positiveSlab, negativeSlab, player) / hardness / getHarvest(positiveSlab, negativeSlab, player);
    }

    private static float getHardness(BlockState positiveSlab, BlockState negativeSlab, BlockGetter world, BlockPos pos) {
        float positiveHardness = positiveSlab.getDestroySpeed(world, pos);
        float negativeHardness = negativeSlab.getDestroySpeed(world, pos);
        if (positiveHardness == -1.0F || negativeHardness == -1.0F) {
            return -1.0F;
        }
        return (positiveHardness + negativeHardness) / 2.0F;
    }

    private static float getBlockBreakingSpeed(BlockState positiveSlab, BlockState negativeSlab, Player player) {
        return (player.getDestroySpeed(positiveSlab) + player.getDestroySpeed(negativeSlab)) / 2.0F;
    }

    private static float getHarvest(BlockState positiveSlab, BlockState negativeSlab, Player player) {
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

    private static boolean canHarvest(BlockState state, Player player) {
        ItemStack mainhandItem = player.getInventory().getSelected();
        if (state.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return (mainhandItem.is(Items.DIAMOND_PICKAXE) || mainhandItem.is(Items.NETHERITE_PICKAXE));
        }
        return (player.hasCorrectToolForDrops(state)) || (mainhandItem.is(Items.SHEARS) && state.is(ModBlockTags.MINEABLE_SHEARS));
    }
}
