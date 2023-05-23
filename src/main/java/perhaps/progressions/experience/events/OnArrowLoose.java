package perhaps.progressions.experience.events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;

import static perhaps.progressions.experience.ExperienceManager.grantXP;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class OnArrowLoose {
    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onArrowLoose(ArrowLooseEvent event) {
        Player player = event.getPlayer();
        grantXP(player, "archery", "skill", 0.2f);
    }
}
