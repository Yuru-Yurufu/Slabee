package com.forestotzka.yurufu.sloves.block;

import com.forestotzka.yurufu.sloves.block.enums.VerticalSlabAxis;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class DoubleVerticalSlabBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final EnumProperty<VerticalSlabAxis> AXIS = EnumProperty.of("axis", VerticalSlabAxis.class);

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
        builder.add(AXIS);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DoubleVerticalSlabBlockEntity(pos, state);
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView view, BlockPos pos) {
        BlockEntity blockEntity = view.getBlockEntity(pos);
        if (!(blockEntity instanceof DoubleVerticalSlabBlockEntity entity)) {
            return 0.0F;
        }
        BlockState positiveSlab = entity.getPositiveSlabState();
        BlockState negativeSlab = entity.getNegativeSlabState();
        float hardness = getHardness(positiveSlab, negativeSlab, view, pos);
        return hardness == -1.0F ? 0.0F : getBlockBreakingSpeed(positiveSlab, negativeSlab, player) / hardness / getHarvest(positiveSlab, negativeSlab, player);
    }

    private float getHardness(BlockState positiveSlab, BlockState negativeSlab, BlockView world, BlockPos pos) {
        float positiveHardness = positiveSlab.getHardness(world, pos);
        float negativeHardness = negativeSlab.getHardness(world, pos);
        if (positiveHardness == -1.0F || negativeHardness == -1.0F) {
            return -1.0F;
        }
        return (positiveHardness + negativeHardness) / 2.0F;
    }
    private float getBlockBreakingSpeed(BlockState positiveSlab, BlockState negativeSlab, PlayerEntity player) {
        return (player.getBlockBreakingSpeed(positiveSlab) + player.getBlockBreakingSpeed(negativeSlab)) / 2.0F;
    }
    private float getHarvest(BlockState positiveSlab, BlockState negativeSlab, PlayerEntity player) {
        float harvest = 30;
        boolean positiveCanHarvest = player.canHarvest(positiveSlab);
        boolean negativeCanHarvest = player.canHarvest(negativeSlab);
        if (!positiveCanHarvest && !negativeCanHarvest) {
            harvest = 100;
        } else if (!positiveCanHarvest || !negativeCanHarvest) {
            harvest = 50;
        }
        return harvest;
    }

    @Override
    public BlockState onBreak(World view, BlockPos pos, BlockState state, PlayerEntity player) {
        DoubleVerticalSlabBlockEntity entity = (DoubleVerticalSlabBlockEntity) view.getBlockEntity(pos);
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
}
