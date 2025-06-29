package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class SoulSandVerticalSlabBlock extends VerticalSlabBlock {
    public static final MapCodec<SoulSandVerticalSlabBlock> CODEC = createCodec(SoulSandVerticalSlabBlock::new);
    protected static final VoxelShape COLLISION_SHAPE_SOUTH = Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 14.0, 16.0);
    protected static final VoxelShape COLLISION_SHAPE_EAST = Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 14.0, 16.0);
    protected static final VoxelShape COLLISION_SHAPE_NORTH = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 8.0);
    protected static final VoxelShape COLLISION_SHAPE_WEST = Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 14.0, 16.0);
    private static final int SCHEDULED_TICK_DELAY = 20;

    public MapCodec<SoulSandVerticalSlabBlock> getCodec() {
        return CODEC;
    }

    public SoulSandVerticalSlabBlock(Settings settings) {
        super(settings);
    }

    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(IS_DOUBLE)) {
            return VoxelShapes.fullCube();
        } else {
            if (state.get(FACING) == Direction.SOUTH) {
                return COLLISION_SHAPE_SOUTH;
            } else if (state.get(FACING) == Direction.EAST) {
                return COLLISION_SHAPE_EAST;
            } else if (state.get(FACING) == Direction.NORTH) {
                return COLLISION_SHAPE_NORTH;
            } else {
                return COLLISION_SHAPE_WEST;
            }
        }
    }

    protected VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        if (state.get(IS_DOUBLE)) {
            return VoxelShapes.fullCube();
        } else {
            if (state.get(FACING) == Direction.SOUTH) {
                return SOUTH;
            } else if (state.get(FACING) == Direction.EAST) {
                return EAST;
            } else if (state.get(FACING) == Direction.NORTH) {
                return NORTH;
            } else {
                return WEST;
            }
        }
    }

    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(IS_DOUBLE)) {
            return VoxelShapes.fullCube();
        } else {
            if (state.get(FACING) == Direction.SOUTH) {
                return SOUTH;
            } else if (state.get(FACING) == Direction.EAST) {
                return EAST;
            } else if (state.get(FACING) == Direction.NORTH) {
                return NORTH;
            } else {
                return WEST;
            }
        }
    }

    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BubbleColumnBlock.update(world, pos.up(), state);
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && neighborState.isOf(Blocks.WATER)) {
            world.scheduleBlockTick(pos, this, SCHEDULED_TICK_DELAY);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, SCHEDULED_TICK_DELAY);
    }

    protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 0.2F;
    }
}
