package com.forestotzka.yurufu.slabee.block.persistence;

import java.util.Optional;

public enum ComponentContainerKind {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical");

    private final String serializedName;

    ComponentContainerKind(String serializedName) {
        this.serializedName = serializedName;
    }

    public String serializedName() {
        return serializedName;
    }

    public static Optional<ComponentContainerKind> fromSerializedName(String name) {
        for (ComponentContainerKind kind : values()) {
            if (kind.serializedName.equals(name)) {
                return Optional.of(kind);
            }
        }
        return Optional.empty();
    }
}
