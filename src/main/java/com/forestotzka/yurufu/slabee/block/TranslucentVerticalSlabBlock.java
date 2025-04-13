package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;

public class TranslucentVerticalSlabBlock extends VerticalSlabBlock {
    public static final MapCodec<TranslucentVerticalSlabBlock> CODEC = createCodec(TranslucentVerticalSlabBlock::new);

    public MapCodec<? extends TranslucentVerticalSlabBlock> getCodec() {
        return CODEC;
    }

    public TranslucentVerticalSlabBlock(Settings settings) {
        super(settings);
    }
}
