package perhaps.progressions.experience.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.experience.ExperienceManager;

import static perhaps.progressions.experience.ExperienceManager.XPTypes.SKILL;
import static perhaps.progressions.experience.ExperienceManager.grantXP;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class OnAttackEntity {
    @SuppressWarnings("unused")
    public void doPostAttack(AttackEntityEvent event) {
        Player player = event.getPlayer();
        Level world = player.level;
        if (world.isClientSide) return;

        // Swordsmanship skill
        grantXP(player, SKILL, "swordsmanship", 0.2f);
    }
}
