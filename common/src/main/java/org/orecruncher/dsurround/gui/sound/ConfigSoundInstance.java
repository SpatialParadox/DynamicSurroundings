package org.orecruncher.dsurround.gui.sound;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

/**
 * Special sound instance created by the sound configuration option menu.  The type is detected through the pipeline
 * to avoid applying behaviors like blocking and volume scaling.
 */
public class ConfigSoundInstance extends PositionedSoundInstance {
    public ConfigSoundInstance(Identifier id, int volumeScale) {
        super(id, SoundCategory.AMBIENT, volumeScale / 100F, 1F, false, 0, AttenuationType.NONE, 0.0D, 0.0D, 0.0D, true);
    }
}
