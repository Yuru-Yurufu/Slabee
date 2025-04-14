package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
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
import org.spongepowered.asm.mixin.Unique;
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
        Block block = state.getBlock();

        if (block instanceof SlabBlock) {
            BlockState blockState = world.getBlockState(pos);
            BlockState otherState = world.getBlockState(otherPos);
            SlabType slabType = state.get(Properties.SLAB_TYPE);

            if (SlabeeUtils.isDoubleSlab(blockState)) {
                if (slabType == SlabType.BOTTOM) {
                    if (side == Direction.UP) {
                        cir.setReturnValue(DoubleSlabUtils.isPositiveSeeThrough(blockState) && DoubleSlabVariant.fromBlock(state.getBlock()) != blockState.get(AbstractDoubleSlabBlock.POSITIVE_SLAB));
                        cir.cancel();
                        return;
                    }
                } else if (slabType == SlabType.TOP) {
                    if (side == Direction.DOWN) {
                        cir.setReturnValue(DoubleSlabUtils.isNegativeSeeThrough(blockState) && DoubleSlabVariant.fromBlock(state.getBlock()) != blockState.get(AbstractDoubleSlabBlock.NEGATIVE_SLAB));
                        cir.cancel();
                        return;
                    }
                }
            }

            if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof AbstractDoubleSlabBlockEntity entity) {
                if (side == Direction.UP) {
                    cir.setReturnValue(!(slabType == SlabType.TOP && areNegativeSlabsEqual(state, entity)));
                } else if (side == Direction.DOWN) {
                    cir.setReturnValue(!(slabType == SlabType.BOTTOM && arePositiveSlabsEqual(state, entity)));
                } else {
                    if (slabType == SlabType.TOP) {
                        cir.setReturnValue(!arePositiveSlabsEqual(state, entity));
                    } else {
                        cir.setReturnValue(!areNegativeSlabsEqual(state, entity));
                    }
                }

                cir.cancel();
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof AbstractDoubleSlabBlockEntity entity) {
                if (side == Direction.UP || side == Direction.DOWN) {
                    cir.setReturnValue(areBothSlabsEqual(state, entity));
                } else if ((side == Direction.EAST || side == Direction.WEST) == ((DoubleVerticalSlabBlockEntity) entity).isX()) {
                    if (side == Direction.EAST || side == Direction.SOUTH) {
                        cir.setReturnValue(areNegativeSlabsEqual(state, entity));
                    } else {
                        cir.setReturnValue(arePositiveSlabsEqual(state, entity));
                    }
                } else {
                    cir.setReturnValue(areBothSlabsEqual(state, entity));
                }

                cir.cancel();
            }
        } else if (block instanceof VerticalSlabBlock) {
            BlockState blockState = world.getBlockState(pos);

            if (SlabeeUtils.isDoubleSlab(blockState)) {
                Direction d = state.get(Properties.HORIZONTAL_FACING);
                if (d.getOpposite() == side) {
                    if (d == Direction.EAST || d == Direction.SOUTH) {
                        cir.setReturnValue(DoubleSlabUtils.isNegativeSeeThrough(blockState) && DoubleSlabVariant.fromBlock(state.getBlock()) != blockState.get(AbstractDoubleSlabBlock.POSITIVE_SLAB));
                        cir.cancel();
                    } else if (d == Direction.WEST || d == Direction.NORTH) {
                        cir.setReturnValue(DoubleSlabUtils.isPositiveSeeThrough(blockState) && DoubleSlabVariant.fromBlock(state.getBlock()) != blockState.get(AbstractDoubleSlabBlock.NEGATIVE_SLAB));
                        cir.cancel();
                    }
                }
            }
        }
    }

    @Unique
    private static boolean areBothSlabsEqual(BlockState state, AbstractDoubleSlabBlockEntity entity) {
        return arePositiveSlabsEqual(state, entity) && areNegativeSlabsEqual(state, entity);
    }

    @Unique
    private static boolean arePositiveSlabsEqual(BlockState state, AbstractDoubleSlabBlockEntity entity) {
        return entity.getPositiveSlabState().getBlock() == state.getBlock();
    }
    @Unique
    private static boolean areNegativeSlabsEqual(BlockState state, AbstractDoubleSlabBlockEntity entity) {
        return entity.getNegativeSlabState().getBlock() == state.getBlock();
    }
}
