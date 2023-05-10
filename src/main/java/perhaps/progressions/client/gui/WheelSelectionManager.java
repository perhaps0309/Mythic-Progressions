package perhaps.progressions.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import perhaps.progressions.abilities.Abilities;
import perhaps.progressions.client.gui.scroll_wheels.WheelOption;
import perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen;
import perhaps.progressions.client.gui.scroll_wheels.ScrollWheel;
import perhaps.progressions.skills.Skills;

import static perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen.*;

public class WheelSelectionManager {
    public static boolean isWheelSetup = false;
    public static ScrollWheel rootScrollWheel = new ScrollWheel(null);
    public static ScrollWheel perksScrollWheel = new ScrollWheel(rootScrollWheel);
    public static ScrollWheel skillsScrollWheel = new ScrollWheel(rootScrollWheel);
    public static ScrollWheel abilitiesScrollWheel = new ScrollWheel(rootScrollWheel);
    public static ScrollWheel settingsScrollWheel = new ScrollWheel(rootScrollWheel);
    public static Player global_player;

    public static void setupWheel(Player player) {
        isWheelSetup = true;
        global_player = player;

        rootScrollWheel.addOption(new WheelOption(PERKS_ICON, "Perks", "Select a perk to unlock", () -> {
            WheelSelectionManager.openWheelSelectionScreen(player, perksScrollWheel);
        }));

        rootScrollWheel.addOption(new WheelOption(ABILITIES_ICON, "Abilities", "Gain an advantage", () -> {
            WheelSelectionManager.openWheelSelectionScreen(player, abilitiesScrollWheel);
        }));

        rootScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skills", "Upgrade your skills", () -> {
            WheelSelectionManager.openWheelSelectionScreen(player, skillsScrollWheel);
        }));

        rootScrollWheel.addOption(new WheelOption(STATS_ICON, "Stats", "View your stats", () -> {
            // Add code to execute when the Stats option is selected
        }));

        rootScrollWheel.addOption(new WheelOption(SETTINGS_ICON, "Settings", "Modify the mod's settings.", () -> {
            WheelSelectionManager.openWheelSelectionScreen(player, settingsScrollWheel);
        }));

        Skills skills = new Skills();
        skills.initializeSkills();
        Abilities.initializeAbilities();
    }

    public static void reinitializeWheel() {
        System.out.println("Reinitializing wheel!");
        if (global_player == null) return;

        rootScrollWheel = new ScrollWheel(null);
        perksScrollWheel = new ScrollWheel(rootScrollWheel);
        skillsScrollWheel = new ScrollWheel(rootScrollWheel);
        abilitiesScrollWheel = new ScrollWheel(rootScrollWheel);
        settingsScrollWheel = new ScrollWheel(rootScrollWheel);

        setupWheel(global_player);
    }

    public static void openWheelSelectionScreen(Player player, ScrollWheel rootScrollWheel) {
        WheelSelectionScreen wheelSelectionScreen = new WheelSelectionScreen(rootScrollWheel);
        Minecraft.getInstance().setScreen(wheelSelectionScreen);
    }
}