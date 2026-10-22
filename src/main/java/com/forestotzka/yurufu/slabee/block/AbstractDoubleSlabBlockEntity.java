package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.Slabee;
import com.forestotzka.yurufu.slabee.SlabeeUtils;
import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import static com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlock.LIGHT_LEVEL;

public abstract class AbstractDoubleSlabBlockEntity extends BlockEntity {
    protected final ResourceLocation defaultPositiveSlabId;
    protected final ResourceLocation defaultNegativeSlabId;
    protected final BlockState defaultPositiveSlabState;
    protected final BlockState defaultNegativeSlabState;
    protected ResourceLocation positiveSlabId;
    protected ResourceLocation negativeSlabId;
    protected BlockState positiveSlabState;
    protected BlockState negativeSlabState;
    protected final BlockState defaultBlockState = this.getBlockState();
    protected BlockState blockState = defaultBlockState;
    public static ToIntFunction<BlockState> LUMINANCE = (state) -> (Integer)state.getValue(LIGHT_LEVEL);

    private boolean init = true;

    private ConversionRequest pendingPositive = null;
    private ConversionRequest pendingNegative = null;
    private boolean queued = false;

    public AbstractDoubleSlabBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ResourceLocation defaultPositiveSlabId, ResourceLocation defaultNegativeSlabId) {
        super(type, pos, state);

        this.defaultPositiveSlabId = defaultPositiveSlabId;
        this.defaultNegativeSlabId = defaultNegativeSlabId;
        this.defaultPositiveSlabState = BuiltInRegistries.BLOCK.get(defaultPositiveSlabId).defaultBlockState();
        this.defaultNegativeSlabState = BuiltInRegistries.BLOCK.get(defaultNegativeSlabId).defaultBlockState();

        this.positiveSlabId = this.defaultPositiveSlabId;
        this.negativeSlabId = this.defaultNegativeSlabId;
        this.positiveSlabState = this.defaultPositiveSlabState;
        this.negativeSlabState = this.defaultNegativeSlabState;
    }

    public ResourceLocation getPositiveSlabId() {
        return positiveSlabId;
    }

    public void setPositiveSlabId(ResourceLocation id) {
        this.positiveSlabId = id;
        updatePositiveSlabState();
        updateBlockProperties();
    }

    public ResourceLocation getNegativeSlabId() {
        return negativeSlabId;
    }

    public void setNegativeSlabId(ResourceLocation id) {
        if (id.equals(ResourceLocation.fromNamespaceAndPath(Slabee.MOD_ID, "dirt_path_slab"))) {
            this.negativeSlabId = ResourceLocation.fromNamespaceAndPath(Slabee.MOD_ID, "dirt_slab");
        } else {
            this.negativeSlabId = id;
        }
        updateNegativeSlabState();
        updateBlockProperties();
    }

    /** チャンクの描画を組み立てるときに、両側のハーフをモデルへ渡す（Fabric の RenderDataBlockEntity） */
    public Object getRenderData() {
        return new DoubleSlabRenderData(this.positiveSlabState, this.negativeSlabState);
    }

    public BlockState getPositiveSlabState() {
        return this.positiveSlabState;
    }

    public BlockState getNegativeSlabState() {
        return this.negativeSlabState;
    }

    public int getPositiveRenderLayerType() {
        Block block = BuiltInRegistries.BLOCK.get(this.positiveSlabId);
        return getRenderLayerType(block);
    }

    public int getNegativeRenderLayerType() {
        Block block = BuiltInRegistries.BLOCK.get(this.negativeSlabId);
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
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
        return this.saveCustomOnly(registryLookup);
    }

    public void updateBlockProperties() {
        if (level != null) {
            if (!this.level.isClientSide()) {
                Block positiveSlab = positiveSlabState.getBlock();
                Block negativeSlab = negativeSlabState.getBlock();

                this.blockState = SlabeeUtils.getAbstractState(positiveSlab, negativeSlab, level.getBlockState(worldPosition));
                level.setBlock(worldPosition, this.blockState, 3);
                this.setChanged();
                level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.blockState, 3);
            } else {
                // クライアント：両側のハーフが変わったら、チャンクの描画を組み立て直す
                level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
            }
        } else {
            this.blockState = this.defaultBlockState;
        }
    }

    public void serverTick(Level world, BlockPos pos, BlockState state) {
        if (init) {
            updateBlockProperties();
            init = false;
        }

        if (!world.isClientSide && queued) {
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

    public BlockState getCombinedBlockState() {
        return this.blockState;
    }

    protected void updateSlabState() {
        updatePositiveSlabState();
        updateNegativeSlabState();
    }

    protected abstract void updatePositiveSlabState();
    protected abstract void updateNegativeSlabState();

    protected boolean isTrueSlabId(ResourceLocation i) {
        Block block = BuiltInRegistries.BLOCK.get(i);
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
        if (level == null || level.isClientSide || conversion == null) return;

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
