package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.block.ModBlockMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.model.*;
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

import static com.forestotzka.yurufu.slabee.model.GlassSprites.*;
import static com.forestotzka.yurufu.slabee.model.NeighborState.*;

@Environment(EnvType.CLIENT)
public class TranslucentBlockConnectGlassModel extends AbstractConnectGlassModel {
    private final Identifier id;
    private final Block block;

    private static final SpriteIdentifier nullSpriteIdentifier = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.ofVanilla("block/stone"));

    protected final boolean isGlass;

    protected final int variantIndex;

    public TranslucentBlockConnectGlassModel(Block block) {
        this.block = block;

        Identifier id = Registries.BLOCK.getId(block);
        this.id = Identifier.of(id.getNamespace(), "block/" + id.getPath());

        this.isGlass = block == Blocks.GLASS;

        this.variantIndex = getVariantIndex(this.block);
    }

    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        for (int patternIndex = 0; patternIndex < (isGlass ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT); patternIndex++) {
            for (Direction dir : Direction.values()) {
                MeshBuilder meshBuilder = getBuilder();
                QuadEmitter emitter = meshBuilder.getEmitter();

                emitter.square(dir, 0, 0, 1, 1, 0);
                emitter.spriteBake(textureGetter.apply(GlassSprites.getFullBlockSpriteIdentifier(patternIndex, ModBlockMap.originalToSlab(block))), MutableQuadView.BAKE_LOCK_UV);
                emitter.color(-1, -1, -1, -1);
                emitter.emit();

                switch (dir) {
                    case UP    -> END_MESHES[variantIndex][patternIndex][dir.ordinal()] = END_POSITIVE_MESHES[0][variantIndex][patternIndex][dir.ordinal()] = meshBuilder.build();
                    case DOWN  -> END_MESHES[variantIndex][patternIndex][dir.ordinal()] = END_NEGATIVE_MESHES[0][variantIndex][patternIndex][dir.ordinal()] = meshBuilder.build();
                    case EAST  -> END_MESHES[variantIndex][patternIndex][dir.ordinal()] = END_POSITIVE_MESHES[1][variantIndex][patternIndex][dir.ordinal()] = meshBuilder.build();
                    case WEST  -> END_MESHES[variantIndex][patternIndex][dir.ordinal()] = END_NEGATIVE_MESHES[1][variantIndex][patternIndex][dir.ordinal()] = meshBuilder.build();
                    case SOUTH -> END_MESHES[variantIndex][patternIndex][dir.ordinal()] = END_POSITIVE_MESHES[2][variantIndex][patternIndex][dir.ordinal()] = meshBuilder.build();
                    case NORTH -> END_MESHES[variantIndex][patternIndex][dir.ordinal()] = END_NEGATIVE_MESHES[2][variantIndex][patternIndex][dir.ordinal()] = meshBuilder.build();
                }
            }
        }

        if (this.id != null) {
            BakedModel bakedModel = baker.getOrLoadModel(this.id).bake(baker, textureGetter, rotationContainer);

            if (bakedModel != null) {
                this.particleSprite = bakedModel.getParticleSprite();
            } else {
                this.particleSprite = textureGetter.apply(nullSpriteIdentifier);
            }
        } else {
            this.particleSprite = textureGetter.apply(nullSpriteIdentifier);
        }

        return this;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {
        Block slab = ModBlockMap.originalToSlab(block);
        NeighborState ns = new NeighborState(blockRenderView, blockPos, slab, slab, DoubleSlabType.DOUBLE_SLAB);

        if (this.id != null) {
            for (Direction face : Direction.values()) {
                if (this.block == null || shouldCull(face, ns)) {
                    continue;
                }

                ContactType contactType = ns.getContactType(NeighborState.asNeighborDirection(face));
                if (contactType == ContactType.NONE) {
                    for (int index : getPatternIndexes(face, ns)) {
                        Mesh mesh = END_MESHES[variantIndex][index][face.ordinal()];
                        if (mesh != null) {
                            mesh.outputTo(renderContext.getEmitter());
                        }
                    }

                    Mesh mesh = END_MESHES[variantIndex][(isGlass ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT) - 1][face.ordinal()];
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
                    }
                } else {
                    /*Mesh mesh = getHalfEndMesh(ns, contactType, face);
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
                    }*/
                }
            }
        }
    }

    /*private Mesh getHalfEndMesh(NeighborState ns, ContactType contactType, Direction face) {
        switch (face.getAxis()) {
            case X -> {
                switch (contactType) {
                    case POSITIVE1 -> {
                        return SIDE_POSITIVE_MESHES[variantIndex][GlassSprites.getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsNegativeY(face, ns, true)))][face.ordinal()];
                    }
                    case NEGATIVE1 -> {
                        return SIDE_POSITIVE_MESHES[variantIndex][GlassSprites.getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsPositiveY(face, ns, true)))][face.ordinal()];
                    }
                    case POSITIVE2 -> {
                        return SIDE_POSITIVE_MESHES[variantIndex][GlassSprites.getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsNegativeZ(face, ns, true)))][face.ordinal()];
                    }
                    default -> {
                        return SIDE_POSITIVE_MESHES[variantIndex][GlassSprites.getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsPositiveZ(face, ns, true)))][face.ordinal()];
                    }
                }
            }
            case Y -> {
                switch (contactType) {
                    case POSITIVE1 -> {
                        return SIDE_POSITIVE_MESHES[variantIndex][GlassSprites.getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsNegativeX(face, ns, true)))][face.ordinal()];
                    }
                    case NEGATIVE1 -> {
                        return SIDE_POSITIVE_MESHES[variantIndex][GlassSprites.getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsPositiveX(face, ns, true)))][face.ordinal()];
                    }
                    case POSITIVE2 -> {
                        return SIDE_POSITIVE_MESHES[variantIndex][GlassSprites.getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsNegativeZ(face, ns, true)))][face.ordinal()];
                    }
                    default -> {
                        return SIDE_POSITIVE_MESHES[variantIndex][GlassSprites.getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsPositiveZ(face, ns, true)))][face.ordinal()];
                    }
                }
            }
            default -> {
                switch (contactType) {
                    case POSITIVE1 -> {
                        return SIDE_POSITIVE_MESHES[variantIndex][GlassSprites.getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsNegativeY(face, ns, true)))][face.ordinal()];
                    }
                    case NEGATIVE1 -> {
                        return SIDE_POSITIVE_MESHES[variantIndex][GlassSprites.getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsPositiveY(face, ns, true)))][face.ordinal()];
                    }
                    case POSITIVE2 -> {
                        return SIDE_POSITIVE_MESHES[variantIndex][GlassSprites.getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsNegativeX(face, ns, true)))][face.ordinal()];
                    }
                    default -> {
                        return SIDE_POSITIVE_MESHES[variantIndex][GlassSprites.getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsPositiveX(face, ns, true)))][face.ordinal()];
                    }
                }
            }
        }
    }*/

    private List<Integer> getPatternIndexes(Direction face, NeighborState ns) {
        List<Integer> indexes;
        indexes = determinePattern(face, ns);
        if (this.isGlass) indexes.replaceAll(i -> i - (i / 6));

        return indexes;
    }

    protected List<Integer> determinePattern(Direction face, NeighborState ns) {
        boolean topLeft = false;
        boolean topRight = false;
        boolean rightTop = false;
        boolean rightBottom = false;
        boolean bottomRight = false;
        boolean bottomLeft = false;
        boolean leftBottom = false;
        boolean leftTop = false;
        boolean cornerTopRight = false;
        boolean cornerBottomRight = false;
        boolean cornerBottomLeft = false;
        boolean cornerTopLeft = false;

        if (face == Direction.UP) {
            ContactType eastTypePositive = ns.getContactType(NeighborDirection.EAST);
            if (eastTypePositive == ContactType.FULL || eastTypePositive == ContactType.POSITIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (eastTypePositive == ContactType.POSITIVE2) {
                rightBottom = true;
            } else if (eastTypePositive == ContactType.NEGATIVE2) {
                rightTop = true;
            }
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH);
            if (southTypePositive == ContactType.FULL || southTypePositive == ContactType.POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (southTypePositive == ContactType.POSITIVE2) {
                bottomRight = true;
            } else if (southTypePositive == ContactType.NEGATIVE2) {
                bottomLeft = true;
            }
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST);
            if (westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (westTypePositive == ContactType.POSITIVE2) {
                leftBottom = true;
            } else if (westTypePositive == ContactType.NEGATIVE2) {
                leftTop = true;
            }
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH);
            if (northTypePositive == ContactType.FULL || northTypePositive == ContactType.POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (northTypePositive == ContactType.POSITIVE2) {
                topRight = true;
            } else if (northTypePositive == ContactType.NEGATIVE2) {
                topLeft = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_EAST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
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
                ContactType type = ns.getContactType(NeighborDirection.NORTH_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.DOWN) {
            ContactType eastTypePositive = ns.getContactType(NeighborDirection.EAST);
            if (eastTypePositive == ContactType.FULL || eastTypePositive == ContactType.NEGATIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (ns.getContactType(NeighborDirection.EAST) == ContactType.POSITIVE2) {
                rightTop = true;
            } else if (ns.getContactType(NeighborDirection.EAST) == ContactType.NEGATIVE2) {
                rightBottom = true;
            }
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH);
            if (southTypePositive == ContactType.FULL || southTypePositive == ContactType.NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (southTypePositive == ContactType.POSITIVE2) {
                topRight = true;
            } else if (southTypePositive == ContactType.NEGATIVE2) {
                topLeft = true;
            }
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST);
            if (westTypePositive == ContactType.FULL || westTypePositive == ContactType.NEGATIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (westTypePositive == ContactType.POSITIVE2) {
                leftTop = true;
            } else if (westTypePositive == ContactType.NEGATIVE2) {
                leftBottom = true;
            }
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH);
            if (northTypePositive == ContactType.FULL || northTypePositive == ContactType.NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (northTypePositive == ContactType.POSITIVE2) {
                bottomRight = true;
            } else if (northTypePositive == ContactType.NEGATIVE2) {
                bottomLeft = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
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
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_WEST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.EAST) {
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH);
            if (southTypePositive == ContactType.FULL || southTypePositive == ContactType.POSITIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (southTypePositive == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (southTypePositive == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            ContactType upTypePositive = ns.getContactType(NeighborDirection.UP);
            if (upTypePositive == ContactType.FULL || upTypePositive == ContactType.POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (upTypePositive == ContactType.POSITIVE2) {
                topLeft = true;
            } else if (upTypePositive == ContactType.NEGATIVE2) {
                topRight = true;
            }
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH);
            if (northTypePositive == ContactType.FULL || northTypePositive == ContactType.POSITIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (northTypePositive == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (northTypePositive == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            ContactType downTypePositive = ns.getContactType(NeighborDirection.DOWN);
            if (downTypePositive == ContactType.FULL || downTypePositive == ContactType.POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downTypePositive == ContactType.POSITIVE2) {
                bottomLeft = true;
            } else if (downTypePositive == ContactType.NEGATIVE2) {
                bottomRight = true;
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
                ContactType type = ns.getContactType(NeighborDirection.DOWN_SOUTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_SOUTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            ContactType southTypeNegative = ns.getContactType(NeighborDirection.SOUTH);
            if (southTypeNegative == ContactType.FULL || southTypeNegative == ContactType.NEGATIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (southTypeNegative == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (southTypeNegative == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            ContactType upTypeNegative = ns.getContactType(NeighborDirection.UP);
            if (upTypeNegative == ContactType.FULL || upTypeNegative == ContactType.NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (upTypeNegative == ContactType.POSITIVE2) {
                topRight = true;
            } else if (upTypeNegative == ContactType.NEGATIVE2) {
                topLeft = true;
            }
            ContactType northTypeNegative = ns.getContactType(NeighborDirection.NORTH);
            if (northTypeNegative == ContactType.FULL || northTypeNegative == ContactType.NEGATIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (northTypeNegative == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (northTypeNegative == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            ContactType downTypeNegative = ns.getContactType(NeighborDirection.DOWN);
            if (downTypeNegative == ContactType.FULL || downTypeNegative == ContactType.NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downTypeNegative == ContactType.POSITIVE2) {
                bottomRight = true;
            } else if (downTypeNegative == ContactType.NEGATIVE2) {
                bottomLeft = true;
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
        } else if (face == Direction.SOUTH) {
            ContactType eastTypePositive = ns.getContactType(NeighborDirection.EAST);
            if (eastTypePositive == ContactType.FULL || eastTypePositive == ContactType.POSITIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (eastTypePositive == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (eastTypePositive == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            ContactType upTypePositive = ns.getContactType(NeighborDirection.UP);
            if (upTypePositive == ContactType.FULL || upTypePositive == ContactType.POSITIVE2) {
                topLeft = true;
                topRight = true;
            } else if (upTypePositive == ContactType.POSITIVE1) {
                topRight = true;
            } else if (upTypePositive == ContactType.NEGATIVE1) {
                topLeft = true;
            }
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST);
            if (westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (westTypePositive == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (westTypePositive == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            ContactType downTypePositive = ns.getContactType(NeighborDirection.DOWN);
            if (downTypePositive == ContactType.FULL || downTypePositive == ContactType.POSITIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downTypePositive == ContactType.POSITIVE1) {
                bottomRight = true;
            } else if (downTypePositive == ContactType.NEGATIVE1) {
                bottomLeft = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_EAST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_EAST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.NORTH) {
            ContactType eastTypeNegative = ns.getContactType(NeighborDirection.EAST);
            if (eastTypeNegative == ContactType.FULL || eastTypeNegative == ContactType.NEGATIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (eastTypeNegative == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (eastTypeNegative == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            ContactType upTypeNegative = ns.getContactType(NeighborDirection.UP);
            if (upTypeNegative == ContactType.FULL || upTypeNegative == ContactType.NEGATIVE2) {
                topLeft = true;
                topRight = true;
            } else if (upTypeNegative == ContactType.POSITIVE1) {
                topLeft = true;
            } else if (upTypeNegative == ContactType.NEGATIVE1) {
                topRight = true;
            }
            ContactType westTypeNegative = ns.getContactType(NeighborDirection.WEST);
            if (westTypeNegative == ContactType.FULL || westTypeNegative == ContactType.NEGATIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (westTypeNegative == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (westTypeNegative == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            ContactType downTypeNegative = ns.getContactType(NeighborDirection.DOWN);
            if (downTypeNegative == ContactType.FULL || downTypeNegative == ContactType.NEGATIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downTypeNegative == ContactType.POSITIVE1) {
                bottomLeft = true;
            } else if (downTypeNegative == ContactType.NEGATIVE1) {
                bottomRight = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_WEST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_WEST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        }

        return determineEndPatternIndexes(new ConnectionFlags(
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
        ), this.isGlass);
    }

    protected boolean shouldCull(Direction face, NeighborState ns) {
        if (face == Direction.UP) {
            return ns.getContactType(NeighborDirection.UP) == ContactType.FULL;
        } else if (face == Direction.DOWN) {
            return ns.getContactType(NeighborDirection.DOWN) == ContactType.FULL;
        } else if (face == Direction.EAST) {
            return ns.getContactType(NeighborDirection.EAST) == ContactType.FULL;
        } else if (face == Direction.SOUTH) {
            return ns.getContactType(NeighborDirection.SOUTH) == ContactType.FULL;
        } else if (face == Direction.WEST) {
            return ns.getContactType(NeighborDirection.WEST) == ContactType.FULL;
        } else {
            return ns.getContactType(NeighborDirection.NORTH) == ContactType.FULL;
        }
    }

    protected int getVariantIndex(Block block) {
        return super.getVariantIndex(ModBlockMap.originalToVerticalSlab(block));
    }
}
