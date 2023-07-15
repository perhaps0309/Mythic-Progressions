package perhaps.progressions;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import perhaps.progressions.capabilities.CapabilityHandler;
import perhaps.progressions.client.keybinds.Keybinds;
import perhaps.progressions.enchantments.Enchantments;
import perhaps.progressions.experience.ExperienceManager;

@Mod(MythicProgressions.MOD_ID)
public class MythicProgressions {
    public static final String MOD_ID = "progressions";

    public MythicProgressions() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        Enchantments.initializeEnchantments(eventBus);
        ExperienceManager.initializeExperience(eventBus);
        eventBus.register(new CapabilityHandler());
        eventBus.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        Logger.log("Beginning setup of Mythic Progressions!", Logger.LoggingType.INFO, false);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        Keybinds.init();
    }
}