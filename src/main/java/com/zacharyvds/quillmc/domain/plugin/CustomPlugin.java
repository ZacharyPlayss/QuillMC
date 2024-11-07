package com.zacharyvds.quillmc.domain.plugin;

import com.zacharyvds.quillmc.domain.plugin.Registration.RegistrationHandler;
import com.zacharyvds.quillmc.domain.server.QuillServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CustomPlugin extends JavaPlugin {
    private static CustomPlugin instance;
    private final RegistrationHandler registrationHandler;
    private String basePackageName;
    private Map<String, QuillServer> virtualServers;
    public Map<Player, QuillServer> playerServerMap;

    public CustomPlugin() {
        this.registrationHandler = new RegistrationHandler(this, getLogger());
        this.virtualServers = new HashMap<>();
        this.playerServerMap = new HashMap<>();
    }

    @Override
    public void onEnable() {
        instance = this;
        registrationHandler.initializeRegistrations();
    }

    @Override public void onDisable(){
        instance = null;
    }

    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    public String getBasePackageName() {
        return basePackageName;
    }
    public static CustomPlugin getInstance() {
        return instance;
    }

    public QuillServer createVirtualServer(String serverName) {
        if (virtualServers.containsKey(serverName)) {
            return null; // Virtual server already exists
        }

        QuillServer newServer = new QuillServer(serverName) {
        }; // Create new virtual server
        virtualServers.put(serverName, newServer);
        return newServer;
    }

    public QuillServer getVirtualServer(String serverName) {
        return virtualServers.get(serverName);
    }

    // Method to remove a virtual server
    public void removeVirtualServer(String serverName) {
        virtualServers.remove(serverName);
    }

    // Method for players to switch between virtual servers
    public boolean switchToVirtualServer(Player player, String serverName) {
        QuillServer newServer = virtualServers.get(serverName);
        if (newServer == null) {
            player.sendMessage("Virtual server not found!");
            return false;
        }

        // Check if player is already on the requested server
        if (playerServerMap.get(player) == newServer) {
            player.sendMessage("You are already on that virtual server.");
            return false;
        }

        // Perform the actual switch (teleport the player to the correct world and handle other context changes)
        if (newServer instanceof QuillServer) {
            QuillServer virtualServer = (QuillServer) newServer;
            player.teleport(virtualServer.getWorld().getSpawnLocation());
            playerServerMap.put(player, virtualServer);
            player.sendMessage("Switched to virtual server: " + serverName);
            return true;
        }
        return false;
    }

    public QuillServer getPlayerVirtualServer(Player player) {
        return playerServerMap.get(player);
    }

    public Map<Player, QuillServer> getPlayerServerMap() {
        return playerServerMap;
    }

    public void setPlayerServerMap(Map<Player, QuillServer> playerServerMap) {
        this.playerServerMap = playerServerMap;
    }
}
