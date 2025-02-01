package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.state.property.ModProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
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
    public static final IntProperty OPAQUE = ModProperties.OPAQUE;
    public static final IntProperty SEE_THROUGH = ModProperties.SEE_THROUGH;

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
                .with(OPAQUE, 3)
                .with(SEE_THROUGH, 0));
    }

    @Override
    protected abstract MapCodec<? extends AbstractDoubleSlabBlock> getCodec();

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_LEVEL).add(OPAQUE).add(IS_EMISSIVE_LIGHTING).add(SEE_THROUGH);
    }

    @Override
    public abstract @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state);

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView view, BlockPos pos) {
        BlockEntity blockEntity = view.getBlockEntity(pos);
        if (!(blockEntity instanceof AbstractDoubleSlabBlockEntity entity)) {
            return 0.0F;
        }

        return DoubleSlabUtils.getMiningSpeed(entity.getPositiveSlabState(), entity.getNegativeSlabState(), player, view, pos);
    }

    @Override
    public BlockState onBreak(World view, BlockPos pos, BlockState state, PlayerEntity player) {
        AbstractDoubleSlabBlockEntity entity = (AbstractDoubleSlabBlockEntity) view.getBlockEntity(pos);
        if (entity == null) return state;

        BlockState positiveSlab = entity.getPositiveSlabState();
        BlockState negativeSlab = entity.getNegativeSlabState();

        boolean isSneaking = player.isSneaking();
        boolean shouldDrop = !player.isCreative() && !player.isSpectator();
        boolean lookingAtPositiveHalf = !isSneaking || isLookingPositiveHalf(state);
        boolean lookingAtNegativeHalf = !isSneaking || !isLookingPositiveHalf(state);

        if (shouldDrop) {
            double x = pos.getX() + 0.5d;
            double y = pos.getY() + 0.5d;
            double z = pos.getZ() + 0.5d;
            if (lookingAtPositiveHalf) view.spawnEntity(new ItemEntity(view, x, y, z, new ItemStack(positiveSlab.getBlock())));
            if (lookingAtNegativeHalf) view.spawnEntity(new ItemEntity(view, x, y, z, new ItemStack(negativeSlab.getBlock())));
        }

        if (lookingAtPositiveHalf) this.spawnBreakParticles(view, player, pos, positiveSlab);
        if (lookingAtNegativeHalf) this.spawnBreakParticles(view, player, pos, negativeSlab);
/*

        if (isSneaking) {
            if (lookingAtPositiveHalf) {
                view.setBlockState(pos, negativeSlab.with(SlabBlock.TYPE, SlabType.BOTTOM), 3);
            } else {
                view.setBlockState(pos, positiveSlab.with(SlabBlock.TYPE, SlabType.TOP), 3);
            }
        }
*/
        view.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);

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
    protected int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        boolean positiveOpaque = DoubleSlabUtils.isPositiveOpaque(state);
        boolean negativeOpaque = DoubleSlabUtils.isNegativeOpaque(state);
        if (negativeOpaque && positiveOpaque) {
            return world.getMaxLightLevel();
        } else if (negativeOpaque || positiveOpaque) {
            return 1;
        } else {
            return 0;
        }
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
