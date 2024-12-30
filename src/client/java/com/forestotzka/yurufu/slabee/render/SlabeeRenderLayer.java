package com.forestotzka.yurufu.slabee.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;

@Environment(EnvType.CLIENT)
public class SlabeeRenderLayer {
    public static RenderLayer getSlabeeTranslucent() {
        return SLABEE_TRANSLUCENT;
    }

    public static RenderLayer getDepthOnlyTranslucent() {
        return DEPTH_ONLY_TRANSLUCENT;
    }

    private static final RenderLayer DEPTH_ONLY_TRANSLUCENT = RenderLayer.of(
            "depth_only_translucent",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,
            4194304,
            true,
            false,
            RenderLayer.MultiPhaseParameters.builder()
                    .transparency(RenderPhase.NO_TRANSPARENCY)
                    .writeMaskState(RenderPhase.WriteMaskState.DEPTH_MASK)
                    .cull(RenderPhase.Cull.DISABLE_CULLING)
                    .lightmap(RenderPhase.Lightmap.ENABLE_LIGHTMAP)
                    .overlay(RenderPhase.Overlay.ENABLE_OVERLAY_COLOR)
                    .target(RenderPhase.TRANSLUCENT_TARGET)
                    .program(RenderPhase.TRANSLUCENT_PROGRAM)
                    .texture(RenderPhase.MIPMAP_BLOCK_ATLAS_TEXTURE)
                    .build(true)
    );

    private static final RenderLayer SLABEE_TRANSLUCENT = RenderLayer.of(
            "slabee_translucent",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,
            4194304,
            true,
            true,
            RenderLayer.MultiPhaseParameters.builder()
                    .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                    .program(RenderPhase.TRANSLUCENT_PROGRAM)
                    .texture(RenderPhase.MIPMAP_BLOCK_ATLAS_TEXTURE)
                    .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                    .target(RenderPhase.TRANSLUCENT_TARGET)
                    .writeMaskState(RenderPhase.WriteMaskState.COLOR_MASK)
                    .build(true)
    );

    public static RenderLayer getCustomTranslucent() {
        return RenderLayer.of("custom_translucent",
                VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
                VertexFormat.DrawMode.QUADS,
                4194304,
                true,
                true,
                RenderLayer.MultiPhaseParameters.builder()
                        .depthTest(RenderLayer.LEQUAL_DEPTH_TEST)
                        .writeMaskState(RenderPhase.ALL_MASK)
                        .transparency(RenderLayer.Transparency.TRANSLUCENT_TRANSPARENCY)
                        .target(RenderLayer.Target.TRANSLUCENT_TARGET)
                        .program(RenderPhase.TRANSLUCENT_PROGRAM)
                        .build(true)
        );
    }
    public static RenderLayer getCustomTranslucent2() {
        return RenderLayer.of("custom_translucent",
                VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
                VertexFormat.DrawMode.QUADS,
                4194304,
                true,
                false,
                RenderLayer.MultiPhaseParameters.builder()
                        .depthTest(RenderLayer.LEQUAL_DEPTH_TEST)
                        .writeMaskState(RenderPhase.ALL_MASK)
                        .transparency(RenderLayer.Transparency.TRANSLUCENT_TRANSPARENCY)
                        .target(RenderLayer.Target.TRANSLUCENT_TARGET)
                        .program(RenderPhase.TRANSLUCENT_PROGRAM)
                        .build(true)
        );
    }
    public static RenderLayer getCustomTranslucent3() {
        return RenderLayer.of("custom_translucent",
                VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
                VertexFormat.DrawMode.QUADS,
                4194304,
                true,
                false,
                RenderLayer.MultiPhaseParameters.builder()
                        .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                        .depthTest(RenderLayer.LEQUAL_DEPTH_TEST)
                        .writeMaskState(RenderPhase.ALL_MASK)
                        .transparency(RenderLayer.Transparency.TRANSLUCENT_TRANSPARENCY)
                        .target(RenderLayer.Target.TRANSLUCENT_TARGET)
                        .program(RenderPhase.TRANSLUCENT_PROGRAM)
                        .build(true)
        );
    }
    public static RenderLayer getCustomTranslucent4() {
        return RenderLayer.of("custom_translucent",
                VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
                VertexFormat.DrawMode.QUADS,
                4194304,
                true,
                true,
                RenderLayer.MultiPhaseParameters.builder()
                        .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                        .writeMaskState(RenderPhase.ALL_MASK)
                        .transparency(RenderLayer.Transparency.TRANSLUCENT_TRANSPARENCY)
                        .target(RenderLayer.Target.TRANSLUCENT_TARGET)
                        .program(ModRenderPhase.SLABEE_TRANSLUCENT_PROGRAM)
                        .texture(RenderPhase.MIPMAP_BLOCK_ATLAS_TEXTURE)
                        .build(true)
        );
    }
}
