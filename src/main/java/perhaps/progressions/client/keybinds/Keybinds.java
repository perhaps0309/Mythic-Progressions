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

        WheelSelectionManager.openWheelSelectionScreen(player, rootScrollWheel);
    }
}