package perhaps.progressions.client.keybinds;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.client.gui.WheelSelectionManager;
import perhaps.progressions.client.gui.scroll_wheels.WheelOption;

import static perhaps.progressions.client.gui.WheelSelectionManager.*;
import static perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen.*;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID, value = Dist.CLIENT)
public class Keybinds {
    public static KeyMapping openWheelSelectionScreen;
    public static KeyMapping reinitializeWheelSelection; // This is a temporary button for dev-use

    public static void init() {
        openWheelSelectionScreen = new KeyMapping("key.progressions.open_wheel_selection", GLFW.GLFW_KEY_P, "category.progressions");
        reinitializeWheelSelection = new KeyMapping("key.progressions.reinitialize_wheel_selection", GLFW.GLFW_KEY_J, "category.progressions");

        ClientRegistry.registerKeyBinding(openWheelSelectionScreen);
        ClientRegistry.registerKeyBinding(reinitializeWheelSelection);
    }

    @SubscribeEvent
    public static void onKeyPress(net.minecraftforge.client.event.InputEvent.KeyInputEvent event) {
        if (reinitializeWheelSelection.consumeClick()) {
            reinitializeWheel();
            return;
        }

        if (!openWheelSelectionScreen.consumeClick()) return;
        Player player = Minecraft.getInstance().player;

        if (player == null) return;
        if (!isWheelSetup) setupWheel(player);

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

//        skillsScrollWheel.addOption(new WheelOption(SKILLS_ICON, "Skill 1", "Skill 1 description", () -> {
//            WheelSelectionManager.openWheelSelectionScreen(player, skill1Wheel);
//        }));

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

        WheelSelectionManager.openWheelSelectionScreen(player, rootScrollWheel);
    }
}