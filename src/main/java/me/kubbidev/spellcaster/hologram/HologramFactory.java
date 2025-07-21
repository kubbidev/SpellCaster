package me.kubbidev.spellcaster.hologram;

import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface HologramFactory {

    /**
     * Creates and spawns a hologram at given location with given lines.
     *
     * @param loc   Target hologram location
     * @param lines Messages to display. Multiple lines are supported
     */
    @NotNull Hologram newHologram(@NotNull Location loc, @NotNull List<Component> lines);
}