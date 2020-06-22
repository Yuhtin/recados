package com.yuhtin.recados;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class YhRecados extends JavaPlugin implements Listener {

    public FileConfiguration config;
    public boolean isPAPI;

    @Override
    public void onEnable() {
        if (!new File("config.yml").exists()) saveDefaultConfig();
        config = getConfig();

        Bukkit.getPluginManager().registerEvents(this, this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            isPAPI = true;
            System.out.println("O plugin 'PlaceholderAPI' foi encontrado, usando as placeholders registradas para ele");
        }
    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPermission(config.getString("permission"))) return;

        config.getStringList("recados").forEach(line -> {
            line = isPAPI ? PlaceholderAPI.setPlaceholders(event.getPlayer(), line) : line.replace("&", "§");

            event.getPlayer().sendMessage(line);
        });
    }

}
