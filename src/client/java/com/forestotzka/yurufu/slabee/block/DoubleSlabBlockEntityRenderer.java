package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.render.SlabeeRenderLayer;
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
    private long lastUpdateTime2 = 0;
    private final MinecraftClient client = MinecraftClient.getInstance();

    public DoubleSlabBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(DoubleSlabBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockPos pos = entity.getPos();
        BlockRenderView world = client.world;
        Random random = Random.create();

        BlockState topSlabState = entity.getTopSlabState();
        VertexConsumer topVertexConsumer = switch (entity.getTopRenderLayerType()) {
            case 1 -> vertexConsumers.getBuffer(RenderLayer.getCutout());
            case 2 -> vertexConsumers.getBuffer(RenderLayer.getCutoutMipped());
            case 3 -> vertexConsumers.getBuffer(SlabeeRenderLayer.getCustomTranslucent4());
            default -> vertexConsumers.getBuffer(RenderLayer.getSolid());
        };
        client.getBlockRenderManager().renderBlock(topSlabState, pos, world, matrices, topVertexConsumer, false, random);

        BlockState bottomSlabState = entity.getBottomSlabState();
        if (bottomSlabState.isOf(ModBlocks.DIRT_PATH_SLAB)) {
            bottomSlabState = ModBlocks.DIRT_SLAB.getDefaultState();
        }
        VertexConsumer bottomVertexConsumer = switch (entity.getBottomRenderLayerType()) {
            case 1 -> vertexConsumers.getBuffer(RenderLayer.getCutout());
            case 2 -> vertexConsumers.getBuffer(RenderLayer.getCutoutMipped());
            case 3 -> vertexConsumers.getBuffer(SlabeeRenderLayer.getCustomTranslucent4());
            default -> vertexConsumers.getBuffer(RenderLayer.getSolid());
        };
        client.getBlockRenderManager().renderBlock(bottomSlabState, pos, world, matrices, bottomVertexConsumer, false, random);

        /*System.out.println("Rendering block at " + pos + " with state: up: " + topSlabState + "bottom: " + bottomSlabState);
        System.out.println("aaaaaaaaaaaaaaaaaaaaa");*/
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
