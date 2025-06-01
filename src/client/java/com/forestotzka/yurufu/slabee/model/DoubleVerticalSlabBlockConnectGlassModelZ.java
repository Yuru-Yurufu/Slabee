package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.block.ModBlockMap;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.block.Block;
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
public class DoubleVerticalSlabBlockConnectGlassModelZ extends AbstractDoubleSlabConnectGlassModel {
    public DoubleVerticalSlabBlockConnectGlassModelZ(@Nullable Block positiveSlab, @Nullable Block negativeSlab) {
        super(positiveSlab, negativeSlab, 2);
    }

    @Override
    protected Identifier setPositiveId(Identifier id) {
        return Identifier.of(id.getNamespace(), "block/" + id.getPath() + "_z");
    }

    @Override
    protected Identifier setNegativeId(Identifier id) {
        return Identifier.of(id.getNamespace(), "block/" + id.getPath() + "_z");
    }

    @Override
    protected boolean setIsGlass(Block block) {
        return block == ModBlocks.GLASS_VERTICAL_SLAB;
    }

    @Override
    protected boolean isEndFace(Direction face) {
        return face.getAxis() == Direction.Axis.Z;
    }

    @Override
    protected boolean isEndPositiveFace(Direction face) {
        return face == Direction.SOUTH;
    }

    @Override
    protected boolean isEndNegativeFace(Direction face) {
        return face == Direction.NORTH;
    }

    @Override
    protected void emitSidePositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<SpriteIdentifier, Sprite> textureGetter) {
        Sprite sprite;
        int x = patternIndex % SLAB_COLS;
        int y = patternIndex / SLAB_COLS;

        float u0;
        float v0;
        float u1;
        float v1;

        switch (dir) {
            case EAST -> {
                sprite = textureGetter.apply(getVerticalSlabSpriteIdentifier(positiveSlab));
                emitter.square(dir, 0, 0, 0.5f, 1, 0);
                u0 = x;
                u1 = x + 0.5f;
                v0 = y;
                v1 = y + 1;
            }
            case WEST -> {
                sprite = textureGetter.apply(getVerticalSlabSpriteIdentifier(positiveSlab));
                emitter.square(dir, 0.5f, 0, 1, 1, 0);
                u0 = x + 0.5f;
                u1 = x + 1f;
                v0 = y;
                v1 = y + 1;
            }
            case UP -> {
                sprite = textureGetter.apply(getSlabSpriteIdentifier(ModBlockMap.verticalSlabToSlab(positiveSlab)));
                emitter.square(dir, 0, 0, 1, 0.5f, 0);
                u0 = x;
                u1 = x + 1f;
                v0 = y + 0.5f;
                v1 = y + 1f;
            }
            default -> {
                sprite = textureGetter.apply(getSlabSpriteIdentifier(ModBlockMap.verticalSlabToSlab(positiveSlab)));
                emitter.square(dir, 0, 0.5f, 1, 1, 0);
                u0 = x;
                u1 = x + 1f;
                v0 = y;
                v1 = y + 0.5f;
            }
        }

        setUV(emitter, u0, u1, v0, v1, sprite);
    }

    @Override
    protected void emitSideNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<SpriteIdentifier, Sprite> textureGetter) {
        Sprite sprite;
        int x = patternIndex % SLAB_COLS;
        int y = patternIndex / SLAB_COLS;

        float u0;
        float v0;
        float u1;
        float v1;

        switch (dir) {
            case EAST -> {
                sprite = textureGetter.apply(getVerticalSlabSpriteIdentifier(negativeSlab));
                emitter.square(dir, 0.5f, 0, 1, 1, 0);
                u0 = x + 0.5f;
                u1 = x + 1f;
                v0 = y;
                v1 = y + 1;
            }
            case WEST -> {
                sprite = textureGetter.apply(getVerticalSlabSpriteIdentifier(negativeSlab));
                emitter.square(dir, 0, 0, 0.5f, 1, 0);
                u0 = x;
                u1 = x + 0.5f;
                v0 = y;
                v1 = y + 1;
            }
            case UP -> {
                sprite = textureGetter.apply(getSlabSpriteIdentifier(ModBlockMap.verticalSlabToSlab(negativeSlab)));
                emitter.square(dir, 0, 0.5f, 1, 1, 0);
                u0 = x;
                u1 = x + 1f;
                v0 = y;
                v1 = y + 0.5f;
            }
            default -> {
                sprite = textureGetter.apply(getSlabSpriteIdentifier(ModBlockMap.verticalSlabToSlab(negativeSlab)));
                emitter.square(dir, 0, 0, 1, 0.5f, 0);
                u0 = x;
                u1 = x + 1f;
                v0 = y + 0.5f;
                v1 = y + 1f;
            }
        }

        setUV(emitter, u0, u1, v0, v1, sprite);
    }

    @Override
    protected SpriteIdentifier emitEndPositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex) {
        if (dir == Direction.SOUTH) {
            emitter.square(dir, 0, 0, 1, 1, 0f);
        } else {
            emitter.square(dir, 0, 0, 1, 1, 0.5f);
        }

        return getFullBlockSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(positiveSlab));
    }

    @Override
    protected SpriteIdentifier emitEndNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex) {
        if (dir == Direction.SOUTH) {
            emitter.square(dir, 0, 0, 1, 1, 0.5f);
        } else {
            emitter.square(dir, 0, 0, 1, 1, 0f);
        }

        return getFullBlockSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(negativeSlab));
    }

    @Override
    protected DoubleSlabType getDoubleSlabType() {
        return DoubleSlabType.DOUBLE_VERTICAL_Z;
    }

    @Override
    protected Mesh getHalfEndMeshPositive(NeighborState ns, ContactType contactType) {
        Direction face = Direction.SOUTH;
        switch (contactType) {
            case POSITIVE1 -> {
                return SIDE_NEGATIVE_MESHES[0][positiveVariantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsNegativeY(face, ns, true)))][face.ordinal()];
            }
            case NEGATIVE1 -> {
                return SIDE_POSITIVE_MESHES[0][positiveVariantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsPositiveY(face, ns, true)))][face.ordinal()];
            }
            case POSITIVE2 -> {
                return SIDE_NEGATIVE_MESHES[1][positiveVariantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsNegativeX(face, ns, true)))][face.ordinal()];
            }
            default -> {
                return SIDE_POSITIVE_MESHES[1][positiveVariantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsPositiveX(face, ns, true)))][face.ordinal()];
            }
        }
    }

    @Override
    protected Mesh getHalfEndMeshNegative(NeighborState ns, ContactType contactType) {
        Direction face = Direction.NORTH;
        switch (contactType) {
            case POSITIVE1 -> {
                return SIDE_NEGATIVE_MESHES[0][positiveVariantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsNegativeY(face, ns, true)))][face.ordinal()];
            }
            case NEGATIVE1 -> {
                return SIDE_POSITIVE_MESHES[0][positiveVariantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsPositiveY(face, ns, true)))][face.ordinal()];
            }
            case POSITIVE2 -> {
                return SIDE_NEGATIVE_MESHES[1][positiveVariantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsNegativeX(face, ns, true)))][face.ordinal()];
            }
            default -> {
                return SIDE_POSITIVE_MESHES[1][positiveVariantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsPositiveX(face, ns, true)))][face.ordinal()];
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
        if (face.getAxis() == Direction.Axis.X) {
            return determineVerticalSlabSidePatternIndex(getSideConnectionFlagsPositiveZ(face, ns));
        } else {
            return determineSlabSidePatternIndex(getSideConnectionFlagsPositiveZ(face, ns));
        }
    }

    @Override
    protected int determinePatternNegative(Direction face, NeighborState ns) {
        if (face.getAxis() == Direction.Axis.X) {
            return determineVerticalSlabSidePatternIndex(getSideConnectionFlagsNegativeZ(face, ns));
        } else {
            return determineSlabSidePatternIndex(getSideConnectionFlagsNegativeZ(face, ns));
        }
    }

    @Override
    protected boolean shouldCullPositive(Direction face, NeighborState ns) {
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

    @Override
    protected boolean shouldCullNegative(Direction face, NeighborState ns) {
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
