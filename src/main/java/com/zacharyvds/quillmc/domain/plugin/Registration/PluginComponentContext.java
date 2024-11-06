package com.zacharyvds.quillmc.domain.plugin.Registration;

import com.zacharyvds.quillmc.domain.plugin.CustomPlugin;

import java.util.HashMap;
import java.util.Map;

public class PluginComponentContext {
    private final CustomPlugin plugin;
    private final Map<Class<?>, Object> components;

    public PluginComponentContext(CustomPlugin plugin) {
        this.plugin = plugin;
        this.components = new HashMap<>();
    }

    public <T> T getComponent(Class<T> componentClass){
        if(components.containsKey(componentClass)){
            return (T) components.get(componentClass);
        }else{
            try{
                T newComponent = componentClass.getDeclaredConstructor().newInstance();
                components.put(componentClass, newComponent);
                return newComponent;
            }catch (Exception e){
                throw new RuntimeException("Failed to instantiate component: " + componentClass.getName(), e);
            }
        }
    }
}