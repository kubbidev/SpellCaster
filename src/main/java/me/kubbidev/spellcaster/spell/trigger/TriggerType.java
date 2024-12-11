package me.kubbidev.spellcaster.spell.trigger;

import me.kubbidev.spellcaster.spell.handler.SpellHandler;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static me.kubbidev.spellcaster.InternalMethod.caseOnWords;

public class TriggerType {

    /**
     * Casts the spell at regular time intervals.
     * <p>
     * Timer period can be edited using the corresponding spell parameter.
     */
    @NotNull
    public static TriggerType TIMER = new TriggerType("TIMER"),

    /**
     * Used when an entity actively casts a spell.
     * <p>
     * This is the only trigger type which you can use to create an active spell.
     * Any other trigger type is used for passive spells.
     */
    CAST = new TriggerType("CAST", false, false),

    /**
     * Should be used by plugins when passive spells get triggered by
     * another cause not listed in {@link TriggerType}.
     * <p>
     * This trigger type is used by any hard coded passive spells.
     *
     * @see SpellHandler#isTriggerable()
     */
    API = new TriggerType("API");

    static {
        register(TIMER);
        register(CAST);
        register(API);
    }

    private static final Map<String, TriggerType> VALUES = new HashMap<>();

    private final String id;
    private final boolean silent, passive, actionHandSpecific;

    public TriggerType(@NotNull String id) {
        this(id, true, true);
    }

    public TriggerType(@NotNull String id, boolean silent) {
        this(id, silent, true);
    }

    /**
     * This constructor is made private to make sure there is only
     * one trigger type that generates active spells.
     *
     * @param id      The trigger type ID
     * @param silent  Does this trigger type generate silent spells
     * @param passive Does this trigger type generate passive spells
     */
    private TriggerType(@NotNull String id, boolean silent, boolean passive) {
        this(id, silent, passive, false);
    }

    /**
     * This constructor is made private to make sure there is only
     * one trigger type that generates active spells.
     *
     * @param id      The trigger type ID
     * @param silent  Does this trigger type generate silent spells
     * @param passive Does this trigger type generate passive spells
     */
    private TriggerType(@NotNull String id, boolean silent, boolean passive, boolean actionHandSpecific) {
        this.id = id;
        this.silent = silent;
        this.passive = passive;
        this.actionHandSpecific = actionHandSpecific;
    }

    /**
     * @return Identical to {@link #toString()}
     */
    public @NotNull String name() {
        return this.id;
    }

    /**
     * When set to false, any spell with this trigger type should send a message
     * to the entity if this spell cannot be used.
     */
    public boolean isSilent() {
        return this.silent;
    }

    /**
     * When set to true, spells granted by the item held in the
     * {@link org.bukkit.inventory.EquipmentSlot#OFF_HAND secondary hand}
     * (opposite of the action hand) will not be applied.
     * <p>
     * These triggers correspond to item interactions (clicks, attacks).
     */
    public boolean isActionHandSpecific() {
        return this.actionHandSpecific;
    }

    /**
     * There are two types of passive spells:
     * <br>- hard coded passive spells which use the API trigger type
     * <br>- passive spells created using other spell plugins using any
     * other trigger type apart from the {@link #CAST} trigger type.
     *
     * @return If this spell is passive
     */
    public boolean isPassive() {
        return this.passive;
    }

    public @NotNull String getName() {
        return caseOnWords(name().toLowerCase(Locale.ROOT)
                .replace("_", " ")
                .replace("-", " "));
    }

    public @NotNull String getLowerCaseId() {
        return name().toLowerCase(Locale.ROOT)
                .replace("_", "-")
                .replace(" ", "-");
    }

    /**
     * @return This trigger type serialized into a string.
     */
    @Override
    public @NotNull String toString() {
        return name();
    }

    /**
     * @param id The string trying to convert
     * @return The trigger type of this name
     * @throws IllegalArgumentException If the string does not correspond to a trigger type
     */
    public static @NotNull TriggerType valueOf(@NotNull String id) {
        return Objects.requireNonNull(VALUES.get(id), "Could not find trigger type with ID '" + id + "'");
    }

    public static void register(@NotNull TriggerType trigger) {
        Objects.requireNonNull(trigger, "Trigger type cannot be null");
        VALUES.put(trigger.name(), trigger);
    }

    public static @NotNull Collection<TriggerType> values() {
        return VALUES.values();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TriggerType other)) {
            return false;
        }
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}