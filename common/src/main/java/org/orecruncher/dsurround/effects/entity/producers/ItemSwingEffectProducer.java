package org.orecruncher.dsurround.effects.entity.producers;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.LivingEntity;
import org.orecruncher.dsurround.effects.IEntityEffect;
import org.orecruncher.dsurround.effects.entity.ItemSwingEffect;

import java.util.Collection;

public class ItemSwingEffectProducer  extends EntityEffectProducer {
    public Collection<IEntityEffect> produce(LivingEntity entity) {
        return ImmutableList.of(new ItemSwingEffect());
    }
}
