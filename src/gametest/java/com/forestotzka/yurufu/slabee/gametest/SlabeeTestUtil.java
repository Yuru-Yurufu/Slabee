package com.forestotzka.yurufu.slabee.gametest;

import com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registries;
import net.minecraft.test.GameTestException;
import net.minecraft.test.TestContext;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;

/**
 * テスト共通の操作。実際のプレイヤー操作と同じ経路（アイテムをブロックに使う）でブロックを置く。
 */
final class SlabeeTestUtil {
    private SlabeeTestUtil() {}

    /**
     * プレイヤーが {@code block} のアイテムを持って、{@code relPos} のブロックの {@code side} 面の
     * {@code hitOffset}（ブロック内の相対座標 0〜1）をクリックしたときと同じ設置処理を行う。
     */
    static void placeItemOn(TestContext context, Block block, BlockPos relPos, Direction side, Vec3d hitOffset) {
        PlayerEntity player = context.createMockPlayer(GameMode.CREATIVE);
        ItemStack stack = new ItemStack(block.asItem());
        player.setStackInHand(Hand.MAIN_HAND, stack);

        BlockPos absPos = context.getAbsolutePos(relPos);
        Vec3d hit = Vec3d.of(absPos).add(hitOffset);
        BlockHitResult hitResult = new BlockHitResult(hit, side, absPos, false);

        stack.useOnBlock(new ItemUsageContext(player, Hand.MAIN_HAND, hitResult));
    }

    /** 重ね置きと同じ手順で、ダブルハーフ（横）を直接置く */
    static void placeDouble(TestContext context, BlockPos relPos, Block top, Block bottom) {
        BlockPos abs = context.getAbsolutePos(relPos);
        BlockState state = com.forestotzka.yurufu.slabee.SlabeeUtils.getAbstractState(top, bottom,
                com.forestotzka.yurufu.slabee.block.ModBlocks.DOUBLE_SLAB_BLOCK.getDefaultState());
        context.getWorld().setBlockState(abs, state, 3);
        if (context.getWorld().getBlockEntity(abs) instanceof AbstractDoubleSlabBlockEntity entity) {
            entity.setPositiveSlabId(Registries.BLOCK.getId(top));
            entity.setNegativeSlabId(Registries.BLOCK.getId(bottom));
        }
    }

    static AbstractDoubleSlabBlockEntity expectDouble(TestContext context, BlockPos relPos, Block expectedOuter) {
        context.expectBlock(expectedOuter, relPos);
        BlockEntity blockEntity = context.getBlockEntity(relPos);
        if (!(blockEntity instanceof AbstractDoubleSlabBlockEntity entity)) {
            throw new GameTestException("ダブルハーフのBlock Entityがない: " + blockEntity);
        }
        return entity;
    }

    static void expectHalves(TestContext context, AbstractDoubleSlabBlockEntity entity, Block expectedPositive, Block expectedNegative) {
        Block positive = entity.getPositiveSlabState().getBlock();
        Block negative = entity.getNegativeSlabState().getBlock();
        context.assertTrue(positive == expectedPositive,
                "positive側が違う: 期待=" + id(expectedPositive) + " 実際=" + id(positive));
        context.assertTrue(negative == expectedNegative,
                "negative側が違う: 期待=" + id(expectedNegative) + " 実際=" + id(negative));
    }

    static void expectState(TestContext context, BlockPos relPos, BlockState expected) {
        BlockState actual = context.getBlockState(relPos);
        context.assertTrue(actual.equals(expected), "ブロックの状態が違う: 期待=" + expected + " 実際=" + actual);
    }

    static String id(Block block) {
        return Registries.BLOCK.getId(block).toString();
    }
}
