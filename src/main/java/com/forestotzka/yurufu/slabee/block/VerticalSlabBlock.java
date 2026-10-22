package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import com.forestotzka.yurufu.slabee.registry.tag.ModBlockTags;
import com.forestotzka.yurufu.slabee.registry.tag.ModItemTags;
import com.forestotzka.yurufu.slabee.state.property.ModProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlock.AXIS;

public class VerticalSlabBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<VerticalSlabBlock> CODEC = simpleCodec(VerticalSlabBlock::new);
    public static final BooleanProperty IS_DOUBLE = ModProperties.IS_DOUBLE;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SOUTH = Block.box(0.0, 0.0, 8.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EAST = Block.box(8.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape NORTH = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 8.0);
    protected static final VoxelShape WEST = Block.box(0.0, 0.0, 0.0, 8.0, 16.0, 16.0);

    @Override
    public MapCodec<? extends VerticalSlabBlock> codec() {
        return CODEC;
    }

    public VerticalSlabBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_DOUBLE, Boolean.FALSE).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return !state.getValue(IS_DOUBLE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IS_DOUBLE, FACING, WATERLOGGED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(IS_DOUBLE)) {
            return Shapes.block();
        } else {
            if (state.getValue(FACING) == Direction.SOUTH) {
                return SOUTH;
            } else if (state.getValue(FACING) == Direction.EAST) {
                return EAST;
            } else if (state.getValue(FACING) == Direction.NORTH) {
                return NORTH;
            } else {
                return WEST;
            }
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        Level world = ctx.getLevel();
        BlockState oldState = world.getBlockState(pos);
        BlockState newState;

        if (oldState.is(ModBlockTags.VERTICAL_SLABS)) {
            String axis;
            Block positiveSlab;
            Block negativeSlab;
            Item placementItem = ctx.getItemInHand().getItem();

            if (placementItem instanceof BlockItem blockItem) {
                Direction facing = oldState.getValue(HorizontalDirectionalBlock.FACING);
                if (facing  == Direction.SOUTH || facing == Direction.EAST) {
                    positiveSlab = oldState.getBlock();
                    negativeSlab = blockItem.getBlock();
                } else {
                    positiveSlab = blockItem.getBlock();
                    negativeSlab = oldState.getBlock();
                }

                ResourceLocation positiveId = BuiltInRegistries.BLOCK.getKey(positiveSlab);
                ResourceLocation negativeId = BuiltInRegistries.BLOCK.getKey(negativeSlab);

                if (facing.getAxis() == Direction.Axis.X) {
                    axis = "x";
                } else {
                    axis = "z";
                }

                newState = SlabeeUtils.getAbstractState(positiveSlab, negativeSlab, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK.defaultBlockState().setValue(AXIS, VerticalSlabAxis.fromString(axis)));

                if (DoubleSlabUtils.canPlace(ctx, getCollisionShape(ctx.getItemInHand(), oldState))) {
                    world.setBlock(pos, newState, 3);

                    DoubleVerticalSlabBlockEntity blockEntity = (DoubleVerticalSlabBlockEntity) world.getBlockEntity(pos);
                    if (blockEntity != null) {
                        blockEntity.setAxis(axis);
                        blockEntity.setPositiveSlabId(positiveId);
                        blockEntity.setNegativeSlabId(negativeId);
                    }

                    return newState;
                }
            }

            return oldState;
        } else {
            FluidState fluidState = ctx.getLevel().getFluidState(pos);

            Direction direction = ctx.getHorizontalDirection();
            Direction[] directions = ctx.getNearestLookingDirections();
            boolean blEast = ctx.getClickLocation().x - (double)ctx.getClickedPos().getX() > 0.5;
            boolean blSouth = ctx.getClickLocation().z - (double)ctx.getClickedPos().getZ() > 0.5;

            for (Direction ignored : directions) {
                if (oldState.canSurvive(ctx.getLevel(), pos)) {
                    if ((direction == Direction.EAST && !blEast) || (direction == Direction.WEST && blEast) || (direction == Direction.SOUTH && !blSouth) || (direction == Direction.NORTH && blSouth)) {
                        direction = direction.getOpposite();
                    }
                    break;
                }
            }

            return this.defaultBlockState()
                    .setValue(IS_DOUBLE, Boolean.FALSE)
                    .setValue(FACING, direction)
                    .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        }
    }

    private static VoxelShape getCollisionShape(ItemStack itemStack, BlockState oldState) {
        boolean bls1 = oldState.is(ModBlocks.SOUL_SAND_VERTICAL_SLAB);
        boolean bls2 = itemStack.is(ModBlocks.SOUL_SAND_VERTICAL_SLAB.asItem());
        boolean bld1 = oldState.is(ModBlocks.DIRT_PATH_VERTICAL_SLAB);
        boolean bld2 = itemStack.is(ModBlocks.DIRT_PATH_VERTICAL_SLAB.asItem());

        if (bls1 && bls2) {
            return AbstractDoubleSlabBlock.getSoulSandCollisionShape();
        } else if (bld1 && bld2) {
            return AbstractDoubleSlabBlock.getDirtPathCollisionShape();
        } else if ((!bls1 && !bls2) || (!bld1 && !bld2)) {
            return Shapes.block();
        } else {
            switch (oldState.getValue(FACING)) {
                case EAST -> {
                    if (bls1) {
                        if (bld2) return DoubleVerticalSlabBlock.SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_EAST;
                        else return DoubleVerticalSlabBlock.SOUL_SAND_COLLISION_SHAPE_EAST;
                    }
                    else {
                        if (bld1) return DoubleVerticalSlabBlock.SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_WEST;
                        else return DoubleVerticalSlabBlock.SOUL_SAND_COLLISION_SHAPE_WEST;
                    }
                }
                case WEST -> {
                    if (bls1) {
                        if (bld2) return DoubleVerticalSlabBlock.SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_WEST;
                        else return DoubleVerticalSlabBlock.SOUL_SAND_COLLISION_SHAPE_WEST;
                    }
                    else {
                        if (bld1) return DoubleVerticalSlabBlock.SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_EAST;
                        else return DoubleVerticalSlabBlock.SOUL_SAND_COLLISION_SHAPE_EAST;
                    }
                }
                case SOUTH -> {
                    if (bls1) {
                        if (bld2) return DoubleVerticalSlabBlock.SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_SOUTH;
                        else return DoubleVerticalSlabBlock.SOUL_SAND_COLLISION_SHAPE_SOUTH;
                    }
                    else {
                        if (bld1) return DoubleVerticalSlabBlock.SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_NORTH;
                        else return DoubleVerticalSlabBlock.SOUL_SAND_COLLISION_SHAPE_NORTH;
                    }
                }
                case NORTH -> {
                    if (bls1) {
                        if (bld2) return DoubleVerticalSlabBlock.SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_NORTH;
                        else return DoubleVerticalSlabBlock.SOUL_SAND_COLLISION_SHAPE_NORTH;
                    }
                    else {
                        if (bld1) return DoubleVerticalSlabBlock.SOUL_SAND_DIRT_PATH_COLLISION_SHAPE_SOUTH;
                        else return DoubleVerticalSlabBlock.SOUL_SAND_COLLISION_SHAPE_SOUTH;
                    }
                }
                default -> {
                    return Shapes.block();
                }
            }
        }
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        ItemStack itemStack = context.getItemInHand();
        if (state.getValue(IS_DOUBLE) || !itemStack.is(ModItemTags.VERTICAL_SLABS)) {
            return false;
        } else if (context.replacingClickedOnBlock()) {
            boolean blEast = context.getClickLocation().x - (double)context.getClickedPos().getX() > 0.5;
            boolean blSouth = context.getClickLocation().z - (double)context.getClickedPos().getZ() > 0.5;
            Direction direction = context.getClickedFace();
            if (state.getValue(FACING) == Direction.SOUTH && (direction == Direction.NORTH || (!blSouth && direction.getAxis().isVertical()))) {
                return true;
            } else if (state.getValue(FACING) == Direction.EAST && (direction == Direction.WEST || (!blEast && direction.getAxis().isVertical()))) {
                return true;
            } else if (state.getValue(FACING) == Direction.NORTH && (direction == Direction.SOUTH || (blSouth && direction.getAxis().isVertical()))) {
                return true;
            } else {
                return state.getValue(FACING) == Direction.WEST && (direction == Direction.EAST || (blEast && direction.getAxis().isVertical()));
            }
        } else {
            return true;
        }
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidState) {
        return !state.getValue(IS_DOUBLE) && SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluidState);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
        return !state.getValue(IS_DOUBLE) && SimpleWaterloggedBlock.super.canPlaceLiquid(player, world, pos, state, fluid);
    }

    @Override
    protected BlockState updateShape(
            BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos
    ) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return switch (type) {
            case LAND, AIR -> false;
            case WATER -> state.getFluidState().is(FluidTags.WATER);
        };
    }
}
