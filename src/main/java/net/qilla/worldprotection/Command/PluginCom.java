package net.qilla.worldprotection.Command;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class PluginCom implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("worldprotection.plugins")) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>You do not have permission to execute this command.</red>"));
            return true;
        }

        String pluginOutput = Arrays.stream(Bukkit.getPluginManager().getPlugins())
                .map(plugin -> String.format(plugin.isEnabled() ? "<yellow>%s</yellow>" : "<red>%s</red>", plugin.getName()))
                .collect(Collectors.joining(", "));
        sender.sendMessage(MiniMessage.miniMessage().deserialize("\n<gold>Server Plugins:</gold>"));
        sender.sendMessage(MiniMessage.miniMessage().deserialize(pluginOutput + "<yellow>.</yellow>\n"));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}