package com.forestotzka.yurufu.sloves.mixin;

import com.forestotzka.yurufu.sloves.ClickPositionTracker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public class MinecraftClientMixin {

    @Inject(method = "doItemPick", at = @At("HEAD"), cancellable = true)
    private void onPickBlock(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hitResult = client.crosshairTarget;

        if (hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos blockPos = blockHitResult.getBlockPos();

            double clickX = blockHitResult.getPos().x - blockPos.getX();
            double clickY = blockHitResult.getPos().y - blockPos.getY();
            double clickZ = blockHitResult.getPos().z - blockPos.getZ();
            if (clickX > 0.5) {
                System.out.println("東半分がクリックされました");
                ClickPositionTracker.clickEasternHalf = true;

            } else {
                System.out.println("西半分がクリックされました");
                ClickPositionTracker.clickEasternHalf = false;
            }
            if (clickY > 0.5) {
                System.out.println("上半分がクリックされました");
                ClickPositionTracker.clickUpperHalf = true;

            } else {
                System.out.println("下半分がクリックされました");
                ClickPositionTracker.clickUpperHalf = false;
            }
            if (clickZ > 0.5) {
                System.out.println("南半分がクリックされました");
                ClickPositionTracker.clickSouthernHalf = true;

            } else {
                System.out.println("北半分がクリックされました");
                ClickPositionTracker.clickSouthernHalf = false;
            }
        }
    }
}
