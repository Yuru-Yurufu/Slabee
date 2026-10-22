package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.SlabBlock;

public class TranslucentSlabBlock extends SlabBlock {
    public static final MapCodec<TranslucentSlabBlock> CODEC = simpleCodec(TranslucentSlabBlock::new);

    public MapCodec<? extends TranslucentSlabBlock> codec() {
        return CODEC;
    }

    public TranslucentSlabBlock(Properties settings) {
        super(settings);
    }
}
