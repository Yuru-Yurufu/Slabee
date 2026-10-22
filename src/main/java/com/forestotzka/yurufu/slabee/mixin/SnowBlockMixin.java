package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.block.DoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlock;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowLayerBlock.class)
public abstract class SnowBlockMixin {
    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void canPlaceAt(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockPos downPos = pos.below();
        BlockState blockState = world.getBlockState(downPos);

        if (blockState.is(ModBlocks.SOUL_SAND_SLAB) || blockState.is(ModBlocks.MUD_SLAB)) {
            cir.setReturnValue(blockState.getValue(SlabBlock.TYPE) != SlabType.BOTTOM);
            cir.cancel();
        } else if (blockState.is(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(downPos) instanceof DoubleSlabBlockEntity entity) {
            BlockState positiveState = entity.getPositiveSlabState();
            if (positiveState.is(ModBlocks.SOUL_SAND_SLAB) || positiveState.is(ModBlocks.MUD_SLAB)) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        } else if (blockState.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(downPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            VoxelShape shape = DoubleVerticalSlabBlock.getCollisionShapeFromPart(entity, false, false, entity.getPositiveSlabState().is(ModBlocks.DIRT_PATH_VERTICAL_SLAB), entity.getNegativeSlabState().is(ModBlocks.DIRT_PATH_VERTICAL_SLAB));
            cir.setReturnValue(Block.isFaceFull(shape, Direction.UP));
            cir.cancel();
        }
    }
}
