package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.*;
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
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow private Level level;

    @Shadow private BlockPos blockPosition;

    @Shadow
    public abstract Level level();

    @Shadow
    public abstract double getY();

    @Unique
    private final Random random = new Random();

    @Redirect(method = "getBlockSpeedFactor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;getSpeedFactor()F", ordinal = 0))
    private float modifyVelocityMultiplier(Block block) {
        if (SlabeeUtils.isDoubleSlab(block)) {
            BlockEntity blockEntity = level.getBlockEntity(blockPosition);

            if (blockEntity instanceof DoubleSlabBlockEntity entity) {
                return entity.getPositiveSlabState().getBlock().getSpeedFactor();
            } else if (blockEntity instanceof DoubleVerticalSlabBlockEntity entity) {
                boolean isPositiveSide = SlabeeUtils.isPositiveSide((Entity)(Object)this, entity.isX(), blockPosition);
                BlockState state = isPositiveSide ? entity.getPositiveSlabState() : entity.getNegativeSlabState();

                return state.getBlock().getSpeedFactor();
            }
        }

        return block.getSpeedFactor();
    }

    @Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
    private void playStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (state.is(ModBlocks.DOUBLE_SLAB_BLOCK)) {
            DoubleSlabBlockEntity entity = (DoubleSlabBlockEntity) level.getBlockEntity(pos);
            SoundType blockSoundGroup = Objects.requireNonNull(entity).getPositiveSlabState().getSoundType();

            playSound(blockSoundGroup, ci);
        } else if (state.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            DoubleVerticalSlabBlockEntity entity = (DoubleVerticalSlabBlockEntity) level.getBlockEntity(pos);
            BlockState playState;

            if (random.nextBoolean()) {
                playState = Objects.requireNonNull(entity).getPositiveSlabState();
            } else {
                playState = Objects.requireNonNull(entity).getNegativeSlabState();
            }
            SoundType blockSoundGroup = playState.getSoundType();

            playSound(blockSoundGroup, ci);
        }
    }

    @Unique
    private void playSound(SoundType blockSoundGroup, CallbackInfo ci) {
        this.playSound(blockSoundGroup.getStepSound(), blockSoundGroup.getVolume() * 0.15F, blockSoundGroup.getPitch());
        ci.cancel();
    }

    @Inject(
            method = "spawnSprintParticle",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"
            ),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void spawnSprintingParticles(CallbackInfo ci, BlockPos blockPos, BlockState blockState, Vec3 vec3d, BlockPos blockPos2, double d, double e) {
        if (SlabeeUtils.isDoubleSlab(blockState)) {
            BlockState particleState;

            if (blockState.is(ModBlocks.DOUBLE_SLAB_BLOCK)) {
                DoubleSlabBlockEntity entity = (DoubleSlabBlockEntity) level.getBlockEntity(blockPos);
                particleState = Objects.requireNonNull(entity).getPositiveSlabState();
            } else {
                DoubleVerticalSlabBlockEntity entity = (DoubleVerticalSlabBlockEntity) level.getBlockEntity(blockPos);
                if (random.nextBoolean()) {
                    particleState = Objects.requireNonNull(entity).getPositiveSlabState();
                } else {
                    particleState = Objects.requireNonNull(entity).getNegativeSlabState();
                }
            }

            this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, particleState), d, this.getY() + 0.1, e, vec3d.x * -4.0, 1.5, vec3d.z * -4.0);
            ci.cancel();
        }
    }
}
