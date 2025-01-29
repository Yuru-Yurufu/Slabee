package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TintedGlassVerticalSlabBlock extends TransparentVerticalSlabBlock {
    public static final MapCodec<TintedGlassVerticalSlabBlock> CODEC = createCodec(TintedGlassVerticalSlabBlock::new);

    public MapCodec<TintedGlassVerticalSlabBlock> getCodec() {
        return CODEC;
    }

    public TintedGlassVerticalSlabBlock(Settings settings) {
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
