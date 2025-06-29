package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;
import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow private World world;

    @Shadow private BlockPos blockPos;

    @Shadow
    public abstract World getWorld();

    @Shadow
    public abstract double getY();

    @Unique
    private final Random random = new Random();

    @Redirect(method = "getVelocityMultiplier", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getVelocityMultiplier()F", ordinal = 0))
    private float modifyVelocityMultiplier(Block block) {
        if (SlabeeUtils.isDoubleSlab(block)) {
            BlockEntity blockEntity = world.getBlockEntity(blockPos);

            if (blockEntity instanceof DoubleSlabBlockEntity entity) {
                return entity.getPositiveSlabState().getBlock().getVelocityMultiplier();
            } else if (blockEntity instanceof DoubleVerticalSlabBlockEntity entity) {
                boolean isPositiveSide = SlabeeUtils.isPositiveSide((Entity)(Object)this, entity.isX(), blockPos);
                BlockState state = isPositiveSide ? entity.getPositiveSlabState() : entity.getNegativeSlabState();

                return state.getBlock().getVelocityMultiplier();
            }
        }

        return block.getVelocityMultiplier();
    }

    @Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
    private void playStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (state.isOf(ModBlocks.DOUBLE_SLAB_BLOCK)) {
            DoubleSlabBlockEntity entity = (DoubleSlabBlockEntity) world.getBlockEntity(pos);
            BlockSoundGroup blockSoundGroup = Objects.requireNonNull(entity).getPositiveSlabState().getSoundGroup();

            playSound(blockSoundGroup, ci);
        } else if (state.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            DoubleVerticalSlabBlockEntity entity = (DoubleVerticalSlabBlockEntity) world.getBlockEntity(pos);
            BlockState playState;

            if (random.nextBoolean()) {
                playState = Objects.requireNonNull(entity).getPositiveSlabState();
            } else {
                playState = Objects.requireNonNull(entity).getNegativeSlabState();
            }
            BlockSoundGroup blockSoundGroup = playState.getSoundGroup();

            playSound(blockSoundGroup, ci);
        }
    }

    @Unique
    private void playSound(BlockSoundGroup blockSoundGroup, CallbackInfo ci) {
        this.playSound(blockSoundGroup.getStepSound(), blockSoundGroup.getVolume() * 0.15F, blockSoundGroup.getPitch());
        ci.cancel();
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
        if (SlabeeUtils.isDoubleSlab(blockState)) {
            BlockState particleState;

            if (blockState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK)) {
                DoubleSlabBlockEntity entity = (DoubleSlabBlockEntity) world.getBlockEntity(blockPos);
                particleState = Objects.requireNonNull(entity).getPositiveSlabState();
            } else {
                DoubleVerticalSlabBlockEntity entity = (DoubleVerticalSlabBlockEntity) world.getBlockEntity(blockPos);
                if (random.nextBoolean()) {
                    particleState = Objects.requireNonNull(entity).getPositiveSlabState();
                } else {
                    particleState = Objects.requireNonNull(entity).getNegativeSlabState();
                }
            }

            this.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, particleState), d, this.getY() + 0.1, e, vec3d.x * -4.0, 1.5, vec3d.z * -4.0);
            ci.cancel();
        }
    }
}
