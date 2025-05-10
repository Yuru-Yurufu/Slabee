package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.Slabee;
import com.forestotzka.yurufu.slabee.block.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class DoubleSlabBlockConnectGlassModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private final Identifier positiveId;
    private final Identifier negativeId;
    private final Block positiveSlab;
    private final Block negativeSlab;
    private BakedModel positiveBakedModel;
    private BakedModel negativeBakedModel;
    private BakedModel nullBakedModel;

    private final Map<Integer, EnumMap<Direction, Mesh>> positiveMeshMap = new HashMap<>();
    private final Map<Integer, EnumMap<Direction, Mesh>> negativeMeshMap = new HashMap<>();
    private final Map<Integer, EnumMap<Direction, Mesh>> endPositiveMeshMap = new HashMap<>();
    private final Map<Integer, EnumMap<Direction, Mesh>> endNegativeMeshMap = new HashMap<>();

    public static final Map<Integer, Integer> SLAB_INDEX_MAP = Map.<Integer, Integer>ofEntries(
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

    private boolean isGlassPositive = false;
    private boolean isGlassNegative = false;

    private static final SpriteIdentifier[] GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] WHITE_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] WHITE_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] LIGHT_GRAY_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] LIGHT_GRAY_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] GRAY_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] GRAY_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] BLACK_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] BLACK_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] BROWN_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] BROWN_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] RED_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] RED_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] ORANGE_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] ORANGE_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] YELLOW_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] YELLOW_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] LIME_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] LIME_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] GREEN_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] GREEN_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] CYAN_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] CYAN_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] LIGHT_BLUE_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] LIGHT_BLUE_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] BLUE_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] BLUE_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] PURPLE_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] PURPLE_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] MAGENTA_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] MAGENTA_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] PINK_STAINED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] PINK_STAINED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] TINTED_GLASS_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] TINTED_GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[SLAB_PATTERN_COUNT];

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
        }
    }

    /**
     * NeighborStateで使うためのプロパティ
     * <p>
     * OTHER -> 処理に関係ない場合全般
     * <p>
     * FULL -> 接面/線が最大の時
     * <p>
     * 上記に当てはまらない場合は以下の4プロパティのいずれかになる
     * <p>
     * POSITIVE1 -> 優先度が高い方の正の方向にある場合
     * <p>
     * NEGATIVE1 -> 優先度が高い方の負の方向にある場合
     * <p>
     * POSITIVE2 -> 優先度が低い方の正の方向にある場合（線で接する場合は使われない）
     * <p>
     * NEGATIVE2 -> 優先度が低い方の負の方向にある場合（線で接する場合は使われない）
     * <p>
     * ※優先度はY方向>X方向>Z方向である
     */
    private final int OTHER = 0;
    private final int FULL = 1;
    private final int POSITIVE1 = 3;
    private final int NEGATIVE1 = 4;
    private final int POSITIVE2 = 5;
    private final int NEGATIVE2 = 6;

    private record NeighborState(
            boolean isSameSlab,
            int upBlock, int downBlock,
            int eastBlockTop, int southBlockTop, int westBlockTop, int northBlockTop,
            int eastBlockBottom, int southBlockBottom, int westBlockBottom, int northBlockBottom,
            int topEastBlock, int topSouthBlock, int topWestBlock, int topNorthBlock,
            int southEastBlockTop, int southWestBlockTop, int northWestBlockTop, int northEastBlockTop,
            int southEastBlockBottom, int southWestBlockBottom, int northWestBlockBottom, int northEastBlockBottom,
            int bottomEastBlock, int bottomSouthBlock, int bottomWestBlock, int bottomNorthBlock
    ) {}

    private record ConnectionFlags(
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

    public DoubleSlabBlockConnectGlassModel(@Nullable Block positiveSlab, @Nullable Block negativeSlab) {
        this.positiveSlab = positiveSlab;
        this.negativeSlab = negativeSlab;

        if (this.positiveSlab == null) {
            this.positiveId = null;
        } else {
            Identifier positiveId = Registries.BLOCK.getId(this.positiveSlab);
            this.positiveId = Identifier.of(positiveId.getNamespace(), "block/" + positiveId.getPath() + "_top");
        }

        if (this.negativeSlab == null) {
            this.negativeId = null;
        } else {
            Identifier negativeId = Registries.BLOCK.getId(this.negativeSlab);
            this.negativeId = Identifier.of(negativeId.getNamespace(), "block/" + negativeId.getPath());
        }
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        if (positiveId != null) {
            return positiveBakedModel.getParticleSprite();
        } else if (negativeId != null) {
            return negativeBakedModel.getParticleSprite();
        } else {
            return nullBakedModel.getParticleSprite();
        }
    }

    @Override
    public ModelTransformation getTransformation() {
        return null;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return null;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return List.of();
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }

    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        if (renderer != null) {
            for (int patternIndex = 0; patternIndex < SLAB_PATTERN_COUNT; patternIndex++) {
                EnumMap<Direction, Mesh> positiveFaceMeshes = new EnumMap<>(Direction.class);
                EnumMap<Direction, Mesh> negativeFaceMeshes = new EnumMap<>(Direction.class);

                for (Direction dir : Direction.values()) {
                    if (dir == Direction.UP || dir == Direction.DOWN) {
                        continue;
                    }

                    {
                        MeshBuilder meshBuilder = renderer.meshBuilder();
                        QuadEmitter emitter = meshBuilder.getEmitter();

                        emitter.square(dir, 0, 0.5f, 1, 1, 0);
                        emitter.spriteBake(textureGetter.apply(getSlabSpriteIdentifier(patternIndex, true)), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        positiveFaceMeshes.put(dir, meshBuilder.build());
                    }
                    {
                        MeshBuilder meshBuilder = renderer.meshBuilder();
                        QuadEmitter emitter = meshBuilder.getEmitter();

                        emitter.square(dir, 0, 0, 1, 0.5f, 0);
                        emitter.spriteBake(textureGetter.apply(getSlabSpriteIdentifier(patternIndex, false)), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        negativeFaceMeshes.put(dir, meshBuilder.build());
                    }
                }

                positiveMeshMap.put(patternIndex, positiveFaceMeshes);
                negativeMeshMap.put(patternIndex, negativeFaceMeshes);
            }

            for (int patternIndex = 0; patternIndex < (isGlassPositive ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT); patternIndex++) {
                EnumMap<Direction, Mesh> endPositiveFaceMeshes = new EnumMap<>(Direction.class);

                for (Direction dir : Direction.values()) {
                    MeshBuilder meshBuilder = renderer.meshBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    if (dir == Direction.UP) {
                        emitter.square(dir, 0, 0, 1, 1, 0f);
                        emitter.spriteBake(textureGetter.apply(getFullBlockSpriteIdentifier(patternIndex, true)), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endPositiveFaceMeshes.put(dir, meshBuilder.build());
                    } else if (dir == Direction.DOWN) {
                        emitter.square(dir, 0, 0, 1, 1, 0.5f);
                        emitter.spriteBake(textureGetter.apply(getFullBlockSpriteIdentifier(patternIndex, true)), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endPositiveFaceMeshes.put(dir, meshBuilder.build());
                    }
                }

                endPositiveMeshMap.put(patternIndex, endPositiveFaceMeshes);
            }

            for (int patternIndex = 0; patternIndex < (isGlassNegative ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT); patternIndex++) {
                EnumMap<Direction, Mesh> endNegativeFaceMeshes = new EnumMap<>(Direction.class);

                for (Direction dir : Direction.values()) {
                    MeshBuilder meshBuilder = renderer.meshBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    if (dir == Direction.UP) {
                        emitter.square(dir, 0, 0, 1, 1, 0.5f);
                        emitter.spriteBake(textureGetter.apply(getFullBlockSpriteIdentifier(patternIndex, false)), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endNegativeFaceMeshes.put(dir, meshBuilder.build());
                    } else if (dir == Direction.DOWN) {
                        emitter.square(dir, 0, 0, 1, 1, 0f);
                        emitter.spriteBake(textureGetter.apply(getFullBlockSpriteIdentifier(patternIndex, false)), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endNegativeFaceMeshes.put(dir, meshBuilder.build());
                    }
                }

                endNegativeMeshMap.put(patternIndex, endNegativeFaceMeshes);
            }
        }

        if (this.positiveId != null) {
            UnbakedModel positiveUnbakedModel = baker.getOrLoadModel(this.positiveId);
            this.positiveBakedModel = positiveUnbakedModel.bake(baker, textureGetter, rotationContainer);
        }

        if (this.negativeId != null) {
            UnbakedModel negativeUnbakedModel = baker.getOrLoadModel(this.negativeId);
            this.negativeBakedModel = negativeUnbakedModel.bake(baker, textureGetter, rotationContainer);
        } else if (this.positiveId == null) {
            this.nullBakedModel = baker.getOrLoadModel(Identifier.of("minecraft:stone")).bake(baker, textureGetter, rotationContainer);
        }

        return this;
    }

    private SpriteIdentifier getFullBlockSpriteIdentifier(int index, boolean isTop) {
        Block b;
        if (isTop && positiveId != null) {
            b = positiveSlab;
        } else if (negativeId != null) {
            b = negativeSlab;
        } else {
            if (isTop) {
                this.isGlassPositive = true;
            } else {
                this.isGlassNegative = true;
            }

            return GLASS_SPRITE_IDS[index];
        }

        if (b == ModBlocks.WHITE_STAINED_GLASS_SLAB) {
            return WHITE_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.LIGHT_GRAY_STAINED_GLASS_SLAB) {
            return LIGHT_GRAY_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.GRAY_STAINED_GLASS_SLAB) {
            return GRAY_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.BLACK_STAINED_GLASS_SLAB) {
            return BLACK_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.BROWN_STAINED_GLASS_SLAB) {
            return BROWN_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.RED_STAINED_GLASS_SLAB) {
            return RED_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.ORANGE_STAINED_GLASS_SLAB) {
            return ORANGE_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.YELLOW_STAINED_GLASS_SLAB) {
            return YELLOW_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.LIME_STAINED_GLASS_SLAB) {
            return LIME_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.GREEN_STAINED_GLASS_SLAB) {
            return GREEN_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.CYAN_STAINED_GLASS_SLAB) {
            return CYAN_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.LIGHT_BLUE_STAINED_GLASS_SLAB) {
            return LIGHT_BLUE_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.BLUE_STAINED_GLASS_SLAB) {
            return BLUE_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.PURPLE_STAINED_GLASS_SLAB) {
            return PURPLE_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.MAGENTA_STAINED_GLASS_SLAB) {
            return MAGENTA_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.PINK_STAINED_GLASS_SLAB) {
            return PINK_STAINED_GLASS_SPRITE_IDS[index];
        } else if (b == ModBlocks.TINTED_GLASS_SLAB) {
            return TINTED_GLASS_SPRITE_IDS[index];
        } else {
            if (isTop) {
                this.isGlassPositive = true;
            } else {
                this.isGlassNegative = true;
            }

            return GLASS_SPRITE_IDS[index];
        }
    }

    private SpriteIdentifier getSlabSpriteIdentifier(int index, boolean isTop) {
        Block b;
        if (isTop && positiveId != null) {
            b = positiveSlab;
        } else if (negativeId != null) {
            b = negativeSlab;
        } else {
            if (isTop) {
                this.isGlassPositive = true;
            } else {
                this.isGlassNegative = true;
            }

            return GLASS_SLAB_SPRITE_IDS[index];
        }

        if (b == ModBlocks.WHITE_STAINED_GLASS_SLAB) {
            return WHITE_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.LIGHT_GRAY_STAINED_GLASS_SLAB) {
            return LIGHT_GRAY_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.GRAY_STAINED_GLASS_SLAB) {
            return GRAY_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.BLACK_STAINED_GLASS_SLAB) {
            return BLACK_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.BROWN_STAINED_GLASS_SLAB) {
            return BROWN_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.RED_STAINED_GLASS_SLAB) {
            return RED_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.ORANGE_STAINED_GLASS_SLAB) {
            return ORANGE_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.YELLOW_STAINED_GLASS_SLAB) {
            return YELLOW_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.LIME_STAINED_GLASS_SLAB) {
            return LIME_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.GREEN_STAINED_GLASS_SLAB) {
            return GREEN_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.CYAN_STAINED_GLASS_SLAB) {
            return CYAN_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.LIGHT_BLUE_STAINED_GLASS_SLAB) {
            return LIGHT_BLUE_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.BLUE_STAINED_GLASS_SLAB) {
            return BLUE_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.PURPLE_STAINED_GLASS_SLAB) {
            return PURPLE_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.MAGENTA_STAINED_GLASS_SLAB) {
            return MAGENTA_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.PINK_STAINED_GLASS_SLAB) {
            return PINK_STAINED_GLASS_SLAB_SPRITE_IDS[index];
        } else if (b == ModBlocks.TINTED_GLASS_SLAB) {
            return TINTED_GLASS_SLAB_SPRITE_IDS[index];
        } else {
            if (isTop) {
                this.isGlassPositive = true;
            } else {
                this.isGlassNegative = true;
            }

            return GLASS_SLAB_SPRITE_IDS[index];
        }
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {
        NeighborState neighborState = neighborComparison(blockRenderView, blockPos);

        if (this.positiveId != null) {
            for (Direction face : Direction.values()) {
                if (this.positiveSlab == null || shouldCullPositive(face, neighborState)) {
                    continue;
                }

                EnumMap<Direction, Mesh> faceMeshes;
                if (face == Direction.UP || face == Direction.DOWN) {
                    for (int index : getEndPatternIndexes(face, neighborState, true)) {
                        faceMeshes = endPositiveMeshMap.get(index);
                        if (faceMeshes == null) return;

                        Mesh mesh = faceMeshes.get(face);
                        if (mesh != null) {
                            mesh.outputTo(renderContext.getEmitter());
                        }
                    }
                    faceMeshes = endPositiveMeshMap.get((isGlassPositive ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT)-1);
                    if (faceMeshes == null) return;

                    Mesh mesh = faceMeshes.get(face);
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
                    }
                } else {
                    faceMeshes = positiveMeshMap.get(getSidePatternIndex(face, neighborState, true));
                    if (faceMeshes == null) return;

                    Mesh mesh = faceMeshes.get(face);
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
                    }
                }
            }
        }

        if (this.negativeId != null) {
            for (Direction face : Direction.values()) {
                if (this.negativeSlab == null || shouldCullNegative(face, neighborState)) {
                    continue;
                }

                EnumMap<Direction, Mesh> faceMeshes;
                if (face == Direction.UP || face == Direction.DOWN) {
                    for (int index : getEndPatternIndexes(face, neighborState, false)) {
                        faceMeshes = endNegativeMeshMap.get(index);
                        if (faceMeshes == null) return;

                        Mesh mesh = faceMeshes.get(face);
                        if (mesh != null) {
                            mesh.outputTo(renderContext.getEmitter());
                        }
                    }
                    faceMeshes = endNegativeMeshMap.get((isGlassNegative ? GLASS_PATTERN_COUNT : STAINED_GLASS_PATTERN_COUNT)-1);
                    if (faceMeshes == null) return;

                    Mesh mesh = faceMeshes.get(face);
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
                    }
                } else {
                    faceMeshes = negativeMeshMap.get(getSidePatternIndex(face, neighborState, false));
                    if (faceMeshes == null) return;

                    Mesh mesh = faceMeshes.get(face);
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
                    }
                }
            }
        }
    }

    private List<Integer> getEndPatternIndexes(Direction face, NeighborState neighborState, boolean isPositive) {
        List<Integer> indexes;
        if (isPositive) {
            indexes = determinePatternEndPositive(face, neighborState);
            if (this.isGlassPositive) indexes.replaceAll(i -> i - (i / 6));
        } else {
            indexes = determinePatternEndNegative(face, neighborState);
            if (this.isGlassNegative) indexes.replaceAll(i -> i - (i / 6));
        }

        return indexes;
    }

    private int getSidePatternIndex(Direction face, NeighborState neighborState, boolean isPositive) {
        int index;
        if (isPositive) {
            index = determinePatternPositive(face, neighborState);
        } else {
            index = determinePatternNegative(face, neighborState);
        }

        if (index >= 64) {
            index = SLAB_INDEX_MAP.getOrDefault(index, 0);
        }

        return index;
    }

    private List<Integer> determinePatternEndPositive(Direction face, NeighborState neighborState) {
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

        if (face == Direction.UP) {
            if (neighborState.eastBlockTop == FULL || neighborState.eastBlockTop == POSITIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (neighborState.eastBlockTop == POSITIVE2) {
                rightBottom = true;
            } else if (neighborState.eastBlockTop == NEGATIVE2) {
                rightTop = true;
            }
            if (neighborState.southBlockTop == FULL || neighborState.southBlockTop == POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.southBlockTop == POSITIVE2) {
                bottomRight = true;
            } else if (neighborState.southBlockTop == NEGATIVE2) {
                bottomLeft = true;
            }
            if (neighborState.westBlockTop == FULL || neighborState.westBlockTop == POSITIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (neighborState.westBlockTop == POSITIVE2) {
                leftBottom = true;
            } else if (neighborState.westBlockTop == NEGATIVE2) {
                leftTop = true;
            }
            if (neighborState.northBlockTop == FULL || neighborState.northBlockTop == POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.northBlockTop == POSITIVE2) {
                topRight = true;
            } else if (neighborState.northBlockTop == NEGATIVE2) {
                topLeft = true;
            }
            if (topRight && rightTop) {
                if (neighborState.northEastBlockTop == FULL) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (neighborState.southEastBlockTop == FULL) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (neighborState.southWestBlockTop == FULL) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (neighborState.northWestBlockTop == FULL) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.DOWN) {
            if (neighborState.eastBlockTop == FULL || neighborState.eastBlockTop == POSITIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (neighborState.eastBlockTop == POSITIVE2) {
                rightTop = true;
            } else if (neighborState.eastBlockTop == NEGATIVE2) {
                rightBottom = true;
            }
            if (neighborState.southBlockTop == FULL || neighborState.southBlockTop == POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.southBlockTop == POSITIVE2) {
                topRight = true;
            } else if (neighborState.southBlockTop == NEGATIVE2) {
                topLeft = true;
            }
            if (neighborState.westBlockTop == FULL || neighborState.westBlockTop == POSITIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (neighborState.westBlockTop == POSITIVE2) {
                leftTop = true;
            } else if (neighborState.westBlockTop == NEGATIVE2) {
                leftBottom = true;
            }
            if (neighborState.northBlockTop == FULL || neighborState.northBlockTop == POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.northBlockTop == POSITIVE2) {
                bottomRight = true;
            } else if (neighborState.northBlockTop == NEGATIVE2) {
                bottomLeft = true;
            }
            if (topRight && rightTop) {
                if (neighborState.southEastBlockTop == FULL) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (neighborState.northEastBlockTop == FULL) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (neighborState.northWestBlockTop == FULL) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (neighborState.southWestBlockTop == FULL) {
                    cornerTopLeft = true;
                }
            }
        }

        return determineEndPatternIndexes(new ConnectionFlags(
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
        ), this.isGlassPositive);
    }

    private List<Integer> determinePatternEndNegative(Direction face, NeighborState neighborState) {
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

        if (face == Direction.UP) {
            if (neighborState.eastBlockBottom == FULL || neighborState.eastBlockBottom == NEGATIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (neighborState.eastBlockBottom == POSITIVE2) {
                rightBottom = true;
            } else if (neighborState.eastBlockBottom == NEGATIVE2) {
                rightTop = true;
            }
            if (neighborState.southBlockBottom == FULL || neighborState.southBlockBottom == NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.southBlockBottom == POSITIVE2) {
                bottomRight = true;
            } else if (neighborState.southBlockBottom == NEGATIVE2) {
                bottomLeft = true;
            }
            if (neighborState.westBlockBottom == FULL || neighborState.westBlockBottom == NEGATIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (neighborState.westBlockBottom == POSITIVE2) {
                leftBottom = true;
            } else if (neighborState.westBlockBottom == NEGATIVE2) {
                leftTop = true;
            }
            if (neighborState.northBlockBottom == FULL || neighborState.northBlockBottom == NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.northBlockBottom == POSITIVE2) {
                topRight = true;
            } else if (neighborState.northBlockBottom == NEGATIVE2) {
                topLeft = true;
            }
            if (topRight && rightTop) {
                if (neighborState.northEastBlockBottom == FULL) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (neighborState.southEastBlockBottom == FULL) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (neighborState.southWestBlockBottom == FULL) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (neighborState.northWestBlockBottom == FULL) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.DOWN) {
            if (neighborState.eastBlockBottom == FULL || neighborState.eastBlockBottom == NEGATIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (neighborState.eastBlockBottom == POSITIVE2) {
                rightTop = true;
            } else if (neighborState.eastBlockBottom == NEGATIVE2) {
                rightBottom = true;
            }
            if (neighborState.southBlockBottom == FULL || neighborState.southBlockBottom == NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.southBlockBottom == POSITIVE2) {
                topRight = true;
            } else if (neighborState.southBlockBottom == NEGATIVE2) {
                topLeft = true;
            }
            if (neighborState.westBlockBottom == FULL || neighborState.westBlockBottom == NEGATIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (neighborState.westBlockBottom == POSITIVE2) {
                leftTop = true;
            } else if (neighborState.westBlockBottom == NEGATIVE2) {
                leftBottom = true;
            }
            if (neighborState.northBlockBottom == FULL || neighborState.northBlockBottom == NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.northBlockBottom == POSITIVE2) {
                bottomRight = true;
            } else if (neighborState.northBlockBottom == NEGATIVE2) {
                bottomLeft = true;
            }
            if (topRight && rightTop) {
                if (neighborState.southEastBlockBottom == FULL) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (neighborState.northEastBlockBottom == FULL) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (neighborState.northWestBlockBottom == FULL) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (neighborState.southWestBlockBottom == FULL) {
                    cornerTopLeft = true;
                }
            }
        }

        return determineEndPatternIndexes(new ConnectionFlags(
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
        ), this.isGlassNegative);
    }

    private int determinePatternPositive(Direction face, NeighborState neighborState) {
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

        if (face == Direction.EAST) {
            if (neighborState.upBlock == FULL || neighborState.upBlock == POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.upBlock == POSITIVE2) {
                topLeft = true;
            } else if (neighborState.upBlock == NEGATIVE2) {
                topRight = true;
            }
            left = neighborState.southBlockTop == FULL || neighborState.southBlockTop == POSITIVE1 || neighborState.southBlockTop == POSITIVE2;
            right = neighborState.northBlockTop == FULL || neighborState.northBlockTop == POSITIVE1 || neighborState.northBlockTop == POSITIVE2;
            if (neighborState.isSameSlab) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.topNorthBlock == FULL || neighborState.topNorthBlock == POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.northBlockTop == FULL || neighborState.northBlockTop == POSITIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.southBlockTop == FULL || neighborState.southBlockTop == POSITIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.topSouthBlock == FULL || neighborState.topSouthBlock == POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.SOUTH) {
            if (neighborState.upBlock == FULL || neighborState.upBlock == POSITIVE2) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.upBlock == NEGATIVE1) {
                topLeft = true;
            } else if (neighborState.upBlock == POSITIVE1) {
                topRight = true;
            }
            left = neighborState.westBlockTop == FULL || neighborState.westBlockTop == POSITIVE1 || neighborState.westBlockTop == POSITIVE2;
            right = neighborState.eastBlockTop == FULL || neighborState.eastBlockTop == POSITIVE1 || neighborState.eastBlockTop == POSITIVE2;
            if (neighborState.isSameSlab) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.topEastBlock == FULL || neighborState.topEastBlock == POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.eastBlockTop == FULL || neighborState.eastBlockTop == POSITIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.westBlockTop == FULL || neighborState.westBlockTop == POSITIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.topWestBlock == FULL || neighborState.topWestBlock == POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            if (neighborState.upBlock == FULL || neighborState.upBlock == NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.upBlock == NEGATIVE2) {
                topLeft = true;
            } else if (neighborState.upBlock == POSITIVE2) {
                topRight = true;
            }
            left = neighborState.northBlockTop == FULL || neighborState.northBlockTop == POSITIVE1 || neighborState.northBlockTop == NEGATIVE2;
            right = neighborState.southBlockTop == FULL || neighborState.southBlockTop == POSITIVE1 || neighborState.southBlockTop == NEGATIVE2;
            if (neighborState.isSameSlab) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.topSouthBlock == FULL || neighborState.topSouthBlock == NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.southBlockTop == FULL || neighborState.southBlockTop == NEGATIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.northBlockTop == FULL || neighborState.northBlockTop == NEGATIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.topNorthBlock == FULL || neighborState.topNorthBlock == NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.NORTH) {
            if (neighborState.upBlock == FULL || neighborState.upBlock == NEGATIVE2) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.upBlock == POSITIVE1) {
                topLeft = true;
            } else if (neighborState.upBlock == NEGATIVE1) {
                topRight = true;
            }
            left = neighborState.eastBlockTop == FULL || neighborState.eastBlockTop == POSITIVE1 || neighborState.eastBlockTop == NEGATIVE2;
            right = neighborState.westBlockTop == FULL || neighborState.westBlockTop == POSITIVE1 || neighborState.westBlockTop == NEGATIVE2;
            if (neighborState.isSameSlab) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.topWestBlock == FULL || neighborState.topWestBlock == NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.westBlockTop == FULL || neighborState.westBlockTop == NEGATIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.eastBlockTop == FULL || neighborState.eastBlockTop == NEGATIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.topEastBlock == FULL || neighborState.topEastBlock == NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        }

        return determineSidePatternIndex(new ConnectionFlags(
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
        ));
    }

    private int determinePatternNegative(Direction face, NeighborState neighborState) {
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

        if (face == Direction.EAST) {
            if (neighborState.isSameSlab) {
                topLeft = true;
                topRight = true;
            }
            left = neighborState.southBlockBottom == FULL || neighborState.southBlockBottom == NEGATIVE1 || neighborState.southBlockBottom == POSITIVE2;
            right = neighborState.northBlockBottom == FULL || neighborState.northBlockBottom == NEGATIVE1 || neighborState.northBlockBottom == POSITIVE2;
            if (neighborState.downBlock == FULL || neighborState.downBlock == POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.downBlock == POSITIVE2) {
                bottomLeft = true;
            } else if (neighborState.downBlock == NEGATIVE2) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.northBlockBottom == FULL || neighborState.northBlockTop == POSITIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.bottomNorthBlock == FULL || neighborState.bottomNorthBlock == POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.bottomSouthBlock == FULL || neighborState.bottomSouthBlock == POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.southBlockBottom == FULL || neighborState.southBlockTop == POSITIVE2) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.SOUTH) {
            if (neighborState.isSameSlab) {
                topLeft = true;
                topRight = true;
            }
            left = neighborState.westBlockBottom == FULL || neighborState.westBlockBottom == NEGATIVE1 || neighborState.westBlockBottom == POSITIVE2;
            right = neighborState.eastBlockBottom == FULL || neighborState.eastBlockBottom == NEGATIVE1 || neighborState.eastBlockBottom == POSITIVE2;
            if (neighborState.downBlock == FULL || neighborState.downBlock == POSITIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.downBlock == NEGATIVE1) {
                bottomLeft = true;
            } else if (neighborState.downBlock == POSITIVE1) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.eastBlockBottom == FULL || neighborState.eastBlockTop == POSITIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.bottomEastBlock == FULL || neighborState.bottomEastBlock == POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.bottomWestBlock == FULL || neighborState.bottomWestBlock == POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.westBlockBottom == FULL || neighborState.westBlockTop == POSITIVE2) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            if (neighborState.isSameSlab) {
                topLeft = true;
                topRight = true;
            }
            left = neighborState.northBlockBottom == FULL || neighborState.northBlockBottom == NEGATIVE1 || neighborState.northBlockBottom == NEGATIVE2;
            right = neighborState.southBlockBottom == FULL || neighborState.southBlockBottom == NEGATIVE1 || neighborState.southBlockBottom == NEGATIVE2;
            if (neighborState.downBlock == FULL || neighborState.downBlock == NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.downBlock == NEGATIVE2) {
                bottomLeft = true;
            } else if (neighborState.downBlock == POSITIVE2) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.southBlockBottom == FULL || neighborState.southBlockTop == NEGATIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.bottomSouthBlock == FULL || neighborState.bottomSouthBlock == NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.bottomNorthBlock == FULL || neighborState.bottomNorthBlock == NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.northBlockBottom == FULL || neighborState.northBlockTop == NEGATIVE2) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.NORTH) {
            if (neighborState.isSameSlab) {
                topLeft = true;
                topRight = true;
            }
            left = neighborState.eastBlockBottom == FULL || neighborState.eastBlockBottom == NEGATIVE1 || neighborState.eastBlockBottom == NEGATIVE2;
            right = neighborState.westBlockBottom == FULL || neighborState.westBlockBottom == NEGATIVE1 || neighborState.westBlockBottom == NEGATIVE2;
            if (neighborState.downBlock == FULL || neighborState.downBlock == NEGATIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.downBlock == POSITIVE1) {
                bottomLeft = true;
            } else if (neighborState.downBlock == NEGATIVE1) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.westBlockBottom == FULL || neighborState.westBlockTop == NEGATIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.bottomWestBlock == FULL || neighborState.bottomWestBlock == NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.bottomEastBlock == FULL || neighborState.bottomEastBlock == NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.eastBlockBottom == FULL || neighborState.eastBlockTop == NEGATIVE2) {
                    cornerTopLeft = true;
                }
            }
        }

        return determineSidePatternIndex(new ConnectionFlags(
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
        ));
    }

    private List<Integer> determineEndPatternIndexes(ConnectionFlags flags, boolean isGlass) {
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

    private int determineSidePatternIndex(ConnectionFlags flags) {
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

    private NeighborState neighborComparison(BlockRenderView world, BlockPos pos) {
        boolean isSameSlab;
        int upBlock, downBlock;
        int eastBlockTop, southBlockTop, westBlockTop, northBlockTop;
        int eastBlockBottom, southBlockBottom, westBlockBottom, northBlockBottom;
        int topEastBlock, topSouthBlock, topWestBlock, topNorthBlock;
        int southEastBlockTop, southWestBlockTop, northWestBlockTop, northEastBlockTop;
        int southEastBlockBottom, southWestBlockBottom, northWestBlockBottom, northEastBlockBottom;
        int bottomEastBlock, bottomSouthBlock, bottomWestBlock, bottomNorthBlock;

        isSameSlab = positiveSlab == negativeSlab;

        // UP
        upBlock = topNeighborComparison(world, pos);

        // DOWN
        downBlock = bottomNeighborComparison(world, pos);

        // EAST,SOUTH,WEST,NORTH
        eastBlockTop = checkHorizontalNeighborComparison(world, pos, Direction.EAST, positiveSlab);
        southBlockTop = checkHorizontalNeighborComparison(world, pos, Direction.SOUTH, positiveSlab);
        westBlockTop = checkHorizontalNeighborComparison(world, pos, Direction.WEST, positiveSlab);
        northBlockTop = checkHorizontalNeighborComparison(world, pos, Direction.NORTH, positiveSlab);

        if (isSameSlab) {
            eastBlockBottom = eastBlockTop;
            southBlockBottom = southBlockTop;
            westBlockBottom = westBlockTop;
            northBlockBottom = northBlockTop;
        } else {
            eastBlockBottom = checkHorizontalNeighborComparison(world, pos, Direction.EAST, negativeSlab);
            southBlockBottom = checkHorizontalNeighborComparison(world, pos, Direction.SOUTH, negativeSlab);
            westBlockBottom = checkHorizontalNeighborComparison(world, pos, Direction.WEST, negativeSlab);
            northBlockBottom = checkHorizontalNeighborComparison(world, pos, Direction.NORTH, negativeSlab);
        }

        // TOP-EAST
        topEastBlock = checkTopEdgeConnection(world, pos, Direction.EAST, upBlock, eastBlockTop);

        // TOP-SOUTH
        topSouthBlock = checkTopEdgeConnection(world, pos, Direction.SOUTH, upBlock, southBlockTop);

        // TOP-WEST
        topWestBlock = checkTopEdgeConnection(world, pos, Direction.WEST, upBlock, westBlockTop);

        // TOP-NORTH
        topNorthBlock = checkTopEdgeConnection(world, pos, Direction.NORTH, upBlock, northBlockTop);

        // SOUTH-EAST-TOP
        southEastBlockTop = checkSideDiagonalConnection(world, pos, Direction.SOUTH, Direction.EAST, southBlockTop, eastBlockTop, true);

        // SOUTH-EAST-BOTTOM
        southEastBlockBottom = checkSideDiagonalConnection(world, pos, Direction.SOUTH, Direction.EAST, southBlockBottom, eastBlockBottom, false);

        // SOUTH-WEST-TOP
        southWestBlockTop = checkSideDiagonalConnection(world, pos, Direction.SOUTH, Direction.WEST, southBlockTop, westBlockTop, true);

        // SOUTH-WEST-BOTTOM
        southWestBlockBottom = checkSideDiagonalConnection(world, pos, Direction.SOUTH, Direction.WEST, southBlockBottom, westBlockBottom, false);

        // NORTH-EAST-TOP
        northEastBlockTop = checkSideDiagonalConnection(world, pos, Direction.NORTH, Direction.EAST, northBlockTop, eastBlockTop, true);

        // NORTH-EAST-BOTTOM
        northEastBlockBottom = checkSideDiagonalConnection(world, pos, Direction.NORTH, Direction.EAST, northBlockBottom, eastBlockBottom, false);

        // NORTH-WEST-TOP
        northWestBlockTop = checkSideDiagonalConnection(world, pos, Direction.NORTH, Direction.WEST, northBlockTop, westBlockTop, true);

        // NORTH-WEST-BOTTOM
        northWestBlockBottom = checkSideDiagonalConnection(world, pos, Direction.NORTH, Direction.WEST, northBlockBottom, westBlockBottom, false);

        // BOTTOM-EAST
        bottomEastBlock = checkBottomEdgeConnection(world, pos, Direction.EAST, downBlock, eastBlockBottom);

        // BOTTOM-SOUTH
        bottomSouthBlock = checkBottomEdgeConnection(world, pos, Direction.SOUTH, downBlock, southBlockBottom);

        // BOTTOM-WEST
        bottomWestBlock = checkBottomEdgeConnection(world, pos, Direction.WEST, downBlock, westBlockBottom);

        // BOTTOM-NORTH
        bottomNorthBlock = checkBottomEdgeConnection(world, pos, Direction.NORTH, downBlock, northBlockBottom);

        return new NeighborState(
                isSameSlab,
                upBlock, downBlock,
                eastBlockTop, southBlockTop, westBlockTop, northBlockTop,
                eastBlockBottom, southBlockBottom, westBlockBottom, northBlockBottom,
                topEastBlock, topSouthBlock, topWestBlock, topNorthBlock,
                southEastBlockTop, southWestBlockTop, northWestBlockTop, northEastBlockTop,
                southEastBlockBottom, southWestBlockBottom, northWestBlockBottom, northEastBlockBottom,
                bottomEastBlock, bottomSouthBlock, bottomWestBlock, bottomNorthBlock
        );
    }

    private int topNeighborComparison(BlockRenderView world, BlockPos pos) {
        BlockPos otherPos = pos.up();
        BlockState otherState = world.getBlockState(otherPos);
        Block otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                return FULL;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            if (positiveSlab == entity.getNegativeSlabState().getBlock()) {
                return FULL;
            }
        } else if (otherBlock instanceof VerticalSlabBlock) {
            switch (otherState.get(VerticalSlabBlock.FACING)) {
                case EAST -> {
                    return POSITIVE1;
                }
                case SOUTH -> {
                    return POSITIVE2;
                }
                case WEST -> {
                    return NEGATIVE1;
                }
                case NORTH -> {
                    return NEGATIVE2;
                }
                default -> {
                    return OTHER;
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
            boolean bl1 = b == entity.getPositiveSlabState().getBlock();
            boolean bl2 = b == entity.getNegativeSlabState().getBlock();

            if (bl1 && bl2) {
                return FULL;
            } else if (bl1) {
                return entity.isX() ? POSITIVE1 : POSITIVE2;
            } else if (bl2) {
                return entity.isX() ? NEGATIVE1 : NEGATIVE2;
            }
        } else {
            if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                return FULL;
            }
        }

        return OTHER;
    }

    private int bottomNeighborComparison(BlockRenderView world, BlockPos pos) {
        BlockPos otherPos = pos.down();
        BlockState otherState = world.getBlockState(otherPos);
        Block otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (negativeSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP) {
                return FULL;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            if (negativeSlab == entity.getPositiveSlabState().getBlock()) {
                return FULL;
            }
        } else if (otherBlock instanceof VerticalSlabBlock) {
            switch (otherState.get(VerticalSlabBlock.FACING)) {
                case EAST -> {
                    return POSITIVE1;
                }
                case SOUTH -> {
                    return POSITIVE2;
                }
                case WEST -> {
                    return NEGATIVE1;
                }
                case NORTH -> {
                    return NEGATIVE2;
                }
                default -> {
                    return OTHER;
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
            boolean bl1 = b == entity.getPositiveSlabState().getBlock();
            boolean bl2 = b == entity.getNegativeSlabState().getBlock();
            if (bl1 && bl2) {
                return FULL;
            } else if (bl1) {
                return entity.isX() ? POSITIVE1 : POSITIVE2;
            } else if (bl2) {
                return entity.isX() ? NEGATIVE1 : NEGATIVE2;
            }
        } else {
            if (ModBlockMap.slabToOriginal(negativeSlab) == otherBlock) {
                return FULL;
            }
        }

        return OTHER;
    }

    private int checkHorizontalNeighborComparison(BlockRenderView world, BlockPos pos, Direction dir, Block slab) {
        BlockPos otherPos = pos.offset(dir);
        BlockState otherState = world.getBlockState(otherPos);
        Block otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (slab == otherBlock) {
                return otherState.get(SlabBlock.TYPE) == SlabType.TOP ? POSITIVE1 : NEGATIVE1;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            boolean bl1 = slab == entity.getPositiveSlabState().getBlock();
            boolean bl2 = slab == entity.getNegativeSlabState().getBlock();
            if (bl1 && bl2) {
                return FULL;
            } else if (bl1) {
                return POSITIVE1;
            } else if (bl2) {
                return NEGATIVE1;
            }
        } else if (otherBlock instanceof VerticalSlabBlock) {
            if (slab == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                if (d == dir.getOpposite()) {
                    return FULL;
                } else if (d != dir) {
                    if (d == Direction.EAST || d == Direction.SOUTH) {
                        return POSITIVE2;
                    } else {
                        return NEGATIVE2;
                    }
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            if ((dir == Direction.EAST || dir == Direction.WEST) == entity.isX()) {
                if (slab == ModBlockMap.verticalSlabToSlab(entity.getPositiveSlabState().getBlock())) {
                    return FULL;
                }
            } else {
                Block b = ModBlockMap.slabToVerticalSlab(slab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();
                if (bl1 && bl2) {
                    return FULL;
                } else if (bl1) {
                    return POSITIVE2;
                } else if (bl2) {
                    return NEGATIVE2;
                }
            }
        } else {
            if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                return FULL;
            }
        }

        return OTHER;
    }

    private int checkTopEdgeConnection(BlockRenderView world, BlockPos pos, Direction dir, int upBlock, int sideBlockTop) {
        int targetU;
        if (dir == Direction.EAST) {
            targetU = NEGATIVE1;
        } else if (dir == Direction.SOUTH) {
            targetU = NEGATIVE2;
        } else if (dir == Direction.WEST) {
            targetU = POSITIVE1;
        } else if (dir == Direction.NORTH) {
            targetU = POSITIVE2;
        } else {
            return OTHER;
        }

        if (upBlock != OTHER && upBlock != targetU && sideBlockTop != OTHER && sideBlockTop != NEGATIVE1) {
            BlockPos otherPos = pos.up().offset(dir);
            BlockState otherState = world.getBlockState(otherPos);
            Block otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                    return FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (positiveSlab == entity.getNegativeSlabState().getBlock()) {
                    return FULL;
                }
            } else if (otherBlock instanceof VerticalSlabBlock) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                if (dir == d.getOpposite()) {
                    return FULL;
                } else if (dir != d) {
                    if (d == Direction.EAST || d == Direction.SOUTH) {
                        return POSITIVE1;
                    } else {
                        return NEGATIVE1;
                    }
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                return checkEdgeConnectionToDVS(bl1, bl2, entity.isX(), dir);
            } else {
                if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                    return FULL;
                }
            }
        }

        return OTHER;
    }

    private int checkSideDiagonalConnection(BlockRenderView world, BlockPos pos, Direction dirZ, Direction dirX, int sideBlockZ, int sideBlockX, boolean isTop) {
        int blockType = isTop ? NEGATIVE1 : POSITIVE1;
        if (sideBlockZ != OTHER && sideBlockZ != blockType && sideBlockX != OTHER && sideBlockX != blockType) {
            Block block = isTop ? positiveSlab : negativeSlab;
            BlockPos otherPos = pos.offset(dirZ).offset(dirX);
            BlockState otherState = world.getBlockState(otherPos);
            Block otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (block == otherBlock) {
                    SlabType type = otherState.get(SlabBlock.TYPE);
                    if ((isTop && type == SlabType.TOP) || (!isTop && type == SlabType.BOTTOM)) {
                        return FULL;
                    }
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                Block compareBlock = isTop ? entity.getPositiveSlabState().getBlock() : entity.getNegativeSlabState().getBlock();
                if (block == compareBlock) {
                    return FULL;
                }
            } else if (otherBlock instanceof VerticalSlabBlock) {
                if (block == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                    Direction d = otherState.get(VerticalSlabBlock.FACING).getOpposite();
                    if (d == dirZ || d == dirX) {
                        return FULL;
                    }
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = entity.isX()
                        ? (dirX == Direction.EAST ? entity.getNegativeSlabState().getBlock() : entity.getPositiveSlabState().getBlock())
                        : (dirZ == Direction.SOUTH ? entity.getNegativeSlabState().getBlock() : entity.getPositiveSlabState().getBlock());
                if (b == ModBlockMap.slabToVerticalSlab(block)) {
                    return FULL;
                }
            } else {
                if (ModBlockMap.slabToOriginal(block) == otherBlock) {
                    return FULL;
                }
            }
        }

        return OTHER;
    }

    private int checkBottomEdgeConnection(BlockRenderView world, BlockPos pos, Direction dir, int downBlock, int sideBlockBottom) {
        int targetD;
        if (dir == Direction.EAST) {
            targetD = NEGATIVE1;
        } else if (dir == Direction.SOUTH) {
            targetD = NEGATIVE2;
        } else if (dir == Direction.WEST) {
            targetD = POSITIVE1;
        } else if (dir == Direction.NORTH) {
            targetD = POSITIVE2;
        } else {
            return OTHER;
        }

        if (downBlock != OTHER && downBlock != targetD && sideBlockBottom != OTHER && sideBlockBottom != POSITIVE1) {
            BlockPos otherPos = pos.down().offset(dir);
            BlockState otherState = world.getBlockState(otherPos);
            Block otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (negativeSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP) {
                    return FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (negativeSlab == entity.getPositiveSlabState().getBlock()) {
                    return FULL;
                }
            } else if (otherBlock instanceof VerticalSlabBlock) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                if (dir == d.getOpposite()) {
                    return FULL;
                } else if (dir != d) {
                    if (d == Direction.EAST || d == Direction.SOUTH) {
                        return POSITIVE1;
                    } else {
                        return NEGATIVE1;
                    }
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                return checkEdgeConnectionToDVS(bl1, bl2, entity.isX(), dir);
            } else {
                if (ModBlockMap.slabToOriginal(negativeSlab) == otherBlock) {
                    return FULL;
                }
            }
        }

        return OTHER;
    }

    private int checkEdgeConnectionToDVS(boolean matchesPositive, boolean matchesNegative, boolean isX, Direction dir) {
        if ((dir == Direction.EAST || dir == Direction.WEST) == isX) {
            if (dir == Direction.EAST || dir == Direction.SOUTH) {
                if (matchesNegative) return FULL;
            } else {
                if (matchesPositive) return FULL;
            }
        } else {
            if (matchesPositive && matchesNegative) {
                return FULL;
            } else if (matchesPositive) {
                return POSITIVE1;
            } else if (matchesNegative) {
                return NEGATIVE1;
            }
        }

        return OTHER;
    }

    private boolean shouldCullPositive(Direction face, NeighborState neighborState) {
        if (face == Direction.UP) {
            return neighborState.upBlock == FULL;
        } else if (face == Direction.DOWN) {
            return neighborState.isSameSlab;
        } else if (face == Direction.EAST) {
            return neighborState.eastBlockTop == FULL || neighborState.eastBlockTop == POSITIVE1;
        } else if (face == Direction.SOUTH) {
            return neighborState.southBlockTop == FULL || neighborState.southBlockTop == POSITIVE1;
        } else if (face == Direction.WEST) {
            return neighborState.westBlockTop == FULL || neighborState.westBlockTop == POSITIVE1;
        } else {
            return neighborState.northBlockTop == FULL || neighborState.northBlockTop == POSITIVE1;
        }
    }

    private boolean shouldCullNegative(Direction face, NeighborState neighborState) {
        if (face == Direction.UP) {
            return neighborState.isSameSlab;
        } else if (face == Direction.DOWN) {
            return neighborState.downBlock == FULL;
        } else if (face == Direction.EAST) {
            return neighborState.eastBlockBottom == FULL || neighborState.eastBlockBottom == NEGATIVE1;
        } else if (face == Direction.SOUTH) {
            return neighborState.southBlockBottom == FULL || neighborState.southBlockBottom == NEGATIVE1;
        } else if (face == Direction.WEST) {
            return neighborState.westBlockBottom == FULL || neighborState.westBlockBottom == NEGATIVE1;
        } else {
            return neighborState.northBlockBottom == FULL || neighborState.northBlockBottom == NEGATIVE1;
        }
    }
}
