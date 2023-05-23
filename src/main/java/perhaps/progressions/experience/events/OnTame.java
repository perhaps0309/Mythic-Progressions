package perhaps.progressions.experience.events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;

import static perhaps.progressions.experience.ExperienceManager.grantXP;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class OnTame {
    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onTame(AnimalTameEvent event) {
        Player player = event.getTamer();
        grantXP(player, "conjuration", "skill", 0.3f);
    }
}
