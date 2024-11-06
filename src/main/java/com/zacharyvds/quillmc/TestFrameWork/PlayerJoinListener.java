package CAZA.custom_Framework.TestFrameWork;

import CAZA.custom_Framework.domain.events.CustomEventListener;
import CAZA.custom_Framework.domain.plugin.annotations.PluginRegistered;
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