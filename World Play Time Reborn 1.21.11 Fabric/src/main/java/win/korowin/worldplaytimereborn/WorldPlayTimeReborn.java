package win.korowin.worldplaytimereborn;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import win.korowin.worldplaytimereborn.config.ServerPlayTimeManager;
import win.korowin.worldplaytimereborn.config.WptConfig;

public final class WorldPlayTimeReborn {
	public static final String MOD_ID = "worldplaytimereborn";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private WorldPlayTimeReborn() {
	}

	/**
	 * Initializes config and persistent playtime storage.
	 */
	public static void init() {
		WptConfig.init();
		ServerPlayTimeManager.load();
	}

	/**
	 * Creates a namespaced identifier under this mod id.
	 */
	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
