package com.zacharyvds.quillmc.domain.plugin.Registration;

import com.zacharyvds.quillmc.domain.plugin.CustomPlugin;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class PluginComponentContext {
    private final CustomPlugin plugin;
    private final Map<Class<?>, Object> components;

    public PluginComponentContext(CustomPlugin plugin) {
        this.plugin = plugin;
        this.components = new HashMap<>();
    }

    public <T> T getComponent(Class<T> clazz) {
        if (components.containsKey(clazz)) {
            return (T) components.get(clazz);
        }
        try {
            T component = createInstanceWithDependencies(clazz);
            components.put(clazz, component);
            return component;
        } catch (Exception e) {
            throw new RuntimeException("Failed to resolve component: " + clazz.getSimpleName(), e);
        }
    }

    private <T> T createInstanceWithDependencies(Class<?> clazz) throws Exception {
        Constructor<?> chosenConstructor = getConstructorWithMostArguments(clazz.getDeclaredConstructors());
        if (chosenConstructor != null && chosenConstructor.getParameterCount() > 0) {
            Object[] parameters = Arrays.stream(chosenConstructor.getParameterTypes())
                    .map(parameterType -> getComponent(parameterType))
                    .toArray();

            return (T) chosenConstructor.newInstance(parameters);
        } else
            return (T) clazz.getDeclaredConstructor().newInstance();
    }

    private Constructor<?> getConstructorWithMostArguments(Constructor<?>[] constructors) {
        return Arrays.stream(constructors)
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElse(null);
    }

}