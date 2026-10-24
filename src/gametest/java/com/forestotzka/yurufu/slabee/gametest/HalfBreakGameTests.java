package com.forestotzka.yurufu.slabee.gametest;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.VerticalSlabBlock;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import static com.forestotzka.yurufu.slabee.gametest.SlabeeTestUtil.*;

/**
 * スニーク中にダブルハーフを壊すと、見ている側だけが壊れる動きの回帰テスト。
 *
 * 注意：今の実装では「どちら側を見ているか」をクライアントが static 変数
 * （{@link LookingPositionTracker}）に書き、サーバー側がそれを読んでいる。
 * テストではその変数を直接設定して、今の動きを記録する。
 */
public class HalfBreakGameTests implements FabricGameTest {
    private static final BlockPos POS = new BlockPos(1, 1, 1);

    private static void resetTracker() {
        LookingPositionTracker.lookingAtUpperHalf = false;
        LookingPositionTracker.lookingAtEasternHalf = false;
        LookingPositionTracker.lookingAtSouthernHalf = false;
    }

    private static void sneakBreak(TestContext context, BlockPos relPos) {
        ServerPlayerEntity player = context.createMockCreativeServerPlayerInWorld();
        player.setSneaking(true);
        player.interactionManager.tryBreakBlock(context.getAbsolutePos(relPos));
    }

    /** 上半分を見てスニーク破壊 → 上（オーク）が消え、下の石が下付きハーフとして残る */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void sneakBreakUpperHalf(TestContext context) {
        context.setBlockState(POS, Blocks.STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM));
        placeItemOn(context, Blocks.OAK_SLAB, POS, Direction.UP, new Vec3d(0.5, 0.5, 0.5));
        expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);

        try {
            resetTracker();
            LookingPositionTracker.lookingAtUpperHalf = true;
            sneakBreak(context, POS);
        } finally {
            resetTracker();
        }

        expectState(context, POS, Blocks.STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM));
        context.complete();
    }

    /** 下半分を見てスニーク破壊 → 下（石）が消え、上のオークが上付きハーフとして残る */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void sneakBreakLowerHalf(TestContext context) {
        context.setBlockState(POS, Blocks.STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM));
        placeItemOn(context, Blocks.OAK_SLAB, POS, Direction.UP, new Vec3d(0.5, 0.5, 0.5));
        expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);

        try {
            resetTracker();
            LookingPositionTracker.lookingAtUpperHalf = false;
            sneakBreak(context, POS);
        } finally {
            resetTracker();
        }

        expectState(context, POS, Blocks.OAK_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP));
        context.complete();
    }

    /** 縦（X軸）で東半分を見てスニーク破壊 → 東（石）が消え、西のオークが西向きで残る */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void sneakBreakEasternHalf(TestContext context) {
        context.setBlockState(POS, ModBlocks.STONE_VERTICAL_SLAB.getDefaultState().with(VerticalSlabBlock.FACING, Direction.EAST));
        placeItemOn(context, ModBlocks.OAK_VERTICAL_SLAB, POS, Direction.WEST, new Vec3d(0.5, 0.5, 0.5));
        expectDouble(context, POS, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK);

        try {
            resetTracker();
            LookingPositionTracker.lookingAtEasternHalf = true;
            sneakBreak(context, POS);
        } finally {
            resetTracker();
        }

        context.expectBlock(ModBlocks.OAK_VERTICAL_SLAB, POS);
        context.assertTrue(context.getBlockState(POS).get(VerticalSlabBlock.FACING) == Direction.WEST, "西向きで残るはず");
        context.complete();
    }

    /** スニークせずに壊すと、両方まとめて壊れる */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void normalBreakRemovesBoth(TestContext context) {
        context.setBlockState(POS, Blocks.STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM));
        placeItemOn(context, Blocks.OAK_SLAB, POS, Direction.UP, new Vec3d(0.5, 0.5, 0.5));
        expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);

        ServerPlayerEntity player = context.createMockCreativeServerPlayerInWorld();
        player.setSneaking(false);
        player.interactionManager.tryBreakBlock(context.getAbsolutePos(POS));

        context.expectBlock(Blocks.AIR, POS);
        context.complete();
    }
}
