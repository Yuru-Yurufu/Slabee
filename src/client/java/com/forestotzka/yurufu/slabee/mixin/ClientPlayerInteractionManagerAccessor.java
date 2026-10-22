package com.forestotzka.yurufu.slabee.mixin;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MultiPlayerGameMode.class)
public interface ClientPlayerInteractionManagerAccessor {

    @Accessor("destroyTicks")
    float getBlockBreakingSoundCooldown();

    @Accessor("destroyTicks")
    void setBlockBreakingSoundCooldown(float cooldown);

    @Accessor("destroyDelay")
    int getBlockBreakingCooldown();

    @Accessor("destroyDelay")
    void setBlockBreakingCooldown(int cooldown);

    @Accessor("destroyProgress")
    float getCurrentBreakingProgress();

    @Accessor("destroyProgress")
    void setCurrentBreakingProgress(float progress);

    @Accessor("destroyBlockPos")
    BlockPos getCurrentBreakingPos();

    @Accessor("localPlayerMode")
    GameType getGameMode();
}
