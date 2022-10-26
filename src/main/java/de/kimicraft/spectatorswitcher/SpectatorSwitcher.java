package de.kimicraft.spectatorswitcher;

import de.kimicraft.spectatorswitcher.command.CommandMain;
import de.kimicraft.spectatorswitcher.command.CommandSave;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpectatorSwitcher extends JavaPlugin {
    public static SpectatorSwitcher instance;

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getLogger().warning("Plugin SpectatorSwitcher enabled.");

        saveDefaultConfig();

        CommandSwitcherManager.loadConfig(getConfig());
        getCommand("s").setExecutor(new CommandMain());
        getCommand("spectatorSwitcherSave").setExecutor(new CommandSave());
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().warning("Plugin SpectatorSwitcher disabled.");
        savePluginConfig();
    }

    public void savePluginConfig() {
        CommandSwitcherManager.saveConfig(getConfig());
        saveConfig();
    }
}
