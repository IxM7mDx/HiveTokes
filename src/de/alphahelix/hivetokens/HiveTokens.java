package de.alphahelix.hivetokens;

import de.alphahelix.alphalibary.AlphaPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Set;

public final class HiveTokens extends AlphaPlugin {

    private static HashMap<Object, Location> armorstands = new HashMap<>();
    private static HashMap<Object, Location> endercrystals = new HashMap<>();

    public static HashMap<Object, Location> getArmorstands() {
        return armorstands;
    }

    public static void addArmorstand(Object as, Location where) {
        getArmorstands().put(as, where);
    }

    public static HashMap<Object, Location> getEndercrystals() {
        return endercrystals;
    }

    public static void addEndercrystal(Object ec, Location where) {
        getEndercrystals().put(ec, where);
    }

    public static boolean isEndercrystal(Player p) {
        for (Block b : p.getLineOfSight((Set<Material>) null, 4)) {
            for (Location l : getEndercrystals().values()) {
                if ((b.getX() == l.getBlockX()
                        && b.getY() == l.getBlockY()
                        && b.getZ() == l.getBlockZ()
                        || (b.getX() == l.getBlockX()
                        && b.getY() == (l.getBlockY() + 1)
                        && b.getZ() == l.getBlockZ())))
                    return true;
            }
        }
        return false;
    }

    public static Location getEndercrystal(Player p) {
        for (Block b : p.getLineOfSight((Set<Material>) null, 4)) {
            for (Location l : getEndercrystals().values()) {
                if ((b.getX() == l.getBlockX()
                        && b.getY() == l.getBlockY()
                        && b.getZ() == l.getBlockZ()
                        || (b.getX() == l.getBlockX()
                        && b.getY() == (l.getBlockY() + 1)
                        && b.getZ() == l.getBlockZ())))
                    return l;
            }
        }
        return null;
    }


    @Override
    public void onEnable() {
        new Register(this).initAll();
    }
}
