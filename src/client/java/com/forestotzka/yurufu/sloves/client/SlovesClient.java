package com.forestotzka.yurufu.sloves.client;

import com.forestotzka.yurufu.sloves.block.ModBlockEntities;
import com.forestotzka.yurufu.sloves.block.ModBlocks;
import com.forestotzka.yurufu.sloves.block.DoubleSlabBlockEntityRenderer;
import com.forestotzka.yurufu.sloves.block.DoubleVerticalSlabBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class SlovesClient implements ClientModInitializer {
    private static final List<Block> CUTOUT_MIPPED_SLABS = Arrays.asList(
            ModBlocks.OAK_LEAF_SLAB,
            ModBlocks.SPRUCE_LEAF_SLAB,
            ModBlocks.BIRCH_LEAF_SLAB,
            ModBlocks.JUNGLE_LEAF_SLAB,
            ModBlocks.ACACIA_LEAF_SLAB,
            ModBlocks.DARK_OAK_LEAF_SLAB,
            ModBlocks.MANGROVE_LEAF_SLAB,
            ModBlocks.CHERRY_LEAF_SLAB,
            ModBlocks.AZALEA_LEAF_SLAB,
            ModBlocks.FLOWERING_AZALEA_LEAF_SLAB
    );

    @Override
    public void onInitializeClient() {
        CUTOUT_MIPPED_SLABS.forEach(block -> {
            System.out.println("Registering block: " + block.getTranslationKey());

            //ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : 0x7CBD6B), block);

            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutoutMipped());
        });
        /*Registries.BLOCK.getOrCreateEntryList(ModBlockTags.CUTOUT_MIPPED_SLABS).forEach(blockEntry -> {
            System.out.println("Registering block: " + blockEntry.value());
            ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
                if (tintIndex == 1) {
                    return world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : 0xFFFFFF;
                } else {
                    return 0xFFFFFF;
                }
            }), blockEntry.value());

            BlockRenderLayerMap.INSTANCE.putBlock(blockEntry.value(), RenderLayer.getCutoutMipped());
        });*/
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : 0xFFFFFF),
                ModBlocks.OAK_LEAF_SLAB, ModBlocks.SPRUCE_LEAF_SLAB, ModBlocks.BIRCH_LEAF_SLAB, ModBlocks.JUNGLE_LEAF_SLAB, ModBlocks.ACACIA_LEAF_SLAB, ModBlocks.DARK_OAK_LEAF_SLAB, ModBlocks.MANGROVE_LEAF_SLAB, ModBlocks.CHERRY_LEAF_SLAB, ModBlocks.AZALEA_LEAF_SLAB, ModBlocks.FLOWERING_AZALEA_LEAF_SLAB
        );
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            if (tintIndex == 1) {
                return world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : 0xFFFFFF;
            } else {
                return 0xFFFFFF;
            }
        }), ModBlocks.GRASS_SLAB, ModBlocks.GRASS_VERTICAL_SLAB);
        ColorProviderRegistry.ITEM.register(((state, tintIndex) -> {
            if (tintIndex == 1) {
                return 0x7CBD6B;
            } else {
                return 0xFFFFFF;
            }
        }), ModBlocks.GRASS_SLAB.asItem(), ModBlocks.GRASS_VERTICAL_SLAB.asItem());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRASS_SLAB, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRASS_VERTICAL_SLAB, RenderLayer.getCutoutMipped());
        //BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DOUBLE_SLAB_BLOCK, RenderLayer.getCutoutMipped());
        //BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK, RenderLayer.getCutoutMipped());

        BlockEntityRendererFactories.register(ModBlockEntities.DOUBLE_SLAB_BLOCK_ENTITY, DoubleSlabBlockEntityRenderer::new);
        //BlockEntityRendererFactories.register(ModBlockEntities.TRANSPARENT_DOUBLE_SLAB_BLOCK_ENTITY, TransparentDoubleSlabBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.DOUBLE_VERTICAL_SLAB_BLOCK_ENTITY, DoubleVerticalSlabBlockEntityRenderer::new);
    }
}
