package me.kubbidev.spellcaster.entity.spell;

import com.google.common.base.Preconditions;
import me.kubbidev.spellcaster.entity.EntityMetadataProvider;
import me.kubbidev.spellcaster.entity.modfier.EntityModifier;
import me.kubbidev.spellcaster.entity.modfier.ModifierSource;
import me.kubbidev.spellcaster.spell.Spell;
import me.kubbidev.spellcaster.spell.trigger.TriggerType;
import me.kubbidev.spellcaster.util.EquipmentSlot;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

/**
 * There is one PassiveSpell instance per passive spell the entity has.
 * <p>
 * Spells that are cast using the casting mode are active and any spell that has to be triggered is passive. The only active spells are the
 * ones cast using the {@link me.kubbidev.spellcaster.spell.trigger.TriggerType#CAST} trigger type.
 */
public class PassiveSpell extends EntityModifier {

    /**
     * Spell cast whenever the action is performed
     */
    private final Spell triggered;

    public PassiveSpell(Spell triggered, UUID uniqueId, ModifierSource source, EquipmentSlot slot, String key) {
        super(uniqueId, source, slot, key);
        Preconditions.checkArgument(triggered.getTrigger().isPassive(), "Spell is active");
        this.triggered = Objects.requireNonNull(triggered, "Spell cannot be null");
    }

    public @NotNull Spell getTriggered() {
        return this.triggered;
    }

    public @NotNull TriggerType getType() {
        return this.triggered.getTrigger();
    }

    /**
     * Zero when spell does not use a timer.
     * <p>
     * The user inputs it in ticks but that field is expressed in milliseconds.
     */
    public long getTimerPeriod() {
        return Math.max(1, (long) this.triggered.getParameter("timer")) * 50;
    }

    @Override
    public void register(@NotNull LivingEntity entity) {
        EntityMetadataProvider.getPassiveSpellMap(entity).addModifier(this);
    }

    @Override
    public void unregister(@NotNull LivingEntity entity) {
        EntityMetadataProvider.getPassiveSpellMap(entity).removeModifier(getUniqueId());
    }
}
