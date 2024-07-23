package me.kubbidev.spellcaster.interaction;

import me.kubbidev.spellcaster.interaction.relation.Relationship;
import org.jetbrains.annotations.NotNull;

public final class EmptyInteractionRules implements InteractionRules {
    @Override
    public boolean isSupportSpellsOnMobs() {
        return true;
    }

    @Override
    public boolean isEnabled(@NotNull InteractionType interaction, @NotNull Relationship relationship, boolean pvp) {
        return true;
    }
}
