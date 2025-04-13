package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.Slabee;
import com.forestotzka.yurufu.slabee.block.TranslucentSlabBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.block.Block;
import net.minecraft.block.TranslucentBlock;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DoubleSlabBlockModelLoadingPlugin implements ModelLoadingPlugin {

    @Override
    public void onInitializeModelLoader(Context context) {
        context.modifyModelOnLoad().register((original, ctx) -> {
            final ModelIdentifier id = ctx.topLevelId();
            if (id == null) {
                return original;
            }

            Identifier i = id.id();

            if (i.equals(Identifier.of(Slabee.MOD_ID, "double_slab_block"))) {
                String[] ss = id.getVariant().split(",");
                Block positiveSlab = null;
                Block negativeSlab = null;

                for (String s : ss) {
                    String[] keyValue = s.split("=");
                    if (keyValue[0].equals("positive_slab") && !keyValue[1].equals("normal") && !keyValue[1].equals("non_opaque")) {
                        positiveSlab = Registries.BLOCK.get(Identifier.of(Slabee.MOD_ID, keyValue[1] + "_slab"));
                    } else if (keyValue[0].equals("negative_slab") && !keyValue[1].equals("normal") && !keyValue[1].equals("non_opaque")) {
                        negativeSlab = Registries.BLOCK.get(Identifier.of(Slabee.MOD_ID, keyValue[1] + "_slab"));
                    }
                }

                return new DoubleSlabBlockModel(positiveSlab, negativeSlab);
            } else if (i.equals(Identifier.of(Slabee.MOD_ID, "double_vertical_slab_block"))) {
                String[] ss = id.getVariant().split(",");
                Block positiveSlab = null;
                Block negativeSlab = null;
                boolean isX = false;

                for (String s : ss) {
                    String[] keyValue = s.split("=");
                    if (keyValue[0].equals("positive_slab") && !keyValue[1].equals("normal") && !keyValue[1].equals("non_opaque")) {
                        positiveSlab = Registries.BLOCK.get(Identifier.of(Slabee.MOD_ID, keyValue[1] + "_vertical_slab"));
                    } else if (keyValue[0].equals("negative_slab") && !keyValue[1].equals("normal") && !keyValue[1].equals("non_opaque")) {
                        negativeSlab = Registries.BLOCK.get(Identifier.of(Slabee.MOD_ID, keyValue[1] + "_vertical_slab"));
                    } else if (keyValue[0].equals("axis")) {
                        isX = keyValue[1].equals("x");
                    }
                }

                return new DoubleVerticalSlabBlockModel(positiveSlab, negativeSlab, isX);
            }

            return original;
        });

        context.modifyModelBeforeBake().register((original, ctx) -> {
            final ModelIdentifier id = ctx.topLevelId();
            if (id == null) {
                return original;
            }

            Identifier i = id.id();

            Block b = Registries.BLOCK.get(i);
            if (b instanceof TranslucentBlock) {
                //return new
            } else if (b instanceof TranslucentSlabBlock) {
                String[] ss = id.getVariant().split(",");

                for (String s : ss) {
                    String[] keyValue = s.split("=");
                    if (keyValue[0].equals("type")) {
                        if (keyValue[1].equals("top")) {
                            return new TranslucentSlabBlockModel(b, true);
                        } else if (keyValue[1].equals("bottom")) {
                            return new TranslucentSlabBlockModel(b, false);
                        }

                        break;
                    }
                }
            }

            return original;
        });
    }
}
