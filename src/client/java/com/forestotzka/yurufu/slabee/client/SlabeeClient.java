package com.forestotzka.yurufu.slabee.client;

import com.forestotzka.yurufu.slabee.block.ModBlockEntities;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.DoubleSlabBlockEntityRenderer;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlockEntityRenderer;
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
import java.util.Set;

@Environment(EnvType.CLIENT)
public class SlabeeClient implements ClientModInitializer {
    private static final List<Block> LEAF_SLABS = Arrays.asList(
            ModBlocks.OAK_LEAF_SLAB,
            ModBlocks.SPRUCE_LEAF_SLAB,
            ModBlocks.BIRCH_LEAF_SLAB,
            ModBlocks.JUNGLE_LEAF_SLAB,
            ModBlocks.ACACIA_LEAF_SLAB,
            ModBlocks.DARK_OAK_LEAF_SLAB,
            ModBlocks.MANGROVE_LEAF_SLAB,
            ModBlocks.CHERRY_LEAF_SLAB,
            ModBlocks.AZALEA_LEAF_SLAB,
            ModBlocks.FLOWERING_AZALEA_LEAF_SLAB,

            ModBlocks.OAK_LEAF_VERTICAL_SLAB,
            ModBlocks.SPRUCE_LEAF_VERTICAL_SLAB,
            ModBlocks.BIRCH_LEAF_VERTICAL_SLAB,
            ModBlocks.JUNGLE_LEAF_VERTICAL_SLAB,
            ModBlocks.ACACIA_LEAF_VERTICAL_SLAB,
            ModBlocks.DARK_OAK_LEAF_VERTICAL_SLAB,
            ModBlocks.MANGROVE_LEAF_VERTICAL_SLAB,
            ModBlocks.CHERRY_LEAF_VERTICAL_SLAB,
            ModBlocks.AZALEA_LEAF_VERTICAL_SLAB,
            ModBlocks.FLOWERING_AZALEA_LEAF_VERTICAL_SLAB
    );
    private static final List<Block> STAINED_GLASS_SLABS = Arrays.asList(
            ModBlocks.WHITE_STAINED_GLASS_SLAB,
            ModBlocks.LIGHT_GRAY_STAINED_GLASS_SLAB,
            ModBlocks.GRAY_STAINED_GLASS_SLAB,
            ModBlocks.BLACK_STAINED_GLASS_SLAB,
            ModBlocks.BROWN_STAINED_GLASS_SLAB,
            ModBlocks.RED_STAINED_GLASS_SLAB,
            ModBlocks.ORANGE_STAINED_GLASS_SLAB,
            ModBlocks.YELLOW_STAINED_GLASS_SLAB,
            ModBlocks.LIME_STAINED_GLASS_SLAB,
            ModBlocks.GREEN_STAINED_GLASS_SLAB,
            ModBlocks.CYAN_STAINED_GLASS_SLAB,
            ModBlocks.LIGHT_BLUE_STAINED_GLASS_SLAB,
            ModBlocks.BLUE_STAINED_GLASS_SLAB,
            ModBlocks.PURPLE_STAINED_GLASS_SLAB,
            ModBlocks.MAGENTA_STAINED_GLASS_SLAB,
            ModBlocks.PINK_STAINED_GLASS_SLAB,

            ModBlocks.WHITE_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.GRAY_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.BLACK_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.BROWN_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.RED_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.ORANGE_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.YELLOW_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.LIME_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.GREEN_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.CYAN_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.BLUE_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.PURPLE_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.MAGENTA_STAINED_GLASS_VERTICAL_SLAB,
            ModBlocks.PINK_STAINED_GLASS_VERTICAL_SLAB
    );

    @Override
    public void onInitializeClient() {
        LEAF_SLABS.forEach(block -> {
            ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
                if (tintIndex >= 0 && tintIndex <= 1 && world != null && pos != null) {
                    if (Set.of(
                            ModBlocks.OAK_LEAF_SLAB, ModBlocks.JUNGLE_LEAF_SLAB, ModBlocks.ACACIA_LEAF_SLAB, ModBlocks.DARK_OAK_LEAF_SLAB, ModBlocks.MANGROVE_LEAF_SLAB,
                            ModBlocks.OAK_LEAF_VERTICAL_SLAB, ModBlocks.JUNGLE_LEAF_VERTICAL_SLAB, ModBlocks.ACACIA_LEAF_VERTICAL_SLAB, ModBlocks.DARK_OAK_LEAF_VERTICAL_SLAB, ModBlocks.MANGROVE_LEAF_VERTICAL_SLAB
                    ).contains(state.getBlock())) {
                        return BiomeColors.getFoliageColor(world, pos);
                    } else if (state.isOf(ModBlocks.SPRUCE_LEAF_SLAB) || state.isOf(ModBlocks.SPRUCE_LEAF_VERTICAL_SLAB)) {
                        return 0x619961;
                    } else if (state.isOf(ModBlocks.BIRCH_LEAF_SLAB) || state.isOf(ModBlocks.BIRCH_LEAF_VERTICAL_SLAB)) {
                        return 0x80a755;
                    } else {
                        return 0xFFFFFF;
                    }
                } else {
                    return 0x000000;
                }
            }), block);

            ColorProviderRegistry.ITEM.register(((stack, tintIndex) -> {
                if (tintIndex >= 0 && tintIndex <= 1) {
                    if (Set.of(
                            ModBlocks.OAK_LEAF_SLAB.asItem(), ModBlocks.JUNGLE_LEAF_SLAB.asItem(), ModBlocks.ACACIA_LEAF_SLAB.asItem(), ModBlocks.DARK_OAK_LEAF_SLAB.asItem(),
                            ModBlocks.OAK_LEAF_VERTICAL_SLAB.asItem(), ModBlocks.JUNGLE_LEAF_VERTICAL_SLAB.asItem(), ModBlocks.ACACIA_LEAF_VERTICAL_SLAB.asItem(), ModBlocks.DARK_OAK_LEAF_VERTICAL_SLAB.asItem()
                    ).contains(stack.getItem())) {
                        return 0x48B518;
                    } else if (stack.isOf(ModBlocks.MANGROVE_LEAF_SLAB.asItem()) || stack.isOf(ModBlocks.MANGROVE_LEAF_VERTICAL_SLAB.asItem())) {
                        return 0x92C648;
                    } else if (stack.isOf(ModBlocks.SPRUCE_LEAF_SLAB.asItem()) || stack.isOf(ModBlocks.SPRUCE_LEAF_VERTICAL_SLAB.asItem())) {
                        return 0x619961;
                    } else if (stack.isOf(ModBlocks.BIRCH_LEAF_SLAB.asItem()) || stack.isOf(ModBlocks.BIRCH_LEAF_VERTICAL_SLAB.asItem())) {
                        return 0x80a755;
                    } else {
                        return 0xFFFFFF;
                    }
                } else {
                    return 0xFFFFFF;
                }
            }), block.asItem());

            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutoutMipped());
        });

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GLASS_SLAB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GLASS_VERTICAL_SLAB, RenderLayer.getCutout());
        STAINED_GLASS_SLABS.forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent()));

        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> {
            if (tintIndex == 1 && world != null && pos != null) {
                return BiomeColors.getGrassColor(world, pos);
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
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGROVE_ROOT_SLAB, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGROVE_ROOT_VERTICAL_SLAB, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DIRT_PATH_SLAB, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DIRT_PATH_VERTICAL_SLAB, RenderLayer.getCutoutMipped());

        BlockEntityRendererFactories.register(ModBlockEntities.DOUBLE_SLAB_BLOCK_ENTITY, DoubleSlabBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.DOUBLE_VERTICAL_SLAB_BLOCK_ENTITY, DoubleVerticalSlabBlockEntityRenderer::new);
    }
}
