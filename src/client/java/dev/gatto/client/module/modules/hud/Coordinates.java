package dev.gatto.client.module.modules.hud;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;

public class Coordinates extends Module {
    public Coordinates() {
        super("Coordinates", "Shows player coordinates", Category.HUD);
        setEnabled(true);
    }

    @Override
    public void onHudRender(GuiGraphics graphics, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        BlockPos pos = client.player.blockPosition();
        String text = String.format("XYZ: %d %d %d", pos.getX(), pos.getY(), pos.getZ());
        graphics.drawString(client.font, text, 4, 16, 0xFFFFFFFF, true);
    }
}
