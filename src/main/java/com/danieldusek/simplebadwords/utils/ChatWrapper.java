package com.danieldusek.simplebadwords.utils;

import org.bukkit.ChatColor;

public class ChatWrapper {

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}