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
public class DoubleSlabBlockModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private final ResourceLocation positiveId;
    private final ResourceLocation negativeId;
    private final Block positiveSlab;
    private final Block negativeSlab;
    private BakedModel positiveBakedModel;
    private BakedModel negativeBakedModel;
    protected BakedModel nullBakedModel;

    public DoubleSlabBlockModel(@Nullable Block positiveSlab, @Nullable Block negativeSlab) {
        this.positiveSlab = positiveSlab;
        this.negativeSlab = negativeSlab;

        if (this.positiveSlab == null) {
            this.positiveId = null;
        } else {
            ResourceLocation positiveId = BuiltInRegistries.BLOCK.getKey(this.positiveSlab);
            this.positiveId = ResourceLocation.fromNamespaceAndPath(positiveId.getNamespace(), "block/" + positiveId.getPath() + "_top");
        }

        if (this.negativeSlab == null) {
            this.negativeId = null;
        } else {
            ResourceLocation negativeId = BuiltInRegistries.BLOCK.getKey(this.negativeSlab);
            this.negativeId = ResourceLocation.fromNamespaceAndPath(negativeId.getNamespace(), "block/" + negativeId.getPath());
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
        if (positiveId != null) {
            return positiveBakedModel.getParticleIcon();
        } else if (negativeId != null) {
            return negativeBakedModel.getParticleIcon();
        } else {
            return nullBakedModel.getParticleIcon();
        }
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
        if (this.positiveId != null) {
            UnbakedModel positiveUnbakedModel = baker.getModel(this.positiveId);
            this.positiveBakedModel = positiveUnbakedModel.bake(baker, textureGetter, rotationContainer);
        }

        if (this.negativeId != null) {
            UnbakedModel negativeUnbakedModel = baker.getModel(this.negativeId);
            this.negativeBakedModel = negativeUnbakedModel.bake(baker, textureGetter, rotationContainer);
        } else if (this.positiveId == null) {
            this.nullBakedModel = baker.getModel(ResourceLocation.parse("minecraft:block/stone")).bake(baker, textureGetter, rotationContainer);
        }

        return this;
    }

    @Override
    public void emitBlockQuads(BlockAndTintGetter blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<RandomSource> supplier, RenderContext renderContext) {
        if (this.positiveId != null) {
            renderContext.pushTransform(quad -> {
                Direction face = quad.cullFace();

                return this.positiveSlab == null || face != null && !shouldCullPositive(face, blockRenderView, blockPos);
            });
            positiveBakedModel.emitBlockQuads(blockRenderView, blockState, blockPos, supplier, renderContext);
            renderContext.popTransform();
        }

        if (this.negativeId != null) {
            renderContext.pushTransform(quad -> {
                Direction face = quad.cullFace();

                return this.negativeSlab == null || face != null && !shouldCullNegative(face, blockRenderView, blockPos);
            });
            negativeBakedModel.emitBlockQuads(blockRenderView, blockState, blockPos, supplier, renderContext);
            renderContext.popTransform();
        }
    }

    private boolean shouldCullPositive(Direction face, BlockAndTintGetter world, BlockPos pos) {
        BlockPos otherPos;
        BlockState otherState;
        Block otherBlock;
        if (face == Direction.UP) {
            otherPos = pos.above();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                return positiveSlab == otherBlock && otherState.getValue(SlabBlock.TYPE) == SlabType.BOTTOM;
            } else if (otherState.is(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                return positiveSlab == entity.getNegativeSlabState().getBlock();
            } else if (otherState.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
            }
        } else if (face == Direction.DOWN) {
            return positiveSlab == negativeSlab;
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

            if (otherBlock instanceof SlabBlock) {
                return positiveSlab == otherBlock && otherState.getValue(SlabBlock.TYPE) == SlabType.TOP;
            } else if (otherState.is(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                return positiveSlab == entity.getPositiveSlabState().getBlock();
            } else if (otherBlock instanceof VerticalSlabBlock) {
                return positiveSlab == ModBlockMap.verticalSlabToSlab(otherBlock) && otherState.getValue(VerticalSlabBlock.FACING) == face.getOpposite();
            } else if (otherState.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                return shouldCullDVSNeighbor(positiveSlab, entity, face);
            }
        }

        return ModBlockMap.slabToOriginal(positiveSlab) == otherBlock;
    }

    private boolean shouldCullNegative(Direction face, BlockAndTintGetter world, BlockPos pos) {
        BlockPos otherPos;
        BlockState otherState;
        Block otherBlock;
        if (face == Direction.UP) {
            return positiveSlab == negativeSlab;
        } else if (face == Direction.DOWN) {
            otherPos = pos.below();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                return negativeSlab == otherBlock && otherState.getValue(SlabBlock.TYPE) == SlabType.TOP;
            } else if (otherState.is(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                return negativeSlab == entity.getPositiveSlabState().getBlock();
            } else if (otherState.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(negativeSlab);
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

            if (otherBlock instanceof SlabBlock) {
                return negativeSlab == otherBlock && otherState.getValue(SlabBlock.TYPE) == SlabType.BOTTOM;
            } else if (otherState.is(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                return negativeSlab == entity.getNegativeSlabState().getBlock();
            } else if (otherBlock instanceof VerticalSlabBlock) {
                return negativeSlab == ModBlockMap.verticalSlabToSlab(otherBlock) && otherState.getValue(VerticalSlabBlock.FACING) == face.getOpposite();
            } else if (otherState.is(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                return shouldCullDVSNeighbor(negativeSlab, entity, face);
            }
        }

        return ModBlockMap.slabToOriginal(negativeSlab) == otherBlock;
    }

    private boolean shouldCullDVSNeighbor(Block block, DoubleVerticalSlabBlockEntity entity, Direction face) {
        if (entity.isX()) {
            if (face == Direction.EAST) {
                return block == ModBlockMap.verticalSlabToSlab(entity.getNegativeSlabState().getBlock());
            } else if (face == Direction.WEST) {
                return block == ModBlockMap.verticalSlabToSlab(entity.getPositiveSlabState().getBlock());
            } else {
                Block b = ModBlockMap.slabToVerticalSlab(block);
                return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
            }
        } else {
            if (face == Direction.SOUTH) {
                return block == ModBlockMap.verticalSlabToSlab(entity.getNegativeSlabState().getBlock());
            } else if (face == Direction.NORTH) {
                return block == ModBlockMap.verticalSlabToSlab(entity.getPositiveSlabState().getBlock());
            } else {
                Block b = ModBlockMap.slabToVerticalSlab(block);
                return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
            }
        }
    }
}