package dev.gatto.client.module.modules.hud;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class SessionHud extends Module {
    private final long startTime = System.currentTimeMillis();

    public SessionHud() {
        super("Session", "Shows session playtime", Category.HUD);
        setEnabled(true);
    }

    @Override
    public void onHudRender(GuiGraphics graphics, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        long hours = elapsed / 3600;
        long minutes = (elapsed % 3600) / 60;
        long seconds = elapsed % 60;

        String text = String.format("Session: %02d:%02d:%02d", hours, minutes, seconds);
        graphics.drawString(client.font, text, 4, 64, 0xFFFFFFFF, true);
    }
}
