package dev.gatto.client.keybind;

import dev.gatto.client.GattoClient;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class KeybindManager {
    public void onTick() {
        Minecraft client = Minecraft.getInstance();
        if (client.screen != null) return;

        long window = client.getWindow().getWindow();
        for (Module module : GattoClient.getInstance().getModuleManager().getModules()) {
            int key = module.getKeybind();
            if (key == GLFW.GLFW_KEY_UNKNOWN) continue;

            // Basic implementation - full edge detection can be added later
            if (GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS) {
                // placeholder for proper press tracking
            }
        }
    }
}
