package org.orecruncher.dsurround.runtime;

import net.minecraft.world.biome.Biome;
import org.orecruncher.dsurround.DynamicSurroundings;
import org.orecruncher.dsurround.lib.scripting.ExecutionContext;
import org.orecruncher.dsurround.lib.scripting.Script;
import org.orecruncher.dsurround.runtime.sets.BiomeVariables;

import java.util.Optional;

public class BiomeConditionEvaluator {

    public static BiomeConditionEvaluator INSTANCE = new BiomeConditionEvaluator();
    // Internal visibility for diagnostics
    final BiomeVariables biomeVariables;
    private final ExecutionContext context;

    public BiomeConditionEvaluator() {
        this.context = new ExecutionContext("BiomeConditions");
        this.biomeVariables = new BiomeVariables();
        this.context.add(this.biomeVariables);
    }

    public boolean check(Biome biome, final Script conditions) {
        final Object result = eval(biome, conditions);
        return result instanceof Boolean && (boolean) result;
    }

    public Object eval(Biome biome, final Script conditions) {
        try {
            this.biomeVariables.setBiome(biome);
            final Optional<Object> result = this.context.eval(conditions);
            return result.orElse(false);
        } catch (Throwable t) {
            DynamicSurroundings.LOGGER.error(t, "Unable to evaluate script");
        }
        return false;
    }
}
