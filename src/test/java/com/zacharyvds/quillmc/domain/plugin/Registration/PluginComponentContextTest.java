package com.zacharyvds.quillmc.domain.plugin.Registration;

import com.zacharyvds.quillmc.domain.plugin.CustomPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PluginComponentContextTest {
    private PluginComponentContext context;

    static class TestComponent {}
    static class AnotherTestComponent {}

    static class ComponentWithoutNoArgConstructor {
        private final String value;
        public ComponentWithoutNoArgConstructor(String value) {
            this.value = value;
        }
    }

    @BeforeEach
    void setUp() {
        CustomPlugin plugin = mock(CustomPlugin.class);
        context = new PluginComponentContext(plugin);
    }

    @Test
    void testGetComponentCreatesAndRetrievesNewComponent() {
        TestComponent component = context.getComponent(TestComponent.class);
        assertNotNull(component, "Expected component to be created and not null.");
    }

    @Test
    void testGetComponentReturnsSameInstanceForExistingComponent() {
        TestComponent firstInstance = context.getComponent(TestComponent.class);
        TestComponent secondInstance = context.getComponent(TestComponent.class);

        assertSame(firstInstance, secondInstance, "Expected the same instance to be returned for existing component.");
    }

    @Test
    void testGetComponentCreatesAndStoresDifferentComponents() {
        TestComponent testComponent = context.getComponent(TestComponent.class);
        AnotherTestComponent anotherTestComponent = context.getComponent(AnotherTestComponent.class);

        assertNotNull(testComponent, "Expected TestComponent to be created.");
        assertNotNull(anotherTestComponent, "Expected AnotherTestComponent to be created.");
        assertNotSame(testComponent, anotherTestComponent, "Expected different component instances.");
    }

    @Test
    void testGetComponentThrowsExceptionForComponentWithoutNoArgConstructor() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            context.getComponent(ComponentWithoutNoArgConstructor.class);
        });

        assertTrue(exception.getMessage().contains("Failed to instantiate component"),
                "Expected exception message to contain instantiation failure information.");
    }
}