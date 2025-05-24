package com.forestotzka.yurufu.slabee.handler;

import com.forestotzka.yurufu.slabee.Slabee;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModClientHandlers {
    public static void register() {
        Slabee.LOGGER.info("Registering Mod Client Handlers for " + Slabee.MOD_ID);

        ParticleRenderHandler.register();
    }
}
