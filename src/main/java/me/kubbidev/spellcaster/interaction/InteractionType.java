package me.kubbidev.spellcaster.interaction;

public enum InteractionType {

    /**
     * Any spell that damages or harms another entity.
     */
    OFFENSE_SPELL,

    /**
     * Any spell that applies a buffs or regenerates some resource to an entity.
     */
    SUPPORT_SPELL,

    /**
     * Any other offense based action like melee attacks.
     */
    OFFENSE_ACTION,

    /**
     * Any other support/friendly action.
     */
    SUPPORT_ACTION;

    public boolean isSpell() {
        return this == OFFENSE_SPELL || this == SUPPORT_SPELL;
    }

    public boolean isOffense() {
        return this == OFFENSE_ACTION || this == OFFENSE_SPELL;
    }
}