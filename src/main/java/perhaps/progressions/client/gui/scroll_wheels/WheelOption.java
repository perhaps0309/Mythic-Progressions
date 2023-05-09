package perhaps.progressions.client.gui.scroll_wheels;

import net.minecraft.resources.ResourceLocation;

public class WheelOption {
    public ResourceLocation icon;
    public String title;
    public String description;
    public Runnable action;
    public Runnable onHover;

    public WheelOption(ResourceLocation icon, String title, String description, Runnable action) {
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.action = action;
    }

    public void addHover(Runnable onHover) {
        this.onHover = onHover;
    }
}