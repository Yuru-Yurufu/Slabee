package com.forestotzka.yurufu.sloves.mixin;

import com.forestotzka.yurufu.sloves.block.DoubleSlabBlock;
import com.forestotzka.yurufu.sloves.block.DoubleSlabBlockEntity;
import com.forestotzka.yurufu.sloves.block.DoubleVerticalSlabBlock;
import com.forestotzka.yurufu.sloves.block.DoubleVerticalSlabBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin implements ClientPlayerInteractionManagerAccessor {
    @Shadow public abstract boolean breakBlock(BlockPos pos);

    @Shadow protected abstract void sendSequencedPacket(ClientWorld world, SequencedPacketCreator packetCreator);

    @Shadow public abstract int getBlockBreakingProgress();

    @Shadow protected abstract boolean isCurrentlyBreaking(BlockPos pos);

    @Shadow private boolean breakingBlock;

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "updateBlockBreakingProgress", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;syncSelectedSlot()V",
            shift = At.Shift.AFTER
    ), cancellable = true)
    private void updateBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        World world = client.world;
        if (world == null) return;

        BlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock() instanceof DoubleSlabBlock || blockState.getBlock() instanceof DoubleVerticalSlabBlock) {
            BlockState topSlab;
            BlockState bottomSlab;
            if (blockState.getBlock() instanceof DoubleSlabBlock) {
                DoubleSlabBlockEntity entity = (DoubleSlabBlockEntity) world.getBlockEntity(pos);
                topSlab = Objects.requireNonNull(entity).getTopSlabState();
                bottomSlab = Objects.requireNonNull(entity).getBottomSlabState();
            } else {
                DoubleVerticalSlabBlockEntity entity = (DoubleVerticalSlabBlockEntity) world.getBlockEntity(pos);
                topSlab = Objects.requireNonNull(entity).getPositiveSlabState();
                bottomSlab = Objects.requireNonNull(entity).getNegativeSlabState();
            }

            float blockBreakingSoundCooldown = this.getBlockBreakingSoundCooldown();
            int blockBreakingCooldown = this.getBlockBreakingCooldown();
            float currentBreakingProgress = this.getCurrentBreakingProgress();

            if (blockBreakingCooldown <= 0 && !(getGameMode().isCreative() && client.world.getWorldBorder().contains(pos)) && this.isCurrentlyBreaking(pos)) {
                currentBreakingProgress = currentBreakingProgress + blockState.calcBlockBreakingDelta(client.player, client.player.getWorld(), pos);

                if (blockBreakingSoundCooldown % 4.0F == 0.0F) {
                    SoundManager soundManager = client.getSoundManager();

                    playSound(soundManager, topSlab.getSoundGroup(), world, pos);
                    if (topSlab.getBlock() != bottomSlab.getBlock()) {
                        playSound(soundManager, bottomSlab.getSoundGroup(), world, pos);
                    }
                }

                blockBreakingSoundCooldown++;
                client.getTutorialManager().onBlockBreaking(client.world, pos, blockState, MathHelper.clamp(currentBreakingProgress, 0.0F, 1.0F));
                if (currentBreakingProgress >= 1.0F) {
                    this.breakingBlock = false;
                    this.sendSequencedPacket(client.world, sequence -> {
                        this.breakBlock(pos);
                        return new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, direction, sequence);
                    });
                    currentBreakingProgress = 0.0F;
                    blockBreakingSoundCooldown = 0.0F;
                    blockBreakingCooldown = 5;
                }

                setBlockBreakingSoundCooldown(blockBreakingSoundCooldown);
                setBlockBreakingCooldown(blockBreakingCooldown);
                setCurrentBreakingProgress(currentBreakingProgress);

                client.world.setBlockBreakingInfo(client.player.getId(), getCurrentBreakingPos(), this.getBlockBreakingProgress());

                cir.setReturnValue(true);
                cir.cancel();
            }
        }
    }

    @Unique
    private void playSound(SoundManager soundManager, BlockSoundGroup soundGroup, World world, BlockPos pos) {
        soundManager.play(new PositionedSoundInstance(
                soundGroup.getHitSound(),
                SoundCategory.BLOCKS,
                (soundGroup.getVolume() + 1.0F) / 8.0F,
                soundGroup.getPitch() * 0.5F,
                world.random,
                pos
        ));
    }
}
