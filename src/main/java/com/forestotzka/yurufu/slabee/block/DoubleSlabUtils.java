package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.enums.DoubleSlabVariant;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import com.forestotzka.yurufu.slabee.registry.tag.ModBlockTags;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public class DoubleSlabUtils {
    private DoubleSlabUtils() {}

    public static boolean canPlace(ItemPlacementContext context, VoxelShape voxelShape) {
        BlockPos pos = context.getBlockPos();
        return voxelShape.isEmpty() || context.getWorld().doesNotIntersectEntities(null, voxelShape.offset(pos.getX(), pos.getY(), pos.getZ()));
    }

    public static boolean isPositiveSeeThrough(BlockState state) {
        if (!SlabeeUtils.isDoubleSlab(state)) {
            return false;
        }
        return isSeeThrough(state.get(AbstractDoubleSlabBlock.POSITIVE_SLAB));
    }

    public static boolean isNegativeSeeThrough(BlockState state) {
        if (!SlabeeUtils.isDoubleSlab(state)) {
            return false;
        }
        return isSeeThrough(state.get(AbstractDoubleSlabBlock.NEGATIVE_SLAB));
    }

    private static boolean isSeeThrough(DoubleSlabVariant variant) {
        return variant != DoubleSlabVariant.NORMAL;
    }

    public static boolean isPositiveOpaque(BlockState state) {
        if (!SlabeeUtils.isDoubleSlab(state)) {
            return false;
        }
        return isOpaque(state.get(AbstractDoubleSlabBlock.POSITIVE_SLAB));
    }

    public static boolean isNegativeOpaque(BlockState state) {
        if (!SlabeeUtils.isDoubleSlab(state)) {
            return false;
        }
        return isOpaque(state.get(AbstractDoubleSlabBlock.NEGATIVE_SLAB));
    }

    private static boolean isOpaque(DoubleSlabVariant variant) {
        return variant == DoubleSlabVariant.NORMAL || variant == DoubleSlabVariant.TINTED_GLASS;
    }

    public static boolean isTrueSlabId(Identifier id) {
        return id != null && Registries.BLOCK.containsId(id);
    }

    public static int getLuminance(BlockState state) {
        if (state.isOf(ModBlocks.GLOWSTONE_SLAB) || state.isOf(ModBlocks.GLOWSTONE_VERTICAL_SLAB)) {
            return 15;
        } else if (state.isOf(ModBlocks.MAGMA_BLOCK_SLAB) || state.isOf(ModBlocks.MAGMA_BLOCK_VERTICAL_SLAB)) {
            return 3;
        } else if (state.isOf(ModBlocks.CRYING_OBSIDIAN_SLAB) || state.isOf(ModBlocks.CRYING_OBSIDIAN_VERTICAL_SLAB)) {
            return 1;
        } else {
            return 0;
        }
    }

    public static float getMiningSpeed(BlockState positiveSlab, BlockState negativeSlab, PlayerEntity player, BlockView world, BlockPos pos) {
        float hardness = getHardness(positiveSlab, negativeSlab, world, pos);
        return hardness == -1.0F ? 0.0F : getBlockBreakingSpeed(positiveSlab, negativeSlab, player) / hardness / getHarvest(positiveSlab, negativeSlab, player);
    }

    private static float getHardness(BlockState positiveSlab, BlockState negativeSlab, BlockView world, BlockPos pos) {
        float positiveHardness = positiveSlab.getHardness(world, pos);
        float negativeHardness = negativeSlab.getHardness(world, pos);
        if (positiveHardness == -1.0F || negativeHardness == -1.0F) {
            return -1.0F;
        }
        return (positiveHardness + negativeHardness) / 2.0F;
    }

    private static float getBlockBreakingSpeed(BlockState positiveSlab, BlockState negativeSlab, PlayerEntity player) {
        return (player.getBlockBreakingSpeed(positiveSlab) + player.getBlockBreakingSpeed(negativeSlab)) / 2.0F;
    }

    private static float getHarvest(BlockState positiveSlab, BlockState negativeSlab, PlayerEntity player) {
        float harvest = 30;
        boolean positiveCanHarvest = canHarvest(positiveSlab, player);
        boolean negativeCanHarvest = canHarvest(negativeSlab, player);
        if (!positiveCanHarvest && !negativeCanHarvest) {
            harvest = 100;
        } else if (!positiveCanHarvest || !negativeCanHarvest) {
            harvest = 50;
        }
        return harvest;
    }

    private static boolean canHarvest(BlockState state, PlayerEntity player) {
        ItemStack mainhandItem = player.getInventory().getMainHandStack();
        if (state.isIn(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return (mainhandItem.isOf(Items.DIAMOND_PICKAXE) || mainhandItem.isOf(Items.NETHERITE_PICKAXE));
        }
        return (player.canHarvest(state)) || (mainhandItem.isOf(Items.SHEARS) && state.isIn(ModBlockTags.MINEABLE_SHEARS));
    }

    public static void toDirt(ServerWorld world, BlockState state, BlockPos pos1, BlockState sourceState) {
        if (state.isOf(Blocks.DIRT) && canSurviveSpreadableBlock(state, world, pos1)) {
            world.setBlockState(pos1, getSpreadableBlock(world, pos1, state, sourceState));
        } else if (state.isOf(ModBlocks.DIRT_SLAB) && canSurviveSpreadableSlabBlock(state, world, pos1)) {
            world.setBlockState(pos1, getSpreadableSlabBlock(world, pos1, state, sourceState));
        } else if (state.isOf(ModBlocks.DIRT_VERTICAL_SLAB) && canSurviveSpreadableVerticalSlabBlock(state, world, pos1)) {
            world.setBlockState(pos1, getSpreadableVerticalSlabBlock(world, pos1, state, sourceState));
        } else if (state.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(pos1) instanceof DoubleSlabBlockEntity entity) {
            if (entity.getPositiveSlabState().isOf(ModBlocks.DIRT_SLAB) && canSurviveSpreadableBlock(state, world, pos1)) {
                entity.requestConversion(AbstractDoubleSlabBlockEntity.Conversion.TO_DIRT, true, 1, 1);
            }
        } else if (state.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(pos1) instanceof DoubleVerticalSlabBlockEntity entity) {
            BlockState positiveState = entity.getPositiveSlabState();
            BlockState negativeState = entity.getNegativeSlabState();
            boolean bl1 = positiveState.isOf(ModBlocks.DIRT_VERTICAL_SLAB);
            boolean bl2 = negativeState.isOf(ModBlocks.DIRT_VERTICAL_SLAB);

            if (bl1 && bl2) {
                if (canSurviveSpreadableBlock(state, world, pos1)) {
                    entity.requestConversion(AbstractDoubleSlabBlockEntity.Conversion.TO_DIRT, true, 1, 1);
                    entity.requestConversion(AbstractDoubleSlabBlockEntity.Conversion.TO_DIRT, false, 1, 1);
                }
            } else if (bl1) {
                if (canSurviveSpreadableVerticalSlabBlock(positiveState, world, pos1)) {
                    entity.requestConversion(AbstractDoubleSlabBlockEntity.Conversion.TO_DIRT, true, 1, 1);
                }
            } else if (bl2) {
                if (canSurviveSpreadableVerticalSlabBlock(negativeState, world, pos1)) {
                    entity.requestConversion(AbstractDoubleSlabBlockEntity.Conversion.TO_DIRT, false, 1, 1);
                }
            }
        }
    }

    public static BlockState getSpreadableBlock(World world, BlockPos pos, BlockState thisState, BlockState sourceState) {
        sourceState = ModBlockMap.toOriginal(sourceState.getBlock()).getDefaultState();
        return sourceState.with(SnowyBlock.SNOWY, world.getBlockState(pos.up()).isOf(Blocks.SNOW));
    }

    public static BlockState getSpreadableSlabBlock(World world, BlockPos pos, BlockState thisState, BlockState sourceState) {
        sourceState = ModBlockMap.toSlab(sourceState.getBlock()).getDefaultState();
        return sourceState
                .with(SnowySlabBlock.SNOWY, world.getBlockState(pos.up()).isOf(Blocks.SNOW))
                .with(SlabBlock.TYPE, thisState.get(SlabBlock.TYPE))
                .with(SlabBlock.WATERLOGGED, thisState.get(SlabBlock.WATERLOGGED));
    }

    public static BlockState getSpreadableVerticalSlabBlock(World world, BlockPos pos, BlockState thisState, BlockState sourceState) {
        sourceState = ModBlockMap.toVerticalSlab(sourceState.getBlock()).getDefaultState();
        return sourceState
                .with(SnowyVerticalSlabBlock.SNOWY, world.getBlockState(pos.up()).isOf(Blocks.SNOW))
                .with(VerticalSlabBlock.FACING, thisState.get(VerticalSlabBlock.FACING))
                .with(VerticalSlabBlock.IS_DOUBLE, thisState.get(VerticalSlabBlock.IS_DOUBLE))
                .with(VerticalSlabBlock.WATERLOGGED, thisState.get(VerticalSlabBlock.WATERLOGGED));
    }

    public static boolean canSurviveSpreadableBlock(BlockState state, WorldView world, BlockPos pos) {
        if (world.getFluidState(pos.up()).isIn(FluidTags.WATER)) return false;

        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(Blocks.SNOW) && blockState.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else if (blockState.getFluidState().getLevel() == 8) {
            return false;
        } else {
            int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(world, blockPos));
            return i < world.getMaxLightLevel();
        }
    }

    public static boolean canSurviveSpreadableSlabBlock(BlockState state, WorldView world, BlockPos pos) {
        if (world.getFluidState(pos.up()).isIn(FluidTags.WATER)) return false;

        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        SlabType type = state.get(SlabBlock.TYPE);
        boolean isBottom = type == SlabType.BOTTOM;

        if (blockState.isOf(Blocks.SNOW) && blockState.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else if (isBottom) {
            return !state.get(SlabBlock.WATERLOGGED);
        } else if (blockState.getFluidState().getLevel() == 8) {
            return false;
        } else {
            int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(world, blockPos));
            return i < world.getMaxLightLevel();
        }
    }

    public static boolean canSurviveSpreadableVerticalSlabBlock(BlockState state, WorldView world, BlockPos pos) {
        if (world.getFluidState(pos.up()).isIn(FluidTags.WATER)) return false;

        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(Blocks.SNOW) && blockState.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else if (blockState.getFluidState().getLevel() == 8) {
            return false;
        } else {
            int i = getRealisticOpacity(world, state, pos, blockState, blockPos, blockState.getOpacity(world, blockPos));
            return i < world.getMaxLightLevel();
        }
    }

    private static int getRealisticOpacity(BlockView world, BlockState state1, BlockPos pos1, BlockState state2, BlockPos pos2, int opacity2) {
        boolean bl2 = !state2.isOpaque() || !state2.hasSidedTransparency();

        if (bl2) {
            return opacity2;
        } else {
            if (state1.getBlock() instanceof VerticalSlabBlock) {
                if (state2.getBlock() instanceof VerticalSlabBlock) {
                    return state1.get(VerticalSlabBlock.FACING) == state2.get(VerticalSlabBlock.FACING) ? 16 : opacity2;
                } else if (state2.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
                    Direction facing = state1.get(VerticalSlabBlock.FACING);
                    boolean isX = state2.get(DoubleVerticalSlabBlock.AXIS) == VerticalSlabAxis.X;

                    if ((facing == Direction.EAST && isX) || (facing == Direction.SOUTH && !isX)) {
                        return DoubleSlabUtils.isPositiveOpaque(state2) ? 16 : opacity2;
                    } else if ((facing == Direction.WEST && isX) || (facing == Direction.NORTH && !isX)) {
                        return DoubleSlabUtils.isNegativeOpaque(state2) ? 16 : opacity2;
                    } else {
                        return DoubleSlabUtils.isPositiveOpaque(state2) || DoubleSlabUtils.isNegativeOpaque(state2) ? 16 : opacity2;
                    }
                }
            }

            return VoxelShapes.adjacentSidesCoverSquare(state1.getCullingShape(world, pos1), state2.getCullingShape(world, pos2), Direction.UP) ? 16 : opacity2;
        }
    }
}
