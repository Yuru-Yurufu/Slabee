package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class DoubleSlabBlock extends AbstractDoubleSlabBlock {
    protected static final VoxelShape DOWN_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0001, 16.0);
    protected static final VoxelShape UP_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 7.9999, 0.0, 16.0, 16.0, 16.0);

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
    protected boolean isLookingPositiveHalf(BlockState state) {
        return LookingPositionTracker.lookingAtUpperHalf;
    }

    @Override
    protected VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return switch (calcCullingShapeType(state)) {
            case FULL -> VoxelShapes.fullCube();
            case POSITIVE -> UP_OPAQUE_SHAPE;
            case NEGATIVE -> DOWN_OPAQUE_SHAPE;
            default -> VoxelShapes.empty();
        };
    }

    public static VoxelShape getLightingShape(BlockState state) {
        return switch (calcLightingShapeType(state)) {
            case FULL -> VoxelShapes.fullCube();
            case POSITIVE -> UP_OPAQUE_SHAPE;
            case NEGATIVE -> DOWN_OPAQUE_SHAPE;
            default -> VoxelShapes.empty();
        };
    }
}
