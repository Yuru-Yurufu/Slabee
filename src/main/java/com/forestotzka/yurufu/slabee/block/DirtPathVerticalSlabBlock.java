package com.forestotzka.yurufu.slabee.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DirtPathVerticalSlabBlock extends VerticalSlabBlock {
    protected static final VoxelShape SOUTH = Block.box(0.0, 0.0, 8.0, 16.0, 15.0, 16.0);
    protected static final VoxelShape EAST = Block.box(8.0, 0.0, 0.0, 16.0, 15.0, 16.0);
    protected static final VoxelShape NORTH = Block.box(0.0, 0.0, 0.0, 16.0, 15.0, 8.0);
    protected static final VoxelShape WEST = Block.box(0.0, 0.0, 0.0, 8.0, 15.0, 16.0);

    public DirtPathVerticalSlabBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
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

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !state.canSurvive(world, pos)) {
            world.scheduleTick(pos, this, 1);
        }

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        setToDirtVerticalSlab(null, state, world, pos);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.above());
        return !blockState.isSolid() || blockState.getBlock() instanceof FenceGateBlock || (blockState.getBlock() instanceof VerticalSlabBlock && blockState.getValue(VerticalSlabBlock.FACING).getOpposite() == state.getValue(VerticalSlabBlock.FACING));
    }

    public static void setToDirtVerticalSlab(@Nullable Entity entity, BlockState state, Level world, BlockPos pos) {
        BlockState blockState = pushEntitiesUp(state, ModBlocks.DIRT_VERTICAL_SLAB.defaultBlockState().setValue(VerticalSlabBlock.FACING, state.getValue(VerticalSlabBlock.FACING)), world, pos);
        world.setBlockAndUpdate(pos, blockState);
        world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockState));
    }
}
