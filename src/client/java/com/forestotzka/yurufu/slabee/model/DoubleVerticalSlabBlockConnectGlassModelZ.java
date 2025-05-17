package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.block.ModBlockMap;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
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

import static com.forestotzka.yurufu.slabee.model.GlassSprites.*;
import static com.forestotzka.yurufu.slabee.model.NeighborState.*;

@Environment(EnvType.CLIENT)
public class DoubleVerticalSlabBlockConnectGlassModelZ implements UnbakedModel, BakedModel, FabricBakedModel {
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

    public DoubleVerticalSlabBlockConnectGlassModelZ(@Nullable Block positiveSlab, @Nullable Block negativeSlab) {
        this.positiveSlab = positiveSlab;
        this.negativeSlab = negativeSlab;

        if (this.positiveSlab == null) {
            this.positiveId = null;
            this.isGlassPositive = true;
        } else {
            Identifier positiveId = Registries.BLOCK.getId(this.positiveSlab);
            this.positiveId = Identifier.of(positiveId.getNamespace(), "block/" + positiveId.getPath() + "_z");
            this.isGlassPositive = positiveSlab == ModBlocks.GLASS_VERTICAL_SLAB;
        }

        if (this.negativeSlab == null) {
            this.negativeId = null;
            this.isGlassNegative = true;
        } else {
            Identifier negativeId = Registries.BLOCK.getId(this.negativeSlab);
            this.negativeId = Identifier.of(negativeId.getNamespace(), "block/" + negativeId.getPath() + "_z");
            this.isGlassNegative = negativeSlab == ModBlocks.GLASS_VERTICAL_SLAB;
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
                    if (dir.getAxis() == Direction.Axis.Z) {
                        continue;
                    }

                    {
                        MeshBuilder meshBuilder = renderer.meshBuilder();
                        QuadEmitter emitter = meshBuilder.getEmitter();

                        SpriteIdentifier spriteIdentifier = switch (dir) {
                            case EAST -> {
                                emitter.square(dir, 0, 0, 0.5f, 1, 0);
                                yield GlassSprites.getVerticalSlabSpriteIdentifier(patternIndex, positiveSlab);
                            }
                            case WEST -> {
                                emitter.square(dir, 0.5f, 0, 1, 1, 0);
                                yield GlassSprites.getVerticalSlabSpriteIdentifier(patternIndex, positiveSlab);
                            }
                            case UP -> {
                                emitter.square(dir, 0, 0, 1, 0.5f, 0);
                                yield GlassSprites.getSlabSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(positiveSlab));
                            }
                            default -> {
                                emitter.square(dir, 0, 0.5f, 1, 1, 0);
                                yield GlassSprites.getSlabSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(positiveSlab));
                            }
                        };

                        emitter.spriteBake(textureGetter.apply(spriteIdentifier), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        positiveFaceMeshes.put(dir, meshBuilder.build());
                    }
                    {
                        MeshBuilder meshBuilder = renderer.meshBuilder();
                        QuadEmitter emitter = meshBuilder.getEmitter();

                        SpriteIdentifier spriteIdentifier = switch (dir) {
                            case EAST -> {
                                emitter.square(dir, 0.5f, 0, 1, 1, 0);
                                yield GlassSprites.getVerticalSlabSpriteIdentifier(patternIndex, negativeSlab);
                            }
                            case WEST -> {
                                emitter.square(dir, 0, 0, 0.5f, 1, 0);
                                yield GlassSprites.getVerticalSlabSpriteIdentifier(patternIndex, negativeSlab);
                            }
                            case UP -> {
                                emitter.square(dir, 0, 0.5f, 1, 1, 0);
                                yield GlassSprites.getSlabSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(negativeSlab));
                            }
                            default -> {
                                emitter.square(dir, 0, 0, 1, 0.5f, 0);
                                yield GlassSprites.getSlabSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(negativeSlab));
                            }
                        };

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

                    if (dir == Direction.SOUTH) {
                        emitter.square(dir, 0, 0, 1, 1, 0f);
                        emitter.spriteBake(textureGetter.apply(GlassSprites.getFullBlockSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(positiveSlab))), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endPositiveFaceMeshes.put(dir, meshBuilder.build());
                    } else if (dir == Direction.NORTH) {
                        emitter.square(dir, 0, 0, 1, 1, 0.5f);
                        emitter.spriteBake(textureGetter.apply(GlassSprites.getFullBlockSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(positiveSlab))), MutableQuadView.BAKE_LOCK_UV);
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

                    if (dir == Direction.SOUTH) {
                        emitter.square(dir, 0, 0, 1, 1, 0.5f);
                        emitter.spriteBake(textureGetter.apply(GlassSprites.getFullBlockSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(negativeSlab))), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endNegativeFaceMeshes.put(dir, meshBuilder.build());
                    } else if (dir == Direction.NORTH) {
                        emitter.square(dir, 0, 0, 1, 1, 0f);
                        emitter.spriteBake(textureGetter.apply(GlassSprites.getFullBlockSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(negativeSlab))), MutableQuadView.BAKE_LOCK_UV);
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
        NeighborState ns = new NeighborState(blockRenderView, blockPos, positiveSlab, negativeSlab, DoubleSlabType.DOUBLE_VERTICAL_Z);

        if (this.positiveId != null) {
            for (Direction face : Direction.values()) {
                if (this.positiveSlab == null || shouldCullPositive(face, ns)) {
                    continue;
                }

                EnumMap<Direction, Mesh> faceMeshes;
                if (face.getAxis() == Direction.Axis.Z) {
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
                if (face.getAxis() == Direction.Axis.Z) {
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

        if (face == Direction.SOUTH) {
            ContactType eastTypePositive = ns.getContactType(NeighborDirection.EAST, Half.POSITIVE);
            if (eastTypePositive == ContactType.FULL || eastTypePositive == ContactType.POSITIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (eastTypePositive == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (eastTypePositive == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            ContactType upTypePositive = ns.getContactType(NeighborDirection.UP, Half.POSITIVE);
            if (upTypePositive == ContactType.FULL || upTypePositive == ContactType.POSITIVE2) {
                topLeft = true;
                topRight = true;
            } else if (upTypePositive == ContactType.POSITIVE1) {
                topRight = true;
            } else if (upTypePositive == ContactType.NEGATIVE1) {
                topLeft = true;
            }
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            if (westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (westTypePositive == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (westTypePositive == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            ContactType downTypePositive = ns.getContactType(NeighborDirection.DOWN, Half.POSITIVE);
            if (downTypePositive == ContactType.FULL || downTypePositive == ContactType.POSITIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downTypePositive == ContactType.POSITIVE1) {
                bottomRight = true;
            } else if (downTypePositive == ContactType.NEGATIVE1) {
                bottomLeft = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_EAST, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_EAST, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_WEST, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_WEST, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.NORTH) {
            ContactType eastTypePositive = ns.getContactType(NeighborDirection.EAST, Half.POSITIVE);
            if (eastTypePositive == ContactType.FULL || eastTypePositive == ContactType.POSITIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (eastTypePositive == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (eastTypePositive == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            ContactType upTypePositive = ns.getContactType(NeighborDirection.UP, Half.POSITIVE);
            if (upTypePositive == ContactType.FULL || upTypePositive == ContactType.POSITIVE2) {
                topLeft = true;
                topRight = true;
            } else if (upTypePositive == ContactType.POSITIVE1) {
                topLeft = true;
            } else if (upTypePositive == ContactType.NEGATIVE1) {
                topRight = true;
            }
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            if (westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (westTypePositive == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (westTypePositive == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            ContactType downTypePositive = ns.getContactType(NeighborDirection.DOWN, Half.POSITIVE);
            if (downTypePositive == ContactType.FULL || downTypePositive == ContactType.POSITIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downTypePositive == ContactType.POSITIVE1) {
                bottomLeft = true;
            } else if (downTypePositive == ContactType.NEGATIVE1) {
                bottomRight = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_WEST, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_WEST, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_EAST, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_EAST, Half.POSITIVE);
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

        if (face == Direction.SOUTH) {
            ContactType eastTypeNegative = ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE);
            if (eastTypeNegative == ContactType.FULL || eastTypeNegative == ContactType.NEGATIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (eastTypeNegative == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (eastTypeNegative == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            ContactType upTypeNegative = ns.getContactType(NeighborDirection.UP, Half.NEGATIVE);
            if (upTypeNegative == ContactType.FULL || upTypeNegative == ContactType.NEGATIVE2) {
                topLeft = true;
                topRight = true;
            } else if (upTypeNegative == ContactType.POSITIVE1) {
                topRight = true;
            } else if (upTypeNegative == ContactType.NEGATIVE1) {
                topLeft = true;
            }
            ContactType westTypeNegative = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            if (westTypeNegative == ContactType.FULL || westTypeNegative == ContactType.NEGATIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (westTypeNegative == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (westTypeNegative == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            ContactType downTypeNegative = ns.getContactType(NeighborDirection.DOWN, Half.NEGATIVE);
            if (downTypeNegative == ContactType.FULL || downTypeNegative == ContactType.NEGATIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downTypeNegative == ContactType.POSITIVE1) {
                bottomRight = true;
            } else if (downTypeNegative == ContactType.NEGATIVE1) {
                bottomLeft = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_EAST, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_EAST, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_WEST, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_WEST, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.NORTH) {
            ContactType eastTypeNegative = ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE);
            if (eastTypeNegative == ContactType.FULL || eastTypeNegative == ContactType.NEGATIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (eastTypeNegative == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (eastTypeNegative == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            ContactType upTypeNegative = ns.getContactType(NeighborDirection.UP, Half.NEGATIVE);
            if (upTypeNegative == ContactType.FULL || upTypeNegative == ContactType.NEGATIVE2) {
                topLeft = true;
                topRight = true;
            } else if (upTypeNegative == ContactType.POSITIVE1) {
                topLeft = true;
            } else if (upTypeNegative == ContactType.NEGATIVE1) {
                topRight = true;
            }
            ContactType westTypeNegative = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            if (westTypeNegative == ContactType.FULL || westTypeNegative == ContactType.NEGATIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (westTypeNegative == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (westTypeNegative == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            ContactType downTypeNegative = ns.getContactType(NeighborDirection.DOWN, Half.NEGATIVE);
            if (downTypeNegative == ContactType.FULL || downTypeNegative == ContactType.NEGATIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downTypeNegative == ContactType.POSITIVE1) {
                bottomLeft = true;
            } else if (downTypeNegative == ContactType.NEGATIVE1) {
                bottomRight = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_WEST, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_WEST, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_EAST, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_EAST, Half.NEGATIVE);
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

        ContactType southType = ns.getContactType(NeighborDirection.SOUTH);
        if (face == Direction.EAST) {
            if (southType == ContactType.FULL || southType == ContactType.POSITIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (southType == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (southType == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            ContactType upTypePositive = ns.getContactType(NeighborDirection.UP, Half.POSITIVE);
            ContactType downTypePositive = ns.getContactType(NeighborDirection.DOWN, Half.POSITIVE);
            topLeft = upTypePositive == ContactType.FULL || upTypePositive == ContactType.POSITIVE1 || upTypePositive == ContactType.POSITIVE2;
            bottomLeft = downTypePositive == ContactType.FULL || downTypePositive == ContactType.POSITIVE1 || downTypePositive == ContactType.POSITIVE2;
            topRight = topLeft;
            bottomRight = bottomLeft;
            if (ns.isSameSlab()) {
                rightTop = true;
                rightBottom = true;
            }
            if (leftTop && topLeft) {
                ContactType type = ns.getContactType(NeighborDirection.UP_SOUTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
            if (topRight && rightTop) {
                if (upTypePositive != ContactType.POSITIVE2) {
                    cornerTopRight = true;
                }
            }
            if (rightBottom && bottomRight) {
                if (downTypePositive != ContactType.POSITIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_SOUTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
        } else if (face == Direction.UP) {
            if (southType == ContactType.FULL || southType == ContactType.POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (southType == ContactType.NEGATIVE2) {
                bottomLeft = true;
            } else if (southType == ContactType.POSITIVE2) {
                bottomRight = true;
            }
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            ContactType eastTypePositive = ns.getContactType(NeighborDirection.EAST, Half.POSITIVE);
            leftBottom = westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE1 || westTypePositive == ContactType.POSITIVE2;
            rightBottom = eastTypePositive == ContactType.FULL || eastTypePositive == ContactType.POSITIVE1 || eastTypePositive == ContactType.POSITIVE2;
            leftTop = leftBottom;
            rightTop = rightBottom;
            if (ns.isSameSlab()) {
                topLeft = true;
                topRight = true;
            }
            if (topRight && rightTop) {
                if (eastTypePositive != ContactType.POSITIVE2) {
                    cornerTopRight = true;
                }

            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_EAST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (westTypePositive != ContactType.POSITIVE2) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            if (southType == ContactType.FULL || southType == ContactType.NEGATIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (southType == ContactType.NEGATIVE1) {
                rightBottom = true;
            } else if (southType == ContactType.POSITIVE1) {
                rightTop = true;
            }
            ContactType upTypePositive = ns.getContactType(NeighborDirection.UP, Half.POSITIVE);
            ContactType downTypePositive = ns.getContactType(NeighborDirection.DOWN, Half.POSITIVE);
            topRight = upTypePositive == ContactType.FULL || upTypePositive == ContactType.NEGATIVE1 || upTypePositive == ContactType.POSITIVE2;
            bottomRight = downTypePositive == ContactType.FULL || downTypePositive == ContactType.NEGATIVE1 || downTypePositive == ContactType.POSITIVE2;
            topLeft = topRight;
            bottomLeft = bottomRight;
            if (ns.isSameSlab()) {
                leftTop = true;
                leftBottom = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_SOUTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_SOUTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (downTypePositive != ContactType.POSITIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (upTypePositive != ContactType.POSITIVE2) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.DOWN) {
            if (southType == ContactType.FULL || southType == ContactType.NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (southType == ContactType.POSITIVE2) {
                topRight = true;
            } else if (southType == ContactType.NEGATIVE2) {
                topLeft = true;
            }
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            ContactType eastTypePositive = ns.getContactType(NeighborDirection.EAST, Half.POSITIVE);
            leftTop = westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE2 || westTypePositive == ContactType.NEGATIVE1;
            rightTop = eastTypePositive == ContactType.FULL || eastTypePositive == ContactType.POSITIVE2 || eastTypePositive == ContactType.NEGATIVE1;
            leftBottom = leftTop;
            rightBottom = rightTop;
            if (ns.isSameSlab()) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (eastTypePositive != ContactType.POSITIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (westTypePositive != ContactType.POSITIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_WEST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        }

        ConnectionFlags flags = new ConnectionFlags(
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
        );
        if (face.getAxis() == Direction.Axis.X) {
            return determineVerticalSlabSidePatternIndex(flags);
        } else {
            return determineSlabSidePatternIndex(flags);
        }
    }

    private int determinePatternNegative(Direction face, NeighborState ns) {
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

        ContactType northType = ns.getContactType(NeighborDirection.NORTH);
        if (face == Direction.EAST) {
            if (ns.isSameSlab()) {
                leftTop = true;
                leftBottom = true;
            }
            ContactType upTypeNegative = ns.getContactType(NeighborDirection.UP, Half.NEGATIVE);
            ContactType downTypeNegative = ns.getContactType(NeighborDirection.DOWN, Half.NEGATIVE);
            topRight = upTypeNegative == ContactType.FULL || upTypeNegative == ContactType.NEGATIVE2 || upTypeNegative == ContactType.POSITIVE1;
            bottomRight = downTypeNegative == ContactType.FULL || downTypeNegative == ContactType.NEGATIVE2 || downTypeNegative == ContactType.POSITIVE1;
            topLeft = topRight;
            bottomLeft = bottomRight;
            if (northType == ContactType.FULL || northType == ContactType.POSITIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (northType == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (northType == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_NORTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_NORTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (downTypeNegative != ContactType.NEGATIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (upTypeNegative != ContactType.NEGATIVE2) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.UP) {
            if (ns.isSameSlab()) {
                bottomLeft = true;
                bottomRight = true;
            }
            ContactType westTypeNegative = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            ContactType eastTypeNegative = ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE);
            leftTop = westTypeNegative == ContactType.FULL || westTypeNegative == ContactType.POSITIVE1 || westTypeNegative == ContactType.NEGATIVE2;
            rightTop = eastTypeNegative == ContactType.FULL || eastTypeNegative == ContactType.POSITIVE1 || eastTypeNegative == ContactType.NEGATIVE2;
            leftBottom = leftTop;
            rightBottom = rightTop;
            if (northType == ContactType.FULL || northType == ContactType.POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (northType == ContactType.NEGATIVE2) {
                topLeft = true;
            } else if (northType == ContactType.POSITIVE2) {
                topRight = true;
            }
            if (topRight && rightTop) {
                ContactType bottomEastType = ns.getContactType(NeighborDirection.NORTH_EAST);
                if (bottomEastType == ContactType.FULL || bottomEastType == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (eastTypeNegative != ContactType.NEGATIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (westTypeNegative != ContactType.NEGATIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType bottomWestType = ns.getContactType(NeighborDirection.NORTH_WEST);
                if (bottomWestType == ContactType.FULL || bottomWestType == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            if (ns.isSameSlab()) {
                rightTop = true;
                rightBottom = true;
            }
            ContactType upTypeNegative = ns.getContactType(NeighborDirection.UP, Half.NEGATIVE);
            ContactType downTypeNegative = ns.getContactType(NeighborDirection.DOWN, Half.NEGATIVE);
            topLeft = upTypeNegative == ContactType.FULL || upTypeNegative == ContactType.NEGATIVE1 || upTypeNegative == ContactType.NEGATIVE2;
            bottomLeft = downTypeNegative == ContactType.FULL || downTypeNegative == ContactType.NEGATIVE1 || downTypeNegative == ContactType.NEGATIVE2;
            topRight = topLeft;
            bottomRight = bottomLeft;
            if (northType == ContactType.FULL || northType == ContactType.NEGATIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (northType == ContactType.NEGATIVE1) {
                leftBottom = true;
            } else if (northType == ContactType.POSITIVE1) {
                leftTop = true;
            }
            if (topRight && rightTop) {
                if (upTypeNegative != ContactType.NEGATIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (downTypeNegative != ContactType.NEGATIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_NORTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_NORTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.DOWN) {
            if (ns.isSameSlab()) {
                topLeft = true;
                topRight = true;
            }
            ContactType eastTypeNegative = ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE);
            ContactType westTypeNegative = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            rightBottom = eastTypeNegative == ContactType.FULL || eastTypeNegative == ContactType.NEGATIVE1 || eastTypeNegative == ContactType.NEGATIVE2;
            leftBottom = westTypeNegative == ContactType.FULL || westTypeNegative == ContactType.NEGATIVE1 || westTypeNegative == ContactType.NEGATIVE2;
            rightTop = rightBottom;
            leftTop = leftBottom;
            if (northType == ContactType.FULL || northType == ContactType.NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (northType == ContactType.POSITIVE2) {
                bottomRight = true;
            } else if (northType == ContactType.NEGATIVE2) {
                bottomLeft = true;
            }
            if (topRight && rightTop) {
                if (eastTypeNegative != ContactType.NEGATIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_WEST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (westTypeNegative != ContactType.NEGATIVE2) {
                    cornerTopLeft = true;
                }
            }
        }

        ConnectionFlags flags = new ConnectionFlags(
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
        );
        if (face.getAxis() == Direction.Axis.X) {
            return determineVerticalSlabSidePatternIndex(flags);
        } else {
            return determineSlabSidePatternIndex(flags);
        }
    }

    private boolean shouldCullPositive(Direction face, NeighborState ns) {
        if (face == Direction.SOUTH) {
            return ns.getContactType(NeighborDirection.SOUTH) == ContactType.FULL;
        } else if (face == Direction.NORTH) {
            return ns.isSameSlab();
        } else if (face == Direction.EAST) {
            ContactType type = ns.getContactType(NeighborDirection.EAST, Half.POSITIVE);
            return type == ContactType.FULL || type == ContactType.POSITIVE2;
        } else if (face == Direction.UP) {
            ContactType type = ns.getContactType(NeighborDirection.UP, Half.POSITIVE);
            return type == ContactType.FULL || type == ContactType.POSITIVE2;
        } else if (face == Direction.WEST) {
            ContactType type = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            return type == ContactType.FULL || type == ContactType.POSITIVE2;
        } else {
            ContactType type = ns.getContactType(NeighborDirection.DOWN, Half.POSITIVE);
            return type == ContactType.FULL || type == ContactType.POSITIVE2;
        }
    }

    private boolean shouldCullNegative(Direction face, NeighborState ns) {
        if (face == Direction.SOUTH) {
            return ns.isSameSlab();
        } else if (face == Direction.NORTH) {
            return ns.getContactType(NeighborDirection.NORTH) == ContactType.FULL;
        } else if (face == Direction.EAST) {
            ContactType type = ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE);
            return type == ContactType.FULL || type == ContactType.NEGATIVE2;
        } else if (face == Direction.UP) {
            ContactType type = ns.getContactType(NeighborDirection.UP, Half.NEGATIVE);
            return type == ContactType.FULL || type == ContactType.NEGATIVE2;
        } else if (face == Direction.WEST) {
            ContactType type = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            return type == ContactType.FULL || type == ContactType.NEGATIVE2;
        } else {
            ContactType type = ns.getContactType(NeighborDirection.DOWN, Half.NEGATIVE);
            return type == ContactType.FULL || type == ContactType.NEGATIVE2;
        }
    }
}
