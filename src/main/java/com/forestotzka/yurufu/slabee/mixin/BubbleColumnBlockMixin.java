package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.block.DoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
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
    public static BooleanProperty DRAG;

    @Inject(method = "update(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;)V", at = @At(value = "HEAD"), cancellable = true)
    private static void update(WorldAccess world, BlockPos pos, BlockState water, BlockState bubbleSource, CallbackInfo ci) {
        if (BubbleColumnBlockInvoker.isStillWater(water)) {
            BlockState blockState = getBubbleState(world, pos.down(), bubbleSource);
            world.setBlockState(pos, blockState, 2);
            BlockPos.Mutable mutable = pos.mutableCopy().move(Direction.UP);

            while (BubbleColumnBlockInvoker.isStillWater(world.getBlockState(mutable))) {
                if (!world.setBlockState(mutable, blockState, 2)) {
                    ci.cancel();
                    return;
                }

                mutable.move(Direction.UP);
            }

            ci.cancel();
        }
    }

    @Unique
    private static BlockState getBubbleState(WorldAccess world, BlockPos pos, BlockState state) {
        if (state.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity entity) {
            Block block = entity.getPositiveSlabState().getBlock();
            if (block == ModBlocks.SOUL_SAND_SLAB) {
                return Blocks.BUBBLE_COLUMN.getDefaultState().with(DRAG, false);
            } else if (block == ModBlocks.MAGMA_BLOCK_SLAB) {
                return Blocks.BUBBLE_COLUMN.getDefaultState().with(DRAG, true);
            } else {
                return Blocks.WATER.getDefaultState();
            }
        } else if (state.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(pos) instanceof DoubleVerticalSlabBlockEntity entity) {
            Block blockPositive = entity.getPositiveSlabState().getBlock();
            Block blockNegative = entity.getNegativeSlabState().getBlock();
            if (blockPositive == ModBlocks.SOUL_SAND_VERTICAL_SLAB || blockNegative == ModBlocks.SOUL_SAND_VERTICAL_SLAB) {
                return Blocks.BUBBLE_COLUMN.getDefaultState().with(DRAG, false);
            } else if (blockPositive == ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB || blockNegative == ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB) {
                return Blocks.BUBBLE_COLUMN.getDefaultState().with(DRAG, true);
            } else {
                return Blocks.WATER.getDefaultState();
            }
        } else if (state.isOf(Blocks.BUBBLE_COLUMN)) {
            return state;
        } else if (state.isOf(Blocks.SOUL_SAND) || state.isOf(ModBlocks.SOUL_SAND_SLAB) || state.isOf(ModBlocks.SOUL_SAND_VERTICAL_SLAB)) {
            return Blocks.BUBBLE_COLUMN.getDefaultState().with(DRAG, false);
        } else if (state.isOf(Blocks.MAGMA_BLOCK) || state.isOf(ModBlocks.MAGMA_BLOCK_SLAB) || state.isOf(ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB)) {
            return Blocks.BUBBLE_COLUMN.getDefaultState().with(DRAG, true);
        } else {
            return Blocks.WATER.getDefaultState();
        }
    }
}
