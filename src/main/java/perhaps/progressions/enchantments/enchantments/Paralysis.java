package perhaps.progressions.enchantments.enchantments;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.Logger;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.enchantments.EnchantmentRarity;
import perhaps.progressions.enchantments.Enchantments;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class Paralysis extends Enchantment {
    public Paralysis(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) {
        return EnchantmentRarity.RARE.getMinCost(level);
    }

    @Override
    public int getMaxCost(int level) {
        return EnchantmentRarity.RARE.getMaxCost(level);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onEntityAttack(AttackEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getTarget();
        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("paralysis"), heldItem);
        if (level < 1 || world.isClientSide || heldItem.isEmpty() || world.getRandom().nextFloat() > (0.05f * level)) return;
        if (!(entity instanceof LivingEntity livingEntity)) return;

        Logger.log("Running paralysis!", Logger.LoggingType.INFO, true);
        livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 100, false, false));
    }
}
