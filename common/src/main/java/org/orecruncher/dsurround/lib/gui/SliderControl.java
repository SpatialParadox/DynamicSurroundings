package org.orecruncher.dsurround.lib.gui;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.util.OrderableTooltip;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public abstract class SliderControl extends SliderWidget implements OrderableTooltip {

    protected final double step;
    protected final double min;
    protected final List<OrderedText> toolTip;
    protected double max;

    public SliderControl(int x, int y, int width, int height, double minValue, double maxValue, float valueStep, double currentValue, List<OrderedText> toolTip) {
        super(x, y, width, height, LiteralText.EMPTY, getRatio(currentValue, minValue, maxValue, valueStep));
        this.min = minValue;
        this.max = maxValue;
        this.step = valueStep;
        this.toolTip = toolTip;
    }

    private static double getRatio(double value, double min, double max, double step) {
        double adjusted = adjust(value, min, max, step);
        return MathHelper.clamp((adjusted - min) / (max - min), 0.0D, 1.0D);
    }

    private static double getValue(double ratio, double min, double max, double step) {
        double value = MathHelper.clamp(ratio, 0.0D, 1.0D);
        return adjust(MathHelper.lerp(value, min, max), min, max, step);
    }

    private static double adjust(double value, double min, double max, double step) {
        if (step > 0) {
            value = (step * (float) Math.round(value / step));
        }
        return MathHelper.clamp(value, min, max);
    }

    public double getValue() {
        return SliderControl.getValue(this.value, this.min, this.max, this.step);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    protected abstract void updateMessage();

    @Override
    protected abstract void applyValue();

    @Override
    public List<OrderedText> getOrderedTooltip() {
        return this.toolTip;
    }
}