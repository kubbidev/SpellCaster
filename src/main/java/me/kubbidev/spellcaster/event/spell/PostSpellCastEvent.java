package me.kubbidev.spellcaster.event.spell;

import me.kubbidev.spellcaster.spell.SpellMetadata;
import me.kubbidev.spellcaster.spell.result.SpellResult;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PostSpellCastEvent extends SpellEvent {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    private final SpellResult result;

    /**
     * Called after an entity has successfully cast a spell.
     *
     * @param spellMetadata The spell of the spell that has been cast.
     * @param result        The spell result.
     */
    public PostSpellCastEvent(SpellMetadata spellMetadata, SpellResult result) {
        super(spellMetadata);
        this.result = result;
    }

    public @NotNull SpellResult getResult() {
        return this.result;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}