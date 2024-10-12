package com.forestotzka.yurufu.sloves.blocks;

import com.forestotzka.yurufu.sloves.Sloves;
import com.forestotzka.yurufu.sloves.block.DoubleSlabBlockEntity;
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

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getCutoutMipped());

        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(entity.getTopSlabState(), pos, world, matrices, vertexConsumer, false, Random.create());
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(entity.getBottomSlabState(), pos, world, matrices, vertexConsumer, false, Random.create());
    }
}
