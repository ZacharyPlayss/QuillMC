package com.zacharyvds.quillmc.TestFrameWork;

import com.zacharyvds.quillmc.domain.events.CustomEventListener;
import com.zacharyvds.quillmc.domain.plugin.annotations.PluginRegistered;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

@PluginRegistered
public class PlayerJoinListener extends CustomEventListener<PlayerJoinEvent> {

    public PlayerJoinListener(Plugin plugin) {
        super(plugin);
    }

    @EventHandler
    @Override
    public void handleEvent(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Welcome to the server!");
    }


}