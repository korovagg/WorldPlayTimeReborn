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
}
