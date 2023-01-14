package me.skmanux.bungeewhitelist.utils;

import net.md_5.bungee.api.ChatColor;

public class ChatUtils {
    public static String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
