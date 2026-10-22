package com.forestotzka.yurufu.slabee.devclient.mixin;

import net.minecraft.client.renderer.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 撮影中はテクスチャのアニメーション（マグマブロックなど）を止めて、同じ場面を撮ったら
 * 毎回同じ画像になるようにする。-Dslabee.scene=true のときだけ。
 */
@Mixin(TextureManager.class)
public class TextureManagerMixin {
    private static final boolean SCENE = Boolean.getBoolean("slabee.scene");

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void slabeeScene$freezeAnimations(CallbackInfo ci) {
        if (SCENE) ci.cancel();
    }
}
