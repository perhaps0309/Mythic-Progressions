package perhaps.progressions.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.enchantments.enchantments.*;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = MythicProgressions.MOD_ID)
public class Enchantments {
    @FunctionalInterface
    public interface EnchantmentCallback {
        void run(Enchantment.Rarity rarity, EnchantmentCategory category, EquipmentSlot[] equipmentSlots);
    }

    private static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MythicProgressions.MOD_ID);

    public static void initializeEnchantments(IEventBus eventBus) {
        enchantments.forEach((enchantmentName, enchantmentObject) -> {
            Enchantment.Rarity rarity = (Enchantment.Rarity) enchantmentObject.get("rarity");
            EnchantmentCategory category = (EnchantmentCategory) enchantmentObject.get("category");
            EquipmentSlot equipmentSlot = (EquipmentSlot) enchantmentObject.get("equipmentSlot");
            EnchantmentCallback callback = (EnchantmentCallback) enchantmentObject.get("callback");

            addEnchantment(enchantmentName, rarity, category, new EquipmentSlot[] { equipmentSlot }, callback);
        });

        ENCHANTMENTS.register(eventBus);
    }

    private static final Map<String, Map<String, Object>> enchantments = Map.ofEntries(
            Map.entry("auto_smelt", Map.of(
                    "rarity", Enchantment.Rarity.RARE,
                    "category", EnchantmentCategory.DIGGER,
                    "equipmentSlot", EquipmentSlot.MAINHAND,
                    "callback", (EnchantmentCallback) AutoSmelt::new
            )),
            Map.entry("smoke_mastery", Map.of(
                    "rarity", Enchantment.Rarity.VERY_RARE,
                    "category", EnchantmentCategory.WEAPON,
                    "equipmentSlot", EquipmentSlot.MAINHAND,
                    "callback", (EnchantmentCallback) SmokeMastery::new
            )),
            Map.entry("deep_miner", Map.of(
                    "rarity", Enchantment.Rarity.RARE,
                    "category", EnchantmentCategory.DIGGER,
                    "equipmentSlot", EquipmentSlot.MAINHAND,
                    "callback", (EnchantmentCallback) DeepMiner::new
            )),
            Map.entry("beheading", Map.of(
                    "rarity", Enchantment.Rarity.VERY_RARE,
                    "category", EnchantmentCategory.WEAPON,
                    "equipmentSlot", EquipmentSlot.MAINHAND,
                    "callback", (EnchantmentCallback) Beheading::new
            )),
            Map.entry("stone_breaker", Map.of(
                    "rarity", Enchantment.Rarity.UNCOMMON,
                    "category", EnchantmentCategory.DIGGER,
                    "equipmentSlot", EquipmentSlot.MAINHAND,
                    "callback", (EnchantmentCallback) StoneBreaker::new
            )),
            Map.entry("pyromania", Map.of(
                    "rarity", Enchantment.Rarity.RARE,
                    "category", EnchantmentCategory.WEAPON,
                    "equipmentSlot", EquipmentSlot.MAINHAND,
                    "callback", (EnchantmentCallback) Pyromania::new
            ))
    );

    public static Map<String, Enchantment> registeredEnchantments = new HashMap<>();

    public static void addEnchantment(String enchantmentName, Enchantment.Rarity rarity, EnchantmentCategory category, EquipmentSlot[] equipmentSlots, EnchantmentCallback callback) {
        ENCHANTMENTS.register(enchantmentName, () -> {
            callback.run(rarity, category, equipmentSlots);
            Enchantment dummyEnchantment = new Enchantment(rarity, category, equipmentSlots) {
                // This is a dummy class to prevent Minecraft from crashing
                // Everything should be handled from callback
            };

            registeredEnchantments.put(enchantmentName, dummyEnchantment);
            return dummyEnchantment;
        });
    }
}
