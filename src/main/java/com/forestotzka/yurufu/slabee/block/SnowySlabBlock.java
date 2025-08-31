package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class SnowySlabBlock extends SlabBlock {
    public static final MapCodec<SnowySlabBlock> CODEC = createCodec(SnowySlabBlock::new);
    public static final BooleanProperty SNOWY = Properties.SNOWY;

    @Override
    public MapCodec<? extends SnowySlabBlock> getCodec() {
        return CODEC;
    }

    public SnowySlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(SNOWY, false));
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP) {
            state = state.with(SNOWY, isSnow(neighborState));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().up());

        BlockState slabState = super.getPlacementState(ctx);
        if (slabState == null || !slabState.contains(SNOWY)) {
            return slabState;
        }
        return slabState.with(SNOWY, isSnow(blockState));
    }

    private static boolean isSnow(BlockState state) {
        return state.isIn(BlockTags.SNOW);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SNOWY);
    }
}
