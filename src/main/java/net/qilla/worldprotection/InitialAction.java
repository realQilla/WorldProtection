package net.qilla.worldprotection;

import org.bukkit.Bukkit;

public class InitialAction {

    public static void setup() {
        Bukkit.setSpawnRadius(0);
        Bukkit.getWorlds().forEach(world -> {
            world.setSpawnLocation(DefaultVariables.getSpawnLoc());
        });
    }
}