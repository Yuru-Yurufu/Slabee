package com.forestotzka.yurufu.slabee.state.property;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ModProperties {
    public static final BooleanProperty IS_DOUBLE = BooleanProperty.create("is_double");
    public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);

    public ModProperties() {}
}
