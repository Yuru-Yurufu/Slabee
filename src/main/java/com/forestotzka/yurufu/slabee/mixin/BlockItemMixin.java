package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
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
            method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"
            ),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void injectCustomSound(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> cir, ItemPlacementContext itemPlacementContext, BlockState blockState, BlockPos blockPos, World world, PlayerEntity playerEntity, ItemStack itemStack, BlockState blockState2, BlockSoundGroup blockSoundGroup) {
        if (SlabeeUtils.isDoubleSlab(blockState2)) {
            BlockState trueBlockState = ((BlockItem) itemStack.getItem()).getBlock().getDefaultState();
            BlockSoundGroup trueBlockSoundGroup = trueBlockState.getSoundGroup();

            world.playSound(playerEntity, blockPos, this.getPlaceSound(trueBlockState), SoundCategory.BLOCKS, (trueBlockSoundGroup.getVolume() + 1.0F) / 2.0F, trueBlockSoundGroup.getPitch() * 0.8F);
            world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Emitter.of(playerEntity, trueBlockState));
            itemStack.decrementUnlessCreative(1, playerEntity);

            cir.setReturnValue(ActionResult.success(world.isClient));
            cir.cancel();
        }
    }

    @Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;Lnet/minecraft/block/BlockState;)Z", at = @At("HEAD"), cancellable = true)
    private void placeDoubleSlab(ItemPlacementContext ctx, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (SlabeeUtils.isDoubleSlab(state)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
