package de.alphahelix.hivetokens.utils;

import de.alphahelix.alphalibary.reflection.ReflectionUtil;
import de.alphahelix.hivetokens.FakeAPI;
import de.alphahelix.hivetokens.Register;
import de.alphahelix.hivetokens.instances.FakeEndercrystal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by AlphaHelixDev.
 */
public class EndercrystalUtil {

    public static void spawnEndercrystalForAll(Location loc, String name) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            spawnEndercrystalForPlayer(p, loc, name);
        }
    }

    public static void spawnEndercrystalForPlayer(Player p, Location loc, String name) {
        try {
            Class<?>[] param = {double.class, double.class, double.class, float.class, float.class};
            Object endercrystal = ReflectionUtil.getNmsClass("EntityEnderCrystal")
                    .getConstructor(ReflectionUtil.getNmsClass("World"))
                    .newInstance(ReflectionUtil.getWorldServer(p.getWorld()));

            endercrystal.getClass().getMethod("setLocation", param).invoke(endercrystal, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntity")
                    .getConstructor(ReflectionUtil.getNmsClass("Entity"), int.class).newInstance(endercrystal, 51));

            Register.getEndercrystalLocationsFile().addEndercrystalToFile(loc, name);
            FakeAPI.addFakeEndercrystal(p, new FakeEndercrystal(loc, name, endercrystal));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void destroyEndercrystalForAll(Object endercrystal) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            destroyEndercrystalForPlayer(p, endercrystal);
        }
    }

    public static void destroyEndercrystalForPlayer(Player p, Object endercrystal) {
        try {
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutEntityDestroy")
                    .getConstructor(int[].class)
                    .newInstance(new int[]{ReflectionUtil.getEntityID(endercrystal)}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
