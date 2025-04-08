package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.block.enums.DoubleSlabVariant;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public class TranslucentSlabBlock extends SlabBlock {
    public static final MapCodec<TranslucentSlabBlock> CODEC = createCodec(TranslucentSlabBlock::new);

    public MapCodec<? extends TranslucentSlabBlock> getCodec() {
        return CODEC;
    }

    public TranslucentSlabBlock(Settings settings) {
        super(settings);
    }

    /*protected boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        SlabType slabType = state.get(Properties.SLAB_TYPE);

        if (stateFrom.isOf(this)) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                return slabType != stateFrom.get(SlabBlock.TYPE);
            } else {
                return slabType == stateFrom.get(SlabBlock.TYPE);
            }
        } else if (stateFrom.isOf(ModBlocks.DOUBLE_SLAB_BLOCK)) {
            if (direction == Direction.UP) {
                return slabType == SlabType.TOP && areNegativeSlabsEqual(state, stateFrom);
            } else if (direction == Direction.DOWN) {
                return slabType == SlabType.BOTTOM && arePositiveSlabsEqual(state, stateFrom);
            } else {
                if (slabType == SlabType.TOP) {
                    return arePositiveSlabsEqual(state, stateFrom);
                } else if (slabType == SlabType.BOTTOM) {
                    return areNegativeSlabsEqual(state, stateFrom);
                }
            }
        } else if (stateFrom.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                return areBothSlabsEqual(state, stateFrom);
            } else if ((direction == Direction.EAST || direction == Direction.WEST) == (stateFrom.get(DoubleVerticalSlabBlock.AXIS) == VerticalSlabAxis.X)) {
                if (direction == Direction.EAST || direction == Direction.SOUTH) {
                    return areNegativeSlabsEqual(state, stateFrom);
                } else {
                    return arePositiveSlabsEqual(state, stateFrom);
                }
            } else {
                return areBothSlabsEqual(state, stateFrom);
            }
        } else {
            Block block = state.getBlock();
            Block blockFrom = stateFrom.getBlock();
            if (block == ModBlockMap.verticalSlabToSlab(blockFrom)) {
                return direction.getOpposite() == stateFrom.get(VerticalSlabBlock.FACING);
            } else if (block == ModBlockMap.originalToSlab(blockFrom)) {
                if (slabType == SlabType.TOP) {
                    return direction != Direction.DOWN;
                } else if (slabType == SlabType.BOTTOM) {
                    return direction != Direction.UP;
                }
            }
        }

        return super.isSideInvisible(state, stateFrom, direction);
    }*/

    /*private boolean areBothSlabsEqual(BlockState state, BlockState stateFrom) {
        return arePositiveSlabsEqual(state, stateFrom) && areNegativeSlabsEqual(state, stateFrom);
    }

    private boolean arePositiveSlabsEqual(BlockState state, BlockState stateFrom) {
        return stateFrom.get(AbstractDoubleSlabBlock.POSITIVE_SLAB) == DoubleSlabVariant.fromBlock(state.getBlock());
    }
    private boolean areNegativeSlabsEqual(BlockState state, BlockState stateFrom) {
        return stateFrom.get(AbstractDoubleSlabBlock.NEGATIVE_SLAB) == DoubleSlabVariant.fromBlock(state.getBlock());
    }*/
}
