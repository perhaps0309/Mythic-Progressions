package perhaps.progressions;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import perhaps.progressions.capabilities.CapabilityHandler;
import perhaps.progressions.client.keybinds.Keybinds;
import perhaps.progressions.enchantments.Enchantments;

@Mod(MythicProgressions.MOD_ID)
public class MythicProgressions {
    public static final String MOD_ID = "progressions";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MythicProgressions() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        Enchantments.initializeEnchantments(eventBus);
        eventBus.register(new CapabilityHandler());
        eventBus.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Beginning setup of Mythic Progressions!");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        Keybinds.init();
    }
}