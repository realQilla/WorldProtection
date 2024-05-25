package net.qilla.worldprotection.GeneralProtection;

import com.destroystokyo.paper.MaterialTags;
import net.qilla.worldprotection.Command.WorldProtectionCom;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class BuildProtection implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void blockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (!WorldProtectionCom.checkWorldProtection(player)) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void playerBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();

        if (!WorldProtectionCom.checkWorldProtection(player)) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void breakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!WorldProtectionCom.checkWorldProtection(player)) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void playerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block == null) return;
        if (!WorldProtectionCom.checkWorldProtection(player)) {
            if(cancelInteract(block)) event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void playerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (!WorldProtectionCom.checkWorldProtection(player)) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void hangingBreakByEntity(HangingBreakByEntityEvent event) {
        Entity entity = event.getRemover();

        if (!(entity instanceof Player player)) {
            event.setCancelled(true);
            return;
        }

        if (!WorldProtectionCom.checkWorldProtection(player)) event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void blockExplode(BlockExplodeEvent event) {
        event.blockList().clear();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void blockDropItem(BlockDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void blockFade(BlockFadeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void blockBurn(BlockBurnEvent event) {
        event.setCancelled(true);
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void blockSpread(BlockSpreadEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void blockPhysics(BlockPhysicsEvent event) {
        if(!MaterialTags.DOORS.isTagged(event.getBlock().getType())) event.setCancelled(true);
    }

    private boolean cancelInteract(Block block) {

        BlockState blockState = block.getState();
        Material material = block.getType();

        return MaterialTags.TRAPDOORS.isTagged(block) ||
                Tag.ANVIL.isTagged(material) ||
                Tag.FLOWER_POTS.isTagged(material) ||
                Tag.CANDLES.isTagged(material) ||
                blockState instanceof Inventory ||
                blockState instanceof TileState ||
                material.equals(Material.CRAFTING_TABLE);
    }
}