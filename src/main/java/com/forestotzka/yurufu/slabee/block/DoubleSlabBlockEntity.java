package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.Slabee;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DoubleSlabBlockEntity extends AbstractDoubleSlabBlockEntity {
    private Direction positiveSlabFacing = Direction.SOUTH;
    private Direction negativeSlabFacing = Direction.SOUTH;

    public DoubleSlabBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DOUBLE_SLAB_BLOCK_ENTITY, pos, state, ResourceLocation.parse("slabee:purple_concrete_slab"), ResourceLocation.parse("slabee:black_concrete_slab"));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.saveAdditional(nbt, registryLookup);

        if (this.positiveSlabId != null) {
            CompoundTag positiveSlab = new CompoundTag();
            positiveSlab.putString("id", this.positiveSlabId.toString());
            positiveSlab.putString("facing", this.positiveSlabFacing.getName());
            nbt.put("positive_slab", positiveSlab);
        }

        if (this.negativeSlabId != null) {
            CompoundTag negativeSlab = new CompoundTag();
            negativeSlab.putString("id", this.negativeSlabId.toString());
            negativeSlab.putString("facing", this.negativeSlabFacing.getName());
            nbt.put("negative_slab", negativeSlab);
        }
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.loadAdditional(nbt, registryLookup);

        if (nbt.contains("positive_slab")) {
            CompoundTag positiveSlabData = nbt.getCompound("positive_slab");
            ResourceLocation i = ResourceLocation.parse(positiveSlabData.getString("id"));
            this.positiveSlabId = isTrueSlabId(i) ? i : defaultPositiveSlabId;
            Direction d = Direction.byName(positiveSlabData.getString("facing"));
            this.positiveSlabFacing = (d != null) ? d : Direction.SOUTH;
        } else {
            this.positiveSlabId = defaultPositiveSlabId;
            this.positiveSlabFacing = Direction.SOUTH;
        }

        if (nbt.contains("negative_slab")) {
            CompoundTag negativeSlabData = nbt.getCompound("negative_slab");
            ResourceLocation i = ResourceLocation.parse(negativeSlabData.getString("id"));
            this.negativeSlabId = isTrueSlabId(i) ? (i.equals(ResourceLocation.fromNamespaceAndPath(Slabee.MOD_ID, "dirt_path_slab")) ? ResourceLocation.fromNamespaceAndPath(Slabee.MOD_ID, "dirt_slab") : i) : defaultNegativeSlabId;
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
        this.positiveSlabState = BuiltInRegistries.BLOCK.get(positiveSlabId).defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, SlabType.TOP);
    }

    protected void updateNegativeSlabState() {
        this.negativeSlabState = BuiltInRegistries.BLOCK.get(negativeSlabId).defaultBlockState();
    }

    @Override
    public VoxelShape getPositiveOutlineShape() {
        if (this.positiveSlabState.is(ModBlocks.DIRT_PATH_SLAB)) {
            return DirtPathSlabBlock.TOP_SHAPE;
        } else {
            return Block.box(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);
        }
    }

    @Override
    public VoxelShape getNegativeOutlineShape() {
        return Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    }

    @Override
    protected void convertToDirt(boolean isPositive) {
        if (isPositive) {
            setPositiveSlabId(ResourceLocation.fromNamespaceAndPath(Slabee.MOD_ID, "dirt_slab"));
        }
    }
}
