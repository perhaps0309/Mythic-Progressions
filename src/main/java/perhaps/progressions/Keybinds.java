package perhaps.progressions;

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
        if (openWheelSelectionScreen.consumeClick()) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                WheelSelectionScreenManager.openWheelSelectionScreen(player);
            }
        }
    }
}