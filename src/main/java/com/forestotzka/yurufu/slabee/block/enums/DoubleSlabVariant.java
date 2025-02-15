package com.forestotzka.yurufu.slabee.block.enums;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.VerticalSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.StringIdentifiable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum DoubleSlabVariant implements StringIdentifiable {
    NORMAL("normal"),
    NON_OPAQUE("non_opaque"),
    WHITE_STAINED_GLASS("white_stained_glass"),
    ORANGE_STAINED_GLASS("orange_stained_glass"),
    MAGENTA_STAINED_GLASS("magenta_stained_glass"),
    LIGHT_BLUE_STAINED_GLASS("light_blue_stained_glass"),
    YELLOW_STAINED_GLASS("yellow_stained_glass"),
    LIME_STAINED_GLASS("lime_stained_glass"),
    PINK_STAINED_GLASS("pink_stained_glass"),
    GRAY_STAINED_GLASS("gray_stained_glass"),
    LIGHT_GRAY_STAINED_GLASS("light_gray_stained_glass"),
    CYAN_STAINED_GLASS("cyan_stained_glass"),
    PURPLE_STAINED_GLASS("purple_stained_glass"),
    BLUE_STAINED_GLASS("blue_stained_glass"),
    BROWN_STAINED_GLASS("brown_stained_glass"),
    GREEN_STAINED_GLASS("green_stained_glass"),
    RED_STAINED_GLASS("red_stained_glass"),
    BLACK_STAINED_GLASS("black_stained_glass"),
    TINTED_GLASS("tinted_glass");

    private static final Map<Block, DoubleSlabVariant> SLAB_VARIANT_MAP = new HashMap<>();
    private static final Map<Block, DoubleSlabVariant> VERTICAL_SLAB_VARIANT_MAP = new HashMap<>();

    static {
        SLAB_VARIANT_MAP.put(ModBlocks.WHITE_STAINED_GLASS_SLAB, WHITE_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.ORANGE_STAINED_GLASS_SLAB, ORANGE_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.MAGENTA_STAINED_GLASS_SLAB, MAGENTA_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.LIGHT_BLUE_STAINED_GLASS_SLAB, LIGHT_BLUE_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.YELLOW_STAINED_GLASS_SLAB, YELLOW_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.LIME_STAINED_GLASS_SLAB, LIME_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.PINK_STAINED_GLASS_SLAB, PINK_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.GRAY_STAINED_GLASS_SLAB, GRAY_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.LIGHT_GRAY_STAINED_GLASS_SLAB, LIGHT_GRAY_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.CYAN_STAINED_GLASS_SLAB, CYAN_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.PURPLE_STAINED_GLASS_SLAB, PURPLE_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.BLUE_STAINED_GLASS_SLAB, BLUE_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.BROWN_STAINED_GLASS_SLAB, BROWN_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.GREEN_STAINED_GLASS_SLAB, GREEN_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.RED_STAINED_GLASS_SLAB, RED_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.BLACK_STAINED_GLASS_SLAB, BLACK_STAINED_GLASS);
        SLAB_VARIANT_MAP.put(ModBlocks.TINTED_GLASS_SLAB, TINTED_GLASS);

        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.WHITE_STAINED_GLASS_VERTICAL_SLAB, WHITE_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.ORANGE_STAINED_GLASS_VERTICAL_SLAB, ORANGE_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.MAGENTA_STAINED_GLASS_VERTICAL_SLAB, MAGENTA_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB, LIGHT_BLUE_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.YELLOW_STAINED_GLASS_VERTICAL_SLAB, YELLOW_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.LIME_STAINED_GLASS_VERTICAL_SLAB, LIME_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.PINK_STAINED_GLASS_VERTICAL_SLAB, PINK_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.GRAY_STAINED_GLASS_VERTICAL_SLAB, GRAY_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB, LIGHT_GRAY_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.CYAN_STAINED_GLASS_VERTICAL_SLAB, CYAN_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.PURPLE_STAINED_GLASS_VERTICAL_SLAB, PURPLE_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.BLUE_STAINED_GLASS_VERTICAL_SLAB, BLUE_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.BROWN_STAINED_GLASS_VERTICAL_SLAB, BROWN_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.GREEN_STAINED_GLASS_VERTICAL_SLAB, GREEN_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.RED_STAINED_GLASS_VERTICAL_SLAB, RED_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.BLACK_STAINED_GLASS_VERTICAL_SLAB, BLACK_STAINED_GLASS);
        VERTICAL_SLAB_VARIANT_MAP.put(ModBlocks.TINTED_GLASS_VERTICAL_SLAB, TINTED_GLASS);
    }

    private final String name;

    DoubleSlabVariant(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static DoubleSlabVariant fromBlock(Block block) {
        DoubleSlabVariant variant = null;
        if (block instanceof SlabBlock) {
            variant = SLAB_VARIANT_MAP.get(block);
        } else if (block instanceof VerticalSlabBlock) {
            variant = VERTICAL_SLAB_VARIANT_MAP.get(block);
        }

        return Objects.requireNonNullElseGet(variant, () -> SlabeeUtils.isSeeThrough(block) ? NON_OPAQUE : NORMAL);
    }
}
