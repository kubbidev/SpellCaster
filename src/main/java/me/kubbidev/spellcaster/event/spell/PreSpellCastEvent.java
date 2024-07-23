package me.kubbidev.spellcaster.event.spell;

import me.kubbidev.spellcaster.spell.SpellMetadata;
import me.kubbidev.spellcaster.spell.result.SpellResult;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PreSpellCastEvent extends SpellEvent implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    private final SpellResult result;
    protected boolean cancelled;

    /**
     * Called after checking that a spell can be cast by an entity
     * right before actually applying its effects.
     *
     * @param spellMeta The info of the spell being cast.
     * @param result    The spell result.
     */
    public PreSpellCastEvent(SpellMetadata spellMeta, SpellResult result) {
        super(spellMeta);
        this.result = result;
    }

    public @NotNull SpellResult getResult() {
        return this.result;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}