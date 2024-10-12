package com.forestotzka.yurufu.sloves;

import com.forestotzka.yurufu.sloves.models.DoubleSlabBlockModel;
import com.forestotzka.yurufu.sloves.models.DoubleVerticalSlabBlockModel;
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
            if (blockId.equals("sloves:double_vertical_slab_block")) {
                //return original;
                String axis = "x";
                for (String pair : blockProperties) {
                    String[] keyValue = pair.split("=");
                    if (keyValue[0].equals("positive_slab")) {
                        positiveSlab = keyValue[1];
                    } else if (keyValue[0].equals("negative_slab")) {
                        negativeSlab = keyValue[1];
                    } else if (keyValue[0].equals("axis")) {
                        axis = keyValue[1];
                    }
                }
                return new DoubleVerticalSlabBlockModel(positiveSlab, negativeSlab, axis);
            } else if (blockId.equals("sloves:double_slab_block")) {
                return original;
                /*for (String pair : blockProperties) {
                    String[] keyValue = pair.split("=");
                    if (keyValue[0].equals("top_slab")) {
                        positiveSlab = keyValue[1];
                    } else if (keyValue[0].equals("bottom_slab")) {
                        negativeSlab = keyValue[1];
                    }
                }
                return new DoubleSlabBlockModel(positiveSlab, negativeSlab);*/
            } else {
                return original;
            }
        });
    }
}
