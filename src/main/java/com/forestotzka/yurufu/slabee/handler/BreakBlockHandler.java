package com.forestotzka.yurufu.slabee.handler;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.forestotzka.yurufu.slabee.block.*;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class BreakBlockHandler {
    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            if (blockEntity instanceof AbstractDoubleSlabBlockEntity abstractEntity) {
                boolean shouldDrop = !player.isCreative() && !player.isSpectator();
                double x = 0.0, y = 0.0, z = 0.0;

                if (player.isSneaking()) {
                    BlockState stayState;
                    BlockState brokenState;

                    if (blockEntity instanceof DoubleSlabBlockEntity entity) {
                        if (LookingPositionTracker.lookingAtUpperHalf) {
                            stayState = entity.getNegativeSlabState().with(SlabBlock.TYPE, SlabType.BOTTOM);
                            brokenState = entity.getPositiveSlabState();
                            y = 0.25;
                        } else {
                            stayState = entity.getPositiveSlabState().with(SlabBlock.TYPE, SlabType.TOP);
                            brokenState = entity.getNegativeSlabState();
                            y = -0.25;
                        }
                    } else if (blockEntity instanceof DoubleVerticalSlabBlockEntity entity) {
                        if (entity.isX()) {
                            if (LookingPositionTracker.lookingAtEasternHalf) {
                                stayState = entity.getNegativeSlabState().with(VerticalSlabBlock.FACING, Direction.WEST);
                                brokenState = entity.getPositiveSlabState();
                                x = 0.25;
                            } else {
                                stayState = entity.getPositiveSlabState().with(VerticalSlabBlock.FACING, Direction.EAST);
                                brokenState = entity.getNegativeSlabState();
                                x = -0.25;
                            }
                        } else {
                            if (LookingPositionTracker.lookingAtSouthernHalf) {
                                stayState = entity.getNegativeSlabState().with(VerticalSlabBlock.FACING, Direction.NORTH);
                                brokenState = entity.getPositiveSlabState();
                                z = 0.25;
                            } else {
                                stayState = entity.getPositiveSlabState().with(VerticalSlabBlock.FACING, Direction.SOUTH);
                                brokenState = entity.getNegativeSlabState();
                                z = -0.25;
                            }
                        }
                    } else {
                        return false;
                    }

                    if (shouldDrop) {
                        breakBlock(world, player, pos, x, y, z, brokenState);
                    }
                    world.syncWorldEvent(2001, pos, Block.getRawIdFromState(brokenState));

                    world.setBlockState(pos, stayState, 3);
                } else {
                    BlockState positiveState = abstractEntity.getPositiveSlabState();
                    BlockState negativeState = abstractEntity.getNegativeSlabState();

                    if (shouldDrop) {
                        breakBlock(world, player, pos, x, y, z, positiveState);
                        breakBlock(world, player, pos, x, y, z, negativeState);
                    }
                    world.syncWorldEvent(2001, pos, Block.getRawIdFromState(positiveState));
                    world.syncWorldEvent(2001, pos, Block.getRawIdFromState(negativeState));

                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                }

                return false;
            }

            return true;
        });
    }

    private static void breakBlock(World world, PlayerEntity player, BlockPos pos, double x, double y, double z, BlockState state) {
        dropStacks(state, world, pos, x, y, z, null, player, player.getMainHandStack());
        player.incrementStat(Stats.MINED.getOrCreateStat(state.getBlock()));
    }

    private static void dropStacks(BlockState state, World world, BlockPos pos, double x, double y, double z, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack tool) {
        if (world instanceof ServerWorld) {
            Block.getDroppedStacks(state, (ServerWorld)world, pos, blockEntity, entity, tool).forEach((stack) -> {
                dropStack(world, pos, x, y, z, stack);
            });
            state.onStacksDropped((ServerWorld)world, pos, tool, true);
        }
    }

    private static void dropStack(World world, BlockPos pos, double x, double y, double z, ItemStack stack) {
        double d = (double) EntityType.ITEM.getHeight() / 2.0;
        double xr = 0.25 - (x * 0.5);
        double yr = 0.25 - (y * 0.5);
        double zr = 0.25 - (z * 0.5);
        double e = (double)pos.getX() + 0.5 + x + MathHelper.nextDouble(world.random, -xr, xr);
        double f = (double)pos.getY() + 0.5 + y + MathHelper.nextDouble(world.random, -yr, yr) - d;
        double g = (double)pos.getZ() + 0.5 + z + MathHelper.nextDouble(world.random, -zr, zr);
        dropStack(world, new ItemEntity(world, e, f, g, stack), stack);
    }

    private static void dropStack(World world, ItemEntity itemEntity, ItemStack stack) {
        if (!world.isClient && !stack.isEmpty() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }
    }
}
