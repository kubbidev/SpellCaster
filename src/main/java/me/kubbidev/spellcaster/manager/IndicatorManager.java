package me.kubbidev.spellcaster.manager;

import me.kubbidev.spellcaster.SpellCaster;
import me.kubbidev.spellcaster.config.ConfigKeys;
import me.kubbidev.spellcaster.listener.indicator.type.DamageIndicator;
import me.kubbidev.spellcaster.listener.indicator.type.RegenerationIndicator;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public final class IndicatorManager {
    private final List<Listener> indicatorsListeners = new ArrayList<>();

    /**
     * Register all indicators listeners and add them to the list.
     */
    public void load(SpellCaster plugin) {
        ConfigManager configManager = plugin.getConfiguration();
        if (configManager.get(ConfigKeys.INDICATOR_DAMAGE_ENABLED)) {
            try {
                Listener listener = new DamageIndicator(plugin,
                        configManager.get(ConfigKeys.INDICATOR_DAMAGE_CONFIG));

                plugin.registerListener(listener);
                this.indicatorsListeners.add(listener);
            } catch (RuntimeException e) {
                plugin.getLogger().warning("Could not load damage indicators: " + e.getMessage());
            }
        }
        if (configManager.get(ConfigKeys.INDICATOR_REGENERATION_ENABLED)) {
            try {
                Listener listener = new RegenerationIndicator(plugin,
                        configManager.get(ConfigKeys.INDICATOR_REGENERATION_CONFIG));

                plugin.registerListener(listener);
                this.indicatorsListeners.add(listener);
            } catch (RuntimeException e) {
                plugin.getLogger().warning("Could not load regeneration indicators: " + e.getMessage());
            }
        }
    }

    /**
     * Unregister all listeners, remove them from the list and call the
     * {@link IndicatorManager#load(SpellCaster)} method.
     */
    public void reload(SpellCaster plugin) {
        // unregister listeners
        this.indicatorsListeners.forEach(HandlerList::unregisterAll);
        this.indicatorsListeners.clear();

        // register listeners
        load(plugin);
    }
}