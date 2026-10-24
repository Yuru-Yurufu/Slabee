package com.forestotzka.yurufu.slabee.block;

import net.minecraft.block.BlockState;

/**
 * チャンクの描画を組み立てるときに、ダブルハーフの中身をモデルへ渡すためのデータ。
 * Block Entity の状態を、描画用に変更されない形で写したもの。
 * チャンクの組み立て中は Block Entity を直接読まず、必ずこれを使う（BlockView#getBlockEntityRenderData）。
 *
 * @param isX 縦のダブルハーフが東西に並んでいるか（横のダブルハーフでは使わない）
 */
public record DoubleSlabRenderData(BlockState positive, BlockState negative, boolean isX) {
    public BlockState getPositiveSlabState() {
        return positive;
    }

    public BlockState getNegativeSlabState() {
        return negative;
    }
}
