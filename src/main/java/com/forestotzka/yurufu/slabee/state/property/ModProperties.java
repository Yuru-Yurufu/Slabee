package com.forestotzka.yurufu.slabee.state.property;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

public class ModProperties {
    public static final BooleanProperty IS_DOUBLE = BooleanProperty.of("is_double");
    public static final IntProperty LIGHT_LEVEL = IntProperty.of("light_level", 0, 15);

    public ModProperties() {}
}
