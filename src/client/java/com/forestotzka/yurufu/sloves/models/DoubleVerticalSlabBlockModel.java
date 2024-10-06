package com.forestotzka.yurufu.sloves.models;

import com.forestotzka.yurufu.sloves.block.DoubleVerticalSlabBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.LightType;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class DoubleVerticalSlabBlockModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private BakedModel bakedModelPositive;
    private BakedModel bakedModelNegative;
    private String positiveSlabPath = "";
    private String negativeSlabPath = "";
    private boolean uvlock;
    String axis = "x";
    private Mesh meshPositive;
    private Mesh meshNegative;
    private static final Matrix4f ROTATION_Z_POSITIVE = new Matrix4f().identity().rotateY((float) Math.toRadians(90));
    private static final Matrix4f ROTATION_Z_NEGATIVE = new Matrix4f().identity().rotateY((float) Math.toRadians(270));
    private static final Matrix4f ROTATION_X_POSITIVE = new Matrix4f().identity().rotateY((float) Math.toRadians(180));
    private static final Matrix4f ROTATION_X_NEGATIVE = new Matrix4f().identity().rotateY((float) Math.toRadians(0));
    private final int max = 100;
    private int now = 0;

    public DoubleVerticalSlabBlockModel(String positiveSlab, String negativeSlab, boolean uvlock, String axis) {
        this.positiveSlabPath = positiveSlab.replace("__",":block/");
        this.negativeSlabPath = negativeSlab.replace("__",":block/");
        this.uvlock = uvlock;
        this.axis = axis;

    }

    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        positiveSlabPath = positiveSlabPath.replace("minecraft:block/waxed_","minecraft:block/");
        negativeSlabPath = negativeSlabPath.replace("minecraft:block/waxed_","minecraft:block/");
        UnbakedModel unbakedModelPositive = baker.getOrLoadModel(Identifier.of(positiveSlabPath));
        UnbakedModel unbakedModelNegative = baker.getOrLoadModel(Identifier.of(negativeSlabPath));
        BakedModel bakedModelPositive = unbakedModelPositive.bake(baker, textureGetter, rotationContainer);
        BakedModel bakedModelNegative = unbakedModelNegative.bake(baker, textureGetter, rotationContainer);
        Matrix4f rotationMatPositive = axis.equals("z") ? ROTATION_Z_POSITIVE: ROTATION_X_POSITIVE;
        Matrix4f rotationMatNegative = axis.equals("z") ? ROTATION_Z_NEGATIVE: ROTATION_X_NEGATIVE;

        Sprite sprite = textureGetter.apply(new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, Identifier.of("minecraft", "block/diamond_block")));

        this.meshPositive = applyRotation(bakedModelPositive, rotationMatPositive, sprite, axis.equals("z") ? 3: 2);
        this.meshNegative = applyRotation(bakedModelNegative, rotationMatNegative, sprite, axis.equals("z") ? 1: 0);

        this.bakedModelPositive = bakedModelPositive;
        //this.bakedModelNegative = bakedModelNegative;

        return this;
    }

    private Mesh applyRotation(BakedModel model, Matrix4f rotationMat, Sprite sprite, int rotationCount) {
        MeshBuilder meshBuilder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
        QuadEmitter emitter = meshBuilder.getEmitter();
        Matrix4f translationMat = new Matrix4f().identity()
                .translate(0.5f, 0.5f, 0.5f)
                .mul(rotationMat)
                .translate(-0.5f, -0.5f, -0.5f);

        // モデルの各クアッドに回転行列を適用
        for (Direction direction : Arrays.asList(Direction.values())) {
            for (BakedQuad quad : model.getQuads(null, direction, null)) {
                emitter.fromVanilla(quad, null, null);

                // 頂点に対して回転行列を適用
                for (int i = 0; i < 4; i++) {
                    Vector3f position = new Vector3f(emitter.x(i), emitter.y(i), emitter.z(i));
                    translationMat.transformPosition(position);
                    emitter.pos(i, position);

                    Vector3f normal = new Vector3f(emitter.normalX(i), emitter.normalY(i), emitter.normalZ(i));
                    rotationMat.transformDirection(normal);
                    //translationMat.transformPosition(normal);
                    emitter.normal(i, normal.x(), normal.y(), normal.z());
                    //emitter.normal(i, 0.0f, 0.0f, 1.0f);
                    //emitter.spriteBake(quad.getSprite(), MutableQuadView.BAKE_LOCK_UV);

                    emitter.sprite(i,0,emitter.u(i),emitter.v(i));
                    if (uvlock) {
                        if (direction == Direction.DOWN || direction == Direction.UP) {
                            emitter.spriteBake(quad.getSprite(), MutableQuadView.BAKE_LOCK_UV);
                            //emitter.spriteBake(sprite, MutableQuadView.BAKE_LOCK_UV);
                        }
                    }
                    /*if (uvlock) {
                        //emitter.spriteBake(i, quad.getSprite(), MutableQuadView.BAKE_LOCK_UV);
                        //emitter.sprite(i, 0, )
                        emitter.sprite(i,0,emitter.spriteU(i,0),emitter.spriteV(i,0));
                        if ()
                    } else {
                        emitter.sprite(i,0,emitter.spriteU(i,0),emitter.spriteV(i,0));
                    }*/
                    //emitter.lightmap(i, 0xF000F0);
                }
                //emitter.spriteBake(MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).apply(Identifier.of("minecraft", "block/stone")), 0);

                //emitter.cullFace(emitter.lightFace());
                //emitter.color(-1,-1,-1,-1);
                /*if (now < max) {
                    System.out.println(direction.toString() + emitter.nominalFace());
                    now++;
                }*/
                if (direction != Direction.DOWN && direction != Direction.UP) {
                    Direction correctFacing = emitter.nominalFace();
                    for (int i = 0; i < rotationCount; i++) {
                        correctFacing = correctFacing.rotateYClockwise();
                    }
                    emitter.nominalFace(correctFacing);
                }
                // クアッドをメッシュに追加
                emitter.emit();
            }
        }
        /*for (BakedQuad quad : model.getQuads(null, null, null)) {
            emitter.fromVanilla(quad,null,null);
            for (int i = 0; i < 4; i++) {
                Vector3f position = new Vector3f(emitter.x(i), emitter.y(i), emitter.z(i));
                translationMat.transformPosition(position);
                emitter.pos(i, position);

                if (uvlock) {
                    emitter.spriteBake(i, quad.getSprite(), MutableQuadView.BAKE_LOCK_UV);
                } else {
                    emitter.sprite(i,0,emitter.spriteU(i,0),emitter.spriteV(i,0));
                }
            }

            emitter.emit();
        }*/

        return meshBuilder.build();
    }

    //private Direction getCorrectDirection(Direction direction, )

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {
        //mesh.outputTo(renderContext.getEmitter());
        if (meshPositive != null) {
            /*renderContext.pushTransform(quad -> {
                for (int i = 0; i < 4; i++) {
                    int blockLight = blockRenderView.getLightLevel(LightType.BLOCK, blockPos);
                    int skyLight = blockRenderView.getLightLevel(LightType.SKY, blockPos);
                    quad.lightmap(i, blockLight | (skyLight << 4));
                    //quad.lightmap(i, 0xF000F0);
                }
                return true;
            });*/
            meshPositive.outputTo(renderContext.getEmitter());
            //renderContext.popTransform();
        }
        if (meshNegative != null) {
            meshNegative.outputTo(renderContext.getEmitter());
        }
    }

    @Override
    public Sprite getParticleSprite() {
        return bakedModelPositive.getParticleSprite();
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return List.of();
        //return bakedModelPositive.getQuads(state, face, random).stream().map(bakedQuad -> ro)
        /*List<BakedQuad> quads = new ArrayList<>();
        quads.addAll(bakedModelPositive.getQuads(state, face, random));
        quads.addAll(bakedModelNegative.getQuads(state, face, random));
        return quads;*/
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
    public void emitItemQuads(ItemStack itemStack, Supplier<Random> supplier, RenderContext renderContext) {
    }
}
