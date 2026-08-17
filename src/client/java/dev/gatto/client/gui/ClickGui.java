package dev.gatto.client.gui;

import dev.gatto.client.GattoClient;
import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import dev.gatto.client.theme.ThemeManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ClickGui extends Screen {
    private Category selectedCategory = Category.COMBAT;
    private int panelX = 40;
    private int panelY = 40;
    private final int panelWidth = 140;
    private final int categoryHeight = 22;
    private final int moduleHeight = 18;

    public ClickGui() {
        super(Component.literal("Gatto Client"));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        graphics.fill(0, 0, width, height, 0x80000000);

        ThemeManager.Theme theme = GattoClient.getInstance().getThemeManager().getCurrent();

        int y = panelY;
        for (Category cat : Category.values()) {
            boolean selected = cat == selectedCategory;
            int bg = selected ? theme.accent : theme.panel;
            graphics.fill(panelX, y, panelX + panelWidth, y + categoryHeight, bg);
            graphics.drawString(font, cat.getDisplayName(), panelX + 8, y + 7, theme.text, false);
            y += categoryHeight + 2;
        }

        int modulesX = panelX + panelWidth + 10;
        int modulesY = panelY;
        graphics.fill(modulesX, modulesY, modulesX + 180, modulesY + 300, theme.panel);

        List<Module> modules = GattoClient.getInstance().getModuleManager().getModulesByCategory(selectedCategory);
        int my = modulesY + 8;
        for (Module module : modules) {
            int color = module.isEnabled() ? theme.enabled : theme.textSecondary;
            graphics.drawString(font, module.getName(), modulesX + 8, my, color, false);
            my += moduleHeight;
        }

        graphics.drawString(font, GattoClient.CLIENT_NAME, panelX, panelY - 16, theme.accent, true);

        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int y = panelY;
        for (Category cat : Category.values()) {
            if (mouseX >= panelX && mouseX <= panelX + panelWidth && mouseY >= y && mouseY <= y + categoryHeight) {
                selectedCategory = cat;
                return true;
            }
            y += categoryHeight + 2;
        }

        int modulesX = panelX + panelWidth + 10;
        int modulesY = panelY + 8;
        List<Module> modules = GattoClient.getInstance().getModuleManager().getModulesByCategory(selectedCategory);
        int my = modulesY;
        for (Module module : modules) {
            if (mouseX >= modulesX && mouseX <= modulesX + 180 && mouseY >= my && mouseY <= my + moduleHeight) {
                module.toggle();
                return true;
            }
            my += moduleHeight;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
