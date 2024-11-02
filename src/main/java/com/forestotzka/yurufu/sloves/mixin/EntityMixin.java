package com.forestotzka.yurufu.sloves.mixin;

import com.forestotzka.yurufu.sloves.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;
import java.util.Optional;
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

    @Shadow
    public float fallDistance;

    @Shadow
    public Optional<BlockPos> supportingBlockPos;

    @Shadow
    private Vec3d pos;

    @Shadow
    public abstract void onLanding();

    @Unique
    private final Random random = new Random();

    @Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
    private void playStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (state.getBlock() instanceof DoubleSlabBlock) {
            DoubleSlabBlockEntity entity = (DoubleSlabBlockEntity) world.getBlockEntity(pos);
            BlockSoundGroup blockSoundGroup = Objects.requireNonNull(entity).getTopSlabState().getSoundGroup();
            playSound(blockSoundGroup, ci);
        } else if (state.getBlock() instanceof DoubleVerticalSlabBlock) {
            DoubleVerticalSlabBlockEntity entity = (DoubleVerticalSlabBlockEntity) world.getBlockEntity(pos);
            if (random.nextBoolean()) {
                state = Objects.requireNonNull(entity).getPositiveSlabState();
            } else {
                state = Objects.requireNonNull(entity).getNegativeSlabState();
            }
            BlockSoundGroup blockSoundGroup = state.getSoundGroup();
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
    private void beforeAddParticle(CallbackInfo ci, BlockPos blockPos, BlockState blockState, Vec3d vec3d, BlockPos blockPos2, double d, double e) {

        // 例えば、特定のブロックに応じた処理を追加
        if (isDoubleBlock(blockState.getBlock())) {
            BlockState particleState;

            if (blockState.getBlock() instanceof DoubleSlabBlock) {
                DoubleSlabBlockEntity entity = (DoubleSlabBlockEntity) world.getBlockEntity(blockPos);
                particleState = Objects.requireNonNull(entity).getTopSlabState();
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

    @Unique
    private boolean isDoubleBlock(Block block) {
        return block instanceof DoubleSlabBlock || block instanceof DoubleVerticalSlabBlock;
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
