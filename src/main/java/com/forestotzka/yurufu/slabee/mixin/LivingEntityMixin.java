package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.block.DoubleSlabBlock;
import com.forestotzka.yurufu.slabee.block.DoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlock;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    private Optional<BlockPos> climbingPos;

    @Unique
    private final Random random = new Random();

    @Unique
    private World world;

    @Inject(
            method = "fall",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;spawnParticles(Lnet/minecraft/particle/ParticleEffect;DDDIDDDD)I"
            ),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition, CallbackInfo ci, ServerWorld serverWorld, double d, double e, double f, double g, BlockPos blockPos, float k, double l, int m) {
        if (isDoubleBlock(state.getBlock())) {
            LivingEntity entity = (LivingEntity) (Object) this;
            world = entity.getWorld();

            if (state.getBlock() instanceof DoubleSlabBlock) {
                state = getTopSlabState(landedPosition);
            } else {
                state = getRandomVerticalSlabState(landedPosition);
            }

            ((ServerWorld)entity.getWorld()).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, state), e, f, g, m, 0.0, 0.0, 0.0, 0.15000000596046448);

            super.fall(heightDifference, onGround, state, landedPosition);
            if (onGround) {
                this.climbingPos = Optional.empty();
            }

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
