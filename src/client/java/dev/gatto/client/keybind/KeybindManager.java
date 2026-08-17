package dev.gatto.client.keybind;

import dev.gatto.client.GattoClient;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class KeybindManager {
    private final Map<Integer, Boolean> previousStates = new HashMap<>();

    public void onTick() {
        Minecraft client = Minecraft.getInstance();
        if (client.screen != null) return;

        long window = client.getWindow().getWindow();

        for (Module module : GattoClient.getInstance().getModuleManager().getModules()) {
            int key = module.getKeybind();
            if (key == GLFW.GLFW_KEY_UNKNOWN) continue;

            boolean isPressed = GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
            boolean wasPressed = previousStates.getOrDefault(key, false);

            if (isPressed && !wasPressed) {
                module.toggle();
            }

            previousStates.put(key, isPressed);
        }
    }
}
