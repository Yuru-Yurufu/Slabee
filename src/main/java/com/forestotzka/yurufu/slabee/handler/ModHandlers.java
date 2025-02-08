package com.forestotzka.yurufu.slabee.handler;

import com.forestotzka.yurufu.slabee.Slabee;

public class ModHandlers {
    public static void register() {
        Slabee.LOGGER.info("Registering Mod Events for " + Slabee.MOD_ID);

        BreakBlockHandler.register();
    }
}
