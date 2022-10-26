package de.kimicraft.spectatorswitcher.command;

import de.kimicraft.spectatorswitcher.CommandSwitcherManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class CommandMain implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return errorCondition(sender, "Does not work in console!");
        }

        if (!sender.hasPermission("spectatorswitcher.cmd.s")) {
            return errorCondition(sender, "You don`t have Permission to use this command!");
        }

        Player player = (Player) sender;
        Logger logger = Bukkit.getLogger();

        CommandIntend intend;
        switch (alias) {
            case "survival":
            case "sur":
                intend = CommandIntend.SURVIVAL;
                break;

            case "spectator":
            case "spec":
                intend = CommandIntend.SPECTATOR;
                break;

            default:
                intend = CommandIntend.TOGGLE;
                break;
        }


        if (intend == CommandIntend.TOGGLE) {
            logger.info("Player did not specify intent!");
            if (player.getGameMode() == GameMode.SPECTATOR) {
                intend = CommandIntend.SURVIVAL;
                logger.info("Guessing SURVIVAL!");
            } else {
                intend = CommandIntend.SPECTATOR;
                logger.info("Guessing SPECTATOR!");
            }
        }


        if (intend == CommandIntend.SPECTATOR) {
            if(player.getGameMode() == GameMode.SPECTATOR) {
                return errorCondition(player, "You are already in SPECTATOR");
            }

            Location location = player.getLocation();
            CommandSwitcherManager.setLocationForPlayer(player, location);

            player.setGameMode(GameMode.SPECTATOR);
            return successCondition(player, "Now Spectating. /s to return to start location.");
        }

        else if (intend == CommandIntend.SURVIVAL) {
            if(player.getGameMode() == GameMode.SURVIVAL) {
                return errorCondition(player, "You are already in SURVIVAL");
            }

            Location location = CommandSwitcherManager.getLocationForPlayer(player.getUniqueId());
            if (location != null) {
                player.teleport(location);
                player.setGameMode(GameMode.SURVIVAL);
                return successCondition(player, "Returned to survival location.");
            } else {
                player.setGameMode(GameMode.SURVIVAL);
                return errorCondition(player, "No previous position saved!");
            }
        }


        return true;
    }



    private @NotNull Boolean errorCondition(@NotNull CommandSender sender, String message) {
        sender.sendMessage(Component.text(message, NamedTextColor.RED));
        return true;
    }

    private @NotNull Boolean successCondition(@NotNull CommandSender sender, String message) {
        sender.sendMessage(Component.text(message, NamedTextColor.GREEN));
        return true;
    }
}
