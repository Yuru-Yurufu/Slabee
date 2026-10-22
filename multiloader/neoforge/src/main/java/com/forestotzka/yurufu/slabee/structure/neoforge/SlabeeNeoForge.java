package com.forestotzka.yurufu.slabee.structure.neoforge;

import com.forestotzka.yurufu.slabee.structure.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

@Mod(StructureContent.MOD_ID)
public final class SlabeeNeoForge implements PlatformRegistrations {
    private final DeferredRegister<Block> blocks = DeferredRegister.create(Registries.BLOCK, StructureContent.MOD_ID);
    private final DeferredRegister<Item> items = DeferredRegister.create(Registries.ITEM, StructureContent.MOD_ID);
    private final DeferredRegister<BlockEntityType<?>> types = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, StructureContent.MOD_ID);

    public SlabeeNeoForge(IEventBus bus) {
        StructureContent.register(this);
        blocks.register(bus);
        items.register(bus);
        types.register(bus);
    }
    @Override public Supplier<Block> block(String path, Supplier<Block> factory) {
        return blocks.register(path, factory);
    }
    @Override public void item(String path, Supplier<Item> factory) { items.register(path, factory); }
    @Override public Supplier<BlockEntityType<StructureDoubleBlockEntity>> blockEntity(
            String path, BiFunction<BlockPos, BlockState, StructureDoubleBlockEntity> factory,
            Supplier<Block> block) {
        return types.register(path, () ->
                BlockEntityType.Builder.of(factory::apply, block.get()).build(null));
    }
}
