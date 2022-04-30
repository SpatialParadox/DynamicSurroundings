package org.orecruncher.dsurround.mixin.core;

import net.minecraft.client.world.ClientChunkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientChunkManager.class)
public interface MixinChunkManagerAccessor {
    @Accessor("chunks")
    ClientChunkManager.ClientChunkMap getChunkMap();
}
