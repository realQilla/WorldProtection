package net.qilla.worldprotection;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.qilla.worldprotection.Command.WorldProtectionCom;
import net.qilla.worldprotection.Config.GenericConfig;
import net.qilla.worldprotection.GeneralProtection.BuildProtection;
import net.qilla.worldprotection.GeneralProtection.EntityProtection;
import net.qilla.worldprotection.GeneralProtection.PlayerProtection;
import net.qilla.worldprotection.GeneralProtection.OtherProtection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class WorldProtection extends JavaPlugin {

    private final LifecycleEventManager<Plugin> manager = this.getLifecycleManager();

    @Override
    public void onEnable() {
        try {
            new GenericConfig(this);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        getServer().getPluginManager().registerEvents(new BuildProtection(), this);
        getServer().getPluginManager().registerEvents(new EntityProtection(), this);
        getServer().getPluginManager().registerEvents(new PlayerProtection(), this);
        getServer().getPluginManager().registerEvents(new OtherProtection(), this);
        registerCommands();
        InitialAction.setup();
    }

    private void registerCommands() {
        this.manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            new WorldProtectionCom().register(commands);
        });
    }

    @Override
    public void onDisable() {
    }

    public static WorldProtection getInstance() {
        return getPlugin(WorldProtection.class);
    }
}
