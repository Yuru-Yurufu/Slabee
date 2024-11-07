package com.forestotzka.yurufu.sloves.block;

import com.forestotzka.yurufu.sloves.block.enums.VerticalSlabAxis;
import com.forestotzka.yurufu.sloves.registry.tag.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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

public class DoubleVerticalSlabBlockEntity extends BlockEntity {
    public DoubleVerticalSlabBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DOUBLE_VERTICAL_SLAB_BLOCK_ENTITY, pos, state);
    }
}
