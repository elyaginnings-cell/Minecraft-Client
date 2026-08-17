package dev.gatto.client.module.modules.hud;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.lwjgl.glfw.GLFW;

public class Keystrokes extends Module {
    public Keystrokes() {
        super("Keystrokes", "Shows WASD + mouse buttons", Category.HUD);
        setEnabled(true);
    }

    @Override
    public void onHudRender(GuiGraphics graphics, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        long window = client.getWindow().getWindow();

        int baseX = 10;
        int baseY = client.getWindow().getGuiScaledHeight() - 80;
        int size = 18;
        int gap = 2;

        drawKey(graphics, client, baseX + size + gap, baseY, size, "W",
                GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS);

        drawKey(graphics, client, baseX, baseY + size + gap, size, "A",
                GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS);
        drawKey(graphics, client, baseX + size + gap, baseY + size + gap, size, "S",
                GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS);
        drawKey(graphics, client, baseX + 2 * (size + gap), baseY + size + gap, size, "D",
                GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS);

        drawKey(graphics, client, baseX, baseY + 2 * (size + gap), size + gap + size / 2, "LMB",
                GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS);
        drawKey(graphics, client, baseX + size + gap + size / 2 + gap, baseY + 2 * (size + gap), size + gap + size / 2, "RMB",
                GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS);

        drawKey(graphics, client, baseX, baseY + 3 * (size + gap), 3 * size + 2 * gap, "SPACE",
                GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS);
    }

    private void drawKey(GuiGraphics graphics, Minecraft client, int x, int y, int w, String label, boolean pressed) {
        int bg = pressed ? 0xAAFFFFFF : 0x66000000;
        int textColor = pressed ? 0xFF000000 : 0xFFFFFFFF;
        graphics.fill(x, y, x + w, y + 18, bg);
        int textWidth = client.font.width(label);
        graphics.drawString(client.font, label, x + (w - textWidth) / 2, y + 5, textColor, false);
    }
}
