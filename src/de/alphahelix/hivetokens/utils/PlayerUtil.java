package de.alphahelix.hivetokens.utils;

import com.mojang.authlib.GameProfile;
import de.alphahelix.alphalibary.UUID.UUIDFetcher;
import de.alphahelix.alphalibary.nms.REnumPlayerInfoAction;
import de.alphahelix.alphalibary.reflection.PacketUtil;
import de.alphahelix.alphalibary.reflection.ReflectionUtil;
import de.alphahelix.hivetokens.EquipSlot;
import de.alphahelix.hivetokens.FakeAPI;
import de.alphahelix.hivetokens.Register;
import de.alphahelix.hivetokens.instances.FakePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by AlphaHelixDev.
 */
public class PlayerUtil {

    public static void spawnPlayerForAll(Location loc, OfflinePlayer skin, String customName) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            spawnPlayerForPlayer(p, loc, skin, customName);
        }
    }

    public static void spawnPlayerForPlayer(final Player p, Location loc, OfflinePlayer skin, String customName) {
        try {
            Class<?> nmsServerClass = ReflectionUtil.getNmsClass("MinecraftServer");
            Class<?> nmsWorldClass = ReflectionUtil.getNmsClass("World");
            Class<?> nmsWorldServerClass = ReflectionUtil.getNmsClass("WorldServer");
            Class<?> nmsPlayerInteractManagerClass = ReflectionUtil.getNmsClass("PlayerInteractManager");

            Field id = ReflectionUtil.getNmsClass("Entity").getDeclaredField("id");
            Field name = ReflectionUtil.getNmsClass("EntityPlayer").getField("listName");
            Field dName = ReflectionUtil.getNmsClass("EntityPlayer").getField("displayName");
            Field gPName = GameProfile.class.getDeclaredField("name");

            id.setAccessible(true);
            name.setAccessible(true);
            dName.setAccessible(true);
            gPName.setAccessible(true);

            Object nmsServer = Bukkit.getServer().getClass().getMethod("getServer").invoke(Bukkit.getServer());
            Object nmsWorld = loc.getWorld().getClass().getMethod("getHandle").invoke(loc.getWorld());
            Object nmsPIM = nmsPlayerInteractManagerClass.getConstructor(nmsWorldClass).newInstance(nmsWorld);
            Object npc = ReflectionUtil.getNmsClass("EntityPlayer")
                    .getConstructor(nmsServerClass, nmsWorldServerClass, GameProfile.class,
                            nmsPlayerInteractManagerClass)
                    .newInstance(nmsServer, nmsWorld,
                            new GameProfile(UUIDFetcher.getUUID(skin.getName()), skin.getName()), nmsPIM);

            ReflectionUtil.getNmsClass("Entity")
                    .getMethod("setLocation", double.class, double.class, double.class, float.class, float.class)
                    .invoke(npc, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

            GameProfile gp = new GameProfile(UUIDFetcher.getUUID(skin.getName()), skin.getName());

            name.set(npc, ReflectionUtil.serializeString(customName));
            dName.set(npc, customName);
            gPName.set(gp, customName);

            Object pPOPlayerInfo = PacketUtil.createPlayerInfoPacket(REnumPlayerInfoAction.ADD_PLAYER.getPlayerInfoAction(),
                    gp, 0, ReflectionUtil.getEnumGamemode(skin), customName);

            final Object pPOPPlayerInfoDestory = PacketUtil.createPlayerInfoPacket(REnumPlayerInfoAction.REMOVE_PLAYER.getPlayerInfoAction(),
                    gp, 0, ReflectionUtil.getEnumGamemode(skin), customName);

            Object pPONamedEntitySpawn = ReflectionUtil.getNmsClass("PacketPlayOutNamedEntitySpawn")
                    .getConstructor(ReflectionUtil.getNmsClass("EntityHuman")).newInstance(npc);

            Object pPOEntityHeadRotation = ReflectionUtil.getNmsClass("PacketPlayOutEntityHeadRotation")
                    .getConstructor(ReflectionUtil.getNmsClass("Entity"), byte.class)
                    .newInstance(npc, toAngle(loc.getYaw()));

            Object pPOEntityLook = ReflectionUtil.getNmsClass("PacketPlayOutEntity$PacketPlayOutEntityLook")
                    .getConstructor(int.class, byte.class, byte.class, boolean.class)
                    .newInstance(id.get(npc), toAngle(loc.getYaw()), toAngle(loc.getPitch()), true);

            Field yaw = ReflectionUtil.getNmsClass("Entity").getField("yaw");
            Field pitch = ReflectionUtil.getNmsClass("Entity").getField("pitch");
            Field lYaw = ReflectionUtil.getNmsClass("Entity").getField("lastYaw");
            Field lPitch = ReflectionUtil.getNmsClass("Entity").getField("lastPitch");
            Field aP = null;
            Field aQ = null;
            Field aO = null;

            if (Register.isOneNine()) {
                aP = ReflectionUtil.getNmsClass("EntityLiving").getField("aP");
                aQ = ReflectionUtil.getNmsClass("EntityLiving").getField("aQ");
                aO = ReflectionUtil.getNmsClass("EntityLiving").getField("aO");
            }

            yaw.setAccessible(true);
            pitch.setAccessible(true);
            lYaw.setAccessible(true);
            lPitch.setAccessible(true);

            if (Register.isOneNine()) {
                aP.setAccessible(true);
                aQ.setAccessible(true);
                aO.setAccessible(true);
            }

            yaw.set(npc, loc.getYaw());
            pitch.set(npc, loc.getPitch());

            if (Register.isOneNine()) {
                aP.set(npc, (loc.getYaw() - 90));
                aQ.set(npc, loc.getYaw());
                aO.set(npc, loc.getYaw());
            }

            ReflectionUtil.sendPacket(p, pPOPlayerInfo);
            ReflectionUtil.sendPacket(p, pPONamedEntitySpawn);
            ReflectionUtil.sendPacket(p, pPOEntityHeadRotation);
            ReflectionUtil.sendPacket(p, pPOEntityLook);

            Register.getPlayerLocationsFile().addPlayerToFile(loc, customName);
            FakeAPI.addFakePlayer(p, new FakePlayer(loc, customName, npc));

            new BukkitRunnable() {
                @Override
                public void run() {
                    ReflectionUtil.sendPacket(p, pPOPPlayerInfoDestory);

                    if (!Register.isOneNine()) {
                        GameProfile gameProfile = new GameProfile(UUIDFetcher.getUUID(p.getName()), p.getName());
                        ReflectionUtil.sendPacket(p, PacketUtil.createPlayerInfoPacket(REnumPlayerInfoAction.ADD_PLAYER.getPlayerInfoAction(),
                                gameProfile, 0, ReflectionUtil.getEnumGamemode(p),
                                p.getPlayerListName()));
                    }
                }
            }.runTaskLater(FakeAPI.getFakeAPI(), 20);
        } catch (IllegalAccessException | NoSuchFieldException | InstantiationException | SecurityException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void movePlayerForAll(double x, double y, double z, Object npc) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            movePlayerForPlayer(p, x, y, z, npc);
        }
    }

    public static void movePlayerForPlayer(Player p, double x, double y, double z, Object npc) {
        try {
            Location l = FakeAPI.getFakePlayerByObject(p, npc).getCurrentlocation().clone().add(x, y, z);
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutEntity$PacketPlayOutRelEntityMove")
                    .getConstructor(int.class, byte.class, byte.class, byte.class, boolean.class)
                    .newInstance(
                            ReflectionUtil.getEntityID(npc),
                            ((byte) (x * 32)),
                            ((byte) (y * 32)),
                            ((byte) (z * 32)),
                            false));
            if (FakeAPI.getFakePlayerByObject(p, npc) != null)
                FakeAPI.getFakePlayerByObject(p, npc).setCurrentlocation(l);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void equipPlayerForAll(Object armorstand, ItemStack item, EquipSlot slot) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            equipPlayerForPlayer(p, armorstand, item, slot);
        }
    }

    public static void equipPlayerForPlayer(Player p, Object npc, ItemStack item, EquipSlot slot) {
        try {
            ReflectionUtil.sendPacket(p, ReflectionUtil.getNmsClass("PacketPlayOutEntityEquipment")
                    .getConstructor(int.class, int.class, ReflectionUtil.getNmsClass("ItemStack"))
                    .newInstance(
                            ReflectionUtil.getEntityID(npc),
                            slot.getNmsSlot(),
                            ReflectionUtil.getObjectNMSItemStack(item)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte toAngle(float v) {
        return (byte) ((int) (v * 256.0F / 360.0F));
    }

}
