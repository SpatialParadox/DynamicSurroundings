package org.orecruncher.dsurround.processing;

import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.orecruncher.dsurround.DynamicSurroundings;
import org.orecruncher.dsurround.eventing.ClientEventHooks;
import org.orecruncher.dsurround.gui.sound.IndividualSoundControlScreen;
import org.orecruncher.dsurround.lib.GameUtils;
import org.orecruncher.dsurround.lib.Singleton;
import org.orecruncher.dsurround.lib.TickCounter;
import org.orecruncher.dsurround.lib.collections.ObjectArray;
import org.orecruncher.dsurround.lib.logging.IModLog;
import org.orecruncher.dsurround.lib.math.LoggingTimerEMA;
import org.orecruncher.dsurround.lib.math.TimerEMA;
import org.orecruncher.dsurround.lib.world.WorldUtils;
import org.orecruncher.dsurround.sound.SoundFactoryBuilder;

import java.util.Collection;

public class Handlers {

    private static final IModLog LOGGER = DynamicSurroundings.LOGGER.createChild(Handlers.class);
    private static final Singleton<Handlers> INSTANCE = new Singleton<>(Handlers::new);

    private final ObjectArray<ClientHandler> effectHandlers = new ObjectArray<>();
    private final LoggingTimerEMA handlerTimer = new LoggingTimerEMA("Handlers");
    private boolean isConnected = false;
    private boolean startupSoundPlayed = false;

    private Handlers() {
        init();
    }

    public static void initialize() {
        INSTANCE.get();
    }

    protected static PlayerEntity getPlayer() {
        return GameUtils.getPlayer();
    }

    private void register(final ClientHandler handler) {
        this.effectHandlers.add(handler);
        LOGGER.debug("Registered handler [%s]", handler.getClass().getName());
    }

    private void init() {
        register(new Scanners());           // Must be first
        register(new PlayerHandler());
        register(new EntityEffectHandler());
        register(new BiomeSoundHandler());
        register(new AreaBlockEffects());

        ClientTickEvent.CLIENT_POST.register(this::onTick);
        ClientEventHooks.COLLECT_DIAGNOSTICS.register(this::gatherDiagnostics);
        ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(this::onConnect);
        ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(this::onDisconnect);
    }

    private void onConnect(ClientPlayerEntity player) {
        if (isConnected) {
            LOGGER.warn("Attempt to initialize EffectManager when it is already initialized");
            onDisconnect(null);
        }

        isConnected = true;
        for (final ClientHandler h : this.effectHandlers) {
            h.connect0();
        }
    }

    private void onDisconnect(ClientPlayerEntity player) {
        for (final ClientHandler h : this.effectHandlers) {
            h.disconnect0();
        }

        isConnected = false;
    }

    protected boolean doTick() {
        return GameUtils.isInGame()
                && !GameUtils.getMC().isPaused()
                && !(GameUtils.getMC().currentScreen instanceof IndividualSoundControlScreen)
                && playerChunkLoaded();
    }

    protected boolean playerChunkLoaded() {
        var player = GameUtils.getPlayer();
        var pos = player.getBlockPos();
        return WorldUtils.isChunkLoaded(player.getEntityWorld(), pos);
    }

    public void onTick(MinecraftClient client) {
        if (!this.startupSoundPlayed)
            handleStartupSound();

        if (!doTick())
            return;

        this.handlerTimer.begin();
        final long tick = TickCounter.getTickCount();

        for (final ClientHandler handler : this.effectHandlers) {
            final long mark = System.nanoTime();
            if (handler.doTick(tick))
                handler.process(getPlayer());
            handler.updateTimer(System.nanoTime() - mark);
        }
        this.handlerTimer.end();
    }

    private void handleStartupSound() {
        var client = GameUtils.getMC();
        if (client.getOverlay() != null)
            return;

        startupSoundPlayed = true;
        DynamicSurroundings.SOUND_CONFIG
                .getRandomStartupSound()
                .ifPresent(id -> {
                    var sound = SoundFactoryBuilder
                            .create(id)
                            .build()
                            .createAsAdditional();
                    client.getSoundManager().play(sound);
                });
    }

    public void gatherDiagnostics(Collection<String> left, Collection<String> right, Collection<TimerEMA> timers) {
        timers.add(this.handlerTimer);

        this.effectHandlers.forEach(h -> {
            h.gatherDiagnostics(left, right, timers);
            timers.add(h.getTimer());
        });
    }
}
