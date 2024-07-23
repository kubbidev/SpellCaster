package me.kubbidev.spellcaster.damage;

import me.kubbidev.spellcaster.entity.EntityMetadata;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Used by attacks caused by projectiles like ranged spells or weapon attacks with bows, crossbows or tridents.
 */
public class ProjectileAttackMetadata extends AttackMetadata {
    private final Projectile projectile;

    /**
     * Used by {@link AttackHandler} instances to register attacks.
     * <p>
     * {@link DamageMetadata} only gives information about the attack damage and types while
     * this class also contains info about the damager.
     * <p>
     * Some plugins don't let SpellCaster determine what the damager is so there might
     * be problem with damage/reduction stat application.
     *
     * @param metadata The attack result.
     * @param target   The entity that received the damage.
     * @param attacker The entity who dealt the damage.
     */
    public ProjectileAttackMetadata(@NotNull DamageMetadata metadata, @NotNull LivingEntity target, @Nullable EntityMetadata attacker, Projectile projectile) {
        super(metadata, target, attacker);
        this.projectile = projectile;
    }

    public Projectile getProjectile() {
        return this.projectile;
    }
}