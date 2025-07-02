package com.forestotzka.yurufu.slabee.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;

@Environment(EnvType.CLIENT)
public class DoubleVerticalSlabBlockEntityRenderer extends AbstractDoubleSlabBlockEntityRenderer<DoubleVerticalSlabBlockEntity> {
    public DoubleVerticalSlabBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }
}
