package perhaps.progressions.enchantments.enchantments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.Logger;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.enchantments.EnchantmentRarity;
import perhaps.progressions.enchantments.Enchantments;

import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class EnderMind extends Enchantment {
    public EnderMind(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.RARE.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.RARE.getMaxCost(level); }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        if (!(event.getEntityLiving() instanceof Player player)) return;

        Level world = player.level;
        Iterable<ItemStack> armor = player.getArmorSlots();
        AtomicInteger totalLevel = new AtomicInteger();
        armor.forEach((item -> totalLevel.addAndGet(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("ender_mind"), item))));
        if (totalLevel.get() < 1 || world.isClientSide || (player.getHealth() - event.getAmount()) > 9) return;
        if (!(event.getSource().getDirectEntity() instanceof Mob) && !(event.getSource().getDirectEntity() instanceof Arrow)) return;

        Logger.log("Teleporting!", Logger.LoggingType.INFO, true);
        double originalDirectionX = player.getX();
        double originalDirectionY = player.getY();
        double originalDirectionZ = player.getZ();

        for (int i = 0; i < 16; i++) {
            double directionX = player.getX() + (player.getRandom().nextDouble() - 0.5D) * 16.0D;
            double directionY = Mth.clamp(player.getY() + (player.getRandom().nextInt(16) - 8), world.getMinBuildHeight(), world.getMinBuildHeight() + ((ServerLevel)world).getLogicalHeight() - 1);
            double directionZ = player.getZ() + (player.getRandom().nextDouble() - 0.5D) * 16.0D;
            if (player.isPassenger()) {
                player.stopRiding();
            }

            EntityTeleportEvent.ChorusFruit chorusEvent = new EntityTeleportEvent.ChorusFruit(player, directionX, directionY, directionZ);
            if (player.randomTeleport(chorusEvent.getTargetX(), chorusEvent.getTargetY(), chorusEvent.getTargetZ(), true)) {
                SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                world.playSound(null, originalDirectionX, originalDirectionY, originalDirectionZ, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                player.playSound(soundevent, 1.0F, 1.0F);
                break;
            }
        }
    }
}