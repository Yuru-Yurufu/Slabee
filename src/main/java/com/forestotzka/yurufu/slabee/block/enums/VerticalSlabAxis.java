package com.forestotzka.yurufu.slabee.block.enums;

import net.minecraft.util.StringRepresentable;

public enum VerticalSlabAxis implements StringRepresentable {
    X("x"),
    Z("z");

    private final String name;

    VerticalSlabAxis(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static VerticalSlabAxis fromString(String name) {
        for (VerticalSlabAxis value : VerticalSlabAxis.values()) {
            if (value.getSerializedName().equals(name)) {
                return value;
            }
        }
        return X;
    }
}
