package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MagmaVerticalSlabBlock extends VerticalSlabBlock {
    public static final MapCodec<MagmaVerticalSlabBlock> CODEC = simpleCodec(MagmaVerticalSlabBlock::new);
    private static final int SCHEDULED_TICK_DELAY = 20;

    public MapCodec<MagmaVerticalSlabBlock> codec() {
        return CODEC;
    }

    public MagmaVerticalSlabBlock(Properties settings) {
        super(settings);
    }

    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isSteppingCarefully() && entity instanceof LivingEntity) {
            entity.hurt(world.damageSources().hotFloor(), 1.0F);
        }

        super.stepOn(world, pos, state, entity);
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
}
