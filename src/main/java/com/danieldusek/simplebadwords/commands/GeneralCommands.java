package com.danieldusek.simplebadwords.commands;

import com.danieldusek.simplebadwords.SimpleBadWords;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class GeneralCommands implements CommandExecutor {

    SimpleBadWords instance;

    public GeneralCommands(SimpleBadWords instance){
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0) {

            //   RELOAD - simplebadwords.reload
            if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("simplebadwords.reload")) {
                Bukkit.getLogger().info("Reloading SimpleBadWords plugin.");

                // Basically just reload configs. This plugins does not have a
                // complex state which would need updating, so we're in the
                // clear here.
                this.instance.config.load();

                sender.sendMessage("§aSimpleBadWords plugin was reloaded per your request.");
            }

            //   ADD - simplebadwords.add
            if (args[0].equalsIgnoreCase("add") && sender.hasPermission("simplebadwords.add")) {
                if (args.length == 3) {

                    if (!args[1].equalsIgnoreCase("level1") && !args[1].equalsIgnoreCase("level2")) {
                        sender.sendMessage("§eUsage: /sbw add level1|level2 word_to_add");
                        return true;
                    }

                    // Update & reload config
                    ArrayList<String> tmp;
                    if (args[1].equalsIgnoreCase("level1")) {
                        tmp = new ArrayList<>(this.instance.config.level1Words());
                        tmp.add(args[2]);
                        this.instance.getConfig().set("words.level1", tmp);
                    } else {
                        tmp = new ArrayList<>(this.instance.config.level2Words());
                        tmp.add(args[2]);
                        this.instance.getConfig().set("words.level2", tmp);
                    }
                    this.instance.saveConfig();
                    this.instance.config.load();

                    sender.sendMessage("§aWord " + args[2] + " added to " + args[1] + "offenses.");
                    Bukkit.getLogger().info("§aWord " + args[2] + " added to " + args[1] + "offenses.");
                } else {
                    sender.sendMessage("§eUsage: /sbw add level1|level2 word_to_add");
                    return true;
                }
            }

            //   REMOVE - simplebadwords.remove
            if (args[0].equalsIgnoreCase("remove") && sender.hasPermission("simplebadwords.remove")) {
                if (args.length == 3) {

                    if (!args[1].equalsIgnoreCase("level1") && !args[1].equalsIgnoreCase("level2")) {
                        sender.sendMessage("§eUsage: /sbw remove level1|level2 word_to_add");
                        return true;
                    }

                    // Remove element from list by value
                    ArrayList<String> tmp;
                    if (args[1].equalsIgnoreCase("level1")) {
                        tmp = new ArrayList<>(this.instance.config.level1Words());
                        tmp.remove(args[2]);
                        this.instance.getConfig().set("words.level1", tmp);
                    } else {
                        tmp = new ArrayList<>(this.instance.config.level2Words());
                        tmp.remove(args[2]);
                        this.instance.getConfig().set("words.level2", tmp);
                    }
                    this.instance.saveConfig();
                    this.instance.config.load();

                    sender.sendMessage("§aWord " + args[2] + " removed from " + args[1] + "offenses.");
                    Bukkit.getLogger().info("§aWord " + args[2] + " removed from " + args[1] + "offenses.");

                } else {
                    sender.sendMessage("§eUsage: /sbw remove level1|level2 word_to_add");
                    return true;
                }
            }

            //   LIST - simplebadwords.list
            if (args[0].equalsIgnoreCase("list") && sender.hasPermission("simplebadwords.list")) {

                StringBuilder message = new StringBuilder("Level 1 offenses: \n");
                for (String word : this.instance.config.level1Words()) {
                    message.append("- ").append(word).append("\n");
                }
                message.append("Level 2 offenses:\n");
                for (String word : this.instance.config.level2Words()) {
                    message.append("- ").append(word).append("\n");
                }

                sender.sendMessage(message.toString());
            }

            return true;
        }

        return false;
    }
}
