package com.zacharyvds.quillmc.domain.events;

import com.zacharyvds.quillmc.domain.plugin.CustomPlugin;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CustomEventListenerTest {

    private Plugin plugin;

    @BeforeEach
    void setUp() {
        plugin = mock(Plugin.class);
    }

    @Test
    void testConstructorThrowsExceptionWhenPluginIsNull() {
        //ARRANGE
        //ACT
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new CustomEventListenerImpl(null);
        });
        //ASSERT
        assertEquals("Plugin cannot be null or empty. Please provide a valid plugin class.", exception.getMessage());
    }

    @Test
    void testConstructorInitializesWithValidPlugin() {
        //ARRANGE
        //ACT
        CustomEventListener<Event> listener = new CustomEventListenerImpl(plugin);
        //ASSERT
        assertNotNull(listener, "Listener should be initialized with a valid plugin.");
    }

    @Test
    void testHandleEventCanBeOverridden() {
        //ARRANGE
        CustomEventListenerImpl listener = new CustomEventListenerImpl(plugin);
        Event mockEvent = mock(Event.class);
        //ACT
        listener.handleEvent(mockEvent);
        //ASSERT
        assertTrue(listener.isEventHandled(), "Expected the event to be handled.");
    }

    // Concrete implementation of CustomEventListener for testing purposes
    private static class CustomEventListenerImpl extends CustomEventListener<Event> {
        private boolean eventHandled = false;

        public CustomEventListenerImpl(Plugin plugin) {
            super(plugin);
        }

        @Override
        public void handleEvent(Event event) {
            this.eventHandled = true;
        }

        public boolean isEventHandled() {
            return eventHandled;
        }
    }

}