package org.orecruncher.dsurround.fabric;

import net.fabricmc.api.ClientModInitializer;
import org.orecruncher.dsurround.DynamicSurroundings;

public class DynamicSurroundingsFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DynamicSurroundings.onClientLoad();
    }
}
