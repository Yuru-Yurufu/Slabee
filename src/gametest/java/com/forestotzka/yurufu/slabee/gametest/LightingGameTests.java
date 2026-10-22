package com.forestotzka.yurufu.slabee.gametest;

import com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlock;
import com.forestotzka.yurufu.slabee.block.ModBlockMap;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.registry.Registries;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;

import java.util.ArrayList;
import java.util.List;

import static com.forestotzka.yurufu.slabee.gametest.SlabeeTestUtil.placeDouble;

/**
 * ダブルハーフの明るさ（あやの決定 2026-10-23：2案目）
 * - 明るさは、元のブロック（単体のハーフ）と同じ
 * - 光らない側のハーフがある方向には光が抜けない（通常ブロックを並べたときと同じ）
 */
public class LightingGameTests implements FabricGameTest {

    /** 全種類のハーフについて、重ねたときの明るさ = 単体のハーフの明るさ（石と重ねた場合） */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void doubleSlabLuminanceMatchesSingleHalf(TestContext context) {
        BlockPos pos = new BlockPos(1, 1, 1);
        List<String> mismatches = new ArrayList<>();
        for (Block slab : Registries.BLOCK) {
            if (!(slab instanceof SlabBlock) || ModBlockMap.slabToVerticalSlab(slab) == null) continue;
            int expected = slab.getDefaultState().getLuminance();
            placeDouble(context, pos, slab, Blocks.STONE_SLAB);
            BlockState state = context.getBlockState(pos);
            int actual = state.get(AbstractDoubleSlabBlock.LIGHT_LEVEL);
            if (actual != expected) {
                mismatches.add(Registries.BLOCK.getId(slab) + " 単体=" + expected + " 重ねたとき=" + actual);
            }
        }
        context.assertTrue(mismatches.isEmpty(),
                "明るさが単体と違うハーフ " + mismatches.size() + " 種類:\n" + String.join("\n", mismatches));
        context.complete();
    }

    /**
     * 上=石、下=グロウストーンのダブルハーフ：
     * 横と下には光が出るが、上には直接出ない（横に回り込んだ分だけ届く）。
     */
    @GameTest(templateName = EMPTY_STRUCTURE, tickLimit = 100)
    public void opaqueTopHalfBlocksLightGoingUp(TestContext context) {
        BlockPos pos = new BlockPos(2, 2, 2);
        placeDouble(context, pos, Blocks.STONE_SLAB, ModBlockMap.originalToSlab(Blocks.GLOWSTONE));
        context.waitAndRun(20, () -> {
            int side = blockLight(context, pos.east());
            int down = blockLight(context, pos.down());
            int up = blockLight(context, pos.up());
            context.assertTrue(side == 14, "横の明るさが14ではない: " + side);
            context.assertTrue(down == 14, "下の明るさが14ではない: " + down);
            context.assertTrue(up <= 12, "上に直接光が抜けている（12以下のはず）: " + up);
            context.complete();
        });
    }

    /** 上=グロウストーン、下=石：上と横には出るが、下には直接出ない */
    @GameTest(templateName = EMPTY_STRUCTURE, tickLimit = 100)
    public void opaqueBottomHalfBlocksLightGoingDown(TestContext context) {
        BlockPos pos = new BlockPos(2, 2, 2);
        placeDouble(context, pos, ModBlockMap.originalToSlab(Blocks.GLOWSTONE), Blocks.STONE_SLAB);
        context.waitAndRun(20, () -> {
            int side = blockLight(context, pos.east());
            int up = blockLight(context, pos.up());
            int down = blockLight(context, pos.down());
            context.assertTrue(side == 14, "横の明るさが14ではない: " + side);
            context.assertTrue(up == 14, "上の明るさが14ではない: " + up);
            context.assertTrue(down <= 12, "下に直接光が抜けている（12以下のはず）: " + down);
            context.complete();
        });
    }

    private static int blockLight(TestContext context, BlockPos relPos) {
        return context.getWorld().getLightLevel(LightType.BLOCK, context.getAbsolutePos(relPos));
    }
}
