package de.alphahelix.hivetokens;

import de.alphahelix.hivetokens.files.ArmorstandLocationsFile;
import de.alphahelix.hivetokens.files.EndercrystalLocationsFile;

/**
 * Created by AlphaHelixDev.
 */
public class Register {

    private static HiveTokens hiveTokens;
    private static JoinListener joinListener;
    private static ArmorstandLocationsFile armorstandLocationsFile;
    private static EndercrystalLocationsFile endercrystalLocationsFile;

    public Register(HiveTokens hiveTokens) {
        Register.hiveTokens = hiveTokens;
    }

    public static HiveTokens getHiveTokens() {
        return hiveTokens;
    }

    public static ArmorstandLocationsFile getArmorstandLocationsFile() {
        return armorstandLocationsFile;
    }

    public static EndercrystalLocationsFile getEndercrystalLocationsFile() {
        return endercrystalLocationsFile;
    }

    public void initAll() {
        joinListener = new JoinListener(getHiveTokens(), this);
        armorstandLocationsFile = new ArmorstandLocationsFile(getHiveTokens());
        endercrystalLocationsFile = new EndercrystalLocationsFile(getHiveTokens());
    }
}
