package perhaps.progressions.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import perhaps.progressions.client.gui.scroll_wheels.WheelSelectionScreen;
import perhaps.progressions.client.gui.scroll_wheels.ScrollWheel;

public class WheelSelectionScreenManager {
    public static void openWheelSelectionScreen(Player player, ScrollWheel rootScrollWheel) {
        WheelSelectionScreen wheelSelectionScreen = new WheelSelectionScreen(rootScrollWheel);
        Minecraft.getInstance().setScreen(wheelSelectionScreen);
    }
}