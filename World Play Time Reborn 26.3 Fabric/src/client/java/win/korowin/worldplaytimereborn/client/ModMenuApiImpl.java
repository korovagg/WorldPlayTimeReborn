package win.korowin.worldplaytimereborn.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import win.korowin.worldplaytimereborn.client.config.cloth.ClothConfigCheck;
import win.korowin.worldplaytimereborn.client.config.cloth.ClothConfigScreenMaker;

public class ModMenuApiImpl implements ModMenuApi {
	/**
	 * Provides the config screen factory for Mod Menu.
	 */
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		if (ClothConfigCheck.isInstalled()) {
			return ClothConfigScreenMaker::create;
		}
		return ModMenuApi.super.getModConfigScreenFactory();
	}
}
