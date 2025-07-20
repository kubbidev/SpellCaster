package me.kubbidev.spellcaster.listener.indicator.type;

import me.kubbidev.spellcaster.listener.indicator.IndicatorConfig;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class DamageIndicatorConfig extends IndicatorConfig {

    private final String spellIcon;
    private final String spellIconCrit;

    private final String  weaponIcon;
    private final String  weaponIconCrit;
    private final boolean splitHolograms;

    public DamageIndicatorConfig(
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
        long tickPeriod,
        @NotNull String spellIcon,
        @NotNull String spellIconCrit,
        @NotNull String weaponIcon,
        @NotNull String weaponIconCrit,
        boolean splitHolograms) {
        super(decimalFormat, format, gravity, radialVelocity, initialUpwardVelocity, entityHeightPercent, entityWidthPercent, yOffset,
            rOffset, move, lifespan, tickPeriod);

        this.spellIcon = spellIcon;
        this.spellIconCrit = spellIconCrit;

        this.weaponIcon = weaponIcon;
        this.weaponIconCrit = weaponIconCrit;
        this.splitHolograms = splitHolograms;
    }

    public @NotNull String getSpellIcon() {
        return this.spellIcon;
    }

    public @NotNull String getSpellIconCrit() {
        return this.spellIconCrit;
    }

    public @NotNull String getWeaponIcon() {
        return this.weaponIcon;
    }

    public @NotNull String getWeaponIconCrit() {
        return this.weaponIconCrit;
    }

    public boolean isSplitHolograms() {
        return this.splitHolograms;
    }
}
