package dev.gatto.client.module.modules.hud;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class FpsHud extends Module {
    public FpsHud() {
        super("FPS", "Shows current FPS", Category.HUD);
        setEnabled(true);
    }

    @Override
    public void onHudRender(GuiGraphics graphics, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        String text = "FPS: " + client.getFps();
        graphics.drawString(client.font, text, 4, 4, 0xFFFFFFFF, true);
    }
}
