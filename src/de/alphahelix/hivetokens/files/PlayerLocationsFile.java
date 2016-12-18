package de.alphahelix.hivetokens.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.hivetokens.FakeAPI;
import org.bukkit.Location;

import java.util.HashMap;

/**
 * Created by AlphaHelixDev.
 */
public class PlayerLocationsFile extends SimpleFile<FakeAPI> {

    public PlayerLocationsFile(FakeAPI pl) {
        super("plugins/HiveTokens", "playerslocations.ht", pl);
    }

    public void addPlayerToFile(Location loc, String name) {
        if(!configContains(name)) {
            setLocation(name.replace(" ", "_").replace("§", "&")+".loc", loc, true);
        }
    }

    public HashMap<String, Location> getPacketPlayers() {
        HashMap<String, Location> playerMap = new HashMap<>();

        for(String names : getKeys(false)) {
            playerMap.put(names.replace("_", " ").replace("&", "§"), getLocation(names, true).build());
        }
        return playerMap;
    }

}
