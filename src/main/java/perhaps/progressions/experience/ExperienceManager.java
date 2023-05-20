package perhaps.progressions.experience;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.IEventBus;
import perhaps.progressions.capabilities.perks.Perk;
import perhaps.progressions.capabilities.perks.PerkProvider;
import perhaps.progressions.capabilities.skills.Skill;
import perhaps.progressions.capabilities.skills.SkillProvider;
import perhaps.progressions.experience.events.OnArrowLoose;
import perhaps.progressions.experience.events.OnBlockBreak;
import perhaps.progressions.experience.events.OnMovement;
import perhaps.progressions.experience.events.OnTame;

import java.util.Objects;

public class ExperienceManager {
    public static void initializeExperience(IEventBus eventBus) {
        // Skills
        eventBus.register(new OnBlockBreak());
        eventBus.register(new OnArrowLoose());
        eventBus.register(new OnTame());
        eventBus.register(new OnMovement());
    }

    public static float calculateXP(float level, float baseXP) {
        return (float) (level * 1.15 + baseXP);
    }

    public static void grantXP(Player player, String name, String type, float amount) {
        if (type.equals("skill")) {
            player.getCapability(SkillProvider.playerSkillsCapability).ifPresent((skills) -> {
                Skill skill = skills.get(type);
                if (skill == null) return;

                skill.add("xp", calculateXP(skill.get("level"), amount));
            });
        } else if (type.equals("perk")) {
            player.getCapability(PerkProvider.playerPerksCapability).ifPresent((perks) -> {
                Perk perk = perks.get(type);
                if (perk == null || perk.get("equipped") == 0f) return;

                perk.add("xp", calculateXP(perk.get("level"), amount));
            });
        }
    }
}
