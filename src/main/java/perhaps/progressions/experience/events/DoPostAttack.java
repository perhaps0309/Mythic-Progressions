package perhaps.progressions.experience.events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;

import static perhaps.progressions.experience.ExperienceManager.grantXP;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class DoPostAttack {
    @SuppressWarnings("unused")
    public void doPostAttack(AttackEntityEvent event) {
        Player player = event.getPlayer();

        // Swordsmanship skill
        grantXP(player, "swordsmanship", "skill", 0.2f);
    }
}
