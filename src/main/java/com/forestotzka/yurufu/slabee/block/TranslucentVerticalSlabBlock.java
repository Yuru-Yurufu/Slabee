package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.block.enums.DoubleSlabVariant;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public class TranslucentVerticalSlabBlock extends VerticalSlabBlock {
    public static final MapCodec<TranslucentVerticalSlabBlock> CODEC = createCodec(TranslucentVerticalSlabBlock::new);

    public MapCodec<? extends TranslucentVerticalSlabBlock> getCodec() {
        return CODEC;
    }

    public TranslucentVerticalSlabBlock(Settings settings) {
        super(settings);
    }

    protected boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        Direction facing = state.get(VerticalSlabBlock.FACING);

        if (stateFrom.isOf(this)) {
            Direction facingFrom = stateFrom.get(VerticalSlabBlock.FACING);

            if (facing == facingFrom) {
                return facing != direction || facing.getOpposite() != direction;
            } else {
                return facingFrom == direction.getOpposite();
            }
        } else if (stateFrom.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            boolean isX = facing == Direction.EAST || facing == Direction.WEST;
            boolean isXFrom = stateFrom.get(DoubleVerticalSlabBlock.AXIS) == VerticalSlabAxis.X;
            boolean isPositive = facing == Direction.EAST || facing == Direction.SOUTH;

            boolean isSamePositive = stateFrom.get(AbstractDoubleSlabBlock.POSITIVE_SLAB) == DoubleSlabVariant.fromBlock(state.getBlock());
            boolean isSameNegative = stateFrom.get(AbstractDoubleSlabBlock.NEGATIVE_SLAB) == DoubleSlabVariant.fromBlock(state.getBlock());
            if (facing == direction) {
                if (isX == isXFrom) {
                    if (isPositive) {
                        return isSameNegative;
                    } else {
                        return isSamePositive;
                    }
                } else {
                    return isSamePositive && isSameNegative;
                }
            } else if (!(facing.getOpposite() == direction)) {
                if (isX == isXFrom) {
                    if (isPositive) {
                        return isSamePositive;
                    } else {
                        return isSameNegative;
                    }
                } else {
                    if (direction == Direction.EAST || direction == Direction.SOUTH) {
                        return isSameNegative;
                    } else if (direction == Direction.WEST || direction == Direction.NORTH) {
                        return isSamePositive;
                    } else {
                        return isSamePositive && isSameNegative;
                    }
                }
            }
        }

        return super.isSideInvisible(state, stateFrom, direction);
    }
}
