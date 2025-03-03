package org.orecruncher.dsurround.eventing.handlers;

import dev.architectury.event.events.client.ClientTickEvent;
import joptsimple.internal.Strings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Formatting;
import org.orecruncher.dsurround.DynamicSurroundings;
import org.orecruncher.dsurround.eventing.ClientEventHooks;
import org.orecruncher.dsurround.lib.GameUtils;
import org.orecruncher.dsurround.lib.collections.ObjectArray;
import org.orecruncher.dsurround.lib.math.LoggingTimerEMA;
import org.orecruncher.dsurround.lib.math.TimerEMA;

public final class DiagnosticHandler {

    private static final LoggingTimerEMA diagnostics = new LoggingTimerEMA("Diagnostics");
    private static final ObjectArray<String> left = new ObjectArray<>(16);
    private static final ObjectArray<String> right = new ObjectArray<>(16);
    private static boolean enableCollection = false;

    static {
        ClientTickEvent.CLIENT_POST.register(DiagnosticHandler::tick);
    }

    public static void toggleCollection() {
        enableCollection = !enableCollection;
        left.clear();
        right.clear();
    }

    public static boolean isCollecting() {
        return enableCollection;
    }

    /**
     * Called by a mixin hook to obtain information for rendering in the diagnostic HUD
     */
    public static ObjectArray<String> getLeft() {
        return left;
    }

    /**
     * Called by a mixin hook to obtain information for rendering in the diagnostic HUD
     */
    public static ObjectArray<String> getRight() {
        return right;
    }

    private static void tick(MinecraftClient client) {
        if (enableCollection && GameUtils.isInGame()) {
            diagnostics.begin();

            left.clear();
            right.clear();

            left.add(DynamicSurroundings.BRANDING);

            ObjectArray<TimerEMA> timers = new ObjectArray<>(8);
            ObjectArray<String> temp = new ObjectArray<>(16);
            ClientEventHooks.COLLECT_DIAGNOSTICS.invoker().onCollect(left, temp, timers);

            right.add(Formatting.LIGHT_PURPLE + diagnostics.toString());

            if (timers.size() > 0) {
                for (TimerEMA timer : timers) {
                    right.add(Formatting.LIGHT_PURPLE + timer.toString());
                }
                right.add(Strings.EMPTY);
            }

            right.addAll(temp);

            diagnostics.end();
        }
    }
}
