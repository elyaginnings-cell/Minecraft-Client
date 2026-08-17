package dev.gatto.client.module.modules.hud;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.effect.MobEffectInstance;

public class PotionHud extends Module {
    public PotionHud() {
        super("PotionHud", "Shows active potion effects", Category.HUD);
        setEnabled(true);
    }

    @Override
    public void onHudRender(GuiGraphics graphics, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        int x = client.getWindow().getGuiScaledWidth() - 120;
        int y = 30;

        for (MobEffectInstance effect : client.player.getActiveEffects()) {
            String name = effect.getEffect().value().getDisplayName().getString();
            int amplifier = effect.getAmplifier();
            int duration = effect.getDuration() / 20;

            String text = name;
            if (amplifier > 0) text += " " + (amplifier + 1);
            text += " " + formatTime(duration);

            graphics.drawString(client.font, text, x, y, 0xFFFFFFFF, true);
            y += 12;
        }
    }

    private String formatTime(int seconds) {
        int m = seconds / 60;
        int s = seconds % 60;
        return String.format("%d:%02d", m, s);
    }
}
