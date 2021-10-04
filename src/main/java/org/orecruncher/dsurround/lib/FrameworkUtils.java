package org.orecruncher.dsurround.lib;

import joptsimple.internal.Strings;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FrameworkUtils {

    @Nullable
    public static String getModDisplayName(String namespace) {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(namespace);
        return container.map(modContainer -> modContainer.getMetadata().getName()).orElse(null);
    }

    public static boolean isModLoaded(String namespace) {
        return FabricLoader.getInstance().isModLoaded(namespace);
    }

    public static String getModBranding(String namespace) {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(namespace);
        if (container.isPresent()) {
            ModMetadata data = container.get().getMetadata();
            return String.format("%s v%s", data.getName(), data.getVersion());
        }
        return Strings.EMPTY;
    }
}
