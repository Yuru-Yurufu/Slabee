package com.forestotzka.yurufu.slabee.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;

import java.util.function.Consumer;

/*@Environment(EnvType.CLIENT)
public class DoubleSlabRenderContext implements RenderContext {
    private final RenderContext delegate;

    public DoubleSlabRenderContext(RenderContext delegate) {
        this.delegate = delegate;
    }

    @Override
    public QuadEmitter getEmitter() {
        return new DoubleSlabQuadEmitter(delegate.getEmitter());
    }

    @Override
    public boolean hasTransform() {
        return delegate.hasTransform();
    }

    @Override
    public void pushTransform(QuadTransform transform) {
        delegate.pushTransform(transform);
    }

    @Override
    public void popTransform() {
        delegate.popTransform();
    }

    @Override
    public Consumer<Mesh> meshConsumer() {
        return delegate.meshConsumer();
    }

    @Override
    public BakedModelConsumer bakedModelConsumer() {
        return null;
    }
}*/
