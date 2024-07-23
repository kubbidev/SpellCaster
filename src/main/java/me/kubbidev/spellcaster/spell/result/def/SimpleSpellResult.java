package me.kubbidev.spellcaster.spell.result.def;

import me.kubbidev.spellcaster.spell.result.SpellResult;

public class SimpleSpellResult implements SpellResult {
    private final boolean success;

    public SimpleSpellResult(boolean success) {
        this.success = success;
    }

    public SimpleSpellResult() {
        this(true);
    }

    @Override
    public boolean isSuccessful() {
        return this.success;
    }
}