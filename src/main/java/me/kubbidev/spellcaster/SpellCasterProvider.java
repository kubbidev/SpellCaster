package me.kubbidev.spellcaster;

import org.jetbrains.annotations.ApiStatus;

/**
 * Provides static access to the {@link SpellCaster} API.
 *
 * <p>Ideally, the ServiceManager for the platform should be used to obtain an
 * instance, however, this provider can be used if this is not viable.</p>
 */
public final class SpellCasterProvider {
    private static SpellCaster instance = null;

    /**
     * Gets an instance of the {@link SpellCaster} API,
     * throwing {@link IllegalStateException} if the API is not loaded yet.
     *
     * <p>This method will never return null.</p>
     *
     * @return an instance of the SpellCaster API
     * @throws IllegalStateException if the API is not loaded yet
     */
    public static SpellCaster get() {
        SpellCaster instance = SpellCasterProvider.instance;
        if (instance == null) {
            throw new NotLoadedException();
        }
        return instance;
    }

    @ApiStatus.Internal
    static void register(SpellCaster instance) {
        SpellCasterProvider.instance = instance;
    }

    @ApiStatus.Internal
    static void unregister() {
        SpellCasterProvider.instance = null;
    }

    @ApiStatus.Internal
    private SpellCasterProvider() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    /**
     * Exception thrown when the API is requested before it has been loaded.
     */
    private static final class NotLoadedException extends IllegalStateException {
        private static final String MESSAGE = """
                The SpellCaster API isn't loaded yet!
                This could be because:
                  a) the SpellCaster plugin is not installed or it failed to enable
                  b) the plugin in the stacktrace does not declare a dependency on SpellCaster
                  c) the plugin in the stacktrace is retrieving the API before the plugin 'enable' phase
                     (call the #get method in onEnable, not the constructor!)
                """;

        NotLoadedException() {
            super(MESSAGE);
        }
    }
}