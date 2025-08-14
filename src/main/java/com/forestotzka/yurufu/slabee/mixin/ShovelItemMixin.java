package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.Slabee;
import com.forestotzka.yurufu.slabee.block.DoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.VerticalSlabBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {

    @Unique
    private static final TagKey<Block> DIRT_SLABS = TagKey.of(RegistryKeys.BLOCK, Identifier.of(Slabee.MOD_ID, "dirt_slabs"));
    @Unique
    private static final TagKey<Block> DIRT_VERTICAL_SLABS = TagKey.of(RegistryKeys.BLOCK, Identifier.of(Slabee.MOD_ID, "dirt_vertical_slabs"));

    @WrapOperation(
            method = "useOnBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    private Object redirectPathStatesGet(Map<Block, BlockState> map, Object key, Operation<Object> original, ItemUsageContext context, @Local World world, @Local BlockPos blockPos, @Local BlockState blockState) {
        if (blockState.isIn(DIRT_SLABS)) {
            SlabType slabType = blockState.get(SlabBlock.TYPE);
            return ModBlocks.DIRT_PATH_SLAB.getDefaultState().with(SlabBlock.TYPE, slabType);
        } else if (blockState.isIn(DIRT_VERTICAL_SLABS)) {
            Direction facing = blockState.get(VerticalSlabBlock.FACING);
            return ModBlocks.DIRT_PATH_VERTICAL_SLAB.getDefaultState().with(VerticalSlabBlock.FACING, facing);
        } else if (blockState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(blockPos) instanceof DoubleSlabBlockEntity entity) {
            if (entity.getPositiveSlabState().isIn(DIRT_SLABS)) {
                entity.setPositiveSlabId(Registries.BLOCK.getId(ModBlocks.DIRT_PATH_SLAB));
                return world.getBlockState(blockPos);
            }
        } else if (blockState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(blockPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            BlockState positiveSlabState = entity.getPositiveSlabState();
            BlockState negativeSlabState = entity.getNegativeSlabState();
            boolean bl1 = positiveSlabState.isIn(DIRT_VERTICAL_SLABS);
            boolean bl2 = negativeSlabState.isIn(DIRT_VERTICAL_SLABS);

            if (bl1 && bl2) {
                entity.setPositiveSlabId(Registries.BLOCK.getId(ModBlocks.DIRT_PATH_VERTICAL_SLAB));
                entity.setNegativeSlabId(Registries.BLOCK.getId(ModBlocks.DIRT_PATH_VERTICAL_SLAB));
                return world.getBlockState(blockPos);
            } else if (bl1) {
                entity.setPositiveSlabId(Registries.BLOCK.getId(ModBlocks.DIRT_PATH_VERTICAL_SLAB));
                return world.getBlockState(blockPos);
            } else if (bl2) {
                entity.setNegativeSlabId(Registries.BLOCK.getId(ModBlocks.DIRT_PATH_VERTICAL_SLAB));
                return world.getBlockState(blockPos);
            }
        }

        return original.call(map, key);
    }
}
