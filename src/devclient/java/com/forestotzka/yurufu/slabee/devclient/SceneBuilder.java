package com.forestotzka.yurufu.slabee.devclient;

import com.forestotzka.yurufu.slabee.SlabeeUtils;
import com.forestotzka.yurufu.slabee.block.AbstractDoubleSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlockEntity;
import com.forestotzka.yurufu.slabee.block.ModBlockMap;
import com.forestotzka.yurufu.slabee.block.ModBlocks;
import com.forestotzka.yurufu.slabee.block.DoubleVerticalSlabBlock;
import com.forestotzka.yurufu.slabee.block.enums.VerticalSlabAxis;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;

/**
 * 撮影・測定用の配置をサーバー側で組み立てる。配置の方法は実際の重ね置き（SlabBlockMixin /
 * VerticalSlabBlock#getPlacementState）と同じ手順（外側の状態を決めてから Block Entity に両側を設定）。
 */
final class SceneBuilder {
    /** 1列に並べる種類の間隔（ブロック） */
    static final int COLUMN_STEP = 2;
    /** 行（z）の一覧。行ごとに組み合わせの種類が違う */
    static final String[] ROWS = {
            "single_bottom",       // z=0  : 普通の下付きハーフ（比較の基準）
            "double_s_over_stone", // z=2  : 上=S、下=石
            "double_stone_over_s", // z=4  : 上=石、下=S
            "double_s_over_s",     // z=6  : 上下とも S
            "double_s_over_glass", // z=8  : 上=S、下=ガラス
            "vertical_x_s_stone",  // z=10 : 縦 X軸、東=S、西=石
            "vertical_z_s_glass",  // z=12 : 縦 Z軸、南=S、北=ガラス
    };
    static final int ROW_STEP = 2;

    static final int FIELD_SIZE = 100;
    static final int SLABEE_FIELD_Z = 200;
    static final int VANILLA_FIELD_Z = 400;
    static final int EMPTY_Z = 700;
    static final int ADJACENT_Z = 30;

    private SceneBuilder() {}

    static List<Block> allSlabs() {
        List<Block> slabs = new ArrayList<>();
        for (Block block : BuiltInRegistries.BLOCK) {
            if (block instanceof SlabBlock && ModBlockMap.slabToVerticalSlab(block) != null) {
                slabs.add(block);
            }
        }
        slabs.sort(Comparator.comparing(b -> BuiltInRegistries.BLOCK.getKey(b).toString()));
        return slabs;
    }

    static List<Block> glassSlabs(List<Block> slabs) {
        List<Block> result = new ArrayList<>();
        for (Block slab : slabs) {
            if (BuiltInRegistries.BLOCK.getKey(slab).getPath().contains("glass")) result.add(slab);
        }
        return result;
    }

    /** 全種類を並べた比較用の配置 */
    static void buildCatalog(ServerLevel world, int y, List<Block> slabs) {
        Block stone = Blocks.STONE_SLAB;
        Block glass = ModBlocks.GLASS_SLAB;
        Block stoneV = ModBlockMap.slabToVerticalSlab(stone);
        Block glassV = ModBlockMap.slabToVerticalSlab(glass);

        for (int i = 0; i < slabs.size(); i++) {
            Block s = slabs.get(i);
            Block sV = ModBlockMap.slabToVerticalSlab(s);
            int x = i * COLUMN_STEP;

            world.setBlock(new BlockPos(x, y, 0), s.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM), 3);
            placeDouble(world, new BlockPos(x, y, 2), s, stone);
            placeDouble(world, new BlockPos(x, y, 4), stone, s);
            placeDouble(world, new BlockPos(x, y, 6), s, s);
            placeDouble(world, new BlockPos(x, y, 8), s, glass);
            placeDoubleVertical(world, new BlockPos(x, y, 10), "x", sV, stoneV);
            placeDoubleVertical(world, new BlockPos(x, y, 12), "z", sV, glassV);
        }
    }

    /** ガラス系を隣り合わせた配置（継ぎ目・面の省略の確認用） */
    static void buildAdjacent(ServerLevel world, int y, List<Block> glassSlabs) {
        Block stone = Blocks.STONE_SLAB;
        for (int k = 0; k < glassSlabs.size(); k++) {
            Block g = glassSlabs.get(k);
            Block gV = ModBlockMap.slabToVerticalSlab(g);
            int x0 = k * 5;
            // 3x2 の壁：上下とも同じガラス
            for (int dx = 0; dx < 3; dx++) {
                for (int dy = 0; dy < 2; dy++) {
                    placeDouble(world, new BlockPos(x0 + dx, y + dy, ADJACENT_Z), g, g);
                }
            }
            // 3x2 の壁：上=ガラス、下=石
            for (int dx = 0; dx < 3; dx++) {
                for (int dy = 0; dy < 2; dy++) {
                    placeDouble(world, new BlockPos(x0 + dx, y + dy, ADJACENT_Z + 3), g, stone);
                }
            }
            // 縦（Z軸）3枚並び：南=ガラス、北=同じガラス
            for (int dx = 0; dx < 3; dx++) {
                placeDoubleVertical(world, new BlockPos(x0 + dx, y, ADJACENT_Z + 6), "z", gV, gV);
            }
            // 普通のガラスのフルブロックとの境目
            world.setBlock(new BlockPos(x0, y, ADJACENT_Z + 8), ModBlockMap.slabToOriginal(g).defaultBlockState(), 3);
            placeDouble(world, new BlockPos(x0 + 1, y, ADJACENT_Z + 8), g, g);
            world.setBlock(new BlockPos(x0 + 2, y, ADJACENT_Z + 8), ModBlockMap.slabToOriginal(g).defaultBlockState(), 3);
        }
    }

    static final int REFERENCE_Z = 60;

    /**
     * 「通常ブロックで同じ並びにしたらどう見えるか」の比較用。
     * ガラス系それぞれについて、左に通常ブロックの壁（下から 石・ガラス・石・ガラス、3x4）、
     * 右に同じ並びのダブルハーフの壁（上=ガラス・下=石、3x2）を並べる。
     */
    static void buildReference(ServerLevel world, int y, List<Block> glassSlabs) {
        Block stone = Blocks.STONE_SLAB;
        for (int k = 0; k < glassSlabs.size(); k++) {
            Block g = glassSlabs.get(k);
            Block full = ModBlockMap.slabToOriginal(g);
            int x0 = k * 10;
            for (int dx = 0; dx < 3; dx++) {
                for (int dy = 0; dy < 4; dy++) {
                    Block b = (dy % 2 == 0) ? Blocks.STONE : full;
                    world.setBlock(new BlockPos(x0 + dx, y + dy, REFERENCE_Z), b.defaultBlockState(), 3);
                }
                for (int dy = 0; dy < 2; dy++) {
                    placeDouble(world, new BlockPos(x0 + 5 + dx, y + dy, REFERENCE_Z), g, stone);
                }
            }
        }
    }

    static final int PAIR_Z = 75;

    /** 素材の組み合わせごとの比較用（上, 下）。葉・光るブロック・ガラスなど、面の省略が分かれやすいもの */
    static final String[][] PAIRS = {
            {"slabee:oak_leaf_slab", "slabee:glowstone_slab"},
            {"slabee:glowstone_slab", "slabee:oak_leaf_slab"},
            {"slabee:oak_leaf_slab", "minecraft:stone_slab"},
            {"slabee:oak_leaf_slab", "slabee:birch_leaf_slab"},
            {"slabee:glass_slab", "slabee:glowstone_slab"},
            {"slabee:glass_slab", "slabee:oak_leaf_slab"},
            {"slabee:magma_block_slab", "slabee:oak_leaf_slab"},
    };

    /**
     * 組み合わせごとに、左に通常ブロック（下・上の2段）、右に同じ組み合わせのダブルハーフを、
     * それぞれ 2x2 の広さで並べる。上から見下ろすと、境目の面が見えるかを比べられる。
     */
    static void buildPairs(ServerLevel world, int y) {
        for (int k = 0; k < PAIRS.length; k++) {
            Block top = BuiltInRegistries.BLOCK.get(net.minecraft.resources.ResourceLocation.parse(PAIRS[k][0]));
            Block bottom = BuiltInRegistries.BLOCK.get(net.minecraft.resources.ResourceLocation.parse(PAIRS[k][1]));
            Block topFull = ModBlockMap.slabToOriginal(top);
            Block bottomFull = ModBlockMap.slabToOriginal(bottom);
            if (topFull == null) topFull = Blocks.STONE;
            if (bottomFull == null) bottomFull = Blocks.STONE;
            int x0 = k * 8;
            for (int dx = 0; dx < 2; dx++) {
                for (int dz = 0; dz < 2; dz++) {
                    world.setBlock(new BlockPos(x0 + dx, y, PAIR_Z + dz), bottomFull.defaultBlockState(), 3);
                    world.setBlock(new BlockPos(x0 + dx, y + 1, PAIR_Z + dz), topFull.defaultBlockState(), 3);
                    placeDouble(world, new BlockPos(x0 + 4 + dx, y, PAIR_Z + dz), top, bottom);
                }
            }
        }
    }

    /** 重さの測定用：Slabee のダブルハーフを敷き詰めた 100x100 */
    static void buildSlabeeField(ServerLevel world, int y) {
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int z = 0; z < FIELD_SIZE; z++) {
                boolean alt = ((x + z) & 1) == 0;
                placeDouble(world, new BlockPos(x, y, SLABEE_FIELD_Z + z),
                        alt ? Blocks.OAK_SLAB : Blocks.STONE_SLAB,
                        alt ? Blocks.STONE_SLAB : Blocks.OAK_SLAB);
            }
        }
    }

    /** 重さの測定用：同じ形のバニラのダブルハーフ（比較の基準） */
    static void buildVanillaField(ServerLevel world, int y) {
        for (int x = 0; x < FIELD_SIZE; x++) {
            for (int z = 0; z < FIELD_SIZE; z++) {
                boolean alt = ((x + z) & 1) == 0;
                Block slab = alt ? Blocks.OAK_SLAB : Blocks.STONE_SLAB;
                world.setBlock(new BlockPos(x, y, VANILLA_FIELD_Z + z),
                        slab.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE), 3);
            }
        }
    }

    static void placeDouble(ServerLevel world, BlockPos pos, Block top, Block bottom) {
        BlockState state = SlabeeUtils.getAbstractState(top, bottom, ModBlocks.DOUBLE_SLAB_BLOCK.defaultBlockState());
        world.setBlock(pos, state, 3);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AbstractDoubleSlabBlockEntity entity) {
            entity.setPositiveSlabId(BuiltInRegistries.BLOCK.getKey(top));
            entity.setNegativeSlabId(BuiltInRegistries.BLOCK.getKey(bottom));
        }
    }

    static void placeDoubleVertical(ServerLevel world, BlockPos pos, String axis, Block positive, Block negative) {
        BlockState state = SlabeeUtils.getAbstractState(positive, negative,
                ModBlocks.DOUBLE_VERTICAL_SLAB_BLOCK.defaultBlockState().setValue(DoubleVerticalSlabBlock.AXIS, VerticalSlabAxis.fromString(axis)));
        world.setBlock(pos, state, 3);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof DoubleVerticalSlabBlockEntity entity) {
            entity.setAxis(axis);
            entity.setPositiveSlabId(BuiltInRegistries.BLOCK.getKey(positive));
            entity.setNegativeSlabId(BuiltInRegistries.BLOCK.getKey(negative));
        }
    }
}
