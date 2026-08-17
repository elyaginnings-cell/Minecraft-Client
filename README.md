# Gatto Client

A modular Fabric client for **Minecraft 1.21.11** focused on legitimate PvP utilities, a clean Click GUI, configurable HUD, and quality-of-life features.

**Default client name:** Gatto Client  
**Loader:** Fabric  
**Minecraft:** 1.21.11

## Features (in progress)

### Core
- Fully modular architecture (enable/disable + keybind + per-module settings)
- Persistent JSON config (`config/gatto-client/`)
- Theme system (Dark / Light / Custom)
- Notification system
- Click GUI (Right Shift by default)

### Modules currently implemented
- **ToggleSprint** (Movement)
- **Fullbright** (Render)
- **FPS HUD**
- **Watermark**

### Planned / Next
- Zoom, No Hurt Cam
- Coordinates, Ping, CPS, Keystrokes
- Armor HUD, Potion HUD
- Custom crosshair, attack indicators
- Full Click GUI with settings panels, search, sliders, color pickers
- HUD editor (drag & scale)

## Building

```bash
./gradlew build
```

## Running in development

```bash
./gradlew runClient
```

## Opening the Click GUI

Default keybind: **Right Shift**

---

Made for Gatto ✨
