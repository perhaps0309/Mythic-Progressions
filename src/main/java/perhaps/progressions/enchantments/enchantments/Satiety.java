package perhaps.progressions.enchantments.enchantments;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import perhaps.progressions.enchantments.EnchantmentRarity;
import perhaps.progressions.enchantments.Enchantments;

public class Satiety extends Enchantment {
    public Satiety(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.EPIC.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.EPIC.getMaxCost(level); }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity target = event.getEntityLiving();
        Entity attacker = event.getSource().getDirectEntity();
        if (!(attacker instanceof Player player) || target instanceof Animal) return;

        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("pyromania"), heldItem);
        if (level < 1 || world.isClientSide || heldItem.isEmpty() || world.getRandom().nextFloat() > (0.10f * level)) return;

        player.getFoodData().eat(3, 0.5f);
    }
}