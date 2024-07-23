package me.kubbidev.spellcaster.listener.indicator.type;

import me.kubbidev.spellcaster.SpellCaster;
import me.kubbidev.spellcaster.event.indicator.IndicatorDisplayEvent;
import me.kubbidev.spellcaster.listener.indicator.AbstractIndicator;
import me.kubbidev.spellcaster.listener.indicator.IndicatorConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import static me.kubbidev.spellcaster.InternalMethod.getAttributeValue;
import static me.kubbidev.spellcaster.InternalMethod.isVanished;

public class RegenerationIndicator extends AbstractIndicator {
    private final IndicatorConfig config;

    public RegenerationIndicator(SpellCaster plugin, IndicatorConfig config) {
        super(plugin, config);
        this.config = config;
    }

    private static final double HEAL_EPSILON = 1e-3;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityRegainHealth(EntityRegainHealthEvent e) {
        if (!(e.getEntity() instanceof LivingEntity entity) || e.getAmount() <= 0) {
            return;
        }
        // no indicator around vanished entities
        if (isVanished(entity)) {
            return;
        }

        double maxHealth = getAttributeValue(entity, Attribute.GENERIC_MAX_HEALTH);
        if ((entity.getHealth() + HEAL_EPSILON) > maxHealth) {
            return;
        }
        Component message = this.config.getFormat()
                .replaceText(TextReplacementConfig.builder().matchLiteral("#").replacement(formatDamage(e.getAmount())).build());

        displayIndicator(entity, message, getIndicatorDirection(entity), IndicatorDisplayEvent.IndicatorType.REGENERATION);
    }

    private @NotNull Vector getIndicatorDirection(Entity entity) {
        if (entity instanceof LivingEntity) {
            double a = Math.toRadians(((LivingEntity) entity).getEyeLocation().getYaw()) + Math.PI * (1 + (RANDOM.nextDouble() - 0.5) / 2);
            return new Vector(Math.cos(a), 0, Math.sin(a));
        }
        double a = RANDOM.nextDouble() * Math.PI * 2;
        return new Vector(Math.cos(a), 0, Math.sin(a));
    }
}