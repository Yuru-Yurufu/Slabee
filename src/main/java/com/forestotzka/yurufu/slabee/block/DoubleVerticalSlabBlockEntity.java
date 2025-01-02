package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
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
    private final BlockState defaultBlockState = this.getCachedState();
    private BlockState blockState = defaultBlockState;
    private boolean isX = true;
    public static ToIntFunction<BlockState> LUMINANCE = (state) -> (Integer)state.get(LIGHT_LEVEL);
    public static boolean POSITIVE_OPAQUE;
    public static boolean NEGATIVE_OPAQUE;

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
            Identifier i = Identifier.of(positiveSlabData.getString("id"));
            this.positiveSlabId = (DoubleSlabUtils.isTrueSlabId(i)) ? i : defaultPositiveSlabId;
        } else {
            this.positiveSlabId = defaultPositiveSlabId;
        }

        if (nbt.contains("negative_slab")) {
            NbtCompound negativeSlabData = nbt.getCompound("negative_slab");
            Identifier i = Identifier.of(negativeSlabData.getString("id"));
            this.negativeSlabId = (DoubleSlabUtils.isTrueSlabId(i)) ? i : defaultNegativeSlabId;
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

        updateSlabState();

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
        Block block = Registries.BLOCK.get(this.positiveSlabId);
        if (SlabeeUtils.isCutoutVerticalSlabs(block)) {
            return 1;
        } else if (SlabeeUtils.isCutoutMippedVerticalSlabs(block)) {
            return 2;
        } else if (SlabeeUtils.isTranslucentVerticalSlabs(block)) {
            return 3;
        } else {
            return 0;
        }
    }

    public Integer getNegativeRenderLayerType() {
        Block block = Registries.BLOCK.get(this.negativeSlabId);
        if (SlabeeUtils.isCutoutVerticalSlabs(block)) {
            return 1;
        } else if (SlabeeUtils.isCutoutMippedVerticalSlabs(block)) {
            return 2;
        } else if (SlabeeUtils.isTranslucentVerticalSlabs(block)) {
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
        int luminance = Math.max(DoubleSlabUtils.getLuminance(positiveSlabState), DoubleSlabUtils.getLuminance(negativeSlabState));

        Block positiveSlab = positiveSlabState.getBlock();
        Block negativeSlab = negativeSlabState.getBlock();
        POSITIVE_OPAQUE = SlabeeUtils.isOpaqueVerticalSlabs(positiveSlab);
        NEGATIVE_OPAQUE = SlabeeUtils.isOpaqueVerticalSlabs(negativeSlab);
        boolean isEmissiveLighting = (SlabeeUtils.isEmissiveLightingVerticalSlabs(positiveSlab) || SlabeeUtils.isEmissiveLightingVerticalSlabs(negativeSlab));

        if (world != null) {
            this.blockState = world.getBlockState(pos).with(DoubleSlabBlock.LIGHT_LEVEL, luminance).with(DoubleVerticalSlabBlock.POSITIVE_OPAQUE, POSITIVE_OPAQUE).with(DoubleVerticalSlabBlock.NEGATIVE_OPAQUE, NEGATIVE_OPAQUE).with(DoubleSlabBlock.IS_EMISSIVE_LIGHTING, isEmissiveLighting);
            world.setBlockState(pos, this.blockState, 3);
        } else {
            this.blockState = this.defaultBlockState;
        }
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    private void updateSlabState() {
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
