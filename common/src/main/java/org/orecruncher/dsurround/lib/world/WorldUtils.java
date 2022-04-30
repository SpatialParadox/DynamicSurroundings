package org.orecruncher.dsurround.lib.world;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;
import org.orecruncher.dsurround.mixin.core.MixinChunkManagerAccessor;
import org.orecruncher.dsurround.mixin.core.MixinChunkMapAccessor;
import org.orecruncher.dsurround.mixin.core.MixinClientWorldProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.Predicate;

public class WorldUtils {
    /**
     * Temperatures LESS than this value are considered cold temperatures.
     */
    public static final float COLD_THRESHOLD = 0.2F;

    /**
     * Temperatures LESS than this value are considered cold enough for snow.
     */
    public static final float SNOW_THRESHOLD = 0.15F;

    public static boolean isSuperFlat(final World world) {
        final WorldProperties info = world.getLevelProperties();
        return info instanceof MixinClientWorldProperties && ((MixinClientWorldProperties) info).isFlatWorld();
    }

    public static BlockPos getTopSolidOrLiquidBlock(final World world, final BlockPos pos) {
        return world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos);
    }

    public static float getTemperatureAt(final World world, final BlockPos pos) {
        return world.getBiomeAccess().getBiome(pos).value().getTemperature();
    }

    public static int getPrecipitationHeight(final World world, final BlockPos pos) {
        return world.getTopY(Heightmap.Type.MOTION_BLOCKING, pos.getX(), pos.getZ());
    }

    public static List<BlockEntity> getLoadedBlockEntities(World world, Predicate<BlockEntity> predicate) {
        MixinChunkManagerAccessor chunkManager =  (MixinChunkManagerAccessor) world.getChunkManager();
        AtomicReferenceArray<WorldChunk> chunks = ((MixinChunkMapAccessor) chunkManager.getChunkMap()).getChunks();

        List<BlockEntity> entities = new ArrayList<>();
        for (int i = 0; i < chunks.length(); i++) {
            WorldChunk chunk = chunks.get(i);
            if (chunk != null) {
                for (BlockEntity entity : chunk.getBlockEntities().values()) {
                    if (predicate.test(entity)) {
                        entities.add(entity);
                    }
                }
            }
        }

        return entities;
    }

    /**
     * Gets the precipitation currently falling at the specified location.  It takes into account temperature and the
     * like.
     */
    public static Biome.Precipitation getCurrentPrecipitationAt(final World world, final BlockPos pos) {
        if (!world.isRaining()) {
            // Not currently raining
            return Biome.Precipitation.NONE;
        }

        final Biome biome = world.getBiome(pos).value();

        // If the biome has no rain...
        if (biome.getPrecipitation() == Biome.Precipitation.NONE)
            return Biome.Precipitation.NONE;

        // Is there a block above that is blocking the rainfall?
        var p = getPrecipitationHeight(world, pos);
        if (p > pos.getY()) {
            return Biome.Precipitation.NONE;
        }

        // Use the temperature of the biome to get whether it is raining or snowing
        final float temp = getTemperatureAt(world, pos);
        return isSnowTemperature(temp) ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN;
    }

    /**
     * Determines if the temperature value is considered a cold temperature.
     */
    public static boolean isColdTemperature(final float temp) {
        return temp < COLD_THRESHOLD;
    }

    /**
     * Determines if the temperature value is considered cold enough for snow.
     */
    public static boolean isSnowTemperature(final float temp) {
        return temp < SNOW_THRESHOLD;
    }

    public static boolean isChunkLoaded(World world, BlockPos pos) {
        var chunkX = ChunkSectionPos.getSectionCoord(pos.getX());
        var chunkZ = ChunkSectionPos.getSectionCoord(pos.getZ());
        return world.getChunkManager().isChunkLoaded(chunkX, chunkZ);
    }
}
