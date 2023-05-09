package perhaps.progressions.client.keybinds;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.client.gui.WheelSelectionScreenManager;
import perhaps.progressions.client.gui.scroll_wheels.ScrollWheel;
import perhaps.progressions.client.gui.scroll_wheels.WheelOption;
import perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen;

import static perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen.*;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID, value = Dist.CLIENT)
public class Keybinds {
    public static KeyMapping openWheelSelectionScreen;

    public static void init() {
        Options options = Minecraft.getInstance().options;
        openWheelSelectionScreen = new KeyMapping("key.progressions.open_wheel_selection", GLFW.GLFW_KEY_P, "category.progressions");
        options.keyMappings = ArrayUtils.add(options.keyMappings, openWheelSelectionScreen);
    }

    @SubscribeEvent
    public static void onKeyPress(net.minecraftforge.client.event.InputEvent.KeyInputEvent event) {
        if (!openWheelSelectionScreen.consumeClick()) return;
        Player player = Minecraft.getInstance().player;

        if (player == null) return;
        // In your mod's initialization code...
        ScrollWheel rootScrollWheel = new ScrollWheel(null);
        ScrollWheel perksScrollWheel = new ScrollWheel(rootScrollWheel);

        rootScrollWheel.addOption(new WheelOption(PERKS_ICON, "Perks", "Select a perk to unlock", () -> {
            // Add code to execute when the Perks option is selected
            WheelSelectionScreenManager.openWheelSelectionScreen(player, perksScrollWheel);
        }));

        rootScrollWheel.addOption(new WheelOption(ABILITIES_ICON, "Abilities", "Gain an advantage", () -> {
            // Add code to execute when the Abilities option is selected
        }));

        rootScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skills", "Upgrade your skills", () -> {
            // Add code to execute when the Skills option is selected
        }));

        rootScrollWheel.addOption(new WheelOption(STATS_ICON, "Stats", "View your stats", () -> {
            // Add code to execute when the Stats option is selected
        }));

        rootScrollWheel.addOption(new WheelOption(SETTINGS_ICON, "Settings", "Modify the mod's settings.", () -> {

        }));

        perksScrollWheel.addOption(new WheelOption(PERKS_ICON, "Perk 1", "Perk 1 description", () -> {
            // Add code to execute when Perk 1 is selected
        }));

        perksScrollWheel.addOption(new WheelOption(PERKS_ICON, "Perk 2", "Perk 2 description", () -> {
            // Add code to execute when Perk 2 is selected
        }));

        perksScrollWheel.addOption(new WheelOption(PERKS_ICON, "Perk 3", "Perk 3 description", () -> {
            // Add code to execute when Perk 3 is selected
        }));

        perksScrollWheel.addOption(new WheelOption(PERKS_ICON, "Perk 4", "Perk 4 description", () -> {
            // Add code to execute when Perk 4 is selected
        }));

        perksScrollWheel.addOption(new WheelOption(PERKS_ICON, "Perk 5", "Perk 5 description", () -> {
            // Add code to execute when Perk 5 is selected
        }));

        perksScrollWheel.addOption(new WheelOption(PERKS_ICON, "Perk 6", "Perk 6 description", () -> {
            // Add code to execute when Perk 6 is selected
        }));

        perksScrollWheel.addOption(new WheelOption(PERKS_ICON, "Perk 7", "Perk 7 description", () -> {
            // Add code to execute when Perk 7 is selected
        }));

        perksScrollWheel.addOption(new WheelOption(PERKS_ICON, "Perk 8", "Perk 8 description", () -> {
            // Add code to execute when Perk 8 is selected
        }));

        WheelSelectionScreenManager.openWheelSelectionScreen(player, rootScrollWheel);
    }
}