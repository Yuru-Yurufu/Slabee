package com.forestotzka.yurufu.slabee.listener;

import com.forestotzka.yurufu.slabee.Slabee;
import com.forestotzka.yurufu.slabee.model.AbstractConnectGlassModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ResourceReloadListener implements SimpleSynchronousResourceReloadListener {
    private static final Identifier ID = Identifier.of(Slabee.MOD_ID, "reload");

    @Override
    public Identifier getFabricId() {
        return ID;
    }

    @Override
    public void reload(ResourceManager manager) {
        // ThreadLocal プールをクリア → 別スレッド生成分も捨てる
        AbstractConnectGlassModel.clearBuilderPool();
    }

    public static void register() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new ResourceReloadListener());
    }
}
