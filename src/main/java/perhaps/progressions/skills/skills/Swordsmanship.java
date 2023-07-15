package perhaps.progressions.skills.skills;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.experience.events.OnAttackEntity;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class Swordsmanship {
    @SubscribeEvent
    public void doPostAttack(OnAttackEntity event) {

    }
}
