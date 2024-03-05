package sonnenlichts.tje.client.event;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import sonnenlichts.tje.client.config.ConfigHolder;
import sonnenlichts.tje.client.config.TjeModConfig;
import sonnenlichts.tje.client.render.gui.TjeModConfigScreen;

import static sonnenlichts.tje.TrajectoryEstimation.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfigHandler {

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfigEvent.Loading event) {
        final ModConfig config = event.getConfig();
        if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
            TjeModConfig.initClient();
        }
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading event) {
        final ModConfig config = event.getConfig();
        if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
            TjeModConfig.initClient();
        }
    }

    @SubscribeEvent
    public static void onInitializeClient(FMLClientSetupEvent var0) {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((a, b) -> new TjeModConfigScreen(b)));
    }

}
