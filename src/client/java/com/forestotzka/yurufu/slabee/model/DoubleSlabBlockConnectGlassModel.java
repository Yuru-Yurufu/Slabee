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

    private final int OTHER = 0;
    private final int FULL = 1;
    private final int POSITIVE1 = 3;
    private final int NEGATIVE1 = 4;
    private final int POSITIVE2 = 5;
    private final int NEGATIVE2 = 6;

    private static record NeighborState(
            boolean isSameSlab,
            int upBlock, int downBlock,
            int eastBlockTop, int southBlockTop, int westBlockTop, int northBlockTop,
            int eastBlockBottom, int southBlockBottom, int westBlockBottom, int northBlockBottom,
            int topEastBlock, int topSouthBlock, int topWestBlock, int topNorthBlock,
            int southEastBlockTop, int southWestBlockTop, int northWestBlockTop, int northEastBlockTop,
            int southEastBlockBottom, int southWestBlockBottom, int northWestBlockBottom, int northEastBlockBottom,
            int bottomEastBlock, int bottomSouthBlock, int bottomWestBlock, int bottomNorthBlock
    ) {}

    private static record ConnectionFlags(
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

        for (int patternIndex = 0; patternIndex < GLASS_SLAB_PATTERN_COUNT; patternIndex++) {
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
                    faceMeshes = endPositiveMeshMap.get(GLASS_PATTERN_COUNT-1);
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
                    faceMeshes = endNegativeMeshMap.get(GLASS_PATTERN_COUNT-1);
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
        } else {
            indexes = determinePatternEndNegative(face, neighborState);
        }

        indexes.replaceAll(i -> i - (i / 6));

        System.out.println("" + face + indexes);
        return indexes;
    }

    private int getSidePatternIndex(Direction face, NeighborState neighborState, boolean isPositive) {
        int index;
        if (isPositive) {
            index = determinePatternPositive(face, neighborState);
        } else {
            index = determinePatternNegative(face, neighborState);
        }

        //System.out.println("" + face + isPositive + index);
        if (index >= 64) {
            index = GLASS_SLAB_INDEX_MAP.getOrDefault(index, 0);
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
                bottomLeft = true;
            } else if (neighborState.southBlockTop == NEGATIVE2) {
                bottomRight = true;
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
                rightBottom = true;
            } else if (neighborState.eastBlockTop == NEGATIVE2) {
                rightTop = true;
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
                bottomLeft = true;
            } else if (neighborState.northBlockTop == NEGATIVE2) {
                bottomRight = true;
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
                bottomLeft,
                bottomRight,
                leftTop,
                leftBottom,
                cornerTopRight,
                cornerBottomRight,
                cornerBottomLeft,
                cornerTopLeft
        ));
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
                bottomLeft = true;
            } else if (neighborState.southBlockBottom == NEGATIVE2) {
                bottomRight = true;
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
                rightBottom = true;
            } else if (neighborState.eastBlockBottom == NEGATIVE2) {
                rightTop = true;
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
                bottomLeft = true;
            } else if (neighborState.northBlockBottom == NEGATIVE2) {
                bottomRight = true;
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
                bottomLeft,
                bottomRight,
                leftTop,
                leftBottom,
                cornerTopRight,
                cornerBottomRight,
                cornerBottomLeft,
                cornerTopLeft
        ));
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
            left = neighborState.southBlockTop == FULL || neighborState.southBlockTop == POSITIVE1;
            right = neighborState.northBlockTop == FULL || neighborState.northBlockTop == POSITIVE1;
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
                if (neighborState.northBlockTop == FULL) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.southBlockTop == FULL) {
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
            left = neighborState.westBlockTop == FULL || neighborState.westBlockTop == POSITIVE1;
            right = neighborState.eastBlockTop == FULL || neighborState.eastBlockTop == POSITIVE1;
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
                if (neighborState.eastBlockTop == FULL) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.westBlockTop == FULL) {
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
            left = neighborState.northBlockTop == FULL || neighborState.northBlockTop == POSITIVE1;
            right = neighborState.southBlockTop == FULL || neighborState.southBlockTop == POSITIVE1;
            if (neighborState.isSameSlab) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.topSouthBlock == FULL || neighborState.topSouthBlock == POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.southBlockTop == FULL) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.northBlockTop == FULL) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.topNorthBlock == FULL || neighborState.topNorthBlock == POSITIVE1) {
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
            left = neighborState.eastBlockTop == FULL || neighborState.eastBlockTop == POSITIVE1;
            right = neighborState.westBlockTop == FULL || neighborState.westBlockTop == POSITIVE1;
            if (neighborState.isSameSlab) {
                bottomLeft = true;
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.topWestBlock == FULL || neighborState.topWestBlock == POSITIVE1) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.westBlockTop == FULL) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.eastBlockTop == FULL) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.topEastBlock == FULL || neighborState.topEastBlock == POSITIVE1) {
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
            left = neighborState.southBlockBottom == FULL || neighborState.southBlockBottom == NEGATIVE1;
            right = neighborState.northBlockBottom == FULL || neighborState.northBlockBottom == NEGATIVE1;
            if (neighborState.downBlock == FULL || neighborState.downBlock == POSITIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.downBlock == POSITIVE2) {
                bottomLeft = true;
            } else if (neighborState.downBlock == NEGATIVE2) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.northBlockBottom == FULL) {
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
                if (neighborState.southBlockBottom == FULL) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.SOUTH) {
            if (neighborState.isSameSlab) {
                topLeft = true;
                topRight = true;
            }
            left = neighborState.westBlockBottom == FULL || neighborState.westBlockBottom == NEGATIVE1;
            right = neighborState.eastBlockBottom == FULL || neighborState.eastBlockBottom == NEGATIVE1;
            if (neighborState.downBlock == FULL || neighborState.downBlock == POSITIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.downBlock == NEGATIVE1) {
                bottomLeft = true;
            } else if (neighborState.downBlock == POSITIVE1) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.eastBlockBottom == FULL) {
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
                if (neighborState.westBlockBottom == FULL) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.WEST) {
            if (neighborState.isSameSlab) {
                topLeft = true;
                topRight = true;
            }
            left = neighborState.northBlockBottom == FULL || neighborState.northBlockBottom == NEGATIVE1;
            right = neighborState.southBlockBottom == FULL || neighborState.southBlockBottom == NEGATIVE1;
            if (neighborState.downBlock == FULL || neighborState.downBlock == NEGATIVE1) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.downBlock == NEGATIVE2) {
                bottomLeft = true;
            } else if (neighborState.downBlock == POSITIVE2) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.southBlockBottom == FULL) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.bottomSouthBlock == FULL || neighborState.bottomSouthBlock == POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.bottomNorthBlock == FULL || neighborState.bottomNorthBlock == POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.northBlockBottom == FULL) {
                    cornerTopLeft = true;
                }
            }
        } else if (face == Direction.NORTH) {
            if (neighborState.isSameSlab) {
                topLeft = true;
                topRight = true;
            }
            left = neighborState.eastBlockBottom == FULL || neighborState.eastBlockBottom == NEGATIVE1;
            right = neighborState.westBlockBottom == FULL || neighborState.westBlockBottom == NEGATIVE1;
            if (neighborState.downBlock == FULL || neighborState.downBlock == NEGATIVE2) {
                bottomLeft = true;
                bottomRight = true;
            } else if (neighborState.downBlock == POSITIVE1) {
                bottomLeft = true;
            } else if (neighborState.downBlock == NEGATIVE1) {
                bottomRight = true;
            }
            if (topRight && right) {
                if (neighborState.westBlockBottom == FULL) {
                    cornerTopRight = true;
                }
            }
            if (bottomRight && right) {
                if (neighborState.bottomWestBlock == FULL || neighborState.bottomWestBlock == POSITIVE1) {
                    cornerBottomRight = true;
                }
            }
            if (bottomLeft && left) {
                if (neighborState.bottomEastBlock == FULL || neighborState.bottomEastBlock == POSITIVE1) {
                    cornerBottomLeft = true;
                }
            }
            if (topLeft && left) {
                if (neighborState.eastBlockBottom == FULL) {
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

    private List<Integer> determineEndPatternIndexes(ConnectionFlags flags) {
        List<Integer> indexes = new ArrayList<>();
        int i = 0;
        if (flags.topLeft) i += 1;
        if (flags.topRight) i += 2;
        if (flags.cornerTopRight) i += 2;
        if (i != 5) {
            indexes.add(i);
        }

        i = 6;
        if (flags.rightTop) i += 1;
        if (flags.rightBottom) i += 2;
        if (flags.cornerBottomRight) i += 2;
        if (i != 11) {
            indexes.add(i);
        }

        i = 12;
        if (flags.bottomRight) i += 1;
        if (flags.bottomLeft) i += 2;
        if (flags.cornerBottomLeft) i += 2;
        if (i != 17) {
            indexes.add(i);
        }

        i = 18;
        if (flags.leftBottom) i += 1;
        if (flags.leftTop) i += 2;
        if (flags.cornerTopLeft) i += 2;
        if (i != 23) {
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
        int upBlock = OTHER;
        int downBlock = OTHER;
        int eastBlockTop = OTHER;
        int southBlockTop = OTHER;
        int westBlockTop = OTHER;
        int northBlockTop = OTHER;
        int eastBlockBottom = OTHER;
        int southBlockBottom = OTHER;
        int westBlockBottom = OTHER;
        int northBlockBottom = OTHER;
        int topEastBlock = OTHER;
        int topSouthBlock = OTHER;
        int topWestBlock = OTHER;
        int topNorthBlock = OTHER;
        int southEastBlockTop = OTHER;
        int southWestBlockTop = OTHER;
        int northWestBlockTop = OTHER;
        int northEastBlockTop = OTHER;
        int southEastBlockBottom = OTHER;
        int southWestBlockBottom = OTHER;
        int northWestBlockBottom = OTHER;
        int northEastBlockBottom = OTHER;
        int bottomEastBlock = OTHER;
        int bottomSouthBlock = OTHER;
        int bottomWestBlock = OTHER;
        int bottomNorthBlock = OTHER;

        BlockPos otherPos;
        BlockState otherState;
        Block otherBlock;

        isSameSlab = positiveSlab == negativeSlab;

        // UP
        otherPos = pos.up();
        otherState = world.getBlockState(otherPos);
        otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                upBlock = FULL;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            if (positiveSlab == entity.getNegativeSlabState().getBlock()) {
                upBlock = FULL;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
            boolean bl1 = b == entity.getPositiveSlabState().getBlock();
            boolean bl2 = b == entity.getNegativeSlabState().getBlock();

            if (bl1 && bl2) {
                upBlock = FULL;
            } else if (bl1) {
                upBlock = entity.isX() ? POSITIVE1 : POSITIVE2;
            } else if (bl2) {
                upBlock = entity.isX() ? NEGATIVE1 : NEGATIVE2;
            }
        } else {
            if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                upBlock = FULL;
            }
        }

        // DOWN
        otherPos = pos.down();
        otherState = world.getBlockState(otherPos);
        otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (negativeSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP) {
                downBlock = FULL;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            if (negativeSlab == entity.getPositiveSlabState().getBlock()) {
                downBlock = FULL;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
            boolean bl1 = b == entity.getPositiveSlabState().getBlock();
            boolean bl2 = b == entity.getNegativeSlabState().getBlock();
            if (bl1 && bl2) {
                downBlock = FULL;
            } else if (bl1) {
                downBlock = entity.isX() ? POSITIVE1 : POSITIVE2;
            } else if (bl2) {
                downBlock = entity.isX() ? NEGATIVE1 : NEGATIVE2;
            }
        } else {
            if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                downBlock = FULL;
            }
        }

        // EAST,SOUTH,WEST,NORTH
        eastBlockTop = eastNeighborComparison(world, pos, positiveSlab);
        southBlockTop = southNeighborComparison(world, pos, positiveSlab);
        westBlockTop = westNeighborComparison(world, pos, positiveSlab);
        northBlockTop = northNeighborComparison(world, pos, positiveSlab);

        if (isSameSlab) {
            eastBlockBottom = eastBlockTop;
            southBlockBottom = southBlockTop;
            westBlockBottom = westBlockTop;
            northBlockBottom = northBlockTop;
        } else {
            eastBlockBottom = eastNeighborComparison(world, pos, negativeSlab);
            southBlockBottom = southNeighborComparison(world, pos, negativeSlab);
            westBlockBottom = westNeighborComparison(world, pos, negativeSlab);
            northBlockBottom = northNeighborComparison(world, pos, negativeSlab);
        }

        // TOP-EAST
        if (upBlock != OTHER && upBlock != NEGATIVE1 && eastBlockTop != OTHER && eastBlockTop != NEGATIVE1) {
            otherPos = pos.up().east();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                    topEastBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (positiveSlab == entity.getNegativeSlabState().getBlock()) {
                    topEastBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (entity.isX()) {
                    if (bl2) {
                        topEastBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        topEastBlock = FULL;
                    } else if (bl1) {
                        topEastBlock = POSITIVE1;
                    } else {
                        topEastBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                    topEastBlock = FULL;
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
                    topSouthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (positiveSlab == entity.getNegativeSlabState().getBlock()) {
                    topSouthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (!entity.isX()) {
                    if (bl2) {
                        topSouthBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        topSouthBlock = FULL;
                    } else if (bl1) {
                        topSouthBlock = POSITIVE1;
                    } else {
                        topSouthBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                    topSouthBlock = FULL;
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
                    topWestBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (positiveSlab == entity.getNegativeSlabState().getBlock()) {
                    topWestBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (entity.isX()) {
                    if (bl1) {
                        topWestBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        topWestBlock = FULL;
                    } else if (bl1) {
                        topWestBlock = POSITIVE1;
                    } else {
                        topWestBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                    topWestBlock = FULL;
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
                    topNorthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (positiveSlab == entity.getNegativeSlabState().getBlock()) {
                    topNorthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (!entity.isX()) {
                    if (bl1) {
                        topNorthBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        topNorthBlock = FULL;
                    } else if (bl1) {
                        topNorthBlock = POSITIVE1;
                    } else {
                        topNorthBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                    topNorthBlock = FULL;
                }
            }
        }

        // SOUTH-EAST-TOP
        if (southBlockTop != OTHER && southBlockTop != NEGATIVE1 && eastBlockTop != OTHER && eastBlockTop != NEGATIVE1) {
            otherPos = pos.south().east();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP) {
                    southEastBlockTop = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (positiveSlab == entity.getPositiveSlabState().getBlock()) {
                    southEastBlockTop = FULL;
                }
            } else if (otherBlock instanceof VerticalSlabBlock) {
                if (positiveSlab == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                    Direction d = otherState.get(VerticalSlabBlock.FACING);
                    if (d == Direction.WEST || d == Direction.NORTH) {
                        southEastBlockTop = FULL;
                    }
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                if (b == entity.getNegativeSlabState().getBlock()) {
                    southEastBlockTop = FULL;
                }
            } else {
                if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                    southEastBlockTop = FULL;
                }
            }
        }

        // SOUTH-EAST-BOTTOM
        if (southBlockBottom != OTHER && southBlockBottom != POSITIVE1 && eastBlockBottom != OTHER && eastBlockBottom != POSITIVE1) {
            otherPos = pos.south().east();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (negativeSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                    southEastBlockBottom = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (negativeSlab == entity.getNegativeSlabState().getBlock()) {
                    southEastBlockBottom = FULL;
                }
            } else if (otherBlock instanceof VerticalSlabBlock) {
                if (negativeSlab == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                    Direction d = otherState.get(VerticalSlabBlock.FACING);
                    if (d == Direction.WEST || d == Direction.NORTH) {
                        southEastBlockBottom = FULL;
                    }
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
                if (b == entity.getNegativeSlabState().getBlock()) {
                    southEastBlockBottom = FULL;
                }
            } else {
                if (ModBlockMap.slabToOriginal(negativeSlab) == otherBlock) {
                    southEastBlockBottom = FULL;
                }
            }
        }

        // SOUTH-WEST-TOP
        if (southBlockTop != OTHER && southBlockTop != NEGATIVE1 && westBlockTop != OTHER && westBlockTop != NEGATIVE1) {
            otherPos = pos.south().west();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP) {
                    southWestBlockTop = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (positiveSlab == entity.getPositiveSlabState().getBlock()) {
                    southWestBlockTop = FULL;
                }
            } else if (otherBlock instanceof VerticalSlabBlock) {
                if (positiveSlab == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                    Direction d = otherState.get(VerticalSlabBlock.FACING);
                    if (d == Direction.EAST || d == Direction.NORTH) {
                        southWestBlockTop = FULL;
                    }
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                if (entity.isX()) {
                    if (b == entity.getPositiveSlabState().getBlock()) {
                        southWestBlockTop = FULL;
                    }
                } else {
                    if (b == entity.getNegativeSlabState().getBlock()) {
                        southWestBlockTop = FULL;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                    southWestBlockTop = FULL;
                }
            }
        }

        // SOUTH-WEST-BOTTOM
        if (southBlockBottom != OTHER && southBlockBottom != POSITIVE1 && westBlockBottom != OTHER && westBlockBottom != POSITIVE1) {
            otherPos = pos.south().west();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (negativeSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                    southWestBlockBottom = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (negativeSlab == entity.getNegativeSlabState().getBlock()) {
                    southWestBlockBottom = FULL;
                }
            } else if (otherBlock instanceof VerticalSlabBlock) {
                if (negativeSlab == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                    Direction d = otherState.get(VerticalSlabBlock.FACING);
                    if (d == Direction.EAST || d == Direction.NORTH) {
                        southWestBlockBottom = FULL;
                    }
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
                if (entity.isX()) {
                    if (b == entity.getPositiveSlabState().getBlock()) {
                        southWestBlockBottom = FULL;
                    }
                } else {
                    if (b == entity.getNegativeSlabState().getBlock()) {
                        southWestBlockBottom = FULL;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(negativeSlab) == otherBlock) {
                    southWestBlockBottom = FULL;
                }
            }
        }

        // NORTH-EAST-TOP
        if (northBlockTop != OTHER && northBlockTop != NEGATIVE1 && eastBlockTop != OTHER && eastBlockTop != NEGATIVE1) {
            otherPos = pos.north().east();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP) {
                    northEastBlockTop = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (positiveSlab == entity.getPositiveSlabState().getBlock()) {
                    northEastBlockTop = FULL;
                }
            } else if (otherBlock instanceof VerticalSlabBlock) {
                if (positiveSlab == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                    Direction d = otherState.get(VerticalSlabBlock.FACING);
                    if (d == Direction.WEST || d == Direction.SOUTH) {
                        northEastBlockTop = FULL;
                    }
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                if (entity.isX()) {
                    if (b == entity.getNegativeSlabState().getBlock()) {
                        northEastBlockTop = FULL;
                    }
                } else {
                    if (b == entity.getPositiveSlabState().getBlock()) {
                        northEastBlockTop = FULL;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                    northEastBlockTop = FULL;
                }
            }
        }

        // NORTH-EAST-BOTTOM
        if (northBlockBottom != OTHER && northBlockBottom != POSITIVE1 && eastBlockBottom != OTHER && eastBlockBottom != POSITIVE1) {
            otherPos = pos.north().east();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (negativeSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                    northEastBlockBottom = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (negativeSlab == entity.getNegativeSlabState().getBlock()) {
                    northEastBlockBottom = FULL;
                }
            } else if (otherBlock instanceof VerticalSlabBlock) {
                if (negativeSlab == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                    Direction d = otherState.get(VerticalSlabBlock.FACING);
                    if (d == Direction.WEST || d == Direction.SOUTH) {
                        northEastBlockBottom = FULL;
                    }
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
                if (entity.isX()) {
                    if (b == entity.getNegativeSlabState().getBlock()) {
                        northEastBlockBottom = FULL;
                    }
                } else {
                    if (b == entity.getPositiveSlabState().getBlock()) {
                        northEastBlockBottom = FULL;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(negativeSlab) == otherBlock) {
                    northEastBlockBottom = FULL;
                }
            }
        }

        // NORTH-WEST-TOP
        if (northBlockTop != OTHER && northBlockTop != NEGATIVE1 && westBlockTop != OTHER && westBlockTop != NEGATIVE1) {
            otherPos = pos.north().west();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP) {
                    northWestBlockTop = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (positiveSlab == entity.getPositiveSlabState().getBlock()) {
                    northWestBlockTop = FULL;
                }
            } else if (otherBlock instanceof VerticalSlabBlock) {
                if (positiveSlab == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                    Direction d = otherState.get(VerticalSlabBlock.FACING);
                    if (d == Direction.EAST || d == Direction.SOUTH) {
                        northWestBlockTop = FULL;
                    }
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                if (b == entity.getPositiveSlabState().getBlock()) {
                    northWestBlockTop = FULL;
                }
            } else {
                if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                    northWestBlockTop = FULL;
                }
            }
        }

        // NORTH-WEST-BOTTOM
        if (northBlockBottom != OTHER && northBlockBottom != POSITIVE1 && westBlockBottom != OTHER && westBlockBottom != POSITIVE1) {
            otherPos = pos.north().west();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (negativeSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                    northWestBlockBottom = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (negativeSlab == entity.getNegativeSlabState().getBlock()) {
                    northWestBlockBottom = FULL;
                }
            } else if (otherBlock instanceof VerticalSlabBlock) {
                if (negativeSlab == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                    Direction d = otherState.get(VerticalSlabBlock.FACING);
                    if (d == Direction.EAST || d == Direction.SOUTH) {
                        northWestBlockBottom = FULL;
                    }
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
                if (b == entity.getPositiveSlabState().getBlock()) {
                    northWestBlockBottom = FULL;
                }
            } else {
                if (ModBlockMap.slabToOriginal(negativeSlab) == otherBlock) {
                    northWestBlockBottom = FULL;
                }
            }
        }

        // BOTTOM-EAST
        if (downBlock != OTHER && downBlock != NEGATIVE1 && eastBlockBottom != OTHER && eastBlockBottom != POSITIVE1) {
            otherPos = pos.down().east();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                if (negativeSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP) {
                    bottomEastBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (negativeSlab == entity.getPositiveSlabState().getBlock()) {
                    bottomEastBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (entity.isX()) {
                    if (bl2) {
                        bottomEastBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        bottomEastBlock = FULL;
                    } else if (bl1) {
                        bottomEastBlock = POSITIVE1;
                    } else {
                        bottomEastBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(negativeSlab) == otherBlock) {
                    bottomEastBlock = FULL;
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
                    bottomSouthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (negativeSlab == entity.getPositiveSlabState().getBlock()) {
                    bottomSouthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (!entity.isX()) {
                    if (bl2) {
                        bottomSouthBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        bottomSouthBlock = FULL;
                    } else if (bl1) {
                        bottomSouthBlock = POSITIVE1;
                    } else {
                        bottomSouthBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(negativeSlab) == otherBlock) {
                    bottomSouthBlock = FULL;
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
                    bottomWestBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (negativeSlab == entity.getPositiveSlabState().getBlock()) {
                    bottomWestBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (entity.isX()) {
                    if (bl1) {
                        bottomWestBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        bottomWestBlock = FULL;
                    } else if (bl1) {
                        bottomWestBlock = POSITIVE1;
                    } else {
                        bottomWestBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(negativeSlab) == otherBlock) {
                    bottomWestBlock = FULL;
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
                    bottomNorthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                if (negativeSlab == entity.getPositiveSlabState().getBlock()) {
                    bottomNorthBlock = FULL;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();

                if (!entity.isX()) {
                    if (bl1) {
                        bottomNorthBlock = FULL;
                    }
                } else {
                    if (bl1 && bl2) {
                        bottomNorthBlock = FULL;
                    } else if (bl1) {
                        bottomNorthBlock = POSITIVE1;
                    } else {
                        bottomNorthBlock = NEGATIVE1;
                    }
                }
            } else {
                if (ModBlockMap.slabToOriginal(negativeSlab) == otherBlock) {
                    bottomNorthBlock = FULL;
                }
            }
        }

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
