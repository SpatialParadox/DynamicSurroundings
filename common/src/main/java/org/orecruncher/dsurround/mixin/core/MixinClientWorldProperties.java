package org.orecruncher.dsurround.mixin.core;

import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientWorld.Properties.class)
public interface MixinClientWorldProperties {
    @Accessor("flatWorld")
    boolean isFlatWorld();
}
