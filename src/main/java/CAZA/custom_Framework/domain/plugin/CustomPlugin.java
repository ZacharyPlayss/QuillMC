package CAZA.custom_Framework.domain.plugin;

import CAZA.custom_Framework.domain.plugin.Registration.RegistrationHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomPlugin extends JavaPlugin {
    private final RegistrationHandler registrationHandler;

    public CustomPlugin() {
        this.registrationHandler = new RegistrationHandler(this, getLogger());
    }

    @Override
    public void onEnable() {
        registrationHandler.registerAnnotatedClasses();
    }

    @Override public void onDisable(){

    }

}
