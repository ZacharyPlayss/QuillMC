package com.zacharyvds.quillmc.domain.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomEventDispatcher {
    private final List<CustomEventListener> listeners = new ArrayList<>();

    public void addListener(CustomEventListener listener) {
        if(listener != null){
            listeners.add(listener);
        }
    }

    public void removeListener(CustomEventListener listener) {
        listeners.remove(listener);
    }

    public void dispatchEvent(Event event){
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

}
