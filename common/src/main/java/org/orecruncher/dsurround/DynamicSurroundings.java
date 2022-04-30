package org.orecruncher.dsurround;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import net.minecraft.client.MinecraftClient;
import org.orecruncher.dsurround.config.*;
import org.orecruncher.dsurround.effects.particles.ParticleSheets;
import org.orecruncher.dsurround.gui.keyboard.KeyBindings;
import org.orecruncher.dsurround.lib.TickCounter;
import org.orecruncher.dsurround.lib.logging.ModLog;
import org.orecruncher.dsurround.processing.Handlers;
import org.orecruncher.dsurround.runtime.diagnostics.BlockViewer;
import org.orecruncher.dsurround.runtime.diagnostics.ClientProfiler;
import org.orecruncher.dsurround.runtime.diagnostics.RuntimeDiagnostics;
import org.orecruncher.dsurround.runtime.diagnostics.SoundEngineDiagnostics;

import java.nio.file.Files;
import java.nio.file.Path;

public class DynamicSurroundings {
    public static final String MOD_ID = "dsurround";
    public static final ModLog LOGGER = new ModLog(MOD_ID);

    public static final Path CONFIG_PATH = Platform.getConfigFolder().resolve(MOD_ID);
    public static final Path DATA_PATH = CONFIG_PATH.resolve("configs");
    public static final Path DUMP_PATH = CONFIG_PATH.resolve("dumps");

    public static final Configuration CONFIG = Configuration.getConfig();
    public static final SoundConfiguration SOUND_CONFIG = SoundConfiguration.getConfig();

    public static String BRANDING;

    public static void onClientLoad() {
        // Ensure all data paths exist
        createPath(CONFIG_PATH);
        createPath(DATA_PATH);
        createPath(DUMP_PATH);

        Mod modData = Platform.getMod(MOD_ID);
        BRANDING = modData.getName() + " v" + modData.getVersion();

        TickCounter.register();
        KeyBindings.register();

        // Register diagnostic handlers.  Ordering is semi important for
        // debug display layout.
        RuntimeDiagnostics.register();
        ClientProfiler.register();
        SoundEngineDiagnostics.register();
        BlockViewer.register();

        refreshConfigs();
        Handlers.initialize();
        ParticleSheets.register();

        LOGGER.info("Initialization complete");
    }

    public static void refreshConfigs() {
        SoundLibrary.load();
        BiomeLibrary.load();
        DimensionLibrary.load();
        BlockLibrary.load();
        ItemLibrary.load();
        EntityEffectLibrary.load();
    }

    private static void createPath(Path path) {
        try {
            Files.createDirectories(path);
        } catch (Throwable t) {
            LOGGER.error(t, "Failed to create path " + path.toString());
        }
    }
}
