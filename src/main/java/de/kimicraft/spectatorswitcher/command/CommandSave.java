package de.kimicraft.spectatorswitcher.command;

import de.kimicraft.spectatorswitcher.SpectatorSwitcher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandSave implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        SpectatorSwitcher.instance.savePluginConfig();
        return true;
    }
}
