package perhaps.progressions.capabilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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
}