# Gatto Client

A modular Fabric client for **Minecraft 1.21.11** focused on legitimate PvP utilities, a clean Click GUI, configurable HUD, and quality-of-life features.

**Default client name:** Gatto Client  
**Loader:** Fabric  
**Minecraft:** 1.21.11

## Features

### Core Systems
- Fully modular architecture (enable/disable + keybind + per-module settings)
- Persistent JSON config (`config/gatto-client/`)
- Theme system (Dark / Light / Custom)
- Notification system
- Click GUI with settings panel + keybind editor (Right Shift)

### Modules

**Movement**
- ToggleSprint
- Zoom (with amount setting)

**Render**
- Fullbright
- NoHurtCam

**Combat**
- CPS Counter
- Attack Cooldown Indicator

**HUD**
- FPS
- Ping
- Coordinates
- Armor durability
- Potion effects
- Keystrokes (WASD + LMB/RMB + Space)
- Clock (real time)
- Session timer
- Watermark

**Misc**
- Sprint Status

## Controls

- **Right Shift** — Open/Close Click GUI
- Left click module = Toggle
- Right click module = Open settings / keybind editor

## Building

```bash
./gradlew build
```

Output jar will be in `build/libs/`.

## Running in development

```bash
./gradlew runClient
```

---

Made for Gatto ✨
