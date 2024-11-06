package com.zacharyvds.quillmc.TestFrameWork;

import com.zacharyvds.quillmc.domain.command.CustomCommandExecutor;
import com.zacharyvds.quillmc.domain.plugin.annotations.PluginRegistered;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@PluginRegistered
public class TestCommand extends CustomCommandExecutor{

    private final TestService testService;

    public TestCommand(TestService testService) {
        this.testService = testService;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        testService.someServiceMethod(commandSender);
        return true;
    }
}