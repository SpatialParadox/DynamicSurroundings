package org.orecruncher.dsurround.eventing;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.util.math.BlockPos;
import org.orecruncher.dsurround.lib.Guard;
import org.orecruncher.dsurround.lib.math.TimerEMA;

import java.util.Collection;

public final class ClientEventHooks {

    /**
     * Used to collect diagnostic information for display in the debug HUD
     */
    public static Event<CollectDiagnosticsEvent> COLLECT_DIAGNOSTICS = EventFactory.of(
            callbacks -> (left, right, timers) -> {
                for (CollectDiagnosticsEvent callback : callbacks)
                    Guard.execute(() -> callback.onCollect(left, right, timers));
            });

    /**
     * Fired when block state updates are received clientside.  Results are coalesced for efficiency.
     */
    public static Event<BlockUpdateEvent> BLOCK_UPDATE = EventFactory.of(
            callbacks -> (positions) -> {
                for (BlockUpdateEvent callback : callbacks)
                    Guard.execute(() -> callback.onUpdate(positions));
            });

    @FunctionalInterface
    public interface CollectDiagnosticsEvent {
        void onCollect(Collection<String> left, Collection<String> right, Collection<TimerEMA> timers);
    }

    @FunctionalInterface
    public interface BlockUpdateEvent {
        void onUpdate(Collection<BlockPos> positions);
    }
}
