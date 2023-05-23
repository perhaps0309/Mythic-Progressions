package perhaps.progressions.enchantments.enchantments;

import net.minecraft.world.damagesource.DamageSource;
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
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.enchantments.EnchantmentRarity;
import perhaps.progressions.enchantments.Enchantments;

import java.util.*;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class Pyromania extends Enchantment {
    public Pyromania(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.EPIC.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.EPIC.getMaxCost(level); }

    // This fixes doPostAttack firing twice due to a vanilla Minecraft bug
    private final List<UUID> playersBeingHandled = new ArrayList<>();

    @SubscribeEvent
    public void doPostAttack(AttackEntityEvent event) {
        Player player = event.getPlayer();
        LivingEntity entity = event.getEntityLiving();
        UUID playerUUID = player.getUUID();
        if (playersBeingHandled.contains(playerUUID)) return;

        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("pyromania"), heldItem);
        if (level < 1 || world.isClientSide || heldItem.isEmpty()) return;
        playersBeingHandled.add(playerUUID);

        if (entity.isOnFire()) {
            float damage = level * 2f;
            DamageSource damageSource = DamageSource.mobAttack(player);
            entity.hurt(damageSource, damage);
        }
        playersBeingHandled.remove(playerUUID);
    }
}
