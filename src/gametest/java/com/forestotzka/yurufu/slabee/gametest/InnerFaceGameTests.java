package com.forestotzka.yurufu.slabee.gametest;

import com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.ModBlockMap;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import java.util.ArrayList;
import java.util.List;

import static com.forestotzka.yurufu.slabee.gametest.SlabeeTestUtil.placeDouble;

/**
 * ダブルハーフの内側の面（上下のハーフが接している面）を描くかどうかが、
 * 「同じ並びを通常ブロックで作ったとき」と同じになることを確かめる（MODの基本方針）。
 * 正解は、その場で通常ブロックを実際に置いて Block#shouldDrawSide で求める。
 */
public class InnerFaceGameTests implements FabricGameTest {
    private static final Block[] FULL_BLOCKS = {
            Blocks.STONE, Blocks.OAK_PLANKS, Blocks.GLOWSTONE, Blocks.OAK_LEAVES, Blocks.BIRCH_LEAVES,
            Blocks.GLASS, Blocks.RED_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS, Blocks.TINTED_GLASS, Blocks.MAGMA_BLOCK,
    };

    @GameTest(template = EMPTY_STRUCTURE)
    public void innerFacesMatchFullBlocks(GameTestHelper context) {
        BlockPos full = new BlockPos(1, 1, 1);
        BlockPos dbl = new BlockPos(3, 1, 1);
        BlockPos fullAbs = context.absolutePos(full);
        BlockPos dblAbs = context.absolutePos(dbl);
        List<String> mismatches = new ArrayList<>();
        int checked = 0;

        for (Block bottomFull : FULL_BLOCKS) {
            for (Block topFull : FULL_BLOCKS) {
                Block bottomSlab = ModBlockMap.originalToSlab(bottomFull);
                Block topSlab = ModBlockMap.originalToSlab(topFull);
                if (bottomSlab == null || topSlab == null) continue;

                // 正解：通常ブロックを上下に置いたときの、接している面
                context.setBlock(full, bottomFull);
                context.setBlock(full.above(), topFull);
                boolean expectBottomUp = Block.shouldRenderFace(bottomFull.defaultBlockState(), context.getLevel(), fullAbs, Direction.UP, fullAbs.above());
                boolean expectTopDown = Block.shouldRenderFace(topFull.defaultBlockState(), context.getLevel(), fullAbs.above(), Direction.DOWN, fullAbs);

                // ダブルハーフ
                placeDouble(context, dbl, topSlab, bottomSlab);
                if (!(context.getLevel().getBlockEntity(dblAbs) instanceof AbstractDoubleSlabBlockEntity entity)) {
                    mismatches.add("ダブルハーフを置けない: " + id(topSlab) + " / " + id(bottomSlab));
                    continue;
                }
                BlockState bottomHalf = entity.getNegativeSlabState();
                BlockState topHalf = entity.getPositiveSlabState();
                boolean actualBottomUp = Block.shouldRenderFace(bottomHalf, context.getLevel(), dblAbs, Direction.UP, dblAbs.above());
                boolean actualTopDown = Block.shouldRenderFace(topHalf, context.getLevel(), dblAbs, Direction.DOWN, dblAbs.below());

                checked++;
                if (actualBottomUp != expectBottomUp) {
                    mismatches.add("上=" + id(topFull) + " 下=" + id(bottomFull) + "：下の上面 通常=" + expectBottomUp + " ダブル=" + actualBottomUp);
                }
                if (actualTopDown != expectTopDown) {
                    mismatches.add("上=" + id(topFull) + " 下=" + id(bottomFull) + "：上の下面 通常=" + expectTopDown + " ダブル=" + actualTopDown);
                }
            }
        }

        context.assertTrue(checked > 50, "比べられた組み合わせが少なすぎる: " + checked);
        context.assertTrue(mismatches.isEmpty(),
                "通常ブロックと違う面 " + mismatches.size() + " 件（" + checked + " 組中）:\n" + String.join("\n", mismatches));
        context.succeed();
    }

    private static String id(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }
}
