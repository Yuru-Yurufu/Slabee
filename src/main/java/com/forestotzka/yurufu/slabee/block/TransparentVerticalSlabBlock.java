package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TransparentVerticalSlabBlock extends TranslucentVerticalSlabBlock {
    public static final MapCodec<TransparentVerticalSlabBlock> CODEC = simpleCodec(TransparentVerticalSlabBlock::new);

    public TransparentVerticalSlabBlock(Properties settings) {
        super(settings);
    }

    public MapCodec<? extends TransparentVerticalSlabBlock> codec() {
        return CODEC;
    }

    protected VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    protected float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0F;
    }

    protected boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }
}
