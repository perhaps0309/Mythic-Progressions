package perhaps.progressions.enchantments.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.enchantments.EnchantmentRarity;
import perhaps.progressions.enchantments.Enchantments;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

// TODO: Make jump height scale with the level of the enchantment
// Also, make the movement speed get removed after the time period passed.
@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class SugarRush extends Enchantment {
    public SugarRush(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinCost(int level) { return EnchantmentRarity.RARE.getMinCost(level); }
    @Override
    public int getMaxCost(int level) { return EnchantmentRarity.RARE.getMaxCost(level); }

    private static final UUID SUGAR_RUSH_MOVEMENT_SPEED_MODIFIER = UUID.fromString("35c9e47e-7c09-46bf-adfd-7233967e8f5e");
    private static final Set<UUID> playersAffected = new HashSet<>();

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof Player player)) return;

        Level world = player.level;
        AttributeInstance movementSpeedAttribute = Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED));
        AtomicInteger highestLevel = new AtomicInteger();
        player.getArmorSlots().forEach((armorItem) -> {
            int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("sugar_rush"), armorItem);
            if (level > highestLevel.get()) highestLevel.set(level);
        });
        if (highestLevel.get() < 1 || world.isClientSide) return;

        float movementSpeedBonus = 0.10f * highestLevel.get();
        playersAffected.add(player.getUUID());
        movementSpeedAttribute.addTransientModifier(
                new AttributeModifier(SUGAR_RUSH_MOVEMENT_SPEED_MODIFIER, "Sugar rush movement speed bonus", movementSpeedBonus, AttributeModifier.Operation.MULTIPLY_TOTAL)
        );
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if (!(event.getEntityLiving() instanceof Player player)) return;

        Level world = player.level;
        if (!playersAffected.contains(player.getUUID()) || world.isClientSide) return;

        Vec3 playerMovement = player.getDeltaMovement();
        player.setDeltaMovement(playerMovement.x, playerMovement.y + (0.25), playerMovement.z);
        player.invulnerableTime = 45;
        player.hurtMarked = true;
    }
}