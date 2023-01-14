package me.skmanux.bungeewhitelist.listeners;

import me.skmanux.bungeewhitelist.BungeeWhitelist;
import me.skmanux.bungeewhitelist.utils.ChatUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import java.util.List;
import java.util.logging.Level;

public class OnJoin implements Listener {

    Configuration cg = BungeeWhitelist.getInstance().cg;

    @EventHandler
    public void onPlayerJoin(ServerConnectEvent e) {
        ProxiedPlayer p = e.getPlayer();
        List<String> list = cg.getStringList("whitelist.list");

        if (!cg.getBoolean("whitelist.enabled")) {
            return;
        }

        if (cg.getInt("whitelist.mode") == 1) {
            String name = p.getName();

            if (!list.contains(name)) {
                p.disconnect(ChatUtils.format(cg.getString("whitelist.kick-message")));
                BungeeWhitelist.getInstance().getLogger().log(Level.INFO, "The player " + name + " tried to connect, but he isn't in the WhiteList.");
            }
        } else if (cg.getInt("whitelist.mode") == 0) {
            String ip = p.getAddress().getHostName();

            if (!list.contains(ip)) {
                p.disconnect(ChatUtils.format(cg.getString("whitelist.kick-message")));
                BungeeWhitelist.getInstance().getLogger().log(Level.INFO, "A player with the IP " + ip + " tried to connect, but he isn't in the WhiteList.");
            }
        }
    }
}
