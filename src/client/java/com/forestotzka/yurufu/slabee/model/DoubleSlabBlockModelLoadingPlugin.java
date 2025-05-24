package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.ModConfig;
import com.forestotzka.yurufu.slabee.Slabee;
import com.forestotzka.yurufu.slabee.block.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.block.*;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Environment(EnvType.CLIENT)
public class DoubleSlabBlockModelLoadingPlugin implements ModelLoadingPlugin {
    record SlabVariantKey(Block positiveSlab, Block negativeSlab, int axis) {}

    private static final Map<SlabVariantKey, UnbakedModel> MODEL_CACHE = new ConcurrentHashMap<>();

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
                Block parsedPositiveSlab = null;
                Block parsedNegativeSlab = null;

                for (String s : ss) {
                    String[] keyValue = s.split("=");
                    if (keyValue[0].equals("positive_slab")) {
                        parsedPositiveSlab = variantStrToSlab(keyValue[1]);
                    } else if (keyValue[0].equals("negative_slab")) {
                        parsedNegativeSlab = variantStrToSlab(keyValue[1]);
                    }
                }

                final Block positiveSlab = parsedPositiveSlab;
                final Block negativeSlab = parsedNegativeSlab;

                SlabVariantKey key = new SlabVariantKey(positiveSlab, negativeSlab, 0);
                return MODEL_CACHE.computeIfAbsent(key, k -> {
                    if (ModConfig.INSTANCE.connectGlassTextures) {
                        return new DoubleSlabBlockConnectGlassModel(positiveSlab, negativeSlab);
                    } else {
                        return new DoubleSlabBlockModel(positiveSlab, negativeSlab);
                    }
                });
            } else if (i.equals(Identifier.of(Slabee.MOD_ID, "double_vertical_slab_block"))) {
                String[] ss = id.getVariant().split(",");
                Block parsedPositiveSlab = null;
                Block parsedNegativeSlab = null;
                boolean parsedIsX = false;

                for (String s : ss) {
                    String[] keyValue = s.split("=");
                    switch (keyValue[0]) {
                        case "positive_slab" -> parsedPositiveSlab = variantStrToVerticalSlab(keyValue[1]);
                        case "negative_slab" -> parsedNegativeSlab = variantStrToVerticalSlab(keyValue[1]);
                        case "axis" -> parsedIsX = keyValue[1].equals("x");
                    }
                }

                final Block positiveSlab = parsedPositiveSlab;
                final Block negativeSlab = parsedNegativeSlab;
                final boolean isX = parsedIsX;

                SlabVariantKey key = new SlabVariantKey(positiveSlab, negativeSlab, isX ? 1 : 2);
                return MODEL_CACHE.computeIfAbsent(key, k -> {
                    if (ModConfig.INSTANCE.connectGlassTextures) {
                        if (isX) {
                            return new DoubleVerticalSlabBlockConnectGlassModelX(positiveSlab, negativeSlab);
                        } else {
                            return new DoubleVerticalSlabBlockConnectGlassModelZ(positiveSlab, negativeSlab);
                        }
                    } else {
                        return new DoubleVerticalSlabBlockModel(positiveSlab, negativeSlab, isX);
                    }
                });
            }

            return original;
        });

        context.modifyModelBeforeBake().register((original, ctx) -> {
            final ModelIdentifier id = ctx.topLevelId();
            if (id == null) {
                return original;
            }

            Identifier i = id.id();

            if (id.getVariant().equals("inventory")) {
                return original;
            }

            Block b = Registries.BLOCK.get(i);
            if (b instanceof TranslucentBlock) {
                return ModConfig.INSTANCE.connectGlassTextures && isGlassFamily(b)
                        ? new TranslucentBlockConnectGlassModel(b)
                        : new TranslucentBlockModel(b);
            } else if (b instanceof TranslucentSlabBlock) {
                String[] ss = id.getVariant().split(",");

                for (String s : ss) {
                    String[] keyValue = s.split("=");
                    if (keyValue[0].equals("type")) {
                        if (keyValue[1].equals("top")) {
                            SlabVariantKey key = new SlabVariantKey(b, null, 0);
                            return MODEL_CACHE.computeIfAbsent(key, k -> {
                                if (ModConfig.INSTANCE.connectGlassTextures && isGlassSlabFamily(b)) {
                                    return new DoubleSlabBlockConnectGlassModel(b, null);
                                } else {
                                    return new TranslucentSlabBlockModel(b, true);
                                }
                            });
                        } else if (keyValue[1].equals("bottom")) {
                            SlabVariantKey key = new SlabVariantKey(null, b, 0);
                            return MODEL_CACHE.computeIfAbsent(key, k -> {
                                if (ModConfig.INSTANCE.connectGlassTextures && isGlassSlabFamily(b)) {
                                    return new DoubleSlabBlockConnectGlassModel(null, b);
                                } else {
                                    return new TranslucentSlabBlockModel(b, false);
                                }
                            });
                        }

                        break;
                    }
                }
            } else if (b instanceof TranslucentVerticalSlabBlock) {
                String[] ss = id.getVariant().split(",");

                for (String s : ss) {
                    String[] keyValue = s.split("=");
                    if (keyValue[0].equals("facing")) {
                        switch (keyValue[1]) {
                            case "east" -> {
                                SlabVariantKey key = new SlabVariantKey(b, null, 1);
                                return MODEL_CACHE.computeIfAbsent(key, k ->
                                        ModConfig.INSTANCE.connectGlassTextures && isGlassVerticalSlabFamily(b)
                                        ? new DoubleVerticalSlabBlockConnectGlassModelX(b, null)
                                        : new DoubleVerticalSlabBlockModel(b, null, true));
                            }
                            case "south" -> {
                                SlabVariantKey key = new SlabVariantKey(b, null, 2);
                                return MODEL_CACHE.computeIfAbsent(key, k ->
                                ModConfig.INSTANCE.connectGlassTextures && isGlassVerticalSlabFamily(b)
                                ? new DoubleVerticalSlabBlockConnectGlassModelZ(b, null)
                                : new DoubleVerticalSlabBlockModel(b, null, false));
                            }
                            case "west" -> {
                                SlabVariantKey key = new SlabVariantKey(null, b, 1);
                                return MODEL_CACHE.computeIfAbsent(key, k ->
                                ModConfig.INSTANCE.connectGlassTextures && isGlassVerticalSlabFamily(b)
                                ? new DoubleVerticalSlabBlockConnectGlassModelX(null, b)
                                : new DoubleVerticalSlabBlockModel(null, b, true));
                            }
                            case "north" -> {
                                SlabVariantKey key = new SlabVariantKey(null, b, 2);
                                return MODEL_CACHE.computeIfAbsent(key, k ->
                                ModConfig.INSTANCE.connectGlassTextures && isGlassVerticalSlabFamily(b)
                                ? new DoubleVerticalSlabBlockConnectGlassModelZ(null, b)
                                : new DoubleVerticalSlabBlockModel(null, b, false));
                            }
                        }

                        break;
                    }
                }
            }

            return original;
        });
    }

    private @Nullable Block variantStrToSlab(String block) {
        if (block.equals("normal") || block.equals("non_opaque")) {
            return null;
        } else {
            return Registries.BLOCK.get(Identifier.of(Slabee.MOD_ID, block + "_slab"));
        }
    }

    private @Nullable Block variantStrToVerticalSlab(String block) {
        if (block.equals("normal") || block.equals("non_opaque")) {
            return null;
        } else {
            return Registries.BLOCK.get(Identifier.of(Slabee.MOD_ID, block + "_vertical_slab"));
        }
    }

    private boolean isGlassFamily(Block block) {
        return block == Blocks.GLASS || block instanceof StainedGlassBlock || block == Blocks.TINTED_GLASS;
    }

    private boolean isGlassSlabFamily(Block block) {
        return block == ModBlocks.GLASS_SLAB || block instanceof StainedGlassSlabBlock || block == ModBlocks.TINTED_GLASS_SLAB;
    }

    private boolean isGlassVerticalSlabFamily(Block block) {
        return block == ModBlocks.GLASS_VERTICAL_SLAB || block instanceof StainedGlassVerticalSlabBlock || block == ModBlocks.TINTED_GLASS_VERTICAL_SLAB;
    }
}
