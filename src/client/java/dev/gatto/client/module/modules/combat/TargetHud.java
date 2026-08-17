package dev.gatto.client.module.modules.combat;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class TargetHud extends Module {
    public TargetHud() {
        super("TargetHud", "Shows info about the entity you're looking at", Category.COMBAT);
        setEnabled(true);
    }

    @Override
    public void onHudRender(GuiGraphics graphics, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        if (client.hitResult == null || client.hitResult.getType() != HitResult.Type.ENTITY) return;

        Entity entity = ((EntityHitResult) client.hitResult).getEntity();
        if (!(entity instanceof LivingEntity living)) return;

        int x = client.getWindow().getGuiScaledWidth() / 2 + 20;
        int y = client.getWindow().getGuiScaledHeight() / 2 - 20;

        String name = living.getName().getString();
        float health = living.getHealth();
        float maxHealth = living.getMaxHealth();

        graphics.fill(x - 4, y - 4, x + 110, y + 28, 0x88000000);
        graphics.drawString(client.font, name, x, y, 0xFFFFFFFF, true);
        graphics.drawString(client.font, String.format("HP: %.1f / %.1f", health, maxHealth), x, y + 12, 0xFF55FF55, true);
    }
}
