/*
package com.forestotzka.yurufu.slabee.render;

import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadView;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class DoubleSlabQuadEmitter implements QuadEmitter {
    private final QuadEmitter delegate;

    public DoubleSlabQuadEmitter(QuadEmitter delegate) {
        this.delegate = delegate;
    }

    @Override
    public QuadEmitter pos(int vertexIndex, float x, float y, float z) {
        return null;
    }

    @Override
    public QuadEmitter color(int vertexIndex, int color) {
        return null;
    }

    @Override
    public QuadEmitter uv(int vertexIndex, float u, float v) {
        return null;
    }

    @Override
    public QuadEmitter spriteBake(Sprite sprite, int bakeFlags) {
        return null;
    }

    @Override
    public QuadEmitter lightmap(int vertexIndex, int lightmap) {
        return null;
    }

    @Override
    public QuadEmitter normal(int vertexIndex, float x, float y, float z) {
        return null;
    }

    @Override
    public QuadEmitter cullFace(@Nullable Direction face) {
        return null;
    }

    @Override
    public QuadEmitter nominalFace(@Nullable Direction face) {
        return null;
    }

    @Override
    public QuadEmitter material(RenderMaterial material) {
        return null;
    }

    @Override
    public QuadEmitter colorIndex(int colorIndex) {
        return null;
    }

    @Override
    public QuadEmitter tag(int tag) {
        return null;
    }

    @Override
    public QuadEmitter copyFrom(QuadView quad) {
        return null;
    }

    @Override
    public QuadEmitter fromVanilla(int[] quadData, int startIndex) {
        return null;
    }

    @Override
    public QuadEmitter fromVanilla(BakedQuad quad, RenderMaterial material, @Nullable Direction cullFace) {
        return delegate.fromVanilla(quad, material, cullFace);
    }

    @Override
    public QuadEmitter emit() {
        if (shouldSkipCurrentQuad()) {
            // このクォードは出力しない
            return null;
        }

        return delegate;
    }

    private boolean shouldSkipCurrentQuad() {
        return false;
    }

    @Override
    public float x(int vertexIndex) {
        return 0;
    }

    @Override
    public float y(int vertexIndex) {
        return 0;
    }

    @Override
    public float z(int vertexIndex) {
        return 0;
    }

    @Override
    public float posByIndex(int vertexIndex, int coordinateIndex) {
        return 0;
    }

    @Override
    public Vector3f copyPos(int vertexIndex, @Nullable Vector3f target) {
        return null;
    }

    @Override
    public int color(int vertexIndex) {
        return 0;
    }

    @Override
    public float u(int vertexIndex) {
        return 0;
    }

    @Override
    public float v(int vertexIndex) {
        return 0;
    }

    @Override
    public Vector2f copyUv(int vertexIndex, @Nullable Vector2f target) {
        return null;
    }

    @Override
    public int lightmap(int vertexIndex) {
        return 0;
    }

    @Override
    public boolean hasNormal(int vertexIndex) {
        return false;
    }

    @Override
    public float normalX(int vertexIndex) {
        return 0;
    }

    @Override
    public float normalY(int vertexIndex) {
        return 0;
    }

    @Override
    public float normalZ(int vertexIndex) {
        return 0;
    }

    @Override
    public @Nullable Vector3f copyNormal(int vertexIndex, @Nullable Vector3f target) {
        return null;
    }

    @Override
    public @Nullable Direction cullFace() {
        return null;
    }

    @Override
    public @NotNull Direction lightFace() {
        return null;
    }

    @Override
    public @Nullable Direction nominalFace() {
        return null;
    }

    @Override
    public Vector3f faceNormal() {
        return null;
    }

    @Override
    public RenderMaterial material() {
        return null;
    }

    @Override
    public int colorIndex() {
        return 0;
    }

    @Override
    public int tag() {
        return 0;
    }

    @Override
    public void toVanilla(int[] target, int targetIndex) {

    }
}
*/
