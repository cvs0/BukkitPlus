package com.cvs0.bukkitplus;

import java.util.Objects;
import java.util.logging.Logger;

import com.cvs0.bukkitplus.commands.ReloadConfigCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.raid.RaidTriggerEvent;

import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitPlus extends JavaPlugin implements Listener {
    public static Logger logger;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        logger = getLogger();
        config = getConfig();

        config.options().copyDefaults(true);

        saveDefaultConfig();

        Objects.requireNonNull(getCommand("reloadconfig")).setExecutor(new ReloadConfigCommand(this));
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void raidTriggerEvent(RaidTriggerEvent event) {
        String playerName = event.getPlayer().getName();

        if(config.getBoolean("removeRaids")) {
            event.setCancelled(true);

            logger.info("Stopped a raid triggered by: " + playerName);
        }
    }

    @EventHandler
    public void spawnEvent(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();

        if(entity.getType() == EntityType.PHANTOM && config.getBoolean("removePhantoms")) {
            event.setCancelled(true);
        }
    }
}
