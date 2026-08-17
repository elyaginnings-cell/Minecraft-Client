package dev.gatto.client.module.modules.hud;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class ArmorHud extends Module {
    public ArmorHud() {
        super("ArmorHud", "Shows armor durability", Category.HUD);
        setEnabled(true);
    }

    @Override
    public void onHudRender(GuiGraphics graphics, float tickDelta) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        int x = client.getWindow().getGuiScaledWidth() / 2 + 20;
        int y = client.getWindow().getGuiScaledHeight() - 60;

        EquipmentSlot[] slots = {
                EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
        };

        for (EquipmentSlot slot : slots) {
            ItemStack stack = client.player.getItemBySlot(slot);
            if (!stack.isEmpty()) {
                graphics.renderItem(stack, x, y);
                if (stack.isDamageableItem()) {
                    int max = stack.getMaxDamage();
                    int dmg = stack.getDamageValue();
                    int remaining = max - dmg;
                    float pct = (float) remaining / max;
                    int color = pct > 0.5f ? 0xFF55FF55 : (pct > 0.2f ? 0xFFFFFF55 : 0xFFFF5555);
                    String text = String.valueOf(remaining);
                    graphics.drawString(client.font, text, x + 18, y + 4, color, true);
                }
                y += 18;
            }
        }
    }
}
