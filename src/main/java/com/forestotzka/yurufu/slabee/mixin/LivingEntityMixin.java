package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.*;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
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
        if (SlabeeUtils.isDoubleSlab(state)) {
            LivingEntity entity = (LivingEntity) (Object) this;
            world = entity.getWorld();
            BlockState particleState;

            if (state.isOf(ModBlocks.DOUBLE_SLAB_BLOCK)) {
                particleState = getTopSlabState(landedPosition);
            } else {
                particleState = getRandomVerticalSlabState(landedPosition);
            }

            ((ServerWorld) world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, particleState), e, f, g, m, 0.0, 0.0, 0.0, 0.15000000596046448);

            super.fall(heightDifference, onGround, particleState, landedPosition);
            if (onGround) {
                this.climbingPos = Optional.empty();
            }

            ci.cancel();
        }

    }

    @Unique
    private BlockState getTopSlabState(BlockPos pos) {
        DoubleSlabBlockEntity entity = (DoubleSlabBlockEntity) world.getBlockEntity(pos);
        return Objects.requireNonNull(entity).getPositiveSlabState();
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

    /**
     * バニラではプレイヤーの0.500001下のブロックの情報が参照される
     * 氷の下付きハーフブロックなどでも滑るように2箇所の情報を取ってmax
     */
    @ModifyExpressionValue(
            method = "travel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;getSlipperiness()F"
            )
    )
    private float wrapSlipperiness(float original) {
        return SlabeeUtils.getSlipperiness(this.getWorld(), this.getPos(), this.getPosWithYOffset(0.5F), original);
    }
}
