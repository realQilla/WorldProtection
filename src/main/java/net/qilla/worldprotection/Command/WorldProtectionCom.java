package net.qilla.worldprotection.Command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Stream;

public final class WorldProtectionCom {

    private static final Set<UUID> worldProtectionMap = new HashSet<>();

    private final String argCommand = "worldproection";
    private final Collection<String> alias = List.of("wp");

    private final Commands commands;
    private final String argPlayer = "player";
    private final String argType = "type";

    public WorldProtectionCom(Commands commands) {
        this.commands = commands;
    }

    public void register() {
        final LiteralArgumentBuilder<CommandSourceStack> commandNode = Commands
                .literal(argCommand)
                .requires(source -> source.getSender() instanceof Player && source.getSender().hasPermission("worldprotection.toggle"))
                .executes(this::setSelf);

        final RequiredArgumentBuilder<CommandSourceStack, String> playerNode = Commands
                .argument(argPlayer, StringArgumentType.word())
                .suggests((context, builder) -> {
                    final String argument = builder.getRemaining();

                    for(final Player player : Bukkit.getOnlinePlayers()) {
                        final String playerName = player.getName();
                        if(playerName.regionMatches(true, 0, argument, 0, argument.length()))
                            builder.suggest(playerName);
                    }
                    return builder.buildFuture();
                })
                .executes(this::usage);

        final ArgumentCommandNode<CommandSourceStack, String> typeNode = Commands
                .argument(argType, StringArgumentType.word())
                .suggests((context, builder) -> {
                    final String argument = builder.getRemaining();
                    Stream.of("DISABLE", "ENABLE").filter(s -> s.regionMatches(true, 0, argument, 0, argument.length())).forEach(builder::suggest);
                    return builder.buildFuture();
                })
                .executes(this::setTarget)
                .build();

        playerNode.then(typeNode);
        commandNode.then(playerNode);

        this.commands.register(commandNode.build(), alias);
    }

    private int usage(final CommandContext<CommandSourceStack> context) {
        context.getSource().getSender().sendMessage(MiniMessage.miniMessage().deserialize("<red>You must specify a protection type when specifying a player.</red>"));
        return Command.SINGLE_SUCCESS;
    }

    private int setSelf(CommandContext<CommandSourceStack> context) {
        final Player player = (Player) context.getSource().getSender();
        if(checkWorldProtection(player)) enable(player);
        else disable(player);
        return Command.SINGLE_SUCCESS;
    }

    private int setTarget(CommandContext<CommandSourceStack> context) {
        final Player player = (Player) context.getSource().getSender();
        final String playerName = context.getArgument(argPlayer, String.class);
        final String type = context.getArgument(argType, String.class);

        if(type.isEmpty()) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You must specify a protection type for the player.</red>"));
            return 0;
        }

        final Optional<Player> possiblePlayer = Optional.ofNullable(Bukkit.getPlayer(playerName));
        final Optional<String> possibleType = Optional.ofNullable(type);

        if(possiblePlayer.isEmpty()) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>That player either does not exist, or is not online.</red>"));
            return 0;
        }

        final Player target = possiblePlayer.get();

        if(Objects.equals(possibleType.get(), "DISABLE")) {
            disable(target);
            player.sendMessage(MiniMessage.miniMessage().deserialize("<yellow>World protection has been <red>DISABLED</red> for " + "<green>" + target.getName() + "</green>.</yellow>"));
        } else if(Objects.equals(possibleType.get(), "ENABLE")) {
            enable(target);
            player.sendMessage(MiniMessage.miniMessage().deserialize("<yellow>World protection has been <green>ENABLED</green> for " + "<green>" + target.getName() + "</green>.</yellow>"));
        } else {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>You must specify a valid protection type for the player.</red>"));
            return 0;
        }
        return Command.SINGLE_SUCCESS;
    }

    private void disable(Player player) {
        player.sendMessage(MiniMessage.miniMessage().deserialize("<yellow>World protection has been <red>DISABLED</red>.</yellow>"));
        addWorldProtection(player);
    }

    private void enable(Player player) {
        player.sendMessage(MiniMessage.miniMessage().deserialize("<yellow>World protection has been <green>ENABLED</green>.</yellow>"));
        removeWorldProtection(player);
    }

    public static boolean checkWorldProtection(Player player) {
        return worldProtectionMap.contains(player.getUniqueId());
    }

    public static void addWorldProtection(Player player) {
        worldProtectionMap.add(player.getUniqueId());
    }

    public static void removeWorldProtection(Player player) {
        worldProtectionMap.remove(player.getUniqueId());
    }
}