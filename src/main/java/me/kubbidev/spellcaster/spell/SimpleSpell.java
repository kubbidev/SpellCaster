package me.kubbidev.spellcaster.spell;

import me.kubbidev.nexuspowered.cooldown.Cooldown;
import me.kubbidev.nexuspowered.cooldown.CooldownMap;
import me.kubbidev.spellcaster.SpellCaster;
import me.kubbidev.spellcaster.entity.EntityMetadataProvider;
import me.kubbidev.spellcaster.spell.handler.SpellHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Can be used to cast a spell handler with configurable modifier input.
 */
public class SimpleSpell extends Spell {
    private final SpellHandler<?> handler;
    private final Map<String, Double> modifiers = new HashMap<>();

    public SimpleSpell(SpellCaster plugin, SpellHandler<?> handler) {
        super(plugin);
        this.handler = handler;
    }

    @Override
    public boolean getResult(SpellMetadata meta) {
        return EntityMetadataProvider.retrieveCooldown(meta.entity()).testSilently(this.handler);
    }

    @Override
    public void whenCast(SpellMetadata meta) {
        long coolSeconds = Math.max((long) meta.parameter("cooldown"), 0L);

        Cooldown cooldown = Cooldown.of(coolSeconds, TimeUnit.SECONDS);
        cooldown.reset();

        CooldownMap<SpellHandler<?>> cooldownMap = EntityMetadataProvider.retrieveCooldown(meta.entity());
        cooldownMap.put(this.handler, cooldown);
    }

    @Override
    public SpellHandler<?> getHandler() {
        return this.handler;
    }

    @Override
    public double getParameter(String path) {
        return this.modifiers.getOrDefault(path, 0d);
    }

    public void registerModifier(String path, double value) {
        this.modifiers.put(path, value);
    }
}