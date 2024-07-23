package me.kubbidev.spellcaster.spell;

import me.kubbidev.spellcaster.SpellCaster;
import me.kubbidev.spellcaster.event.spell.PostSpellCastEvent;
import me.kubbidev.spellcaster.event.spell.PreSpellCastEvent;
import me.kubbidev.spellcaster.spell.handler.SpellHandler;
import me.kubbidev.spellcaster.spell.result.SpellResult;
import me.kubbidev.spellcaster.spell.trigger.TriggerMetadata;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public abstract class Spell {
    private final SpellCaster plugin;

    public Spell(SpellCaster plugin) {
        this.plugin = plugin;
    }

    public @NotNull SpellResult cast(LivingEntity caster) {
        return cast(new TriggerMetadata(caster));
    }

    public @NotNull SpellResult cast(TriggerMetadata triggerMeta) {
        return cast(triggerMeta.toSpellMetadata(this));
    }

    public @NotNull SpellCaster getPlugin() {
        return this.plugin;
    }

    @SuppressWarnings("unchecked")
    public <T extends SpellResult> SpellResult cast(SpellMetadata meta) {
        SpellHandler<T> handler = (SpellHandler<T>) getHandler();

        // lower level spell restrictions
        T result = handler.getResult(meta);
        if (!result.isSuccessful()) return result;

        // high level spell restrictions
        if (!getResult(meta)) return result;

        // call first bukkit event
        PreSpellCastEvent called = new PreSpellCastEvent(meta, result);
        if (!called.callEvent()) {
            return result;
        }

        // if the delay is null we cast normally the spell
        int delayTicks = (int) (meta.parameter("delay") * 20);
        if (delayTicks <= 0) {
            castInstantly(meta, result);
        }
        // todo implement delayed casting
        return result;
    }

    /**
     * Called when the casting delay (potentially zero) is passed.
     * <p>
     * This does not call {@link PreSpellCastEvent} and does not
     * check for both high & low level spell conditions.
     * <p>
     * This method however calls {@link PostSpellCastEvent} after spell casting.
     */
    @SuppressWarnings("unchecked")
    public <T extends SpellResult> void castInstantly(SpellMetadata meta, T result) {
        // high level spell effects
        whenCast(meta);

        // lower level spell effects
        SpellHandler<T> handler = (SpellHandler<T>) getHandler();
        handler.whenCast(result, meta);

        // call second bukkit event
        PostSpellCastEvent called = new PostSpellCastEvent(meta, result);
        called.callEvent();
    }

    /**
     * This method should be used to check for resource costs
     * or other spell limitations.
     * <p>
     * Runs last after {@link SpellHandler#getResult(SpellMetadata)}.
     *
     * @param meta the info of spell being cast.
     * @return True if the spell can be cast, otherwise false
     */
    public abstract boolean getResult(SpellMetadata meta);

    /**
     * This is not where the actual spell effects are applied.
     * <p>
     * This method should be used to handle resource costs or
     * cooldown messages if required.
     * <p>
     * Runs first before {@link SpellHandler#whenCast(SpellResult, SpellMetadata)}.
     *
     * @param meta The info of spell being cast.
     */
    public abstract void whenCast(SpellMetadata meta);

    /**
     * Gets the {@link SpellHandler} containing all effects used to
     * to be applied on spell casting.
     *
     * @return The handler instance of this spell.
     */
    public abstract SpellHandler<?> getHandler();

    /**
     * !! WARNING !! Final spell parameter values also depend
     * on the entity's spell modifiers, and this method does NOT
     * take them into account.
     *
     * @param path The modifier name.
     * @return The spell parameter value unaffected by spell modifiers.
     * @see SpellMetadata#parameter(String)
     */
    public abstract double getParameter(String path);
}