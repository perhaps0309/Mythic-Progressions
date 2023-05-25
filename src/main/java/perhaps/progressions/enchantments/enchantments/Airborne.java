package perhaps.progressions.enchantments.enchantments;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.enchantments.EnchantmentRarity;
import perhaps.progressions.enchantments.Enchantments;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class Airborne extends Enchantment {
    public Airborne(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
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
        ItemStack item = player.getItemBySlot(EquipmentSlot.FEET);
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("airborne"), item);
        if (level < 1 || world.isClientSide || event.getSource() != DamageSource.FALL) return;

        float amount = event.getAmount();
        float newAmount = amount - (amount * (0.25f * level));
        event.setAmount(newAmount);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if (!(event.getEntityLiving() instanceof Player player)) {
            return;
        }

        Level world = player.level;
        ItemStack item = player.getSlot(100).get();
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("airborne"), item);
        if (level < 1 || world.isClientSide) {
            return;
        }

        Vec3 playerMovement = player.getDeltaMovement();
        player.setDeltaMovement(playerMovement.x, playerMovement.y + (0.25 * level), playerMovement.z);
        player.invulnerableTime = 45;
        player.hurtMarked = true;
    }
}