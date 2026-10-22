package com.forestotzka.yurufu.slabee.block;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GlowingVerticalSlabBlock extends VerticalSlabBlock {
    public GlowingVerticalSlabBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(IS_DOUBLE)) {
            return Shapes.block();
        } else {
            if (state.getValue(FACING) == Direction.SOUTH) {
                return Block.box(0.0, 0.0, 8.0, 16.0, 16.0, 15.99999);
            } else if (state.getValue(FACING) == Direction.EAST) {
                return Block.box(8.0, 0.0, 0.0, 15.99999, 16.0, 16.0);
            } else if (state.getValue(FACING) == Direction.NORTH) {
                return Block.box(0.0, 0.0, 0.00001, 16.0, 16.0, 8.0);
            } else {
                return Block.box(0.00001, 0.0, 0.0, 8.0, 16.0, 16.0);
            }
        }
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(IS_DOUBLE)) {
            return Shapes.block();
        } else {
            if (Objects.requireNonNull(state.getValue(FACING)) == Direction.SOUTH) {
                return SOUTH;
            } else if (Objects.requireNonNull(state.getValue(FACING)) == Direction.EAST) {
                return EAST;
            } else if (Objects.requireNonNull(state.getValue(FACING)) == Direction.NORTH) {
                return NORTH;
            } else {
                return WEST;
            }
        }
    }
}
