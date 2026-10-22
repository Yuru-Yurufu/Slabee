package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.DoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class WorldRendererMixin {
    @Shadow
    private @Final Minecraft minecraft;

    @Shadow @Nullable private ClientLevel level;

    @Inject(method = "renderHitOutline", at = @At("HEAD"), cancellable = true)
    private void onDrawBlockOutline(PoseStack matrices, VertexConsumer vertexConsumer, Entity entity, double cameraX, double cameraY, double cameraZ, BlockPos pos, BlockState state, CallbackInfo ci) {
        Player player = minecraft.player;
        if (player != null && player.isShiftKeyDown() && SlabeeUtils.isDoubleSlab(state) && level != null) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            VoxelShape shape;
            if (blockEntity instanceof DoubleSlabBlockEntity e) {
                if (LookingPositionTracker.lookingAtUpperHalf) {
                    shape = e.getPositiveOutlineShape();
                } else {
                    shape = e.getNegativeOutlineShape();
                }
            } else if (blockEntity instanceof DoubleVerticalSlabBlockEntity e) {
                if (e.isX()) {
                    if (LookingPositionTracker.lookingAtEasternHalf) {
                        shape = e.getPositiveOutlineShape();
                    } else {
                        shape = e.getNegativeOutlineShape();
                    }
                } else {
                    if (LookingPositionTracker.lookingAtSouthernHalf) {
                        shape = e.getPositiveOutlineShape();
                    } else {
                        shape = e.getNegativeOutlineShape();
                    }
                }
            } else {
                shape = Shapes.block();
            }

            WorldRendererAccessor.drawCuboidShapeOutline(matrices, vertexConsumer, shape, pos.getX() - cameraX, pos.getY() - cameraY, pos.getZ() - cameraZ, 0.0F, 0.0F, 0.0F, 0.4F);
            ci.cancel();
        }
    }
}
