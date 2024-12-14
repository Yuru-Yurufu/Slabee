package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import com.forestotzka.yurufu.slabee.state.property.ModProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class DoubleVerticalSlabBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final BooleanProperty IS_OPAQUE = ModProperties.IS_OPAQUE;
    public static final BooleanProperty IS_EMISSIVE_LIGHTING = ModProperties.IS_EMISSIVE_LIGHTING;
    public static final IntProperty LIGHT_LEVEL = ModProperties.LIGHT_LEVEL;
    public static final EnumProperty<VerticalSlabAxis> AXIS = EnumProperty.of("axis", VerticalSlabAxis.class);

    public DoubleVerticalSlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIGHT_LEVEL, 0).with(IS_OPAQUE, false).with(IS_EMISSIVE_LIGHTING, false).with(AXIS, VerticalSlabAxis.X));
    }

    @Override
    protected MapCodec<? extends DoubleVerticalSlabBlock> getCodec() {
        return createCodec(DoubleVerticalSlabBlock::new);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_LEVEL).add(IS_OPAQUE).add(IS_EMISSIVE_LIGHTING).add(AXIS);
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

        return DoubleSlabUtils.getMiningSpeed(entity.getPositiveSlabState(), entity.getNegativeSlabState(), player, view, pos);
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

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
