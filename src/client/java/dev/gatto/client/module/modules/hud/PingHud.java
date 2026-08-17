package dev.gatto.client.module.modules.hud;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.PlayerInfo;

public class PingHud extends Module {
    public PingHud() {
        super("Ping", "Shows current ping", Category.HUD);
        setEnabled(true);
    }

    @Override
    public void onHudRender(GuiGraphics graphics, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.getConnection() == null) return;

        PlayerInfo info = client.getConnection().getPlayerInfo(client.player.getUUID());
        int ping = info != null ? info.getLatency() : 0;
        String text = "Ping: " + ping + "ms";
        graphics.drawString(client.font, text, 4, 28, 0xFFFFFFFF, true);
    }
}
