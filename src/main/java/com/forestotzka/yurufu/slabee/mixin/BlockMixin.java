package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.SlabeeAccessor;
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
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
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

            world.setBlockState(pos, ModBlocks.DOUBLE_SLAB_BLOCK.getDefaultState(), 3);

            DoubleSlabBlockEntity blockEntity = (DoubleSlabBlockEntity) world.getBlockEntity(pos);
            if (slabeeAccessor.slabee$getBottomFirst(state)) {
                Objects.requireNonNull(blockEntity).setTopSlabId(Identifier.of(second_slab));
                Objects.requireNonNull(blockEntity).setBottomSlabId(Identifier.of(first_slab));
            } else {
                Objects.requireNonNull(blockEntity).setTopSlabId(Identifier.of(first_slab));
                Objects.requireNonNull(blockEntity).setBottomSlabId(Identifier.of(second_slab));
            }

            blockEntity.updateBlockProperties();
            blockEntity.markDirty();
            world.updateListeners(pos, blockEntity.getCachedState(), blockEntity.getBlockState(),3);
        } else if (state.getBlock() instanceof VerticalSlabBlock && state.get(ModProperties.IS_DOUBLE)) {
            String axis;
            String first_slab = state.getBlock().toString();
            first_slab = first_slab.substring(first_slab.indexOf('{') + 1, first_slab.indexOf('}'));
            String second_slab = itemStack.getItem().toString();

            if (state.get(HorizontalFacingBlock.FACING) == Direction.EAST || state.get(HorizontalFacingBlock.FACING) == Direction.WEST) {
                axis = "x";
            } else {
                axis = "z";
            }

            world.setBlockState(pos, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK.getDefaultState().with(AXIS, VerticalSlabAxis.fromString(axis)), 3);

            DoubleVerticalSlabBlockEntity blockEntity = (DoubleVerticalSlabBlockEntity) world.getBlockEntity(pos);
            Objects.requireNonNull(blockEntity).setAxis(axis);
            if (state.get(HorizontalFacingBlock.FACING) == Direction.SOUTH || state.get(HorizontalFacingBlock.FACING) == Direction.EAST) {
                Objects.requireNonNull(blockEntity).setPositiveSlabId(Identifier.of(first_slab));
                Objects.requireNonNull(blockEntity).setNegativeSlabId(Identifier.of(second_slab));
            } else {
                Objects.requireNonNull(blockEntity).setPositiveSlabId(Identifier.of(second_slab));
                Objects.requireNonNull(blockEntity).setNegativeSlabId(Identifier.of(first_slab));
            }

            blockEntity.updateBlockProperties();
            blockEntity.markDirty();
            world.updateListeners(pos, blockEntity.getCachedState(), blockEntity.getCachedState(),3);
        }
    }

    @Inject(method = "getPickStack", at= @At("HEAD"), cancellable = true)
    public void getPickStack(WorldView world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> cir) {
        if (state.isOf(ModBlocks.DOUBLE_SLAB_BLOCK)) {
            Identifier slabId;
            DoubleSlabBlockEntity blockEntity = (DoubleSlabBlockEntity) world.getBlockEntity(pos);
            if (ClickPositionTracker.clickUpperHalf) {
                slabId = Objects.requireNonNull(blockEntity).getTopSlabId();
            } else {
                slabId = Objects.requireNonNull(blockEntity).getBottomSlabId();
            }
            cir.setReturnValue(new ItemStack(Registries.ITEM.get(slabId)));
            cir.cancel();
        } else if (state.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            Identifier slabId;
            DoubleVerticalSlabBlockEntity blockEntity= (DoubleVerticalSlabBlockEntity) world.getBlockEntity(pos);
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
        if (blockState.getBlock() instanceof DoubleSlabBlock) {
            cir.setReturnValue(!blockState.get(DoubleSlabBlock.DOWN_OPAQUE) || !blockState.get(DoubleSlabBlock.UP_OPAQUE));
            cir.cancel();
        } else if (blockState.getBlock() instanceof DoubleVerticalSlabBlock) {
            cir.setReturnValue(!blockState.get(DoubleVerticalSlabBlock.NEGATIVE_OPAQUE) || !blockState.get(DoubleVerticalSlabBlock.POSITIVE_OPAQUE));
            cir.cancel();
        }
    }
}
