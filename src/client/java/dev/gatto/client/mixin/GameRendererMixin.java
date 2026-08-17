package dev.gatto.client.mixin;

import dev.gatto.client.GattoClient;
import dev.gatto.client.module.modules.render.NoHurtCam;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "bobHurt", at = @At("HEAD"), cancellable = true)
    private void onBobHurt(CallbackInfo ci) {
        if (GattoClient.getInstance() == null) return;
        GattoClient.getInstance().getModuleManager().getModule(NoHurtCam.class).ifPresent(module -> {
            if (module.isEnabled()) {
                ci.cancel();
            }
        });
    }
}
