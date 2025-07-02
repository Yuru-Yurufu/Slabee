package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.block.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
public class TranslucentSlabBlockModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private final Identifier id;
    private final Block block;
    private final boolean isTop;
    private BakedModel bakedModel;

    public TranslucentSlabBlockModel(Block block, boolean isTop) {
        this.block = block;
        this.isTop = isTop;

        Identifier id = Registries.BLOCK.getId(block);

        if (isTop) {
            this.id = Identifier.of(id.getNamespace(), "block/" + id.getPath() + "_top");
        } else {
            this.id = Identifier.of(id.getNamespace(), "block/" + id.getPath());
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
        return bakedModel.getParticleSprite();
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
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        UnbakedModel unbakedModel = baker.getOrLoadModel(this.id);
        this.bakedModel = unbakedModel.bake(baker, textureGetter, rotationContainer);

        return this;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {
        renderContext.pushTransform(quad -> {
            Direction face = quad.cullFace();

            return face != null && !shouldCull(face, blockRenderView, blockPos);
        });
        bakedModel.emitBlockQuads(blockRenderView, blockState, blockPos, supplier, renderContext);
        renderContext.popTransform();
    }

    private boolean shouldCull(Direction face, BlockRenderView world, BlockPos pos) {
        BlockPos otherPos;
        BlockState otherState;
        Block otherBlock;

        if (face == Direction.UP) {
            if (isTop) {
                otherPos = pos.up();
                otherState = world.getBlockState(otherPos);
                otherBlock = otherState.getBlock();
                if (otherBlock instanceof SlabBlock) {
                    return block == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM;
                } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                    return block == entity.getNegativeSlabState().getBlock();
                } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                    Block b = ModBlockMap.slabToVerticalSlab(block);
                    return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
                }
            } else {
                return false;
            }
        } else if (face == Direction.DOWN) {
            if (!isTop) {
                otherPos = pos.down();
                otherState = world.getBlockState(otherPos);
                otherBlock = otherState.getBlock();
                if (otherBlock instanceof SlabBlock) {
                    return block == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP;
                } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                    return block == entity.getPositiveSlabState().getBlock();
                } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
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
                    return otherState.get(SlabBlock.TYPE) == SlabType.TOP;
                } else {
                    return otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM;
                }
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                return block == entity.getPositiveSlabState().getBlock();
            } else if (otherBlock instanceof VerticalSlabBlock) {
                return block == ModBlockMap.verticalSlabToSlab(otherBlock) && otherState.get(VerticalSlabBlock.FACING) == face.getOpposite();
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
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
