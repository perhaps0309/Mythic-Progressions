package perhaps.progressions.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.enchantments.enchantments.AutoSmeltEnchantment;

import java.util.Map;

public class Enchantments {
    @FunctionalInterface
    public interface EnchantmentCallback {
        void run(Enchantment.Rarity rarity, EnchantmentCategory category, EquipmentSlot equipmentSlot);
    }

    private static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MythicProgressions.MOD_ID);

    public static void initializeEnchantments(IEventBus eventBus) {
        enchantments.forEach((enchantmentName, enchantmentObject) -> {
            Enchantment.Rarity rarity = (Enchantment.Rarity) enchantmentObject.get("rarity");
            EnchantmentCategory category = (EnchantmentCategory) enchantmentObject.get("category");
            EquipmentSlot equipmentSlot = (EquipmentSlot) enchantmentObject.get("equipmentSlot");
            EnchantmentCallback callback = (EnchantmentCallback) enchantmentObject.get("callback");

            addEnchantment(enchantmentName, rarity, category, equipmentSlot, callback);
        });

        ENCHANTMENTS.register(eventBus);
    }

    private static final Map<String, Map<String, Object>> enchantments = Map.ofEntries(
            Map.entry("auto_smelt", Map.of(
                    "rarity", Enchantment.Rarity.RARE,
                    "category", EnchantmentCategory.DIGGER,
                    "equipmentSlot", EquipmentSlot.MAINHAND,
                    "callback", (EnchantmentCallback) (Enchantment.Rarity rarity, EnchantmentCategory category, EquipmentSlot equipmentSlot) -> {
                        new AutoSmeltEnchantment(rarity, category, new EquipmentSlot[] { equipmentSlot });
                    }
            ))
    );

    public static void addEnchantment(String enchantmentName, Enchantment.Rarity rarity, EnchantmentCategory category, EquipmentSlot equipmentSlot, EnchantmentCallback callback) {
        ENCHANTMENTS.register(enchantmentName, () -> {
            callback.run(rarity, category, equipmentSlot);
            return new Enchantment(rarity, category, new EquipmentSlot[] { equipmentSlot }) {
                // This is a dummy class to prevent Minecraft from crashing
                // Everything should be handled from callback
            };
        });
    }
}