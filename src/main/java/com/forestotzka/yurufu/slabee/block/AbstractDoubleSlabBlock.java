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

import java.util.Objects;

public abstract class AbstractDoubleSlabBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final BooleanProperty IS_EMISSIVE_LIGHTING = ModProperties.IS_EMISSIVE_LIGHTING;
    public static final IntProperty LIGHT_LEVEL = ModProperties.LIGHT_LEVEL;
    public static final IntProperty OPAQUE = ModProperties.OPAQUE;
    public static final IntProperty SEE_THROUGH = ModProperties.SEE_THROUGH;

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
        BlockState positiveSlab = Objects.requireNonNull(entity).getPositiveSlabState();
        BlockState negativeSlab = Objects.requireNonNull(entity).getNegativeSlabState();
        if (!player.isCreative() && !player.isSpectator()) {
            double x = pos.getX() + 0.5d;
            double y = pos.getY() + 0.5d;
            double z = pos.getZ() + 0.5d;
            view.spawnEntity(new ItemEntity(view, x, y, z, new ItemStack(positiveSlab.getBlock())));
            view.spawnEntity(new ItemEntity(view, x, y, z, new ItemStack(negativeSlab.getBlock())));
        }
        this.spawnBreakParticles(view, player, pos, positiveSlab);
        this.spawnBreakParticles(view, player, pos, negativeSlab);
        view.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);

        return state;
    }

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
}
