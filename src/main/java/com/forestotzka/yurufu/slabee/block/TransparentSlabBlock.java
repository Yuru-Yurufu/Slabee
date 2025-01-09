package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class TransparentSlabBlock extends TranslucentSlabBlock {
    public static final MapCodec<TransparentSlabBlock> CODEC = createCodec(TransparentSlabBlock::new);

    public TransparentSlabBlock(Settings settings) {
        super(settings);
    }

    public MapCodec<? extends TransparentSlabBlock> getCodec() {
        return CODEC;
    }

    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
}
