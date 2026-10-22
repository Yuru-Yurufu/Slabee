package com.forestotzka.yurufu.slabee.handler;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.forestotzka.yurufu.slabee.block.*;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.jetbrains.annotations.Nullable;

public class BreakBlockHandler {
    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            if (blockEntity instanceof AbstractDoubleSlabBlockEntity abstractEntity) {
                boolean shouldDrop = !player.isCreative() && !player.isSpectator();
                double x = 0.0, y = 0.0, z = 0.0;

                if (player.isShiftKeyDown()) {
                    BlockState stayState;
                    BlockState brokenState;

                    if (blockEntity instanceof DoubleSlabBlockEntity entity) {
                        if (LookingPositionTracker.lookingAtUpperHalf) {
                            stayState = entity.getNegativeSlabState().setValue(SlabBlock.TYPE, SlabType.BOTTOM);
                            brokenState = entity.getPositiveSlabState();
                            y = 0.25;
                        } else {
                            stayState = entity.getPositiveSlabState().setValue(SlabBlock.TYPE, SlabType.TOP);
                            brokenState = entity.getNegativeSlabState();
                            y = -0.25;
                        }
                    } else if (blockEntity instanceof DoubleVerticalSlabBlockEntity entity) {
                        if (entity.isX()) {
                            if (LookingPositionTracker.lookingAtEasternHalf) {
                                stayState = entity.getNegativeSlabState().setValue(VerticalSlabBlock.FACING, Direction.WEST);
                                brokenState = entity.getPositiveSlabState();
                                x = 0.25;
                            } else {
                                stayState = entity.getPositiveSlabState().setValue(VerticalSlabBlock.FACING, Direction.EAST);
                                brokenState = entity.getNegativeSlabState();
                                x = -0.25;
                            }
                        } else {
                            if (LookingPositionTracker.lookingAtSouthernHalf) {
                                stayState = entity.getNegativeSlabState().setValue(VerticalSlabBlock.FACING, Direction.NORTH);
                                brokenState = entity.getPositiveSlabState();
                                z = 0.25;
                            } else {
                                stayState = entity.getPositiveSlabState().setValue(VerticalSlabBlock.FACING, Direction.SOUTH);
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
                    world.levelEvent(2001, pos, Block.getId(brokenState));

                    if (stayState.getBlock() instanceof SnowySlabBlock) {
                        stayState = stayState.setValue(SnowySlabBlock.SNOWY, world.getBlockState(pos.above()).is(BlockTags.SNOW));
                    }

                    world.setBlock(pos, stayState, 3);
                } else {
                    BlockState positiveState = abstractEntity.getPositiveSlabState();
                    BlockState negativeState = abstractEntity.getNegativeSlabState();

                    if (shouldDrop) {
                        breakBlock(world, player, pos, x, y, z, positiveState);
                        breakBlock(world, player, pos, x, y, z, negativeState);
                    }

                    if (positiveState.getBlock() == negativeState.getBlock()) {
                        if (state.is(ModBlocks.DOUBLE_SLAB_BLOCK)) {
                            world.levelEvent(2001, pos, Block.getId(positiveState.setValue(SlabBlock.TYPE, SlabType.DOUBLE)));
                        } else if (state.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
                            world.levelEvent(2001, pos, Block.getId(positiveState.setValue(VerticalSlabBlock.IS_DOUBLE, true)));
                        } else {
                            return false;
                        }
                    } else {
                        world.levelEvent(2001, pos, Block.getId(positiveState));
                        world.levelEvent(2001, pos, Block.getId(negativeState));
                    }

                    world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                }

                return false;
            }

            return true;
        });
    }

    private static void breakBlock(Level world, Player player, BlockPos pos, double x, double y, double z, BlockState state) {
        dropStacks(state, world, pos, x, y, z, null, player, player.getMainHandItem());
        player.awardStat(Stats.BLOCK_MINED.get(state.getBlock()));
    }

    private static void dropStacks(BlockState state, Level world, BlockPos pos, double x, double y, double z, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack tool) {
        if (world instanceof ServerLevel) {
            Block.getDrops(state, (ServerLevel)world, pos, blockEntity, entity, tool).forEach((stack) -> {
                dropStack(world, pos, x, y, z, stack);
            });
            state.spawnAfterBreak((ServerLevel)world, pos, tool, true);
        }
    }

    private static void dropStack(Level world, BlockPos pos, double x, double y, double z, ItemStack stack) {
        double d = (double) EntityType.ITEM.getHeight() / 2.0;
        double xr = 0.25 - (x * 0.5);
        double yr = 0.25 - (y * 0.5);
        double zr = 0.25 - (z * 0.5);
        double e = (double)pos.getX() + 0.5 + x + Mth.nextDouble(world.random, -xr, xr);
        double f = (double)pos.getY() + 0.5 + y + Mth.nextDouble(world.random, -yr, yr) - d;
        double g = (double)pos.getZ() + 0.5 + z + Mth.nextDouble(world.random, -zr, zr);
        dropStack(world, new ItemEntity(world, e, f, g, stack), stack);
    }

    private static void dropStack(Level world, ItemEntity itemEntity, ItemStack stack) {
        if (!world.isClientSide && !stack.isEmpty() && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            itemEntity.setDefaultPickUpDelay();
            world.addFreshEntity(itemEntity);
        }
    }
}
