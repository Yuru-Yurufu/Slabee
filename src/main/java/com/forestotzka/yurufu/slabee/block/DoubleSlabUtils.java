package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.registry.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class DoubleSlabUtils {
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
}
