package org.orecruncher.dsurround.gui.keyboard;

import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.orecruncher.dsurround.eventing.handlers.DiagnosticHandler;
import org.orecruncher.dsurround.gui.sound.IndividualSoundControlScreen;
import org.orecruncher.dsurround.lib.GameUtils;
import org.orecruncher.dsurround.sound.MinecraftAudioPlayer;

public class KeyBindings {

    public static final KeyBinding individualSoundConfigBinding;
    public static final KeyBinding diagnosticHud;

    static {
        individualSoundConfigBinding = new KeyBinding(
                "dsurround.text.keybind.individualSoundConfig",
                InputUtil.UNKNOWN_KEY.getCode(),
                "dsurround.text.keybind.section"
        );

        diagnosticHud = new KeyBinding(
                "dsurround.text.keybind.diagnosticHud",
                InputUtil.UNKNOWN_KEY.getCode(),
                "dsurround.text.keybind.section"
        );
    }

    public static void register() {
        KeyMappingRegistry.register(individualSoundConfigBinding);
        KeyMappingRegistry.register(diagnosticHud);

        ClientTickEvent.CLIENT_POST.register(client -> {
            if (GameUtils.getMC().currentScreen == null && GameUtils.getPlayer() != null) {
                if (individualSoundConfigBinding.wasPressed()) {
                    final boolean singlePlayer = GameUtils.getMC().isInSingleplayer();
                    GameUtils.getMC().setScreen(new IndividualSoundControlScreen(null, singlePlayer));
                    if (singlePlayer) {
                        MinecraftAudioPlayer.INSTANCE.stopAll();
                    }
                }

                if (diagnosticHud.wasPressed()) {
                    DiagnosticHandler.toggleCollection();
                }
            }
        });
    }
}
