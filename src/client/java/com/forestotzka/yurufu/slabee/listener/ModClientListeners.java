package com.forestotzka.yurufu.slabee.listener;

import com.forestotzka.yurufu.slabee.Slabee;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModClientListeners {
    public static void register() {
        Slabee.LOGGER.info("Registering Mod Client Listeners for " + Slabee.MOD_ID);

        ResourceReloadListener.register();
    }
}
