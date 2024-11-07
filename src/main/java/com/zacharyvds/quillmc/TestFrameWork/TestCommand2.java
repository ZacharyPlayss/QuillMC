package com.zacharyvds.quillmc.TestFrameWork;

import com.zacharyvds.quillmc.domain.command.CustomCommandExecutor;
import com.zacharyvds.quillmc.domain.plugin.annotations.PluginRegistered;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Random;

@PluginRegistered(isCommand = true, commandName = "pizza")
public class TestCommand2 extends CustomCommandExecutor{

    private final PizzaService pizzaService;

    public TestCommand2(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage("Pizza command fired!");
        commandSender.sendMessage("You have ordered a: " + pizzaService.getRandomPizza());
        return true;
    }
}