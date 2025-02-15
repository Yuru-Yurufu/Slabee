package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.Slabee;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<DoubleSlabBlockEntity> DOUBLE_SLAB_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Slabee.MOD_ID, "double_slab_block"),
                    BlockEntityType.Builder.create(DoubleSlabBlockEntity::new, ModBlocks.DOUBLE_SLAB_BLOCK).build());
    public static final BlockEntityType<DoubleVerticalSlabBlockEntity> DOUBLE_VERTICAL_SLAB_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Slabee.MOD_ID, "double_vertical_slab_block"),
                    BlockEntityType.Builder.create(DoubleVerticalSlabBlockEntity::new, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK).build());

    public static void registerModBlockEntities() {
        Slabee.LOGGER.info("Registering Block Entities for " + Slabee.MOD_ID);
    }
}
