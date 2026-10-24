package com.forestotzka.yurufu.slabee.mixin;

import net.minecraft.world.chunk.light.LightStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LightStorage.class)
public interface LightStorageInvoker {
    @Invoker("set")
    void slabee$set(long blockPos, int value);
}
