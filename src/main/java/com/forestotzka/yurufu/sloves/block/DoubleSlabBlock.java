package com.forestotzka.yurufu.sloves.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class DoubleSlabBlock extends BlockWithEntity implements BlockEntityProvider {
    public DoubleSlabBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends DoubleSlabBlock> getCodec() {
        return createCodec(DoubleSlabBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DoubleSlabBlockEntity(pos, state);
    }
}
