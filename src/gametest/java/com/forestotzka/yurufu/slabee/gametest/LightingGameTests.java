package com.forestotzka.yurufu.slabee.gametest;

import com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlock;
import com.forestotzka.yurufu.slabee.block.ModBlockMap;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.VerticalSlabBlock;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.registry.Registries;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;

import java.util.ArrayList;
import java.util.List;

import static com.forestotzka.yurufu.slabee.gametest.SlabeeTestUtil.placeDouble;
import static com.forestotzka.yurufu.slabee.gametest.SlabeeTestUtil.placeItemOn;

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

    /**
     * 縦のダブルハーフ（北=グロウストーン、南=オーク）は、南へ直接光を出さない。
     * 置く順番で結果が変わらないこと（あやの報告 2026-10-24：グロウストーンを先に置くと南に光が残る）。
     */
    @GameTest(templateName = EMPTY_STRUCTURE, tickLimit = 100)
    public void verticalDoubleBlocksLightWhenGlowstonePlacedFirst(TestContext context) {
        BlockPos pos = new BlockPos(2, 2, 2);
        Block glowstone = ModBlockMap.slabToVerticalSlab(ModBlockMap.originalToSlab(Blocks.GLOWSTONE));
        context.setBlockState(pos, glowstone.getDefaultState().with(VerticalSlabBlock.FACING, Direction.NORTH));
        context.waitAndRun(10, () -> {
            placeItemOn(context, ModBlocks.OAK_VERTICAL_SLAB, pos, Direction.SOUTH, new Vec3d(0.5, 0.5, 0.5));
            context.waitAndRun(20, () -> expectNoDirectLightSouth(context, pos));
        });
    }

    @GameTest(templateName = EMPTY_STRUCTURE, tickLimit = 100)
    public void verticalDoubleBlocksLightWhenOakPlacedFirst(TestContext context) {
        BlockPos pos = new BlockPos(2, 2, 2);
        Block glowstone = ModBlockMap.slabToVerticalSlab(ModBlockMap.originalToSlab(Blocks.GLOWSTONE));
        context.setBlockState(pos, ModBlocks.OAK_VERTICAL_SLAB.getDefaultState().with(VerticalSlabBlock.FACING, Direction.SOUTH));
        context.waitAndRun(10, () -> {
            placeItemOn(context, glowstone, pos, Direction.NORTH, new Vec3d(0.5, 0.5, 0.5));
            context.waitAndRun(20, () -> expectNoDirectLightSouth(context, pos));
        });
    }

    /** 横のダブルハーフも同じ：下=グロウストーンを先に置き、上に石を重ねたら、上へ直接光が出ない */
    @GameTest(templateName = EMPTY_STRUCTURE, tickLimit = 100)
    public void doubleSlabBlocksLightWhenGlowstonePlacedFirst(TestContext context) {
        BlockPos pos = new BlockPos(2, 2, 2);
        Block glowstone = ModBlockMap.originalToSlab(Blocks.GLOWSTONE);
        context.setBlockState(pos, glowstone.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM));
        context.waitAndRun(10, () -> {
            placeItemOn(context, Blocks.STONE_SLAB, pos, Direction.UP, new Vec3d(0.5, 0.5, 0.5));
            context.waitAndRun(20, () -> {
                context.expectBlock(ModBlocks.DOUBLE_SLAB_BLOCK, pos);
                int up = blockLight(context, pos.up());
                context.assertTrue(up <= 12, "上に直接光が残っている（12以下のはず）: " + up);
                context.complete();
            });
        });
    }

    private static void expectNoDirectLightSouth(TestContext context, BlockPos pos) {
        context.expectBlock(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK, pos);
        int south = blockLight(context, pos.south());
        int north = blockLight(context, pos.north());
        context.assertTrue(north == 14, "北の明るさが14ではない: " + north);
        context.assertTrue(south <= 12, "南に直接光が残っている（12以下のはず）: " + south);
        context.complete();
    }

    private static int blockLight(TestContext context, BlockPos relPos) {
        return context.getWorld().getLightLevel(LightType.BLOCK, context.getAbsolutePos(relPos));
    }
}
