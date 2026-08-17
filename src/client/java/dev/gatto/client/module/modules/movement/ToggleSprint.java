package dev.gatto.client.module.modules.movement;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;

public class ToggleSprint extends Module {
    public ToggleSprint() {
        super("ToggleSprint", "Keeps sprint toggled on", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        Minecraft client = Minecraft.getInstance();
        if (client.player != null && client.options != null) {
            client.options.keySprint.setDown(true);
        }
    }

    @Override
    public void onDisable() {
        Minecraft client = Minecraft.getInstance();
        if (client.options != null) {
            client.options.keySprint.setDown(false);
        }
    }
}
