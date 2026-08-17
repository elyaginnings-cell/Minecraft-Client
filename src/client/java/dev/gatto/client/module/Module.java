package dev.gatto.client.module;

import dev.gatto.client.GattoClient;
import dev.gatto.client.config.setting.Setting;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    private final String name;
    private final String description;
    private final Category category;
    private boolean enabled;
    private int keybind;
    private final List<Setting<?>> settings = new ArrayList<>();

    protected Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.enabled = false;
        this.keybind = GLFW.GLFW_KEY_UNKNOWN;
    }

    public void toggle() {
        setEnabled(!enabled, true);
    }

    public void setEnabled(boolean enabled) {
        setEnabled(enabled, true);
    }

    public void setEnabled(boolean enabled, boolean notify) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;
        if (enabled) {
            onEnable();
            if (notify && GattoClient.getInstance() != null) {
                GattoClient.getInstance().getNotificationManager().add(name + " enabled",
                    dev.gatto.client.notification.NotificationManager.Type.SUCCESS);
            }
        } else {
            onDisable();
            if (notify && GattoClient.getInstance() != null) {
                GattoClient.getInstance().getNotificationManager().add(name + " disabled",
                    dev.gatto.client.notification.NotificationManager.Type.INFO);
            }
        }
        if (notify && GattoClient.getInstance() != null) {
            GattoClient.getInstance().getConfigManager().save();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public List<Setting<?>> getSettings() {
        return settings;
    }

    protected <T extends Setting<?>> T addSetting(T setting) {
        settings.add(setting);
        return setting;
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}
    public void onRender(float tickDelta) {}
    public void onHudRender(net.minecraft.client.gui.GuiGraphics graphics, float tickDelta) {}
}
