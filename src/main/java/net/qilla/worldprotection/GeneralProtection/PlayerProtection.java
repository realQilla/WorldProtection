package net.qilla.worldprotection.GeneralProtection;

import net.kyori.adventure.text.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.qilla.worldprotection.Command.WorldProtectionCom;
import net.qilla.worldprotection.DefaultVariables;
import net.qilla.worldprotection.Utils.SoundSystem;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;

public class PlayerProtection implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void playerRecipeUnlock(PlayerRecipeDiscoverEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void craftItem(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(event.getClickedInventory() == null) return;
        if(event.getClickedInventory().getSize() == 5) {
            event.setCancelled(true);
            SoundSystem.ACTION_NOT_ALLOWED.play(player);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void inventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();

        if(inventory == null) return;
        if(inventory.getSize() != 5) return;

        event.setCancelled(true);
        SoundSystem.ACTION_NOT_ALLOWED.play(player);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void playerAdvancement(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();
        Advancement advancement = event.getAdvancement();
        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        for(String criterion : progress.getAwardedCriteria()) progress.revokeCriteria(criterion);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void playerExpChange(PlayerExpChangeEvent event) {
        event.setAmount(0);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void foodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void playerJoin(PlayerJoinEvent event) {
        event.joinMessage(Component.text(""));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void playerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.quitMessage(Component.text(""));

        WorldProtectionCom.removeWorldProtection(player);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void playerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        event.deathMessage(Component.text(""));

        ServerPlayer nmsPlayer = ((CraftPlayer)player).getHandle();
        Packet<ClientGamePacketListener> packet = new ClientboundGameEventPacket(ClientboundGameEventPacket.IMMEDIATE_RESPAWN, 11);
        nmsPlayer.connection.sendPacket(packet);

        player.setRespawnLocation(DefaultVariables.getSpawnLoc(), true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void fallBelowVoid(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(player.getLocation().getY() < -64) player.setHealth(0);
    }
}