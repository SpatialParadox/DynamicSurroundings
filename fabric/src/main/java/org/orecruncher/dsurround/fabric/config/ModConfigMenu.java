package org.orecruncher.dsurround.fabric.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.util.Formatting;
import org.orecruncher.dsurround.DynamicSurroundings;
import org.orecruncher.dsurround.fabric.config.clothapi.ClothAPIFactory;
import org.orecruncher.dsurround.lib.GameUtils;
import org.orecruncher.dsurround.lib.config.ConfigOptions;

public class ModConfigMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigOptions options = new ConfigOptions()
                    .setTranslationRoot(DynamicSurroundings.MOD_ID + ".config")
                    .setPropertyGroupStyle(Formatting.GOLD)
                    .setPropertyValueStyle(Formatting.GRAY)
                    .setTooltipStyle(Formatting.WHITE)
                    .setStripTitle(false);

            return new ClothAPIFactory(options, DynamicSurroundings.CONFIG).apply(GameUtils.getMC(), parent);
        };
    }
}
