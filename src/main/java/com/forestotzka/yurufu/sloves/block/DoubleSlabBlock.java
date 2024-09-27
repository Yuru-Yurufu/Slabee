package com.forestotzka.yurufu.sloves.block;

import com.forestotzka.yurufu.sloves.block.enums.CustomSlabType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;

public class DoubleSlabBlock extends Block {
    public static final EnumProperty<CustomSlabType> TOP_SLAB = EnumProperty.of("top_slab", CustomSlabType.class);
    public static final EnumProperty<CustomSlabType> BOTTOM_SLAB = EnumProperty.of("bottom_slab", CustomSlabType.class);

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
