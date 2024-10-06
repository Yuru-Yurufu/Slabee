package com.forestotzka.yurufu.sloves.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class DoubleSlabBlockModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private BakedModel bakedModelPositive;
    private BakedModel bakedModelNegative;
    private String topSlabPath = "";
    private String bottomSlabPath = "";

    public DoubleSlabBlockModel(String topSlab, String bottomSlab) {
        this.topSlabPath = topSlab.replace("__",":block/");
        this.bottomSlabPath = bottomSlab.replace("__",":block/");
    }

    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        topSlabPath = topSlabPath.replace("minecraft:block/waxed_","minecraft:block/");
        bottomSlabPath = bottomSlabPath.replace("minecraft:block/waxed_","minecraft:block/");
        UnbakedModel unbakedModelPositive = baker.getOrLoadModel(Identifier.of(topSlabPath + "_top"));
        UnbakedModel unbakedModelNegative = baker.getOrLoadModel(Identifier.of(bottomSlabPath));
        BakedModel bakedModelPositive = unbakedModelPositive.bake(baker, textureGetter, rotationContainer);
        BakedModel bakedModelNegative = unbakedModelNegative.bake(baker, textureGetter, rotationContainer);
        this.bakedModelPositive = bakedModelPositive;
        this.bakedModelNegative = bakedModelNegative;
        return this;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {
        bakedModelPositive.emitBlockQuads(blockRenderView, blockState, blockPos, supplier, renderContext);
        bakedModelNegative.emitBlockQuads(blockRenderView, blockState, blockPos, supplier, renderContext);
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
