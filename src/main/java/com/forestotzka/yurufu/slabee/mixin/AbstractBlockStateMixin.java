package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.extensions.AbstractBlockSettingsExtensions;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin {
    @Mutable
    @Final
    @Shadow
    private boolean opaque;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(Block block, Reference2ObjectArrayMap<Property<?>, Comparable<?>> propertyMap, MapCodec<BlockState> codec, CallbackInfo ci) {
        AbstractBlock.Settings settings = block.getSettings();
        BlockState state = (BlockState) (Object) this;
        if (settings instanceof AbstractBlockSettingsExtensions extensions) {
            if (extensions.getSetOpaqueMarker()) {
                //this.opaque = ((AbstractBlockSettingsExtensions) settings).getIntOpaque().applyAsInt(state) == 0;
            }
        }
        //this.opaque = false;
        //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + this);
    }
}
