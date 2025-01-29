package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TintedGlassSlabBlock extends TransparentSlabBlock {
    public static final MapCodec<TintedGlassSlabBlock> CODEC = createCodec(TintedGlassSlabBlock::new);

    public MapCodec<TintedGlassSlabBlock> getCodec() {
        return CODEC;
    }

    public TintedGlassSlabBlock(Settings settings) {
        super(settings);
    }

    protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    protected int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 1;
    }

    @Override
    protected boolean hasSidedTransparency(BlockState state) {
        return true;
    }
}
