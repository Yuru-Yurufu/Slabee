package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.block.DoubleSlabUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpreadableBlock.class)
public abstract class SpreadableBlockMixin {

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (DoubleSlabUtils.canSurviveSpreadableBlock(state, world, pos) && world.getLightLevel(pos.up()) >= 9) {
            BlockState blockState = ((SpreadableBlock)(Object) this).getDefaultState();

            for (int i = 0; i < 4; i++) {
                BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                DoubleSlabUtils.toDirt(world, world.getBlockState(blockPos), blockPos, blockState);
            }

            ci.cancel();
        }
    }
}
