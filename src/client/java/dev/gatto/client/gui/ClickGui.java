package dev.gatto.client.gui;

import dev.gatto.client.GattoClient;
import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import dev.gatto.client.theme.ThemeManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class ClickGui extends Screen {
    private Category selectedCategory = Category.COMBAT;
    private Module selectedModule = null;
    private int panelX = 40;
    private int panelY = 40;
    private final int categoryWidth = 110;
    private final int moduleWidth = 160;
    private final int settingsWidth = 180;
    private final int categoryHeight = 20;
    private final int moduleHeight = 18;
    private boolean listeningForKeybind = false;

    public ClickGui() {
        super(Component.literal("Gatto Client"));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        graphics.fill(0, 0, width, height, 0x90000000);

        ThemeManager.Theme theme = GattoClient.getInstance().getThemeManager().getCurrent();

        graphics.drawString(font, GattoClient.CLIENT_NAME, panelX, panelY - 18, theme.accent, true);

        // Categories
        int y = panelY;
        for (Category cat : Category.values()) {
            boolean selected = cat == selectedCategory;
            boolean hovered = mouseX >= panelX && mouseX <= panelX + categoryWidth &&
                    mouseY >= y && mouseY <= y + categoryHeight;

            int bg = selected ? theme.accent : (hovered ? 0xFF22222E : theme.panel);
            graphics.fill(panelX, y, panelX + categoryWidth, y + categoryHeight, bg);
            graphics.drawString(font, cat.getDisplayName(), panelX + 8, y + 6, theme.text, false);
            y += categoryHeight + 2;
        }

        // Modules
        int modulesX = panelX + categoryWidth + 8;
        int modulesY = panelY;
        List<Module> modules = GattoClient.getInstance().getModuleManager().getModulesByCategory(selectedCategory);

        int panelHeight = Math.max(220, modules.size() * (moduleHeight + 2) + 16);
        graphics.fill(modulesX, modulesY, modulesX + moduleWidth, modulesY + panelHeight, theme.panel);

        int my = modulesY + 6;
        for (Module module : modules) {
            boolean hovered = mouseX >= modulesX && mouseX <= modulesX + moduleWidth &&
                    mouseY >= my && mouseY <= my + moduleHeight;
            boolean isSelected = module == selectedModule;

            if (hovered || isSelected) {
                graphics.fill(modulesX + 2, my, modulesX + moduleWidth - 2, my + moduleHeight, 0x22FFFFFF);
            }

            int color = module.isEnabled() ? theme.enabled : theme.textSecondary;
            graphics.drawString(font, module.getName(), modulesX + 8, my + 5, color, false);

            if (module.isEnabled()) {
                graphics.fill(modulesX + moduleWidth - 12, my + 6, modulesX + moduleWidth - 6, my + 12, theme.enabled);
            }

            my += moduleHeight + 2;
        }

        // Settings panel
        if (selectedModule != null) {
            int settingsX = modulesX + moduleWidth + 8;
            graphics.fill(settingsX, panelY, settingsX + settingsWidth, panelY + 220, theme.panel);

            graphics.drawString(font, selectedModule.getName(), settingsX + 8, panelY + 8, theme.accent, true);
            graphics.drawString(font, selectedModule.getDescription(), settingsX + 8, panelY + 22, theme.textSecondary, false);

            String keyName = selectedModule.getKeybind() == GLFW.GLFW_KEY_UNKNOWN
                    ? "None"
                    : GLFW.glfwGetKeyName(selectedModule.getKeybind(), 0);
            if (keyName == null) keyName = "Key " + selectedModule.getKeybind();

            String keybindText = listeningForKeybind ? "Press a key..." : "Keybind: " + keyName;
            graphics.drawString(font, keybindText, settingsX + 8, panelY + 48, theme.text, false);

            int sy = panelY + 70;
            for (var setting : selectedModule.getSettings()) {
                String valueStr = String.valueOf(setting.get());
                graphics.drawString(font, setting.getName() + ": " + valueStr, settingsX + 8, sy, theme.text, false);
                sy += 16;
            }
        }

        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int y = panelY;
        for (Category cat : Category.values()) {
            if (mouseX >= panelX && mouseX <= panelX + categoryWidth && mouseY >= y && mouseY <= y + categoryHeight) {
                selectedCategory = cat;
                selectedModule = null;
                listeningForKeybind = false;
                return true;
            }
            y += categoryHeight + 2;
        }

        int modulesX = panelX + categoryWidth + 8;
        int my = panelY + 6;
        List<Module> modules = GattoClient.getInstance().getModuleManager().getModulesByCategory(selectedCategory);
        for (Module module : modules) {
            if (mouseX >= modulesX && mouseX <= modulesX + moduleWidth && mouseY >= my && mouseY <= my + moduleHeight) {
                if (button == 0) {
                    module.toggle();
                } else if (button == 1) {
                    selectedModule = module;
                    listeningForKeybind = false;
                }
                return true;
            }
            my += moduleHeight + 2;
        }

        if (selectedModule != null) {
            int settingsX = modulesX + moduleWidth + 8;
            if (mouseX >= settingsX + 8 && mouseX <= settingsX + settingsWidth - 8 &&
                    mouseY >= panelY + 45 && mouseY <= panelY + 60) {
                listeningForKeybind = true;
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (listeningForKeybind && selectedModule != null) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_DELETE) {
                selectedModule.setKeybind(GLFW.GLFW_KEY_UNKNOWN);
            } else {
                selectedModule.setKeybind(keyCode);
            }
            listeningForKeybind = false;
            GattoClient.getInstance().getConfigManager().save();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
