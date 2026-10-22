package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlock;
import com.forestotzka.yurufu.slabee.block.DoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.DoubleSlabUtils;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlabBlock.class)
public abstract class SlabBlockMixin extends BlockMixin {
    @Unique
    private static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;

    @Inject(method = "getStateForPlacement", at = @At("HEAD"), cancellable = true)
    private void getPlacementState(BlockPlaceContext ctx, CallbackInfoReturnable<BlockState> cir) {
        BlockPos pos = ctx.getClickedPos();
        Level world = ctx.getLevel();
        BlockState oldState = world.getBlockState(pos);
        BlockState newState;

        if (oldState.is(BlockTags.SLABS)) {
            Block positiveSlab;
            Block negativeSlab;
            Item placementItem = ctx.getItemInHand().getItem();

            if (placementItem instanceof BlockItem blockItem) {
                if (oldState.getValue(SlabBlock.TYPE) == SlabType.BOTTOM) {
                    positiveSlab = blockItem.getBlock();
                    negativeSlab = oldState.getBlock();
                } else {
                    positiveSlab = oldState.getBlock();
                    negativeSlab = blockItem.getBlock();
                }

                ResourceLocation positiveId = BuiltInRegistries.BLOCK.getKey(positiveSlab);
                ResourceLocation negativeId = BuiltInRegistries.BLOCK.getKey(negativeSlab);

                newState = SlabeeUtils.getAbstractState(positiveSlab, negativeSlab, ModBlocks.DOUBLE_SLAB_BLOCK.defaultBlockState());

                if (DoubleSlabUtils.canPlace(ctx, getCollisionShape(ctx.getItemInHand(), oldState))) {
                    world.setBlock(pos, newState, 3);

                    DoubleSlabBlockEntity blockEntity = (DoubleSlabBlockEntity) world.getBlockEntity(pos);
                    if (blockEntity != null) {
                        blockEntity.setPositiveSlabId(positiveId);
                        blockEntity.setNegativeSlabId(negativeId);
                    }

                    cir.setReturnValue(newState);
                    cir.cancel();
                    return;
                }
            }

            cir.setReturnValue(oldState);
            cir.cancel();
        }
    }

    @Unique
    private static VoxelShape getCollisionShape(ItemStack itemStack, BlockState oldState) {
        if (oldState.getValue(TYPE) == SlabType.BOTTOM) {
            if (itemStack.is(ModBlocks.SOUL_SAND_SLAB.asItem())) return AbstractDoubleSlabBlock.getSoulSandCollisionShape();
            else if (itemStack.is(ModBlocks.DIRT_PATH_SLAB.asItem())) return AbstractDoubleSlabBlock.getDirtPathCollisionShape();
        } else {
            if (oldState.is(ModBlocks.SOUL_SAND_SLAB)) return AbstractDoubleSlabBlock.getSoulSandCollisionShape();
            else if (oldState.is(ModBlocks.DIRT_PATH_SLAB)) return AbstractDoubleSlabBlock.getDirtPathCollisionShape();
        }

        return Shapes.block();
    }

    @Inject(
            method = "canBeReplaced(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/item/context/BlockPlaceContext;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void injectCanReplace(BlockState state, BlockPlaceContext ctx, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = ctx.getItemInHand();
        SlabType slabType = state.getValue(TYPE);

        if (slabType == SlabType.DOUBLE || !itemStack.is(ItemTags.SLABS)) {
            cir.setReturnValue(false);
        } else if (ctx.replacingClickedOnBlock()) {
            boolean bl = ctx.getClickLocation().y - (double)ctx.getClickedPos().getY() > 0.5;
            Direction direction = ctx.getClickedFace();

            if (slabType == SlabType.BOTTOM) {
                cir.setReturnValue(direction == Direction.UP || bl && direction.getAxis().isHorizontal());
            } else {
                cir.setReturnValue(direction == Direction.DOWN || !bl && direction.getAxis().isHorizontal());
            }
        } else {
            cir.setReturnValue(true);
        }

        cir.cancel();
    }
}
