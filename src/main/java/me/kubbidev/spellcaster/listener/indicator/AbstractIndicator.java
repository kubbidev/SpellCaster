package me.kubbidev.spellcaster.listener.indicator;

import java.util.Collections;
import java.util.Random;
import me.kubbidev.spellcaster.SpellCaster;
import me.kubbidev.spellcaster.event.indicator.IndicatorDisplayEvent;
import me.kubbidev.spellcaster.hologram.Hologram;
import me.kubbidev.spellcaster.hologram.factory.BukkitHologramFactory;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractIndicator implements Listener {

    protected static final Random RANDOM = new Random();

    private final SpellCaster     plugin;
    private final IndicatorConfig config;

    public AbstractIndicator(SpellCaster plugin, IndicatorConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    public @NotNull String formatDamage(double d) {
        return this.config.getDecimalFormat().format(d);
    }

    /**
     * Displays a message using a hologram around an entity
     *
     * @param entity  The entity used to find the hologram initial position
     * @param message The message to display
     * @param dir     The average direction of the hologram indicator
     */
    public void displayIndicator(Entity entity, Component message, Vector dir, IndicatorDisplayEvent.IndicatorType type) {
        IndicatorDisplayEvent called = new IndicatorDisplayEvent(entity, message, type);
        if (!called.callEvent()) {
            return;
        }

        double a = RANDOM.nextDouble() * 2 * Math.PI,

            // Entity width defined as arithmetical mean of widths across the two dimensions
            width = (entity.getBoundingBox().getWidthX() + entity.getBoundingBox().getWidthZ()) / 2,

            // Starting distance to center location
            r = this.config.getROffset() + width * this.config.getEntityWidthPercent(),

            // Starting Z coordinate
            h = this.config.getYOffset() + entity.getHeight() * this.config.getEntityHeightPercent();

        Location loc = entity.getLocation().add(Math.cos(a) * r, h, Math.sin(a) * r);
        Hologram holo = BukkitHologramFactory.INSTANCE.newHologram(loc, Collections.singletonList(called.getText()));

        if (!this.config.isMove()) {
            Bukkit.getScheduler().runTaskLater(this.plugin, holo::despawn, this.config.getLifespan());
        } else {
            holo.flyOut(this.plugin, this.config, dir);
        }

    }
}