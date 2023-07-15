package perhaps.progressions.enchantments.enchantments;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.enchantments.EnchantmentRarity;
import perhaps.progressions.enchantments.Enchantments;

import java.util.*;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class AtomicResistance extends Enchantment {
    public AtomicResistance(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) {
        return EnchantmentRarity.LEGENDARY.getMinCost(level);
    }

    @Override
    public int getMaxCost(int level) {
        return EnchantmentRarity.LEGENDARY.getMaxCost(level);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntityLiving() instanceof Player player)) return;

        Level world = player.level;
        ItemStack item = player.getItemBySlot(EquipmentSlot.FEET);
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("airborne"), item);
        if (level < 1 || world.isClientSide || event.getSource().isExplosion()) return;

        float amount = event.getAmount();
        float newAmount = amount - (amount * (0.15f * level));
        event.setAmount(newAmount);
    }
}