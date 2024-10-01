package com.forestotzka.yurufu.sloves;

import com.forestotzka.yurufu.sloves.models.DoubleSlabBlockModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.util.ModelIdentifier;

@Environment(EnvType.CLIENT)
public class ModModelLoader implements ModelLoadingPlugin {
    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        pluginContext.modifyModelOnLoad().register((original, context) -> {
            final ModelIdentifier id = context.topLevelId();
            if (id == null) {
                return original;
            }
            String blockId = id.id().toString();
            String[] blockProperties = id.getVariant().split(",");
            String positiveSlab = "";
            String negativeSlab = "";
            //System.out.println("blockId: " + blockId);
            if (blockId.contains("vertical_slab")) {
                /*for (String pair : blockProperties) {
                    String[] keyValue = pair.split("=");
                    if (keyValue[0].equals("positive_slab")) {
                        positiveSlab = keyValue[1].replace("_vertical_slab", "").split("__")[1];
                    } else if (keyValue[0].equals("negative_slab")) {
                        negativeSlab = keyValue[1].replace("_vertical_slab", "").split("__")[1];
                    }
                }
                return new DoubleSlabBlockModel(positiveSlab, negativeSlab);*/
                return original;
            } else if (blockId.equals("sloves:double_slab_block")) {
                for (String pair : blockProperties) {
                    String[] keyValue = pair.split("=");
                    if (keyValue[0].equals("top_slab")) {
                        positiveSlab = keyValue[1];
                    } else if (keyValue[0].equals("bottom_slab")) {
                        negativeSlab = keyValue[1];
                    }
                }
                return new DoubleSlabBlockModel(positiveSlab, negativeSlab);
            } else {
                return original;
            }
        });
    }
}
