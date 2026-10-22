package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.*;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ParticleEngine.class)
public abstract class ParticleManagerMixin {
    @Shadow protected ClientLevel level;

    @Shadow public abstract void add(Particle particle);

    @Inject(
            method = "crack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/ParticleEngine;add(Lnet/minecraft/client/particle/Particle;)V"
            ),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void addBlockBreakingParticles(BlockPos pos, Direction direction, CallbackInfo cir, BlockState blockState, int i, int j, int k, float f, AABB box, double d, double e, double g) {
        if (SlabeeUtils.isDoubleSlab(blockState)) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            BlockState particleState;

            if (blockEntity instanceof DoubleSlabBlockEntity entity) {
                if ((e - j) > 0.5) {
                    particleState = entity.getPositiveSlabState();
                } else {
                    particleState = entity.getNegativeSlabState();
                }
            } else if (blockEntity instanceof DoubleVerticalSlabBlockEntity entity) {
                if ((blockState.getValue(DoubleVerticalSlabBlock.AXIS) == VerticalSlabAxis.X && (d - i) > 0.5) || (blockState.getValue(DoubleVerticalSlabBlock.AXIS) == VerticalSlabAxis.Z && (g - k) > 0.5)) {
                    particleState = entity.getPositiveSlabState();
                } else {
                    particleState = entity.getNegativeSlabState();
                }
            } else {
                return;
            }

            add(new TerrainParticle(this.level, d, e, g, 0.0, 0.0, 0.0, particleState, pos).setPower(0.2F).scale(0.6F));
            cir.cancel();
        }
    }
}
