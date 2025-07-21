package me.kubbidev.spellcaster.element;

import me.kubbidev.spellcaster.spell.handler.SpellHandler;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Element extends Elements {

    @NotNull Component icon();

    @Nullable SpellHandler<?> regularSpell();

    @Nullable SpellHandler<?> criticalSpell();
}