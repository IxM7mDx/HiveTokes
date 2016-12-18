package de.alphahelix.hivetokens.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.hivetokens.FakeAPI;
import org.bukkit.Location;

import java.util.HashMap;

/**
 * Created by AlphaHelixDev.
 */
public class ArmorstandLocationsFile extends SimpleFile<FakeAPI> {

    public ArmorstandLocationsFile(FakeAPI pl) {
        super("plugins/HiveTokens", "armorstandlocations.ht", pl);
    }

    public void addArmorstandToFile(Location loc, String name) {
        if(!configContains(name)) {
            setLocation(name.replace(" ", "_").replace("§", "&"), loc, true);
        }
    }

    public HashMap<String, Location> getPacketArmorstand() {
        HashMap<String, Location> standsMap = new HashMap<>();

        for(String names : getKeys(false)) {
            standsMap.put(names.replace("_", " ").replace("&", "§"), getLocation(names, true).build());
        }
        return standsMap;
    }
}
