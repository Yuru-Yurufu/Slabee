package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public abstract class SpreadableVerticalSlabBlock extends SnowyVerticalSlabBlock {
    public SpreadableVerticalSlabBlock(Settings settings) {
        super(settings.ticksRandomly());
    }

    @Override
    public abstract MapCodec<? extends SpreadableVerticalSlabBlock> getCodec();

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!DoubleSlabUtils.canSurviveSpreadableVerticalSlabBlock(state, world, pos)) {
            world.setBlockState(pos, ModBlocks.DIRT_VERTICAL_SLAB.getDefaultState().with(FACING, state.get(VerticalSlabBlock.FACING)).with(WATERLOGGED, state.get(WATERLOGGED)));
        } else {
            if (world.getLightLevel(pos.up()) >= 9) {
                BlockState blockState = this.getDefaultState();

                for (int i = 0; i < 4; i++) {
                    BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    DoubleSlabUtils.toDirt(world, world.getBlockState(blockPos), blockPos, blockState);
                }
            }
        }
    }
}
