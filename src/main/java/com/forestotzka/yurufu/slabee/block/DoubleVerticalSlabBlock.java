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
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class DoubleVerticalSlabBlock extends AbstractDoubleSlabBlock {
    public static final EnumProperty<VerticalSlabAxis> AXIS = EnumProperty.of("axis", VerticalSlabAxis.class);

    protected static final VoxelShape EAST_OPAQUE_SHAPE = Block.createCuboidShape(7.9999, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape WEST_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 8.0001, 16.0, 16.0);
    protected static final VoxelShape SOUTH_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.0, 7.9999, 16.0, 16.0, 16.0);
    protected static final VoxelShape NORTH_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 8.0001);

    protected static final VoxelShape SOUL_SAND_COLLISION_SHAPE_EAST = VoxelShapes.union(Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 14.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 16.0, 16.0)).simplify();
    protected static final VoxelShape SOUL_SAND_COLLISION_SHAPE_WEST = VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 14.0, 16.0), Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 16.0, 16.0)).simplify();
    protected static final VoxelShape SOUL_SAND_COLLISION_SHAPE_SOUTH = VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 14.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 8.0)).simplify();
    protected static final VoxelShape SOUL_SAND_COLLISION_SHAPE_NORTH = VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 8.0), Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 16.0, 16.0)).simplify();

    protected static final VoxelShape DIRT_PATH_COLLISION_SHAPE_EAST = VoxelShapes.union(Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 15.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 16.0, 16.0)).simplify();
    protected static final VoxelShape DIRT_PATH_COLLISION_SHAPE_WEST = VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 15.0, 16.0), Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 16.0, 16.0)).simplify();
    protected static final VoxelShape DIRT_PATH_COLLISION_SHAPE_SOUTH = VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 15.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 8.0)).simplify();
    protected static final VoxelShape DIRT_PATH_COLLISION_SHAPE_NORTH = VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 15.0, 8.0), Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 16.0, 16.0)).simplify();

    protected static final VoxelShape SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_EAST = VoxelShapes.union(Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 14.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 15.0, 16.0)).simplify();
    protected static final VoxelShape SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_WEST = VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 14.0, 16.0), Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 15.0, 16.0)).simplify();
    protected static final VoxelShape SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_SOUTH = VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 14.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 15.0, 8.0)).simplify();
    protected static final VoxelShape SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_NORTH = VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 8.0), Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 15.0, 16.0)).simplify();

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
            boolean isDirtPathPositive = entity.getPositiveSlabState().isOf(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
            boolean isDirtPathNegative = entity.getNegativeSlabState().isOf(ModBlocks.DIRT_PATH_VERTICAL_SLAB);

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
            return VoxelShapes.fullCube();
        }
    }

    protected VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof DoubleVerticalSlabBlockEntity entity) {
            boolean bl1 = entity.getPositiveSlabState().isOf(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
            boolean bl2 = entity.getNegativeSlabState().isOf(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
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

        return VoxelShapes.fullCube();
    }

    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (world.getBlockEntity(pos) instanceof DoubleVerticalSlabBlockEntity entity) {
            boolean bl1 = entity.getPositiveSlabState().isOf(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
            boolean bl2 = entity.getNegativeSlabState().isOf(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
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

        return VoxelShapes.fullCube();
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (world.getBlockEntity(pos) instanceof DoubleVerticalSlabBlockEntity entity) {
            boolean bl1 = entity.getPositiveSlabState().isOf(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
            boolean bl2 = entity.getNegativeSlabState().isOf(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
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

        return VoxelShapes.fullCube();
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !state.canPlaceAt(world, pos)) {
            world.scheduleBlockTick(pos, this, 1);
        }
        if (world.getBlockEntity(pos) instanceof DoubleVerticalSlabBlockEntity entity) {
            if (entity.getPositiveSlabState().isOf(ModBlocks.DIRT_PATH_VERTICAL_SLAB)) {
                if (direction == Direction.UP && !canPlaceAt(world.getBlockState(pos.up()), entity.getPositiveSlabState().get(VerticalSlabBlock.FACING))) {
                    entity.requestConversion(AbstractDoubleSlabBlockEntity.Conversion.TO_DIRT, true, 1, 1);
                }
            }
            if (entity.getNegativeSlabState().isOf(ModBlocks.DIRT_PATH_VERTICAL_SLAB)) {
                if (direction == Direction.UP && !canPlaceAt(world.getBlockState(pos.up()), entity.getNegativeSlabState().get(VerticalSlabBlock.FACING))) {
                    entity.requestConversion(AbstractDoubleSlabBlockEntity.Conversion.TO_DIRT, false, 1, 1);
                }
            }
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public static boolean canPlaceAt(BlockState state, Direction facing) {
        return !state.isSolid() || state.getBlock() instanceof FenceGateBlock|| (state.getBlock() instanceof VerticalSlabBlock && state.get(VerticalSlabBlock.FACING).getOpposite() == facing);
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
