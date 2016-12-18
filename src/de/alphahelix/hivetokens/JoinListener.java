package de.alphahelix.hivetokens;

import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.hivetokens.events.ArmorstandClickEvent;
import de.alphahelix.hivetokens.events.EndercrystalClickEvent;
import de.alphahelix.hivetokens.events.PlayerClickEvent;
import de.alphahelix.hivetokens.instances.FakeArmorstand;
import de.alphahelix.hivetokens.instances.FakeEndercrystal;
import de.alphahelix.hivetokens.instances.FakePlayer;
import de.alphahelix.hivetokens.utils.ArmorstandUtil;
import de.alphahelix.hivetokens.utils.EndercrystalUtil;
import de.alphahelix.hivetokens.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by AlphaHelixDev.
 */
public class JoinListener extends SimpleListener<FakeAPI, Register> {

    public JoinListener(FakeAPI plugin, Register register) {
        super(plugin, register);
    }

    @EventHandler
    public void onEndercrystalInteract(PlayerInteractEvent e) {
        if (FakeAPI.isArmorstandInRange(e.getPlayer())) {
            Bukkit.getPluginManager().callEvent(new ArmorstandClickEvent(e.getPlayer(), FakeAPI.getArmorstandInRange(e.getPlayer())));
        }
        if (FakeAPI.isEndercrystalInRange(e.getPlayer())) {
            Bukkit.getPluginManager().callEvent(new EndercrystalClickEvent(e.getPlayer(), FakeAPI.getEndercrystalInRange(e.getPlayer())));
        }
        if (FakeAPI.isPlayerInRange(e.getPlayer())) {
            Bukkit.getPluginManager().callEvent(new PlayerClickEvent(e.getPlayer(), FakeAPI.getPlayerInRange(e.getPlayer())));
        }
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
        for (String names : getRegister().getPlayerLocationsFile().getPacketPlayers().keySet()) {
            PlayerUtil.spawnPlayerForPlayer(e.getPlayer(),
                    getRegister().getPlayerLocationsFile().getPacketPlayers().get(names),
                    e.getPlayer(),
                    names);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (FakeAPI.getFakePlayers().containsKey(p.getName())) {
            for (FakePlayer fakePlayer : FakeAPI.getFakePlayers().get(p.getName())) {
                fakePlayer.setCurrentlocation(fakePlayer.getStartLocation());
            }
        }
        if (FakeAPI.getFakeArmorstands().containsKey(p.getName())) {
            for (FakeArmorstand fakeArmorstand : FakeAPI.getFakeArmorstands().get(p.getName())) {
                fakeArmorstand.setCurrentlocation(fakeArmorstand.getStartLocation());
            }
        }
        if (FakeAPI.getFakeEndercrystals().containsKey(p.getName())) {
            for (FakeEndercrystal fakeEndercrystal : FakeAPI.getFakeEndercrystals().get(p.getName())) {
                fakeEndercrystal.setCurrentlocation(fakeEndercrystal.getStartLocation());
            }
        }
    }

    @EventHandler
    public void test(PlayerChatEvent e) {
        Player p = e.getPlayer();
        switch (e.getMessage()) {
            case "a":
                PlayerUtil.spawnPlayerForPlayer(p, p.getLocation(), p, "Hubert");
                break;
            case "b":
                PlayerUtil.movePlayerForPlayer(p, 1, 0, 1, FakeAPI.getPlayerInRange(p).getFake());
                break;
        }
    }
}
