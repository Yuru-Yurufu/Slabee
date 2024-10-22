package com.forestotzka.yurufu.sloves.block;

import com.forestotzka.yurufu.sloves.registry.tag.ModBlockTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
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
        float hardness = getHardness(topSlab, bottomSlab, view, pos);
        return hardness == -1.0F ? 0.0F : getBlockBreakingSpeed(topSlab, bottomSlab, player) / hardness / getHarvest(topSlab, bottomSlab, player);
    }

    private float getHardness(BlockState topSlab, BlockState bottomSlab, BlockView world, BlockPos pos) {
        float topHardness = topSlab.getHardness(world, pos);
        float bottomHardness = bottomSlab.getHardness(world, pos);
        if (topHardness == -1.0F || bottomHardness == -1.0F) {
            return -1.0F;
        }
        return (topHardness + bottomHardness) / 2.0F;
    }
    private float getBlockBreakingSpeed(BlockState topSlab, BlockState bottomSlab, PlayerEntity player) {
        return (player.getBlockBreakingSpeed(topSlab) + player.getBlockBreakingSpeed(bottomSlab)) / 2.0F;
    }
    private float getHarvest(BlockState topSlab, BlockState bottomSlab, PlayerEntity player) {
        float harvest = 30;
        boolean topCanHarvest = canHarvest(topSlab, player);
        boolean bottomCanHarvest = canHarvest(bottomSlab, player);
        if (!topCanHarvest && !bottomCanHarvest) {
            harvest = 100;
        } else if (!topCanHarvest || !bottomCanHarvest) {
            harvest = 50;
        }
        return harvest;
    }
    private boolean canHarvest(BlockState state, PlayerEntity player) {
        ItemStack mainhandItem = player.getInventory().getMainHandStack();
        if (state.isIn(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return (mainhandItem.isOf(Items.DIAMOND_PICKAXE) || mainhandItem.isOf(Items.NETHERITE_PICKAXE));
        }
        return (player.canHarvest(state)) || (mainhandItem.isOf(Items.SHEARS) && state.isIn(ModBlockTags.MINEABLE_SHEARS));
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
}
