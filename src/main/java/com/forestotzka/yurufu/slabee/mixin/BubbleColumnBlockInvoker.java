package com.forestotzka.yurufu.slabee.mixin;

import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BubbleColumnBlock.class)
public interface BubbleColumnBlockInvoker {
    @Invoker("canExistIn")
    static boolean isStillWater(BlockState state) {
        throw new AssertionError();
    }
}
