package me.kubbidev.spellcaster.entity.spellmod;

import me.kubbidev.spellcaster.entity.EntityMetadataProvider;
import me.kubbidev.spellcaster.entity.modfier.ModifierSource;
import me.kubbidev.spellcaster.entity.modfier.ModifierType;
import me.kubbidev.spellcaster.spell.handler.SpellHandler;
import me.kubbidev.spellcaster.stat.InstanceModifier;
import me.kubbidev.spellcaster.util.EquipmentSlot;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A spell "modifier" modifies a specific parameter of a spell, in the same way that
 * a stat modifier modifies a stat for a player.
 * <p>
 * It can also be given a boolean formula, which determines which
 * spells the modifier will apply onto.
 */
public class SpellModifier extends InstanceModifier {

    /**
     * The list of all the spells this modifier will be applied to.
     * <p>
     * A spell modifier can target one spell or a set of spells like
     * giving for example +10% damage to all the passive spells.
     */
    private final List<SpellHandler<?>> spells;
    private final String parameter;

    public SpellModifier(String key, double value, List<SpellHandler<?>> spells, String parameter) {
        this(ModifierSource.OTHER, EquipmentSlot.OTHER, key, value, ModifierType.FLAT, spells, parameter);
    }

    public SpellModifier(String key, double value, ModifierType type, List<SpellHandler<?>> spells, String parameter) {
        this(ModifierSource.OTHER, EquipmentSlot.OTHER, key, value, type, spells, parameter);
    }

    public SpellModifier(ModifierSource source, EquipmentSlot slot, String key, double value, ModifierType type, List<SpellHandler<?>> spells, String parameter) {
        super(source, slot, key, value, type);
        this.spells = spells;
        this.parameter = parameter;
    }

    public SpellModifier(UUID uniqueId, ModifierSource source, EquipmentSlot slot, String key, double value, ModifierType type, List<SpellHandler<?>> spells, String parameter) {
        super(uniqueId, source, slot, key, value, type);
        this.spells = spells;
        this.parameter = parameter;
    }

    public @NotNull List<SpellHandler<?>> getSpells() {
        return this.spells;
    }

    public @NotNull String getParameter() {
        return this.parameter;
    }

    /**
     * Used to add a constant to some existing stat modifier, usually an
     * integer, for instance it is used when a spell buff trigger is triggered multiple times.
     *
     * @param offset The offset added.
     * @return A new instance of {@link SpellModifier} with modified value
     */
    public @NotNull SpellModifier add(double offset) {
        return new SpellModifier(
                getUniqueId(),
                getSource(),
                getSlot(),
                getKey(), getValue() + offset,
                getType(), new ArrayList<>(this.spells), this.parameter);
    }

    @Override
    public void register(@NotNull LivingEntity entity) {
        EntityMetadataProvider.getSpellModifierMap(entity).addModifier(this);
    }

    @Override
    public void unregister(@NotNull LivingEntity entity) {
        EntityMetadataProvider.getSpellModifierMap(entity).removeModifier(getUniqueId());
    }
}