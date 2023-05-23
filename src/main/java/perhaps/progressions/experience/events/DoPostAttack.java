package perhaps.progressions.experience.events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static perhaps.progressions.experience.ExperienceManager.grantXP;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class DoPostAttack {
    // This fixes doPostAttack firing twice due to a vanilla Minecraft bug
    private final List<UUID> playersBeingHandled = new ArrayList<>();

    @SuppressWarnings("unused")
    public void doPostAttack(AttackEntityEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUUID();
        if (playersBeingHandled.contains(playerUUID)) return;
        playersBeingHandled.add(playerUUID);

        // Swordsmanship skill
        grantXP(player, "swordsmanship", "skill", 0.2f);
        playersBeingHandled.remove(playerUUID);
    }
}
