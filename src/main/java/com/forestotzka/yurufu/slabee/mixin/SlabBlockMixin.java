package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.DoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlabBlock.class)
public abstract class SlabBlockMixin extends BlockMixin {
    @Unique
    private static final EnumProperty<SlabType> TYPE = Properties.SLAB_TYPE;

    @Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
    private void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        BlockState oldState = world.getBlockState(pos);
        BlockState newState;

        if (oldState.isIn(BlockTags.SLABS)) {
            Block positiveSlab;
            Block negativeSlab;
            Item placementItem = ctx.getStack().getItem();

            if (placementItem instanceof BlockItem blockItem) {
                if (oldState.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                    positiveSlab = blockItem.getBlock();
                    negativeSlab = oldState.getBlock();
                } else {
                    positiveSlab = oldState.getBlock();
                    negativeSlab = blockItem.getBlock();
                }

                Identifier positiveId = Registries.BLOCK.getId(positiveSlab);
                Identifier negativeId = Registries.BLOCK.getId(negativeSlab);

                newState = SlabeeUtils.getAbstractState(positiveSlab, negativeSlab, ModBlocks.DOUBLE_SLAB_BLOCK.getDefaultState());

                world.setBlockState(pos, newState, 3);

                DoubleSlabBlockEntity blockEntity = (DoubleSlabBlockEntity) world.getBlockEntity(pos);
                if (blockEntity != null) {
                    blockEntity.setPositiveSlabId(positiveId);
                    blockEntity.setNegativeSlabId(negativeId);
                }

                cir.setReturnValue(newState);
                cir.cancel();
            }
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
        if (slabType == SlabType.DOUBLE || !itemStack.isIn(ItemTags.SLABS)) {
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
