package dev.gatto.client.module.modules.render;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright", "Maximum brightness / night vision", Category.RENDER);
    }

    @Override
    public void onEnable() {
        apply();
    }

    @Override
    public void onDisable() {
        Minecraft client = Minecraft.getInstance();
        if (client.player != null) {
            client.player.removeEffect(MobEffects.NIGHT_VISION);
        }
    }

    @Override
    public void onTick() {
        apply();
    }

    private void apply() {
        Minecraft client = Minecraft.getInstance();
        if (client.player != null && !client.player.hasEffect(MobEffects.NIGHT_VISION)) {
            client.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, -1, 0, false, false, false));
        }
    }
}
