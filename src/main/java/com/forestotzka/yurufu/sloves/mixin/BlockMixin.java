package com.forestotzka.yurufu.sloves.mixin;

import com.forestotzka.yurufu.sloves.SlovesAccessor;
import com.forestotzka.yurufu.sloves.block.DoubleSlabBlockEntity;
import com.forestotzka.yurufu.sloves.block.DoubleVerticalSlabBlockEntity;
import com.forestotzka.yurufu.sloves.block.ModBlocks;
import com.forestotzka.yurufu.sloves.block.VerticalSlabBlock;
import com.forestotzka.yurufu.sloves.block.enums.VerticalSlabAxis;
import com.forestotzka.yurufu.sloves.state.property.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.forestotzka.yurufu.sloves.ClickPositionTracker;

import static com.forestotzka.yurufu.sloves.block.DoubleVerticalSlabBlock.AXIS;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Shadow
    protected abstract void setDefaultState(BlockState state);

    @Shadow
    public abstract BlockState getDefaultState();

    @Inject(method = "onPlaced", at = @At("HEAD"))
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        if (state.getBlock() instanceof SlabBlock && state.get(Properties.SLAB_TYPE) == SlabType.DOUBLE) {
            SlovesAccessor slovesAccessor = (SlovesAccessor) this;
            boolean bf = slovesAccessor.getBottomFirst(state);
            String first_slab = state.getBlock().toString();
            first_slab = first_slab.substring(first_slab.indexOf('{') + 1, first_slab.indexOf('}'));
            String second_slab = itemStack.getItem().toString();
            world.setBlockState(pos, ModBlocks.DOUBLE_SLAB_BLOCK.getDefaultState(), 3);
            DoubleSlabBlockEntity blockEntity = (DoubleSlabBlockEntity) world.getBlockEntity(pos);
            if (bf) {
                blockEntity.setTopSlabId(Identifier.of(second_slab));
                blockEntity.setBottomSlabId(Identifier.of(first_slab));
            } else {
                blockEntity.setTopSlabId(Identifier.of(first_slab));
                blockEntity.setBottomSlabId(Identifier.of(second_slab));
            }
            blockEntity.markDirty();
            world.updateListeners(pos, blockEntity.getCachedState(), blockEntity.getCachedState(),3);
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
            if (state.get(HorizontalFacingBlock.FACING) == Direction.SOUTH || state.get(HorizontalFacingBlock.FACING) == Direction.EAST) {
                blockEntity.setPositiveSlabId(Identifier.of(first_slab));
                blockEntity.setNegativeSlabId(Identifier.of(second_slab));
            } else {
                blockEntity.setPositiveSlabId(Identifier.of(second_slab));
                blockEntity.setNegativeSlabId(Identifier.of(first_slab));
            }
            blockEntity.markDirty();
            world.updateListeners(pos, blockEntity.getCachedState(), blockEntity.getCachedState(),3);
        }
    }

    @Inject(method = "getPickStack", at= @At("HEAD"), cancellable = true)
    public void getPickStack(WorldView world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> cir) {
        if (state.isOf(ModBlocks.DOUBLE_SLAB_BLOCK)) {
            System.out.println("double slab!");
            Identifier slabId;
            DoubleSlabBlockEntity blockEntity = (DoubleSlabBlockEntity) world.getBlockEntity(pos);
            if (ClickPositionTracker.clickUpperHalf) {
                slabId = blockEntity.getTopSlabId();
            } else {
                slabId = blockEntity.getBottomSlabId();
            }
            cir.setReturnValue(new ItemStack(Registries.ITEM.get(slabId)));
            cir.cancel();
        } else if (state.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            Identifier slabId;
            DoubleVerticalSlabBlockEntity blockEntity= (DoubleVerticalSlabBlockEntity) world.getBlockEntity(pos);
            if ((state.get(AXIS) == VerticalSlabAxis.X && ClickPositionTracker.clickEasternHalf) || (state.get(AXIS) == VerticalSlabAxis.Z && ClickPositionTracker.clickSouthernHalf)) {
                slabId = blockEntity.getPositiveSlabId();
            } else {
                slabId = blockEntity.getNegativeSlabId();
            }
            cir.setReturnValue(new ItemStack(Registries.ITEM.get(slabId)));
            cir.cancel();
        }
    }
}
