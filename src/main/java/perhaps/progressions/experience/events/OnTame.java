package perhaps.progressions.experience.events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.AnimalTameEvent;

import static perhaps.progressions.experience.ExperienceManager.grantXP;

public class OnTame {
    public static void onTame(AnimalTameEvent event) {
        Player player = event.getTamer();
        grantXP(player, "conjuration", "skill", 0.3f);
    }
}
