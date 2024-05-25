package net.qilla.worldprotection.Config;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class GetConfig {

    private static GetConfig instance;

    private GetConfig() {
    }

    public Location getSpawnLoc() {
        double x, y, z;
        float pitch, yaw;
        World world;
        try {
            JsonObject spawnLoc = GenericConfig.getInstance().getConfig().getAsJsonObject("spawnLoc");
            x = spawnLoc.get("x").getAsDouble();
            y = spawnLoc.get("y").getAsDouble();
            z = spawnLoc.get("z").getAsDouble();
            pitch = spawnLoc.get("pitch").getAsFloat();
            yaw = spawnLoc.get("yaw").getAsFloat();
            world = Bukkit.getWorld(spawnLoc.get("world").getAsString());
        } catch(NumberFormatException | NullPointerException | UnsupportedOperationException e) {
            return null;
        }
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static void resetConfig() {
        GenericConfig.getInstance().resetConfig();
    }

    public static GetConfig getInstance() {
        if(instance == null) {
            instance = new GetConfig();
        }
        return instance;
    }
}
