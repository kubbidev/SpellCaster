package me.kubbidev.spellcaster.listener.indicator.type;

import me.kubbidev.nexuspowered.util.Text;
import me.kubbidev.spellcaster.listener.indicator.IndicatorConfig;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class DamageIndicatorConfig extends IndicatorConfig {
    private final Component spellIcon;
    private final Component spellIconCrit;
    private final Component weaponIcon;
    private final Component weaponIconCrit;

    private final boolean splitHolograms;

    public DamageIndicatorConfig(@NotNull DecimalFormat decimalFormat,
                                 @NotNull String format,
                                 double gravity,
                                 double radialVelocity,
                                 double initialUpwardVelocity,
                                 double entityHeightP,
                                 double entityYOffset,
                                 @NotNull String spellIcon,
                                 @NotNull String spellIconCrit,
                                 @NotNull String weaponIcon,
                                 @NotNull String weaponIconCrit,
                                 boolean splitHolograms) {
        super(decimalFormat, format, gravity, radialVelocity, initialUpwardVelocity, entityHeightP, entityYOffset);

        this.spellIcon = Text.fromMiniMessage(spellIcon);
        this.spellIconCrit = Text.fromMiniMessage(spellIconCrit);

        this.weaponIcon = Text.fromMiniMessage(weaponIcon);
        this.weaponIconCrit = Text.fromMiniMessage(weaponIconCrit);
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
