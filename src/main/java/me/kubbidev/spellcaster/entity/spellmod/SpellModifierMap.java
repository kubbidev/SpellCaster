package me.kubbidev.spellcaster.entity.spellmod;

import me.kubbidev.spellcaster.entity.modfier.ModifierMap;
import me.kubbidev.spellcaster.entity.modfier.ModifierType;
import me.kubbidev.spellcaster.spell.Spell;
import me.kubbidev.spellcaster.spell.handler.SpellHandler;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class SpellModifierMap extends ModifierMap<SpellModifier> {
    public SpellModifierMap(LivingEntity entity) {
        super(entity);
    }

    public double calculateValue(@NotNull Spell cast, @NotNull String parameter) {
        return calculateValue(cast.getHandler(), cast.getParameter(parameter), parameter);
    }

    public double calculateValue(@NotNull SpellHandler<?> handler, double base, @NotNull String parameter) {
        for (SpellModifier mod : getModifiers())
            if (mod.getType() == ModifierType.FLAT
                    && mod.getParameter().equals(parameter)
                    && mod.getSpells().contains(handler)) {
                base += mod.getValue();
            }

        for (SpellModifier mod : getModifiers())
            if (mod.getType() == ModifierType.RELATIVE
                    && mod.getParameter().equals(parameter)
                    && mod.getSpells().contains(handler)) {
                base *= 1 + mod.getValue() / 100;
            }

        return base;
    }
}