package perhaps.progressions.skills.skills;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.Logger;
import perhaps.progressions.MythicProgressions;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class AutoCompression {
    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onItemPickup(PlayerEvent.ItemPickupEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getStack();
        Item item = itemStack.getItem();

        Logger.log(countSimilarItems(player, item), Logger.LoggingType.INFO, false);
    }

    private static int countSimilarItems(Player player, Item item) {
        int count = 0;

        for (ItemStack stack : player.inventoryMenu.getItems()) {
            if (stack.getItem() == item) {
                count += stack.getCount();
            }
        }
        return count;
    }
}
