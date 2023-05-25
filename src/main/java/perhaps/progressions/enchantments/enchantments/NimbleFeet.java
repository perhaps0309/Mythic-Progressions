package perhaps.progressions.enchantments.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import perhaps.progressions.enchantments.EnchantmentRarity;
import perhaps.progressions.enchantments.Enchantments;

import java.util.Objects;
import java.util.UUID;

public class NimbleFeet extends Enchantment {
    public NimbleFeet(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot[] equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) {
        return EnchantmentRarity.UNCOMMON.getMinCost(level);
    }

    @Override
    public int getMaxCost(int level) {
        return EnchantmentRarity.UNCOMMON.getMaxCost(level);
    }

    private static final UUID NIMBLE_FEET_MOVEMENT_SPEED_MODIFIER = UUID.fromString("e9c53012-6f9c-4a98-84e6-3f76e31469f3");

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level world = player.level;
        AttributeInstance movementSpeedAttribute = Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED));
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.registeredEnchantments.get("nimble_feet"), boots);
        if (level < 1 || world.isClientSide) {
            if (movementSpeedAttribute.getModifier(NIMBLE_FEET_MOVEMENT_SPEED_MODIFIER) != null) {
                Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(NIMBLE_FEET_MOVEMENT_SPEED_MODIFIER);
            }
            return;
        };

        float movementSpeedBonus = 0.05f * level;
        if (movementSpeedAttribute.getModifier(NIMBLE_FEET_MOVEMENT_SPEED_MODIFIER) != null) return;
        movementSpeedAttribute.addPermanentModifier(
                new AttributeModifier(NIMBLE_FEET_MOVEMENT_SPEED_MODIFIER, "Nimble feet movement speed bonus", movementSpeedBonus, AttributeModifier.Operation.MULTIPLY_TOTAL)
        );
    }
}