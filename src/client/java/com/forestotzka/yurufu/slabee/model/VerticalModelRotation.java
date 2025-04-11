package com.forestotzka.yurufu.slabee.model;

import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.util.math.AffineTransformation;

public class VerticalModelRotation implements ModelBakeSettings {
    private final AffineTransformation rotation;
    private final boolean uvLocked;

    public VerticalModelRotation(AffineTransformation rotation, boolean uvLocked) {
        this.rotation = rotation;
        this.uvLocked = uvLocked;
    }

    @Override
    public AffineTransformation getRotation() {
        return rotation;
    }

    @Override
    public boolean isUvLocked() {
        return uvLocked;
    }
}
