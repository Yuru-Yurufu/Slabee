package com.forestotzka.yurufu.slabee.model;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.DoubleSlabRenderData;
import com.forestotzka.yurufu.slabee.block.ModBlockMap;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.SnowySlabBlock;
import com.forestotzka.yurufu.slabee.block.SnowyVerticalSlabBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;
import net.fabricmc.fabric.api.renderer.v1.material.MaterialFinder;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import java.util.function.Supplier;

/**
 * 【描画の試作】ダブルハーフのモデルを包み、これまで Block Entity Renderer で毎フレーム描いていた
 * 不透明・カットアウトのハーフを、チャンクの描画に焼き込む。
 *
 * 再現のために、Block Entity Renderer（BlockRenderManager#renderBlock）と同じになるよう次を合わせる：
 * - 面の省略は、ダブルハーフではなく「そのハーフ単体」の状態で判断する（Block#shouldDrawSide）
 * - 描画レイヤー（不透明／カットアウト）はハーフ単体のレイヤー
 * - 色付け（草・葉など）はハーフ単体の色
 * - 雪が上にあるときの見た目（SNOWY）
 * - 陰影（AO）の有無はハーフ単体が光るかどうか
 * - 光るハーフの面の明るさの最低値はハーフ単体の明るさ
 */
@Environment(EnvType.CLIENT)
public class BakedHalvesModel extends ForwardingBakedModel {
    private final boolean vertical;

    public BakedHalvesModel(BakedModel wrapped, boolean vertical) {
        this.wrapped = wrapped;
        this.vertical = vertical;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context) {
        super.emitBlockQuads(blockView, state, pos, randomSupplier, context);

        if (!(blockView.getBlockEntityRenderData(pos) instanceof DoubleSlabRenderData data)) {
            return;
        }

        BlockState positive = data.positive();
        BlockState negative = data.negative();
        boolean snowAbove = blockView.getBlockState(pos.above()).is(BlockTags.SNOW);

        if (isBakedHalf(positive)) {
            emitHalf(blockView, state, pos, withSnowy(positive, snowAbove, true), randomSupplier, context);
        }
        if (isBakedHalf(negative)) {
            emitHalf(blockView, state, pos, withSnowy(negative, snowAbove, vertical), randomSupplier, context);
        }
    }

    /** これまで Block Entity Renderer が描いていたハーフ（不透明・カットアウト）。ガラス系は包まれたモデルが描く。 */
    private static boolean isBakedHalf(BlockState half) {
        Block block = half.getBlock();
        if (SlabeeUtils.isCutoutMippedSlabs(block)) return true;
        if (ModBlockMap.toSlab(block) == ModBlocks.GLASS_SLAB) return false;
        return !SlabeeUtils.isTranslucentSlabs(block);
    }

    private static BlockState withSnowy(BlockState half, boolean snowAbove, boolean apply) {
        if (!apply) return half;
        if (half.getBlock() instanceof SnowySlabBlock) {
            return half.setValue(SnowySlabBlock.SNOWY, snowAbove);
        }
        if (half.getBlock() instanceof SnowyVerticalSlabBlock) {
            return half.setValue(SnowyVerticalSlabBlock.SNOWY, snowAbove);
        }
        return half;
    }

    private static void emitHalf(BlockAndTintGetter blockView, BlockState doubleState, BlockPos pos, BlockState half, Supplier<RandomSource> randomSupplier, RenderContext context) {
        Minecraft client = Minecraft.getInstance();
        BakedModel model = client.getBlockRenderer().getBlockModel(half);
        BlockColors colors = client.getBlockColors();
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        BlendMode blendMode = BlendMode.fromRenderLayer(ItemBlockRenderTypes.getChunkRenderType(half));
        MaterialFinder finder = renderer.materialFinder();
        // 陰影（AO）は、ダブルハーフ全体ではなくハーフ単体が光るかどうかで決める（バニラの BlockModelRenderer と同じ）
        TriState ao = (half.getLightEmission() == 0 && model.useAmbientOcclusion()) ? TriState.TRUE : TriState.FALSE;
        // 光るハーフは、面の明るさの最低値をハーフ自身の明るさにする（バニラの WorldRenderer#getLightmapCoordinates と同じ）
        int minBlockLight = half.getLightEmission();

        context.pushTransform(quad -> {
            Direction cullFace = quad.cullFace();
            if (cullFace != null) {
                BlockPos otherPos = pos.relative(cullFace);
                if (!Block.shouldRenderFace(half, blockView, pos, cullFace, otherPos)) {
                    return false;
                }
                // 面の明るさは隣のマスから取るため、cullFace はなるべく残す。
                // ただしダブルハーフ全体の状態だと省略されてしまう面だけは、省略されないよう外す。
                if (!Block.shouldRenderFace(doubleState, blockView, pos, cullFace, otherPos)) {
                    quad.cullFace(null);
                }
            }

            RenderMaterial material = finder.clear().copyFrom(quad.material()).blendMode(blendMode).ambientOcclusion(ao).find();
            quad.material(material);

            if (minBlockLight > 0) {
                for (int i = 0; i < 4; i++) {
                    int light = quad.lightmap(i);
                    int block = Math.max(light & 0xFFFF, minBlockLight << 4);
                    quad.lightmap(i, (light & 0xFFFF0000) | block);
                }
            }

            int colorIndex = quad.colorIndex();
            if (colorIndex != -1) {
                int tint = colors.getColor(half, blockView, pos, colorIndex);
                for (int i = 0; i < 4; i++) {
                    quad.color(i, multiply(quad.color(i), tint));
                }
                quad.colorIndex(-1);
            }
            return true;
        });
        model.emitBlockQuads(blockView, half, pos, randomSupplier, context);
        context.popTransform();
    }

    private static int multiply(int argb, int rgb) {
        int a = (argb >>> 24) & 0xFF;
        int r = ((argb >> 16) & 0xFF) * ((rgb >> 16) & 0xFF) / 255;
        int g = ((argb >> 8) & 0xFF) * ((rgb >> 8) & 0xFF) / 255;
        int b = (argb & 0xFF) * (rgb & 0xFF) / 255;
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
