package com.zacharyvds.quillmc.domain.server;

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class QuillServer{
    private String name;
    private Set<Player> players;
    private World world;

    public QuillServer(String name) {
        this.name = name;
        this.players = new HashSet<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.teleport(world.getSpawnLocation());
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public String getName() {
        return name;
    }
}
