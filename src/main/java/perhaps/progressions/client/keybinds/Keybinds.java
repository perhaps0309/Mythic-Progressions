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
        ScrollWheel skillsScrollWheel = new ScrollWheel(rootScrollWheel);
        ScrollWheel abilitiesScrollWheel = new ScrollWheel(rootScrollWheel);
        ScrollWheel settingsScrollWheel = new ScrollWheel(rootScrollWheel);

        ScrollWheel skill1Wheel = new ScrollWheel(perksScrollWheel);

        rootScrollWheel.addOption(new WheelOption(PERKS_ICON, "Perks", "Select a perk to unlock", () -> {
            WheelSelectionScreenManager.openWheelSelectionScreen(player, perksScrollWheel);
        }));

        rootScrollWheel.addOption(new WheelOption(ABILITIES_ICON, "Abilities", "Gain an advantage", () -> {
            WheelSelectionScreenManager.openWheelSelectionScreen(player, abilitiesScrollWheel);
        }));

        rootScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skills", "Upgrade your skills", () -> {
            WheelSelectionScreenManager.openWheelSelectionScreen(player, skillsScrollWheel);
        }));

        rootScrollWheel.addOption(new WheelOption(STATS_ICON, "Stats", "View your stats", () -> {
            // Add code to execute when the Stats option is selected
        }));

        rootScrollWheel.addOption(new WheelOption(SETTINGS_ICON, "Settings", "Modify the mod's settings.", () -> {
            WheelSelectionScreenManager.openWheelSelectionScreen(player, settingsScrollWheel);
        }));


        perksScrollWheel.addOption(new WheelOption(PERKS_ICON, "Perk 1", "Perk 1 description", () -> {

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

        skill1Wheel.addOption(new WheelOption(PRESTIGE_ICON, "Prestige", "Prestige your skill", () -> {

        }));

        skill1Wheel.addOption(new WheelOption(UPGRADE_ICON, "Level Up", "Level up your skill", () -> {

        }));

        WheelOption skill1Option = new WheelOption(SKILLS_ICON, "Skill 1", "Prestige: 0\nLevel: 1/10\nXP: 57/100", () -> {

        });

        skill1Option.addHover(() -> {
            skill1Option.title = "Skill 2";
            skill1Option.description = "Prestige: 0\nLevel: 1/10\nXP: 58/100";
        });

        skill1Wheel.addOption(skill1Option);

        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skill 1", "Skill 1 description", () -> {
            WheelSelectionScreenManager.openWheelSelectionScreen(player, skill1Wheel);
        }));

        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skill 2", "Skill 2 description", () -> {
            // Add code to execute when Skill 2 is selected
        }));

        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skill 3", "Skill 3 description", () -> {
            // Add code to execute when Skill 3 is selected
        }));

        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skill 4", "Skill 4 description", () -> {
            // Add code to execute when Skill 4 is selected
        }));

        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skill 5", "Skill 5 description", () -> {
            // Add code to execute when Skill 5 is selected
        }));

        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skill 6", "Skill 6 description", () -> {
            // Add code to execute when Skill 6 is selected
        }));

        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skill 7", "Skill 7 description", () -> {
            // Add code to execute when Skill 7 is selected
        }));

        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skill 8", "Skill 8 description", () -> {
            // Add code to execute when Skill 8 is selected
        }));

        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skill 9", "Skill 9 description", () -> {
            // Add code to execute when Skill 9 is selected
        }));

        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skill 10", "Skill 10 description", () -> {
            // Add code to execute when Skill 10 is selected
        }));

        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skill 11", "Skill 11 description", () -> {
            // Add code to execute when Skill 11 is selected
        }));

        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skill 12", "Skill 12 description", () -> {
            // Add code to execute when Skill 12 is selected
        }));

        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skill 13", "Skill 13 description", () -> {
            // Add code to execute when Skill 13 is selected
        }));

        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skill 14", "Skill 14 description", () -> {
            // Add code to execute when Skill 14 is selected
        }));

        abilitiesScrollWheel.addOption(new WheelOption(ABILITIES_ICON, "Ability 1", "Ability 1 description", () -> {
            // Add code to execute when Ability 1 is selected
        }));

        abilitiesScrollWheel.addOption(new WheelOption(ABILITIES_ICON, "Ability 2", "Ability 2 description", () -> {
            // Add code to execute when Ability 2 is selected
        }));

        abilitiesScrollWheel.addOption(new WheelOption(ABILITIES_ICON, "Ability 3", "Ability 3 description", () -> {
            // Add code to execute when Ability 3 is selected
        }));

        abilitiesScrollWheel.addOption(new WheelOption(ABILITIES_ICON, "Ability 4", "Ability 4 description", () -> {
            // Add code to execute when Ability 4 is selected
        }));

        settingsScrollWheel.addOption(new WheelOption(MODE_ICON, "Switch Mode", "Changes to light/dark mode", () -> {
            darkMode = !darkMode;
            if(darkMode) {
                outlineColor = darkOutline;
                baseColor = darkBase;
            } else {
                outlineColor = lightOutline;
                baseColor = lightBase;
            }
        }));

        WheelSelectionScreenManager.openWheelSelectionScreen(player, rootScrollWheel);
    }
}