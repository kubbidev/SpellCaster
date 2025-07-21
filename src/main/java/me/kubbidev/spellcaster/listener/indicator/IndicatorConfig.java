package me.kubbidev.spellcaster.listener.indicator;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class IndicatorConfig {

    protected static Component deserialize(String string) {
        return MiniMessage.miniMessage().deserialize(string);
    }

    private final DecimalFormat decimalFormat;
    private final Component     format;

    private final double  gravity;
    private final double  radialVelocity;
    private final double  initialUpwardVelocity;
    private final double  entityHeightPercent;
    private final double  entityWidthPercent;
    private final double  yOffset;
    private final double  rOffset;
    private final boolean move;
    private final long    lifespan;
    private final long    tickPeriod;

    public IndicatorConfig(
        @NotNull DecimalFormat decimalFormat,
        @NotNull String format,
        double gravity,
        double radialVelocity,
        double initialUpwardVelocity,
        double entityHeightPercent,
        double entityWidthPercent,
        double yOffset,
        double rOffset,
        boolean move,
        long lifespan,
        long tickPeriod
    ) {
        this.decimalFormat = decimalFormat;
        this.format = deserialize(format);
        this.gravity = gravity;
        this.radialVelocity = radialVelocity;
        this.initialUpwardVelocity = initialUpwardVelocity;
        this.entityHeightPercent = entityHeightPercent;
        this.entityWidthPercent = entityWidthPercent;
        this.yOffset = yOffset;
        this.rOffset = rOffset;
        this.move = move;
        this.lifespan = lifespan;
        this.tickPeriod = tickPeriod;
    }

    public @NotNull DecimalFormat getDecimalFormat() {
        return this.decimalFormat;
    }

    public @NotNull Component getFormat() {
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

    public double getEntityHeightPercent() {
        return this.entityHeightPercent;
    }

    public double getEntityWidthPercent() {
        return this.entityWidthPercent;
    }

    public double getYOffset() {
        return this.yOffset;
    }

    public double getROffset() {
        return this.rOffset;
    }

    public boolean isMove() {
        return this.move;
    }

    public long getLifespan() {
        return this.lifespan;
    }

    public long getTickPeriod() {
        return this.tickPeriod;
    }
}
