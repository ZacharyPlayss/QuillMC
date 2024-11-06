package com.zacharyvds.quillmc.domain.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CustomCommandExecutorTest {

    @Mock
    private CommandSender mockCommandSender;
    @Mock
    private Command mockCommand;

    private CustomCommandExecutor customCommandExecutor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks
        customCommandExecutor = new CustomCommandExecutor();
    }

    @Test
    void testOnCommandReturnsFalseByDefault() {
        // ARRANGE
        // ACT
        boolean result = customCommandExecutor.onCommand(mockCommandSender, mockCommand, "commandName", new String[] {"arg1", "arg2"});
        //ASSERT
        assertFalse(result, "The onCommand method should return false.");
    }
}