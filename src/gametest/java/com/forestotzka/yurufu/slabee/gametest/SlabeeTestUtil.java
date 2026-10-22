package com.forestotzka.yurufu.slabee.gametest;

import com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * テスト共通の操作。実際のプレイヤー操作と同じ経路（アイテムをブロックに使う）でブロックを置く。
 */
final class SlabeeTestUtil {
    private SlabeeTestUtil() {}

    /**
     * プレイヤーが {@code block} のアイテムを持って、{@code relPos} のブロックの {@code side} 面の
     * {@code hitOffset}（ブロック内の相対座標 0〜1）をクリックしたときと同じ設置処理を行う。
     */
    static void placeItemOn(GameTestHelper context, Block block, BlockPos relPos, Direction side, Vec3 hitOffset) {
        Player player = context.makeMockPlayer(GameType.CREATIVE);
        ItemStack stack = new ItemStack(block.asItem());
        player.setItemInHand(InteractionHand.MAIN_HAND, stack);

        BlockPos absPos = context.absolutePos(relPos);
        Vec3 hit = Vec3.atLowerCornerOf(absPos).add(hitOffset);
        BlockHitResult hitResult = new BlockHitResult(hit, side, absPos, false);

        stack.useOn(new UseOnContext(player, InteractionHand.MAIN_HAND, hitResult));
    }

    /** 重ね置きと同じ手順で、ダブルハーフ（横）を直接置く */
    static void placeDouble(GameTestHelper context, BlockPos relPos, Block top, Block bottom) {
        BlockPos abs = context.absolutePos(relPos);
        BlockState state = com.forestotzka.yurufu.slabee.SlabeeUtils.getAbstractState(top, bottom,
                com.forestotzka.yurufu.slabee.block.ModBlocks.DOUBLE_SLAB_BLOCK.defaultBlockState());
        context.getLevel().setBlock(abs, state, 3);
        if (context.getLevel().getBlockEntity(abs) instanceof AbstractDoubleSlabBlockEntity entity) {
            entity.setPositiveSlabId(BuiltInRegistries.BLOCK.getKey(top));
            entity.setNegativeSlabId(BuiltInRegistries.BLOCK.getKey(bottom));
        }
    }

    static AbstractDoubleSlabBlockEntity expectDouble(GameTestHelper context, BlockPos relPos, Block expectedOuter) {
        context.assertBlockPresent(expectedOuter, relPos);
        BlockEntity blockEntity = context.getBlockEntity(relPos);
        if (!(blockEntity instanceof AbstractDoubleSlabBlockEntity entity)) {
            throw new GameTestAssertException("ダブルハーフのBlock Entityがない: " + blockEntity);
        }
        return entity;
    }

    static void expectHalves(GameTestHelper context, AbstractDoubleSlabBlockEntity entity, Block expectedPositive, Block expectedNegative) {
        Block positive = entity.getPositiveSlabState().getBlock();
        Block negative = entity.getNegativeSlabState().getBlock();
        context.assertTrue(positive == expectedPositive,
                "positive側が違う: 期待=" + id(expectedPositive) + " 実際=" + id(positive));
        context.assertTrue(negative == expectedNegative,
                "negative側が違う: 期待=" + id(expectedNegative) + " 実際=" + id(negative));
    }

    static void expectState(GameTestHelper context, BlockPos relPos, BlockState expected) {
        BlockState actual = context.getBlockState(relPos);
        context.assertTrue(actual.equals(expected), "ブロックの状態が違う: 期待=" + expected + " 実際=" + actual);
    }

    static String id(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).toString();
    }
}
