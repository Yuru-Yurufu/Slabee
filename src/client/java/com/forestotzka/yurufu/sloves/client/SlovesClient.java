package com.forestotzka.yurufu.sloves.client;

import com.forestotzka.yurufu.sloves.ModModelLoader;
import com.forestotzka.yurufu.sloves.Sloves;
import com.forestotzka.yurufu.sloves.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;

public class SlovesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            if (tintIndex == 1) {
                return world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : 0xFFFFFF;
            } else {
                return 0xFFFFFF;
            }
        }), ModBlocks.GRASS_SLAB, ModBlocks.GRASS_VERTICAL_SLAB, ModBlocks.DOUBLE_SLAB_BLOCK, ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK);ColorProviderRegistry.ITEM.register(((state, tintIndex) -> {
            if (tintIndex == 1) {
                return 0x7CBD6B;
            } else {
                return 0xFFFFFF;
            }
        }), ModBlocks.GRASS_SLAB.asItem(), ModBlocks.GRASS_VERTICAL_SLAB.asItem());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRASS_SLAB, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRASS_VERTICAL_SLAB, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DOUBLE_SLAB_BLOCK, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK, RenderLayer.getCutoutMipped());

        ModelLoadingPlugin.register(
            new ModModelLoader()
        );
    }
}
