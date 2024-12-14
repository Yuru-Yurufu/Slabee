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
    public DoubleVerticalSlabBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(DoubleVerticalSlabBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockPos pos = entity.getPos();
        BlockRenderView world = MinecraftClient.getInstance().world;
        Random random = Random.create();

        BlockState positiveSlabState = entity.getPositiveSlabState();
        VertexConsumer positiveVertexConsumer = switch (entity.getPositiveRenderLayerType()) {
            case 1 -> vertexConsumers.getBuffer(RenderLayer.getCutout());
            case 2 -> vertexConsumers.getBuffer(RenderLayer.getCutoutMipped());
            default -> vertexConsumers.getBuffer(RenderLayer.getSolid());
        };
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(positiveSlabState, pos, world, matrices, positiveVertexConsumer, true, random);

        BlockState negativeSlabState = entity.getNegativeSlabState();
        VertexConsumer negativeVertexConsumer= switch (entity.getNegativeRenderLayerType()) {
            case 1 -> vertexConsumers.getBuffer(RenderLayer.getCutout());
            case 2 -> vertexConsumers.getBuffer(RenderLayer.getCutoutMipped());
            default -> vertexConsumers.getBuffer(RenderLayer.getSolid());
        };
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(negativeSlabState, pos, world, matrices, negativeVertexConsumer, true, random);
    }
}
