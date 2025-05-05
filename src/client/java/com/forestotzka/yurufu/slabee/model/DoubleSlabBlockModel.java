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
public class DoubleSlabBlockModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private final Identifier positiveId;
    private final Identifier negativeId;
    private final Block positiveSlab;
    private final Block negativeSlab;
    private BakedModel positiveBakedModel;
    private BakedModel negativeBakedModel;
    private Mesh nullMesh;
    private Mesh mesh;

    private final Map<Integer, EnumMap<Direction, Mesh>> positiveMeshMap = new HashMap<>();
    private final Map<Integer, EnumMap<Direction, Mesh>> negativeMeshMap = new HashMap<>();

    private static final int PATTERN_COUNT = 169;
    /*public static final Map<Integer, Integer> INDEX_MAP = Map.<Integer, Integer>ofEntries(
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
    );*/

    private static final SpriteIdentifier[] SPRITE_IDS = new SpriteIdentifier[PATTERN_COUNT];
    static {
        for (int i = 0; i < PATTERN_COUNT; i++) {
            SPRITE_IDS[i] = new SpriteIdentifier(
                    PlayerScreenHandler.BLOCK_ATLAS_TEXTURE,
                    Identifier.of(Slabee.MOD_ID, "block/glass_slab/" + i)
            );
        }
    }

    private Baker baker;
    private Function<SpriteIdentifier, Sprite> textureGetter;
    private ModelBakeSettings rotationContainer;

    public DoubleSlabBlockModel(String positiveSlab, String negativeSlab) {
        if (positiveSlab.equals("normal") || positiveSlab.equals("non_opaque")) {
            this.positiveSlab = null;
            this.positiveId = null;
        } else {
            this.positiveSlab = Registries.BLOCK.get(Identifier.of(Slabee.MOD_ID, positiveSlab + "_slab"));
            Identifier positiveId = Registries.BLOCK.getId(this.positiveSlab);
            this.positiveId = Identifier.of(positiveId.getNamespace(), "block/" + positiveId.getPath() + "_top");
        }

        if (negativeSlab.equals("normal") || negativeSlab.equals("non_opaque")) {
            this.negativeSlab = null;
            this.negativeId = null;
        } else {
            this.negativeSlab = Registries.BLOCK.get(Identifier.of(Slabee.MOD_ID, negativeSlab + "_slab"));
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
        return null;
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

        for (int patternIndex = 0; patternIndex < PATTERN_COUNT; patternIndex++) {
            EnumMap<Direction, Mesh> positiveFaceMeshes = new EnumMap<>(Direction.class);
            EnumMap<Direction, Mesh> negativeFaceMeshes = new EnumMap<>(Direction.class);

            for (Direction dir : Direction.values()) {
                {
                    MeshBuilder meshBuilder = renderer.meshBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    emitter.square(dir, 0, 0.5f, 1, 1, 0);
                    emitter.spriteBake(textureGetter.apply(SPRITE_IDS[patternIndex]), MutableQuadView.BAKE_LOCK_UV);
                    emitter.color(-1, -1, -1, -1);
                    emitter.emit();

                    positiveFaceMeshes.put(dir, meshBuilder.build());
                }
                {
                    MeshBuilder meshBuilder = renderer.meshBuilder();
                    QuadEmitter emitter = meshBuilder.getEmitter();

                    emitter.square(dir, 0, 0, 1, 0.5f, 0);
                    emitter.spriteBake(textureGetter.apply(SPRITE_IDS[patternIndex]), MutableQuadView.BAKE_LOCK_UV);
                    emitter.color(-1, -1, -1, -1);
                    emitter.emit();

                    negativeFaceMeshes.put(dir, meshBuilder.build());
                }
            }

            positiveMeshMap.put(patternIndex, positiveFaceMeshes);
            negativeMeshMap.put(patternIndex, negativeFaceMeshes);
        }


        if (this.positiveId != null) {
            UnbakedModel positiveUnbakedModel = baker.getOrLoadModel(this.positiveId);
            this.positiveBakedModel = positiveUnbakedModel.bake(baker, textureGetter, rotationContainer);
        }

        if (this.negativeId != null) {
            UnbakedModel negativeUnbakedModel = baker.getOrLoadModel(this.negativeId);
            this.negativeBakedModel = negativeUnbakedModel.bake(baker, textureGetter, rotationContainer);
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

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {
        neighborComparison(blockRenderView, blockPos);

        if (this.positiveId != null) {
            for (Direction face : Direction.values()) {
                if (this.positiveSlab == null || shouldCullPositive(face)) {
                    continue;
                }

                EnumMap<Direction, Mesh> faceMeshes = positiveMeshMap.get(determinePatternPositive(blockRenderView, blockPos, blockState, face));
                if (faceMeshes == null) return;

                Mesh mesh = faceMeshes.get(face);
                if (mesh != null) {
                    mesh.outputTo(renderContext.getEmitter());
                }
            }
            /*renderContext.pushTransform(quad -> {
                Direction face = quad.cullFace();

                return this.positiveSlab == null || face != null && !shouldCullPositive(face);
            });
            //positiveBakedModel.emitBlockQuads(blockRenderView, blockState, blockPos, supplier, renderContext);
            renderContext.popTransform();*/
        }

        if (this.negativeId != null) {
            for (Direction face : Direction.values()) {
                if (this.negativeSlab == null || shouldCullNegative(face)) {
                    continue;
                }

                EnumMap<Direction, Mesh> faceMeshes = negativeMeshMap.get(determinePatternNegative(blockRenderView, blockPos, blockState, face));
                if (faceMeshes == null) return;

                Mesh mesh = faceMeshes.get(face);
                if (mesh != null) {
                    mesh.outputTo(renderContext.getEmitter());
                }
            }
            /*renderContext.pushTransform(quad -> {
                Direction face = quad.cullFace();

                return this.negativeSlab == null || face != null && !shouldCullNegative(face);
            });
            //negativeBakedModel.emitBlockQuads(blockRenderView, blockState, blockPos, supplier, renderContext);
            renderContext.popTransform();*/
        }
    }

    private boolean connectUL;
    private boolean connectUR;
    private boolean connectR;
    private boolean connectDL;
    private boolean connectDR;
    private boolean connectL;
    private boolean connectRU;
    private boolean connectRD;
    private boolean connectLD;
    private boolean connectLU;
    private int determinePatternPositive(BlockRenderView world, BlockPos pos, BlockState state, Direction face) {
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

        BlockPos otherPos;

        if (face == Direction.UP) {
            //
        } else if (face == Direction.DOWN) {
            //
        } else if (face == Direction.EAST) {
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
                otherPos = pos.up().north();

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
        } else {
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

        return determinePatternIndex();
    }

    private int determinePatternNegative(BlockRenderView world, BlockPos pos, BlockState state, Direction face) {
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

        if (face == Direction.UP) {
            //
        } else if (face == Direction.DOWN) {
            //
        } else if (face == Direction.EAST) {
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
        } else {
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

        return determinePatternIndex();
    }

    private int determinePatternIndex() {
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

        if (patternIndex >= 64) {
            //patternIndex = INDEX_MAP.getOrDefault(patternIndex, 0);
            patternIndex = 0;
        }

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
        horizontalNeighborComparison(world, pos, positiveSlab);

        if (isSameSlab) {
            eastBlockBottom = eastBlockTop;
            southBlockBottom = southBlockTop;
            westBlockBottom = westBlockTop;
            northBlockBottom = northBlockTop;
        } else {
            horizontalNeighborComparison(world, pos, negativeSlab);
        }
    }

    private void horizontalNeighborComparison(BlockRenderView world, BlockPos pos, Block block) {
        BlockPos otherPos;
        BlockState otherState;
        Block otherBlock;

        // EAST
        otherPos = pos.east();
        otherState = world.getBlockState(otherPos);
        otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (block == otherBlock) {
                eastBlockTop = otherState.get(SlabBlock.TYPE) == SlabType.TOP ? POSITIVE1 : NEGATIVE1;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            boolean bl1 = block == entity.getPositiveSlabState().getBlock();
            boolean bl2 = block == entity.getNegativeSlabState().getBlock();
            if (bl1 && bl2) {
                eastBlockTop = FULL;
            } else if (bl1) {
                eastBlockTop = POSITIVE1;
            } else if (bl2) {
                eastBlockTop = NEGATIVE1;
            }
        } else if (otherBlock instanceof VerticalSlabBlock) {
            if (block == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                if (d == Direction.SOUTH) {
                    eastBlockTop = POSITIVE2;
                } else if (d == Direction.WEST) {
                    eastBlockTop = FULL;
                } else if (d == Direction.NORTH) {
                    eastBlockTop = NEGATIVE2;
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            if (entity.isX()) {
                if (block == ModBlockMap.verticalSlabToSlab(entity.getNegativeSlabState().getBlock())) {
                    eastBlockTop = FULL;
                }
            } else {
                Block b = ModBlockMap.slabToVerticalSlab(block);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();
                if (bl1 && bl2) {
                    eastBlockTop = FULL;
                } else if (bl1) {
                    eastBlockTop = POSITIVE2;
                } else if (bl2) {
                    eastBlockTop = NEGATIVE2;
                }
            }
        } else {
            if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                this.eastBlockTop = FULL;
            }
        }

        // SOUTH
        otherPos = pos.south();
        otherState = world.getBlockState(otherPos);
        otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (block == otherBlock) {
                southBlockTop = otherState.get(SlabBlock.TYPE) == SlabType.TOP ? POSITIVE1 : NEGATIVE1;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            boolean bl1 = block == entity.getPositiveSlabState().getBlock();
            boolean bl2 = block == entity.getNegativeSlabState().getBlock();
            if (bl1 && bl2) {
                southBlockTop = FULL;
            } else if (bl1) {
                southBlockTop = POSITIVE1;
            } else if (bl2) {
                southBlockTop = NEGATIVE1;
            }
        } else if (otherBlock instanceof VerticalSlabBlock) {
            if (block == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                if (d == Direction.EAST) {
                    southBlockTop = POSITIVE2;
                } else if (d == Direction.NORTH) {
                    southBlockTop = FULL;
                } else if (d == Direction.WEST) {
                    southBlockTop = NEGATIVE2;
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            if (!entity.isX()) {
                if (block == ModBlockMap.verticalSlabToSlab(entity.getNegativeSlabState().getBlock())) {
                    southBlockTop = FULL;
                }
            } else {
                Block b = ModBlockMap.slabToVerticalSlab(block);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();
                if (bl1 && bl2) {
                    southBlockTop = FULL;
                } else if (bl1) {
                    southBlockTop = POSITIVE2;
                } else if (bl2) {
                    southBlockTop = NEGATIVE2;
                }
            }
        } else {
            if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                this.southBlockTop = FULL;
            }
        }

        // WEST
        otherPos = pos.west();
        otherState = world.getBlockState(otherPos);
        otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (block == otherBlock) {
                westBlockTop = otherState.get(SlabBlock.TYPE) == SlabType.TOP ? POSITIVE1 : NEGATIVE1;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            boolean bl1 = block == entity.getPositiveSlabState().getBlock();
            boolean bl2 = block == entity.getNegativeSlabState().getBlock();
            if (bl1 && bl2) {
                westBlockTop = FULL;
            } else if (bl1) {
                westBlockTop = POSITIVE1;
            } else if (bl2) {
                westBlockTop = NEGATIVE1;
            }
        } else if (otherBlock instanceof VerticalSlabBlock) {
            if (block == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                if (d == Direction.SOUTH) {
                    westBlockTop = POSITIVE2;
                } else if (d == Direction.EAST) {
                    westBlockTop = FULL;
                } else if (d == Direction.NORTH) {
                    westBlockTop = NEGATIVE2;
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            if (entity.isX()) {
                if (block == ModBlockMap.verticalSlabToSlab(entity.getPositiveSlabState().getBlock())) {
                    westBlockTop = FULL;
                }
            } else {
                Block b = ModBlockMap.slabToVerticalSlab(block);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();
                if (bl1 && bl2) {
                    westBlockTop = FULL;
                } else if (bl1) {
                    westBlockTop = POSITIVE2;
                } else if (bl2) {
                    westBlockTop = NEGATIVE2;
                }
            }
        } else {
            if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                this.westBlockTop = FULL;
            }
        }

        // NORTH
        otherPos = pos.north();
        otherState = world.getBlockState(otherPos);
        otherBlock = otherState.getBlock();
        if (otherBlock instanceof SlabBlock) {
            if (block == otherBlock) {
                northBlockTop = otherState.get(SlabBlock.TYPE) == SlabType.TOP ? POSITIVE1 : NEGATIVE1;
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
            boolean bl1 = block == entity.getPositiveSlabState().getBlock();
            boolean bl2 = block == entity.getNegativeSlabState().getBlock();
            if (bl1 && bl2) {
                northBlockTop = FULL;
            } else if (bl1) {
                northBlockTop = POSITIVE1;
            } else if (bl2) {
                northBlockTop = NEGATIVE1;
            }
        } else if (otherBlock instanceof VerticalSlabBlock) {
            if (block == ModBlockMap.verticalSlabToSlab(otherBlock)) {
                Direction d = otherState.get(VerticalSlabBlock.FACING);
                if (d == Direction.EAST) {
                    northBlockTop = POSITIVE2;
                } else if (d == Direction.SOUTH) {
                    northBlockTop = FULL;
                } else if (d == Direction.WEST) {
                    northBlockTop = NEGATIVE2;
                }
            }
        } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
            if (!entity.isX()) {
                if (block == ModBlockMap.verticalSlabToSlab(entity.getPositiveSlabState().getBlock())) {
                    northBlockTop = FULL;
                }
            } else {
                Block b = ModBlockMap.slabToVerticalSlab(block);
                boolean bl1 = b == entity.getPositiveSlabState().getBlock();
                boolean bl2 = b == entity.getNegativeSlabState().getBlock();
                if (bl1 && bl2) {
                    northBlockTop = FULL;
                } else if (bl1) {
                    northBlockTop = POSITIVE2;
                } else if (bl2) {
                    northBlockTop = NEGATIVE2;
                }
            }
        } else {
            if (ModBlockMap.slabToOriginal(positiveSlab) == otherBlock) {
                this.northBlockTop = FULL;
            }
        }
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
