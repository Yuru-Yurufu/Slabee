package com.forestotzka.yurufu.slabee.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayerInteractionManager.class)
public interface ClientPlayerInteractionManagerAccessor {

    @Accessor("blockBreakingSoundCooldown")
    float getBlockBreakingSoundCooldown();

    @Accessor("blockBreakingSoundCooldown")
    void setBlockBreakingSoundCooldown(float cooldown);

    @Accessor("blockBreakingCooldown")
    int getBlockBreakingCooldown();

    @Accessor("blockBreakingCooldown")
    void setBlockBreakingCooldown(int cooldown);

    @Accessor("currentBreakingProgress")
    float getCurrentBreakingProgress();

    @Accessor("currentBreakingProgress")
    void setCurrentBreakingProgress(float progress);

    @Accessor("currentBreakingPos")
    BlockPos getCurrentBreakingPos();

    @Accessor("gameMode")
    GameMode getGameMode();
}
