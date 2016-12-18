package de.alphahelix.hivetokens;

import de.alphahelix.alphalibary.AlphaPlugin;
import de.alphahelix.hivetokens.instances.FakeArmorstand;
import de.alphahelix.hivetokens.instances.FakeEndercrystal;
import de.alphahelix.hivetokens.instances.FakePlayer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public final class FakeAPI extends AlphaPlugin {

    private static FakeAPI fakeAPI;

    private static HashMap<String, ArrayList<FakePlayer>> fakePlayers = new HashMap<>();
    private static HashMap<String, ArrayList<FakeArmorstand>> fakeArmorstands = new HashMap<>();
    private static HashMap<String, ArrayList<FakeEndercrystal>> fakeEndercrystals = new HashMap<>();

    public static HashMap<String, ArrayList<FakePlayer>> getFakePlayers() {
        return fakePlayers;
    }

    public static HashMap<String, ArrayList<FakeArmorstand>> getFakeArmorstands() {
        return fakeArmorstands;
    }

    public static HashMap<String, ArrayList<FakeEndercrystal>> getFakeEndercrystals() {
        return fakeEndercrystals;
    }

    public static void addFakePlayer(Player p, FakePlayer fp) {
        ArrayList<FakePlayer> list;
        if (getFakePlayers().containsKey(p.getName())) {
            list = getFakePlayers().get(p.getName());
            list.add(fp);
        } else {
            list = new ArrayList<>();
            list.add(fp);
        }
        getFakePlayers().put(p.getName(), list);
    }

    public static void addFakeArmorstand(Player p, FakeArmorstand fa) {
        ArrayList<FakeArmorstand> list;
        if (getFakeArmorstands().containsKey(p.getName())) {
            list = getFakeArmorstands().get(p.getName());
            list.add(fa);
        } else {
            list = new ArrayList<>();
            list.add(fa);
        }
        getFakeArmorstands().put(p.getName(), list);
    }

    public static void addFakeEndercrystal(Player p, FakeEndercrystal fe) {
        ArrayList<FakeEndercrystal> list;
        if (getFakeEndercrystals().containsKey(p.getName())) {
            list = getFakeEndercrystals().get(p.getName());
            list.add(fe);
        } else {
            list = new ArrayList<>();
            list.add(fe);
        }
        getFakeEndercrystals().put(p.getName(), list);
    }

    public static boolean isEndercrystalInRange(Player p) {
        for (Block b : p.getLineOfSight((Set<Material>) null, 4)) {
            if (!getFakeEndercrystals().containsKey(p.getName())) return false;
            for (FakeEndercrystal fakeEndercrystal : getFakeEndercrystals().get(p.getName())) {
                if ((b.getX() == fakeEndercrystal.getCurrentlocation().getBlockX()
                        && b.getY() == fakeEndercrystal.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeEndercrystal.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeEndercrystal.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeEndercrystal.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeEndercrystal.getCurrentlocation().getBlockZ())))
                    return true;
            }
        }
        return false;
    }

    public static FakeEndercrystal getEndercrystalInRange(Player p) {
        for (Block b : p.getLineOfSight((Set<Material>) null, 4)) {
            if (!getFakeEndercrystals().containsKey(p.getName())) return null;
            for (FakeEndercrystal fakeEndercrystal : getFakeEndercrystals().get(p.getName())) {
                if ((b.getX() == fakeEndercrystal.getCurrentlocation().getBlockX()
                        && b.getY() == fakeEndercrystal.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeEndercrystal.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeEndercrystal.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeEndercrystal.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeEndercrystal.getCurrentlocation().getBlockZ())))
                    return fakeEndercrystal;
            }
        }
        return null;
    }

    public static boolean isPlayerInRange(Player p) {
        if (!getFakePlayers().containsKey(p.getName())) return false;
        for (Block b : p.getLineOfSight((Set<Material>) null, 4)) {
            for (FakePlayer fakePlayer : getFakePlayers().get(p.getName())) {
                if ((b.getX() == fakePlayer.getCurrentlocation().getBlockX()
                        && b.getY() == fakePlayer.getCurrentlocation().getBlockY()
                        && b.getZ() == fakePlayer.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakePlayer.getCurrentlocation().getBlockX()
                        && b.getY() == (fakePlayer.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakePlayer.getCurrentlocation().getBlockZ())))
                    return true;
            }
        }
        return false;
    }

    public static FakePlayer getPlayerInRange(Player p) {
        for (Block b : p.getLineOfSight((Set<Material>) null, 4)) {
            if (!getFakePlayers().containsKey(p.getName())) return null;
            for (FakePlayer fakePlayer : getFakePlayers().get(p.getName())) {
                if ((b.getX() == fakePlayer.getCurrentlocation().getBlockX()
                        && b.getY() == fakePlayer.getCurrentlocation().getBlockY()
                        && b.getZ() == fakePlayer.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakePlayer.getCurrentlocation().getBlockX()
                        && b.getY() == (fakePlayer.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakePlayer.getCurrentlocation().getBlockZ())))
                    return fakePlayer;
            }
        }
        return null;
    }

    public static boolean isArmorstandInRange(Player p) {
        for (Block b : p.getLineOfSight((Set<Material>) null, 4)) {
            if (!getFakeArmorstands().containsKey(p.getName())) return false;
            for (FakeArmorstand fakeArmorstand : getFakeArmorstands().get(p.getName())) {
                if ((b.getX() == fakeArmorstand.getCurrentlocation().getBlockX()
                        && b.getY() == fakeArmorstand.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeArmorstand.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeArmorstand.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeArmorstand.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeArmorstand.getCurrentlocation().getBlockZ())))
                    return true;
            }
        }
        return false;
    }

    public static FakeArmorstand getArmorstandInRange(Player p) {
        for (Block b : p.getLineOfSight((Set<Material>) null, 4)) {
            if (!getFakeArmorstands().containsKey(p.getName())) return null;
            for (FakeArmorstand fakeArmorstand : getFakeArmorstands().get(p.getName())) {
                if ((b.getX() == fakeArmorstand.getCurrentlocation().getBlockX()
                        && b.getY() == fakeArmorstand.getCurrentlocation().getBlockY()
                        && b.getZ() == fakeArmorstand.getCurrentlocation().getBlockZ()
                        || (b.getX() == fakeArmorstand.getCurrentlocation().getBlockX()
                        && b.getY() == (fakeArmorstand.getCurrentlocation().getBlockY() + 1)
                        && b.getZ() == fakeArmorstand.getCurrentlocation().getBlockZ())))
                    return fakeArmorstand;
            }
        }
        return null;
    }

    public static FakePlayer getFakePlayerByObject(Player p, Object fake) {
        if (!getFakePlayers().containsKey(p.getName())) return null;
        for (FakePlayer fakePlayer : getFakePlayers().get(p.getName())) {
            if (fakePlayer.getFake() == fake) return fakePlayer;
        }
        return null;
    }

    public static FakeArmorstand getFakeArmorstandByObject(Player p, Object fake) {
        if (!getFakeArmorstands().containsKey(p.getName())) return null;
        for (FakeArmorstand fakeArmorstand : getFakeArmorstands().get(p.getName())) {
            if (fakeArmorstand.getFake() == fake) return fakeArmorstand;
        }
        return null;
    }

    public static FakeEndercrystal getFakeEndercrystalByObject(Player p, Object fake) {
        if (!getFakeEndercrystals().containsKey(p.getName())) return null;
        for (FakeEndercrystal fakeEndercrystal : getFakeEndercrystals().get(p.getName())) {
            if (fakeEndercrystal.getFake() == fake) return fakeEndercrystal;
        }
        return null;
    }

    public static FakeAPI getFakeAPI() {
        return fakeAPI;
    }

    @Override
    public void onEnable() {
        new Register(this).initAll();
        fakeAPI = this;
    }
}
