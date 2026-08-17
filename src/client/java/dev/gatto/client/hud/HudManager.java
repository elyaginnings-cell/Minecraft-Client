package dev.gatto.client.hud;

import dev.gatto.client.GattoClient;
import dev.gatto.client.module.Module;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.GuiGraphics;

public class HudManager {
    public HudManager() {
        HudRenderCallback.EVENT.register(this::onHudRender);
    }

    private void onHudRender(GuiGraphics graphics, float tickDelta) {
        for (Module module : GattoClient.getInstance().getModuleManager().getModules()) {
            if (module.isEnabled()) {
                module.onHudRender(graphics, tickDelta);
            }
        }
    }
}
