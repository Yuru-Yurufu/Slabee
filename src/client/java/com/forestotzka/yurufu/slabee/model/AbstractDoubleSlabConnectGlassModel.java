package com.forestotzka.yurufu.slabee.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.forestotzka.yurufu.slabee.model.NeighborState.*;

@Environment(EnvType.CLIENT)
public abstract class AbstractDoubleSlabConnectGlassModel extends AbstractConnectGlassModel {
    protected final Identifier positiveId;
    protected final Identifier negativeId;
    protected final Block positiveSlab;
    protected final Block negativeSlab;

    protected static final int SLAB_COLS = 16;

    protected final boolean isGlassPositive;
    protected final boolean isGlassNegative;

    protected final int positiveVariantIndex;
    protected final int negativeVariantIndex;
    protected final int axis;

    protected AbstractDoubleSlabConnectGlassModel(@Nullable Block positiveSlab, @Nullable Block negativeSlab, int axis) {
        this.positiveSlab = positiveSlab;
        this.negativeSlab = negativeSlab;
        this.axis = axis;

        if (this.positiveSlab == null) {
            this.positiveId = null;
            this.isGlassPositive = true;
        } else {
            Identifier positiveId = Registries.BLOCK.getId(this.positiveSlab);
            this.positiveId = setPositiveId(positiveId);
            this.isGlassPositive = setIsGlass(positiveSlab);
        }

        this.positiveVariantIndex = getVariantIndex(this.positiveSlab);

        if (this.negativeSlab == null) {
            this.negativeId = null;
            this.isGlassNegative = true;
        } else {
            Identifier negativeId = Registries.BLOCK.getId(this.negativeSlab);
            this.negativeId = setNegativeId(negativeId);
            this.isGlassNegative = setIsGlass(negativeSlab);
        }

        this.negativeVariantIndex = getVariantIndex(this.negativeSlab);
    }

    protected abstract Identifier setPositiveId(Identifier id);
    protected abstract Identifier setNegativeId(Identifier id);
    protected abstract boolean setIsGlass(Block block);

    protected abstract boolean isEndFace(Direction face);
    protected abstract boolean isEndPositiveFace(Direction face);
    protected abstract boolean isEndNegativeFace(Direction face);

    protected abstract void emitSidePositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<SpriteIdentifier, Sprite> textureGetter);
    protected abstract void emitSideNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<SpriteIdentifier, Sprite> textureGetter);
    protected abstract SpriteIdentifier emitEndPositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex);
    protected abstract SpriteIdentifier emitEndNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex);

    protected void emitQuarterQuad(QuadEmitter emitter, Direction dir, int patternIndex, int quarterIndex, Sprite sprite) {
        int x = patternIndex % SLAB_COLS;
        int y = patternIndex / SLAB_COLS;

        switch (quarterIndex) {
            case 0 /* 左上 */ -> {
                emitter.square(dir, 0, 0.5f, 0.5f, 1, 0);
                setUV(emitter, x, x+0.5f, y, y+0.5f, sprite);
            }
            case 1 /* 右上 */ -> {
                emitter.square(dir, 0.5f, 0.5f, 1, 1, 0);
                setUV(emitter, x+0.5f, x+1, y, y+0.5f, sprite);
            }
            case 2 /* 右下 */ -> {
                emitter.square(dir, 0.5f, 0, 1, 0.5f, 0);
                setUV(emitter, x+0.5f, x+1, y+0.5f, y+1, sprite);
            }
            case 3 /* 左下 */ -> {
                emitter.square(dir, 0, 0, 0.5f, 0.5f, 0);
                setUV(emitter, x, x+0.5f, y+0.5f, y+1, sprite);
            }
        }
    }

    protected void setUV(QuadEmitter emitter, float u0, float u1, float v0, float v1, Sprite sprite) {
        emitter.uv(0, u0, v0);
        emitter.uv(1, u0, v1);
        emitter.uv(2, u1, v1);
        emitter.uv(3, u1, v0);

        emitter.spriteBake(sprite, 0);
        emitter.color(-1, -1, -1, -1);
        emitter.emit();
    }

    protected abstract DoubleSlabType getDoubleSlabType();

    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        if (this.positiveId != null && SIDE_POSITIVE_MESHES[axis][positiveVariantIndex][0][0] == null) {
            for (int patternIndex = 0; patternIndex < SLAB_PATTERN_COUNT; patternIndex++) {
                for (Direction dir : Direction.values()) {
                    if (isEndFace(dir)) {
                        continue;
                    }

                    MeshBuilder meshBuilder = getBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    emitSidePositiveQuad(emitter, dir, patternIndex, textureGetter);

                    SIDE_POSITIVE_MESHES[axis][positiveVariantIndex][patternIndex][dir.ordinal()] = meshBuilder.build();
                }
            }
        }

        if (this.negativeId != null && SIDE_NEGATIVE_MESHES[axis][negativeVariantIndex][0][0] == null) {
            for (int patternIndex = 0; patternIndex < SLAB_PATTERN_COUNT; patternIndex++) {
                for (Direction dir : Direction.values()) {
                    if (isEndFace(dir)) {
                        continue;
                    }

                    MeshBuilder meshBuilder = getBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    emitSideNegativeQuad(emitter, dir, patternIndex, textureGetter);

                    SIDE_NEGATIVE_MESHES[axis][negativeVariantIndex][patternIndex][dir.ordinal()] = meshBuilder.build();
                }
            }
        }

        if (this.positiveId != null && END_POSITIVE_MESHES[axis][positiveVariantIndex][0][0] == null) {
            for (int patternIndex = 0; patternIndex < (isGlassPositive ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT); patternIndex++) {
                for (Direction dir : Direction.values()) {
                    if (!isEndFace(dir)) {
                        continue;
                    }

                    MeshBuilder meshBuilder = getBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    SpriteIdentifier spriteIdentifier = emitEndPositiveQuad(emitter, dir, patternIndex);
                    emitter.spriteBake(textureGetter.apply(spriteIdentifier), MutableQuadView.BAKE_LOCK_UV);
                    emitter.color(-1, -1, -1, -1);
                    emitter.emit();

                    END_POSITIVE_MESHES[axis][positiveVariantIndex][patternIndex][dir.ordinal()] = meshBuilder.build();
                }
            }
        }

        if (this.negativeId != null && END_NEGATIVE_MESHES[axis][negativeVariantIndex][0][0] == null) {
            for (int patternIndex = 0; patternIndex < (isGlassNegative ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT); patternIndex++) {
                for (Direction dir : Direction.values()) {
                    if (!isEndFace(dir)) {
                        continue;
                    }

                    MeshBuilder meshBuilder = getBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    SpriteIdentifier spriteIdentifier = emitEndNegativeQuad(emitter, dir, patternIndex);
                    emitter.spriteBake(textureGetter.apply(spriteIdentifier), MutableQuadView.BAKE_LOCK_UV);
                    emitter.color(-1, -1, -1, -1);
                    emitter.emit();

                    END_NEGATIVE_MESHES[axis][negativeVariantIndex][patternIndex][dir.ordinal()] = meshBuilder.build();
                }
            }
        }

        if ((this.positiveId == null) == (this.negativeId == null)) {
            this.particleSprite = textureGetter.apply(nullSpriteIdentifier);
        } else {
            BakedModel bakedModel = baker.getOrLoadModel(Objects.requireNonNullElse(this.positiveId, this.negativeId)).bake(baker, textureGetter, rotationContainer);

            if (bakedModel != null) {
                this.particleSprite = bakedModel.getParticleSprite();
            } else {
                this.particleSprite = textureGetter.apply(nullSpriteIdentifier);
            }
        }

        return this;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {
        NeighborState ns = new NeighborState(blockRenderView, blockPos, positiveSlab, negativeSlab, getDoubleSlabType());

        if (this.positiveId != null) {
            for (Direction face : Direction.values()) {
                if (this.positiveSlab == null) {
                    continue;
                }

                ContactType contactType = ns.getContactType(asNeighborDirection(face), Half.POSITIVE);
                if (isEndFace(face)) {
                    if (shouldCullPositive(face, ns)) continue;

                    if (contactType == ContactType.NONE || !isEndPositiveFace(face)) {
                        for (int index : getEndPatternIndexes(face, ns, true)) {
                            Mesh mesh = END_POSITIVE_MESHES[axis][positiveVariantIndex][index][face.ordinal()];
                            if (mesh != null) {
                                mesh.outputTo(renderContext.getEmitter());
                            }
                        }

                        Mesh mesh = END_POSITIVE_MESHES[axis][positiveVariantIndex][(isGlassPositive ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT) - 1][face.ordinal()];
                        if (mesh != null) {
                            mesh.outputTo(renderContext.getEmitter());
                        }
                    } else {
                        Mesh mesh = getHalfEndMeshPositive(ns, contactType);
                        if (mesh != null) {
                            mesh.outputTo(renderContext.getEmitter());
                        }
                    }
                } else {
                    Mesh mesh;
                    if (ns.isSameSlab()) {
                        if (contactType != ContactType.NONE && contactType != ContactType.FULL) {
                            mesh = getHalfEndMesh(ns, contactType, face, positiveVariantIndex);
                        } else {
                            if (shouldCullPositive(face, ns)) continue;
                            mesh = SIDE_POSITIVE_MESHES[axis][positiveVariantIndex][getSidePatternIndex(face, ns, true)][face.ordinal()];
                        }
                    } else {
                        if (shouldCullPositive(face, ns)) continue;
                        mesh = getSideMesh(face, ns, contactType, true);
                    }
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
                    }
                }
            }
        }

        if (this.negativeId != null) {
            for (Direction face : Direction.values()) {
                if (this.negativeSlab == null || shouldCullNegative(face, ns)) {
                    continue;
                }

                ContactType contactType = ns.getContactType(asNeighborDirection(face), Half.NEGATIVE);
                if (isEndFace(face)) {
                    if (contactType == ContactType.NONE || !isEndNegativeFace(face)) {
                        for (int index : getEndPatternIndexes(face, ns, false)) {
                            Mesh mesh = END_NEGATIVE_MESHES[axis][negativeVariantIndex][index][face.ordinal()];
                            if (mesh != null) {
                                mesh.outputTo(renderContext.getEmitter());
                            }
                        }

                        Mesh mesh = END_NEGATIVE_MESHES[axis][negativeVariantIndex][(isGlassNegative ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT) - 1][face.ordinal()];
                        if (mesh != null) {
                            mesh.outputTo(renderContext.getEmitter());
                        }
                    } else {
                        Mesh mesh = getHalfEndMeshNegative(ns, contactType);
                        if (mesh != null) {
                            mesh.outputTo(renderContext.getEmitter());
                        }
                    }
                } else {
                    if (!ns.isSameSlab() || contactType == ContactType.NONE) {
                        Mesh mesh = getSideMesh(face, ns, contactType, false);
                        if (mesh != null) {
                            mesh.outputTo(renderContext.getEmitter());
                        }
                    }
                }
            }
        }
    }

    private List<Integer> getEndPatternIndexes(Direction face, NeighborState ns, boolean isPositive) {
        List<Integer> indexes;
        if (isPositive) {
            indexes = determinePatternEndPositive(face, ns);
            if (this.isGlassPositive) indexes.replaceAll(i -> i - (i / 6));
        } else {
            indexes = determinePatternEndNegative(face, ns);
            if (this.isGlassNegative) indexes.replaceAll(i -> i - (i / 6));
        }

        return indexes;
    }

    protected int getSidePatternIndex(Direction face, NeighborState ns, boolean isPositive) {
        int index;
        if (isPositive) {
            index = determinePatternPositive(face, ns);
        } else {
            index = determinePatternNegative(face, ns);
        }

        return GlassSprites.getMappedIndex(index);
    }

    protected abstract Mesh getSideMesh(Direction face, NeighborState ns, ContactType contactType, boolean isPositive);
    protected abstract Mesh getHalfEndMeshPositive(NeighborState ns, ContactType contactType);
    protected abstract Mesh getHalfEndMeshNegative(NeighborState ns, ContactType contactType);
    protected abstract List<Integer> determinePatternEndPositive(Direction face, NeighborState ns);
    protected abstract List<Integer> determinePatternEndNegative(Direction face, NeighborState ns);
    protected abstract int determinePatternPositive(Direction face, NeighborState ns);
    protected abstract int determinePatternNegative(Direction face, NeighborState ns);
    protected abstract boolean shouldCullPositive(Direction face, NeighborState ns);
    protected abstract boolean shouldCullNegative(Direction face, NeighborState ns);
}
