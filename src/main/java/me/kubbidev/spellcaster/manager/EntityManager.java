package me.kubbidev.spellcaster.manager;

import me.kubbidev.spellcaster.SpellCaster;
import me.kubbidev.spellcaster.config.ConfigKeys;
import me.kubbidev.spellcaster.event.attack.fake.DamageCheckEvent;
import me.kubbidev.spellcaster.interaction.InteractionRestriction;
import me.kubbidev.spellcaster.interaction.InteractionRules;
import me.kubbidev.spellcaster.interaction.InteractionType;
import me.kubbidev.spellcaster.interaction.relation.Relationship;
import me.kubbidev.spellcaster.interaction.relation.RelationshipHandler;
import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public final class EntityManager {
    private final SpellCaster plugin;
    private final Set<InteractionRestriction> restrictions = new HashSet<>();
    private final Set<RelationshipHandler> relationshipHandlers = new HashSet<>();

    public EntityManager(SpellCaster plugin) {
        this.plugin = plugin;
    }

    /**
     * This should be called by plugins implementing player sets like parties, friends, factions....
     * any set that could support friendly fire.
     * <p>
     * This is also helpful to prevent players from interacting with
     * specific invulnerable entities like NPCs.
     *
     * @param restriction The new restriction for entities
     * @see InteractionRestriction
     */
    public void registerRestriction(InteractionRestriction restriction) {
        this.restrictions.add(restriction);
    }

    /**
     * Plugins which create player groups create relations between entities.
     * <p>
     * Depending on the type of relationship between entities, two entities
     * may or may not be able to pvp/cast spells onto each other.
     *
     * @param handler The handler for entity relations
     * @see RelationshipHandler
     */
    public void registerRelationshipHandler(RelationshipHandler handler) {
        this.relationshipHandlers.add(handler);
    }

    public @NotNull Set<RelationshipHandler> getRelationshipHandlers() {
        return this.relationshipHandlers;
    }

    /**
     * Called whenever an entity tries to damage OR buff another entity.
     * <p>
     * This should be used by:
     * <br>- plugins which implement friendly fire player sets like parties, guilds, nations, factions....
     * <br>- plugins which implement custom invulnerable entities like NPCs, sentinels....
     *
     * @param source The entity targeting another entity
     * @param target The entity Entity being targeted
     * @param type   The type of interaction, whether it's positive (buff, heal) or negative (offense spell, attack)
     * @return True if the interaction between the two entity is possible, otherwise false (should be cancelled!)
     */
    public boolean canInteract(Entity source, Entity target, InteractionType type) {

        // simple verification
        if (source.equals(target) || target.isDead()
                || !(source instanceof LivingEntity)
                || !(target instanceof LivingEntity) || target instanceof ArmorStand)
            return false;

        // specific plugin restrictions
        for (InteractionRestriction restriction : this.restrictions) {
            if (!restriction.canTarget((LivingEntity) source, (LivingEntity) target, type)) {
                return false;
            }
        }

        InteractionRules rules = this.plugin.getConfiguration().get(ConfigKeys.INTERACTION_RULES);
        // pvp interaction rules
        if (target instanceof Player) {
            if (((Player) target).getGameMode() == GameMode.SPECTATOR) {
                return false;
            }

            boolean pvpEnabled = target.getWorld().getPVP();
            if (pvpEnabled) {
                pvpEnabled = new DamageCheckEvent(source, target, type).callEvent();
            }
            // if offense, cancel if the pvp is disabled
            if (type.isOffense() && !pvpEnabled) {
                return false;
            }
            // otherwise check rules
            return isInteractionAllowed(
                    (LivingEntity) source,
                    (LivingEntity) target, rules, type, pvpEnabled);
        } else {
            // pve interaction rules
            return type.isOffense() || rules.isSupportSpellsOnMobs();
        }
    }

    public boolean isInteractionAllowed(@NotNull LivingEntity source,
                                        @NotNull LivingEntity target,

                                        @NotNull InteractionRules rules,
                                        @NotNull InteractionType type, boolean pvp) {
        if (source.equals(target)) {
            return rules.isEnabled(type, Relationship.SELF, pvp);
        }
        for (RelationshipHandler handler : this.relationshipHandlers) {
            if (!rules.isEnabled(type, handler.getRelationship(source, target), pvp)) {
                return false;
            }
        }
        return true;
    }
}