package org.orecruncher.dsurround.gui.sound;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.orecruncher.dsurround.lib.gui.SliderControl;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class VolumeSliderControl extends SliderControl {

    private static final int SLIDER_WIDTH = 100;
    private static final int SLIDER_HEIGHT = 20;

    private static final DecimalFormat FORMAT;

    static {
        FORMAT = new DecimalFormat("0.00");
        FORMAT.setRoundingMode(RoundingMode.HALF_UP);
        FORMAT.setDecimalSeparatorAlwaysShown(true);
    }

    private final IndividualSoundControlListEntry entry;

    public VolumeSliderControl(IndividualSoundControlListEntry entry, int x, int y) {
        this(entry, x, y, ImmutableList.of());
    }

    public VolumeSliderControl(IndividualSoundControlListEntry entry, int x, int y, List<OrderedText> toolTip) {
        super(x, y, SLIDER_WIDTH, SLIDER_HEIGHT, 0F, 400F, 1, entry.getData().volumeScale, toolTip);
        this.entry = entry;
        this.updateMessage();
    }

    protected void updateMessage() {
        Text text = (float) this.value == (float) this.getYImage(false) ? ScreenTexts.OFF : new LiteralText("x" + FORMAT.format(this.value * 4.0D));
        this.setMessage(text);
    }

    @Override
    protected void applyValue() {
        entry.getData().volumeScale = (int) this.getValue();
    }
}
