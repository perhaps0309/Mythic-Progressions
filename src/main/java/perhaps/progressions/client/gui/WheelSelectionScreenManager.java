package perhaps.progressions.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import perhaps.progressions.client.gui.WheelSelectionScreen;

public class WheelSelectionScreenManager {
    public static void openWheelSelectionScreen(Player player) {
        WheelSelectionScreen wheelSelectionScreen = new WheelSelectionScreen();
        Minecraft.getInstance().setScreen(wheelSelectionScreen);
    }
}