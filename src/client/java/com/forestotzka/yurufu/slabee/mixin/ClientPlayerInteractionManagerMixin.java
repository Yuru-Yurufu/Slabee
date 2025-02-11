package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
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
        World world = client.world;
        if (world == null) return;

        BlockState blockState = world.getBlockState(pos);
        if (SlabeeUtils.isDoubleSlab(blockState)) {
            BlockState topSlab;
            BlockState bottomSlab;
            if (world.getBlockEntity(pos) instanceof AbstractDoubleSlabBlockEntity entity) {
                topSlab = Objects.requireNonNull(entity).getPositiveSlabState();
                bottomSlab = Objects.requireNonNull(entity).getNegativeSlabState();
            } else {
                return;
            }

            float blockBreakingSoundCooldown = this.getBlockBreakingSoundCooldown();
            int blockBreakingCooldown = this.getBlockBreakingCooldown();
            float currentBreakingProgress = this.getCurrentBreakingProgress();

            if (blockBreakingCooldown <= 0 && !(getGameMode().isCreative() && client.world.getWorldBorder().contains(pos)) && this.isCurrentlyBreaking(pos)) {
                currentBreakingProgress = currentBreakingProgress + blockState.calcBlockBreakingDelta(client.player, Objects.requireNonNull(client.player).getWorld(), pos);

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

    @Inject(
            method = "breakBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/block/BlockState;",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void onBreakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        World world = client.world;
        ClientPlayerEntity player = client.player;
        if (world != null && player != null && player.isSneaking()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            BlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            if (SlabeeUtils.isDoubleSlab(blockState)) {
                BlockState stayState;
                if (blockEntity instanceof DoubleSlabBlockEntity entity) {
                    if (LookingPositionTracker.lookingAtUpperHalf) {
                        stayState = entity.getNegativeSlabState().with(SlabBlock.TYPE, SlabType.BOTTOM);
                    } else {
                        stayState = entity.getPositiveSlabState().with(SlabBlock.TYPE, SlabType.TOP);
                    }
                } else if (blockEntity instanceof DoubleVerticalSlabBlockEntity entity) {
                    if (entity.isX()) {
                        if (LookingPositionTracker.lookingAtEasternHalf) {
                            stayState = entity.getNegativeSlabState().with(VerticalSlabBlock.FACING, Direction.WEST);
                        } else {
                            stayState = entity.getPositiveSlabState().with(VerticalSlabBlock.FACING, Direction.EAST);
                        }
                    } else {
                        if (LookingPositionTracker.lookingAtSouthernHalf) {
                            stayState = entity.getNegativeSlabState().with(VerticalSlabBlock.FACING, Direction.NORTH);
                        } else {
                            stayState = entity.getPositiveSlabState().with(VerticalSlabBlock.FACING, Direction.SOUTH);
                        }
                    }
                } else {
                    stayState = Blocks.STONE_SLAB.getDefaultState();
                }

                boolean bl = world.setBlockState(pos, stayState, 11);
                if (bl) {
                    block.onBroken(world, pos, blockState);
                }
                cir.setReturnValue(bl);
                cir.cancel();
            }
        }
    }
}
