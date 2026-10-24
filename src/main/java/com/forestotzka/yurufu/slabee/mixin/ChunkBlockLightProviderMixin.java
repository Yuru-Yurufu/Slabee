package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.world.chunk.light.BlockLightStorage;
import net.minecraft.world.chunk.light.ChunkBlockLightProvider;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * ダブルハーフになったとき、前に漏れていた光を消してから広げ直す。
 *
 * 本体は、ブロックが変わったときに「新しい明るさ ＜ 今の明るさ」の場合しか周りの光を消さない。
 * 光るハーフ（15）に別のハーフを重ねてダブルハーフ（15）になると、明るさは同じなので、
 * ふさがった面の外に前の光が残ってしまう（あやの報告 2026-10-24）。
 * ダブルハーフの位置では、いったん明るさを0にして周りの光を消し、その後で本体の処理に広げ直させる。
 */
@Mixin(ChunkBlockLightProvider.class)
public abstract class ChunkBlockLightProviderMixin {
    @WrapOperation(
            method = "method_51529",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/light/BlockLightStorage;get(J)I", ordinal = 0)
    )
    private int slabee$clearOldLightOfDoubleSlab(BlockLightStorage storage, long blockPos, Operation<Integer> original,
                                                 @Local(ordinal = 0) BlockState state) {
        int current = original.call(storage, blockPos);
        if (current > 0 && (state.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) || state.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK))) {
            ((LightStorageInvoker) storage).slabee$set(blockPos, 0);
            ((ChunkLightProviderInvoker) this).slabee$queueLightDecrease(blockPos,
                    ChunkLightProvider.class_8531.packWithAllDirectionsSet(current));
            return 0;
        }
        return current;
    }
}
