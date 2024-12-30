package com.forestotzka.yurufu.slabee.mixin;

import com.forestotzka.yurufu.slabee.block.DoubleSlabBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.List;

@Mixin(WorldRenderMixin.class)
public class WorldRenderMixin {
    /*@Shadow
    @Final
    private List<ChunkBuilder.BuiltChunk> builtChunks;*/

    /*@Inject(method = "renderLayer", at = @At("HEAD"))
    private void injectCustomSorting(RenderLayer renderLayer, double x, double y, double z, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
        if (renderLayer == RenderLayer.getTranslucent()) {
            MinecraftClient client = MinecraftClient.getInstance();
            World world = client.world;
            if (world == null) return;

            Profiler profiler = world.getProfiler();
            profiler.push("custom_translucent_sort");

            // カスタムブロックエンティティを収集
            List<DoubleSlabBlockEntity> customEntities = world.getEntitiesByType()
                    .stream()
                    .filter(entity -> entity instanceof DoubleSlabBlockEntity)
                    .map(entity -> (DoubleSlabBlockEntity) entity)
                    .toList();

            // 距離を基準にソート
            Vec3d cameraPos = client.gameRenderer.getCamera().getPos();
            customEntities.sort((a, b) -> {
                double distA = a.getPos().getSquaredDistance(cameraPos);
                double distB = b.getPos().getSquaredDistance(cameraPos);
                return Double.compare(distB, distA); // 遠い順
            });

            // ソート結果を使用して描画キューに追加
            for (DoubleSlabBlockEntity entity : customEntities) {
                // 必要に応じて、描画リストやソートリストに追加処理
            }

            profiler.pop();
        }
    }*/
}
