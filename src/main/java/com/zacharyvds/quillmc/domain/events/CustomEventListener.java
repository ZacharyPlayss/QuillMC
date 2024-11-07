package com.zacharyvds.quillmc.domain.events;

import com.zacharyvds.quillmc.domain.plugin.CustomPlugin;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class CustomEventListener<E extends Event> implements Listener {
    public CustomEventListener(){
    }

    public abstract void handleEvent(E event);
}