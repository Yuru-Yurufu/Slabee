package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DoubleSlabBlock extends AbstractDoubleSlabBlock {
    protected static final VoxelShape DOWN_OPAQUE_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0001, 16.0);
    protected static final VoxelShape UP_OPAQUE_SHAPE = Block.box(0.0, 7.9999, 0.0, 16.0, 16.0, 16.0);

    public DoubleSlabBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends DoubleSlabBlock> codec() {
        return simpleCodec(DoubleSlabBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DoubleSlabBlockEntity(pos, state);
    }

    @Override
    protected boolean isLookingPositiveHalf(BlockState state) {
        return LookingPositionTracker.lookingAtUpperHalf;
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return switch (calcCullingShapeType(state)) {
            case FULL -> Shapes.block();
            case POSITIVE -> (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity e && e.getPositiveSlabState().is(ModBlocks.DIRT_PATH_SLAB)) ? DirtPathSlabBlock.TOP_SHAPE : UP_OPAQUE_SHAPE;
            case NEGATIVE -> (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity e && e.getNegativeSlabState().is(ModBlocks.DIRT_PATH_SLAB)) ? DirtPathSlabBlock.BOTTOM_SHAPE : DOWN_OPAQUE_SHAPE;
            default -> Shapes.empty();
        };
    }

    public static VoxelShape getLightingShape(BlockState state, BlockGetter world, BlockPos pos) {
        return switch (calcLightingShapeType(state)) {
            case FULL -> Shapes.block();
            case POSITIVE -> (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity e && e.getPositiveSlabState().is(ModBlocks.DIRT_PATH_SLAB)) ? DirtPathSlabBlock.TOP_SHAPE : UP_OPAQUE_SHAPE;
            case NEGATIVE -> (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity e && e.getNegativeSlabState().is(ModBlocks.DIRT_PATH_SLAB)) ? DirtPathSlabBlock.BOTTOM_SHAPE : DOWN_OPAQUE_SHAPE;
            default -> Shapes.empty();
        };
    }

    protected VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity entity) {
            BlockState blockState = entity.getPositiveSlabState();
            if (blockState.is(ModBlocks.SOUL_SAND_SLAB)) {
                return SOUL_SAND_COLLISION_SHAPE;
            } else if (blockState.is(ModBlocks.DIRT_PATH_SLAB)) {
                return DIRT_PATH_COLLISION_SHAPE;
            }
        }

        return Shapes.block();
    }

    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity entity) {
            if (entity.getPositiveSlabState().is(ModBlocks.DIRT_PATH_SLAB)) {
                return DIRT_PATH_COLLISION_SHAPE;
            }
        }

        return Shapes.block();
    }

    protected VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity entity) {
            if (entity.getPositiveSlabState().is(ModBlocks.DIRT_PATH_SLAB)) {
                return DIRT_PATH_COLLISION_SHAPE;
            }
        }

        return Shapes.block();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity entity) {
            if (entity.getPositiveSlabState().is(ModBlocks.DIRT_PATH_SLAB)) {
                return DIRT_PATH_COLLISION_SHAPE;
            }
        }

        return Shapes.block();
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity entity) {
            if (entity.getPositiveSlabState().is(ModBlocks.DIRT_PATH_SLAB)) {
                if (direction == Direction.UP && !canPlaceAt(world.getBlockState(pos.above()))) {
                    entity.requestConversion(AbstractDoubleSlabBlockEntity.Conversion.TO_DIRT, true, 1, 1);
                }
            }
        }

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    public static boolean canPlaceAt(BlockState state) {
        return !state.isSolid() || state.getBlock() instanceof FenceGateBlock;
    }

    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof AbstractDoubleSlabBlockEntity doubleSlabBlockEntity && !entity.isSteppingCarefully() && entity instanceof LivingEntity) {
            if (doubleSlabBlockEntity.getPositiveSlabState().is(ModBlocks.MAGMA_BLOCK_SLAB)) {
                entity.hurt(world.damageSources().hotFloor(), 1.0F);
            }
        }

        super.stepOn(world, pos, state, entity);
    }
}
