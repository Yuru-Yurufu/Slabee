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
public class DoubleVerticalSlabBlockConnectGlassModelZ extends AbstractDoubleSlabConnectGlassModel {
    public DoubleVerticalSlabBlockConnectGlassModelZ(@Nullable Block positiveSlab, @Nullable Block negativeSlab) {
        super(positiveSlab, negativeSlab);
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
    protected void emitSidePositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<SpriteIdentifier, Sprite> textureGetter) {
        Sprite sprite;

        int x = patternIndex % SLAB_COLS;
        int y = patternIndex / SLAB_COLS;
        int z = 8;
        switch (dir) {
            case EAST -> {
                sprite = textureGetter.apply(GlassSprites.getVerticalSlabSpriteIdentifier(patternIndex, positiveSlab));
                emitter.square(dir, 0, 0, 0.5f, 1, 0);
            }
            case WEST -> {
                sprite = textureGetter.apply(GlassSprites.getVerticalSlabSpriteIdentifier(patternIndex, positiveSlab));
                emitter.square(dir, 0.5f, 0, 1, 1, 0);
            }
            case UP -> {
                sprite = textureGetter.apply(GlassSprites.getSlabSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(positiveSlab)));
                emitter.square(dir, 0, 0, 1, 0.5f, 0);
            }
            default -> {
                sprite = textureGetter.apply(GlassSprites.getSlabSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(positiveSlab)));
                emitter.square(dir, 0, 0.5f, 1, 1, 0);
            }
        }

        float u0 = sprite.getFrameU(x * z);
        float v0 = sprite.getFrameV(y * z);
        float u1 = sprite.getFrameU((x + 1) * z);
        float v1 = sprite.getFrameV((y + 1) * z);

        setUV(emitter, u0, u1, v0, v1, sprite);
    }

    @Override
    protected void emitSideNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex, Function<SpriteIdentifier, Sprite> textureGetter) {
        Sprite sprite;
        switch (dir) {
            case EAST -> {
                sprite = textureGetter.apply(GlassSprites.getVerticalSlabSpriteIdentifier(patternIndex, negativeSlab));
                emitter.square(dir, 0.5f, 0, 1, 1, 0);
            }
            case WEST -> {
                sprite = textureGetter.apply(GlassSprites.getVerticalSlabSpriteIdentifier(patternIndex, negativeSlab));
                emitter.square(dir, 0, 0, 0.5f, 1, 0);
            }
            case UP -> {
                sprite = textureGetter.apply(GlassSprites.getSlabSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(negativeSlab)));
                emitter.square(dir, 0, 0.5f, 1, 1, 0);
            }
            default -> {
                sprite = textureGetter.apply(GlassSprites.getSlabSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(negativeSlab)));
                emitter.square(dir, 0, 0, 1, 0.5f, 0);
            }
        }

        int x = patternIndex % SLAB_COLS;
        int y = patternIndex / SLAB_COLS;
        int z = 8;

        float u0 = sprite.getFrameU(x * z);
        float v0 = sprite.getFrameV(y * z);
        float u1 = sprite.getFrameU((x + 1) * z);
        float v1 = sprite.getFrameV((y + 1) * z);

        setUV(emitter, u0, u1, v0, v1, sprite);
    }

    @Override
    protected SpriteIdentifier emitEndPositiveQuad(QuadEmitter emitter, Direction dir, int patternIndex) {
        if (dir == Direction.SOUTH) {
            emitter.square(dir, 0, 0, 1, 1, 0f);
        } else {
            emitter.square(dir, 0, 0, 1, 1, 0.5f);
        }

        return GlassSprites.getFullBlockSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(positiveSlab));
    }

    @Override
    protected SpriteIdentifier emitEndNegativeQuad(QuadEmitter emitter, Direction dir, int patternIndex) {
        if (dir == Direction.SOUTH) {
            emitter.square(dir, 0, 0, 1, 1, 0.5f);
        } else {
            emitter.square(dir, 0, 0, 1, 1, 0f);
        }

        return GlassSprites.getFullBlockSpriteIdentifier(patternIndex, ModBlockMap.verticalSlabToSlab(negativeSlab));
    }

    @Override
    protected DoubleSlabType getDoubleSlabType() {
        return DoubleSlabType.DOUBLE_VERTICAL_Z;
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
                ContactType type = ns.getContactType(NeighborDirection.NORTH_EAST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
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
                ContactType type = ns.getContactType(NeighborDirection.NORTH_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
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
