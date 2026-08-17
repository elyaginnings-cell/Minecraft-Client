package dev.gatto.client.module.modules.hud;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockHud extends Module {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ClockHud() {
        super("Clock", "Shows real-world time", Category.HUD);
        setEnabled(true);
    }

    @Override
    public void onHudRender(GuiGraphics graphics, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        String text = LocalTime.now().format(FORMAT);
        graphics.drawString(client.font, text, 4, 52, 0xFFFFFFFF, true);
    }
}
