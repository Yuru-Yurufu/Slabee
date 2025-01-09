package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.SlabeeAccessor;
import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.*;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import com.forestotzka.yurufu.slabee.state.property.ModProperties;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

    @Inject(method = "onPlaced", at = @At("HEAD"))
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        if (state.getBlock() instanceof SlabBlock && state.get(Properties.SLAB_TYPE) == SlabType.DOUBLE) {
            SlabeeAccessor slabeeAccessor = (SlabeeAccessor) this;
            String first_slab = state.getBlock().toString();
            first_slab = first_slab.substring(first_slab.indexOf('{') + 1, first_slab.indexOf('}'));
            String second_slab = itemStack.getItem().toString();
            Identifier positiveSlabId;
            Identifier negativeSlabId;

            if (slabeeAccessor.slabee$getBottomFirst(state)) {
                positiveSlabId = Identifier.of(second_slab);
                negativeSlabId = Identifier.of(first_slab);
            } else {
                positiveSlabId = Identifier.of(first_slab);
                negativeSlabId = Identifier.of(second_slab);
            }

            Block positiveSlabBlock = Registries.BLOCK.get(positiveSlabId);
            Block negativeSlabBlock = Registries.BLOCK.get(negativeSlabId);

            world.setBlockState(pos, SlabeeUtils.getAbstractState(positiveSlabBlock, negativeSlabBlock), 3);

            DoubleSlabBlockEntity blockEntity = (DoubleSlabBlockEntity) world.getBlockEntity(pos);
            if (blockEntity != null) {
                blockEntity.setPositiveSlabId(positiveSlabId);
                blockEntity.setNegativeSlabId(negativeSlabId);
            }
        } else if (state.getBlock() instanceof VerticalSlabBlock && state.get(ModProperties.IS_DOUBLE)) {
            String axis;
            String first_slab = state.getBlock().toString();
            first_slab = first_slab.substring(first_slab.indexOf('{') + 1, first_slab.indexOf('}'));
            String second_slab = itemStack.getItem().toString();
            Identifier positiveSlabId;
            Identifier negativeSlabId;

            if (state.get(HorizontalFacingBlock.FACING)  == Direction.SOUTH || state.get(HorizontalFacingBlock.FACING) == Direction.EAST) {
                positiveSlabId = Identifier.of(first_slab);
                negativeSlabId = Identifier.of(second_slab);
            } else {
                positiveSlabId = Identifier.of(second_slab);
                negativeSlabId = Identifier.of(first_slab);
            }

            Block positiveSlabBlock = Registries.BLOCK.get(positiveSlabId);
            Block negativeSlabBlock = Registries.BLOCK.get(negativeSlabId);

            if (state.get(HorizontalFacingBlock.FACING) == Direction.EAST || state.get(HorizontalFacingBlock.FACING) == Direction.WEST) {
                axis = "x";
            } else {
                axis = "z";
            }

            world.setBlockState(pos, SlabeeUtils.getAbstractState(positiveSlabBlock, negativeSlabBlock).with(AXIS, VerticalSlabAxis.fromString(axis)), 3);

            DoubleVerticalSlabBlockEntity blockEntity = (DoubleVerticalSlabBlockEntity) world.getBlockEntity(pos);
            if (blockEntity != null) {
                blockEntity.setAxis(axis);
                blockEntity.setPositiveSlabId(positiveSlabId);
                blockEntity.setNegativeSlabId(negativeSlabId);
            }
        }
    }

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
        } else if (!state.isOf(blockState.getBlock()) && (blockState.isOf(ModBlocks.TINTED_GLASS_SLAB) || blockState.isOf(ModBlocks.TINTED_GLASS_VERTICAL_SLAB))) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
