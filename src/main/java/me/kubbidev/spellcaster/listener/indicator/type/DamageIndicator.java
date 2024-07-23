package me.kubbidev.spellcaster.listener.indicator.type;

import me.kubbidev.spellcaster.SpellCaster;
import me.kubbidev.spellcaster.damage.DamageMetadata;
import me.kubbidev.spellcaster.damage.DamagePacket;
import me.kubbidev.spellcaster.damage.DamageType;
import me.kubbidev.spellcaster.damage.Element;
import me.kubbidev.spellcaster.event.attack.AttackUnregisteredEvent;
import me.kubbidev.spellcaster.event.indicator.IndicatorDisplayEvent;
import me.kubbidev.spellcaster.listener.indicator.AbstractIndicator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.kubbidev.spellcaster.InternalMethod.isVanished;

public class DamageIndicator extends AbstractIndicator {
    private final DamageIndicatorConfig config;

    public DamageIndicator(SpellCaster plugin, DamageIndicatorConfig config) {
        super(plugin, config);
        this.config = config;
    }

    @EventHandler
    public void displayIndicators(AttackUnregisteredEvent e) {
        if (e.getMetadata().getDamage() <= DamageMetadata.MINIMAL_DAMAGE) {
            return;
        }
        // no indicator around vanished entities
        if (isVanished(e.getEntity())) {
            return;
        }

        List<Component> holograms = new ArrayList<>();
        Map<IndicatorType, Double> mappedDamage = new HashMap<>();
        for (DamagePacket packet : e.getMetadata().getPackets()) {

            IndicatorType type = new IndicatorType(e.getMetadata(), packet);
            mappedDamage.put(type, mappedDamage.getOrDefault(type, 0d) + packet.getFinalValue());
        }

        double modifier = (e.toBukkit().getFinalDamage() - e.getMetadata().getDamage()) / Math.max(1, mappedDamage.size());
        mappedDamage.forEach((type, value) -> holograms.add(type.computeIndicator(value + modifier)));

        if (this.config.isSplitHolograms()) {
            for (Component hologram : holograms) {
                displayIndicator(e.getEntity(), hologram, getDirection(e.toBukkit()), IndicatorDisplayEvent.IndicatorType.DAMAGE);
            }
        } else {
            Component joined = Component.join(JoinConfiguration.spaces(), holograms);
            displayIndicator(e.getEntity(), joined, getDirection(e.toBukkit()), IndicatorDisplayEvent.IndicatorType.DAMAGE);
        }

    }

    /**
     * If SpellCaster can find a damager, display the
     * {@link me.kubbidev.nexuspowered.hologram.Hologram}
     * in a cone which direction is the damager-target line.
     *
     * @param e the damage event
     * @return The direction of the hologram
     */
    private @NotNull Vector getDirection(EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent) {
            Vector direction = getDirectionToEntity(e.getEntity(), ((EntityDamageByEntityEvent) e).getDamager());

            if (direction.lengthSquared() > 0) {
                double a = Math.atan2(direction.getZ(), direction.getX()) + Math.PI / 2 * (RANDOM.nextDouble() - 0.5);
                return new Vector(Math.cos(a), 0, Math.sin(a));
            }
        }
        double a = RANDOM.nextDouble() * Math.PI * 2;
        return new Vector(Math.cos(a), 0, Math.sin(a));
    }

    private Vector getDirectionToEntity(Entity e1, Entity e2) {
        return e1.getLocation().toVector().subtract(e2.getLocation().toVector()).setY(0);
    }

    private class IndicatorType {
        private final boolean physical;
        private final boolean crit;

        /**
         * The element present inside the damage packet or null.
         */
        @Nullable
        private final Element element;

        /**
         * Constructs an IndicatorType instance.
         *
         * @param metadata The damage metadata.
         * @param packet   The damage packet.
         */
        IndicatorType(DamageMetadata metadata, DamagePacket packet) {
            this.physical = packet.hasType(DamageType.PHYSICAL);
            this.element = packet.getElement();

            this.crit = (this.physical ? metadata.isWeaponCrit() : metadata.isSpellCrit())
                    || (this.element != null && metadata.isElementCrit(this.element));
        }

        private @NotNull Component computeIcon() {
            TextComponent.Builder builder = Component.text();
            if (this.physical) {
                builder.append(this.crit
                        ? config.getWeaponIcon()
                        : config.getWeaponIconCrit());
            } else {
                builder.append(this.crit
                        ? config.getSpellIcon()
                        : config.getSpellIconCrit());
            }

            if (this.element != null) {
                builder.append(this.element.getIcon());
            }
            return builder.build();
        }

        private @NotNull Component computeIndicator(double d) {
            return config.getFormat()
                    .replaceText(TextReplacementConfig.builder().matchLiteral("{icon}").replacement(computeIcon()).build())
                    .replaceText(TextReplacementConfig.builder().matchLiteral("{value}").replacement(formatDamage(d)).build());
        }
    }
}