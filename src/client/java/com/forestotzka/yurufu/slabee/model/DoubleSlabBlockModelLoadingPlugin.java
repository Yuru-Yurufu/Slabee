package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.Slabee;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.block.Block;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DoubleSlabBlockModelLoadingPlugin implements ModelLoadingPlugin {

    @Override
    public void onInitializeModelLoader(ModelLoadingPlugin.Context context) {
        context.modifyModelOnLoad().register((original, ctx) -> {
            final ModelIdentifier id = ctx.topLevelId();
            if (id != null && id.id().equals(Identifier.of(Slabee.MOD_ID, "double_slab_block"))) {
                String[] ss = id.getVariant().split(",");
                Block positiveSlab = null;
                Block negativeSlab = null;
                for (String s : ss) {
                    String[] keyValue = s.split("=");
                    if (keyValue[0].equals("positive_slab") && !keyValue[1].equals("normal") && !keyValue[1].equals("non_opaque")) {
                        positiveSlab = Registries.BLOCK.get(Identifier.of("slabee", keyValue[1] + "_slab"));
                    } else if (keyValue[0].equals("negative_slab") && !keyValue[1].equals("normal") && !keyValue[1].equals("non_opaque")) {
                        negativeSlab = Registries.BLOCK.get(Identifier.of("slabee", keyValue[1] + "_slab"));
                    }
                }

                return new DoubleSlabBlockModel(positiveSlab, negativeSlab);
                //return original;
            } else {
                return original;
            }
        });
    }
}
