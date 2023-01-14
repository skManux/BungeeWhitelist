package me.skmanux.bungeewhitelist;

import me.skmanux.bungeewhitelist.commands.WhitelistCommand;
import me.skmanux.bungeewhitelist.listeners.OnJoin;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;

public final class BungeeWhitelist extends Plugin {

    public File file;
    public Configuration cg;

    public BungeeWhitelist plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        getLogger().log(Level.INFO, "Loading Plugin...");

        createFiles();
        getLogger().log(Level.INFO, "Creating Files...");

        registerConfig();
        getLogger().log(Level.INFO, "Registering Config...");

        getProxy().getPluginManager().registerCommand(this, new WhitelistCommand());
        getLogger().log(Level.INFO, "Registering Commands...");

        getProxy().getPluginManager().registerListener(this, new OnJoin());
        getLogger().log(Level.INFO, "Registering Events...");

        getLogger().log(Level.INFO, "Plugin Loaded Successfully!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().log(Level.INFO, "Disabling Plugin...");

        getProxy().getPluginManager().unregisterCommands(this);
        getLogger().log(Level.INFO, "Unregistering Commands...");

        getProxy().getPluginManager().unregisterListeners(this);
        getLogger().log(Level.INFO, "Unregistering Events...");

        getLogger().log(Level.INFO, "Plugin Disabled Successfully!");
    }

    public void createFiles() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) {
            try {
                InputStream in = getResourceAsStream("config.yml");
                Files.copy(in, file.toPath());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void registerConfig() {
        try {
            cg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getDataFolder(), "config.yml"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static BungeeWhitelist getInstance() {
        return getInstance().plugin;
    }
}
