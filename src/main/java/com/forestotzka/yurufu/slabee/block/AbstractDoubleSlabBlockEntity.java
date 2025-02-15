package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.function.ToIntFunction;

import static com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlock.LIGHT_LEVEL;

public abstract class AbstractDoubleSlabBlockEntity extends BlockEntity {
    protected final Identifier defaultPositiveSlabId;
    protected final Identifier defaultNegativeSlabId;
    protected final BlockState defaultPositiveSlabState;
    protected final BlockState defaultNegativeSlabState;
    protected Identifier positiveSlabId;
    protected Identifier negativeSlabId;
    protected BlockState positiveSlabState;
    protected BlockState negativeSlabState;
    protected final BlockState defaultBlockState = this.getCachedState();
    protected BlockState blockState = defaultBlockState;
    public static ToIntFunction<BlockState> LUMINANCE = (state) -> (Integer)state.get(LIGHT_LEVEL);

    private boolean init = true;

    public AbstractDoubleSlabBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, Identifier defaultPositiveSlabId, Identifier defaultNegativeSlabId) {
        super(type, pos, state);

        this.defaultPositiveSlabId = defaultPositiveSlabId;
        this.defaultNegativeSlabId = defaultNegativeSlabId;
        this.defaultPositiveSlabState = Registries.BLOCK.get(defaultPositiveSlabId).getDefaultState();
        this.defaultNegativeSlabState = Registries.BLOCK.get(defaultNegativeSlabId).getDefaultState();

        this.positiveSlabId = this.defaultPositiveSlabId;
        this.negativeSlabId = this.defaultNegativeSlabId;
        this.positiveSlabState = this.defaultPositiveSlabState;
        this.negativeSlabState = this.defaultNegativeSlabState;
    }

    public Identifier getPositiveSlabId() {
        return positiveSlabId;
    }

    public void setPositiveSlabId(Identifier id) {
        this.positiveSlabId = id;
        updatePositiveSlabState();
        markDirty();
    }

    public Identifier getNegativeSlabId() {
        return negativeSlabId;
    }

    public void setNegativeSlabId(Identifier id) {
        this.negativeSlabId = id;
        updateNegativeSlabState();
        markDirty();
    }

    public BlockState getPositiveSlabState() {
        return this.positiveSlabState;
    }

    public BlockState getNegativeSlabState() {
        return negativeSlabState;
    }

    public Integer getPositiveRenderLayerType() {
        Block block = Registries.BLOCK.get(this.positiveSlabId);
        if (SlabeeUtils.isCutoutSlabs(block)) {
            return 1;
        } else if (SlabeeUtils.isCutoutMippedSlabs(block)) {
            return 2;
        } else if (SlabeeUtils.isTranslucentSlabs(block)) {
            return 3;
        } else {
            return 0;
        }
    }

    public Integer getNegativeRenderLayerType() {
        Block block = Registries.BLOCK.get(this.negativeSlabId);
        if (SlabeeUtils.isCutoutSlabs(block)) {
            return 1;
        } else if (SlabeeUtils.isCutoutMippedSlabs(block)) {
            return 2;
        } else if (SlabeeUtils.isTranslucentSlabs(block)) {
            return 3;
        } else {
            return 0;
        }
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return this.createComponentlessNbt(registryLookup);
    }

    public void updateBlockProperties() {
        if (world != null) {
            if (!this.world.isClient()) {
                Block positiveSlab = positiveSlabState.getBlock();
                Block negativeSlab = negativeSlabState.getBlock();

                this.blockState = SlabeeUtils.getAbstractState(positiveSlab, negativeSlab, world.getBlockState(pos));
                world.setBlockState(pos, this.blockState, 3);
                this.markDirty();
                world.updateListeners(this.pos, this.getCachedState(), this.blockState, 3);
            }
        } else {
            this.blockState = this.defaultBlockState;
        }
    }

    public void serverTick() {
        if (init) {
            updateBlockProperties();
            init = false;
        }
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    protected void updateSlabState() {
        updatePositiveSlabState();
        updateNegativeSlabState();
    }

    protected abstract void updatePositiveSlabState();
    protected abstract void updateNegativeSlabState();

    protected boolean isTrueSlabId(Identifier i) {
        Block block = Registries.BLOCK.get(i);
        return DoubleSlabUtils.isTrueSlabId(i) && (block instanceof SlabBlock || block instanceof VerticalSlabBlock);
    }
}
