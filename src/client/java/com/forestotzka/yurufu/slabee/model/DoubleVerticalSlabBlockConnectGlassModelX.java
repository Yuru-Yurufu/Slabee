package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.block.ModBlockMap;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
        super(positiveSlab, negativeSlab);
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
    protected void emitSidePositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<SpriteIdentifier, Sprite> textureGetter) {
        Sprite sprite = textureGetter.apply(GlassSprites.getVerticalSlabSpriteIdentifier(positiveSlab));
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
        Sprite sprite = textureGetter.apply(GlassSprites.getVerticalSlabSpriteIdentifier(negativeSlab));
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

        return GlassSprites.getFullBlockSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(positiveSlab));
    }

    @Override
    protected SpriteIdentifier emitEndNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex) {
        if (dir == Direction.EAST) {
            emitter.square(dir, 0, 0, 1, 1, 0.5f);
        } else {
            emitter.square(dir, 0, 0, 1, 1, 0f);
        }

        return GlassSprites.getFullBlockSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(negativeSlab));
    }

    @Override
    protected DoubleSlabType getDoubleSlabType() {
        return DoubleSlabType.DOUBLE_VERTICAL_X;
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

    @Override
    protected int determinePatternPositive(Direction face, NeighborState ns) {
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

        ContactType eastType = ns.getContactType(NeighborDirection.EAST);
        if (face == Direction.SOUTH) {
            if (eastType == ContactType.FULL || eastType == ContactType.POSITIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (eastType == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (eastType == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            ContactType upTypePositive = ns.getContactType(NeighborDirection.UP, Half.POSITIVE);
            ContactType downTypePositive = ns.getContactType(NeighborDirection.DOWN, Half.POSITIVE);
            topRight = upTypePositive == ContactType.FULL || upTypePositive == ContactType.POSITIVE1 || upTypePositive == ContactType.POSITIVE2;
            bottomRight = downTypePositive == ContactType.FULL || downTypePositive == ContactType.POSITIVE1 || downTypePositive == ContactType.POSITIVE2;
            topLeft = topRight;
            bottomLeft = bottomRight;
            if (ns.isSameSlab()) {
                leftTop = true;
                leftBottom = true;
            }
            if (topLeft && leftTop) {
                if (upTypePositive != ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
            if (rightTop && topRight) {
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
            if (leftBottom && bottomLeft) {
                if (downTypePositive != ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
        } else if (face == Direction.UP) {
            if (eastType == ContactType.FULL || eastType == ContactType.POSITIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (eastType == ContactType.NEGATIVE2) {
                rightTop = true;
            } else if (eastType == ContactType.POSITIVE2) {
                rightBottom = true;
            }
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            topRight = northTypePositive == ContactType.FULL || northTypePositive == ContactType.POSITIVE1 || northTypePositive == ContactType.POSITIVE2;
            bottomRight = southTypePositive == ContactType.FULL || southTypePositive == ContactType.POSITIVE1 || southTypePositive == ContactType.POSITIVE2;
            topLeft = topRight;
            bottomLeft = bottomRight;
            if (ns.isSameSlab()) {
                leftTop = true;
                leftBottom = true;
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
                if (southTypePositive != ContactType.POSITIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (northTypePositive != ContactType.POSITIVE2) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.NORTH) {
            if (eastType == ContactType.FULL || eastType == ContactType.POSITIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (eastType == ContactType.NEGATIVE1) {
                leftBottom = true;
            } else if (eastType == ContactType.POSITIVE1) {
                leftTop = true;
            }
            ContactType upTypePositive = ns.getContactType(NeighborDirection.UP, Half.POSITIVE);
            ContactType downTypePositive = ns.getContactType(NeighborDirection.DOWN, Half.POSITIVE);
            topLeft = upTypePositive == ContactType.FULL || upTypePositive == ContactType.POSITIVE1 || upTypePositive == ContactType.NEGATIVE2;
            bottomLeft = downTypePositive == ContactType.FULL || downTypePositive == ContactType.POSITIVE1 || downTypePositive == ContactType.NEGATIVE2;
            topRight = topLeft;
            bottomRight = bottomLeft;
            if (ns.isSameSlab()) {
                rightTop = true;
                rightBottom = true;
            }
            if (topRight && rightTop) {
                if (upTypePositive != ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (downTypePositive != ContactType.POSITIVE1) {
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
        } else if (face == Direction.DOWN) {
            if (eastType == ContactType.FULL || eastType == ContactType.NEGATIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (eastType == ContactType.NEGATIVE2) {
                rightBottom = true;
            } else if (eastType == ContactType.POSITIVE2) {
                rightTop = true;
            }
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            bottomRight = northTypePositive == ContactType.FULL || northTypePositive == ContactType.NEGATIVE1 || northTypePositive == ContactType.POSITIVE2;
            topRight = southTypePositive == ContactType.FULL || southTypePositive == ContactType.NEGATIVE1 || southTypePositive == ContactType.POSITIVE2;
            bottomLeft = bottomRight;
            topLeft = topRight;
            if (ns.isSameSlab()) {
                leftTop = true;
                leftBottom = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_WEST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (northTypePositive != ContactType.POSITIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (southTypePositive != ContactType.POSITIVE2) {
                    cornerTopLeft = true;
                }
            }
        }

        return determineVerticalSlabSidePatternIndex(new GlassSprites.ConnectionFlags(
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
        ));
    }

    @Override
    protected int determinePatternNegative(Direction face, NeighborState ns) {
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

        ContactType westType = ns.getContactType(NeighborDirection.WEST);
        if (face == Direction.SOUTH) {
            if (ns.isSameSlab()) {
                rightTop = true;
                rightBottom = true;
            }
            ContactType upTypeNegative = ns.getContactType(NeighborDirection.UP, Half.NEGATIVE);
            ContactType downTypeNegative = ns.getContactType(NeighborDirection.DOWN, Half.NEGATIVE);
            topLeft = upTypeNegative == ContactType.FULL || upTypeNegative == ContactType.POSITIVE2 || upTypeNegative == ContactType.NEGATIVE1;
            bottomLeft = downTypeNegative == ContactType.FULL || downTypeNegative == ContactType.POSITIVE2 || downTypeNegative == ContactType.NEGATIVE1;
            topRight = topLeft;
            bottomRight = bottomLeft;
            if (westType == ContactType.FULL || westType == ContactType.POSITIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (westType == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (westType == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            if (topRight && rightTop) {
                if (upTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (downTypeNegative != ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.UP_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.UP) {
            if (ns.isSameSlab()) {
                rightTop = true;
                rightBottom = true;
            }
            ContactType northTypeNegative = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            ContactType southTypeNegative = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            topLeft = northTypeNegative == ContactType.FULL || northTypeNegative == ContactType.POSITIVE1 || northTypeNegative == ContactType.NEGATIVE2;
            bottomLeft = southTypeNegative == ContactType.FULL || southTypeNegative == ContactType.POSITIVE1 || southTypeNegative == ContactType.NEGATIVE2;
            topRight = topLeft;
            bottomRight = bottomLeft;
            if (westType == ContactType.FULL || westType == ContactType.POSITIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (westType == ContactType.NEGATIVE2) {
                leftTop = true;
            } else if (westType == ContactType.POSITIVE2) {
                leftBottom = true;
            }
            if (topRight && rightTop) {
                if (northTypeNegative != ContactType.NEGATIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (southTypeNegative != ContactType.NEGATIVE2) {
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
        } else if (face == Direction.NORTH) {
            if (ns.isSameSlab()) {
                leftTop = true;
                leftBottom = true;
            }
            ContactType upTypeNegative = ns.getContactType(NeighborDirection.UP, Half.NEGATIVE);
            ContactType downTypeNegative = ns.getContactType(NeighborDirection.DOWN, Half.NEGATIVE);
            topRight = upTypeNegative == ContactType.FULL || upTypeNegative == ContactType.NEGATIVE1 || upTypeNegative == ContactType.NEGATIVE2;
            bottomRight = downTypeNegative == ContactType.FULL || downTypeNegative == ContactType.NEGATIVE1 || downTypeNegative == ContactType.NEGATIVE2;
            topLeft = topRight;
            bottomLeft = bottomRight;
            if (westType == ContactType.FULL || westType == ContactType.NEGATIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (westType == ContactType.NEGATIVE1) {
                rightBottom = true;
            } else if (westType == ContactType.POSITIVE1) {
                rightTop = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_NORTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_NORTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
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
        } else if (face == Direction.DOWN) {
            if (ns.isSameSlab()) {
                rightTop = true;
                rightBottom = true;
            }
            ContactType northTypeNegative = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            ContactType southTypeNegative = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            bottomLeft = northTypeNegative == ContactType.FULL || northTypeNegative == ContactType.NEGATIVE1 || northTypeNegative == ContactType.NEGATIVE2;
            topLeft = southTypeNegative == ContactType.FULL || southTypeNegative == ContactType.NEGATIVE1 || southTypeNegative == ContactType.NEGATIVE2;
            bottomRight = bottomLeft;
            topRight = topLeft;
            if (westType == ContactType.FULL || westType == ContactType.POSITIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (westType == ContactType.NEGATIVE2) {
                leftBottom = true;
            } else if (westType == ContactType.POSITIVE2) {
                leftTop = true;
            }
            if (topRight && rightTop) {
                if (southTypeNegative != ContactType.NEGATIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_WEST);
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
                if (northTypeNegative != ContactType.NEGATIVE2) {
                    cornerTopLeft = true;
                }
            }
        }

        return determineVerticalSlabSidePatternIndex(new GlassSprites.ConnectionFlags(
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
        ));
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
