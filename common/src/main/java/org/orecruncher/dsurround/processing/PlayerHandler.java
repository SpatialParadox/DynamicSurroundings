package org.orecruncher.dsurround.processing;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.orecruncher.dsurround.DynamicSurroundings;
import org.orecruncher.dsurround.lib.GameUtils;
import org.orecruncher.dsurround.lib.reflection.ReflectedMethod;

public class PlayerHandler extends ClientHandler {

    private static final ReflectedMethod<Void> clearPotionSwirls = new ReflectedMethod<>(LivingEntity.class, "clearPotionSwirls", "method_6069");

    PlayerHandler() {
        super("Player Handler");
    }

    @Override
    public void process(final PlayerEntity player) {
        if (DynamicSurroundings.CONFIG.particleTweaks.suppressPlayerParticles && GameUtils.isInGame()) {
            if (clearPotionSwirls.isNotAvailable()) {
                DynamicSurroundings.LOGGER.info("clearPotionSwirls not available");
                return;
            }
            final boolean hide = GameUtils.isFirstPersonView();
            if (hide) {
                clearPotionSwirls.invoke(GameUtils.getPlayer());
            }
        }
    }
}
