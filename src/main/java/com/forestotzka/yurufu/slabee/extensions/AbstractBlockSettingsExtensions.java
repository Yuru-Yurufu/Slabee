package com.forestotzka.yurufu.slabee.extensions;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public interface AbstractBlockSettingsExtensions {
    AbstractBlock.Settings slabee$setOpaque(ToIntFunction<BlockState> opaqueFunction);
    ToIntFunction<BlockState> getIntOpaque();
    boolean getSetOpaqueMarker();
}
