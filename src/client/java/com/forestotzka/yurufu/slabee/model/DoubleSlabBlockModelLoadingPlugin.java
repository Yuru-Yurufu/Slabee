package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.Slabee;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

@Environment(EnvType.CLIENT)
public class DoubleSlabBlockModelLoadingPlugin implements ModelLoadingPlugin {
    public static final ModelIdentifier DOUBLE_SLAB_BLOCK_MODEL = new ModelIdentifier(Identifier.of(Slabee.MOD_ID, "double_slab_block"), "");

    @Override
    public void onInitializeModelLoader(ModelLoadingPlugin.Context context) {
        context.modifyModelOnLoad().register((original, ctx) -> {
            final ModelIdentifier id = ctx.topLevelId();
            //System.out.println(id.toString());
            if (id != null && id.id().equals(Identifier.of(Slabee.MOD_ID, "double_slab_block"))) {
                String[] ss = id.getVariant().split(",");
                //String positiveSlabPath = "slabee:block/red_stained_glass_slab_top";
                //String negativeSlabPath = "slabee:block/red_stained_glass_slab";
                //Block positiveSlab = Registries.BLOCK.get(Identifier.of("slabee", "glass_slab"));
                //Block negativeSlab = Registries.BLOCK.get(Identifier.of("slabee", "glass_slab"));
                Block positiveSlab = null;
                Block negativeSlab = null;
                for (String s : ss) {
                    String[] keyValue = s.split("=");
                    if (keyValue[0].equals("positive_slab") && !keyValue[1].equals("normal") && !keyValue[1].equals("non_opaque")) {
                        //positiveSlabPath = "slabee:block/" + keyValue[1] + "_slab_top";
                        positiveSlab = Registries.BLOCK.get(Identifier.of("slabee", keyValue[1] + "_slab"));
                    } else if (keyValue[0].equals("negative_slab") && !keyValue[1].equals("normal") && !keyValue[1].equals("non_opaque")) {
                        //negativeSlabPath = "slabee:block/" + keyValue[1] + "_slab";
                        negativeSlab = Registries.BLOCK.get(Identifier.of("slabee", keyValue[1] + "_slab"));
                    }
                }

                //return new DoubleSlabBlockModel(positiveSlabPath, negativeSlabPath);
                //return new DoubleSlabBlockModel(positiveSlab, negativeSlab);
                return original;
            } else {
                return original;
            }
        });
    }
}
