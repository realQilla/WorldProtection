package net.qilla.worldprotection.GeneralProtection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

public class EntityProtection implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void entitySpawn(EntitySpawnEvent event) {
        CreatureSpawnEvent.SpawnReason spawnReason = event.getEntity().getEntitySpawnReason();
        if (spawnReason.equals(CreatureSpawnEvent.SpawnReason.NATURAL) || spawnReason.equals(CreatureSpawnEvent.SpawnReason.DEFAULT)) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void entityExplode(EntityExplodeEvent event) {
        event.blockList().clear();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void entityChangeBlock(EntityChangeBlockEvent event) {
        event.setCancelled(true);
    }
}
