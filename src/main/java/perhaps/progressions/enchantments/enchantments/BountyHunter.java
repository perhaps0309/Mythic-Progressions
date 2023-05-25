package perhaps.progressions.enchantments.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.enchantments.EnchantmentRarity;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class BountyHunter extends Enchantment {
    public BountyHunter(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
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
}