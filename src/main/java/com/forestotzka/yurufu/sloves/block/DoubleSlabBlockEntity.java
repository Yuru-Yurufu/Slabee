package com.forestotzka.yurufu.sloves.block;

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

public class DoubleSlabBlockEntity extends BlockEntity {
    private Identifier topSlabId = Identifier.of("sloves:purple_concrete_slab");
    private Identifier bottomSlabId = Identifier.of("sloves:black_concrete_slab");
    private Direction topSlabFacing = Direction.SOUTH;
    private Direction bottomSlabFacing = Direction.SOUTH;
    private BlockState cachedTopSlabState;
    private BlockState cachedBottomSlabState;

    public DoubleSlabBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DOUBLE_SLAB_BLOCK_ENTITY, pos, state);
        System.out.println("inittttttttttttttttttaaaaaaaaaaaaa");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        NbtCompound topSlab = new NbtCompound();
        topSlab.putString("id", this.topSlabId.toString());
        topSlab.putString("facing", this.topSlabFacing.getName());
        nbt.put("top_slab", topSlab);

        NbtCompound bottomSlab = new NbtCompound();
        bottomSlab.putString("id", this.bottomSlabId.toString());
        bottomSlab.putString("facing", this.bottomSlabFacing.getName());
        nbt.put("bottom_slab", bottomSlab);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        if (nbt.contains("top_slab")) {
            NbtCompound topSlabData = nbt.getCompound("top_slab");
            this.topSlabId = Identifier.of(topSlabData.getString("id"));
            this.topSlabFacing = Direction.byName(topSlabData.getString("facing"));
            System.out.println(topSlabId);
        } else {
            this.topSlabId = Identifier.of("sloves:purple_wool_slab");
            this.topSlabFacing = Direction.SOUTH;
        }

        if (nbt.contains("bottom_slab")) {
            NbtCompound bottomSlabData = nbt.getCompound("bottom_slab");
            this.bottomSlabId = Identifier.of(bottomSlabData.getString("id"));
            this.bottomSlabFacing = Direction.byName(bottomSlabData.getString("facing"));
            System.out.println("booooooooooooooootommmmmmmmmmmmmmmmmmmm");
        } else {
            this.bottomSlabId = Identifier.of("sloves:black_wool_slab");
            this.bottomSlabFacing = Direction.SOUTH;
        }

        this.cachedTopSlabState = null;
        this.cachedBottomSlabState = null;

        if (this.world != null && !this.world.isClient()) {
            this.markDirty();
            world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), 3);
        }
    }

    public Identifier getTopSlabId() {
        return topSlabId;
    }

    public void setTopSlabId(Identifier id) {
        this.topSlabId = id;
        this.cachedTopSlabState = null;
        markDirty();
    }

    public Direction getTopSlabFacing() {
        return topSlabFacing;
    }

    public void setTopSlabFacing(Direction facing) {
        this.topSlabFacing = facing;
        markDirty();
    }

    public Identifier getBottomSlabId() {
        return bottomSlabId;
    }

    public void setBottomSlabId(Identifier id) {
        this.bottomSlabId = id;
        this.cachedTopSlabState = null;
        markDirty();
    }

    public BlockState getTopSlabState() {
        if (this.cachedTopSlabState == null) {
            Block block = Registries.BLOCK.get(this.topSlabId);
            if (block.getDefaultState().contains(Properties.SLAB_TYPE)) {
                this.cachedTopSlabState = Registries.BLOCK.get(this.topSlabId).getDefaultState().with(Properties.SLAB_TYPE, SlabType.TOP);
            } else {
                this.topSlabId = Identifier.of("sloves:purple_concrete_slab");
                this.cachedTopSlabState = Registries.BLOCK.get(this.topSlabId).getDefaultState().with(Properties.SLAB_TYPE, SlabType.TOP);
            }
            System.out.println("top id: " + topSlabId);
        }
        return this.cachedTopSlabState;
    }

    public BlockState getBottomSlabState() {
        if (this.cachedBottomSlabState == null) {
            Block block = Registries.BLOCK.get(this.bottomSlabId);
            if (block.getDefaultState().contains(Properties.SLAB_TYPE)) {
                this.cachedBottomSlabState = Registries.BLOCK.get(this.bottomSlabId).getDefaultState();
            } else {
                this.topSlabId = Identifier.of("sloves:black_concrete_slab");
                this.cachedTopSlabState = Registries.BLOCK.get(this.topSlabId).getDefaultState();
            }
        }
        return cachedBottomSlabState;
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return this.createComponentlessNbt(registryLookup);
    }
}
