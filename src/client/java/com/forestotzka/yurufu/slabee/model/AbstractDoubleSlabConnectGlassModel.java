package com.forestotzka.yurufu.slabee.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.forestotzka.yurufu.slabee.model.NeighborState.*;

import com.forestotzka.yurufu.slabee.model.NeighborState.ContactType;
import com.forestotzka.yurufu.slabee.model.NeighborState.DoubleSlabType;
import com.forestotzka.yurufu.slabee.model.NeighborState.Half;

@Environment(EnvType.CLIENT)
public abstract class AbstractDoubleSlabConnectGlassModel extends AbstractConnectGlassModel {
    protected final ResourceLocation positiveId;
    protected final ResourceLocation negativeId;
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
            ResourceLocation positiveId = BuiltInRegistries.BLOCK.getKey(this.positiveSlab);
            this.positiveId = setPositiveId(positiveId);
            this.isGlassPositive = setIsGlass(positiveSlab);
        }

        this.positiveVariantIndex = getVariantIndex(this.positiveSlab);

        if (this.negativeSlab == null) {
            this.negativeId = null;
            this.isGlassNegative = true;
        } else {
            ResourceLocation negativeId = BuiltInRegistries.BLOCK.getKey(this.negativeSlab);
            this.negativeId = setNegativeId(negativeId);
            this.isGlassNegative = setIsGlass(negativeSlab);
        }

        this.negativeVariantIndex = getVariantIndex(this.negativeSlab);
    }

    protected abstract ResourceLocation setPositiveId(ResourceLocation id);
    protected abstract ResourceLocation setNegativeId(ResourceLocation id);
    protected abstract boolean setIsGlass(Block block);

    protected abstract boolean isEndFace(Direction face);
    protected abstract boolean isEndPositiveFace(Direction face);
    protected abstract boolean isEndNegativeFace(Direction face);

    protected abstract void emitSidePositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<Material, TextureAtlasSprite> textureGetter);
    protected abstract void emitSideNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<Material, TextureAtlasSprite> textureGetter);
    protected abstract Material emitEndPositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex);
    protected abstract Material emitEndNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex);

    protected void squareEndQuadPositive(QuadEmitter emitter, Direction dir, int patternIndex, float depth) {
        if (this.isGlassPositive) {
            squareEndQuad(emitter, dir, patternIndex / 5, depth);
        } else {
            squareEndQuad(emitter, dir, patternIndex / 6, depth);
        }
    }

    protected void squareEndQuadNegative(QuadEmitter emitter, Direction dir, int patternIndex, float depth) {
        if (this.isGlassNegative) {
            squareEndQuad(emitter, dir, patternIndex / 5, depth);
        } else {
            squareEndQuad(emitter, dir, patternIndex / 6, depth);
        }
    }

    protected void emitQuarterQuad(QuadEmitter emitter, Direction dir, int patternIndex, int quarterIndex, TextureAtlasSprite sprite) {
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

    protected void setUV(QuadEmitter emitter, float u0, float u1, float v0, float v1, TextureAtlasSprite sprite) {
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
    public @Nullable BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> textureGetter, ModelState rotationContainer) {
        if (this.positiveId != null && !SIDE_POSITIVE_BUILT[axis][positiveVariantIndex]) {
            SIDE_POSITIVE_BUILT[axis][positiveVariantIndex] = true;
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

        if (this.negativeId != null && !SIDE_NEGATIVE_BUILT[axis][negativeVariantIndex]) {
            SIDE_NEGATIVE_BUILT[axis][negativeVariantIndex] = true;
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

        if (this.positiveId != null && !END_POSITIVE_BUILT[axis][positiveVariantIndex]) {
            END_POSITIVE_BUILT[axis][positiveVariantIndex] = true;
            for (int patternIndex = 0; patternIndex < (isGlassPositive ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT); patternIndex++) {
                for (Direction dir : Direction.values()) {
                    if (!isEndFace(dir)) {
                        continue;
                    }

                    MeshBuilder meshBuilder = getBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    Material spriteIdentifier = emitEndPositiveQuad(emitter, dir, patternIndex);
                    emitter.spriteBake(textureGetter.apply(spriteIdentifier), MutableQuadView.BAKE_LOCK_UV);
                    emitter.color(-1, -1, -1, -1);
                    emitter.emit();

                    END_POSITIVE_MESHES[axis][positiveVariantIndex][patternIndex][dir.ordinal()] = meshBuilder.build();
                }
            }
        }

        if (this.negativeId != null && !END_NEGATIVE_BUILT[axis][negativeVariantIndex]) {
            END_NEGATIVE_BUILT[axis][negativeVariantIndex] = true;
            for (int patternIndex = 0; patternIndex < (isGlassNegative ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT); patternIndex++) {
                for (Direction dir : Direction.values()) {
                    if (!isEndFace(dir)) {
                        continue;
                    }

                    MeshBuilder meshBuilder = getBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    Material spriteIdentifier = emitEndNegativeQuad(emitter, dir, patternIndex);
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
            BakedModel bakedModel = baker.getModel(Objects.requireNonNullElse(this.positiveId, this.negativeId)).bake(baker, textureGetter, rotationContainer);

            if (bakedModel != null) {
                this.particleSprite = bakedModel.getParticleIcon();
            } else {
                this.particleSprite = textureGetter.apply(nullSpriteIdentifier);
            }
        }

        return this;
    }

    @Override
    public void emitBlockQuads(BlockAndTintGetter blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<RandomSource> supplier, RenderContext renderContext) {
        // 描くガラスのハーフがない（両側とも普通のハーフ）なら、周りのガラスを調べる必要はない
        if (this.positiveId == null && this.negativeId == null) {
            return;
        }

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
                        {
                            Mesh mesh = END_POSITIVE_MESHES[axis][positiveVariantIndex][(isGlassPositive ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT) - 1][face.ordinal()];
                            if (mesh != null) {
                                mesh.outputTo(renderContext.getEmitter());
                            }
                        }

                        for (int index : getEndPatternIndexes(face, ns, true)) {
                            Mesh mesh = END_POSITIVE_MESHES[axis][positiveVariantIndex][index][face.ordinal()];
                            if (mesh != null) {
                                mesh.outputTo(renderContext.getEmitter());
                            }
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
