package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DoubleVerticalSlabBlock extends AbstractDoubleSlabBlock {
    public static final EnumProperty<VerticalSlabAxis> AXIS = EnumProperty.of("axis", VerticalSlabAxis.class);

    protected static final VoxelShape EAST_OPAQUE_SHAPE = Block.createCuboidShape(7.9999, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape WEST_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 8.0001, 16.0, 16.0);
    protected static final VoxelShape SOUTH_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.0, 7.9999, 16.0, 16.0, 16.0);
    protected static final VoxelShape NORTH_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 8.0001);

    protected static final VoxelShape SOUL_SAND_COLLISION_SHAPE_EAST = VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0), Block.createCuboidShape(0.0, 14.0, 0.0, 8.0, 16.0, 16.0));
    protected static final VoxelShape SOUL_SAND_COLLISION_SHAPE_WEST = VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 14.0, 16.0), Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 16.0, 16.0));
    protected static final VoxelShape SOUL_SAND_COLLISION_SHAPE_SOUTH = VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 14.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 8.0));
    protected static final VoxelShape SOUL_SAND_COLLISION_SHAPE_NORTH = VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 8.0), Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 16.0, 16.0));

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
    protected boolean isLookingPositiveHalf(BlockState state) {
        boolean isX = state.get(AXIS) == VerticalSlabAxis.X;
        return (isX && LookingPositionTracker.lookingAtEasternHalf) || (!isX && LookingPositionTracker.lookingAtSouthernHalf);
    }

    @Override
    protected VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        boolean isX = state.get(AXIS) == VerticalSlabAxis.X;
        switch (calcCullingShapeType(state)) {
            case FULL:
                return VoxelShapes.fullCube();
            case POSITIVE:
                if (isX) {
                    return EAST_OPAQUE_SHAPE;
                } else {
                    return SOUTH_OPAQUE_SHAPE;
                }
            case NEGATIVE:
                if (isX) {
                    return WEST_OPAQUE_SHAPE;
                } else {
                    return NORTH_OPAQUE_SHAPE;
                }
            default:
                return VoxelShapes.empty();
        }
    }

    public static VoxelShape getLightingShape(BlockState state) {
        boolean isX = state.get(AXIS) == VerticalSlabAxis.X;
        switch (calcLightingShapeType(state)) {
            case FULL:
                return VoxelShapes.fullCube();
            case POSITIVE:
                if (isX) {
                    return EAST_OPAQUE_SHAPE;
                } else {
                    return SOUTH_OPAQUE_SHAPE;
                }
            case NEGATIVE:
                if (isX) {
                    return WEST_OPAQUE_SHAPE;
                } else {
                    return NORTH_OPAQUE_SHAPE;
                }
            default:
                return VoxelShapes.empty();
        }
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (world.getBlockEntity(pos) instanceof DoubleVerticalSlabBlockEntity entity) {
            boolean isSoulSandPositive = entity.getPositiveSlabState().isOf(ModBlocks.SOUL_SAND_VERTICAL_SLAB);
            boolean isSoulSandNegative = entity.getNegativeSlabState().isOf(ModBlocks.SOUL_SAND_VERTICAL_SLAB);

            if (isSoulSandPositive && isSoulSandNegative) {
                return SOUL_SAND_COLLISION_SHAPE;
            } else if (isSoulSandPositive) {
                return entity.isX() ? SOUL_SAND_COLLISION_SHAPE_EAST : SOUL_SAND_COLLISION_SHAPE_SOUTH;
            } else if (isSoulSandNegative) {
                return entity.isX() ? SOUL_SAND_COLLISION_SHAPE_WEST : SOUL_SAND_COLLISION_SHAPE_NORTH;
            } else {
                return VoxelShapes.fullCube();
            }
        }

        return SOUL_SAND_COLLISION_SHAPE;
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof AbstractDoubleSlabBlockEntity doubleSlabBlockEntity && !entity.bypassesSteppingEffects() && entity instanceof LivingEntity) {
            boolean isPositiveSide = SlabeeUtils.isPositiveSide(entity, state.get(AXIS), pos);

            if (isPositiveSide && doubleSlabBlockEntity.getPositiveSlabState().isOf(ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB)) {
                entity.damage(world.getDamageSources().hotFloor(), 1.0F);
            } else if (!isPositiveSide && doubleSlabBlockEntity.getNegativeSlabState().isOf(ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB)) {
                entity.damage(world.getDamageSources().hotFloor(), 1.0F);
            }
        }

        super.onSteppedOn(world, pos, state, entity);
    }
}
