package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.block.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class TranslucentBlockModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private final ResourceLocation id;
    private final Block block;
    private BakedModel bakedModel;
    protected BakedModel nullBakedModel;

    public TranslucentBlockModel(Block block) {
        this.block = block;

        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(this.block);

        this.id = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "block/" + id.getPath());
    }
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, RandomSource random) {
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        if (bakedModel != null) {
            return bakedModel.getParticleIcon();
        } else {
            return nullBakedModel.getParticleIcon();
        }
    }

    @Override
    public ItemTransforms getTransforms() {
        return bakedModel != null ? bakedModel.getTransforms() : ItemTransforms.NO_TRANSFORMS;
    }

    @Override
    public ItemOverrides getOverrides() {
        return bakedModel != null ? bakedModel.getOverrides() : ItemOverrides.EMPTY;
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return List.of();
    }

    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> modelLoader) {

    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public @Nullable BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> textureGetter, ModelState rotationContainer) {
        UnbakedModel unbakedModel = baker.getModel(this.id);
        this.bakedModel = unbakedModel.bake(baker, textureGetter, rotationContainer);

        return this;
    }

    @Override
    public void emitBlockQuads(BlockAndTintGetter blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<RandomSource> supplier, RenderContext renderContext) {
        renderContext.pushTransform(quad -> {
            Direction face = quad.cullFace();

            return face != null && !shouldCull(face, blockRenderView, blockPos);
        });
        bakedModel.emitBlockQuads(blockRenderView, blockState, blockPos, supplier, renderContext);
        renderContext.popTransform();
    }

    private boolean shouldCull(Direction face, BlockAndTintGetter world, BlockPos pos) {
        BlockPos otherPos;
        BlockState otherState;
        Block otherBlock;

        if (face == Direction.UP) {
            otherPos = pos.above();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                return block == ModBlockMap.slabToOriginal(otherBlock) && otherState.getValue(SlabBlock.TYPE) == SlabType.BOTTOM;
            } else if (otherState.is(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                return block == ModBlockMap.slabToOriginal(entity.getNegativeSlabState().getBlock());
            } else if (otherState.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(block);
                return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
            }
        } else if (face == Direction.DOWN) {
            otherPos = pos.below();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                return block == ModBlockMap.slabToOriginal(otherBlock) && otherState.getValue(SlabBlock.TYPE) == SlabType.TOP;
            } else if (otherState.is(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                return block == ModBlockMap.slabToOriginal(entity.getPositiveSlabState().getBlock());
            } else if (otherState.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
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

            if (otherState.is(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                Block b = ModBlockMap.originalToSlab(block);
                return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
            } else if (otherBlock instanceof VerticalSlabBlock) {
                return block == ModBlockMap.verticalSlabToOriginal(otherBlock) && otherState.getValue(VerticalSlabBlock.FACING) == face.getOpposite();
            } else if (otherState.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
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
