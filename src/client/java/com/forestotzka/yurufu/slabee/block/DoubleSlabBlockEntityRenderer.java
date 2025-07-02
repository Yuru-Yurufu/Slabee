package com.forestotzka.yurufu.slabee.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

@Environment(EnvType.CLIENT)
public class DoubleSlabBlockEntityRenderer extends AbstractDoubleSlabBlockEntityRenderer<DoubleSlabBlockEntity> {
    public DoubleSlabBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderNegative(int renderLayerType, BlockState slabState, BlockPos pos, BlockRenderView world, MatrixStack matrices, VertexConsumerProvider vertexConsumers, Random random) {
        if (slabState.isOf(ModBlocks.DIRT_PATH_SLAB)) {
            slabState = ModBlocks.DIRT_SLAB.getDefaultState();
        }

        VertexConsumer vertexConsumer = getVertexConsumer(renderLayerType, vertexConsumers);
        client.getBlockRenderManager().renderBlock(slabState, pos, world, matrices, vertexConsumer, true, random);
    }
}
