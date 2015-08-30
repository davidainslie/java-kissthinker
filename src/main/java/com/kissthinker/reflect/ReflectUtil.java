package com.kissthinker.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;
import com.kissthinker.object.Singleton;
import static java.text.MessageFormat.format;

/**
 * @author David Ainslie
 *
 */
@Singleton
public final class ReflectUtil
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtil.class);

    /** */
    private static final ReflectionFactory REFLECTION_FACTORY = ReflectionFactory.getReflectionFactory();

    /**
     *
     * @param <O> Object type
     * @param field field
     * @return Object
     */
    @SuppressWarnings("unchecked")
    public static <O> O get(Field field)
    {
        return (O)doGet(null, field);
    }

    /**
     *
     * @param <O> Object type
     * @param object Object
     * @param field Field
     * @return Object
     */
    @SuppressWarnings("unchecked")
    public static <O> O get(Object object, Field field)
    {
        return (O)doGet(object, field);
    }

    /**
     * Set a given static field to the given value.
     * The field may be final.
     * @param field Field
     * @param value Value
     * @return true if set operation completed without error
     */
    public static boolean set(Field field, Object value)
    {
        return doSet(null, field, value);
    }

    /**
     * Set a given field to the given value for the given object.
     * The field may be final and/or static.
     * @param object Object
     * @param field Field
     * @param value Value
     * @return true if set operation completed without error
     */
    public static boolean set(Object object, Field field, Object value)
    {
        return doSet(object, field, value);
    }

    /**
     *
     * @param <O> Object type
     * @param object Object
     * @param field Field
     * @return Object
     */
    @SuppressWarnings("unchecked")
    private static <O> O doGet(Object object, Field field)
    {
        field.setAccessible(true);

        try
        {
            return (O)field.get(object);
        }
        catch (Exception e)
        {
            LOGGER.warn(format("Issue getting {0}field {1} from {2}, and so returning null",
                                field.getName(),
                                object == null ? "static " : "",
                                field.getDeclaringClass().getName()), e);
            return null;
        }
    }

    /**
     * Set a given field to the given value for the possibly given object i.e maybe no object if given so should be a static field.
     * @param object Object
     * @param field Field
     * @param value Value
     * @return true if set operation completed without error
     */
    private static boolean doSet(Object object, Field field, Object value)
    {
        try
        {
            field.setAccessible(true);            
            
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, modifiersField.getInt(field) & ~Modifier.FINAL);

            FieldAccessor fieldAccessor = REFLECTION_FACTORY.newFieldAccessor(field, false);

            fieldAccessor.set(object, value);
        }
        catch (Exception e)
        {
            // TODO
        }
        
        return true;
    }

    /**
     * Utility.
     */
    private ReflectUtil()
    {
        super();
    }
}