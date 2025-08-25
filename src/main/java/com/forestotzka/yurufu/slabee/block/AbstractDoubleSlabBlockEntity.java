package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.Slabee;
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
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

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

    private ConversionRequest pendingPositive = null;
    private ConversionRequest pendingNegative = null;
    private boolean queued = false;

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
        updateBlockProperties();
    }

    public Identifier getNegativeSlabId() {
        return negativeSlabId;
    }

    public void setNegativeSlabId(Identifier id) {
        if (id.equals(Identifier.of(Slabee.MOD_ID, "dirt_path_slab"))) {
            this.negativeSlabId = Identifier.of(Slabee.MOD_ID, "dirt_slab");
        } else {
            this.negativeSlabId = id;
        }
        updateNegativeSlabState();
        updateBlockProperties();
    }

    public BlockState getPositiveSlabState() {
        return this.positiveSlabState;
    }

    public BlockState getNegativeSlabState() {
        return this.negativeSlabState;
    }

    public int getPositiveRenderLayerType() {
        Block block = Registries.BLOCK.get(this.positiveSlabId);
        return getRenderLayerType(block);
    }

    public int getNegativeRenderLayerType() {
        Block block = Registries.BLOCK.get(this.negativeSlabId);
        return getRenderLayerType(block);
    }

    public int getRenderLayerType(Block block) {
        if (SlabeeUtils.isCutoutMippedSlabs(block)) {
            return 1;
        } else if (ModBlockMap.toSlab(block) == ModBlocks.GLASS_SLAB) {
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

    public void serverTick(World world, BlockPos pos, BlockState state) {
        if (init) {
            updateBlockProperties();
            init = false;
        }

        if (!world.isClient && queued) {
            if (pendingPositive != null) {
                pendingPositive.delayTicks--;
                if (pendingPositive.delayTicks <= 0) {
                    convert(pendingPositive, true);
                }
            }

            if (pendingNegative != null) {
                pendingNegative.delayTicks--;
                if (pendingNegative.delayTicks <= 0) {
                    convert(pendingNegative, false);
                }
            }
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

    public abstract VoxelShape getPositiveOutlineShape();
    public abstract VoxelShape getNegativeOutlineShape();

    public enum Conversion {
        NONE,
        TO_DIRT,
        TO_GRASS,
        TO_SOMETHING
    }

    public static class ConversionRequest {
        public Conversion conversion;
        public int delayTicks;
        public int priority;

        public ConversionRequest(Conversion conversion, int delayTicks, int priority) {
            this.conversion = conversion;
            this.delayTicks = Math.max(1, delayTicks);
            this.priority = priority;
        }

        public static ConversionRequest mergeOrCreate(ConversionRequest first, Conversion conversion, int delayTicks, int priority) {
            if (first == null) return new ConversionRequest(conversion, delayTicks, priority);
            if (priority >= first.priority) {
                first.conversion = conversion;
                first.delayTicks = delayTicks;
                first.priority = priority;
            }
            return first;
        }
    }

    public void requestConversion(Conversion conversion, boolean isPositive, int delayTicks, int priority) {
        if (world == null || world.isClient || conversion == null) return;

        if (isPositive) {
            pendingPositive = ConversionRequest.mergeOrCreate(pendingPositive, conversion, delayTicks, priority);
        } else {
            pendingNegative = ConversionRequest.mergeOrCreate(pendingNegative, conversion, delayTicks, priority);
        }

        this.queued = true;
    }

    private void convert(ConversionRequest req, boolean isPositive) {
        // 実行
        switch (req.conversion) {
            case TO_DIRT -> convertToDirt(isPositive);
            default -> {
            }
        }

        // リセット
        req.conversion = Conversion.NONE;
        req.delayTicks = 0;
        queued = false;
    }

    protected abstract void convertToDirt(boolean isPositive);
}
