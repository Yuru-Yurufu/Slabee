package com.forestotzka.yurufu.slabee.listener;

import com.forestotzka.yurufu.slabee.Slabee;
import com.forestotzka.yurufu.slabee.model.AbstractConnectGlassModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

@Environment(EnvType.CLIENT)
public class ResourceReloadListener implements SimpleSynchronousResourceReloadListener {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Slabee.MOD_ID, "reload");

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        // ThreadLocal プールをクリア → 別スレッド生成分も捨てる
        AbstractConnectGlassModel.clearBuilderPool();
    }

    public static void register() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new ResourceReloadListener());
    }
}
