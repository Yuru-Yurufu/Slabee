package com.forestotzka.yurufu.slabee;

import com.forestotzka.yurufu.slabee.block.ModBlockEntities;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.DoubleSlabBlockEntityRenderer;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlockEntityRenderer;
import com.forestotzka.yurufu.slabee.handler.ModClientHandlers;
import com.forestotzka.yurufu.slabee.listener.ModClientListeners;
import com.forestotzka.yurufu.slabee.model.ModModelLoaders;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

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
    private static final List<Block> COLORED_GLASS_SLABS = Arrays.asList(
            ModBlocks.TINTED_GLASS_SLAB,
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

            ModBlocks.TINTED_GLASS_VERTICAL_SLAB,
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
        ModClientListeners.register();
        ModClientHandlers.register();
        ModModelLoaders.register();

        LEAF_SLABS.forEach(block -> {
            ColorProviderRegistry.BLOCK.register(this::getLeafSlabColor, block);

            ColorProviderRegistry.ITEM.register(((stack, tintIndex) -> {
                if (stack.is(ModBlocks.MANGROVE_LEAF_SLAB.asItem()) || stack.is(ModBlocks.MANGROVE_LEAF_VERTICAL_SLAB.asItem())) {
                    return FoliageColor.getMangroveColor();
                } else if (
                        stack.is(ModBlocks.OAK_LEAF_SLAB.asItem()) ||stack.is(ModBlocks.SPRUCE_LEAF_SLAB.asItem()) ||stack.is(ModBlocks.BIRCH_LEAF_SLAB.asItem()) || stack.is(ModBlocks.JUNGLE_LEAF_SLAB.asItem()) || stack.is(ModBlocks.ACACIA_LEAF_SLAB.asItem()) || stack.is(ModBlocks.DARK_OAK_LEAF_SLAB.asItem()) ||
                        stack.is(ModBlocks.OAK_LEAF_VERTICAL_SLAB.asItem()) || stack.is(ModBlocks.SPRUCE_LEAF_VERTICAL_SLAB.asItem()) ||stack.is(ModBlocks.BIRCH_LEAF_VERTICAL_SLAB.asItem()) ||stack.is(ModBlocks.JUNGLE_LEAF_VERTICAL_SLAB.asItem()) || stack.is(ModBlocks.ACACIA_LEAF_VERTICAL_SLAB.asItem()) || stack.is(ModBlocks.DARK_OAK_LEAF_VERTICAL_SLAB.asItem())
                ) {
                    BlockState state = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
                    return getLeafSlabColor(state, null, null, tintIndex);
                } else {
                    return 0xFFFFFF;
                }
            }), block.asItem());

            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutoutMipped());
        });

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GLASS_SLAB, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GLASS_VERTICAL_SLAB, RenderType.cutout());
        COLORED_GLASS_SLABS.forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.translucent()));

        ColorProviderRegistry.BLOCK.register(this::getGrassSlabColor, ModBlocks.GRASS_SLAB, ModBlocks.GRASS_VERTICAL_SLAB);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            BlockState state = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
            return getGrassSlabColor(state, null, null, tintIndex);
        }, ModBlocks.GRASS_SLAB, ModBlocks.GRASS_VERTICAL_SLAB);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRASS_SLAB, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRASS_VERTICAL_SLAB, RenderType.cutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGROVE_ROOT_SLAB, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGROVE_ROOT_VERTICAL_SLAB, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DIRT_PATH_SLAB, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DIRT_PATH_VERTICAL_SLAB, RenderType.cutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DOUBLE_SLAB_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK, RenderType.translucent());

        // 【描画の試作】ハーフはチャンクの描画に焼き込むので、毎フレームの Block Entity Renderer は使わない
        // BlockEntityRendererFactories.register(ModBlockEntities.DOUBLE_SLAB_BLOCK_ENTITY, DoubleSlabBlockEntityRenderer::new);
        // BlockEntityRendererFactories.register(ModBlockEntities.DOUBLE_VERTICAL_SLAB_BLOCK_ENTITY, DoubleVerticalSlabBlockEntityRenderer::new);
    }

    private int getLeafSlabColor(BlockState state, @Nullable BlockAndTintGetter world, @Nullable BlockPos pos, int tintIndex) {
        if (state.is(ModBlocks.SPRUCE_LEAF_SLAB) || state.is(ModBlocks.SPRUCE_LEAF_VERTICAL_SLAB)) {
            return FoliageColor.getEvergreenColor();
        } else if (state.is(ModBlocks.BIRCH_LEAF_SLAB) || state.is(ModBlocks.BIRCH_LEAF_VERTICAL_SLAB)) {
            return FoliageColor.getBirchColor();
        } else if (world == null || pos == null) {
            return FoliageColor.getDefaultColor();
        } else if (
                state.is(ModBlocks.OAK_LEAF_SLAB) || state.is(ModBlocks.JUNGLE_LEAF_SLAB) || state.is(ModBlocks.ACACIA_LEAF_SLAB) || state.is(ModBlocks.DARK_OAK_LEAF_SLAB) || state.is(ModBlocks.MANGROVE_LEAF_SLAB) ||
                state.is(ModBlocks.OAK_LEAF_VERTICAL_SLAB) || state.is(ModBlocks.JUNGLE_LEAF_VERTICAL_SLAB) || state.is(ModBlocks.ACACIA_LEAF_VERTICAL_SLAB) || state.is(ModBlocks.DARK_OAK_LEAF_VERTICAL_SLAB) || state.is(ModBlocks.MANGROVE_LEAF_VERTICAL_SLAB)
        ) {
            return BiomeColors.getAverageFoliageColor(world, pos);
        } else {
            return 0xFFFFFF;
        }
    }

    private int getGrassSlabColor(BlockState state, @Nullable BlockAndTintGetter world, @Nullable BlockPos pos, int tintIndex) {
        if (world != null && pos != null) {
            return BiomeColors.getAverageGrassColor(world, pos);
        } else {
            return GrassColor.getDefaultColor();
        }
    }
}
