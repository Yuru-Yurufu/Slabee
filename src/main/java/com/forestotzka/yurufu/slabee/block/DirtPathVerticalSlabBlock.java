package com.forestotzka.yurufu.slabee.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class DirtPathVerticalSlabBlock extends VerticalSlabBlock {
    protected static final VoxelShape SOUTH = Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 15.0, 16.0);
    protected static final VoxelShape EAST = Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 15.0, 16.0);
    protected static final VoxelShape NORTH = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 15.0, 8.0);
    protected static final VoxelShape WEST = Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 15.0, 16.0);

    public DirtPathVerticalSlabBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
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

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !state.canPlaceAt(world, pos)) {
            world.scheduleBlockTick(pos, this, 1);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        setToDirtVerticalSlab(null, state, world, pos);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.up());
        return !blockState.isSolid() || blockState.getBlock() instanceof FenceGateBlock || (blockState.getBlock() instanceof VerticalSlabBlock && blockState.get(VerticalSlabBlock.FACING).getOpposite() == state.get(VerticalSlabBlock.FACING));
    }

    public static void setToDirtVerticalSlab(@Nullable Entity entity, BlockState state, World world, BlockPos pos) {
        BlockState blockState = pushEntitiesUpBeforeBlockChange(state, ModBlocks.DIRT_VERTICAL_SLAB.getDefaultState().with(VerticalSlabBlock.FACING, state.get(VerticalSlabBlock.FACING)), world, pos);
        world.setBlockState(pos, blockState);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(entity, blockState));
    }
}
