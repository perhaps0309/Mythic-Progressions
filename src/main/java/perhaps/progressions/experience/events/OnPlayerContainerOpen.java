package perhaps.progressions.experience.events;

import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class OnPlayerContainerOpen {
    @SubscribeEvent
    public static void onPlayerContainerOpen(ScreenEvent event) {
        Screen screen = event.getScreen();
        // Top 10 things to do later or something
    }
}
