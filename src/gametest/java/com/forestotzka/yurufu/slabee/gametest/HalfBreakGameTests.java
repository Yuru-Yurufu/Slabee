package com.forestotzka.yurufu.slabee.gametest;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.VerticalSlabBlock;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.Vec3;

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

    private static void sneakBreak(GameTestHelper context, BlockPos relPos) {
        ServerPlayer player = context.makeMockServerPlayerInLevel();
        player.setShiftKeyDown(true);
        player.gameMode.destroyBlock(context.absolutePos(relPos));
    }

    /** 上半分を見てスニーク破壊 → 上（オーク）が消え、下の石が下付きハーフとして残る */
    @GameTest(template = EMPTY_STRUCTURE)
    public void sneakBreakUpperHalf(GameTestHelper context) {
        context.setBlock(POS, Blocks.STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM));
        placeItemOn(context, Blocks.OAK_SLAB, POS, Direction.UP, new Vec3(0.5, 0.5, 0.5));
        expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);

        try {
            resetTracker();
            LookingPositionTracker.lookingAtUpperHalf = true;
            sneakBreak(context, POS);
        } finally {
            resetTracker();
        }

        expectState(context, POS, Blocks.STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM));
        context.succeed();
    }

    /** 下半分を見てスニーク破壊 → 下（石）が消え、上のオークが上付きハーフとして残る */
    @GameTest(template = EMPTY_STRUCTURE)
    public void sneakBreakLowerHalf(GameTestHelper context) {
        context.setBlock(POS, Blocks.STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM));
        placeItemOn(context, Blocks.OAK_SLAB, POS, Direction.UP, new Vec3(0.5, 0.5, 0.5));
        expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);

        try {
            resetTracker();
            LookingPositionTracker.lookingAtUpperHalf = false;
            sneakBreak(context, POS);
        } finally {
            resetTracker();
        }

        expectState(context, POS, Blocks.OAK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP));
        context.succeed();
    }

    /** 縦（X軸）で東半分を見てスニーク破壊 → 東（石）が消え、西のオークが西向きで残る */
    @GameTest(template = EMPTY_STRUCTURE)
    public void sneakBreakEasternHalf(GameTestHelper context) {
        context.setBlock(POS, ModBlocks.STONE_VERTICAL_SLAB.defaultBlockState().setValue(VerticalSlabBlock.FACING, Direction.EAST));
        placeItemOn(context, ModBlocks.OAK_VERTICAL_SLAB, POS, Direction.WEST, new Vec3(0.5, 0.5, 0.5));
        expectDouble(context, POS, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK);

        try {
            resetTracker();
            LookingPositionTracker.lookingAtEasternHalf = true;
            sneakBreak(context, POS);
        } finally {
            resetTracker();
        }

        context.assertBlockPresent(ModBlocks.OAK_VERTICAL_SLAB, POS);
        context.assertTrue(context.getBlockState(POS).getValue(VerticalSlabBlock.FACING) == Direction.WEST, "西向きで残るはず");
        context.succeed();
    }

    /** スニークせずに壊すと、両方まとめて壊れる */
    @GameTest(template = EMPTY_STRUCTURE)
    public void normalBreakRemovesBoth(GameTestHelper context) {
        context.setBlock(POS, Blocks.STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM));
        placeItemOn(context, Blocks.OAK_SLAB, POS, Direction.UP, new Vec3(0.5, 0.5, 0.5));
        expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);

        ServerPlayer player = context.makeMockServerPlayerInLevel();
        player.setShiftKeyDown(false);
        player.gameMode.destroyBlock(context.absolutePos(POS));

        context.assertBlockPresent(Blocks.AIR, POS);
        context.succeed();
    }
}
