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
public class DoubleVerticalSlabBlockEntityRenderer implements BlockEntityRenderer<DoubleVerticalSlabBlockEntity> {
    public DoubleVerticalSlabBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(DoubleVerticalSlabBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockPos pos = entity.getPos();
        BlockRenderView world = MinecraftClient.getInstance().world;

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getCutoutMipped());

        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(entity.getPositiveSlabState(), pos, world, matrices, vertexConsumer, false, Random.create());
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(entity.getNegativeSlabState(), pos, world, matrices, vertexConsumer, false, Random.create());
    }
}
