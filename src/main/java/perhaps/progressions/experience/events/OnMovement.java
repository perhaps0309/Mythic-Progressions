package perhaps.progressions.experience.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static perhaps.progressions.experience.ExperienceManager.XPTypes.SKILL;
import static perhaps.progressions.experience.ExperienceManager.grantXP;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class OnMovement {
    private static final Map<UUID, Vec3> lastPlayerPositions = new HashMap<>();

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level world = player.level;
        UUID playerUUID = player.getUUID();
        if (world.isClientSide) return;
        if (!lastPlayerPositions.containsKey(playerUUID)) lastPlayerPositions.put(playerUUID, player.getEyePosition());

        Vec3 currentPlayerPosition = player.getEyePosition();
        Vec3 lastPlayerPosition = lastPlayerPositions.get(playerUUID);
        boolean isMoving = currentPlayerPosition.x() != lastPlayerPosition.x() || currentPlayerPosition.z() != lastPlayerPosition.z();

        grantXP(player, SKILL, "athletics", -1.05f);
        lastPlayerPositions.replace(playerUUID, currentPlayerPosition);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        Level world = player.level;
        if (world.isClientSide) return;

        grantXP(player, SKILL, "athletics", -1f);
    }
}
