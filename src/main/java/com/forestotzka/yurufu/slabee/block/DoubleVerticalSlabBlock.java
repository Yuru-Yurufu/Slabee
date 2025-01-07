package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

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
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView view, BlockPos pos) {
        BlockEntity blockEntity = view.getBlockEntity(pos);
        if (!(blockEntity instanceof DoubleVerticalSlabBlockEntity entity)) {
            return 0.0F;
        }

        return DoubleSlabUtils.getMiningSpeed(entity.getPositiveSlabState(), entity.getNegativeSlabState(), player, view, pos);
    }

    @Override
    public BlockState onBreak(World view, BlockPos pos, BlockState state, PlayerEntity player) {
        DoubleVerticalSlabBlockEntity entity = (DoubleVerticalSlabBlockEntity) view.getBlockEntity(pos);
        BlockState positiveSlab = Objects.requireNonNull(entity).getPositiveSlabState();
        BlockState negativeSlab = Objects.requireNonNull(entity).getNegativeSlabState();
        if (!player.isCreative() && !player.isSpectator()) {
            double x = pos.getX() + 0.5d;
            double y = pos.getY() + 0.5d;
            double z = pos.getZ() + 0.5d;
            view.spawnEntity(new ItemEntity(view, x, y, z, new ItemStack(positiveSlab.getBlock())));
            view.spawnEntity(new ItemEntity(view, x, y, z, new ItemStack(negativeSlab.getBlock())));
        }
        this.spawnBreakParticles(view, player, pos, positiveSlab);
        this.spawnBreakParticles(view, player, pos, negativeSlab);
        view.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);

        return state;
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
