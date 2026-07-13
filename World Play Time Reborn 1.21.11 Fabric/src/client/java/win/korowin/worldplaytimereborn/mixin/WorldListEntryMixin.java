package win.korowin.worldplaytimereborn.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import win.korowin.worldplaytimereborn.client.util.PlayTimeRenderer;
import win.korowin.worldplaytimereborn.config.WptConfig;
import win.korowin.worldplaytimereborn.util.IWithPlayTime;

@Mixin(WorldSelectionList.WorldListEntry.class)
public class WorldListEntryMixin {
	@Shadow
	@Final
	LevelSummary summary;

	/**
	 * Renders playtime indicator inside the world selection list.
	 */
	@Inject(at = @At("TAIL"), method = "renderContent")
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean pHovering, float pPartialTick, CallbackInfo ci) {
		if (!WptConfig.showWorldPlayTime.get()) {
			return;
		}

		if (this.summary instanceof IWithPlayTime withPlayTime) {
			WorldSelectionList.WorldListEntry entry = (WorldSelectionList.WorldListEntry) (Object) this;
			int ticks = withPlayTime.getPlayTimeTicks();
			int indicatorWidth = PlayTimeRenderer.getWholeWidth(ticks);

			if (indicatorWidth != 0) {
				int renderX;
				int renderY;

				switch (WptConfig.worldPlayTimePosition.get()) {
					case TOP_RIGHT -> {
						renderX = entry.getContentX() + entry.getContentWidth() - indicatorWidth - 4;
						renderY = entry.getContentY();
					}
					case LEFT -> {
						renderX = entry.getContentX() - indicatorWidth - 5;
						renderY = entry.getContentY() + 10;
					}
					case RIGHT -> {
						renderX = entry.getContentX() + entry.getContentWidth() + 14;
						renderY = entry.getContentY() + 10;
					}
					default -> {
						return;
					}
				}

				PlayTimeRenderer.render(guiGraphics, renderX, renderY, ticks, WptConfig.worldPlayTimeColor.get());
			}
		}
	}
}
