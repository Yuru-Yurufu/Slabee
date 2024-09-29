package com.forestotzka.yurufu.sloves.block;

import com.forestotzka.yurufu.sloves.block.enums.CustomVerticalSlabType;
import com.forestotzka.yurufu.sloves.block.enums.VerticalSlabAxis;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;

public class DoubleVerticalSlabBlock extends Block {
    public static final EnumProperty<CustomVerticalSlabType> POSITIVE_SLAB = EnumProperty.of("positive_slab", CustomVerticalSlabType.class);
    public static final EnumProperty<CustomVerticalSlabType> NEGATIVE_SLAB = EnumProperty.of("negative_slab", CustomVerticalSlabType.class);
    public static final EnumProperty<VerticalSlabAxis> AXIS = EnumProperty.of("axis", VerticalSlabAxis.class);

    public DoubleVerticalSlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(POSITIVE_SLAB, CustomVerticalSlabType.NONE)
                .with(NEGATIVE_SLAB, CustomVerticalSlabType.NONE)
                .with(AXIS, VerticalSlabAxis.X));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POSITIVE_SLAB, NEGATIVE_SLAB, AXIS);
    }

}
