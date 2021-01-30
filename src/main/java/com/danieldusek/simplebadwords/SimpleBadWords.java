package com.danieldusek.simplebadwords;

import com.danieldusek.simplebadwords.commands.GeneralCommands;
import com.danieldusek.simplebadwords.model.OffenseLevel;
import com.danieldusek.simplebadwords.utils.BukkitUtils;
import com.danieldusek.simplebadwords.utils.ChatWrapper;
import com.danieldusek.simplebadwords.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class SimpleBadWords extends JavaPlugin implements Listener {

    public SBWConfig config;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Loading started");

        configSetup();

        getCommand("sbw").setExecutor(new GeneralCommands(this));
        Bukkit.getLogger().info("Commands registered");

        getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getLogger().info("Chat listener hooked. We're ready to go.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Disabling plugin. No actions to be performed.");
    }

    private void configSetup() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        this.config = new SBWConfig(this);

        Bukkit.getLogger().info("Config loaded");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player p = event.getPlayer();

        switch (this.inspectMessage(message)){
            case NO_OFFENSE:
                break;
            case LEVEL1:
                for (String punishmentCommand : this.config.level1Punishments()) {
                    punishPlayer(ChatWrapper.colorize(
                            punishmentCommand.replace("%player%", p.getName())
                    ));
                }
                break;
            case LEVEL2:
                for (String punishmentCommand : this.config.level2Punishments()) {
                    punishPlayer(ChatWrapper.colorize(
                            punishmentCommand.replace("%player%", p.getName())
                    ));
                }
                break;
        }
    }

    /**
     * Needs to be implemented like this, because it calls bukkit API from
     * asynchronous context. Failure to do it like this can result in
     * nullpointer exceptiosn in some cases.
     * @param command Command to be executed in a SERVER context
     */
    private void punishPlayer(String command) {
        new BukkitRunnable(){
            public void run(){
                BukkitUtils.executeConsoleCommand(command);
            }
        }.runTask(this);
    }

    private OffenseLevel inspectMessage(String message) {
        message = StringUtils.unaccent(message).toLowerCase();

        // Check message to contain words of interest
        // return higher offense level detected
        for (String word : this.config.level2WordsNormalized) {
            if (message.contains(word)) {
                Bukkit.getLogger().warning("[Level-2 Offense][Word:" + word + "][Message:" + message +"]");
                return OffenseLevel.LEVEL2;
            }
        }

        for (String word : this.config.level1WordsNormalized)
        {
            if (message.contains(word)) {
                Bukkit.getLogger().warning("[Level-1 Offense][Word:" + word + "][Message:" + message +"]");
                return OffenseLevel.LEVEL1;
            }
        }

        return OffenseLevel.NO_OFFENSE;
    }
}