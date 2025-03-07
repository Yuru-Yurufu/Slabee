package com.forestotzka.yurufu.slabee.block;

import com.forestotzka.yurufu.slabee.block.enums.DoubleSlabVariant;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public class TranslucentSlabBlock extends SlabBlock {
    public static final MapCodec<TranslucentSlabBlock> CODEC = createCodec(TranslucentSlabBlock::new);

    public MapCodec<? extends TranslucentSlabBlock> getCodec() {
        return CODEC;
    }

    public TranslucentSlabBlock(Settings settings) {
        super(settings);
    }

    protected boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (direction == Direction.UP || direction == Direction.DOWN) {
            if (stateFrom.isOf(this)) {
                return state.get(SlabBlock.TYPE) != stateFrom.get(SlabBlock.TYPE);
            } else if (stateFrom.isOf(ModBlocks.DOUBLE_SLAB_BLOCK)) {
                if (direction == Direction.UP) {
                    return state.get(Properties.SLAB_TYPE) == SlabType.TOP && stateFrom.get(AbstractDoubleSlabBlock.NEGATIVE_SLAB) == DoubleSlabVariant.fromBlock(state.getBlock());
                } else {
                    return state.get(Properties.SLAB_TYPE) == SlabType.BOTTOM && stateFrom.get(AbstractDoubleSlabBlock.POSITIVE_SLAB) == DoubleSlabVariant.fromBlock(state.getBlock());
                }
            }
        } else {
            if (stateFrom.isOf(this)) {
                return state.get(SlabBlock.TYPE) == stateFrom.get(SlabBlock.TYPE);
            } else if (stateFrom.isOf(ModBlocks.DOUBLE_SLAB_BLOCK)) {
                if (state.get(Properties.SLAB_TYPE) == SlabType.TOP) {
                    return stateFrom.get(AbstractDoubleSlabBlock.POSITIVE_SLAB) == DoubleSlabVariant.fromBlock(state.getBlock());
                } else if (state.get(Properties.SLAB_TYPE) == SlabType.BOTTOM) {
                    return stateFrom.get(AbstractDoubleSlabBlock.NEGATIVE_SLAB) == DoubleSlabVariant.fromBlock(state.getBlock());
                }
            }
        }

        return super.isSideInvisible(state, stateFrom, direction);
    }
}
