package net.qilla.worldprotection;

import net.qilla.worldprotection.Config.GetConfig;
import org.bukkit.Location;

public class DefaultVariables {

    private static Location spawnLoc;

    public static Location getSpawnLoc() {
        if(spawnLoc == null) {
            Location location = GetConfig.getInstance().getSpawnLoc();
            if(location == null) {
                GetConfig.resetConfig();
                spawnLoc = GetConfig.getInstance().getSpawnLoc();
                return spawnLoc;
            } else spawnLoc = location;
        }
        return spawnLoc;
    }
}