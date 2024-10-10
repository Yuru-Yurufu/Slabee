package com.forestotzka.yurufu.sloves.block;

import com.forestotzka.yurufu.sloves.block.enums.CustomVerticalSlabType;
import com.forestotzka.yurufu.sloves.registry.tag.ModBlockTags;
import com.forestotzka.yurufu.sloves.registry.tag.ModItemTags;
import com.forestotzka.yurufu.sloves.state.property.ModProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class VerticalSlabBlock extends Block implements Waterloggable {
    public static final MapCodec<VerticalSlabBlock> CODEC = createCodec(VerticalSlabBlock::new);
    public static final BooleanProperty IS_DOUBLE = ModProperties.IS_DOUBLE;
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    //public static final EnumProperty<CustomVerticalSlabType> SECOND_VERTICAL_SLAB = ModProperties.SECOND_VERTICAL_SLAB;
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
        //this.setDefaultState(this.getDefaultState().with(IS_DOUBLE, Boolean.valueOf(false)).with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.valueOf(false)).with(SECOND_VERTICAL_SLAB, CustomVerticalSlabType.NONE));
        this.setDefaultState(this.getDefaultState().with(IS_DOUBLE, Boolean.valueOf(false)).with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    protected boolean hasSidedTransparency(BlockState state) {
        return !state.get(IS_DOUBLE);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        //builder.add(IS_DOUBLE, FACING, WATERLOGGED, SECOND_VERTICAL_SLAB);
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
        ItemStack itemStack = ctx.getStack();
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        Direction direction = ctx.getHorizontalPlayerFacing();
        if (blockState.isOf(this)) {
            String itemName = itemStack.getItem().toString();
            CustomVerticalSlabType sb  = CustomVerticalSlabType.fromString(itemName);
            //return blockState.with(IS_DOUBLE, Boolean.valueOf(true)).with(FACING, direction).with(WATERLOGGED, Boolean.valueOf(false)).with(SECOND_VERTICAL_SLAB, sb);
            return blockState.with(IS_DOUBLE, Boolean.valueOf(true)).with(FACING, direction).with(WATERLOGGED, Boolean.valueOf(false));
        } else if (blockState.isIn(ModBlockTags.VERTICAL_SLABS)) {
            Direction d;
            String itemName = itemStack.getItem().toString();
            CustomVerticalSlabType sb  = CustomVerticalSlabType.fromString(itemName);
            if (Objects.requireNonNull(blockState.get(FACING)) == Direction.SOUTH) {
                d = Direction.SOUTH;
            } else if (Objects.requireNonNull(blockState.get(FACING)) == Direction.EAST) {
                d = Direction.EAST;
            } else if (Objects.requireNonNull(blockState.get(FACING)) == Direction.NORTH) {
                d = Direction.NORTH;
            } else {
                d = Direction.WEST;
            }
            //return blockState.with(IS_DOUBLE, Boolean.valueOf(true)).with(FACING, d).with(WATERLOGGED, Boolean.valueOf(false)).with(SECOND_VERTICAL_SLAB, sb);
            return blockState.with(IS_DOUBLE, Boolean.valueOf(true)).with(FACING, d).with(WATERLOGGED, Boolean.valueOf(false));
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(blockPos);

            BlockState blockState2 = this.getDefaultState()
                    .with(IS_DOUBLE, Boolean.valueOf(false))
                    .with(FACING, ctx.getHorizontalPlayerFacing())
                    .with(WATERLOGGED, Boolean.valueOf(fluidState.getFluid() == Fluids.WATER));
            return blockState2;
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
            } else if (Objects.requireNonNull(state.get(FACING)) == Direction.WEST && (direction == Direction.EAST || (blEast && direction.getAxis().isVertical()))) {
                return true;
            } else {
                return false;
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
        return !state.get(IS_DOUBLE) ? Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState) : false;
    }

    @Override
    public boolean canFillWithFluid(@Nullable PlayerEntity player, BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return !state.get(IS_DOUBLE) ? Waterloggable.super.canFillWithFluid(player, world, pos, state, fluid) : false;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos
    ) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        switch (type) {
            case LAND:
                return false;
            case WATER:
                return state.getFluidState().isIn(FluidTags.WATER);
            case AIR:
                return false;
            default:
                return false;
        }
    }
}
