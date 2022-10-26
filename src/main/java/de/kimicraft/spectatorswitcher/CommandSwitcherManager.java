package de.kimicraft.spectatorswitcher;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class CommandSwitcherManager {

    private static HashMap<UUID, Location> list = new HashMap<>();

    public static void setLocationForPlayer(Player player, Location location) {
        CommandSwitcherManager.list.put(player.getUniqueId(), location);
    }

    public static void setLocationForUUID(UUID uuid, Location location) {
        CommandSwitcherManager.list.put(uuid, location);
    }

    public static @Nullable Location getLocationForPlayer(UUID uuid) {
        return CommandSwitcherManager.list.get(uuid);
    }

    public static void loadConfig(FileConfiguration config) {
        ConfigurationSection storedLocations = config.getConfigurationSection("storedLocations");
        Logger logger = Bukkit.getLogger();

        if (storedLocations == null) {
            config.createSection("storedLocations");
            SpectatorSwitcher.instance.saveConfig();
        }

        logger.info("v - Loading stored locations...");
        for (String key : storedLocations.getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            Location location = storedLocations.getLocation(key);

            CommandSwitcherManager.setLocationForUUID(uuid, location);
            logger.info("| --- " + uuid.toString() + " : " + location.toString());
        }
        logger.info("^ - Load complete!");

    }

    public static void saveConfig(FileConfiguration config) {
        ConfigurationSection storedLocations = config.getConfigurationSection("storedLocations");
        Logger logger = Bukkit.getLogger();

        if (storedLocations == null) {
            config.createSection("storedLocations");
//            throw new RuntimeException("Ja Rofl");
        }

        logger.info("v - Storing locations...");
        for (Map.Entry<UUID, Location> entry : list.entrySet()) {
            UUID uuid = entry.getKey();
            Location location = entry.getValue();
            if(uuid == null || location == null) continue;
            storedLocations.set(uuid.toString(), location);
            logger.info("| --- " + uuid.toString()+ " : " + location.toString());
        }
        logger.info("^ - Store complete!");

        config.set("storedLocations", storedLocations);
    }

}