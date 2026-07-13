package win.korowin.worldplaytimereborn.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import win.korowin.worldplaytimereborn.client.util.PlayTimeRenderer;
import win.korowin.worldplaytimereborn.config.ServerPlayTimeManager;
import win.korowin.worldplaytimereborn.config.WptConfig;
import win.korowin.worldplaytimereborn.util.ServerEntryRenderPos;

@Mixin(value = ServerSelectionList.OnlineServerEntry.class, priority = 2000)
public class OnlineServerEntryMixin {
	@Shadow
	@Final
	private ServerData serverData;

	@Shadow
	@Final
	private Minecraft minecraft;

	@Unique
	private static int worldplaytimereborn$serverNameStartX;

	/**
	 * Captures the X coordinate where the server name starts rendering.
	 */
	@ModifyArg(
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V",
					ordinal = 0
			),
			method = "renderContent",
			index = 2
	)
	public int serverNameX(int x) {
		worldplaytimereborn$serverNameStartX = x;
		return x;
	}

	/**
	 * Renders playtime indicator in the multiplayer server list entry.
	 */
	@Inject(at = @At("TAIL"), method = "renderContent")
	public void onRender(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo ci) {
		if (!WptConfig.showServerPlayTime.get()) {
			return;
		}

		int playTime = ServerPlayTimeManager.getPlayTime(serverData.ip);
		int playTimeWidth = PlayTimeRenderer.getWholeWidth(playTime);

		if (playTimeWidth <= 0) {
			return;
		}

		int renderX;
		int renderY;
		ServerEntryRenderPos renderPos = WptConfig.serverPlayTimePosition.get();

		ServerSelectionList.OnlineServerEntry entry = (ServerSelectionList.OnlineServerEntry) (Object) this;

		switch (renderPos) {
			case AFTER_NAME -> {
				int serverNameWidth = this.minecraft.font.width(serverData.name);
				renderX = worldplaytimereborn$serverNameStartX + 3 + serverNameWidth;
				renderY = entry.getContentY() + 1;
			}
			case BEHIND_COUNT -> {
				int statusWidth = this.minecraft.font.width(serverData.status);
				renderX = entry.getContentX() + entry.getContentWidth() - 24 - statusWidth - playTimeWidth;
				renderY = entry.getContentY();
			}
			case LEFT -> {
				renderX = entry.getContentX() - playTimeWidth - 5;
				renderY = entry.getContentY() + 10;
			}
			case RIGHT -> {
				renderX = entry.getContentX() + entry.getContentWidth() + 6;
				renderY = entry.getContentY() + 10;
			}
			default -> {
				return;
			}
		}

		PlayTimeRenderer.render(guiGraphics, renderX, renderY, playTime, WptConfig.serverPlayTimeColor.get());
	}
}
