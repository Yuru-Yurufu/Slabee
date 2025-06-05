package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.block.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.block.Block;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

import static com.forestotzka.yurufu.slabee.model.GlassSprites.*;
import static com.forestotzka.yurufu.slabee.model.NeighborState.*;

@Environment(EnvType.CLIENT)
public class DoubleSlabBlockConnectGlassModel extends AbstractDoubleSlabConnectGlassModel {
    public DoubleSlabBlockConnectGlassModel(@Nullable Block positiveSlab, @Nullable Block negativeSlab) {
        super(positiveSlab, negativeSlab, 0);
    }

    @Override
    protected Identifier setPositiveId(Identifier id) {
        return Identifier.of(id.getNamespace(), "block/" + id.getPath() + "_top");
    }

    @Override
    protected Identifier setNegativeId(Identifier id) {
        return Identifier.of(id.getNamespace(), "block/" + id.getPath());
    }

    @Override
    protected boolean setIsGlass(Block block) {
        return block == ModBlocks.GLASS_SLAB;
    }

    @Override
    protected boolean isEndFace(Direction face) {
        return face.getAxis().isVertical();
    }

    @Override
    protected boolean isEndPositiveFace(Direction face) {
        return face == Direction.UP;
    }
    @Override
    protected boolean isEndNegativeFace(Direction face) {
        return face == Direction.DOWN;
    }

    @Override
    protected void emitSidePositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<SpriteIdentifier, Sprite> textureGetter) {
        Sprite sprite = textureGetter.apply(getSlabSpriteIdentifier(positiveSlab));
        emitter.square(dir, 0, 0.5f, 1, 1, 0);

        int x = patternIndex % SLAB_COLS;
        int y = patternIndex / SLAB_COLS;

        setUV(emitter, x, x+1, y, y+0.5f, sprite);
    }

    @Override
    protected void emitSideNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<SpriteIdentifier, Sprite> textureGetter) {
        Sprite sprite = textureGetter.apply(getSlabSpriteIdentifier(negativeSlab));
        emitter.square(dir, 0, 0, 1, 0.5f, 0);

        int x = patternIndex % SLAB_COLS;
        int y = patternIndex / SLAB_COLS;

        setUV(emitter, x, x+1, y+0.5f, y+1, sprite);
    }

    @Override
    protected SpriteIdentifier emitEndPositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex) {
        if (dir == Direction.UP) {
            emitter.square(dir, 0, 0, 1, 1, 0f);
        } else {
            emitter.square(dir, 0, 0, 1, 1, 0.5f);
        }

        return getFullBlockSpriteIdentifier(patternIndex, positiveSlab);
    }

    @Override
    protected SpriteIdentifier emitEndNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex) {
        if (dir == Direction.UP) {
            emitter.square(dir, 0, 0, 1, 1, 0.5f);
        } else {
            emitter.square(dir, 0, 0, 1, 1, 0f);
        }

        return getFullBlockSpriteIdentifier(patternIndex, negativeSlab);
    }

    private void emitSlabQuarterQuad(QuadEmitter emitter, Direction dir, int patternIndex, int quarterIndex, Function<SpriteIdentifier, Sprite> textureGetter) {
        Sprite sprite = textureGetter.apply(getSlabSpriteIdentifier(positiveSlab));

        emitQuarterQuad(emitter, dir, patternIndex, quarterIndex, sprite);
    }

    private void emitVerticalSlabQuarterQuad(QuadEmitter emitter, Direction dir, int patternIndex, int quarterIndex, Function<SpriteIdentifier, Sprite> textureGetter) {
        Sprite sprite = textureGetter.apply(getVerticalSlabSpriteIdentifier(ModBlockMap.slabToVerticalSlab(positiveSlab)));

        emitQuarterQuad(emitter, dir, patternIndex, quarterIndex, sprite);
    }

    @Override
    protected DoubleSlabType getDoubleSlabType() {
        return DoubleSlabType.DOUBLE_SLAB;
    }

    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        if (this.positiveId != null && QUARTER_MESHES[positiveVariantIndex][0][0][0][0] == null) {
            for (int patternIndex = 0; patternIndex < SLAB_PATTERN_COUNT; patternIndex++) {
                for (Direction dir : Direction.values()) {
                    for (int quarterIndex = 0; quarterIndex < QUARTER_COUNT; quarterIndex++) {
                        {
                            MeshBuilder meshBuilder = getBuilder();
                            QuadEmitter emitter = meshBuilder.getEmitter();

                            emitSlabQuarterQuad(emitter, dir, patternIndex, quarterIndex, textureGetter);

                            QUARTER_MESHES[positiveVariantIndex][patternIndex][dir.ordinal()][quarterIndex][0] = meshBuilder.build();
                        }
                        {
                            MeshBuilder meshBuilder = getBuilder();
                            QuadEmitter emitter = meshBuilder.getEmitter();

                            emitVerticalSlabQuarterQuad(emitter, dir, patternIndex, quarterIndex, textureGetter);

                            QUARTER_MESHES[positiveVariantIndex][patternIndex][dir.ordinal()][quarterIndex][1] = meshBuilder.build();
                        }
                    }
                }
            }
        }

        super.bake(baker,textureGetter, rotationContainer);

        return this;
    }

    @Override
    protected Mesh getSideMesh(Direction face, NeighborState ns, ContactType contactType, boolean isPositive) {
        int variantIndex = isPositive ? positiveVariantIndex : negativeVariantIndex;
        int patternIndex = getSidePatternIndex(face, ns, isPositive);

        switch (face) {
            case EAST, NORTH -> {
                switch (contactType) {
                    case POSITIVE2 -> {
                        return QUARTER_MESHES[variantIndex][patternIndex][face.ordinal()][isPositive ? 1 : 2][0];
                    }
                    case NEGATIVE2 -> {
                        return QUARTER_MESHES[variantIndex][patternIndex][face.ordinal()][isPositive ? 0 : 3][0];
                    }
                }
            }
            case WEST, SOUTH -> {
                switch (contactType) {
                    case POSITIVE2 -> {
                        return QUARTER_MESHES[variantIndex][patternIndex][face.ordinal()][isPositive ? 0 : 3][0];
                    }
                    case NEGATIVE2 -> {
                        return QUARTER_MESHES[variantIndex][patternIndex][face.ordinal()][isPositive ? 1 : 2][0];
                    }
                }
            }
        }

        return isPositive ? SIDE_POSITIVE_MESHES[axis][variantIndex][patternIndex][face.ordinal()] : SIDE_NEGATIVE_MESHES[axis][variantIndex][patternIndex][face.ordinal()];
    }

    @Override
    protected Mesh getHalfEndMeshPositive(NeighborState ns, ContactType contactType) {
        Direction face = Direction.UP;
        switch (contactType) {
            case POSITIVE1 -> {
                return SIDE_NEGATIVE_MESHES[1][positiveVariantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsNegativeX(face, ns, true, Half.POSITIVE)))][face.ordinal()];
            }
            case NEGATIVE1 -> {
                return SIDE_POSITIVE_MESHES[1][positiveVariantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsPositiveX(face, ns, true, Half.POSITIVE)))][face.ordinal()];
            }
            case POSITIVE2 -> {
                return SIDE_NEGATIVE_MESHES[2][positiveVariantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsNegativeZ(face, ns, true, Half.POSITIVE)))][face.ordinal()];
            }
            default -> {
                return SIDE_POSITIVE_MESHES[2][positiveVariantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsPositiveZ(face, ns, true, Half.POSITIVE)))][face.ordinal()];
            }
        }
    }

    @Override
    protected Mesh getHalfEndMeshNegative(NeighborState ns, ContactType contactType) {
        Direction face = Direction.DOWN;
        switch (contactType) {
            case POSITIVE1 -> {
                return SIDE_NEGATIVE_MESHES[1][negativeVariantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsNegativeX(face, ns, true, Half.NEGATIVE)))][face.ordinal()];
            }
            case NEGATIVE1 -> {
                return SIDE_POSITIVE_MESHES[1][negativeVariantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsPositiveX(face, ns, true, Half.NEGATIVE)))][face.ordinal()];
            }
            case POSITIVE2 -> {
                return SIDE_NEGATIVE_MESHES[2][negativeVariantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsNegativeZ(face, ns, true, Half.NEGATIVE)))][face.ordinal()];
            }
            default -> {
                return SIDE_POSITIVE_MESHES[2][negativeVariantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsPositiveZ(face, ns, true, Half.NEGATIVE)))][face.ordinal()];
            }
        }
    }

    @Override
    protected List<Integer> determinePatternEndPositive(Direction face, NeighborState ns) {
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
            } else if (eastTypePositive == ContactType.POSITIVE2) {
                rightBottom = true;
            } else if (eastTypePositive == ContactType.NEGATIVE2) {
                rightTop = true;
            }
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            if (southTypePositive == ContactType.FULL || southTypePositive == ContactType.POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (southTypePositive == ContactType.POSITIVE2) {
                bottomRight = true;
            } else if (southTypePositive == ContactType.NEGATIVE2) {
                bottomLeft = true;
            }
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            if (westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (westTypePositive == ContactType.POSITIVE2) {
                leftBottom = true;
            } else if (westTypePositive == ContactType.NEGATIVE2) {
                leftTop = true;
            }
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            if (northTypePositive == ContactType.FULL || northTypePositive == ContactType.POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (northTypePositive == ContactType.POSITIVE2) {
                topRight = true;
            } else if (northTypePositive == ContactType.NEGATIVE2) {
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
            } else if (eastTypePositive == ContactType.POSITIVE2) {
                rightTop = true;
            } else if (eastTypePositive == ContactType.NEGATIVE2) {
                rightBottom = true;
            }
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            if (southTypePositive == ContactType.FULL || southTypePositive == ContactType.POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (southTypePositive == ContactType.POSITIVE2) {
                topRight = true;
            } else if (southTypePositive == ContactType.NEGATIVE2) {
                topLeft = true;
            }
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            if (westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (westTypePositive == ContactType.POSITIVE2) {
                leftTop = true;
            } else if (westTypePositive == ContactType.NEGATIVE2) {
                leftBottom = true;
            }
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            if (northTypePositive == ContactType.FULL || northTypePositive == ContactType.POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (northTypePositive == ContactType.POSITIVE2) {
                bottomRight = true;
            } else if (northTypePositive == ContactType.NEGATIVE2) {
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
        ), this.isGlassPositive);
    }

    @Override
    protected List<Integer> determinePatternEndNegative(Direction face, NeighborState ns) {
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
            } else if (eastTypeNegative == ContactType.POSITIVE2) {
                rightBottom = true;
            } else if (eastTypeNegative == ContactType.NEGATIVE2) {
                rightTop = true;
            }
            ContactType southTypeNegative = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            if (southTypeNegative == ContactType.FULL || southTypeNegative == ContactType.NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (southTypeNegative == ContactType.POSITIVE2) {
                bottomRight = true;
            } else if (southTypeNegative == ContactType.NEGATIVE2) {
                bottomLeft = true;
            }
            ContactType westTypeNegative = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            if (westTypeNegative == ContactType.FULL || westTypeNegative == ContactType.NEGATIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (westTypeNegative == ContactType.POSITIVE2) {
                leftBottom = true;
            } else if (westTypeNegative == ContactType.NEGATIVE2) {
                leftTop = true;
            }
            ContactType northTypeNegative = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            if (northTypeNegative == ContactType.FULL || northTypeNegative == ContactType.NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (northTypeNegative == ContactType.POSITIVE2) {
                topRight = true;
            } else if (northTypeNegative == ContactType.NEGATIVE2) {
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
            } else if (eastTypeNegative == ContactType.POSITIVE2) {
                rightTop = true;
            } else if (eastTypeNegative == ContactType.NEGATIVE2) {
                rightBottom = true;
            }
            ContactType southTypeNegative = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            if (southTypeNegative == ContactType.FULL || southTypeNegative == ContactType.NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (southTypeNegative == ContactType.POSITIVE2) {
                topRight = true;
            } else if (southTypeNegative == ContactType.NEGATIVE2) {
                topLeft = true;
            }
            ContactType westTypeNegative = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            if (westTypeNegative == ContactType.FULL || westTypeNegative == ContactType.NEGATIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (westTypeNegative == ContactType.POSITIVE2) {
                leftTop = true;
            } else if (westTypeNegative == ContactType.NEGATIVE2) {
                leftBottom = true;
            }
            ContactType northTypeNegative = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            if (northTypeNegative == ContactType.FULL || northTypeNegative == ContactType.NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (northTypeNegative == ContactType.POSITIVE2) {
                bottomRight = true;
            } else if (northTypeNegative == ContactType.NEGATIVE2) {
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
        ), this.isGlassNegative);
    }

    @Override
    protected int determinePatternPositive(Direction face, NeighborState ns) {
        return determineSlabSidePatternIndex(getSideConnectionFlagsPositiveY(face, ns));
    }

    @Override
    protected int determinePatternNegative(Direction face, NeighborState ns) {
        return determineSlabSidePatternIndex(getSideConnectionFlagsNegativeY(face, ns));
    }

    @Override
    protected boolean shouldCullPositive(Direction face, NeighborState ns) {
        if (face == Direction.UP) {
            return ns.getContactType(NeighborDirection.UP, Half.POSITIVE) == ContactType.FULL;
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

    @Override
    protected boolean shouldCullNegative(Direction face, NeighborState ns) {
        if (face == Direction.UP) {
            return ns.isSameSlab();
        } else if (face == Direction.DOWN) {
            return ns.getContactType(NeighborDirection.DOWN, Half.NEGATIVE) == ContactType.FULL;
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

    @Override
    protected int getVariantIndex(Block block) {
        return super.getVariantIndex(ModBlockMap.slabToVerticalSlab(block));
    }
}
