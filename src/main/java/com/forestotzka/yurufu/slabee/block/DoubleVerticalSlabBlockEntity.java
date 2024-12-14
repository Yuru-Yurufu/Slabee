package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.registry.tag.ModBlockTags;
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

import java.util.Objects;
import java.util.function.ToIntFunction;

import static com.forestotzka.yurufu.slabee.block.DoubleSlabBlock.LIGHT_LEVEL;

public class DoubleVerticalSlabBlockEntity extends BlockEntity {
    private final Identifier defaultPositiveSlabId = Identifier.of("slabee:purple_concrete_vertical_slab");
    private final Identifier defaultNegativeSlabId = Identifier.of("slabee:black_concrete_vertical_slab");
    private final BlockState defaultPositiveSlabState = Registries.BLOCK.get(defaultPositiveSlabId).getDefaultState();
    private final BlockState defaultNegativeSlabState = Registries.BLOCK.get(defaultNegativeSlabId).getDefaultState();
    private Identifier positiveSlabId = defaultPositiveSlabId;
    private Identifier negativeSlabId = defaultNegativeSlabId;
    private BlockState positiveSlabState = defaultPositiveSlabState;
    private BlockState negativeSlabState = defaultNegativeSlabState;
    private boolean isX = true;
    public static ToIntFunction<BlockState> LUMINANCE = (state) -> (Integer)state.get(LIGHT_LEVEL);

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

        if (this.isX) {
            nbt.putString("axis", "X");
        } else {
            nbt.putString("axis", "Z");
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

        if (nbt.contains("axis")) {
            String inputAxis = nbt.getString("axis");
            if ("X".equals(inputAxis)) {
                this.isX = true;
            } else if ("Z".equals(inputAxis)) {
                this.isX = false;
            }
        }

        updateBothSlabState();

        if (this.world != null && !this.world.isClient()) {
            updateBlockProperties();
            this.markDirty();
            world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), 3);
        }
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

    public String getAxis() {
        return this.isX ? "x" : "z";
    }

    public boolean isX() {
        return this.isX;
    }

    public void setAxis(String axis) {
        this.isX = axis.equals("x");
        markDirty();
    }

    public BlockState getPositiveSlabState() {
        return this.positiveSlabState;
    }

    public BlockState getNegativeSlabState() {
        return negativeSlabState;
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

    public void updateBlockProperties() {
        int positiveLuminance;
        int negativeLuminance;
        if (positiveSlabState.isOf(ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB)) {
            positiveLuminance = 3;
        } else if (positiveSlabState.isOf(ModBlocks.CRYING_OBSIDIAN_VERTICAL_SLAB)) {
            positiveLuminance = 1;
        } else {
            positiveLuminance = 0;
        }
        if (negativeSlabState.isOf(ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB)) {
            negativeLuminance = 3;
        } else if (negativeSlabState.isOf(ModBlocks.CRYING_OBSIDIAN_VERTICAL_SLAB)) {
            negativeLuminance = 1;
        } else {
            negativeLuminance = 0;
        }
        int luminance = Math.max(positiveLuminance, negativeLuminance);

        boolean isOpaque = (positiveSlabState.isIn(ModBlockTags.TRANSPARENT_SLABS)) || (negativeSlabState.isIn(ModBlockTags.TRANSPARENT_SLABS));
        boolean isEmissiveLighting = (positiveSlabState.isOf(ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB)) || (negativeSlabState.isOf(ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB));

        Objects.requireNonNull(world).setBlockState(pos, world.getBlockState(pos).with(DoubleVerticalSlabBlock.LIGHT_LEVEL, luminance).with(DoubleVerticalSlabBlock.IS_OPAQUE, isOpaque).with(DoubleVerticalSlabBlock.IS_EMISSIVE_LIGHTING, isEmissiveLighting), 3);
    }

    private void updateBothSlabState() {
        updatePositiveSlabState();
        updateNegativeSlabState();
    }

    private void updatePositiveSlabState() {
        if (this.isX) {
            this.positiveSlabState = Registries.BLOCK.get(positiveSlabId).getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST);
        } else {
            this.positiveSlabState = Registries.BLOCK.get(positiveSlabId).getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH);
        }
    }

    private void updateNegativeSlabState() {
        if (this.isX) {
            this.negativeSlabState = Registries.BLOCK.get(negativeSlabId).getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST);
        } else {
            this.negativeSlabState = Registries.BLOCK.get(negativeSlabId).getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH);
        }
    }
}
