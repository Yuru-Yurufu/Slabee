package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.block.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
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
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class TranslucentBlockModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private final Identifier id;
    private final Block block;
    private BakedModel bakedModel;
    protected BakedModel nullBakedModel;

    public static final SpriteIdentifier GLASS_SLAB_ATLAS =
            new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE,
                    Identifier.of("slabee", "block/glass_slab_"));

    public TranslucentBlockModel(Block block) {
        this.block = block;

        Identifier id = Registries.BLOCK.getId(block);

        this.id = Identifier.of(id.getNamespace(), "block/" + id.getPath());
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
        if (bakedModel != null) {
            return bakedModel.getParticleSprite();
        } else {
            return nullBakedModel.getParticleSprite();
        }
    }

    @Override
    public ModelTransformation getTransformation() {
        return bakedModel != null ? bakedModel.getTransformation() : ModelTransformation.NONE;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return bakedModel != null ? bakedModel.getOverrides() : ModelOverrideList.EMPTY;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return List.of();
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }

    private Sprite atlas;
    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        /*UnbakedModel unbakedModel = baker.getOrLoadModel(this.id);
        this.bakedModel = unbakedModel.bake(baker, textureGetter, rotationContainer);

        return this;*/
        this.atlas = textureGetter.apply(GLASS_SLAB_ATLAS); // ← ここでキャッシュ！
        return this;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {
        /*renderContext.pushTransform(quad -> {
            Direction face = quad.cullFace();

            return face != null && !shouldCull(face, blockRenderView, blockPos);
        });
        bakedModel.emitBlockQuads(blockRenderView, blockState, blockPos, supplier, renderContext);
        renderContext.popTransform();*/

        Sprite sprite = this.atlas; // bake() で取得しておいたスプライト
        System.out.println("sprite" + sprite.getMaxU() + ", " + sprite.getMinU() + ", " + sprite.getX());
        System.out.println("U: " + sprite.getMinU() + " - " + sprite.getMaxU());
        System.out.println("V: " + sprite.getMinV() + " - " + sprite.getMaxV());
        System.out.println("X: " + sprite.getX() + ", Y: " + sprite.getY());
        System.out.println("Sprite Width: " + sprite.getContents().getWidth());

        int frameIndex = 5;
        int cols = 8;
        int x = frameIndex % cols;
        int y = frameIndex / cols;
        int z = 256 / cols;

        float u0 = sprite.getFrameU(x * z);
        float v0 = sprite.getFrameV(y * z);
        float u1 = sprite.getFrameU((x + 1) * z);
        float v1 = sprite.getFrameV((y + 1) * z);

        QuadEmitter emitter = renderContext.getEmitter();

        emitter.square(Direction.UP, 0, 0, 1, 1, 0);

// 頂点順にUVを直接指定（spriteIndex = 0）
        emitter.uv(0, u0, v0); // 左下
        emitter.uv(1, u0, v1); // 右下
        emitter.uv(2, u1, v1); // 右上
        emitter.uv(3, u1, v0); // 左上

// アトラスのスプライトに対応するようベイク
        emitter.spriteBake(sprite, 0); // ← これが公式のやり方！

// 色・マテリアルなども設定
        emitter.color(-1, -1, -1, -1);
        emitter.emit();

    }

    private boolean shouldCull(Direction face, BlockRenderView world, BlockPos pos) {
        BlockPos otherPos;
        BlockState otherState;
        Block otherBlock;

        if (face == Direction.UP) {
            otherPos = pos.up();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                return block == ModBlockMap.slabToOriginal(otherBlock) && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM;
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                return block == ModBlockMap.slabToOriginal(entity.getNegativeSlabState().getBlock());
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(block);
                return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
            }
        } else if (face == Direction.DOWN) {
            otherPos = pos.down();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                return block == ModBlockMap.slabToOriginal(otherBlock) && otherState.get(SlabBlock.TYPE) == SlabType.TOP;
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                return block == ModBlockMap.slabToOriginal(entity.getPositiveSlabState().getBlock());
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(block);
                return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
            }
        } else {
            if (face == Direction.EAST) {
                otherPos = pos.east();
            } else if (face == Direction.SOUTH) {
                otherPos = pos.south();
            } else if (face == Direction.WEST) {
                otherPos = pos.west();
            } else {
                otherPos = pos.north();
            }
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();

            if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                Block b = ModBlockMap.originalToSlab(block);
                return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
            } else if (otherBlock instanceof VerticalSlabBlock) {
                return block == ModBlockMap.verticalSlabToOriginal(otherBlock) && otherState.get(VerticalSlabBlock.FACING) == face.getOpposite();
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                if (entity.isX()) {
                    if (face == Direction.EAST) {
                        return block == ModBlockMap.verticalSlabToOriginal(entity.getNegativeSlabState().getBlock());
                    } else if (face == Direction.WEST) {
                        return block == ModBlockMap.verticalSlabToOriginal(entity.getPositiveSlabState().getBlock());
                    } else {
                        Block b = ModBlockMap.originalToVerticalSlab(block);
                        return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
                    }
                } else {
                    if (face == Direction.SOUTH) {
                        return block == ModBlockMap.verticalSlabToOriginal(entity.getNegativeSlabState().getBlock());
                    } else if (face == Direction.NORTH) {
                        return block == ModBlockMap.verticalSlabToOriginal(entity.getPositiveSlabState().getBlock());
                    } else {
                        Block b = ModBlockMap.originalToVerticalSlab(block);
                        return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
                    }
                }
            }
        }

        return ModBlockMap.slabToOriginal(block) == otherBlock;
    }
}
