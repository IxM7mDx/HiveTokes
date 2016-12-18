package de.alphahelix.hivetokens;

import de.alphahelix.alphalibary.reflection.ReflectionUtil;
import de.alphahelix.hivetokens.files.ArmorstandLocationsFile;
import de.alphahelix.hivetokens.files.EndercrystalLocationsFile;
import de.alphahelix.hivetokens.files.PlayerLocationsFile;
import de.alphahelix.hivetokens.utils.ArmorstandUtil;

/**
 * Created by AlphaHelixDev.
 */
public class Register {

    private static boolean isOneNine;
    private static FakeAPI fakeAPI;
    private static JoinListener joinListener;
    private static ArmorstandLocationsFile armorstandLocationsFile;
    private static EndercrystalLocationsFile endercrystalLocationsFile;
    private static PlayerLocationsFile playerLocationsFile;

    public Register(FakeAPI fakeAPI) {
        Register.fakeAPI = fakeAPI;
    }

    public static FakeAPI getFakeAPI() {
        return fakeAPI;
    }

    public static ArmorstandLocationsFile getArmorstandLocationsFile() {
        return armorstandLocationsFile;
    }

    public static EndercrystalLocationsFile getEndercrystalLocationsFile() {
        return endercrystalLocationsFile;
    }

    public static boolean isOneNine() {
        return isOneNine;
    }

    public static PlayerLocationsFile getPlayerLocationsFile() {
        return playerLocationsFile;
    }

    public void initAll() {
        joinListener = new JoinListener(getFakeAPI(), this);
        armorstandLocationsFile = new ArmorstandLocationsFile(getFakeAPI());
        endercrystalLocationsFile = new EndercrystalLocationsFile(getFakeAPI());
        playerLocationsFile = new PlayerLocationsFile(getFakeAPI());
        new ArmorstandUtil();
        isOneNine = ReflectionUtil.getVersion().contains("1_9")
                || ReflectionUtil.getVersion().contains("1_10")
                || ReflectionUtil.getVersion().contains("1_11");
    }
}
