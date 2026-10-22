package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Final
    @Shadow
    Minecraft minecraft;

    @Inject(method = "pick(F)V", at = @At("HEAD"))
    private void updateCrossHairTarget(float tickDelta, CallbackInfo ci) {
        if (minecraft.player == null || minecraft.level == null) return;

        if (minecraft.player.isShiftKeyDown() && minecraft.hitResult instanceof BlockHitResult hitResult) {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = minecraft.level.getBlockState(pos);

            if (state.is(ModBlocks.DOUBLE_SLAB_BLOCK)) {
                LookingPositionTracker.lookingAtUpperHalf = hitResult.getLocation().y - pos.getY() > 0.5;
            } else if (state.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
                LookingPositionTracker.lookingAtEasternHalf = hitResult.getLocation().x - pos.getX() > 0.5;
                LookingPositionTracker.lookingAtSouthernHalf = hitResult.getLocation().z - pos.getZ() > 0.5;
            }
        }
    }
}
