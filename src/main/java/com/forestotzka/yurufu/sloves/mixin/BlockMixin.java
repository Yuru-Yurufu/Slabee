package com.forestotzka.yurufu.sloves.mixin;

import com.forestotzka.yurufu.sloves.SlovesAccessor;
import com.forestotzka.yurufu.sloves.block.ModBlocks;
import com.forestotzka.yurufu.sloves.block.VerticalSlabBlocks;
import com.forestotzka.yurufu.sloves.block.enums.CustomSlabType;
import com.forestotzka.yurufu.sloves.block.enums.CustomVerticalSlabType;
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

import static com.forestotzka.yurufu.sloves.block.DoubleSlabBlock.TOP_SLAB;
import static com.forestotzka.yurufu.sloves.block.DoubleSlabBlock.BOTTOM_SLAB;
import static com.forestotzka.yurufu.sloves.block.DoubleVerticalSlabBlock.POSITIVE_SLAB;
import static com.forestotzka.yurufu.sloves.block.DoubleVerticalSlabBlock.NEGATIVE_SLAB;
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
            CustomSlabType top_slab;
            CustomSlabType bottom_slab;
            if (bf) {
                top_slab = CustomSlabType.fromString(second_slab);
                bottom_slab = CustomSlabType.fromString(first_slab);
            } else {
                top_slab = CustomSlabType.fromString(first_slab);
                bottom_slab = CustomSlabType.fromString(second_slab);
            }
            world.setBlockState(pos, ModBlocks.DOUBLE_SLAB_BLOCK.getDefaultState().with(TOP_SLAB, top_slab).with(BOTTOM_SLAB, bottom_slab), 3);
        } else if (state.getBlock() instanceof VerticalSlabBlocks && state.get(ModProperties.IS_DOUBLE)) {
            String axis = "";
            CustomVerticalSlabType positive_slab;
            CustomVerticalSlabType negative_slab;
            String first_slab = state.getBlock().toString();
            first_slab = first_slab.substring(first_slab.indexOf('{') + 1, first_slab.indexOf('}'));
            String second_slab = itemStack.getItem().toString();
            if (state.get(HorizontalFacingBlock.FACING) == Direction.SOUTH) {
                axis = "z";
                positive_slab = CustomVerticalSlabType.fromString(first_slab);
                negative_slab = CustomVerticalSlabType.fromString(second_slab);
            } else if (state.get(HorizontalFacingBlock.FACING) == Direction.EAST) {
                axis = "x";
                positive_slab = CustomVerticalSlabType.fromString(first_slab);
                negative_slab = CustomVerticalSlabType.fromString(second_slab);
            } else if (state.get(HorizontalFacingBlock.FACING) == Direction.NORTH) {
                axis = "z";
                positive_slab = CustomVerticalSlabType.fromString(second_slab);
                negative_slab = CustomVerticalSlabType.fromString(first_slab);
            } else {
                axis = "x";
                positive_slab = CustomVerticalSlabType.fromString(second_slab);
                negative_slab = CustomVerticalSlabType.fromString(first_slab);
            }
            world.setBlockState(pos, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK.getDefaultState().with(POSITIVE_SLAB, positive_slab).with(NEGATIVE_SLAB, negative_slab).with(AXIS, VerticalSlabAxis.fromString(axis)), 3);
        }
    }

    @Inject(method = "getPickStack", at= @At("HEAD"), cancellable = true)
    public void getPickStack(WorldView world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> cir) {
        if (state.isOf(ModBlocks.DOUBLE_SLAB_BLOCK)) {
            System.out.println("double slab!");
            String slab = "";
            if (ClickPositionTracker.clickUpperHalf) {
                slab = state.get(TOP_SLAB).asString().replace("__",":");
            } else {
                slab = state.get(BOTTOM_SLAB).asString().replace("__",":");
            }
            cir.setReturnValue(new ItemStack(Registries.ITEM.get(Identifier.of(slab))));
            cir.cancel();
        } else if (state.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            String slab = "";
            if ((state.get(AXIS) == VerticalSlabAxis.X && ClickPositionTracker.clickEasternHalf) || (state.get(AXIS) == VerticalSlabAxis.Z && ClickPositionTracker.clickSouthernHalf)) {
                slab = state.get(POSITIVE_SLAB).asString().replace("__",":");
            } else {
                slab = state.get(NEGATIVE_SLAB).asString().replace("__",":");
            }
            cir.setReturnValue(new ItemStack(Registries.ITEM.get(Identifier.of(slab))));
            cir.cancel();
        }
    }
}
