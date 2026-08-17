package dev.gatto.client.mixin;

import dev.gatto.client.GattoClient;
import dev.gatto.client.module.modules.misc.ChatTimestamps;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    @ModifyVariable(method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V",
            at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private Component addTimestamp(Component message) {
        if (GattoClient.getInstance() == null) return message;

        boolean enabled = GattoClient.getInstance().getModuleManager()
                .getModule(ChatTimestamps.class)
                .map(m -> m.isEnabled())
                .orElse(false);

        if (!enabled) return message;

        String time = LocalTime.now().format(FORMAT);
        MutableComponent prefix = Component.literal("§7[" + time + "] §r");
        return prefix.append(message);
    }
}
