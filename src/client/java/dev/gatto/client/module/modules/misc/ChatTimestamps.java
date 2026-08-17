package dev.gatto.client.module.modules.misc;

import dev.gatto.client.module.Category;
import dev.gatto.client.module.Module;

public class ChatTimestamps extends Module {
    public ChatTimestamps() {
        super("ChatTimestamps", "Adds timestamps to chat messages", Category.MISC);
        setEnabled(true);
    }
}
