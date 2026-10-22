package com.forestotzka.yurufu.slabee.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractDoubleSlabBlockEntityRenderer<T extends AbstractDoubleSlabBlockEntity> implements BlockEntityRenderer<T> {
    protected int cachedRenderDistance = 1;
    protected long lastUpdateTime = 0;
    protected final Minecraft client = Minecraft.getInstance();

    public AbstractDoubleSlabBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(T entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        BlockPos pos = entity.getBlockPos();
        BlockAndTintGetter world = client.level;
        RandomSource random = RandomSource.create();

        int positiveRenderLayerType = entity.getPositiveRenderLayerType();
        if (positiveRenderLayerType <= 1) {
            renderPositive(positiveRenderLayerType, entity.getPositiveSlabState(), pos, world, matrices, vertexConsumers, random);
        }

        int negativeRenderLayerType = entity.getNegativeRenderLayerType();
        if (negativeRenderLayerType <= 1) {
            renderNegative(negativeRenderLayerType, entity.getNegativeSlabState(), pos, world, matrices, vertexConsumers, random);
        }
    }

    protected void renderPositive(int renderLayerType, BlockState slabState, BlockPos pos, BlockAndTintGetter world, PoseStack matrices, MultiBufferSource vertexConsumers, RandomSource random) {
        VertexConsumer vertexConsumer = getVertexConsumer(renderLayerType, vertexConsumers);
        client.getBlockRenderer().renderBatched(slabState, pos, world, matrices, vertexConsumer, true, random);
    }

    protected void renderNegative(int renderLayerType, BlockState slabState, BlockPos pos, BlockAndTintGetter world, PoseStack matrices, MultiBufferSource vertexConsumers, RandomSource random) {
        VertexConsumer vertexConsumer = getVertexConsumer(renderLayerType, vertexConsumers);
        client.getBlockRenderer().renderBatched(slabState, pos, world, matrices, vertexConsumer, true, random);
    }

    protected VertexConsumer getVertexConsumer(int renderLayerType, MultiBufferSource vertexConsumers) {
        if (renderLayerType == 1) {
            return vertexConsumers.getBuffer(RenderType.cutoutMipped());
        } else {
            return vertexConsumers.getBuffer(RenderType.solid());
        }
    }

    @Override
    public int getViewDistance() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime > 1000) {
            cachedRenderDistance = (client.options.renderDistance().get() + 1) * 16;
            lastUpdateTime = currentTime;
        }
        return cachedRenderDistance;
    }
}
