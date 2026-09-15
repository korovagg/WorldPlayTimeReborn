package win.korowin.worldplaytimereborn.util;

public interface IWithPlayTime {
	/**
	 * Sets the cached playtime in ticks.
	 */
	void setPlayTimeTicks(int playTimeTicks);

	/**
	 * Gets the cached playtime in ticks.
	 */
	int getPlayTimeTicks();

	/**
	 * Sets the cached world size in bytes.
	 */
	void setWorldSizeBytes(long worldSizeBytes);

	/**
	 * Gets the cached world size in bytes.
	 */
	long getWorldSizeBytes();
}
