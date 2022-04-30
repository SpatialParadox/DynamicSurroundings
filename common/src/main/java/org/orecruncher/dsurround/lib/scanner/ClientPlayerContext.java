package org.orecruncher.dsurround.lib.scanner;

import org.orecruncher.dsurround.DynamicSurroundings;
import org.orecruncher.dsurround.lib.GameUtils;

public class ClientPlayerContext extends ScanContext {

    public ClientPlayerContext() {
        super(
                GameUtils::getWorld,
                () -> GameUtils.getPlayer().getBlockPos(),
                () -> DynamicSurroundings.LOGGER,
                () -> GameUtils.getWorld().getRegistryKey().getValue()
        );
    }

}