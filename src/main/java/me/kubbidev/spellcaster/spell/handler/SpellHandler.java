package me.kubbidev.spellcaster.spell.handler;

import me.kubbidev.spellcaster.InternalMethod;
import me.kubbidev.spellcaster.spell.Spell;
import me.kubbidev.spellcaster.spell.SpellMetadata;
import me.kubbidev.spellcaster.spell.result.SpellResult;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * {@link SpellHandler} are spells subtracted from all of there data.
 *
 * @param <T> Spell result class being used by that spell behaviour
 */
public abstract class SpellHandler<T extends SpellResult> {
    private final String id;
    private final Set<String> parameters = new HashSet<>();

    /**
     * Global random number generator used throughout the class.
     */
    protected static final Random random = new Random();

    /**
     * Used by default spell handlers.
     */
    public SpellHandler() {
        this.id = InternalMethod.convertToKebabCase(getClass().getSimpleName())
                .toLowerCase(Locale.ROOT)
                .replace("-", "_")
                .replace(" ", "_");

        registerParameters("cooldown", "mana", "stamina", "timer", "delay");
    }

    /**
     * Used by default spell handlers.
     *
     * @param id The spell handler identifier
     */
    public SpellHandler(String id) {
        this.id = id.toLowerCase(Locale.ROOT)
                .replace("-", "_")
                .replace(" ", "_");

        registerParameters("cooldown", "mana", "stamina", "timer", "delay");
    }

    public @NotNull String getId() {
        return this.id;
    }

    public @NotNull Set<String> getParameters() {
        return this.parameters;
    }

    public void registerParameters(String... params) {
        registerParameters(Arrays.asList(params));
    }

    public void registerParameters(Collection<String> params) {
        this.parameters.addAll(params);
    }

    /**
     * Gets the spell result used to check if the spell can be cast.
     * <p>
     * This method evaluates custom conditions, checks if the caster has an entity
     * in their line of sight, if he is on the ground...
     * <p>
     * Runs first before {@link Spell#getResult(SpellMetadata)}
     *
     * @param meta The info of spell being cast.
     * @return A spell result
     */
    public abstract T getResult(SpellMetadata meta);

    /**
     * This is where the actual spell effects are applied.
     * <p>
     * Runs last, after {@link Spell#whenCast(SpellMetadata)}
     *
     * @param result The spell result.
     * @param meta   The info of spell being cast.
     */
    public abstract void whenCast(T result, SpellMetadata meta);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpellHandler<?> other)) {
            return false;
        }
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}