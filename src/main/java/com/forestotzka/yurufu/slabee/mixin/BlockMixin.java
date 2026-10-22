package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.block.*;
import com.forestotzka.yurufu.slabee.block.enums.DoubleSlabVariant;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
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
    public abstract BlockState defaultBlockState();

    @Inject(method = "getCloneItemStack", at= @At("HEAD"), cancellable = true)
    public void getPickStack(LevelReader world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> cir) {
        if (state.is(ModBlocks.DOUBLE_SLAB_BLOCK)) {
            ResourceLocation slabId;
            DoubleSlabBlockEntity blockEntity = (DoubleSlabBlockEntity) world.getBlockEntity(pos);
            if (ClickPositionTracker.clickUpperHalf) {
                slabId = Objects.requireNonNull(blockEntity).getPositiveSlabId();
            } else {
                slabId = Objects.requireNonNull(blockEntity).getNegativeSlabId();
            }
            cir.setReturnValue(new ItemStack(BuiltInRegistries.ITEM.get(slabId)));
            cir.cancel();
        } else if (state.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            ResourceLocation slabId;
            DoubleVerticalSlabBlockEntity blockEntity = (DoubleVerticalSlabBlockEntity) world.getBlockEntity(pos);
            if ((state.getValue(AXIS) == VerticalSlabAxis.X && ClickPositionTracker.clickEasternHalf) || (state.getValue(AXIS) == VerticalSlabAxis.Z && ClickPositionTracker.clickSouthernHalf)) {
                slabId = Objects.requireNonNull(blockEntity).getPositiveSlabId();
            } else {
                slabId = Objects.requireNonNull(blockEntity).getNegativeSlabId();
            }
            cir.setReturnValue(new ItemStack(BuiltInRegistries.ITEM.get(slabId)));
            cir.cancel();
        }
    }

    @Inject(method = "shouldRenderFace", at = @At("HEAD"), cancellable = true)
    private static void onShouldDrawSide(BlockState state, BlockGetter world, BlockPos pos, Direction side, BlockPos otherPos, CallbackInfoReturnable<Boolean> cir) {
        // ダブルハーフの中の片方のハーフについて、もう片方に接している内側の面を描くかどうか。
        // この判定はすべてのブロックの面で呼ばれるので、ハーフ以外はワールドを見る前にすぐ抜ける。
        Block block = state.getBlock();
        if (!(block instanceof SlabBlock) && !(block instanceof VerticalSlabBlock)) {
            return;
        }
        BlockState here = world.getBlockState(pos);
        if (!(here.getBlock() instanceof AbstractDoubleSlabBlock)) {
            return;
        }
        if (!(world.getBlockEntity(pos) instanceof AbstractDoubleSlabBlockEntity entity)) {
            return;
        }

        if (block instanceof SlabBlock && here.is(ModBlocks.DOUBLE_SLAB_BLOCK)) {
            SlabType slabType = state.getValue(BlockStateProperties.SLAB_TYPE);
            if (slabType == SlabType.BOTTOM && side == Direction.UP) {
                cir.setReturnValue(DoubleSlabUtils.shouldDrawInnerFace(state, entity.getPositiveSlabState(), side));
            } else if (slabType == SlabType.TOP && side == Direction.DOWN) {
                cir.setReturnValue(DoubleSlabUtils.shouldDrawInnerFace(state, entity.getNegativeSlabState(), side));
            }
        } else if (block instanceof VerticalSlabBlock && here.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            Direction d = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            if (d.getOpposite() == side) {
                if (d == Direction.EAST || d == Direction.SOUTH) {
                    cir.setReturnValue(DoubleSlabUtils.shouldDrawInnerFace(state, entity.getNegativeSlabState(), side));
                } else {
                    cir.setReturnValue(DoubleSlabUtils.shouldDrawInnerFace(state, entity.getPositiveSlabState(), side));
                }
            }
        }
    }
}
