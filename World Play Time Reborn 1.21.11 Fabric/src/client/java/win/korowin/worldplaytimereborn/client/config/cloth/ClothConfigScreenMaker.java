package win.korowin.worldplaytimereborn.client.config.cloth;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import win.korowin.worldplaytimereborn.config.WptConfig;
import win.korowin.worldplaytimereborn.util.Color;
import win.korowin.worldplaytimereborn.util.ServerEntryRenderPos;
import win.korowin.worldplaytimereborn.util.WorldEntryRenderPos;

public class ClothConfigScreenMaker {
	/**
	 * ModMenu-compatible factory method.
	 */
	public static Screen create(Minecraft minecraft, Screen parent) {
		return create(parent);
	}

	/**
	 * Builds the config screen.
	 */
	public static Screen create(Screen parent) {
		ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(parent)
				.setTitle(Component.translatable("worldplaytimereborn.config.header"))
				.setSavingRunnable(WptConfig::save);

		ConfigEntryBuilder entryBuilder = builder.entryBuilder();

		ConfigCategory worldEntryCategory = builder.getOrCreateCategory(Component.translatable("worldplaytimereborn.config.category.world_entry"));
		ConfigCategory serverEntryCategory = builder.getOrCreateCategory(Component.translatable("worldplaytimereborn.config.category.server_entry"));

		worldEntryCategory.addEntry(entryBuilder.startBooleanToggle(Component.translatable("worldplaytimereborn.config.showWorldPlayTime"), WptConfig.showWorldPlayTime.get())
				.setDefaultValue(WptConfig.showWorldPlayTime::getDefault)
				.setSaveConsumer(WptConfig.showWorldPlayTime::set)
				.build());

		worldEntryCategory.addEntry(entryBuilder.startEnumSelector(Component.translatable("worldplaytimereborn.config.worldPlayTimePosition"), WorldEntryRenderPos.class, WptConfig.worldPlayTimePosition.get())
				.setDefaultValue(WptConfig.worldPlayTimePosition::getDefault)
				.setSaveConsumer(WptConfig.worldPlayTimePosition::set)
				.setEnumNameProvider(anEnum -> Component.translatable("worldplaytimereborn.config.worldPlayTimePosition." + anEnum.name().toLowerCase()))
				.build());

		worldEntryCategory.addEntry(entryBuilder.startAlphaColorField(Component.translatable("worldplaytimereborn.config.worldPlayTimeColor"), WptConfig.worldPlayTimeColor.get().toARGB())
				.setDefaultValue(WptConfig.worldPlayTimeColor.getDefault()::toARGB)
				.setSaveConsumer(argb -> WptConfig.worldPlayTimeColor.set(Color.fromARGB(argb)))
				.build());

		serverEntryCategory.addEntry(entryBuilder.startBooleanToggle(Component.translatable("worldplaytimereborn.config.showServerPlayTime"), WptConfig.showServerPlayTime.get())
				.setDefaultValue(WptConfig.showServerPlayTime::getDefault)
				.setSaveConsumer(WptConfig.showServerPlayTime::set)
				.build());

		serverEntryCategory.addEntry(entryBuilder.startEnumSelector(Component.translatable("worldplaytimereborn.config.serverPlayTimePosition"), ServerEntryRenderPos.class, WptConfig.serverPlayTimePosition.get())
				.setDefaultValue(WptConfig.serverPlayTimePosition::getDefault)
				.setSaveConsumer(WptConfig.serverPlayTimePosition::set)
				.setEnumNameProvider(anEnum -> Component.translatable("worldplaytimereborn.config.serverPlayTimePosition." + anEnum.name().toLowerCase()))
				.build());

		serverEntryCategory.addEntry(entryBuilder.startAlphaColorField(Component.translatable("worldplaytimereborn.config.serverPlayTimeColor"), WptConfig.serverPlayTimeColor.get().toARGB())
				.setDefaultValue(WptConfig.serverPlayTimeColor.getDefault()::toARGB)
				.setSaveConsumer(argb -> WptConfig.serverPlayTimeColor.set(Color.fromARGB(argb)))
				.build());

		return builder.build();
	}
}
