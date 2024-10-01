package com.forestotzka.yurufu.sloves.state.property;

import com.forestotzka.yurufu.sloves.block.enums.CustomSlabType;
import com.forestotzka.yurufu.sloves.block.enums.CustomVerticalSlabType;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;

public class ModProperties {
    public static final BooleanProperty IS_DOUBLE = BooleanProperty.of("is_double");
    public static final EnumProperty<CustomSlabType> SECOND_SLAB = EnumProperty.of("second_slab", CustomSlabType.class);
    public static final EnumProperty<CustomVerticalSlabType> SECOND_VERTICAL_SLAB = EnumProperty.of("second_vertical_slab", CustomVerticalSlabType.class);
    public static final EnumProperty<CustomSlabType> TOP_SLAB = EnumProperty.of("top_slab", CustomSlabType.class);
    public static final EnumProperty<CustomSlabType> BOTTOM_SLAB = EnumProperty.of("bottom_slab", CustomSlabType.class);

    public ModProperties() {}
}
