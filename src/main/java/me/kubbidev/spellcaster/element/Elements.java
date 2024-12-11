package me.kubbidev.spellcaster.element;

import static net.kyori.adventure.text.format.NamedTextColor.*;

@SuppressWarnings({"UnnecessaryUnicodeEscape"})
interface Elements {

    Element FIRE = BuiltinElement.get("&c\uD83D\uDD25", RED);

    Element ICE = BuiltinElement.get("&b\u2744", AQUA);

    Element EARTH = BuiltinElement.get("&3\u20AA", DARK_GREEN);

    Element WIND = BuiltinElement.get("\uD83C\uDF0A", GRAY);

    Element THUNDER = BuiltinElement.get("\u2605", YELLOW);

    Element WATER = BuiltinElement.get("\uD83C\uDF0A", DARK_AQUA);

    Element DARKNESS = BuiltinElement.get("\u263D", DARK_GRAY);

    Element LIGHTNESS = BuiltinElement.get("\u2600", WHITE);
}