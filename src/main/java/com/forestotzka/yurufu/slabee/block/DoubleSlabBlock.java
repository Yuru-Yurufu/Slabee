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

public class DoubleSlabBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final BooleanProperty IS_EMISSIVE_LIGHTING = ModProperties.IS_EMISSIVE_LIGHTING;
    public static final IntProperty LIGHT_LEVEL = ModProperties.LIGHT_LEVEL;
    public static final BooleanProperty DOWN_OPAQUE = BooleanProperty.of("down_opaque");
    public static final BooleanProperty UP_OPAQUE = BooleanProperty.of("up_opaque");

    protected static final VoxelShape DOWN_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 15.99999, 16.0);
    protected static final VoxelShape UP_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.00001, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape NON_OPAQUE_SHAPE = Block.createCuboidShape(0.0, 0.00001, 0.0, 16.0, 15.99999, 16.0);

    public DoubleSlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIGHT_LEVEL, 0).with(DOWN_OPAQUE, false).with(UP_OPAQUE, false).with(IS_EMISSIVE_LIGHTING, false));
    }

    @Override
    protected MapCodec<? extends DoubleSlabBlock> getCodec() {
        return createCodec(DoubleSlabBlock::new);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_LEVEL).add(DOWN_OPAQUE).add(UP_OPAQUE).add(IS_EMISSIVE_LIGHTING);
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

    @Override
    protected boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(DOWN_OPAQUE) && state.get(UP_OPAQUE)) {
            return VoxelShapes.fullCube();
        } else if (state.get(DOWN_OPAQUE)) {
            return DOWN_OPAQUE_SHAPE;
        } else if (state.get(UP_OPAQUE)) {
            return UP_OPAQUE_SHAPE;
        } else {
            return NON_OPAQUE_SHAPE;
        }
    }
}
