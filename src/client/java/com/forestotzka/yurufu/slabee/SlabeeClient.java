package com.forestotzka.yurufu.slabee;

import com.forestotzka.yurufu.slabee.block.ModBlockEntities;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.DoubleSlabBlockEntityRenderer;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlockEntityRenderer;
import com.forestotzka.yurufu.slabee.handler.ModClientHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.biome.FoliageColors;
import net.minecraft.world.biome.GrassColors;
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
        ModClientHandlers.register();

        LEAF_SLABS.forEach(block -> {
            ColorProviderRegistry.BLOCK.register(this::getLeafSlabColor, block);

            ColorProviderRegistry.ITEM.register(((stack, tintIndex) -> {
                if (stack.isOf(ModBlocks.MANGROVE_LEAF_SLAB.asItem()) || stack.isOf(ModBlocks.MANGROVE_LEAF_VERTICAL_SLAB.asItem())) {
                    return FoliageColors.getMangroveColor();
                } else if (
                        stack.isOf(ModBlocks.OAK_LEAF_SLAB.asItem()) ||stack.isOf(ModBlocks.SPRUCE_LEAF_SLAB.asItem()) ||stack.isOf(ModBlocks.BIRCH_LEAF_SLAB.asItem()) || stack.isOf(ModBlocks.JUNGLE_LEAF_SLAB.asItem()) || stack.isOf(ModBlocks.ACACIA_LEAF_SLAB.asItem()) || stack.isOf(ModBlocks.DARK_OAK_LEAF_SLAB.asItem()) ||
                        stack.isOf(ModBlocks.OAK_LEAF_VERTICAL_SLAB.asItem()) || stack.isOf(ModBlocks.SPRUCE_LEAF_VERTICAL_SLAB.asItem()) ||stack.isOf(ModBlocks.BIRCH_LEAF_VERTICAL_SLAB.asItem()) ||stack.isOf(ModBlocks.JUNGLE_LEAF_VERTICAL_SLAB.asItem()) || stack.isOf(ModBlocks.ACACIA_LEAF_VERTICAL_SLAB.asItem()) || stack.isOf(ModBlocks.DARK_OAK_LEAF_VERTICAL_SLAB.asItem())
                ) {
                    BlockState state = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
                    return getLeafSlabColor(state, null, null, tintIndex);
                } else {
                    return 0xFFFFFF;
                }
            }), block.asItem());

            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutoutMipped());
        });

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GLASS_SLAB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GLASS_VERTICAL_SLAB, RenderLayer.getCutout());
        COLORED_GLASS_SLABS.forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent()));

        ColorProviderRegistry.BLOCK.register(this::getGrassSlabColor, ModBlocks.GRASS_SLAB, ModBlocks.GRASS_VERTICAL_SLAB);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            BlockState state = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return getGrassSlabColor(state, null, null, tintIndex);
        }, ModBlocks.GRASS_SLAB, ModBlocks.GRASS_VERTICAL_SLAB);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRASS_SLAB, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GRASS_VERTICAL_SLAB, RenderLayer.getCutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGROVE_ROOT_SLAB, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGROVE_ROOT_VERTICAL_SLAB, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DIRT_PATH_SLAB, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DIRT_PATH_VERTICAL_SLAB, RenderLayer.getCutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DOUBLE_SLAB_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK, RenderLayer.getTranslucent());

        BlockEntityRendererFactories.register(ModBlockEntities.DOUBLE_SLAB_BLOCK_ENTITY, DoubleSlabBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.DOUBLE_VERTICAL_SLAB_BLOCK_ENTITY, DoubleVerticalSlabBlockEntityRenderer::new);
    }

    private int getLeafSlabColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
        if (state.isOf(ModBlocks.SPRUCE_LEAF_SLAB) || state.isOf(ModBlocks.SPRUCE_LEAF_VERTICAL_SLAB)) {
            return FoliageColors.getSpruceColor();
        } else if (state.isOf(ModBlocks.BIRCH_LEAF_SLAB) || state.isOf(ModBlocks.BIRCH_LEAF_VERTICAL_SLAB)) {
            return FoliageColors.getBirchColor();
        } else if (world == null || pos == null) {
            return FoliageColors.getDefaultColor();
        } else if (
                state.isOf(ModBlocks.OAK_LEAF_SLAB) || state.isOf(ModBlocks.JUNGLE_LEAF_SLAB) || state.isOf(ModBlocks.ACACIA_LEAF_SLAB) || state.isOf(ModBlocks.DARK_OAK_LEAF_SLAB) || state.isOf(ModBlocks.MANGROVE_LEAF_SLAB) ||
                state.isOf(ModBlocks.OAK_LEAF_VERTICAL_SLAB) || state.isOf(ModBlocks.JUNGLE_LEAF_VERTICAL_SLAB) || state.isOf(ModBlocks.ACACIA_LEAF_VERTICAL_SLAB) || state.isOf(ModBlocks.DARK_OAK_LEAF_VERTICAL_SLAB) || state.isOf(ModBlocks.MANGROVE_LEAF_VERTICAL_SLAB)
        ) {
            return BiomeColors.getFoliageColor(world, pos);
        } else {
            return FoliageColors.getDefaultColor();
        }
    }

    private int getGrassSlabColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
        if (world != null && pos != null) {
            return BiomeColors.getGrassColor(world, pos);
        } else {
            return GrassColors.getDefaultColor();
        }
    }
}
