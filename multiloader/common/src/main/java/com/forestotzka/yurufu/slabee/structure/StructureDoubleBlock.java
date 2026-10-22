package com.forestotzka.yurufu.slabee.structure;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/** Storage host only; the full Double's placement, lighting and renderer are not ported. */
public final class StructureDoubleBlock extends BaseEntityBlock {
    public static final MapCodec<StructureDoubleBlock> CODEC = simpleCodec(StructureDoubleBlock::new);
    public StructureDoubleBlock(Properties properties) { super(properties); }
    @Override protected MapCodec<? extends BaseEntityBlock> codec() { return CODEC; }
    @Override public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StructureDoubleBlockEntity(pos, state);
    }
}
