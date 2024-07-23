package me.kubbidev.spellcaster.event.attack.fake;

import me.kubbidev.spellcaster.interaction.InteractionType;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class DamageCheckEvent extends FakeEntityDamageByEntityEvent {

    @NotNull
    private final InteractionType interactionType;

    /**
     * This is the fake event used to determine if an entity can hit ANY entity.
     *
     * @param damager         The entity damaging the other entity.
     * @param victim          The entity being attacked.
     * @param interactionType The interaction type check for this event.
     */
    public DamageCheckEvent(@NotNull Entity damager, @NotNull Entity victim, @NotNull InteractionType interactionType) {
        super(damager, victim, DamageCause.ENTITY_ATTACK, 0);
        this.interactionType = interactionType;
    }

    public @NotNull InteractionType getInteractionType() {
        return this.interactionType;
    }
}