package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.VehicleEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin extends VehicleEntity {
    public BoatEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(
            method = "getNearbySlipperiness",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/shape/VoxelShape;")
    )
    private VoxelShape redirectGetCollisionShape(BlockState state, BlockView world, BlockPos pos) {
        if (state.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            // ボートの slipperiness 判定用にだけ fullCube を返す
            return VoxelShapes.fullCube();
        }
        return state.getCollisionShape(world, pos);
    }

    /**
     * バニラではプレイヤーの0.500001下のブロックの情報が参照される
     * 氷の下付きハーフブロックなどでも滑るように2箇所の情報を取ってmax
     */
    @ModifyExpressionValue(
            method = "getNearbySlipperiness",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;getSlipperiness()F"
            )
    )
    private float wrapSlipperiness(float original) {
        return SlabeeUtils.getSlipperiness(this.getWorld(), this.getPos(), this.getPosWithYOffset(0.5F), original);
    }
}
