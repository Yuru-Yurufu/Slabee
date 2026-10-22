package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.Slabee;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static final BlockEntityType<DoubleSlabBlockEntity> DOUBLE_SLAB_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Slabee.MOD_ID, "double_slab_block"),
                    BlockEntityType.Builder.of(DoubleSlabBlockEntity::new, ModBlocks.DOUBLE_SLAB_BLOCK).build());
    public static final BlockEntityType<DoubleVerticalSlabBlockEntity> DOUBLE_VERTICAL_SLAB_BLOCK_ENTITY =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Slabee.MOD_ID, "double_vertical_slab_block"),
                    BlockEntityType.Builder.of(DoubleVerticalSlabBlockEntity::new, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK).build());

    public static void registerModBlockEntities() {
        Slabee.LOGGER.info("Registering Block Entities for " + Slabee.MOD_ID);
    }
}
