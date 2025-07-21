package me.kubbidev.spellcaster.listener.indicator.type;

import me.kubbidev.spellcaster.listener.indicator.IndicatorConfig;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class DamageIndicatorConfig extends IndicatorConfig {

    private final Component spellIcon;
    private final Component spellIconCrit;

    private final Component weaponIcon;
    private final Component weaponIconCrit;
    private final boolean   splitHolograms;

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

        this.spellIcon = deserialize(spellIcon);
        this.spellIconCrit = deserialize(spellIconCrit);

        this.weaponIcon = deserialize(weaponIcon);
        this.weaponIconCrit = deserialize(weaponIconCrit);
        this.splitHolograms = splitHolograms;
    }

    public @NotNull Component getSpellIcon() {
        return this.spellIcon;
    }

    public @NotNull Component getSpellIconCrit() {
        return this.spellIconCrit;
    }

    public @NotNull Component getWeaponIcon() {
        return this.weaponIcon;
    }

    public @NotNull Component getWeaponIconCrit() {
        return this.weaponIconCrit;
    }

    public boolean isSplitHolograms() {
        return this.splitHolograms;
    }
}
