package com.forestotzka.yurufu.sloves.block;

import com.forestotzka.yurufu.sloves.block.enums.VerticalSlabAxis;
import com.forestotzka.yurufu.sloves.registry.tag.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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

public class DoubleVerticalSlabBlockEntity extends BlockEntity {
    private final Identifier defaultPositiveSlabId = Identifier.of("sloves:purple_concrete_vertical_slab");
    private final Identifier defaultNegativeSlabId = Identifier.of("sloves:black_concrete_vertical_slab");
    private Identifier positiveSlabId = defaultPositiveSlabId;
    private Identifier negativeSlabId = defaultNegativeSlabId;
    private BlockState cachedPositiveSlabState;
    private BlockState cachedNegativeSlabState;

    public DoubleVerticalSlabBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DOUBLE_VERTICAL_SLAB_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        if (this.positiveSlabId != null) {
            NbtCompound positiveSlab = new NbtCompound();
            positiveSlab.putString("id", this.positiveSlabId.toString());
            nbt.put("positive_slab", positiveSlab);
        }

        if (this.negativeSlabId != null) {
            NbtCompound negativeSlab = new NbtCompound();
            negativeSlab.putString("id", this.negativeSlabId.toString());
            nbt.put("negative_slab", negativeSlab);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        if (nbt.contains("positive_slab")) {
            NbtCompound positiveSlabData = nbt.getCompound("positive_slab");
            this.positiveSlabId = Identifier.of(positiveSlabData.getString("id"));
        } else {
            this.positiveSlabId = defaultPositiveSlabId;
        }

        if (nbt.contains("negative_slab")) {
            NbtCompound negativeSlabData = nbt.getCompound("negative_slab");
            this.negativeSlabId = Identifier.of(negativeSlabData.getString("id"));
        } else {
            this.negativeSlabId = defaultNegativeSlabId;
        }

        this.cachedPositiveSlabState = null;
        this.cachedNegativeSlabState = null;

        if (this.world != null && !this.world.isClient()) {
            this.markDirty();
            world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), 3);
        }
    }

    public Identifier getPositiveSlabId() {
        return positiveSlabId;
    }

    public void setPositiveSlabId(Identifier id) {
        this.positiveSlabId = id;
        this.cachedPositiveSlabState = null;
        markDirty();
    }

    public Identifier getNegativeSlabId() {
        return negativeSlabId;
    }

    public void setNegativeSlabId(Identifier id) {
        this.negativeSlabId = id;
        this.cachedPositiveSlabState = null;
        markDirty();
    }

    public BlockState getPositiveSlabState() {
        if (this.cachedPositiveSlabState == null) {
            Block block = Registries.BLOCK.get(this.positiveSlabId);
            if (!block.getDefaultState().isIn(ModBlockTags.VERTICAL_SLABS)) {
                this.positiveSlabId = defaultPositiveSlabId;
                block = Registries.BLOCK.get(this.positiveSlabId);
            }
            BlockState blockState = this.world.getBlockState(this.pos);
            if (blockState.get(DoubleVerticalSlabBlock.AXIS) == VerticalSlabAxis.X) {
                this.cachedPositiveSlabState = block.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST);
            } else {
                this.cachedPositiveSlabState = block.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH);
            }
        }
        return this.cachedPositiveSlabState;
    }

    public BlockState getNegativeSlabState() {
        if (this.cachedNegativeSlabState == null) {
            Block block = Registries.BLOCK.get(this.negativeSlabId);
            if (!block.getDefaultState().isIn(ModBlockTags.VERTICAL_SLABS)) {
                this.negativeSlabId = defaultNegativeSlabId;
                block = Registries.BLOCK.get(this.negativeSlabId);
            }
            BlockState blockState = this.world.getBlockState(this.pos);
            if (blockState.get(DoubleVerticalSlabBlock.AXIS) == VerticalSlabAxis.X) {
                this.cachedNegativeSlabState = block.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST);
            } else {
                this.cachedNegativeSlabState = block.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH);
            }
        }
        return cachedNegativeSlabState;
    }

    public Integer getPositiveRenderLayerType() {
        if (Registries.BLOCK.get(this.positiveSlabId).getDefaultState().isIn(ModBlockTags.CUTOUT_SLABS)) {
            return 1;
        } else if (Registries.BLOCK.get(this.positiveSlabId).getDefaultState().isIn(ModBlockTags.CUTOUT_MIPPED_SLABS)) {
            return 2;
        } else {
            return 0;
        }
    }

    public Integer getNegativeRenderLayerType() {
        if (Registries.BLOCK.get(this.negativeSlabId).getDefaultState().isIn(ModBlockTags.CUTOUT_SLABS)) {
            return 1;
        } else if (Registries.BLOCK.get(this.negativeSlabId).getDefaultState().isIn(ModBlockTags.CUTOUT_MIPPED_SLABS)) {
            return 2;
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
}
