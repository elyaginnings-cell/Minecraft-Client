package dev.gatto.client.module.modules.movement;

import dev.gatto.client.config.setting.NumberSetting;
import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;

public class Zoom extends Module {
    private final NumberSetting zoomAmount = addSetting(new NumberSetting("Amount", "Zoom multiplier", 0.3, 0.1, 1.0, 0.05));
    private double originalFov = 70.0;

    public Zoom() {
        super("Zoom", "Zoom in with a keybind", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        Minecraft client = Minecraft.getInstance();
        if (client.options != null) {
            originalFov = client.options.fov().get();
            client.options.fov().set(originalFov * zoomAmount.get());
        }
    }

    @Override
    public void onDisable() {
        Minecraft client = Minecraft.getInstance();
        if (client.options != null) {
            client.options.fov().set(originalFov);
        }
    }
}
