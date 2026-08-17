package dev.gatto.client.module.modules.combat;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CpsCounter extends Module {
    private final List<Long> clicks = new ArrayList<>();
    private boolean wasPressed = false;

    public CpsCounter() {
        super("CPS", "Shows clicks per second", Category.COMBAT);
        setEnabled(true);
    }

    @Override
    public void onTick() {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        boolean pressed = client.options.keyAttack.isDown();
        if (pressed && !wasPressed) {
            clicks.add(System.currentTimeMillis());
        }
        wasPressed = pressed;

        long now = System.currentTimeMillis();
        Iterator<Long> it = clicks.iterator();
        while (it.hasNext()) {
            if (now - it.next() > 1000) {
                it.remove();
            }
        }
    }

    @Override
    public void onHudRender(GuiGraphics graphics, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        String text = "CPS: " + clicks.size();
        graphics.drawString(client.font, text, 4, 40, 0xFFFFFFFF, true);
    }
}
