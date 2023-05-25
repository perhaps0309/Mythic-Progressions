package perhaps.progressions.enchantments.enchantments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.enchantments.EnchantmentRarity;
import perhaps.progressions.enchantments.Enchantments;

import java.util.Collection;
import java.util.List;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class Magnetism extends Enchantment {
    public Magnetism(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.RARE.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.RARE.getMaxCost(level); }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("magnetism"), heldItem);
        if (level < 1 || world.isClientSide || heldItem.isEmpty()) return;

        BlockPos pos = event.getPos();
        List<ItemStack> drops = Block.getDrops(event.getState(), (ServerLevel) world, pos, null, player, heldItem);
        drops.forEach((drop) -> {
            player.getInventory().add(drop);
            drops.remove(drop);
        });
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof Player player)) return;

        Level world = player.level;
        ItemStack heldItem = player.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("magnetism"), heldItem);
        if (level < 1 || world.isClientSide || heldItem.isEmpty()) return;

        Collection<ItemEntity> entityDrops = event.getDrops();
        entityDrops.forEach((itemEntity -> player.getInventory().add(itemEntity.getItem())));
        event.setCanceled(true);
    }
}