package me.kubbidev.spellcaster.element;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;
import static net.kyori.adventure.text.format.NamedTextColor.DARK_AQUA;
import static net.kyori.adventure.text.format.NamedTextColor.DARK_GRAY;
import static net.kyori.adventure.text.format.NamedTextColor.GOLD;
import static net.kyori.adventure.text.format.NamedTextColor.GRAY;
import static net.kyori.adventure.text.format.NamedTextColor.RED;
import static net.kyori.adventure.text.format.NamedTextColor.WHITE;
import static net.kyori.adventure.text.format.NamedTextColor.YELLOW;

@SuppressWarnings({"UnnecessaryUnicodeEscape"})
interface Elements {

    Element FIRE = BuiltinElement.get(text("\uD83D\uDD25", RED));

    Element ICE = BuiltinElement.get(text('\u2744', AQUA));

    Element EARTH = BuiltinElement.get(text('\u20AA', GOLD));

    Element WIND = BuiltinElement.get(text("\uD83C\uDF0A", GRAY));

    Element THUNDER = BuiltinElement.get(text('\u2605', YELLOW));

    Element WATER = BuiltinElement.get(text("\uD83C\uDF0A", DARK_AQUA));

    Element DARKNESS = BuiltinElement.get(text('\u263D', DARK_GRAY));

    Element LIGHTNESS = BuiltinElement.get(text('\u2600', WHITE));
}