package dev.gatto.client.module.modules.render;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;

public class NoHurtCam extends Module {
    public NoHurtCam() {
        super("NoHurtCam", "Disables the hurt camera shake", Category.RENDER);
    }

    // Actual effect is applied via mixin later
}
