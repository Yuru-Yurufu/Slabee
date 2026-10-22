package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class TintedGlassVerticalSlabBlock extends TransparentVerticalSlabBlock {
    public static final MapCodec<TintedGlassVerticalSlabBlock> CODEC = simpleCodec(TintedGlassVerticalSlabBlock::new);

    public MapCodec<TintedGlassVerticalSlabBlock> codec() {
        return CODEC;
    }

    public TintedGlassVerticalSlabBlock(Properties settings) {
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
