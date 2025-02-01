package com.forestotzka.yurufu.slabee.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.enums.SlabType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class DoubleSlabBlockEntity extends AbstractDoubleSlabBlockEntity {
    private Direction positiveSlabFacing = Direction.SOUTH;
    private Direction negativeSlabFacing = Direction.SOUTH;

    public DoubleSlabBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DOUBLE_SLAB_BLOCK_ENTITY, pos, state, Identifier.of("slabee:purple_concrete_slab"), Identifier.of("slabee:black_concrete_slab"));
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        if (this.positiveSlabId != null) {
            NbtCompound positiveSlab = new NbtCompound();
            positiveSlab.putString("id", this.positiveSlabId.toString());
            positiveSlab.putString("facing", this.positiveSlabFacing.getName());
            nbt.put("positive_slab", positiveSlab);
        }

        if (this.negativeSlabId != null) {
            NbtCompound negativeSlab = new NbtCompound();
            negativeSlab.putString("id", this.negativeSlabId.toString());
            negativeSlab.putString("facing", this.negativeSlabFacing.getName());
            nbt.put("negative_slab", negativeSlab);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        if (nbt.contains("positive_slab")) {
            NbtCompound positiveSlabData = nbt.getCompound("positive_slab");
            Identifier i = Identifier.of(positiveSlabData.getString("id"));
            this.positiveSlabId = isTrueSlabId(i) ? i : defaultPositiveSlabId;
            Direction d = Direction.byName(positiveSlabData.getString("facing"));
            this.positiveSlabFacing = (d != null) ? d : Direction.SOUTH;
        } else {
            this.positiveSlabId = defaultPositiveSlabId;
            this.positiveSlabFacing = Direction.SOUTH;
        }

        if (nbt.contains("negative_slab")) {
            NbtCompound negativeSlabData = nbt.getCompound("negative_slab");
            Identifier i = Identifier.of(negativeSlabData.getString("id"));
            this.negativeSlabId = isTrueSlabId(i) ? i : defaultNegativeSlabId;
            Direction d = Direction.byName(negativeSlabData.getString("facing"));
            this.negativeSlabFacing = (d != null) ? d : Direction.SOUTH;
        } else {
            this.negativeSlabId = defaultNegativeSlabId;
            this.negativeSlabFacing = Direction.SOUTH;
        }

        updateSlabState();
        updateBlockProperties();
    }

    protected void updatePositiveSlabState() {
        this.positiveSlabState = Registries.BLOCK.get(positiveSlabId).getDefaultState().with(Properties.SLAB_TYPE, SlabType.TOP);
    }

    protected void updateNegativeSlabState() {
        this.negativeSlabState = Registries.BLOCK.get(negativeSlabId).getDefaultState();
    }
}
