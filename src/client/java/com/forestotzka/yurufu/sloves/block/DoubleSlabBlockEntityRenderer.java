package com.forestotzka.yurufu.sloves.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

        VertexConsumer topVertexConsumer = switch (entity.getTopRenderLayerType()) {
            case 1 -> vertexConsumers.getBuffer(RenderLayer.getCutout());
            case 2 -> vertexConsumers.getBuffer(RenderLayer.getCutoutMipped());
            default -> vertexConsumers.getBuffer(RenderLayer.getSolid());
        };
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(entity.getTopSlabState(), pos, world, matrices, topVertexConsumer, true, random);

        VertexConsumer bottomVertexConsumer= switch (entity.getBottomRenderLayerType()) {
            case 1 -> vertexConsumers.getBuffer(RenderLayer.getCutout());
            case 2 -> vertexConsumers.getBuffer(RenderLayer.getCutoutMipped());
            default -> vertexConsumers.getBuffer(RenderLayer.getSolid());
        };
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(entity.getBottomSlabState(), pos, world, matrices, bottomVertexConsumer, true, random);
    }
}
