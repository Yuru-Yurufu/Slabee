package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;

public class TranslucentVerticalSlabBlock extends VerticalSlabBlock {
    public static final MapCodec<TranslucentVerticalSlabBlock> CODEC = simpleCodec(TranslucentVerticalSlabBlock::new);

    public MapCodec<? extends TranslucentVerticalSlabBlock> codec() {
        return CODEC;
    }

    public TranslucentVerticalSlabBlock(Properties settings) {
        super(settings);
    }
}
