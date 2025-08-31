package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.block.DoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlock;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowBlock.class)
public abstract class SnowBlockMixin {
    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    private void canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockPos downPos = pos.down();
        BlockState blockState = world.getBlockState(downPos);

        if (blockState.isOf(ModBlocks.SOUL_SAND_SLAB) || blockState.isOf(ModBlocks.MUD_SLAB)) {
            cir.setReturnValue(blockState.get(SlabBlock.TYPE) != SlabType.BOTTOM);
            cir.cancel();
        } else if (blockState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(downPos) instanceof DoubleSlabBlockEntity entity) {
            BlockState positiveState = entity.getPositiveSlabState();
            if (positiveState.isOf(ModBlocks.SOUL_SAND_SLAB) || positiveState.isOf(ModBlocks.MUD_SLAB)) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        } else if (blockState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(downPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            VoxelShape shape = DoubleVerticalSlabBlock.getCollisionShapeFromPart(entity, false, false, entity.getPositiveSlabState().isOf(ModBlocks.DIRT_PATH_VERTICAL_SLAB), entity.getNegativeSlabState().isOf(ModBlocks.DIRT_PATH_VERTICAL_SLAB));
            cir.setReturnValue(Block.isFaceFullSquare(shape, Direction.UP));
            cir.cancel();
        }
    }
}
