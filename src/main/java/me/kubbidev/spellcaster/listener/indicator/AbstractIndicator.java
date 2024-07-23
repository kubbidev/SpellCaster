package me.kubbidev.spellcaster.listener.indicator;

import me.kubbidev.nexuspowered.hologram.Hologram;
import me.kubbidev.nexuspowered.serialize.Position;
import me.kubbidev.spellcaster.SpellCaster;
import me.kubbidev.spellcaster.event.indicator.IndicatorDisplayEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Random;

public abstract class AbstractIndicator implements Listener {
    private final SpellCaster plugin;
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
     * @param entity    The entity used to find the hologram initial position
     * @param message   The message to display
     * @param direction The average direction of the hologram indicator
     */
    public void displayIndicator(Entity entity, Component message, Vector direction, IndicatorDisplayEvent.IndicatorType type) {
        IndicatorDisplayEvent called = new IndicatorDisplayEvent(entity, message, type);
        if (!called.callEvent()) return;

        double entityYOffset = this.config.getEntityYOffset();
        double entityHeightP = this.config.getEntityHeightP();
        Location location = entity.getLocation().add(
                (RANDOM.nextDouble() - 0.5) * 1.2, entityYOffset + entity.getHeight() * entityHeightP,
                (RANDOM.nextDouble() - 0.5) * 1.2);

        displayIndicator(location, called.getText(), direction);
    }

    @NotNull
    protected static final Random RANDOM = new Random();

    /**
     * Hologram life span in ticks.
     */
    private static final int HOLOGRAM_LIFE_SPAN = 7;

    @SuppressWarnings("resource")
    private void displayIndicator(Location location, Component message, Vector direction) {
        Hologram hologram = Hologram.create(Position.of(location), Collections.singletonList(message));
        hologram.spawn();

        new BukkitRunnable() {
            double v = 6 * config.getInitialUpwardVelocity();
            int i = 0;

            @Override
            public void run() {
                if (i == 0) {
                    direction.multiply(2 * config.getRadialVelocity());
                }

                if (i++ >= HOLOGRAM_LIFE_SPAN) {
                    hologram.despawn();
                    cancel();
                    return;
                }
                v += (-10 * config.getGravity()) * 0.15;
                location.add(
                        direction.getX() * 0.15, v * 0.15,
                        direction.getZ() * 0.15
                );
                hologram.updatePosition(Position.of(location));
            }
        }.runTaskTimer(this.plugin, 0, 3);
    }
}