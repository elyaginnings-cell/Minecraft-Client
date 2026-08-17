package dev.gatto.client.module.modules.combat;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class AttackIndicator extends Module {
    public AttackIndicator() {
        super("AttackIndicator", "Shows attack cooldown progress", Category.COMBAT);
        setEnabled(true);
    }

    @Override
    public void onHudRender(GuiGraphics graphics, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        float cooldown = client.player.getAttackStrengthScale(0f);
        if (cooldown >= 1f) return;

        int width = client.getWindow().getGuiScaledWidth();
        int height = client.getWindow().getGuiScaledHeight();

        int barWidth = 40;
        int barHeight = 3;
        int x = (width - barWidth) / 2;
        int y = height / 2 + 12;

        graphics.fill(x, y, x + barWidth, y + barHeight, 0x88000000);
        int progress = (int) (barWidth * cooldown);
        int color = cooldown > 0.9f ? 0xFF55FF55 : 0xFFFFFF55;
        graphics.fill(x, y, x + progress, y + barHeight, color);
    }
}
