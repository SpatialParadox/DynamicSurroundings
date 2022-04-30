package org.orecruncher.dsurround.effects;

import net.minecraft.util.math.BlockPos;

public interface IBlockEffect {

    void tick();

    boolean isDone();

    void setDone();

    BlockPos getPos();
}
