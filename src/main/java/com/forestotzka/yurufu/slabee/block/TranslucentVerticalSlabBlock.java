package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public class TranslucentVerticalSlabBlock extends VerticalSlabBlock {
    public static final MapCodec<TranslucentVerticalSlabBlock> CODEC = createCodec(TranslucentVerticalSlabBlock::new);

    public MapCodec<? extends TranslucentVerticalSlabBlock> getCodec() {
        return CODEC;
    }

    public TranslucentVerticalSlabBlock(Settings settings) {
        super(settings);
    }

    protected boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.isOf(this)) {
            Direction facing = state.get(VerticalSlabBlock.FACING);
            Direction facingFrom = stateFrom.get(VerticalSlabBlock.FACING);
            return facing == facingFrom || facingFrom == direction.getOpposite();
        }

        return super.isSideInvisible(state, stateFrom, direction);
    }
}
