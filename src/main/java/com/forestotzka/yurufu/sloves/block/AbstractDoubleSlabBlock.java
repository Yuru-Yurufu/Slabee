package com.forestotzka.yurufu.sloves.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AbstractDoubleSlabBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final IntProperty LIGHT_LEVEL = Properties.LEVEL_15;

    protected AbstractDoubleSlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIGHT_LEVEL, 0));
    }

    @Override
    protected MapCodec<? extends AbstractDoubleSlabBlock> getCodec() {
        return createCodec(AbstractDoubleSlabBlock::new);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_LEVEL);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView view, BlockPos pos) {
        BlockEntity blockEntity = view.getBlockEntity(pos);
        if (!(blockEntity instanceof AbstractDoubleSlabBlockEntity entity)) {
            return 0.0F;
        }
        BlockState positiveSlab = entity.getPositiveSlabState();
        BlockState negativeSlab = entity.getNegativeSlabState();

        return DoubleSlabUtils.getMiningSpeed(positiveSlab, negativeSlab, player, view, pos);
    }

    @Override
    public BlockState onBreak(World view, BlockPos pos, BlockState state, PlayerEntity player) {
        DoubleSlabBlockEntity entity = (DoubleSlabBlockEntity) view.getBlockEntity(pos);
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
}
