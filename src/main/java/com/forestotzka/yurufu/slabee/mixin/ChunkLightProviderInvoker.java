package com.forestotzka.yurufu.slabee.mixin;

import net.minecraft.world.chunk.light.ChunkLightProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ChunkLightProvider.class)
public interface ChunkLightProviderInvoker {
    /** 光を消す処理の予約（Yarn 1.21.1 では名前がついていない） */
    @Invoker("method_51565")
    void slabee$queueLightDecrease(long blockPos, long packed);
}
