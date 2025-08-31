package com.forestotzka.yurufu.slabee.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

@Environment(EnvType.CLIENT)
public class DoubleVerticalSlabBlockEntityRenderer extends AbstractDoubleSlabBlockEntityRenderer<DoubleVerticalSlabBlockEntity> {
    public DoubleVerticalSlabBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderPositive(int renderLayerType, BlockState slabState, BlockPos pos, BlockRenderView world, MatrixStack matrices, VertexConsumerProvider vertexConsumers, Random random) {
        if (slabState.getBlock() instanceof SnowyVerticalSlabBlock) {
            slabState = slabState.with(SnowyVerticalSlabBlock.SNOWY, world.getBlockState(pos.up()).isIn(BlockTags.SNOW));
        }

        super.renderPositive(renderLayerType, slabState, pos, world, matrices, vertexConsumers, random);
    }

    @Override
    protected void renderNegative(int renderLayerType, BlockState slabState, BlockPos pos, BlockRenderView world, MatrixStack matrices, VertexConsumerProvider vertexConsumers, Random random) {
        if (slabState.getBlock() instanceof SnowyVerticalSlabBlock) {
            slabState = slabState.with(SnowyVerticalSlabBlock.SNOWY, world.getBlockState(pos.up()).isIn(BlockTags.SNOW));
        }

        super.renderNegative(renderLayerType, slabState, pos, world, matrices, vertexConsumers, random);
    }
}
