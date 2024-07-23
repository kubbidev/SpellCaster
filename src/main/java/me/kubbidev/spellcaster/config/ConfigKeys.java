package me.kubbidev.spellcaster.config;

import me.kubbidev.nexuspowered.config.KeyedConfiguration;
import me.kubbidev.nexuspowered.config.key.ConfigKey;
import me.kubbidev.nexuspowered.config.key.SimpleConfigKey;
import me.kubbidev.spellcaster.interaction.EmptyInteractionRules;
import me.kubbidev.spellcaster.interaction.InteractionRules;
import me.kubbidev.spellcaster.interaction.InteractionRulesImpl;
import me.kubbidev.spellcaster.listener.indicator.IndicatorConfig;
import me.kubbidev.spellcaster.listener.indicator.type.DamageIndicatorConfig;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import static me.kubbidev.nexuspowered.config.key.ConfigKeyFactory.*;

/**
 * All of the {@link ConfigKey}s used by SpellCaster.
 *
 * <p>The {@link #getKeys()} method and associated behaviour allows this class
 * to function a bit like an enum, but with generics.</p>
 */
public final class ConfigKeys {
    private ConfigKeys() {
    }

    /**
     * Main number formatting separator symbol used in every decimal formatter across the plugin.
     */
    public static final ConfigKey<DecimalFormatSymbols> DECIMAL_FORMAT_SEPARATOR = key(c -> {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.ROOT);
        decimalFormatSymbols.setDecimalSeparator(c.getString("decimal-format-separator", ".").charAt(0));
        return decimalFormatSymbols;
    });

    /**
     * Whether or not SpellCaster should display damage indicators.
     */
    public static final ConfigKey<Boolean> INDICATOR_DAMAGE_ENABLED = booleanKey("indicators.damage.enabled", true);

    @SuppressWarnings("UnnecessaryUnicodeEscape")
    public static final ConfigKey<DamageIndicatorConfig> INDICATOR_DAMAGE_CONFIG = key(c -> new DamageIndicatorConfig(
            new DecimalFormat(c.getString("indicators.damage.decimal-format", "0.#"), ConfigKeys.DECIMAL_FORMAT_SEPARATOR.get(c)),
            c.getString("indicators.damage.format", "{icon} <white>{value}</white>"),
            c.getDouble("indicators.damage.gravity", 1.0),
            c.getDouble("indicators.damage.radial-velocity", 1.0),
            c.getDouble("indicators.damage.initial-upward-velocity", 1.0),
            c.getDouble("indicators.damage.entity-height-percent", 0.75),
            c.getDouble("indicators.damage.entity-y-offset", 0.1),
            c.getString("indicators.damage.icon.spell.normal", "<gold>\u2605</gold>"),
            c.getString("indicators.damage.icon.spell.crit", "<gold><bold>\u2605</bold></gold>"),
            c.getString("indicators.damage.icon.weapon.normal", "<red>\uD83D\uDDE1</red>"),
            c.getString("indicators.damage.icon.weapon.crit", "<red><bold>\uD83D\uDDE1</bold></red>"),
            c.getBoolean("indicators.damage.split-holograms", true)
    ));

    /**
     * Whether or not SpellCaster should display regeneration indicators.
     */
    public static final ConfigKey<Boolean> INDICATOR_REGENERATION_ENABLED = booleanKey("indicators.regeneration.enabled", true);

    public static final ConfigKey<IndicatorConfig> INDICATOR_REGENERATION_CONFIG = key(c -> new IndicatorConfig(
            new DecimalFormat(c.getString("indicators.regeneration.decimal-format", "0.#"), ConfigKeys.DECIMAL_FORMAT_SEPARATOR.get(c)),
            c.getString("indicators.regeneration.format", "<green>+#</green>"),
            c.getDouble("indicators.regeneration.gravity", 1.0),
            c.getDouble("indicators.regeneration.radial-velocity", 1.0),
            c.getDouble("indicators.regeneration.initial-upward-velocity", 1.0),
            c.getDouble("indicators.regeneration.entity-height-percent", 0.75),
            c.getDouble("indicators.regeneration.entity-y-offset", 0.1)
    ));

    /**
     * If SpellCaster should applied specific rules on entities actions when casting spells or damaging others.
     */
    public static final ConfigKey<InteractionRules> INTERACTION_RULES = key(c -> {
        boolean isEnabled = c.getBoolean("interaction-rules.enabled", true);
        return isEnabled
                ? new InteractionRulesImpl(c)
                : new EmptyInteractionRules();
    });

    /**
     * A list of the keys defined in this class.
     */
    private static final List<SimpleConfigKey<?>> KEYS = KeyedConfiguration.initialise(ConfigKeys.class);

    public static List<? extends ConfigKey<?>> getKeys() {
        return KEYS;
    }
}
