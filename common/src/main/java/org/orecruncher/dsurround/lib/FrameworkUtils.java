package org.orecruncher.dsurround.lib;

import dev.architectury.platform.Platform;
import org.orecruncher.dsurround.DynamicSurroundings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class FrameworkUtils {
    public static String getModDisplayName(String namespace) {
        return Platform.getMod(namespace).getName();
    }

    public static String getModVersion(String namespace) {
        return Platform.getMod(namespace).getVersion();
    }

    public static Collection<String> getModIdList(boolean loadedOnly) {
        return Platform.getModIds().stream().filter(Platform::isModLoaded).collect(Collectors.toList());
    }

    /**
     * Gets the path to a mod's configuration directory. If it doesn't exist it will be created.  If for some reason
     * it cannot be created, the standard Minecraft config path will be returned.
     *
     * @param modId ModId to obtain the configuration path.
     * @return Path to the mod's configuration directory.
     */
    public static Path getConfigPath(final String modId) {
        Path configDir = Platform.getConfigFolder();
        Path configPath = configDir.resolve(Objects.requireNonNull(modId));

        if (Files.notExists(configPath))
            try {
                Files.createDirectory(configPath);
            } catch (final IOException ex) {
                DynamicSurroundings.LOGGER.error(ex, "Unable to create directory path %s", configPath.toString());
                configPath = configDir;
            }

        return configPath;
    }
}
