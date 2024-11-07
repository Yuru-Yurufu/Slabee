package com.forestotzka.yurufu.sloves.mixin;

import com.forestotzka.yurufu.sloves.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;
import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow private World world;

    @Shadow
    public abstract World getWorld();

    @Shadow
    public abstract double getY();

    @Unique
    private final Random random = new Random();

    @Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
    private void playStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
        Block block = state.getBlock();
        if (DoubleSlabUtils.isDoubleBlock(block)) {
            BlockSoundGroup blockSoundGroup;
            if (block instanceof DoubleSlabBlock) {
                blockSoundGroup = getTopSlabState(pos).getSoundGroup();
            } else {
                blockSoundGroup = getRandomVerticalSlabState(pos).getSoundGroup();
            }
            this.playSound(blockSoundGroup.getStepSound(), blockSoundGroup.getVolume() * 0.15F, blockSoundGroup.getPitch());
            ci.cancel();
        }
    }

    @Inject(
            method = "spawnSprintingParticles",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"
            ),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void spawnSprintingParticles(CallbackInfo ci, BlockPos blockPos, BlockState blockState, Vec3d vec3d, BlockPos blockPos2, double d, double e) {
        Block block = blockState.getBlock();
        if (DoubleSlabUtils.isDoubleBlock(block)) {
            BlockState particleState;

            if (block instanceof DoubleSlabBlock) {
                particleState = getTopSlabState(blockPos);
            } else {
                particleState = getRandomVerticalSlabState(blockPos);
            }

            this.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, particleState), d, this.getY() + 0.1, e, vec3d.x * -4.0, 1.5, vec3d.z * -4.0);
            ci.cancel();
        }
    }

    @Unique
    private BlockState getTopSlabState(BlockPos pos) {
        DoubleSlabBlockEntity entity = (DoubleSlabBlockEntity) world.getBlockEntity(pos);
        return Objects.requireNonNull(entity).getTopSlabState();
    }

    @Unique
    private BlockState getRandomVerticalSlabState(BlockPos pos) {
        DoubleVerticalSlabBlockEntity entity = (DoubleVerticalSlabBlockEntity) world.getBlockEntity(pos);
        if (random.nextBoolean()) {
            return Objects.requireNonNull(entity).getPositiveSlabState();
        } else {
            return Objects.requireNonNull(entity).getNegativeSlabState();
        }
    }
}
