package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.block.DoubleSlabBlock;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlock;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkLightProvider.class)
public class ChunkLightProviderMixin {
    @Redirect(method = "isTrivialForLighting", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOpaque()Z", ordinal = 0))
    private static boolean isOpaque(BlockState state) {
        return state.isOpaque() || state.isOf(ModBlocks.TINTED_GLASS_SLAB) || state.isOf(ModBlocks.TINTED_GLASS_VERTICAL_SLAB);
    }

    @Redirect(
            method = "getOpaqueShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Lnet/minecraft/util/shape/VoxelShape;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;getCullingFace(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Lnet/minecraft/util/shape/VoxelShape;",
                    ordinal = 0
            )
    )
    private static VoxelShape getLightingShape(BlockState state, BlockView blockView, BlockPos pos, Direction direction) {
        if (state.isOf(ModBlocks.DOUBLE_SLAB_BLOCK)) {
            return VoxelShapes.extrudeFace(DoubleSlabBlock.getLightingShape(state, blockView, pos), direction);
        } else if (state.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            return VoxelShapes.extrudeFace(DoubleVerticalSlabBlock.getLightingShape(state), direction);
        }
        return state.getCullingFace(blockView, pos, direction);
    }
}
