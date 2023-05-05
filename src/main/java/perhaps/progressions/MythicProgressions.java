package perhaps.progressions;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

@Mod("progressions")
public class MythicProgressions {

    public static final String MOD_ID = "progressions";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MythicProgressions() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(PlayerJoinEventHandler.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Setting up Mythic Progressions mod!");
    }
}
