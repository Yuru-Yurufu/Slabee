package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.block.*;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.forestotzka.yurufu.slabee.ClickPositionTracker;

import java.util.Objects;

import static com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlock.AXIS;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Shadow
    protected abstract void setDefaultState(BlockState state);

    @Shadow
    public abstract BlockState getDefaultState();

    @Inject(method = "getPickStack", at= @At("HEAD"), cancellable = true)
    public void getPickStack(WorldView world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> cir) {
        if (state.isOf(ModBlocks.DOUBLE_SLAB_BLOCK)) {
            Identifier slabId;
            DoubleSlabBlockEntity blockEntity = (DoubleSlabBlockEntity) world.getBlockEntity(pos);
            if (ClickPositionTracker.clickUpperHalf) {
                slabId = Objects.requireNonNull(blockEntity).getPositiveSlabId();
            } else {
                slabId = Objects.requireNonNull(blockEntity).getNegativeSlabId();
            }
            cir.setReturnValue(new ItemStack(Registries.ITEM.get(slabId)));
            cir.cancel();
        } else if (state.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            Identifier slabId;
            DoubleVerticalSlabBlockEntity blockEntity = (DoubleVerticalSlabBlockEntity) world.getBlockEntity(pos);
            if ((state.get(AXIS) == VerticalSlabAxis.X && ClickPositionTracker.clickEasternHalf) || (state.get(AXIS) == VerticalSlabAxis.Z && ClickPositionTracker.clickSouthernHalf)) {
                slabId = Objects.requireNonNull(blockEntity).getPositiveSlabId();
            } else {
                slabId = Objects.requireNonNull(blockEntity).getNegativeSlabId();
            }
            cir.setReturnValue(new ItemStack(Registries.ITEM.get(slabId)));
            cir.cancel();
        }
    }

    @Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true)
    private static void shouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos otherPos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(otherPos);
        if (blockState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK)) {
            if (side == Direction.UP) {
                cir.setReturnValue(DoubleSlabUtils.isNegativeSeeThrough(blockState));
            } else if (side == Direction.DOWN) {
                cir.setReturnValue(DoubleSlabUtils.isPositiveSeeThrough(blockState));
            } else {
                cir.setReturnValue(DoubleSlabUtils.isPositiveSeeThrough(blockState) || DoubleSlabUtils.isNegativeSeeThrough(blockState));
            }
            cir.cancel();
        } else if (blockState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            if (blockState.get(AXIS) == VerticalSlabAxis.X) {
                if (side == Direction.EAST) {
                    cir.setReturnValue(DoubleSlabUtils.isNegativeSeeThrough(blockState));
                } else if (side == Direction.WEST) {
                    cir.setReturnValue(DoubleSlabUtils.isPositiveSeeThrough(blockState));
                } else {
                    cir.setReturnValue(DoubleSlabUtils.isPositiveSeeThrough(blockState) || DoubleSlabUtils.isNegativeSeeThrough(blockState));
                }
            } else {
                if (side == Direction.SOUTH) {
                    cir.setReturnValue(DoubleSlabUtils.isNegativeSeeThrough(blockState));
                } else if (side == Direction.NORTH) {
                    cir.setReturnValue(DoubleSlabUtils.isPositiveSeeThrough(blockState));
                } else {
                    cir.setReturnValue(DoubleSlabUtils.isPositiveSeeThrough(blockState) || DoubleSlabUtils.isNegativeSeeThrough(blockState));
                }
            }
            cir.cancel();
        }
    }
}
