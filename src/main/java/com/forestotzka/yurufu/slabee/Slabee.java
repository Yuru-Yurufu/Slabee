package com.forestotzka.yurufu.slabee;

import com.forestotzka.yurufu.slabee.block.ModBlockEntities;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slabee implements ModInitializer {
    public static final String MOD_ID = "slabee";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModCreativeTabs.registerItemGroups();
        ModBlockEntities.registerModBlockEntities();
    }
}
