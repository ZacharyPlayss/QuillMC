package com.zacharyvds.quillmc.TestFrameWork;

import com.zacharyvds.quillmc.domain.command.CustomCommandExecutor;
import com.zacharyvds.quillmc.domain.plugin.annotations.PluginRegistered;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@PluginRegistered
public class TestCommand extends CustomCommandExecutor{

    private final TestService testService;

    public TestCommand(TestService testService) {
        this.testService = testService;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        testService.someServiceMethod(commandSender);
        return true;
    }
}