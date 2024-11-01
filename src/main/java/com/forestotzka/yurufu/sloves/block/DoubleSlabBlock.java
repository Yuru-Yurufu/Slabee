package com.forestotzka.yurufu.sloves.block;

import com.forestotzka.yurufu.sloves.registry.tag.ModBlockTags;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class DoubleSlabBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final IntProperty LIGHT_LEVEL = Properties.LEVEL_15;

    public DoubleSlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIGHT_LEVEL, 0));
    }

    @Override
    protected MapCodec<? extends DoubleSlabBlock> getCodec() {
        return createCodec(DoubleSlabBlock::new);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_LEVEL);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DoubleSlabBlockEntity(pos, state);
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView view, BlockPos pos) {
        BlockEntity blockEntity = view.getBlockEntity(pos);
        if (!(blockEntity instanceof DoubleSlabBlockEntity entity)) {
            return 0.0F;
        }
        BlockState topSlab = entity.getTopSlabState();
        BlockState bottomSlab = entity.getBottomSlabState();

        return DoubleSlabUtils.getMiningSpeed(topSlab, bottomSlab, player, view, pos);
    }

    @Override
    public BlockState onBreak(World view, BlockPos pos, BlockState state, PlayerEntity player) {
        DoubleSlabBlockEntity entity = (DoubleSlabBlockEntity) view.getBlockEntity(pos);
        BlockState topSlab = Objects.requireNonNull(entity).getTopSlabState();
        BlockState bottomSlab = Objects.requireNonNull(entity).getBottomSlabState();
        if (!player.isCreative() && !player.isSpectator()) {
            double x = pos.getX() + 0.5d;
            double y = pos.getY() + 0.5d;
            double z = pos.getZ() + 0.5d;
            view.spawnEntity(new ItemEntity(view, x, y, z, new ItemStack(topSlab.getBlock())));
            view.spawnEntity(new ItemEntity(view, x, y, z, new ItemStack(bottomSlab.getBlock())));
        }
        this.spawnBreakParticles(view, player, pos, topSlab);
        this.spawnBreakParticles(view, player, pos, bottomSlab);
        view.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);

        return state;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
