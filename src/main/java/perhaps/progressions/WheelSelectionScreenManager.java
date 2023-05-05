package perhaps.progressions;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class WheelSelectionScreenManager {
    public static void openWheelSelectionScreen(Player player) {
        WheelSelectionScreen wheelSelectionScreen = new WheelSelectionScreen();
        Minecraft.getInstance().setScreen(wheelSelectionScreen);
    }
}