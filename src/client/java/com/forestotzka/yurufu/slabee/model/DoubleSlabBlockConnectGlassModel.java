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

    public static final Map<Integer, Integer> GLASS_SLAB_INDEX_MAP = Map.<Integer, Integer>ofEntries(
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
    private static final int GLASS_SLAB_PATTERN_COUNT = 169;

    private static final SpriteIdentifier[] GLASS_SPRITE_IDS = new SpriteIdentifier[GLASS_SLAB_PATTERN_COUNT];
    private static final SpriteIdentifier[] GLASS_SLAB_SPRITE_IDS = new SpriteIdentifier[GLASS_SLAB_PATTERN_COUNT];
    static {
        for (int i = 0; i < GLASS_PATTERN_COUNT-1; i++) {
            GLASS_SPRITE_IDS[i] = new SpriteIdentifier(
                    PlayerScreenHandler.BLOCK_ATLAS_TEXTURE,
                    Identifier.of(Slabee.MOD_ID, "block/glass/" + i)
            );
        }
        GLASS_SPRITE_IDS[GLASS_PATTERN_COUNT-1] = new SpriteIdentifier(
                PlayerScreenHandler.BLOCK_ATLAS_TEXTURE,
                Identifier.of(Slabee.MOD_ID, "block/glass/center")
        );

        for (int i = 0; i < GLASS_SLAB_PATTERN_COUNT; i++) {
            GLASS_SLAB_SPRITE_IDS[i] = new SpriteIdentifier(
                    PlayerScreenHandler.BLOCK_ATLAS_TEXTURE,
                    Identifier.of(Slabee.MOD_ID, "block/glass_slab/" + i)
            );
        }
    }

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

        for (int patternIndex = 0; patternIndex < GLASS_SLAB_PATTERN_COUNT; patternIndex++) {
            EnumMap<Direction, Mesh> positiveFaceMeshes = new EnumMap<>(Direction.class);
            EnumMap<Direction, Mesh> negativeFaceMeshes = new EnumMap<>(Direction.class);

            for (Direction dir : Direction.values()) {
                {
                    MeshBuilder meshBuilder = renderer.meshBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    emitter.square(dir, 0, 0.5f, 1, 1, 0);
                    emitter.spriteBake(textureGetter.apply(GLASS_SLAB_SPRITE_IDS[patternIndex]), MutableQuadView.BAKE_LOCK_UV);
                    emitter.color(-1, -1, -1, -1);
                    emitter.emit();

                    positiveFaceMeshes.put(dir, meshBuilder.build());
                }
                {
                    MeshBuilder meshBuilder = renderer.meshBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    emitter.square(dir, 0, 0, 1, 0.5f, 0);
                    emitter.spriteBake(textureGetter.apply(GLASS_SLAB_SPRITE_IDS[patternIndex]), MutableQuadView.BAKE_LOCK_UV);
                    emitter.color(-1, -1, -1, -1);
                    emitter.emit();

                    negativeFaceMeshes.put(dir, meshBuilder.build());
                }
            }

            positiveMeshMap.put(patternIndex, positiveFaceMeshes);
            negativeMeshMap.put(patternIndex, negativeFaceMeshes);
        }

        for (int patternIndex = 0; patternIndex < GLASS_PATTERN_COUNT; patternIndex++) {
            EnumMap<Direction, Mesh> endPositiveFaceMeshes = new EnumMap<>(Direction.class);
            EnumMap<Direction, Mesh> endNegativeFaceMeshes = new EnumMap<>(Direction.class);

            for (Direction dir : Direction.values()) {
                {
                    MeshBuilder meshBuilder = renderer.meshBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    if (dir == Direction.UP) {
                        emitter.square(dir, 0, 0, 1, 1, 0f);
                        emitter.spriteBake(textureGetter.apply(GLASS_SPRITE_IDS[patternIndex]), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endPositiveFaceMeshes.put(dir, meshBuilder.build());
                    } else if (dir == Direction.DOWN) {
                        emitter.square(dir, 0, 0, 1, 1, 0.5f);
                        emitter.spriteBake(textureGetter.apply(GLASS_SPRITE_IDS[patternIndex]), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endPositiveFaceMeshes.put(dir, meshBuilder.build());
                    }
                }
                {
                    MeshBuilder meshBuilder = renderer.meshBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    if (dir == Direction.UP) {
                        emitter.square(dir, 0, 0, 1, 1, 0.5f);
                        emitter.spriteBake(textureGetter.apply(GLASS_SPRITE_IDS[patternIndex]), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endNegativeFaceMeshes.put(dir, meshBuilder.build());
                    } else if (dir == Direction.DOWN) {
                        emitter.square(dir, 0, 0, 1, 1, 0f);
                        emitter.spriteBake(textureGetter.apply(GLASS_SPRITE_IDS[patternIndex]), MutableQuadView.BAKE_LOCK_UV);
                        emitter.color(-1, -1, -1, -1);
                        emitter.emit();

                        endNegativeFaceMeshes.put(dir, meshBuilder.build());
                    }
                }
            }

            endPositiveMeshMap.put(patternIndex, endPositiveFaceMeshes);
            endNegativeMeshMap.put(patternIndex, endNegativeFaceMeshes);
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

    private final int OTHER = 0;
    private final int FULL = 1;
    /*private final int SLAB_TOP = 2;
    private final int SLAB_BOTTOM = 3;
    private final int V_SLAB_EAST = 4;
    private final int V_SLAB_SOUTH = 5;
    private final int V_SLAB_WEST = 6;
    private final int V_SLAB_NORTH = 7;*/
    private final int POSITIVE1 = 3;
    private final int NEGATIVE1 = 4;
    private final int POSITIVE2 = 5;
    private final int NEGATIVE2 = 6;

    private boolean isSameSlab;
    private int upBlock = OTHER;
    private int downBlock = OTHER;
    private int eastBlockTop = OTHER;
    private int southBlockTop = OTHER;
    private int westBlockTop = OTHER;
    private int northBlockTop = OTHER;
    private int eastBlockBottom = OTHER;
    private int southBlockBottom = OTHER;
    private int westBlockBottom = OTHER;
    private int northBlockBottom = OTHER;
    private int topEastBlock = OTHER;
    private int topSouthBlock = OTHER;
    private int topWestBlock = OTHER;
    private int topNorthBlock = OTHER;
    private int southEastBlock = OTHER;
    private int southWestBlock = OTHER;
    private int northWestBlock = OTHER;
    private int northEastBlock = OTHER;
    private int bottomEastBlock = OTHER;
    private int bottomSouthBlock = OTHER;
    private int bottomWestBlock = OTHER;
    private int bottomNorthBlock = OTHER;

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {
        neighborComparison(blockRenderView, blockPos);

        if (this.positiveId != null) {
            for (Direction face : Direction.values()) {
                if (this.positiveSlab == null || shouldCullPositive(face)) {
                    continue;
                }

                EnumMap<Direction, Mesh> faceMeshes;
                if (face == Direction.UP || face == Direction.DOWN) {
                    if (face == Direction.UP) System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + blockPos);
                    for (int index : getEndPatternIndexes(face)) {
                        if (face == Direction.UP) System.out.println(index);
                        faceMeshes = endPositiveMeshMap.get(index);
                        if (faceMeshes == null) return;

                        Mesh mesh = faceMeshes.get(face);
                        if (mesh != null) {
                            mesh.outputTo(renderContext.getEmitter());
                        }
                    }
                    faceMeshes = endPositiveMeshMap.get(GLASS_PATTERN_COUNT-1);
                    if (faceMeshes == null) return;

                    Mesh mesh = faceMeshes.get(face);
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
                    }
                } else {
                    faceMeshes = positiveMeshMap.get(getSidePatternIndex(face, true));
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
                if (this.negativeSlab == null || shouldCullNegative(face)) {
                    continue;
                }

                EnumMap<Direction, Mesh> faceMeshes;
                if (face == Direction.UP || face == Direction.DOWN) {
                    for (int index : getEndPatternIndexes(face)) {
                        faceMeshes = endNegativeMeshMap.get(index);
                        if (faceMeshes == null) return;

                        Mesh mesh = faceMeshes.get(face);
                        if (mesh != null) {
                            mesh.outputTo(renderContext.getEmitter());
                        }
                    }
                    faceMeshes = endNegativeMeshMap.get(GLASS_PATTERN_COUNT-1);
                    if (faceMeshes == null) return;

                    Mesh mesh = faceMeshes.get(face);
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
                    }
                } else {
                    faceMeshes = negativeMeshMap.get(getSidePatternIndex(face, false));
                    if (faceMeshes == null) return;

                    Mesh mesh = faceMeshes.get(face);
                    if (mesh != null) {
                        mesh.outputTo(renderContext.getEmitter());
                    }
                }
            }
        }
    }

    private List<Integer> getEndPatternIndexes(Direction face) {
        List<Integer> indexes = determinePatternEnd(face);

        indexes.replaceAll(i -> i - (i / 6));

        return indexes;
        //return new ArrayList<>(List.of(0,5,10,15));
    }

    private int getSidePatternIndex(Direction face, boolean isPositive) {
        int index;
        if (isPositive) {
            index = determinePatternPositive(face);
        } else {
            index = determinePatternNegative(face);
        }

        if (index >= 64) {
            index = GLASS_SLAB_INDEX_MAP.getOrDefault(index, 0);
        }

        return index;
    }

    private boolean connectUL;
    private boolean connectUR;
    private boolean connectR;
    private boolean connectR2;
    private boolean connectDL;
    private boolean connectDR;
    private boolean connectL;
    private boolean connectL2;
    private boolean connectRU;
    private boolean connectRD;
    private boolean connectLD;
    private boolean connectLU;
    private List<Integer> determinePatternEnd(Direction face) {
        connectUL = false;
        connectUR = false;
        connectR = false;
        connectR2 = false;
        connectDL = false;
        connectDR = false;
        connectL = false;
        connectL2 = false;
        connectRU = false;
        connectRD = false;
        connectLD = false;
        connectLU = false;

        if (face == Direction.UP) {
            if (eastBlockTop == FULL || eastBlockTop == POSITIVE1) {
                connectR = true;
                connectR2 = true;
            } else if (eastBlockTop == POSITIVE2) {
                connectR2 = true;
            } else if (eastBlockTop == NEGATIVE2) {
                connectR = true;
            }
            if (southBlockTop == FULL || southBlockTop == POSITIVE1) {
                connectDL = true;
                connectDR = true;
            } else if (southBlockTop == POSITIVE2) {
                connectDL = true;
            } else if (southBlockTop == NEGATIVE2) {
                connectDR = true;
            }
            if (westBlockTop == FULL || westBlockTop == POSITIVE1) {
                connectL = true;
                connectL2 = true;
            } else if (westBlockTop == POSITIVE2) {
                connectL = true;
            } else if (westBlockTop == NEGATIVE2) {
                connectL2 = true;
            }
            if (northBlockTop == FULL || northBlockTop == POSITIVE1) {
                connectUL = true;
                connectUR = true;
            } else if (northBlockTop == POSITIVE2) {
                connectUR = true;
            } else if (northBlockTop == NEGATIVE2) {
                connectUL = true;
            }
        } else if (face == Direction.DOWN) {
            if (eastBlockBottom == FULL || eastBlockBottom == NEGATIVE1) {
                connectR = true;
                connectR2 = true;
            } else if (eastBlockBottom == POSITIVE2) {
                connectR2 = true;
            } else if (eastBlockBottom == NEGATIVE2) {
                connectR = true;
            }
            if (southBlockBottom == FULL || southBlockBottom == NEGATIVE1) {
                connectDL = true;
                connectDR = true;
            } else if (southBlockBottom == POSITIVE2) {
                connectDL = true;
            } else if (southBlockBottom == NEGATIVE2) {
                connectDR = true;
            }
            if (westBlockBottom == FULL || westBlockBottom == NEGATIVE1) {
                connectL = true;
                connectL2 = true;
            } else if (westBlockBottom == POSITIVE2) {
                connectL = true;
            } else if (westBlockBottom == NEGATIVE2) {
                connectL2 = true;
            }
            if (northBlockBottom == FULL || northBlockBottom == NEGATIVE1) {
                connectUL = true;
                connectUR = true;
            } else if (northBlockBottom == POSITIVE2) {
                connectUR = true;
            } else if (northBlockBottom == NEGATIVE2) {
                connectUL = true;
            }
        }

        return determineEndPatternIndexes();
    }

    private int determinePatternPositive(Direction face) {
        connectUL = false;
        connectUR = false;
        connectR = false;
        connectDL = false;
        connectDR = false;
        connectL = false;
        connectRU = false;
        connectRD = false;
        connectLD = false;
        connectLU = false;

        if (face == Direction.EAST) {
            if (upBlock == FULL || upBlock == POSITIVE1) {
                connectUL = true;
                connectUR = true;
            } else if (upBlock == POSITIVE2) {
                connectUL = true;
            } else if (upBlock == NEGATIVE2) {
                connectUR = true;
            }
            connectL = southBlockTop == FULL || southBlockTop == POSITIVE1;
            connectR = northBlockTop == FULL || northBlockTop == POSITIVE1;
            if (isSameSlab) {
                connectDL = true;
                connectDR = true;
            }
            if (connectUR && connectR) {
                if (topNorthBlock == FULL || topNorthBlock == POSITIVE1) {
                    connectRU = true;
                }
            }
            if (connectDR && connectR) {
                if (northBlockTop == FULL) {
                    connectRD = true;
                }
            }
            if (connectDL && connectL) {
                if (southBlockTop == FULL) {
                    connectLD = true;
                }
            }
            if (connectUL && connectL) {
                if (topSouthBlock == FULL || topSouthBlock == POSITIVE1) {
                    connectLU = true;
                }
            }
        } else if (face == Direction.SOUTH) {
            if (upBlock == FULL || upBlock == POSITIVE2) {
                connectUL = true;
                connectUR = true;
            } else if (upBlock == NEGATIVE1) {
                connectUL = true;
            } else if (upBlock == POSITIVE1) {
                connectUR = true;
            }
            connectL = westBlockTop == FULL || westBlockTop == POSITIVE1;
            connectR = eastBlockTop == FULL || eastBlockTop == POSITIVE1;
            if (isSameSlab) {
                connectDL = true;
                connectDR = true;
            }
        } else if (face == Direction.WEST) {
            if (upBlock == FULL || upBlock == NEGATIVE1) {
                connectUL = true;
                connectUR = true;
            } else if (upBlock == NEGATIVE2) {
                connectUL = true;
            } else if (upBlock == POSITIVE2) {
                connectUR = true;
            }
            connectL = northBlockTop == FULL || northBlockTop == POSITIVE1;
            connectR = southBlockTop == FULL || southBlockTop == POSITIVE1;
            if (isSameSlab) {
                connectDL = true;
                connectDR = true;
            }
        } else if (face == Direction.NORTH) {
            if (upBlock == FULL || upBlock == NEGATIVE2) {
                connectUL = true;
                connectUR = true;
            } else if (upBlock == POSITIVE1) {
                connectUL = true;
            } else if (upBlock == NEGATIVE1) {
                connectUR = true;
            }
            connectL = eastBlockTop == FULL || eastBlockTop == POSITIVE1;
            connectR = westBlockTop == FULL || westBlockTop == POSITIVE1;
            if (isSameSlab) {
                connectDL = true;
                connectDR = true;
            }
        }

        return determineSidePatternIndex();
    }

    private int determinePatternNegative(Direction face) {
        connectUL = false;
        connectUR = false;
        connectR = false;
        connectDL = false;
        connectDR = false;
        connectL = false;
        connectRU = false;
        connectRD = false;
        connectLD = false;
        connectLU = false;

        if (face == Direction.EAST) {
            if (isSameSlab) {
                connectUL = true;
                connectUR = true;
            }
            connectL = southBlockBottom == FULL || southBlockBottom == NEGATIVE1;
            connectR = northBlockBottom == FULL || northBlockBottom == NEGATIVE1;
            if (downBlock == FULL || downBlock == POSITIVE1) {
                connectDL = true;
                connectDR = true;
            } else if (downBlock == POSITIVE2) {
                connectDL = true;
            } else if (downBlock == NEGATIVE2) {
                connectDR = true;
            }
            if (connectUR && connectR) {
                if (northBlockBottom == FULL) {
                    connectRU = true;
                }
            }
            if (connectDR && connectR) {
                if (bottomNorthBlock == FULL || bottomNorthBlock == POSITIVE1) {
                    connectRD = true;
                }
            }
            if (connectDL && connectL) {
                if (bottomSouthBlock == FULL || bottomSouthBlock == POSITIVE1) {
                    connectLD = true;
                }
            }
            if (connectUL && connectL) {
                if (southBlockBottom == FULL) {
                    connectLU = true;
                }
            }
        } else if (face == Direction.SOUTH) {
            if (isSameSlab) {
                connectUL = true;
                connectUR = true;
            }
            connectL = westBlockBottom == FULL || westBlockBottom == NEGATIVE1;
            connectR = eastBlockBottom == FULL || eastBlockBottom == NEGATIVE1;
            if (downBlock == FULL || downBlock == POSITIVE2) {
                connectDL = true;
                connectDR = true;
            } else if (downBlock == NEGATIVE1) {
                connectDL = true;
            } else if (downBlock == POSITIVE1) {
                connectDR = true;
            }
        } else if (face == Direction.WEST) {
            if (isSameSlab) {
                connectUL = true;
                connectUR = true;
            }
            connectL = northBlockBottom == FULL || northBlockBottom == NEGATIVE1;
            connectR = southBlockBottom == FULL || southBlockBottom == NEGATIVE1;
            if (downBlock == FULL || downBlock == NEGATIVE1) {
                connectDL = true;
                connectDR = true;
            } else if (downBlock == NEGATIVE2) {
                connectDL = true;
            } else if (downBlock == POSITIVE2) {
                connectDR = true;
            }
        } else if (face == Direction.NORTH) {
            if (isSameSlab) {
                connectUL = true;
                connectUR = true;
            }
            connectL = eastBlockBottom == FULL || eastBlockBottom == NEGATIVE1;
            connectR = westBlockBottom == FULL || westBlockBottom == NEGATIVE1;
            if (downBlock == FULL || downBlock == NEGATIVE2) {
                connectDL = true;
                connectDR = true;
            } else if (downBlock == POSITIVE1) {
                connectDL = true;
            } else if (downBlock == NEGATIVE1) {
                connectDR = true;
            }
        }

        return determineSidePatternIndex();
    }

    private List<Integer> determineEndPatternIndexes() {
        List<Integer> indexes = new ArrayList<>();
        int i = 0;
        if (connectUL) i += 1;
        if (connectUR) i += 2;
        if (connectRU) i += 2;
        indexes.add(i);

        i = 6;
        if (connectR) i += 1;
        if (connectR2) i += 2;
        if (connectRD) i += 2;
        indexes.add(i);

        i = 12;
        if (connectDR) i += 1;
        if (connectDL) i += 2;
        if (connectLD) i += 2;
        indexes.add(i);

        i = 18;
        if (connectL2) i += 1;
        if (connectL) i += 2;
        if (connectLU) i += 2;
        indexes.add(i);

        return indexes;
    }

    private int determineSidePatternIndex() {
        int patternIndex = 0;
        if (connectUL) patternIndex |= 1;
        if (connectUR) patternIndex |= 1 << 1;
        if (connectR) patternIndex |= 1 << 2;
        if (connectDL) patternIndex |= 1 << 3;
        if (connectDR) patternIndex |= 1 << 4;
        if (connectL) patternIndex |= 1 << 5;
        if (connectRU) patternIndex |= 1 << 6;
        if (connectRD) patternIndex |= 1 << 7;
        if (connectLD) patternIndex |= 1 << 8;
        if (connectLU) patternIndex |= 1 << 9;

        return patternIndex;
    }

    private void neighborComparison(BlockRenderView world, BlockPos pos) {
        this.upBlock = OTHER;
        this.downBlock = OTHER;
        this.eastBlockTop = OTHER;
        this.southBlockTop = OTHER;
        this.westBlockTop = OTHER;
        this.northBlockTop = OTHER;
        this.eastBlockBottom = OTHER;
        this.southBlockBottom = OTHER;
        this.westBlockBottom = OTHER;
        this.northBlockBottom = OTHER;
        this.topEastBlock = OTHER;
        this.topSouthBlock = OTHER;
        this.topWestBlock = OTHER;
        this.topNorthBlock = OTHER;
        this.southEastBlock = OTHER;
        this.southWestBlock = OTHER;
        this.northWestBlock = OTHER;
        this.northEastBlock = OTHER;
        this.bottomEastBlock = OTHER;
        this.bottomSouthBlock = OTHER;
        this.bottomWestBlock = OTHER;
        this.bottomNorthBlock = OTHER;

        BlockPos otherPos;
        BlockState otherState;
        Block otherBlock;

        this.isSameSlab = positiveSlab == negativeSlab;

        // UP
        otherPos = pos.up();
        otherState = world.getBlockState(otherPos);
        otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                this.upBlock = FULL;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            if (positiveSlab == entity.getNegativeSlabState().getBlock()) {
                this.upBlock = FULL;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
            boolean bl1 = b == entity.getPositiveSlabState().getBlock();
            boolean bl2 = b == entity.getNegativeSlabState().getBlock();

            if (bl1 && bl2) {
                this.upBlock = FULL;
            } else if (bl1) {
                this.upBlock = entity.isX() ? POSITIVE1 : POSITIVE2;
            } else if (bl2) {
                this.upBlock = entity.isX() ? NEGATIVE1 : NEGATIVE2;
            }
        } else {
            if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                this.upBlock = FULL;
            }
        }

        // DOWN
        otherPos = pos.down();
        otherState = world.getBlockState(otherPos);
        otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (negativeSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP) {
                this.downBlock = FULL;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            if (negativeSlab == entity.getPositiveSlabState().getBlock()) {
                this.downBlock = FULL;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
            boolean bl1 = b == entity.getPositiveSlabState().getBlock();
            boolean bl2 = b == entity.getNegativeSlabState().getBlock();
            if (bl1 && bl2) {
                this.downBlock = FULL;
            } else if (bl1) {
                this.downBlock = entity.isX() ? POSITIVE1 : POSITIVE2;
            } else if (bl2) {
                this.downBlock = entity.isX() ? NEGATIVE1 : NEGATIVE2;
            }
        } else {
            if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                this.downBlock = FULL;
            }
        }

        // EAST,SOUTH,WEST,NORTH
        this.eastBlockTop = eastNeighborComparison(world, pos, positiveSlab);
        this.southBlockTop = southNeighborComparison(world, pos, positiveSlab);
        this.westBlockTop = westNeighborComparison(world, pos, positiveSlab);
        this.northBlockTop = northNeighborComparison(world, pos, positiveSlab);

        if (isSameSlab) {
            this.eastBlockBottom = this.eastBlockTop;
            this.southBlockBottom = this.southBlockTop;
            this.westBlockBottom = this.westBlockTop;
            this.northBlockBottom = this.northBlockTop;
        } else {
            this.eastBlockBottom = eastNeighborComparison(world, pos, negativeSlab);
            this.southBlockBottom = southNeighborComparison(world, pos, negativeSlab);
            this.westBlockBottom = westNeighborComparison(world, pos, negativeSlab);
            this.northBlockBottom = northNeighborComparison(world, pos, negativeSlab);
        }

        // TOP-EAST
        if (upBlock != OTHER && upBlock != NEGATIVE1 && eastBlockTop != OTHER && eastBlockTop != NEGATIVE1) {
            otherPos = pos.up().east();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                    this.topEastBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (positiveSlab == entity.getNegativeSlabState().getBlock()) {
                    this.topEastBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (entity.isX()) {
                    if (bl2) {
                        this.topEastBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        this.topEastBlock = FULL;
                    } else if (bl1) {
                        this.topEastBlock = POSITIVE1;
                    } else {
                        this.topEastBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                    this.topEastBlock = FULL;
                }
            }
        }

        // TOP-SOUTH
        if (upBlock != OTHER && upBlock != NEGATIVE2 && southBlockTop != OTHER && southBlockTop != NEGATIVE1) {
            otherPos = pos.up().south();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                    this.topSouthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (positiveSlab == entity.getNegativeSlabState().getBlock()) {
                    this.topSouthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (!entity.isX()) {
                    if (bl2) {
                        this.topSouthBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        this.topSouthBlock = FULL;
                    } else if (bl1) {
                        this.topSouthBlock = POSITIVE1;
                    } else {
                        this.topSouthBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                    this.topSouthBlock = FULL;
                }
            }
        }

        // TOP-WEST
        if (upBlock != OTHER && upBlock != POSITIVE1 && westBlockTop != OTHER && westBlockTop != NEGATIVE1) {
            otherPos = pos.up().west();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                    this.topWestBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (positiveSlab == entity.getNegativeSlabState().getBlock()) {
                    this.topWestBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (entity.isX()) {
                    if (bl1) {
                        this.topWestBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        this.topWestBlock = FULL;
                    } else if (bl1) {
                        this.topWestBlock = POSITIVE1;
                    } else {
                        this.topWestBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                    this.topWestBlock = FULL;
                }
            }
        }

        // TOP-WEST
        if (upBlock != OTHER && upBlock != POSITIVE2 && northBlockTop != OTHER && northBlockTop != NEGATIVE1) {
            otherPos = pos.up().north();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                    this.topNorthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (positiveSlab == entity.getNegativeSlabState().getBlock()) {
                    this.topNorthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (!entity.isX()) {
                    if (bl1) {
                        this.topNorthBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        this.topNorthBlock = FULL;
                    } else if (bl1) {
                        this.topNorthBlock = POSITIVE1;
                    } else {
                        this.topNorthBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                    this.topNorthBlock = FULL;
                }
            }
        }

        // SOUTH-EAST
        /*if (southBlockTop != OTHER && southBlockTop != POSITIVE2 && northBlockTop != OTHER && northBlockTop != NEGATIVE1) {
            otherPos = pos.up().north();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                    this.topNorthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (positiveSlab == entity.getNegativeSlabState().getBlock()) {
                    this.topNorthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (!entity.isX()) {
                    if (bl1) {
                        this.topNorthBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        this.topNorthBlock = FULL;
                    } else if (bl1) {
                        this.topNorthBlock = POSITIVE1;
                    } else {
                        this.topNorthBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                    this.topNorthBlock = FULL;
                }
            }
        }*/

        // BOTTOM-EAST
        if (downBlock != OTHER && downBlock != NEGATIVE1 && eastBlockBottom != OTHER && eastBlockBottom != POSITIVE1) {
            otherPos = pos.down().east();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (negativeSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP) {
                    this.bottomEastBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (negativeSlab == entity.getPositiveSlabState().getBlock()) {
                    this.bottomEastBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (entity.isX()) {
                    if (bl2) {
                        this.bottomEastBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        this.bottomEastBlock = FULL;
                    } else if (bl1) {
                        this.bottomEastBlock = POSITIVE1;
                    } else {
                        this.bottomEastBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(negativeSlab) == otherBlock) {
                    this.bottomEastBlock = FULL;
                }
            }
        }

        // BOTTOM-SOUTH
        if (downBlock != OTHER && downBlock != NEGATIVE2 && southBlockBottom != OTHER && southBlockBottom != POSITIVE1) {
            otherPos = pos.down().south();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (negativeSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP) {
                    this.bottomSouthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (negativeSlab == entity.getPositiveSlabState().getBlock()) {
                    this.bottomSouthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (!entity.isX()) {
                    if (bl2) {
                        this.bottomSouthBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        this.bottomSouthBlock = FULL;
                    } else if (bl1) {
                        this.bottomSouthBlock = POSITIVE1;
                    } else {
                        this.bottomSouthBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(negativeSlab) == otherBlock) {
                    this.bottomSouthBlock = FULL;
                }
            }
        }

        // BOTTOM-WEST
        if (downBlock != OTHER && downBlock != POSITIVE1 && westBlockBottom != OTHER && westBlockBottom != POSITIVE1) {
            otherPos = pos.down().west();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (negativeSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP) {
                    this.bottomWestBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (negativeSlab == entity.getPositiveSlabState().getBlock()) {
                    this.bottomWestBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (entity.isX()) {
                    if (bl1) {
                        this.bottomWestBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        this.bottomWestBlock = FULL;
                    } else if (bl1) {
                        this.bottomWestBlock = POSITIVE1;
                    } else {
                        this.bottomWestBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(negativeSlab) == otherBlock) {
                    this.bottomWestBlock = FULL;
                }
            }
        }

        // BOTTOM-WEST
        if (downBlock != OTHER && downBlock != POSITIVE2 && northBlockBottom != OTHER && northBlockBottom != POSITIVE1) {
            otherPos = pos.down().north();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (negativeSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP) {
                    this.bottomNorthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (negativeSlab == entity.getPositiveSlabState().getBlock()) {
                    this.bottomNorthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (!entity.isX()) {
                    if (bl1) {
                        this.bottomNorthBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        this.bottomNorthBlock = FULL;
                    } else if (bl1) {
                        this.bottomNorthBlock = POSITIVE1;
                    } else {
                        this.bottomNorthBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(negativeSlab) == otherBlock) {
                    this.bottomNorthBlock = FULL;
                }
            }
        }
    }

    private int eastNeighborComparison(BlockRenderView world, BlockPos pos, Block block) {
        BlockPos otherPos = pos.east();
        BlockState otherState = world.getBlockState(otherPos);
        Block otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (block == otherBlock) {
                return otherState.get(SlabBlock.TYPE) == SlabType.TOP ? POSITIVE1 : NEGATIVE1;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            boolean bl1 = block == entity.getPositiveSlabState().getBlock();
            boolean bl2 = block == entity.getNegativeSlabState().getBlock();
            if (bl1 && bl2) {
                return FULL;
            } else if (bl1) {
                return POSITIVE1;
            } else if (bl2) {
                return NEGATIVE1;
            }
        } else if (otherBlock instanceof VerticalSlabBlock) {
            if (block == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                if (d == Direction.SOUTH) {
                    return POSITIVE2;
                } else if (d == Direction.WEST) {
                    return FULL;
                } else if (d == Direction.NORTH) {
                    return NEGATIVE2;
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            if (entity.isX()) {
                if (block == ModBlockMap.verticalSlabToSlab(entity.getNegativeSlabState().getBlock())) {
                    return FULL;
                }
            } else {
                Block b = ModBlockMap.slabToVerticalSlab(block);
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

    private int southNeighborComparison(BlockRenderView world, BlockPos pos, Block block) {
        BlockPos otherPos = pos.south();
        BlockState otherState = world.getBlockState(otherPos);
        Block otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (block == otherBlock) {
                return otherState.get(SlabBlock.TYPE) == SlabType.TOP ? POSITIVE1 : NEGATIVE1;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            boolean bl1 = block == entity.getPositiveSlabState().getBlock();
            boolean bl2 = block == entity.getNegativeSlabState().getBlock();
            if (bl1 && bl2) {
                return FULL;
            } else if (bl1) {
                return POSITIVE1;
            } else if (bl2) {
                return NEGATIVE1;
            }
        } else if (otherBlock instanceof VerticalSlabBlock) {
            if (block == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                if (d == Direction.EAST) {
                    return POSITIVE2;
                } else if (d == Direction.NORTH) {
                    return FULL;
                } else if (d == Direction.WEST) {
                    return NEGATIVE2;
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            if (!entity.isX()) {
                if (block == ModBlockMap.verticalSlabToSlab(entity.getNegativeSlabState().getBlock())) {
                    return FULL;
                }
            } else {
                Block b = ModBlockMap.slabToVerticalSlab(block);
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

    private int westNeighborComparison(BlockRenderView world, BlockPos pos, Block block) {
        BlockPos otherPos = pos.west();
        BlockState otherState = world.getBlockState(otherPos);
        Block otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (block == otherBlock) {
                return otherState.get(SlabBlock.TYPE) == SlabType.TOP ? POSITIVE1 : NEGATIVE1;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            boolean bl1 = block == entity.getPositiveSlabState().getBlock();
            boolean bl2 = block == entity.getNegativeSlabState().getBlock();
            if (bl1 && bl2) {
                return FULL;
            } else if (bl1) {
                return POSITIVE1;
            } else if (bl2) {
                return NEGATIVE1;
            }
        } else if (otherBlock instanceof VerticalSlabBlock) {
            if (block == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                if (d == Direction.SOUTH) {
                    return POSITIVE2;
                } else if (d == Direction.EAST) {
                    return FULL;
                } else if (d == Direction.NORTH) {
                    return NEGATIVE2;
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            if (entity.isX()) {
                if (block == ModBlockMap.verticalSlabToSlab(entity.getPositiveSlabState().getBlock())) {
                    return FULL;
                }
            } else {
                Block b = ModBlockMap.slabToVerticalSlab(block);
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

    private int northNeighborComparison(BlockRenderView world, BlockPos pos, Block block) {
        BlockPos otherPos = pos.north();
        BlockState otherState = world.getBlockState(otherPos);
        Block otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (block == otherBlock) {
                return otherState.get(SlabBlock.TYPE) == SlabType.TOP ? POSITIVE1 : NEGATIVE1;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            boolean bl1 = block == entity.getPositiveSlabState().getBlock();
            boolean bl2 = block == entity.getNegativeSlabState().getBlock();
            if (bl1 && bl2) {
                return FULL;
            } else if (bl1) {
                return POSITIVE1;
            } else if (bl2) {
                return NEGATIVE1;
            }
        } else if (otherBlock instanceof VerticalSlabBlock) {
            if (block == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                if (d == Direction.EAST) {
                    return POSITIVE2;
                } else if (d == Direction.SOUTH) {
                    return FULL;
                } else if (d == Direction.WEST) {
                    return NEGATIVE2;
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            if (!entity.isX()) {
                if (block == ModBlockMap.verticalSlabToSlab(entity.getPositiveSlabState().getBlock())) {
                    return FULL;
                }
            } else {
                Block b = ModBlockMap.slabToVerticalSlab(block);
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

    private boolean shouldCullPositive(Direction face) {
        if (face == Direction.UP) {
            return this.upBlock == FULL;
        } else if (face == Direction.DOWN) {
            return this.isSameSlab;
        } else if (face == Direction.EAST) {
            return eastBlockTop == FULL || eastBlockTop == POSITIVE1;
        } else if (face == Direction.SOUTH) {
            return southBlockTop == FULL || southBlockTop == POSITIVE1;
        } else if (face == Direction.WEST) {
            return westBlockTop == FULL || westBlockTop == POSITIVE1;
        } else {
            return northBlockTop == FULL || northBlockTop == POSITIVE1;
        }
    }

    private boolean shouldCullNegative(Direction face) {
        if (face == Direction.UP) {
            return this.isSameSlab;
        } else if (face == Direction.DOWN) {
            return this.downBlock == FULL;
        } else if (face == Direction.EAST) {
            return eastBlockBottom == FULL || eastBlockBottom == NEGATIVE1;
        } else if (face == Direction.SOUTH) {
            return southBlockBottom == FULL || southBlockBottom == NEGATIVE1;
        } else if (face == Direction.WEST) {
            return westBlockBottom == FULL || westBlockBottom == NEGATIVE1;
        } else {
            return northBlockBottom == FULL || northBlockBottom == NEGATIVE1;
        }
    }
}
