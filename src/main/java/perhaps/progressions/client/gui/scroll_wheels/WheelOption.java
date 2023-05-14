package perhaps.progressions.client.gui.scroll_wheels;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class WheelOption {
    public ResourceLocation icon;
    public String title;
    public String description;
    public Runnable action;
    public Runnable onHover;

    public SoundEvent soundEffect;
    public boolean playErrorSound = false;
    public Supplier<Boolean> conditionalCallback;
    public float soundVolume = 1.0f;

    public WheelOption(ResourceLocation icon, String title, String description, Runnable action) {
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.action = action;
    }

    public void addHover(Runnable onHover) {
        this.onHover = onHover;
    }
    public void addSound(float soundVolume, SoundEvent soundEffect, boolean playError, Supplier<Boolean> conditionalCallback) {
        this.soundEffect = soundEffect;
        this.playErrorSound = playError;
        this.conditionalCallback = conditionalCallback;
        this.soundVolume = soundVolume;
    }
}