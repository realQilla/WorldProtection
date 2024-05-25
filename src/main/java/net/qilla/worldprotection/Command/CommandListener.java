package net.qilla.worldprotection.Command;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class CommandListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void playerQuitEvent(PlayerQuitEvent event) {
        WorldProtectionCom.removeWorldProtection(event.getPlayer());
    }
}
