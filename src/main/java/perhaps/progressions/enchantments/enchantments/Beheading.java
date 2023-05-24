package perhaps.progressions.enchantments.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.enchantments.EnchantmentRarity;
import perhaps.progressions.enchantments.Enchantments;

import java.util.Map;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class Beheading extends Enchantment {
    public Beheading(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.EPIC.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.EPIC.getMaxCost(level); }

    private static final Map<EntityType<?>, Item> entityHeads = Map.of(
            EntityType.ZOMBIE, Items.ZOMBIE_HEAD,
            EntityType.SKELETON, Items.SKELETON_SKULL,
            EntityType.CREEPER, Items.CREEPER_HEAD,
            EntityType.WITHER_SKELETON, Items.WITHER_SKELETON_SKULL,
            EntityType.ENDER_DRAGON, Items.DRAGON_HEAD,
            EntityType.PLAYER, Items.PLAYER_HEAD
    );

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        Entity target = event.getEntityLiving();
        Entity attacker = event.getSource().getDirectEntity();
        BlockPos pos = target.blockPosition();
        if (!(attacker instanceof Player player)) return;

        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("beheading"), heldItem);
        if (level < 1 || world.isClientSide || heldItem.isEmpty()) return;

        EntityType<?> entityType = target.getType();
        if (!entityHeads.containsKey(entityType) || world.getRandom().nextFloat() > (0.05f * level)) return;

        ItemStack entityHeadStack = new ItemStack(entityHeads.get(entityType));
        ItemEntity headEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, entityHeadStack);
        world.addFreshEntity(headEntity);
    }
}
