package me.kubbidev.spellcaster.hologram.factory;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import me.kubbidev.spellcaster.hologram.Hologram;
import me.kubbidev.spellcaster.hologram.HologramFactory;
import me.kubbidev.spellcaster.listener.indicator.IndicatorConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class BukkitHologramFactory implements HologramFactory /*, Listener*/ {

    public static final HologramFactory INSTANCE = new BukkitHologramFactory();

    private BukkitHologramFactory() {
    }

    @Override
    public @NotNull Hologram newHologram(@NotNull Location loc, @NotNull List<Component> lines) {
        return new HologramImpl(loc, lines);
    }

    private static final class HologramImpl extends Hologram {

        private static final double LINE_OFFSET = 0.25;
        private static final double EPSILON     = 1e-5;

        private final List<Component>   lines           = new ArrayList<>();
        private final List<TextDisplay> spawnedEntities = new ArrayList<>();

        private Location loc;
        private boolean  spawned = false;

        HologramImpl(@NotNull Location loc, @NotNull List<Component> lines) {
            this.loc = Objects.requireNonNull(loc, "Location cannot be null").clone();
            this.updateLines(lines);
            this.spawn();
        }


        @Override
        public void despawn() {
            this.spawnedEntities.forEach(Entity::remove);
            this.spawnedEntities.clear();
            this.spawned = false;
        }

        @Override
        public boolean isSpawned() {
            return this.spawned;
        }

        @Override
        public void updateLocation(@NotNull Location newLoc) {
            if (this.loc.distanceSquared(newLoc) < EPSILON) {
                return;
            }
            this.loc = newLoc.clone();

            Location clone = this.loc.clone();
            for (TextDisplay textDisplay : getSpawnedEntities()) {
                textDisplay.teleport(clone);
                clone.subtract(0, LINE_OFFSET, 0);
            }
        }

        @Override
        public void updateLines(List<Component> lines) {
            Objects.requireNonNull(lines, "lines");
            Preconditions.checkArgument(!lines.isEmpty(), "Lines cannot be empty");
            for (Component line : lines) {
                Preconditions.checkArgument(line != null, "Null line");
            }

            this.lines.clear();
            this.lines.addAll(lines);

        }

        @Override
        public List<Component> getLines() {
            return this.lines;
        }

        @Override
        public Location getLocation() {
            return this.loc;
        }

        public List<TextDisplay> getSpawnedEntities() {
            return this.spawnedEntities;
        }

        private void spawn() {
            Location clone = loc.clone();
            for (Component line : this.lines) {
                TextDisplay as = clone.getWorld().spawn(clone, TextDisplay.class);
                as.setBillboard(Display.Billboard.CENTER);
                // as.setInterpolationDuration(INTERPOLATION_DURATION);

                this.spawnedEntities.add(as);
                as.text(line);
                clone.subtract(0, LINE_OFFSET, 0);
            }

            this.spawned = true;
        }

        @Override
        public void flyOut(@NotNull Plugin plugin, @NotNull IndicatorConfig config, @NotNull Vector dir) {
            for (TextDisplay td : getSpawnedEntities()) {
                td.setTeleportDuration((int) config.getTickPeriod());
            }

            super.flyOut(plugin, config, dir);
        }
    }
}
