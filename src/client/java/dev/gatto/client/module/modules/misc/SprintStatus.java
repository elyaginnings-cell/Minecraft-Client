package dev.gatto.client.module.modules.misc;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class SprintStatus extends Module {
    public SprintStatus() {
        super("SprintStatus", "Shows if sprint is active", Category.MISC);
        setEnabled(true);
    }

    @Override
    public void onHudRender(GuiGraphics graphics, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        boolean sprinting = client.player.isSprinting();
        String text = sprinting ? "Sprinting" : "Walking";
        int color = sprinting ? 0xFF55FF55 : 0xFFAAAAAA;
        graphics.drawString(client.font, text, 4, 76, color, true);
    }
}
