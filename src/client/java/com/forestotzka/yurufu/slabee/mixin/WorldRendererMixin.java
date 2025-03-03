package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.LookingPositionTracker;
import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.DoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Shadow
    private @Final MinecraftClient client;

    @Shadow @Nullable private ClientWorld world;

    @Inject(method = "drawBlockOutline", at = @At("HEAD"), cancellable = true)
    private void onDrawBlockOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity, double cameraX, double cameraY, double cameraZ, BlockPos pos, BlockState state, CallbackInfo ci) {
        PlayerEntity player = client.player;
        if (player != null && player.isSneaking() && SlabeeUtils.isDoubleSlab(state) && world != null) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            VoxelShape shape;
            if (blockEntity instanceof DoubleSlabBlockEntity) {
                if (LookingPositionTracker.lookingAtUpperHalf) {
                    shape = Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);
                } else {
                    shape = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
                }
            } else if (blockEntity instanceof DoubleVerticalSlabBlockEntity e) {
                if (e.isX()) {
                    if (LookingPositionTracker.lookingAtEasternHalf) {
                        shape = Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 16.0, 16.0);
                    } else {
                        shape = Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 16.0, 16.0);
                    }
                } else {
                    if (LookingPositionTracker.lookingAtSouthernHalf) {
                        shape = Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 16.0, 16.0);
                    } else {
                        shape = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 8.0);
                    }
                }
            } else {
                shape = VoxelShapes.fullCube();
            }

            WorldRendererAccessor.drawCuboidShapeOutline(matrices, vertexConsumer, shape, pos.getX() - cameraX, pos.getY() - cameraY, pos.getZ() - cameraZ, 0.0F, 0.0F, 0.0F, 0.4F);
            ci.cancel();
        }
    }
}
