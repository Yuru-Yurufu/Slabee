package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.Slabee;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;

@Environment(EnvType.CLIENT)
public class ModModelLoaders {
    public static void register() {
        Slabee.LOGGER.info("Registering Mod Model Loaders for " + Slabee.MOD_ID);

        ModelLoadingPlugin.register(new DoubleSlabBlockModelLoadingPlugin());
    }
}
