package dev.gatto.client.theme;

import java.util.HashMap;
import java.util.Map;

public class ThemeManager {
    public static class Theme {
        public final String name;
        public final int background;
        public final int panel;
        public final int accent;
        public final int text;
        public final int textSecondary;
        public final int enabled;
        public final int disabled;

        public Theme(String name, int background, int panel, int accent, int text, int textSecondary, int enabled, int disabled) {
            this.name = name;
            this.background = background;
            this.panel = panel;
            this.accent = accent;
            this.text = text;
            this.textSecondary = textSecondary;
            this.enabled = enabled;
            this.disabled = disabled;
        }
    }

    private final Map<String, Theme> themes = new HashMap<>();
    private Theme current;

    public ThemeManager() {
        themes.put("Dark", new Theme(
                "Dark",
                0xFF0D0D12,
                0xFF16161F,
                0xFF7C5CFF,
                0xFFE8E8F0,
                0xFF9A9AB0,
                0xFF4ADE80,
                0xFF6B6B80
        ));
        themes.put("Light", new Theme(
                "Light",
                0xFFF5F5FA,
                0xFFFFFFFF,
                0xFF6D4AFF,
                0xFF1A1A24,
                0xFF5A5A70,
                0xFF16A34A,
                0xFF9CA3AF
        ));
        themes.put("Custom", new Theme(
                "Custom",
                0xFF0A0A10,
                0xFF12121C,
                0xFFFF6B9D,
                0xFFF0F0F8,
                0xFFA0A0B8,
                0xFF22C55E,
                0xFF707088
        ));
        current = themes.get("Dark");
    }

    public Theme getCurrent() {
        return current;
    }

    public String getCurrentThemeName() {
        return current.name;
    }

    public void setTheme(String name) {
        if (themes.containsKey(name)) {
            current = themes.get(name);
        }
    }

    public Map<String, Theme> getThemes() {
        return themes;
    }
}
