package com.zacharyvds.quillmc.domain.plugin;

import com.zacharyvds.quillmc.domain.plugin.Registration.RegistrationHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomPlugin extends JavaPlugin {
    private final RegistrationHandler registrationHandler;
    private String basePackageName;

    public CustomPlugin() {
        this.registrationHandler = new RegistrationHandler(this, getLogger());
    }

    @Override
    public void onEnable() {
        registrationHandler.initializeRegistrations();
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
