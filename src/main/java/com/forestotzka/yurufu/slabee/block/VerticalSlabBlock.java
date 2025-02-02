package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import com.forestotzka.yurufu.slabee.registry.tag.ModBlockTags;
import com.forestotzka.yurufu.slabee.registry.tag.ModItemTags;
import com.forestotzka.yurufu.slabee.state.property.ModProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlock.AXIS;

public class VerticalSlabBlock extends Block implements Waterloggable {
    public static final MapCodec<VerticalSlabBlock> CODEC = createCodec(VerticalSlabBlock::new);
    public static final BooleanProperty IS_DOUBLE = ModProperties.IS_DOUBLE;
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    protected static final VoxelShape SOUTH = Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EAST = Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape NORTH = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 8.0);
    protected static final VoxelShape WEST = Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 16.0, 16.0);

    @Override
    public MapCodec<? extends VerticalSlabBlock> getCodec() {
        return CODEC;
    }

    public VerticalSlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(IS_DOUBLE, Boolean.FALSE).with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected boolean hasSidedTransparency(BlockState state) {
        return !state.get(IS_DOUBLE);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(IS_DOUBLE, FACING, WATERLOGGED);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(IS_DOUBLE)) {
            return VoxelShapes.fullCube();
        } else {
            if (Objects.requireNonNull(state.get(FACING)) == Direction.SOUTH) {
                return SOUTH;
            } else if (Objects.requireNonNull(state.get(FACING)) == Direction.EAST) {
                return EAST;
            } else if (Objects.requireNonNull(state.get(FACING)) == Direction.NORTH) {
                return NORTH;
            } else {
                return WEST;
            }
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        BlockState oldState = world.getBlockState(pos);
        BlockState newState;

        if (oldState.isIn(ModBlockTags.VERTICAL_SLABS)) {
            String axis;
            Block positiveSlab;
            Block negativeSlab;
            Item placementItem = ctx.getStack().getItem();

            if (placementItem instanceof BlockItem blockItem) {
                Direction facing = oldState.get(HorizontalFacingBlock.FACING);
                if (facing  == Direction.SOUTH || facing == Direction.EAST) {
                    positiveSlab = oldState.getBlock();
                    negativeSlab = blockItem.getBlock();
                } else {
                    positiveSlab = blockItem.getBlock();
                    negativeSlab = oldState.getBlock();
                }

                Identifier positiveId = Registries.BLOCK.getId(positiveSlab);
                Identifier negativeId = Registries.BLOCK.getId(negativeSlab);

                if (facing == Direction.EAST || facing == Direction.WEST) {
                    axis = "x";
                } else {
                    axis = "z";
                }

                newState = SlabeeUtils.getAbstractState(positiveSlab, negativeSlab, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK.getDefaultState().with(AXIS, VerticalSlabAxis.fromString(axis)));

                world.setBlockState(pos, newState, 3);

                DoubleVerticalSlabBlockEntity blockEntity = (DoubleVerticalSlabBlockEntity) world.getBlockEntity(pos);
                if (blockEntity != null) {
                    blockEntity.setAxis(axis);
                    blockEntity.setPositiveSlabId(positiveId);
                    blockEntity.setNegativeSlabId(negativeId);
                }

                return newState;
            }

            return oldState;
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(pos);

            Direction direction = ctx.getHorizontalPlayerFacing();
            Direction[] directions = ctx.getPlacementDirections();
            boolean blEast = ctx.getHitPos().x - (double)ctx.getBlockPos().getX() > 0.5;
            boolean blSouth = ctx.getHitPos().z - (double)ctx.getBlockPos().getZ() > 0.5;

            for (Direction value : directions) {
                if (oldState.canPlaceAt(ctx.getWorld(), pos)) {
                    if ((direction == Direction.EAST && !blEast) || (direction == Direction.WEST && blEast) || (direction == Direction.SOUTH && !blSouth) || (direction == Direction.NORTH && blSouth)) {
                        direction = direction.getOpposite();
                    }
                    break;
                }
            }

            return this.getDefaultState()
                    .with(IS_DOUBLE, Boolean.FALSE)
                    .with(FACING, direction)
                    .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        }
    }

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        ItemStack itemStack = context.getStack();
        if (state.get(IS_DOUBLE) || !itemStack.isIn(ModItemTags.VERTICAL_SLABS)) {
            return false;
        } else if (context.canReplaceExisting()) {
            boolean blEast = context.getHitPos().x - (double)context.getBlockPos().getX() > 0.5;
            boolean blSouth = context.getHitPos().z - (double)context.getBlockPos().getZ() > 0.5;
            Direction direction = context.getSide();
            if (Objects.requireNonNull(state.get(FACING)) == Direction.SOUTH && (direction == Direction.NORTH || (!blSouth && direction.getAxis().isVertical()))) {
                return true;
            } else if (Objects.requireNonNull(state.get(FACING)) == Direction.EAST && (direction == Direction.WEST || (!blEast && direction.getAxis().isVertical()))) {
                return true;
            } else if (Objects.requireNonNull(state.get(FACING)) == Direction.NORTH && (direction == Direction.SOUTH || (blSouth && direction.getAxis().isVertical()))) {
                return true;
            } else {
                return Objects.requireNonNull(state.get(FACING)) == Direction.WEST && (direction == Direction.EAST || (blEast && direction.getAxis().isVertical()));
            }
        } else {
            return true;
        }
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return !state.get(IS_DOUBLE) && Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
    }

    @Override
    public boolean canFillWithFluid(@Nullable PlayerEntity player, BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return !state.get(IS_DOUBLE) && Waterloggable.super.canFillWithFluid(player, world, pos, state, fluid);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos
    ) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return switch (type) {
            case LAND -> false;
            case WATER -> state.getFluidState().isIn(FluidTags.WATER);
            case AIR -> false;
            default -> false;
        };
    }
}
