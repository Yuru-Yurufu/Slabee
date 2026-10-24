package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlock;
import com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlockEntity;

import com.forestotzka.yurufu.slabee.block.*;
import com.forestotzka.yurufu.slabee.block.enums.DoubleSlabVariant;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
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
    private static void onShouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos otherPos, CallbackInfoReturnable<Boolean> cir) {
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
        // チャンクの組み立て中にも呼ばれるので、Block Entity ではなく描画データを読む
        if (!(world.getBlockEntityRenderData(pos) instanceof DoubleSlabRenderData entity)) {
            return;
        }

        if (block instanceof SlabBlock && here.isOf(ModBlocks.DOUBLE_SLAB_BLOCK)) {
            SlabType slabType = state.get(Properties.SLAB_TYPE);
            if (slabType == SlabType.BOTTOM && side == Direction.UP) {
                cir.setReturnValue(DoubleSlabUtils.shouldDrawInnerFace(state, entity.getPositiveSlabState(), side));
            } else if (slabType == SlabType.TOP && side == Direction.DOWN) {
                cir.setReturnValue(DoubleSlabUtils.shouldDrawInnerFace(state, entity.getNegativeSlabState(), side));
            }
        } else if (block instanceof VerticalSlabBlock && here.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            Direction d = state.get(Properties.HORIZONTAL_FACING);
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
