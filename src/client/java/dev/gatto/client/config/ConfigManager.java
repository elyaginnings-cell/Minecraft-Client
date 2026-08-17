package dev.gatto.client.config;

import com.google.gson.*;
import dev.gatto.client.GattoClient;
import dev.gatto.client.config.setting.*;
import dev.gatto.client.module.Module;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    private final Path configDir;
    private final Path configFile;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ConfigManager() {
        this.configDir = FabricLoader.getInstance().getConfigDir().resolve("gatto-client");
        this.configFile = configDir.resolve("config.json");
        try {
            Files.createDirectories(configDir);
        } catch (IOException e) {
            GattoClient.LOGGER.error("Failed to create config directory", e);
        }
    }

    public void save() {
        try {
            JsonObject root = new JsonObject();

            JsonObject modulesObj = new JsonObject();
            for (Module module : GattoClient.getInstance().getModuleManager().getModules()) {
                JsonObject modObj = new JsonObject();
                modObj.addProperty("enabled", module.isEnabled());
                modObj.addProperty("keybind", module.getKeybind());

                JsonObject settingsObj = new JsonObject();
                for (Setting<?> setting : module.getSettings()) {
                    if (setting instanceof BooleanSetting bs) {
                        settingsObj.addProperty(setting.getName(), bs.get());
                    } else if (setting instanceof NumberSetting ns) {
                        settingsObj.addProperty(setting.getName(), ns.get());
                    } else if (setting instanceof ModeSetting ms) {
                        settingsObj.addProperty(setting.getName(), ms.get());
                    } else if (setting instanceof ColorSetting cs) {
                        settingsObj.addProperty(setting.getName(), cs.get());
                    }
                }
                modObj.add("settings", settingsObj);
                modulesObj.add(module.getName(), modObj);
            }
            root.add("modules", modulesObj);
            root.addProperty("theme", GattoClient.getInstance().getThemeManager().getCurrentThemeName());
            root.addProperty("clientName", GattoClient.CLIENT_NAME);

            Files.writeString(configFile, gson.toJson(root));
            GattoClient.LOGGER.info("Config saved");
        } catch (Exception e) {
            GattoClient.LOGGER.error("Failed to save config", e);
        }
    }

    public void load() {
        if (!Files.exists(configFile)) {
            save();
            return;
        }

        try {
            String json = Files.readString(configFile);
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();

            if (root.has("modules")) {
                JsonObject modulesObj = root.getAsJsonObject("modules");
                for (Module module : GattoClient.getInstance().getModuleManager().getModules()) {
                    if (modulesObj.has(module.getName())) {
                        JsonObject modObj = modulesObj.getAsJsonObject(module.getName());
                        if (modObj.has("enabled") && modObj.get("enabled").getAsBoolean()) {
                            module.setEnabled(true);
                        }
                        if (modObj.has("keybind")) {
                            module.setKeybind(modObj.get("keybind").getAsInt());
                        }
                        if (modObj.has("settings")) {
                            JsonObject settingsObj = modObj.getAsJsonObject("settings");
                            for (Setting<?> setting : module.getSettings()) {
                                if (settingsObj.has(setting.getName())) {
                                    JsonElement el = settingsObj.get(setting.getName());
                                    if (setting instanceof BooleanSetting bs) {
                                        bs.set(el.getAsBoolean());
                                    } else if (setting instanceof NumberSetting ns) {
                                        ns.set(el.getAsDouble());
                                    } else if (setting instanceof ModeSetting ms) {
                                        ms.set(el.getAsString());
                                    } else if (setting instanceof ColorSetting cs) {
                                        cs.set(el.getAsInt());
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (root.has("theme")) {
                GattoClient.getInstance().getThemeManager().setTheme(root.get("theme").getAsString());
            }

            GattoClient.LOGGER.info("Config loaded");
        } catch (Exception e) {
            GattoClient.LOGGER.error("Failed to load config", e);
        }
    }

    public Path getConfigDir() {
        return configDir;
    }
}
