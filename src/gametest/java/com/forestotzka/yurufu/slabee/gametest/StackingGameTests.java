package com.forestotzka.yurufu.slabee.gametest;

import com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.VerticalSlabBlock;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.test.GameTest;
import net.minecraft.test.GameTestException;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import static com.forestotzka.yurufu.slabee.gametest.SlabeeTestUtil.*;

/**
 * 異種ハーフの重ね置きと保存の回帰テスト。
 * 「今の動き」を記録するためのテストで、仕様の良し悪しは判断していない。
 */
public class StackingGameTests implements FabricGameTest {
    private static final BlockPos POS = new BlockPos(1, 1, 1);

    /** 下付きの石ハーフの上面をクリックしてオークのハーフを置く → 上=オーク、下=石 */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void stackOnBottomSlab(TestContext context) {
        context.setBlockState(POS, Blocks.STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM));
        placeItemOn(context, Blocks.OAK_SLAB, POS, Direction.UP, new Vec3d(0.5, 0.5, 0.5));

        AbstractDoubleSlabBlockEntity entity = expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);
        expectHalves(context, entity, Blocks.OAK_SLAB, Blocks.STONE_SLAB);
        context.complete();
    }

    /** 上付きの石ハーフの下面をクリックしてオークのハーフを置く → 上=石、下=オーク */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void stackUnderTopSlab(TestContext context) {
        context.setBlockState(POS, Blocks.STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP));
        placeItemOn(context, Blocks.OAK_SLAB, POS, Direction.DOWN, new Vec3d(0.5, 0.5, 0.5));

        AbstractDoubleSlabBlockEntity entity = expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);
        expectHalves(context, entity, Blocks.STONE_SLAB, Blocks.OAK_SLAB);
        context.complete();
    }

    /** Slabeeが追加したハーフ（ガラス）を石ハーフに重ねる */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void stackModSlabOnVanillaSlab(TestContext context) {
        context.setBlockState(POS, Blocks.STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM));
        placeItemOn(context, ModBlocks.GLASS_SLAB, POS, Direction.UP, new Vec3d(0.5, 0.5, 0.5));

        AbstractDoubleSlabBlockEntity entity = expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);
        expectHalves(context, entity, ModBlocks.GLASS_SLAB, Blocks.STONE_SLAB);
        context.complete();
    }

    /** 東向きの石の縦ハーフの西面をクリックしてオークの縦ハーフを置く → X軸、東(positive)=石、西(negative)=オーク */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void stackVerticalSlabsOnXAxis(TestContext context) {
        context.setBlockState(POS, ModBlocks.STONE_VERTICAL_SLAB.getDefaultState().with(VerticalSlabBlock.FACING, Direction.EAST));
        placeItemOn(context, ModBlocks.OAK_VERTICAL_SLAB, POS, Direction.WEST, new Vec3d(0.5, 0.5, 0.5));

        AbstractDoubleSlabBlockEntity entity = expectDouble(context, POS, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK);
        expectHalves(context, entity, ModBlocks.STONE_VERTICAL_SLAB, ModBlocks.OAK_VERTICAL_SLAB);
        context.assertTrue(((DoubleVerticalSlabBlockEntity) entity).isX(), "X軸のはず");
        context.complete();
    }

    /** 北向きの石の縦ハーフの南面をクリックしてオークの縦ハーフを置く → Z軸、南(positive)=オーク、北(negative)=石 */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void stackVerticalSlabsOnZAxis(TestContext context) {
        context.setBlockState(POS, ModBlocks.STONE_VERTICAL_SLAB.getDefaultState().with(VerticalSlabBlock.FACING, Direction.NORTH));
        placeItemOn(context, ModBlocks.OAK_VERTICAL_SLAB, POS, Direction.SOUTH, new Vec3d(0.5, 0.5, 0.5));

        AbstractDoubleSlabBlockEntity entity = expectDouble(context, POS, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK);
        expectHalves(context, entity, ModBlocks.OAK_VERTICAL_SLAB, ModBlocks.STONE_VERTICAL_SLAB);
        context.assertTrue(!((DoubleVerticalSlabBlockEntity) entity).isX(), "Z軸のはず");
        context.complete();
    }

    /** ダブルハーフの中身が、保存（NBT）→読み込みで変わらない */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void doubleSlabSurvivesNbtRoundTrip(TestContext context) {
        context.setBlockState(POS, Blocks.STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM));
        placeItemOn(context, Blocks.OAK_SLAB, POS, Direction.UP, new Vec3d(0.5, 0.5, 0.5));
        AbstractDoubleSlabBlockEntity original = expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);

        RegistryWrapper.WrapperLookup registries = context.getWorld().getRegistryManager();
        NbtCompound nbt = original.createNbtWithIdentifyingData(registries);
        BlockState state = context.getBlockState(POS);
        BlockEntity copy = BlockEntity.createFromNbt(context.getAbsolutePos(POS), state, nbt, registries);

        if (!(copy instanceof AbstractDoubleSlabBlockEntity restored)) {
            throw new GameTestException("NBTから復元できなかった: " + nbt);
        }
        expectHalves(context, restored, Blocks.OAK_SLAB, Blocks.STONE_SLAB);
        context.complete();
    }

    /** 縦のダブルハーフも、保存→読み込みで軸と中身が変わらない */
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void doubleVerticalSlabSurvivesNbtRoundTrip(TestContext context) {
        context.setBlockState(POS, ModBlocks.STONE_VERTICAL_SLAB.getDefaultState().with(VerticalSlabBlock.FACING, Direction.NORTH));
        placeItemOn(context, ModBlocks.OAK_VERTICAL_SLAB, POS, Direction.SOUTH, new Vec3d(0.5, 0.5, 0.5));
        AbstractDoubleSlabBlockEntity original = expectDouble(context, POS, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK);

        RegistryWrapper.WrapperLookup registries = context.getWorld().getRegistryManager();
        NbtCompound nbt = original.createNbtWithIdentifyingData(registries);
        BlockEntity copy = BlockEntity.createFromNbt(context.getAbsolutePos(POS), context.getBlockState(POS), nbt, registries);

        if (!(copy instanceof DoubleVerticalSlabBlockEntity restored)) {
            throw new GameTestException("NBTから復元できなかった: " + nbt);
        }
        expectHalves(context, restored, ModBlocks.OAK_VERTICAL_SLAB, ModBlocks.STONE_VERTICAL_SLAB);
        context.assertTrue(!restored.isX(), "Z軸のはず");
        context.complete();
    }
}
