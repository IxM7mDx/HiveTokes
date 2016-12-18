package de.alphahelix.hivetokens.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.hivetokens.FakeAPI;
import org.bukkit.Location;

import java.util.HashMap;

/**
 * Created by AlphaHelixDev.
 */
public class EndercrystalLocationsFile extends SimpleFile<FakeAPI> {

    public EndercrystalLocationsFile(FakeAPI pl) {
        super("plugins/HiveTokens", "endercrystallocations.ht", pl);
    }

    public void addEndercrystalToFile(Location loc, String name) {
        if(!configContains(name)) {
            setLocation(name.replace(" ", "_").replace("§", "&"), loc, true);
        }
    }

    public HashMap<String, Location> getPacketEndercrystal() {
        HashMap<String, Location> crystalMap = new HashMap<>();

        for(String names : getKeys(false)) {
            crystalMap.put(names.replace("_", " ").replace("&", "§"), getLocation(names, true).build());
        }
        return crystalMap;
    }
}
