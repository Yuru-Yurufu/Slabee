package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.SlabBlock;

public class TranslucentSlabBlock extends SlabBlock {
    public static final MapCodec<TranslucentSlabBlock> CODEC = createCodec(TranslucentSlabBlock::new);

    public MapCodec<? extends TranslucentSlabBlock> getCodec() {
        return CODEC;
    }

    public TranslucentSlabBlock(Settings settings) {
        super(settings);
    }
}
