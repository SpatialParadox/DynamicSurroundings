package org.orecruncher.dsurround.lib;

import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.MinecraftClient;

/**
 * Monotonically increasing tick count based on client ticks.
 */
public final class TickCounter {

    private static long tickCount = 0;

    private TickCounter() {
    }

    public static void register() {
        ClientTickEvent.CLIENT_PRE.register(TickCounter::onClientTick);
    }

    private static void onClientTick(final MinecraftClient event) {
        tickCount++;
    }

    public static long getTickCount() {
        return tickCount;
    }
}