package com.zacharyvds.quillmc.TestFrameWork;

import com.zacharyvds.quillmc.domain.command.CustomCommandExecutor;
import com.zacharyvds.quillmc.domain.plugin.annotations.PluginRegistered;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Random;

@PluginRegistered(isCommand = true, commandName = "pizza")
public class TestCommand2 extends CustomCommandExecutor{

    private final TestService testService;
    List<String> pizzaList = List.of("Margherita", "Pepperoni", "Hawaiian", "BBQ Chicken", "Veggie");

    public TestCommand2(TestService testService) {
        this.testService = testService;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Random random = new Random();
        String randomPizza = pizzaList.get(random.nextInt(pizzaList.size()));
        commandSender.sendMessage("You ordered a: " + randomPizza);
        return true;
    }
}