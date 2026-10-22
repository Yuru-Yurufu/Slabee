package com.forestotzka.yurufu.slabee.structure;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.function.Supplier;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface PlatformRegistrations {
    Supplier<Block> block(String path, Supplier<Block> factory);
    void item(String path, Supplier<Item> factory);
    Supplier<BlockEntityType<StructureDoubleBlockEntity>> blockEntity(
            String path, BiFunction<BlockPos, BlockState, StructureDoubleBlockEntity> factory,
            Supplier<Block> block);
}
