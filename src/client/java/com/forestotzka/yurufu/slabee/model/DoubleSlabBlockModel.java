package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.Slabee;
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
public class DoubleSlabBlockModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private final Identifier positiveId;
    private final Identifier negativeId;
    private final Block positiveSlab;
    private final Block negativeSlab;
    private BakedModel positiveBakedModel;
    private BakedModel negativeBakedModel;

    public DoubleSlabBlockModel(@Nullable Block positiveSlab, @Nullable Block negativeSlab) {
        this.positiveSlab = positiveSlab;
        this.negativeSlab = negativeSlab;

        if (this.positiveSlab != null) {
            Identifier positiveId = Registries.BLOCK.getId(positiveSlab);
            this.positiveId = Identifier.of(positiveId.getNamespace(), "block/" + positiveId.getPath() + "_top");
        } else {
            this.positiveId = Identifier.of(Slabee.MOD_ID, "block/normal_slab_top");
        }

        if (this.negativeSlab != null) {
            Identifier negativeId = Registries.BLOCK.getId(negativeSlab);
            this.negativeId = Identifier.of(negativeId.getNamespace(), "block/" + negativeId.getPath());
        } else {
            this.negativeId = Identifier.of(Slabee.MOD_ID, "block/normal_slab");
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
        if (this.positiveSlab != null) {
            UnbakedModel positiveUnbakedModel = baker.getOrLoadModel(this.positiveId);
            this.positiveBakedModel = positiveUnbakedModel.bake(baker, textureGetter, rotationContainer);
        }

        if (this.negativeSlab != null) {
            UnbakedModel negativeUnbakedModel = baker.getOrLoadModel(this.negativeId);
            this.negativeBakedModel = negativeUnbakedModel.bake(baker, textureGetter, rotationContainer);
        }
        return this;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {
        if (this.positiveSlab != null) {
            renderContext.pushTransform(quad -> {
                Direction face = quad.cullFace();

                return face != null && !shouldCullPositive(face, blockRenderView, blockPos);
            });
            positiveBakedModel.emitBlockQuads(blockRenderView, blockState, blockPos, supplier, renderContext);
            renderContext.popTransform();
        }

        if (this.negativeSlab != null) {
            renderContext.pushTransform(quad -> {
                Direction face = quad.cullFace();

                return face != null && !shouldCullNegative(face, blockRenderView, blockPos);
            });
            negativeBakedModel.emitBlockQuads(blockRenderView, blockState, blockPos, supplier, renderContext);
            renderContext.popTransform();
        }
    }

    private boolean shouldCullPositive(Direction face, BlockRenderView world, BlockPos pos) {
        BlockPos otherPos;
        BlockState otherState;
        Block otherBlock;
        if (face == Direction.UP) {
            otherPos = pos.up();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                return positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM;
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                return positiveSlab == entity.getNegativeSlabState().getBlock();
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
                return b == entity.getPositiveSlabState().getBlock() && b == entity.getNegativeSlabState().getBlock();
            } else {
                return positiveSlab == ModBlockMap.originalToVerticalSlab(otherBlock);
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
                return positiveSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP;
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                return positiveSlab == entity.getPositiveSlabState().getBlock();
            } else if (otherBlock instanceof VerticalSlabBlock) {
                return positiveSlab == ModBlockMap.verticalSlabToSlab(otherBlock) && otherState.get(VerticalSlabBlock.FACING) == face.getOpposite();
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                if (face == Direction.SOUTH || face == Direction.EAST) {
                    return positiveSlab == ModBlockMap.verticalSlabToSlab(entity.getNegativeSlabState().getBlock());
                } else {
                    return positiveSlab == ModBlockMap.verticalSlabToSlab(entity.getPositiveSlabState().getBlock());
                }
            } else {
                return positiveSlab == ModBlockMap.originalToVerticalSlab(otherBlock);
            }
        }
    }

    private boolean shouldCullNegative(Direction face, BlockRenderView world, BlockPos pos) {
        BlockPos otherPos;
        BlockState otherState;
        Block otherBlock;
        if (face == Direction.UP) {
            return positiveSlab == negativeSlab;
        } else if (face == Direction.DOWN) {
            otherPos = pos.down();
            otherState = world.getBlockState(otherPos);
            otherBlock = otherState.getBlock();
            if (otherBlock instanceof SlabBlock) {
                return negativeSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.TOP;
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                return negativeSlab == entity.getPositiveSlabState().getBlock();
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                Block b = ModBlockMap.slabToVerticalSlab(positiveSlab);
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
                return negativeSlab == otherBlock && otherState.get(SlabBlock.TYPE) == SlabType.BOTTOM;
            } else if (otherState.isOf(ModBlocks.DOUBLE_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleSlabBlockEntity entity) {
                return negativeSlab == entity.getNegativeSlabState().getBlock();
            } else if (otherBlock instanceof VerticalSlabBlock) {
                return positiveSlab == ModBlockMap.verticalSlabToSlab(otherBlock) && otherState.get(VerticalSlabBlock.FACING) == face.getOpposite();
            } else if (otherState.isOf(ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK) && world.getBlockEntity(otherPos) instanceof DoubleVerticalSlabBlockEntity entity) {
                if (face == Direction.SOUTH || face == Direction.EAST) {
                    return positiveSlab == ModBlockMap.verticalSlabToSlab(entity.getNegativeSlabState().getBlock());
                } else {
                    return positiveSlab == ModBlockMap.verticalSlabToSlab(entity.getPositiveSlabState().getBlock());
                }
            }
        }

        return false;
    }
}
