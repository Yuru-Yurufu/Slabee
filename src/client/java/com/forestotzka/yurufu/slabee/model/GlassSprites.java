package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.Slabee;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GlassSprites {
    private static final Map<Integer, Integer> SLAB_INDEX_MAP = Map.<Integer, Integer>ofEntries(
            Map.entry(70, 64),
            Map.entry(71, 65),
            Map.entry(78, 66),
            Map.entry(79, 67),
            Map.entry(86, 68),
            Map.entry(87, 69),
            Map.entry(94, 70),
            Map.entry(95, 71),
            Map.entry(102, 72),
            Map.entry(103, 73),
            Map.entry(110, 74),
            Map.entry(111, 75),
            Map.entry(118, 76),
            Map.entry(119, 77),
            Map.entry(126, 78),
            Map.entry(127, 79),
            Map.entry(148, 80),
            Map.entry(149, 81),
            Map.entry(150, 82),
            Map.entry(151, 83),
            Map.entry(156, 84),
            Map.entry(157, 85),
            Map.entry(158, 86),
            Map.entry(159, 87),
            Map.entry(180, 88),
            Map.entry(181, 89),
            Map.entry(182, 90),
            Map.entry(183, 91),
            Map.entry(188, 92),
            Map.entry(189, 93),
            Map.entry(190, 94),
            Map.entry(191, 95),
            Map.entry(214, 96),
            Map.entry(215, 97),
            Map.entry(222, 98),
            Map.entry(223, 99),
            Map.entry(246, 100),
            Map.entry(247, 101),
            Map.entry(254, 102),
            Map.entry(255, 103),
            Map.entry(296, 104),
            Map.entry(297, 105),
            Map.entry(298, 106),
            Map.entry(299, 107),
            Map.entry(300, 108),
            Map.entry(301, 109),
            Map.entry(302, 110),
            Map.entry(303, 111),
            Map.entry(312, 112),
            Map.entry(313, 113),
            Map.entry(314, 114),
            Map.entry(315, 115),
            Map.entry(316, 116),
            Map.entry(317, 117),
            Map.entry(318, 118),
            Map.entry(319, 119),
            Map.entry(366, 120),
            Map.entry(367, 121),
            Map.entry(382, 122),
            Map.entry(383, 123),
            Map.entry(444, 124),
            Map.entry(445, 125),
            Map.entry(446, 126),
            Map.entry(447, 127),
            Map.entry(510, 128),
            Map.entry(511, 129),
            Map.entry(545, 130),
            Map.entry(547, 131),
            Map.entry(549, 132),
            Map.entry(551, 133),
            Map.entry(553, 134),
            Map.entry(555, 135),
            Map.entry(557, 136),
            Map.entry(559, 137),
            Map.entry(561, 138),
            Map.entry(563, 139),
            Map.entry(565, 140),
            Map.entry(567, 141),
            Map.entry(569, 142),
            Map.entry(571, 143),
            Map.entry(573, 144),
            Map.entry(575, 145),
            Map.entry(615, 146),
            Map.entry(623, 147),
            Map.entry(631, 148),
            Map.entry(639, 149),
            Map.entry(693, 150),
            Map.entry(695, 151),
            Map.entry(701, 152),
            Map.entry(703, 153),
            Map.entry(759, 154),
            Map.entry(767, 155),
            Map.entry(809, 156),
            Map.entry(811, 157),
            Map.entry(813, 158),
            Map.entry(815, 159),
            Map.entry(825, 160),
            Map.entry(827, 161),
            Map.entry(829, 162),
            Map.entry(831, 163),
            Map.entry(879, 164),
            Map.entry(895, 165),
            Map.entry(957, 166),
            Map.entry(959, 167),
            Map.entry(1023, 168)
    );

    private static final int GLASS_PATTERN_COUNT = 21;
    private static final int STAINED_GLASS_PATTERN_COUNT = 25;
    private static final int SLAB_PATTERN_COUNT = 169;

    private static final SpriteIdentifier[] GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/glass_slab_atlas"));
    private static final SpriteIdentifier GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] WHITE_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier WHITE_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/white_stained_glass_slab_atlas"));
    private static final SpriteIdentifier WHITE_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/white_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier LIGHT_GRAY_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_gray_stained_glass_slab_atlas"));
    private static final SpriteIdentifier LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_gray_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier GRAY_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/gray_stained_glass_slab_atlas"));
    private static final SpriteIdentifier GRAY_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/gray_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier BLACK_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/black_stained_glass_slab_atlas"));
    private static final SpriteIdentifier BLACK_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/black_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier BROWN_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/brown_stained_glass_slab_atlas"));
    private static final SpriteIdentifier BROWN_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/brown_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier RED_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/red_stained_glass_slab_atlas"));
    private static final SpriteIdentifier RED_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/red_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier ORANGE_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/orange_stained_glass_slab_atlas"));
    private static final SpriteIdentifier ORANGE_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/orange_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier YELLOW_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/yellow_stained_glass_slab_atlas"));
    private static final SpriteIdentifier YELLOW_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/yellow_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier LIME_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/lime_stained_glass_slab_atlas"));
    private static final SpriteIdentifier LIME_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/lime_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier GREEN_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/green_stained_glass_slab_atlas"));
    private static final SpriteIdentifier GREEN_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/green_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier CYAN_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/cyan_stained_glass_slab_atlas"));
    private static final SpriteIdentifier CYAN_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/cyan_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier LIGHT_BLUE_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_blue_stained_glass_slab_atlas"));
    private static final SpriteIdentifier LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_blue_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier BLUE_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/blue_stained_glass_slab_atlas"));
    private static final SpriteIdentifier BLUE_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/blue_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier PURPLE_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/purple_stained_glass_slab_atlas"));
    private static final SpriteIdentifier PURPLE_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/purple_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier MAGENTA_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/magenta_stained_glass_slab_atlas"));
    private static final SpriteIdentifier MAGENTA_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/magenta_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier PINK_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/pink_stained_glass_slab_atlas"));
    private static final SpriteIdentifier PINK_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/pink_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier TINTED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/tinted_glass_slab_atlas"));
    private static final SpriteIdentifier TINTED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/tinted_glass_vertical_slab_atlas"));

    static {
        for (int i = 0; i < GLASS_PATTERN_COUNT-1; i++) {
            GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/glass/" + i));
        }
        for (int i = 0; i < STAINED_GLASS_PATTERN_COUNT-1; i++) {
            WHITE_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/white_stained_glass/" + i));
            /*LIGHT_GRAY_STAINED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_gray_stained_glass/" + i));
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
            TINTED_GLASS_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/tinted_glass/" + i));*/
        }
        GLASS_SPRITE_IDS[GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/glass/center"));
        WHITE_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/white_stained_glass/center"));
        /*LIGHT_GRAY_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_gray_stained_glass/center"));
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
        TINTED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/tinted_glass/center"));*/
    }

    public record ConnectionFlags(
            boolean topLeft,
            boolean topRight,
            boolean rightTop,
            boolean rightBottom,
            boolean bottomRight,
            boolean bottomLeft,
            boolean leftBottom,
            boolean leftTop,
            boolean cornerTopRight,
            boolean cornerBottomRight,
            boolean cornerBottomLeft,
            boolean cornerTopLeft
    ) {}

    public static SpriteIdentifier getFullBlockSpriteIdentifier(int index, Block slab) {
        /*if (slab == ModBlocks.WHITE_STAINED_GLASS_SLAB) {
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
        }*/
        if (slab == ModBlocks.GLASS_SLAB) {
            return GLASS_SPRITE_IDS[index];
        } else {
            return WHITE_STAINED_GLASS_SPRITE_IDS[index];
        }
    }

    public static SpriteIdentifier getSlabSpriteIdentifier(int index, Block slab) {
        if (slab == ModBlocks.WHITE_STAINED_GLASS_SLAB) {
            return WHITE_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.LIGHT_GRAY_STAINED_GLASS_SLAB) {
            return LIGHT_GRAY_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.GRAY_STAINED_GLASS_SLAB) {
            return GRAY_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.BLACK_STAINED_GLASS_SLAB) {
            return BLACK_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.BROWN_STAINED_GLASS_SLAB) {
            return BROWN_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.RED_STAINED_GLASS_SLAB) {
            return RED_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.ORANGE_STAINED_GLASS_SLAB) {
            return ORANGE_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.YELLOW_STAINED_GLASS_SLAB) {
            return YELLOW_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.LIME_STAINED_GLASS_SLAB) {
            return LIME_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.GREEN_STAINED_GLASS_SLAB) {
            return GREEN_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.CYAN_STAINED_GLASS_SLAB) {
            return CYAN_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.LIGHT_BLUE_STAINED_GLASS_SLAB) {
            return LIGHT_BLUE_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.BLUE_STAINED_GLASS_SLAB) {
            return BLUE_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.PURPLE_STAINED_GLASS_SLAB) {
            return PURPLE_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.MAGENTA_STAINED_GLASS_SLAB) {
            return MAGENTA_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.PINK_STAINED_GLASS_SLAB) {
            return PINK_STAINED_GLASS_SLAB_SPRITE;
        } else if (slab == ModBlocks.TINTED_GLASS_SLAB) {
            return TINTED_GLASS_SLAB_SPRITE;
        } else {
            return GLASS_SLAB_SPRITE;
        }
    }

    public static SpriteIdentifier getVerticalSlabSpriteIdentifier(int index, Block verticalSlab) {
        if (verticalSlab == ModBlocks.WHITE_STAINED_GLASS_VERTICAL_SLAB) {
            return WHITE_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB) {
            return LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.GRAY_STAINED_GLASS_VERTICAL_SLAB) {
            return GRAY_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.BLACK_STAINED_GLASS_VERTICAL_SLAB) {
            return BLACK_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.BROWN_STAINED_GLASS_VERTICAL_SLAB) {
            return BROWN_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.RED_STAINED_GLASS_VERTICAL_SLAB) {
            return RED_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.ORANGE_STAINED_GLASS_VERTICAL_SLAB) {
            return ORANGE_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.YELLOW_STAINED_GLASS_VERTICAL_SLAB) {
            return YELLOW_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.LIME_STAINED_GLASS_VERTICAL_SLAB) {
            return LIME_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.GREEN_STAINED_GLASS_VERTICAL_SLAB) {
            return GREEN_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.CYAN_STAINED_GLASS_VERTICAL_SLAB) {
            return CYAN_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB) {
            return LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.BLUE_STAINED_GLASS_VERTICAL_SLAB) {
            return BLUE_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.PURPLE_STAINED_GLASS_VERTICAL_SLAB) {
            return PURPLE_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.MAGENTA_STAINED_GLASS_VERTICAL_SLAB) {
            return MAGENTA_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.PINK_STAINED_GLASS_VERTICAL_SLAB) {
            return PINK_STAINED_GLASS_VERTICAL_SLAB_SPRITE;
        } else if (verticalSlab == ModBlocks.TINTED_GLASS_VERTICAL_SLAB) {
            return TINTED_GLASS_VERTICAL_SLAB_SPRITE;
        } else {
            return GLASS_VERTICAL_SLAB_SPRITE;
        }
    }

    public static List<Integer> determineEndPatternIndexes(ConnectionFlags flags, boolean isGlass) {
        List<Integer> indexes = new ArrayList<>();
        int i = 0;
        if (flags.topLeft) i += 1;
        if (flags.topRight) i += 2;
        if (flags.cornerTopRight) i += 2;
        if (!isGlass || i != 5) {
            indexes.add(i);
        }

        i = 6;
        if (flags.rightTop) i += 1;
        if (flags.rightBottom) i += 2;
        if (flags.cornerBottomRight) i += 2;
        if (!isGlass || i != 11) {
            indexes.add(i);
        }

        i = 12;
        if (flags.bottomRight) i += 1;
        if (flags.bottomLeft) i += 2;
        if (flags.cornerBottomLeft) i += 2;
        if (!isGlass || i != 17) {
            indexes.add(i);
        }

        i = 18;
        if (flags.leftBottom) i += 1;
        if (flags.leftTop) i += 2;
        if (flags.cornerTopLeft) i += 2;
        if (!isGlass || i != 23) {
            indexes.add(i);
        }

        return indexes;
    }

    public static int determineSlabSidePatternIndex(ConnectionFlags flags) {
        int patternIndex = 0;
        if (flags.topLeft) patternIndex |= 1;
        if (flags.topRight) patternIndex |= 1 << 1;
        if (flags.rightTop) patternIndex |= 1 << 2;
        if (flags.bottomLeft) patternIndex |= 1 << 3;
        if (flags.bottomRight) patternIndex |= 1 << 4;
        if (flags.leftTop) patternIndex |= 1 << 5;
        if (flags.cornerTopRight) patternIndex |= 1 << 6;
        if (flags.cornerBottomRight) patternIndex |= 1 << 7;
        if (flags.cornerBottomLeft) patternIndex |= 1 << 8;
        if (flags.cornerTopLeft) patternIndex |= 1 << 9;

        return patternIndex;
    }

    public static int determineVerticalSlabSidePatternIndex(ConnectionFlags flags) {
        int patternIndex = 0;
        if (flags.leftBottom) patternIndex |= 1;
        if (flags.leftTop) patternIndex |= 1 << 1;
        if (flags.topLeft) patternIndex |= 1 << 2;
        if (flags.rightTop) patternIndex |= 1 << 3;
        if (flags.rightBottom) patternIndex |= 1 << 4;
        if (flags.bottomLeft) patternIndex |= 1 << 5;
        if (flags.cornerTopLeft) patternIndex |= 1 << 6;
        if (flags.cornerTopRight) patternIndex |= 1 << 7;
        if (flags.cornerBottomRight) patternIndex |= 1 << 8;
        if (flags.cornerBottomLeft) patternIndex |= 1 << 9;

        return patternIndex;
    }

    public static int getMappedIndex(int index) {
        if (index < 64) {
            return index;
        }

        return SLAB_INDEX_MAP.getOrDefault(index, index);
    }
}
