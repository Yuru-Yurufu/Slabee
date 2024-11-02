package com.forestotzka.yurufu.sloves.mixin;

import com.forestotzka.yurufu.sloves.block.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow private World world;

    @Unique
    private final Random random = new Random();

    @Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
    private void playStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (state.getBlock() instanceof DoubleSlabBlock) {
            DoubleSlabBlockEntity entity = (DoubleSlabBlockEntity) world.getBlockEntity(pos);
            BlockSoundGroup blockSoundGroup = Objects.requireNonNull(entity).getTopSlabState().getSoundGroup();
            this.playSound(blockSoundGroup.getStepSound(), blockSoundGroup.getVolume() * 0.15F, blockSoundGroup.getPitch());

            ci.cancel();
        } else if (state.getBlock() instanceof DoubleVerticalSlabBlock) {
            DoubleVerticalSlabBlockEntity entity = (DoubleVerticalSlabBlockEntity) world.getBlockEntity(pos);
            if (random.nextBoolean()) {
                state = Objects.requireNonNull(entity).getPositiveSlabState();
            } else {
                state = Objects.requireNonNull(entity).getNegativeSlabState();
            }
            BlockSoundGroup blockSoundGroup = state.getSoundGroup();
            this.playSound(blockSoundGroup.getStepSound(), blockSoundGroup.getVolume() * 0.15F, blockSoundGroup.getPitch());

            ci.cancel();
        }
    }
}
