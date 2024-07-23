package me.kubbidev.spellcaster.entity.modfier;

import me.kubbidev.spellcaster.util.EquipmentSlot;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public abstract class EntityModifier {

    /**
     * Similarly to {@link org.bukkit.attribute.Attribute} modifiers, entity modifiers have a uuid
     * for differentiation.
     * <p>
     * However, it is easier to check the source plugin of the modifier using {@link #getKey()}
     */
    private final UUID uniqueId;

    private final ModifierSource source;
    private final EquipmentSlot slot;

    /**
     * Identifier given to spells to differentiate them.
     * <p>
     * Every plugin has a key to be able to manipulate
     * the triggers that were registered on the entity at any time.
     * <p>
     * Unlike the uuid, this key is NOT ALWAYS unique in the case
     * of modifier instances.
     */
    private final String key;

    public EntityModifier(ModifierSource source, EquipmentSlot slot, String key) {
        this(UUID.randomUUID(), source, slot, key);
    }

    public EntityModifier(UUID uniqueId, ModifierSource source, EquipmentSlot slot, String key) {
        this.uniqueId = uniqueId;
        this.source = source;
        this.slot = slot;
        this.key = key;
    }

    public @NotNull UUID getUniqueId() {
        return this.uniqueId;
    }

    public @NotNull ModifierSource getSource() {
        return this.source;
    }

    public @NotNull EquipmentSlot getSlot() {
        return this.slot;
    }

    public @NotNull String getKey() {
        return this.key;
    }

    public abstract void register(@NotNull LivingEntity entity);

    public abstract void unregister(@NotNull LivingEntity entity);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntityModifier other)) {
            return false;
        }
        return this.uniqueId.equals(other.uniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uniqueId);
    }
}