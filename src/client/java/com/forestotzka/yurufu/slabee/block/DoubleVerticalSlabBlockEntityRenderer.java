package com.forestotzka.yurufu.slabee.block;

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
public class DoubleVerticalSlabBlockEntityRenderer implements BlockEntityRenderer<DoubleVerticalSlabBlockEntity> {
    private int cachedRenderDistance = 1;
    private long lastUpdateTime = 0;
    private final MinecraftClient client = MinecraftClient.getInstance();

    public DoubleVerticalSlabBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(DoubleVerticalSlabBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockPos pos = entity.getPos();
        BlockRenderView world = client.world;
        Random random = Random.create();

        BlockState positiveSlabState = entity.getPositiveSlabState();
        int positiveRenderLayerType = entity.getPositiveRenderLayerType();
        if (positiveRenderLayerType <= 2) {
            VertexConsumer positiveVertexConsumer = switch (entity.getPositiveRenderLayerType()) {
                case 1 -> vertexConsumers.getBuffer(RenderLayer.getCutout());
                case 2 -> vertexConsumers.getBuffer(RenderLayer.getCutoutMipped());
                default -> vertexConsumers.getBuffer(RenderLayer.getSolid());
            };
            client.getBlockRenderManager().renderBlock(positiveSlabState, pos, world, matrices, positiveVertexConsumer, true, random);
        }

        BlockState negativeSlabState = entity.getNegativeSlabState();
        int negativeRenderLayerType = entity.getNegativeRenderLayerType();
        if (negativeRenderLayerType <= 2) {
            VertexConsumer negativeVertexConsumer = switch (entity.getNegativeRenderLayerType()) {
                case 1 -> vertexConsumers.getBuffer(RenderLayer.getCutout());
                case 2 -> vertexConsumers.getBuffer(RenderLayer.getCutoutMipped());
                default -> vertexConsumers.getBuffer(RenderLayer.getSolid());
            };
            client.getBlockRenderManager().renderBlock(negativeSlabState, pos, world, matrices, negativeVertexConsumer, true, random);
        }
    }

    @Override
    public int getRenderDistance() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime > 1000) {
            cachedRenderDistance = (client.options.getViewDistance().getValue() + 1) * 16;
            lastUpdateTime = currentTime;
        }
        return cachedRenderDistance;
    }
}
