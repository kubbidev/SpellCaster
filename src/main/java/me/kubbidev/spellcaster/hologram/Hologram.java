package me.kubbidev.spellcaster.hologram;

import java.util.List;
import me.kubbidev.spellcaster.listener.indicator.IndicatorConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public abstract class Hologram {

    public abstract void despawn();

    public abstract boolean isSpawned();

    public abstract void updateLocation(@NotNull Location loc);

    public void flyOut(@NotNull Plugin plugin, @NotNull IndicatorConfig config, @NotNull Vector dir) {
        new BukkitRunnable() {
            final Location loc = getLocation().clone();
            double v = 6 * config.getInitialUpwardVelocity(); // Initial upward velocity
            int    i = 0; // Counter

            private final        double acc = -10 * config.getGravity(); // Downwards acceleration
            private static final double DT  = 3d / 20d; // Delta_t used to integrate acceleration and velocity

            @Override
            public void run() {

                if (i == 0) {
                    dir.multiply(2 * config.getRadialVelocity());
                }

                // Remove hologram when reaching end of life
                if (i++ >= config.getLifespan()) {
                    despawn();
                    cancel();
                    return;
                }

                v += acc * DT;
                loc.add(dir.getX() * DT, v * DT, dir.getZ() * DT);
                updateLocation(loc);
            }
        }.runTaskTimer(plugin, 0, config.getTickPeriod());
    }

    public abstract void updateLines(List<Component> lines);

    public abstract List<Component> getLines();

    public abstract Location getLocation();
}
