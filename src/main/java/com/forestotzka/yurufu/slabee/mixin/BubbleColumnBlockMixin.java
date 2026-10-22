package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.block.DoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BubbleColumnBlock.class)
public abstract class BubbleColumnBlockMixin {
    @Final
    @Shadow
    public static BooleanProperty DRAG_DOWN;

    @Inject(method = "updateColumn(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;)V", at = @At(value = "HEAD"), cancellable = true)
    private static void update(LevelAccessor world, BlockPos pos, BlockState water, BlockState bubbleSource, CallbackInfo ci) {
        if (BubbleColumnBlockInvoker.isStillWater(water)) {
            BlockState blockState = getBubbleState(world, pos.below(), bubbleSource);
            world.setBlock(pos, blockState, 2);
            BlockPos.MutableBlockPos mutable = pos.mutable().move(Direction.UP);

            while (BubbleColumnBlockInvoker.isStillWater(world.getBlockState(mutable))) {
                if (!world.setBlock(mutable, blockState, 2)) {
                    ci.cancel();
                    return;
                }

                mutable.move(Direction.UP);
            }

            ci.cancel();
        }
    }

    @Unique
    private static BlockState getBubbleState(LevelAccessor world, BlockPos pos, BlockState state) {
        if (state.is(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity entity) {
            Block block = entity.getPositiveSlabState().getBlock();
            if (block == ModBlocks.SOUL_SAND_SLAB) {
                return Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(DRAG_DOWN, false);
            } else if (block == ModBlocks.MAGMA_BLOCK_SLAB) {
                return Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(DRAG_DOWN, true);
            } else {
                return Blocks.WATER.defaultBlockState();
            }
        } else if (state.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(pos) instanceof DoubleVerticalSlabBlockEntity entity) {
            Block blockPositive = entity.getPositiveSlabState().getBlock();
            Block blockNegative = entity.getNegativeSlabState().getBlock();
            if (blockPositive == ModBlocks.SOUL_SAND_VERTICAL_SLAB || blockNegative == ModBlocks.SOUL_SAND_VERTICAL_SLAB) {
                return Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(DRAG_DOWN, false);
            } else if (blockPositive == ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB || blockNegative == ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB) {
                return Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(DRAG_DOWN, true);
            } else {
                return Blocks.WATER.defaultBlockState();
            }
        } else if (state.is(Blocks.BUBBLE_COLUMN)) {
            return state;
        } else if (state.is(Blocks.SOUL_SAND) || state.is(ModBlocks.SOUL_SAND_SLAB) || state.is(ModBlocks.SOUL_SAND_VERTICAL_SLAB)) {
            return Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(DRAG_DOWN, false);
        } else if (state.is(Blocks.MAGMA_BLOCK) || state.is(ModBlocks.MAGMA_BLOCK_SLAB) || state.is(ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB)) {
            return Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(DRAG_DOWN, true);
        } else {
            return Blocks.WATER.defaultBlockState();
        }
    }
}
