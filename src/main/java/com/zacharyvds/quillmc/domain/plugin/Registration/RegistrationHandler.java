package CAZA.custom_Framework.domain.plugin.Registration;

import CAZA.custom_Framework.domain.command.CustomCommandExecutor;
import CAZA.custom_Framework.domain.events.CustomEventListener;
import CAZA.custom_Framework.domain.plugin.CustomPlugin;
import CAZA.custom_Framework.domain.plugin.annotations.PluginRegistered;
import CAZA.custom_Framework.domain.utils.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;


import java.util.Set;
import java.util.logging.Logger;

public class RegistrationHandler {
    private final CustomPlugin plugin;
    private final Logger logger;

    public RegistrationHandler(CustomPlugin plugin, Logger logger) {
        this.plugin = plugin;
        this.logger = logger;
    }

    public void registerAnnotatedClasses(){
        Set<Class<?>> annotatedClasses = ReflectionUtils.getClassesWithAnnotation(plugin.getBasePackageName(), PluginRegistered.class);

        for(Class<?> clazz : annotatedClasses){
            try{
                if (CustomEventListener.class.isAssignableFrom(clazz)) {
                    Object instance = clazz.getDeclaredConstructor(Plugin.class).newInstance(plugin);
                    Bukkit.getPluginManager().registerEvents((Listener) instance, plugin);
                    logger.info("Registered event listener: " + clazz.getSimpleName());
                }

                if(CustomCommandExecutor.class.isAssignableFrom(clazz)){
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    String commandName = clazz.getSimpleName();
                    if (commandName.endsWith("Command")) {
                        commandName = commandName.substring(0, commandName.length() - "Command".length()).toLowerCase();
                    }
                    plugin.getCommand(commandName).setExecutor((CustomCommandExecutor) instance);
                    logger.info("Registered command executor: " + commandName);
                }

            }catch(Exception e){
                logger.severe("Failed to register class: " + clazz.getSimpleName());
                e.printStackTrace();
            }
        }
    }

}