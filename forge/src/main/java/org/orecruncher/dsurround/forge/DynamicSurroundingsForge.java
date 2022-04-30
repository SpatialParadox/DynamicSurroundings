package org.orecruncher.dsurround.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraft.util.Formatting;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkConstants;
import org.orecruncher.dsurround.DynamicSurroundings;
import org.orecruncher.dsurround.forge.config.clothapi.ClothAPIFactory;
import org.orecruncher.dsurround.lib.GameUtils;
import org.orecruncher.dsurround.lib.config.ConfigOptions;

@Mod(DynamicSurroundings.MOD_ID)
public class DynamicSurroundingsForge {
    public DynamicSurroundingsForge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(DynamicSurroundings.MOD_ID, eventBus);
        eventBus.addListener(this::onClientLoad);

        DynamicSurroundings.init();

        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
                () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));

        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class,
                () -> new ConfigGuiHandler.ConfigGuiFactory((client, parent) -> {
                    ConfigOptions options = new ConfigOptions()
                            .setTranslationRoot(DynamicSurroundings.MOD_ID + ".config")
                            .setPropertyGroupStyle(Formatting.GOLD)
                            .setPropertyValueStyle(Formatting.GRAY)
                            .setTooltipStyle(Formatting.WHITE)
                            .setStripTitle(false);

                    return new ClothAPIFactory(options, DynamicSurroundings.CONFIG).apply(GameUtils.getMC(), parent);
                }));

    }

    private void onClientLoad(FMLClientSetupEvent event) {
        DynamicSurroundings.onClientLoad();
    }
}
