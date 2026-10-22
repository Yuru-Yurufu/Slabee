package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.ClickPositionTracker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
@Environment(EnvType.CLIENT)
public class MinecraftClientMixin {

    @Inject(method = "pickBlock", at = @At("HEAD"))
    private void onPickBlock(CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();
        HitResult hitResult = client.hitResult;

        if (hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos blockPos = blockHitResult.getBlockPos();

            double clickX = blockHitResult.getLocation().x - blockPos.getX();
            double clickY = blockHitResult.getLocation().y - blockPos.getY();
            double clickZ = blockHitResult.getLocation().z - blockPos.getZ();
            ClickPositionTracker.clickEasternHalf = clickX > 0.5;
            ClickPositionTracker.clickUpperHalf = clickY > 0.5;
            ClickPositionTracker.clickSouthernHalf = clickZ > 0.5;
        }
    }
}
