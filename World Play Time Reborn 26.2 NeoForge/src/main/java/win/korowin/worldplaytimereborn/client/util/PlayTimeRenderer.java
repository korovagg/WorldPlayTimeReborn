package win.korowin.worldplaytimereborn.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import win.korowin.worldplaytimereborn.WorldPlayTimeReborn;
import win.korowin.worldplaytimereborn.util.Color;

import java.util.Locale;

public class PlayTimeRenderer {
    private static final net.minecraft.resources.Identifier TIME_ICON = WorldPlayTimeReborn.id("textures/gui/time_icon.png");

    public static @Nullable Component getPlayTimeComponent(int ticks) {
        if (ticks <= 0) {
            return null;
        }

        double hours = (ticks / 20.0) / 3600.0;

        return Component.translatable(
                "worldplaytimereborn.format",
                Component.literal(hours >= 100.0 ? String.valueOf((int) hours) : String.format(Locale.US, "%.1f", hours))
        );
    }

    public static int getWholeWidth(int ticks) {
        Component component = getPlayTimeComponent(ticks);

        if (component == null) {
            return 0;
        }

        Minecraft minecraft = Minecraft.getInstance();

        return minecraft.font.width(component) + 11;
    }

    /**
     * Renders the indicator with icon and formatted time.
     */
    public static void render(GuiGraphicsExtractor guiGraphics, int x, int y, int playTimeTicks, Color color) {
        Minecraft minecraft = Minecraft.getInstance();
        Component component = getPlayTimeComponent(playTimeTicks);

        if (component == null) {
            return;
        }

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TIME_ICON, x, y, 0.f, 0.f, 9, 9, 9, 9, color.toARGB());
        guiGraphics.text(minecraft.font, component, x + 11, y + 1, color.toARGB(), false);
    }
}
