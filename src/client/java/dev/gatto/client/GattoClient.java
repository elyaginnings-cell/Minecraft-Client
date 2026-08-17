package dev.gatto.client;

import dev.gatto.client.config.ConfigManager;
import dev.gatto.client.gui.ClickGui;
import dev.gatto.client.hud.HudManager;
import dev.gatto.client.keybind.KeybindManager;
import dev.gatto.client.module.ModuleManager;
import dev.gatto.client.notification.NotificationManager;
import dev.gatto.client.theme.ThemeManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GattoClient implements ClientModInitializer {
    public static final String MOD_ID = "gatto-client";
    public static final String CLIENT_NAME = "Gatto Client";
    public static final Logger LOGGER = LoggerFactory.getLogger(CLIENT_NAME);

    private static GattoClient instance;

    private ModuleManager moduleManager;
    private ConfigManager configManager;
    private KeybindManager keybindManager;
    private HudManager hudManager;
    private ThemeManager themeManager;
    private NotificationManager notificationManager;
    private ClickGui clickGui;

    private KeyMapping openGuiKey;

    @Override
    public void onInitializeClient() {
        instance = this;
        LOGGER.info("Initializing {}...", CLIENT_NAME);

        this.themeManager = new ThemeManager();
        this.configManager = new ConfigManager();
        this.moduleManager = new ModuleManager();
        this.keybindManager = new KeybindManager();
        this.hudManager = new HudManager();
        this.notificationManager = new NotificationManager();
        this.clickGui = new ClickGui();

        moduleManager.init();
        configManager.load();

        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.gatto-client.open_gui",
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.gatto-client.main"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);

        LOGGER.info("{} initialized successfully!", CLIENT_NAME);
        notificationManager.add("Gatto Client loaded", NotificationManager.Type.SUCCESS);
    }

    private void onClientTick(Minecraft client) {
        if (client.player == null) return;

        while (openGuiKey.consumeClick()) {
            if (client.screen == null) {
                client.setScreen(clickGui);
            } else if (client.screen instanceof ClickGui) {
                client.setScreen(null);
            }
        }

        moduleManager.onTick();
        keybindManager.onTick();
        notificationManager.onTick();
    }

    public static GattoClient getInstance() {
        return instance;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public KeybindManager getKeybindManager() {
        return keybindManager;
    }

    public HudManager getHudManager() {
        return hudManager;
    }

    public ThemeManager getThemeManager() {
        return themeManager;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public ClickGui getClickGui() {
        return clickGui;
    }
}
