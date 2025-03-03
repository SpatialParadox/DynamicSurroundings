package org.orecruncher.dsurround.mixin.core;

import net.minecraft.world.biome.Biome;
import org.orecruncher.dsurround.config.biome.BiomeInfo;
import org.orecruncher.dsurround.xface.IBiomeExtended;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class MixinBiome implements IBiomeExtended {

    private BiomeInfo dsurround_info;

    @Override
    public BiomeInfo getInfo() {
        return this.dsurround_info;
    }

    @Override
    public void setInfo(BiomeInfo info) {
        this.dsurround_info = info;
    }

    /**
     * Obtain fog color from Dynamic Surroundings' config if available.
     *
     * @param cir Mixin callback result
     */
    @Inject(method = "getFogColor()I", at = @At("HEAD"), cancellable = true)
    public void dsurround_getFogColor(CallbackInfoReturnable<Integer> cir) {
        if (this.dsurround_info != null) {
            var color = this.dsurround_info.getFogColor();
            if (color != null)
                cir.setReturnValue(color.getRGB());
        }
    }
}
