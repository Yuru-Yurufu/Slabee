package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
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
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DoubleVerticalSlabBlock extends AbstractDoubleSlabBlock {
    public static final EnumProperty<VerticalSlabAxis> AXIS = EnumProperty.create("axis", VerticalSlabAxis.class);

    protected static final VoxelShape EAST_OPAQUE_SHAPE = Block.box(7.9999, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape WEST_OPAQUE_SHAPE = Block.box(0.0, 0.0, 0.0, 8.0001, 16.0, 16.0);
    protected static final VoxelShape SOUTH_OPAQUE_SHAPE = Block.box(0.0, 0.0, 7.9999, 16.0, 16.0, 16.0);
    protected static final VoxelShape NORTH_OPAQUE_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 8.0001);

    protected static final VoxelShape SOUL_SAND_COLLISION_SHAPE_EAST = Shapes.or(Block.box(8.0, 0.0, 0.0, 16.0, 14.0, 16.0), Block.box(0.0, 0.0, 0.0, 8.0, 16.0, 16.0)).optimize();
    protected static final VoxelShape SOUL_SAND_COLLISION_SHAPE_WEST = Shapes.or(Block.box(0.0, 0.0, 0.0, 8.0, 14.0, 16.0), Block.box(8.0, 0.0, 0.0, 16.0, 16.0, 16.0)).optimize();
    protected static final VoxelShape SOUL_SAND_COLLISION_SHAPE_SOUTH = Shapes.or(Block.box(0.0, 0.0, 8.0, 16.0, 14.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 8.0)).optimize();
    protected static final VoxelShape SOUL_SAND_COLLISION_SHAPE_NORTH = Shapes.or(Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 8.0), Block.box(0.0, 0.0, 8.0, 16.0, 16.0, 16.0)).optimize();

    protected static final VoxelShape DIRT_PATH_COLLISION_SHAPE_EAST = Shapes.or(Block.box(8.0, 0.0, 0.0, 16.0, 15.0, 16.0), Block.box(0.0, 0.0, 0.0, 8.0, 16.0, 16.0)).optimize();
    protected static final VoxelShape DIRT_PATH_COLLISION_SHAPE_WEST = Shapes.or(Block.box(0.0, 0.0, 0.0, 8.0, 15.0, 16.0), Block.box(8.0, 0.0, 0.0, 16.0, 16.0, 16.0)).optimize();
    protected static final VoxelShape DIRT_PATH_COLLISION_SHAPE_SOUTH = Shapes.or(Block.box(0.0, 0.0, 8.0, 16.0, 15.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 8.0)).optimize();
    protected static final VoxelShape DIRT_PATH_COLLISION_SHAPE_NORTH = Shapes.or(Block.box(0.0, 0.0, 0.0, 16.0, 15.0, 8.0), Block.box(0.0, 0.0, 8.0, 16.0, 16.0, 16.0)).optimize();

    protected static final VoxelShape SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_EAST = Shapes.or(Block.box(8.0, 0.0, 0.0, 16.0, 14.0, 16.0), Block.box(0.0, 0.0, 0.0, 8.0, 15.0, 16.0)).optimize();
    protected static final VoxelShape SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_WEST = Shapes.or(Block.box(0.0, 0.0, 0.0, 8.0, 14.0, 16.0), Block.box(8.0, 0.0, 0.0, 16.0, 15.0, 16.0)).optimize();
    protected static final VoxelShape SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_SOUTH = Shapes.or(Block.box(0.0, 0.0, 8.0, 16.0, 14.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 15.0, 8.0)).optimize();
    protected static final VoxelShape SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_NORTH = Shapes.or(Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 8.0), Block.box(0.0, 0.0, 8.0, 16.0, 15.0, 16.0)).optimize();

    public DoubleVerticalSlabBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, VerticalSlabAxis.X));
    }

    @Override
    protected MapCodec<? extends DoubleVerticalSlabBlock> codec() {
        return simpleCodec(DoubleVerticalSlabBlock::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DoubleVerticalSlabBlockEntity(pos, state);
    }

    @Override
    protected boolean isLookingPositiveHalf(BlockState state) {
        boolean isX = state.getValue(AXIS) == VerticalSlabAxis.X;
        return (isX && LookingPositionTracker.lookingAtEasternHalf) || (!isX && LookingPositionTracker.lookingAtSouthernHalf);
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        boolean isX = state.getValue(AXIS) == VerticalSlabAxis.X;
        switch (calcCullingShapeType(state)) {
            case FULL:
                return Shapes.block();
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
                return Shapes.empty();
        }
    }

    public static VoxelShape getLightingShape(BlockState state) {
        boolean isX = state.getValue(AXIS) == VerticalSlabAxis.X;
        switch (calcLightingShapeType(state)) {
            case FULL:
                return Shapes.block();
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
                return Shapes.empty();
        }
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (world.getBlockEntity(pos) instanceof DoubleVerticalSlabBlockEntity entity) {
            boolean isSoulSandPositive = entity.getPositiveSlabState().is(ModBlocks.SOUL_SAND_VERTICAL_SLAB);
            boolean isSoulSandNegative = entity.getNegativeSlabState().is(ModBlocks.SOUL_SAND_VERTICAL_SLAB);
            boolean isDirtPathPositive = entity.getPositiveSlabState().is(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
            boolean isDirtPathNegative = entity.getNegativeSlabState().is(ModBlocks.DIRT_PATH_VERTICAL_SLAB);

            return getCollisionShapeFromPart(entity, isSoulSandPositive, isSoulSandNegative, isDirtPathPositive, isDirtPathNegative);
        }

        return SOUL_SAND_COLLISION_SHAPE;
    }

    public static VoxelShape getCollisionShapeFromPart(DoubleVerticalSlabBlockEntity entity, boolean isSoulSandPositive, boolean isSoulSandNegative, boolean isDirtPathPositive, boolean isDirtPathNegative) {
        if (isSoulSandPositive && isSoulSandNegative) {
            return SOUL_SAND_COLLISION_SHAPE;
        } else if (isDirtPathPositive && isDirtPathNegative) {
            return DIRT_PATH_COLLISION_SHAPE;
        } else if (isSoulSandPositive) {
            if (isDirtPathNegative) {
                return entity.isX() ? SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_EAST : SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_SOUTH;
            } else {
                return entity.isX() ? SOUL_SAND_COLLISION_SHAPE_EAST : SOUL_SAND_COLLISION_SHAPE_SOUTH;
            }
        } else if (isSoulSandNegative) {
            if (isDirtPathPositive) {
                return entity.isX() ? SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_WEST : SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_NORTH;
            } else {
                return entity.isX() ? SOUL_SAND_COLLISION_SHAPE_WEST : SOUL_SAND_COLLISION_SHAPE_NORTH;
            }
        } else if (isDirtPathPositive) {
            return entity.isX() ? DIRT_PATH_COLLISION_SHAPE_EAST : DIRT_PATH_COLLISION_SHAPE_SOUTH;
        } else if (isDirtPathNegative) {
            return entity.isX() ? DIRT_PATH_COLLISION_SHAPE_WEST : DIRT_PATH_COLLISION_SHAPE_NORTH;
        } else {
            return Shapes.block();
        }
    }

    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof DoubleVerticalSlabBlockEntity entity) {
            boolean bl1 = entity.getPositiveSlabState().is(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
            boolean bl2 = entity.getNegativeSlabState().is(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
            if (bl1 && bl2) {
                return DIRT_PATH_COLLISION_SHAPE;
            } else if (bl1) {
                if (entity.isX()) return DIRT_PATH_COLLISION_SHAPE_EAST;
                else return DIRT_PATH_COLLISION_SHAPE_SOUTH;
            } else if (bl2) {
                if (entity.isX()) return DIRT_PATH_COLLISION_SHAPE_WEST;
                else return DIRT_PATH_COLLISION_SHAPE_NORTH;
            }
        }

        return Shapes.block();
    }

    protected VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (world.getBlockEntity(pos) instanceof DoubleVerticalSlabBlockEntity entity) {
            boolean bl1 = entity.getPositiveSlabState().is(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
            boolean bl2 = entity.getNegativeSlabState().is(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
            if (bl1 && bl2) {
                return DIRT_PATH_COLLISION_SHAPE;
            } else if (bl1) {
                if (entity.isX()) return DIRT_PATH_COLLISION_SHAPE_EAST;
                else return DIRT_PATH_COLLISION_SHAPE_SOUTH;
            } else if (bl2) {
                if (entity.isX()) return DIRT_PATH_COLLISION_SHAPE_WEST;
                else return DIRT_PATH_COLLISION_SHAPE_NORTH;
            }
        }

        return Shapes.block();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (world.getBlockEntity(pos) instanceof DoubleVerticalSlabBlockEntity entity) {
            boolean bl1 = entity.getPositiveSlabState().is(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
            boolean bl2 = entity.getNegativeSlabState().is(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
            if (bl1 && bl2) {
                return DIRT_PATH_COLLISION_SHAPE;
            } else if (bl1) {
                if (entity.isX()) return DIRT_PATH_COLLISION_SHAPE_EAST;
                else return DIRT_PATH_COLLISION_SHAPE_SOUTH;
            } else if (bl2) {
                if (entity.isX()) return DIRT_PATH_COLLISION_SHAPE_WEST;
                else return DIRT_PATH_COLLISION_SHAPE_NORTH;
            }
        }

        return Shapes.block();
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !state.canSurvive(world, pos)) {
            world.scheduleTick(pos, this, 1);
        }
        if (world.getBlockEntity(pos) instanceof DoubleVerticalSlabBlockEntity entity) {
            if (entity.getPositiveSlabState().is(ModBlocks.DIRT_PATH_VERTICAL_SLAB)) {
                if (direction == Direction.UP && !canPlaceAt(world.getBlockState(pos.above()), entity.getPositiveSlabState().getValue(VerticalSlabBlock.FACING))) {
                    entity.requestConversion(AbstractDoubleSlabBlockEntity.Conversion.TO_DIRT, true, 1, 1);
                }
            }
            if (entity.getNegativeSlabState().is(ModBlocks.DIRT_PATH_VERTICAL_SLAB)) {
                if (direction == Direction.UP && !canPlaceAt(world.getBlockState(pos.above()), entity.getNegativeSlabState().getValue(VerticalSlabBlock.FACING))) {
                    entity.requestConversion(AbstractDoubleSlabBlockEntity.Conversion.TO_DIRT, false, 1, 1);
                }
            }
        }

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    public static boolean canPlaceAt(BlockState state, Direction facing) {
        return !state.isSolid() || state.getBlock() instanceof FenceGateBlock|| (state.getBlock() instanceof VerticalSlabBlock && state.getValue(VerticalSlabBlock.FACING).getOpposite() == facing);
    }

    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof AbstractDoubleSlabBlockEntity doubleSlabBlockEntity && !entity.isSteppingCarefully() && entity instanceof LivingEntity) {
            boolean isPositiveSide = SlabeeUtils.isPositiveSide(entity, state.getValue(AXIS), pos);

            if (isPositiveSide && doubleSlabBlockEntity.getPositiveSlabState().is(ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB)) {
                entity.hurt(world.damageSources().hotFloor(), 1.0F);
            } else if (!isPositiveSide && doubleSlabBlockEntity.getNegativeSlabState().is(ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB)) {
                entity.hurt(world.damageSources().hotFloor(), 1.0F);
            }
        }

        super.stepOn(world, pos, state, entity);
    }
}
