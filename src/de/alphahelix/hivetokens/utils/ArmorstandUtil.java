package de.alphahelix.hivetokens.utils;

import de.alphahelix.alphalibary.reflection.ReflectionUtil;
import de.alphahelix.hivetokens.EquipSlot;
import de.alphahelix.hivetokens.HiveTokens;
import de.alphahelix.hivetokens.Register;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by AlphaHelixDev.
 */
public class ArmorstandUtil {

    private static Class<?>[] param = {double.class, double.class, double.class, float.class, float.class};

    public static void spawnArmorstandForAll(Location loc, String name) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            spawnArmorstandForPlayer(p, loc, name);
        }
    }

    public static void spawnArmorstandForPlayer(Player p, Location loc, String name) {
        try {
            Object armorstand = ReflectionUtil.getNmsClass("EntityArmorStand").getConstructor(ReflectionUtil.getNmsClass("World")).newInstance(ReflectionUtil.getWorldServer(p.getWorld()));

            ReflectionUtil.getNmsClass("EntityArmorStand").getMethod("setLocation", param)
                    .invoke(armorstand, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
            ReflectionUtil.getNmsClass("EntityArmorStand").getMethod("setInvisible", boolean.class)
                    .invoke(armorstand, true);
            ReflectionUtil.getNmsClass("EntityArmorStand").getMethod("setCustomName", String.class)
                    .invoke(armorstand, name);
            ReflectionUtil.getNmsClass("EntityArmorStand").getMethod("setCustomNameVisible", boolean.class)
                    .invoke(armorstand, true);

            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutSpawnEntityLiving").getConstructor(ReflectionUtil.getNmsClass("EntityLiving"))
                    .newInstance(armorstand));

            Register.getArmorstandLocationsFile().addArmorstandToFile(loc, name);
            HiveTokens.addArmorstand(armorstand, loc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void moveArmorstandForAll(Location loc, double x, double y, double z, Object armorstand) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            moveArmorstandForPlayer(p, loc, x, y, z, armorstand);
        }
    }

    public static void moveArmorstandForPlayer(Player p, Location loc, double x, double y, double z, Object armorstand) {
        try {
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutEntity$PacketPlayOutRelEntityMove")
                    .getConstructor(int.class, byte.class, byte.class, byte.class, boolean.class)
                    .newInstance(
                            ReflectionUtil.getEntityID(armorstand),
                            ((byte) (x * 32)),
                            ((byte) (y * 32)),
                            ((byte) (z * 32)),
                            (loc.getBlockY() == loc.getWorld().getHighestBlockYAt(loc))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void equipArmorstandForAll(Object armorstand, ItemStack item, EquipSlot slot) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            equipArmorstandForPlayer(p, armorstand, item, slot);
        }
    }

    public static void equipArmorstandForPlayer(Player p, Object armorstand, ItemStack item, EquipSlot slot) {
        try {
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutEntityEquipment")
                    .getConstructor(int.class, int.class, ReflectionUtil.getNmsClass("ItemStack"))
                    .newInstance(
                            ReflectionUtil.getEntityID(armorstand),
                            slot.getNmsSlot(),
                            ReflectionUtil.getObjectNMSItemStack(item)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void destroyArmorstandForAll(Object armorstand) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            destroyArmorstandForPlayer(p, armorstand);
        }
    }

    public static void destroyArmorstandForPlayer(Player p, Object armorstand) {
        try {
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutEntityDestroy")
                    .getConstructor(int[].class)
                    .newInstance(new int[]{ReflectionUtil.getEntityID(armorstand)}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
