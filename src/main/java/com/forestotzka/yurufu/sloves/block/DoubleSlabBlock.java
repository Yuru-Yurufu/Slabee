package com.forestotzka.yurufu.sloves.block;

import com.forestotzka.yurufu.sloves.block.enums.CustomSlabType;
import com.forestotzka.yurufu.sloves.state.property.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;

public class DoubleSlabBlock extends Block {
    public static final EnumProperty<CustomSlabType> TOP_SLAB = ModProperties.TOP_SLAB;
    public static final EnumProperty<CustomSlabType> BOTTOM_SLAB = ModProperties.BOTTOM_SLAB;

    public DoubleSlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(TOP_SLAB, CustomSlabType.NONE)
                .with(BOTTOM_SLAB, CustomSlabType.NONE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TOP_SLAB, BOTTOM_SLAB);
    }

}
