package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
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
    MinecraftClient client;

    @Inject(method = "updateCrosshairTarget", at = @At("HEAD"))
    private void updateCrossHairTarget(float tickDelta, CallbackInfo ci) {
        if (client.player == null || client.world == null) return;

        if (client.player.isSneaking() && client.crosshairTarget instanceof BlockHitResult hitResult) {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = client.world.getBlockState(pos);

            if (state.isOf(ModBlocks.DOUBLE_SLAB_BLOCK)) {
                LookingPositionTracker.lookingAtUpperHalf = hitResult.getPos().y - pos.getY() > 0.5;
            } else if (state.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
                LookingPositionTracker.lookingAtEasternHalf = hitResult.getPos().x - pos.getX() > 0.5;
                LookingPositionTracker.lookingAtSouthernHalf = hitResult.getPos().z - pos.getZ() > 0.5;
            }
        }
    }
}
