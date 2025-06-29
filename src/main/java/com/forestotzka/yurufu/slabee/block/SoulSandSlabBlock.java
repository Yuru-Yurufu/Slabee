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

public class SoulSandSlabBlock extends SlabBlock {
    public static final MapCodec<SoulSandSlabBlock> CODEC = createCodec(SoulSandSlabBlock::new);
    protected static final VoxelShape COLLISION_SHAPE_TOP = Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 14.0, 16.0);
    protected static final VoxelShape COLLISION_SHAPE_BOTTOM = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);
    private static final int SCHEDULED_TICK_DELAY = 20;

    public MapCodec<SoulSandSlabBlock> getCodec() {
        return CODEC;
    }

    public SoulSandSlabBlock(Settings settings) {
        super(settings);
    }

    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(TYPE)) {
            case TOP -> {
                return COLLISION_SHAPE_TOP;
            }
            case BOTTOM -> {
                return COLLISION_SHAPE_BOTTOM;
            }
            default -> {
                return VoxelShapes.fullCube();
            }
        }
    }

    protected VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        switch (state.get(TYPE)) {
            case TOP -> {
                return TOP_SHAPE;
            }
            case BOTTOM -> {
                return BOTTOM_SHAPE;
            }
            default -> {
                return VoxelShapes.fullCube();
            }
        }
    }

    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(TYPE)) {
            case TOP -> {
                return TOP_SHAPE;
            }
            case BOTTOM -> {
                return BOTTOM_SHAPE;
            }
            default -> {
                return VoxelShapes.fullCube();
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
