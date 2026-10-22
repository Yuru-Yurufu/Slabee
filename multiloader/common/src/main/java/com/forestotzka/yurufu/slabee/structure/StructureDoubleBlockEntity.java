package com.forestotzka.yurufu.slabee.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class StructureDoubleBlockEntity extends BlockEntity {
    private HorizontalSlabData data = HorizontalSlabData.defaults();

    public StructureDoubleBlockEntity(BlockPos pos, BlockState state) {
        super(StructureContent.doubleType.get(), pos, state);
    }
    public HorizontalSlabData data() { return data; }
    public void setData(HorizontalSlabData data) { this.data = data; setChanged(); }

    @Override protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        data.write(tag);
    }
    @Override protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        data = HorizontalSlabData.read(tag, id ->
                BuiltInRegistries.BLOCK.containsKey(id) && BuiltInRegistries.BLOCK.get(id) instanceof SlabBlock);
    }
}
