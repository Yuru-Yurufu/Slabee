package com.forestotzka.yurufu.slabee;

import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;

import java.util.HashMap;
import java.util.Map;

public class StaticBlockLinker {
    private static final Map<AbstractBlock.Settings, Block> SETTINGS_TO_BLOCK = new HashMap<>();

    public static void link(AbstractBlock.Settings settings, Block block) {
        SETTINGS_TO_BLOCK.put(settings, block);
        System.out.println("Mapped block to settings: " + block);
    }

    public static Block getLinkedBlock(AbstractBlock.Settings settings) {
        return SETTINGS_TO_BLOCK.get(settings);
    }
}