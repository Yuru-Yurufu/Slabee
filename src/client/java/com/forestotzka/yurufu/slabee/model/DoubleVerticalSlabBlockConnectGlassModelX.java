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
public class DoubleVerticalSlabBlockConnectGlassModelX extends AbstractDoubleSlabConnectGlassModel {
    public DoubleVerticalSlabBlockConnectGlassModelX(@Nullable Block positiveSlab, @Nullable Block negativeSlab) {
        super(positiveSlab, negativeSlab, 1);
    }

    @Override
    protected Identifier setPositiveId(Identifier id) {
        return Identifier.of(id.getNamespace(), "block/" + id.getPath() + "_x");
    }

    @Override
    protected Identifier setNegativeId(Identifier id) {
        return Identifier.of(id.getNamespace(), "block/" + id.getPath() + "_x");
    }

    @Override
    protected boolean setIsGlass(Block block) {
        return block == ModBlocks.GLASS_VERTICAL_SLAB;
    }

    @Override
    protected boolean isEndFace(Direction face) {
        return face.getAxis() == Direction.Axis.X;
    }

    @Override
    protected boolean isEndPositiveFace(Direction face) {
        return face == Direction.EAST;
    }

    @Override
    protected boolean isEndNegativeFace(Direction face) {
        return face == Direction.WEST;
    }

    @Override
    protected void emitSidePositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<SpriteIdentifier, Sprite> textureGetter) {
        Sprite sprite = textureGetter.apply(getVerticalSlabSpriteIdentifier(positiveSlab));
        int x = patternIndex % SLAB_COLS;
        int y = patternIndex / SLAB_COLS;

        float u0;
        float u1;

        if (dir == Direction.NORTH) {
            emitter.square(dir, 0, 0, 0.5f, 1, 0);
            u0 = x;
        } else {
            emitter.square(dir, 0.5f, 0, 1, 1, 0);
            u0 = x + 0.5f;
        }
        u1 = u0 + 0.5f;

        setUV(emitter, u0, u1, y, y+1, sprite);
    }

    @Override
    protected void emitSideNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<SpriteIdentifier, Sprite> textureGetter) {
        Sprite sprite = textureGetter.apply(getVerticalSlabSpriteIdentifier(negativeSlab));
        int x = patternIndex % SLAB_COLS;
        int y = patternIndex / SLAB_COLS;

        float u0;
        float u1;

        if (dir == Direction.NORTH) {
            emitter.square(dir, 0.5f, 0, 1, 1, 0);
            u0 = x + 0.5f;
        } else {
            emitter.square(dir, 0, 0, 0.5f, 1, 0);
            u0 = x;
        }
        u1 = u0 + 0.5f;

        setUV(emitter, u0, u1, y, y+1, sprite);
    }

    @Override
    protected SpriteIdentifier emitEndPositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex) {
        if (dir == Direction.EAST) {
            emitter.square(dir, 0, 0, 1, 1, 0f);
        } else {
            emitter.square(dir, 0, 0, 1, 1, 0.5f);
        }

        return getFullBlockSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(positiveSlab));
    }

    @Override
    protected SpriteIdentifier emitEndNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex) {
        if (dir == Direction.EAST) {
            emitter.square(dir, 0, 0, 1, 1, 0.5f);
        } else {
            emitter.square(dir, 0, 0, 1, 1, 0f);
        }

        return getFullBlockSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(negativeSlab));
    }

    @Override
    protected DoubleSlabType getDoubleSlabType() {
        return DoubleSlabType.DOUBLE_VERTICAL_X;
    }

    @Override
    protected Mesh getHalfEndMeshPositive(NeighborState ns, ContactType contactType) {
        Direction face = Direction.EAST;
        switch (contactType) {
            case POSITIVE1 -> {
                return SIDE_NEGATIVE_MESHES[0][positiveVariantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsNegativeY(face, ns, true)))][face.ordinal()];
            }
            case NEGATIVE1 -> {
                return SIDE_POSITIVE_MESHES[0][positiveVariantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsPositiveY(face, ns, true)))][face.ordinal()];
            }
            case POSITIVE2 -> {
                return SIDE_NEGATIVE_MESHES[2][positiveVariantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsNegativeZ(face, ns, true)))][face.ordinal()];
            }
            default -> {
                return SIDE_POSITIVE_MESHES[2][positiveVariantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsPositiveZ(face, ns, true)))][face.ordinal()];
            }
        }
    }

    @Override
    protected Mesh getHalfEndMeshNegative(NeighborState ns, ContactType contactType) {
        Direction face = Direction.WEST;
        switch (contactType) {
            case POSITIVE1 -> {
                return SIDE_NEGATIVE_MESHES[0][negativeVariantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsNegativeY(face, ns, true)))][face.ordinal()];
            }
            case NEGATIVE1 -> {
                return SIDE_POSITIVE_MESHES[0][negativeVariantIndex][getMappedIndex(determineSlabSidePatternIndex(getSideConnectionFlagsPositiveY(face, ns, true)))][face.ordinal()];
            }
            case POSITIVE2 -> {
                return SIDE_NEGATIVE_MESHES[2][negativeVariantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsNegativeZ(face, ns, true)))][face.ordinal()];
            }
            default -> {
                return SIDE_POSITIVE_MESHES[2][negativeVariantIndex][getMappedIndex(determineVerticalSlabSidePatternIndex(getSideConnectionFlagsPositiveZ(face, ns, true)))][face.ordinal()];
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

        if (face == Direction.EAST) {
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            if (southTypePositive == ContactType.FULL || southTypePositive == ContactType.POSITIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (southTypePositive == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (southTypePositive == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            ContactType upTypePositive = ns.getContactType(NeighborDirection.UP, Half.POSITIVE);
            if (upTypePositive == ContactType.FULL || upTypePositive == ContactType.POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (upTypePositive == ContactType.POSITIVE2) {
                topLeft = true;
            } else if (upTypePositive == ContactType.NEGATIVE2) {
                topRight = true;
            }
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            if (northTypePositive == ContactType.FULL || northTypePositive == ContactType.POSITIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (northTypePositive == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (northTypePositive == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            ContactType downTypePositive = ns.getContactType(NeighborDirection.DOWN, Half.POSITIVE);
            if (downTypePositive == ContactType.FULL || downTypePositive == ContactType.POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downTypePositive == ContactType.POSITIVE2) {
                bottomLeft = true;
            } else if (downTypePositive == ContactType.NEGATIVE2) {
                bottomRight = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_NORTH, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_NORTH, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_SOUTH, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_SOUTH, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            if (southTypePositive == ContactType.FULL || southTypePositive == ContactType.POSITIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (southTypePositive == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (southTypePositive == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            ContactType upTypePositive = ns.getContactType(NeighborDirection.UP, Half.POSITIVE);
            if (upTypePositive == ContactType.FULL || upTypePositive == ContactType.POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (upTypePositive == ContactType.POSITIVE2) {
                topLeft = true;
            } else if (upTypePositive == ContactType.NEGATIVE2) {
                topRight = true;
            }
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            if (northTypePositive == ContactType.FULL || northTypePositive == ContactType.POSITIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (northTypePositive == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (northTypePositive == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            ContactType downTypePositive = ns.getContactType(NeighborDirection.DOWN, Half.POSITIVE);
            if (downTypePositive == ContactType.FULL || downTypePositive == ContactType.POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downTypePositive == ContactType.POSITIVE2) {
                bottomRight = true;
            } else if (downTypePositive == ContactType.NEGATIVE2) {
                bottomLeft = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_SOUTH, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_SOUTH, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_NORTH, Half.POSITIVE);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_NORTH, Half.POSITIVE);
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

        if (face == Direction.EAST) {
            ContactType southTypeNegative = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            if (southTypeNegative == ContactType.FULL || southTypeNegative == ContactType.NEGATIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (southTypeNegative == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (southTypeNegative == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            ContactType upTypeNegative = ns.getContactType(NeighborDirection.UP, Half.NEGATIVE);
            if (upTypeNegative == ContactType.FULL || upTypeNegative == ContactType.NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (upTypeNegative == ContactType.POSITIVE2) {
                topLeft = true;
            } else if (upTypeNegative == ContactType.NEGATIVE2) {
                topRight = true;
            }
            ContactType northTypeNegative = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            if (northTypeNegative == ContactType.FULL || northTypeNegative == ContactType.NEGATIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (northTypeNegative == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (northTypeNegative == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            ContactType downTypeNegative = ns.getContactType(NeighborDirection.DOWN, Half.NEGATIVE);
            if (downTypeNegative == ContactType.FULL || downTypeNegative == ContactType.NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downTypeNegative == ContactType.POSITIVE2) {
                bottomLeft = true;
            } else if (downTypeNegative == ContactType.NEGATIVE2) {
                bottomRight = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_NORTH, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_NORTH, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_SOUTH, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_SOUTH, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            ContactType southTypeNegative = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            if (southTypeNegative == ContactType.FULL || southTypeNegative == ContactType.NEGATIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (southTypeNegative == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (southTypeNegative == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            ContactType upTypeNegative = ns.getContactType(NeighborDirection.UP, Half.NEGATIVE);
            if (upTypeNegative == ContactType.FULL || upTypeNegative == ContactType.NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (upTypeNegative == ContactType.POSITIVE2) {
                topRight = true;
            } else if (upTypeNegative == ContactType.NEGATIVE2) {
                topLeft = true;
            }
            ContactType northTypeNegative = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            if (northTypeNegative == ContactType.FULL || northTypeNegative == ContactType.NEGATIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (northTypeNegative == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (northTypeNegative == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            ContactType downTypeNegative = ns.getContactType(NeighborDirection.DOWN, Half.NEGATIVE);
            if (downTypeNegative == ContactType.FULL || downTypeNegative == ContactType.NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downTypeNegative == ContactType.POSITIVE2) {
                bottomRight = true;
            } else if (downTypeNegative == ContactType.NEGATIVE2) {
                bottomLeft = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_SOUTH, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_SOUTH, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_NORTH, Half.NEGATIVE);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_NORTH, Half.NEGATIVE);
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
        return determineVerticalSlabSidePatternIndex(getSideConnectionFlagsPositiveX(face, ns));
    }

    @Override
    protected int determinePatternNegative(Direction face, NeighborState ns) {
        return determineVerticalSlabSidePatternIndex(getSideConnectionFlagsNegativeX(face, ns));
    }

    @Override
    protected boolean shouldCullPositive(Direction face, NeighborState ns) {
        if (face == Direction.EAST) {
            return ns.getContactType(NeighborDirection.EAST) == ContactType.FULL;
        } else if (face == Direction.WEST) {
            return ns.isSameSlab();
        } else if (face == Direction.SOUTH) {
            ContactType type = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            return type == ContactType.FULL || type == ContactType.POSITIVE2;
        } else if (face == Direction.UP) {
            ContactType type = ns.getContactType(NeighborDirection.UP, Half.POSITIVE);
            return type == ContactType.FULL || type == ContactType.POSITIVE1;
        } else if (face == Direction.NORTH) {
            ContactType type = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            return type == ContactType.FULL || type == ContactType.POSITIVE2;
        } else {
            ContactType type = ns.getContactType(NeighborDirection.DOWN, Half.POSITIVE);
            return type == ContactType.FULL || type == ContactType.POSITIVE1;
        }
    }

    @Override
    protected boolean shouldCullNegative(Direction face, NeighborState ns) {
        if (face == Direction.EAST) {
            return ns.isSameSlab();
        } else if (face == Direction.WEST) {
            return ns.getContactType(NeighborDirection.WEST) == ContactType.FULL;
        } else if (face == Direction.SOUTH) {
            ContactType type = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            return type == ContactType.FULL || type == ContactType.NEGATIVE2;
        } else if (face == Direction.UP) {
            ContactType type = ns.getContactType(NeighborDirection.UP, Half.NEGATIVE);
            return type == ContactType.FULL || type == ContactType.NEGATIVE1;
        } else if (face == Direction.NORTH) {
            ContactType type = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            return type == ContactType.FULL || type == ContactType.NEGATIVE2;
        } else {
            ContactType type = ns.getContactType(NeighborDirection.DOWN, Half.NEGATIVE);
            return type == ContactType.FULL || type == ContactType.NEGATIVE1;
        }
    }
}
