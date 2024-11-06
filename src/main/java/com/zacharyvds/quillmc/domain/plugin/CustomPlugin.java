package CAZA.custom_Framework.domain.plugin;

import CAZA.custom_Framework.domain.plugin.Registration.RegistrationHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomPlugin extends JavaPlugin {
    private final RegistrationHandler registrationHandler;
    private String basePackageName;
    public CustomPlugin() {
        this.registrationHandler = new RegistrationHandler(this, getLogger());
    }

    @Override
    public void onEnable() {
        registrationHandler.registerAnnotatedClasses();
    }

    @Override public void onDisable(){

    }

    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    public String getBasePackageName() {
        return basePackageName;
    }
}
