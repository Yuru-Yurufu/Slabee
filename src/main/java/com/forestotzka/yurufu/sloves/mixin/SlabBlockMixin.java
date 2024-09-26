package com.forestotzka.yurufu.sloves.mixin;

import com.forestotzka.yurufu.sloves.block.enums.SecondSlab;
import com.forestotzka.yurufu.sloves.registry.tag.ModBlockTags;
import com.forestotzka.yurufu.sloves.registry.tag.ModItemTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlabBlock.class)
public abstract class SlabBlockMixin extends BlockMixin {
    private static final EnumProperty<SlabType> TYPE = Properties.SLAB_TYPE;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final EnumProperty<SecondSlab> SECOND_BLOCK = EnumProperty.of("second_slab", SecondSlab.class);
    private static final BooleanProperty BOTTOM_FIRST = BooleanProperty.of("bottom_first");

    @Inject(method = "<init>", at = @At("TAIL"))
    public void onInit(AbstractBlock.Settings settings, CallbackInfo info) {
        this.setDefaultState(this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, Boolean.valueOf(false)).with(SECOND_BLOCK, SecondSlab.NONE).with(BOTTOM_FIRST, true));
    }

    /**
     * @author yurufu
     * @reason slab blockを重ねられるようにするため。
     */
    @Overwrite
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED, SECOND_BLOCK, BOTTOM_FIRST);
    }

    /**
     * @author yurufu
     * @reason slab blockを重ねられるようにするため。
     */
    @Overwrite
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        ItemStack itemStack = ctx.getStack();
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        Boolean bf = true;
        if (blockState.isIn(ModBlockTags.SLABS)) {
            bf = blockState.get(BOTTOM_FIRST);
            String itemName = itemStack.getItem().toString();
            SecondSlab sb = SecondSlab.fromString(itemName);
            return blockState.with(TYPE, SlabType.DOUBLE).with(WATERLOGGED, Boolean.valueOf(false)).with(SECOND_BLOCK, sb).with(BOTTOM_FIRST, bf);
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
            BlockState blockState2 = this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, Boolean.valueOf(fluidState.getFluid() == Fluids.WATER)).with(SECOND_BLOCK, SecondSlab.NONE).with(BOTTOM_FIRST, true);
            Direction direction = ctx.getSide();
            return direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getHitPos().y - (double)blockPos.getY() > 0.5))
                    ? blockState2
                    : blockState2.with(TYPE, SlabType.TOP).with(BOTTOM_FIRST, false);
        }
    }

    /**
     * @author yurufu
     * @reason slab blockを重ねられるようにするため。
     */
    @Overwrite
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        ItemStack itemStack = context.getStack();
        SlabType slabType = state.get(TYPE);
        if (slabType == SlabType.DOUBLE || !itemStack.isIn(ModItemTags.SLABS)) {
            return false;
        } else if (context.canReplaceExisting()) {
            boolean bl = context.getHitPos().y - (double)context.getBlockPos().getY() > 0.5;
            Direction direction = context.getSide();
            return slabType == SlabType.BOTTOM
                    ? direction == Direction.UP || bl && direction.getAxis().isHorizontal()
                    : direction == Direction.DOWN || !bl && direction.getAxis().isHorizontal();
        } else {
            return true;
        }
    }
}
