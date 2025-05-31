package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class NeighborState {
    private final BlockRenderView world;

    public enum NeighborDirection {
        UP   ( 0,  1,  0),
        DOWN ( 0, -1,  0),
        EAST ( 1,  0,  0),
        SOUTH( 0,  0,  1),
        WEST (-1,  0,  0),
        NORTH( 0,  0, -1),

        UP_EAST   ( 1,   1,  0),
        UP_SOUTH  ( 0,   1,  1),
        UP_WEST   ( -1,  1,  0),
        UP_NORTH  ( 0,   1,  -1),
        DOWN_EAST ( 1,  -1,  0),
        DOWN_SOUTH( 0,  -1,  1),
        DOWN_WEST ( -1, -1,  0),
        DOWN_NORTH( 0,  -1, -1),
        SOUTH_EAST( 1,   0,  1),
        SOUTH_WEST(-1,   0,  1),
        NORTH_WEST(-1,   0, -1),
        NORTH_EAST( 1,   0, -1);

        public final Vec3i offset;
        public final int dx;
        public final int dy;
        public final int dz;

        NeighborDirection(int dx, int dy, int dz) {
            this.offset = new Vec3i(dx, dy, dz);
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
        }

        public int getX() {
            return this.dx;
        }
        public int getY() {
            return this.dy;
        }
        public int getZ() {
            return this.dz;
        }

        public boolean validFor(DoubleSlabType type) {
            if (type == DoubleSlabType.DOUBLE_SLAB) {
                return switch (this) {
                    case EAST, SOUTH, WEST, NORTH, SOUTH_EAST, SOUTH_WEST, NORTH_WEST, NORTH_EAST -> true;
                    default -> false;
                };
            } else if (type == DoubleSlabType.DOUBLE_VERTICAL_X) {
                return switch (this) {
                    case UP, DOWN, SOUTH, NORTH, UP_SOUTH, UP_NORTH, DOWN_SOUTH, DOWN_NORTH -> true;
                    default -> false;
                };
            } else {
                return switch (this) {
                    case UP, DOWN, EAST, WEST, UP_EAST, UP_WEST, DOWN_EAST, DOWN_WEST -> true;
                    default -> false;
                };
            }
        }

        private boolean isPositiveSide(DoubleSlabType type) {
            if (type == DoubleSlabType.DOUBLE_SLAB) {
                return switch (this) {
                    case UP, UP_EAST, UP_SOUTH, UP_WEST, UP_NORTH -> true;
                    default -> false;
                };
            } else if (type == DoubleSlabType.DOUBLE_VERTICAL_X) {
                return switch (this) {
                    case EAST, UP_EAST, DOWN_EAST, SOUTH_EAST, NORTH_EAST -> true;
                    default -> false;
                };
            } else {
                return switch (this) {
                    case SOUTH, UP_SOUTH, DOWN_SOUTH, SOUTH_EAST, SOUTH_WEST -> true;
                    default -> false;
                };
            }
        }
    }

    public enum Half {
        POSITIVE, NEGATIVE
    }

    /**
     * OTHER -> 処理に関係ない場合全般
     * <p>
     * FULL -> 接面/線が最大の時
     * <p>
     * 上記に当てはまらない場合は以下の4プロパティのいずれかになる
     * <p>
     * POSITIVE1 -> 優先度が高い方の正の方向にある場合
     * <p>
     * NEGATIVE1 -> 優先度が高い方の負の方向にある場合
     * <p>
     * POSITIVE2 -> 優先度が低い方の正の方向にある場合（線で接する場合は使われない）
     * <p>
     * NEGATIVE2 -> 優先度が低い方の負の方向にある場合（線で接する場合は使われない）
     * <p>
     * ※優先度はY方向>X方向>Z方向である
     */
    public enum ContactType {
        NONE, FULL, POSITIVE1, NEGATIVE1, POSITIVE2, NEGATIVE2
    }

    private enum NeighborBlockType {
        OTHER, SLAB, DOUBLE
    }

    private record NeighborBlockInfo(NeighborBlockType type, Direction direction) {}

    private final EnumMap<NeighborDirection, EnumMap<Half, ContactType>> contactTypeMap;

    public enum DoubleSlabType {
        DOUBLE_SLAB, DOUBLE_VERTICAL_X, DOUBLE_VERTICAL_Z
    }

    private final boolean isSameSlab;

    public NeighborState(BlockRenderView world, BlockPos pos, @Nullable Block positiveSlab, @Nullable Block negativeSlab, DoubleSlabType type) {
        this.world = world;
        isSameSlab = positiveSlab == negativeSlab;

        if (type != DoubleSlabType.DOUBLE_SLAB) {
            positiveSlab = ModBlockMap.verticalSlabToSlab(positiveSlab);
            negativeSlab = ModBlockMap.verticalSlabToSlab(negativeSlab);
        }

        contactTypeMap = new EnumMap<>(NeighborDirection.class);
        for (NeighborDirection dir : NeighborDirection.values()) {
            EnumMap<Half, ContactType> halfMap = new EnumMap<>(Half.class);
            BlockPos nPos = pos.add(dir.offset);
            if (isSameSlab) {
                ContactType contactType = mapToContactType(dir, getBlockInfo(nPos, positiveSlab));
                halfMap.put(Half.POSITIVE, contactType);
                halfMap.put(Half.NEGATIVE, contactType);
            } else {
                if (dir.validFor(type)) {
                    halfMap.put(Half.POSITIVE, mapToContactType(dir, getBlockInfo(nPos, positiveSlab)));
                    halfMap.put(Half.NEGATIVE, mapToContactType(dir, getBlockInfo(nPos, negativeSlab)));
                } else {
                    ContactType contactType;
                    if (dir.isPositiveSide(type)) {
                        contactType = mapToContactType(dir, getBlockInfo(nPos, positiveSlab));
                    } else {
                        contactType = mapToContactType(dir, getBlockInfo(nPos, negativeSlab));
                    }
                    halfMap.put(Half.POSITIVE, contactType);
                    halfMap.put(Half.NEGATIVE, contactType);
                }
            }

            contactTypeMap.put(dir, halfMap);
        }
    }

    public boolean isSameSlab() {
        return isSameSlab;
    }

    public static NeighborDirection asNeighborDirection(Direction direction) {
        return switch (direction) {
            case UP -> NeighborDirection.UP;
            case DOWN -> NeighborDirection.DOWN;
            case EAST -> NeighborDirection.EAST;
            case WEST -> NeighborDirection.WEST;
            case SOUTH -> NeighborDirection.SOUTH;
            case NORTH -> NeighborDirection.NORTH;
        };
    }

    public ContactType getContactType(NeighborDirection direction) {
        EnumMap<Half, ContactType> halfMap = this.contactTypeMap.get(direction);
        return halfMap.get(Half.POSITIVE);
    }

    public ContactType getContactType(NeighborDirection direction, Half half) {
        EnumMap<Half, ContactType> halfMap = this.contactTypeMap.get(direction);
        return halfMap.get(half);
    }

    private ContactType mapToContactType(NeighborDirection direction, NeighborBlockInfo info) {
        if (info.type() == NeighborBlockType.OTHER) {
            return ContactType.NONE;
        } else if (info.type() == NeighborBlockType.DOUBLE) {
            return ContactType.FULL;
        } else {
            int offsetX = direction.getX();
            int offsetY = direction.getY();
            int offsetZ = direction.getZ();
            return switch (info.direction()) {
                case UP -> switch (offsetY) {
                    case -1 -> ContactType.FULL;
                    case 0 -> ContactType.POSITIVE1;
                    default -> ContactType.NONE;
                };
                case DOWN -> switch (offsetY) {
                    case 1 -> ContactType.FULL;
                    case 0 -> ContactType.NEGATIVE1;
                    default -> ContactType.NONE;
                };
                case EAST -> switch (offsetX) {
                    case -1 -> ContactType.FULL;
                    case 0 -> offsetY == 0 ? ContactType.POSITIVE2 : ContactType.POSITIVE1;
                    default -> ContactType.NONE;
                };
                case SOUTH -> switch (offsetZ) {
                    case -1 -> ContactType.FULL;
                    case 0 -> (offsetX == 0 || offsetY == 0) ? ContactType.POSITIVE2 : ContactType.POSITIVE1;
                    default -> ContactType.NONE;
                };
                case WEST -> switch (offsetX) {
                    case 1 -> ContactType.FULL;
                    case 0 -> offsetY == 0 ? ContactType.NEGATIVE2 : ContactType.NEGATIVE1;
                    default -> ContactType.NONE;
                };
                case NORTH -> switch (offsetZ) {
                    case 1 -> ContactType.FULL;
                    case 0 -> (offsetX == 0 || offsetY == 0) ? ContactType.NEGATIVE2 : ContactType.NEGATIVE1;
                    default -> ContactType.NONE;
                };
            };
        }
    }

    private NeighborBlockInfo getBlockInfo(BlockPos pos, Block block) {
        BlockState otherState = world.getBlockState(pos);
        Block otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (otherBlock == block) {
                SlabType type = otherState.get(SlabBlock.TYPE);

                if (type == SlabType.TOP) {
                    return new NeighborBlockInfo(NeighborBlockType.SLAB, Direction.UP);
                } else if (type == SlabType.BOTTOM) {
                    return new NeighborBlockInfo(NeighborBlockType.SLAB, Direction.DOWN);
                }
            }
        } else if (otherBlock instanceof VerticalSlabBlock) {
            if (ModBlockMap.verticalSlabToSlab(otherBlock) == block) {
                Direction facing = otherState.get(VerticalSlabBlock.FACING);

                return new NeighborBlockInfo(NeighborBlockType.SLAB, facing);
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity entity) {
            boolean bl1 = block == entity.getPositiveSlabState().getBlock();
            boolean bl2 = block == entity.getNegativeSlabState().getBlock();

            if (bl1 && bl2) {
                return new NeighborBlockInfo(NeighborBlockType.DOUBLE, Direction.UP);
            } else if (bl1) {
                return new NeighborBlockInfo(NeighborBlockType.SLAB, Direction.UP);
            } else if (bl2) {
                return new NeighborBlockInfo(NeighborBlockType.SLAB, Direction.DOWN);
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(pos) instanceof DoubleVerticalSlabBlockEntity entity) {
            block = ModBlockMap.slabToVerticalSlab(block);
            boolean bl1 = block == entity.getPositiveSlabState().getBlock();
            boolean bl2 = block == entity.getNegativeSlabState().getBlock();

            if (bl1 && bl2) {
                return new NeighborBlockInfo(NeighborBlockType.DOUBLE, entity.isX() ? Direction.EAST : Direction.SOUTH);
            } else if (bl1) {
                return new NeighborBlockInfo(NeighborBlockType.SLAB, entity.isX() ? Direction.EAST : Direction.SOUTH);
            } else if (bl2) {
                return new NeighborBlockInfo(NeighborBlockType.SLAB, entity.isX() ? Direction.WEST : Direction.NORTH);
            }
        } else {
            if (ModBlockMap.slabToOriginal(block) == otherBlock) {
                return new NeighborBlockInfo(NeighborBlockType.DOUBLE, null);
            }
        }

        return new NeighborBlockInfo(NeighborBlockType.OTHER, null);
    }
}
