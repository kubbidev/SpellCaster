package me.kubbidev.spellcaster.event.spell;

import me.kubbidev.spellcaster.event.LivingEntityEvent;
import me.kubbidev.spellcaster.spell.Spell;
import me.kubbidev.spellcaster.spell.SpellMetadata;
import org.jetbrains.annotations.NotNull;

public abstract class SpellEvent extends LivingEntityEvent {
    private final SpellMetadata spellMeta;

    public SpellEvent(SpellMetadata spellMeta) {
        super(spellMeta.entity());
        this.spellMeta = spellMeta;
    }

    public @NotNull SpellMetadata getSpellMeta() {
        return this.spellMeta;
    }

    public @NotNull Spell getSpell() {
        return this.spellMeta.cast();
    }
}