package com.forestotzka.yurufu.slabee.structure.fabric;

import com.forestotzka.yurufu.slabee.structure.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.function.Supplier;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public final class SlabeeFabric implements ModInitializer, PlatformRegistrations {
    @Override public void onInitialize() { StructureContent.register(this); }
    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(StructureContent.MOD_ID, path);
    }
    @Override public Supplier<Block> block(String path, Supplier<Block> factory) {
        Block block = Registry.register(BuiltInRegistries.BLOCK, id(path), factory.get());
        return () -> block;
    }
    @Override public void item(String path, Supplier<Item> factory) {
        Registry.register(BuiltInRegistries.ITEM, id(path), factory.get());
    }
    @Override public Supplier<BlockEntityType<StructureDoubleBlockEntity>> blockEntity(
            String path, BiFunction<BlockPos, BlockState, StructureDoubleBlockEntity> factory,
            Supplier<Block> block) {
        var type = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id(path),
                FabricBlockEntityTypeBuilder.create(factory::apply, block.get()).build());
        return () -> type;
    }
}
