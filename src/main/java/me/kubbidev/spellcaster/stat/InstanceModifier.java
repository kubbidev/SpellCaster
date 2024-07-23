package me.kubbidev.spellcaster.stat;

import me.kubbidev.spellcaster.entity.modfier.EntityModifier;
import me.kubbidev.spellcaster.entity.modfier.ModifierSource;
import me.kubbidev.spellcaster.entity.modfier.ModifierType;
import me.kubbidev.spellcaster.manager.ConfigManager;
import me.kubbidev.spellcaster.util.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Used anywhere where instances similar to {@link org.bukkit.attribute.Attribute}
 * instances are being modified by numerical modifiers.
 */
public abstract class InstanceModifier extends EntityModifier {

    protected final double value;
    protected final ModifierType type;

    public InstanceModifier(String key, double value) {
        this(ModifierSource.OTHER, EquipmentSlot.OTHER, key, value, ModifierType.FLAT);
    }

    public InstanceModifier(ModifierSource source, EquipmentSlot slot, String key, double value, ModifierType type) {
        this(UUID.randomUUID(), source, slot, key, value, type);
    }

    public InstanceModifier(UUID uniqueId, ModifierSource source, EquipmentSlot slot, String key, double value, ModifierType type) {
        super(uniqueId, source, slot, key);
        this.value = value;
        this.type = type;
    }

    public double getValue() {
        return this.value;
    }

    public @NotNull ModifierType getType() {
        return this.type;
    }

    @NotNull
    public String toString(ConfigManager configuration) {
        return configuration.getDecimalFormat().format(this.value) + (this.type == ModifierType.RELATIVE ? '%' : "");
    }
}