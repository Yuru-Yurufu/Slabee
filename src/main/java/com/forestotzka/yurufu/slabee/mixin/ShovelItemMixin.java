package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.forestotzka.yurufu.slabee.Slabee;
import com.forestotzka.yurufu.slabee.block.*;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {

    @Unique
    private static final TagKey<Block> DIRT_SLABS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Slabee.MOD_ID, "dirt_slabs"));
    @Unique
    private static final TagKey<Block> DIRT_VERTICAL_SLABS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Slabee.MOD_ID, "dirt_vertical_slabs"));

    @WrapOperation(
            method = "useOn",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    private Object redirectPathStatesGet(Map<Block, BlockState> map, Object key, Operation<Object> original, UseOnContext context, @Local Level world, @Local BlockPos blockPos, @Local BlockState blockState) {
        if (blockState.is(DIRT_SLABS)) {
            SlabType slabType = blockState.getValue(SlabBlock.TYPE);
            return ModBlocks.DIRT_PATH_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, slabType);
        } else if (blockState.is(DIRT_VERTICAL_SLABS)) {
            Direction facing = blockState.getValue(VerticalSlabBlock.FACING);
            return ModBlocks.DIRT_PATH_VERTICAL_SLAB.defaultBlockState().setValue(VerticalSlabBlock.FACING, facing);
        } else if (blockState.is(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(blockPos) instanceof DoubleSlabBlockEntity entity) {
            Player player = context.getPlayer();
            if (player != null && player.isShiftKeyDown()) {
                if (LookingPositionTracker.lookingAtUpperHalf) {
                    if (entity.getPositiveSlabState().is(DIRT_SLABS) && DoubleSlabBlock.canPlaceAt(world.getBlockState(blockPos.above()))) {
                        entity.setPositiveSlabId(BuiltInRegistries.BLOCK.getKey(ModBlocks.DIRT_PATH_SLAB));
                        return world.getBlockState(blockPos);
                    }
                }
            } else {
                if (entity.getPositiveSlabState().is(DIRT_SLABS) && DoubleSlabBlock.canPlaceAt(world.getBlockState(blockPos.above()))) {
                    entity.setPositiveSlabId(BuiltInRegistries.BLOCK.getKey(ModBlocks.DIRT_PATH_SLAB));
                    return world.getBlockState(blockPos);
                }
            }

        } else if (blockState.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(blockPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            Player player = context.getPlayer();
            boolean lookingPositive = true;
            boolean lookingNegative = true;
            if (player != null && player.isShiftKeyDown()) {
                if (entity.isX()) {
                    lookingPositive = LookingPositionTracker.lookingAtEasternHalf;
                } else {
                    lookingPositive = LookingPositionTracker.lookingAtSouthernHalf;
                }
                lookingNegative = !lookingPositive;
            }
            BlockState positiveSlabState = entity.getPositiveSlabState();
            BlockState negativeSlabState = entity.getNegativeSlabState();
            boolean bl1 = positiveSlabState.is(DIRT_VERTICAL_SLABS) && DoubleVerticalSlabBlock.canPlaceAt(world.getBlockState(blockPos.above()), positiveSlabState.getValue(VerticalSlabBlock.FACING)) && lookingPositive;
            boolean bl2 = negativeSlabState.is(DIRT_VERTICAL_SLABS) && DoubleVerticalSlabBlock.canPlaceAt(world.getBlockState(blockPos.above()), negativeSlabState.getValue(VerticalSlabBlock.FACING)) && lookingNegative;

            if (bl1 && bl2) {
                entity.setPositiveSlabId(BuiltInRegistries.BLOCK.getKey(ModBlocks.DIRT_PATH_VERTICAL_SLAB));
                entity.setNegativeSlabId(BuiltInRegistries.BLOCK.getKey(ModBlocks.DIRT_PATH_VERTICAL_SLAB));
                return world.getBlockState(blockPos);
            } else if (bl1) {
                entity.setPositiveSlabId(BuiltInRegistries.BLOCK.getKey(ModBlocks.DIRT_PATH_VERTICAL_SLAB));
                return world.getBlockState(blockPos);
            } else if (bl2) {
                entity.setNegativeSlabId(BuiltInRegistries.BLOCK.getKey(ModBlocks.DIRT_PATH_VERTICAL_SLAB));
                return world.getBlockState(blockPos);
            }
        }

        return original.call(map, key);
    }

    @ModifyExpressionValue(
            method = "useOn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z"
            )
    )
    private boolean isAirOrDoubleSlab(boolean original, @Local Level world, @Local BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        BlockState upState = world.getBlockState(pos.above());
        if (state.getBlock() instanceof AbstractDoubleSlabBlock) {
            return true;
        } else if (upState.is(DIRT_VERTICAL_SLABS)) {
            return upState.getValue(VerticalSlabBlock.FACING).getOpposite() == state.getValue(VerticalSlabBlock.FACING);
        }
        return original;
    }
}
