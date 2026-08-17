package dev.gatto.client.module.modules.render;

import dev.gatto.client.config.setting.BooleanSetting;
import dev.gatto.client.config.setting.ColorSetting;
import dev.gatto.client.config.setting.NumberSetting;
import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class CustomCrosshair extends Module {
    private final NumberSetting size = addSetting(new NumberSetting("Size", "Crosshair size", 6, 2, 20, 1));
    private final NumberSetting gap = addSetting(new NumberSetting("Gap", "Center gap", 2, 0, 10, 1));
    private final NumberSetting thickness = addSetting(new NumberSetting("Thickness", "Line thickness", 1, 1, 4, 1));
    private final BooleanSetting dot = addSetting(new BooleanSetting("Dot", "Center dot", true));
    private final BooleanSetting outline = addSetting(new BooleanSetting("Outline", "Black outline", true));
    private final ColorSetting color = addSetting(new ColorSetting("Color", "Crosshair color", 0xFFFFFFFF));

    public CustomCrosshair() {
        super("CustomCrosshair", "Customizable crosshair", Category.RENDER);
        setEnabled(true);
    }

    @Override
    public void onHudRender(GuiGraphics graphics, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        int cx = client.getWindow().getGuiScaledWidth() / 2;
        int cy = client.getWindow().getGuiScaledHeight() / 2;

        int s = size.getInt();
        int g = gap.getInt();
        int t = thickness.getInt();
        int col = color.get();

        if (outline.get()) {
            int o = 0xFF000000;
            drawLine(graphics, cx - s - g, cy, cx - g, cy, t + 2, o);
            drawLine(graphics, cx + g, cy, cx + s + g, cy, t + 2, o);
            drawLine(graphics, cx, cy - s - g, cx, cy - g, t + 2, o);
            drawLine(graphics, cx, cy + g, cx, cy + s + g, t + 2, o);
            if (dot.get()) {
                graphics.fill(cx - 1, cy - 1, cx + 2, cy + 2, o);
            }
        }

        drawLine(graphics, cx - s - g, cy, cx - g, cy, t, col);
        drawLine(graphics, cx + g, cy, cx + s + g, cy, t, col);
        drawLine(graphics, cx, cy - s - g, cx, cy - g, t, col);
        drawLine(graphics, cx, cy + g, cx, cy + s + g, t, col);

        if (dot.get()) {
            graphics.fill(cx, cy, cx + 1, cy + 1, col);
        }
    }

    private void drawLine(GuiGraphics graphics, int x1, int y1, int x2, int y2, int thickness, int color) {
        if (x1 == x2) {
            int minY = Math.min(y1, y2);
            int maxY = Math.max(y1, y2);
            graphics.fill(x1 - thickness / 2, minY, x1 + (thickness + 1) / 2, maxY, color);
        } else {
            int minX = Math.min(x1, x2);
            int maxX = Math.max(x1, x2);
            graphics.fill(minX, y1 - thickness / 2, maxX, y1 + (thickness + 1) / 2, color);
        }
    }
}
