package com.forestotzka.yurufu.slabee.structure;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import java.util.function.Supplier;

/** Two real ModBlocks registrations, with creation timing delegated to the loader. */
public final class StructureContent {
    public static final String MOD_ID = "slabee";
    public static Supplier<Block> purple;
    public static Supplier<Block> black;
    public static Supplier<Block> doubleSlab;
    public static Supplier<BlockEntityType<StructureDoubleBlockEntity>> doubleType;

    private StructureContent() {}

    public static void register(PlatformRegistrations registry) {
        purple = registry.block("purple_concrete_slab", () -> concreteSlab(Blocks.PURPLE_CONCRETE));
        black = registry.block("black_concrete_slab", () -> concreteSlab(Blocks.BLACK_CONCRETE));
        doubleSlab = registry.block("double_slab_block", () ->
                new StructureDoubleBlock(BlockBehaviour.Properties.of()));
        registry.item("purple_concrete_slab", () -> new BlockItem(purple.get(), new Item.Properties()));
        registry.item("black_concrete_slab", () -> new BlockItem(black.get(), new Item.Properties()));
        doubleType = registry.blockEntity("double_slab_block_entity",
                StructureDoubleBlockEntity::new, doubleSlab);
    }

    private static Block concreteSlab(Block source) {
        return new SlabBlock(BlockBehaviour.Properties.of()
                .mapColor(source.defaultMapColor()).instrument(NoteBlockInstrument.BASEDRUM)
                .strength(1.8F).requiresCorrectToolForDrops().sound(SoundType.STONE));
    }
}
