package org.orecruncher.dsurround.effects.particles;

import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import org.orecruncher.dsurround.lib.GameUtils;
import org.orecruncher.dsurround.lib.random.XorShiftRandom;
import org.orecruncher.dsurround.mixin.core.MixinParticleManager;

import java.util.Random;

public final class ParticleUtils {

    private static final Random RANDOM = XorShiftRandom.current();

    public static SpriteProvider getSpriteProvider(ParticleType<?> particleType) {
        var id = Registry.PARTICLE_TYPE.getId(particleType);
        return ((MixinParticleManager) GameUtils.getMC().particleManager).getSpriteAwareFactories().get(id);
    }

    public static Vec3d getBreathOrigin(final LivingEntity entity) {
        final Vec3d eyePosition = eyePosition(entity).subtract(0D, entity.isBaby() ? 0.1D : 0.2D, 0D);
        final Vec3d look = entity.getRotationVec(1F); // Don't use the other look vector method!
        return eyePosition.add(look.multiply(entity.isBaby() ? 0.25D : 0.5D));
    }

    public static Vec3d getLookTrajectory(final LivingEntity entity) {
        return entity.getRotationVec(1F)
                .rotateZ(RANDOM.nextFloat() * 2F)   // yaw
                .rotateY(RANDOM.nextFloat() * 2F)   // pitch
                .normalize();
    }

    /*
     * Use some corrective lenses because the MC routine just doesn't lower the
     * height enough for our rendering purpose.
     */
    private static Vec3d eyePosition(final Entity e) {
        var y = e.getEyePos();
        if (e.isSneaking()) {
            y = y.subtract(0, 0.25D, 0);
        }
        return y;
    }
}