package com.forestotzka.yurufu.sloves.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class pickBlockPacket {
    public static final Identifier ID = Identifier.of("sloves", "pick_block_packet");

    /*public static void registerServerReceiver() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            Direction direction = buf.readEnumConstant(Direction.class);  // 受信した面
            BlockPos pos = buf.readBlockPos();  // 受信した座標

            // サーバー側の処理
            server.execute(() -> {
                player.giveItemStack(new ItemStack(Items.DIAMOND));  // 例: ダイヤモンドを与える処理
            });
        });
    }*/
}
