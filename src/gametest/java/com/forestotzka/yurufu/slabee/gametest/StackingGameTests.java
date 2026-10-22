package com.forestotzka.yurufu.slabee.gametest;

import com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.VerticalSlabBlock;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.Vec3;

import static com.forestotzka.yurufu.slabee.gametest.SlabeeTestUtil.*;

/**
 * 異種ハーフの重ね置きと保存の回帰テスト。
 * 「今の動き」を記録するためのテストで、仕様の良し悪しは判断していない。
 */
public class StackingGameTests implements FabricGameTest {
    private static final BlockPos POS = new BlockPos(1, 1, 1);

    /** 下付きの石ハーフの上面をクリックしてオークのハーフを置く → 上=オーク、下=石 */
    @GameTest(template = EMPTY_STRUCTURE)
    public void stackOnBottomSlab(GameTestHelper context) {
        context.setBlock(POS, Blocks.STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM));
        placeItemOn(context, Blocks.OAK_SLAB, POS, Direction.UP, new Vec3(0.5, 0.5, 0.5));

        AbstractDoubleSlabBlockEntity entity = expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);
        expectHalves(context, entity, Blocks.OAK_SLAB, Blocks.STONE_SLAB);
        context.succeed();
    }

    /** 上付きの石ハーフの下面をクリックしてオークのハーフを置く → 上=石、下=オーク */
    @GameTest(template = EMPTY_STRUCTURE)
    public void stackUnderTopSlab(GameTestHelper context) {
        context.setBlock(POS, Blocks.STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP));
        placeItemOn(context, Blocks.OAK_SLAB, POS, Direction.DOWN, new Vec3(0.5, 0.5, 0.5));

        AbstractDoubleSlabBlockEntity entity = expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);
        expectHalves(context, entity, Blocks.STONE_SLAB, Blocks.OAK_SLAB);
        context.succeed();
    }

    /** Slabeeが追加したハーフ（ガラス）を石ハーフに重ねる */
    @GameTest(template = EMPTY_STRUCTURE)
    public void stackModSlabOnVanillaSlab(GameTestHelper context) {
        context.setBlock(POS, Blocks.STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM));
        placeItemOn(context, ModBlocks.GLASS_SLAB, POS, Direction.UP, new Vec3(0.5, 0.5, 0.5));

        AbstractDoubleSlabBlockEntity entity = expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);
        expectHalves(context, entity, ModBlocks.GLASS_SLAB, Blocks.STONE_SLAB);
        context.succeed();
    }

    /** 東向きの石の縦ハーフの西面をクリックしてオークの縦ハーフを置く → X軸、東(positive)=石、西(negative)=オーク */
    @GameTest(template = EMPTY_STRUCTURE)
    public void stackVerticalSlabsOnXAxis(GameTestHelper context) {
        context.setBlock(POS, ModBlocks.STONE_VERTICAL_SLAB.defaultBlockState().setValue(VerticalSlabBlock.FACING, Direction.EAST));
        placeItemOn(context, ModBlocks.OAK_VERTICAL_SLAB, POS, Direction.WEST, new Vec3(0.5, 0.5, 0.5));

        AbstractDoubleSlabBlockEntity entity = expectDouble(context, POS, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK);
        expectHalves(context, entity, ModBlocks.STONE_VERTICAL_SLAB, ModBlocks.OAK_VERTICAL_SLAB);
        context.assertTrue(((DoubleVerticalSlabBlockEntity) entity).isX(), "X軸のはず");
        context.succeed();
    }

    /** 北向きの石の縦ハーフの南面をクリックしてオークの縦ハーフを置く → Z軸、南(positive)=オーク、北(negative)=石 */
    @GameTest(template = EMPTY_STRUCTURE)
    public void stackVerticalSlabsOnZAxis(GameTestHelper context) {
        context.setBlock(POS, ModBlocks.STONE_VERTICAL_SLAB.defaultBlockState().setValue(VerticalSlabBlock.FACING, Direction.NORTH));
        placeItemOn(context, ModBlocks.OAK_VERTICAL_SLAB, POS, Direction.SOUTH, new Vec3(0.5, 0.5, 0.5));

        AbstractDoubleSlabBlockEntity entity = expectDouble(context, POS, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK);
        expectHalves(context, entity, ModBlocks.OAK_VERTICAL_SLAB, ModBlocks.STONE_VERTICAL_SLAB);
        context.assertTrue(!((DoubleVerticalSlabBlockEntity) entity).isX(), "Z軸のはず");
        context.succeed();
    }

    /** ダブルハーフの中身が、保存（NBT）→読み込みで変わらない */
    @GameTest(template = EMPTY_STRUCTURE)
    public void doubleSlabSurvivesNbtRoundTrip(GameTestHelper context) {
        context.setBlock(POS, Blocks.STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM));
        placeItemOn(context, Blocks.OAK_SLAB, POS, Direction.UP, new Vec3(0.5, 0.5, 0.5));
        AbstractDoubleSlabBlockEntity original = expectDouble(context, POS, ModBlocks.DOUBLE_SLAB_BLOCK);

        HolderLookup.Provider registries = context.getLevel().registryAccess();
        CompoundTag nbt = original.saveWithFullMetadata(registries);
        BlockState state = context.getBlockState(POS);
        BlockEntity copy = BlockEntity.loadStatic(context.absolutePos(POS), state, nbt, registries);

        if (!(copy instanceof AbstractDoubleSlabBlockEntity restored)) {
            throw new GameTestAssertException("NBTから復元できなかった: " + nbt);
        }
        expectHalves(context, restored, Blocks.OAK_SLAB, Blocks.STONE_SLAB);
        context.succeed();
    }

    /** 縦のダブルハーフも、保存→読み込みで軸と中身が変わらない */
    @GameTest(template = EMPTY_STRUCTURE)
    public void doubleVerticalSlabSurvivesNbtRoundTrip(GameTestHelper context) {
        context.setBlock(POS, ModBlocks.STONE_VERTICAL_SLAB.defaultBlockState().setValue(VerticalSlabBlock.FACING, Direction.NORTH));
        placeItemOn(context, ModBlocks.OAK_VERTICAL_SLAB, POS, Direction.SOUTH, new Vec3(0.5, 0.5, 0.5));
        AbstractDoubleSlabBlockEntity original = expectDouble(context, POS, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK);

        HolderLookup.Provider registries = context.getLevel().registryAccess();
        CompoundTag nbt = original.saveWithFullMetadata(registries);
        BlockEntity copy = BlockEntity.loadStatic(context.absolutePos(POS), context.getBlockState(POS), nbt, registries);

        if (!(copy instanceof DoubleVerticalSlabBlockEntity restored)) {
            throw new GameTestAssertException("NBTから復元できなかった: " + nbt);
        }
        expectHalves(context, restored, ModBlocks.OAK_VERTICAL_SLAB, ModBlocks.STONE_VERTICAL_SLAB);
        context.assertTrue(!restored.isX(), "Z軸のはず");
        context.succeed();
    }
}
