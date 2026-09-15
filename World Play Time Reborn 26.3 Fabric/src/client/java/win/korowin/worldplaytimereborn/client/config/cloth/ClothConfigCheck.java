package win.korowin.worldplaytimereborn.client.config.cloth;

public class ClothConfigCheck {
	/**
	 * Checks whether Cloth Config is present on the classpath.
	 */
	public static boolean isInstalled() {
		try {
			Class.forName("me.shedaniel.clothconfig2.ClothConfigDemo");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
