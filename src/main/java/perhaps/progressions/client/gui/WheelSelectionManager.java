package perhaps.progressions.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import perhaps.progressions.abilities.Abilities;
import perhaps.progressions.client.gui.scroll_wheels.WheelOption;
import perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen;
import perhaps.progressions.client.gui.scroll_wheels.ScrollWheel;
import perhaps.progressions.perks.Perks;
import perhaps.progressions.skills.Skills;
import perhaps.progressions.stats.Stats;

import static perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen.*;

public class WheelSelectionManager {
    public static boolean isWheelSetup = false;
    public static ScrollWheel rootScrollWheel = new ScrollWheel(null);
    public static ScrollWheel perksScrollWheel = new ScrollWheel(rootScrollWheel);
    public static ScrollWheel skillsScrollWheel = new ScrollWheel(rootScrollWheel);
    public static ScrollWheel abilitiesScrollWheel = new ScrollWheel(rootScrollWheel);
    public static ScrollWheel settingsScrollWheel = new ScrollWheel(rootScrollWheel);

    public static ScrollWheel statsScrollWheel = new ScrollWheel(rootScrollWheel);
    public static Player globalPlayer;

    public static void setupWheel(Player player) {
        isWheelSetup = true;
        globalPlayer = player;

        rootScrollWheel.addOption(new WheelOption(PERKS_ICON, "Perk", "Select a perk to unlock", () -> {
            WheelSelectionManager.openWheelSelectionScreen(player, perksScrollWheel);
        }));

        rootScrollWheel.addOption(new WheelOption(ABILITIES_ICON, "Abilities", "Gain an advantage", () -> {
            WheelSelectionManager.openWheelSelectionScreen(player, abilitiesScrollWheel);
        }));

        rootScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skills", "Upgrade your skills", () -> {
            WheelSelectionManager.openWheelSelectionScreen(player, skillsScrollWheel);
        }));

        rootScrollWheel.addOption(new WheelOption(STATS_ICON, "Stats", "View your Stats", () -> {
            WheelSelectionManager.openWheelSelectionScreen(player, statsScrollWheel);
        }));

        rootScrollWheel.addOption(new WheelOption(SETTINGS_ICON, "Settings", "Modify the mod's settings.", () -> {
            WheelSelectionManager.openWheelSelectionScreen(player, settingsScrollWheel);
        }));

        settingsScrollWheel.addOption(new WheelOption(MODE_ICON, "Switch Mode", "Changes to light/dark mode", () -> {
            darkMode = !darkMode;
            if (darkMode) {
                outlineColor = darkOutline;
                baseColor = darkBase;
            } else {
                outlineColor = lightOutline;
                baseColor = lightBase;
            }
        }));

        Skills skills = new Skills();
        Abilities abilities = new Abilities();
        Perks perks = new Perks();
        Stats stats = new Stats();
        skills.initializeSkills();
        abilities.initializeAbilities();
        perks.initializePerks();
        stats.initializeStats();
    }

    public static void reinitializeWheel() {
        if (globalPlayer == null) return;
        System.out.println("Reinitializing wheel!");

        rootScrollWheel = new ScrollWheel(null);
        perksScrollWheel = new ScrollWheel(rootScrollWheel);
        skillsScrollWheel = new ScrollWheel(rootScrollWheel);
        abilitiesScrollWheel = new ScrollWheel(rootScrollWheel);
        settingsScrollWheel = new ScrollWheel(rootScrollWheel);
        statsScrollWheel = new ScrollWheel(rootScrollWheel);

        setupWheel(globalPlayer);
    }

    public static void openWheelSelectionScreen(Player player, ScrollWheel rootScrollWheel) {
        WheelSelectionScreen wheelSelectionScreen = new WheelSelectionScreen(rootScrollWheel);
        Minecraft.getInstance().setScreen(wheelSelectionScreen);
    }
}