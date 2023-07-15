package perhaps.progressions.skills.skills;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.capabilities.skills.Skill;
import perhaps.progressions.skills.Skills;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class Lumberjack {
    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        Level world = player.level;
        Skill skillInfo = Skills.getSkill("lumberjack", player);
        float level = skillInfo.get("level");
        if (level < 1f || world.isClientSide || event.getState().getMaterial() != Material.WOOD) return;

        event.setNewSpeed(event.getOriginalSpeed() + (2f * level));
    }
}