package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<? extends ItemEntity> type, World world) {
        super(type, world);
    }

    /**
     * バニラではプレイヤーの0.500001下のブロックの情報が参照される
     * 氷の下付きハーフブロックなどでも滑るように2箇所の情報を取ってmax
     */
    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;getSlipperiness()F"
            )
    )
    private float wrapSlipperiness(float original) {
        return SlabeeUtils.getSlipperiness(this.getWorld(), this.getPos(), this.getPosWithYOffset(0.5F), original);
    }
}
