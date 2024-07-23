package me.kubbidev.spellcaster;

import me.kubbidev.nexuspowered.item.ItemStackBuilder;
import me.kubbidev.spellcaster.interaction.InteractionType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A utility class providing various static methods.
 */
public final class InternalMethod {
    private InternalMethod() {
    }

    /**
     * The random number generator used by the utility methods.
     */
    private static final Random random = new Random();

    /**
     * Checks if the source entity can target the target entity using the default interaction type.
     *
     * @param plugin The plugin instance.
     * @param source The source entity attempting to target.
     * @param target The target entity.
     * @return true if the source can target the target entity, false otherwise.
     */
    public static boolean canTarget(SpellCaster plugin, Entity source, Entity target) {
        return canTarget(plugin, source, target, InteractionType.OFFENSE_SPELL);
    }

    /**
     * Checks if the source entity can target the target entity using a specified interaction type.
     *
     * @param plugin The plugin instance.
     * @param source The source entity attempting to target.
     * @param target The target entity.
     * @param type   The type of interaction.
     * @return true if the source can target the target entity, false otherwise.
     */
    public static boolean canTarget(SpellCaster plugin, Entity source, Entity target, InteractionType type) {
        return plugin.getEntityManager().canInteract(source, target, type);
    }

    /**
     * Retrieves all entities in the chunks surrounding the given location.
     *
     * @param location The location used to determine the chunks to search.
     * @return A list of entities found in the surrounding chunks.
     */
    public static List<Entity> getNearbyChunkEntities(Location location) {
        List<Entity> entities = new ArrayList<>();

        int cx = location.getChunk().getX();
        int cz = location.getChunk().getZ();

        for (int x = -1; x < 2; x++) {
            for (int z = -1; z < 2; z++) {
                entities.addAll(Arrays.asList(location.getWorld().getChunkAt(cx + x, cz + z).getEntities()));
            }
        }

        return entities;
    }

    /**
     * Checks if an entity is vanished based on its metadata.
     *
     * @param entity The entity to check.
     * @return true if the entity is vanished, false otherwise.
     */
    public static boolean isVanished(Metadatable entity) {
        return entity.getMetadata("vanished").stream().anyMatch(MetadataValue::asBoolean);
    }

    /**
     * Checks if an item stack is air or null.
     *
     * @param item The item stack to check. Can be null.
     * @return true if the item stack is air or null, false otherwise.
     */
    public static boolean isAir(@Nullable ItemStack item) {
        return item == null || item.getType().isAir();
    }

    /**
     * Checks if an item stack is considered a weapon based on its durability
     * (Purely arbitrary but works decently).
     *
     * @param item The item stack to check. Can be null.
     * @return true if the item stack is a weapon, false otherwise.
     */
    public static boolean isWeapon(@Nullable ItemStack item) {
        return item != null && item.getType().getMaxDurability() > 0;
    }

    /**
     * Heals a damageable entity by the specified amount.
     *
     * @param <T>        The type of the entity to be healed, which must implement Damageable and Attributable.
     * @param entity     The entity to be healed.
     * @param healAmount The amount of health to regain. This value must be positive.
     */
    public static <T extends Damageable & Attributable> void heal(T entity, double healAmount) {
        heal(entity, healAmount, false);
    }

    /**
     * Heals a damageable entity by the specified amount.
     *
     * @param <T>            The type of the entity to be healed, which must implement Damageable and Attributable.
     * @param entity         The entity to be healed.
     * @param healAmount     The amount of health to regain. If allowNegatives is false, this value must be positive.
     * @param allowNegatives Whether negative heal amounts are allowed. If false, healAmount must be positive to heal the entity.
     */
    public static <T extends Damageable & Attributable> void heal(T entity, double healAmount, boolean allowNegatives) {
        if (!(healAmount > 0) && !allowNegatives) {
            throw new IllegalArgumentException("Heal amount must be strictly positive");
        }
        double currentHealth = entity.getHealth();
        double maxHealth = getAttributeValue(entity, Attribute.GENERIC_MAX_HEALTH);

        EntityRegainHealthEvent called = new EntityRegainHealthEvent(entity, healAmount, EntityRegainHealthEvent.RegainReason.CUSTOM);
        if (called.callEvent()) {
            entity.setHealth(Math.min(currentHealth + called.getAmount(), maxHealth));
        }
    }

    /**
     * Safely retrieves the value of an attribute for a given entity.
     *
     * @param entity    The entity whose attribute value is being retrieved.
     * @param attribute The attribute whose value is being retrieved.
     * @return The value of the attribute for the entity, or 0.0 if the attribute instance is not found.
     */
    public static double getAttributeValue(Attributable entity, Attribute attribute) {
        AttributeInstance attributeInstance = entity.getAttribute(attribute);
        return attributeInstance != null ? attributeInstance.getValue() : 0.0;
    }

    /**
     * Safely retrieves the base value of an attribute for a given entity.
     *
     * @param entity    The entity whose attribute base value is being retrieved.
     * @param attribute The attribute whose base value is being retrieved.
     * @return The base value of the attribute for the entity, or 0.0 if the attribute instance is not found.
     */
    public static double getAttributeBaseValue(Attributable entity, Attribute attribute) {
        AttributeInstance attributeInstance = entity.getAttribute(attribute);
        return attributeInstance != null ? attributeInstance.getBaseValue() : 0.0;
    }

    /**
     * Safely retrieves the default value of an attribute for a given entity.
     *
     * @param entity    The entity whose attribute default value is being retrieved.
     * @param attribute The attribute whose default value is being retrieved.
     * @return The default value of the attribute for the entity, or 0.0 if the attribute instance is not found.
     */
    public static double getAttributeDefaultValue(Attributable entity, Attribute attribute) {
        AttributeInstance attributeInstance = entity.getAttribute(attribute);
        return attributeInstance != null ? attributeInstance.getDefaultValue() : 0.0;
    }

    /**
     * Converts a camel case string (e.g., "MySimpleWord") to kebab-case (e.g., "my_simple_word").
     *
     * @param input the camel case string to be converted
     * @return the converted kebab-case string, or the input string if it is null or empty
     */
    public static String convertToKebabCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();

        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                if (!result.isEmpty()) {
                    result.append('_');
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * Super useful to display enum names like DIAMOND_SWORD in chat.
     *
     * @param input String with lower cases and spaces only
     * @return Same string with capital letters at the beginning of each word.
     */
    public static String caseOnWords(String input) {
        StringBuilder builder = new StringBuilder(input);

        boolean isLastSpace = true;
        for (int i = 0; i < builder.length(); i++) {
            char ch = builder.charAt(i);
            if (isLastSpace && ch >= 'a' && ch <= 'z') {
                builder.setCharAt(i, (char) (ch + ('A' - 'a')));
                isLastSpace = false;
            } else {
                isLastSpace = (ch == ' ');
            }
        }
        return builder.toString();
    }

    /**
     * Reads an icon string and converts it into an {@link ItemStack}.
     * <p>
     * The icon string should be in the format "MATERIAL" or "MATERIAL:customModelData".
     * <p>
     * Example formats:
     * <ul>
     * <li>{@code DIAMOND}</li>
     * <li>{@code DIAMOND:123}</li>
     * </ul>
     *
     * @param icon The icon string representing the material and optional custom model data.
     * @return The created {@link ItemStack}.
     * @throws IllegalArgumentException If the material is invalid or the custom model data is not a number.
     */
    public static ItemStack readIcon(String icon) throws IllegalArgumentException {
        String[] split = icon.split(":");
        Material material = Material.valueOf(split[0].toUpperCase(Locale.ROOT)
                .replace("-", "_")
                .replace(" ", "_"));

        ItemStackBuilder itemStack = ItemStackBuilder.of(material);
        if (split.length > 1) {
            itemStack.customModelData(Integer.parseInt(split[1]));
        }
        return itemStack.build();
    }
}
