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
public class TranslucentSlabBlockModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private final ResourceLocation id;
    private final Block block;
    private final boolean isTop;
    private BakedModel bakedModel;

    public TranslucentSlabBlockModel(Block block, boolean isTop) {
        this.block = block;
        this.isTop = isTop;

        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);

        if (isTop) {
            this.id = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "block/" + id.getPath() + "_top");
        } else {
            this.id = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "block/" + id.getPath());
        }
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
        return bakedModel.getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms() {
        return null;
    }

    @Override
    public ItemOverrides getOverrides() {
        return null;
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
            if (isTop) {
                otherPos = pos.above();
                otherState = world.getBlockState(otherPos);
                otherBlock = otherState.getBlock();
                if (otherBlock instanceof SlabBlock) {
                    return block == otherBlock && otherState.getValue(SlabBlock.TYPE) == SlabType.BOTTOM;
                } else if (otherState.is(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                    return block == entity.getNegativeSlabState().getBlock();
                } else if (otherState.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                    Block b = ModBlockMap.slabToVerticalSlab(block);
                    return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
                }
            } else {
                return false;
            }
        } else if (face == Direction.DOWN) {
            if (!isTop) {
                otherPos = pos.below();
                otherState = world.getBlockState(otherPos);
                otherBlock = otherState.getBlock();
                if (otherBlock instanceof SlabBlock) {
                    return block == otherBlock && otherState.getValue(SlabBlock.TYPE) == SlabType.TOP;
                } else if (otherState.is(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                    return block == entity.getPositiveSlabState().getBlock();
                } else if (otherState.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                    Block b = ModBlockMap.slabToVerticalSlab(block);
                    return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
                }
            } else {
                return false;
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

            if (otherBlock instanceof SlabBlock && block == otherBlock) {
                if (isTop) {
                    return otherState.getValue(SlabBlock.TYPE) == SlabType.TOP;
                } else {
                    return otherState.getValue(SlabBlock.TYPE) == SlabType.BOTTOM;
                }
            } else if (otherState.is(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                return block == entity.getPositiveSlabState().getBlock();
            } else if (otherBlock instanceof VerticalSlabBlock) {
                return block == ModBlockMap.verticalSlabToSlab(otherBlock) && otherState.getValue(VerticalSlabBlock.FACING) == face.getOpposite();
            } else if (otherState.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                if (face == Direction.SOUTH || face == Direction.EAST) {
                    return block == ModBlockMap.verticalSlabToSlab(entity.getNegativeSlabState().getBlock());
                } else {
                    return block == ModBlockMap.verticalSlabToSlab(entity.getPositiveSlabState().getBlock());
                }
            }
        }

        return ModBlockMap.slabToOriginal(block) == otherBlock;
    }
}
