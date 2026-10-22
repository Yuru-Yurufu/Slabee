package com.forestotzka.yurufu.slabee.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

@Environment(EnvType.CLIENT)
public class DoubleSlabBlockEntityRenderer extends AbstractDoubleSlabBlockEntityRenderer<DoubleSlabBlockEntity> {
    public DoubleSlabBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderPositive(int renderLayerType, BlockState slabState, BlockPos pos, BlockAndTintGetter world, PoseStack matrices, MultiBufferSource vertexConsumers, RandomSource random) {
        if (slabState.getBlock() instanceof SnowySlabBlock) {
            slabState = slabState.setValue(SnowySlabBlock.SNOWY, world.getBlockState(pos.above()).is(BlockTags.SNOW));
        }

        super.renderPositive(renderLayerType, slabState, pos, world, matrices, vertexConsumers, random);
    }
}
