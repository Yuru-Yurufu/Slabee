package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static com.forestotzka.yurufu.slabee.model.GlassSprites.*;
import static com.forestotzka.yurufu.slabee.model.NeighborState.*;

public class AbstractConnectGlassModel implements UnbakedModel, BakedModel, FabricBakedModel {
    protected Sprite particleSprite;

    protected static final int VARIANT_COUNT = 18;
    protected static final int GLASS_PATTERN_COUNT = 21;
    protected static final int STAINED_GLASS_PATTERN_COUNT = 25;
    protected static final int SLAB_PATTERN_COUNT = 169;
    protected static final int DIRECTION_COUNT = Direction.values().length;
    protected static final int AXIS_COUNT = 3;
    protected static final int QUARTER_COUNT = 4;
    protected static final int UV_AXIS_COUNT = 2;

    protected static final Mesh[][][][] SIDE_POSITIVE_MESHES = new Mesh[AXIS_COUNT][VARIANT_COUNT][SLAB_PATTERN_COUNT][DIRECTION_COUNT];
    protected static final Mesh[][][][] SIDE_NEGATIVE_MESHES = new Mesh[AXIS_COUNT][VARIANT_COUNT][SLAB_PATTERN_COUNT][DIRECTION_COUNT];
    protected static final Mesh[][][][] END_POSITIVE_MESHES = new Mesh[AXIS_COUNT][VARIANT_COUNT][STAINED_GLASS_PATTERN_COUNT][DIRECTION_COUNT];
    protected static final Mesh[][][][] END_NEGATIVE_MESHES = new Mesh[AXIS_COUNT][VARIANT_COUNT][STAINED_GLASS_PATTERN_COUNT][DIRECTION_COUNT];
    protected static final Mesh[][][] END_MESHES = new Mesh[VARIANT_COUNT][STAINED_GLASS_PATTERN_COUNT][DIRECTION_COUNT];
    protected static final Mesh[][][][][] QUARTER_MESHES = new Mesh[VARIANT_COUNT][SLAB_PATTERN_COUNT][DIRECTION_COUNT][QUARTER_COUNT][UV_AXIS_COUNT];

    protected static final SpriteIdentifier nullSpriteIdentifier = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.ofVanilla("block/stone"));

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
    protected static MeshBuilder getBuilder() {
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
        this.particleSprite = textureGetter.apply(nullSpriteIdentifier);

        return this;
    }

    protected Mesh getHalfEndMesh(NeighborState ns, ContactType contactType, Direction face, int variantIndex) {
        switch (face.getAxis()) {
            case X -> {
                switch (contactType) {
                    case POSITIVE1 -> {
                        return SIDE_NEGATIVE_MESHES[0][variantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsNegativeY(face, ns, true, Half.POSITIVE)))][face.ordinal()];
                    }
                    case NEGATIVE1 -> {
                        return SIDE_POSITIVE_MESHES[0][variantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsPositiveY(face, ns, true, Half.POSITIVE)))][face.ordinal()];
                    }
                    case POSITIVE2 -> {
                        return SIDE_NEGATIVE_MESHES[2][variantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsNegativeZ(face, ns, true, Half.POSITIVE)))][face.ordinal()];
                    }
                    default -> {
                        return SIDE_POSITIVE_MESHES[2][variantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsPositiveZ(face, ns, true, Half.POSITIVE)))][face.ordinal()];
                    }
                }
            }
            case Y -> {
                switch (contactType) {
                    case POSITIVE1 -> {
                        return SIDE_NEGATIVE_MESHES[1][variantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsNegativeX(face, ns, true, Half.POSITIVE)))][face.ordinal()];
                    }
                    case NEGATIVE1 -> {
                        return SIDE_POSITIVE_MESHES[1][variantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsPositiveX(face, ns, true, Half.POSITIVE)))][face.ordinal()];
                    }
                    case POSITIVE2 -> {
                        return SIDE_NEGATIVE_MESHES[2][variantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsNegativeZ(face, ns, true, Half.POSITIVE)))][face.ordinal()];
                    }
                    default -> {
                        return SIDE_POSITIVE_MESHES[2][variantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsPositiveZ(face, ns, true, Half.POSITIVE)))][face.ordinal()];
                    }
                }
            }
            default -> {
                switch (contactType) {
                    case POSITIVE1 -> {
                        return SIDE_NEGATIVE_MESHES[0][variantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsNegativeY(face, ns, true, Half.POSITIVE)))][face.ordinal()];
                    }
                    case NEGATIVE1 -> {
                        return SIDE_POSITIVE_MESHES[0][variantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsPositiveY(face, ns, true, Half.POSITIVE)))][face.ordinal()];
                    }
                    case POSITIVE2 -> {
                        return SIDE_NEGATIVE_MESHES[1][variantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsNegativeX(face, ns, true, Half.POSITIVE)))][face.ordinal()];
                    }
                    default -> {
                        return SIDE_POSITIVE_MESHES[1][variantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsPositiveX(face, ns, true, Half.POSITIVE)))][face.ordinal()];
                    }
                }
            }
        }
    }

    protected int getVariantIndex(Block block) {
        if (block == ModBlocks.WHITE_STAINED_GLASS_VERTICAL_SLAB) {
            return 1;
        } else if (block == ModBlocks.LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB) {
            return 2;
        } else if (block == ModBlocks.GRAY_STAINED_GLASS_VERTICAL_SLAB) {
            return 3;
        } else if (block == ModBlocks.BLACK_STAINED_GLASS_VERTICAL_SLAB) {
            return 4;
        } else if (block == ModBlocks.BROWN_STAINED_GLASS_VERTICAL_SLAB) {
            return 5;
        } else if (block == ModBlocks.RED_STAINED_GLASS_VERTICAL_SLAB) {
            return 6;
        } else if (block == ModBlocks.ORANGE_STAINED_GLASS_VERTICAL_SLAB) {
            return 7;
        } else if (block == ModBlocks.YELLOW_STAINED_GLASS_VERTICAL_SLAB) {
            return 8;
        } else if (block == ModBlocks.LIME_STAINED_GLASS_VERTICAL_SLAB) {
            return 9;
        } else if (block == ModBlocks.GREEN_STAINED_GLASS_VERTICAL_SLAB) {
            return 10;
        } else if (block == ModBlocks.CYAN_STAINED_GLASS_VERTICAL_SLAB) {
            return 11;
        } else if (block == ModBlocks.LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB) {
            return 12;
        } else if (block == ModBlocks.BLUE_STAINED_GLASS_VERTICAL_SLAB) {
            return 13;
        } else if (block == ModBlocks.PURPLE_STAINED_GLASS_VERTICAL_SLAB) {
            return 14;
        } else if (block == ModBlocks.MAGENTA_STAINED_GLASS_VERTICAL_SLAB) {
            return 15;
        } else if (block == ModBlocks.PINK_STAINED_GLASS_VERTICAL_SLAB) {
            return 16;
        } else if (block == ModBlocks.TINTED_GLASS_VERTICAL_SLAB) {
            return 17;
        } else {
            return 0;
        }
    }
}
