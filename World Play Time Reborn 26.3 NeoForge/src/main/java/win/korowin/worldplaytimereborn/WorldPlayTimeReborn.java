package win.korowin.worldplaytimereborn;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.GameShuttingDownEvent;
import org.slf4j.Logger;
import win.korowin.worldplaytimereborn.client.config.cloth.ClothConfigScreenMaker;
import win.korowin.worldplaytimereborn.client.handler.EventHandlerCommon;
import win.korowin.worldplaytimereborn.config.ServerPlayTimeManager;
import win.korowin.worldplaytimereborn.config.WptConfig;

@Mod(WorldPlayTimeReborn.MODID)
public class WorldPlayTimeReborn {
    public static final String MODID = "worldplaytimereborn";
    public static final Logger LOGGER = LogUtils.getLogger();

    public WorldPlayTimeReborn(IEventBus modEventBus, ModContainer modContainer) {
        if (FMLLoader.getCurrent().getDist() == Dist.CLIENT) {
            init();

            NeoForge.EVENT_BUS.addListener(WorldPlayTimeReborn::onClientTick);
            NeoForge.EVENT_BUS.addListener(WorldPlayTimeReborn::onShutDown);
            NeoForge.EVENT_BUS.addListener(WorldPlayTimeReborn::onLoggedOut);

            modContainer.registerExtensionPoint(IConfigScreenFactory.class, (mc, screen) -> ClothConfigScreenMaker.create(screen));
        }
    }

    public static void init() {
        WptConfig.init();
        ServerPlayTimeManager.load();
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    private static void onClientTick(ClientTickEvent.Post e) {
        EventHandlerCommon.onClientTick();
    }

    private static void onShutDown(GameShuttingDownEvent e) {
        EventHandlerCommon.onLeavingGame();
    }

    private static void onLoggedOut(ClientPlayerNetworkEvent.LoggingOut e) {
        EventHandlerCommon.onLeaveServer();
    }
}
