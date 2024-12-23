package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.registry.tag.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Objects;
import java.util.function.ToIntFunction;

import static com.forestotzka.yurufu.slabee.block.DoubleSlabBlock.LIGHT_LEVEL;

public class DoubleSlabBlockEntity extends BlockEntity {
    private final Identifier defaultTopSlabId = Identifier.of("slabee:purple_concrete_slab");
    private final Identifier defaultBottomSlabId = Identifier.of("slabee:black_concrete_slab");
    private final BlockState defaultTopSlabState = Registries.BLOCK.get(defaultTopSlabId).getDefaultState();
    private final BlockState defaultBottomSlabState = Registries.BLOCK.get(defaultBottomSlabId).getDefaultState();
    private Identifier topSlabId = defaultTopSlabId;
    private Identifier bottomSlabId = defaultBottomSlabId;
    private Direction topSlabFacing = Direction.SOUTH;
    private Direction bottomSlabFacing = Direction.SOUTH;
    private BlockState topSlabState = defaultTopSlabState;
    private BlockState bottomSlabState = defaultBottomSlabState;
    public static ToIntFunction<BlockState> LUMINANCE = (state) -> (Integer)state.get(LIGHT_LEVEL);
    public static boolean IS_OPAQUE;
    public static boolean DOWN_OPAQUE;
    public static boolean UP_OPAQUE;
    //public static Predicate<BlockState> IS_OPAQUE = state -> state.get(DoubleSlabBlock.IS_OPAQUE);

    public DoubleSlabBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DOUBLE_SLAB_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        if (this.topSlabId != null) {
            NbtCompound topSlab = new NbtCompound();
            topSlab.putString("id", this.topSlabId.toString());
            topSlab.putString("facing", this.topSlabFacing.getName());
            nbt.put("top_slab", topSlab);
        }

        if (this.bottomSlabId != null) {
            NbtCompound bottomSlab = new NbtCompound();
            bottomSlab.putString("id", this.bottomSlabId.toString());
            bottomSlab.putString("facing", this.bottomSlabFacing.getName());
            nbt.put("bottom_slab", bottomSlab);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        if (nbt.contains("top_slab")) {
            NbtCompound topSlabData = nbt.getCompound("top_slab");
            this.topSlabId = Identifier.of(topSlabData.getString("id"));
            this.topSlabFacing = Direction.byName(topSlabData.getString("facing"));
        } else {
            this.topSlabId = defaultTopSlabId;
            this.topSlabFacing = Direction.SOUTH;
        }

        if (nbt.contains("bottom_slab")) {
            NbtCompound bottomSlabData = nbt.getCompound("bottom_slab");
            this.bottomSlabId = Identifier.of(bottomSlabData.getString("id"));
            this.bottomSlabFacing = Direction.byName(bottomSlabData.getString("facing"));
        } else {
            this.bottomSlabId = defaultBottomSlabId;
            this.bottomSlabFacing = Direction.SOUTH;
        }

        updateSlabState();

        if (this.world != null && !this.world.isClient()) {
            updateBlockProperties();
            this.markDirty();
            world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), 3);
        }
    }

    public Identifier getTopSlabId() {
        return topSlabId;
    }

    public void setTopSlabId(Identifier id) {
        this.topSlabId = id;
        updateTopSlabState();
        markDirty();
    }

    public Identifier getBottomSlabId() {
        return bottomSlabId;
    }

    public void setBottomSlabId(Identifier id) {
        this.bottomSlabId = id;
        updateBottomSlabState();
        markDirty();
    }

    public BlockState getTopSlabState() {
        return this.topSlabState;
    }

    public BlockState getBottomSlabState() {
        return this.bottomSlabState;
    }

    public Integer getTopRenderLayerType() {
        Block block = Registries.BLOCK.get(this.topSlabId);
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

    public Integer getBottomRenderLayerType() {
        Block block = Registries.BLOCK.get(this.bottomSlabId);
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
        int topLuminance;
        int bottomLuminance;
        if (topSlabState.isOf(ModBlocks.MAGMA_BLOCK_SLAB)) {
            topLuminance = 3;
        } else if (topSlabState.isOf(ModBlocks.CRYING_OBSIDIAN_SLAB)) {
            topLuminance = 1;
        } else {
            topLuminance = 0;
        }
        if (bottomSlabState.isOf(ModBlocks.MAGMA_BLOCK_SLAB)) {
            bottomLuminance = 3;
        } else if (bottomSlabState.isOf(ModBlocks.CRYING_OBSIDIAN_SLAB)) {
            bottomLuminance = 1;
        } else {
            bottomLuminance = 0;
        }
        int luminance = Math.max(topLuminance, bottomLuminance);

        Block topSlab = topSlabState.getBlock();
        Block bottomSlab = bottomSlabState.getBlock();
        IS_OPAQUE = (SlabeeUtils.isOpaqueSlabs(topSlab) || SlabeeUtils.isOpaqueSlabs(bottomSlab));
        DOWN_OPAQUE = SlabeeUtils.isOpaqueSlabs(bottomSlab);
        UP_OPAQUE = SlabeeUtils.isOpaqueSlabs(topSlab);
        boolean isEmissiveLighting = (SlabeeUtils.isEmissiveLightingSlabs(topSlab) || SlabeeUtils.isEmissiveLightingSlabs(bottomSlab));

        Objects.requireNonNull(world).setBlockState(pos, world.getBlockState(pos).with(DoubleSlabBlock.LIGHT_LEVEL, luminance).with(DoubleSlabBlock.IS_OPAQUE, IS_OPAQUE).with(DoubleSlabBlock.DOWN_OPAQUE, DOWN_OPAQUE).with(DoubleSlabBlock.UP_OPAQUE, UP_OPAQUE).with(DoubleSlabBlock.IS_EMISSIVE_LIGHTING, isEmissiveLighting), 3);
    }

    private void updateSlabState() {
        updateTopSlabState();
        updateBottomSlabState();
    }

    private void updateTopSlabState() {
        this.topSlabState = Registries.BLOCK.get(topSlabId).getDefaultState().with(Properties.SLAB_TYPE, SlabType.TOP);
    }

    private void updateBottomSlabState() {
        this.bottomSlabState = Registries.BLOCK.get(bottomSlabId).getDefaultState();
    }
}
