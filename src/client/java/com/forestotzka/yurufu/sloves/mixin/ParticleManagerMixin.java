package com.forestotzka.yurufu.sloves.mixin;

import com.forestotzka.yurufu.sloves.block.DoubleSlabBlock;
import com.forestotzka.yurufu.sloves.block.DoubleSlabBlockEntity;
import com.forestotzka.yurufu.sloves.block.DoubleVerticalSlabBlock;
import com.forestotzka.yurufu.sloves.block.DoubleVerticalSlabBlockEntity;
import com.forestotzka.yurufu.sloves.block.enums.VerticalSlabAxis;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {
    @Shadow protected ClientWorld world;

    @Shadow public abstract void addParticle(Particle particle);

    @Inject(
            method = "addBlockBreakingParticles",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/ParticleManager;addParticle(Lnet/minecraft/client/particle/Particle;)V"
            ),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void modifyBlockBreakingParticles(BlockPos pos, Direction direction, CallbackInfo cir, BlockState blockState, int i, int j, int k, float f, Box box, double d, double e, double g) {
        if (blockState.getBlock() instanceof DoubleSlabBlock || blockState.getBlock() instanceof DoubleVerticalSlabBlock) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            BlockState particleState;

            if (blockEntity instanceof DoubleSlabBlockEntity entity) {
                if ((e - j) > 0.5) {
                    particleState = entity.getTopSlabState();
                } else {
                    particleState = entity.getBottomSlabState();
                }
            } else if (blockEntity instanceof DoubleVerticalSlabBlockEntity entity) {
                if ((blockState.get(DoubleVerticalSlabBlock.AXIS) == VerticalSlabAxis.X && (d - i) > 0.5) || (blockState.get(DoubleVerticalSlabBlock.AXIS) == VerticalSlabAxis.Z && (g - k) > 0.5)) {
                    particleState = entity.getPositiveSlabState();
                } else {
                    particleState = entity.getNegativeSlabState();
                }
            } else {
                return;
            }

            addParticle(new BlockDustParticle(this.world, d, e, g, 0.0, 0.0, 0.0, particleState, pos).move(0.2F).scale(0.6F));
            cir.cancel();
        }
    }
}
