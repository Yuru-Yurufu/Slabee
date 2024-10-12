package com.forestotzka.yurufu.sloves.block;

import com.forestotzka.yurufu.sloves.block.enums.CustomSlabType;
import com.forestotzka.yurufu.sloves.state.property.ModProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class DoubleSlabBlock extends BlockWithEntity implements BlockEntityProvider {
    //public static final EnumProperty<CustomSlabType> TOP_SLAB = ModProperties.TOP_SLAB;
    //public static final EnumProperty<CustomSlabType> BOTTOM_SLAB = ModProperties.BOTTOM_SLAB;

    public DoubleSlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                //.with(TOP_SLAB, CustomSlabType.NONE)
                //.with(BOTTOM_SLAB, CustomSlabType.NONE)
                );
    }

    @Override
    protected MapCodec<? extends DoubleSlabBlock> getCodec() {
        return createCodec(DoubleSlabBlock::new);
    }

    /*@Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        DoubleSlabBlockEntity blockEntity =
    }*/

    /*@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TOP_SLAB, BOTTOM_SLAB);
    }*/

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        System.out.println("initbbbbbbbbbbbbbbbbb");
        return new DoubleSlabBlockEntity(pos, state);
    }
}
