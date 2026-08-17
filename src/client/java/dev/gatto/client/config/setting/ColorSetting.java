package dev.gatto.client.config.setting;

public class ColorSetting extends Setting<Integer> {
    public ColorSetting(String name, String description, int defaultValue) {
        super(name, description, defaultValue);
    }

    public int getRed() {
        return (get() >> 16) & 0xFF;
    }

    public int getGreen() {
        return (get() >> 8) & 0xFF;
    }

    public int getBlue() {
        return get() & 0xFF;
    }

    public int getAlpha() {
        return (get() >> 24) & 0xFF;
    }

    public int withAlpha(int alpha) {
        return (get() & 0x00FFFFFF) | (alpha << 24);
    }
}
