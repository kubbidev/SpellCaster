package me.kubbidev.spellcaster;

import me.kubbidev.nexuspowered.plugin.ExtendedJavaPlugin;
import me.kubbidev.spellcaster.listener.AttackEventListener;
import me.kubbidev.spellcaster.manager.*;
import org.jetbrains.annotations.NotNull;

public final class SpellCaster extends ExtendedJavaPlugin {
    // init during enable
    private ConfigManager configuration;

    private DamageManager damageManager;
    private EntityManager entityManager;

    private final IndicatorManager indicatorManager = new IndicatorManager();
    private final FakeEventManager fakeEventManager = new FakeEventManager();

    @Override
    public void load() {

    }

    @Override
    public void enable() {
        // load configuration
        getLogger().info("Loading configuration...");
        this.configuration = new ConfigManager(this);

        this.damageManager = new DamageManager(this);
        this.entityManager = new EntityManager(this);

        // load indicators from configuration file
        this.indicatorManager.load(this);

        // register listeners
        registerPlatformListeners();

        // register with the SpellCaster API
        SpellCasterProvider.register(this);
        registerApiOnPlatform();
    }

    @Override
    public void disable() {
        // unregister api
        SpellCasterProvider.unregister();
    }

    public void reloadPlugin() {
        this.configuration.reload();
        // reload in game indicators as well after
        // the configuration reload itself
        this.indicatorManager.reload(this);
    }

    private void registerPlatformListeners() {
        registerListener(this.damageManager);
        registerListener(new AttackEventListener(this));
    }

    private void registerApiOnPlatform() {
        provideService(SpellCaster.class, this);
    }

    public @NotNull ConfigManager getConfiguration() {
        return this.configuration;
    }

    public @NotNull DamageManager getDamageManager() {
        return this.damageManager;
    }

    public @NotNull EntityManager getEntityManager() {
        return this.entityManager;
    }

    public @NotNull IndicatorManager getIndicatorManager() {
        return this.indicatorManager;
    }

    public @NotNull FakeEventManager getFakeEventManager() {
        return this.fakeEventManager;
    }
}
