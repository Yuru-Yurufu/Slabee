package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.Slabee;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.forestotzka.yurufu.slabee.model.NeighborState.*;

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
            Map.entry(140, 80),
            Map.entry(141, 81),
            Map.entry(142, 82),
            Map.entry(143, 83),
            Map.entry(156, 84),
            Map.entry(157, 85),
            Map.entry(158, 86),
            Map.entry(159, 87),
            Map.entry(172, 88),
            Map.entry(173, 89),
            Map.entry(174, 90),
            Map.entry(175, 91),
            Map.entry(188, 92),
            Map.entry(189, 93),
            Map.entry(190, 94),
            Map.entry(191, 95),
            Map.entry(206, 96),
            Map.entry(207, 97),
            Map.entry(222, 98),
            Map.entry(223, 99),
            Map.entry(238, 100),
            Map.entry(239, 101),
            Map.entry(254, 102),
            Map.entry(255, 103),
            Map.entry(304, 104),
            Map.entry(305, 105),
            Map.entry(306, 106),
            Map.entry(307, 107),
            Map.entry(308, 108),
            Map.entry(309, 109),
            Map.entry(310, 110),
            Map.entry(311, 111),
            Map.entry(312, 112),
            Map.entry(313, 113),
            Map.entry(314, 114),
            Map.entry(315, 115),
            Map.entry(316, 116),
            Map.entry(317, 117),
            Map.entry(318, 118),
            Map.entry(319, 119),
            Map.entry(374, 120),
            Map.entry(375, 121),
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
            Map.entry(685, 150),
            Map.entry(687, 151),
            Map.entry(701, 152),
            Map.entry(703, 153),
            Map.entry(751, 154),
            Map.entry(767, 155),
            Map.entry(817, 156),
            Map.entry(819, 157),
            Map.entry(821, 158),
            Map.entry(823, 159),
            Map.entry(825, 160),
            Map.entry(827, 161),
            Map.entry(829, 162),
            Map.entry(831, 163),
            Map.entry(887, 164),
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
    private static final SpriteIdentifier[] LIGHT_GRAY_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier LIGHT_GRAY_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_gray_stained_glass_slab_atlas"));
    private static final SpriteIdentifier LIGHT_GRAY_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_gray_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] GRAY_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier GRAY_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/gray_stained_glass_slab_atlas"));
    private static final SpriteIdentifier GRAY_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/gray_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] BLACK_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier BLACK_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/black_stained_glass_slab_atlas"));
    private static final SpriteIdentifier BLACK_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/black_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] BROWN_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier BROWN_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/brown_stained_glass_slab_atlas"));
    private static final SpriteIdentifier BROWN_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/brown_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] RED_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier RED_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/red_stained_glass_slab_atlas"));
    private static final SpriteIdentifier RED_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/red_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] ORANGE_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier ORANGE_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/orange_stained_glass_slab_atlas"));
    private static final SpriteIdentifier ORANGE_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/orange_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] YELLOW_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier YELLOW_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/yellow_stained_glass_slab_atlas"));
    private static final SpriteIdentifier YELLOW_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/yellow_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] LIME_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier LIME_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/lime_stained_glass_slab_atlas"));
    private static final SpriteIdentifier LIME_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/lime_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] GREEN_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier GREEN_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/green_stained_glass_slab_atlas"));
    private static final SpriteIdentifier GREEN_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/green_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] CYAN_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier CYAN_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/cyan_stained_glass_slab_atlas"));
    private static final SpriteIdentifier CYAN_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/cyan_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] LIGHT_BLUE_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier LIGHT_BLUE_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_blue_stained_glass_slab_atlas"));
    private static final SpriteIdentifier LIGHT_BLUE_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_blue_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] BLUE_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier BLUE_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/blue_stained_glass_slab_atlas"));
    private static final SpriteIdentifier BLUE_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/blue_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] PURPLE_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier PURPLE_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/purple_stained_glass_slab_atlas"));
    private static final SpriteIdentifier PURPLE_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/purple_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] MAGENTA_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier MAGENTA_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/magenta_stained_glass_slab_atlas"));
    private static final SpriteIdentifier MAGENTA_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/magenta_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] PINK_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier PINK_STAINED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/pink_stained_glass_slab_atlas"));
    private static final SpriteIdentifier PINK_STAINED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/pink_stained_glass_vertical_slab_atlas"));
    private static final SpriteIdentifier[] TINTED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier TINTED_GLASS_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/tinted_glass_slab_atlas"));
    private static final SpriteIdentifier TINTED_GLASS_VERTICAL_SLAB_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/tinted_glass_vertical_slab_atlas"));

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

    public static SpriteIdentifier getSlabSpriteIdentifier(Block slab) {
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

    public static SpriteIdentifier getVerticalSlabSpriteIdentifier(Block verticalSlab) {
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
        if (flags.bottomRight) patternIndex |= 1 << 3;
        if (flags.bottomLeft) patternIndex |= 1 << 4;
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

    public static ConnectionFlags getSideConnectionFlagsPositiveX(Direction face, NeighborState ns) {
        return getSideConnectionFlagsPositiveX(face, ns, false);
    }

    public static ConnectionFlags getSideConnectionFlagsNegativeX(Direction face, NeighborState ns) {
        return getSideConnectionFlagsNegativeX(face, ns, false);
    }

    public static ConnectionFlags getSideConnectionFlagsPositiveY(Direction face, NeighborState ns) {
        return getSideConnectionFlagsPositiveY(face, ns, false);
    }

    public static ConnectionFlags getSideConnectionFlagsNegativeY(Direction face, NeighborState ns) {
        return getSideConnectionFlagsNegativeY(face, ns, false);
    }

    public static ConnectionFlags getSideConnectionFlagsPositiveZ(Direction face, NeighborState ns) {
        return getSideConnectionFlagsPositiveZ(face, ns, false);
    }

    public static ConnectionFlags getSideConnectionFlagsNegativeZ(Direction face, NeighborState ns) {
        return getSideConnectionFlagsNegativeZ(face, ns, false);
    }

    public static ConnectionFlags getSideConnectionFlagsPositiveX(Direction face, NeighborState ns, boolean isSameSlab) {
        boolean topLeft = false;
        boolean topRight = false;
        boolean rightTop = false;
        boolean rightBottom = false;
        boolean bottomLeft = false;
        boolean bottomRight = false;
        boolean leftTop = false;
        boolean leftBottom = false;
        boolean cornerTopRight = false;
        boolean cornerBottomRight = false;
        boolean cornerBottomLeft = false;
        boolean cornerTopLeft = false;

        ContactType eastType = ns.getContactType(NeighborDirection.EAST);
        if (face == Direction.SOUTH) {
            if (eastType == ContactType.FULL || eastType == ContactType.POSITIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (eastType == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (eastType == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            ContactType upTypePositive = ns.getContactType(NeighborDirection.UP, Half.POSITIVE);
            ContactType downTypePositive = ns.getContactType(NeighborDirection.DOWN, Half.POSITIVE);
            topRight = upTypePositive == ContactType.FULL || upTypePositive == ContactType.POSITIVE1 || upTypePositive == ContactType.POSITIVE2;
            bottomRight = downTypePositive == ContactType.FULL || downTypePositive == ContactType.POSITIVE1 || downTypePositive == ContactType.POSITIVE2;
            topLeft = topRight;
            bottomLeft = bottomRight;
            if (isSameSlab || ns.isSameSlab()) {
                leftTop = true;
                leftBottom = true;
            }
            if (topLeft && leftTop) {
                if (upTypePositive != ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
            if (rightTop && topRight) {
                ContactType type = ns.getContactType(NeighborDirection.UP_EAST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_EAST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (leftBottom && bottomLeft) {
                if (downTypePositive != ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
        } else if (face == Direction.UP) {
            if (eastType == ContactType.FULL || eastType == ContactType.POSITIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (eastType == ContactType.NEGATIVE2) {
                rightTop = true;
            } else if (eastType == ContactType.POSITIVE2) {
                rightBottom = true;
            }
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            topRight = northTypePositive == ContactType.FULL || northTypePositive == ContactType.POSITIVE1 || northTypePositive == ContactType.POSITIVE2;
            bottomRight = southTypePositive == ContactType.FULL || southTypePositive == ContactType.POSITIVE1 || southTypePositive == ContactType.POSITIVE2;
            topLeft = topRight;
            bottomLeft = bottomRight;
            if (isSameSlab || ns.isSameSlab()) {
                leftTop = true;
                leftBottom = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_EAST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_EAST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (southTypePositive != ContactType.POSITIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (northTypePositive != ContactType.POSITIVE2) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.NORTH) {
            if (eastType == ContactType.FULL || eastType == ContactType.POSITIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (eastType == ContactType.NEGATIVE1) {
                leftBottom = true;
            } else if (eastType == ContactType.POSITIVE1) {
                leftTop = true;
            }
            ContactType upTypePositive = ns.getContactType(NeighborDirection.UP, Half.POSITIVE);
            ContactType downTypePositive = ns.getContactType(NeighborDirection.DOWN, Half.POSITIVE);
            topLeft = upTypePositive == ContactType.FULL || upTypePositive == ContactType.POSITIVE1 || upTypePositive == ContactType.NEGATIVE2;
            bottomLeft = downTypePositive == ContactType.FULL || downTypePositive == ContactType.POSITIVE1 || downTypePositive == ContactType.NEGATIVE2;
            topRight = topLeft;
            bottomRight = bottomLeft;
            if (isSameSlab || ns.isSameSlab()) {
                rightTop = true;
                rightBottom = true;
            }
            if (topRight && rightTop) {
                if (upTypePositive != ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (downTypePositive != ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.DOWN) {
            if (eastType == ContactType.FULL || eastType == ContactType.NEGATIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (eastType == ContactType.NEGATIVE2) {
                rightBottom = true;
            } else if (eastType == ContactType.POSITIVE2) {
                rightTop = true;
            }
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            bottomRight = northTypePositive == ContactType.FULL || northTypePositive == ContactType.NEGATIVE1 || northTypePositive == ContactType.POSITIVE2;
            topRight = southTypePositive == ContactType.FULL || southTypePositive == ContactType.NEGATIVE1 || southTypePositive == ContactType.POSITIVE2;
            bottomLeft = bottomRight;
            topLeft = topRight;
            if (isSameSlab || ns.isSameSlab()) {
                leftTop = true;
                leftBottom = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (northTypePositive != ContactType.POSITIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (southTypePositive != ContactType.POSITIVE2) {
                    cornerTopLeft = true;
                }
            }
        }

        return new ConnectionFlags(
                topLeft,
                topRight,
                rightTop,
                rightBottom,
                bottomRight,
                bottomLeft,
                leftBottom,
                leftTop,
                cornerTopRight,
                cornerBottomRight,
                cornerBottomLeft,
                cornerTopLeft
        );
    }

    public static ConnectionFlags getSideConnectionFlagsNegativeX(Direction face, NeighborState ns, boolean isSameSlab) {
        boolean topLeft = false;
        boolean topRight = false;
        boolean rightTop = false;
        boolean rightBottom = false;
        boolean bottomLeft = false;
        boolean bottomRight = false;
        boolean leftTop = false;
        boolean leftBottom = false;
        boolean cornerTopRight = false;
        boolean cornerBottomRight = false;
        boolean cornerBottomLeft = false;
        boolean cornerTopLeft = false;

        ContactType westType = ns.getContactType(NeighborDirection.WEST);
        if (face == Direction.SOUTH) {
            if (isSameSlab || ns.isSameSlab()) {
                rightTop = true;
                rightBottom = true;
            }
            ContactType upTypeNegative = ns.getContactType(NeighborDirection.UP, Half.NEGATIVE);
            ContactType downTypeNegative = ns.getContactType(NeighborDirection.DOWN, Half.NEGATIVE);
            topLeft = upTypeNegative == ContactType.FULL || upTypeNegative == ContactType.POSITIVE2 || upTypeNegative == ContactType.NEGATIVE1;
            bottomLeft = downTypeNegative == ContactType.FULL || downTypeNegative == ContactType.POSITIVE2 || downTypeNegative == ContactType.NEGATIVE1;
            topRight = topLeft;
            bottomRight = bottomLeft;
            if (westType == ContactType.FULL || westType == ContactType.POSITIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (westType == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (westType == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            if (topRight && rightTop) {
                if (upTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (downTypeNegative != ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.UP) {
            if (isSameSlab || ns.isSameSlab()) {
                rightTop = true;
                rightBottom = true;
            }
            ContactType northTypeNegative = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            ContactType southTypeNegative = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            topLeft = northTypeNegative == ContactType.FULL || northTypeNegative == ContactType.POSITIVE1 || northTypeNegative == ContactType.NEGATIVE2;
            bottomLeft = southTypeNegative == ContactType.FULL || southTypeNegative == ContactType.POSITIVE1 || southTypeNegative == ContactType.NEGATIVE2;
            topRight = topLeft;
            bottomRight = bottomLeft;
            if (westType == ContactType.FULL || westType == ContactType.POSITIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (westType == ContactType.NEGATIVE2) {
                leftTop = true;
            } else if (westType == ContactType.POSITIVE2) {
                leftBottom = true;
            }
            if (topRight && rightTop) {
                if (northTypeNegative != ContactType.NEGATIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (southTypeNegative != ContactType.NEGATIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.NORTH) {
            if (isSameSlab || ns.isSameSlab()) {
                leftTop = true;
                leftBottom = true;
            }
            ContactType upTypeNegative = ns.getContactType(NeighborDirection.UP, Half.NEGATIVE);
            ContactType downTypeNegative = ns.getContactType(NeighborDirection.DOWN, Half.NEGATIVE);
            topRight = upTypeNegative == ContactType.FULL || upTypeNegative == ContactType.NEGATIVE1 || upTypeNegative == ContactType.NEGATIVE2;
            bottomRight = downTypeNegative == ContactType.FULL || downTypeNegative == ContactType.NEGATIVE1 || downTypeNegative == ContactType.NEGATIVE2;
            topLeft = topRight;
            bottomLeft = bottomRight;
            if (westType == ContactType.FULL || westType == ContactType.NEGATIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (westType == ContactType.NEGATIVE1) {
                rightBottom = true;
            } else if (westType == ContactType.POSITIVE1) {
                rightTop = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_NORTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_NORTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (downTypeNegative != ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (upTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.DOWN) {
            if (isSameSlab || ns.isSameSlab()) {
                rightTop = true;
                rightBottom = true;
            }
            ContactType northTypeNegative = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            ContactType southTypeNegative = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            bottomLeft = northTypeNegative == ContactType.FULL || northTypeNegative == ContactType.NEGATIVE1 || northTypeNegative == ContactType.NEGATIVE2;
            topLeft = southTypeNegative == ContactType.FULL || southTypeNegative == ContactType.NEGATIVE1 || southTypeNegative == ContactType.NEGATIVE2;
            bottomRight = bottomLeft;
            topRight = topLeft;
            if (westType == ContactType.FULL || westType == ContactType.NEGATIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (westType == ContactType.NEGATIVE2) {
                leftBottom = true;
            } else if (westType == ContactType.POSITIVE2) {
                leftTop = true;
            }
            if (topRight && rightTop) {
                if (southTypeNegative != ContactType.NEGATIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (northTypeNegative != ContactType.NEGATIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_WEST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_WEST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        }

        return new ConnectionFlags(
                topLeft,
                topRight,
                rightTop,
                rightBottom,
                bottomRight,
                bottomLeft,
                leftBottom,
                leftTop,
                cornerTopRight,
                cornerBottomRight,
                cornerBottomLeft,
                cornerTopLeft
        );
    }

    public static ConnectionFlags getSideConnectionFlagsPositiveY(Direction face, NeighborState ns, boolean isSameSlab) {
        boolean topLeft = false;
        boolean topRight = false;
        boolean right = false;
        boolean bottomLeft = false;
        boolean bottomRight = false;
        boolean left = false;
        boolean cornerTopRight = false;
        boolean cornerBottomRight = false;
        boolean cornerBottomLeft = false;
        boolean cornerTopLeft = false;

        ContactType upType = ns.getContactType(NeighborDirection.UP);
        if (face == Direction.EAST) {
            if (upType == ContactType.FULL || upType == ContactType.POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (upType == ContactType.POSITIVE2) {
                topLeft = true;
            } else if (upType == ContactType.NEGATIVE2) {
                topRight = true;
            }
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            left = southTypePositive == ContactType.FULL || southTypePositive == ContactType.POSITIVE1 || southTypePositive == ContactType.POSITIVE2;
            right = northTypePositive == ContactType.FULL || northTypePositive == ContactType.POSITIVE1 || northTypePositive == ContactType.POSITIVE2;
            if (isSameSlab || ns.isSameSlab()) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                ContactType type = ns.getContactType(NeighborDirection.UP_NORTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (northTypePositive != ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (southTypePositive != ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.UP_SOUTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.SOUTH) {
            if (upType == ContactType.FULL || upType == ContactType.POSITIVE2) {
                topLeft = true;
                topRight = true;
            } else if (upType == ContactType.NEGATIVE1) {
                topLeft = true;
            } else if (upType == ContactType.POSITIVE1) {
                topRight = true;
            }
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            ContactType eastTypePositive = ns.getContactType(NeighborDirection.EAST, Half.POSITIVE);
            left = westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE1 || westTypePositive == ContactType.POSITIVE2;
            right = eastTypePositive == ContactType.FULL || eastTypePositive == ContactType.POSITIVE1 || eastTypePositive == ContactType.POSITIVE2;
            if (isSameSlab || ns.isSameSlab()) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                ContactType type = ns.getContactType(NeighborDirection.UP_EAST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (eastTypePositive != ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (westTypePositive != ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.UP_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            if (upType == ContactType.FULL || upType == ContactType.NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (upType == ContactType.NEGATIVE2) {
                topLeft = true;
            } else if (upType == ContactType.POSITIVE2) {
                topRight = true;
            }
            ContactType northTypePositive = ns.getContactType(NeighborDirection.NORTH, Half.POSITIVE);
            ContactType southTypePositive = ns.getContactType(NeighborDirection.SOUTH, Half.POSITIVE);
            left = northTypePositive == ContactType.FULL || northTypePositive == ContactType.POSITIVE1 || northTypePositive == ContactType.NEGATIVE2;
            right = southTypePositive == ContactType.FULL || southTypePositive == ContactType.POSITIVE1 || southTypePositive == ContactType.NEGATIVE2;
            if (isSameSlab || ns.isSameSlab()) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                ContactType type = ns.getContactType(NeighborDirection.UP_SOUTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (southTypePositive != ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (northTypePositive != ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.UP_NORTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.NORTH) {
            if (upType == ContactType.FULL || upType == ContactType.NEGATIVE2) {
                topLeft = true;
                topRight = true;
            } else if (upType == ContactType.POSITIVE1) {
                topLeft = true;
            } else if (upType == ContactType.NEGATIVE1) {
                topRight = true;
            }
            ContactType eastTypePositive = ns.getContactType(NeighborDirection.EAST, Half.POSITIVE);
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            left = eastTypePositive == ContactType.FULL || eastTypePositive == ContactType.POSITIVE1 || eastTypePositive == ContactType.NEGATIVE2;
            right = westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE1 || westTypePositive == ContactType.NEGATIVE2;
            if (isSameSlab || ns.isSameSlab()) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                ContactType type = ns.getContactType(NeighborDirection.UP_WEST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (westTypePositive != ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (eastTypePositive != ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.UP_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        }

        return new ConnectionFlags(
                topLeft,
                topRight,
                right,
                false,
                bottomRight,
                bottomLeft,
                false,
                left,
                cornerTopRight,
                cornerBottomRight,
                cornerBottomLeft,
                cornerTopLeft
        );
    }

    public static ConnectionFlags getSideConnectionFlagsNegativeY(Direction face, NeighborState ns, boolean isSameSlab) {
        boolean topLeft = false;
        boolean topRight = false;
        boolean right = false;
        boolean bottomLeft = false;
        boolean bottomRight = false;
        boolean left = false;
        boolean cornerTopRight = false;
        boolean cornerBottomRight = false;
        boolean cornerBottomLeft = false;
        boolean cornerTopLeft = false;

        ContactType downType = ns.getContactType(NeighborDirection.DOWN);
        if (face == Direction.EAST) {
            if (isSameSlab || ns.isSameSlab()) {
                topLeft = true;
                topRight = true;
            }
            ContactType southTypeNegative = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            ContactType northTypeNegative = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            left = southTypeNegative == ContactType.FULL || southTypeNegative == ContactType.NEGATIVE1 || southTypeNegative == ContactType.POSITIVE2;
            right = northTypeNegative == ContactType.FULL || northTypeNegative == ContactType.NEGATIVE1 || northTypeNegative == ContactType.POSITIVE2;
            if (downType == ContactType.FULL || downType == ContactType.POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downType == ContactType.POSITIVE2) {
                bottomLeft = true;
            } else if (downType == ContactType.NEGATIVE2) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (northTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_NORTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_SOUTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (southTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.SOUTH) {
            if (isSameSlab || ns.isSameSlab()) {
                topLeft = true;
                topRight = true;
            }
            ContactType westTypeNegative = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            ContactType eastTypeNegative = ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE);
            left = westTypeNegative == ContactType.FULL || westTypeNegative == ContactType.NEGATIVE1 || westTypeNegative == ContactType.POSITIVE2;
            right = eastTypeNegative == ContactType.FULL || eastTypeNegative == ContactType.NEGATIVE1 || eastTypeNegative == ContactType.POSITIVE2;
            if (downType == ContactType.FULL || downType == ContactType.POSITIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downType == ContactType.NEGATIVE1) {
                bottomLeft = true;
            } else if (downType == ContactType.POSITIVE1) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (eastTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_EAST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (westTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            if (isSameSlab || ns.isSameSlab()) {
                topLeft = true;
                topRight = true;
            }
            ContactType northTypeNegative = ns.getContactType(NeighborDirection.NORTH, Half.NEGATIVE);
            ContactType southTypeNegative = ns.getContactType(NeighborDirection.SOUTH, Half.NEGATIVE);
            left = northTypeNegative == ContactType.FULL || northTypeNegative == ContactType.NEGATIVE1 || northTypeNegative == ContactType.NEGATIVE2;
            right = southTypeNegative == ContactType.FULL || southTypeNegative == ContactType.NEGATIVE1 || southTypeNegative == ContactType.NEGATIVE2;
            if (downType == ContactType.FULL || downType == ContactType.NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downType == ContactType.NEGATIVE2) {
                bottomLeft = true;
            } else if (downType == ContactType.POSITIVE2) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (southTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_SOUTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_NORTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (northTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.NORTH) {
            if (isSameSlab || ns.isSameSlab()) {
                topLeft = true;
                topRight = true;
            }
            ContactType eastTypeNegative = ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE);
            ContactType westTypeNegative = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            left = eastTypeNegative == ContactType.FULL || eastTypeNegative == ContactType.NEGATIVE1 || eastTypeNegative == ContactType.NEGATIVE2;
            right = westTypeNegative == ContactType.FULL || westTypeNegative == ContactType.NEGATIVE1 || westTypeNegative == ContactType.NEGATIVE2;
            if (downType == ContactType.FULL || downType == ContactType.NEGATIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (downType == ContactType.POSITIVE1) {
                bottomLeft = true;
            } else if (downType == ContactType.NEGATIVE1) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (westTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_WEST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (eastTypeNegative != ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        }

        return new ConnectionFlags(
                topLeft,
                topRight,
                right,
                false,
                bottomRight,
                bottomLeft,
                false,
                left,
                cornerTopRight,
                cornerBottomRight,
                cornerBottomLeft,
                cornerTopLeft
        );
    }

    public static ConnectionFlags getSideConnectionFlagsPositiveZ(Direction face, NeighborState ns, boolean isSameSlab) {
        boolean topLeft = false;
        boolean topRight = false;
        boolean rightTop = false;
        boolean rightBottom = false;
        boolean bottomLeft = false;
        boolean bottomRight = false;
        boolean leftTop = false;
        boolean leftBottom = false;
        boolean cornerTopRight = false;
        boolean cornerBottomRight = false;
        boolean cornerBottomLeft = false;
        boolean cornerTopLeft = false;

        ContactType southType = ns.getContactType(NeighborDirection.SOUTH);
        if (face == Direction.EAST) {
            if (southType == ContactType.FULL || southType == ContactType.POSITIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (southType == ContactType.POSITIVE1) {
                leftTop = true;
            } else if (southType == ContactType.NEGATIVE1) {
                leftBottom = true;
            }
            ContactType upTypePositive = ns.getContactType(NeighborDirection.UP, Half.POSITIVE);
            ContactType downTypePositive = ns.getContactType(NeighborDirection.DOWN, Half.POSITIVE);
            topLeft = upTypePositive == ContactType.FULL || upTypePositive == ContactType.POSITIVE1 || upTypePositive == ContactType.POSITIVE2;
            bottomLeft = downTypePositive == ContactType.FULL || downTypePositive == ContactType.POSITIVE1 || downTypePositive == ContactType.POSITIVE2;
            topRight = topLeft;
            bottomRight = bottomLeft;
            if (isSameSlab || ns.isSameSlab()) {
                rightTop = true;
                rightBottom = true;
            }
            if (leftTop && topLeft) {
                ContactType type = ns.getContactType(NeighborDirection.UP_SOUTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
            if (topRight && rightTop) {
                if (upTypePositive != ContactType.POSITIVE2) {
                    cornerTopRight = true;
                }
            }
            if (rightBottom && bottomRight) {
                if (downTypePositive != ContactType.POSITIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_SOUTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
        } else if (face == Direction.UP) {
            if (southType == ContactType.FULL || southType == ContactType.POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (southType == ContactType.NEGATIVE2) {
                bottomLeft = true;
            } else if (southType == ContactType.POSITIVE2) {
                bottomRight = true;
            }
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            ContactType eastTypePositive = ns.getContactType(NeighborDirection.EAST, Half.POSITIVE);
            leftBottom = westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE1 || westTypePositive == ContactType.POSITIVE2;
            rightBottom = eastTypePositive == ContactType.FULL || eastTypePositive == ContactType.POSITIVE1 || eastTypePositive == ContactType.POSITIVE2;
            leftTop = leftBottom;
            rightTop = rightBottom;
            if (isSameSlab || ns.isSameSlab()) {
                topLeft = true;
                topRight = true;
            }
            if (topRight && rightTop) {
                if (eastTypePositive != ContactType.POSITIVE2) {
                    cornerTopRight = true;
                }

            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_EAST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (westTypePositive != ContactType.POSITIVE2) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            if (southType == ContactType.FULL || southType == ContactType.NEGATIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (southType == ContactType.NEGATIVE1) {
                rightBottom = true;
            } else if (southType == ContactType.POSITIVE1) {
                rightTop = true;
            }
            ContactType upTypePositive = ns.getContactType(NeighborDirection.UP, Half.POSITIVE);
            ContactType downTypePositive = ns.getContactType(NeighborDirection.DOWN, Half.POSITIVE);
            topRight = upTypePositive == ContactType.FULL || upTypePositive == ContactType.NEGATIVE1 || upTypePositive == ContactType.POSITIVE2;
            bottomRight = downTypePositive == ContactType.FULL || downTypePositive == ContactType.NEGATIVE1 || downTypePositive == ContactType.POSITIVE2;
            topLeft = topRight;
            bottomLeft = bottomRight;
            if (isSameSlab || ns.isSameSlab()) {
                leftTop = true;
                leftBottom = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_SOUTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_SOUTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (downTypePositive != ContactType.POSITIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (upTypePositive != ContactType.POSITIVE2) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.DOWN) {
            if (southType == ContactType.FULL || southType == ContactType.NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (southType == ContactType.POSITIVE2) {
                topRight = true;
            } else if (southType == ContactType.NEGATIVE2) {
                topLeft = true;
            }
            ContactType westTypePositive = ns.getContactType(NeighborDirection.WEST, Half.POSITIVE);
            ContactType eastTypePositive = ns.getContactType(NeighborDirection.EAST, Half.POSITIVE);
            leftTop = westTypePositive == ContactType.FULL || westTypePositive == ContactType.POSITIVE2 || westTypePositive == ContactType.NEGATIVE1;
            rightTop = eastTypePositive == ContactType.FULL || eastTypePositive == ContactType.POSITIVE2 || eastTypePositive == ContactType.NEGATIVE1;
            leftBottom = leftTop;
            rightBottom = rightTop;
            if (isSameSlab || ns.isSameSlab()) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (eastTypePositive != ContactType.POSITIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (westTypePositive != ContactType.POSITIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.SOUTH_WEST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        }

        return new ConnectionFlags(
                topLeft,
                topRight,
                rightTop,
                rightBottom,
                bottomRight,
                bottomLeft,
                leftBottom,
                leftTop,
                cornerTopRight,
                cornerBottomRight,
                cornerBottomLeft,
                cornerTopLeft
        );
    }

    public static ConnectionFlags getSideConnectionFlagsNegativeZ(Direction face, NeighborState ns, boolean isSameSlab) {
        boolean topLeft = false;
        boolean topRight = false;
        boolean rightTop = false;
        boolean rightBottom = false;
        boolean bottomLeft = false;
        boolean bottomRight = false;
        boolean leftTop = false;
        boolean leftBottom = false;
        boolean cornerTopRight = false;
        boolean cornerBottomRight = false;
        boolean cornerBottomLeft = false;
        boolean cornerTopLeft = false;

        ContactType northType = ns.getContactType(NeighborDirection.NORTH);
        if (face == Direction.EAST) {
            if (isSameSlab || ns.isSameSlab()) {
                leftTop = true;
                leftBottom = true;
            }
            ContactType upTypeNegative = ns.getContactType(NeighborDirection.UP, Half.NEGATIVE);
            ContactType downTypeNegative = ns.getContactType(NeighborDirection.DOWN, Half.NEGATIVE);
            topRight = upTypeNegative == ContactType.FULL || upTypeNegative == ContactType.NEGATIVE2 || upTypeNegative == ContactType.POSITIVE1;
            bottomRight = downTypeNegative == ContactType.FULL || downTypeNegative == ContactType.NEGATIVE2 || downTypeNegative == ContactType.POSITIVE1;
            topLeft = topRight;
            bottomLeft = bottomRight;
            if (northType == ContactType.FULL || northType == ContactType.POSITIVE2) {
                rightTop = true;
                rightBottom = true;
            } else if (northType == ContactType.POSITIVE1) {
                rightTop = true;
            } else if (northType == ContactType.NEGATIVE1) {
                rightBottom = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_NORTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_NORTH);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (downTypeNegative != ContactType.NEGATIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (upTypeNegative != ContactType.NEGATIVE2) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.UP) {
            if (isSameSlab || ns.isSameSlab()) {
                bottomLeft = true;
                bottomRight = true;
            }
            ContactType westTypeNegative = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            ContactType eastTypeNegative = ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE);
            leftTop = westTypeNegative == ContactType.FULL || westTypeNegative == ContactType.POSITIVE1 || westTypeNegative == ContactType.NEGATIVE2;
            rightTop = eastTypeNegative == ContactType.FULL || eastTypeNegative == ContactType.POSITIVE1 || eastTypeNegative == ContactType.NEGATIVE2;
            leftBottom = leftTop;
            rightBottom = rightTop;
            if (northType == ContactType.FULL || northType == ContactType.POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (northType == ContactType.NEGATIVE2) {
                topLeft = true;
            } else if (northType == ContactType.POSITIVE2) {
                topRight = true;
            }
            if (topRight && rightTop) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_EAST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (eastTypeNegative != ContactType.NEGATIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (westTypeNegative != ContactType.NEGATIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_WEST);
                if (type == ContactType.FULL || type == ContactType.POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            if (isSameSlab || ns.isSameSlab()) {
                rightTop = true;
                rightBottom = true;
            }
            ContactType upTypeNegative = ns.getContactType(NeighborDirection.UP, Half.NEGATIVE);
            ContactType downTypeNegative = ns.getContactType(NeighborDirection.DOWN, Half.NEGATIVE);
            topLeft = upTypeNegative == ContactType.FULL || upTypeNegative == ContactType.NEGATIVE1 || upTypeNegative == ContactType.NEGATIVE2;
            bottomLeft = downTypeNegative == ContactType.FULL || downTypeNegative == ContactType.NEGATIVE1 || downTypeNegative == ContactType.NEGATIVE2;
            topRight = topLeft;
            bottomRight = bottomLeft;
            if (northType == ContactType.FULL || northType == ContactType.NEGATIVE2) {
                leftTop = true;
                leftBottom = true;
            } else if (northType == ContactType.NEGATIVE1) {
                leftBottom = true;
            } else if (northType == ContactType.POSITIVE1) {
                leftTop = true;
            }
            if (topRight && rightTop) {
                if (upTypeNegative != ContactType.NEGATIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (downTypeNegative != ContactType.NEGATIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.DOWN_NORTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                ContactType type = ns.getContactType(NeighborDirection.UP_NORTH);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.DOWN) {
            if (isSameSlab || ns.isSameSlab()) {
                topLeft = true;
                topRight = true;
            }
            ContactType eastTypeNegative = ns.getContactType(NeighborDirection.EAST, Half.NEGATIVE);
            ContactType westTypeNegative = ns.getContactType(NeighborDirection.WEST, Half.NEGATIVE);
            rightBottom = eastTypeNegative == ContactType.FULL || eastTypeNegative == ContactType.NEGATIVE1 || eastTypeNegative == ContactType.NEGATIVE2;
            leftBottom = westTypeNegative == ContactType.FULL || westTypeNegative == ContactType.NEGATIVE1 || westTypeNegative == ContactType.NEGATIVE2;
            rightTop = rightBottom;
            leftTop = leftBottom;
            if (northType == ContactType.FULL || northType == ContactType.NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (northType == ContactType.POSITIVE2) {
                bottomRight = true;
            } else if (northType == ContactType.NEGATIVE2) {
                bottomLeft = true;
            }
            if (topRight && rightTop) {
                if (eastTypeNegative != ContactType.NEGATIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_EAST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                ContactType type = ns.getContactType(NeighborDirection.NORTH_WEST);
                if (type == ContactType.FULL || type == ContactType.NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (westTypeNegative != ContactType.NEGATIVE2) {
                    cornerTopLeft = true;
                }
            }
        }

        return new ConnectionFlags(
                topLeft,
                topRight,
                rightTop,
                rightBottom,
                bottomRight,
                bottomLeft,
                leftBottom,
                leftTop,
                cornerTopRight,
                cornerBottomRight,
                cornerBottomLeft,
                cornerTopLeft
        );
    }
}
