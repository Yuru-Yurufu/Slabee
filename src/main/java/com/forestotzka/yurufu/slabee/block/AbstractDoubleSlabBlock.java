package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.block.enums.DoubleSlabVariant;
import com.forestotzka.yurufu.slabee.state.property.ModProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractDoubleSlabBlock extends BaseEntityBlock implements EntityBlock {
    public static final IntegerProperty LIGHT_LEVEL = ModProperties.LIGHT_LEVEL;
    public static final EnumProperty<DoubleSlabVariant> POSITIVE_SLAB = EnumProperty.create("positive_slab", DoubleSlabVariant.class);
    public static final EnumProperty<DoubleSlabVariant> NEGATIVE_SLAB = EnumProperty.create("negative_slab", DoubleSlabVariant.class);
    protected static final VoxelShape SOUL_SAND_COLLISION_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);
    protected static final VoxelShape DIRT_PATH_COLLISION_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);

    protected enum ShapeType {
        FULL,
        POSITIVE,
        NEGATIVE,
        NONE
    }

    protected AbstractDoubleSlabBlock(Properties settings) {
        super(settings.dynamicShape());
        this.registerDefaultState(this.defaultBlockState()
                .setValue(LIGHT_LEVEL, 0)
                .setValue(POSITIVE_SLAB, DoubleSlabVariant.NORMAL)
                .setValue(NEGATIVE_SLAB, DoubleSlabVariant.NORMAL));
    }

    @Override
    protected abstract MapCodec<? extends AbstractDoubleSlabBlock> codec();

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_LEVEL).add(POSITIVE_SLAB).add(NEGATIVE_SLAB);
    }

    @Override
    public abstract @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state);

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return world.isClientSide ? null : (world1, pos, blockState, blockEntity) -> {
            if (blockEntity instanceof AbstractDoubleSlabBlockEntity entity) {
                entity.serverTick(world1, pos, blockState);
            }
        };
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter view, BlockPos pos) {
        BlockEntity blockEntity = view.getBlockEntity(pos);
        if (!(blockEntity instanceof AbstractDoubleSlabBlockEntity entity)) {
            return 0.0F;
        }

        BlockState positiveState = entity.getPositiveSlabState();
        BlockState negativeState = entity.getNegativeSlabState();

        if (player.isShiftKeyDown()) {
            if (isLookingPositiveHalf(state)) {
                return DoubleSlabUtils.getMiningSpeed(positiveState, positiveState, player, view, pos);
            } else {
                return DoubleSlabUtils.getMiningSpeed(negativeState, negativeState, player, view, pos);
            }
        } else {
            return DoubleSlabUtils.getMiningSpeed(positiveState, negativeState, player, view, pos);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (state.is(BlockTags.GUARDED_BY_PIGLINS)) {
            PiglinAi.angerNearbyPiglins(player, false);
        }

        world.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(player, state));
        return state;
    }

    protected abstract boolean isLookingPositiveHalf(BlockState state);

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        boolean positiveSeeThrough = DoubleSlabUtils.isPositiveSeeThrough(state);
        boolean negativeSeeThrough = DoubleSlabUtils.isNegativeSeeThrough(state);
        if (positiveSeeThrough || negativeSeeThrough) {
            return 1.0f;
        } else {
            float f = 0.0f;
            if (world.getBlockEntity(pos) instanceof AbstractDoubleSlabBlockEntity entity) {
                if (ModBlockMap.toSlab(entity.getPositiveSlabState().getBlock()) == ModBlocks.SOUL_SAND_SLAB) f += 0.1f;
                if (ModBlockMap.toSlab(entity.getNegativeSlabState().getBlock()) == ModBlocks.SOUL_SAND_SLAB) f += 0.1f;
            }
            return f;
        }
    }

    protected ShapeType calcCullingShapeType(BlockState state) {
        boolean positiveSeeThrough = DoubleSlabUtils.isPositiveSeeThrough(state);
        boolean negativeSeeThrough = DoubleSlabUtils.isNegativeSeeThrough(state);
        if (positiveSeeThrough && negativeSeeThrough) {
            return ShapeType.NONE;
        } else if (positiveSeeThrough) {
            return ShapeType.NEGATIVE;
        } else if (negativeSeeThrough) {
            return ShapeType.POSITIVE;
        } else {
            return ShapeType.FULL;
        }
    }

    protected static ShapeType calcLightingShapeType(BlockState state) {
        boolean positiveOpaque = DoubleSlabUtils.isPositiveOpaque(state);
        boolean negativeOpaque = DoubleSlabUtils.isNegativeOpaque(state);
        if (positiveOpaque && negativeOpaque) {
            return ShapeType.FULL;
        } else if (positiveOpaque) {
            return ShapeType.POSITIVE;
        } else if (negativeOpaque) {
            return ShapeType.NEGATIVE;
        } else {
            return ShapeType.NONE;
        }
    }

    private static final int SCHEDULED_TICK_DELAY = 20;

    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        BubbleColumnBlock.updateColumn(world, pos.above(), state);
    }

    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && neighborState.is(Blocks.WATER)) {
            world.scheduleTick(pos, this, SCHEDULED_TICK_DELAY);
        }

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    protected void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleTick(pos, this, SCHEDULED_TICK_DELAY);
    }

    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.block();
    }

    protected VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    public static VoxelShape getSoulSandCollisionShape() {
        return SOUL_SAND_COLLISION_SHAPE;
    }

    public static VoxelShape getDirtPathCollisionShape() {
        return DIRT_PATH_COLLISION_SHAPE;
    }
}
