package com.forestotzka.yurufu.slabee.render;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderPhase;

public abstract class ModRenderPhase {
    public static final RenderPhase.ShaderProgram SLABEE_TRANSLUCENT_PROGRAM = new RenderPhase.ShaderProgram(GameRenderer::getRenderTypeTranslucentProgram);
}
