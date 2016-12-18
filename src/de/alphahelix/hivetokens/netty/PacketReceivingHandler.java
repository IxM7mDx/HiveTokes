package de.alphahelix.hivetokens.netty;

import org.bukkit.entity.Player;

/**
 * Created by AlphaHelixDev.
 */
public interface PacketReceivingHandler {

    boolean handle(Player p, Object packetPlayInUseEntity);

}
