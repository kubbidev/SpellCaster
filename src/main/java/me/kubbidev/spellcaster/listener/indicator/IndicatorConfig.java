package me.kubbidev.spellcaster.listener.indicator;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class IndicatorConfig {
    private final DecimalFormat decimalFormat;
    private final String format;

    private final double gravity;
    private final double radialVelocity;
    private final double initialUpwardVelocity;
    private final double entityHeightP;
    private final double entityYOffset;

    public IndicatorConfig(
            @NotNull DecimalFormat decimalFormat,
            @NotNull String format,
            double gravity,
            double radialVelocity,
            double initialUpwardVelocity,
            double entityHeightP,
            double entityYOffset) {
        this.decimalFormat = decimalFormat;
        this.format = format;
        this.gravity = gravity;
        this.radialVelocity = radialVelocity;
        this.initialUpwardVelocity = initialUpwardVelocity;
        this.entityHeightP = entityHeightP;
        this.entityYOffset = entityYOffset;
    }

    public @NotNull DecimalFormat getDecimalFormat() {
        return this.decimalFormat;
    }

    public @NotNull String getFormat() {
        return this.format;
    }

    public double getGravity() {
        return this.gravity;
    }

    public double getRadialVelocity() {
        return this.radialVelocity;
    }

    public double getInitialUpwardVelocity() {
        return this.initialUpwardVelocity;
    }

    public double getEntityHeightP() {
        return this.entityHeightP;
    }

    public double getEntityYOffset() {
        return this.entityYOffset;
    }
}
