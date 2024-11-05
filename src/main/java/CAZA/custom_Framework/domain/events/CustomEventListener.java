package CAZA.custom_Framework.domain.events;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class CustomEventListener<E extends Event> implements Listener {
    private final Plugin plugin;
    public CustomEventListener(Plugin plugin){
        if(plugin == null){
            throw new IllegalArgumentException("Plugin cannot be null or empty. Please provide a valid plugin class.");
        }
        this.plugin = plugin;
    }

    public abstract void handleEvent(E event);
}