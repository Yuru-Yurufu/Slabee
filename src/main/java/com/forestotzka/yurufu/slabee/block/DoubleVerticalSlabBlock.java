package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class DoubleVerticalSlabBlock extends AbstractDoubleSlabBlock {
    public static final EnumProperty<VerticalSlabAxis> AXIS = EnumProperty.of("axis", VerticalSlabAxis.class);

    protected static final VoxelShape EAST_OPAQUE_SHAPE = Block.createCuboidShape(0.00001, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape WEST_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 15.99999, 16.0, 16.0);
    protected static final VoxelShape SOUTH_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.00001, 16.0, 16.0, 16.0);
    protected static final VoxelShape NORTH_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 15.99999);
    protected static final VoxelShape NON_OPAQUE_SHAPE_X = Block.createCuboidShape(0.00001, 0.0, 0.0, 15.99999, 16.0, 16.0);
    protected static final VoxelShape NON_OPAQUE_SHAPE_Z = Block.createCuboidShape(0.0, 0.0, 0.00001, 16.0, 16.0, 15.99999);

    public DoubleVerticalSlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(AXIS, VerticalSlabAxis.X));
    }

    @Override
    protected MapCodec<? extends DoubleVerticalSlabBlock> getCodec() {
        return createCodec(DoubleVerticalSlabBlock::new);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AXIS);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DoubleVerticalSlabBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        boolean positiveOpaque = DoubleSlabUtils.isPositiveOpaque(state);
        boolean negativeOpaque = DoubleSlabUtils.isNegativeOpaque(state);
        if (positiveOpaque && negativeOpaque) {
            return VoxelShapes.fullCube();
        } else if (state.get(AXIS) == VerticalSlabAxis.X) {
            if (positiveOpaque) {
                return WEST_OPAQUE_SHAPE;
            } else if (negativeOpaque) {
                return EAST_OPAQUE_SHAPE;
            } else {
                return NON_OPAQUE_SHAPE_X;
            }
        } else {
            if (positiveOpaque) {
                return NORTH_OPAQUE_SHAPE;
            } else if (negativeOpaque) {
                return SOUTH_OPAQUE_SHAPE;
            } else {
                return NON_OPAQUE_SHAPE_Z;
            }
        }
    }
}
