package perhaps.progressions.experience.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;

import static perhaps.progressions.experience.ExperienceManager.grantXP;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class OnBlockBreak {
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        BlockState state = event.getState();
        Block block = state.getBlock();

        // Lumberjack skill
        if (state.getMaterial() == Material.WOOD) {
            grantXP(player, "lumberjack", "skill", 0.2f);
            return;
        }

        // Excavation skill
        if (state.getMaterial() == Material.DIRT) {
            grantXP(player, "excavation", "skill", 0.2f);
            return;
        }

        // Mining skill
        if (block instanceof OreBlock) {
            grantXP(player, "mining", "skill", 0.3f);
        } else {
            grantXP(player, "mining", "skill", 0.2f);
        }

        // Miners Instinct perk
        grantXP(player, "miners_instinct", "perk", 0.2f);
    }
}