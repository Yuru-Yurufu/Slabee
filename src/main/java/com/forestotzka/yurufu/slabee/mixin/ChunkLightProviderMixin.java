package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.block.DoubleSlabBlock;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlock;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightEngine.class)
public class ChunkLightProviderMixin {
    @Redirect(method = "isEmptyShape", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;canOcclude()Z", ordinal = 0))
    private static boolean isOpaque(BlockState state) {
        return state.canOcclude() || state.is(ModBlocks.TINTED_GLASS_SLAB) || state.is(ModBlocks.TINTED_GLASS_VERTICAL_SLAB);
    }

    @Redirect(
            method = "getOcclusionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;getFaceOcclusionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
                    ordinal = 0
            )
    )
    private static VoxelShape getLightingShape(BlockState state, BlockGetter blockView, BlockPos pos, Direction direction) {
        if (state.is(ModBlocks.DOUBLE_SLAB_BLOCK)) {
            return Shapes.getFaceShape(DoubleSlabBlock.getLightingShape(state, blockView, pos), direction);
        } else if (state.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            return Shapes.getFaceShape(DoubleVerticalSlabBlock.getLightingShape(state), direction);
        }
        return state.getFaceOcclusionShape(blockView, pos, direction);
    }
}
