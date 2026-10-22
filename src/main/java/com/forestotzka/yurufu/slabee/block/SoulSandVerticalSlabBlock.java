package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SoulSandVerticalSlabBlock extends VerticalSlabBlock {
    public static final MapCodec<SoulSandVerticalSlabBlock> CODEC = simpleCodec(SoulSandVerticalSlabBlock::new);
    protected static final VoxelShape COLLISION_SHAPE_SOUTH = Block.box(0.0, 0.0, 8.0, 16.0, 14.0, 16.0);
    protected static final VoxelShape COLLISION_SHAPE_EAST = Block.box(8.0, 0.0, 0.0, 16.0, 14.0, 16.0);
    protected static final VoxelShape COLLISION_SHAPE_NORTH = Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 8.0);
    protected static final VoxelShape COLLISION_SHAPE_WEST = Block.box(0.0, 0.0, 0.0, 8.0, 14.0, 16.0);
    private static final int SCHEDULED_TICK_DELAY = 20;

    public MapCodec<SoulSandVerticalSlabBlock> codec() {
        return CODEC;
    }

    public SoulSandVerticalSlabBlock(Properties settings) {
        super(settings);
    }

    protected VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(IS_DOUBLE)) {
            return Shapes.block();
        } else {
            if (state.getValue(FACING) == Direction.SOUTH) {
                return COLLISION_SHAPE_SOUTH;
            } else if (state.getValue(FACING) == Direction.EAST) {
                return COLLISION_SHAPE_EAST;
            } else if (state.getValue(FACING) == Direction.NORTH) {
                return COLLISION_SHAPE_NORTH;
            } else {
                return COLLISION_SHAPE_WEST;
            }
        }
    }

    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
        if (state.getValue(IS_DOUBLE)) {
            return Shapes.block();
        } else {
            if (state.getValue(FACING) == Direction.SOUTH) {
                return SOUTH;
            } else if (state.getValue(FACING) == Direction.EAST) {
                return EAST;
            } else if (state.getValue(FACING) == Direction.NORTH) {
                return NORTH;
            } else {
                return WEST;
            }
        }
    }

    protected VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(IS_DOUBLE)) {
            return Shapes.block();
        } else {
            if (state.getValue(FACING) == Direction.SOUTH) {
                return SOUTH;
            } else if (state.getValue(FACING) == Direction.EAST) {
                return EAST;
            } else if (state.getValue(FACING) == Direction.NORTH) {
                return NORTH;
            } else {
                return WEST;
            }
        }
    }

    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        BubbleColumnBlock.updateColumn(world, pos.above(), state);
    }

    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && neighborState.is(Blocks.WATER)) {
            world.scheduleTick(pos, this, SCHEDULED_TICK_DELAY);
        }

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    protected void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleTick(pos, this, SCHEDULED_TICK_DELAY);
    }

    protected float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 0.2F;
    }
}
