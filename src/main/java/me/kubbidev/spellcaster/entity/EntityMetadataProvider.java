package me.kubbidev.spellcaster.entity;

import com.google.common.reflect.TypeToken;
import me.kubbidev.nexuspowered.cooldown.CooldownMap;
import me.kubbidev.nexuspowered.metadata.Metadata;
import me.kubbidev.nexuspowered.metadata.MetadataKey;
import me.kubbidev.spellcaster.entity.spellmod.SpellModifierMap;
import me.kubbidev.spellcaster.spell.handler.SpellHandler;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
@ApiStatus.Internal
public final class EntityMetadataProvider {
    private EntityMetadataProvider() {
    }

    /**
     * Metadata key used to retrieve {@link LivingEntity} cooldown map from memory.
     */
    private static final MetadataKey<CooldownMap<SpellHandler<?>>> COOLDOWN_MAP = MetadataKey.create("cooldown_map", new TypeToken<>() {
    });

    /**
     * Gets the provided {@link LivingEntity}'s cooldown map associated to him.
     *
     * @param entity The entity owning the map.
     * @return cooldown map or new instance if not found
     */
    public static CooldownMap<SpellHandler<?>> getCooldownMap(LivingEntity entity) {
        return Metadata.provide(entity).getOrPut(COOLDOWN_MAP, CooldownMap::create);
    }

    /**
     * Metadata key used to retrieve {@link org.bukkit.entity.LivingEntity} spell modifiers map from memory.
     */
    private static final MetadataKey<SpellModifierMap> SPELL_MODIFIER_MAP = MetadataKey.create("spell_modifier_map", SpellModifierMap.class);

    /**
     * Gets the provided {@link LivingEntity}'s spell modifier map associated to him.
     *
     * @param entity The entity owning the map.
     * @return spell modifier map or new instance if not found
     */
    public static SpellModifierMap getSpellModifierMap(LivingEntity entity) {
        return Metadata.provide(entity).getOrPut(SPELL_MODIFIER_MAP, () -> new SpellModifierMap(entity));
    }
}