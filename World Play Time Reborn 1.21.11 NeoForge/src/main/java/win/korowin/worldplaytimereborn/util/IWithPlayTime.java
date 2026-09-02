package win.korowin.worldplaytimereborn.util;

public interface IWithPlayTime {
    void setPlayTimeTicks(int playTimeTicks);

    int getPlayTimeTicks();

    void setWorldSizeBytes(long worldSizeBytes);

    long getWorldSizeBytes();
}
