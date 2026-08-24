package win.korowin.worldplaytimereborn.mixin;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import win.korowin.worldplaytimereborn.config.WptConfig;
import win.korowin.worldplaytimereborn.util.IWithPlayTime;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

@Mixin(LevelStorageSource.class)
public class LevelStorageSourceMixin {
    @Inject(at = @At("RETURN"), method = "makeLevelSummary")
    public void onMakeLevelSummary(Dynamic<?> dynamic, LevelStorageSource.LevelDirectory levelDirectory, boolean idk, int idk2, CallbackInfoReturnable<LevelSummary> cir) {
        LevelSummary levelSummary = cir.getReturnValue();

        if (levelSummary instanceof IWithPlayTime withPlayTime) {
            if (WptConfig.showWorldSize.get() && Files.isDirectory(levelDirectory.path())) {
                long[] worldSize = {0};
                try {
                    Files.walkFileTree(levelDirectory.path(), new SimpleFileVisitor<>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                            worldSize[0] += attributes.size();
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException exception) {
                            return FileVisitResult.CONTINUE;
                        }
                    });
                    withPlayTime.setWorldSizeBytes(worldSize[0]);
                } catch (IOException ignored) {
                }
            }

            Path stats = levelDirectory.resourcePath(LevelResource.PLAYER_STATS_DIR);
            File statsFile = stats.toFile();

            if (statsFile.isDirectory()) {
                File[] saveFiles = statsFile.listFiles();

                if (saveFiles != null) {
                    int totalPlayTime = 0;
                    for (File file : saveFiles) {
                        try (FileReader fileReader = new FileReader(file)) {
                            JsonObject jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();

                            if (jsonObject.has("stats")) {
                                JsonObject statsObject = jsonObject.getAsJsonObject("stats");

                                if (statsObject.has("minecraft:custom")) {
                                    JsonObject customObject = statsObject.getAsJsonObject("minecraft:custom");

                                    if (customObject.has("minecraft:play_time")) {
                                        int playTime = customObject.get("minecraft:play_time").getAsInt();
                                        totalPlayTime += playTime;
                                    }
                                }
                            }
                        } catch (JsonIOException | IOException | IllegalStateException ignored) {
                        }
                    }

                    if (totalPlayTime > 0) {
                        withPlayTime.setPlayTimeTicks(totalPlayTime);
                    }
                }
            }
        }
    }
}
