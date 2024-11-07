package com.zacharyvds.quillmc.TestFrameWork;

import com.zacharyvds.quillmc.domain.command.CustomCommandExecutor;
import com.zacharyvds.quillmc.domain.plugin.CustomPlugin;
import com.zacharyvds.quillmc.domain.plugin.annotations.PluginRegistered;
import com.zacharyvds.quillmc.domain.server.QuillServer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@PluginRegistered(isCommand = true, commandName = "vst")
public class VirtualServerTest extends CustomCommandExecutor {

    private CustomPlugin plugin = CustomPlugin.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Usage: /vst <create|join> <serverName>");
            return false;
        }

        String subcommand = args[0].toLowerCase();
        String serverName = args[1];

        switch (subcommand) {
            case "create":
                return handleCreateCommand(sender, serverName);
            case "join":
                return handleJoinCommand(sender, serverName);
            default:
                sender.sendMessage("Usage: /vst <create|join> <serverName>");
                return false;
        }
    }

    private boolean handleCreateCommand(CommandSender sender, String serverName) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can create virtual servers.");
            return false;
        }
        Player player = (Player) sender;

        QuillServer existingServer = plugin.getVirtualServer(serverName);
        if (existingServer != null) {
            sender.sendMessage("A virtual server with that name already exists!");
            return false;
        }
        QuillServer newServer = plugin.createVirtualServer(serverName);

        if (newServer instanceof QuillServer) {
            QuillServer virtualServer = (QuillServer) newServer;

            String worldName = "virtual_" + UUID.randomUUID().toString();
            World newWorld = Bukkit.createWorld(new WorldCreator(worldName));
            virtualServer.setWorld(newWorld);

            sender.sendMessage("Virtual server '" + serverName + "' has been created with world '" + worldName + "'.");
        } else {
            sender.sendMessage("Failed to create the virtual server.");
        }

        return true;
    }

    private boolean handleJoinCommand(CommandSender sender, String serverName) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can join virtual servers.");
            return false;
        }

        Player player = (Player) sender;

        if (serverName.equalsIgnoreCase("root") || serverName.isEmpty()) {
            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
            plugin.playerServerMap.put(player, null);
            player.sendMessage("You have returned to the root server.");
            return true;
        }
        boolean switched = plugin.switchToVirtualServer(player, serverName);
        if (!switched) {
            player.sendMessage("The specified virtual server does not exist or there was an error.");
        }

        return switched;
    }
}