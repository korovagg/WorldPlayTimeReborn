package win.korowin.worldplaytimereborn.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

import win.korowin.worldplaytimereborn.WorldPlayTimeReborn;
import win.korowin.worldplaytimereborn.client.handler.EventHandlerCommon;

public class WorldPlayTimeRebornClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		WorldPlayTimeReborn.init();
		ClientTickEvents.END_CLIENT_TICK.register(mc -> EventHandlerCommon.onClientTick());
		ClientLifecycleEvents.CLIENT_STOPPING.register(mc -> EventHandlerCommon.onLeavingGame());
		ClientPlayConnectionEvents.DISCONNECT.register((listener, mc) -> EventHandlerCommon.onLeaveServer());
	}
}
