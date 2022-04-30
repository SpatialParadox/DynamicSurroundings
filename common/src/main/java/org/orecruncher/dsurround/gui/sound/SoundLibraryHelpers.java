package org.orecruncher.dsurround.gui.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.orecruncher.dsurround.DynamicSurroundings;
import org.orecruncher.dsurround.config.IndividualSoundConfigEntry;
import org.orecruncher.dsurround.config.SoundLibrary;
import org.orecruncher.dsurround.sound.MinecraftAudioPlayer;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

final class SoundLibraryHelpers {

    public static Collection<IndividualSoundConfigEntry> getSortedSoundConfigurations() {

        final SortedMap<Identifier, IndividualSoundConfigEntry> map = new TreeMap<>();

        // Get a list of all registered sounds.  We don't use the vanilla registries since
        // we will have more sounds than are registered.
        for (final SoundEvent event : SoundLibrary.getRegisteredSoundEvents()) {
            IndividualSoundConfigEntry entry = IndividualSoundConfigEntry.createDefault(event);
            map.put(entry.soundEventId, entry);
        }

        // Override with the defaults from configuration.  Make a copy of the original, so it doesn't change.
        for (IndividualSoundConfigEntry entry : DynamicSurroundings.SOUND_CONFIG.getIndividualSoundConfigs()) {
            map.put(entry.soundEventId, entry);
        }

        final Comparator<IndividualSoundConfigEntry> iscComparator = Comparator.comparing(isc -> isc.soundEventId);
        return map.values().stream().sorted(iscComparator).collect(Collectors.toList());
    }

    public static ConfigSoundInstance playSound(IndividualSoundConfigEntry entry) {
        ConfigSoundInstance sound = new ConfigSoundInstance(entry.soundEventId, entry.volumeScale);
        MinecraftAudioPlayer.INSTANCE.play(sound);
        return sound;
    }

    public static void stopSound(ConfigSoundInstance sound) {
        MinecraftAudioPlayer.INSTANCE.stop(sound);
    }

    public static boolean isPlaying(ConfigSoundInstance sound) {
        return MinecraftAudioPlayer.INSTANCE.isPlaying(sound);
    }
}
