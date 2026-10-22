package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.multiplayer.prediction.PredictiveAction;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;

@Mixin(MultiPlayerGameMode.class)
public abstract class ClientPlayerInteractionManagerMixin implements ClientPlayerInteractionManagerAccessor {
    @Shadow public abstract boolean destroyBlock(BlockPos pos);

    @Shadow protected abstract void startPrediction(ClientLevel world, PredictiveAction packetCreator);

    @Shadow public abstract int getDestroyStage();

    @Shadow protected abstract boolean sameDestroyTarget(BlockPos pos);

    @Shadow private boolean isDestroying;

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "continueDestroyBlock", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;ensureHasSentCarriedItem()V",
            shift = At.Shift.AFTER
    ), cancellable = true)
    private void updateBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        Level world = minecraft.level;
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

            if (blockBreakingCooldown <= 0 && !(getGameMode().isCreative() && minecraft.level.getWorldBorder().isWithinBounds(pos)) && this.sameDestroyTarget(pos)) {
                currentBreakingProgress = currentBreakingProgress + blockState.getDestroyProgress(minecraft.player, Objects.requireNonNull(minecraft.player).level(), pos);

                if (blockBreakingSoundCooldown % 4.0F == 0.0F) {
                    SoundManager soundManager = minecraft.getSoundManager();

                    playSound(soundManager, topSlab.getSoundType(), world, pos);
                    if (topSlab.getBlock() != bottomSlab.getBlock()) {
                        playSound(soundManager, bottomSlab.getSoundType(), world, pos);
                    }
                }

                blockBreakingSoundCooldown++;
                minecraft.getTutorial().onDestroyBlock(minecraft.level, pos, blockState, Mth.clamp(currentBreakingProgress, 0.0F, 1.0F));
                if (currentBreakingProgress >= 1.0F) {
                    this.isDestroying = false;
                    this.startPrediction(minecraft.level, sequence -> {
                        this.destroyBlock(pos);
                        return new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK, pos, direction, sequence);
                    });
                    currentBreakingProgress = 0.0F;
                    blockBreakingSoundCooldown = 0.0F;
                    blockBreakingCooldown = 5;
                }

                setBlockBreakingSoundCooldown(blockBreakingSoundCooldown);
                setBlockBreakingCooldown(blockBreakingCooldown);
                setCurrentBreakingProgress(currentBreakingProgress);

                minecraft.level.destroyBlockProgress(minecraft.player.getId(), getCurrentBreakingPos(), this.getDestroyStage());

                cir.setReturnValue(true);
                cir.cancel();
            }
        }
    }

    @Unique
    private void playSound(SoundManager soundManager, SoundType soundGroup, Level world, BlockPos pos) {
        soundManager.play(new SimpleSoundInstance(
                soundGroup.getHitSound(),
                SoundSource.BLOCKS,
                (soundGroup.getVolume() + 1.0F) / 8.0F,
                soundGroup.getPitch() * 0.5F,
                world.random,
                pos
        ));
    }

    @Inject(
            method = "destroyBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;playerWillDestroy(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/level/block/state/BlockState;",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void onBreakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Level world = minecraft.level;
        LocalPlayer player = minecraft.player;
        if (world != null && player != null && player.isShiftKeyDown()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            BlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            if (SlabeeUtils.isDoubleSlab(blockState)) {
                BlockState stayState;
                if (blockEntity instanceof DoubleSlabBlockEntity entity) {
                    if (LookingPositionTracker.lookingAtUpperHalf) {
                        stayState = entity.getNegativeSlabState().setValue(SlabBlock.TYPE, SlabType.BOTTOM);
                    } else {
                        stayState = entity.getPositiveSlabState().setValue(SlabBlock.TYPE, SlabType.TOP);
                    }
                } else if (blockEntity instanceof DoubleVerticalSlabBlockEntity entity) {
                    if (entity.isX()) {
                        if (LookingPositionTracker.lookingAtEasternHalf) {
                            stayState = entity.getNegativeSlabState().setValue(VerticalSlabBlock.FACING, Direction.WEST);
                        } else {
                            stayState = entity.getPositiveSlabState().setValue(VerticalSlabBlock.FACING, Direction.EAST);
                        }
                    } else {
                        if (LookingPositionTracker.lookingAtSouthernHalf) {
                            stayState = entity.getNegativeSlabState().setValue(VerticalSlabBlock.FACING, Direction.NORTH);
                        } else {
                            stayState = entity.getPositiveSlabState().setValue(VerticalSlabBlock.FACING, Direction.SOUTH);
                        }
                    }
                } else {
                    stayState = Blocks.STONE_SLAB.defaultBlockState();
                }

                if (stayState.getBlock() instanceof SnowySlabBlock) {
                    stayState = stayState.setValue(SnowySlabBlock.SNOWY, world.getBlockState(pos.above()).is(BlockTags.SNOW));
                }

                boolean bl = world.setBlock(pos, stayState, 11);
                if (bl) {
                    block.destroy(world, pos, blockState);
                }
                cir.setReturnValue(bl);
                cir.cancel();
            }
        }
    }
}
