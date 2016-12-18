package de.alphahelix.hivetokens;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.hivetokens.events.EndercrystalClickEvent;
import de.alphahelix.hivetokens.netty.PacketReader;
import de.alphahelix.hivetokens.netty.PacketReceivingHandler;
import de.alphahelix.hivetokens.utils.ArmorstandUtil;
import de.alphahelix.hivetokens.utils.EndercrystalUtil;
import io.netty.channel.ChannelHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

/**
 * Created by AlphaHelixDev.
 */
public class JoinListener extends SimpleListener<HiveTokens, Register> {

    private HashMap<String, ChannelHandler> handlerHashMap = new HashMap<>();

    public JoinListener(HiveTokens plugin, Register register) {
        super(plugin, register);
    }

    @EventHandler
    public void onInteract(final PlayerJoinEvent e) {
        final PacketReader pr = new PacketReader();
        handlerHashMap.put(e.getPlayer().getName(),
                pr.listen(e.getPlayer(), new PacketReceivingHandler() {
                    @Override
                    public boolean handle(Player p, Object packetPlayInUseEntity) {
                        if (HiveTokens.isEndercrystal(e.getPlayer())) {
                            Bukkit.getPluginManager().callEvent(new EndercrystalClickEvent(e.getPlayer(), HiveTokens.getEndercrystal(e.getPlayer())));
                        }
                        return false;
                    }
                })
        );
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        for (String names : getRegister().getArmorstandLocationsFile().getPacketArmorstand().keySet()) {
            ArmorstandUtil.spawnArmorstandForPlayer(e.getPlayer(),
                    getRegister().getArmorstandLocationsFile().getPacketArmorstand().get(names),
                    names);
        }
        for (String names : getRegister().getEndercrystalLocationsFile().getPacketEndercrystal().keySet()) {
            EndercrystalUtil.spawnEndercrystalForPlayer(e.getPlayer(),
                    getRegister().getEndercrystalLocationsFile().getPacketEndercrystal().get(names),
                    names);
        }
    }

    @EventHandler
    public void test(EndercrystalClickEvent e) {
        System.out.println("See it works!");
    }
}
