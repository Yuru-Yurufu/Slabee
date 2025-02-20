package com.forestotzka.yurufu.slabee.handler;

import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleRenderEvents;
import net.minecraft.block.Block;

import java.util.Set;

@Environment(EnvType.CLIENT)
public class ParticleRenderHandler {
    public static void register() {
        ParticleRenderEvents.ALLOW_BLOCK_DUST_TINT.register((state, world, pos) -> !UNTINT_BLOCKS.contains(state.getBlock()));
    }

    private static final Set<Block> UNTINT_BLOCKS = Set.of(
            ModBlocks.GRASS_SLAB,
            ModBlocks.GRASS_VERTICAL_SLAB
    );
}
