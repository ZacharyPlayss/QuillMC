package CAZA.custom_Framework.TestFrameWork;

import CAZA.custom_Framework.domain.command.CustomCommandExecutor;
import CAZA.custom_Framework.domain.plugin.annotations.PluginRegistered;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@PluginRegistered
public class TestCommand extends CustomCommandExecutor{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        commandSender.sendMessage("Test commando succesvol uitgevoerd!");
        return true;
    }
}