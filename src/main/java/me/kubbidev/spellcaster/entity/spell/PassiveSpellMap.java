package me.kubbidev.spellcaster.entity.spell;

import me.kubbidev.spellcaster.entity.modfier.ModifierMap;
import me.kubbidev.spellcaster.spell.handler.SpellHandler;
import me.kubbidev.spellcaster.spell.trigger.TriggerMetadata;
import me.kubbidev.spellcaster.spell.trigger.TriggerType;
import me.kubbidev.spellcaster.util.EquipmentSlot;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static me.kubbidev.spellcaster.InternalMethod.isSpectator;

public class PassiveSpellMap extends ModifierMap<PassiveSpell> {

    // string -> spell handler identifier & last time that spell was cast due to a timer
    private final Map<String, Long> lastCast = new HashMap<>();

    public PassiveSpellMap(LivingEntity entity) {
        super(entity);
    }

    /**
     * This method can be used to check if an entity has a specific passive spell registered in his spell set.
     * <p>
     * An entity can have multiple passive spells with the same spell handler. The output function is completely random given the use of an
     * HashSet which does not feature order.
     *
     * @param handler Some passive spell handler
     * @return Any passive spell with the same handler
     */
    public @Nullable PassiveSpell getSpell(@NotNull SpellHandler<?> handler) {
        return getModifiers().stream().filter(p -> handler.equals(p.getTriggered().getHandler())).findFirst().orElse(null);
    }

    public void tickTimerSpells() {
        TriggerMetadata triggerMeta = new TriggerMetadata(getEntity(), TriggerType.TIMER, EquipmentSlot.MAIN_HAND,
            null, null, null, null);

        for (PassiveSpell passive : getModifiers()) {
            if (!passive.getType().equals(TriggerType.TIMER)) {
                continue;
            }

            if (isSpectator(getEntity())) {
                continue;
            }
            String key = passive.getTriggered().getHandler().getId();
            long lastCast = this.lastCast.getOrDefault(key, 0L);
            if (lastCast + passive.getTimerPeriod() > System.currentTimeMillis()) {
                continue;
            }
            this.lastCast.put(key, System.currentTimeMillis());
            passive.getTriggered().cast(triggerMeta);
        }
    }
}