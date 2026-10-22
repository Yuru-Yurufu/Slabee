package com.forestotzka.yurufu.slabee.block.persistence;

public enum ComponentSlot {
    POSITIVE("positive"),
    NEGATIVE("negative");

    private final String serializedName;

    ComponentSlot(String serializedName) {
        this.serializedName = serializedName;
    }

    public String serializedName() {
        return serializedName;
    }
}
