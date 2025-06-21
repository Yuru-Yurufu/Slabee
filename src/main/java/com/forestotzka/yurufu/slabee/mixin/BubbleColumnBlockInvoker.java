package com.forestotzka.yurufu.slabee.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.BubbleColumnBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BubbleColumnBlock.class)
public interface BubbleColumnBlockInvoker {
    @Invoker("isStillWater")
    static boolean isStillWater(BlockState state) {
        throw new AssertionError();
    }
}
