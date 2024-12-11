package me.kubbidev.spellcaster.element;

import me.kubbidev.spellcaster.spell.handler.SpellHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record BuiltinElement(String icon,
                             @Nullable SpellHandler<?> regularSpell,
                             @Nullable SpellHandler<?> criticalSpell) implements Element {

    public static @NotNull BuiltinElement get(@NotNull String icon) {
        return get(icon, null, null);
    }

    public static @NotNull BuiltinElement get(@NotNull String icon,
                                              @Nullable SpellHandler<?> regularSpell,
                                              @Nullable SpellHandler<?> criticalSpell) {
        return new BuiltinElement(icon, regularSpell, criticalSpell);
    }
}
