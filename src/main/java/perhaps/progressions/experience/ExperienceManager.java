package perhaps.progressions.experience;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.IEventBus;
import perhaps.progressions.capabilities.perks.Perk;
import perhaps.progressions.capabilities.perks.PerkProvider;
import perhaps.progressions.capabilities.skills.Skill;
import perhaps.progressions.capabilities.skills.SkillProvider;
import perhaps.progressions.experience.events.*;

public class ExperienceManager {
    public enum XPTypes {
        SKILL,
        PERK,
        ABILITY
    }

    public static void initializeExperience(IEventBus eventBus) {
        // Skills
        eventBus.register(new OnBlockBreak());
        eventBus.register(new OnArrowLoose());
        eventBus.register(new OnTame());
        eventBus.register(new OnMovement());
        eventBus.register(new OnAttackEntity());
    }

    public static float calculateXP(float level, float baseXP) {
        return level * 1.15f + baseXP;
    }

    public static void grantXP(Player player, XPTypes type, String name, float amount) {
        if (type == XPTypes.SKILL) {
            player.getCapability(SkillProvider.playerSkillsCapability).ifPresent((skills) -> {
                Skill skill = skills.get(name);
                if (skill == null) return;

                skill.add("xp", calculateXP(skill.get("level"), amount));
            });
        } else if (type == XPTypes.PERK) {
            player.getCapability(PerkProvider.playerPerksCapability).ifPresent((perks) -> {
                Perk perk = perks.get(name);
                if (perk == null || perk.get("equipped") == 0f) return;

                perk.add("xp", calculateXP(perk.get("level"), amount));
            });
        }
    }
}
