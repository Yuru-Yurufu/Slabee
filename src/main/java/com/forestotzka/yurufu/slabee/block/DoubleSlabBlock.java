package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
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
            case POSITIVE -> (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity e && e.getPositiveSlabState().isOf(ModBlocks.DIRT_PATH_SLAB)) ? DirtPathSlabBlock.TOP_SHAPE : UP_OPAQUE_SHAPE;
            case NEGATIVE -> (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity e && e.getNegativeSlabState().isOf(ModBlocks.DIRT_PATH_SLAB)) ? DirtPathSlabBlock.BOTTOM_SHAPE : DOWN_OPAQUE_SHAPE;
            default -> VoxelShapes.empty();
        };
    }

    public static VoxelShape getLightingShape(BlockState state, BlockView world, BlockPos pos) {
        return switch (calcLightingShapeType(state)) {
            case FULL -> VoxelShapes.fullCube();
            case POSITIVE -> (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity e && e.getPositiveSlabState().isOf(ModBlocks.DIRT_PATH_SLAB)) ? DirtPathSlabBlock.TOP_SHAPE : UP_OPAQUE_SHAPE;
            case NEGATIVE -> (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity e && e.getNegativeSlabState().isOf(ModBlocks.DIRT_PATH_SLAB)) ? DirtPathSlabBlock.BOTTOM_SHAPE : DOWN_OPAQUE_SHAPE;
            default -> VoxelShapes.empty();
        };
    }

    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity entity) {
            BlockState blockState = entity.getPositiveSlabState();
            if (blockState.isOf(ModBlocks.SOUL_SAND_SLAB)) {
                return SOUL_SAND_COLLISION_SHAPE;
            } else if (blockState.isOf(ModBlocks.DIRT_PATH_SLAB)) {
                return DIRT_PATH_COLLISION_SHAPE;
            }
        }

        return VoxelShapes.fullCube();
    }

    protected VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity entity) {
            if (entity.getPositiveSlabState().isOf(ModBlocks.DIRT_PATH_SLAB)) {
                return DIRT_PATH_COLLISION_SHAPE;
            }
        }

        return VoxelShapes.fullCube();
    }

    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity entity) {
            if (entity.getPositiveSlabState().isOf(ModBlocks.DIRT_PATH_SLAB)) {
                return DIRT_PATH_COLLISION_SHAPE;
            }
        }

        return VoxelShapes.fullCube();
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity entity) {
            if (entity.getPositiveSlabState().isOf(ModBlocks.DIRT_PATH_SLAB)) {
                return DIRT_PATH_COLLISION_SHAPE;
            }
        }

        return VoxelShapes.fullCube();
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof AbstractDoubleSlabBlockEntity doubleSlabBlockEntity && !entity.bypassesSteppingEffects() && entity instanceof LivingEntity) {
            if (doubleSlabBlockEntity.getPositiveSlabState().isOf(ModBlocks.MAGMA_BLOCK_SLAB)) {
                entity.damage(world.getDamageSources().hotFloor(), 1.0F);
            }
        }

        super.onSteppedOn(world, pos, state, entity);
    }
}
