package me.kubbidev.spellcaster.element;

import me.kubbidev.spellcaster.spell.handler.SpellHandler;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record BuiltinElement(Component icon,
                             @Nullable SpellHandler<?> regularSpell,
                             @Nullable SpellHandler<?> criticalSpell) implements Element {

    public static @NotNull BuiltinElement get(@NotNull Component icon) {
        return get(icon, null, null);
    }

    public static @NotNull BuiltinElement get(@NotNull Component icon,
                                              @Nullable SpellHandler<?> regularSpell,
                                              @Nullable SpellHandler<?> criticalSpell) {
        return new BuiltinElement(icon, regularSpell, criticalSpell);
    }
}
