package perhaps.progressions.enchantments.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.enchantments.EnchantmentRarity;
import perhaps.progressions.enchantments.Enchantments;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class Wisdom extends Enchantment {
    public Wisdom(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.GODLY.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.GODLY.getMaxCost(level); }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (event.getState().getMaterial() != Material.STONE) return;

        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("wisdom"), heldItem);
        if (level < 1 || world.isClientSide || heldItem.isEmpty()) return;

        int bonusXP = (event.getExpToDrop() + 1) * level;
        event.setExpToDrop(event.getExpToDrop() + bonusXP);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onLivingExperienceDrop(LivingExperienceDropEvent event) {
        Player player = event.getAttackingPlayer();
        if (player == null) return;

        ItemStack heldItem = player.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("wisdom"), heldItem);
        if (level < 1) return;

        int bonusXP = (event.getDroppedExperience() + 1) * level;
        event.setDroppedExperience(event.getDroppedExperience() + bonusXP);
    }
}