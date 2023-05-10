package perhaps.progressions.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import perhaps.progressions.MythicProgressions;
import perhaps.progressions.enchantments.enchantments.AutoSmeltEnchantment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

// TODO: I'll rewrite this to be more like skills/abilities soon:tm:
// Registering this now will break the game!
public class EnchantmentInit {
    private static final Map<String, Map<String, Object>> enchantments = Map.of(
            "auto_smelt", Map.of(
                    "class", AutoSmeltEnchantment.class,
                    "rarity", Enchantment.Rarity.RARE,
                    "category", EnchantmentCategory.DIGGER,
                    "equipmentSlot", EquipmentSlot.MAINHAND
            )
    );


    @SuppressWarnings("unchecked")
    public static void register(IEventBus eventBus) {
        final DeferredRegister<Enchantment> enchantmentsRegister = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MythicProgressions.MOD_ID);

        enchantments.forEach((enchantmentId, enchantmentObject) -> {
            Class<? extends AutoSmeltEnchantment> enchantmentClass = (Class<? extends AutoSmeltEnchantment>) enchantmentObject.get("class");
            Enchantment.Rarity rarity = (Enchantment.Rarity) enchantmentObject.get("rarity");
            EnchantmentCategory category = (EnchantmentCategory) enchantmentObject.get("category");
            EquipmentSlot equipmentSlot = (EquipmentSlot) enchantmentObject.get("equipmentSlot");

            try {
                Constructor<? extends Enchantment> constructor = enchantmentClass.getConstructor();
                Enchantment enchantment = constructor.newInstance(rarity, category, new EquipmentSlot[] { equipmentSlot });
                enchantmentsRegister.register(enchantmentId, () -> enchantment);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        enchantmentsRegister.register(eventBus);
    }
}
