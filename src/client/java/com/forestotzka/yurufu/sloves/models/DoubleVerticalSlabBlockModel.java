package com.forestotzka.yurufu.sloves.models;

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
    String axis = "x";
    private Mesh meshPositive;
    private Mesh meshNegative;
    private static final Matrix4f ROTATION_Z_POSITIVE = new Matrix4f().identity().rotateY((float) Math.toRadians(90));
    private static final Matrix4f ROTATION_Z_NEGATIVE = new Matrix4f().identity().rotateY((float) Math.toRadians(270));
    private static final Matrix4f ROTATION_X_POSITIVE = new Matrix4f().identity().rotateY((float) Math.toRadians(180));
    private static final Matrix4f ROTATION_X_NEGATIVE = new Matrix4f().identity().rotateY((float) Math.toRadians(0));
    private boolean positiveSlabUVLock = true;
    private boolean negativeSlabUVLock = true;

    public DoubleVerticalSlabBlockModel(String positiveSlab, String negativeSlab, String axis) {
        this.positiveSlabPath = positiveSlab.replace("__",":block/");
        this.negativeSlabPath = negativeSlab.replace("__",":block/");
        this.axis = axis;
        if (positiveSlab.equals("sloves__smooth_stone_vertical_slab")) {
            positiveSlabUVLock = false;
            this.positiveSlabPath = this.positiveSlabPath + "_positive";
        }
        if (negativeSlab.equals("sloves__smooth_stone_vertical_slab")) {
            negativeSlabUVLock = false;
            this.negativeSlabPath = this.negativeSlabPath + "_negative";
        }
    }

    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        positiveSlabPath = positiveSlabPath.replace("minecraft:block/waxed_","minecraft:block/");
        negativeSlabPath = negativeSlabPath.replace("minecraft:block/waxed_","minecraft:block/");
        UnbakedModel unbakedModelPositive = baker.getOrLoadModel(Identifier.of(positiveSlabPath));
        UnbakedModel unbakedModelNegative = baker.getOrLoadModel(Identifier.of(negativeSlabPath));
        BakedModel bakedModelPositive = unbakedModelPositive.bake(baker, textureGetter, rotationContainer);
        BakedModel bakedModelNegative = unbakedModelNegative.bake(baker, textureGetter, rotationContainer);
        if (positiveSlabUVLock) {
            Matrix4f rotationMatPositive = axis.equals("z") ? ROTATION_Z_POSITIVE : ROTATION_X_POSITIVE;
            this.meshPositive = applyRotation(bakedModelPositive, rotationMatPositive, axis.equals("z") ? 3 : 2, true);
        } else {
            Matrix4f rotationMatPositive = axis.equals("z") ? ROTATION_Z_NEGATIVE : ROTATION_X_NEGATIVE;
            this.meshPositive = applyRotation(bakedModelPositive, rotationMatPositive, axis.equals("z") ? 1 : 0, false);
        }
        Matrix4f rotationMatNegative = axis.equals("z") ? ROTATION_Z_NEGATIVE: ROTATION_X_NEGATIVE;
        this.meshNegative = applyRotation(bakedModelNegative, rotationMatNegative, axis.equals("z") ? 1: 0, negativeSlabUVLock);

        this.bakedModelPositive = bakedModelPositive;
        //this.bakedModelNegative = bakedModelNegative;

        return this;
    }

    private Mesh applyRotation(BakedModel model, Matrix4f rotationMat, int rotationCount, boolean uvlock) {
        MeshBuilder meshBuilder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
        QuadEmitter emitter = meshBuilder.getEmitter();
        Matrix4f translationMat = new Matrix4f().identity()
                .translate(0.5f, 0.5f, 0.5f)
                .mul(rotationMat)
                .translate(-0.5f, -0.5f, -0.5f);

        // モデルの各クアッドに回転行列を適用
        for (Direction direction : Direction.values()) {
            for (BakedQuad quad : model.getQuads(null, direction, null)) {
                emitter.fromVanilla(quad, null, null);

                // 頂点に対して回転行列を適用
                for (int i = 0; i < 4; i++) {
                    Vector3f position = new Vector3f(emitter.x(i), emitter.y(i), emitter.z(i));
                    translationMat.transformPosition(position);
                    emitter.pos(i, position);

                    Vector3f normal = new Vector3f(emitter.normalX(i), emitter.normalY(i), emitter.normalZ(i));
                    rotationMat.transformDirection(normal);
                    emitter.normal(i, normal.x(), normal.y(), normal.z());

                    emitter.sprite(i,0,emitter.u(i),emitter.v(i));
                    if (uvlock && (direction == Direction.DOWN || direction == Direction.UP)) {
                        emitter.spriteBake(quad.getSprite(), MutableQuadView.BAKE_LOCK_UV);
                    }
                }

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
        return meshBuilder.build();
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {
        if (meshPositive != null) {
            meshPositive.outputTo(renderContext.getEmitter());
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
