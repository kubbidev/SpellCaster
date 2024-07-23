package me.kubbidev.spellcaster.manager;

import me.kubbidev.nexuspowered.config.KeyedConfiguration;
import me.kubbidev.nexuspowered.config.key.ConfigKey;
import me.kubbidev.spellcaster.SpellCaster;
import me.kubbidev.spellcaster.config.ConfigKeys;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public final class ConfigManager {
    private final KeyedConfiguration configuration;

    private DecimalFormat decimalFormat;
    private DecimalFormat decimalsFormat;

    public ConfigManager(@NotNull SpellCaster plugin) {
        this.configuration = plugin.loadKeyedConfig("config.yml", ConfigKeys.getKeys());
        this.decimalFormat = newDecimalFormat("0.#");
        this.decimalsFormat = newDecimalFormat("0.##");
    }

    public void reload() {
        this.configuration.reload();
        this.decimalFormat = newDecimalFormat("0.#");
        this.decimalsFormat = newDecimalFormat("0.##");
    }

    public <T> T get(@NotNull ConfigKey<T> key) {
        return this.configuration.get(key);
    }

    @NotNull
    public DecimalFormat getDecimalFormat() {
        return this.decimalFormat;
    }

    @NotNull
    public DecimalFormat getDecimalsFormat() {
        return this.decimalsFormat;
    }

    /**
     * The plugin mostly cache the return value of that method in fields
     * for easy access, therefore a server restart is required when editing the
     * decimal-separator option in the config
     *
     * @param pattern Something like "0.#"
     * @return New decimal format with the decimal separator given by the config.
     */
    @NotNull
    public DecimalFormat newDecimalFormat(String pattern) {
        return new DecimalFormat(pattern, get(ConfigKeys.DECIMAL_FORMAT_SEPARATOR));
    }
}
