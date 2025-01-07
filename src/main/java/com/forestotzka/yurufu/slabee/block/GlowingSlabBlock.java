package com.forestotzka.yurufu.slabee.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class GlowingSlabBlock extends SlabBlock {
    public GlowingSlabBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(TYPE) == SlabType.TOP) {
            return Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 15.99999, 16.0);
        } else if (state.get(TYPE) == SlabType.BOTTOM) {
            return Block.createCuboidShape(0.0, 0.00001, 0.0, 16.0, 8.0, 16.0);
        } else {
            return VoxelShapes.fullCube();
        }
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        SlabType slabType = state.get(TYPE);
        return switch (slabType) {
            case DOUBLE -> VoxelShapes.fullCube();
            case TOP -> TOP_SHAPE;
            default -> BOTTOM_SHAPE;
        };
    }
}
