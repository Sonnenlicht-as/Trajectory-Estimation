package sonnenlichts.tje;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sonnenlichts.tje.client.config.ConfigHolder;
import sonnenlichts.tje.client.event.ClientRenderHandler;

@Mod(TrajectoryEstimation.MOD_ID)
public class TrajectoryEstimation {
    public static final String MOD_ID = "trajectory_estimation";
    public static final String VERSION = "1.0.2";
    public static final String NAME = "Trajectory Estimation";
    public static IEventBus MOD_EVENT_BUS;

    public TrajectoryEstimation() {
        MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        MinecraftForge.EVENT_BUS.register(this);
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
        MOD_EVENT_BUS.register(this);
        MOD_EVENT_BUS.addListener(this::setupClient);
    }

    private void setupClient(final FMLClientSetupEvent event) {
        ClientRenderHandler commonEventHandler = new ClientRenderHandler();
        MinecraftForge.EVENT_BUS.register(commonEventHandler);
    }
}
