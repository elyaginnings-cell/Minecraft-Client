package dev.gatto.client.module.modules.hud;

import dev.gatto.client.GattoClient;
import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class Watermark extends Module {
    public Watermark() {
        super("Watermark", "Client watermark", Category.HUD);
        setEnabled(true);
    }

    @Override
    public void onHudRender(GuiGraphics graphics, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        String text = GattoClient.CLIENT_NAME;
        int x = client.getWindow().getGuiScaledWidth() - client.font.width(text) - 4;
        graphics.drawString(client.font, text, x, 4, 0xFF7C5CFF, true);
    }
}
