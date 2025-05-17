package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.Slabee;
import com.forestotzka.yurufu.slabee.block.*;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
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
public class DoubleVerticalSlabBlockConnectGlassModelX implements UnbakedModel, BakedModel, FabricBakedModel {
    private final Identifier positiveId;
    private final Identifier negativeId;
    private final Block positiveSlab;
    private final Block negativeSlab;
    private final boolean isX;
    private BakedModel positiveBakedModel;
    private BakedModel negativeBakedModel;
    private BakedModel nullBakedModel;

    private final VerticalModelRotation Y0 = new VerticalModelRotation(ModelRotation.X0_Y0.getRotation(), true);
    private final VerticalModelRotation Y90 = new VerticalModelRotation(ModelRotation.X0_Y90.getRotation(), true);
    private final VerticalModelRotation Y180 = new VerticalModelRotation(ModelRotation.X0_Y180.getRotation(), true);
    private final VerticalModelRotation Y270 = new VerticalModelRotation(ModelRotation.X0_Y270.getRotation(), true);

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
        /*for (int i = 0; i < STAINED_GLASS_PATTERN_COUNT-1; i++) {
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
        }*/
        GLASS_SPRITE_IDS[GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/glass/center"));
        /*WHITE_STAINED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/white_stained_glass/center"));
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
        TINTED_GLASS_SPRITE_IDS[STAINED_GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/tinted_glass/center"));*/

        for (int i = 0; i < SLAB_PATTERN_COUNT; i++) {
            GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/glass_vertical_slab/" + i));
            /*WHITE_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/white_stained_glass_vertical_slab/" + i));
            LIGHT_GRAY_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_gray_stained_glass_vertical_slab/" + i));
            GRAY_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/gray_stained_glass_vertical_slab/" + i));
            BLACK_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/black_stained_glass_vertical_slab/" + i));
            BROWN_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/brown_stained_glass_vertical_slab/" + i));
            RED_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/red_stained_glass_vertical_slab/" + i));
            ORANGE_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/orange_stained_glass_vertical_slab/" + i));
            YELLOW_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/yellow_stained_glass_vertical_slab/" + i));
            LIME_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/lime_stained_glass_vertical_slab/" + i));
            GREEN_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/green_stained_glass_vertical_slab/" + i));
            CYAN_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/cyan_stained_glass_vertical_slab/" + i));
            LIGHT_BLUE_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/light_blue_stained_glass_vertical_slab/" + i));
            BLUE_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/blue_stained_glass_vertical_slab/" + i));
            PURPLE_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/purple_stained_glass_vertical_slab/" + i));
            MAGENTA_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/magenta_stained_glass_vertical_slab/" + i));
            PINK_STAINED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/pink_stained_glass_vertical_slab/" + i));
            TINTED_GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of(Slabee.MOD_ID, "block/tinted_glass_vertical_slab/" + i));*/
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
            int eastBlock, int westBlock,
            int upBlockPositive, int southBlockPositive, int downBlockPositive, int northBlockPositive,
            int upBlockNegative, int southBlockNegative, int downBlockNegative, int northBlockNegative,
            int eastUpBlock, int eastSouthBlock, int eastWestBlock, int eastNorthBlock,
            int southUpBlockPositive, int southDownBlockPositive, int northDownBlockPositive, int northUpBlockPositive,
            int southUpBlockNegative, int southDownBlockNegative, int northDownBlockNegative, int northUpBlockNegative,
            int westUpBlock, int westSouthBlock, int westDownBlock, int westNorthBlock
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

    public DoubleVerticalSlabBlockConnectGlassModelX(@Nullable Block positiveSlab, @Nullable Block negativeSlab) {
        this.positiveSlab = positiveSlab;
        this.negativeSlab = negativeSlab;
        this.isX = true;

        if (this.positiveSlab != null) {
            Identifier positiveId = Registries.BLOCK.getId(positiveSlab);

            this.positiveId = Identifier.of(positiveId.getNamespace(), "block/" + positiveId.getPath() + "_x");
        } else {
            this.positiveId = null;
        }

        if (this.negativeSlab != null) {
            Identifier negativeId = Registries.BLOCK.getId(negativeSlab);

            if (isX) {
                this.negativeId = Identifier.of(negativeId.getNamespace(), "block/" + negativeId.getPath() + "_x");
            } else {
                this.negativeId = Identifier.of(negativeId.getNamespace(), "block/" + negativeId.getPath() + "_z");
            }
        } else {
            this.negativeId = null;
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
                    if (isEnd(dir)) {
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

                    if (dir == Direction.EAST) {
                        emitter.square(dir, 0, 0, 1, 1, 0f);
                        emitter.spriteBake(textureGetter.apply(getFullBlockSpriteIdentifier(patternIndex, true)), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endPositiveFaceMeshes.put(dir, meshBuilder.build());
                    } else if (dir == Direction.WEST) {
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

                    if (dir == Direction.EAST) {
                        emitter.square(dir, 0, 0, 1, 1, 0.5f);
                        emitter.spriteBake(textureGetter.apply(getFullBlockSpriteIdentifier(patternIndex, false)), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endNegativeFaceMeshes.put(dir, meshBuilder.build());
                    } else if (dir == Direction.WEST) {
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
        DoubleVerticalSlabBlockConnectGlassModelX.NeighborState neighborState = neighborComparison(blockRenderView, blockPos);

        if (this.positiveId != null) {
            for (Direction face : Direction.values()) {
                if (this.positiveSlab == null || shouldCullPositive(face, neighborState)) {
                    continue;
                }

                EnumMap<Direction, Mesh> faceMeshes;
                if (isEnd(face)) {
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
                    faceMeshes = positiveMeshMap.get(/*getSidePatternIndex(face, neighborState, true)*/0);
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
                if (isEnd(face)) {
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
                    faceMeshes = negativeMeshMap.get(/*getSidePatternIndex(face, neighborState, false)*/0);
                    if (faceMeshes == null) return;

                    Mesh mesh = faceMeshes.get(face);
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
                    }
                }
            }
        }
    }

    private List<Integer> getEndPatternIndexes(Direction face, DoubleVerticalSlabBlockConnectGlassModelX.NeighborState neighborState, boolean isPositive) {
        List<Integer> indexes;
        if (isPositive) {
            indexes = determinePatternEndPositive(face, neighborState);
            if (this.isGlassPositive) indexes.replaceAll(i -> i - (i / 6));
        } else {
            indexes = determinePatternEndNegative(face, neighborState);
            if (this.isGlassNegative) indexes.replaceAll(i -> i - (i / 6));
        }

        return indexes;
        //return new ArrayList<>(List.of(0,5,10,15));
    }

    private int getSidePatternIndex(Direction face, DoubleVerticalSlabBlockConnectGlassModelX.NeighborState neighborState, boolean isPositive) {
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

    private List<Integer> determinePatternEndPositive(Direction face, DoubleVerticalSlabBlockConnectGlassModelX.NeighborState neighborState) {
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

        if (face == Direction.EAST) {
            if (neighborState.upBlockPositive == FULL || neighborState.upBlockPositive == POSITIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (neighborState.upBlockPositive == POSITIVE2) {
                rightBottom = true;
            } else if (neighborState.upBlockPositive == NEGATIVE2) {
                rightTop = true;
            }
            if (neighborState.southBlockPositive == FULL || neighborState.southBlockPositive == POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.southBlockPositive == POSITIVE2) {
                bottomRight = true;
            } else if (neighborState.southBlockPositive == NEGATIVE2) {
                bottomLeft = true;
            }
            if (neighborState.downBlockPositive == FULL || neighborState.downBlockPositive == POSITIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (neighborState.downBlockPositive == POSITIVE2) {
                leftBottom = true;
            } else if (neighborState.downBlockPositive == NEGATIVE2) {
                leftTop = true;
            }
            if (neighborState.northBlockPositive == FULL || neighborState.northBlockPositive == POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.northBlockPositive == POSITIVE2) {
                topRight = true;
            } else if (neighborState.northBlockPositive == NEGATIVE2) {
                topLeft = true;
            }
            if (topRight && rightTop) {
                if (neighborState.northUpBlockPositive == FULL) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (neighborState.southUpBlockPositive == FULL) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (neighborState.southDownBlockPositive == FULL) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (neighborState.northDownBlockPositive == FULL) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            if (neighborState.upBlockPositive == FULL || neighborState.upBlockPositive == POSITIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (neighborState.upBlockPositive == POSITIVE2) {
                rightTop = true;
            } else if (neighborState.upBlockPositive == NEGATIVE2) {
                rightBottom = true;
            }
            if (neighborState.southBlockPositive == FULL || neighborState.southBlockPositive == POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.southBlockPositive == POSITIVE2) {
                topRight = true;
            } else if (neighborState.southBlockPositive == NEGATIVE2) {
                topLeft = true;
            }
            if (neighborState.downBlockPositive == FULL || neighborState.downBlockPositive == POSITIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (neighborState.downBlockPositive == POSITIVE2) {
                leftTop = true;
            } else if (neighborState.downBlockPositive == NEGATIVE2) {
                leftBottom = true;
            }
            if (neighborState.northBlockPositive == FULL || neighborState.northBlockPositive == POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.northBlockPositive == POSITIVE2) {
                bottomRight = true;
            } else if (neighborState.northBlockPositive == NEGATIVE2) {
                bottomLeft = true;
            }
            if (topRight && rightTop) {
                if (neighborState.southUpBlockPositive == FULL) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (neighborState.northUpBlockPositive == FULL) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (neighborState.northDownBlockPositive == FULL) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (neighborState.southDownBlockPositive == FULL) {
                    cornerTopLeft = true;
                }
            }
        }

        return determineEndPatternIndexes(new DoubleVerticalSlabBlockConnectGlassModelX.ConnectionFlags(
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

    private List<Integer> determinePatternEndNegative(Direction face, DoubleVerticalSlabBlockConnectGlassModelX.NeighborState neighborState) {
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

        if (face == Direction.EAST) {
            if (neighborState.upBlockNegative == FULL || neighborState.upBlockNegative == NEGATIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (neighborState.upBlockNegative == POSITIVE2) {
                rightBottom = true;
            } else if (neighborState.upBlockNegative == NEGATIVE2) {
                rightTop = true;
            }
            if (neighborState.southBlockNegative == FULL || neighborState.southBlockNegative == NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.southBlockNegative == POSITIVE2) {
                bottomRight = true;
            } else if (neighborState.southBlockNegative == NEGATIVE2) {
                bottomLeft = true;
            }
            if (neighborState.downBlockNegative == FULL || neighborState.downBlockNegative == NEGATIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (neighborState.downBlockNegative == POSITIVE2) {
                leftBottom = true;
            } else if (neighborState.downBlockNegative == NEGATIVE2) {
                leftTop = true;
            }
            if (neighborState.northBlockNegative == FULL || neighborState.northBlockNegative == NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.northBlockNegative == POSITIVE2) {
                topRight = true;
            } else if (neighborState.northBlockNegative == NEGATIVE2) {
                topLeft = true;
            }
            if (topRight && rightTop) {
                if (neighborState.northUpBlockNegative == FULL) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (neighborState.southUpBlockNegative == FULL) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (neighborState.southDownBlockNegative == FULL) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (neighborState.northDownBlockNegative == FULL) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            if (neighborState.upBlockNegative == FULL || neighborState.upBlockNegative == NEGATIVE1) {
                rightTop = true;
                rightBottom = true;
            } else if (neighborState.upBlockNegative == POSITIVE2) {
                rightTop = true;
            } else if (neighborState.upBlockNegative == NEGATIVE2) {
                rightBottom = true;
            }
            if (neighborState.southBlockNegative == FULL || neighborState.southBlockNegative == NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.southBlockNegative == POSITIVE2) {
                topRight = true;
            } else if (neighborState.southBlockNegative == NEGATIVE2) {
                topLeft = true;
            }
            if (neighborState.downBlockNegative == FULL || neighborState.downBlockNegative == NEGATIVE1) {
                leftTop = true;
                leftBottom = true;
            } else if (neighborState.downBlockNegative == POSITIVE2) {
                leftTop = true;
            } else if (neighborState.downBlockNegative == NEGATIVE2) {
                leftBottom = true;
            }
            if (neighborState.northBlockNegative == FULL || neighborState.northBlockNegative == NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.northBlockNegative == POSITIVE2) {
                bottomRight = true;
            } else if (neighborState.northBlockNegative == NEGATIVE2) {
                bottomLeft = true;
            }
            if (topRight && rightTop) {
                if (neighborState.southUpBlockNegative == FULL) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && rightBottom) {
                if (neighborState.northUpBlockNegative == FULL) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && leftBottom) {
                if (neighborState.northDownBlockNegative == FULL) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && leftTop) {
                if (neighborState.southDownBlockNegative == FULL) {
                    cornerTopLeft = true;
                }
            }
        }

        return determineEndPatternIndexes(new DoubleVerticalSlabBlockConnectGlassModelX.ConnectionFlags(
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

    private int determinePatternPositive(Direction face, DoubleVerticalSlabBlockConnectGlassModelX.NeighborState neighborState) {
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

        if (face == Direction.UP) {
            if (neighborState.eastBlock == FULL || neighborState.eastBlock == POSITIVE1) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.eastBlock == POSITIVE2) {
                topLeft = true;
            } else if (neighborState.eastBlock == NEGATIVE2) {
                topRight = true;
            }
            left = neighborState.southBlockPositive == FULL || neighborState.southBlockPositive == POSITIVE1 || neighborState.southBlockPositive == POSITIVE2;
            right = neighborState.northBlockPositive == FULL || neighborState.northBlockPositive == POSITIVE1 || neighborState.northBlockPositive == POSITIVE2;
            if (neighborState.isSameSlab) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.eastNorthBlock == FULL || neighborState.eastNorthBlock == POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.northBlockPositive == FULL || neighborState.northBlockPositive == POSITIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.southBlockPositive == FULL || neighborState.southBlockPositive == POSITIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.eastSouthBlock == FULL || neighborState.eastSouthBlock == POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.SOUTH) {
            if (neighborState.eastBlock == FULL || neighborState.eastBlock == POSITIVE2) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.eastBlock == NEGATIVE1) {
                topLeft = true;
            } else if (neighborState.eastBlock == POSITIVE1) {
                topRight = true;
            }
            left = neighborState.downBlockPositive == FULL || neighborState.downBlockPositive == POSITIVE1 || neighborState.downBlockPositive == POSITIVE2;
            right = neighborState.upBlockPositive == FULL || neighborState.upBlockPositive == POSITIVE1 || neighborState.upBlockPositive == POSITIVE2;
            if (neighborState.isSameSlab) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.eastUpBlock == FULL || neighborState.eastUpBlock == POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.upBlockPositive == FULL || neighborState.upBlockPositive == POSITIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.downBlockPositive == FULL || neighborState.downBlockPositive == POSITIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.eastWestBlock == FULL || neighborState.eastWestBlock == POSITIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.DOWN) {
            if (neighborState.eastBlock == FULL || neighborState.eastBlock == NEGATIVE1) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.eastBlock == NEGATIVE2) {
                topLeft = true;
            } else if (neighborState.eastBlock == POSITIVE2) {
                topRight = true;
            }
            left = neighborState.northBlockPositive == FULL || neighborState.northBlockPositive == POSITIVE1 || neighborState.northBlockPositive == NEGATIVE2;
            right = neighborState.southBlockPositive == FULL || neighborState.southBlockPositive == POSITIVE1 || neighborState.southBlockPositive == NEGATIVE2;
            if (neighborState.isSameSlab) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.eastSouthBlock == FULL || neighborState.eastSouthBlock == NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.southBlockPositive == FULL || neighborState.southBlockPositive == NEGATIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.northBlockPositive == FULL || neighborState.northBlockPositive == NEGATIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.eastNorthBlock == FULL || neighborState.eastNorthBlock == NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.NORTH) {
            if (neighborState.eastBlock == FULL || neighborState.eastBlock == NEGATIVE2) {
                topLeft = true;
                topRight = true;
            } else if (neighborState.eastBlock == POSITIVE1) {
                topLeft = true;
            } else if (neighborState.eastBlock == NEGATIVE1) {
                topRight = true;
            }
            left = neighborState.upBlockPositive == FULL || neighborState.upBlockPositive == POSITIVE1 || neighborState.upBlockPositive == NEGATIVE2;
            right = neighborState.downBlockPositive == FULL || neighborState.downBlockPositive == POSITIVE1 || neighborState.downBlockPositive == NEGATIVE2;
            if (neighborState.isSameSlab) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.eastWestBlock == FULL || neighborState.eastWestBlock == NEGATIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.downBlockPositive == FULL || neighborState.downBlockPositive == NEGATIVE2) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.upBlockPositive == FULL || neighborState.upBlockPositive == NEGATIVE2) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.eastUpBlock == FULL || neighborState.eastUpBlock == NEGATIVE1) {
                    cornerTopLeft = true;
                }
            }
        }

        return determineSidePatternIndex(new DoubleVerticalSlabBlockConnectGlassModelX.ConnectionFlags(
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

    private int determinePatternNegative(Direction face, DoubleVerticalSlabBlockConnectGlassModelX.NeighborState neighborState) {
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

        if (face == Direction.UP) {
            if (neighborState.isSameSlab) {
                topLeft = true;
                topRight = true;
            }
            left = neighborState.southBlockNegative == FULL || neighborState.southBlockNegative == NEGATIVE1 || neighborState.southBlockNegative == POSITIVE2;
            right = neighborState.northBlockNegative == FULL || neighborState.northBlockNegative == NEGATIVE1 || neighborState.northBlockNegative == POSITIVE2;
            if (neighborState.westBlock == FULL || neighborState.westBlock == POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.westBlock == POSITIVE2) {
                bottomLeft = true;
            } else if (neighborState.westBlock == NEGATIVE2) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.northBlockNegative == FULL || neighborState.northBlockPositive == POSITIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.westNorthBlock == FULL || neighborState.westNorthBlock == POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.westSouthBlock == FULL || neighborState.westSouthBlock == POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.southBlockNegative == FULL || neighborState.southBlockPositive == POSITIVE2) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.SOUTH) {
            if (neighborState.isSameSlab) {
                topLeft = true;
                topRight = true;
            }
            left = neighborState.downBlockNegative == FULL || neighborState.downBlockNegative == NEGATIVE1 || neighborState.downBlockNegative == POSITIVE2;
            right = neighborState.upBlockNegative == FULL || neighborState.upBlockNegative == NEGATIVE1 || neighborState.upBlockNegative == POSITIVE2;
            if (neighborState.westBlock == FULL || neighborState.westBlock == POSITIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.westBlock == NEGATIVE1) {
                bottomLeft = true;
            } else if (neighborState.westBlock == POSITIVE1) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.upBlockNegative == FULL || neighborState.upBlockPositive == POSITIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.westUpBlock == FULL || neighborState.westUpBlock == POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.westDownBlock == FULL || neighborState.westDownBlock == POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.downBlockNegative == FULL || neighborState.downBlockPositive == POSITIVE2) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.DOWN) {
            if (neighborState.isSameSlab) {
                topLeft = true;
                topRight = true;
            }
            left = neighborState.northBlockNegative == FULL || neighborState.northBlockNegative == NEGATIVE1 || neighborState.northBlockNegative == NEGATIVE2;
            right = neighborState.southBlockNegative == FULL || neighborState.southBlockNegative == NEGATIVE1 || neighborState.southBlockNegative == NEGATIVE2;
            if (neighborState.westBlock == FULL || neighborState.westBlock == NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.westBlock == NEGATIVE2) {
                bottomLeft = true;
            } else if (neighborState.westBlock == POSITIVE2) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.southBlockNegative == FULL || neighborState.southBlockPositive == NEGATIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.westSouthBlock == FULL || neighborState.westSouthBlock == NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.westNorthBlock == FULL || neighborState.westNorthBlock == NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.northBlockNegative == FULL || neighborState.northBlockPositive == NEGATIVE2) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.NORTH) {
            if (neighborState.isSameSlab) {
                topLeft = true;
                topRight = true;
            }
            left = neighborState.upBlockNegative == FULL || neighborState.upBlockNegative == NEGATIVE1 || neighborState.upBlockNegative == NEGATIVE2;
            right = neighborState.downBlockNegative == FULL || neighborState.downBlockNegative == NEGATIVE1 || neighborState.downBlockNegative == NEGATIVE2;
            if (neighborState.westBlock == FULL || neighborState.westBlock == NEGATIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.westBlock == POSITIVE1) {
                bottomLeft = true;
            } else if (neighborState.westBlock == NEGATIVE1) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.downBlockNegative == FULL || neighborState.downBlockPositive == NEGATIVE2) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.westDownBlock == FULL || neighborState.westDownBlock == NEGATIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.westUpBlock == FULL || neighborState.westUpBlock == NEGATIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.upBlockNegative == FULL || neighborState.upBlockPositive == NEGATIVE2) {
                    cornerTopLeft = true;
                }
            }
        }

        return determineSidePatternIndex(new DoubleVerticalSlabBlockConnectGlassModelX.ConnectionFlags(
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

    private List<Integer> determineEndPatternIndexes(DoubleVerticalSlabBlockConnectGlassModelX.ConnectionFlags flags, boolean isGlass) {
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

    private int determineSidePatternIndex(DoubleVerticalSlabBlockConnectGlassModelX.ConnectionFlags flags) {
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

    private DoubleVerticalSlabBlockConnectGlassModelX.NeighborState neighborComparison(BlockRenderView world, BlockPos pos) {
        boolean isSameSlab;
        int eastBlock, westBlock;
        int upBlockPositive, southBlockPositive, downBlockPositive, northBlockPositive;
        int upBlockNegative, southBlockNegative, downBlockNegative, northBlockNegative;
        int eastUpBlock, eastSouthBlock, eastWestBlock, eastNorthBlock;
        int southUpBlockPositive, southDownBlockPositive, northDownBlockPositive, northUpBlockPositive;
        int southUpBlockNegative, southDownBlockNegative, northDownBlockNegative, northUpBlockNegative;
        int westUpBlock, westSouthBlock, westDownBlock, westNorthBlock;

        isSameSlab = positiveSlab == negativeSlab;

        // EAST
        eastBlock = topNeighborComparison(world, pos);

        // WEST
        westBlock = bottomNeighborComparison(world, pos);

        // UP,SOUTH,DOWN,NORTH
        upBlockPositive = checkHorizontalNeighborComparison(world, pos, Direction.UP, positiveSlab);
        southBlockPositive = checkHorizontalNeighborComparison(world, pos, Direction.SOUTH, positiveSlab);
        downBlockPositive = checkHorizontalNeighborComparison(world, pos, Direction.DOWN, positiveSlab);
        northBlockPositive = checkHorizontalNeighborComparison(world, pos, Direction.NORTH, positiveSlab);

        if (isSameSlab) {
            upBlockNegative = upBlockPositive;
            southBlockNegative = southBlockPositive;
            downBlockNegative = downBlockPositive;
            northBlockNegative = northBlockPositive;
        } else {
            upBlockNegative = checkHorizontalNeighborComparison(world, pos, Direction.EAST, negativeSlab);
            southBlockNegative = checkHorizontalNeighborComparison(world, pos, Direction.SOUTH, negativeSlab);
            downBlockNegative = checkHorizontalNeighborComparison(world, pos, Direction.WEST, negativeSlab);
            northBlockNegative = checkHorizontalNeighborComparison(world, pos, Direction.NORTH, negativeSlab);
        }

        // TOP-EAST
        eastUpBlock = checkTopEdgeConnection(world, pos, Direction.EAST, eastBlock, upBlockPositive);

        // TOP-SOUTH
        eastSouthBlock = checkTopEdgeConnection(world, pos, Direction.SOUTH, eastBlock, southBlockPositive);

        // TOP-WEST
        eastWestBlock = checkTopEdgeConnection(world, pos, Direction.WEST, eastBlock, downBlockPositive);

        // TOP-NORTH
        eastNorthBlock = checkTopEdgeConnection(world, pos, Direction.NORTH, eastBlock, northBlockPositive);

        // SOUTH-EAST-TOP
        southUpBlockPositive = checkSideDiagonalConnection(world, pos, Direction.SOUTH, Direction.EAST, southBlockPositive, upBlockPositive, true);

        // SOUTH-EAST-BOTTOM
        southUpBlockNegative = checkSideDiagonalConnection(world, pos, Direction.SOUTH, Direction.EAST, southBlockNegative, upBlockNegative, false);

        // SOUTH-WEST-TOP
        southDownBlockPositive = checkSideDiagonalConnection(world, pos, Direction.SOUTH, Direction.WEST, southBlockPositive, downBlockPositive, true);

        // SOUTH-WEST-BOTTOM
        southDownBlockNegative = checkSideDiagonalConnection(world, pos, Direction.SOUTH, Direction.WEST, southBlockNegative, downBlockNegative, false);

        // NORTH-EAST-TOP
        northUpBlockPositive = checkSideDiagonalConnection(world, pos, Direction.NORTH, Direction.EAST, northBlockPositive, upBlockPositive, true);

        // NORTH-EAST-BOTTOM
        northUpBlockNegative = checkSideDiagonalConnection(world, pos, Direction.NORTH, Direction.EAST, northBlockNegative, upBlockNegative, false);

        // NORTH-WEST-TOP
        northDownBlockPositive = checkSideDiagonalConnection(world, pos, Direction.NORTH, Direction.WEST, northBlockPositive, downBlockPositive, true);

        // NORTH-WEST-BOTTOM
        northDownBlockNegative = checkSideDiagonalConnection(world, pos, Direction.NORTH, Direction.WEST, northBlockNegative, downBlockNegative, false);

        // BOTTOM-EAST
        westUpBlock = checkBottomEdgeConnection(world, pos, Direction.EAST, westBlock, upBlockNegative);

        // BOTTOM-SOUTH
        westSouthBlock = checkBottomEdgeConnection(world, pos, Direction.SOUTH, westBlock, southBlockNegative);

        // BOTTOM-WEST
        westDownBlock = checkBottomEdgeConnection(world, pos, Direction.WEST, westBlock, downBlockNegative);

        // BOTTOM-NORTH
        westNorthBlock = checkBottomEdgeConnection(world, pos, Direction.NORTH, westBlock, northBlockNegative);

        return new DoubleVerticalSlabBlockConnectGlassModelX.NeighborState(
                isSameSlab,
                eastBlock, westBlock,
                upBlockPositive, southBlockPositive, downBlockPositive, northBlockPositive,
                upBlockNegative, southBlockNegative, downBlockNegative, northBlockNegative,
                eastUpBlock, eastSouthBlock, eastWestBlock, eastNorthBlock,
                southUpBlockPositive, southDownBlockPositive, northDownBlockPositive, northUpBlockPositive,
                southUpBlockNegative, southDownBlockNegative, northDownBlockNegative, northUpBlockNegative,
                westUpBlock, westSouthBlock, westDownBlock, westNorthBlock
        );
    }

    private int topNeighborComparison(BlockRenderView world, BlockPos pos) {
        BlockPos otherPos = pos.east();
        BlockState otherState = world.getBlockState(otherPos);
        Block otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (ModBlockMap.verticalSlabToSlab(positiveSlab) == otherBlock) {
                SlabType type = otherState.get(SlabBlock.TYPE);
                if (type == SlabType.TOP) {
                    return POSITIVE1;
                } else if (type == SlabType.BOTTOM) {
                    return NEGATIVE1;
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            Block b = ModBlockMap.verticalSlabToSlab(positiveSlab);
            boolean bl1 = b == entity.getPositiveSlabState().getBlock();
            boolean bl2 = b == entity.getNegativeSlabState().getBlock();

            if (bl1 && bl2) {
                return FULL;
            } else if (bl1) {
                return POSITIVE1;
            } else if (bl2) {
                return NEGATIVE1;
            }
        } else if (otherBlock instanceof VerticalSlabBlock) {
            if (otherBlock == positiveSlab) {
                switch (otherState.get(VerticalSlabBlock.FACING)) {
                    case WEST -> {
                        return FULL;
                    }
                    case SOUTH -> {
                        return POSITIVE2;
                    }
                    case NORTH -> {
                        return NEGATIVE2;
                    }
                    default -> {
                        return OTHER;
                    }
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            boolean bl1 = positiveSlab == entity.getPositiveSlabState().getBlock();
            boolean bl2 = positiveSlab == entity.getNegativeSlabState().getBlock();

            if (entity.isX()) {
                if (bl2) return FULL;
            } else {
                if (bl1 && bl2) {
                    return FULL;
                } else if (bl1) {
                    return POSITIVE2;
                } else if (bl2) {
                    return NEGATIVE2;
                }
            }
        } else {
            if (ModBlockMap.verticalSlabToOriginal(positiveSlab) == otherBlock) {
                return FULL;
            }
        }

        return OTHER;
    }

    private int bottomNeighborComparison(BlockRenderView world, BlockPos pos) {
        BlockPos otherPos = pos.west();
        BlockState otherState = world.getBlockState(otherPos);
        Block otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (ModBlockMap.verticalSlabToSlab(negativeSlab) == otherBlock) {
                SlabType type = otherState.get(SlabBlock.TYPE);
                if (type == SlabType.TOP) {
                    return POSITIVE1;
                } else if (type == SlabType.BOTTOM) {
                    return NEGATIVE1;
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            Block b = ModBlockMap.verticalSlabToSlab(positiveSlab);
            boolean bl1 = b == entity.getPositiveSlabState().getBlock();
            boolean bl2 = b == entity.getNegativeSlabState().getBlock();

            if (bl1 && bl2) {
                return FULL;
            } else if (bl1) {
                return POSITIVE1;
            } else if (bl2) {
                return NEGATIVE1;
            }
        } else if (otherBlock instanceof VerticalSlabBlock) {
            if (otherBlock == negativeSlab) {
                switch (otherState.get(VerticalSlabBlock.FACING)) {
                    case EAST -> {
                        return FULL;
                    }
                    case SOUTH -> {
                        return POSITIVE2;
                    }
                    case NORTH -> {
                        return NEGATIVE2;
                    }
                    default -> {
                        return OTHER;
                    }
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            boolean bl1 = positiveSlab == entity.getPositiveSlabState().getBlock();
            boolean bl2 = positiveSlab == entity.getNegativeSlabState().getBlock();

            if (entity.isX()) {
                if (bl1) return FULL;
            } else {
                if (bl1 && bl2) {
                    return FULL;
                } else if (bl1) {
                    return POSITIVE2;
                } else if (bl2) {
                    return NEGATIVE2;
                }
            }
        } else {
            if (ModBlockMap.verticalSlabToOriginal(negativeSlab) == otherBlock) {
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
                if (d == dir.rotateYClockwise()) {
                    return POSITIVE2;
                } else if (d == dir.getOpposite()) {
                    return FULL;
                } else if (d == dir.rotateYCounterclockwise()) {
                    return NEGATIVE2;
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            if ((dir.getAxis() == Direction.Axis.X) == entity.isX()) {
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

    private int checkTopEdgeConnection(BlockRenderView world, BlockPos pos, Direction dir, int eastBlock, int sideBlockTop) {
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

        if (eastBlock != OTHER && eastBlock != targetU && sideBlockTop != OTHER && sideBlockTop != NEGATIVE1) {
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

    private int checkBottomEdgeConnection(BlockRenderView world, BlockPos pos, Direction dir, int westBlock, int sideBlockBottom) {
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

        if (westBlock != OTHER && westBlock != targetD && sideBlockBottom != OTHER && sideBlockBottom != POSITIVE1) {
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
        if ((dir.getAxis() == Direction.Axis.X) == isX) {
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

    private boolean shouldCullPositive(Direction face, DoubleVerticalSlabBlockConnectGlassModelX.NeighborState neighborState) {
        if (face == Direction.EAST) {
            return neighborState.eastBlock == FULL;
        } else if (face == Direction.WEST) {
            return neighborState.isSameSlab;
        } else if (face == Direction.UP) {
            return neighborState.upBlockPositive == FULL || neighborState.upBlockPositive == POSITIVE1;
        } else if (face == Direction.SOUTH) {
            return neighborState.southBlockPositive == FULL || neighborState.southBlockPositive == POSITIVE1;
        } else if (face == Direction.DOWN) {
            return neighborState.downBlockPositive == FULL || neighborState.downBlockPositive == POSITIVE1;
        } else {
            return neighborState.northBlockPositive == FULL || neighborState.northBlockPositive == POSITIVE1;
        }
    }

    private boolean shouldCullNegative(Direction face, DoubleVerticalSlabBlockConnectGlassModelX.NeighborState neighborState) {
        if (face == Direction.EAST) {
            return neighborState.isSameSlab;
        } else if (face == Direction.WEST) {
            return neighborState.westBlock == FULL;
        } else if (face == Direction.UP) {
            return neighborState.upBlockNegative == FULL || neighborState.upBlockNegative == NEGATIVE1;
        } else if (face == Direction.SOUTH) {
            return neighborState.southBlockNegative == FULL || neighborState.southBlockNegative == NEGATIVE1;
        } else if (face == Direction.DOWN) {
            return neighborState.downBlockNegative == FULL || neighborState.downBlockNegative == NEGATIVE1;
        } else {
            return neighborState.northBlockNegative == FULL || neighborState.northBlockNegative == NEGATIVE1;
        }
    }

    private boolean shouldCullPositive(Direction face, BlockRenderView world, BlockPos pos) {
        BlockPos otherPos;
        BlockState otherState;
        Block otherBlock;

        if ((isX && face == Direction.WEST) || (!isX && face == Direction.NORTH)) {
            return positiveSlab == negativeSlab;
        } else if (face == Direction.EAST) {
            otherPos = pos.east();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();

            if (otherBlock instanceof VerticalSlabBlock && positiveSlab == otherBlock) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                return d == Direction.WEST || (!isX && d == Direction.SOUTH);
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block bp = entity.getPositiveSlabState().getBlock();
                Block bn = entity.getNegativeSlabState().getBlock();

                if (otherState.get(DoubleVerticalSlabBlock.AXIS) == VerticalSlabAxis.X) {
                    return positiveSlab == bn;
                } else {
                    if (isX) {
                        return positiveSlab == bp && positiveSlab == bn;
                    } else {
                        return positiveSlab == bp;
                    }
                }
            }
        } else if (face == Direction.SOUTH) {
            otherPos = pos.south();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();

            if (otherBlock instanceof VerticalSlabBlock && positiveSlab == otherBlock) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                return d == Direction.NORTH || (isX && d == Direction.EAST);
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block bp = entity.getPositiveSlabState().getBlock();
                Block bn = entity.getNegativeSlabState().getBlock();

                if (otherState.get(DoubleVerticalSlabBlock.AXIS) == VerticalSlabAxis.Z) {
                    return positiveSlab == bn;
                } else {
                    if (isX) {
                        return positiveSlab == bp;
                    } else {
                        return positiveSlab == bp && positiveSlab == bn;
                    }
                }
            }
        } else if (face == Direction.WEST) {
            otherPos = pos.west();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();

            if (otherBlock instanceof VerticalSlabBlock && positiveSlab == otherBlock) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                return d == Direction.EAST || d == Direction.SOUTH;
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                return positiveSlab == entity.getPositiveSlabState().getBlock();
            }
        } else if (face == Direction.NORTH) {
            otherPos = pos.north();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();

            if (otherBlock instanceof VerticalSlabBlock && positiveSlab == otherBlock) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                return d == Direction.EAST || d == Direction.SOUTH;
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                return positiveSlab == entity.getPositiveSlabState().getBlock();
            }
        } else {
            if (face == Direction.UP) {
                otherPos = pos.up();
            } else {
                otherPos = pos.down();
            }
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();

            if (otherBlock instanceof VerticalSlabBlock && positiveSlab == otherBlock) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                return (isX && d == Direction.EAST) || (!isX && d == Direction.SOUTH);
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                return positiveSlab == entity.getPositiveSlabState().getBlock();
            } else if (otherBlock instanceof SlabBlock && positiveSlab == ModBlockMap.slabToVerticalSlab(otherBlock)) {
                if (face == Direction.UP) {
                    return otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM;
                } else {
                    return otherState.get(SlabBlock.TYPE) == SlabType.TOP;
                }
            }
        }

        if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            Block b = ModBlockMap.verticalSlabToSlab(positiveSlab);
            return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
        }

        return ModBlockMap.verticalSlabToOriginal(positiveSlab) == otherBlock;
    }

    private boolean shouldCullNegative(Direction face, BlockRenderView world, BlockPos pos) {
        BlockPos otherPos;
        BlockState otherState;
        Block otherBlock;

        if ((isX && face == Direction.EAST) || (!isX && face == Direction.SOUTH)) {
            return positiveSlab == negativeSlab;
        } else if (face == Direction.EAST) {
            otherPos = pos.east();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();

            if (otherBlock instanceof VerticalSlabBlock && negativeSlab == otherBlock) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                return d == Direction.WEST || d == Direction.NORTH;
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                return negativeSlab == entity.getNegativeSlabState().getBlock();
            }
        } else if (face == Direction.SOUTH) {
            otherPos = pos.south();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();

            if (otherBlock instanceof VerticalSlabBlock && negativeSlab == otherBlock) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                return d == Direction.NORTH || d == Direction.WEST;
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                return negativeSlab == entity.getNegativeSlabState().getBlock();
            }
        } else if (face == Direction.WEST) {
            otherPos = pos.west();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();

            if (otherBlock instanceof VerticalSlabBlock && negativeSlab == otherBlock) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                return d == Direction.EAST || (!isX && d == Direction.NORTH);
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block bp = entity.getPositiveSlabState().getBlock();
                Block bn = entity.getNegativeSlabState().getBlock();

                if (otherState.get(DoubleVerticalSlabBlock.AXIS) == VerticalSlabAxis.X) {
                    return negativeSlab == bp;
                } else {
                    if (isX) {
                        return negativeSlab == bp && negativeSlab == bn;
                    } else {
                        return negativeSlab == bn;
                    }
                }
            }
        } else if (face == Direction.NORTH) {
            otherPos = pos.north();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();

            if (otherBlock instanceof VerticalSlabBlock && negativeSlab == otherBlock) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                return d == Direction.SOUTH || (isX && d == Direction.WEST);
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block bp = entity.getPositiveSlabState().getBlock();
                Block bn = entity.getNegativeSlabState().getBlock();

                if (otherState.get(DoubleVerticalSlabBlock.AXIS) == VerticalSlabAxis.Z) {
                    return negativeSlab == bp;
                } else {
                    if (isX) {
                        return negativeSlab == bn;
                    } else {
                        return negativeSlab == bp && negativeSlab == bn;
                    }
                }
            }
        } else {
            if (face == Direction.UP) {
                otherPos = pos.up();
            } else {
                otherPos = pos.down();
            }
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();

            if (otherBlock instanceof VerticalSlabBlock && negativeSlab == otherBlock) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                return (isX && d == Direction.WEST) || (!isX && d == Direction.NORTH);
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                return negativeSlab == entity.getNegativeSlabState().getBlock();
            } else if (otherBlock instanceof SlabBlock && negativeSlab == ModBlockMap.slabToVerticalSlab(otherBlock)) {
                if (face == Direction.UP) {
                    return otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM;
                } else {
                    return otherState.get(SlabBlock.TYPE) == SlabType.TOP;
                }
            }
        }

        if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            Block b = ModBlockMap.verticalSlabToSlab(negativeSlab);
            return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
        }

        return ModBlockMap.verticalSlabToOriginal(negativeSlab) == otherBlock;
    }

    private boolean isEnd(Direction dir) {
        if (this.isX) {
            return dir.getAxis() == Direction.Axis.X;
        } else {
            return dir == Direction.SOUTH || dir == Direction.NORTH;
        }
    }
}
