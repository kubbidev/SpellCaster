package me.kubbidev.spellcaster.element;

import me.kubbidev.spellcaster.spell.handler.SpellHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Element extends Elements {

    @NotNull
    String icon();

    @Nullable
    SpellHandler<?> regularSpell();

    @Nullable
    SpellHandler<?> criticalSpell();
}