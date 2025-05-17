package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.Slabee;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

public class GlassSprites {
    private static final int GLASS_PATTERN_COUNT = 21;
    private static final int STAINED_GLASS_PATTERN_COUNT = 25;
    private static final int SLAB_PATTERN_COUNT = 169;

    private static final SpriteIdentifier[] GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] WHITE_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] WHITE_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] WHITE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] LIGHT_GRAY_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] LIGHT_GRAY_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] GRAY_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] GRAY_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] GRAY_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] BLACK_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] BLACK_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] BLACK_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] BROWN_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] BROWN_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] BROWN_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] RED_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] RED_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] RED_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] ORANGE_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] ORANGE_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] ORANGE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] YELLOW_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] YELLOW_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] YELLOW_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] LIME_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] LIME_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] LIME_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] GREEN_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] GREEN_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] GREEN_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] CYAN_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] CYAN_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] CYAN_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] LIGHT_BLUE_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] LIGHT_BLUE_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] BLUE_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] BLUE_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] BLUE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] PURPLE_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] PURPLE_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] PURPLE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] MAGENTA_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] MAGENTA_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] MAGENTA_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] PINK_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] PINK_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] PINK_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] TINTED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] TINTED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] TINTED_GLASS_VERTICAL_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];

    static {
        for (int i = 0; i < GLASS_PATTERN_COUNT-1; i++) {
            GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/glass/" + i));
        }
        for (int i = 0; i < STAINED_GLASS_PATTERN_COUNT-1; i++) {
            WHITE_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/white_stained_glass/" + i));
            LIGHT_GRAY_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_gray_stained_glass/" + i));
            GRAY_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/gray_stained_glass/" + i));
            BLACK_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/black_stained_glass/" + i));
            BROWN_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/brown_stained_glass/" + i));
            RED_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/red_stained_glass/" + i));
            ORANGE_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/orange_stained_glass/" + i));
            YELLOW_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/yellow_stained_glass/" + i));
            LIME_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/lime_stained_glass/" + i));
            GREEN_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/green_stained_glass/" + i));
            CYAN_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/cyan_stained_glass/" + i));
            LIGHT_BLUE_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_blue_stained_glass/" + i));
            BLUE_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/blue_stained_glass/" + i));
            PURPLE_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/purple_stained_glass/" + i));
            MAGENTA_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/magenta_stained_glass/" + i));
            PINK_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/pink_stained_glass/" + i));
            TINTED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/tinted_glass/" + i));
        }
        GLASS_SPRITE_IDS[GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/glass/center"));
        WHITE_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/white_stained_glass/center"));
        LIGHT_GRAY_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_gray_stained_glass/center"));
        GRAY_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/gray_stained_glass/center"));
        BLACK_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/black_stained_glass/center"));
        BROWN_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/brown_stained_glass/center"));
        RED_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/red_stained_glass/center"));
        ORANGE_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/orange_stained_glass/center"));
        YELLOW_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/yellow_stained_glass/center"));
        LIME_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/lime_stained_glass/center"));
        GREEN_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/green_stained_glass/center"));
        CYAN_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/cyan_stained_glass/center"));
        LIGHT_BLUE_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_blue_stained_glass/center"));
        BLUE_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/blue_stained_glass/center"));
        PURPLE_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/purple_stained_glass/center"));
        MAGENTA_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/magenta_stained_glass/center"));
        PINK_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/pink_stained_glass/center"));
        TINTED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/tinted_glass/center"));

        for (int i = 0; i < SLAB_PATTERN_COUNT; i++) {
            GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/glass_slab/" + i));
            WHITE_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/white_stained_glass_slab/" + i));
            LIGHT_GRAY_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_gray_stained_glass_slab/" + i));
            GRAY_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/gray_stained_glass_slab/" + i));
            BLACK_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/black_stained_glass_slab/" + i));
            BROWN_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/brown_stained_glass_slab/" + i));
            RED_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/red_stained_glass_slab/" + i));
            ORANGE_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/orange_stained_glass_slab/" + i));
            YELLOW_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/yellow_stained_glass_slab/" + i));
            LIME_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/lime_stained_glass_slab/" + i));
            GREEN_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/green_stained_glass_slab/" + i));
            CYAN_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/cyan_stained_glass_slab/" + i));
            LIGHT_BLUE_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_blue_stained_glass_slab/" + i));
            BLUE_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/blue_stained_glass_slab/" + i));
            PURPLE_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/purple_stained_glass_slab/" + i));
            MAGENTA_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/magenta_stained_glass_slab/" + i));
            PINK_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/pink_stained_glass_slab/" + i));
            TINTED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/tinted_glass_slab/" + i));

            GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/glass_vertical_slab/" + i));
            WHITE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/white_stained_glass_vertical_slab/" + i));
            LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_gray_stained_glass_vertical_slab/" + i));
            GRAY_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/gray_stained_glass_vertical_slab/" + i));
            BLACK_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/black_stained_glass_vertical_slab/" + i));
            BROWN_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/brown_stained_glass_vertical_slab/" + i));
            RED_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/red_stained_glass_vertical_slab/" + i));
            ORANGE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/orange_stained_glass_vertical_slab/" + i));
            YELLOW_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/yellow_stained_glass_vertical_slab/" + i));
            LIME_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/lime_stained_glass_vertical_slab/" + i));
            GREEN_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/green_stained_glass_vertical_slab/" + i));
            CYAN_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/cyan_stained_glass_vertical_slab/" + i));
            LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_blue_stained_glass_vertical_slab/" + i));
            BLUE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/blue_stained_glass_vertical_slab/" + i));
            PURPLE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/purple_stained_glass_vertical_slab/" + i));
            MAGENTA_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/magenta_stained_glass_vertical_slab/" + i));
            PINK_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/pink_stained_glass_vertical_slab/" + i));
            TINTED_GLASS_VERTICAL_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/tinted_glass_vertical_slab/" + i));
        }
    }

    /*public static SpriteIdentifier[] getGlassSpriteIds() {
        return GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getGlassSlabSpriteIds() {
        return GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getGlassVerticalSlabSpriteIds() {
        return GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getWhiteStainedGlassSpriteIds() {
        return WHITE_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getWhiteStainedGlassSlabSpriteIds() {
        return WHITE_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getWhiteStainedGlassVerticalSlabSpriteIds() {
        return WHITE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getLightGrayStainedGlassSpriteIds() {
        return LIGHT_GRAY_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getLightGrayStainedGlassSlabSpriteIds() {
        return LIGHT_GRAY_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getLightGrayStainedGlassVerticalSlabSpriteIds() {
        return LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getGrayStainedGlassSpriteIds() {
        return GRAY_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getGrayStainedGlassSlabSpriteIds() {
        return GRAY_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getGrayStainedGlassVerticalSlabSpriteIds() {
        return GRAY_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getBlackStainedGlassSpriteIds() {
        return BLACK_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getBlackStainedGlassSlabSpriteIds() {
        return BLACK_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getBlackStainedGlassVerticalSlabSpriteIds() {
        return BLACK_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getBrownStainedGlassSpriteIds() {
        return BROWN_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getBrownStainedGlassSlabSpriteIds() {
        return BROWN_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getBrownStainedGlassVerticalSlabSpriteIds() {
        return BROWN_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getRedStainedGlassSpriteIds() {
        return RED_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getRedStainedGlassSlabSpriteIds() {
        return RED_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getRedStainedGlassVerticalSlabSpriteIds() {
        return RED_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getOrangeStainedGlassSpriteIds() {
        return ORANGE_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getOrangeStainedGlassSlabSpriteIds() {
        return ORANGE_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getOrangeStainedGlassVerticalSlabSpriteIds() {
        return ORANGE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getYellowStainedGlassSpriteIds() {
        return YELLOW_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getYellowStainedGlassSlabSpriteIds() {
        return YELLOW_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getYellowStainedGlassVerticalSlabSpriteIds() {
        return YELLOW_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getLimeStainedGlassSpriteIds() {
        return LIME_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getLimeStainedGlassSlabSpriteIds() {
        return LIME_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getLimeStainedGlassVerticalSlabSpriteIds() {
        return LIME_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getGreenStainedGlassSpriteIds() {
        return GREEN_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getGreenStainedGlassSlabSpriteIds() {
        return GREEN_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getGreenStainedGlassVerticalSlabSpriteIds() {
        return GREEN_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getCyanStainedGlassSpriteIds() {
        return CYAN_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getCyanStainedGlassSlabSpriteIds() {
        return CYAN_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getCyanStainedGlassVerticalSlabSpriteIds() {
        return CYAN_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getLightBlueStainedGlassSpriteIds() {
        return LIGHT_BLUE_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getLightBlueStainedGlassSlabSpriteIds() {
        return LIGHT_BLUE_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getLightBlueStainedGlassVerticalSlabSpriteIds() {
        return LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getBlueStainedGlassSpriteIds() {
        return BLUE_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getBlueStainedGlassSlabSpriteIds() {
        return BLUE_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getBlueStainedGlassVerticalSlabSpriteIds() {
        return BLUE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getPurpleStainedGlassSpriteIds() {
        return PURPLE_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getPurpleStainedGlassSlabSpriteIds() {
        return PURPLE_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getPurpleStainedGlassVerticalSlabSpriteIds() {
        return PURPLE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getMagentaStainedGlassSpriteIds() {
        return MAGENTA_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getMagentaStainedGlassSlabSpriteIds() {
        return MAGENTA_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getMagentaStainedGlassVerticalSlabSpriteIds() {
        return MAGENTA_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getPinkStainedGlassSpriteIds() {
        return PINK_STAINED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getPinkStainedGlassSlabSpriteIds() {
        return PINK_STAINED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getPinkStainedGlassVerticalSlabSpriteIds() {
        return PINK_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getTintedGlassSpriteIds() {
        return TINTED_GLASS_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getTintedGlassSlabSpriteIds() {
        return TINTED_GLASS_SLAB_SPRITE_IDS;
    }

    public static SpriteIdentifier[] getTintedGlassVerticalSlabSpriteIds() {
        return TINTED_GLASS_VERTICAL_SLAB_SPRITE_IDS;
    }*/

    public static SpriteIdentifier getFullBlockSpriteIdentifier(int index, Block slab) {
        if (slab == ModBlocks.WHITE_STAINED_GLASS_SLAB) {
            return WHITE_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.LIGHT_GRAY_STAINED_GLASS_SLAB) {
            return LIGHT_GRAY_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.GRAY_STAINED_GLASS_SLAB) {
            return GRAY_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.BLACK_STAINED_GLASS_SLAB) {
            return BLACK_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.BROWN_STAINED_GLASS_SLAB) {
            return BROWN_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.RED_STAINED_GLASS_SLAB) {
            return RED_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.ORANGE_STAINED_GLASS_SLAB) {
            return ORANGE_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.YELLOW_STAINED_GLASS_SLAB) {
            return YELLOW_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.LIME_STAINED_GLASS_SLAB) {
            return LIME_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.GREEN_STAINED_GLASS_SLAB) {
            return GREEN_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.CYAN_STAINED_GLASS_SLAB) {
            return CYAN_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.LIGHT_BLUE_STAINED_GLASS_SLAB) {
            return LIGHT_BLUE_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.BLUE_STAINED_GLASS_SLAB) {
            return BLUE_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.PURPLE_STAINED_GLASS_SLAB) {
            return PURPLE_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.MAGENTA_STAINED_GLASS_SLAB) {
            return MAGENTA_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.PINK_STAINED_GLASS_SLAB) {
            return PINK_STAINED_GLASS_SPRITE_IDS[index];
        } else if (slab == ModBlocks.TINTED_GLASS_SLAB) {
            return TINTED_GLASS_SPRITE_IDS[index];
        } else {
            return GLASS_SPRITE_IDS[index];
        }
    }

    public static SpriteIdentifier getSlabSpriteIdentifier(int index, Block slab) {
        if (slab == ModBlocks.WHITE_STAINED_GLASS_SLAB) {
            return WHITE_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.LIGHT_GRAY_STAINED_GLASS_SLAB) {
            return LIGHT_GRAY_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.GRAY_STAINED_GLASS_SLAB) {
            return GRAY_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.BLACK_STAINED_GLASS_SLAB) {
            return BLACK_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.BROWN_STAINED_GLASS_SLAB) {
            return BROWN_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.RED_STAINED_GLASS_SLAB) {
            return RED_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.ORANGE_STAINED_GLASS_SLAB) {
            return ORANGE_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.YELLOW_STAINED_GLASS_SLAB) {
            return YELLOW_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.LIME_STAINED_GLASS_SLAB) {
            return LIME_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.GREEN_STAINED_GLASS_SLAB) {
            return GREEN_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.CYAN_STAINED_GLASS_SLAB) {
            return CYAN_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.LIGHT_BLUE_STAINED_GLASS_SLAB) {
            return LIGHT_BLUE_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.BLUE_STAINED_GLASS_SLAB) {
            return BLUE_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.PURPLE_STAINED_GLASS_SLAB) {
            return PURPLE_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.MAGENTA_STAINED_GLASS_SLAB) {
            return MAGENTA_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.PINK_STAINED_GLASS_SLAB) {
            return PINK_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (slab == ModBlocks.TINTED_GLASS_SLAB) {
            return TINTED_GLASS_SLAB_SPRITE_IDS[index];
        } else {
            return GLASS_SLAB_SPRITE_IDS[index];
        }
    }

    public static SpriteIdentifier getVerticalSlabSpriteIdentifier(int index, Block verticalSlab) {
        if (verticalSlab == ModBlocks.WHITE_STAINED_GLASS_VERTICAL_SLAB) {
            return WHITE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB) {
            return LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.GRAY_STAINED_GLASS_VERTICAL_SLAB) {
            return GRAY_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.BLACK_STAINED_GLASS_VERTICAL_SLAB) {
            return BLACK_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.BROWN_STAINED_GLASS_VERTICAL_SLAB) {
            return BROWN_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.RED_STAINED_GLASS_VERTICAL_SLAB) {
            return RED_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.ORANGE_STAINED_GLASS_VERTICAL_SLAB) {
            return ORANGE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.YELLOW_STAINED_GLASS_VERTICAL_SLAB) {
            return YELLOW_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.LIME_STAINED_GLASS_VERTICAL_SLAB) {
            return LIME_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.GREEN_STAINED_GLASS_VERTICAL_SLAB) {
            return GREEN_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.CYAN_STAINED_GLASS_VERTICAL_SLAB) {
            return CYAN_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB) {
            return LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.BLUE_STAINED_GLASS_VERTICAL_SLAB) {
            return BLUE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.PURPLE_STAINED_GLASS_VERTICAL_SLAB) {
            return PURPLE_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.MAGENTA_STAINED_GLASS_VERTICAL_SLAB) {
            return MAGENTA_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.PINK_STAINED_GLASS_VERTICAL_SLAB) {
            return PINK_STAINED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else if (verticalSlab == ModBlocks.TINTED_GLASS_VERTICAL_SLAB) {
            return TINTED_GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        } else {
            return GLASS_VERTICAL_SLAB_SPRITE_IDS[index];
        }
    }
}
