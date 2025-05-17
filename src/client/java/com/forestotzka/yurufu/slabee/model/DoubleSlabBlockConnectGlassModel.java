package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.block.*;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.forestotzka.yurufu.slabee.model.GlassSprites.determineEndPatternIndexes;
import static com.forestotzka.yurufu.slabee.model.GlassSprites.determineSlabSidePatternIndex;
import static com.forestotzka.yurufu.slabee.model.NeighborState.ContactType;
import static com.forestotzka.yurufu.slabee.model.NeighborState.Half;
import static com.forestotzka.yurufu.slabee.model.NeighborState.NeighborDirection;

@Environment(EnvType.CLIENT)
public class DoubleSlabBlockConnectGlassModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private final Identifier positiveId;
    private final Identifier negativeId;
    private final Block positiveSlab;
    private final Block negativeSlab;
    private BakedModel positiveBakedModel;
    private BakedModel negativeBakedModel;
    private BakedModel nullBakedModel;

    private final Map<Integer, EnumMap<Direction, Mesh>> positiveMeshMap = new HashMap<>();
    private final Map<Integer, EnumMap<Direction, Mesh>> negativeMeshMap = new HashMap<>();
    private final Map<Integer, EnumMap<Direction, Mesh>> endPositiveMeshMap = new HashMap<>();
    private final Map<Integer, EnumMap<Direction, Mesh>> endNegativeMeshMap = new HashMap<>();

    private static final int GLASS_PATTERN_COUNT = 21;
    private static final int STAINED_GLASS_PATTERN_COUNT = 25;
    private static final int SLAB_PATTERN_COUNT = 169;

    private final boolean isGlassPositive;
    private final boolean isGlassNegative;

    public DoubleSlabBlockConnectGlassModel(@Nullable Block positiveSlab, @Nullable Block negativeSlab) {
        this.positiveSlab = positiveSlab;
        this.negativeSlab = negativeSlab;

        if (this.positiveSlab == null) {
            this.positiveId = null;
            this.isGlassPositive = true;
        } else {
            Identifier positiveId = Registries.BLOCK.getId(this.positiveSlab);
            this.positiveId = Identifier.of(positiveId.getNamespace(), "block/" + positiveId.getPath() + "_top");
            this.isGlassPositive = positiveSlab == ModBlocks.GLASS_SLAB;
        }

        if (this.negativeSlab == null) {
            this.negativeId = null;
            this.isGlassNegative = true;
        } else {
            Identifier negativeId = Registries.BLOCK.getId(this.negativeSlab);
            this.negativeId = Identifier.of(negativeId.getNamespace(), "block/" + negativeId.getPath());
            this.isGlassNegative = negativeSlab == ModBlocks.GLASS_SLAB;
        }
    }

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
        if (positiveId != null) {
            return positiveBakedModel.getParticleSprite();
        } else if (negativeId != null) {
            return negativeBakedModel.getParticleSprite();
        } else {
            return nullBakedModel.getParticleSprite();
        }
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

    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        if (renderer != null) {
            for (int patternIndex = 0; patternIndex < SLAB_PATTERN_COUNT; patternIndex++) {
                EnumMap<Direction, Mesh> positiveFaceMeshes = new EnumMap<>(Direction.class);
                EnumMap<Direction, Mesh> negativeFaceMeshes = new EnumMap<>(Direction.class);

                for (Direction dir : Direction.values()) {
                    if (dir.getAxis() == Direction.Axis.Y) {
                        continue;
                    }

                    {
                        MeshBuilder meshBuilder = renderer.meshBuilder();
                        QuadEmitter emitter = meshBuilder.getEmitter();

                        emitter.square(dir, 0, 0.5f, 1, 1, 0);
                        SpriteIdentifier spriteIdentifier = GlassSprites.getSlabSpriteIdentifier(patternIndex, positiveSlab);
                        emitter.spriteBake(textureGetter.apply(spriteIdentifier), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        positiveFaceMeshes.put(dir, meshBuilder.build());
                    }
                    {
                        MeshBuilder meshBuilder = renderer.meshBuilder();
                        QuadEmitter emitter = meshBuilder.getEmitter();

                        emitter.square(dir, 0, 0, 1, 0.5f, 0);
                        SpriteIdentifier spriteIdentifier = GlassSprites.getSlabSpriteIdentifier(patternIndex, negativeSlab);
                        emitter.spriteBake(textureGetter.apply(spriteIdentifier), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        negativeFaceMeshes.put(dir, meshBuilder.build());
                    }
                }

                positiveMeshMap.put(patternIndex, positiveFaceMeshes);
                negativeMeshMap.put(patternIndex, negativeFaceMeshes);
            }

            for (int patternIndex = 0; patternIndex < (isGlassPositive ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT); patternIndex++) {
                EnumMap<Direction, Mesh> endPositiveFaceMeshes = new EnumMap<>(Direction.class);

                for (Direction dir : Direction.values()) {
                    MeshBuilder meshBuilder = renderer.meshBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    if (dir == Direction.UP) {
                        emitter.square(dir, 0, 0, 1, 1, 0f);
                        emitter.spriteBake(textureGetter.apply(GlassSprites.getFullBlockSpriteIdentifier(patternIndex, positiveSlab)), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endPositiveFaceMeshes.put(dir, meshBuilder.build());
                    } else if (dir == Direction.DOWN) {
                        emitter.square(dir, 0, 0, 1, 1, 0.5f);
                        emitter.spriteBake(textureGetter.apply(GlassSprites.getFullBlockSpriteIdentifier(patternIndex, positiveSlab)), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endPositiveFaceMeshes.put(dir, meshBuilder.build());
                    }
                }

                endPositiveMeshMap.put(patternIndex, endPositiveFaceMeshes);
            }

            for (int patternIndex = 0; patternIndex < (isGlassNegative ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT); patternIndex++) {
                EnumMap<Direction, Mesh> endNegativeFaceMeshes = new EnumMap<>(Direction.class);

                for (Direction dir : Direction.values()) {
                    MeshBuilder meshBuilder = renderer.meshBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    if (dir == Direction.UP) {
                        emitter.square(dir, 0, 0, 1, 1, 0.5f);
                        emitter.spriteBake(textureGetter.apply(GlassSprites.getFullBlockSpriteIdentifier(patternIndex, negativeSlab)), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endNegativeFaceMeshes.put(dir, meshBuilder.build());
                    } else if (dir == Direction.DOWN) {
                        emitter.square(dir, 0, 0, 1, 1, 0f);
                        emitter.spriteBake(textureGetter.apply(GlassSprites.getFullBlockSpriteIdentifier(patternIndex, negativeSlab)), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endNegativeFaceMeshes.put(dir, meshBuilder.build());
                    }
                }

                endNegativeMeshMap.put(patternIndex, endNegativeFaceMeshes);
            }
        }

        if (this.positiveId != null) {
            UnbakedModel positiveUnbakedModel = baker.getOrLoadModel(this.positiveId);
            this.positiveBakedModel = positiveUnbakedModel.bake(baker, textureGetter, rotationContainer);
        }

        if (this.negativeId != null) {
            UnbakedModel negativeUnbakedModel = baker.getOrLoadModel(this.negativeId);
            this.negativeBakedModel = negativeUnbakedModel.bake(baker, textureGetter, rotationContainer);
        } else if (this.positiveId == null) {
            this.nullBakedModel = baker.getOrLoadModel(Identifier.of("minecraft:block/stone")).bake(baker, textureGetter, rotationContainer);
        }

        return this;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {
        NeighborState ns = new NeighborState(blockRenderView, blockPos, positiveSlab, negativeSlab, NeighborState.DoubleSlabType.DOUBLE_SLAB);

        if (this.positiveId != null) {
            for (Direction face : Direction.values()) {
                if (this.positiveSlab == null || shouldCullPositive(face, ns)) {
                    continue;
                }

                EnumMap<Direction, Mesh> faceMeshes;
                if (face == Direction.UP || face == Direction.DOWN) {
                    for (int index : getEndPatternIndexes(face, ns, true)) {
                        faceMeshes = endPositiveMeshMap.get(index);
                        if (faceMeshes == null) return;

                        Mesh mesh = faceMeshes.get(face);
                        if (mesh != null) {
                            mesh.outputTo(renderContext.getEmitter());
                        }
                    }
                    faceMeshes = endPositiveMeshMap.get((isGlassPositive ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT)-1);
                    if (faceMeshes == null) return;

                    Mesh mesh = faceMeshes.get(face);
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
                    }
                } else {
                    faceMeshes = positiveMeshMap.get(getSidePatternIndex(face, ns, true));
                    if (faceMeshes == null) return;

                    Mesh mesh = faceMeshes.get(face);
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

                EnumMap<Direction, Mesh> faceMeshes;
                if (face == Direction.UP || face == Direction.DOWN) {
                    for (int index : getEndPatternIndexes(face, ns, false)) {
                        faceMeshes = endNegativeMeshMap.get(index);
                        if (faceMeshes == null) return;

                        Mesh mesh = faceMeshes.get(face);
                        if (mesh != null) {
                            mesh.outputTo(renderContext.getEmitter());
                        }
                    }
                    faceMeshes = endNegativeMeshMap.get((isGlassNegative ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT)-1);
                    if (faceMeshes == null) return;

                    Mesh mesh = faceMeshes.get(face);
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
                    }
                } else {
                    faceMeshes = negativeMeshMap.get(getSidePatternIndex(face, ns, false));
                    if (faceMeshes == null) return;

                    Mesh mesh = faceMeshes.get(face);
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

    private List<Integer> determinePatternEndPositive(Direction face, NeighborState ns) {
        boolean topLeft = false;
        boolean topRight = false;
        boolean rightTop = false;
        boolean rightBottom = false;
        boolean bottomLeft = false;
        boolean bottomRight = false;
        boolean leftTop = false;
        boolean leftBottom = false;
        boolean cornerTopRight = false;
        boolean cornerBottomRight = false;
        boolean cornerBottomLeft = false;
        boolean cornerTopLeft = false;

        if (face == Direction.UP) {
            ContactType eastTypePositive = ns.getContactType(NeighborDirection.EAST, Half.POSITIVE);
            if (eastTypePositive == ContactType.FULL || eastTypePositive == ContactType.POSITIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (ns.getContactType(NeighborDirection.EAST, Half.POSITIVE) == ContactType.POSITIVE2) {
                rightBottom = true;
            } else if (ns.getContactType(NeighborDirection.EAST, Half.POSITIVE) == ContactType.NEGATIVE2) {
                rightTop = true;
            }
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            if (southTypePositive == ContactType.FULL || southTypePositive == ContactType.POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE) == ContactType.POSITIVE2) {
                bottomRight = true;
            } else if (ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE) == ContactType.NEGATIVE2) {
                bottomLeft = true;
            }
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            if (westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (ns.getContactType(NeighborDirection.WEST, Half.POSITIVE) == ContactType.POSITIVE2) {
                leftBottom = true;
            } else if (ns.getContactType(NeighborDirection.WEST, Half.POSITIVE) == ContactType.NEGATIVE2) {
                leftTop = true;
            }
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            if (northTypePositive == ContactType.FULL || northTypePositive == ContactType.POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE) == ContactType.POSITIVE2) {
                topRight = true;
            } else if (ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE) == ContactType.NEGATIVE2) {
                topLeft = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_EAST, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_EAST, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_WEST, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_WEST, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.DOWN) {
            ContactType eastTypePositive = ns.getContactType(NeighborDirection.EAST, Half.POSITIVE);
            if (eastTypePositive == ContactType.FULL || eastTypePositive == ContactType.POSITIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (ns.getContactType(NeighborDirection.EAST, Half.POSITIVE) == ContactType.POSITIVE2) {
                rightTop = true;
            } else if (ns.getContactType(NeighborDirection.EAST, Half.POSITIVE) == ContactType.NEGATIVE2) {
                rightBottom = true;
            }
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            if (southTypePositive == ContactType.FULL || southTypePositive == ContactType.POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE) == ContactType.POSITIVE2) {
                topRight = true;
            } else if (ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE) == ContactType.NEGATIVE2) {
                topLeft = true;
            }
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            if (westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (ns.getContactType(NeighborDirection.WEST, Half.POSITIVE) == ContactType.POSITIVE2) {
                leftTop = true;
            } else if (ns.getContactType(NeighborDirection.WEST, Half.POSITIVE) == ContactType.NEGATIVE2) {
                leftBottom = true;
            }
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            if (northTypePositive == ContactType.FULL || northTypePositive == ContactType.POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE) == ContactType.POSITIVE2) {
                bottomRight = true;
            } else if (ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE) == ContactType.NEGATIVE2) {
                bottomLeft = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_EAST, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_EAST, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_WEST, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_WEST, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        }

        return determineEndPatternIndexes(new GlassSprites.ConnectionFlags(
                topLeft,
                topRight,
                rightTop,
                rightBottom,
                bottomRight,
                bottomLeft,
                leftBottom,
                leftTop,
                cornerTopRight,
                cornerBottomRight,
                cornerBottomLeft,
                cornerTopLeft
        ), this.isGlassPositive);
    }

    private List<Integer> determinePatternEndNegative(Direction face, NeighborState ns) {
        boolean topLeft = false;
        boolean topRight = false;
        boolean rightTop = false;
        boolean rightBottom = false;
        boolean bottomLeft = false;
        boolean bottomRight = false;
        boolean leftTop = false;
        boolean leftBottom = false;
        boolean cornerTopRight = false;
        boolean cornerBottomRight = false;
        boolean cornerBottomLeft = false;
        boolean cornerTopLeft = false;

        if (face == Direction.UP) {
            ContactType eastTypeNegative = ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE);
            if (eastTypeNegative == ContactType.FULL || eastTypeNegative == ContactType.NEGATIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE) == ContactType.POSITIVE2) {
                rightBottom = true;
            } else if (ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE) == ContactType.NEGATIVE2) {
                rightTop = true;
            }
            ContactType southTypeNegative = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            if (southTypeNegative == ContactType.FULL || southTypeNegative == ContactType.NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE) == ContactType.POSITIVE2) {
                bottomRight = true;
            } else if (ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE) == ContactType.NEGATIVE2) {
                bottomLeft = true;
            }
            ContactType westTypeNegative = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            if (westTypeNegative == ContactType.FULL || westTypeNegative == ContactType.NEGATIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE) == ContactType.POSITIVE2) {
                leftBottom = true;
            } else if (ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE) == ContactType.NEGATIVE2) {
                leftTop = true;
            }
            ContactType northTypeNegative = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            if (northTypeNegative == ContactType.FULL || northTypeNegative == ContactType.NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE) == ContactType.POSITIVE2) {
                topRight = true;
            } else if (ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE) == ContactType.NEGATIVE2) {
                topLeft = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_EAST, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_EAST, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_WEST, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_WEST, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.DOWN) {
            ContactType eastTypeNegative = ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE);
            if (eastTypeNegative == ContactType.FULL || eastTypeNegative == ContactType.NEGATIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE) == ContactType.POSITIVE2) {
                rightTop = true;
            } else if (ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE) == ContactType.NEGATIVE2) {
                rightBottom = true;
            }
            ContactType southTypeNegative = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            if (southTypeNegative == ContactType.FULL || southTypeNegative == ContactType.NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE) == ContactType.POSITIVE2) {
                topRight = true;
            } else if (ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE) == ContactType.NEGATIVE2) {
                topLeft = true;
            }
            ContactType westTypeNegative = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            if (westTypeNegative == ContactType.FULL || westTypeNegative == ContactType.NEGATIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE) == ContactType.POSITIVE2) {
                leftTop = true;
            } else if (ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE) == ContactType.NEGATIVE2) {
                leftBottom = true;
            }
            ContactType northTypeNegative = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            if (northTypeNegative == ContactType.FULL || northTypeNegative == ContactType.NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE) == ContactType.POSITIVE2) {
                bottomRight = true;
            } else if (ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE) == ContactType.NEGATIVE2) {
                bottomLeft = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_EAST, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_EAST, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_WEST, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_WEST, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        }

        return determineEndPatternIndexes(new GlassSprites.ConnectionFlags(
                topLeft,
                topRight,
                rightTop,
                rightBottom,
                bottomRight,
                bottomLeft,
                leftBottom,
                leftTop,
                cornerTopRight,
                cornerBottomRight,
                cornerBottomLeft,
                cornerTopLeft
        ), this.isGlassNegative);
    }

    private int determinePatternPositive(Direction face, NeighborState ns) {
        boolean topLeft = false;
        boolean topRight = false;
        boolean right = false;
        boolean bottomLeft = false;
        boolean bottomRight = false;
        boolean left = false;
        boolean cornerTopRight = false;
        boolean cornerBottomRight = false;
        boolean cornerBottomLeft = false;
        boolean cornerTopLeft = false;

        ContactType upType = ns.getContactType(NeighborDirection.UP);
        if (face == Direction.EAST) {
            if (upType == ContactType.FULL || upType == ContactType.POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (upType == ContactType.POSITIVE2) {
                topLeft = true;
            } else if (upType == ContactType.NEGATIVE2) {
                topRight = true;
            }
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            left = southTypePositive == ContactType.FULL || southTypePositive == ContactType.POSITIVE1 || southTypePositive == ContactType.POSITIVE2;
            right = northTypePositive == ContactType.FULL || northTypePositive == ContactType.POSITIVE1 || northTypePositive == ContactType.POSITIVE2;
            if (ns.isSameSlab()) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                ContactType type = ns.getContactType(NeighborDirection.UP_NORTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (northTypePositive != ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (southTypePositive != ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.UP_SOUTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.SOUTH) {
            if (upType == ContactType.FULL || upType == ContactType.POSITIVE2) {
                topLeft = true;
                topRight = true;
            } else if (upType == ContactType.NEGATIVE1) {
                topLeft = true;
            } else if (upType == ContactType.POSITIVE1) {
                topRight = true;
            }
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            ContactType eastTypePositive = ns.getContactType(NeighborDirection.EAST, Half.POSITIVE);
            left = westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE1 || westTypePositive == ContactType.POSITIVE2;
            right = eastTypePositive == ContactType.FULL || eastTypePositive == ContactType.POSITIVE1 || eastTypePositive == ContactType.POSITIVE2;
            if (ns.isSameSlab()) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                ContactType type = ns.getContactType(NeighborDirection.UP_EAST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (eastTypePositive != ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (westTypePositive != ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.UP_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            if (upType == ContactType.FULL || upType == ContactType.NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (upType == ContactType.NEGATIVE2) {
                topLeft = true;
            } else if (upType == ContactType.POSITIVE2) {
                topRight = true;
            }
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            left = northTypePositive == ContactType.FULL || northTypePositive == ContactType.POSITIVE1 || northTypePositive == ContactType.NEGATIVE2;
            right = southTypePositive == ContactType.FULL || southTypePositive == ContactType.POSITIVE1 || southTypePositive == ContactType.NEGATIVE2;
            if (ns.isSameSlab()) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                ContactType type = ns.getContactType(NeighborDirection.UP_SOUTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (southTypePositive != ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (northTypePositive != ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.UP_NORTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.NORTH) {
            if (upType == ContactType.FULL || upType == ContactType.NEGATIVE2) {
                topLeft = true;
                topRight = true;
            } else if (upType == ContactType.POSITIVE1) {
                topLeft = true;
            } else if (upType == ContactType.NEGATIVE1) {
                topRight = true;
            }
            ContactType eastTypePositive = ns.getContactType(NeighborDirection.EAST, Half.POSITIVE);
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            left = eastTypePositive == ContactType.FULL || eastTypePositive == ContactType.POSITIVE1 || eastTypePositive == ContactType.NEGATIVE2;
            right = westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE1 || westTypePositive == ContactType.NEGATIVE2;
            if (ns.isSameSlab()) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                ContactType type = ns.getContactType(NeighborDirection.UP_WEST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (westTypePositive != ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (eastTypePositive != ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.UP_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        }

        return determineSlabSidePatternIndex(new GlassSprites.ConnectionFlags(
                topLeft,
                topRight,
                right,
                false,
                bottomRight,
                bottomLeft,
                false,
                left,
                cornerTopRight,
                cornerBottomRight,
                cornerBottomLeft,
                cornerTopLeft
        ));
    }

    private int determinePatternNegative(Direction face, NeighborState ns) {
        boolean topLeft = false;
        boolean topRight = false;
        boolean right = false;
        boolean bottomLeft = false;
        boolean bottomRight = false;
        boolean left = false;
        boolean cornerTopRight = false;
        boolean cornerBottomRight = false;
        boolean cornerBottomLeft = false;
        boolean cornerTopLeft = false;

        ContactType downType = ns.getContactType(NeighborDirection.DOWN);
        if (face == Direction.EAST) {
            if (ns.isSameSlab()) {
                topLeft = true;
                topRight = true;
            }
            ContactType southTypeNegative = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            ContactType northTypeNegative = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            left = southTypeNegative == ContactType.FULL || southTypeNegative == ContactType.NEGATIVE1 || southTypeNegative == ContactType.POSITIVE2;
            right = northTypeNegative == ContactType.FULL || northTypeNegative == ContactType.NEGATIVE1 || northTypeNegative == ContactType.POSITIVE2;
            if (downType == ContactType.FULL || downType == ContactType.POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downType == ContactType.POSITIVE2) {
                bottomLeft = true;
            } else if (downType == ContactType.NEGATIVE2) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (northTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                ContactType bottomNorthType = ns.getContactType(NeighborDirection.DOWN_NORTH);
                if (bottomNorthType == ContactType.FULL || bottomNorthType == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                ContactType bottomSouthType = ns.getContactType(NeighborDirection.DOWN_SOUTH);
                if (bottomSouthType == ContactType.FULL || bottomSouthType == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (southTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.SOUTH) {
            if (ns.isSameSlab()) {
                topLeft = true;
                topRight = true;
            }
            ContactType westTypeNegative = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            ContactType eastTypeNegative = ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE);
            left = westTypeNegative == ContactType.FULL || westTypeNegative == ContactType.NEGATIVE1 || westTypeNegative == ContactType.POSITIVE2;
            right = eastTypeNegative == ContactType.FULL || eastTypeNegative == ContactType.NEGATIVE1 || eastTypeNegative == ContactType.POSITIVE2;
            if (downType == ContactType.FULL || downType == ContactType.POSITIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downType == ContactType.NEGATIVE1) {
                bottomLeft = true;
            } else if (downType == ContactType.POSITIVE1) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (eastTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                ContactType bottomEastType = ns.getContactType(NeighborDirection.DOWN_EAST);
                if (bottomEastType == ContactType.FULL || bottomEastType == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                ContactType bottomWestType = ns.getContactType(NeighborDirection.DOWN_WEST);
                if (bottomWestType == ContactType.FULL || bottomWestType == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (westTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            if (ns.isSameSlab()) {
                topLeft = true;
                topRight = true;
            }
            ContactType northTypeNegative = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            ContactType southTypeNegative = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            left = northTypeNegative == ContactType.FULL || northTypeNegative == ContactType.NEGATIVE1 || northTypeNegative == ContactType.NEGATIVE2;
            right = southTypeNegative == ContactType.FULL || southTypeNegative == ContactType.NEGATIVE1 || southTypeNegative == ContactType.NEGATIVE2;
            if (downType == ContactType.FULL || downType == ContactType.NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downType == ContactType.NEGATIVE2) {
                bottomLeft = true;
            } else if (downType == ContactType.POSITIVE2) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (southTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                ContactType bottomSouthType = ns.getContactType(NeighborDirection.DOWN_SOUTH);
                if (bottomSouthType == ContactType.FULL || bottomSouthType == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                ContactType bottomNorthType = ns.getContactType(NeighborDirection.DOWN_NORTH);
                if (bottomNorthType == ContactType.FULL || bottomNorthType == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (northTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.NORTH) {
            if (ns.isSameSlab()) {
                topLeft = true;
                topRight = true;
            }
            ContactType eastTypeNegative = ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE);
            ContactType westTypeNegative = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            left = eastTypeNegative == ContactType.FULL || eastTypeNegative == ContactType.NEGATIVE1 || eastTypeNegative == ContactType.NEGATIVE2;
            right = westTypeNegative == ContactType.FULL || westTypeNegative == ContactType.NEGATIVE1 || westTypeNegative == ContactType.NEGATIVE2;
            if (downType == ContactType.FULL || downType == ContactType.NEGATIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downType == ContactType.POSITIVE1) {
                bottomLeft = true;
            } else if (downType == ContactType.NEGATIVE1) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (westTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                ContactType bottomWestType = ns.getContactType(NeighborDirection.DOWN_WEST);
                if (bottomWestType == ContactType.FULL || bottomWestType == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                ContactType bottomEastType = ns.getContactType(NeighborDirection.DOWN_EAST);
                if (bottomEastType == ContactType.FULL || bottomEastType == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (eastTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        }

        return determineSlabSidePatternIndex(new GlassSprites.ConnectionFlags(
                topLeft,
                topRight,
                right,
                false,
                bottomRight,
                bottomLeft,
                false,
                left,
                cornerTopRight,
                cornerBottomRight,
                cornerBottomLeft,
                cornerTopLeft
        ));
    }

    private boolean shouldCullPositive(Direction face, NeighborState ns) {
        if (face == Direction.UP) {
            return ns.getContactType(NeighborDirection.UP) == ContactType.FULL;
        } else if (face == Direction.DOWN) {
            return ns.isSameSlab();
        } else if (face == Direction.EAST) {
            ContactType type = ns.getContactType(NeighborDirection.EAST, Half.POSITIVE);
            return type == ContactType.FULL || type == ContactType.POSITIVE1;
        } else if (face == Direction.SOUTH) {
            ContactType type = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            return type == ContactType.FULL || type == ContactType.POSITIVE1;
        } else if (face == Direction.WEST) {
            ContactType type = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            return type == ContactType.FULL || type == ContactType.POSITIVE1;
        } else {
            ContactType type = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            return type == ContactType.FULL || type == ContactType.POSITIVE1;
        }
    }

    private boolean shouldCullNegative(Direction face, NeighborState ns) {
        if (face == Direction.UP) {
            return ns.isSameSlab();
        } else if (face == Direction.DOWN) {
            return ns.getContactType(NeighborDirection.DOWN) == ContactType.FULL;
        } else if (face == Direction.EAST) {
            ContactType type = ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE);
            return type == ContactType.FULL || type == ContactType.NEGATIVE1;
        } else if (face == Direction.SOUTH) {
            ContactType type = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            return type == ContactType.FULL || type == ContactType.NEGATIVE1;
        } else if (face == Direction.WEST) {
            ContactType type = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            return type == ContactType.FULL || type == ContactType.NEGATIVE1;
        } else {
            ContactType type = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            return type == ContactType.FULL || type == ContactType.NEGATIVE1;
        }
    }
}
