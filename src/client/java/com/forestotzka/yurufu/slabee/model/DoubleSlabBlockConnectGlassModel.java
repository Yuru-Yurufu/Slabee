package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.block.*;
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
public class DoubleSlabBlockConnectGlassModel extends AbstractDoubleSlabConnectGlassModel {
    public DoubleSlabBlockConnectGlassModel(@Nullable Block positiveSlab, @Nullable Block negativeSlab) {
        super(positiveSlab, negativeSlab);
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
    protected void emitSidePositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<SpriteIdentifier, Sprite> textureGetter) {
        Sprite sprite = textureGetter.apply(GlassSprites.getSlabSpriteIdentifier(patternIndex, positiveSlab));
        emitter.square(dir, 0, 0.5f, 1, 1, 0);

        int x = patternIndex % SLAB_COLS;
        int y = patternIndex / SLAB_COLS;

        setUV(emitter, x, x+1, y, y+0.5f, sprite);
    }

    @Override
    protected void emitSideNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<SpriteIdentifier, Sprite> textureGetter) {
        Sprite sprite = textureGetter.apply(GlassSprites.getSlabSpriteIdentifier(patternIndex, negativeSlab));
        emitter.square(dir, 0, 0, 1, 0.5f, 0);

        int x = patternIndex % SLAB_COLS;
        int y = patternIndex / SLAB_COLS;
        int z = 8;

        setUV(emitter, x, x+1, y+0.5f, y+1, sprite);
    }

    @Override
    protected SpriteIdentifier emitEndPositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex) {
        if (dir == Direction.UP) {
            emitter.square(dir, 0, 0, 1, 1, 0f);
        } else {
            emitter.square(dir, 0, 0, 1, 1, 0.5f);
        }

        return GlassSprites.getFullBlockSpriteIdentifier(patternIndex, positiveSlab);
    }

    @Override
    protected SpriteIdentifier emitEndNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex) {
        if (dir == Direction.UP) {
            emitter.square(dir, 0, 0, 1, 1, 0.5f);
        } else {
            emitter.square(dir, 0, 0, 1, 1, 0f);
        }

        return GlassSprites.getFullBlockSpriteIdentifier(patternIndex, negativeSlab);
    }

    @Override
    protected DoubleSlabType getDoubleSlabType() {
        return DoubleSlabType.DOUBLE_SLAB;
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

    @Override
    protected int determinePatternNegative(Direction face, NeighborState ns) {
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
                ContactType type = ns.getContactType(NeighborDirection.DOWN_NORTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_SOUTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
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
                ContactType type = ns.getContactType(NeighborDirection.DOWN_EAST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
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
                ContactType type = ns.getContactType(NeighborDirection.DOWN_SOUTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_NORTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
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
                ContactType type = ns.getContactType(NeighborDirection.DOWN_WEST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
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

    @Override
    protected boolean shouldCullPositive(Direction face, NeighborState ns) {
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

    @Override
    protected boolean shouldCullNegative(Direction face, NeighborState ns) {
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
