package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.math.Direction;

public class TranslucentSlabBlock extends SlabBlock {
    public static final MapCodec<TranslucentSlabBlock> CODEC = createCodec(TranslucentSlabBlock::new);

    public MapCodec<? extends TranslucentSlabBlock> getCodec() {
        return CODEC;
    }

    public TranslucentSlabBlock(Settings settings) {
        super(settings);
    }

    protected boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return (stateFrom.isOf(this) && state.get(SlabBlock.TYPE) == stateFrom.get(SlabBlock.TYPE)) || super.isSideInvisible(state, stateFrom, direction);
    }
}
