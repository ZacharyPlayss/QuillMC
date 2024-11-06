package com.zacharyvds.quillmc;

import com.zacharyvds.quillmc.domain.plugin.CustomPlugin;

public final class Custom_Framework extends CustomPlugin {

    @Override
    public void onEnable() {
        //PLUGIN ENABLE LOGIC
        super.setBasePackageName(this.getClass().getPackageName());
        super.onEnable();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
