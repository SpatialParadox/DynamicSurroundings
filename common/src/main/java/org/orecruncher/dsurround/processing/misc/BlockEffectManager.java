package org.orecruncher.dsurround.processing.misc;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.orecruncher.dsurround.DynamicSurroundings;
import org.orecruncher.dsurround.effects.IBlockEffect;
import org.orecruncher.dsurround.lib.BlockPosUtil;

import java.util.function.Predicate;

public class BlockEffectManager        {

    private static final Predicate<IBlockEffect> STANDARD = system -> {
        system.tick();
        return system.isDone();
    };

    public BlockEffectManager() {
    }

    private final Long2ObjectOpenHashMap<IBlockEffect> systems = new Long2ObjectOpenHashMap<>(512);
    private BlockPos lastPos = BlockPos.ORIGIN;

    public void tick(PlayerEntity player) {
        final BlockPos current = player.getBlockPos();
        final boolean sittingStill = this.lastPos.equals(current);
        this.lastPos = current;

        Predicate<IBlockEffect> pred = STANDARD;

        if (!sittingStill) {
            final double range = DynamicSurroundings.CONFIG.blockEffects.blockEffectRange;
            final BlockPos min = new BlockPos(current.getX() - range, current.getY() - range, current.getZ() - range);
            final BlockPos max = new BlockPos(current.getX() + range, current.getY() + range, current.getZ() + range);

            pred = system -> {

                if (BlockPosUtil.notContains(system.getPos(), min, max)) {
                    system.setDone();
                } else {
                    system.tick();
                }
                return system.isDone();
            };
        }

        this.systems.values().removeIf(pred);
    }

    // Determines if it is OK to spawn a particle system at the specified
    // location. Generally only a single system can occupy a block.
    public boolean okToSpawn(final BlockPos pos) {
        return !this.systems.containsKey(pos.asLong());
    }

    public void add(final IBlockEffect system) {
        this.systems.put(system.getPos().asLong(), system);
    }

    public int count() {
        return this.systems.size();
    }

}