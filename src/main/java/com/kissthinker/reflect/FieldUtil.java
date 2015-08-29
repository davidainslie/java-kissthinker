package com.kissthinker.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kissthinker.object.Singleton;

/**
 * @author David Ainslie
 */
@Singleton
public final class FieldUtil
{
    /** */
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldUtil.class);

    /**
     *
     * @param <O> Object type
     * @param object Object
     * @param annotationClass Annotation class
     * @return Field
     */
    public static <O> Field field(O object, Class<? extends Annotation> annotationClass)
    {
        Field[] fields = fields(object, annotationClass);

        if (fields == null)
        {
            return null;
        }
        else if (fields.length == 1)
        {
            return fields[0];
        }
        else
        {
            throw new FieldException(String.format("Found %s occurences of required annotation %s in object %s, when there should be only one!",
                                                   fields.length, annotationClass, object));
        }
    }

    /**
     *
     * @param <O> Object type
     * @param object Object
     * @param fieldName Field name
     * @param annotationClass Annotation class
     * @return Field
     */
    public static <O> Field field(O object, String fieldName, Class<? extends Annotation> annotationClass)
    {
        Field[] fields = fields(object, annotationClass);

        if (fields != null)
        {
            for (Field field : fields)
            {
                field.setAccessible(true);
                
                if (field.getName().equals(fieldName))
                {
                    return field;
                }
            }
        }

        return null;
    }

    /**
     *
     * @param object Object
     * @param annotationClass Annotation class
     * @param <O> Object type
     * @return Field array
     */
    public static <O> Field[] fields(O object, Class<? extends Annotation> annotationClass)
    {
        assert(object != null);
        
        // Safety check, in case (somehow) this method was called with a given class (maybe by reflection).
        if (object instanceof Class<?>)
        {
            return fields((Class<?>)object, annotationClass);
        }
        
        return fields(object.getClass(), annotationClass);
    }

    /**
     * 
     * @param class_ Class
     * @param annotationClass Annotation class
     * @param <O> Object type
     * @return Field array
     */
    public static <O> Field[] fields(Class<?> class_, Class<? extends Annotation> annotationClass)
    {
        assert(class_ != null);

        class_ = declaredClass(class_);

        List<Field> fields = new ArrayList<>();

        if (class_.getSuperclass() != null)
        {
            List<Field> superFields = new ArrayList<>();
            Collections.addAll(superFields, fields(class_.getSuperclass(), annotationClass));
            fields.addAll(superFields);
        }
        
        for (Field field : class_.getDeclaredFields())
        {
            field.setAccessible(true);
            
            if (field.isAnnotationPresent(annotationClass))
            {
                fields.add(field);
            }
        }

        return fields.toArray(new Field[0]);
    }

    /**
     *
     * @param object Object
     * @param annotationClass Annotation class
     * @return Object array
     */
    public static Object[] values(Object object, Class<? extends Annotation> annotationClass)
    {
        try
        {
            Field[] fields = fields(object, annotationClass);

            List<Object> objects = new ArrayList<>();

            for (Field field : fields)
            {
                field.setAccessible(true);
                objects.add(field.get(object));
            }

            return objects.toArray(new Object[0]);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage());
            throw new FieldException(e);
        }
    }

    /**
     * 
     * @param field Field
     * @param target Target
     * @param <V> Value type
     * @return Value
     */
    @SuppressWarnings("unchecked")
    public static <V> V value(Field field, Object target)
    {
        try
        {
            field.setAccessible(true);
            return (V)field.get(target);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Get declared class that is not an anonymous class;
     * @param class_ Class
     * @return Class
     */
    public static Class<?> declaredClass(Class<?> class_)
    {
        if (class_.getName().contains("$"))
        {
            LOGGER.info("Anonymous class so aquiring 'superclass'");
            return declaredClass(class_.getSuperclass());
        }
        else
        {
            return class_;
        }
    }

    /**
     *
     * @param object Object
     * @param name Name
     * @param value Value
     */
    public static void set(Object object, String name, Object value)
    {
        Field field = null;

        try
        {
            field = object.getClass().getField(name);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
        }

        if (field == null)
        {
            try
            {
                field = object.getClass().getDeclaredField(name);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
            }
        }

        if (field != null)
        {
            field.setAccessible(true);

            try
            {
                System.out.println(field.get(object));
                field.set(object, value);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
            }
        }
    }

    /**
     * Utility.
     */
    private FieldUtil()
    {
        super();
    }
}