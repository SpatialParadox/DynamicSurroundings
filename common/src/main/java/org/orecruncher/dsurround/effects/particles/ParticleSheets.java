package org.orecruncher.dsurround.effects.particles;

import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.util.Identifier;
import org.orecruncher.dsurround.DynamicSurroundings;
import org.orecruncher.dsurround.lib.GameUtils;
import org.orecruncher.dsurround.mixin.core.MixinParticleManager;

import java.util.ArrayList;

public final class ParticleSheets {

    public static final Identifier TEXTURE_WATER_RIPPLE_PIXELATED_CIRCLE = new Identifier(DynamicSurroundings.MOD_ID,
            "textures/particles/pixel_ripples.png");

    public static final ParticleTextureSheet RIPPLE_RENDER =
            new ParticleRenderType(TEXTURE_WATER_RIPPLE_PIXELATED_CIRCLE) {
                @Override
                protected Identifier getTexture() {
                    return DynamicSurroundings.CONFIG.blockEffects.waterRippleStyle.getTexture();
                }
            };

    public static void register() {

        var manager = GameUtils.getMC().getTextureManager();
        manager.registerTexture(TEXTURE_WATER_RIPPLE_PIXELATED_CIRCLE, new ResourceTexture(TEXTURE_WATER_RIPPLE_PIXELATED_CIRCLE));

        var existingSheets = MixinParticleManager.getParticleTextureSheets();
        assert existingSheets != null;
        existingSheets = new ArrayList<>(existingSheets);
        existingSheets.add(RIPPLE_RENDER);
        MixinParticleManager.setParticleTextureSheets(existingSheets);
    }
}
