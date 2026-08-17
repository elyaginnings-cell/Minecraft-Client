package dev.gatto.client.module;

import dev.gatto.client.module.modules.combat.AttackIndicator;
import dev.gatto.client.module.modules.combat.CpsCounter;
import dev.gatto.client.module.modules.combat.TargetHud;
import dev.gatto.client.module.modules.hud.*;
import dev.gatto.client.module.modules.misc.ChatTimestamps;
import dev.gatto.client.module.modules.misc.SprintStatus;
import dev.gatto.client.module.modules.movement.ToggleSprint;
import dev.gatto.client.module.modules.movement.Zoom;
import dev.gatto.client.module.modules.render.CustomCrosshair;
import dev.gatto.client.module.modules.render.Fullbright;
import dev.gatto.client.module.modules.render.NoHurtCam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public void init() {
        register(new ToggleSprint());
        register(new Zoom());
        register(new Fullbright());
        register(new NoHurtCam());
        register(new CustomCrosshair());
        register(new CpsCounter());
        register(new AttackIndicator());
        register(new TargetHud());
        register(new FpsHud());
        register(new PingHud());
        register(new Coordinates());
        register(new ArmorHud());
        register(new PotionHud());
        register(new Keystrokes());
        register(new ClockHud());
        register(new SessionHud());
        register(new Watermark());
        register(new SprintStatus());
        register(new ChatTimestamps());
    }

    private void register(Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getModulesByCategory(Category category) {
        return modules.stream().filter(m -> m.getCategory() == category).toList();
    }

    public Optional<Module> getModule(String name) {
        return modules.stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst();
    }

    public <T extends Module> Optional<T> getModule(Class<T> clazz) {
        return modules.stream().filter(clazz::isInstance).map(clazz::cast).findFirst();
    }

    public void onTick() {
        for (Module module : modules) {
            if (module.isEnabled()) module.onTick();
        }
    }

    public void onRender(float tickDelta) {
        for (Module module : modules) {
            if (module.isEnabled()) module.onRender(tickDelta);
        }
    }
}
