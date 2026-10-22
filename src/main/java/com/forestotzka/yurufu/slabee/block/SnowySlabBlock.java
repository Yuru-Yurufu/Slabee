package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class SnowySlabBlock extends SlabBlock {
    public static final MapCodec<SnowySlabBlock> CODEC = simpleCodec(SnowySlabBlock::new);
    public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;

    @Override
    public MapCodec<? extends SnowySlabBlock> codec() {
        return CODEC;
    }

    public SnowySlabBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(SNOWY, false));
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP) {
            state = state.setValue(SNOWY, isSnow(neighborState));
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos().above());

        BlockState slabState = super.getStateForPlacement(ctx);
        if (slabState == null || !slabState.hasProperty(SNOWY)) {
            return slabState;
        }
        return slabState.setValue(SNOWY, isSnow(blockState));
    }

    private static boolean isSnow(BlockState state) {
        return state.is(BlockTags.SNOW);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SNOWY);
    }
}
