package com.zacharyvds.quillmc.domain.plugin.Registration;

import com.zacharyvds.quillmc.domain.command.CustomCommandExecutor;
import com.zacharyvds.quillmc.domain.events.CustomEventListener;
import com.zacharyvds.quillmc.domain.plugin.CustomPlugin;
import com.zacharyvds.quillmc.domain.plugin.annotations.PluginRegistered;
import com.zacharyvds.quillmc.domain.plugin.annotations.QuillService;
import com.zacharyvds.quillmc.domain.service.CustomService;
import com.zacharyvds.quillmc.domain.utils.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.logging.Logger;

public class NewRegistrationHandler {
    private final CustomPlugin plugin;
    private final Logger logger;
    private final PluginComponentContext pluginComponentContext;

    public NewRegistrationHandler(CustomPlugin plugin, Logger logger ) {
        this.plugin = plugin;
        this.logger = logger;
        this.pluginComponentContext = new PluginComponentContext(plugin);
    }

    public void initializeRegistrations(){
        registerAnnotatedClasses(QuillService.class, CustomService.class);
        registerAnnotatedClasses(PluginRegistered.class, CustomCommandExecutor.class);
        registerAnnotatedClasses(PluginRegistered.class, CustomEventListener.class);
    }

    public <T> void registerAnnotatedClasses(Class<? extends java.lang.annotation.Annotation> annotationClass, Class<T> toAssignClass) {
        Set<Class<?>> annotatedClasses = ReflectionUtils.getClassesWithAnnotation(plugin.getBasePackageName(), annotationClass);

        for (Class<?> clazz : annotatedClasses) {
            try {
                if (toAssignClass.isAssignableFrom(clazz)) {
                    T instance = createInstanceWithDependencies(clazz);

                    if (instance instanceof CustomCommandExecutor) {
                        handleCommandRegistration((CustomCommandExecutor) instance, clazz);
                        return;
                    }
                    if (instance instanceof Listener) {
                        handleListenerRegistration((Listener) instance, clazz);
                        return;
                    }
                    logger.info("Registered component: " + clazz.getSimpleName());
                    return;
                }
            } catch (Exception e) {
                logger.severe("Failed to register component: " + clazz.getSimpleName());
                e.printStackTrace();
            }
        }
    }

    private <T> T createInstanceWithDependencies(Class<?> clazz) throws Exception {
        Constructor<?> chosenConstructor = getConstructorWithMostArguments(clazz.getDeclaredConstructors());

        if (chosenConstructor != null && chosenConstructor.getParameterCount() > 0) {
            Object[] parameters = Arrays.stream(chosenConstructor.getParameterTypes())
                    .map(pluginComponentContext::getComponent)
                    .toArray();
            return (T) chosenConstructor.newInstance(parameters);
        } else {
            return (T) clazz.getDeclaredConstructor().newInstance();
        }
    }

    private Constructor<?> getConstructorWithMostArguments(Constructor<?>[] constructors) {
        return Arrays.stream(constructors)
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElse(null);
    }

    protected void handleCommandRegistration(CustomCommandExecutor instance, Class<?> clazz) {
        PluginRegistered annotation = clazz.getAnnotation(PluginRegistered.class);
        Boolean isCommand = annotation.isCommand();
        String commandName = annotation.commandName();

        if(isCommand == true && commandName == null){
            throw new RuntimeException("command name parameter cannot be null when isCommand == true.");
        }
        if(isCommand == false ){
            commandName = extractCommandName(clazz.getSimpleName());
        }
        plugin.getCommand(commandName).setExecutor(instance);
        logger.info("Registered command: " + clazz.getSimpleName());
    }

    protected void handleListenerRegistration(Listener listenerInstance,Class<?> clazz){
        Bukkit.getPluginManager().registerEvents(listenerInstance, plugin);
        logger.info("Registered event listener: " + clazz.getSimpleName());
    }

    private String extractCommandName(String className) {
        if (className.endsWith("Command")) {
            return className.substring(0, className.length() - "Command".length()).toLowerCase();
        }
        return null;
    }

}