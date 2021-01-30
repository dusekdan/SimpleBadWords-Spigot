package com.danieldusek.simplebadwords.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class BukkitUtils {
    /**
     * With great power comes great responsibility. Do not let players
     * supply their input as a parameter to this method.
     *
     * Anything fed to this method will be executed from the server console.
     * @param command Command to be executed.
     */
    public static void executeConsoleCommand(String command) {
        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), command);
    }
}