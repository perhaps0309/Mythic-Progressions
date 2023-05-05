package perhaps.progressions;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.event.entity.player.PlayerEvent;
import perhaps.progressions.WheelSelectionScreenManager;

@Mod.EventBusSubscriber(modid = "progressions")
public class PlayerJoinEventHandler {
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        // Your event handling code...
        System.out.println("Player joined the game!");
    }
}