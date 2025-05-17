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

import java.util.EnumMap;

public class NeighborStateFull {
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

    private final EnumMap<NeighborDirection, ContactType> contactTypeMap;

    public NeighborStateFull(BlockRenderView world, BlockPos pos, Block block) {
        this.world = world;

        contactTypeMap = new EnumMap<>(NeighborDirection.class);
        for (NeighborDirection dir : NeighborDirection.values()) {
            BlockPos nPos = pos.add(dir.offset);
            ContactType contactType = mapToContactType(dir, getBlockInfo(nPos, block));
            contactTypeMap.put(dir, contactType);
        }
    }

    public ContactType getContactType(NeighborDirection direction) {
        return this.contactTypeMap.get(direction);
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
            if (ModBlockMap.slabToOriginal(otherBlock) == block) {
                SlabType type = otherState.get(SlabBlock.TYPE);

                if (type == SlabType.TOP) {
                    return new NeighborBlockInfo(NeighborBlockType.SLAB, Direction.UP);
                } else if (type == SlabType.BOTTOM) {
                    return new NeighborBlockInfo(NeighborBlockType.SLAB, Direction.DOWN);
                }
            }
        } else if (otherBlock instanceof VerticalSlabBlock) {
            if (ModBlockMap.verticalSlabToOriginal(otherBlock) == block) {
                Direction facing = otherState.get(VerticalSlabBlock.FACING);

                return new NeighborBlockInfo(NeighborBlockType.SLAB, facing);
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(pos) instanceof DoubleSlabBlockEntity entity) {
            Block b = ModBlockMap.originalToSlab(block);
            boolean bl1 = b == entity.getPositiveSlabState().getBlock();
            boolean bl2 = b == entity.getNegativeSlabState().getBlock();

            if (bl1 && bl2) {
                return new NeighborBlockInfo(NeighborBlockType.DOUBLE, Direction.UP);
            } else if (bl1) {
                return new NeighborBlockInfo(NeighborBlockType.SLAB, Direction.UP);
            } else if (bl2) {
                return new NeighborBlockInfo(NeighborBlockType.SLAB, Direction.DOWN);
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(pos) instanceof DoubleVerticalSlabBlockEntity entity) {
            Block b = ModBlockMap.originalToVerticalSlab(block);
            boolean bl1 = b == entity.getPositiveSlabState().getBlock();
            boolean bl2 = b == entity.getNegativeSlabState().getBlock();

            if (bl1 && bl2) {
                return new NeighborBlockInfo(NeighborBlockType.DOUBLE, entity.isX() ? Direction.EAST : Direction.SOUTH);
            } else if (bl1) {
                return new NeighborBlockInfo(NeighborBlockType.SLAB, entity.isX() ? Direction.EAST : Direction.SOUTH);
            } else if (bl2) {
                return new NeighborBlockInfo(NeighborBlockType.SLAB, entity.isX() ? Direction.WEST : Direction.NORTH);
            }
        } else {
            if (block == otherBlock) {
                return new NeighborBlockInfo(NeighborBlockType.DOUBLE, null);
            }
        }

        return new NeighborBlockInfo(NeighborBlockType.OTHER, null);
    }
}
