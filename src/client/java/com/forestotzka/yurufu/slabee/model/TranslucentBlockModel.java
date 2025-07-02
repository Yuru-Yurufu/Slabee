package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.block.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class TranslucentBlockModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private final Identifier id;
    private final Block block;
    private BakedModel bakedModel;
    protected BakedModel nullBakedModel;

    public TranslucentBlockModel(Block block) {
        Map<Block, Block> removeWaxMap = Map.of(
                Blocks.WAXED_COPPER_GRATE, Blocks.COPPER_GRATE,
                Blocks.WAXED_EXPOSED_COPPER_GRATE, Blocks.EXPOSED_COPPER_GRATE,
                Blocks.WAXED_OXIDIZED_COPPER_GRATE, Blocks.OXIDIZED_COPPER_GRATE,
                Blocks.WAXED_WEATHERED_COPPER_GRATE, Blocks.WEATHERED_COPPER_GRATE
        );

        this.block = removeWaxMap.getOrDefault(block, block);

        Identifier id = Registries.BLOCK.getId(this.block);

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
