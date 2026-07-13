package win.korowin.worldplaytimereborn.mixin;

import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import win.korowin.worldplaytimereborn.util.IWithPlayTime;

@Mixin(LevelSummary.class)
public class LevelSummaryMixin implements IWithPlayTime {
    @Unique
    private int worldplaytimereborn$playTimeTicks = -1;

    @Override
    public void setPlayTimeTicks(int playTimeTicks) {
        this.worldplaytimereborn$playTimeTicks = playTimeTicks;
    }

    @Override
    public int getPlayTimeTicks() {
        return this.worldplaytimereborn$playTimeTicks;
    }
}
