
package me.kubbidev.spellcaster.spell.trigger;

import me.kubbidev.spellcaster.SpellCaster;
import me.kubbidev.spellcaster.damage.AttackMetadata;
import me.kubbidev.spellcaster.entity.EntityMetadata;
import me.kubbidev.spellcaster.event.attack.EntityAttackEvent;
import me.kubbidev.spellcaster.spell.Spell;
import me.kubbidev.spellcaster.spell.SpellMetadata;
import me.kubbidev.spellcaster.util.EquipmentSlot;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TriggerMetadata {
    private final LivingEntity caster;
    private final EquipmentSlot actionHand;
    private final Location source;

    @Nullable
    private final Entity target;

    @Nullable
    private final Location targetLocation;

    @Nullable
    private final AttackMetadata attack;

    /**
     * The instantiation of an EntityMetadata can be quite intensive in computation,
     * especially because it can be up to 20 times a second for every player in the server.
     * <p>
     * For this reason, it's best to NOT generate the EntityMetadata unless it has been
     * provided beforehand in the constructor, until it's finally asked for in the getter.
     */
    @Nullable
    private EntityMetadata cachedMetadata;

    public TriggerMetadata(LivingEntity entity) {
        this(entity, (LivingEntity) null);
    }

    public TriggerMetadata(LivingEntity entity, @Nullable LivingEntity target) {
        this(entity, EquipmentSlot.MAIN_HAND, entity.getLocation(), target, null, null);
    }

    public TriggerMetadata(LivingEntity entity, @Nullable Location targetLocation) {
        this(entity, EquipmentSlot.MAIN_HAND, entity.getLocation(), null, targetLocation, null);
    }

    public TriggerMetadata(LivingEntity entity, Location source, @Nullable Location targetLocation) {
        this(entity, EquipmentSlot.MAIN_HAND, source, null, targetLocation, null);
    }

    /**
     * The entity responsible for the attack is the one triggering the spell.
     */
    public TriggerMetadata(EntityAttackEvent event) {
        this(event.getAttacker(), event.getEntity(), event.getAttack());
    }

    public TriggerMetadata(EntityMetadata caster, @Nullable Entity target, @Nullable AttackMetadata attack) {
        this(caster.entity(), caster.actionHand(), caster.entity().getLocation(), target, null, attack);
    }

    public TriggerMetadata(LivingEntity caster,
                           EquipmentSlot actionHand,
                           Location source,
                           @Nullable Entity target,
                           @Nullable Location targetLocation,
                           @Nullable AttackMetadata attack) {
        this.caster = caster;
        this.actionHand = actionHand;
        this.source = source;
        this.target = target;
        this.targetLocation = targetLocation;
        this.attack = attack;
    }

    public @NotNull LivingEntity getCaster() {
        return this.caster;
    }

    public @NotNull EquipmentSlot getActionHand() {
        return this.actionHand;
    }

    public @NotNull Location getSource() {
        return this.source;
    }

    public @Nullable Entity getTarget() {
        return this.target;
    }

    public @Nullable Location getTargetLocation() {
        return this.targetLocation;
    }

    public @Nullable AttackMetadata getAttack() {
        return this.attack;
    }

    public @NotNull EntityMetadata getCachedMetadata(SpellCaster plugin) {
        if (this.cachedMetadata == null) {
            this.cachedMetadata = new EntityMetadata(plugin, this.caster, this.actionHand);
        }
        return this.cachedMetadata;
    }

    public @NotNull SpellMetadata toSpellMetadata(Spell cast) {
        return new SpellMetadata(cast, getCachedMetadata(cast.getPlugin()), this.source, this.target, this.targetLocation, this.attack);
    }
}