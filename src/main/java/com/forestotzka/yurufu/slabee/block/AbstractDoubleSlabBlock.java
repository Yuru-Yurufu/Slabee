package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.block.enums.DoubleSlabVariant;
import com.forestotzka.yurufu.slabee.state.property.ModProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractDoubleSlabBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final BooleanProperty IS_EMISSIVE_LIGHTING = ModProperties.IS_EMISSIVE_LIGHTING;
    public static final IntProperty LIGHT_LEVEL = ModProperties.LIGHT_LEVEL;
    public static final EnumProperty<DoubleSlabVariant> POSITIVE_SLAB = EnumProperty.of("positive_slab", DoubleSlabVariant.class);
    public static final EnumProperty<DoubleSlabVariant> NEGATIVE_SLAB = EnumProperty.of("negative_slab", DoubleSlabVariant.class);

    protected enum ShapeType {
        FULL,
        POSITIVE,
        NEGATIVE,
        NONE
    }

    protected AbstractDoubleSlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(IS_EMISSIVE_LIGHTING, false)
                .with(LIGHT_LEVEL, 0)
                .with(POSITIVE_SLAB, DoubleSlabVariant.NORMAL)
                .with(NEGATIVE_SLAB, DoubleSlabVariant.NORMAL));
    }

    @Override
    protected abstract MapCodec<? extends AbstractDoubleSlabBlock> getCodec();

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_LEVEL).add(IS_EMISSIVE_LIGHTING).add(POSITIVE_SLAB).add(NEGATIVE_SLAB);
    }

    @Override
    public abstract @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state);

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : (world1, pos, blockState, blockEntity) -> {
            if (blockEntity instanceof AbstractDoubleSlabBlockEntity entity) {
                entity.serverTick();
            }
        };
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView view, BlockPos pos) {
        BlockEntity blockEntity = view.getBlockEntity(pos);
        if (!(blockEntity instanceof AbstractDoubleSlabBlockEntity entity)) {
            return 0.0F;
        }

        BlockState positiveState = entity.getPositiveSlabState();
        BlockState negativeState = entity.getNegativeSlabState();

        if (player.isSneaking()) {
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
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.isIn(BlockTags.GUARDED_BY_PIGLINS)) {
            PiglinBrain.onGuardedBlockInteracted(player, false);
        }

        world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(player, state));
        return state;
    }

    protected abstract boolean isLookingPositiveHalf(BlockState state);

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
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
}
