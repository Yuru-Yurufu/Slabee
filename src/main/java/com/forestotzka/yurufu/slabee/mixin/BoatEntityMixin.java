package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Boat.class)
public abstract class BoatEntityMixin extends VehicleEntity {
    public BoatEntityMixin(EntityType<?> entityType, Level world) {
        super(entityType, world);
    }

    @Redirect(
            method = "getGroundFriction",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/shapes/VoxelShape;")
    )
    private VoxelShape redirectGetCollisionShape(BlockState state, BlockGetter world, BlockPos pos) {
        if (state.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK)) {
            // ボートの slipperiness 判定用にだけ fullCube を返す
            return Shapes.block();
        }
        return state.getCollisionShape(world, pos);
    }

    /**
     * バニラではプレイヤーの0.500001下のブロックの情報が参照される
     * 氷の下付きハーフブロックなどでも滑るように2箇所の情報を取ってmax
     */
    @ModifyExpressionValue(
            method = "getGroundFriction",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;getFriction()F"
            )
    )
    private float wrapSlipperiness(float original) {
        return SlabeeUtils.getSlipperiness(this.level(), this.position(), this.getOnPos(0.5F), original);
    }
}
