package com.forestotzka.yurufu.slabee.state.property;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

public class ModProperties {
    public static final BooleanProperty IS_DOUBLE = BooleanProperty.of("is_double");
    public static final BooleanProperty IS_OPAQUE = BooleanProperty.of("is_opaque");
    public static final BooleanProperty IS_EMISSIVE_LIGHTING = BooleanProperty.of("is_emissive");
    public static final IntProperty LIGHT_LEVEL = IntProperty.of("light_level", 0, 15);
    public static final IntProperty SEE_THROUGH = IntProperty.of("see_through", 0, 3);

    public ModProperties() {}
}
