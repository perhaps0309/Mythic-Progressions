package perhaps.progressions.enchantments.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.enchantments.EnchantmentRarity;
import perhaps.progressions.enchantments.Enchantments;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class Glimmering extends Enchantment {
    public Glimmering(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.EPIC.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.EPIC.getMaxCost(level); }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onLivingChangeTargetEvent(LivingChangeTargetEvent event) {
        if (!(event.getNewTarget() instanceof Player player) || !(event.getEntityLiving() instanceof Piglin piglin)) return;

        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("glimmering"), heldItem);
        if (level < 1 || world.isClientSide || heldItem.isEmpty()) return;

        piglin.setTarget(null);
        piglin.setAggressive(false);
    }
}