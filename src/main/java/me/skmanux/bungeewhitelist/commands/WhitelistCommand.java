package me.skmanux.bungeewhitelist.commands;

import me.skmanux.bungeewhitelist.BungeeWhitelist;
import me.skmanux.bungeewhitelist.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.util.List;

public class WhitelistCommand extends Command {

    Configuration cg = BungeeWhitelist.getInstance().cg;
    File cgFile = BungeeWhitelist.getInstance().file;

    List<String> whitelistedPlayers = cg.getStringList("whitelist.list");

    public WhitelistCommand() {
        super("bwl");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        CommandSender p = sender;

        switch (args.length) {
            case 0: {
                p.sendMessage(ChatUtils.format("&8» &7Running &6&lBungeeWhitelist &6V1.0 &7by &esk_Manux"));
                return;
            }

            case 1: {
                if (args[0].equalsIgnoreCase("on")) {
                    p.sendMessage(ChatUtils.format(cg.getString("messages.whitelist.enabled")));

                    cg.set("whitelist.enabled", true);

                    try {
                        ConfigurationProvider.getProvider(YamlConfiguration.class).save(cg, cgFile);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    try {
                        ConfigurationProvider.getProvider(YamlConfiguration.class).load(cgFile);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (args[0].equalsIgnoreCase("off")) {
                    p.sendMessage(ChatUtils.format(cg.getString("messages.whitelist.disabled")));

                    cg.set("whitelist.enabled", false);

                    try {
                        ConfigurationProvider.getProvider(YamlConfiguration.class).save(cg, cgFile);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    try {
                        ConfigurationProvider.getProvider(YamlConfiguration.class).load(cgFile);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            case 2: {
                if (args[0].equalsIgnoreCase("mode")) {
                    if (args[1].equalsIgnoreCase("username")) {
                        p.sendMessage(ChatUtils.format(cg.getString("messages.whitelist.username-mode")));

                        cg.set("whitelist.mode", 1);

                        try {
                            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cg, cgFile);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        try {
                            ConfigurationProvider.getProvider(YamlConfiguration.class).load(cgFile);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else if (args[1].equalsIgnoreCase("ip")) {
                        p.sendMessage(ChatUtils.format(cg.getString("messages.whitelist.ip-mode")));

                        cg.set("whitelist.mode", 2);

                        try {
                            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cg, cgFile);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        try {
                            ConfigurationProvider.getProvider(YamlConfiguration.class).load(cgFile);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        p.sendMessage(ChatUtils.format(cg.getString("messages.whitelist.unknown-mode")));
                    }
                } else if (args[0].equalsIgnoreCase("add")) {
                    p.sendMessage(ChatUtils.format(cg.getString("messages.whitelist.added").replaceAll("%target%", args[1])));

                    List<String> tempWhitelistedPlayers = whitelistedPlayers;
                    tempWhitelistedPlayers.add(args[1]);

                    cg.set("whitelist.list", tempWhitelistedPlayers);

                    try {
                        ConfigurationProvider.getProvider(YamlConfiguration.class).save(cg, cgFile);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    try {
                        ConfigurationProvider.getProvider(YamlConfiguration.class).load(cgFile);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    tempWhitelistedPlayers.clear();
                } else if (args[0].equalsIgnoreCase("remove")) {
                    p.sendMessage(ChatUtils.format(cg.getString("messages.whitelist.removed").replaceAll("%target%", args[1])));

                    List<String> tempWhitelistedPlayers = whitelistedPlayers;
                    tempWhitelistedPlayers.remove(args[1]);

                    cg.set("whitelist.list", tempWhitelistedPlayers);

                    try {
                        ConfigurationProvider.getProvider(YamlConfiguration.class).save(cg, cgFile);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    try {
                        ConfigurationProvider.getProvider(YamlConfiguration.class).load(cgFile);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    tempWhitelistedPlayers.clear();
                }

                return;
            }

            default: {
                if (!sender.hasPermission("whitelist.manage")) {
                    sender.sendMessage(ChatUtils.format(cg.getString("messages.no-perms")));
                    return;
                }

                p.sendMessage(ChatUtils.format(cg.getString("messages.whitelist.usage")));
                return;
            }
        }
    }
}
