package perhaps.progressions.experience.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;

import static perhaps.progressions.experience.ExperienceManager.XPTypes.PERK;
import static perhaps.progressions.experience.ExperienceManager.XPTypes.SKILL;
import static perhaps.progressions.experience.ExperienceManager.grantXP;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class OnBlockBreak {
    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        BlockState state = event.getState();
        Block block = state.getBlock();
        Level world = player.level;
        if (world.isClientSide) return;

        // Lumberjack skill
        if (state.getMaterial() == Material.WOOD) {
            grantXP(player, SKILL, "lumberjack", 0.5f);
            return;
        }

        // Excavation skill
        if (state.getMaterial() == Material.DIRT) {
            grantXP(player, SKILL, "excavation", 0.2f);
            return;
        }

        // Mining skill
        if (block instanceof OreBlock) {
            grantXP(player, SKILL, "mining", 0.35f);
        } else {
            grantXP(player, SKILL, "mining", 0.2f);
        }

        // Miners Instinct perk
        grantXP(player, PERK, "miners_instinct", 0.2f);
    }
}