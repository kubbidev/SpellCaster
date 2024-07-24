package me.kubbidev.spellcaster.spell;

import me.kubbidev.spellcaster.SpellCaster;
import me.kubbidev.spellcaster.damage.AttackMetadata;
import me.kubbidev.spellcaster.entity.EntityMetadata;
import me.kubbidev.spellcaster.entity.EntityMetadataProvider;
import me.kubbidev.spellcaster.util.EntityBody;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record SpellMetadata(Spell cast, EntityMetadata caster, Location source,
                            @Nullable Entity targetEntity,
                            @Nullable Location targetLocation,
                            @Nullable AttackMetadata attackSource) {

    @NotNull
    public SpellCaster plugin() {
        return this.caster.plugin();
    }

    @NotNull
    public LivingEntity entity() {
        return this.caster.entity();
    }

    @Override
    @NotNull
    public Location source() {
        return this.source.clone();
    }

    public boolean hasAttackSource() {
        return this.attackSource != null;
    }

    /**
     * @return The attack which triggered the spell.
     */
    @Override
    public AttackMetadata attackSource() {
        return Objects.requireNonNull(this.attackSource, "Spell was not triggered by any attack");
    }

    /**
     * Retrieves a specific spell parameter value.
     * <p>
     * This applies to the original spell being cast.
     *
     * @param parameter Spell parameter name
     * @return Spell parameter final value, taking into account spell mods
     */
    public double parameter(String parameter) {
        return EntityMetadataProvider.getSpellModifierMap(entity()).calculateValue(this.cast, parameter);
    }

    @Override
    @NotNull
    public Entity targetEntity() {
        return Objects.requireNonNull(this.targetEntity, "Spell has no target entity");
    }

    @Nullable
    public Entity targetEntityOrNull() {
        return this.targetEntity;
    }

    public boolean hasTargetEntity() {
        return this.targetEntity != null;
    }

    @Override
    @NotNull
    public Location targetLocation() {
        return Objects.requireNonNull(this.targetLocation, "Spell has no target location").clone();
    }

    @Nullable
    public Location targetLocationOrNull() {
        return this.targetLocation == null ? null : this.targetLocation.clone();
    }

    public boolean hasTargetLocation() {
        return this.targetLocation != null;
    }

    /**
     * Analog of {@link #spellEntity(boolean)}.
     * <p>
     * Used when a spell requires a location when no target is provided.
     *
     * @param sourceLocation If the source location should be prioritized.
     * @return Target location (and if it exists) OR location of target entity (and if it exists), source location otherwise
     */
    public Location spellLocation(boolean sourceLocation) {
        return sourceLocation ? this.source.clone() : this.targetLocation != null ? targetLocation() : this.targetEntity != null ? EntityBody.BODY.getLocation(this.targetEntity) : this.source.clone();
    }

    /**
     * Analog of {@link #spellLocation(boolean)}.
     * <p>
     * Used when a spell requires an entity when no target is provided.
     *
     * @param caster If the spell caster should be prioritized.
     * @return Target entity if prioritized (and if it exists), spell caster otherwise
     */
    public Entity spellEntity(boolean caster) {
        return caster || this.targetEntity == null ? this.caster.entity() : this.targetEntity;
    }

    /**
     * Keeps the same spell caster.
     * <p>
     * Used when casting sub-spells with different targets.
     * <p>
     * This has the effect of keeping every spell data, put aside targets.
     * <p>
     * Data that is kept on cloning:
     * <br>- spell being cast
     * <br>- spell caster
     * <br>- attack source
     * <p>
     * Data being replaced on cloning:
     * <br>- source location
     * <br>- target entity
     * <br>- target location
     *
     * @return New spell metadata for other sub-spells
     */
    public SpellMetadata clone(Location source, @Nullable Entity targetEntity, @Nullable Location targetLocation) {
        return new SpellMetadata(this.cast, this.caster, source, targetEntity, targetLocation, this.attackSource);
    }

    public SpellMetadata clone(Location targetLocation) {
        return clone(this.source, this.targetEntity, targetLocation);
    }
}