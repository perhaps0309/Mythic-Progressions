package perhaps.progressions.capabilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.intellij.lang.annotations.Identifier;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.capabilities.skills.SkillProvider;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class CapabilityHandler {
    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof ServerPlayer)) return;
        event.addCapability(new ResourceLocation(MythicProgressions.MOD_ID, "player_skills"), new SkillProvider());

        event.getObject().getCapability(SkillProvider.playerSkillsCapability).ifPresent(skills -> {
            skills.forEach((skillName, skill) -> {
                System.out.println(skill.getSkillData());
            });
        });
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
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(SkillProvider.class);
    }
}