package perhaps.progressions.enchantments.enchantments;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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

import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class Nocturnal extends Enchantment {
    public Nocturnal(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.RARE.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.RARE.getMaxCost(level); }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntityLiving() instanceof Player player)) return;

        Level world = player.level;
        Iterable<ItemStack> armor = player.getArmorSlots();
        AtomicInteger totalLevel = new AtomicInteger();
        armor.forEach((item -> totalLevel.addAndGet(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("nocturnal"), item))));
        if (totalLevel.get() < 1 || world.isClientSide || !world.isNight()) return;

        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 100, 0)); // Duration: 5 seconds (100 ticks)
    }
}