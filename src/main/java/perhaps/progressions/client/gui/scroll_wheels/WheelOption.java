package perhaps.progressions.client.gui.scroll_wheels;

import net.minecraft.resources.ResourceLocation;

public class WheelOption {
    public final ResourceLocation icon;
    public final String title;
    public final String description;
    public final Runnable action;

    public WheelOption(ResourceLocation icon, String title, String description, Runnable action) {
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.action = action;
    }
}