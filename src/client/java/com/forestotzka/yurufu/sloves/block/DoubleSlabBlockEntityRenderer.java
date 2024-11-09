package com.forestotzka.yurufu.sloves.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

@Environment(EnvType.CLIENT)
public class DoubleSlabBlockEntityRenderer implements BlockEntityRenderer<DoubleSlabBlockEntity> {
    public DoubleSlabBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(DoubleSlabBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockPos pos = entity.getPos();
        BlockRenderView world = MinecraftClient.getInstance().world;
        Random random = Random.create();

        BlockState topSlabState = entity.getTopSlabState();
        VertexConsumer topVertexConsumer = switch (entity.getTopRenderLayerType()) {
            case 1 -> vertexConsumers.getBuffer(RenderLayer.getCutout());
            case 2 -> vertexConsumers.getBuffer(RenderLayer.getCutoutMipped());
            default -> vertexConsumers.getBuffer(RenderLayer.getSolid());
        };
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(topSlabState, pos, world, matrices, topVertexConsumer, true, random);

        BlockState bottomSlabState = entity.getBottomSlabState();
        if (bottomSlabState.isOf(ModBlocks.DIRT_PATH_SLAB)) {
            bottomSlabState = ModBlocks.DIRT_SLAB.getDefaultState();
        }
        VertexConsumer bottomVertexConsumer= switch (entity.getBottomRenderLayerType()) {
            case 1 -> vertexConsumers.getBuffer(RenderLayer.getCutout());
            case 2 -> vertexConsumers.getBuffer(RenderLayer.getCutoutMipped());
            default -> vertexConsumers.getBuffer(RenderLayer.getSolid());
        };
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(bottomSlabState, pos, world, matrices, bottomVertexConsumer, true, random);
    }
}
