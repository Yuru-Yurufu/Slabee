package com.forestotzka.yurufu.sloves.block;

import com.forestotzka.yurufu.sloves.registry.tag.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LightBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.SlabType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Objects;
import java.util.function.ToIntFunction;

import static com.forestotzka.yurufu.sloves.block.DoubleSlabBlock.LIGHT_LEVEL;

public class DoubleSlabBlockEntity extends AbstractDoubleSlabBlockEntity {
    public DoubleSlabBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DOUBLE_SLAB_BLOCK_ENTITY, pos, state);
        System.out.println("init");
    }
}
