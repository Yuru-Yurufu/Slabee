package com.forestotzka.yurufu.slabee.model;

import com.mojang.math.Transformation;
import net.minecraft.client.resources.model.ModelState;

public class VerticalModelRotation implements ModelState {
    private final Transformation rotation;
    private final boolean uvLocked;

    public VerticalModelRotation(Transformation rotation, boolean uvLocked) {
        this.rotation = rotation;
        this.uvLocked = uvLocked;
    }

    @Override
    public Transformation getRotation() {
        return rotation;
    }

    @Override
    public boolean isUvLocked() {
        return uvLocked;
    }
}
