package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class TintedGlassSlabBlock extends TransparentSlabBlock {
    public static final MapCodec<TintedGlassSlabBlock> CODEC = simpleCodec(TintedGlassSlabBlock::new);

    public MapCodec<TintedGlassSlabBlock> codec() {
        return CODEC;
    }

    public TintedGlassSlabBlock(Properties settings) {
        super(settings);
    }

    protected boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    protected int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return 1;
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }
}
