package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.block.enums.DoubleSlabVariant;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.property.Properties;
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
                return facing != direction && facing.getOpposite() != direction;
            } else {
                return facingFrom == direction.getOpposite();
            }
        } else if (stateFrom.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            boolean isX = facing == Direction.EAST || facing == Direction.WEST;
            boolean isXFrom = stateFrom.get(DoubleVerticalSlabBlock.AXIS) == VerticalSlabAxis.X;
            boolean isPositive = facing == Direction.EAST || facing == Direction.SOUTH;

            if (facing == direction) {
                if (isX == isXFrom) {
                    if (isPositive) {
                        return areNegativeSlabsEqual(state, stateFrom);
                    } else {
                        return arePositiveSlabsEqual(state, stateFrom);
                    }
                } else {
                    return areBothSlabsEqual(state, stateFrom);
                }
            } else if (!(facing.getOpposite() == direction)) {
                if (isX == isXFrom) {
                    if (isPositive) {
                        return arePositiveSlabsEqual(state, stateFrom);
                    } else {
                        return areNegativeSlabsEqual(state, stateFrom);
                    }
                } else {
                    if (direction == Direction.EAST || direction == Direction.SOUTH) {
                        return areNegativeSlabsEqual(state, stateFrom);
                    } else if (direction == Direction.WEST || direction == Direction.NORTH) {
                        return arePositiveSlabsEqual(state, stateFrom);
                    } else {
                        return areBothSlabsEqual(state, stateFrom);
                    }
                }
            }
        } else if (stateFrom.isOf(ModBlocks.DOUBLE_SLAB_BLOCK)) {
            if (direction == Direction.UP) {
                return areNegativeSlabsEqual(state, stateFrom);
            } else if (direction == Direction.DOWN) {
                return arePositiveSlabsEqual(state, stateFrom);
            } else {
                return facing.getOpposite() != direction && areBothSlabsEqual(state, stateFrom);
            }
        } else {
            Block block = state.getBlock();
            Block blockFrom = stateFrom.getBlock();
            if (block == ModBlockMap.slabToVerticalSlab(blockFrom)) {
                if (direction == Direction.UP) {
                    return stateFrom.get(Properties.SLAB_TYPE) == SlabType.BOTTOM;
                } else if (direction == Direction.DOWN) {
                    return stateFrom.get(Properties.SLAB_TYPE) == SlabType.TOP;
                }
            } else if (block == ModBlockMap.originalToVerticalSlab(blockFrom)) {
                return facing.getOpposite() != direction;
            }
        }

        return super.isSideInvisible(state, stateFrom, direction);
    }

    private boolean areBothSlabsEqual(BlockState state, BlockState stateFrom) {
        return arePositiveSlabsEqual(state, stateFrom) && areNegativeSlabsEqual(state, stateFrom);
    }

    private boolean arePositiveSlabsEqual(BlockState state, BlockState stateFrom) {
        return stateFrom.get(AbstractDoubleSlabBlock.POSITIVE_SLAB) == DoubleSlabVariant.fromBlock(state.getBlock());
    }
    private boolean areNegativeSlabsEqual(BlockState state, BlockState stateFrom) {
        return stateFrom.get(AbstractDoubleSlabBlock.NEGATIVE_SLAB) == DoubleSlabVariant.fromBlock(state.getBlock());
    }
}
