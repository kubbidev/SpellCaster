package me.kubbidev.spellcaster.listener;

import me.kubbidev.nexuspowered.metadata.Metadata;
import me.kubbidev.spellcaster.SpellCaster;
import me.kubbidev.spellcaster.entity.EntityMetadataProvider;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public class SpellTriggers implements Listener, Runnable {
    private final SpellCaster plugin;

    public SpellTriggers(SpellCaster plugin) {
        this.plugin = plugin;
        Bukkit.getScheduler().runTaskTimer(plugin, this, 0, 1);
    }

    @Override
    public void run() {
        Metadata.lookupEntitiesWithKey(EntityMetadataProvider.PASSIVE_SPELL_MAP)
                .forEach((entity, passiveSpellMap) -> passiveSpellMap.tickTimerSpells());
    }
}
