package com.forestotzka.yurufu.sloves.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum VerticalSlabAxis implements StringIdentifiable {
    X("x"),
    Z("z");

    private final String name;

    VerticalSlabAxis(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static VerticalSlabAxis fromString(String name) {
        for (VerticalSlabAxis value : VerticalSlabAxis.values()) {
            if (value.asString().equals(name)) {
                return value;
            }
        }
        return X;
    }
}
