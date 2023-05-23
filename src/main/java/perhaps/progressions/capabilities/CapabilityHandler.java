package perhaps.progressions.capabilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.capabilities.perks.Perk;
import perhaps.progressions.capabilities.perks.PerkProvider;
import perhaps.progressions.capabilities.skills.Skill;
import perhaps.progressions.capabilities.skills.SkillProvider;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class CapabilityHandler {
    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player)) return;
        if (event.getObject().getCapability(SkillProvider.playerSkillsCapability).isPresent()) return;
        event.addCapability(new ResourceLocation(MythicProgressions.MOD_ID, "player_skills"), new SkillProvider());
        event.addCapability(new ResourceLocation(MythicProgressions.MOD_ID, "player_perks"), new PerkProvider());
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        event.getOriginal().getCapability(SkillProvider.playerSkillsCapability).ifPresent(originalSkills -> {
            event.getOriginal().getCapability(SkillProvider.playerSkillsCapability).ifPresent(clonedSkills -> {
                clonedSkills.forEach((skillName, skill) -> {
                    skill.copyFrom(originalSkills.get(skillName));
                });
            });
        });

        event.getOriginal().getCapability(PerkProvider.playerPerksCapability).ifPresent(originalPerks -> {
            event.getOriginal().getCapability(PerkProvider.playerPerksCapability).ifPresent(clonedPerks -> {
                clonedPerks.forEach((perkName, perk) -> {
                    perk.copyFrom(originalPerks.get(perkName));
                });
            });
        });
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(Skill.class);
        event.register(Perk.class);
    }
}