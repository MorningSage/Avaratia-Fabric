package avaritia._helpers;

import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.UseAction;
import org.jetbrains.annotations.Nullable;
import sun.reflect.ConstructorAccessor;
import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Supplier;


/**
 * This class provides methods to add enum values to existing enums at runtime.
 *
 * For the original source, see:
 * https://www.niceideas.ch/roller2/badtrash/entry/java_create_enum_instances_dynamically
 */
public final class EnumHelper {
    private static final ReflectionFactory reflectionFactory = ReflectionFactory.getReflectionFactory();

    private static final Map<Class<?>, Class<?>[]> constructorSignatures = new HashMap<Class<?>, Class<?>[]>() {{
        put(ArmorMaterials.class, new Class<?>[] {
            String.class, int.class, int[].class, int.class, SoundEvent.class, float.class, float.class, Supplier.class
        });
        put(ToolMaterials.class, new Class<?>[] {
            int.class, int.class, float.class, float.class, int.class, Supplier.class
        });
        put(Rarity.class, new Class<?>[] {
            Formatting.class
        });
    }};

    /**
     * Add an enum instance to the enum class given as argument
     *
     * @param <T> the type of the enum (implicit)
     * @param enumType the class of the enum to be modified
     * @param enumName the name of the new enum instance to be added to the class.
     */
    @SuppressWarnings("unchecked")
    @Nullable
    private static <T extends Enum<?>> T addEnum(Class<T> enumType, String enumName, Object... args) {
        // 0. Sanity checks
        if (!Enum.class.isAssignableFrom(enumType)) {
            throw new RuntimeException("class " + enumType + " is not an instance of Enum");
        }

        // 0.a. Look up the signature
        Class<?>[] signature = new Class<?>[]{};
        if (constructorSignatures.containsKey(enumType)) signature = constructorSignatures.get(enumType);

        // 1. Lookup "$VALUES" holder or the array return type in enum class and get previous enum instances
        Field valuesField = null;
        Field[] fields = enumType.getDeclaredFields();

        for (Field field : fields) {
            if (field.getName().contains("$VALUES") || field.getType().getName().equals("[L" + enumType.getName() + ";")) {
                valuesField = field;
                break;
            }
        }

        if (valuesField == null) return null;

        AccessibleObject.setAccessible(new Field[] { valuesField }, true);

        try {
            // 2. Copy it
            T[] previousValues = (T[]) valuesField.get(enumType);
            List<T> values = new ArrayList<T>(Arrays.asList(previousValues));

            // 3. build new enum
            T newValue = (T) makeEnum(enumType, // The target enum class
                enumName, // THE NEW ENUM INSTANCE TO BE DYNAMICALLY ADDED
                values.size(),
                signature, // can be used to pass values to the enum constuctor
                args); // can be used to pass values to the enum constuctor

            // 4. add new value
            values.add(newValue);

            // 5. Set new values field
            setFailsafeFieldValue(valuesField, null, values.toArray((T[]) Array.newInstance(enumType, 0)));

            // 6. Clean enum cache
            cleanEnumCache(enumType);

            return newValue;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static Object makeEnum(Class<?> enumClass, String value, int ordinal, Class<?>[] additionalTypes, Object[] additionalValues) throws Exception {
        Object[] parms = new Object[additionalValues.length + 2];
        parms[0] = value;
        parms[1] = ordinal;
        System.arraycopy(additionalValues, 0, parms, 2, additionalValues.length);
        return enumClass.cast(getConstructorAccessor(enumClass, additionalTypes).newInstance(parms));
    }
    private static ConstructorAccessor getConstructorAccessor(Class<?> enumClass, Class<?>[] additionalParameterTypes) throws NoSuchMethodException {
        Class<?>[] parameterTypes = new Class[additionalParameterTypes.length + 2];
        parameterTypes[0] = String.class;
        parameterTypes[1] = int.class;
        System.arraycopy(additionalParameterTypes, 0, parameterTypes, 2, additionalParameterTypes.length);
        return reflectionFactory.newConstructorAccessor(enumClass.getDeclaredConstructor(parameterTypes));
    }
    private static void setFailsafeFieldValue(Field field, Object target, Object value) throws NoSuchFieldException, IllegalAccessException {
        // let's make the field accessible
        field.setAccessible(true);

        // next we change the modifier in the Field instance to
        // not be final anymore, thus tricking reflection into
        // letting us modify the static final field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        int modifiers = modifiersField.getInt(field);

        // blank out the final bit in the modifiers int
        modifiers &= ~Modifier.FINAL;
        modifiersField.setInt(field, modifiers);

        FieldAccessor fa = reflectionFactory.newFieldAccessor(field, false);
        fa.set(target, value);
    }
    private static void cleanEnumCache(Class<?> enumClass) throws NoSuchFieldException, IllegalAccessException {
        blankField(enumClass, "enumConstantDirectory"); // Sun (Oracle?!?) JDK 1.5/6
        blankField(enumClass, "enumConstants"); // IBM JDK
    }
    private static void blankField(Class<?> enumClass, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        for (Field field : Class.class.getDeclaredFields()) {
            if (field.getName().contains(fieldName)) {
                AccessibleObject.setAccessible(new Field[] { field }, true);
                setFailsafeFieldValue(field, enumClass, null);
                break;
            }
        }
    }

    @Nullable
    public static ArmorMaterials addArmorMaterial(String enumName, String materialName, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> supplier) {
        return addEnum(ArmorMaterials.class, enumName.toUpperCase(), materialName, durabilityMultiplier, protectionAmounts, enchantability, equipSound, toughness, knockbackResistance, supplier);
    }

    @Nullable
    public static ToolMaterials addToolMaterial(String enumName, int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        return addEnum(ToolMaterials.class, enumName.toUpperCase(), miningLevel, itemDurability, miningSpeed, attackDamage, enchantability, repairIngredient);
    }

    @Nullable
    public static UseAction addUseAction(String enumName) {
        return addEnum(UseAction.class, enumName.toUpperCase());
    }

    @Nullable
    public static Rarity addRarity(String enumName, Formatting formatting) {
        return addEnum(Rarity.class, enumName.toUpperCase(), formatting);
    }
}
