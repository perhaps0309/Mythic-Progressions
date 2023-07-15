package perhaps.progressions.enchantments.enchantments;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.Logger;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.enchantments.EnchantmentRarity;
import perhaps.progressions.enchantments.Enchantments;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class ColdBlooded extends Enchantment {
    public ColdBlooded(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.LEGENDARY.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.LEGENDARY.getMaxCost(level); }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onPotionApplied(PotionEvent.PotionApplicableEvent event) {
        if (!(event.getEntityLiving() instanceof Player player)) return;

        Level world = player.level;
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("cold_blooded"), boots);
        if (level < 1 || world.isClientSide) return;

        MobEffectInstance potionEffect = event.getPotionEffect();
        if (potionEffect.getEffect() == MobEffects.MOVEMENT_SLOWDOWN) event.setCanceled(true);
    }
}