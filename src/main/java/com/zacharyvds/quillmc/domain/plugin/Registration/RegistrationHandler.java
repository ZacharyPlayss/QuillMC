package com.zacharyvds.quillmc.domain.plugin.Registration;

import com.zacharyvds.quillmc.domain.command.CustomCommandExecutor;
import com.zacharyvds.quillmc.domain.events.CustomEventListener;
import com.zacharyvds.quillmc.domain.plugin.CustomPlugin;
import com.zacharyvds.quillmc.domain.plugin.annotations.PluginRegistered;
import com.zacharyvds.quillmc.domain.plugin.annotations.QuillService;
import com.zacharyvds.quillmc.domain.utils.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.logging.Logger;

public class RegistrationHandler {
    private final CustomPlugin plugin;
    private final Logger logger;
    private final PluginComponentContext pluginComponentContext;

    public RegistrationHandler(CustomPlugin plugin, Logger logger ) {
        this.plugin = plugin;
        this.logger = logger;
        this.pluginComponentContext = new PluginComponentContext(plugin);
    }

    public void initializeRegistrations(){
        registerQuillServices();
        registerPluginRegisteredAnottatedClasses();
    }

    private void registerQuillServices(){
        Set<Class<?>> annotatedClasses = ReflectionUtils.getClassesWithAnnotation(plugin.getBasePackageName(), QuillService.class);
        for(Class<?> clazz : annotatedClasses){
            if(QuillService.class.isAssignableFrom(clazz)){
                try{
                    pluginComponentContext.getComponent(clazz);
                    logger.info("Registered service: " + clazz.getSimpleName());
                }catch (Exception e){
                    logger.severe("Failed to registered service: " + clazz.getSimpleName());
                    e.printStackTrace();
                }
            }
        }
    }

    private void registerPluginRegisteredAnottatedClasses(){
        Set<Class<?>> annotatedClasses = ReflectionUtils.getClassesWithAnnotation(plugin.getBasePackageName(), PluginRegistered.class);

        for(Class<?> clazz : annotatedClasses){
            try{
                if (CustomEventListener.class.isAssignableFrom(clazz)) {
                    handleListenerRegistration(clazz);
                }
                if(CustomCommandExecutor.class.isAssignableFrom(clazz)){
                    Constructor<?>[] constructors = clazz.getDeclaredConstructors();
                    PluginRegistered annotation = clazz.getAnnotation(PluginRegistered.class);
                    boolean isCommand = annotation.isCommand();
                    String commandName = annotation.commandName();
                    if(constructors.length == 1 && constructors[0].getParameterCount() == 0){
                        handleCommandRegistrationWithEmptyConstructor(clazz, isCommand, commandName);
                    }else{
                        handleCommandRegistrationWithComponents(clazz, isCommand, commandName);
                    }
                }
            }catch(Exception e){
                logger.severe("Failed to register class: " + clazz.getSimpleName());
                e.printStackTrace();
            }
        }
    }

    private void handleCommandRegistrationWithComponents(Class<?> clazz, boolean isCommand, String commandName){
        try{
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            Constructor<?> chosenConstructor = getConstructorWithMostArguments(constructors);

            if(chosenConstructor != null){
                Class<?>[] parameterTypes = chosenConstructor.getParameterTypes();
                Object[] parameters = new Object[parameterTypes.length];
                for(int i = 0; i < parameterTypes.length; i++){
                    Class<?> paramType = parameterTypes[i];
                    Object component = pluginComponentContext.getComponent(paramType);
                    parameters[i] = component;
                }
                Object instance = chosenConstructor.newInstance(parameters);

                if(isCommand && commandName == null){
                    throw new RuntimeException("commandName must be set when isCommand is true.");
                }
                if(isCommand && commandName != null){
                    plugin.getCommand(commandName).setExecutor((CustomCommandExecutor) instance);
                    logger.info("Registered command executor: " + commandName);
                    return;
                }
                commandName = extractCommandName(clazz.getSimpleName());
                if (commandName != null) {
                    plugin.getCommand(commandName).setExecutor((CustomCommandExecutor) instance);
                    logger.info("Registered command executor with components: " + commandName);
                }
            }
        }catch (Exception e){
            logger.severe("Failed to register command with components: " + clazz.getSimpleName());
            e.printStackTrace();
        }
    }


    private String extractCommandName(String fullCommandClassName){
        if(fullCommandClassName.endsWith("Command")){
            return fullCommandClassName.substring(0, fullCommandClassName.length() - "Command".length()).toLowerCase();
        }
        return null;
    }

    protected void handleCommandRegistrationWithEmptyConstructor(Class<?> clazz, boolean isCommand, String commandName){
        try{
            Object instance = clazz.getDeclaredConstructor().newInstance();
            if(isCommand && commandName == null){
                throw new RuntimeException("commandName must be set when isCommand is true.");
            }
            if(isCommand && commandName != null){
                plugin.getCommand(commandName).setExecutor((CustomCommandExecutor) instance);
                logger.info("Registered command executor: " + commandName);
                return;
            }
            commandName = clazz.getSimpleName();
            if (commandName.endsWith("Command")) {
                commandName = commandName.substring(0, commandName.length() - "Command".length()).toLowerCase();
            }
            plugin.getCommand(commandName).setExecutor((CustomCommandExecutor) instance);
            logger.info("Registered command executor: " + commandName);
        }catch (Exception e){
            logger.severe("Failed to register command with empty constructor: " + clazz.getSimpleName());
            e.printStackTrace();
        }
    }

    protected void handleListenerRegistration(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Object instance = clazz.getDeclaredConstructor(Plugin.class).newInstance(plugin);
        Bukkit.getPluginManager().registerEvents((Listener) instance, plugin);
        logger.info("Registered event listener: " + clazz.getSimpleName());
    }

    private Constructor<?> getConstructorWithMostArguments(Constructor<?>[] constructors) {
        return Arrays.stream(constructors)
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElse(null);
    }

}