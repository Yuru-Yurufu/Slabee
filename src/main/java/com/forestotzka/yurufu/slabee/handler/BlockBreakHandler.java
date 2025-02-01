package com.forestotzka.yurufu.slabee.handler;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlock;
import com.forestotzka.yurufu.slabee.block.DoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.VerticalSlabBlock;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.Direction;

public class BlockBreakHandler {
    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            if (player.isSneaking() && blockEntity != null && state.getBlock() instanceof AbstractDoubleSlabBlock) {
                BlockState stayState;

                if (blockEntity instanceof DoubleSlabBlockEntity entity) {
                    if (LookingPositionTracker.lookingAtUpperHalf) {
                        stayState = entity.getNegativeSlabState().with(SlabBlock.TYPE, SlabType.BOTTOM);
                    } else {
                        stayState = entity.getPositiveSlabState().with(SlabBlock.TYPE, SlabType.TOP);
                    }

                    world.setBlockState(pos, stayState, 3);
                } else if (blockEntity instanceof DoubleVerticalSlabBlockEntity entity) {
                    if (entity.isX()) {
                        if (LookingPositionTracker.lookingAtEasternHalf) {
                            stayState = entity.getNegativeSlabState().with(VerticalSlabBlock.FACING, Direction.WEST);
                        } else {
                            stayState = entity.getPositiveSlabState().with(VerticalSlabBlock.FACING, Direction.EAST);
                        }
                    } else {
                        if (LookingPositionTracker.lookingAtSouthernHalf) {
                            stayState = entity.getNegativeSlabState().with(VerticalSlabBlock.FACING, Direction.NORTH);
                        } else {
                            stayState = entity.getPositiveSlabState().with(VerticalSlabBlock.FACING, Direction.SOUTH);
                        }
                    }

                    world.setBlockState(pos, stayState, 3);
                }

                return false;
            }

            return true;
        });
    }
}
