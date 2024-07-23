package me.kubbidev.spellcaster.event.attack;

import me.kubbidev.spellcaster.damage.AttackMetadata;
import me.kubbidev.spellcaster.event.LivingEntityEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EntityKillEntityEvent extends LivingEntityEvent {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    private final EntityDamageEvent event;

    private final LivingEntity target;
    private final AttackMetadata attack;

    public EntityKillEntityEvent(@NotNull EntityDamageEvent event, @NotNull AttackMetadata attack, @NotNull LivingEntity target) {
        super(Objects.requireNonNull(attack.getAttacker(), "attacker").entity());
        this.event = event;
        this.attack = attack;
        this.target = target;
    }

    public EntityDamageEvent toBukkit() {
        return this.event;
    }

    public LivingEntity getTarget() {
        return this.target;
    }

    public AttackMetadata getAttack() {
        return this.attack;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}