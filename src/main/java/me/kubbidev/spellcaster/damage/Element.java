package me.kubbidev.spellcaster.damage;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import static net.kyori.adventure.text.Component.text;

@SuppressWarnings("UnnecessaryUnicodeEscape")
public enum Element {
    FIRE     (text("\uD83D\uDD25", NamedTextColor.RED)),
    ICE      (text("\u2744",       NamedTextColor.AQUA)),
    EARTH    (text("\u20AA",       NamedTextColor.DARK_GREEN)),
    WIND     (text("\uD83C\uDF0A", NamedTextColor.GRAY)),
    THUNDER  (text("\u2605",       NamedTextColor.YELLOW)),
    WATER    (text("\uD83C\uDF0A", NamedTextColor.DARK_AQUA)),
    DARKNESS (text("\u263D",       NamedTextColor.DARK_GRAY)),
    LIGHTNESS(text("\u2600",       NamedTextColor.WHITE));

    private final Component icon;

    Element(Component icon) {
        this.icon = icon;
    }

    public Component getIcon() {
        return this.icon;
    }
}