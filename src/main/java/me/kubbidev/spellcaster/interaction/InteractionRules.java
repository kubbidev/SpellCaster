package me.kubbidev.spellcaster.interaction;

import me.kubbidev.spellcaster.interaction.relation.Relationship;
import org.jetbrains.annotations.NotNull;

public interface InteractionRules {

    /**
     * Gets if in general, support spells should also take {@link org.bukkit.entity.Mob}
     * and {@link org.bukkit.entity.Creature} in count when applied.
     *
     * @return true if supported, otherwise false
     */
    boolean isSupportSpellsOnMobs();

    /**
     * Gets whether the specified {@link InteractionType} is enabled taking account different parameters.
     *
     * @param interaction  The type of entity interaction
     * @param relationship The relationship between the entities
     * @param pvp          If the PvP is enabled at a specific location
     * @return true this specific interaction is enabled, otherwise false
     */
    boolean isEnabled(@NotNull InteractionType interaction, @NotNull Relationship relationship, boolean pvp);
}