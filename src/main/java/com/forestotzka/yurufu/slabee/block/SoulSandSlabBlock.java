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
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SoulSandSlabBlock extends SlabBlock {
    public static final MapCodec<SoulSandSlabBlock> CODEC = simpleCodec(SoulSandSlabBlock::new);
    protected static final VoxelShape COLLISION_SHAPE_TOP = Block.box(0.0, 8.0, 0.0, 16.0, 14.0, 16.0);
    protected static final VoxelShape COLLISION_SHAPE_BOTTOM = Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);
    private static final int SCHEDULED_TICK_DELAY = 20;

    public MapCodec<SoulSandSlabBlock> codec() {
        return CODEC;
    }

    public SoulSandSlabBlock(Properties settings) {
        super(settings);
    }

    protected VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(TYPE)) {
            case TOP -> {
                return COLLISION_SHAPE_TOP;
            }
            case BOTTOM -> {
                return COLLISION_SHAPE_BOTTOM;
            }
            default -> {
                return Shapes.block();
            }
        }
    }

    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
        switch (state.getValue(TYPE)) {
            case TOP -> {
                return TOP_AABB;
            }
            case BOTTOM -> {
                return BOTTOM_AABB;
            }
            default -> {
                return Shapes.block();
            }
        }
    }

    protected VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(TYPE)) {
            case TOP -> {
                return TOP_AABB;
            }
            case BOTTOM -> {
                return BOTTOM_AABB;
            }
            default -> {
                return Shapes.block();
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
