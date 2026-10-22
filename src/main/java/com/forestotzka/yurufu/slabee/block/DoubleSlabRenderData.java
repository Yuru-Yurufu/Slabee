package com.forestotzka.yurufu.slabee.block;

import net.minecraft.block.BlockState;

/**
 * チャンクの描画を組み立てるときに、ダブルハーフの両側のハーフをモデルへ渡すためのデータ。
 * Block Entity の状態を、描画用に変更されない形で写したもの。
 */
public record DoubleSlabRenderData(BlockState positive, BlockState negative) {
}
