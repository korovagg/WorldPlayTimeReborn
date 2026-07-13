package win.korowin.worldplaytimereborn.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import win.korowin.worldplaytimereborn.config.ServerPlayTimeManager;

public class EventHandlerCommon {
    private static int playTicks;

    public static void onClientTick() {
        ClientPacketListener connection = Minecraft.getInstance().getConnection();

        if (connection == null) {
            return;
        }

        ServerData serverData = connection.getServerData();

        if (serverData == null) {
            return;
        }

        String connectedServer = serverData.ip;

        ServerPlayTimeManager.onPlayTick(connectedServer);

        if (++playTicks >= 6000) {
            ServerPlayTimeManager.saveAsync();
            playTicks = 0;
        }
    }

    public static void onLeavingGame() {
        ServerPlayTimeManager.save();
    }

    public static void onLeaveServer() {
        ServerPlayTimeManager.save();
    }
}
