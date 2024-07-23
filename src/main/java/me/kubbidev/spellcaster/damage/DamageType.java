package me.kubbidev.spellcaster.damage;

public enum DamageType {
    /**
     * Magic damage dealt by magic weapons or abilities
     */
    MAGIC,
    /**
     * Physical damage dealt by melee attacks or spells
     */
    PHYSICAL,
    /**
     * Damage dealt by any type of weapon
     */
    WEAPON,
    /**
     * Damage dealt by spells or abilities
     */
    SPELL,
    /**
     * Projectile based weapons or spells
     */
    PROJECTILE,
    /**
     * Hitting an enemy with bare hands
     */
    UNARMED,
    /**
     * Damage over time
     */
    DOT
}