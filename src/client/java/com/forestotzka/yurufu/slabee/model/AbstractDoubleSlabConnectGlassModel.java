package com.forestotzka.yurufu.slabee.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.screen.PlayerScreenHandler;
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
public abstract class AbstractDoubleSlabConnectGlassModel implements UnbakedModel, BakedModel, FabricBakedModel {
    protected final Identifier positiveId;
    protected final Identifier negativeId;
    protected final Block positiveSlab;
    protected final Block negativeSlab;
    private Sprite particleSprite;

    protected static final int GLASS_PATTERN_COUNT = 21;
    protected static final int STAINED_GLASS_PATTERN_COUNT = 25;
    protected static final int SLAB_PATTERN_COUNT = 169;
    protected static final int SLAB_COLS = 16;
    private static final int DIRECTION_COUNT = Direction.values().length;

    protected final Mesh[][] positiveMeshes = new Mesh[SLAB_PATTERN_COUNT][DIRECTION_COUNT];
    protected final Mesh[][] negativeMeshes = new Mesh[SLAB_PATTERN_COUNT][DIRECTION_COUNT];
    protected final Mesh[][] endPositiveMeshes = new Mesh[STAINED_GLASS_PATTERN_COUNT][DIRECTION_COUNT];
    protected final Mesh[][] endNegativeMeshes = new Mesh[STAINED_GLASS_PATTERN_COUNT][DIRECTION_COUNT];

    protected final boolean isGlassPositive;
    protected final boolean isGlassNegative;

    protected AbstractDoubleSlabConnectGlassModel(@Nullable Block positiveSlab, @Nullable Block negativeSlab) {
        this.positiveSlab = positiveSlab;
        this.negativeSlab = negativeSlab;

        if (this.positiveSlab == null) {
            this.positiveId = null;
            this.isGlassPositive = true;
        } else {
            Identifier positiveId = Registries.BLOCK.getId(this.positiveSlab);
            this.positiveId = setPositiveId(positiveId);
            this.isGlassPositive = setIsGlass(positiveSlab);
        }

        if (this.negativeSlab == null) {
            this.negativeId = null;
            this.isGlassNegative = true;
        } else {
            Identifier negativeId = Registries.BLOCK.getId(this.negativeSlab);
            this.negativeId = setNegativeId(negativeId);
            this.isGlassNegative = setIsGlass(negativeSlab);
        }
    }

    protected abstract Identifier setPositiveId(Identifier id);
    protected abstract Identifier setNegativeId(Identifier id);
    protected abstract boolean setIsGlass(Block block);

    protected abstract boolean isEndFace(Direction face);

    protected abstract void emitSidePositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<SpriteIdentifier, Sprite> textureGetter);
    protected abstract void emitSideNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<SpriteIdentifier, Sprite> textureGetter);
    protected abstract SpriteIdentifier emitEndPositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex/*, Function<SpriteIdentifier, Sprite> textureGetter*/);
    protected abstract SpriteIdentifier emitEndNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex/*, Function<SpriteIdentifier, Sprite> textureGetter*/);

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
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        return this.particleSprite;
    }

    @Override
    public ModelTransformation getTransformation() {
        return null;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return null;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return List.of();
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }

    private static final ThreadLocal<MeshBuilder> BUILDER_POOL = ThreadLocal.withInitial(() -> {
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        if (renderer == null) {
            throw new IllegalStateException("Renderer not yet available");
        }
        // １度だけ new MeshBuilderImpl() が呼ばれる
        return renderer.meshBuilder();
    });

    // 利用前に、もし前回のデータが残っているなら build() でリセット
    private static MeshBuilder getBuilder() {
        MeshBuilder builder = BUILDER_POOL.get();
        // 呼び出し直後は index==0 だが、ループ２回目以降で必要なら build() しておく
        builder.build(); // これで内部 index が 0 にリセットされる
        return builder;
    }

    public static void clearBuilderPool() {
        BUILDER_POOL.remove();
    }

    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        for (int patternIndex = 0; patternIndex < SLAB_PATTERN_COUNT; patternIndex++) {
            Mesh[] sidePositiveFaceMeshes = new Mesh[DIRECTION_COUNT];
            Mesh[] sideNegativeFaceMeshes = new Mesh[DIRECTION_COUNT];

            for (Direction dir : Direction.values()) {
                if (isEndFace(dir)) {
                    continue;
                }

                {
                    MeshBuilder meshBuilder = getBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    emitSidePositiveQuad(emitter, dir, patternIndex, textureGetter);

                    sidePositiveFaceMeshes[dir.ordinal()] = meshBuilder.build();
                }
                {
                    MeshBuilder meshBuilder = getBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    emitSideNegativeQuad(emitter, dir, patternIndex, textureGetter);

                    sideNegativeFaceMeshes[dir.ordinal()] = meshBuilder.build();
                }
            }

            positiveMeshes[patternIndex] = sidePositiveFaceMeshes;
            negativeMeshes[patternIndex] = sideNegativeFaceMeshes;
        }

        for (int patternIndex = 0; patternIndex < (isGlassPositive ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT); patternIndex++) {
            Mesh[] endPositiveFaceMeshes = new Mesh[DIRECTION_COUNT];

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

                endPositiveFaceMeshes[dir.ordinal()] = meshBuilder.build();
            }

            endPositiveMeshes[patternIndex] = endPositiveFaceMeshes;
        }

        for (int patternIndex = 0; patternIndex < (isGlassNegative ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT); patternIndex++) {
            Mesh[] endNegativeFaceMeshes = new Mesh[DIRECTION_COUNT];

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

                endNegativeFaceMeshes[dir.ordinal()] = meshBuilder.build();
            }

            endNegativeMeshes[patternIndex] = endNegativeFaceMeshes;
        }

        if ((this.positiveId == null) == (this.negativeId == null)) {
            this.particleSprite = textureGetter.apply(new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.ofVanilla("block/stone")));
        } else {
            BakedModel bakedModel = baker.getOrLoadModel(Objects.requireNonNullElse(this.positiveId, this.negativeId)).bake(baker, textureGetter, rotationContainer);

            if (bakedModel != null) {
                this.particleSprite = bakedModel.getParticleSprite();
            } else {
                this.particleSprite = textureGetter.apply(new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.ofVanilla("block/stone")));
            }
        }

        return this;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {
        NeighborState ns = new NeighborState(blockRenderView, blockPos, positiveSlab, negativeSlab, getDoubleSlabType());

        if (this.positiveId != null) {
            for (Direction face : Direction.values()) {
                if (this.positiveSlab == null || shouldCullPositive(face, ns)) {
                    continue;
                }

                if (isEndFace(face)) {
                    for (int index : getEndPatternIndexes(face, ns, true)) {
                        Mesh mesh = endPositiveMeshes[index][face.ordinal()];
                        if (mesh != null) {
                            mesh.outputTo(renderContext.getEmitter());
                        }
                    }

                    Mesh mesh = endPositiveMeshes[(isGlassPositive ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT)-1][face.ordinal()];
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
                    }
                } else {
                    Mesh mesh = positiveMeshes[getSidePatternIndex(face, ns, true)][face.ordinal()];
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

                if (isEndFace(face)) {
                    for (int index : getEndPatternIndexes(face, ns, false)) {
                        Mesh mesh = endNegativeMeshes[index][face.ordinal()];
                        if (mesh != null) {
                            mesh.outputTo(renderContext.getEmitter());
                        }
                    }

                    Mesh mesh = endNegativeMeshes[(isGlassNegative ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT)-1][face.ordinal()];
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
                    }
                } else {
                    Mesh mesh = negativeMeshes[getSidePatternIndex(face, ns, false)][face.ordinal()];
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
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
        //return new ArrayList<>(List.of(0,5,10,15));
    }

    private int getSidePatternIndex(Direction face, NeighborState ns, boolean isPositive) {
        int index;
        if (isPositive) {
            index = determinePatternPositive(face, ns);
        } else {
            index = determinePatternNegative(face, ns);
        }

        return GlassSprites.getMappedIndex(index);
        //return 0;
    }

    protected abstract List<Integer> determinePatternEndPositive(Direction face, NeighborState ns);
    protected abstract List<Integer> determinePatternEndNegative(Direction face, NeighborState ns);
    protected abstract int determinePatternPositive(Direction face, NeighborState ns);
    protected abstract int determinePatternNegative(Direction face, NeighborState ns);
    protected abstract boolean shouldCullPositive(Direction face, NeighborState ns);
    protected abstract boolean shouldCullNegative(Direction face, NeighborState ns);
}
