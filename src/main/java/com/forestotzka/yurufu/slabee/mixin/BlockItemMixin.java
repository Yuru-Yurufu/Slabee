package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {
    @Shadow
    protected abstract SoundEvent getPlaceSound(BlockState state);

    @Inject(
            method = "place(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/InteractionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"
            ),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void injectCustomSound(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir, BlockPlaceContext itemPlacementContext, BlockState blockState, BlockPos blockPos, Level world, Player playerEntity, ItemStack itemStack, BlockState blockState2, SoundType blockSoundGroup) {
        if (SlabeeUtils.isDoubleSlab(blockState2)) {
            BlockState trueBlockState = ((BlockItem) itemStack.getItem()).getBlock().defaultBlockState();
            SoundType trueBlockSoundGroup = trueBlockState.getSoundType();

            world.playSound(playerEntity, blockPos, this.getPlaceSound(trueBlockState), SoundSource.BLOCKS, (trueBlockSoundGroup.getVolume() + 1.0F) / 2.0F, trueBlockSoundGroup.getPitch() * 0.8F);
            world.gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(playerEntity, trueBlockState));
            itemStack.consume(1, playerEntity);

            cir.setReturnValue(InteractionResult.sidedSuccess(world.isClientSide));
            cir.cancel();
        }
    }

    @Inject(method = "placeBlock(Lnet/minecraft/world/item/context/BlockPlaceContext;Lnet/minecraft/world/level/block/state/BlockState;)Z", at = @At("HEAD"), cancellable = true)
    private void placeDoubleSlab(BlockPlaceContext ctx, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (SlabeeUtils.isDoubleSlab(state)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
