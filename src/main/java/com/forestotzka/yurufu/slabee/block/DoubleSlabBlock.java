package com.forestotzka.yurufu.slabee.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class DoubleSlabBlock extends AbstractDoubleSlabBlock {
    protected static final VoxelShape DOWN_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 15.99999, 16.0);
    protected static final VoxelShape UP_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.00001, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape NON_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.00001, 0.0, 16.0, 15.99999, 16.0);

    public DoubleSlabBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends DoubleSlabBlock> getCodec() {
        return createCodec(DoubleSlabBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DoubleSlabBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        boolean topOpaque = DoubleSlabUtils.isPositiveOpaque(state);
        boolean bottomOpaque = DoubleSlabUtils.isNegativeOpaque(state);
        if (bottomOpaque && topOpaque) {
            return VoxelShapes.fullCube();
        } else if (bottomOpaque) {
            return DOWN_OPAQUE_SHAPE;
        } else if (topOpaque) {
            return UP_OPAQUE_SHAPE;
        } else {
            return NON_OPAQUE_SHAPE;
        }
    }
}
