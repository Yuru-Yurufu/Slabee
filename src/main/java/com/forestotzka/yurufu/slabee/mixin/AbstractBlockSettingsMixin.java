package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.StaticBlockLinker;
import com.forestotzka.yurufu.slabee.extensions.AbstractBlockSettingsExtensions;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.apache.commons.lang3.function.ToBooleanBiFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;
import java.util.function.ToIntFunction;

@Mixin(AbstractBlock.Settings.class)
public class AbstractBlockSettingsMixin implements AbstractBlockSettingsExtensions {

    @Unique
    ToIntFunction<BlockState> intOpaque;

    @Unique
    private boolean setOpaqueMarker = false;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        this.intOpaque = (state) -> 0;
    }

    public AbstractBlock.Settings slabee$setOpaque(ToIntFunction<BlockState> opaqueFunction) {
        this.intOpaque = opaqueFunction;
        this.setOpaqueMarker = true;
        return (AbstractBlock.Settings) (Object) this;
    }

    /*@Inject(method = "copyShallow", at = @At("RETURN"), cancellable = true)
    private static void copyShallow(AbstractBlock block, CallbackInfoReturnable<AbstractBlock.Settings> cir) {
        AbstractBlock.Settings original = block.getSettings();
        AbstractBlock.Settings copy = cir.getReturnValue();
        ((AbstractBlockSettingsMixin) (Object) copy).intOpaque = ((AbstractBlockSettingsMixin) (Object) original).intOpaque;
        cir.setReturnValue(copy);
    }*/

    @Override
    public ToIntFunction<BlockState> getIntOpaque() {
        return this.intOpaque;
    }

    @Override
    public boolean getSetOpaqueMarker() {
        return this.setOpaqueMarker;
    }
}
