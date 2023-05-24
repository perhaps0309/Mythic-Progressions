package perhaps.progressions.enchantments.enchantments;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
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

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class GodOfThunder extends Enchantment {
    public GodOfThunder(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
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
    public static void doPostAttack(AttackEntityEvent event) {
        Player player = event.getPlayer();
        LivingEntity entity = event.getEntityLiving();
        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        Enchantment enchantment = Enchantments.registeredEnchantments.get("god_of_thunder");
        int heldItemLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantment, heldItem);
        int offhandItemLevel = EnchantmentHelper.getItemEnchantmentLevel(enchantment, offhandItem);
        if ((heldItemLevel < 1 && offhandItemLevel < 1) || world.isClientSide || heldItem.isEmpty()) return;

        int level = (heldItemLevel < 1) ? heldItemLevel : offhandItemLevel;
        if (world.getRandom().nextFloat() > (0.05f * level)) return;

        LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
        lightning.setPos(entity.getX(), entity.getY(), entity.getX());
        world.addFreshEntity(lightning);
    }
}