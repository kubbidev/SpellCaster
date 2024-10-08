package me.kubbidev.spellcaster.event.indicator;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;

public class IndicatorDisplayEvent extends EntityEvent implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    private boolean cancelled;

    private final IndicatorType type;
    private Component text;

    /**
     * Called when an entity emits either a damage or a healing indicator.
     *
     * @param entity The entity emitting the indicator
     * @param text   The message displayed
     * @param type   The type of indicator
     */
    public IndicatorDisplayEvent(@NotNull Entity entity, @NotNull Component text, @NotNull IndicatorType type) {
        super(entity);
        this.text = text;
        this.type = type;
    }

    public IndicatorType getType() {
        return this.type;
    }

    public Component getText() {
        return this.text;
    }

    public void setText(Component text) {
        this.text = text;
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

    public enum IndicatorType {

        /**
         * Displayed when an entity is being damaged
         */
        DAMAGE,

        /**
         * Displayed when an entity regenerates some health
         */
        REGENERATION
    }
}