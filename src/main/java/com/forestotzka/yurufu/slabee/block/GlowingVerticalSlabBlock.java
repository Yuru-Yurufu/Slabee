package com.forestotzka.yurufu.slabee.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.Objects;

public class GlowingVerticalSlabBlock extends VerticalSlabBlock {
    public GlowingVerticalSlabBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(IS_DOUBLE)) {
            return VoxelShapes.fullCube();
        } else {
            if (state.get(FACING) == Direction.SOUTH) {
                return Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 16.0, 15.99999);
            } else if (state.get(FACING) == Direction.EAST) {
                return Block.createCuboidShape(8.0, 0.0, 0.0, 15.99999, 16.0, 16.0);
            } else if (state.get(FACING) == Direction.NORTH) {
                return Block.createCuboidShape(0.0, 0.0, 0.00001, 16.0, 16.0, 8.0);
            } else {
                return Block.createCuboidShape(0.00001, 0.0, 0.0, 8.0, 16.0, 16.0);
            }
        }
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(IS_DOUBLE)) {
            return VoxelShapes.fullCube();
        } else {
            if (Objects.requireNonNull(state.get(FACING)) == Direction.SOUTH) {
                return SOUTH;
            } else if (Objects.requireNonNull(state.get(FACING)) == Direction.EAST) {
                return EAST;
            } else if (Objects.requireNonNull(state.get(FACING)) == Direction.NORTH) {
                return NORTH;
            } else {
                return WEST;
            }
        }
    }
}
