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
public class DoubleSlabBlockEntityRenderer implements BlockEntityRenderer<DoubleSlabBlockEntity> {
    private int cachedRenderDistance = 1;
    private long lastUpdateTime = 0;
    private final MinecraftClient client = MinecraftClient.getInstance();

    public DoubleSlabBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(DoubleSlabBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockPos pos = entity.getPos();
        BlockRenderView world = client.world;
        Random random = Random.create();

        int positiveRenderLayerType = entity.getPositiveRenderLayerType();
        if (positiveRenderLayerType <= 2) {
            BlockState topSlabState = entity.getPositiveSlabState();
            VertexConsumer topVertexConsumer = switch (positiveRenderLayerType) {
                case 1 -> vertexConsumers.getBuffer(RenderLayer.getCutout());
                case 2 -> vertexConsumers.getBuffer(RenderLayer.getCutoutMipped());
                default -> vertexConsumers.getBuffer(RenderLayer.getSolid());
            };
            client.getBlockRenderManager().renderBlock(topSlabState, pos, world, matrices, topVertexConsumer, true, random);
        }

        int negativeRenderLayerType = entity.getNegativeRenderLayerType();
        if (negativeRenderLayerType <= 2) {
            BlockState bottomSlabState = entity.getNegativeSlabState();
            if (bottomSlabState.isOf(ModBlocks.DIRT_PATH_SLAB)) {
                bottomSlabState = ModBlocks.DIRT_SLAB.getDefaultState();
            }

            VertexConsumer bottomVertexConsumer = switch (negativeRenderLayerType) {
                case 1 -> vertexConsumers.getBuffer(RenderLayer.getCutout());
                case 2 -> vertexConsumers.getBuffer(RenderLayer.getCutoutMipped());
                default -> vertexConsumers.getBuffer(RenderLayer.getSolid());
            };
            client.getBlockRenderManager().renderBlock(bottomSlabState, pos, world, matrices, bottomVertexConsumer, true, random);
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
